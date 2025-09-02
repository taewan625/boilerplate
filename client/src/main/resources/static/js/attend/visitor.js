const visitorModule = (() => {
    const buttonActive = () => {
        $.ajax({
            type: "GET",
            url: `/api/v1/woc/sales-tickets`,
            success: (res, textStatus, jqXHR) =>{
                if (res.success) {
                    const ticketCount = res.data.length;
                    if (0 < ticketCount) {
                        $('#cardPurchase').prop('disabled', false).toggleClass('component__button--disabled', false);
                        $('#qrPurchase').prop('disabled', false).toggleClass('component__button--disabled', false);

                        //card 버튼 이벤트 추가
                        $('#cardPurchase').off('click').on('click', () => {
                            window.location.href = '/payment';
                        });

                        //QR 버튼 이벤트 추가
                        $('#qrPurchase').off('click').on('click', () => {
                            const paymentURL = 'https://os.dyandratiket.com/buy-now/world-of-coffee-jakarta-2025';
                            window.open(paymentURL, '_blank');
                        });
                    }
                }
            },
            beforeSend: () =>{loadingStart()} ,
            complete:() =>{loadingExit()},
            error: (jqXHR, textStatus, errorThrown) => {},
        });
    };

    return {buttonActive}
})();


//모듈 초기화
(() => {
    visitorModule.buttonActive();
})();