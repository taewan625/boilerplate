window.addEventListener('load', () => {
    const exhibitorId = getExhibitorId();

    $("#search-type").select2({
        width: '100%',
        minimumResultsForSearch: Infinity
    })

    setHistoryDatatable(exhibitorId);

    const popupModal = $('#invitation-update-modal')
    const popupModalEl = document.getElementById('invitation-update-modal');
    popupModalInstance = bootstrap.Modal.getInstance(popupModalEl) || new bootstrap.Modal(popupModalEl);
    const form = $("#invitation-modify-form");
    setValid(form);

    popupModal.on('hide.bs.modal', (e)=> {
        form[0].reset();
        form.validate().resetForm();
        isModify = false;

        form.find(".form-control").removeClass("error");
    });


    $('#btn-save').click(()=>{
        if(form.valid()){
            if(confirm("입력한 정보를 저장하시겠습니까?")){
                updateInvitation(exhibitorId, popupModalInstance);
            }
        }
    })

    $('#btn-search').click(()=>{
        historyDatatable.ajax.reload();
    })

    loadingExit();
});
let historyDatatable;
let popupModalInstance
let isModify = false;


const updateInvitation =(exhibitorId, modal) => {
    const id = $("#invitationId").val();
    const name = $("#modify-name").val();
    const company = isEmpty($("#modify-company").val().trim()) ? null : $("#modify-company").val();
    const jobTitle = isEmpty($("#modify-job-title").val().trim()) ? null : $("#modify-job-title").val();
    const country = isEmpty($("#modify-country").val().trim()) ? null : $("#modify-country").val();
    const city = isEmpty($("#modify-city").val().trim()) ? null : $("#modify-city").val();
    const phoneNumber = isEmpty($("#modify-phone-number").val().trim()) ? null : $("#modify-phone-number").val();
    const cancelled = $("input[name='modifyCancelled']:checked").val()
    const cancelReason = isEmpty($("#modify-cancel-reason").val().trim()) ? null : $("#modify-cancel-reason").val();

    const data = {
        id: id,
        name: name,
        company: company,
        jobTitle: jobTitle,
        country: country,
        city: city,
        phoneNumber: phoneNumber,
        cancelled : cancelled,
        cancelReason: cancelReason
    }

    $.ajax({
        type: "PUT",
        url: `/api/v1/admin/exhibitor/invitation/${id}`,
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: "JSON",
        success: (res, textStatus, jqXHR) =>{
            if(res.success) {
                toastr.success('',"정상적으로 처리되었습니다.",{timeOut:1500})
                historyDatatable.ajax.reload();
                popupModalInstance.hide();
                getExhibitor(exhibitorId);

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





const setValid = (form) => {
    $("input[name='modifyCancelled']").change((e) =>{
        if(e.target.value === "false"){
            isModify = false;
            form.validate().resetForm();
            form.find(".form-control").removeClass("error");
        }else{
            isModify = true;
        }
    })

    form.validate({
        rules: {
            modifyName: {
                required: true,
                maxlength: 200,
                normalizer: function(value) {
                    return $.trim(value);
                }
            },
            modifyCompany: {
                maxlength: 100,
            },
            modifyJobTitle: {
                maxlength: 100,
            },
            modifyCountry: {
                maxlength: 100,
            },
            modifyCity: {
                maxlength: 500,
            },
            modifyPhoneNumber: {
                maxlength: 100,
            },
            modifyCancelReason: {
                required: function (){
                    return isModify;
                },
                maxlength:500,
                normalizer: function(value) {
                    return $.trim(value);
                }
            }
        },
        messages: {
            modifyName: {
                required: "is a required field.",
                maxlength: 'Must be 100 characters or fewer.'
            },
            modifyCompany: {
                maxlength: 'Must be 100 characters or fewer.'
            },
            modifyJobTitle: {
                maxlength: 'Must be 100 characters or fewer.'
            },
            modifyCountry: {
                maxlength: 'Must be 100 characters or fewer.'
            },
            modifyCity: {
                maxlength: 'Must be 500 characters or fewer.'
            },
            modifyPhoneNumber: {
                maxlength: 'Must be 100 characters or fewer.'
            },
            modifyCancelReason: {
                required: "is a required field.",
                maxlength: 'Must be 500 characters or fewer.'
            }
        },
        errorPlacement: function (error, element) {
            if (element.parent().hasClass('input-group')) {
                // input-group인 경우 input-group 뒤에 메시지 붙이기
                error.insertAfter(element.parent());
            } else {
                error.insertAfter(element);
            }
        }
    });
}





const setHistoryDatatable = (id) => {
    historyDatatable = $("#invitation-datatable").DataTable({
        searching: false,
        info: true,   //하단 페이지 수 비활성화
        infoCallback: (settings, start, end, max, total, pre) =>{
            // 현재 테이블 전체 데이터 가져오기
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
            url : `/api/v1/admin/exhibitor/invitation`,
            method : "POST",
            data : (d)=> {

                d.exhibitorId =id;
                // d.applicationType= $("input[name=applicationType]:checked").val();
                // d.sponsor= $("input[name=sponsor]:checked").val();
                // d.industry= $("#industry option:selected").val();
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
            { name : "NO", title : "NO", data : "no", className : "dt-head-center dt-body-center px-2", orderable: false},
            { name : "BARCODE", title : "BARCODE", data : "barcode", className : "dt-head-center dt-body-left px-2", orderable: false,
                render: (data, type, row) =>{
                    return `<span>${data}</span><br /><span class="text-muted">${row.type}</span>`;
                }
            },
            { name : "EMAIL", title : "EMAIL", data : "email", className : "dt-head-center dt-body-left px-2", orderable: false,
                render: (data, type, row) =>{
                    let email = data;
                    let phoneNumber = row.phoneNumber;
                    if(!phoneNumber){
                        phoneNumber =  '-';
                    }
                    return `<span>${email}</span><br /><span class="text-muted">${phoneNumber}</span>`;
                }
            },
            { name : "NAME", title : "NAME", data : "name", className : "dt-head-center dt-body-left px-2", orderable: false},
            { name : "COMPANY", title : "COMPANY", data : "company", className : "dt-head-center dt-body-left px-2", orderable: false,
                render: (data, type, row) =>{
                    let company = data;
                    let jobTitle = row.jobTitle;
                    if(!company){
                        company =  '-';
                    }
                    if(!jobTitle){
                        jobTitle =  '-';
                    }
                    return `<span>${company}</span><br /><span class="text-muted">${jobTitle}</span>`;
                }
            },
            { name : "COUNTRY", title : "COUNTRY", data : "country", className : "dt-head-center dt-body-left px-2", orderable: false,
                render: (data, type, row) =>{
                    let country = data;
                    let city = row.city;
                    if(!country){
                        country =  '-';
                    }
                    if(!city){
                        city =  '-';
                    }
                    return `<span>${country}</span><br /><span class="text-muted">${city}</span>`;
                }
            },
            { name : "STATE", title : "STATE", data : "cancelled", className : "dt-head-center dt-body-center px-2", orderable: false,
                render: (data, type, row) =>{
                    let state = `<span class="badge bg-label-success">Sent</span>`

                    if(data){
                        state = `<span class="badge bg-label-danger">Cancelled</span>`
                    }

                    return state;
                }
            },
            { name : "DATE", title : "DATE", data : "createdAt", className : "dt-head-center dt-body-center px-2", orderable: false,
                render: (data, type, row) =>{
                    return moment(data).format("YYYY-MM-DD");
                }
            },
        ],
        fnRowCallback: (nRow, aData, iDisplayIndex)=> {
            $(nRow).click(()=>{

                $("#modify-type").val(aData.type)
                $("#modify-barcode").val(aData.barcode)
                $("#modify-email").val(aData.email)
                $("#modify-name").val(aData.name)
                $("#modify-company").val(aData.company)
                $("#modify-job-title").val(aData.jobTitle)
                $("#modify-country").val(aData.country)
                $("#modify-city").val(aData.city)
                $("#modify-phone-number").val(aData.phoneNumber)
                $("#modify-cancel-reason").val(aData.cancelReason)

                if (aData.cancelled){
                    $("#modifyCancelled2").prop('checked', true);
                    $("input[name='modifyCancelled']").attr("disabled","disabled");
                    $("#modify-name").attr("disabled","disabled");
                    $("#modify-company").attr("disabled","disabled");
                    $("#modify-job-title").attr("disabled","disabled");
                    $("#modify-country").attr("disabled","disabled");
                    $("#modify-city").attr("disabled","disabled");
                    $("#modify-phone-number").attr("disabled","disabled");
                    $("#modify-cancel-reason").attr("disabled","disabled");
                    $("#btn-save").attr("disabled","disabled");
                }else{
                    $("#modifyCancelled1").prop('checked', true);
                    $("input[name='modifyCancelled']").removeAttr("disabled");
                    $("#modify-name").removeAttr("disabled");
                    $("#modify-company").removeAttr("disabled");
                    $("#modify-job-title").removeAttr("disabled");
                    $("#modify-country").removeAttr("disabled");
                    $("#modify-city").removeAttr("disabled");
                    $("#modify-phone-number").removeAttr("disabled");
                    $("#modify-cancel-reason").removeAttr("disabled");
                    $("#btn-save").removeAttr("disabled");
                }


                $("#invitationId").val(aData.id)


                popupModalInstance.show();
            })

        },
        columnDefs:[
            {targets:[0], width:"5%", padding:"0px"}
            ,{targets:[1], width:"10%", padding:"0px"}
            ,{targets:[2], width:"20%", padding:"0px"}
            ,{targets:[3], width:"15%", padding:"0px"}
            ,{targets:[4], width:"18%", padding:"0px"}
            ,{targets:[5], width:"12%", padding:"0px"}
            ,{targets:[6], width:"5%", padding:"0px"}
            ,{targets:[7], width:"10%", padding:"0px"}
        ]

    });
}

