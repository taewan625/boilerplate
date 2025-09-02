window.addEventListener('load', () => {
    //콤보박스 옵션 세팅
    renderCodeFormHTML('contactus-register', [
        { id: 'contactorCode', type: 'codeList', parentCode: 'CONTACTOR-TYPE', formElement: 'select' },
        { id: 'messageAboutCode', type: 'codeList', parentCode: 'MESSAGE-ABOUT-TYPE', formElement: 'select' },
    ]);
    //select2 세팅
    $('#contactorCode').select2({
        placeholder: 'Select',
        width: '100%',
    });
    $('#messageAboutCode').select2({
        placeholder: 'Select',
        width: '100%',
    });

    //jquery 유효성 세팅
    $("select").on("select2:close", function () {
        $(this).valid();
    });

    //textArea count 세팅
    setTextAreaCount([{id:'content', maxlength: 5000}]);

    const formId = '#contactUsForm';
    const validator = setValid(formId, {
        //유효성 적용 요소 name
        rules: {
            firstName: { required: true, maxlength: 255 },
            lastName: { required: true, maxlength: 255 },
            companyName: { required: true },
            email: { required: true, email: true, maxlength: 50 },
            contactorCode: { required: true, maxlength: 50 },
            messageAboutCode: { required: true, maxlength: 50 },
            title: {},
            content: { required: true, maxlength: 5000 }
        },

        //유효성 실패 메시지
        messages: {
            firstName: { required: "is a required field.", maxlength: 'Must be 255 characters or fewer.' },
            lastName: { required: "is a required field.", maxlength: 'Must be 255 characters or fewer.' },
            companyName: { required: "is a required field."},
            email: { required: "is a required field.", maxlength: 'Must be 50 characters or fewer.' },
            contactorCode: { required: "Please select an option.", maxlength: 'Must be 50 characters or fewer.'},
            messageAboutCode: { required: "Please select an option.", maxlength: 'Must be 50 characters or fewer.'},
            content: { required: "is a required field.", maxlength: 'Must be 5000 characters or fewer.' }
        },
    });

    //form 제출
    $(formId + ' #submit').click(() => {
        const form = $(formId);
        //유효성 통과 시, 제출
        if (form.valid()) {
            const data = getFormJson(formId);

            $.ajax({
                type: "POST",
                url: `/api/v1/woc/contact-us`,
                data: JSON.stringify(data),
                enctype:'multipart/form-data',
                contentType: "application/json",
                processData: false,
                dataType: "JSON",
                success: (res, textStatus, jqXHR) => {
                    if(res.success) {
                        alert('Your inquiry has been successfully submitted. We will get back to you as soon as possible. Thank you')
                        window.location.reload()
                    }
                    else{
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
                complete:() =>{loadingExit()},
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