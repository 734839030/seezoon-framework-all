$(function() {
	$(document).keydown(function(event) {
		if (event.keyCode == 13) {
			$("#login").click();
		}
	});
	$('input:checkbox').iCheck({
		checkboxClass : 'icheckbox_square-blue',
		radioClass : 'iradio_square-blue',
		increaseArea : '20%' // optional
	});
	$("#login").click(function() {
		var loginName = $.trim($("input[name='loginName']").val());
		var password = $.trim($("input[name='password']").val());
		if (!loginName) {
			layer.msg("用户名不能为空", {
				offset : 'm',
				anim : 6
			});
			return false;
		}
		if (loginName.length < 2) {
			layer.msg("用户名过短", {
				offset : 'm',
				anim : 6
			});
			return false;
		}
		if (!password) {
			layer.msg("密码不能为空", {
				offset : 'm',
				anim : 6
			});
			return false;
		}
		if (password.length < 6) {
			layer.msg("密码不能少于6位", {
				offset : 'm',
				anim : 6
			});
			return false;
		}
		$.post(adminContextPath + "/login.do", {
			loginName : loginName,
			password : password,
			rememberMe : $("input[name='rememberMe']").val()
		}, function(respone) {
			if (respone.responeCode == "0") {
				sessionStorage.clear();
				window.location.href = "/pages/index.html";
			}
		});
	});
	
});