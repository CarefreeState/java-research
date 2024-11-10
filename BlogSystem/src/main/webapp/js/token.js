
function setToken(token) {
    if(token) {
        console.log("设置 token：" + token);
        jQuery.cookie("token", token, {
            expires: 365
        });
    }
}

function getToken() {
    var token = jQuery.cookie("token");
    if (token) {
        console.log("获取 token：" + token);
        return jQuery.cookie("token");
    } else {
        toLogin();
    }
}