$(function(){
	
	touch.on("#getUserInfo","tap",function(){
		$.get(frontContextPath + "/demo/getUserInfo.do",function(respone){
			if (respone.responeCode == '0') {
				layer.msg(respone.data.name);
			}
		})
	})
	touch.on("#pay","tap",function(){
		$.get(frontContextPath + "/demo/jsPay.do",function(respone){
			if (respone.responeCode == '0') {
				wxPay(respone.data,function(){
					layer.msg("支付成功");
				},function(){
					layer.msg("取消支付");
				});
			}
		})
	})
	touch.on("#qrpay1","tap",function(){
		$.get(frontContextPath + "/demo/qrPay1.do",function(respone){
			if (respone.responeCode == '0') {
				new QRCode(document.getElementById('qrcode1'), respone.data);
			}
		})
	})
	$.get(frontContextPath + "/demo/qrPay2.do",function(respone){
		if (respone.responeCode == '0') {
			new QRCode(document.getElementById('qrcode2'), respone.data);
		}
	})
});