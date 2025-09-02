window.addEventListener('load', () => {
    const popupModal = $('#popup-modal');
    const popupModalEl = document.getElementById('popup-modal');
    const popupModalInstance = bootstrap.Modal.getInstance(popupModalEl) || new bootstrap.Modal(popupModalEl);

    const form = $("#modify-form");
    setDateRangePicker();
    setValid();

    setPublishTable();
    setUnpublishTable();



    $('#datatable tbody').on('click', '.btn-detail', function () {
        const rowData = datatable.row($(this).parents('tr')).data();
        $("#modal-popup-title").text("POPUP MODIFY")
        setPopupModal(rowData);
        popupModalInstance.show();
    })
    $('#unpublish-datatable tbody').on('click', '.btn-detail', function () {
        const rowData = unpublishDatatable.row($(this).parents('tr')).data();
        $("#modal-popup-title").text("POPUP MODIFY")
        setPopupModal(rowData);
        popupModalInstance.show();
    })



    $("#file").change((e)=>{
        fileChange(e);
    });

    $("#btn-link").click(()=>{
        const urlRegex = /^(https?:\/\/)([\w.-]+)\.([a-z]{2,6})(:\d{1,5})?(\/[\w.@/%-]*)*(\?[\w@=&%-]*)?(#[\w@-]*)?$/;
        const url = $('#link').val()
        if(urlRegex.test(url)){
            window.open(url, "_blank");
        } else {
            toastr.error('',"https://,http:// 포함하여 정확한 URL을 입력해주세요. ",{timeOut:1500})

        }
    })

    $("#datatable tbody").sortable({
        helper: fixHelper,
        items: 'tr',
        update: (event, ui) => {
            const newData = [];
            $('#datatable tbody tr').each(function () {
                const rowData = datatable.row(this).data();
                newData.push(rowData);
            });
            datatable.clear().rows.add(newData).draw(false);

            updateOrder();
        }
    }).disableSelection();



    $('#btn-save').click(()=>{
       if(form.valid()){
           save();
       }
    });

    popupModal.on('show.bs.modal', (e)=> {
        if(datatable.rows().data().toArray().length > 2){
            $('#publish').attr('disabled','disabled');
        }else{
            $('#publish').removeAttr('disabled');
        }

    });

    popupModal.on('hide.bs.modal', (e)=> {
        const picker = $('#publicationPeriod').data('daterangepicker');

        $("#modal-popup-title").text("POPUP ADD")
        form[0].reset();
        form.validate().resetForm();
        $("#modify-form .form-control").removeClass("error");
        $("#popup").attr('src', '/static/img/backgrounds/noimage.png');

        picker.setStartDate(moment().format('YYYY-MM-DD'));
        picker.setEndDate(moment().format('YYYY-MM-DD'))

        $('#fileId').val(0);
        isModify = false;
    });

    loadingExit();
});
let logoFileSrc;
let datatable;
let unpublishDatatable;
let isModify = false;

function fixHelper(e, ui){
    ui.children().each(() => {
        $(this).width($(this).width());
    });
    return ui;
}


const updateOrder = () =>{
    const popups = datatable.rows().data().toArray().map((row, index) => ({
        ...row, // 기존 row 데이터 유지
        displayOrder: index + 1
    }));

    $.ajax({
        type: "PUT",
        url: `/api/v1/admin/popup/order`,
        data: JSON.stringify(popups),
        contentType: "application/json",
        dataType: "JSON",
        success: (res) => {
            if (res.success) {
                toastr.success('정상적으로 처리되었습니다.');
                datatable.ajax.reload();
            } else {
                toastr.error('실패 하였습니다.');
            }
        },
        beforeSend: () => { loadingStart() },
        complete: () => { loadingExit() },
        error: () => { toastr.error('서버 오류 발생'); }
    });

}



const setPublishTable = () => {
    datatable = $("#datatable").DataTable({
        searching: false,
        pageLength: 10,
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
            url : `/api/v1/admin/popup`,
            method : "POST",
            data : (d)=> {

                d.exhibitionId = $("#exhibition option:selected").val();
                d.published= 1;
            },
            beforeSend: () =>{loadingStart()} ,
            complete:() =>{loadingExit()},
            error: ()=>{}
        },
        processing: false,
        serverSide: false,
        order: [],
        columns: [
            { name : "", title : "", data : "displayOrder", className : "dt-head-center dt-body-center px-2", orderable: false,
                render:(data, type, row) =>{
                    return `<span class="cursor-grab"><i class='bx bx-grid-vertical' ></i></span>`
                }
            },
            { name : "DISPLAY-ORDER", title : "ORDER", data : "displayOrder", className : "dt-head-center dt-body-center px-2", orderable: false},
            { name : "PREVIEW", title : "PREVIEW", data : 'path', className : "dt-head-center dt-body-left px-2", orderable: false,
                render:(data, type, row) =>{
                    return `<div style="display: flex; flex-direction: column; align-items: center;">
                                <div style="position: relative; width: 100%; max-width: 152px; aspect-ratio: 1 / 1; border: 1px solid #f9f9f9;">
                                    <img onerror="this.src='/static/img/backgrounds/noimage.png'"
                                         draggable="false"
                                         alt="image"
                                         loading="lazy"
                                         decoding="async"
                                         src="${data}" style="position: absolute; width: 100%; height: 100%; inset: 0; object-fit: hover; color: transparent;" />
                                </div>
                            </div>`;
                }
            },
            { name : "TITLE", title : "TITLE", data : "title", className : "dt-head-center dt-body-left px-2", orderable: false},
            { name : "LINK", title : "LINK", data : "link", className : "dt-head-center dt-body-left px-2", orderable: false},
            { name : "PUBLICATION-PERIOD", title : "PUBLICATION PERIOD", data : "publicationPeriod", className : "dt-head-center dt-body-center px-2", orderable: false},
            { name : "ACTION", title : "", data : "id", className : "dt-head-center dt-body-center px-2", orderable: false,
                render:(data, type, row) =>{
                    return `<button class="btn btn-outline-primary border-0 btn-detail"><i class='bx bx-edit' ></i></button>`;
                }
            },
        ],
        "fnRowCallback": function(nRow, aData, iDisplayIndex) {

        },
        columnDefs:[
            {targets:[0], width:"5%", padding:"0px"}
            ,{targets:[1], width:"5%", padding:"0px"}
            ,{targets:[2], width:"15%", padding:"0px"}
            ,{targets:[3], width:"25%", padding:"0px"}
            ,{targets:[4], width:"30%", padding:"0px"}
            ,{targets:[5], width:"15%", padding:"0px"}
            ,{targets:[6], width:"5%", padding:"0px"}
        ],
    });
}


const setUnpublishTable = () => {
    unpublishDatatable = $("#unpublish-datatable").DataTable({
        searching: false,
        pageLength: 5,
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
            url : `/api/v1/admin/popup`,
            method : "POST",
            data : (d)=> {

                d.exhibitionId = $("#exhibition option:selected").val();
                d.published= 0;
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
            { name : "PREVIEW", title : "PREVIEW", data : 'path', className : "dt-head-center dt-body-left px-2", orderable: false,
                render:(data, type, row) =>{
                    return `<div style="display: flex; flex-direction: column; align-items: center;">
                                <div style="position: relative; width: 100%; max-width: 152px; aspect-ratio: 1 / 1; border: 1px solid #f9f9f9;">
                                    <img onerror="this.src='/static/img/backgrounds/noimage.png'"
                                         draggable="false"
                                         alt="image"
                                         loading="lazy"
                                         decoding="async"
                                         src="${data}" style="position: absolute; width: 100%; height: 100%; inset: 0; object-fit: hover; color: transparent;" />
                                </div>
                            </div>`;
                }
            },
            { name : "TITLE", title : "TITLE", data : "title", className : "dt-head-center dt-body-left px-2", orderable: false},
            { name : "LINK", title : "LINK", data : "link", className : "dt-head-center dt-body-left px-2", orderable: false},
            { name : "PUBLICATION-PERIOD", title : "PUBLICATION PERIOD", data : "publicationPeriod", className : "dt-head-center dt-body-center px-2", orderable: false},
            { name : "ACTION", title : "", data : "id", className : "dt-head-center dt-body-center px-2", orderable: false,
                render:(data, type, row) =>{
                    return `<button class="btn btn-outline-primary border-0 btn-detail"><i class='bx bx-edit' ></i></button>`;
                }
            },
        ],
        "fnRowCallback": function(nRow, aData, iDisplayIndex) {},
        columnDefs:[
            {targets:[0], width:"5%", padding:"0px"}
            ,{targets:[1], width:"15%", padding:"0px"}
            ,{targets:[2], width:"25%", padding:"0px"}
            ,{targets:[3], width:"30%", padding:"0px"}
            ,{targets:[4], width:"20%", padding:"0px"}
            ,{targets:[5], width:"5%", padding:"0px"}
        ],
    });
}


const setValid = () => {
    const updateForm = $("#modify-form");

    $.validator.addMethod("urlCheck", function (value) {
        if(value){
            return /^(https?:\/\/)([\w.-]+)\.([a-z]{2,6})(:\d{1,5})?(\/[\w.@/%-]*)*(\?[\w@=&%-]*)?(#[\w@-]*)?$/.test(value);
        }else{
            return true;
        }

    }, "The URL format is invalid.");

    updateForm.validate({
        rules: {
            title: {
                required: true,
                maxlength: 255,
            },
            link: {
                maxlength: 255,
                urlCheck: true
            },
            file: {
                required: function (){
                    return !isModify;
                }
            },
            publicationPeriod: {
                required: true,
            }
        },
        messages: {
            title: {
                required: "is a required field.",
                maxlength: 'Must be 255 characters or fewer.'
            },
            link: {
                maxlength: 'Must be 255 characters or fewer.'
            },
            file: {
                required: 'is a required field.'
            },
            publicationPeriod: {
                required: 'is a required field.'
            },
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

const geFormData = () =>{
    const formData = new FormData();

    let datepicker = $("#publicationPeriod").data("daterangepicker");

    if($('#id').val()){
        formData.append('id', $('#id').val());
    }

    formData.append('exhibitionId', $('#exhibition option:selected').val());
    formData.append('title', $('#title').val());
    formData.append('startDate', datepicker.startDate.format("YYYY-MM-DD"));
    formData.append('endDate', datepicker.endDate.format("YYYY-MM-DD"));
    formData.append('linkTargetCode', $('#linkType option:selected').val());


    formData.append('link', $('#link').val());

    formData.append('deviceCode', $('#deviceCode').val());
    formData.append('popupType', $('#popupType').val());
    formData.append('publish', $("input[name='publish']:checked").val());

    if($('#fileId').val()){
        formData.append('fileId', $('#fileId').val());
    }

    if($('#file')[0].files[0]){
        formData.append('file', $('#file')[0].files[0]);
    }

    return formData;
}


const save = () =>{
    const formData = geFormData();
    const option = {
        type: "POST",
        url: `/api/v1/admin/popup/insert`,
        data: formData,
        enctype:'multipart/form-data',
        contentType: false,
        processData: false,
        dataType: "JSON",
        success: (res, textStatus, jqXHR) =>{
            if(res.success) {
                toastr.success('',"저장 되었습니다.",{timeOut:1500})
                const modalEl = document.getElementById('popup-modal');
                const modalInstance = bootstrap.Modal.getInstance(modalEl);

                datatable.ajax.reload();
                unpublishDatatable.ajax.reload();

                modalInstance.hide();
            }else{
                if(res.message === "Invalid MIME type detected"){
                    toastr.error('',"변조된 파일은 업로드할 수 없습니다.",{timeOut:1500})
                }else{
                    toastr.error('',"실패 하였습니다.",{timeOut:1500})
                }
            }
        },
        beforeSend: () =>{loadingStart()} ,
        complete:() =>{loadingExit()},
        error: (jqXHR, textStatus, errorThrown) => {},
        async: false
    }


    if(isModify){
        option.type = "PUT";
        option.url =`/api/v1/admin/popup`;
    }

    $.ajax(option);
}



const setPopupModal = (data) => {
    isModify = true;
    if (data) {
        Object.entries(Object.keys(data)).forEach(([key, value]) => {
            switch (value) {
                case 'path':
                    $("#popup").attr('src', data[value]);
                    break;
                case 'published':

                    if(data['published']){
                        $('#publish').prop('checked', true);
                    }else{
                        $('#publish1').prop('checked', true);
                    }

                    break;
                case 'publicationPeriod':
                    const picker = $(`#${value}`).data('daterangepicker');
                    picker.setStartDate(data['startDate']);
                    picker.setEndDate(data['endDate'])
                    break;

                default :
                    $(`#${value}`).val(data[value]);
            }
        });
    }

}

const setDateRangePicker = () =>{
    $("#publicationPeriod").daterangepicker({
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
        startDate: moment().format('YYYY-MM-DD'),
        endDate: moment().format('YYYY-MM-DD')
    })
}



const fileChange = (e) => {
    const file = e.target.files[0];
    const src = !isModify ? '/static/img/backgrounds/noimage.png' : logoFileSrc;

    if(!file){
        $("#popup").attr('src', src);
        return;
    }

    // 파일 타입 체크 (png)
    if (!file.type.startsWith('image/png') &&
        !file.type.startsWith('image/jpeg')
    ) {
        toastr.error('JPG, PNG 이미지 파일만 선택해주세요.');
        e.target.value = ''; // 선택된 파일 초기화

        $("#popup").attr('src', src);

        return;
    }

    const maxSize = 10 * 1024 * 1024;
    if (file.size > maxSize) {
        toastr.error('파일 크기는 10MB 이하만 가능합니다.');
        e.target.value = ''; // 선택된 파일 초기화
        $("#popup").attr('src', src);
        return;
    }


    const reader = new FileReader();
    reader.onload = function (e) {
        $("#popup").attr('src', e.target.result);
    };
    reader.readAsDataURL(file);
}