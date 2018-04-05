//后台请求地址
var requestPath = "${requestPath}$";
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
	} else {
		if (!jqxhr.status) {
			layer.msg("服务器故障");
		}
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
		},
		311 :function(){
			layer.msg("未授权，请联系管理员");
		},
		404 : function() {
		  layer.msg("请求路径错误");
		},
		500 : function() {
			  layer.msg("服务器故障");
		}
	},
	// 同步设置
	//async : false
});
$.extend({
	getSearchCondition : function() {
		return way.get("model.search");
	}
});
/**
 * 验证框架默认属性
 */
$.extend($.fn.bootstrapValidator.DEFAULT_OPTIONS,{
	excluded: [':disabled'],
});
// 分页表格 默认值设置
$.extend($.fn.bootstrapTable.defaults, {
	method : 'post',
	striped : true,
	pagination : true,
	pageSize : 10,
	paginationLoop : false,
	pageList : [ 20, 50, 100 ],
	sidePagination : 'server',
	idField : 'id',
	uniqueId : 'id',
	singleSelect : true,
	clickToSelect : true,
	contentType : 'application/x-www-form-urlencoded',
	queryParams : function(params) {
		var param = {
			page : this.pageNumber,
			pageSize : this.pageSize,
			sortField : this.sortName,
			direction : this.sortOrder
		}
		var data = $.getSearchCondition();
		$.extend(param, data);
		return param;
	},
	onDblClickRow:function(row, $element, field){
		$('#table').bootstrapTable('checkBy', {field:'id',values:[row.id]});
		$("#edit:visible").click();
	},
	responseHandler : function(res) {
		return {
			total : res.data.total,
			rows : res.data.list
		};
	},
});