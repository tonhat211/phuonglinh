function countdown(container,hours, minutes, seconds) {
    // Chuyển đổi toàn bộ thời gian thành giây
    let totalSeconds = hours * 3600 + minutes * 60 + seconds;
    const hourE = document.querySelector(`${container} .hour`);
    const minuteE = document.querySelector(`${container} .minute`);
    const secondE = document.querySelector(`${container} .second`);

    const interval = setInterval(() => {
        if (totalSeconds <= 0) {
            clearInterval(interval);
            console.log("Time's up!");
            return;
        }

        let hoursLeft = Math.floor(totalSeconds / 3600);
        let minutesLeft = Math.floor((totalSeconds % 3600) / 60);
        let secondsLeft = totalSeconds % 60;

        hourE.innerText=String(hoursLeft).padStart(2, '0');
        minuteE.innerText=String(minutesLeft).padStart(2, '0');
        secondE.innerText=String(secondsLeft).padStart(2, '0');

        totalSeconds--;
    }, 1000);
}