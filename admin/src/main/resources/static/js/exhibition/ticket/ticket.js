window.addEventListener('load', () => {
    setDatatable();

    $('#btn-registry').click(()=>{
       window.location.href = `/exhibition/ticket/register`;
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
            url : `/api/v1/admin/ticket`,
            method : "POST",
            data : (d)=> {
                d.exhibitionId = $("#exhibition option:selected").val();
            },
            beforeSend: () =>{loadingStart()} ,
            complete:(d) =>{loadingExit()},
            error: ()=>{}
        },
        processing: false,
        serverSide: true,
        order: [],
        columns: [
            { name : "NO", title : "No", data : "no", className : "dt-head-center dt-body-center px-2", orderable: false},
            { name : "TYPE", title : "TYPE", data : 'typeName', className : "dt-head-center dt-body-center px-2", orderable: false},
            { name : "TICKET-NAME", title : "NAME", data : 'ticketInfo', className : "dt-head-center dt-body-left px-2", orderable: false},
            { name : "PRICE", title : "PRICE", data : 'price', className : "dt-head-center dt-body-center px-2", orderable: false,
                render:(data, type, row) =>{
                    return `${row.currency} ${data}`;
                }
            },
            { name : "SALES-START", title : "SALES START", data : 'startDate', className : "dt-head-center dt-body-center px-2", orderable: false,
                render:(data, type, row) =>{
                    return `${row.localStartDate} <br /><span class="text-muted">${data} (UTC+9)</span>`
                }
            },
            { name : "SALES-END", title : "SALES END", data : 'endDate', className : "dt-head-center dt-body-center px-2", orderable: false,
                render:(data, type, row) =>{
                    return `${row.localEndDate} <br /><span class="text-muted">${data} (UTC+9)</span>`
                }
            },
            { name : "STATUS", title : "STATUS", data : "buying", className : "dt-head-center dt-body-center px-2", orderable: false,
                render:(data, type, row) =>{
                    if(data == 0){
                        return `<span class="badge bg-label-danger ">UNPUBLISHED</span>`
                    }
                    if(data == 1){
                        return `<span class="badge bg-label-success">PUBLISHED</span>`
                    }

                }
            },
            { name : "DATE", title : "DATE", data : "createdAt", className : "dt-head-center dt-body-center px-2", orderable: false},

        ],
        "fnRowCallback": function(nRow, aData, iDisplayIndex) {
            $(nRow).on('click', ()=> {
                // 모든 행의 활성화 제거
                $(datatable.rows().nodes()).removeClass('row-selected');
                // 현재 클릭한 행에 활성화 클래스 추가
                $(nRow).addClass('row-selected');

                window.location.href = `/exhibition/ticket/${aData.id}`;
            });
        },
        columnDefs:[
            {targets:[0], width:"5%", padding:"0px"}
            ,{targets:[1], width:"10%", padding:"0px"}
            ,{targets:[2], width:"20%", padding:"0px"}
            ,{targets:[3], width:"10%", padding:"0px"}
            ,{targets:[4], width:"15%", padding:"0px"}
            ,{targets:[5], width:"15%", padding:"0px"}
            ,{targets:[6], width:"10%", padding:"0px"}
            ,{targets:[7], width:"15%", padding:"0px"}
        ],
    });
}