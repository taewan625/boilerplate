/**
 * 로그인 js
 */

window.addEventListener('load', () => {
    const username = getCookie("username");
    if (username !== '') {
        $("#email").val(username);
        $("#remember-me").attr("checked", true);
        $("#password").focus();
    }

    const params = new URLSearchParams(window.location.search);
    if (params.get("error") === 'unauthorized') {
        alert("아이디나 패스워드를 확인해주세요.");
    }

    setValid();
});

const setValid = () => {
    $("#formAuthentication").validate({
        // 유효성 검사 규칙
        rules: {
            username: { required: true },
            password: { required: true }
        },
        // 오류값 발생시 출력할 메시지 수동 지정
        messages: {
            username: { required: '이메일을 입력해주세요.' },
            password: { required: '패스워드를 입력해주세요.' }
        }
    });
};

// 쿠키를 가져오는 함수
const getCookie = (key) => {
    key = key + "=";
    let cookieData = document.cookie;
    let firstCookie = cookieData.indexOf(key);
    let cookieValue = "";

    if (firstCookie !== -1) {
        firstCookie += key.length;
        let endCookie = cookieData.indexOf(";", firstCookie);
        if (endCookie === -1) {
            endCookie = cookieData.length;
        }
        cookieValue = cookieData.substring(firstCookie, endCookie);
    }

    return decodeURIComponent(cookieValue); // `unescape()` 대신 사용
}