//모듈 정의
const showGuideModule = (() => {
    const renderEbook = () => {
        //로딩 시작
        loadingStart();

        //iframe 동적 생성
        const iframe = document.createElement('iframe');
        iframe.id = 'ebook';
        iframe.className = 'w-100 h-100';
        iframe.style.visibility = 'hidden';
        iframe.allowFullscreen = true;
        iframe.allow = 'clipboard-write';
        iframe.sandbox = 'allow-top-navigation allow-top-navigation-by-user-activation allow-downloads allow-scripts allow-same-origin allow-popups allow-modals allow-popups-to-escape-sandbox allow-forms';
        iframe.src = 'https://e.issuu.com/embed.html?backgroundColor=%23fff&d=wocshowguide&hideIssuuLogo=true&pageLayout=singlePage&themeMainColor=%23fff&themeSecondaryColor=%23F05B2A&u=seoulcafeshow';

        iframe.addEventListener('load', () => {
            iframe.style.visibility = 'visible';
            loadingExit();
        });

        const container = document.getElementById('ebookContainer');
        container.appendChild(iframe);
    };

    return {renderEbook};

})();

//모듈 초기화
(() => {
    showGuideModule.renderEbook();
})();