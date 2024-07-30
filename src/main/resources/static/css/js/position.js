document.addEventListener('DOMContentLoaded', function () {
    const positions = ['팀장', '사원', '매니저', '디렉터'];

    const positionSearch = document.getElementById('positionSearch');
    const positionList = document.getElementById('positionList');

    function displayPositionList(positions) {
        positionList.innerHTML = '';
        positions.forEach(position => {
            const div = document.createElement('div');
            div.className = 'position-item';
            div.textContent = position;
            positionList.appendChild(div);
        });
    }

    positionSearch.addEventListener('input', () => {
        const searchValue = positionSearch.value.toLowerCase();
        const filteredPositions = positions.filter(pos => pos.toLowerCase().includes(searchValue));
        displayPositionList(filteredPositions);
    });

    // 초기 직책 목록 표시
    displayPositionList(positions);
});
