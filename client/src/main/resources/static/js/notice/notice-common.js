
const noticeModule = (() => {
    const noticeList = (page) => {
        const path = window.location.pathname;
        const bbsType = (path === '/notice') ? 'NOTICE' : 'PRESS';
        const noticeTitle = (path === '/notice') ? 'OFFICIAL NOTICES' : 'PRESS CENTER UPDATES';

        //title 세팅
        $('#noticeTitle').text(noticeTitle);

        $.ajax({
            type: 'GET',
            url: `/api/v1/woc/bbs?page=${page}&pageSize=10&bbsType=${bbsType}`,
            dataType: "JSON",

            success: (res) => {
                if (res.success) {
                    const data = res.data;
                    const pageable = data.pageable;

                    renderNoticeList(data.bbsList);
                    renderPagination(pageable);
                }
            },

            beforeSend: () => loadingStart(),
            complete: () => loadingExit(),
            error: (jqXHR, textStatus, errorThrown) => {},
        });
    };

    //게시글 render
    const renderNoticeList = (list) => {
        const path = window.location.pathname;
        let HTML = '';

        list.forEach((object) => {
            HTML += `<li class="list-group-item px-0 py-3 py-md-4">
                        <a href="${path}/${object.id}" class="d-flex flex-column flex-md-row justify-content-between text-decoration-none text-dark gap-3">
                          <span class="component__desc-font--middle">${object.title}</span>
                          <span class="component__desc-font--small">${object.createdAt.split(' ')[0].replace(/-/g, '.')}</span>
                        </a>
                      </li>`;
        });

        $('#noticeList').html(HTML);
    };

    //페이징 render
    const renderPagination = ({ page, totalPages }) => {
        let pageHTML = '';

        //이전 버튼
        pageHTML += `<a href="#" class="${(page <= 1) ? 'cursor-default page-disable' : ''} js-evt__pageClick" data-page="${page-1}">
                       <svg width="44" height="44" viewBox="0 0 24 24" fill="none">
                         <path d="M15 6L9 12L15 18" stroke="#adb5bd" stroke-linecap="round" stroke-linejoin="round"></path>
                       </svg>
                      </a>
                    `;

        //페이지
        for (let i = 1; i <= totalPages; i++) {
            const active = i === page ? 'active' : '';
            pageHTML += `<a href="#" class="component__page-button ${active} js-evt__pageClick" data-page="${i}">${i}</a>`;
        }

        //다음 버튼
        pageHTML += `<a href="#" class="${(totalPages <= page) ? 'cursor-default page-disable' : ''} js-evt__pageClick" data-page="${page+1}">
                       <svg width="44" height="44" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                         <path d="M9 6L15 12L9 18" stroke="#adb5bd" stroke-linecap="round" stroke-linejoin="round"/>
                       </svg>
                     </a>
                    `;

        $('#pagination').html(pageHTML);

        //페이지 클릭 이벤트
        $('.js-evt__pageClick').off('click').on('click', (e) => {
            if (e.currentTarget.classList.contains('page-disable')) {
                e.preventDefault();
                return;
            }

            //페이지 리로드
            noticeList(e.currentTarget.dataset.page);
        })
    };


    return {
        noticeList,
    };
})();

// 초기 실행
(() => {
    noticeModule.noticeList(1);
})();
