window.addEventListener('load', () => {
    const ticketId = getTicketId();
    getTicket(ticketId)

    $("#btn-pre").click(() => {
        window.location.href = '/exhibition/ticket';
    });

    $('#btn-update').click(()=>{
        window.location.href = `/exhibition/ticket/${ticketId}/modify`;
    })

    $('#btn-publish').click(()=>{
        if(confirm('현재 티켓을 활성화 하시겠습니까?')){
            updatePublish(ticketId, 1);
        }
    })

    $('#btn-unpublish').click(()=>{
        if(confirm('티켓을 비활성화하시겠습니까?')){
            updatePublish(ticketId, 0);
        }
    })

    $('#btn-delete').click(()=>{
        if(confirm("정말 삭제 하시겠습니까?")){
            deleteSchedule(ticketId);
        }
    });

    loadingExit();
});

const deleteSchedule = (id) => {
    $.ajax({
        type: "DELETE",
        url: `/api/v1/admin/ticket/${id}`,
        contentType: "application/json",
        dataType: "JSON",
        success: (res) => {
            if (res.success) {
                toastr.success('정상적으로 처리되었습니다.');
                window.location.href = `/exhibition/ticket`;
            } else {
                toastr.error('실패 하였습니다.');
            }
        },
        beforeSend: () => { loadingStart() },
        complete: () => { loadingExit() },
        error: () => { toastr.error('서버 오류 발생'); }
    });
}

const updatePublish = (id, used) =>{
    const data = {
        buying : used
    }

    $.ajax({
        type: "PUT",
        url: `/api/v1/admin/ticket/${id}/publish`,
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: "JSON",
        success: (res) => {
            if (res.success) {
                toastr.success('정상적으로 처리되었습니다.');
                getTicket(id);
            } else {
                toastr.error('실패 하였습니다.');
            }
        },
        beforeSend: () => { loadingStart() },
        complete: () => { loadingExit() },
        error: () => { toastr.error('서버 오류 발생'); }
    });

}

const getTicket = (id) => {
    $.ajax({
        type: "GET",
        url: `/api/v1/admin/ticket/${id}`,
        success: (res, textStatus, jqXHR) =>{
            if (res.success) {
                Object.entries(res.data).forEach(([key, value]) => {
                    switch (key) {
                        case 'buying':
                            if (value == 0) {
                                $('#status').val('UNPUBLISHED');
                                $('#btn-unpublish').addClass('d-none');
                                $('#btn-publish').removeClass('d-none');
                            } else {
                                $('#status').val('PUBLISHED');
                                $('#btn-unpublish').removeClass('d-none');
                                $('#btn-publish').addClass('d-none');
                            }
                            break;

                        case 'prices':
                            if (Array.isArray(value) && value.length > 0) {
                                const priceText = value
                                    .map(item => `${item.currency} ${numberWithCommas(item.price)}`)
                                    .join(", ");
                                $('#price-datatable').val(priceText);
                            } else {
                                $('#price-datatable').val("N/A");
                            }
                            break;

                        case 'startDate':
                            $(`#${key}`).html(` ${res.data.localStartDate} / <span class="text-muted">${value} (UTC+9)</span>`)
                            break;
                        case 'localStartDate':
                            break;
                        case 'endDate':
                            $(`#${key}`).html(` ${res.data.localEndDate} / <span class="text-muted">${value} (UTC+9)</span>`)
                            break;
                        case 'localEndDate':
                            break;
                        default: $(`#${key}`).val(value);
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

const getTicketId = () => {
    const path = window.location.pathname;
    const match = path.match(/\/exhibition\/ticket\/(\d+)/);
    const id = match ? match[1] : null;
    return id;
}
