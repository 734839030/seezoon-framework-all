$(function() {
	var roleId;
	var model = {
		path : adminContextPath + "/sys/role",
		tableRefresh : function() {
			$('#table').bootstrapTable("refresh");
		},
		addUser:function(userIds){
			layer.confirm('确定分配用户角色？', {
				shadeClose : true,
				icon : 3,
				anim : 6,
				btn : [ '确定', '取消' ]
			// 按钮
			}, function() {
				$.post(model.path  + "/addUser.do",$.param({userIds:userIds},true) + "&roleId=" + roleId , function(respone) {
					if (respone.responeCode == "0") {
						layer.msg("分配成功");
						model.tableRefresh();
					} 
				});
			});
		},
		init:function(){
			roleId = $.getQueryString("roleId");
			way.set("model.search.roleId",roleId);
			way.set("model.search.roleAssigned","0");
		}
	};
	model.init();
	// 查询
	$("#search").click(function() {
		model.tableRefresh();
	});
	//批量分配
	$("#assign").click(function(){
		var rows = $('#table').bootstrapTable("getSelections");
		if (rows.length == 0) {
			layer.msg("请选择一行");
		} else {
			var userIds = [];
			$.each(rows,function(i,v){
				userIds.push(v.id);
			});
			model.addUser(userIds);
		}
	});
	//返回上一级
	$("#back").click(function(){
		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		//parent.layer.close(index); //再执行关闭
		parent.layer.iframeSrc(index, '/admin/pages/sys/role-assign.html?roleId=' + roleId)
	});
	//分配角色
	$("body").on("click",".role-assign-add",function(){
		var userId = $(this).data("id");
		model.addUser([userId]);
	});
	// 列表
	$('#table').bootstrapTable({
		url : adminContextPath + '/sys/user/qryPage.do',
		singleSelect:false,
		onPostBody:function(){//渲染完后执行
			$.bntPermissionHandler();
		},
		columns : [ {
			checkbox : true
		}, {
			field : 'loginName',
			title : '用户名',
		},
		{
			field : 'name',
			title : '姓名',
		},
		{
			field : 'deptName',
			title : '部门',
		},
		{
			field : 'mobile',
			title : '手机号',
		},
		{
			field : 'userType',
			title : '用户类型',
			formatter : function(value, row, index) {
				return $.getDictName('sys_user_type',value);
			}
		},
		{
			field : 'status',
			title : '状态',
			formatter : function(value, row, index) {
				if (value == '1'){//正常
					value = "<span class='label label-success'>正常</span>";
				} else if (value == '0') {//禁用
					value = "<span class='label label-danger'>禁用</span>"
				} 
				return value;
			}
		},{
			field : 'oper',
			title : '操作',
			formatter : function(value, row, index) {
				var 	oper = "<a  href='#' class='text-success sf-permission-ctl role-assign-add' data-sf-permission='sys:role:update' data-id='" + row.id  + "'>分配角色</a>";
				return oper;
			}
		}]
	});
});