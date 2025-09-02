window.addEventListener('load', () => {
    const swiper = new Swiper('#wordRolling', {
        //슬라이드를 무한 반복되도록 설정 (마지막 슬라이드 → 첫 슬라이드로 자연스럽게 순환)
        loop: true,
        //슬라이드가 이동하는 애니메이션 속도 (ms 단위) → 8000ms = 8초 동안 이동
        speed: 8000,

        autoplay: {
            //자동 재생 사이 간격 (0이면 끊김 없이 즉시 다음 슬라이드로 이동)
            delay: 0,
            //사용자 조작(스와이프 등) 이후에도 자동 재생 유지
            disableOnInteraction: false,
            //마우스 올려도 멈추지 않음
            pauseOnMouseEnter: false
        },

        //화면에 보여지는 슬라이드 개수를 자동으로 설정 (슬라이드 길이에 따라 유동적으로 배치)
        slidesPerView: 'auto',
        //슬라이드가 자유롭게 움직이도록 설정 (스냅되지 않고 자연스럽게 이동)
        freeMode: true,
        //슬라이드를 멈췄을 때 관성(momentum) 없이 즉시 정지
        freeModeMomentum: false,

        //터치 조작 비활성화
        allowTouchMove: false,
        //마우스 드래그 비활성화
        simulateTouch: false,
    });

    //하루 안보기 쿠키 여부 분기 처리
    if (getCookie('noShowToday') === 'true') {
        $('#homePopup').remove();

    }
    else {
        $.ajax({
            type: 'GET',
            url: `/api/v1/woc/popup`,
            processData: false,
            dataType: 'JSON',

            success: (res, textStatus, jqXHR) => {
                if (res.success) {
                    if (res.data.length > 0) {
                        renderCarouse(res.data, {id: "popupCarouselIndicators", imgClass: 'home__popup-img', moveBtnClass: 'd-none'});

                        $('#homePopup').removeClass('d-none');
                    }
                }
            },

            beforeSend: () => {loadingStart();},
            complete: () => {loadingExit();},
            error: (jqXHR, textStatus, errorThrown) => {
                //에러 처리
            },
        });
    }



    //팝업 닫기 버튼 이벤트
    $('#homePopupClose').off('click').on('click', (e) => {
        if ($('#noShowOneDay').is(':checked')) {
            setCookieUntilMidnight('noShowToday', 'true');
        }

        $('#homePopup').remove();
    });
});


/**
 * 쿠키 생성 (다음날 자정까지 유효)
 * @param {string} name - 쿠키 이름
 * @param {string} value - 쿠키 값
 */
function setCookieUntilMidnight(name, value) {
    const now = new Date();
    const expire = new Date();
    expire.setDate(now.getDate() + 1);
    expire.setHours(0, 0, 0, 0); // 다음날 00:00:00로 설정

    document.cookie = `${name}=${value}; path=/; expires=${expire.toUTCString()}`;
}

/**
 * 쿠키 조회
 */
function getCookie(name) {
    const cookies = document.cookie.split(';');
    for (const cookie of cookies) {
        const [k, v] = cookie.trim().split('=');
        if (k === name) return v;
    }
    return null;
}