//이벤트 바인딩 및 초기화
window.addEventListener('load', () => {
    const formId = '#boothInquiryForm';
    const snsIds = ['instagram', 'facebook'];

    //콤보박스 옵션 세팅
    renderCodeFormHTML('exhibitor-register', [
        {id: 'industryCode', type: 'codeList', parentCode: 'INDUSTRY-TYPE', formElement: 'radio'},
        {id: 'countryId', type: 'countryList'},
        {id: 'officeCallingNumber', type: 'callingCodeList'},
        {id: 'mobileCallingNumber', type: 'callingCodeList'},
    ]);

    //select2 세팅
    $('#countryId').select2({
        placeholder: 'Select',
        width: '100%',
    });
    $('#officeCallingNumber').select2({
        placeholder: 'Select',
        width: '100%',
    });
    $('#mobileCallingNumber').select2({
        placeholder: 'Select',
        width: '100%',
    });

    //jquery 유효성 세팅
    $("select").on("select2:close", function () {
        $(this).valid();
    });

    //input number 정규식 처리
    inputNumberRegex('#officeNumber, #mobileNumber');

    //sns UI 및 class 세팅
    setSnsUI(snsIds, {'init': true});

    //width 값에 따른 sns 처리
    window.addEventListener('resize', debounce(() => {
        setSnsUI(snsIds, {'init': false});
    }, 300));

    //sns placeholder 클릭 이벤트
    $(formId).off('click').on('click', '.js-evt__mobile-sns',(e) => {
        const target = e.currentTarget;
        if (!target.value) {
            target.value = target.placeholder;
        }
    });
    //sns placeholder focusout 이벤트
    $(formId).off('focusout').on('focusout', '.js-evt__mobile-sns', (e) => {
        const target = e.currentTarget;
        if (target.value.length <= target.placeholder.length || target.placeholder.includes(target.value)) {
            target.value = '';
        }
    });

    //유효성 정규식 처리
    $(formId).off('input').on('input', '.js-evt__web-sns', (e) => {
        const target = e.currentTarget;
        //아래와 같은 형태 replace 처리 예시) 'https://www.~/'    'http://~/'   'www~/'   '~/'
        target.value = target.value.replace(/^(https?:\/\/)?(www\.)?[^\/]+\//i, '');
    });

    //textArea count 세팅
    setTextAreaCount([{id:'companyAddress', maxlength: 500}]);

    const validator = setValid(formId, {
        //유효성 적용 요소 name
        rules: {
            companyName: {required: true, maxlength: 100},
            brandName: {required: true, maxlength: 300},
            countryId: {required: true},
            managerName: {required: true, maxlength: 100},
            jobTitle: {required: true, maxlength: 255},
            email: {required: true, maxlength: 255},
            secondEmail: {maxlength: 255},
            officeCallingNumber: {required: true},
            officeNumber: {required: true, number:true, maxlength: 100},
            mobileCallingNumber: {},
            mobileNumber: {maxlength: 100, number:true},
            companyAddress: {maxlength: 500},
            homepage: {maxlength: 500},
            instagram: {maxlength: 450},
            facebook: {maxlength: 450},
            etcSns: {url: true, maxlength: 500},
            industryCode: {required: true},
            exhibitionId: {required: true},
            confirmSubmission: {required: true},
        },

        //유효성 실패 메시지
        messages: {
            companyName: {required: 'is a required field.', maxlength: 'Must be 100 characters or fewer.'},
            brandName: {required: 'is a required field.', maxlength: 'Must be 300 characters or fewer.'},
            countryId: {required: 'Please select an option.'},
            managerName: {required: 'is a required field.', maxlength: 'Must be 100 characters or fewer.'},
            jobTitle: {required: 'is a required field.', maxlength: 'Must be 255 characters or fewer.'},
            email: {required: 'is a required field.', maxlength: 'Must be 255 characters or fewer.'},
            secondEmail: {maxlength: 'Must be 255 characters or fewer.'},
            officeCallingNumber: {required: 'Please select an option.'},
            officeNumber: {required: 'is a required field.', maxlength: 'Must be 100 characters or fewer.'},
            mobileCallingNumber: {},
            mobileNumber: {maxlength: 'Must be 100 characters or fewer.'},
            companyAddress: {maxlength: 'Must be 500 characters or fewer.'},
            homepage: {maxlength: 'Must be 500 characters or fewer.'},
            instagram: {maxlength: 'Must be 450 characters or fewer.'},
            facebook: {maxlength: 'Must be 450 characters or fewer.'},
            etcSns: {maxlength: 'Must be 500 characters or fewer.'},
            industryCode: {required: 'is a required field.'},
            confirmSubmission: {required: 'is a required field.'},
        },
    });

    //form 제출
    $(formId + ' #submit').click(() => {
        const form = $(formId);
        //유효성 통과 시, 제출
        if (form.valid()) {
            const data = getFormJson(formId);
            //수동 추가 데이터
            data['exhibitionId'] = defaultParams.exhibitionId;

            //sns url의 값이 있는 경우 regex 처리 후 prefix 세팅
            snsIds.forEach(snsId => {
                if (data[snsId]) {
                    const regexUrl = data[snsId].replace(/^(https?:\/\/)?(www\.)?[^\/]+\//i, '');
                    data[snsId] = `https://www.instagram.com/${regexUrl}`;
                }
            });

            $.ajax({
                type: "POST",
                url: `/api/v1/woc/exhibitor/inquiry`,
                data: JSON.stringify(data),
                enctype: 'multipart/form-data',
                contentType: "application/json",
                processData: false,
                dataType: "JSON",
                success: (res, textStatus, jqXHR) => {
                    if (res.success) {
                        alert('Your inquiry has been successfully submitted. We will get back to you as soon as possible. Thank you')
                        window.location.reload()
                    } else {
                        alert('Failed to submit your inquiry. Please try again. If the issue persists, please contact our support team.')
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
                    const errors = jqXHR.responseJSON?.errors;

                    //유효성 초기화 (에러 메시지, 클래스 제거)
                    validator.resetForm();

                    for (const field in errors) {
                        if (Object.hasOwn(errors, field)) {
                            const fieldName = Object.keys(errors[field])[0];
                            const message = errors[field][fieldName];

                            // 에러 표시
                            validator.showErrors({
                                [fieldName]: message
                            });

                            $(`[name=${fieldName}]`).on("input change", function () {
                                validator.element(this);
                            });
                        }
                    }
                },
                async: true
            });
        }
    });
});

/**
 * SNS 필드 UI 옵션 설정
 * @param {string []} snsIds snsId 목록
 * @param {object} options
 * @param {boolean} options.init 최초 초기화 목적 여부
 */
function setSnsUI(snsIds, options) {
    snsIds.forEach((snsId) => {
        //web
        if (window.matchMedia("(min-width: 768px)").matches) {
            $(`#${snsId}`).attr('type', 'text').attr('placeholder', '');
            //$(`#${snsId}`).removeClass('js-evt__mobile-sns').addClass('js-evt__web-sns');
            $(`#${snsId}`).addClass('js-evt__web-sns');
            $(`#${snsId}Addon`).removeClass('d-none');
        }
        //mobile
        else {
            $(`#${snsId}`).attr('type', 'url').attr('placeholder', `ex) https://www.${snsId}.com/worldofcoffee`);
            //$(`#${snsId}`).addClass('js-evt__mobile-sns').removeClass('js-evt__web-sns');
            $(`#${snsId}`).removeClass('js-evt__web-sns');
            $(`#${snsId}Addon`).addClass('d-none');
        }

        if(!options.init) {
            $(`#${snsId}`).valid();
        }
    });
}