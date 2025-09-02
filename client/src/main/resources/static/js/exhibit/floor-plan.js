//변수
let pdfDoc = null;
let currentPage = 1;
let currentScale = 1.0;
let isLock = false;

//zoom in & out 변수
let minScale;
let maxScale;
const ZOOM_STEP = 1.2;
const ZOOM_RANGE = 2; //해당 숫자만큼 min, max 비율 범위가 조정

window.addEventListener('load', () => {
    //file 정보 조회
    $.ajax({
        type: "GET",
        url: `/api/v1/woc/floor-plan`,
        processData: false,
        dataType: "JSON",

        success: (res, textStatus, jqXHR) => {
            if (res.success) {
                const data = res.data;

                //1. file download uuid 세팅
                setDownloadFile('server', 'floorPlanDownload', {
                    uuid: data.uuid,
                    downloadName: '[World of Coffee Jakarta] Floor Plan'
                });

                //성능 개선 목적 workerSrc 세팅
                pdfjsLib.GlobalWorkerOptions.workerSrc = '/static/vendor/libs/pdf/pdf.worker.min.js';

                // PDF 문서 열기
                pdfjsLib.getDocument(data.path).promise.then(pdf => {
                    pdfDoc = pdf;
                    renderPage(currentPage, true);

                }).catch(err => {
                    console.error('Failed to load PDF:', err);
                });

            }
            else {
                alert('Failed to submit your inquiry. Please try again. If the issue persists, please contact our support team.');
            }
        },

        beforeSend: () => { loadingStart() },
        complete: () => { loadingExit() },
        error: (jqXHR, textStatus, errorThrown) => {},
    });

    //zoom-in 이벤트
    $('#zoomInBtn').on('click', () => {
        if (currentScale * ZOOM_STEP <= maxScale) {
            currentScale = currentScale * ZOOM_STEP;
            renderPage(currentPage, false);
        }
    });

    //zoom-out 이벤트
    $('#zoomOutBtn').on('click', () => {
        if (currentScale / ZOOM_STEP >= minScale) {
            currentScale = currentScale / ZOOM_STEP;
            renderPage(currentPage, false);
        }
    });

    //resize 이벤트
    let lastInnerWidth = window.innerWidth;
    window.addEventListener('resize', debounce(() => {
        const newWidth = window.innerWidth;

        if (pdfDoc && newWidth !== lastInnerWidth) {
            renderPage(currentPage, true);
        }
    }, 500));
});

/**
 * PDF 페이지 렌더링
 *
 * @param {number} pageNum - 렌더링할 페이지 번호
 * @param {boolean} autoScale - 브라우저 너비 기준으로 강제 scale 계산 여부
 */
function renderPage(pageNum, autoScale = false) {
    //렌더링 진행 중이면 함수 종료
    if (isLock) return;
    //상태값 변경
    isLock = true;
    loadingStart();

    const canvasWrapper = document.getElementById('canvasWrapper');
    const canvas = document.getElementById('floorPlanCanvas');
    const ctx = canvas.getContext('2d', { willReadFrequently: true });

    //현재 페이지 가져오기
    pdfDoc.getPage(pageNum).then(page => {
        if (autoScale) {
            //부모 요소 너비 기준으로 scale 계산
            currentScale = canvasWrapper.clientWidth / page.getViewport({ scale: 1.0 }).width;

            //zoom 요소 동적으로 비율 계산
            maxScale = ZOOM_RANGE * currentScale;
            minScale = currentScale;
        }
        //계산된 scale에 맞는 viewport 설정
        const viewport = page.getViewport({ scale: currentScale });

        //캔버스 실제 픽셀 사이즈 설정
        canvas.width = viewport.width;
        canvas.height = viewport.height;

        //브라우저 상에 보여지는 캔버스 스타일 사이즈 설정
        canvas.style.width = `${viewport.width}px`;
        canvas.style.height = `${viewport.height}px`;

        //렌더링 작업 실행
        const renderContext = {
            canvasContext: ctx,
            viewport: viewport
        };
        return page.render(renderContext).promise;

    }).then(() => {
        //렌더링 완료 시 상태 변경
        isLock = false;
        loadingExit();
    }).catch(error => {
        //오류 시에도 해제 보장
        isLock = false;
        console.error('PDF 렌더링 오류:', error);
    });
}