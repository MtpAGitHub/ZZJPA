//240314    MtpA    Created

function passwordConfirm() {
    var password = document.getElementById("userReg:password");
    var confirm = document.getElementById("userReg:confPassword");
    
    if (password.value === confirm.value) {
        return true;
    } else {
        document.getElementById("userReg:errorTxt").innerHTML = "Passwords must be the same !";
        return false;        
    }
}


