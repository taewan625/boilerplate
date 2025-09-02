window.addEventListener('load', () => {
    const ticketId = getTicketId();
    setPriceDatatable();
    getTicket(ticketId);
    setDateTimepicker();
    setValid();

    $('#btn-cancel').click(()=>{
        if(confirm("정말 취소하시겠습니까? 변경한 내용은 저장되지 않습니다.")){
            window.location.href = `/exhibition/ticket/${ticketId}`
        }
    });

    $('#btn-save').click(() =>{
        if($('#modify-form').valid()){
            if(confirm("입력한 정보를 저장하시겠습니까?")){
                updateTicket(ticketId);
            }
        }
    })

    $('#startDate').change(() => {
        compareTime('startDate', 'endDate', '', '종료 시간보다 이후시간을 설정할 수 없습니다.');
    });

    $('#endDate').change(() => {
        compareTime('endDate', 'startDate', '', '시작 시간보다 이전시간을 설정할 수 없습니다.');
    });

    loadingExit();
});

let datatable;

const compareTime = (startId, endId, initTime, message) =>{
    if($(`#${endId}`).val()){
        const startTime = moment($('#startDate').val(), 'YYYY-MM-DD HH:mm');
        const endTime = moment($('#endDate').val(), 'YYYY-MM-DD HH:mm');

        if(startTime.isAfter(endTime)){
            $(`#${startId}`).val(moment().format('YYYY-MM-DD HH:mm'));
            toastr.error(message)
        }
    }
}

const updateTicket = (id) =>{
    const prices = datatable.rows().data().toArray();
    const data = {
        exhibitionId: $('#exhibition option:selected').val(),
        startDate: $('#startDate').val(),
        endDate: $('#endDate').val(),
        typeCode: $('#typeCode option:selected').val(),
        ticketName: $('#ticketName').val(),
        ticketInfo: $('#ticketInfo').val(),
        prices: prices
    }

    $.ajax({
        type: "PUT",
        url: `/api/v1/admin/ticket/${id}`,
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: "JSON",
        success: (res) => {
            if (res.success) {
                toastr.success('정상적으로 처리되었습니다.');
                window.location.href = `/exhibition/ticket/${id}`;
            } else {
                toastr.error('실패 하였습니다.');
            }
        },
        beforeSend: () => { loadingStart() },
        complete: () => { loadingExit() },
        error: () => { toastr.error('서버 오류 발생'); }
    });
}

const setValid = () => {
    $('#modify-form').validate({
        rules: {                    // 유효성 검사 규칙
            startDate: {             // 이름 필드
                required: true
            },
            endDate: {
                required: true
            },
            typeCode: {
                required: true
            },
            ticketName: {
                required: true,
                maxlength: 50
            },
            ticketInfo: {
                required: true,
                maxlength: 5000
            },
            price:{
                required: true,
            }
        },
        messages: {
            startDate: {
                required: "is a required field.",
            },
            endDate: {
                required: "is a required field.",
            },
            typeCode: {
                required: "is a required field.",
            },
            ticketName: {
                required: "is a required field.",
                maxlength: 'Must be 50 characters or fewer.'
            },
            ticketInfo: {
                required: "is a required field.",
                maxlength: 'Must be 5000 characters or fewer.'
            },
            price:{
                required: "is a required field.",
            }
        },
        submitHandler: (form) => {}
    });
}

const getTicket = (id) => {
    $.ajax({
        type: "GET",
        url: `/api/v1/admin/ticket/${id}`,
        success: (res, textStatus, jqXHR) =>{
            if(res.success){
                 Object.entries(res.data).forEach(([key, value]) => {
                     switch (key) {
                         case 'buying':
                             $('#status').val(value == 0 ? 'UNPUBLISHED' : 'PUBLISHED');
                             break;

                         case 'prices':
                             if (Array.isArray(value) && value.length > 0) {
                                 datatable.clear().rows.add(value).draw(false);
                             }
                             break;
                         case 'startDate':
                             $(`#${key}`).val(`${res.data.localStartDate}`)
                             break;
                         case 'localStartDate':
                             break;
                         case 'endDate':
                             $(`#${key}`).val(`${res.data.localEndDate}`)
                             break;
                         case 'localEndDate':
                             break;

                        default: $(`#${key}`).val(value);
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

const setPriceDatatable = (id) => {
    datatable = $("#datatable").DataTable({
        searching: false,
        info: false,
        lengthChange: false,
        paging: false,
        responsive: true,
        processing: false,
        serverSide: false,
        order: [],
        columns: [
            {
                name: "CURRENCY", title: "CURRENCY", data: "currency", className: "dt-head-center dt-body-center pe-3", orderable: false,
                render: (data, type, row) => {
                    return `<input type="text" class="form-control-plaintext text-center" value="${data}" readOnly disabled />`;
                }
            },
            {
                name: "PRICE", title: "PRICE", data: "price", className: "dt-head-center dt-body-left ps-3", orderable: false,
                render: (data, type, row) => {
                    const price = numberWithCommas(data);
                    return `<input type="text" class="form-control usd-price" value="${price}" name="price" inputmode="numeric" />`;
                }
            },
        ],
        columnDefs: [
            { targets: [0], width: "20%" },
            { targets: [1], width: "80%" }
        ],
        fnRowCallback: (nRow, aData, iDisplayIndex)=> {
            $(nRow).find('.usd-price').on('input', function () {
                let inputValue = $(this).val().replace(/,/g, ''); // 콤마 제거
                inputValue = inputValue.replace(/[^0-9]/g, ''); // 숫자만 남기기
                $(this).val(numberWithCommas(inputValue)); // 천단위 콤마 적용
                aData.price = inputValue; // DataTables 데이터 업데이트
            });
        },
        createdRow: (row, data, dataIndex)=> {
            $('td:eq(0)', row).css("width", "20%");
            $('td:eq(1)', row).css("width", "80%");
        },
        headerCallback: (thead, data, start, end, display) => {
            $(thead).css('display','none')
            //$(thead).empty(); // 헤더 내용만 비우기 (컬럼 크기 조정 유지)
        }
    });
};

const setDateTimepicker = () => {
    $("#startDate").daterangepicker({
        locale: {
            format: "YYYY-MM-DD HH:mm",
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
        timePicker: true,
        singleDatePicker: true,
        timePicker24Hour: true
    });

    $("#endDate").daterangepicker({
        locale: {
            format: "YYYY-MM-DD HH:mm",
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
        timePicker: true,
        singleDatePicker: true,
        timePicker24Hour: true
    });
}

const getTicketId = () => {
    const path = window.location.pathname;
    const match = path.match(/\/exhibition\/ticket\/(\d+)/);
    const id = match ? match[1] : null;
    return id;
}
