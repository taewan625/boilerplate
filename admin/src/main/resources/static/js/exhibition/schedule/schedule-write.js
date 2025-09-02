window.addEventListener('load', () => {
    $('#exhibitionName').val($('#exhibition option:selected').text());

    setDateRangePicker();
    setDatatable();
    setValid();

    $('#datatable tbody').on('click', '.btn-delete', function () {
        const row = datatable.row($(this).parents('tr'));
        const rowData = row.data();
        row.remove().draw(false);
    })

    $("#datatable tbody").sortable({
        helper: fixHelper,
        items: 'tr:not(#input-row)',
        update: (event, ui) => {
            const newData = [];
            $('#datatable tbody tr:not(#input-row)').each(function () {
                const rowData = datatable.row(this).data();
                newData.push(rowData);
            });
            datatable.clear().rows.add(newData).draw(false);
        }
    }).disableSelection();

    $('#btn-cancel').click(()=>{
        if(confirm("정말 취소하시겠습니까? 변경한 내용은 저장되지 않습니다.")){
            window.location.href = `/exhibition/schedule`;
        }
    });



    $('#btn-save').click(() =>{
        if($('#modify-form').valid()){
            if(confirm("작성 내용을 저장하시겠습니까?")){
                insertSchedule()
            }
        }
    })


    loadingExit();
});
let datatable;

const insertSchedule = () => {
    const events = datatable.rows().data().toArray().map((row, index) => ({
        ...row, // 기존 row 데이터 유지
        order: index + 1,
        eventTitle: row.title,
        eventLocation: row.location
    }));
    const data = {
        eventDate : $('#eventDate').val(),
        organization: $('#organization').val(),
        eventName: $('#eventName').val(),
        eventDescription: $('#description').val(),
        eventCode: $('#eventCode').val(),
        exhibitionId: $('#exhibition option:selected').val(),
        events: events
    }

    $.ajax({
        type: "POST",
        url: `/api/v1/admin/schedule/register`,
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: "JSON",
        success: (res) => {
            if (res.success) {
                toastr.success('정상적으로 처리되었습니다.');
                window.location.href = `/exhibition/schedule`;
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
            eventDate: {             // 이름 필드
                required: true
            },
            organization: {
                maxlength: 500
            },
            eventName: {
                required: true,
                maxlength: 500
            },
            description: {
                maxlength: 500
            }
        },
        messages: {
            eventDate: {
                required: "is a required field.",
            },
            organization: {
                maxlength: 'Must be 500 characters or fewer.'
            },
            eventName: {
                required: "is a required field.",
                maxlength: 'Must be 500 characters or fewer.'
            },
            description: {
                maxlength: 'Must be 500 characters or fewer.'
            }
        },
        submitHandler: (form) => {}
    });
}



const setDatatable = () => {
    datatable = $("#datatable").DataTable({
        searching: false,
        info: false,   //하단 페이지 수 비활성화
        infoCallback: (settings, start, end, max, total, pre) =>{
            return `Total ${max}`;
        },//  엔트리 갯수 custom
        lengthChange: false, // 상단 엔트리 개수 설정 비활성화
        language: {   //로딩 중 문자 수정
            processing :""
        },
        paging: false,
        bPaginate: false,
        responsive: true,
        processing: false,
        serverSide: false,
        order: [],
        columns: [
            { name : "START-TIME", title : "START TIME", data : "startTime", className : "dt-head-center dt-body-center px-2", orderable: false},
            { name : "END-TIME", title : "END TIME", data : 'endTime', className : "dt-head-center dt-body-center px-2", orderable: false},
            { name : "TITLE", title : "TITLE", data : "title", className : "dt-head-center dt-body-left px-2", orderable: false},
            { name : "LOCATION", title : "LOCATION", data : "location", className : "dt-head-center dt-body-left px-2", orderable: false},
            { name : "ACTION", title : "", data : "id", className : "dt-head-center dt-body-left px-2", orderable: false,
                render: (data, type, row) =>{
                    return `<button class="btn btn-outline-danger border-0 btn-delete"><i class='bx bxs-minus-square'></i></button>`
                }
            },

        ],
        fnRowCallback: (nRow, aData, iDisplayIndex)=> {},
        columnDefs:[
            {targets:[0], width:"10%", padding:"0px"}
            ,{targets:[1], width:"10%", padding:"0px"}
            ,{targets:[2], width:"40%", padding:"0px"}
            ,{targets:[3], width:"35%", padding:"0px"}
            ,{targets:[4], width:"5%", padding:"0px"}

        ],
        initComplete: () =>{
            appendInputRow();
        },
        drawCallback: () =>{
            appendInputRow();
        }
    });
}

const compareTime = (startId, endId, initTime, message) =>{
    if($(`#${endId}`).val()){
        const startTime = moment($('#start-time').val(), 'HH:mm');
        const endTime = moment($('#end-time').val(), 'HH:mm');

        if(startTime.isAfter(endTime)){
            const startTimePicker = $(`#${startId}`)[0]._flatpickr;
            startTimePicker.setDate(initTime, true);
            toastr.error(message)
        }
    }
}

const appendInputRow = () => {
    // 이미 있으면 다시 안 붙이도록 체크
    if ($('#input-row').length === 0) {
        $('#datatable tbody').prepend(`
            <tr id="input-row">
                <form id="input-row-form">
                    <td class="px-2"><input type="text" class="form-control bg-white text-center" id="start-time"></td>
                    <td class="px-2"><input type="text" class="form-control bg-white text-center" id="end-time"></td>
                    <td class="px-2"><input type="text" class="form-control" name="title" id="input-title" placeholder="title"></td>
                    <td class="px-2"><input type="text" class="form-control" name="location" id="input-location" placeholder="location"></td>
                    <td class="px-2"><button type="button" class="btn btn-outline-primary border-0" id="btn-add"><i class='bx bxs-plus-square'></i></button></td>
                </form>
            </tr>
        `);

        setTimePicker('start-time', '09:00');
        setTimePicker('end-time', '18:00');

        $('#start-time').change(() => {
            compareTime('start-time', 'end-time', '09:00', '종료 시간보다 이후시간을 설정할 수 없습니다.');
        });

        $('#end-time').change(() => {
            compareTime('end-time', 'start-time', '18:00', '시작 시간보다 이전시간을 설정할 수 없습니다.');
        });

        $('#btn-add').click(() => {
            const startTime = $('#start-time').val().trim();
            const endTime = $('#end-time').val().trim();
            const title = $('#input-title').val().trim();
            const location = $('#input-location').val().trim();

            if (!title) {
                toastr.error('please enter the title.');
                $('#input-title').focus();
                return;
            }

            if (!location) {
                toastr.error('please enter the location.');
                $('#input-location').focus();
                return;
            }

            const data = {
                eventTitle: title,
                eventLocation: location,
                startTime: startTime,
                endTime: endTime
            };


            datatable.row.add({
                startTime: startTime,
                endTime: endTime,
                title: title,
                location: location
            }).draw(false);


        });
    }
}


const setDateRangePicker = () =>{
    $("#eventDate").daterangepicker({
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
        singleDatePicker: true,
        timePicker: false,
        startDate: moment().format('YYYY-MM-DD')

    })
}


const setTimePicker = (id, defaultTime) =>{

    flatpickr(`#${id}`, {
        enableTime: true,
        noCalendar: true,
        dateFormat: "H:i",
        time_24hr: true,
        defaultDate: defaultTime,
        minuteIncrement: 10,
    });


}