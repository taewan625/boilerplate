'use strict';

(function (){
    loadingStart();

    $('#exhibition').select2({
        minimumResultsForSearch: Infinity
    });


    if(sessionStorage.getItem("exhibitionId")){
        $('#exhibition').val(sessionStorage.getItem("exhibitionId")).trigger('change');
    }else{
        sessionStorage.setItem('exhibitionId',$('#exhibition option:selected').val())
    }
    $('#exhibition').change(() => {
        sessionStorage.setItem('exhibitionId',$('#exhibition option:selected').val())
        window.location.href = `/`;
    })

})();


function clearSessionStorage() {
    sessionStorage.clear(); // ✅ 모든 세션 스토리지 데이터 삭제
}

function loadingStart() {
    $('.wrap-loading').removeClass('d-none');
}

function loadingExit(){
    $('.wrap-loading').addClass('d-none');
}

function unescapeHtml(escapedStr) {
    const parser = new DOMParser();
    const doc = parser.parseFromString(escapedStr, "text/html");
    return doc.body.textContent || "";
}

function getExcelDownload(url) {
    //const url = `/api/v1/admin/exhibitor/excel/${$("#exhibition option:selected").val()}`;
    let html = '';
    html += "<form id='excel' method='get'></form>";
    $('#datatable').append(html);
    $("#excel").attr("action", url).submit();
    $("#excel").remove();
}

function getFileDownload(id, uuid) {
    //const url = `/api/v1/admin/exhibitor/excel/${$("#exhibition option:selected").val()}`;
    let html = '';
    html += "<form id='file-download' method='get'></form>";

    $(`#${id}`).append(html);

    $("#file-download").attr("action", `/api/v1/woc/ncp/files/${uuid}`).submit();
    $("#file-download").remove();
}


function fixHelper(e, ui){
    ui.children().each(() => {
        $(this).width($(this).width());
    });
    return ui;
}

const numberWithCommas = (value) => {
    if (typeof value !== 'string') {
        value = String(value); // 숫자일 경우 문자열로 변환
    }
    value = value.replace(/[^0-9]/g, ''); // 숫자 외 제거
    return value.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}


function numberReplaceCommas(value){
    return value.replace(/,/g, '');
}

function setDatepicker(id){
    $(`#${id}`).daterangepicker({
        locale: {
            format: "YYYY-MM-DD",
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
        singleDatePicker: true,
    });

}

function getValue(id){
    const val = $(`${id}`).val();
    return val !== '' ? val : null;
}

function getSelectValue(id){
    const val = $(`${id} option:selected`).val();
    return val !== '' ? val : null;
}