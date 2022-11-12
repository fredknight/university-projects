function checkBasket() {
    if (document.getElementById("name").value === "") {
        alert("Please enter a name!");
        return false;
    } else {
        return true;
    }
}

function eraseText() {
    document.getElementById("name").value = "";
}