//검색 조건
const searchParams = {
    page: 1,
    searchText: '',

    reset() {
        this.page = 1;
        this.searchText = '';
    }
}

//observer 객체 생성
const {observer, infinityParams} = setObserver(() => {
    searchParams.page++;
    renderNewsletters(searchParams);
});


//초기화 및 이벤트 바인딩
window.addEventListener('load', () => {
    //observer 세팅
    observer.observe(document.getElementById('observerSentinel'));
    //newsletter list 조회
    renderNewsletters(searchParams);


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

        //기존 newsletters 비우기
        $('#newsletters').empty();

        //기존 Newsletter list 비우기
        renderNewsletters(searchParams);
    }, 300));

    //클릭 이벤트
    $('#newsletters .js-evt__newsletter').off('click').on('click', (event) => {

    });
});


/**
 * 년도별 참가 기업 데이터 조회 함수
 *
 * @function
 * @param {Object} searchParams - 조회 조건 객체
 * @param {number} [searchParams.page=1] - 현재 페이지 번호 (기본값: 1)
 * @param {string} [searchParams.searchText=''] - 검색어
 * @returns {void}
 */
function renderNewsletters(searchParams) {
    if (infinityParams.isLoading || infinityParams.isEnd) return;
    infinityParams.isLoading = true;

    $.ajax({
        type: 'GET',
        url: `/api/v1/woc/newsletter?page=${searchParams.page}&searchText=${searchParams.searchText}`,
        processData: false,
        dataType: "JSON",

        success: (res, textStatus, jqXHR) => {
            if (res.success) {
                const data = res.data;
                const newsletters = data.newsletters;
                const pageable = data.pageable;

                //전체페이지와 현재페이지가 동일한 경우
                if (pageable.totalPages === pageable.page) {
                    infinityParams.isEnd = true;
                }

                //최초 다 숨기기(No data, Coming soon)
                $('#noData').addClass('d-none');
                $('#comingSoon').addClass('d-none');

                //검색 결과가 없는 경우
                if (pageable.totalElements === 0 && pageable.searchText) {
                    $('#noData').removeClass('d-none');

                }
                //데이터가 없는 경우
                else if (pageable.totalElements === 0) {
                    $('#comingSoon').removeClass('d-none');
                    $('#searchGroup').addClass('d-none');
                }

                //데이터가 있는 경우
                if (0 < newsletters.length) {
                    //검색창 표출
                    $('#searchGroup').removeClass('d-none');

                    let html = '';

                    newsletters.forEach((item, index) => {
                        html += `
                                <div class="col js-evt__newsletter">
                                  <a href="/newsletter/${item.id}" class="card h-100 text-decoration-none">
                                    <div class="d-flex p-4 component__card-img--middle">
                                      <img
                                          src="${item.logoFilePath || ''}"
                                          class="img-fluid object-fit-contain" alt="..." draggable="false" loading="lazy" decoding="async"
                                          onerror="this.onerror=null; this.src='https://kr.object.ncloudstorage.com/bucket-exporum-prod/woc/resource/bangkok/logo/bangkok-horizontal.webp';">
                                    </div>
                                    <div class="fw-semibold text-truncate border-top px-3 py-md-2" data-bs-toggle="tooltip" data-bs-placement="top" title="${item.title}">
                                      <span class="component__desc-font--middle">
                                         ${item.title}
                                      </span>
                                    </div>
                                    
                                    <div class="text-start text-truncate px-3 py-md-2">
                                      <span class="component__desc-font--small">
                                        ${transformDateFormat(item.issueDate)}
                                      </span>
                                    </div>
                                  </a>
                                </div>`;
                    });

                    $('#newsletters').append(html);

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

const transformDateFormat = (date) => date.split(' ')[0].replace(/-/g, '.');