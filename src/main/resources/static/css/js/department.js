function editDepartment(button) {
    // data-* 속성에서 값을 가져옴
    var departmentCode = button.getAttribute('data-department-code');
    var departmentName = button.getAttribute('data-department-name');

    // 부서 수정 폼에 값을 설정
    document.getElementById('editDepartment').value = departmentCode;
    document.getElementById('editDepartmentName').value = departmentName;

    // 수정 폼을 표시
    document.getElementById('editForm').style.display = 'block';
}

// 폼 제출 전에 데이터가 있는지 확인하는 로직
document.getElementById('updateForm').onsubmit = function() {
    const department = document.getElementById('editDepartment').value;
    const departmentName = document.getElementById('editDepartmentName').value;

    // 콘솔에 값을 찍어서 확인
    console.log("Submitting form: ", department, departmentName);

    // 데이터가 없으면 경고창 표시
    if (!department || !departmentName) {
        alert("부서 코드와 부서 이름을 모두 입력해주세요.");
        return false;
    }

    return true;
};
