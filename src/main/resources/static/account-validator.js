function validate() {
    alert("starting validation of account");
    var accountCurrency = document.getElementById("accountCurrency");

    var euro = "euro";
    var dollar = "dollar";
    var zloty = "zloty"

    var result = true;
    var infoText = "";

    if(euro.test(accountCurrency.value)){
        title.style.background = null;
    } else if (dollar.test(accountCurrency.value)){
        title.style.background = null;
    } else if (zloty.test(accountCurrency.value)){
        title.style.background = null;
    } else {
        title.style.background = "#a8beff";
        infoText = infoText + "Incorrect Currency <br>";
        result = false;
    }

    info.innerHTML = infoText;

    return result;
}