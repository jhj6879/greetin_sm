$(document).ready(function() {
    // 사원 목록의 각 행을 클릭할 때 동작
    $('#employeeSalaryList').on('click', 'tr.employee-row', function() {
        var employeeId = $(this).data('employee-id');  // 클릭된 사원의 ID를 가져옴

        // 현재 URL의 쿼리스트링에서 month와 year 값을 추출
        var urlParams = new URLSearchParams(window.location.search);
        var month = urlParams.get('month');  // 현재 페이지에서 조회 중인 month 값
        var year = urlParams.get('year');    // 현재 페이지에서 조회 중인 year 값

        // Ajax 요청을 통해 서버로부터 급여 명세서 정보를 받아옴
        $.ajax({
            url: '/salary/' + employeeId,  // 사원 ID에 맞는 URL
            method: 'GET',
            data: { year: year, month: month },  // 현재 페이지에서 조회 중인 연도와 달을 서버로 전달
            success: function(data) {
                // 서버에서 받은 명세서를 해당 컨테이너에 삽입
                $('#salary-details-container').html(data);
            },
            error: function(err) {
                console.error('급여 정보를 불러오는 중 오류가 발생했습니다.', err);
            }
        });
    });
});
