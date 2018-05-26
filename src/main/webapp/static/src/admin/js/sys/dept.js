$(function() {
	var model = {
		path : adminContextPath + "/sys/dept",
		resetDataForm : function() {
			$("#data-form").bootstrapValidator('resetForm', true);
			way.set("model.form.data",{
				sort:10,
			});
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
				model.setParent(respone.data.parentId);
			})
		},
		setParent:function(parentId){
			if (parentId && parentId !='0') {
				//查询上级部门名称
				$.get(this.path + "/get.do",{id:parentId},function(respone1){
					way.set("model.form.data.parentId",respone1.data.id);
					way.set("model.form.data.parentName",respone1.data.name);
				});
			}
		}
	};
	// 校验
	$("#data-form").bootstrapValidator().on("success.form.bv", function(e) {// 提交
		e.preventDefault();
		var id = model.getFormData().id;
		var optUrl = model.path + "/save.do";
		if (id) {
			optUrl = model.path + "/update.do";
		}
		$.post(optUrl, $("#data-form").serialize(), function(respone) {
			if (respone.responeCode == '0') {
				layer.msg("保存成功");
				model.tableRefresh();
				$("#form-panel").modal('toggle');
			}
		});
	});
	// 查询
	$("#search").click(function() {
		model.tableRefresh();
	});
	//点击父部门选择框
	$("#parent-input").click(function(){
		$.seezoon.chooseDept(way.get("model.form.data.parentId"),function(treeNode){
			//自己选择自己时候提示
			var currentId = way.get("model.form.data.id");
			if (treeNode.id == currentId) {
				layer.msg("不能选择当前部门");
				return false;
			}
			if ( currentId && -1!=treeNode.parentIds.indexOf(currentId)) {//不能选择自己的子节点
				layer.msg("不能选择子节点");
				return false;
			}
			way.set("model.form.data.parentId",treeNode.id);
			way.set("model.form.data.parentName",treeNode.name);
			return true;
		},function(){
			way.set("model.form.data.parentId",null);
			way.set("model.form.data.parentName",null);
		});
	});
	// 添加
	$("#add").click(function() {
		model.resetDataForm();
		model.setFormTitle("<i class='fa fa-plus'>添加</i>");
		$("#form-panel").modal('toggle');
	});
	//添加下级部门
	$("body").on("click", ".addDept", function(){
		model.resetDataForm();
		model.setParent($(this).data("id"));
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
			layer.confirm('将一起删除下级部门,确定删除？', {
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
		url : model.path + '/qryAll.do',
		pagination:false,
		queryParams:function(){
			return {name:way.get("model.search.name")};
		},
		responseHandler : function(res) {
			return {
				rows : res.data
			};
		},
		onPostBody:function(){//渲染完后执行
			 $("#table").treegrid({
				    treeColumn:1,
					initialState:'expanded',
					saveState:true,
					expanderExpandedClass: 'glyphicon glyphicon-chevron-down',
			        expanderCollapsedClass: 'glyphicon glyphicon-chevron-right'
				});
			 $('#table').treegrid('render');
			 $.bntPermissionHandler();
		},
		rowStyle:function(row,index){//整合treegrid
			var classes = "treegrid-" + row.id
			if (row.parentId != '0') {
				classes = classes + " treegrid-parent-" + row.parentId;
			}
			return {classes:classes};
		},
		columns : [ {
			checkbox : true
		}, {
			field : 'name',
			title : '名称',
		},
		{
			field : 'sort',
			title : '顺序',
		}, {
			field : 'updateDate',
			title : '更新时间',
			sortName : 'update_date',
			sortable : true,
			order : 'desc'
		} ,{
			field : 'oper',
			title : '操作',
			formatter : function(value, row, index) {
				var oper =  "<a  href='#' class='text-success addDept sf-permission-ctl'  data-id='" + row.id+ "' data-sf-permission='sys:dept:save'>添加下级部门</a>";
				return oper;
			}
		}]
	});
});