$(function() {
	var model = {
		path : adminContextPath + "/sys/role",
		resetDataForm : function() {
			$("#data-form").bootstrapValidator('resetForm', true);
			way.set("model.form.data",null);
			model.menuTree.checkAllNodes(false);
			model.deptTree.checkAllNodes(false);
			$("#dept-wrapper").hide();
		},
		getFormData : function() {
			var data =  way.get("model.form.data");
			return data?data:{};
		},
		setFormTitle : function(title) {
			way.set("model.form.title", title);
		},
		tableRefresh : function() {
			$('#table').bootstrapTable("refresh");
		},
		setFormDataById:function(id){
			$.get(this.path + "/get.do",{id:id},function(respone){
				way.set("model.form.data",respone.data);
				$.post(adminContextPath + "/sys/menu/qryByRoleId.do",{roleId:id},function(respone1){
					//勾选角色所拥有的菜单
					var roleMenus = respone1.data;
					for(var i=0; i<roleMenus.length; i++) {
						var node = model.menuTree.getNodeByParam("id", roleMenus[i].id);
						model.menuTree.checkNode(node, true, false);
					} 
				});
				var dataScope = respone.data.dataScope;
				if (dataScope == '4') {
					$.post(adminContextPath + "/sys/role/qryDeptIdsByRoleId.do",{roleId:id},function(respone1){
						//勾选角色所拥有的部门
						var roleDepts = respone1.data;
						for(var i=0; i<roleDepts.length; i++) {
							var node = model.deptTree.getNodeByParam("id", roleDepts[i]);
							model.deptTree.checkNode(node, true, false);
						} 
					});
					$("#dept-wrapper").show();
				}
			});
		},
		menuTree:null,
		deptTree:null,
		init:function(){
			//初始化菜单 ztree
			$.post(adminContextPath + "/sys/menu/qryAll.do",function(respone){
				model.menuTree = $.fn.zTree.init($("#menuTree"), {
					data:{
						simpleData:{
							enable: true,
							idKey: "id",
							pIdKey: "parentId",
						}	
					},
					check:{
						enable: true,
						chkStyle:'checkbox',
						chkboxType: { "Y": "ps", "N": "ps" }
					}
				},respone.data);
				//model.menuTree.expandAll(true);
			});
			//初始化部门 ztree
			$.post(adminContextPath + "/sys/dept/qryAll.do",function(respone){
				model.deptTree = $.fn.zTree.init($("#deptTree"), {
					data:{
						simpleData:{
							enable: true,
							idKey: "id",
							pIdKey: "parentId",
						}	
					},
					check:{
						enable: true,
						chkStyle:'checkbox',
						chkboxType: { "Y": "ps", "N": "ps" }
					}
				},respone.data);
				model.deptTree.expandAll(true);
			});
		}
	};
	model.init();
	// 校验
	$("#data-form").bootstrapValidator().on("success.form.bv", function(e) {// 提交
		e.preventDefault();
		var id = model.getFormData().id;
		var optUrl = model.path + "/save.do";
		if (id) {
			optUrl = model.path + "/update.do";
		}
		var nodes = model.menuTree.getCheckedNodes(true);
		var menuIds = [];
		for(var i=0; i<nodes.length; i++) {
			menuIds.push(nodes[i].id);
        }
		var deptTreeNodes = model.deptTree.getCheckedNodes(true);
		var deptIds = [];
		for(var i=0; i<deptTreeNodes.length; i++) {
			deptIds.push(deptTreeNodes[i].id);
        }
		var param = $("#data-form").serialize();
		if (menuIds.length > 0)  {
			param = param + "&" + $.param({menuIds:menuIds},true);
		}
		if (deptIds.length > 0) {
			param = param + "&" +  $.param({deptIds:deptIds},true);
		}
		$.post(optUrl, param , function(respone) {
			if (respone.responeCode == '0') {
				layer.msg("保存成功");
				model.tableRefresh();
				$("#form-panel").modal('toggle');
			}
		});
	});
	$("#data-scope-select").change(function(){
		if ($(this).val() == '4') {
			$("#dept-wrapper").show();
		} else {
			$("#dept-wrapper").hide();
		}
	});
	// 查询
	$("#search").click(function() {
		model.tableRefresh();
	});
	//分配
	$("body").on("click",".role-assign",function(){
		var roleId = $(this).data("id");
		layer.open({
			  title:"角色分配",
			  type: 2, //iframe
			  maxmin:true,
			  area: ['95%', '95%'],
			  content: '/admin/pages/sys/role-assign.html?roleId=' + roleId //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
			});
	});
	// 添加
	$("#add").click(function() {
		model.resetDataForm();
		model.setFormTitle("<i class='fa fa-plus'>添加</i>");
		$("#form-panel").modal('toggle');
	});
	// 编辑
	$("#edit").click(function() {
		var rows = $('#table').bootstrapTable("getSelections");
		if (rows.length == 0) {
			layer.msg("请选择一行");
		} else {
			model.resetDataForm();
			model.setFormDataById(rows[0].id);
			model.setFormTitle("<i class='fa fa-edit'>编辑</i>");
			$("#form-panel").modal('toggle');
		}
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
		onPostBody:function(){//渲染完后执行
			$.bntPermissionHandler();
		},
		columns : [ {
			checkbox : true
		}, {
			field : 'name',
			title : '名称',
			
		},{
			field : 'dataScope',
			title : '数据范围',
			formatter : function(value, row, index) {
				if (value == '0') {//全部
					value = "<span class='label label-primary'>全部</span>";
				} else if (value == '1'){//本部门及以下
					value = "<span class='label label-success'>本部门及以下</span>";
				} else if (value == '2') {//本部门
					value = "<span class='label label-info'>本部门</span>"
				}  else if (value == '3') {//本人
					value = "<span class='label label-default'>本人</span>"
				}  else if (value == '4'){//自定义部门
					value = "<span class='label label-warning'>自定义部门</span>"
				}
				return value;
			}
		},{
			field : 'updateDate',
			title : '更新时间',
			sortName : 'update_date',
			sortable : true,
			order : 'desc'
		},{
			field : 'oper',
			title : '操作',
			formatter : function(value, row, index) {
				var oper = "<a  href='#' class='text-success sf-permission-ctl role-assign' data-sf-permission='sys:role:update' data-id='" + row.id  + "'>分配用户</a>";
				return oper;
			}
		}]
	});
});