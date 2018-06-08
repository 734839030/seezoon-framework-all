$(function() {
    	var model = {
		path : adminContextPath + "/sys/loginlog",
		tableRefresh : function() {
			$('#table').bootstrapTable("refresh");
		},
		init:function(){//需要初始化的功能
		}
	};
	model.init();
	// 查询
	$("#search").click(function() {
		model.tableRefresh();
	});
	// 删除
	$("#delete").click(function() {
		var rows = $('#table').bootstrapTable("getSelections");
		if (rows.length == 0) {
			layer.msg("请选择一行");
		} else {
			layer.confirm('确定删除？', {
				shadeClose : true,
				icon : 3,
				anim : 6,
				btn : [ '确定', '取消' ]
			// 按钮
			}, function() {
				$.post(model.path  + "/delete.do", {
					id : rows[0].id
				}, function(respone) {
					if (respone.responeCode == "0") {
						layer.msg("删除成功");
						model.tableRefresh();
					} 
				});
			});
		}
	});
	// 列表
	$('#table').bootstrapTable({
		url : model.path + '/qryPage.do',
		sortName:'l.login_time',
		sortOrder:'desc',
		columns : [ {
			checkbox : true
		}, 
			{
			field : 'loginName',
			title : '登录名',
			},
			{
			field : 'userName',
			title : '姓名',
			},
			{
			field : 'ip',
			title : 'IP地址',
			},
			{
			field : 'area',
			title : '登录地区',
			formatter : function(value, row, index) {
				if (value) {
					return value;
				}
				return "<a target='_blank' href='http://ip.chinaz.com/?IP=" + row.ip +"'>查看</a>";
			}
			},
			{
			field : 'deviceName',
			title : '设备名称',
			},
			{
			field : 'browserName',
			title : '浏览器',
			},
			{
				field : 'status',
				title : '状态',
				formatter : function(value, row, index) {
					if (value == '0'){//正常
						value = "<span class='label label-success'>成功</span>";
					} else if (value == '1') {//密码错误
						value = "<span class='label label-danger'>密码错误</span>"
					} else if (value == '2') {//禁用
						value = "<span class='label label-danger'>禁用</span>"
					} else if (value == '3') {//锁定24小时
						value = "<span class='label label-danger'>锁定24小时</span>"
					} 
					return value;
				}
			},
			{
				field : 'loginTime',
				title : '登录时间',
				sortName : 'l.login_time',
				sortable : true,
				order : 'desc',
				}
		 ]
	});
	
});