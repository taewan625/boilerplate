const paymentDetailModule = (() => {
    const setPaymentDetail = () => {
        //새로고침 경고창
        window.addEventListener('beforeunload', function (e) {
            e.preventDefault();
            e.returnValue = '';
        });

        //이동을 통해서 접근할 경우
        const merchantUid = $('#merchantUid').text();

        if (merchantUid) {
            $.ajax({
                type: 'POST',
                url: `/api/v1/woc/payment/detail`,
                data: JSON.stringify({merchantUid: merchantUid}),
                enctype: 'multipart/form-data',
                contentType: 'application/json',
                processData: false,
                dataType: 'JSON',
                success: (response) => {
                    if (response.success) {
                        const { orderNo, orderDate, currency, amount, name, email, mobileNumber, ticketName, quantity, pricePerTicket } = response.data;

                        $('#orderNo').text(orderNo);
                        $('#orderDate').text(orderDate);
                        $('#amountCurrency').text(currency + ' ' + amount);
                        $('#name').text(name);
                        $('#email').text(email);
                        $('#mobileNumber').text(mobileNumber);
                        $('#ticketName').text(ticketName);
                        $('#quantity').text(quantity);
                        $('#pricePerTicket').text(pricePerTicket);
                        $('#totalAmount').text(quantity * pricePerTicket);
                    }

                },
                beforeSend: (xhr) => {
                    xhr.setRequestHeader(
                        document.querySelector('meta[name="_csrf_header"]').content,
                        document.querySelector('meta[name="_csrf"]').content
                    );
                    loadingStart()
                },
                complete: () => {
                    loadingExit()
                },
                error: (jqXHR, textStatus, errorThrown) => {
                    alert('Failed to show payment detail page. please contact our support team.')
                },
            });
        }
    };

    return {setPaymentDetail};
})();

(() => {
    paymentDetailModule.setPaymentDetail();
})();