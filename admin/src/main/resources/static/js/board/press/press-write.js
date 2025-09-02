window.addEventListener('load', () => {

    setQuill();

    setValid();

    $("#btn-save").click(() => {

        if($("#modify-form").valid()){
            if(confirm("작성 내용을 저장하시겠습니까?")){
                //updateExhibitor(exhibitorId);
                insertBoard();
            }
        }
    });


    $("#btn-cancel").click(() => {
        if(confirm("정말 취소하시겠습니까? 변경한 내용은 저장되지 않습니다.")){
            window.location.href = `/board/press`;
        }
    });
    $("#file").change((e)=>{
        fileChange(e);
    });

    loadingExit();
});

let quill;

// const fileChange = (e) => {
//     const file = e.target.files[0];
//     if(!file){
//         $("#logo").attr('src', logoFileSrc);
//         return;
//     }
//
//     // 파일 타입 체크 (png)
//     if (!file.type.startsWith('image/png')) {
//         toastr.error('PNG 이미지 파일만 선택해주세요.');
//         e.target.value = ''; // 선택된 파일 초기화
//
//         $("#logo").attr('src', logoFileSrc);
//
//         return;
//     }
//
//     const reader = new FileReader();
//     reader.onload = function (e) {
//         $("#logo").attr('src', e.target.result);
//     };
//     reader.readAsDataURL(file);
// }

const fileChange = (e) => {
    const file = e.target.files[0];
    if(!file){
        return;
    }


    const maxSize = 10 * 1024 * 1024;

    // 파일 타입 체크 (png)
    if (!file.type.startsWith('image/png') &&
        !file.type.startsWith('image/jpeg') &&
        !file.type.startsWith('application/pdf') &&
        !file.type.startsWith('application/x-zip-compressed')) {
        toastr.error('JPG, PNG, PDF, ZIP 파일만 선택해주세요.');
        e.target.value = ''; // 선택된 파일 초기화
        return;
    }

    if (file.size > maxSize) {
        toastr.error('파일 크기는 10MB 이하만 가능합니다.');
        e.target.value = ''; // 선택된 파일 초기화
        return;
    }
}


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



const setQuill = () => {
    const toolbarOptions =[[{ 'header': [1, 2, 3, 4, 5, 6, false] }],[{ 'color': [] }, { 'background': [] }],[{ 'align': [] }],['bold', 'italic', 'underline', 'strike'], ['link', 'image']];

    quill =  new Quill(`#content`, {
        theme: 'snow',
        modules : {
            toolbar: toolbarOptions
        }
    });

    quill.root.style.fontSize = '16px';
    quill.root.style.height = '300px';
}


const insertBoard = () =>{
    const formData = geFormData();
    $.ajax({
        type: "POST",
        url: `/api/v1/admin/board/insert`,
        data: formData,
        enctype:'multipart/form-data',
        contentType: false,
        processData: false,
        dataType: "JSON",
        success: (res, textStatus, jqXHR) =>{
            if(res.success) {
                toastr.success('',"저장 되었습니다.",{timeOut:1500})
                window.location.href = `/board/press`;
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


const geFormData = () =>{
    const $form = $('#modify-form');
    const formData = new FormData();

    formData.append('title', $('#title').val());
    formData.append('content', quill.root.innerHTML);
    formData.append('bbsCode', $('#boardCode').val());
    formData.append('exhibitionId', $('#exhibition option:selected').val());


    if($('#file')[0].files[0]){
        formData.append('file', $('#file')[0].files[0]);
    }

    return formData;
}

