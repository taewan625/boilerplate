window.addEventListener('load', () => {
    setDatatable();

    $('#btn-search').click(()=>{
        datatable.ajax.reload();
    });

    $("#btn-excel").click(() => {
        getExcelDownload(`/api/v1/admin/subscribe/excel`);
    });

    $('#datatable tbody').on('click', '.switch-subscribe', function () {
        const rowData = datatable.row($(this).parents('tr')).data();
        rowData.subscribe = !rowData.subscribe;
        updatedSubscribe(rowData);
    })



    loadingExit();
});
let datatable;

const updatedSubscribe = (row) => {
    const data = {
        subscribe: row.subscribe
    }

    $.ajax({
        type: "PUT",
        url: `/api/v1/admin/subscribe/${row.id}`,
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: "JSON",
        success: (res) => {
            if (res.success) {
                toastr.success('정상적으로 처리되었습니다.');
            } else {
                toastr.error('실패 하였습니다.');
            }
        },
        beforeSend: () => { loadingStart() },
        complete: () => { loadingExit() },
        error: () => { toastr.error('서버 오류 발생'); }
    });

}


const setDatatable = () => {
    datatable = $("#datatable").DataTable({
        searching: false,
        pageLength: 10,
        info: true,   //하단 페이지 수 비활성화
        infoCallback: (settings, start, end, max, total, pre) =>{
            return `Total ${max}`;
        },//  엔트리 갯수 custom
        lengthChange: false, // 상단 엔트리 개수 설정 비활성화
        language: {   //로딩 중 문자 수정
            processing :""
        },
        paging: true,
        bPaginate: true,
        responsive: true,
        ajax: {
            url : `/api/v1/admin/subscribe`,
            method : "POST",
            data : (d)=> {
                d.status= $("input[name=status]:checked").val();
                d.searchType = $('#search-type option:selected').val();
                d.searchText = $('#search-text').val();

            },
            beforeSend: () =>{loadingStart()} ,
            complete:() =>{loadingExit()},
            error: ()=>{}
        },
        processing: false,
        serverSide: true,
        order: [],
        columns: [
            { name : "NO", title : "NO", data : "no", className : "dt-head-center dt-body-center", orderable: false},
            { name : "EMAIL", title : "EMAIL", data : 'email', className : "dt-head-center dt-body-left", orderable: false},
            { name : "NAME", title : "NAME", data : "firstName", className : "dt-head-center dt-body-left", orderable: false,
                render:(data, type, row) =>{
                    return data + ' ' + row.lastName
                },
            },
            { name : "STATUS", title : "SUBSCRIBE", data : "subscribe", className : "dt-head-center dt-body-center", orderable: false,
                render:(data, type, row) =>{
                    if(data == 1){
                        return `
                                <div class="row d-flex justify-content-center">
                                    <div class="col-auto">
                                        <div class="form-check form-switch">
                                            <input class="form-check-input switch-subscribe w-px-50 h-px-20" type="checkbox" value="${data}" checked="">
                                        </div>        
                                    </div>
                                </div>
                                
                        `
                    }
                    if(data == 0){
                        return `
                                <div class="row d-flex justify-content-center">
                                    <div class="col-auto">
                                        <div class="form-check form-switch">
                                            <input class="form-check-input switch-subscribe  w-px-50 h-px-20" type="checkbox" value="${data}">
                                        </div>        
                                    </div>
                                </div> 
                              `
                    }

                }
            },
            { name : "SUBSCRIBE DATE", title : "SUBSCRIBE DATE", data : "subscribeDate", className : "dt-head-center dt-body-center", orderable: false}

        ],
        "fnRowCallback": function(nRow, aData, iDisplayIndex) {

        },
        columnDefs:[
            {targets:[0], width:"5%", padding:"0px"}
            ,{targets:[1], width:"30%", padding:"0px"}
            ,{targets:[2], width:"35%", padding:"0px"}
            ,{targets:[3], width:"10%", padding:"0px"}
            ,{targets:[4], width:"20%", padding:"0px"}

        ],
    });
}