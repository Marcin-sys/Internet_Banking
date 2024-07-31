function validate() {
    alert("starting validation of donate in account");
    var accountBalance = document.getElementById("accountBalance");

    var donateRegex = /^-?(?!0(?:\.0{1,2})?$)(?:\d{1,6}(?:\.\d{1,2})?|\.\d{1,2})$/;

    var result = true;
    var infoText = "";

    if(!donateRegex.test(accountBalance.value)){
        infoText = infoText + "Incorrect number input, check if your input meets requirements: <br> " +
        "1. Is not zero <br>" +
        "2. Has up to 2 decimal places <br>" +
        "3. Has no more than 6 digits in total (before and after the decimal point combined) <br>"+
        "4. Is a number <br>" +
        "5. Have '.'";

        result = false;
    }

    info.innerHTML = infoText;
    return result;
}