'use strict';

const defaultParams = Object.freeze({
    year: new Date().getFullYear(),
    //TODO 개선: 전시코드 동적으로 세팅될 수 있도록 처리 필요
    exhibitionId: 3,//전시 코드
    path: window.location.pathname,
});


function loadingStart(){
    $('.js-evt__loading').removeClass('d-none');
}

function loadingExit(){
    $('.js-evt__loading').addClass('d-none');
}

/**
 * 조회한 공통코드로 form 요소를 렌더링하는 함수
 *
 * @param {string} setCode set-code 값
 * @param {Array<Object>} options - 각 요소에 대한 렌더링 정보를 담은 배열
 * @param {string} options[].id - HTML 요소의 id (select 또는 radio 컨테이너)
 * @param {'codeList'|'countryList'|'callingCodeList'} options[].type - 데이터 종류를 구분하는 타입
 * @param {string} [options[].parentCode] - type이 'codeList'일 경우 필요한 부모 코드
 * @param {'select'|'radio'} options[].formElement - 생성할 폼 요소 타입 (select 박스 또는 radio 그룹)
 *
 * cf. 국가 관련 코드는 radio로 사용될 가능성이 적기 때문에 formElement 제외 (select만 사용한다고 가정)
 */
function renderCodeFormHTML(setCode, options){
    $.ajax({
        type: "GET",
        url: `/api/v1/woc/code-set-new?setcode=${setCode}`,
        success: (res, textStatus, jqXHR) => {
            if (res.success) {
                const codes = res.data;

                //공통코드 목록 타입변경
                const codeMap = {};
                codes.codeList?.forEach((element, idx) => {
                    codeMap[element.code] = element.children;
                });

                //select option 별 공통코드 세팅
                options.forEach((option, idx) => {
                    switch (option.type) {
                        case 'codeList':
                            renderCodeListHTML((codeMap[option.parentCode] || []), option.id, option.formElement);
                            break;
                        case 'countryList':
                            renderCountryHTML((codes.countryList || []), option.id);
                            break;
                        case 'callingCodeList':
                            renderCallingCodeHTML((codes.callingCodeList || []), option.id);
                            break;
                        default:
                            console.warn(`Unknown type: ${option.type}`);
                            return;
                    }
                });
            }
        },

        beforeSend: () => {/*loadingStart()*/},
        complete:() => {/*loadingExit()*/},
        error: (jqXHR, textStatus, errorThrown) => {},
        async: true
    });
}

/**
 * CodeList 렌더링
 *
 * @param {Array<Object>} codeList code 목록
 * @param {string} id HTML id
 * @param {'select'|'radio'} formElement - 생성할 폼 요소 타입 (select 박스 또는 radio 그룹)
 */
function renderCodeListHTML(codeList, id, formElement) {
    //렌더링
    codeList.forEach((element) => {
        if (formElement === 'select') {
            $('#' + id).append(`<option value="${element.code}">${element.codeName}</option>`);
        }
        else {
            $('#' + id).append(`<div class="col-12">
                              <div class="form-check mb-2">
                                <input class="form-check-input" type="radio" name="${id}" id="${element.code}" value="${element.code}">
                                <label class="form-check-label" for="${element.code}">${element.codeName}</label>
                              </div>
                            </div>`);
        }
    });

    if (formElement === 'select') {
        $(`label[for="${id}"]`).on('click', function (e) {
            e.preventDefault();
            $('#'+id).select2('open');
        });
    }
}

/**
 * countryCodeList 렌더링
 *
 * @param {Array<Object>} codeList code 목록
 * @param {string} id - HTML id
 */
function renderCountryHTML(codeList, id) {
    //렌더링
    codeList.forEach((code) => {
        $('#' + id).append(`<option value="${code.id}">${code.countryNameEn}</option>`);
    });

    $(`label[for="${id}"]`).on('click', function (e) {
        e.preventDefault();
        $('#'+id).select2('open');
    });
}
/**
 * callingCodeList 렌더링
 *
 * @param {Array<Object>} codeList code 목록
 * @param {string} id - HTML id
 */
function renderCallingCodeHTML(codeList, id) {
    //렌더링
    codeList.forEach((code) => {
        $('#' + id).append(`<option value="${code.callingCode}">${code.callingCode}</option>`);
    });

    $(`label[for="${id}"]`).on('click', function (e) {
        e.preventDefault();
        $('#'+id).select2('open');
    });
}


/**
 * 파일 다운로드
 *
 * @param {string} type 다운로드 시작점에 따른 타입 구분 (web: 화면단에서 직접 다운로드, server: 서버단에서 파일 다운로드)
 * @param {string} id DOM ID
 *
 * @param {Object} fileInfo 파일 정보 객체
 * @param {string} [fileInfo.url] web 다운로드 URL
 * @param {string} [fileInfo.uuid] server 다운로드 UUID
 * @param {string} [fileInfo.downloadName] 저장 시 사용할 파일 이름 (선택, 기본값: 서버 응답 또는 URL에서 자동 추출)
 */
function setDownloadFile(type, id, fileInfo){
    $('#' + id).off('click').on('click', async function (e) {
        e.preventDefault();

        try {
            let url;

            //화면단 다운로드 (url 필수)
            if (type === 'web') {
                if (!fileInfo.url) throw new Error('URL is missing.');
                url = fileInfo.url;
            }
            //서버 다운로드 (uuid 필수)
            else if (type === 'server') {
                if (!fileInfo.uuid) throw new Error('UUID is missing.');
                url = '/api/v1/woc/ncp/files/' + fileInfo.uuid;
            }
            else {
                throw new Error('Unknown download type.');
            }

            const resp = await fetch(url);

            if (resp.ok) {
                const blob = await resp.blob();
                const blobUrl = window.URL.createObjectURL(blob);
                const element = document.createElement('a');
                element.href = blobUrl;
                element.download = fileInfo.downloadName || 'downloaded-file';
                document.body.appendChild(element);
                element.click();
                document.body.appendChild(element);
                window.URL.revokeObjectURL(blobUrl);
            }
            else {
                throw new Error(`HTTP error! status: ${resp.status}`);
            }

        } catch (error) {
            console.error('File download failed:', error);
            alert('An error occurred while downloading the file. Please try again.');
        }
    });
}

/**
 * 디바운스 함수
 * 지정한 시간(delay) 내에 연속 호출되는 이벤트 중 마지막 호출만 실행
 * 예: resize, scroll, input 이벤트 처리 최적화 등
 *
 * @param {Function} fn - 디바운스 처리할 콜백 함수
 * @param {number} delay - 지연 시간 (ms 단위)
 * @returns {Function} - 디바운스 래핑 함수
 */
function debounce(fn, delay) {
    let timer = null;
    return function (...args) {
        clearTimeout(timer);
        timer = setTimeout(() => fn.apply(this, args), delay);
    };
}

/**
 * 슬라이드 이미지 그리는 함수
 *
 * @param {Object[]} images - 이미지 정보
 * @param {string} [images.path] - 이미지 src
 * @param {string} [images.link] - 이미지 클릭 시, 이동하는 URL
 * @param {string} [images.linkTarget] - a 태그 target 타입
 *
 * @param {Object} options 옵션 값
 * @param {string} options.id 슬라이드 dom id
 * @param {string} [options.imgClass] 슬라이드 img class
 * @param {string} [options.moveBtnClass] 슬라이드 좌우 버튼 class
 *
 * @returns {void}
 */
function renderCarouse(images, options) {
    const carouseId = options.id;

    let buttonHTML = '';
    let contentHTML = '';

    images.forEach((data, index) => {
        const active = (index === 0) ? 'active' : '';

        buttonHTML += `<button type="button" data-bs-target="#${carouseId}" data-bs-slide-to="${index}" class="${active}" aria-current="true" aria-label="Slide ${index + 1}"></button>`;

        let imgHTML = `<img src="${data.path}" class="${options.imgClass || 'd-block object-fit-cover w-100 h-189-675'}" draggable="false" alt="...">`;

        //a 태그 포함 img 처리
        if (data.link) {
            contentHTML += `<div class="carousel-item ${active}"><a target="${data.linkTarget || '_target'}" href="${data.link}">${imgHTML}</a></div>`;

        }
        //img 처리
        else {
            contentHTML += `<div class="carousel-item ${active}">${imgHTML}</div>`;
        }
    });

    //img 렌더링
    $('#' + carouseId + ' .carousel-inner').append(contentHTML);

    //이미지가 다건인 경우 렌더링 목록
    if (1 < images.length) {
        //하단 버튼 렌더링
        $('#' + carouseId + ' .carousel-indicators').append(buttonHTML);

        //좌우 이동 클릭 버튼 렌더링
        $('#' + carouseId).append(`<button class="carousel-control-prev text-primary-600" type="button" data-bs-target="#${carouseId}" data-bs-slide="prev">
                                        <span class="visually-hidden">Previous</span>
                                        <svg class="${options.moveBtnClass}" width="48" height="48" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                          <path d="M15 6L9 12L15 18" stroke="currentColor"  stroke-width="1.5"  stroke-linecap="round" stroke-linejoin="round"/>
                                        </svg>
                                      </button>
                                      <button class="carousel-control-next text-primary-600" type="button" data-bs-target="#${carouseId}" data-bs-slide="next">
                                        <span class="visually-hidden">Next</span>
                                        <svg class="${options.moveBtnClass}" width="48" height="48" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                          <path d="M9 6L15 12L9 18" stroke="currentColor" stroke-width="1.5"  stroke-linecap="round" stroke-linejoin="round"/>
                                        </svg>
                                      </button>`
        );
    }
}

/**
 * form에 대한 jQuery Validation 유효성 검사기 초기화
 * 입력 변경 시 submit 버튼의 활성/비활성 상태를 동적으로 제어
 *
 * @param {string} formId formId
 * @param {Object} options - 설정 옵션 객체
 * @param {Object<string, Object>} options.rules - 유효성 검사 규칙
 * @param {Object<string, Object>} options.messages - 에러 메시지 설정
 * @param {string} [options.id] - submit 버튼 id
 *
 * @returns {import('jquery-validation').Validator} jQuery Validation validator 인스턴스 반환
 */
const setValid = (formId, options) => {
    const validator = $(formId).validate({
        //안보이는 요소, select2 검증 포함
        ignore: [],

        rules: options.rules,
        messages: options.messages,

        //유효성 검증 실패 요소로 포커스 강제 이동
        invalidHandler: function(event, validator) {
            if (validator.errorList.length) {
                validator.errorList[0].element.focus();
            }
        },

        errorClass: 'is-invalid',
        validClass: '',
        errorElement: 'div',

        errorPlacement: function (error, element) {
            //해당 class 존재 시, 우선순위로 error msg 위치를 선점
            const errorContainer = element.closest('.js-evt__errorPlacement');

            //textarea인 경우, 별도 영역에 직접 추가
            if (element.prop('tagName') === 'TEXTAREA') {
                element.parent().find('.js-evt__textErrorContainer').html(error);

            }
            //select2 처리
            else if (element.hasClass('select2-hidden-accessible')) {
                element.next('.select2').after(error);
            }
            //에러문구 사용자가 직접 지정
            else if (errorContainer && errorContainer.length) {
                errorContainer.append(error);
            }
            //기본 처리
            else {
                element.parent().append(error);
            }

            error.addClass('invalid-feedback');
        },

        highlight: function (element, errorClass, validClass) {
            $(element).addClass(errorClass).removeClass(validClass);
            $('label[for="' + element.id + '"]').addClass('text-danger');

            // select2 요소인 경우, 감싸고 있는 .select2에도 오류 클래스 추가
            if ($(element).hasClass('select2-hidden-accessible')) {
                $(element).next('.select2').addClass(errorClass);
            }
        },

        unhighlight: function (element, errorClass, validClass) {
            $(element).removeClass(errorClass).addClass(validClass);
            $('label[for="' + element.id + '"]').removeClass('text-danger');

            // select2 요소인 경우, 감싸고 있는 .select2에서 오류 클래스 제거
            if ($(element).hasClass('select2-hidden-accessible')) {
                $(element).next('.select2').removeClass(errorClass);
            }
        },
    });

    //focus out 이벤트 앞뒤 공백 일괄 제거
    $(formId).off('blur', 'input, textarea').on('blur', 'input, textarea',  (event) => {
        const target = event.currentTarget;
        target.value = target.value.trim();

        //textarea인 경우 #textCount 도 trim.length로 변경
        if(target.tagName === 'TEXTAREA'){
            $(`#${target.id}`).parent().find(`.js-evt__textCount`).text(target.value.length);
        }

        validator.element(target);
        updateSubmitButtonState(formId, options.id);
    });

    //폼 내 요소가 변경될 때 submit 버튼 상태 갱신
    $(formId).off('input change', 'input, textarea, select')
        .on('input change', 'input, textarea, select', () => updateSubmitButtonState(formId, options.id));

    $(formId).off('click', 'input[type=checkbox], input[type=radio]')
        .on('click', 'input[type=checkbox], input[type=radio]', () => updateSubmitButtonState(formId, options.id));


    //초기에도 버튼 상태 설정
    updateSubmitButtonState(formId, options.id);

    return validator;
};

/**
 * 폼의 유효성을 검사하여 submit 버튼의 활성화 상태와 CSS 클래스(component__button--disabled)를 동적으로 제어
 * @param {string} formId
 * @param {string} submitButtonId
 * @returns {void}
 */
function updateSubmitButtonState(formId, submitButtonId = '#submit') {
    const form = $(formId);
    const validator = form.validate();

    // 검사 대상이 없으면 함수 종료
    if (0 < validator.currentElements.length) {
        //검사 대상이 존재하는 경우만 검증 및 옵션
        const isValid = validator.checkForm(); //에러 메시지 없이 유효성 검사만 수행

        $(`${formId} ${submitButtonId}`).prop('disabled', !isValid);
        $(`${formId} ${submitButtonId}`).toggleClass('component__button--disabled', !isValid); // 클래스 추가/제거
    }
}

/**
 * 지정된 폼 요소의 데이터를 JSON 객체 형태로 반환
 *
 * @param {string} formId - formId
 *
 * @returns {Object.<string, string>} 폼 객체.
 */
function getFormJson(formId) {
    const result = {};

    const formArray = $(formId).serializeArray();
    formArray.forEach(({name, value}) => {
        const trimmed = value.trim();
        result[name] = trimmed === '' ? null : trimmed;
    });

    return result;
}

/**
 * textArea count 영역 동적 렌더링 및 count 이벤트 세팅 함수
 *
 * @param {{id: string, maxlength: number}[]} textAreas
 * @returns {void}
 */
function setTextAreaCount(textAreas) {
    textAreas.forEach((textArea) => {
        if (textArea.maxlength) {
            //렌더링
            $(`#${textArea.id}`).after(`
                                    <div class="d-flex justify-content-between align-items-center">
                                      <div class="js-evt__textErrorContainer"></div>
                                      <div class="form-text text-end ms-auto">
                                        <span class="js-evt__textCount">0</span> / ${textArea.maxlength}
                                      </div>
                                    </div>
                                    `);

            //textarea 작성 글자수 체크 이벤트
            $(`#${textArea.id}`).off('input').on('input', function () {
                $(`#${textArea.id}`).parent().find(`.js-evt__textCount`).text($(this).val().length);
            });
        }
    });
}

/**
 * input type='text' 에서 숫자만 작성가능하도록 정규식 처리
 *
 * @param {string} domIds
 * @returns {void}
 */
function inputNumberRegex(domIds) {
    $(domIds).off('input').on('input', function () {
        this.value = this.value.replace(/[^0-9]/g, '');
    });
}


/**
 * 카운트다운 타이머를 지정된 DOM 요소에 표시, 만료 시간 콜백 함수 실행
 *
 * @param {string} id - 타이머를 표시할 HTML 요소의 ID.
 * @param {number} seconds - 시작 시간 (초 단위).
 * @param {function} [callback] - 시간 종료 시 실행할 선택적 콜백 함수.
 */
function setCountDown(id, seconds, callback) {
    const timer = document.getElementById(id);

    if (!timer) {
        console.warn(`Element with id '${id}' not found.`);
        return null;
    }

    //초기 시간 표시
    let remainSeconds = seconds;
    timer.textContent = formatTime(remainSeconds);

    const interval = setInterval(() => {
        remainSeconds--;

        if (remainSeconds <= 0) {
            clearInterval(interval);
            timer.textContent = formatTime(0);

            if (typeof callback === 'function') {
                callback();
            }

            return false;
        }

        timer.textContent = formatTime(remainSeconds);

    }, 1000);

    return interval;
}

/**
 * 초 단위 숫자를 "MM:SS" 형식의 문자열로 변환
 *
 * @param {number} seconds - 변환할 시간 (초 단위)
 * @returns {string} "MM:SS" 형식의 문자열
 */
function formatTime(seconds) {
    const MM = String(Math.floor(seconds / 60)).padStart(2, '0');
    const SS = String(seconds % 60).padStart(2, '0');
    return `${MM}:${SS}`;
}


/**
 * 무한 스크롤을 위한 IntersectionObserver 객체를 생성하고 상태를 관리
 *
 * @param {Function} callback - 관찰 대상이 화면에 들어올 때 실행할 함수
 *
 * @returns {Object} 생성된 객체
 *   @property {Object} infinityParams - 무한 스크롤 상태 관리 객체
 *       @property {boolean} isLoading - 데이터 로딩 중 여부
 *       @property {boolean} isEnd - 마지막 페이지 도달 여부
 *       @property {Function} reset - 상태를 초기값으로 초기화
 *   @property {IntersectionObserver} observer - 생성된 IntersectionObserver 인스턴스
 *
 */
function setObserver(callback) {
    //무한 스크롤 변수
    const infinityParams = {
        isLoading: false,//로딩 여부
        isEnd: false,//마지막 페이지 여부

        reset() {
            this.isLoading = false;
            this.isEnd = false;
        }
    }

    //IntersectionObserver web API 객체 세팅
    const observer = new IntersectionObserver((entries) => {
        const entry = entries[0];

        if (entry.isIntersecting && !infinityParams.isLoading && !infinityParams.isEnd) {
            //실제 수행할 함수
            callback();

        }
        }, { rootMargin: '100px', }
    );

    return {infinityParams, observer};
}

/*

//logout 시 세션 날리기
function clearSessionStorage() {
    sessionStorage.clear();
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
*/