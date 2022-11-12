function checkInp() {
    var min = document.forms["searchPrice"]["min"].value;
    var max = document.forms["searchPrice"]["max"].value;
    if (isNaN(min) || isNaN(max)) {
        alert("Please input a price");
        return false;
    }
}