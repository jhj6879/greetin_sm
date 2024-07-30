document.addEventListener('DOMContentLoaded', function () {
    const calendarElement = document.getElementById('calendar');
    const today = new Date();
    let currentMonth = today.getMonth();
    let currentYear = today.getFullYear();

    const months = [
        'January', 'February', 'March', 'April', 'May', 'June',
        'July', 'August', 'September', 'October', 'November', 'December'
    ];

    function renderCalendar(month, year) {
        calendarElement.innerHTML = '';

        const firstDay = new Date(year, month).getDay();
        const daysInMonth = 32 - new Date(year, month, 32).getDate();

        const calendarTable = document.createElement('table');
        const headerRow = document.createElement('tr');
        const monthYearHeader = document.createElement('th');
        monthYearHeader.colSpan = 7;
        monthYearHeader.textContent = `${months[month]} ${year}`;
        headerRow.appendChild(monthYearHeader);
        calendarTable.appendChild(headerRow);

        const daysOfWeekRow = document.createElement('tr');
        const daysOfWeek = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
        for (const day of daysOfWeek) {
            const dayHeader = document.createElement('th');
            dayHeader.textContent = day;
            daysOfWeekRow.appendChild(dayHeader);
        }
        calendarTable.appendChild(daysOfWeekRow);

        let date = 1;
        for (let i = 0; i < 6; i++) {
            const weekRow = document.createElement('tr');
            for (let j = 0; j < 7; j++) {
                const dayCell = document.createElement('td');
                if (i === 0 && j < firstDay) {
                    dayCell.textContent = '';
                } else if (date > daysInMonth) {
                    break;
                } else {
                    dayCell.textContent = date;
                    if (date === today.getDate() && month === today.getMonth() && year === today.getFullYear()) {
                        dayCell.classList.add('today');
                    }
                    date++;
                }
                weekRow.appendChild(dayCell);
            }
            calendarTable.appendChild(weekRow);
        }

        calendarElement.appendChild(calendarTable);
    }

    function previousMonth() {
        currentMonth--;
        if (currentMonth < 0) {
            currentMonth = 11;
            currentYear--;
        }
        renderCalendar(currentMonth, currentYear);
    }

    function nextMonth() {
        currentMonth++;
        if (currentMonth > 11) {
            currentMonth = 0;
            currentYear++;
        }
        renderCalendar(currentMonth, currentYear);
    }

    const prevButton = document.createElement('button');
    prevButton.textContent = 'Previous';
    prevButton.addEventListener('click', previousMonth);
    calendarElement.appendChild(prevButton);

    const nextButton = document.createElement('button');
    nextButton.textContent = 'Next';
    nextButton.addEventListener('click', nextMonth);
    calendarElement.appendChild(nextButton);

    renderCalendar(currentMonth, currentYear);
});


