window.addEventListener('load', () => {
    const attendeeId = getAttendeeId();
    loadCode();
    getAttendee(attendeeId);

    $("#btn-pre").click(() => {
        window.location.href = '/attendee';
    });

    $("#btn-payment-refund").click(() => {
        if(confirm("정말로 결제 취소 하시겠습니끼?")){
            paymentRefund(attendeeId);
        }
    });

    $("#btn-send-barcode").click(() => {
        if(confirm("바코드 메일을 다시 전송하시겠습니까?")){
            sendBarcode(attendeeId)
        }else{
            toastr.info('',"발송을 취소하였습니다.",{timeOut:1500})
        }
    });

    setValid();
    $('#attendee-update-modal').on('show.bs.modal', (e)=> {
        setUpdateModal();
    });

    $('#btn-save').click(() =>{
        if($("#modify-form").valid()){
            updateAttendee(attendeeId);
        }
    });

    loadingExit();
});
let attendeeData;

const updateAttendee = (attendeeId) =>{
    const firstName = $('#modify-first-name').val();
    const lastName = $('#modify-last-name').val();
    const company = $('#modify-company').val();
    const jobTitle = $('#modify-job-title').val();
    const countryId = $('#modify-country option:selected').val();
    const callingCode = $('#modify-calling option:selected').val();
    const mobileNumber = $('#modify-mobile').val();

    const data = {
        firstName : firstName,
        lastName: lastName,
        company: company,
        jobTitle: jobTitle,
        countryId: countryId,
        callingCode: callingCode,
        mobileNumber: mobileNumber
    }

    $.ajax({
        type: "PUT",
        url: `/api/v1/admin/attendee/${attendeeData.orderNumber}`,
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: "JSON",
        success: (res, textStatus, jqXHR) =>{
            if(res.success) {
                const modalEl = document.getElementById('attendee-update-modal');
                const modalInstance = bootstrap.Modal.getInstance(modalEl);

                toastr.success('',"정상적으로 처리되었습니다.",{timeOut:1500})
                getAttendee(attendeeId);
                modalInstance.hide();
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

const setUpdateModal = () =>{
    $("#modify-form").validate().resetForm();
    $("#modify-form .form-control").removeClass("error");
    if(attendeeData){
        $('#modify-first-name').val(attendeeData.firstName);
        $('#modify-last-name').val(attendeeData.lastName);
        $('#modify-company').val(attendeeData.company);
        $('#modify-job-title').val(attendeeData.jobTitle);
        $('#modify-country').val(attendeeData.countryId);
        $('#modify-calling').val(attendeeData.callingCode);
        $('#modify-mobile').val(attendeeData.mobile);
    }
}

const setValid = () => {
    const updateForm = $("#modify-form");
    updateForm.validate({
        rules: {
            modifyFirstName: {
                required: true,
                maxlength: 100,
            },
            modifyLastName: {
                required: true,
                maxlength: 100,
            },
            modifyCompany: {
                maxlength: 100,
            },
            modifyJobTitle: {
                maxlength: 500,
            }
        },
        messages: {
            modifyFirstName: {
                required: "is a required field.",
                maxlength: 'Must be 100 characters or fewer.'
            },
            modifyLastName: {
                required: "is a required field.",
                maxlength: 'Must be 100 characters or fewer.'
            },
            modifyCompany: {
                maxlength: 'Must be 100 characters or fewer.'
            },
            modifyJobTitle: {
                maxlength: 'Must be 500 characters or fewer.'
            },
        }
    });
}


const loadSelect = (key, options) => {
    switch (key){
        case  'callingCodeList':
            $.each(options, function(index, item) {
                $('#modify-calling').append(`<option value="${item.callingCode}">${item.callingCode + ' ' + item.countryNameEn}</option>`);
            });
            break;
        case  'countryList':
            $.each(options, function(index, item) {
                $("#modify-country").append(`<option value="${item.id}">${item.countryNameEn}</option>`);
            });
            break;

    }
}

const loadCode = (id) => {
    $.ajax({
        type: "GET",
        url: `/api/v1/woc/code-set?setcode=exhibitor-register`,
        success: (res, textStatus, jqXHR) =>{
            if(res.success){
                Object.entries(Object.keys(res.data.data)).forEach(([key, value])=>{
                    loadSelect(value, res.data.data[value]);
                });
            }
        },
        beforeSend: () =>{loadingStart()} ,
        complete:() =>{loadingExit()},
        error: (jqXHR, textStatus, errorThrown) => {},
        async: false
    });
}



const sendBarcode = (attendeeId) =>{
    $.ajax({
        type: "GET",
        url: `/api/v1/admin/attendee/${attendeeId}/remind`,
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

const paymentRefund = (id) =>{
    const merchantUid = $("#orderNumber").val();;
    const impUid = $("#impUid").val();
    const barcode = $("#barcode").val();
    const attendeeId = id;

    const data = {
        merchantUid : merchantUid,
        impUid: impUid,
        barcode: barcode,
        attendeeId: attendeeId
    }

    $.ajax({
        type: "POST",
        url: `/api/v1/admin/attendee/cancel`,
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: "JSON",
        success: (res, textStatus, jqXHR) =>{
            if(res.success) {
                toastr.success('',"정상적으로 환불되었습니다.",{timeOut:1500})
                getAttendee(attendeeId);
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

const getAttendee = (attendeeId) => {
    $.ajax({
        type: "GET",
        url: `/api/v1/admin/attendee/${attendeeId}`,
        success: (res, textStatus, jqXHR) =>{
            if(res.success){
                attendeeData = res.data;
                Object.entries(Object.keys(res.data)).forEach(([key, value])=>{
                    if(value !== 'payments'){
                        switch (value){
                            case 'status':
                                if(res.data[value]){
                                    $(`#${value}`).val("Cancelled");
                                    $('#btn-payment-refund').attr('disabled', 'disabled');
                                    $('#btn-send-barcode').attr('disabled', 'disabled');
                                }else{
                                    $(`#${value}`).val("Active");
                                }
                                break;

                            default : $(`#${value}`).val(res.data[value]);
                        }

                    }
                })
            }
        },
        beforeSend: () =>{loadingStart()} ,
        complete:() =>{loadingExit()},
        error: (jqXHR, textStatus, errorThrown) => {},
        async: false
    });
}

const getAttendeeId = () => {
    const path = window.location.pathname;
    const match = path.match(/\/attendee\/(\d+)/);
    const id = match ? match[1] : null;
    return id;
}
