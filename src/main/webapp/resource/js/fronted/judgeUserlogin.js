$(function () {
    var judgeUrl = '/o2oshop/fronted/userloginornot';

    $.get(judgeUrl, function (data) {
        if (data.success) {
            $("#judge_user_login").hide();
        } else {
            $("#judge_user_quit").hide();
        }
    });

});