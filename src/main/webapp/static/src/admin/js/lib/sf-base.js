//后台请求地址
var requestPath = "${requestPath}$";
/**
 * admin后端应用请求接口前缀
 */
var adminContextPath = requestPath + "/a";
/**
 * 前端应用请求接口前缀
 */
var frontContextPath = requestPath + "/f";
/**
 * 公共
 */
var publicContextPath = requestPath + "/public"; 
// wayjs 默认不存储localstoreage
way.options.persistent = false;
/**
 * ajax 全局事件
 * @returns
 */
$(document).ajaxStart(function() {// 开始
	layer.load({
		time : 10 * 1000
	});
}).ajaxStop(function() {// 结束
	layer.closeAll('loading');
}).ajaxError(function(event, jqxhr, settings, thrownError) {// 异常
	layer.closeAll('loading');
	if ("timeout" == thrownError) {
		layer.msg("请求超时，请重试");
	} else if ("Not Found" == thrownError) {
		layer.msg("请求路径错误,请检查");
	} else if (0 == jqxhr.status) {
		layer.msg("网络连接失败");
	} 
}).ajaxSuccess(function(event, xhr, settings) {
	if (xhr.responseText) {
		var data = $.parseJSON(xhr.responseText);
		var responeCode = data.responeCode;
		//90 开头为系统错误码
		var reg = /^90/
		if (responeCode == '-1' || reg.test(responeCode)) {
			layer.msg(data.responeMsg);
		}
	}
});
$.ajaxSetup({
	global : true,// 默认就是true ，触发全局事件
	type : "POST",
	contentType : "application/x-www-form-urlencoded;charset=utf-8",
	timeout : 10000,
	dataType : 'json',
	xhrFields:{
		withCredentials:true
	},
	crossDomain:true,
	statusCode : {
		310 :function(){//未登录
			if  (window.location.href.indexOf("/login.html") ==-1) {
				if (self != top) {
					window.top.location.href='/admin/pages/login.html';
				} else {
					window.location.href='/admin/pages/login.html';
				}
			}
		},
		311 :function(){
			layer.msg("未授权，请联系管理员");
		},
		404 : function() {
		  layer.msg("请求路径错误");
		},
		500 : function() {
			  layer.msg("服务器出了点问题");
		}
	},
	// 同步设置
	//async : false
});
