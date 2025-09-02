window.addEventListener('load', () => {
    const scheduleId = getScheduleId();
    getSchedule(scheduleId);
    setDatatable(scheduleId);


    $('#datatable tbody').on('click', '.btn-delete', function () {
        const rowData = datatable.row($(this).parents('tr')).data();
        const rowId = rowData.id;

        deleteEvent(scheduleId, rowId);
    })

    $('#btn-update').click(()=>{
        window.location.href = `/exhibition/schedule/${scheduleId}/modify`;

    });


    $('#btn-publish').click(()=>{
        if(datatable.rows().data().toArray().length < 1){
            toastr.error('DETAILED SCHEDULE 1개 이상 등록해 주세요.');
            return;
        }
        if(confirm('현재 일정을 활성화하시겠습니까?')){
            updatePublish(scheduleId, 1);
        }
    })

    $('#btn-unpublish').click(()=>{
        if(confirm('일정을 비활성화하시겠습니까?')){
            updatePublish(scheduleId, 0);
        }
    })

    $('#btn-delete').click(()=>{
        if(confirm("일정을 삭제하시겠습니까?")){
            deleteSchedule(scheduleId);
        }
    });


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

            updateOrder();
        }
    }).disableSelection();


    loadingExit();
});
let datatable;


const deleteSchedule = (id) => {
    $.ajax({
        type: "DELETE",
        url: `/api/v1/admin/schedule/${id}`,
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


const updateOrder = () =>{
    const events = datatable.rows().data().toArray().map((row, index) => ({
        ...row, // 기존 row 데이터 유지
        order: index + 1,
        eventTitle: row.title,
        eventLocation: row.location
    }));

    $.ajax({
        type: "PUT",
        url: `/api/v1/admin/schedule/event/order`,
        data: JSON.stringify(events),
        contentType: "application/json",
        dataType: "JSON",
        success: (res) => {
            if (res.success) {
                toastr.success('정상적으로 처리되었습니다.');
            } else {
                toastr.error('실패 하였습니다.');
            }
        },
        beforeSend: () => { loadingStart() },
        complete: () => { loadingExit() },
        error: () => { toastr.error('서버 오류 발생'); }
    });

}


const updatePublish = (id, used) =>{
    const data = {
        used : used
    }

    $.ajax({
        type: "PUT",
        url: `/api/v1/admin/schedule/${id}/publish`,
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: "JSON",
        success: (res) => {
            if (res.success) {
                toastr.success('정상적으로 처리되었습니다.');
                getSchedule(id);
            } else {
                toastr.error('실패 하였습니다.');
            }
        },
        beforeSend: () => { loadingStart() },
        complete: () => { loadingExit() },
        error: () => { toastr.error('서버 오류 발생'); }
    });

}

const deleteEvent = (eventId, id) => {
    $.ajax({
        type: "DELETE",
        url: `/api/v1/admin/schedule/event/${eventId}/${id}`,
        contentType: "application/json",
        dataType: "JSON",
        success: (res) => {
            if (res.success) {
                toastr.success('정상적으로 처리되었습니다.');
                datatable.ajax.reload(null, false);
            } else {
                toastr.error('실패 하였습니다.');
            }
        },
        beforeSend: () => { loadingStart() },
        complete: () => { loadingExit() },
        error: () => { toastr.error('서버 오류 발생'); }
    });
}

const setDatatable = (id) => {
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
        ajax: {
            url : `/api/v1/admin/schedule/event`,
            method : "POST",
            data : (d)=> {
                d.eventId = id;
            },
            beforeSend: () =>{loadingStart()} ,
            complete:(d) =>{loadingExit()},
            error: ()=>{}
        },
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
            appendInputRow(id);
        },
        drawCallback: () =>{
            appendInputRow(id);
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

const appendInputRow = (id) => {
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
                eventId: id,
                eventTitle: title,
                eventLocation: location,
                startTime: startTime,
                endTime: endTime,
                order: datatable.rows().data().toArray().length+1
            };

            $.ajax({
                type: "POST",
                url: `/api/v1/admin/schedule/event/register`,
                data: JSON.stringify(data),
                contentType: "application/json",
                dataType: "JSON",
                success: (res) => {
                    if (res.success) {
                        toastr.success('정상적으로 처리되었습니다.');
                        datatable.ajax.reload(null, false);
                    } else {
                        toastr.error('실패 하였습니다.');
                    }
                },
                beforeSend: () => { loadingStart() },
                complete: () => { loadingExit() },
                error: () => { toastr.error('서버 오류 발생'); }
            });
        });
    }
}



const getSchedule = (id) => {
    $.ajax({
        type: "GET",
        url: `/api/v1/admin/schedule/${id}`,
        success: (res, textStatus, jqXHR) =>{
            if(res.success){
                Object.entries(Object.keys(res.data)).forEach(([key, value])=>{
                    switch (value){
                        case 'status':
                            if(res.data[value] === 'PUBLISHED'){
                                $('#btn-unpublish').removeClass('d-none')
                                $('#btn-publish').addClass('d-none');
                            }else{
                                $('#btn-unpublish').addClass('d-none')
                                $('#btn-publish').removeClass('d-none');
                            }
                        default : $(`#${value}`).val(res.data[value]);
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


const getScheduleId = () => {
    const path = window.location.pathname;
    const match = path.match(/\/exhibition\/schedule\/(\d+)/);
    const id = match ? match[1] : null;
    return id;
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