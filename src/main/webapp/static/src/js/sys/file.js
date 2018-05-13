$(function() {
	var model = {
		path : adminContextPath + "/sys/file",
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
		tableRefresh : function() {
			$('#table').bootstrapTable("refresh");
		},
	};
	// 查询
	$("#search").click(function() {
		model.tableRefresh();
	});
	//下载
	$("body").on("click",".down",function(){
		var relativePath = $(this).data("relative-path");
		window.location.href= adminContextPath + "/file/down.do?relativePath=" + relativePath;
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
	//图像上传
	 $('#fileUpload').fileupload({
		 url:adminContextPath + "/file/uploadFile.do",
		 type:'POST',
		 formData:null,
		 change: function (e, data) {
		    if (data.files[0].size > 10 * 1024 * 1024) {
		    		layer.msg(data.files[0].name + " 文件大小超过10M,请重新选择");
		    		return false;
		    }
		  },
		 done: function (e, response) {//设置文件上传完毕事件的回调函数 
			 if (response.result.responeCode == '0') {
				 layer.msg(response.result.data.originalFilename + " 上传成功");
				 model.tableRefresh();
			 } 
         }, 
	 });
	// 列表
	$('#table').bootstrapTable({
		url : model.path + '/qryPage.do',
		sortName:'update_date', //默认顺序
		sortOrder:'desc',
		columns : [ {
			checkbox : true
		}, {
			field : 'name',
			title : '名称',
		}, {
			field : 'contentType',
			title : '文件类型',
		}, {
			field : 'fileSize',
			title : '文件大小(B)'
		}, {
			field : 'updateDate',
			title : '更新时间',
			sortName : 'update_date',
			sortable : true,
			order : 'desc'
		},{
			field : 'oper',
			title : '操作',
			formatter : function(value, row, index) {
				var oper =  "<a  href='" + row.fullUrl +"' target='_blank'  class='text-success'>预览</a> | ";
				oper = oper +  "<a  href='#' class='text-success down' data-relative-path='" + row.relativePath  + "' >下载</a> ";
				return oper;
			}
				
		}]
	});
});