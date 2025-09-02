window.addEventListener('load', () => {
    setDatatable();

    $("#btn-search").click(() => {
        datatable.ajax.reload();
    });
    $('#btn-registry').click(() =>{

        if($("#board-type").val() === 'NOTICE'){
            window.location.href = `/board/notice/register`;
        }else{
            window.location.href = `/board/press/register`;
        }


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
            url : `/api/v1/admin/board`,
            method : "POST",
            data : (d)=> {
                d.status = $("input[name='disabled']:checked").val();
                d.boardType = $("#board-type").val();
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
            { name : "FILE", title : "FILE", data : "attached", className : "dt-head-center dt-body-center px-2", orderable: false,
                render:(data, type, row) =>{
                    if(data){
                        return `<i class='bx bx-link-alt'></i>`;
                    }else{
                        return "-";
                    }

                }
            },
            { name : "STATUS", title : "STATUS", data : "disabled", className : "dt-head-center dt-body-center px-2", orderable: false,
                render:(data, type, row) =>{
                    if(data){
                        return `<span class="badge bg-label-secondary">Disabled</span>`;
                    }else{
                        return `<span class="badge bg-label-success ">Enabled</span>`;
                    }
                }
            },
            { name : "AUTHOR", title : "AUTHOR", data : "createdBy", className : "dt-head-center dt-body-center px-2", orderable: false},
            { name : "DATE", title : "DATE", data : "createdAt", className : "dt-head-center dt-body-center px-2", orderable: false},
        ],
        "fnRowCallback": function(nRow, aData, iDisplayIndex) {
            $("td:lt(8)", nRow).click(function() {

                if($("#board-type").val() === 'NOTICE'){
                    window.location.href = `/board/notice/${aData.id}`
                }else{
                    window.location.href = `/board/press/${aData.id}`
                }

                //    goDetailUserInfo(aData.userId);
            });
        },
        columnDefs:[
            {targets:[0], width:"5%", padding:"0px"}
            ,{targets:[1], width:"45%", padding:"0px"}
            ,{targets:[2], width:"5%", padding:"0px"}
            ,{targets:[3], width:"15%", padding:"0px"}
            ,{targets:[4], width:"15%", padding:"0px"}
            ,{targets:[5], width:"15%", padding:"0px"}
        ],
    });
}