document.addEventListener('DOMContentLoaded', function () {
    const userRole = localStorage.getItem('userRole');

    const userSalary = document.getElementById('userSalary');
    const adminSalary = document.getElementById('adminSalary');

    const employeeList = document.getElementById('employeeList');
    const salaryDetails = document.getElementById('salaryDetails');

    const employees = [
        { id: 1, name: '김철수', monthlySalary: 3000000 },
        { id: 2, name: '이영희', monthlySalary: 3200000 },
        // 더 많은 사원 데이터를 추가할 수 있습니다.
    ];

    if (userRole === 'admin') {
        adminSalary.style.display = 'block';
        displayEmployeeList(employees);
    } else if (userRole === 'user') {
        userSalary.style.display = 'block';
        const user = employees.find(emp => emp.name === '김철수'); // 사용자 이름으로 검색 (예시)
        document.getElementById('monthlySalary').textContent = user.monthlySalary + '원';
        document.getElementById('annualSalary').textContent = user.monthlySalary * 12 + '원';
    } else {
        alert('잘못된 접근입니다. 다시 로그인해주세요.');
        window.location.href = 'login.html';
    }

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
            salaryDetails.innerHTML = `
                <h3>${employee.name}</h3>
                <p><strong>월급:</strong> ${employee.monthlySalary}원</p>
                <form>
                    <div class="mb-3">
                        <label for="newMonthlySalary" class="form-label">새 월급</label>
                        <input type="number" id="newMonthlySalary" class="form-control" value="${employee.monthlySalary}">
                    </div>
                    <button type="button" class="btn btn-primary" onclick="updateSalary(${employee.id})">업데이트</button>
                </form>
            `;
        }
    }

    window.updateSalary = function(employeeId) {
        const newMonthlySalary = document.getElementById('newMonthlySalary').value;
        const employee = employees.find(emp => emp.id === employeeId);
        if (employee) {
            employee.monthlySalary = newMonthlySalary;
            alert('월급이 업데이트되었습니다.');
            displayEmployeeDetails(employeeId);
        }
    }
});
