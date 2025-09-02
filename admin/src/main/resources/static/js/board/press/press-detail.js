window.addEventListener('load', () => {
    const boardId = getBoardId();
    getBoard(boardId);

    $("#btn-pre").click(() => {
        window.location.href = '/board/press';
    });

    $('#btn-download').click(()=>{
        getFileDownload('attached', $('#uuid').val())
    });


    $('#btn-update').click(()=>{
       window.location.href = `/board/press/${boardId}/modify`;
    });

    $('#btn-disable').click(()=>{
        if(confirm("비활성화 하시겠습니까?")){
            updateBoardDisabled(boardId, 1)
        }
    });
    $('#btn-enable').click(()=>{
        if(confirm("활성화 하시겠습니까?")){
            updateBoardDisabled(boardId, 0)
        }
    });


    $('#btn-delete').click(()=>{
        if(confirm("정말 삭제 하시겠습니까?")){
            deletedBoard(boardId);
        }
    });

    loadingExit();
});

const deletedBoard =(id) =>{

    $.ajax({
        type: "DELETE",
        url: `/api/v1/admin/board/${id}`,
        dataType: "JSON",
        success: (res, textStatus, jqXHR) =>{
            if(res.success) {
                toastr.success('',"정상적으로 처리되었습니다.",{timeOut:1500})
                window.location.href = `/board/press`;
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


const updateBoardDisabled =(id, status) =>{
    const data = {
        disabled: status
    }

    $.ajax({
        type: "PUT",
        url: `/api/v1/admin/board/${id}/disable`,
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: "JSON",
        success: (res, textStatus, jqXHR) =>{
            if(res.success) {
                toastr.success('',"정상적으로 처리되었습니다.",{timeOut:1500})
                getBoard(id);
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




const getBoard = (boardId) => {
    $.ajax({
        type: "GET",
        url: `/api/v1/admin/board/${boardId}`,
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
                        case 'attached':
                            if(res.data[value]){
                                $(`#${value}`).html(`<button class="btn btn-outline-primary border-0" type="button" id="btn-download"><i class='bx bx-download' ></i> ${res.data['filename']}</button>`);
                            }else{
                                $('#div-attached').addClass('d-none');
                                $('#title').addClass('mb-3')
                            }
                            break;
                        case 'fileSize':break;
                        case 'filename':break;
                        case 'disabled':

                            if(res.data[value]){
                                $('#btn-disable').addClass('d-none')
                                $('#btn-enable').removeClass('d-none')
                            }else{
                                $('#btn-disable').removeClass('d-none')
                                $('#btn-enable').addClass('d-none')
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

const getBoardId = () => {
    const path = window.location.pathname;
    const match = path.match(/\/board\/press\/(\d+)/);
    const id = match ? match[1] : null;
    return id;
}