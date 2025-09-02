//TODO 개선: 추후 일괄적으로 이미지를 서버에서 내려받아서 관리할 수 있도록 동적처리 필요 (예: 이미지를 내리는 core service에서 받을 수 있도록 처리)
const tempImgList = [
    { path: "https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/test-for-jakarta/uploadImage/exhibit/240501_WCB_07.%20Exhibitor_CHN-0075.webp"},
    { path: "https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/test-for-jakarta/uploadImage/exhibit/240501_WCB_07.%20Exhibitor_CHN-0371.webp"},
    { path: "https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/test-for-jakarta/uploadImage/exhibit/240501_WCB_07.%20Exhibitor_CHN-0416.webp"},
    { path: "https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/test-for-jakarta/uploadImage/exhibit/240502_WCB_07.%20Exhibitor_CHN-0222.webp"},
    { path: "https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/test-for-jakarta/uploadImage/exhibit/240501_WCB_07.%20Exhibitor_CHN-0007.webp"}];


//초기화 및 이벤트 바인딩
window.addEventListener('load', () => {
    renderCarouse(tempImgList, {id: 'carouselIndicators', imgClass: ''})

    //파일 다운로드
    const fileInfo = {
        url: 'https://kr.object.ncloudstorage.com/bucket-exporum-prod/woc/resource/bangkok/exhibitor/price-table.pdf',
        downloadName: 'World of Coffee Bangkok 2026_Price Table.pdf'
    };

    setDownloadFile('web', 'priceTableDownload', fileInfo);
});