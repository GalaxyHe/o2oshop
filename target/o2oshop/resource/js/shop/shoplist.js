$(function () {
    getlist();
    function getlist() {
        $.ajax({
            url: "/o2oshop/shopadmin/getshoplist",
            type: 'get',
            dataType: 'json',
            success: function (data) {
                if (data.success) {
                    handleList(data.shopList);
                    handleUser(data.shopowner);
                }
            }
        });
    }

    function handleUser(data) {
        $("#user-name").text(data.name);
    }

    function handleList(data) {
        var htmlstr = '';
        data.map(function (item) {
            htmlstr += '<div class="row row-shop"><div class="col-40">'
                + item.shopName + '</div><div class="col-40">'
                + shopStatus(item.enableStatus)
                + '</div><div class="col-20">'
                + goShop(item.enableStatus, item.shopId) + '</div></div>';
        });
        $('.shop-wrap').html(htmlstr);
    }

    function shopStatus(status) {
        if (status === 0) {
            return '审核中';
        } else if (status === 1) {
            return '审核通过';
        } else {
            return '店铺非法';
        }
    }

    function goShop(status, id) {
        //审核通过或者审核中均可进行修改商铺的操作
        if (status === 0 || status === 1) {
            return '<a href="/o2oshop/shopadmin/shopmanagement?shopId=' + id + '">进入</a>';
        } else {
            return '';
        }
    }
});