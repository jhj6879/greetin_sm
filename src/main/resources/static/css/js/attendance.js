document.addEventListener('DOMContentLoaded', function () {
    const submitAttendance = document.getElementById('submitAttendance');
    const submitHolidayRequest = document.getElementById('submitHolidayRequest');
    const historyMonth = document.getElementById('historyMonth');
    const approvalList = document.getElementById('approvalList');

    const attendanceRecords = [];
    const holidayRequests = [];

    submitAttendance.addEventListener('click', () => {
        const clockInTime = document.getElementById('clockInTime').value;
        const clockOutTime = document.getElementById('clockOutTime').value;
        const today = new Date().toISOString().split('T')[0];

        if (clockInTime && clockOutTime) {
            attendanceRecords.push({ date: today, clockInTime, clockOutTime });
            alert('출근/퇴근 시간이 저장되었습니다.');
        } else {
            alert('출근 시간과 퇴근 시간을 모두 입력해주세요.');
        }
    });

    submitHolidayRequest.addEventListener('click', () => {
        const holidayDate = document.getElementById('holidayDate').value;
        const holidayReason = document.getElementById('holidayReason').value;

        if (holidayDate && holidayReason) {
            holidayRequests.push({ date: holidayDate, reason: holidayReason, status: 'Pending' });
            alert('휴일 결재가 제출되었습니다.');
            renderApprovalList();
        } else {
            alert('휴일 날짜와 사유를 모두 입력해주세요.');
        }
    });

    historyMonth.addEventListener('input', () => {
        renderApprovalList();
    });

    function renderApprovalList() {
        const selectedMonth = historyMonth.value;
        approvalList.innerHTML = '';

        holidayRequests.forEach(request => {
            if (request.date.startsWith(selectedMonth)) {
                const div = document.createElement('div');
                div.className = 'approval-item';
                div.innerHTML = `
                    <p><strong>날짜:</strong> ${request.date}</p>
                    <p><strong>사유:</strong> ${request.reason}</p>
                    <p><strong>상태:</strong> ${request.status}</p>
                    <button class="btn btn-danger btn-sm cancel-request" data-date="${request.date}">취소</button>
                `;
                approvalList.appendChild(div);
            }
        });

        document.querySelectorAll('.cancel-request').forEach(button => {
            button.addEventListener('click', (event) => {
                const date = event.target.dataset.date;
                const index = holidayRequests.findIndex(req => req.date === date);
                if (index > -1) {
                    holidayRequests.splice(index, 1);
                    renderApprovalList();
                    alert('결재가 취소되었습니다.');
                }
            });
        });
    }
});
