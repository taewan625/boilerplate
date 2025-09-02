//이벤트 바인딩 및 초기화
window.addEventListener('load', () => {
    const formId = '#newsletterForm';

    const validator = setValid(formId, {
        //유효성 적용 요소 name
        rules: {
            firstName: {required: true, maxlength: 255},
            lastName: {required: true, maxlength: 255},
            email: {required: true, email: true, maxlength: 50},
            accepted: {required: true},
        },

        //유효성 실패 메시지
        messages: {
            firstName: {required: 'is a required field.', maxlength: 'Must be 255 characters or fewer.',},
            lastName: {required: 'is a required field.', maxlength: 'Must be 255 characters or fewer.',},
            email: {required: 'is a required field.', maxlength: 'Must be 50 characters or fewer.',},
            accepted: {required: 'You must accept the terms and conditions.'},
        },
    });

    //form 제출
    $(formId + ' #submit').click(() => {
        const form = $(formId);
        //유효성 통과 시, 제출
        if (form.valid()) {
            const data = getFormJson(formId);

            $.ajax({
                type: 'POST',
                url: `/api/v1/woc/subscribe`,
                data: JSON.stringify(data),
                enctype: 'multipart/form-data',
                contentType: 'application/json',
                processData: false,
                dataType: 'JSON',
                success: (res, textStatus, jqXHR) => {
                    if (res.success) {
                        alert('Your inquiry has been successfully submitted. We will get back to you as soon as possible. Thank you');
                        window.location.reload();
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
                async: true,
            });
        }
    });

    //최근 newsletter 세팅
    $.ajax({
        type: 'GET',
        url: `/api/v1/woc/newsletter?pageSize=3`,
        processData: false,
        dataType: "JSON",

        success: (res, textStatus, jqXHR) => {
            if (res.success) {
                const data = res.data;
                const newsletters = data.newsletters;

                //데이터가 없는 경우 early return
                if (newsletters.length < 1) {
                    return false;
                }

                $('#latestNewsletter').removeClass('d-none');

                let html = '';

                newsletters.forEach((item, index) => {
                    html += `
                            <div class="col js-evt__newsletter">
                              <a href="/newsletter/${item.id}" class="card h-100 text-decoration-none">
                                <div class="d-flex p-4 component__card-img--middle">
                                  <img
                                      src="${item.logoFilePath || ''}"
                                      class="img-fluid object-fit-contain" alt="..." draggable="false" loading="lazy" decoding="async"
                                      onerror="this.onerror=null; this.src='https://kr.object.ncloudstorage.com/bucket-exporum-prod/woc/resource/bangkok/logo/bangkok-horizontal.webp';">
                                </div>
                                <div class="fw-semibold text-truncate border-top px-3 py-md-2" data-bs-toggle="tooltip" data-bs-placement="top" title="${item.title}">
                                  <span class="component__desc-font--middle">
                                     ${item.title}
                                  </span>
                                </div>
                                
                                <div class="text-start text-truncate px-3 py-md-2">
                                  <span class="component__desc-font--small">
                                    ${transformDateFormat(item.issueDate)}
                                  </span>
                                </div>
                              </a>
                            </div>`;
                });

                $('#newsletters').append(html);

                //툴팁 재초기화(있는 경우 재사용)
                document.querySelectorAll('[data-bs-toggle="tooltip"]').forEach(el => {
                    bootstrap.Tooltip.getOrCreateInstance(el);
                });

            }
        },

        beforeSend: () => {
            loadingStart();
        },
        complete: () => {
            loadingExit();
        },
        error: (jqXHR, textStatus, errorThrown) => {
            // 에러 처리
        },
    });
});

const transformDateFormat = (date) => date.split(' ')[0].replace(/-/g, '.');