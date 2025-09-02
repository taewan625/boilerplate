

window.addEventListener('load', () => {

    //  setDateRangePicker();
    setDatatable();

    $("#btn-search").click(() => {
        datatable.ajax.reload();
    });

    $("#btn-excel").click(() => {

        getExcelDownload(`/api/v1/admin/brand/excel/${$("#exhibition option:selected").val()}`);
    });

    $('#btn-logos').click(() =>{
        $.ajax({
            type: "GET",
            url: `/api/v1/admin/brand/logo/download`,
            contentType: "application/json",
            dataType: "JSON",
            success: (res, textStatus, jqXHR) =>{
                if(res.success) {
                    toastr.success('',"정상적으로 처리되었습니다.",{timeOut:1500})
                }else{
                    toastr.error('',"실패 하였습니다.",{timeOut:1500})
                }
            },
            beforeSend: () =>{loadingStart()} ,
            complete:() =>{loadingExit()},
            error: (jqXHR, textStatus, errorThrown) => {},
            async: false
        });
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
            url : `/api/v1/admin/brand`,
            method : "POST",
            data : (d)=> {

                //     let datepicker = $("#date-range").data("daterangepicker")
                //    d.startDate = datepicker.startDate.format("YYYY-MM-DD");
                //    d.endDate = datepicker.endDate.format("YYYY-MM-DD");

                d.exhibitionId = $("#exhibition option:selected").val();
                d.approve= $("input[name=approve]:checked").val();
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
            { name : "BRAND", title : "BRAND", data : "brand", className : "dt-head-center dt-body-left px-2", orderable: false},
            { name : "COMPANY", title : "COMPANY", data : "company", className : "dt-head-center dt-body-left px-2", orderable: false},
            { name : "COUNTRY", title : "COUNTRY", data : "country", className : "dt-head-center dt-body-left px-2", orderable: false},
            { name : "INDUSTRY", title : "INDUSTRY", data : "industry", className : "dt-head-center dt-body-left px-2", orderable: false},
            { name : "BOOTH", title : "BOOTH", data : "booth", className : "dt-head-center dt-body-center px-2", orderable: false,
                render:(data, type, row) =>{
                    if(data){
                        return data;
                    }else{
                        return '-';
                    }
                }
            },
            { name : "STATUS", title : "STATUS", data : "approve", className : "dt-head-center dt-body-center px-2", orderable: false,
                render:(data, type, row) =>{
                    if(data == 0){
                        return `<span class="badge bg-label-secondary">Pending</span>`
                    }
                    if(data == -1){
                        return `<span class="badge bg-label-danger ">Rejected</span>`
                    }
                    if(data == 1){
                        return `<span class="badge bg-label-success ">Approve</span>`
                    }
                }
            },
            { name : "DATE", title : "DATE", data : "createdAt", className : "dt-head-center dt-body-center px-2", orderable: false},

        ],
        "fnRowCallback": function(nRow, aData, iDisplayIndex) {
            $("td:lt(8)", nRow).click(function() {
                window.location.href = `/exhibition/brand/${aData.id}`
                //    goDetailUserInfo(aData.userId);
            });
        },
        columnDefs:[
            {targets:[0], width:"5%", padding:"0px"}
            ,{targets:[1], width:"18%", padding:"0px"}
            ,{targets:[2], width:"18%", padding:"0px"}
            ,{targets:[3], width:"10%", padding:"0px"}
            ,{targets:[4], width:"20%", padding:"0px"}
            ,{targets:[5], width:"6%", padding:"0px"}
            ,{targets:[6], width:"9%", padding:"0px"}
            ,{targets:[7], width:"14%", padding:"0px"}
        ],
    });
}