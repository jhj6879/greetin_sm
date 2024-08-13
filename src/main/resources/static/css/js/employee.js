$(document).ready(function() {
    $('#employeeList tr').click(function() {
        var employeeId = $(this).find('td:first').text();
        $.ajax({
            url: '/employee/' + employeeId,
            method: 'GET',
            success: function(data) {
                $('#employeeDetails').html(data);
            },
            error: function(err) {
                console.error(err);
            }
        });
    });
});

document.addEventListener('DOMContentLoaded', function () {
//    const employees = [
//        { id: 1, name: '김철수', department: '개발팀', position: '팀장', email: 'chulsoo@example.com', phone: '010-1234-5678' },
//        { id: 2, name: '이영희', department: '마케팅팀', position: '사원', email: 'younghee@example.com', phone: '010-9876-5432' },
//        // 더 많은 사원 데이터를 추가할 수 있습니다.
//    ];

    const employees = document.getElementById('employeeList');
    const employeeDetails = document.getElementById('employeeDetails');
    const employeeSearch = document.getElementById('employeeSearch');

    function displayEmployeeList(employees) {
        employeeList.innerHTML = '';
        employees.forEach(employee => {
            const li = document.createElement('li');
            li.className = 'list-group-item';
            li.textContent = employee.name;
            li.dataset.id = employee.id;
            li.addEventListener('click', () => displayEmployeeDetails(employee.id));
            employeeList.appendChild(li);
        });
    }

    function displayEmployeeDetails(employeeId) {
        const employee = employees.find(emp => emp.id === employeeId);
        if (employee) {
            employeeDetails.innerHTML = `
                <h3>${employee.name}</h3>
                <p><strong>부서:</strong> ${employee.department}</p>
                <p><strong>직급:</strong> ${employee.position}</p>
                <p><strong>이메일:</strong> ${employee.email}</p>
                <p><strong>전화번호:</strong> ${employee.phone}</p>
            `;
        }
    }

    employeeSearch.addEventListener('input', () => {
        const searchValue = employeeSearch.value.toLowerCase();
        const filteredEmployees = employees.filter(emp => emp.name.toLowerCase().includes(searchValue));
        displayEmployeeList(filteredEmployees);
    });


});

function upEmployee() {
    const form = document.getElementById('employeeDetailsForm');

    if (form) {
        window.alert("수정이 완료되었습니다.");
        form.submit(); // 서버 연동
    } else {
        window.alert("폼을 찾을 수 없습니다.");
    }
}