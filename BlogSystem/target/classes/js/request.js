
var baseUrl = "http://localhost:8080/BlogSystem";

function jsonRequest(u, m, d, s) {
    jQuery.ajax({
        url: baseUrl + u,
        method: m,
        contentType: "application/json; charset=utf8",
        data: JSON.stringify(d),
        // 3. 处理响应
        success: function (body) {
            if (body.code === 200) {
                s(body.data);
            } else {
                jQuery.toast({
                    heading: "异常",
                    text: body.message,
                    icon: "warning",
                    allowToastClose: true,
                });
            }
        },
        error: function () {
            //提示信息
            $.toast({
                heading: "错误",
                text: "访问出现问题",
                icon: "error",
                allowToastClose: true,
            });
        },
    });
}


