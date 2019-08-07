$(function () {
    var quitUrl = "/o2oshop/fronted/userquit";

    $("#log-out").click(function () {
        $.ajax({
            url: quitUrl,
            type: 'get',
            dataType: 'json',
            success: function (data) {
                if(data.redirect){
                    window.location.href = data.url;
                }
            }
        });
    });

});