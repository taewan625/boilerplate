const paymentModule = (() => {
    let availableTickets = {};
    let paymentInfo = {};

    let stepOneFormValidator;
    let stepTwoFormValidator;
    let stepThreeFormValidator;

    const beforeUnloadHandler = (e) => {
        e.preventDefault();
        e.returnValue = ''; // 크롬, 사파리, 파이어폭스 모두 필요
    };

    const setAvailableTickets = () => {
        $.ajax({
            type: 'GET',
            url: `/api/v1/woc/sales-tickets`,
            success: (res, textStatus, jqXHR) => {
                if (res.success) {
                    const data = res.data;

                    //티켓 판매 종료된 경우
                    if (data.length <= 0) {
                        alert('Oops!\nIt looks like you tried to access this page in an invalid way.');
                        window.location.href = '/';
                        return false;
                    }

                    //티켓 정보 저장(불변)
                    availableTickets = Object.freeze(
                        Object.fromEntries(
                            data.map(el => [
                                el.id,
                                Object.freeze({
                                    id: el.id,
                                    ticketName: el.ticketName,
                                    ticketInfo: el.ticketInfo,
                                    price: el.prices[0].price,
                                    currency: el.prices[0].currency
                                })
                            ])
                        )
                    );
                }
            },
            beforeSend: () => {
                loadingStart()
            },
            complete: () => {
                loadingExit()
            },
            error: (jqXHR, textStatus, errorThrown) => {
                alert('Failed to show payment page. Please try again. If the issue persists, please contact our support team.')
                window.location.href = '/';
            },
            async: false,//유효성 검증 체크를 위해 동기 처리
        });
    };

    const setPayment = () => {
        $.ajax({
            type: 'GET',
            url: `/api/v1/woc/payment-info`,
            success: (res, textStatus, jqXHR) => {
                if (res.success) {
                    paymentInfo = Object.freeze(res.data);
                }
            },
            beforeSend: () => {
                loadingStart()
            },
            complete: () => {
                loadingExit()
            },
            error: (jqXHR, textStatus, errorThrown) => {
                alert('Failed to show payment page. Please try again. If the issue persists, please contact our support team.')
            },
        });
    };

    const openPortOne = (data) => {
        try {
            const { amount, buyerEmail, buyerName, buyerTel, currency, language, merchantId, noticeUrl, ticketName, ticketQuantity } = data;

            IMP.init(paymentInfo.init); //가맹점 식별 코드
            IMP.request_pay(
                {
                    pg: paymentInfo.pg, //사용할 PG사 코드 (결제 대행사 식별자)
                    pay_method: "card",
                    notice_url: noticeUrl, //결제 결과 알림을 받는 서버측 콜백 URL (web hook)
                    language: language || 'en', //결제창 언어 설정
                    merchant_uid: merchantId, // 주문 고유 번호
                    name: ticketName,
                    buyer_email: buyerEmail,
                    currency: currency, //통화 단위 (ISO 4217 코드)
                    buyer_addr: "-",//주소
                    buyer_postcode: "-", //우편번호
                    amount: amount, //결제금액
                    buyer_name: buyerName,
                    buyer_tel: buyerTel,
                    popup: true,
                    bypass: {
                        issuercountry: language === 'en' ? 'US' : 'KR' //카드 발급국 정보 TODO 이상함 보편적인 예시) issuercountry: userCountry || 'KR'
                    },
                },
                //결제 완료 후처리
                (response) => {
                    if (response.success) {
                        //새로고침 방지 해제
                        window.removeEventListener('beforeunload', beforeUnloadHandler);

                        //직전 url history를 visitor로 세팅 - 뒤로 가기 시, payment가 아닌 visitor로 이동
                        history.replaceState(null, '', '/visitor');

                        const csrfToken = document.querySelector('meta[name="_csrf"]').content;
                        const csrfParameter = document.querySelector('meta[name="_csrf_parameter"]').content;

                        //post로 이동 form으로 post할 경우 ajax와 달리 request header 세팅이 아니여서 name을 _csrf로 고정
                        const formHtml = `
                                                <form id="paymentDetailForm" class="d-none" method="post" action="/payment/detail">
                                                  <input type="hidden" name="merchantUid" value="${response.merchant_uid}">
                                                  <input type="hidden" name="${csrfParameter}" value="${csrfToken}">
                                                </form>
                                                `;

                        $('body').append(formHtml);
                        $('#paymentDetailForm').submit();

                    } else {
                        alert("Failed to process the payment. Please try again. If the issue persists, please contact our support team.");
                    }
                },
            );
        } catch (error) {
            alert("Failed to process the payment. Please try again. If the issue persists, please contact our support team.");
        }
    };

    const setMultiForm = () => {
        //새로고침 경고 이벤트
        window.addEventListener('beforeunload', beforeUnloadHandler);

        //티켓 목록 렌더링
        Object.values(availableTickets).forEach(element => {
            const ticketHTML = `
                                        <div class="col-12 col-md-6">
                                          <label class="form-check-label d-flex border p-3 gap-4 w-100 js-evt__ticket" for="ticket${element.id}">
                                            <input class="form-check-input position-static" type="radio" name="ticketCard" id="ticket${element.id}" data-id="${element.id}">
                                            <div class="d-flex flex-column gap-3">
                                              <span class="fw-semibold component__desc-font--big">${element.ticketName}</span>
                                              <span class="fw-semibold text-primary-600 component__title-font--small">${element.currency} ${element.price}</span>
                                            </div>
                                          </label>
                                        </div>`;
            $(`#ticketList`).append(ticketHTML);
        });

        //공통코드 세팅
        renderCodeFormHTML('exhibitor-register', [
            {id: 'countryId', type: 'countryList'},
            {id: 'countryCallingNumber', type: 'callingCodeList'},
        ]);

        //email 유효성 검사 및 버튼 이벤트 세팅
        $('#email').off('input').on('input', debounce((event) => {
            const targetValue = event.currentTarget.value;
            const emailRegex = /^[\w\.-]+@[\w\.-]+\.\w+$/;

            $('#verificationEmail').toggleClass('component__button--disabled', !emailRegex.test(targetValue));
        }, 300));

        //select2 세팅
        $('#countryId, #countryCallingNumber, #occupation, #industry, #interest').select2({
            placeholder: 'Select',
            width: '100%',
        });
        $('#experience, #authority, #objective, #ageGroup').select2({
            placeholder: 'Select',
            width: '100%',
            minimumResultsForSearch: Infinity, //검색 비활성화
        });

        //select2 jquery 유효성 세팅
        $('select').off('select2:close').on('select2:close', function () {
            $(this).valid();
        });

        //input 숫자 정규식 세팅
        inputNumberRegex('#verificationCode, #mobileNumber');

        //선택된 티켓 색상 상태 변경 옵션
        $('.js-evt__ticket').off('click').on('click', (e) => {
            $('.js-evt__ticket').removeClass('border-primary');
            $(e.currentTarget).addClass('border-primary');
        });

        //유효성 세팅
        stepOneFormValidator = setValid('#stepOneForm', {
            //유효성 적용 요소 name
            rules: {
                email: {required: true, maxlength: 200},
                firstName: {required: true, maxlength: 100},
                lastName: {required: true, maxlength: 100},
                company: {required: true, maxlength: 100},
                jobTitle: {required: true, maxlength: 500},
                countryCallingNumber: {required: true},
                countryId: {required: true},
                city: {required: true, maxlength: 500},
                mobileNumber: {number: true},
                // privacyAgree: {required: true},
                // policyAgree: {required: true},
                ticketCard: {required: true},
            },

            //유효성 실패 메시지
            messages: {
                email: {
                    required: 'is a required field.',
                    maxlength: 'Must be 200 characters or fewer.',
                },
                firstName: {
                    required: 'is a required field.',
                    maxlength: 'Must be 100 characters or fewer.',
                },
                lastName: {
                    required: 'is a required field.',
                    maxlength: 'Must be 100 characters or fewer.',
                },
                company: {
                    required: 'is a required field.',
                    maxlength: 'Must be 100 characters or fewer.',
                },
                jobTitle: {
                    required: 'is a required field.',
                    maxlength: 'Must be 500 characters or fewer.',
                },
                countryId: {
                    required: 'is a required field.',
                },
                city: {
                    required: 'is a required field.',
                    maxlength: 'Must be 500 characters or fewer.',
                },
                verificationCode: {
                    required: 'is a required field.',
                    number: 'Please enter a valid number.'
                },
                privacyAgree: {
                    required: 'You must agree the privacy.'
                },
                policyAgree: {
                    required: 'You must agree the policy.'
                },
            },

            id: '#nextToStepTwo',
        });

        stepTwoFormValidator = setValid('#stepTwoForm', {
            //유효성 적용 요소 name
            rules: {
                occupation: {required: true},
                industry: {required: true},
                //firstTimeAttending: {required: true},
                experience: {required: true},
                authority: {required: true},
                objective: {required: true},
                ageGroup: {required: true},
                interest: {required: true},
            },

            //유효성 실패 메시지
            messages: {},

            //제어 버튼 id
            id: '#nextToStepThree',
        });

        stepThreeFormValidator = setValid('#stepThreeForm', {
            //유효성 적용 요소 name
            rules: {
                privacyAgree: {required: true},
                policyAgree: {required: true},
            },

            //유효성 실패 메시지
            messages: {
                privacyAgree: {
                    required: 'You must agree the privacy.'
                },
                policyAgree: {
                    required: 'You must agree the policy.'
                },
            },

            id: '#submit',
        });
    };

    const setMultiFormStepState = () => {
        let currentStep = 1;

        const updateStepTabs = (beforeChangeCurrentStep, afterChangeCurrentStep, isNextStep) => {
            const beforeChangeStepEl = $(`.js-evt__paymentTab[data-step="${beforeChangeCurrentStep}"]`);
            beforeChangeStepEl.removeClass('payment__current-step');
            beforeChangeStepEl.children('.js-evt__paymentTabTitle').addClass('d-none');

            const afterChangeStepEl = $(`.js-evt__paymentTab[data-step="${afterChangeCurrentStep}"]`);
            afterChangeStepEl.children('.js-evt__paymentTabTitle').removeClass('d-none');
            afterChangeStepEl.addClass('payment__current-step');

            if (isNextStep) {
                beforeChangeStepEl.addClass('payment__step--done');
                afterChangeStepEl.addClass('payment__step--active');
            }
        }

        const updateStepVisibility = (afterChangeCurrentStep) => {
            $('.js-evt__step').addClass('d-none');
            $(`.js-evt__step[data-step="${afterChangeCurrentStep}"]`).removeClass('d-none');
        };

        const handleStepValidation = (beforeChangeCurrentStep) => {
            //유효성 검증
            if (beforeChangeCurrentStep < 1  || 3 < beforeChangeCurrentStep) return false;

            const validateInfos = {
                1: { formIds: ['#stepOneForm'], buttonId: '#nextToStepTwo' },
                2: { formIds: ['#stepTwoForm'], buttonId: '#nextToStepThree' },
                3: { formIds: ['#stepOneForm', '#stepTwoForm', '#stepThreeForm'], buttonId: '#submit' },
            };

            const { formIds, buttonId } = validateInfos[beforeChangeCurrentStep];

            if (!formIds.every(formId => $(formId).valid())) {
                $(buttonId).addClass('component__button--disabled');
                return false;
            }

            //유효성 검증 통과 후처리
            let result = true;

            switch (beforeChangeCurrentStep) {
                case 1:
                    try {
                        //attendee 정보 조회
                        $('#attendeeName').text($('#firstName').val() + ' ' +$('#lastName').val());
                        $('#attendeeEmail').text($('#email').val());
                        $('#attendeeMobileNumber').text($('#countryCallingNumber').val() + ') ' + $('#mobileNumber').val());

                        //payment details 세팅
                        const selectedTicket = $('input[name="ticketCard"]:checked').data('id');
                        const ticket = availableTickets[selectedTicket];
                        $('#ticketInfo').text(ticket.ticketInfo);
                        $('#ticketQuantity').text('1');
                        $('#currencyPrice').text(`${ticket.currency} ${ticket.price}`);

                        //total amount 세팅
                        $('#totalAmount').text(`${ticket.currency} ${ticket.price}`);

                    } catch (e) {
                        result = false;
                    }
                    break;

                case 3:
                    try {
                        const stepOneFormData = getFormJson(`#stepOneForm`);
                        delete stepOneFormData.ticketCard;
                        const stepTwoFormData = getFormJson(`#stepTwoForm`);
                        const stepThreeFormData = getFormJson(`#stepThreeForm`);

                        const ticketId = $('input[name="ticketCard"]:checked').data('id');

                        if (!ticketId || !availableTickets[ticketId]) {
                            alert('Please select a valid ticket.');
                            return false;
                        }

                        const ticket = availableTickets[ticketId];

                        const data = {
                            ticketId: ticket.id,
                            currency: ticket.currency,
                            ticketQuantity: 1,

                            attendee: {...stepOneFormData, ...stepThreeFormData},
                            demographic: stepTwoFormData,
                        };

                        //4. post 처리 - sync 처리 (attendee insert)
                        $.ajax({
                            type: 'POST',
                            url: `/api/v1/woc/register-to-attendee`,
                            data: JSON.stringify(data),
                            enctype: 'multipart/form-data',
                            contentType: 'application/json',
                            processData: false,
                            dataType: 'JSON',
                            success: (res, textStatus, jqXHR) => {
                                if (res.success) {
                                    openPortOne(res.data);

                                } else {
                                    alert('Failed to submit your inquiry. Please try again. If the issue persists, please contact our support team.');
                                }
                            },
                            beforeSend: (xhr) => {
                                xhr.setRequestHeader(
                                    document.querySelector('meta[name="_csrf_header"]').content,
                                    document.querySelector('meta[name="_csrf"]').content
                                );

                                loadingStart();
                            },
                            complete: () => {
                                loadingExit();
                            },
                            error: (jqXHR, textStatus, errorThrown) => {
                                alert('Oops!\nInvalid form submission detected.\nPlease reload the page and try again.');
                                $('#submit').addClass('component__button--disabled');
                            },
                            async: false,
                        });

                    } catch (e) {
                        result = false;
                    }
                    break;
            }

            return result;
        };

        //이전 버튼
        $('.js-evt__prev').off('click').on('click', () => {
            const beforeChangeCurrentStep = currentStep;
            const afterChangeCurrentStep = currentStep - 1;

            if (1 < beforeChangeCurrentStep) {
                updateStepTabs(beforeChangeCurrentStep, afterChangeCurrentStep, false);
                updateStepVisibility(afterChangeCurrentStep);
                currentStep = afterChangeCurrentStep;
            }
        });

        //다음 버튼
        $('.js-evt__next').off('click').on('click', () => {
            const beforeChangeCurrentStep = currentStep;
            const afterChangeCurrentStep = currentStep + 1;

            const isStepHandled = handleStepValidation(beforeChangeCurrentStep);

            //form 전환
            if (isStepHandled && beforeChangeCurrentStep < 3) {
                updateStepTabs(beforeChangeCurrentStep, afterChangeCurrentStep, true);
                updateStepVisibility(afterChangeCurrentStep);
                currentStep = afterChangeCurrentStep;
            }
        });

        /* observer 활용 예시
        const targetNode = document.getElementById('verificationCode');
        if (targetNode) {
            const observer = new MutationObserver(function(mutationsList) {
                mutationsList.forEach(function(mutation) {
                    if (mutation.attributeName === 'class') {
                        if ($(mutation.target).hasClass('is-invalid')) {
                            $('#countdown').removeClass('me-3').addClass('me-5');
                        } else {
                            $('#countdown').removeClass('me-5').addClass('me-3');
                        }
                    }
                });
            });
            observer.observe(targetNode, { attributes: true });
        }
        */
    }

    const setEmailVerificationFlow = () => {
        //내부 전역 변수
        let countDownInterval;

        const disabledVerificationEmail = () => {
            $('#email').prop('readonly', true);
            $('#verificationEmail').addClass('disabled d-none');
        }
        const enabledVerificationEmail = () => {
            $('#email').prop('readonly', false);
            $('#verificationEmail').removeClass('disabled d-none');
        }
        const disabledVerificationConfirm = () => {
            $('#verificationCode').val('').addClass('component__button--disabled');
            $('#verificationConfirm').addClass('component__button--disabled');
        };

        const enabledVerificationConfirm = () => {
            $('#verificationCode').removeClass('component__button--disabled');
            $('#verificationConfirm').removeClass('component__button--disabled');
        };

        //인증번호 요청 이벤트
        $('#verificationEmail').off('click').on('click', () => {
            disabledVerificationEmail();

            $.ajax({
                type: "POST",
                url: `/api/v1/woc/email-verification-code`,
                data: JSON.stringify({email: $('#email').val()}),
                contentType: "application/json",
                dataType: "JSON",
                success: (res, textStatus, jqXHR) => {
                    if (res.success) {
                        enabledVerificationConfirm();

                        //인증확인 영역에 카운팅 시작 - 만료시 원복
                        countDownInterval = setCountDown('countdown', 300, () => {
                            enabledVerificationEmail();
                            disabledVerificationConfirm();
                        });
                    }
                    //오류시 원복 처리
                    else {
                        alert('Failed to send the email. Please try again.\nIf the problem continues, contact our support team.');

                        enabledVerificationEmail();
                        disabledVerificationConfirm();
                    }
                },
                beforeSend: (xhr) => {
                    xhr.setRequestHeader(
                        document.querySelector('meta[name="_csrf_header"]').content,
                        document.querySelector('meta[name="_csrf"]').content
                    );

                    loadingStart();
                },
                complete: () => {
                    loadingExit()
                },
                error: (jqXHR, textStatus, errorThrown) => {
                    alert('Failed to send the email. Please try again.\nIf the problem continues, contact our support team.');
                },
            });
        });

        //인증번호 인증 이벤트
        $(`#verificationConfirm`).off('click').on('click', () => {
            $.ajax({
                type: "POST",
                url: `/api/v1/woc/verify-email`,
                data: JSON.stringify({email: $('#email').val(), code: $('#verificationCode').val()}),
                contentType: "application/json",
                dataType: "JSON",
                success: (res, textStatus, jqXHR) => {
                    if (res.success) {
                        //TODO 이미 등록한 사람이면 튕구기 혹은 상세 (확인 필요)
                        res.data.alreadyRegistered;
                        res.data.demographic;

                        clearInterval(countDownInterval);
                        //alert('verification completed.');

                        //인증 영역 제거 및 인증 영역 form 표출
                        $('#verificationConFirmContent').addClass('d-none');
                        $('#userDetailsContent').removeClass('d-none');

                    }
                },
                beforeSend: (xhr) => {
                    xhr.setRequestHeader(
                        document.querySelector('meta[name="_csrf_header"]').content,
                        document.querySelector('meta[name="_csrf"]').content
                    );

                    loadingStart();
                },
                complete: () => {
                    loadingExit()
                },
                error: (jqXHR, textStatus, errorThrown) => {
                    //유효성 초기화 (미사용시, rules에 포함된 모든 유효성 검증 실행되는 side effect 발생)
                    stepOneFormValidator.resetForm();

                    //인증 오류 문구 수동 세팅
                    stepOneFormValidator.showErrors({
                        'verificationCode': jqXHR.responseJSON?.message || 'An unexpected error occurred.'
                    });
                },
            });
        });
    };

    return {setAvailableTickets, setPayment, setMultiForm, setMultiFormStepState, setEmailVerificationFlow};
})();


//모듈 초기화
(() => {
    paymentModule.setAvailableTickets();
    paymentModule.setPayment();
    paymentModule.setMultiForm();
    paymentModule.setMultiFormStepState();
    paymentModule.setEmailVerificationFlow();
})();