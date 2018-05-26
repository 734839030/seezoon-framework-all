$(function() {
	var model = {
		path : adminContextPath + "/sys/dict",
		resetDataForm : function() {
			$("#data-form").bootstrapValidator('resetForm', true);
			way.set("model.form.data",{
				status:'1',
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
			})
		},
		setViewDataById:function(id){
			$.get(this.path + "/get.do",{id:id},function(respone){
				way.set("model.view",respone.data);
			})
		},
		init:function(){
			//类型模板
			$.get(this.path + "/getTypes.do",function(respone){
				var data = [];
				$.each(respone.data,function(i,v){
					data.push({
						code:v,
						name:v
					});
				});
				$("#sf-select-template").tmpl(data).appendTo($("#search-type"));
				$('#search-type').selectpicker('refresh');
			})
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
	// 添加
	$("#add").click(function() {
		model.resetDataForm();
		model.setFormTitle("<i class='fa fa-plus'>添加</i>");
		$("#form-panel").modal('toggle');
	});
	//添加同类字典
	$("body").on("click", ".addDict", function(){
		model.resetDataForm();
		way.set("model.form.data.type",$(this).data("type"));
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
		}, {
			field : 'code',
			title : '编码',
		}, 
		{
			field : 'sort',
			title : '顺序',
			sortName : 'sort',
			sortable : true,
			order : 'desc'
		}, 
		{
			field : 'status',
			title : '状态',
			formatter : function(value, row, index) {
				if (value == '0') {//无效
					value = "<span class='label label-danger'>无效</span>";
				} else if (value == '1'){//有效
					value = "<span class='label label-success'>有效</span>";
				} 
				return value;
			}
		}, 
		{
			field : 'type',
			title : '类型',
			sortName : 'type',
			sortable : true,
			order : 'desc'
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
				var oper =  "<a  href='#' class='text-success addDict sf-permission-ctl' data-sf-permission='sys:dict:save' data-type='" + row.type+ "' >添加同类字典</a>";
				return oper;
			}
		}]
	});
});