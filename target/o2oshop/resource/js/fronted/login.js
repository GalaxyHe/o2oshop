$(function () {
    var loginUrl = '/o2oshop/fronted/loginuserandshopowner';

    $('#submit2').click(function () {
        var personInfo = {};
        personInfo.name= $("#name").val();
        personInfo.password = $("#password").val();
        var formData = new FormData();
        formData.append('personinfoStr', JSON.stringify(personInfo));

        $.ajax({
            url: loginUrl,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    //type等于1，我们就跳转至index界面
                    if (data.usertype === 1) {
                        window.location.href = "/o2oshop/fronted/index";
                    } else {
                        //重定向到shoplist界面
                        window.location.replace("/o2oshop/shopadmin/shoplist");
                    }
                } else {
                    $.toast("登录失败！" + data.errMsg);
                }
            }
        });

    });
});