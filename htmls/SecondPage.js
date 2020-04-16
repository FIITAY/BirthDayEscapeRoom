function checkAnswer() {
    const rightAnswer = 'חיסון';
    var answer = document.getElementById('decoded').value;
    if(answer == rightAnswer) {
        location="http://localhost:8001/";
    } else {
        location="SecondPage-Wrong.html"
    }
    return false;
}