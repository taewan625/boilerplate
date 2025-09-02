//TODO 개선: 추후 일괄적으로 이미지를 서버에서 내려받아서 관리할 수 있도록 동적처리 필요 (예: 이미지를 내리는 core service에서 받을 수 있도록 처리)
const tempImgList = [
    {path: "https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/test-for-jakarta/producer-village/GettyImages-2187386714.jpg",},
    {path: "https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/test-for-jakarta/producer-village/GettyImages-2194540761.jpg",},
    {path: "https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/test-for-jakarta/producer-village/GettyImages-a13839636%20%281%29.jpg",},
    {path: "https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/test-for-jakarta/producer-village/GettyImages-a13850754.jpg"},
];

//모듈 초기화
(() => {
    renderCarouse(tempImgList, {id: 'carouselIndicators', imgClass: ''})
})();