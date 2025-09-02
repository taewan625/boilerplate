const newsletterDetailModule = (() => {
    const initRefererButton = () => {
        const referrerUrl = document.referrer;
        const lastPath = referrerUrl.split('/').pop();

        const refererButton = $("#refererButton");
        if (lastPath === 'subscribe') {
            refererButton.attr('href', '/newsletter/subscribe');
            refererButton.text('SUBSCRIBE TO NEWSLETTER');
        }
        else {
            refererButton.attr('href', '/newsletter');
            refererButton.text('LIST');
        }
    }

    const renderNewsletterDetail = () => {
        $.ajax({
            type: 'GET',
            url: `/api/v1/woc/newsletter/${document.getElementById('newsletterDetail').dataset.id}`,
            dataType: "JSON",

            success: (res) => {
                if (res.success) {
                    const data = res.data;
                    console.log(data);

                    //제목
                    $('#title').html(data.title);

                    //컨텐츠 그리기
                    $('#content').html(data.content);
                }
            },

            beforeSend: () => loadingStart(),
            complete: () => loadingExit(),
            error: (jqXHR, textStatus, errorThrown) => {
                alert("Oops!\nWe couldn’t find what you were looking for.");
                window.location.href = `/newsletter`;
            },
        });
    };

    return {
        renderNewsletterDetail, initRefererButton
    };
})();


// 초기 실행
(() => {
    newsletterDetailModule.renderNewsletterDetail();
    newsletterDetailModule.initRefererButton();
})();
