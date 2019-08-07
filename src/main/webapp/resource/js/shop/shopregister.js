$(function () {
    var initurl = '/o2oshop/shopadmin/getshopinitinfo';
    var registerShopRUrl = '/o2oshop/shopadmin/registershop';
    getShopInitInfo();



    //获取商铺注册时的必要信息
    function getShopInitInfo() {
        $.getJSON(initurl, function (data) {
            if (data.success) {
                var temphtml = '';
                var tempAreahtml = '';
                data.shopCategoryList.map(function (item, index) {
                    temphtml += '<option data-id = "' + item.shopCategoryId + '">' + item.shopCategoryName + '</option>';
                });
                data.areaList.map(function (item, index) {
                    tempAreahtml += '<option data-id="' + item.areaId + '">'
                        + item.areaName + '</option>';
                });
                $("#reg_shop-category").html(temphtml);
                $("#reg_shop-area").html(tempAreahtml);
            }
        });
    }


    $("#submit2").click(function () {
        var regshop = {};
        regshop.shopName = $("#reg_shop-name").val();
        regshop.shopAddr = $("#reg_shop-address").val();
        regshop.phone = $("#reg_shop-phone").val();
        regshop.shopCategory = {
            shopCategoryId : $('#reg_shop-category').find('option').not(function() {
                return !this.selected;
            }).data('id')
        };

        regshop.area = {
            areaId : $('#reg_shop-area').find('option').not(function() {
                return !this.selected;
            }).data('id')
        };

        var regshopImg = $("#reg_shop-img")[0].files[0];
        var formData = new FormData();
        formData.append('regshopImg', regshopImg);
        formData.append('regshopStr', JSON.stringify(regshop));
        var verifyCodeActual = $("#reg_j_captcha").val();
        if (!verifyCodeActual) {
            $.toast("请输入验证码！");
            return;
        }

        formData.append("verifyCodeActual", verifyCodeActual);
        $.ajax({
            url: registerShopRUrl,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    $.toast("提交成功！");
                    window.location.href="/o2oshop/shopadmin/shoplist";
                } else {
                    $.toast("提交失败！" + data.errMsg);
                }
                $("#reg_captcha_img").click();
            }
        });
    });


});