
window.addEventListener('load', () => {
    const exhibitorId = getExhibitorId();

    loadCode(exhibitorId);

    setValid();

    $("#btn-pre").click(() => {
        window.location.href = '/exhibition/brand';
    });

    $("#btn-cancel").click(() => {
        if(confirm("정말 취소하시겠습니까? 변경한 내용은 저장되지 않습니다.")){
            window.location.href = `/exhibition/brand/${exhibitorId}`;
        }

    });


    $("#btn-save").click(() => {


        if($("#modify-form").valid()){
            if(confirm("입력한 정보를 저장하시겠습니까?")){
                updateExhibitor(exhibitorId);
            }
        }
    });

    $("#file").change((e)=>{
        fileChange(e);
    });



    loadingExit();

});
let logoFileSrc;


const loadCode = (id) => {
    $.ajax({
        type: "GET",
        url: `/api/v1/woc/code-set?setcode=exhibitor-register`,
        success: (res, textStatus, jqXHR) =>{
            if(res.success){
                Object.entries(Object.keys(res.data.data)).forEach(([key, value])=>{
                    loadSelect(value, res.data.data[value]);
                });
            }
        },
        beforeSend: () =>{loadingStart()} ,
        complete:() =>{getExhibitor(id)},
        error: (jqXHR, textStatus, errorThrown) => {},
        async: false
    });
}

const loadSelect = (key, options) => {

    switch (key){
        case  'callingCodeList':
            $('#officeCalling').append(`<option value="" selected>--Select--</option>`);
            $('#mobileCalling').append(`<option value="" selected>--Select--</option>`);
            $.each(options, function(index, item) {
                $('#officeCalling').append(`<option value="${item.callingCode}">${item.callingCode + ' ' + item.countryNameEn}</option>`);
                $('#mobileCalling').append(`<option value="${item.callingCode}">${item.callingCode + ' ' + item.countryNameEn}</option>`);
            });
            break;
        case  'codeList':
            $.each(options[0].children, function(index, item) {
                $('#industry').append(`<option value="${item.code}">${item.codeName}</option>`);
            });
            break;

        case  'countryList':
            $.each(options, function(index, item) {
                $("#country").append(`<option value="${item.id}">${item.countryNameEn}</option>`);
            });
            break;

    }

}


const setValid = () => {

    $.validator.addMethod("urlCheck", function (value) {
        if(value){
            return /^(https?:\/\/)([\w.-]+)\.([a-z]{2,6})(:\d{1,5})?(\/[\w.@/%-]*)*(\?[\w@=&%-]*)?(#[\w@-]*)?$/.test(value);
        }else{
            return true;
        }

    }, "The URL format is invalid.");

    $('#modify-form').validate({
        rules: {                    // 유효성 검사 규칙
            brand: {             // 이름 필드
                required: true,
                maxlength: 500
            },
            company: {
                required: true,
                maxlength: 100
            },
            industry: {
                required: true
            },
            address: {
                maxlength: 500
            },
            managerName: {
                required: true,
                maxlength: 100
            },
            jobTitle:{
                maxlength: 200
            },
            email: {
                required: true,
                email: true,
                maxlength: 200
            },
            officeNumber: {
                maxlength: 200
            },
            mobileNumber: {
                maxlength: 200
            },
            homepage: {
                urlCheck: true,
                maxlength: 255
            },
            instagram: {
                urlCheck: true,
                maxlength: 500
            },
            facebook: {
                urlCheck: true,
                maxlength: 500
            },
            etcSns: {
                urlCheck: true,
                maxlength: 500
            }
        },
        messages: {
            brand: {
                required: "is a required field.",
                maxlength: 'Must be 500 characters or fewer.'
            },
            company: {
                required: "is a required field.",
                maxlength: 'Must be 100 characters or fewer.'
            },
            industry: {
                required: "is a required field.",
            },
            address: {
                required: "is a required field.",
            },
            managerName: {
                required: "is a required field.",
                maxlength: 'Must be 100 characters or fewer.'
            },
            jobTitle:{
                maxlength: 'Must be 200 characters or fewer.'
            },
            email: {
                required: "is a required field.",
                email: 'Invalid email format.',
                maxlength: 'Must be 200 characters or fewer.'
            },
            officeNumber: {
                maxlength: 'Must be 200 characters or fewer.'
            },
            mobileNumber: {
                maxlength: 'Must be 200 characters or fewer.'
            },
            homepage: {
                maxlength: 'Must be 255 characters or fewer.'
            },
            facebook: {
                maxlength: 'Must be 500 characters or fewer.'
            },
            instagram: {
                maxlength: 'Must be 500 characters or fewer.'
            },
            etcSns: {
                maxlength: 'Must be 500 characters or fewer.'
            }
        },
        errorPlacement: function (error, element) {
            if (element.parent().hasClass('input-group')) {
                // input-group인 경우 input-group 뒤에 메시지 붙이기
                error.insertAfter(element.parent());
            } else {
                error.insertAfter(element);
            }
        }
    });
}


const updateExhibitor = (id) =>{
    const formData = geFormData();


    $.ajax({
        type: "PUT",
        url: `/api/v1/admin/brand/${id}`,
        data: formData,
        enctype:'multipart/form-data',
        contentType: false,
        processData: false,
        dataType: "JSON",
        success: (res, textStatus, jqXHR) =>{
            if(res.success) {

                toastr.success('',"저장 되었습니다.",{timeOut:1500})
                window.location.href = `/exhibition/brand/${id}`;
            }else{
                toastr.error('',"실패 하였습니다.",{timeOut:1500})
            }
        },
        beforeSend: () =>{loadingStart()} ,
        complete:() =>{loadingExit()},
        error: (jqXHR, textStatus, errorThrown) => {},
        async: false
    });

}

const geFormData = () =>{
    const $form = $('#modify-form');
    const formData = new FormData();


    formData.append('brandName', $('#brand').val());

    formData.append('companyId', $('#companyId').val());
    formData.append('companyName', $('#company').val());
    formData.append('companyNameEn', $('#company').val());
    formData.append('companyEmail', $('#email').val());
    formData.append('industryCode', $('#industry option:selected').val());
    formData.append('countryId', $('#country option:selected').val());
    formData.append('booth', $('#booth').val());
    formData.append('address', $('#address').val());


    formData.append('managerName', $('#managerName').val());
    formData.append('jobTitle', $('#jobTitle').val());
    formData.append('managerEmail', $('#email').val());
    formData.append('officeCallingCode', $('#officeCalling option:selected').val());
    formData.append('officeNumber', $('#officeNumber').val());
    formData.append('managerCallingCode', $('#mobileCalling option:selected').val());
    formData.append('mobileNumber', $('#mobileNumber').val());

    formData.append('introduction', $('#introduction').val());
    formData.append('instagram', $('#instagram').val());
    formData.append('facebook', $('#facebook').val());
    formData.append('etcSns', $('#etcSns').val());

    formData.append('homepage', $('#homepage').val());

    formData.append('fileId', $('#fileId').val());
    if($('#file')[0].files[0]){
        formData.append('file', $('#file')[0].files[0]);
    }
    return formData;
}




const fileChange = (e) => {
    const file = e.target.files[0];
    if(!file){
        $("#logo").attr('src', logoFileSrc);
        return;
    }

    // 파일 타입 체크 (png)
    if (!file.type.startsWith('image/png')) {
        toastr.error('PNG 이미지 파일만 선택해주세요.');
        e.target.value = ''; // 선택된 파일 초기화

        $("#logo").attr('src', logoFileSrc);

        return;
    }

    const maxSize = 10 * 1024 * 1024;
    if (file.size > maxSize) {
        toastr.error('파일 크기는 10MB 이하만 가능합니다.');
        e.target.value = ''; // 선택된 파일 초기화
        $("#logo").attr('src', logoFileSrc);
        return;
    }


    const reader = new FileReader();
    reader.onload = function (e) {
        $("#logo").attr('src', e.target.result);
    };
    reader.readAsDataURL(file);
}


const getExhibitor = (exhibitorId) => {
    $.ajax({
        type: "GET",
        url: `/api/v1/admin/brand/${exhibitorId}`,
        success: (res, textStatus, jqXHR) =>{
            if(res.success){
                Object.entries(Object.keys(res.data)).forEach(([key, value])=>{

                    if (typeof res.data[value] === 'string') {
                        res.data[value] = unescapeHtml(res.data[value]);
                    }

                    switch (value){
                        case 'industry':
                            break;
                        case 'industryCode':
                            $(`#industry`).val(res.data[value]);
                            break;
                        case 'country':
                            break;
                        case 'countryId':
                            $(`#country`).val(res.data[value]);
                            break;
                        case 'approve':
                            break;
                        case 'filePath':
                            logoFileSrc=res.data[value];
                            $("#logo").attr('src', res.data[value]).on('error', ()=>{
                                $(this).attr('src', '/static/img/backgrounds/noimage.png');
                            });
                            break;
                        case 'homepage':
                            $(`#${value}`).val(getUrl(res.data[value]));
                            break;
                        case 'sns':
                            $(`#${value}`).val(getUrl(res.data[value]));
                            break;
                        default : $(`#${value}`).val(res.data[value]);
                    }
                });

            }
        },
        beforeSend: () =>{loadingStart()} ,
        complete:() =>{loadingExit()},
        error: (jqXHR, textStatus, errorThrown) => {},
        async: false
    });
}




const getUrl = (url) => {
    if(!url){
        return '';
    }

    if (!url.startsWith('http://') && !url.startsWith('https://')) {
        return 'https://' + url;
    }


    return url
}


const getExhibitorId = () => {
    const path = window.location.pathname;
    const match = path.match(/\/exhibition\/brand\/(\d+)/);
    const id = match ? match[1] : null;
    return id;
}