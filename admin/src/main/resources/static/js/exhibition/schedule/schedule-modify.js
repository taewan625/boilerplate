

window.addEventListener('load', () => {
    const scheduleId = getScheduleId();

    getSchedule(scheduleId);
    setDateRangePicker();

    setValid();

    $('#btn-save').click(()=>{
        if($('#modify-form').valid()){
            if(confirm("입력한 정보를 저장하시겠습니까?")){
                updateSchedule(scheduleId);
            }
        }
    })

    $('#btn-cancel').click(()=>{
        if(confirm("정말 취소하시겠습니까? 변경한 내용은 저장되지 않습니다.")){
            window.location.href = `/exhibition/schedule/${scheduleId}`;
        }
    })



    loadingExit();
});

const updateSchedule = (id) =>{
    const data = {
        eventDate : $('#eventDate').val(),
        organization: $('#organization').val(),
        eventName: $('#eventName').val(),
        eventDescription: $('#description').val()
    }

    $.ajax({
        type: "PUT",
        url: `/api/v1/admin/schedule/${id}`,
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: "JSON",
        success: (res) => {
            if (res.success) {
                toastr.success('정상적으로 처리되었습니다.');
                window.location.href = `/exhibition/schedule/${id}`;
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
        submitHandler: (form) => {

            // updateExhibitor();
        }
    });
}



const getSchedule = (id) => {
    $.ajax({
        type: "GET",
        url: `/api/v1/admin/schedule/${id}`,
        success: (res, textStatus, jqXHR) =>{
            if(res.success){
                Object.entries(Object.keys(res.data)).forEach(([key, value])=>{
                    switch (value){
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
        timePicker: false
    })
}
