function editDepartment(departmentCode, departmentName) {
    // 부서 수정 폼에 기존 부서 정보 설정
    document.getElementById('editDepartment').value = departmentCode;
    document.getElementById('editDepartmentName').value = departmentName;
    // 수정 폼 표시
    document.getElementById('editForm').style.display = 'block';
}