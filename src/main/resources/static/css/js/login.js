function login() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    // 로그인 로직 (예: 서버로부터 사용자 정보를 확인)
    // 여기서는 예시로 관리자를 'admin', 일반 사용자를 'user'로 구분
    if (username === 'admin' && password === 'admin123') {
        localStorage.setItem('userRole', 'admin');
        window.location.href = 'salary.html';
    } else if (username === 'user' && password === 'user123') {
        localStorage.setItem('userRole', 'user');
        window.location.href = 'salary.html';
    } else {
        alert('잘못된 사용자명 또는 비밀번호입니다.');
    }
}

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


