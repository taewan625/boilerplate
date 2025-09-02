
//TODO 개선: desc 내용도 같이 담아서 exhibition-list 뿌려주기 필요
const tempData = {
    '2024': 'World of Coffee is the essential event for coffee professionals, drawing a loyal audience from the global specialty coffee community. Check out the Exhibitor List from World of Coffee Busan 2024.',
    '2025': 'World of Coffee is the essential event for coffee professionals, drawing a loyal audience from the global specialty coffee community. Get ready to explore the world’s top coffee industry leaders coming together at World of Coffee Jakarta 2025!',
    '2026': 'World of Coffee is the essential event for coffee professionals, drawing a loyal audience from the global specialty coffee community. Check out the Exhibitor List from World of Coffee Bangkok 2026.',
    'default': 'World of Coffee is the essential event for coffee professionals, drawing a loyal audience from the global specialty coffee community.',
}

//검색 조건
const searchParams = {
    year: defaultParams.year,
    page: 1,
    searchText: '',

    reset() {
        this.year = defaultParams.year;
        this.page = 1;
        this.searchText = '';
    }
}

//observer 객체 생성
const { observer, infinityParams } = setObserver(() => {
    searchParams.page++;
    renderExhibitorList(searchParams);
});

//초기화 및 이벤트 바인딩
window.addEventListener('load', () => {
    //exhibition 정보 조회
    $.ajax({
        type: 'GET',
        url: `/api/v1/woc/exhibition/years`,
        processData: false,
        dataType: "JSON",

        success: (res, textStatus, jqXHR) => {
            if (res.success) {
                //년도별 list button HTML 생성
                res.data.exhibitionYears.forEach(function (year) {
                    const currentYearClass = (year === defaultParams.year) ? 'component__button--primary-dark' : 'component__button--primary-inherit';

                    const html = `
                                    <button type="button" class="component__button--primary-one gap-2 ${currentYearClass} js-evt__exhibitorYearButton" data-year="${year}">
                                        <span>${year}</span>
                                    </button>
                    `;

                    $('#exhibitionYears').append(html);
                });

                //Title 문구 세팅
                $('#exhibitionYear').text(defaultParams.year);
                //TODO 개선: desc 내용도 같이 담아서 exhibition-list 뿌려주기 필요

                $('#exhibitionDesc').text(tempData[defaultParams.year] || tempData['default']);
            }
        },

        beforeSend: () => {
            loadingStart()
        },
        complete: () => {
            loadingExit()
        },
        error: (jqXHR, textStatus, errorThrown) => {
        },
    });

    //observer 세팅
    observer.observe(document.getElementById('exhibitorsSentinel'));
    //exhibitor list 조회
    renderExhibitorList(searchParams);

    //년도 버튼 클릭 이벤트
    $('#exhibitionYears').off('click').on('click','.js-evt__exhibitorYearButton', (event) => {
        //검색 조건 초기화
        searchParams.reset();
        //년도 세팅
        searchParams.year = event.currentTarget.dataset.year;

        //무한 스크롤 상태값 초기화
        infinityParams.reset();

        //년도 버튼 css 변경
        $('.js-evt__exhibitorYearButton').removeClass('component__button--primary-dark').addClass('component__button--primary-inherit');
        $(event.currentTarget).removeClass('component__button--primary-inherit').addClass('component__button--primary-dark');

        //내용 변경
        $('#exhibitionYear').text(searchParams.year);
        $('#exhibitionDesc').text(tempData[searchParams.year] || tempData['default']);
        $('#searchText').val('');

        //기존 exhibitor list 비우기
        $('#exhibitorList').empty();

        //기존 exhibitor list 비우기
        renderExhibitorList(searchParams);
    });

    //검색 조건 이벤트
    $('#searchText').off('input').on('input', debounce((event) => {
        //페이징 초기화
        searchParams.page = 1;
        //검색어 세팅
        searchParams.searchText = event.target.value;

        //무한 스크롤 상태값 초기화
        infinityParams.reset();
        //스크롤 상태 변경
        //infinityParams.isScroll = true;

        //기존 exhibitor list 비우기
        $('#exhibitorList').empty();

        //기존 exhibitor list 비우기
        renderExhibitorList(searchParams);
    }, 300));

    //모달 객체 생성 혹은 재사용
    const modal = bootstrap.Modal.getOrCreateInstance(document.getElementById('exhibitorModal'));
    //모달 이벤트
    $('#exhibitorList').off('click').on('click', '.card', function (event) {
        //상세 조회
        $.ajax({
            type: 'GET',
            url: `/api/v1/woc/exhibitor/${event.currentTarget.dataset.id}`,
            processData: false,
            dataType: "JSON",

            success: (res, textStatus, jqXHR) => {
                if (res.success) {
                    const data = res.data;
                    $('#exhibitorImg').attr('src', data.path);
                    $('#companyName').text(data.companyName);
                    $('#brandName').text(data.brandName);
                    $('#boothNumber').text(data.boothNumber);
                    //모달 설명이 없는 경우 해당 영역 삭제 처리
                    data.introduction ? $('#introduction').text(data.introduction) : $('#introduction').remove();

                    //참가 기업 링크
                    [
                        { id: '#homepage', url: data.homepage },
                        { id: '#facebook', url: data.facebook },
                        { id: '#instagram', url: data.instagram },
                        { id: '#etcSns', url: data.etcSns },
                    ].forEach(({ id, url }) => {
                        if (url) {
                            const setUrl =  url.includes('http') ? url : `https:${url}`;
                            $(id).attr('href', setUrl).removeClass('d-none');

                        }
                        else {
                            $(id).attr('href', '#').addClass('d-none');
                        }
                    });

                    $('#exhibitorImg').off('error')
                        .on('error', () => { $('#exhibitorImg').hide();})
                        .on('load', () => {$('#exhibitorImg').show(); })
                        .attr('src', data.path);

                    modal.show();
                }
            },

            beforeSend: () => {loadingStart();},
            complete: () => {loadingExit();},
            error: (jqXHR, textStatus, errorThrown) => {//에러 처리
            },
        });
    });

});


/**
 * 년도별 참가 기업 데이터 조회 함수
 *
 * @function
 * @param {Object} searchParams - 조회 조건 객체
 * @param {number|string} searchParams.year - 조회할 연도 (예: 2025)
 * @param {number} [searchParams.page=1] - 현재 페이지 번호 (기본값: 1)
 * @param {string} [searchParams.searchText=''] - 검색어
 * @returns {void}
 */
function renderExhibitorList(searchParams) {
    if (infinityParams.isLoading || infinityParams.isEnd) return;
    infinityParams.isLoading = true;

    $.ajax({
        type: 'GET',
        url: `/api/v1/woc/exhibitor?year=${searchParams.year}&page=${searchParams.page}&searchText=${searchParams.searchText}`,
        processData: false,
        dataType: "JSON",

        success: (res, textStatus, jqXHR) => {
            if (res.success) {
                const data = res.data;
                const exhibitors = data.exhibitors;
                const pageable = data.pageable;

                //전체페이지와 현재페이지가 동일한 경우
                if (pageable.totalPages === pageable.page) {
                    infinityParams.isEnd = true;
                }

                //최초 다 숨기기(No data, Coming soon)
                $('#noData').addClass('d-none');
                $('#comingSoon').addClass('d-none');

                //데이터가 없는 경우
                if (pageable.totalElements === 0) {
                    //올해 이후: coming Soon 표출, 검색창 제거
                    if (defaultParams.year < searchParams.year) {
                        $('#comingSoon').removeClass('d-none');
                        $('#searchGroup').addClass('d-none');

                    }
                    //올해 및 이전년도: No data만 표출
                    else {
                        $('#noData').removeClass('d-none');
                    }
                }

                //데이터가 있는 경우
                if (0 < exhibitors.length) {
                    //검색창 표출
                    $('#searchGroup').removeClass('d-none');

                    let html = '';

                    exhibitors.forEach((item, index) => {
                        html += `
                            <div class="col">
                              <div class="card h-100" data-id="${item.id}">
                                <div class="d-flex p-4 component__card-img--middle">
                                  <img src=${item.path} class="img-fluid object-fit-contain" alt="..." draggable="false" loading="lazy" decoding="async" onerror="this.style.display='none';"/>
                                </div>
                                <div class="text-center text-truncate border-top px-3 py-md-2" data-bs-toggle="tooltip" data-bs-placement="top" title="${item.brandName}">
                                  <span class="component__desc-font--small">
                                    ${item.brandName}
                                  </span>
                                </div>
                              </div>
                            </div>
                            `;
                    });

                    $('#exhibitorList').append(html);

                    //툴팁 재초기화(있는 경우 재사용)
                    document.querySelectorAll('[data-bs-toggle="tooltip"]').forEach(el => {
                        bootstrap.Tooltip.getOrCreateInstance(el);
                    });
                }
            }
        },

        beforeSend: () => {
            loadingStart();
        },
        complete: () => {
            infinityParams.isLoading = false;
            loadingExit();
        },
        error: (jqXHR, textStatus, errorThrown) => {
            infinityParams.isLoading = false;
            // 에러 처리
        },
    });
}