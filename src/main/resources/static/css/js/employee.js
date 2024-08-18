$(document).ready(function() {
    $('#searchForm').submit(function(event) {
        event.preventDefault();  // 폼 제출 기본 동작 방지

        var keyword = $('#keyword').val();
        $.ajax({
            url: '/employee/search',
            method: 'GET',
            data: { keyword: keyword },
            success: function(data) {
                // 검색 결과를 HTML에 반영
                $('#employeeList').html($(data).find('#employeeList').html());
            },
            error: function(err) {
                console.error('검색 중 오류가 발생했습니다.', err);
            }
        });
    });

    // 사원 목록의 각 행을 클릭할 때 동작
    $('#employeeList').on('click', 'tr', function() {
        var employeeId = $(this).find('td:first').text();
        $.ajax({
            url: '/employee/' + employeeId,
            method: 'GET',
            success: function(data) {
                $('#employeeDetails').html(data);
            },
            error: function(err) {
                console.error('사원 정보를 불러오는 중 오류가 발생했습니다.', err);
            }
        });
    });
});
