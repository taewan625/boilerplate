const noticeDetailModule = (() => {
    const renderNoticeDetail = () => {
        $.ajax({
            type: 'GET',
            url: `/api/v1/woc/bbs/${document.getElementById('noticeDetail').dataset.id}`,
            dataType: "JSON",

            success: (res) => {
                if (res.success) {
                    const data = res.data;

                    //제목
                    $('#title').html(data.title);

                    //첨부파일 영역 숨김처리 여부
                    $('#attachFileElement').toggleClass('d-none', data.attachFiles.length <= 0);

                    //첨부파일 그리기 + 기능 추가
                    data.attachFiles.forEach((attachFile, index) => {
                        $('#attachFiles').empty();
                        $('#attachFiles').append(`
                                                      <a download href="#" class="notice-common-detail__attachment-btn" id="${'fileDownload'+index}">
                                                        <span class="fs-14px">WORLD OF COFFEE BANGKOK 2026_BOOTHT INFORMATION.PDF</span>
                                                        <svg class="flex-shrink-0" width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                                          <path d="M22 14.2003L22 20.3242C22 20.477 21.9042 20.6001 21.7851 20.6001H2.21484C2.09582 20.6001 2 20.477 2 20.3242V14.2003M12 3.17822L12 17.1782M12 17.1782L6.74117 11.8054M12 17.1782L17.2588 11.8054" stroke="currentColor" stroke-width="1" stroke-linecap="round" stroke-linejoin="round"></path>
                                                        </svg>
                                                      </a>
                        `);

                        //함수 세팅
                        const fileInfo = {
                            url: attachFile.path,
                            uuid: attachFile.uuid,
                            downloadName: attachFile.fileOriginalName,
                        };
                        setDownloadFile('server', 'fileDownload'+index, fileInfo);
                    })

                    //컨텐츠 그리기
                    $('#noticeDetail').html(data.content);

                    //href 세팅
                    if (data.bbsCode === 'NOTICE') {
                        $('#listButton').attr('href', '/notice');
                    }
                    else if (data.bbsCode === 'PRESS') {
                        $('#listButton').attr('href', '/press-center');
                    }
                }
            },

            beforeSend: () => loadingStart(),
            complete: () => loadingExit(),
            error: (jqXHR, textStatus, errorThrown) => {
                alert("Oops!\nWe couldn’t find what you were looking for.");
                window.location.href = `/${(window.location.pathname.split('/'))[1]}`;
            },
        });
    };

    return {
        renderNoticeDetail,
    };
})();


// 초기 실행
(() => {
    noticeDetailModule.renderNoticeDetail();
})();
