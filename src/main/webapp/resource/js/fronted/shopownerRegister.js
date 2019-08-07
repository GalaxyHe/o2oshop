$(function () {
    var shopownerRegUrl = '/o2oshop/fronted/shopownerregister';

    function checkUsername2() {
        //1.获取用户名值
        var username = $("#shop_owner_reg_name").val();
        //2.定义正则
        var reg_username = /^\w{8,20}$/;

        //3.判断，给出提示信息
        var flag = reg_username.test(username);
        if(!flag){
            //用户名不合法
            $.toast("用户名格式不正确！");
        }

        return flag;
    }

    //校验密码
    function checkPassword2() {
        //1.获取密码值
        var password = $("#shop_owner_reg_password").val();
        //2.定义正则
        var reg_password = /^\w{8,20}$/;

        //3.判断，给出提示信息
        var flag = reg_password.test(password);
        if(!flag){
            //密码不合法
            $.toast("密码格式不正确！")
        }

        return flag;
    }

    //校验邮箱
    function checkEmail2(){
        //1.获取邮箱
        var email = $("#shop_owner_email").val();
        //2.定义正则
        var reg_email = /^\w+@\w+\.\w+$/;

        //3.判断
        var flag = reg_email.test(email);
        if(!flag){
            $.toast("邮箱格式不正确！");
        }

        return flag;
    }



        $('#user_reg_submit').click(function () {
            var shopowner = {};
            user.name = $("#shopowner_reg_name").val();
            user.password = $("#shopowner_reg_password").val();
            user.email = $("#shopowner_email").val();
            user.gender = $("#shopowner_gender").val();
            var formData = new FormData();
            formData.append('shopownerStr', JSON.stringify(shopowner));

            if (checkUsername2() && checkPassword2() && checkEmail2()) {
                $.ajax({
                    url: shopownerRegUrl,
                    type: 'POST',
                    data: formData,
                    contentType: false,
                    processData: false,
                    cache: false,
                    success: function (data) {
                        if (data.success) {
                            window.location.href = "/o2oshop/fronted/login";
                        } else {
                            $.toast("提交失败！" + data.errMsg);
                        }
                    }
                });
            } else {
                $.toast("请检查您的输入信息是否有误！")
            }

            return false;

        });

        $("#shopowner_reg_name").blur(checkUsername2);
        $("#shopowner_reg_password").blur(checkPassword2);
        $("#shopowner_email").blur(checkEmail2);

});