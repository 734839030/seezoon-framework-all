$(function() {
	var model = {
		path : adminContextPath + "/sys/gen",
		resetDataForm : function() {
			$("#data-form").bootstrapValidator('resetForm', true);
			$("#table-body").empty();
			$('#table-select').selectpicker('refresh'); 
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
			$('#table-select').selectpicker('refresh'); 
			//渲染字段
			$("#table-column-tr-template").tmpl(data.columnInfos).appendTo($("#table-body"));
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
		}
	};
	model.init();
	//新增场景
	$("#table-select").change(function(){
		var tableName = $(this).val();
		if (tableName) {//查询信息
			$.post(model.path + "/qryTableInfo.do",{tableName:tableName},function(respone){
				if (respone.responeCode == '0') {
					model.setFormData(respone.data);
				}
			});
		} else {//清空
			model.resetDataForm();
		}
	});
	//代码生成
	$("body").on("click",".code-gen",function(){
		var id = $(this).data("id");
		window.location.href= model.path + "/codeGen.do?id=" + id;
	})
	// 校验
	$("#data-form").bootstrapValidator().on("success.form.bv", function(e) {// 提交
		e.preventDefault();
		var hasError = false;
		//校验
		$("#table-body > tr").find("td:eq(1),td:eq(4),td:eq(13)").find("input").each(function(i,v){//备注, 字段名，排序
			if (!$.trim($(v).val())) {
				$(v).parent().addClass("has-error");
				hasError = true;
			} else {
				$(v).parent().removeClass("has-error");
			}
		});
		
		if (hasError) {
			layer.msg("生成方案填写不完整");
			$('#data-form').bootstrapValidator('disableSubmitButtons', false);  
			return false;
		}
		var id = model.getFormData().id;
		var optUrl = model.path + "/save.do";
		if (id) {
			optUrl = model.path + "/update.do";
		}
		
		$.ajax({
			url:optUrl,
			contentType : "application/json;charset=UTF-8",
			data:JSON.stringify($('#data-form').serializeJSON()),
			success:function(respone){
				if (respone.responeCode == "0") {
					layer.msg("保存成功");
					model.tableRefresh();
					layer.closeAll('page');
				}
			}
		});
	});
	// 查询
	$("#search").click(function() {
		model.tableRefresh();
	});
	// 添加
	$("#add").click(function() {
		model.resetDataForm();
		model.openModal("<i class='fa fa-plus'>添加</i>");
	});
	// 编辑
	$("#edit").click(function() {
		var rows = $('#table').bootstrapTable("getSelections");
		if (rows.length == 0) {
			layer.msg("请选择一行");
		} else {
			$.get(model.path  + "/get.do",{id:rows[0].id},function(respone){
				if (respone.responeCode == "0") {
					model.openModal("<i class='fa fa-edit'>编辑</i>");
					model.setFormData(respone.data);
				}
			});
		}
	});
	//取消
	$("#form-panel-close").click(function(){
		layer.closeAll('page');
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
			formatter : function(value, row, index) {
				if (value == '1'){//基本增删改查
					value = "<span class='label label-success'>增删改查</span>";
				} else if (value == '2') {//树形结构
					value = "<span class='label label-danger'>树结构</span>"
				} 
				return value;
			}
			
		}  ,{
			field : 'updateDate',
			title : '更新时间',
			sortName : 'update_date',
			sortable : true,
			order : 'desc'
		},{
			field : 'oper',
			title : '操作',
			formatter : function(value, row, index) {
				var oper = "<a  href='#' class='text-success sf-permission-ctl code-gen' data-sf-permission='sys:gen:qry' data-id='" + row.id  + "'>生成</a>";
				return oper;
			}
		} ]
	});
});