$(function() {
    <#list columnInfos as columnInfo>
    <#if columnInfo.inputType! == "richtext">
    	var editor${columnInfo.javaFieldName ? cap_first};
	KindEditor.ready(function(K) {
        editor${columnInfo.javaFieldName ? cap_first} = K.create("textarea[name='${columnInfo.javaFieldName}']",{
	        	syncType:'auto',//无效
	    		items:items,
	    		zIndex:99999999,
	    		uploadJson : adminContextPath + '/file/k_upload_image.do'
        });
	});
    </#if>
    </#list>
	var model = {
		path : adminContextPath + "/${moduleName}/${functionName}",
		resetDataForm : function() {
			$("#data-form").bootstrapValidator('resetForm', true);
			//表单默认值可以在这里设置
			way.set("model.form.data",null);
			<#list columnInfos as columnInfo>
		    <#if columnInfo.inputType! == "richtext">
		    if (	editor${columnInfo.javaFieldName ? cap_first}) {
		    	editor${columnInfo.javaFieldName ? cap_first}.html('');
		    }
		    </#if>
		     <#if columnInfo.inputType! == "file" || columnInfo.inputType! == "picture">
		     	$("#${columnInfo.javaFieldName}UploadFileContainer").empty();
		    </#if>
   			</#list>
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
				<#list columnInfos as columnInfo>
			    <#if columnInfo.inputType! == "richtext">
			    	editor${columnInfo.javaFieldName ? cap_first}.html(respone.data.${columnInfo.javaFieldName});
			    </#if>
			    <#if columnInfo.inputType! == "picture" || columnInfo.inputType! == "file">
			    if (respone.data.${columnInfo.javaFieldName}Array) {
					 var files = [];
					 $.each(respone.data.${columnInfo.javaFieldName}Array,function(i,v){
						 files.push({"inputName":"${columnInfo.javaFieldName}","path":v.relativePath,"url":v.fullUrl,"fileName":v.originalFilename});
					 });
					 $("#sf-${(columnInfo.inputType! == "picture")?string("image","file")}-template").tmpl(files).appendTo($("#${columnInfo.javaFieldName}UploadFileContainer"));
			 	} 
			    </#if>
	   			</#list>
			})
		},
		setViewDataById:function(id){
			$.get(this.path + "/get.do",{id:id},function(respone){
				way.set("model.view",respone.data);
				<#list columnInfos as columnInfo>
				    <#if columnInfo.dictType?? && columnInfo.dictType!= "">
				    way.set("model.view.${columnInfo.javaFieldName}",$.getDictName('${columnInfo.dictType}',respone.data.${columnInfo.javaFieldName}));
				    </#if>
					<#if columnInfo.inputType! == "picture" || columnInfo.inputType! == "file">
					if (respone.data.${columnInfo.javaFieldName}Array) {
					 var files = [];
					 $.each(respone.data.${columnInfo.javaFieldName}Array,function(i,v){
						 files.push({"url":v.fullUrl,"fileName":v.originalFilename});
					 });
					 $("#sf-view-${(columnInfo.inputType! == "picture")?string("image","file")}-template").tmpl(files).appendTo($("#sf-view-${columnInfo.javaFieldName}-file"));
					 }
					</#if>
				</#list>
			})
		},
		init:function(){//需要初始化的功能
		}
	};
	model.init();
	// 校验
	$("#data-form").bootstrapValidator().on("success.form.bv", function(e) {// 提交
		e.preventDefault();
		<#list columnInfos as columnInfo>
		<#if columnInfo.inputType! == "richtext">
		 editor${columnInfo.javaFieldName ? cap_first}.sync();
		 <#if columnInfo.nullable! !="1">>
		 if (!$("textarea[name='${columnInfo.javaFieldName}']").val()){
			layer.msg("${columnInfo.columnComment}不能为空");
			$('#data-form').bootstrapValidator('disableSubmitButtons', false);  
			return false;
		 }
		 <#if columnInfo.maxLength??>
		 if ($("textarea[name='${columnInfo.javaFieldName}']").val().length > ${columnInfo.maxLength?c}){
			layer.msg("${columnInfo.columnComment}长度大于${columnInfo.maxLength?c}");
			$('#data-form').bootstrapValidator('disableSubmitButtons', false);  
			return false;
		 }
		 </#if>
		 </#if>
		</#if>
	   	</#list>
		<#list columnInfos as columnInfo>
		<#if columnInfo.inputType! == "date" && columnInfo.nullable! !="1" && columnInfo.javaFieldName != "createDate" && columnInfo.javaFieldName != "updateDate">
		if(!$("#${columnInfo.javaFieldName}").val()){
			layer.msg("${columnInfo.columnComment}不能为空");
			$('#data-form').bootstrapValidator('disableSubmitButtons', false);  
			return false;
		} 
		</#if>
		<#if (columnInfo.inputType! == "file" || columnInfo.inputType! == "picture") && columnInfo.nullable! !="1">
		if($("input[name='${columnInfo.javaFieldName}']").length == 0){
			layer.msg("${columnInfo.columnComment}不能为空");
			$('#data-form').bootstrapValidator('disableSubmitButtons', false);  
			return false;
		} 
		</#if>
		</#list>
		
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
	<#assign listIndex=0>
	// 列表
	$('#table').bootstrapTable({
		url : model.path + '/qryPage.do',
		columns : [ {
			checkbox : true
		}, 
		<#list columnInfos as columnInfo>
			<#if columnInfo.list! == "1">
			{
			field : '${columnInfo.javaFieldName}',
			title : '${columnInfo.columnComment}',
			<#if columnInfo.sortable! =="1">
			sortName : '${columnInfo.dbColumnName}',
			sortable : true,
			order : 'desc',
			</#if>
			<#if listIndex == 0>
            formatter : function(value, row, index) {
			    return "<a href='#' class='view text-success' data-id='" + row.id + "'>" + ${(columnInfo.dictType?? && columnInfo.dictType!= "")?string("$.getDictName('${columnInfo.dictType}',value)","value")} + "</a>"
			 }
			 </#if>
			 <#if columnInfo.dictType?? && columnInfo.dictType!= "" && listIndex != 0>
			formatter : function(value, row, index) {
			    return $.getDictName('${columnInfo.dictType}',value);
			 }
			 </#if>
			},
			<#assign listIndex=listIndex + 1>
			</#if>
		</#list>
		 ]
	});
	<#list columnInfos as columnInfo>
		<#if columnInfo.inputType! == "picture">
		//图片上传
		 $('#${columnInfo.javaFieldName}Upload').fileupload({
			 url:adminContextPath + "/file/uploadBatchImage.do",
			 type:'POST',
			 change: function (e, data) {
				for (var i=0;i< data.files.length;i++) {
					var file= data.files[i];
					if (file.size > 2 * 1024 * 1024) {
						layer.msg(file.name + " 文件大小超过2M,请重新选择");
						return false;
					}
					 // 开头为image/
					var reg = /^image\//
				    if (!reg.test(file.type)) {
					    	layer.msg(file.name + " 不是图片格式");
				    		return false;
				    }
				}
			    //当前传了多少个 可以自己写校验
			    var cnt = $("input[name='${columnInfo.javaFieldName}']").length;
			    //本次传了多少个
			    var chooseFilecnt = data.files.length;
			  },
			 done: function (e, response) {//设置文件上传完毕事件的回调函数 
				 if (response.result.responeCode == '0') {
					 var files = [];
					 $.each(response.result.data,function(i,v){
						 files.push({"inputName":"${columnInfo.javaFieldName}","path":v.relativePath,"url":v.fullUrl,"fileName":v.originalFilename});
					 });
					 $("#sf-image-template").tmpl(files).appendTo($("#${columnInfo.javaFieldName}UploadFileContainer"));
				 } 
			 }, 
		 });
		</#if>
		<#if columnInfo.inputType! == "file">
		 //文件上传
		 $('#${columnInfo.javaFieldName}Upload').fileupload({
			 url:adminContextPath + "/file/uploadBatchFile.do",
			 type:'POST',
			 change: function (e, data) {
				for (var i=0;i< data.files.length;i++) {
					var file= data.files[i];
					if (file.size > 2 * 1024 * 1024) {
						layer.msg(file.name + " 文件大小超过2M,请重新选择");
						return false;
					}
				}
			    //当前传了多少个 可以自己写校验
			    var cnt = $("input[name='${columnInfo.javaFieldName}']").length;
			    //本次传了多少个
			    var chooseFilecnt = data.files.length;
			  },
			 done: function (e, response) {//设置文件上传完毕事件的回调函数 
				 if (response.result.responeCode == '0') {
					 var files = [];
					 $.each(response.result.data,function(i,v){
						 files.push({"inputName":"${columnInfo.javaFieldName}","path":v.relativePath,"url":v.fullUrl,"fileName":v.originalFilename});
					 });
					 $("#sf-file-template").tmpl(files).appendTo($("#${columnInfo.javaFieldName}UploadFileContainer"));
				 } 
			 }, 
		 });
		</#if>
	</#list>
	
});
