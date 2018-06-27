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
	} else {
		if (thrownError) {
			layer.msg(jqxhr.status + ":" + thrownError);
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
			layer.msg("未登录");
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

/**
 * 字典列表与code转汉字
 */
$.extend({
	// 获取字典列表
	getDictList : function(type) {
		if (!type) {
			return null;
		}
		// 先从sessionStorage 取
		var typeDict = sessionStorage.getItem("model.dict." + type);
		if (typeDict) {
			return $.parseJSON(typeDict);
		} else {
			// 取消异步
			$.ajaxSetup({
				async : false
			});
			var dict = [];
			$.get(publicContextPath + "/dict/getDictsByType.do", {
				type : type
			}, function(respone) {
				if (respone.responeCode == "0") {
					dict = respone.data;
					// 存入sessionStorage
					if (dict) {
						sessionStorage.setItem("model.dict." + type, JSON
								.stringify(dict));
					}
				}
			});
			$.ajaxSetup({
				async : true
			});
			return dict;
		}
	},
	// 字典code 转value
	getDictName : function(type, value) {
		if (!type || !value) {
			return null;
		}
		// 先从sessionStorage 取
		var typeMap = sessionStorage.getItem("model.dict.map." + type);
		if (typeMap) {
			var json = $.parseJSON(typeMap);
			return json[value];
		} else {
			var map = {};
			var dictList = $.getDictList(type);
			if (dictList) {
				$.each(dictList, function(i, v) {
					map[v.code] = v.name
				});
				if (map) {
					sessionStorage.setItem("model.dict.map." + type, JSON
							.stringify(map));
				}
				return map[value];
			}
		}
	},
	// checkBox 反选
	/**
	 * 节点，选中值
	 */
	checkBoxCheck : function($input, array) {
		if (array) {
			$.each($input, function(i, v) {
				if (-1 != $.inArray($(v).val(), array)) {
					$(v).attr("checked", "checked");
				} else {
					$(v).removeAttr("checked");
				}
			})
		}
	},
	dictInputhandler:function(){
		/**
		 * <div class="col-sm-5"> <label class="radio-inline"> <input type="radio"
		 * required way-data="status" name="status" value="1">有效 </label> <label
		 * class="radio-inline"> <input type="radio" required way-data="status"
		 * name="status" value="0">无效 </label> </div>
		 * 
		 * <div class="col-sm-5"> <label class="checkbox-inline"> <input
		 * type="checkbox" required way-data="status" name="status" value="1">有效
		 * </label> <label class="checkbox-inline"> <input type="checkbox" required
		 * way-data="status" name="status" value="0">无效 </label> </div>
		 * 
		 */
		// 字典渲染
		$(".sf-radio,.sf-checkbox").each(function(i, v) {
			var inputName = $(this).data("sf-input-name");
			var dictType = $(this).data("sf-dict-type");
			var required = $(this).data("sf-required");

			var dictList = $.getDictList(dictType);
			if (dictList) {
				$.each(dictList, function(j, k) {
					k.inputName = inputName;
					if (required) {
						k.required = required
					}
					// 禁用
					if (k.status == '0') {
						k.disabled = "disabled";
					}
				});
			}
			if ($(this).hasClass("sf-radio")) {
				$("#sf-radio-template").tmpl(dictList).appendTo(this);
			} else {
				$("#sf-checkbox-template").tmpl(dictList).appendTo(this);
			}
		});
		/**
		 * eg:
		 * <div class="form-group">
			<label class="col-sm-3 control-label">用户类型</label>
			<div class="col-sm-5">
				<select class="form-control sf-select" way-data="userType"
					name="userType" data-sf-dict-type="sys_user_type">
					<option value="">请选择</option>
				</select>
			</div>
		</div>
		 */
		$(".sf-select").each(function(i, v) {
			var dictType = $(this).data("sf-dict-type");
			var dictList = $.getDictList(dictType);
			if (dictList) {
				$.each(dictList, function(j, k) {
					// 禁用
					if (k.status == '0') {
						k.disabled = "disabled";
					}
				});
			}
			$("#sf-select-template").tmpl(dictList).appendTo(this);
		});
	},
});
$.dictInputhandler();

