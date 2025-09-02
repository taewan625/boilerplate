window.addEventListener('load', () => {
    const exhibitorId = getExhibitorId();
    getExhibitor(exhibitorId);


    $("#btn-pre").click(() => {
        window.location.href = '/exhibition/brand';
    });

    $("#btn-update").click(() => {
        window.location.href = `/exhibition/brand/${exhibitorId}/modify`;
    });


    $('#btn-pend').click(()=>{
        if(confirm("보류 하시겠습니까?")){
            updateApprove(exhibitorId, 0)
        }
    });
    $('#btn-approve').click(()=>{
        if(confirm("승인 하시겠습니까?")){
            updateApprove(exhibitorId, 1)
        }
    });
    $('#btn-reject').click(()=>{
        if(confirm("반려 하시겠습니까?")){
            updateApprove(exhibitorId, -1)
        }
    });



    loadingExit();
});


const updateApprove =(id, status) =>{
    const data = {
        approve: status
    }

    $.ajax({
        type: "PUT",
        url: `/api/v1/admin/brand/${id}/approve`,
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: "JSON",
        success: (res, textStatus, jqXHR) =>{
            if(res.success) {
                toastr.success('',"정상적으로 처리되었습니다.",{timeOut:1500})
                getExhibitor(id);
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



const getExhibitor = (exhibitorId) => {
    $.ajax({
        type: "GET",
        url: `/api/v1/admin/brand/${exhibitorId}`,
        success: (res, textStatus, jqXHR) =>{
            if(res.success){
                console.log(res.data);
                Object.keys(res.data).forEach((key) => {
                    if (typeof res.data[key] === 'string') {
                        res.data[key] = unescapeHtml(res.data[key]);
                    }

                    switch (key) {
                        case 'booth':
                            $(`#${key}`).val(res.data[key] || '-');
                            break;

                        case 'approve':
                            setApprove(key, res.data[key]);
                            break;

                        case 'filePath':
                            $("#logo").attr('src', res.data[key]).on('error', function () {
                                $(this).attr('src', '/static/img/backgrounds/noimage.png');
                            });
                            break;

                        case 'homepage':
                        case 'instagram':
                        case 'facebook':
                        case 'etcSns':
                            const content = `${res.data[key]} ${getUrl(res.data[key])}`;
                            if(content.startsWith('null') || !content.trim()){
                                $(`#${key}`).html('-');
                            }else{
                                $(`#${key}`).html(content);
                            }
                            break;

                        default:
                            $(`#${key}`).val(res.data[key]);
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

const setApprove = (key, state) => {

    if (state == 1) {
        $('#btn-pend').removeClass('d-none');
        $('#btn-approve').addClass('d-none');
        $('#btn-reject').removeClass('d-none');
        $(`#company-header`).html(`<span class="badge bg-label-success ">Approve</span>`);
    }
    if (state == 0) {
        $('#btn-pend').addClass('d-none');
        $('#btn-approve').removeClass('d-none');
        $('#btn-reject').removeClass('d-none');
        $(`#company-header`).html(`<span class="badge bg-label-secondary">Pending</span>`);
    }
    if (state == -1) {
        $('#btn-pend').removeClass('d-none');
        $('#btn-approve').removeClass('d-none');
        $('#btn-reject').addClass('d-none');
        $(`#company-header`).html(`<span class="badge bg-label-danger ">Rejected</span>`);
    }
}

const getUrl = (url) => {
    if (!url) {
        return '';
    }

    if (!url.startsWith('http://') && !url.startsWith('https://')) {
        return  `<a class="btn-outline-primary border-0" href="https://${url}" target="_blank" rel="noopener noreferrer"><i class='bx bx-link-external'></i> </a>`;
    }


    return `<a class="btn-outline-primary border-0" href="${url}" target="_blank" rel="noopener noreferrer"><i class='bx bx-link-external'></i> </a>`;
}


const getExhibitorId = () => {
    const path = window.location.pathname;
    const match = path.match(/\/exhibition\/brand\/(\d+)/);
    const id = match ? match[1] : null;
    return id;
}