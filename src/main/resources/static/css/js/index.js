function formatDateToMysql(datetime) {
    const date = new Date(datetime);
    const year = date.getFullYear();
    const month = ('0' + (date.getMonth() + 1)).slice(-2);
    const day = ('0' + date.getDate()).slice(-2);
    const hours = ('0' + date.getHours()).slice(-2);
    const minutes = ('0' + date.getMinutes()).slice(-2);
    const seconds = ('0' + date.getSeconds()).slice(-2);
    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}

function recordTime(action) {
    const currentTime = formatDateToMysql(new Date());
    console.log("Recording time for action:", action, "at", currentTime);

    fetch('/record-time', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ action: action, time: currentTime })
    })
    .then(response => response.json())
    .then(data => {
        if (action === 'clockIn') {
            document.getElementById('clockInTime').innerText = currentTime;
        } else if (action === 'clockOut') {
            document.getElementById('clockOutTime').innerText = currentTime;
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}









