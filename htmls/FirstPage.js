
function checkAnswer() {
    const rightAnswer = 193287;
    var answer = document.getElementById('password').value;
    if(answer == rightAnswer) {
        location="SecondPage.html";
    } else {
        location="FirstPage-Wrong.html"
    }
    return false;
}