
function submitForm() {
    // 유효성 검사
    const from = document.leaveForm;
    const holi_day = document.getElementById('holi_day').value;
    const start_day = document.getElementById('start_day').value;
    const end_day = document.getElementById('end_day').value;
    const leave_reason = document.getElementById('leave_reason').value;

    if (holi_day == "") {
        window.alert("휴가를 선택해 주세요");
        document.getElementById('holi_day').focus();
        return;
    }
    if (start_day == "") {
        window.alert("휴가 시작일을 선택해 주세요");
        document.getElementById('start_day').focus();
        return;
    }
    if (end_day == "") {
        window.alert("휴가 끝나는 일을 선택해 주세요");
        document.getElementById('end_day').focus();
        return;
    }
    if (leave_reason == "") {
        window.alert("휴가 사유를 입력해주세요");
        document.getElementById('leave_reason').focus();
        return;
    }

    window.alert("휴가 신청이 완료되었습니다.");
    from.submit(); // 서버 연동
};
