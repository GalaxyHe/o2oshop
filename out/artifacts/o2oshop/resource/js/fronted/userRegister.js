$(function () {
    var userRegUrl = '/o2oshop/fronted/userregister';


    function checkUsername() {
        //1.获取用户名值
        var username = $("#user_reg_name").val();
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
    function checkPassword() {
        //1.获取密码值
        var password = $("#user_reg_password").val();
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
    function checkEmail(){
        //1.获取邮箱
        var email = $("#email").val();
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
            var user = {};
            user.name = $("#user_reg_name").val();
            user.password = $("#user_reg_password").val();
            user.email = $("#email").val();
            user.gender = $("#gender").val();
            var formData = new FormData();
            formData.append('userStr', JSON.stringify(user));

            if (checkUsername() && checkPassword() && checkEmail()) {
                $.ajax({
                    url: userRegUrl,
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
                $.toast("请检查您的输入信息是否有误！");
            }
            return false;
        });

        //输入框失去焦点就进行校验
        $("#user_reg_name").blur(checkUsername);
        $("#user_reg_password").blur(checkPassword);
        $("#email").blur(checkEmail);

});