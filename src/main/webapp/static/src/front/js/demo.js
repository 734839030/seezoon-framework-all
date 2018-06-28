$(function(){
	
	touch.on("#getUserInfo","tap",function(){
		$.get(frontContextPath + "/demo/getUserInfo.do",function(respone){
			if (respone.responeCode == '0') {
				layer.msg(respone.data.name);
			}
		})
	})
});