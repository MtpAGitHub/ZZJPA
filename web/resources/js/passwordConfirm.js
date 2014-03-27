//240314    MtpA    Created

function passwordConfirm() {
    var password = document.getElementById("JPARegistration:password");
    var confirm = document.getElementById("JPARegistration:confPassword");
    
    if (password.value === confirm.value) {
        return true;
    } else {
        document.getElementById("JPARegistration:errorTxt").innerHTML = "Passwords must be the same !";
        return false;        
    }
}


