$(function() {
	var model = {
		path : adminContextPath + "/sys/gen",
		resetDataForm : function() {
			$("#data-form").bootstrapValidator('resetForm', true);
			way.set("model.form.data",null);
		},
		getFormData : function() {
			var data =  way.get("model.form.data");
			return data?data:{};
		},
		setFormTitle : function(title) {
			way.set("model.form.title", title);
		},
		setFormData:function(data){
			model.resetDataForm();
			way.set("model.form.data",data);
			//渲染字段
			$("#table-column-tr-template").tmpl(data.columnInfo).appendTo($("#table-body").empty());
			model.bootstrapValidator();
		},
		tableRefresh : function() {
			$('#table').bootstrapTable("refresh");
		},
		init:function(){
			$.post(this.path + "/qryTables.do",function(respone){
				var tableData = [];
				$.each(respone.data,function(i,v){
					var comment = v.comment;
					if (comment) {
						comment = v.name + "(" + comment + ")";
					} else {
						comment = v.name;
					}
					tableData.push({code:v.name,name:comment});
				});
				
				$("#sf-select-template").tmpl(tableData).appendTo($("#table-select"));
				$('#table-select').selectpicker('refresh'); 
			})
		},
		openModal:function(title){
			model.resetDataForm();
			layer.open({
			 	  title:title,
	              shadeClose:true,
			 	  icon: 1,
			 	  area: ['95%', '95%'],
				  type: 1, 
				  maxmin:true,
				  content:  $("#form-panel") 
				});
		},
		bootstrapValidator:function(){
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
		}
	};
	model.openModal("添加");
	model.init();
	//新增场景
	$("#table-select").change(function(){
		var tableName = $(this).val();
		if (tableName) {//查询信息
			$.post(model.path + "/qryTableInfo.do",{tableName:tableName},function(respone){
				model.setFormData(respone.data);
			});
		} else {//清空
			
		}
	});
	
	// 查询
	$("#search").click(function() {
		model.tableRefresh();
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
		columns : [ {
			checkbox : true
		}, {
			field : 'tableName',
			title : '表名',
		}, {
			field : 'menuName',
			title : '菜单名称',
		}, {
			field : 'moduleName',
			title : '模块名'
		}, {
			field : 'className',
			title : '类名',
		}, {
			field : 'template',
			title : '生成模板',
		} , {
			field : 'template',
			title : '生成模板',
		} ,{
			field : 'updateDate',
			title : '更新时间',
			sortName : 'update_date',
			sortable : true,
			order : 'desc'
		}  ]
	});
});