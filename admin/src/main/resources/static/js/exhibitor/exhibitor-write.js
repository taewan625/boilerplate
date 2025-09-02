window.addEventListener('load', () => {
    setSelect();
    setDatepicker("applicationDate");
    setValid();
    linkOpen();

    $("#btn-cancel").click(() => {
        if(confirm("정말 취소하시겠습니까? 변경한 내용은 저장되지 않습니다.")){
            window.location.href = `/exhibitor`;
        }
    });

    $("#btn-save").click(() => {
        if($("#modify-form").valid()){
            if(confirm("입력한 정보를 저장하시겠습니까?")){
                createdExhibitor();
            }
        }
    });

    loadingExit();

});

const createdExhibitor = () =>{

    const data = {
        exhibitionId: $("#exhibition option:selected").val(),
        scaiSales: $("#scai").is(':checked'),
        scaMembership: $("#sca").is(':checked'),
        companyName: $("#company").val(),
        brandName: $("#brand").val(),
        applicationType:  $("#applicationType option:selected").val(),
        industry:  $("#industry option:selected").val(),
        countryId: $("#country option:selected").val(),
        sponsorCode: $("#sponsor option:selected").val(),
        earlyApplicationCode: $("#earlyApplication option:selected").val(),
        applicationDate: $("#applicationDate").val(),
        homepage: $("#homepage").val(),
        instagram: $("#instagram").val(),
        facebook: $("#facebook").val(),
        etcSns: $("#etcSns").val(),
        contactName: $("#contactName").val(),
        jobTitle: $("#jobTitle").val(),
        email: $("#email").val(),
        callingCode: $("#callingNumber option:selected").val(),
        phoneNumber: $("#phoneNumber").val(),
        address: $("#address").val(),
        description: $("#description").val()
    }

    console.log(data);

    $.ajax({
        type: "POST",
        url: `/api/v1/admin/exhibitor/register`,
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: "JSON",
        success: (res) => {
            if (res.success) {
                toastr.success('정상적으로 처리되었습니다.');
                window.location.href = `/exhibitor`;
            } else {
                toastr.error('실패 하였습니다.');
            }
        },
        beforeSend: () => { loadingStart() },
        complete: () => { loadingExit() },
        error: () => { toastr.error('서버 오류 발생'); }
    });

}




const setSelect = () => {
    $('#industry').select2({
        width: '100%',
        minimumResultsForSearch: Infinity
    });
    $('#country').select2({
        width: '100%'
    });
    $('#sponsor').select2({
        width: '100%',
        minimumResultsForSearch: Infinity
    });
    $('#applicationType').select2({
        width: '100%',
        minimumResultsForSearch: Infinity
    });
    $('#earlyApplication').select2({
        width: '100%',
        minimumResultsForSearch: Infinity
    });
    $('#callingNumber').select2({
        width: '100%'
    });

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
            company: {
                required: true,
                maxlength: 100
            },
            brand: {
                maxlength: 500
            },
            country:{
                required: true
            },
            address: {
                maxlength: 500
            },
            contactName: {
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
            phoneNumber: {
                maxlength: 200
            },
            homepage: {
                maxlength: 255
            },
            instagram: {
                maxlength: 500
            },
            facebook: {
                maxlength: 500
            },
            etcSns: {
                maxlength: 500
            }
        },
        messages: {
            company: {
                required: "is a required field.",
                maxlength: 'Must be 100 characters or fewer.'
            },
            brand: {
                maxlength: 'Must be 500 characters or fewer.'
            },
            address: {
                required: "is a required field.",
            },
            contactName: {
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
            phoneNumber: {
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





const linkOpen = () =>{
    const urlRegex = /^(https?:\/\/)([\w.-]+)\.([a-z]{2,6})(:\d{1,5})?(\/[\w.@/%-]*)*(\?[\w@=&%-]*)?(#[\w@-]*)?$/
    $("#btn-homepage-link").click(()=>{
        const url = $('#homepage').val()
        if(urlRegex.test(url)){
            window.open(url, "_blank");
        } else {
            toastr.error('',"https://,http:// 포함하여 정확한 URL을 입력해주세요. ",{timeOut:1500})

        }
    })
    $("#btn-instagram-link").click(()=>{
        const url = $('#instagram').val()
        if(urlRegex.test(url)){
            window.open(url, "_blank");
        } else {
            toastr.error('',"https://,http:// 포함하여 정확한 URL을 입력해주세요. ",{timeOut:1500})

        }
    })

    $("#btn-facebook-link").click(()=>{
        const url = $('#facebook').val()
        if(urlRegex.test(url)){
            window.open(url, "_blank");
        } else {
            toastr.error('',"https://,http:// 포함하여 정확한 URL을 입력해주세요. ",{timeOut:1500})

        }
    })
    $("#btn-etc-link").click(()=>{
        const url = $('#etcSns').val()
        if(urlRegex.test(url)){
            window.open(url, "_blank");
        } else {
            toastr.error('',"https://,http:// 포함하여 정확한 URL을 입력해주세요. ",{timeOut:1500})

        }
    })

}