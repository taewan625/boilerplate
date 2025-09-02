window.addEventListener('load', () => {
    const newsletterId = getNewsletterId();
    getNewsletter(newsletterId);

    $("#btn-pre").click(() => {
        window.location.href = '/board/newsletter';
    });

    $('#btn-download').click(()=>{
        getFileDownload('logoAttached', $('#logoUuid').val());
    });

    $('#btn-update').click(()=>{
       window.location.href = `/board/newsletter/${newsletterId}/modify`;
    });

    $('#btn-disable').click(()=>{
        if(confirm("비활성화 하시겠습니까?")){
            updateNewsletterEnabled(newsletterId, 0)
        }
    });
    $('#btn-enable').click(()=>{
        if(confirm("활성화 하시겠습니까?")){
            updateNewsletterEnabled(newsletterId, 1)
        }
    });


    $('#btn-delete').click(()=>{
        if(confirm("정말 삭제 하시겠습니까?")){
            deletedNewsletter(newsletterId);
        }
    });

    loadingExit();
});

const deletedNewsletter =(id) =>{
    $.ajax({
        type: "DELETE",
        url: `/api/v1/admin/newsletters/${id}`,
        dataType: "JSON",
        success: (res, textStatus, jqXHR) =>{
            if(res.success) {
                toastr.success('',"정상적으로 처리되었습니다.",{timeOut:1500})
                window.location.href = `/board/newsletter`;
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


const updateNewsletterEnabled =(id, status) =>{
    const data = {
        enable: status
    }

    $.ajax({
        type: "PUT",
        url: `/api/v1/admin/newsletters/${id}/enabled`,
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: "JSON",
        success: (res, textStatus, jqXHR) =>{
            if(res.success) {
                toastr.success('',"정상적으로 처리되었습니다.",{timeOut:1500})
                getNewsletter(id);
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




const getNewsletter = (newsletterId) => {
    $.ajax({
        type: "GET",
        url: `/api/v1/admin/newsletters/${newsletterId}`,
        success: (res, textStatus, jqXHR) =>{
            if(res.success){

                Object.entries(Object.keys(res.data)).forEach(([key, value])=>{
                    switch (value){
                        case 'title':
                            $(`#${value}`).html(res.data[value]);

                            break;
                        case 'content':
                            $(`#${value}`).html(res.data[value]);

                            break;
                        case 'logoAttached':
                            if(res.data[value]){
                                $(`#${value}`).html(`<button class="btn btn-outline-primary border-0" id="btn-download">${res.data['logoFileName']} <i class='bx bx-download' ></i></button>`);
                                $('#logoPreview').attr('src', res.data['logoFilePath']);
                            }else{
                                $('#div-attached').addClass('d-none');
                                $('#title').addClass('mb-3')
                            }

                            break;
                        case 'fileSize':break;
                        case 'filename':break;
                        case 'enable':
                            if(res.data[value]){
                                $('#btn-disable').removeClass('d-none')
                                $('#btn-enable').addClass('d-none')
                            }else{
                                $('#btn-disable').addClass('d-none')
                                $('#btn-enable').removeClass('d-none')
                            }

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

const getNewsletterId = () => {
    const path = window.location.pathname;
    const match = path.match(/\/board\/newsletter\/(\d+)/);

    return match ? match[1] : null;
}
