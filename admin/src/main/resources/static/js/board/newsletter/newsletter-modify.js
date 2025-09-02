window.addEventListener('load', () => {
    setValid();
    const newsletterId = getNewsletterId();
    getNewsletter(newsletterId);

    $('#btn-download').click(()=>{
        getFileDownload('temp', $('#logoUuid').val());
    });


    $("#logoFile").change((e)=>{
        fileChange(e);
    });

    $("#btn-save").click(() => {
        if($("#modify-form").valid()){
            if(confirm("입력한 정보를 저장하시겠습니까?")){
                updateNewsletter(newsletterId);
            }
        }
    });


    $("#btn-cancel").click(() => {
        if(confirm("정말 취소하시겠습니까? 변경한 내용은 저장되지 않습니다.")){
            window.location.href = `/board/newsletter/${newsletterId}`;
        }
    });


    loadingExit();
});

const setValid = () => {
    $('#modify-form').validate({
        rules: {                    // 유효성 검사 규칙
            title: {             // 이름 필드
                required: true,
                maxlength: 500
            }
        },
        title: {
            title: {
                required: "is a required field.",
                maxlength: 'Must be 500 characters or fewer.'
            }
        },
        submitHandler: (form) => {

            // updateExhibitor();
        }
    });
}

const getNewsletter = (newsletterId) => {
    $.ajax({
        type: "GET",
        url: `/api/v1/admin/newsletters/${newsletterId}`,
        success: (res, textStatus, jqXHR) =>{
            if(res.success){

                Object.entries(Object.keys(res.data)).forEach(([key, value])=>{
                    switch (value){
                        case 'logoAttached':
                            if(res.data[value]){
                                $(`#${value}`).html(`<button class="btn btn-outline-primary border-0" type="button" id="btn-download"><i class='bx bx-download' ></i> ${res.data['logoFileName']}</button>`);
                                $('#logoPreview').attr('src', res.data['logoFilePath']);
                            }else{}
                            break;
                        case 'fileSize':break;
                        case 'filename':break;
                        case 'disabled':
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

const updateNewsletter = (id) =>{
    const formData = geFormData();

    $.ajax({
        type: "PUT",
        url: `/api/v1/admin/newsletters/${id}`,
        data: formData,
        enctype:'multipart/form-data',
        contentType: false,
        processData: false,
        dataType: "JSON",
        success: (res, textStatus, jqXHR) =>{
            if(res.success) {
                toastr.success('',"저장 되었습니다.",{timeOut:1500})
                window.location.href = `/board/newsletter/${id}`;
            }else{
                if(res.message === "Invalid MIME type detected"){
                    toastr.error('',"변조된 파일은 업로드할 수 없습니다.",{timeOut:1500})
                }else{
                    toastr.error('',"실패 하였습니다.",{timeOut:1500})
                }
            }
        },
        beforeSend: () =>{loadingStart()} ,
        complete:() =>{loadingExit()},
        error: (jqXHR, textStatus, errorThrown) => {},
        async: false
    });

}


const fileChange = (e) => {
    const file = e.target.files[0];
    const src = '/static/img/backgrounds/noimage.png';
    if(!file){
        $("#logoPreview").attr('src', src);
        return;
    }

    // 파일 타입 체크 (png)
    if (!file.type.startsWith('image/png') &&
        !file.type.startsWith('image/jpeg')) {
        toastr.error('JPG, PNG 파일만 선택해주세요.');
        e.target.value = ''; // 선택된 파일 초기화
        return;
    }

    const maxSize = 10 * 1024 * 1024;
    if (file.size > maxSize) {
        toastr.error('파일 크기는 10MB 이하만 가능합니다.');
        e.target.value = ''; // 선택된 파일 초기화
        $("#logoPreview").attr('src', src);
        return;
    }

    const reader = new FileReader();
    reader.onload = function (e) {
        $("#logoPreview").attr('src', e.target.result);
    };
    reader.readAsDataURL(file);
}


const geFormData = () =>{
    const formData = new FormData();

    formData.append('title', $('#title').val());
    formData.append('content', $('#content').val());
    formData.append('issueDate', $('#issueDate').val());

    formData.append('logoFileId', $('#logoFileId').val());
    if($('#logoFile')[0].files[0]){
        formData.append('logoFile', $('#logoFile')[0].files[0]);
    }

    return formData;
}



const getNewsletterId = () => {
    const path = window.location.pathname;
    const match = path.match(/\/board\/newsletter\/(\d+)/);

    return match ? match[1] : null;
}
