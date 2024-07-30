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
