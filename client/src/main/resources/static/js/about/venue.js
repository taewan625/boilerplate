//모듈 정의
const venueModule = (() => {
    const renderMap = () => {
        //로딩 시작
        loadingStart();

        //iframe 동적 생성
        const iframe = document.createElement('iframe');
        iframe.id = 'ebook';
        iframe.className = 'w-100 h-100';
        iframe.src = 'https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d1983.1869967464336!2d106.807206!3d-6.214309000000001!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x2e69f6ada54c7649%3A0x8d7ed0bd65c0a14f!2sJHCC%20Assembly!5e0!3m2!1sen!2sus!4v1750234301942!5m2!1sen!2sus';
        iframe.style.border = '0';
        iframe.style.visibility = 'hidden';
        iframe.loading = 'lazy';
        iframe.allowFullscreen = true;
        iframe.referrerPolicy = 'no-referrer-when-downgrade';
        iframe.sandbox = 'allow-same-origin allow-scripts allow-popups allow-forms';

        iframe.addEventListener('load', () => {
            iframe.style.visibility = 'visible';
            loadingExit();
        });

        const container = document.getElementById('mapContainer');
        container.appendChild(iframe);
    };

    return {renderMap};

})();

//모듈 초기화
(() => {
    venueModule.renderMap();
})();