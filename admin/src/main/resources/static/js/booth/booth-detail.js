window.addEventListener('load', () => {
    //pk 추출
    const boothDetailId = getBoothDetailId();

    //데이터 조회 및 세팅
    getBoothDetail(boothDetailId);

    //체크 이벤트 세팅
    $('#btn-check').click(()=>{
        if(confirm("확인 완료하시겠습니까?")){
            updateCheck(boothDetailId);
        }
    });

    //뒤로가기 버튼
    $("#btn-pre").click(() => {
        window.location.href = '/booth-request';
    });

    //로딩바 제거
    loadingExit();
});

/**
 * pk 조회 함수
 */
const getBoothDetailId = () => {
    const path = window.location.pathname;
    const match = path.match(/\/booth-request\/(\d+)/);
    const id = match ? match[1] : null;
    return id;
}

/**
 * 데이터 조회 함수
 * @params boothDetailId: pk
 */
const getBoothDetail = (boothDetailId) => {
    $.ajax({
        type: "GET",
        url: `/api/v1/admin/booth-request/${boothDetailId}`,

        //성공
        success: (res, textStatus, jqXHR) =>{
            //응답 성공
            if(res.success){
                Object.keys(res.data).forEach((key) => {
                    //unescape 처리
                    if (typeof res.data[key] === 'string') {
                        res.data[key] = unescapeHtml(res.data[key]);
                    }

                    //동적 데이터 세팅
                    switch (key) {
                        //체크 버튼(true인 경우 disable 처리)
                        case 'check':
                            //체크된 경우
                            if(res.data[key]){
                                $('#btn-check').prop('disabled', true);
                                $(`#company-header`).html(`<span class="badge bg-label-success ">Checked</span>`);
                            }
                            //아닌 경우
                            else{
                                $(`#company-header`).html(`<span class="badge bg-label-secondary ">UNCHECKED</span>`);
                            }
                            break;

                        //url
                        case 'homepage':
                        case 'instagram':
                        case 'facebook':
                        case 'etcSns':
                            const content = `${res.data[key]} ${getUrl(res.data[key])}`;
                            if(content.startsWith('null') || !content.trim()){
                                $(`#${key}`).html('-');
                            }else{
                                $(`#${key}`).html(content);
                            }
                            break;

                        //그 외
                        default:
                            $(`#${key}`).val(res.data[key]);
                    }
                });
            }
        },

        //로딩바 동작
        beforeSend: () =>{loadingStart()} ,
        complete:() =>{loadingExit()},

        //실패
        error: (jqXHR, textStatus, errorThrown) => {},

        //동기 요청
        async: false
    });
}

/**
 * url link 세팅 함수
 * @params url: url
 */
const getUrl = (url) => {
    //falsy 값인 경우
    if (!url) {
        return '';
    }

    //protocal이 없는 경우
    if (!url.startsWith('http://') && !url.startsWith('https://')) {
        return  `<a class="btn-outline-primary border-0" href="https://${url}" target="_blank" rel="noopener noreferrer"><i class='bx bx-link-external'></i> </a>`;
    }

    //protocal이 있는 경우
    return `<a class="btn-outline-primary border-0" href="${url}" target="_blank" rel="noopener noreferrer"><i class='bx bx-link-external'></i> </a>`;
}

/**
 * check 상태 변경 함수
 * @params id: pk
 */
const updateCheck =(id) =>{
    $.ajax({
        type: "PUT",
        url: `/api/v1/admin/booth-request/${id}`,
        contentType: "application/json",
        dataType: "JSON",
        success: (res, textStatus, jqXHR) =>{
            if(res.success) {
                toastr.success('',"정상적으로 처리되었습니다.",{timeOut:1500})
                //데이터 리로드
                getBoothDetail(id);
            }else{
                toastr.error('',"실패 하였습니다.",{timeOut:1500})
            }
        },
        beforeSend: () =>{loadingStart()} ,
        complete:() =>{loadingExit()},
        error: (jqXHR, textStatus, errorThrown) => {},
        async: false
    });
}