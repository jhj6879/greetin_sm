function openTab(event, tabName) {
    const tabButtons = document.querySelectorAll('.tab-button');
    const tabContents = document.querySelectorAll('.tab-content');

    tabButtons.forEach(button => button.classList.remove('active'));
    tabContents.forEach(content => content.classList.remove('active'));

    event.currentTarget.classList.add('active');
    document.getElementById(tabName).classList.add('active');
}

document.getElementById('searchButton').addEventListener('click', function() {
    const workDate = document.getElementById('workDate').value;
    console.log("Selected work date:", workDate);
    // 여기에 검색 로직을 추가하세요.
});