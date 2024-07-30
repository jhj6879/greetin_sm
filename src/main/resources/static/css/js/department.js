document.addEventListener('DOMContentLoaded', function () {
    const departments = ['개발팀', '마케팅팀', '영업팀', '인사팀'];

    const departmentSearch = document.getElementById('departmentSearch');
    const departmentList = document.getElementById('departmentList');

    function displayDepartmentList(departments) {
        departmentList.innerHTML = '';
        departments.forEach(department => {
            const div = document.createElement('div');
            div.className = 'department-item';
            div.textContent = department;
            departmentList.appendChild(div);
        });
    }

    departmentSearch.addEventListener('input', () => {
        const searchValue = departmentSearch.value.toLowerCase();
        const filteredDepartments = departments.filter(dep => dep.toLowerCase().includes(searchValue));
        displayDepartmentList(filteredDepartments);
    });

    // 초기 부서 목록 표시
    displayDepartmentList(departments);
});
