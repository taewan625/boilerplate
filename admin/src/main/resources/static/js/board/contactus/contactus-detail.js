window.addEventListener('load', () => {
    const contactusId = getContactusId();
    getContactus(contactusId);


    $('#btn-replied').click(()=>{
        if(confirm("해당 문의 건에 대해 응답완료 처리하시겠습니까?")){
            updatedReplied(contactusId);
        }
    });

    loadingExit();
});

const updatedReplied = (id) =>{
    $.ajax({
        type: "PUT",
        url: `/api/v1/admin/contactus/${id}`,
        contentType: "application/json",
        dataType: "JSON",
        success: (res, textStatus, jqXHR) =>{
            if(res.success) {
                toastr.success('',"정상적으로 처리되었습니다.",{timeOut:1500})
                window.location.href = '/board/contactus';
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


const getContactus = (id) => {
    $.ajax({
        type: "GET",
        url: `/api/v1/admin/contactus/${id}`,
        success: (res, textStatus, jqXHR) =>{
            if(res.success){
                Object.entries(Object.keys(res.data)).forEach(([key, value])=>{
                    switch (value){
                        case 'answer':
                            if(res.data[value] == 0){
                                $(`#${value}`).val('PENDING');
                            }

                            if(res.data[value] == 1){
                                $(`#${value}`).val('REPLIED');
                            }
                            break;
                            case 'title':
                                if(res.data[value]){
                                    $(`#${value}`).val(res.data[value]);
                                }else{
                                    $(`#${value}`).val('-');
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


const getContactusId = () => {
    const path = window.location.pathname;
    const match = path.match(/\/board\/contactus\/(\d+)/);
    const id = match ? match[1] : null;
    return id;
}