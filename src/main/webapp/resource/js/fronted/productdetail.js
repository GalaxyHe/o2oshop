$(function() {
	var productId = getParameter('productId');
	var productUrl = '/o2oshop/fronted/listproductdetailpageinfo?productId='
		+ productId;
	var purchaseUrl = '/o2oshop/fronted/purchaseproduct';

	$.getJSON(
		productUrl,
		function (data) {
			if (data.success) {
				var product = data.product;
				$('#product-img').attr('src', product.imgAddr);
				$('#product-time').text(
					new Date(product.lastEditTime)
						.Format("yyyy-MM-dd"));
				$('#product-name').text(product.productName);
				$('#product-desc').text(product.productDesc);
				var imgListHtml = '';
				product.productImgList.map(function (item, index) {
					imgListHtml += '<div> <img src="'
						+ item.imgAddr + '"/></div>';
				});

			}
		});

	$('#me').click(function () {
		$.openPanel('#panel-left-demo');
	});

	$('#purchase').click(function () {
		$.ajax({
			url: purchaseUrl,
			type: 'POST',
			contentType: false,
			processData: false,
			cache: false,
			success: function (data) {
				if (data.success && data.islogin) {
					$.toast(data.message);
				} else if (!data.islogin) {
					$.toast("请登录后再购买！！");
				} else {
					$.toast("购买失败！")
				}
			}
		});
	});



	$.init();
});
