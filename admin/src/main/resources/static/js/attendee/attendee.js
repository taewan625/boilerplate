window.addEventListener('load', () => {
    getSelectOption();
    dynamicSelect();
  //  setDateRangePicker();
    setDatatable();


    $("#btn-search").click(() => {
        datatable.ajax.reload();
    });

    $("#btn-remind").click(() => {
        if(confirm(`${$('#exhibition option:selected').text()} 참관객에게 바코드 리마인드 메일을 발송하시겠습니까?`)){
            sendRemind();
        }else{
            toastr.info('',"발송을 취소하였습니다.",{timeOut:1500})
        }
    });

    $("#btn-excel").click(() => {
        getExcelDownload(`/api/v1/admin/attendee/excel/${$("#exhibition option:selected").val()}`);
    });


    loadingExit();
});
let datatable;
let ticketType = [];
let badgeType = [];

const dynamicSelect = () => {
    const category = $('#ticket-type');
    const badge = $('#badge-type');


    ticketType.forEach(item => {
        category.append(new Option(item.text, item.value));
    });

    badgeType.forEach(item => {
        const type = ticketType.filter(i => i.value === item.type)[0];
        badge.append(new Option(`${item.text} (${type.text})`, item.value));
    });

    category.on("change", function () {
        const selectedCategory = $(this).val();

        // 두 번째 select 초기화
        badge.empty();
        badge.append(new Option("All", ""));

        if(selectedCategory === ""){
            badgeType.forEach(item => {
                const type = ticketType.filter(i => i.value === item.type)[0];
                badge.append(new Option(`${item.text} (${type.text})`, item.value));
            });
        }else{
            // 선택한 카테고리에 맞는 배지 옵션 추가
            badgeType
                .filter(item => item.type === selectedCategory)
                .forEach(item => {
                    badge.append(new Option(item.text, item.value));
                });
        }


        // 첫 번째 선택이 없을 경우 두 번째 select 비활성화
        //badge.prop("disabled", selectedCategory === "");
    });

    // 페이지 로드 시 두 번째 select 비활성화
    //badge.prop("disabled", true);
}

const getSelectOption = () =>{
    $.ajax({
        type: "GET",
        url: `/api/v1/admin/select-code/TICKET-TYPE`,
        success: (res, textStatus, jqXHR) =>{
            if(res.success) {
                ticketType = res.data;
            }
        },
        beforeSend: () =>{loadingStart()} ,
        complete:() =>{loadingExit()},
        error: (jqXHR, textStatus, errorThrown) => {},
        async: false
    });

    $.ajax({
        type: "GET",
        url: `/api/v1/admin/select-ticket/${$('#exhibition option:selected').val()}`,
        success: (res, textStatus, jqXHR) =>{
            if(res.success) {
                badgeType = res.data;
            }
        },
        beforeSend: () =>{loadingStart()} ,
        complete:() =>{loadingExit()},
        error: (jqXHR, textStatus, errorThrown) => {},
        async: false
    });
}



const sendRemind =() => {
    $.ajax({
        type: "GET",
        url: `/api/v1/admin/attendee/remind/${$('#exhibition option:selected').val()}`,
        success: (res, textStatus, jqXHR) =>{
            if(res.success) {
                toastr.success('',"정상적으로 발송되었습니다.",{timeOut:1500})
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


const setDateRangePicker = () =>{
    $("#date-range").daterangepicker({
        locale: {
            format: "YYYY-MM-DD",
            separator: " - ",
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
        startDate: moment().subtract(7,'days').format('YYYY-MM-DD'),
        endDate: moment().format('YYYY-MM-DD')
    })
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
            url : `/api/v1/admin/attendee`,
            method : "POST",
            data : (d)=> {
                //     let datepicker = $("#date-range").data("daterangepicker")
                //    d.startDate = datepicker.startDate.format("YYYY-MM-DD");
                //    d.endDate = datepicker.endDate.format("YYYY-MM-DD");

                d.exhibitionId = $("#exhibition option:selected").val();
                d.status= $("input[name=status]:checked").val();
                d.type= $("#ticket-type option:selected").val();
                d.badge= $('#badge-type option:selected').val();
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
            { name : "NO", title : "No", data : "no", className : "dt-head-center dt-body-center px-2", orderable: false},
            { name : "ORDER-NUMBER", title : "ORDER NUMBER", data : 'orderNo', className : "dt-head-center dt-body-left px-2", orderable: false,
                render:(data, type, row) =>{
                    return `<span>${data}</span><br /><span class="text-muted">Barcode: ${row.barcode}</span>`
                }
            },
            { name : "BADGE-TYPE", title : "Type", data : "badgeType", className : "dt-head-center dt-body-left px-2", orderable: false,
                render:(data, type, row) =>{
                    return `<span>${data}</span><br /><span class="text-muted">${row.ticketType}</span>`
                }
            },
            { name : "NAME", title : "NAME", data : "name", className : "dt-head-center dt-body-left px-2", orderable: false,
                render:(data, type, row) =>{
                    return `<span>${data}</span><br /><span class="text-muted">${row.country}</span>`
                }
            },
            { name : "EMAIL", title : "Email", data : "email", className : "dt-head-center dt-body-left px-2", orderable: false,
                render:(data, type, row) =>{
                    return `<span>${data}</span><br /><span class="text-muted">${row.mobileNumber}</span>`
                }
            },
            { name : "STATUS", title : "Status", data : "status", className : "dt-head-center dt-body-center px-2", orderable: false,
                render:(data, type, row) =>{
                    if(data == 0){
                        return `<span class="badge bg-label-success">Registered</span>`
                    }
                    if(data == 1){
                        return `<span class="badge bg-label-danger ">Cancelled</span>`
                    }

                }
            },
            { name : "ORDER-DATE", title : "Order Date", data : "orderDate", className : "dt-head-center dt-body-center px-2", orderable: false}
        ],
        "fnRowCallback": function(nRow, aData, iDisplayIndex) {
            $(nRow).click(function() {
                window.location.href = `/attendee/${aData.id}`
                //    goDetailUserInfo(aData.userId);
            });
        },
        columnDefs:[
            {targets:[0], width:"5%", padding:"0px"}
            ,{targets:[1], width:"20%", padding:"0px"}
            ,{targets:[2], width:"14%", padding:"0px"}
            ,{targets:[3], width:"25%", padding:"0px"}
            ,{targets:[4], width:"15%", padding:"0px"}
            ,{targets:[5], width:"8%", padding:"0px"}
            ,{targets:[6], width:"13%", padding:"0px"}
        ],
    });
}