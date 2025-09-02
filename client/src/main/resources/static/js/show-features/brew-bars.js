//TODO 개선: 추후 일괄적으로 이미지를 서버에서 내려받아서 관리할 수 있도록 동적처리 필요 (예: 이미지를 내리는 core service에서 받을 수 있도록 처리)
const tempImgList = [
    {path: "https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/test-for-jakarta/producer-village/Experience%20bar%20%26%20Brew%20Bar%20%2810%29.jpg",},
    {path: "https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/test-for-jakarta/producer-village/240501_WCB_04.%20Experience%20bar%20%26%20Brew%20Bar_CHN-0111.jpg",},
    {path: "https://kr.object.ncloudstorage.com/bucket-exporum-dev/woc/test-for-jakarta/producer-village/Experience%20bar%20%26%20Brew%20Bar%20%2818%29.jpg"},
];

//모듈 초기화
(() => {
    renderCarouse(tempImgList, {id: 'carouselIndicators', imgClass: ''})
})();