window.addEventListener('load', () => {
    setDateRangePicker();
    setDatatable();


    $('#btn-search').click(()=>{
        datatable.ajax.reload();
    })

    $("#btn-excel").click(() => {
        getExcelDownload(`/api/v1/admin/payment/excel/${$('#exhibition option:selected').val()}`);
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
            url : `/api/v1/admin/payment`,
            method : "POST",
            data : (d)=> {
                let datepicker = $("#date-range").data("daterangepicker")
                d.startDate = datepicker.startDate.format("YYYY-MM-DD");
                d.endDate = datepicker.endDate.format("YYYY-MM-DD");
                d.status= $("input[name=status]:checked").val();
                d.exhibitionId = $("#exhibition option:selected").val();
            },
            beforeSend: () =>{loadingStart()} ,
            complete:() =>{loadingExit()},
            error: ()=>{}
        },
        processing: false,
        serverSide: true,
        order: [],
        columns: [
            { name : "NO", title : "NO", data : "no", className : "dt-head-center dt-body-center px-2", orderable: false},
            { name : "NAME", title : "NAME", data : 'name', className : "dt-head-center dt-body-left px-2", orderable: false},
            { name : "EMAIL", title : "EMAIL", data : "email", className : "dt-head-center dt-body-left px-2", orderable: false},
            { name : "STATUS", title : "STATUS", data : "status", className : "dt-head-center dt-body-center px-2", orderable: false,
                render:(data, type, row) =>{
                    if(data === 'failed'){
                        return `<span class="badge bg-label-secondary">FAILED</span>`
                    }
                    if(data === 'paid'){
                        return `<span class="badge bg-label-success">COMPLETED</span>`
                    }
                    if(data === 'cancelled') {
                        return `<span class="badge bg-label-danger">CANCELLED</span>`
                    }
                }
            },
            { name : "ORDER-NAME", title : "TICKET", data : "orderName", className : "dt-head-center dt-body-left px-2", orderable: false},
            { name : "METHOD", title : "METHOD", data : "method", className : "dt-head-center dt-body-center px-2", orderable: false},
            { name : "AMOUNT", title : "AMOUNT", data : "amount", className : "dt-head-center dt-body-right px-2", orderable: false},
            { name : "CURRENCY", title : "CURRENCY", data : "currency", className : "dt-head-center dt-body-center px-2", orderable: false},
            { name : "APPROVAL DATE", title : "APPROVAL DATE", data : "statusApprovalDate", className : "dt-head-center dt-body-center px-2", orderable: false}

        ],
        "fnRowCallback": function(nRow, aData, iDisplayIndex) {

        },
        columnDefs:[
            {targets:[0], width:"5%", padding:"0px"}  // 25 40 45 60 75 95
            ,{targets:[1], width:"15%", padding:"0px"}
            ,{targets:[2], width:"20%", padding:"0px"}
            ,{targets:[3], width:"5%", padding:"0px"}
            ,{targets:[4], width:"25%", padding:"0px"}
            ,{targets:[5], width:"5%", padding:"0px"}
            ,{targets:[6], width:"5%", padding:"0px"}
            ,{targets:[7], width:"5%", padding:"0px"}
            ,{targets:[8], width:"15%", padding:"0px"}
        ],
    });
}

const setDateRangePicker = () =>{
    $("#date-range").daterangepicker({
        locale: {
            format: "YYYY-MM-DD",
            separator: " ~ ",
            applyLabel: "확인",
            cancelLabel: "취소",
            fromLabel: "From",
            toLabel: "To",
            customRangeLabel: "Custom",
            weekLabel: "W",
            daysOfWeek: ["일", "월", "화", "수", "목", "금", "토"],
            monthNames: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
        },
        timePicker: false,
        startDate: moment().subtract(30,'days').format('YYYY-MM-DD'),
        endDate: moment().format('YYYY-MM-DD')
    })
}
