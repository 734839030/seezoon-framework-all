$(function() {
    	var model = {
		path : adminContextPath + "/wechat/userinfo",
		resetDataForm : function() {
			$("#data-form").bootstrapValidator('resetForm', true);
			//表单默认值可以在这里设置
			way.set("model.form.data",null);
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
				    way.set("model.view.sex",$.getDictName('sex',respone.data.sex));
				    way.set("model.view.subscribe",$.getDictName('yes_no',respone.data.subscribe));
			})
		},
		init:function(){//需要初始化的功能
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
	//查看图像
	$("body").on("click", ".viewImages", function() {
		var url = $(this).data("url");
		layer.photos({
		    photos: {
		    	title: "图像", //相册标题
		    	data:[{src:url}]
		    },
		    anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
		  });
	});
	/**
	 * 查看
	 */
	$("body").on("click", ".view", function() {
		var id = $(this).data("id");
		model.setViewDataById(id);
		$('#modal-view').modal('toggle');
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
		}, 
			{
			field : 'nickname',
			title : '昵称',
            formatter : function(value, row, index) {
			    return "<a href='#' class='view text-success' data-id='" + row.id + "'>" + value + "</a>"
			 }
			},
			{
			field : 'sex',
			title : '性别',
			formatter : function(value, row, index) {
			    return $.getDictName('sex',value);
			 }
			},
			{
			field : 'country',
			title : '国家',
			},
			{
			field : 'province',
			title : '省份',
			},
			{
			field : 'city',
			title : '城市',
			},
			{
			field : 'headImgUrl',
			title : '图像',
			cellStyle:function cellStyle(value, row, index, field) {
				  return {
					    css: {"width": "50px", "padding": "3px","text-align":"center","cursor":'pointer'}
					  };
					},
			formatter : function(value, row, index) {
				if (value) {
					return "<image src='" + value  + "' class='viewImages' style='width:40px;height:40px;' data-url='" + value + "'/>";
				} else {
					return value;
				}
			}
			},
			{
			field : 'subscribe',
			title : '是否关注',
			formatter : function(value, row, index) {
			    return $.getDictName('yes_no',value);
			 }
			},
			{
			field : 'subscribeTime',
			title : '关注时间',
			sortName : 'subscribe_time',
			sortable : true,
			order : 'desc',
			},
			{
			field : 'subscribeScene',
			title : '关注场景',
			},
			{
			field : 'updateDate',
			title : '更新时间',
			},
		 ]
	});
	
});