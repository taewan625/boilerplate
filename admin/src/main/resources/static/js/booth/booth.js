window.addEventListener('load', () => {
    setDatatable();

    $('#btn-search').click(()=>{
        datatable.ajax.reload();
    })

    $("#btn-excel").click(() => {
        getExcelDownload(`/api/v1/admin/booth-request/excel/${$("#exhibition option:selected").val()}`);
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
            url : `/api/v1/admin/booth-request`,
            method : "POST",
            data : (d)=> {
                d.searchType= $("input[name=status]:checked").val();
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
            { name : "EXHIBITION", title : "EXHIBITION", data : 'exhibition', className : "dt-head-center dt-body-left", orderable: false,
                render:(data, type, row) =>{
                    if(data){
                        return `${data}`;
                    }
                    return `-`;
                },
            },
            { name : "COMPANY", title : "COMPANY", data : "company", className : "dt-head-center dt-body-left", orderable: false,
                render:(data, type, row) =>{
                    return `<span>${data}</span><br /> <span class="text-muted">${row.country}</span>`;
                },
            },
            { name : "INDUSTRY", title : "INDUSTRY", data : "industry", className : "dt-head-center dt-body-center", orderable: false},
            { name : "CHECK", title : "CHECK", data : "check", className : "dt-head-center dt-body-center", orderable: false,
                render:(data, type, row) =>{
                    if(data){
                        return `<span class="badge bg-label-success ">Checked</span>`

                    }
                    return `<span class="badge bg-label-secondary">Unchecked</span>`
                },
            },
            { name : "createdAt", title : "CREATED DATE", data : "createdAt", className : "dt-head-center dt-body-center", orderable: false,
                render:(data, type, row) =>{
                    if(data){
                        return moment(data).format('YYYY-MM-DD HH:mm');
                    }
                    return `-`;
                },
            },
            { name : "updatedAt", title : "Check DATE", data : "updatedAt", className : "dt-head-center dt-body-center", orderable: false,
                render:(data, type, row) =>{
                    if(data){
                        return moment(data).format('YYYY-MM-DD HH:mm');
                    }
                    return `-`;
                },
            }
        ],
        "fnRowCallback": function(nRow, aData, iDisplayIndex) {
            console.log(aData)
            $(nRow).click(()=>{
                window.location.href=`/booth-request/${aData.id}`;
            })
        },
        columnDefs:[
            {targets:[0], width:"5%", padding:"0px"}
            ,{targets:[1], width:"20%", padding:"0px"}
            ,{targets:[2], width:"20%", padding:"0px"}
            ,{targets:[3], width:"10%", padding:"0px"}
            ,{targets:[4], width:"15%", padding:"0px"}
            ,{targets:[5], width:"15%", padding:"0px"}
            ,{targets:[6], width:"15%", padding:"0px"}

        ],
    });
}