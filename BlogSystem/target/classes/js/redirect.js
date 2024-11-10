
var baseUrl = "/BlogSystem";

function toLogin() {
    jQuery.toast({
        heading: "提示",
        text: "前往登录",
        icon: "info",
        allowToastClose: true,
    });
    setTimeout(function () {
        location.href = baseUrl + "/login.html";
    }, 3000);
}

