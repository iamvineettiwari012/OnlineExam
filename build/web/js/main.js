let startTime = 0;
if (sessionStorage.getItem("timeLeft")){
    startTime = sessionStorage.getItem("timeLeft");
} else {
    startTime = 600;
}
let displayArea = document.getElementById("timer");
function displayTime() {
    let hh = Math.floor(startTime / 3600);
    let mm = Math.floor((startTime - hh*3600) / 60);
    let ss = Math.floor((startTime - mm*60) % 60);
    if (hh < 10) {
        hh = "0"+hh;
    }
    if (mm < 10) {
        mm = "0"+mm;
    }
    if (ss < 10) {
        ss = "0"+ss;
    }
    displayArea.innerHTML =  mm+":"+ss;
    sessionStorage.setItem("timeLeft", startTime);
    if (startTime == 0) {
        alert("got it");
        clearInterval(interval);
    }
}

function makeTimer() {
    startTime--;
    displayTime();
}
displayTime();
let interval = setInterval(makeTimer, 1000);
