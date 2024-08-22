function checkFormLogin() {
    const form = document.login;
    const username = document.getElementById('user_id');
    const user_pw = document.getElementById('user_pw');

    if (username.value.length < 3 || username.value.length > 8) {
        window.alert("아이디는 3~8자로 입력해주세요.");
        username.focus();
        return;
    }
    if (user_pw.value.length < 4 || user_pw.value.length > 20) {
        window.alert("패스워드는 4~20자로 입력해주세요.");
        user_pw.focus();
        return;
    }

    window.alert("로그인이 완료되었습니다.");
    form.submit();
}


