window.addEventListener('load', () => {
    setDatatable();

    $('#btn-search').click(()=>{
        datatable.ajax.reload();
    });

    loadingExit();
});

let datatable;

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
            url : `/api/v1/admin/contactus`,
            method : "POST",
            data : (d)=> {
                d.status = $("input[name='status']:checked").val();
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
            { name : "TYPE", title : "TYPE", data : 'contactor', className : "dt-head-center dt-body-center px-2", orderable: false},
            { name : "INQUIRY", title : "INQUIRY", data : 'inquiry', className : "dt-head-center dt-body-left px-2", orderable: false},
            { name : "TITLE", title : "TITLE", data : 'title', className : "dt-head-center dt-body-left px-2", orderable: false,
                render:(data, type, row) =>{
                    if(data){
                        return data;
                    }else{
                        return '-'
                    }
                }
            },
            { name : "STATUS", title : "STATUS", data : "answer", className : "dt-head-center dt-body-center px-2", orderable: false,
                render:(data, type, row) =>{
                    if(data){
                        return `<span class="badge bg-label-success ">REPLIED</span>`;

                    }else{
                        return `<span class="badge bg-label-secondary">WAIT</span>`;
                    }
                }
            },
            { name : "DATE", title : "DATE", data : "createdAt", className : "dt-head-center dt-body-center px-2", orderable: false},
        ],
        "fnRowCallback": function(nRow, aData, iDisplayIndex) {
            $(nRow).click(function() {
                window.location.href = `/board/contactus/${aData.id}`;
            });
        },
        columnDefs:[
            {targets:[0], width:"5%", padding:"0px"}
            ,{targets:[1], width:"15%", padding:"0px"}
            ,{targets:[2], width:"20%", padding:"0px"}
            ,{targets:[3], width:"35%", padding:"0px"}
            ,{targets:[4], width:"10%", padding:"0px"}
            ,{targets:[5], width:"15%", padding:"0px"}
        ],
    });
}