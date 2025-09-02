window.addEventListener('load', () => {
    setDatatable();

    $("#btn-search").click(() => {
        datatable.ajax.reload();
    });
    $('#btn-registry').click(() =>{
        window.location.href = `/board/newsletter/register`;
    });

    loadingExit();
});

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
            url : `/api/v1/admin/newsletters`,
            method : "POST",
            data : (d)=> {
                d.status = $("input[name='disabled']:checked").val();
                d.type= $("#search-type option:selected").val();
                d.searchText= $("#search-text").val();
            },
            beforeSend: () =>{loadingStart()} ,
            complete:() =>{loadingExit()},
            error: ()=>{}
        },
        processing: false,
        serverSide: true,
        order: [],
        columns: [
            { name : "NO", title : "No", data : "no", className : "dt-head-center dt-body-center px-2", orderable: false},
            { name : "TITLE", title : "TITLE", data : 'title', className : "dt-head-center dt-body-left px-2", orderable: false},
            { name : "STATUS", title : "STATUS", data : "enable", className : "dt-head-center dt-body-center px-2", orderable: false,
                render:(data, type, row) =>{
                    if(data){
                        return `<span class="badge bg-label-success ">PUBLISHED</span>`;

                    }else{
                        return `<span class="badge bg-label-secondary">UNPUBLISHED</span>`;
                    }
                }
            },
            { name : "AUTHOR", title : "AUTHOR", data : "createdBy", className : "dt-head-center dt-body-center px-2", orderable: false},
            { name : "ISSUE DATE", title : "ISSUE DATE", data : "issueDate", className : "dt-head-center dt-body-center px-2", orderable: false},
            { name : "DATE", title : "DATE", data : "createdAt", className : "dt-head-center dt-body-center px-2", orderable: false},
        ],
        "fnRowCallback": function(nRow, aData, iDisplayIndex) {
            $("td:lt(8)", nRow).click(function() {
                window.location.href = `/board/newsletter/${aData.id}`
            });
        },
        columnDefs:[
            {targets:[0], width:"5%", padding:"0px"}
            ,{targets:[1], width:"35%", padding:"0px"}
            ,{targets:[2], width:"15%", padding:"0px"}
            ,{targets:[3], width:"10%", padding:"0px"}
            ,{targets:[4], width:"15%", padding:"0px"}
            ,{targets:[4], width:"15%", padding:"0px"}
        ],
    });
}