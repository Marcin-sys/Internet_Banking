function validate() {
    alert("starting validation of donate in account");
    var accountNumber = document.getElementById("accountNumber")
    var amountOfMoneyToTransfer = document.getElementById("amountOfMoneyToTransfer");

    var accountNumberRegex = /^\d{26}$/;
    var transferMoneyRegex = /^-?(?!0(?:\.0{1,2})?$)(?:\d{1,6}(?:\.\d{1,2})?|\.\d{1,2})$/;

    var result = true;
    var infoText = "";

    if(!accountNumberRegex.test(accountNumber.value)){
        infoText = infoText + "Incorrect account number, need to be 26 digits. <br>"
    }

    if(!transferMoneyRegex.test(amountOfMoneyToTransfer.value)){
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
