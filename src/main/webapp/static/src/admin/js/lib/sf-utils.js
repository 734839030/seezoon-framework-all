$(function() {
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
							k.required = "required"
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
				//重新渲染
				$('.selectpicker').selectpicker('refresh');
			});
		},
		//日期控件
		inputDateHandler:function(){
			// 日期控件
			$(".date").attr("readonly", "readonly");
			$(".date").each(function(){
				//默认时间 0  是当天，-1 是前一天，1 后一天
				var defaultDay = $(this).data("default-day");
				var dateClear = $(this).data("date-clear");
				$(this).datepicker({
					format : 'yyyy-mm-dd',
					language : 'zh-CN',
					clearBtn : typeof(dateClear)!='undefined'? dateClear : true,
					autoclose : true,
					todayHighlight : true
				});
				if (typeof(defaultDay)!='undefined') {
					var date = new Date();
				    date.setDate(date.getDate() + defaultDay);
					$(this).datepicker("setDate",date);
				}
			});
		},
		//按钮权限控制
		bntPermissionHandler:function(){
			// 按钮权限处理
			$(".sf-permission-ctl").each(function(i, v) {
				// 按钮权限控制
				var permission = $(v).data("sf-permission");
				// 支持父子权限判断
				if (permission) {
					var p = permission.split(":");
					var hp = [];
					for (var i = 0; i < p.length; i++) {
						hp.push(handlePermission(i, p));
					}
					for (var i = 0; i < hp.length; i++) {
						var value = sessionStorage.getItem("model.permission." + hp[i]);
						if ('1' == value) {
							$(this).show();
							break;
						}
					}
				}
			});
		},
		/**
		 * 地址栏参数
		 * @param name
		 * @returns
		 */
		getQueryString:function getQueryString(name)
		{
			var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
			var r = window.location.search.substr(1).match(reg);
			if(r!=null)return unescape(r[2]); return null;
		}
	});
	$.dictInputhandler();
	$.inputDateHandler();
	$.bntPermissionHandler();
});
// 处理权限递归,以冒号拆分，父子权限
function handlePermission(i, p) {
	var ps;
	for (var j = 0; j <= i; j++) {
		if (ps) {
			ps = ps + ":" + p[j];
		} else {
			ps = p[j];
		}
	}
	return ps;
}