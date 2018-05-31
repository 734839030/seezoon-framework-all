<!DOCTYPE html>
<html>
<head>@@include('../common/header.html',{"title":"${menuName}"})
<#if hasRichText>
@@include('../common/editor.html')
</#if>
<#if hasFileUpload>
@@include('../common/file.html')
</#if>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<div class="content-wrapper ml0">
			<section class="content-header">
				<h1>${menuName}</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i>模块名称</a></li>
					<li class="active">${menuName}</li>
				</ol>
			</section>
			<section class="content container-fluid">
				<div class="box box-primary">
					<div class="box-body">
						<form class="form-inline" way-scope="model.search">
							<#if hasSearch>
							<#list columnInfos as columnInfo>
								<#if columnInfo.search! == "1">
									<#if columnInfo.inputType! == "text">
										<div class="form-group">
										<input type="text" class="form-control" placeholder="${columnInfo.columnComment}" name="${columnInfo.javaFieldName}" way-data="${columnInfo.javaFieldName}" <#if columnInfo.maxLength?? >maxlength="${columnInfo.maxLength?c}"</#if>  >
										</div>
									</#if>
									<#if columnInfo.inputType! == "select">
									<div class="form-group">
										<#if columnInfo.dictType?? && columnInfo.dictType!="">
										<select class="form-control sf-select" way-data="${columnInfo.javaFieldName}"
											name="${columnInfo.javaFieldName}" data-sf-dict-type="${columnInfo.dictType}">
											<option value="">请选择</option>
										</select>
										<#else>
										<select class="form-control" way-data="${columnInfo.javaFieldName}"
											name="${columnInfo.javaFieldName}">
											<option value="">请选择</option>
										</select>
										</#if>
									</div>
									</#if>
									<#if columnInfo.inputType! == "number">
									<div class="form-group">
										<input type="number" class="form-control" placeholder="${columnInfo.columnComment}" name="${columnInfo.javaFieldName}" way-data="${columnInfo.javaFieldName}">
									</div>
									</#if>
									<#if columnInfo.inputType! == "checkbox">
										<#if columnInfo.dictType?? && columnInfo.dictType!="">
										<div class="form-group sf-checkbox" data-sf-input-name="${columnInfo.javaFieldName}" data-sf-dict-type="${columnInfo.dictType}">
										</div>
										<#else>
										<div class="form-group">
											<label class="checkbox-inline"> <input type="checkbox" required way-data="${columnInfo.javaFieldName}" name="${columnInfo.javaFieldName}" value="">${columnInfo.columnComment}</label>
										</div>
										</#if>
									</#if>
									<#if columnInfo.inputType! == "radio">
										<#if columnInfo.dictType?? && columnInfo.dictType!="">
										<div class="form-group sf-radio" data-sf-input-name="${columnInfo.javaFieldName}" data-sf-dict-type="${columnInfo.dictType}">
										</div>
										<#else>
										<div class="form-group">
											<label class="radio-inline"> <input type="radio" required way-data="${columnInfo.javaFieldName}" name="${columnInfo.javaFieldName}" value="">${columnInfo.columnComment}</label>
										</div>
										</#if>
									</#if>
									<#if columnInfo.inputType! == "date">
									<div class="form-group">
										<label>${columnInfo.columnComment}</label> <input type="text" class="form-control date search" placeholder="${columnInfo.columnComment}" name="${columnInfo.javaFieldName}"  way-data="${columnInfo.javaFieldName}">
									</div>
									</#if>
								</#if>
							</#list>
							<button type="button" class="btn btn-default sf-permission-ctl" id="search" data-sf-permission="${moduleName}:${functionName}:qry">
								<i class="fa fa-search"></i>
							</button>
							</#if>
							<div class="btn-group pull-right">
								<button type="button" class="btn btn-primary sf-permission-ctl" id="add" data-sf-permission="${moduleName}:${functionName}:save">
									<i class="fa fa-plus"></i>
								</button>
								<button type="button" class="btn btn-info sf-permission-ctl" id="edit" data-sf-permission="${moduleName}:${functionName}:update">
									<i class="fa fa-edit"></i>
								</button>
								<button type="button" class="btn btn-danger sf-permission-ctl" id="delete" data-sf-permission="${moduleName}:${functionName}:delete">
									<i class="fa fa-trash-o"></i>
								</button>
							</div>
						</form>
					</div>
				</div>
				<div class="box">
					<div class="box-body">
						<!-- table -->
						<table id="table"></table>
					</div>
				</div>
			</section>
		</div>
	</div>
	<!-- 添加与修改start -->
	<div class="modal fade" id="form-panel" way-scope="model.form">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" way-data="title" way-html="true"></h4>
				</div>
				<form class="form-horizontal" way-scope="data" id="data-form">
					<div class="modal-body">
						<#list columnInfos as columnInfo>
							<#if columnInfo.inputType! == "hidden">
							<input type="hidden" way-data="${columnInfo.javaFieldName}" name="${columnInfo.javaFieldName}">
							</#if>
						</#list>
						<#list columnInfos as columnInfo>
							<#if columnInfo.inputType! != "hidden" &&  columnInfo.javaFieldName != "createBy" && columnInfo.javaFieldName != "createDate" && columnInfo.javaFieldName != "updateBy" && columnInfo.javaFieldName != "updateDate">
							<#if columnInfo.inputType! == "text">
								<div class="form-group">
									<label class="col-sm-2 control-label">${columnInfo.columnComment}<#if columnInfo.nullable! !="1"><font class="text-red">*</font></#if></label>
									<div class="col-sm-5">
										<input type="text" class="form-control" way-data="${columnInfo.javaFieldName}" <#if columnInfo.maxLength??>maxlength="${columnInfo.maxLength?c}"</#if>
											name="${columnInfo.javaFieldName}" <#if columnInfo.nullable! !="1"> required</#if> >
									</div>
								</div>
							</#if>
							<#if columnInfo.inputType! == "select">
								<div class="form-group">
									<label class="col-sm-2 control-label">${columnInfo.columnComment}<#if columnInfo.nullable! !="1"><font class="text-red">*</font></#if></label>
									<div class="col-sm-5">
										<#if columnInfo.dictType?? && columnInfo.dictType!="">
										<select class="form-control sf-select" way-data="${columnInfo.javaFieldName}"
											name="${columnInfo.javaFieldName}" data-sf-dict-type="${columnInfo.dictType}" <#if columnInfo.nullable! !="1">required</#if>>
											<option value="">请选择</option>
										</select>
										<#else>
										<select class="form-control" way-data="${columnInfo.javaFieldName}" <#if columnInfo.nullable! !="1">required</#if>
											name="${columnInfo.javaFieldName}">
											<option value="">请选择</option>
										</select>
										</#if>
									</div>
								</div>
							</#if>
							<#if columnInfo.inputType! == "zhengshu">
								<div class="form-group">
									<label class="col-sm-2 control-label">${columnInfo.columnComment}<#if columnInfo.nullable! !="1"><font class="text-red">*</font></#if></label>
									<div class="col-sm-5">
										<input type="number" class="form-control" way-data="${columnInfo.javaFieldName}" 
											name="${columnInfo.javaFieldName}"  <#if columnInfo.nullable! !="1"> required</#if> >
									</div>
								</div>
							</#if>
							<#if columnInfo.inputType! == "xiaoshu">
								<div class="form-group">
									<label class="col-sm-2 control-label">${columnInfo.columnComment}<#if columnInfo.nullable! !="1"><font class="text-red">*</font></#if></label>
									<div class="col-sm-5">
										<input type="text" class="form-control" way-data="${columnInfo.javaFieldName}" pattern="^-{0,1}\d+(\.\d{0,2})?$" data-bv-regexp-message="请按提示格式输入"
											name="${columnInfo.javaFieldName}"  <#if columnInfo.nullable! !="1"> required</#if> >
									</div>
								</div>
							</#if>
							<#if columnInfo.inputType! == "checkbox">
								<div class="form-group">
									<label class="col-sm-2 control-label">${columnInfo.columnComment}<#if columnInfo.nullable! !="1"><font class="text-red">*</font></#if></label>
										<#if columnInfo.dictType?? && columnInfo.dictType!="">
										<div class="col-sm-5 sf-checkbox" data-sf-input-name="${columnInfo.javaFieldName}" data-sf-dict-type="${columnInfo.dictType}"  <#if columnInfo.nullable! !="1"> data-sf-required="required"</#if> >
										</div>
										<#else>
										<div class="col-sm-5">
											<label class="checkbox-inline"> <input type="checkbox" required way-data="${columnInfo.javaFieldName}" name="${columnInfo.javaFieldName}" value="" <#if columnInfo.nullable! !="1"> required</#if>>${columnInfo.columnComment}</label>
										</div>
										</#if>
								</div>
							</#if>
							<#if columnInfo.inputType! == "radio">
								<div class="form-group">
									<label class="col-sm-2 control-label">${columnInfo.columnComment}<#if columnInfo.nullable! !="1"><font class="text-red">*</font></#if></label>
										<#if columnInfo.dictType?? && columnInfo.dictType!="">
										<div class="col-sm-5 sf-radio" data-sf-input-name="${columnInfo.javaFieldName}" data-sf-dict-type="${columnInfo.dictType}"  <#if columnInfo.nullable! !="1"> data-sf-required="required"</#if> >
										</div>
										<#else>
										<div class="col-sm-5">
											<label class="radio-inline"> <input type="radio" required way-data="${columnInfo.javaFieldName}" name="${columnInfo.javaFieldName}" value="" <#if columnInfo.nullable! !="1"> required</#if>>${columnInfo.columnComment}</label>
										</div>
										</#if>
								</div>
							</#if>
							<#if columnInfo.inputType! == "date">
								<div class="form-group">
									<label class="col-sm-2 control-label">${columnInfo.columnComment}<#if columnInfo.nullable! !="1"><font class="text-red">*</font></#if></label>
									<div class="col-sm-5">
										<input type="text" class="form-control date" way-data="${columnInfo.javaFieldName}" 
											name="${columnInfo.javaFieldName}" <#if columnInfo.nullable! !="1">id="${columnInfo.javaFieldName}"</#if> >
									</div>
								</div>
							</#if>
							<#if columnInfo.inputType! == "textarea">
								<div class="form-group">
									<label class="col-sm-2 control-label">${columnInfo.columnComment}<#if columnInfo.nullable! !="1"><font class="text-red">*</font></#if></label>
									<div class="col-sm-10">
										<textarea class="form-control" rows="3" way-data="${columnInfo.javaFieldName}" name="${columnInfo.javaFieldName}" <#if columnInfo.maxLength??>maxlength="${columnInfo.maxLength?c}"</#if>  <#if columnInfo.nullable! !="1">required</#if> ></textarea>
									</div>
								</div>
							</#if>
							<#if columnInfo.inputType! == "richtext">
								<div class="form-group">
									<label class="col-sm-2 control-label">${columnInfo.columnComment}<#if columnInfo.nullable! !="1"><font class="text-red">*</font></#if></label>
									<div class="col-sm-10">
										<textarea class="form-control" rows="3" way-data="${columnInfo.javaFieldName}" style="width:100%;height:400px;visibility:hidden;" name="${columnInfo.javaFieldName}" <#if columnInfo.maxLength??>maxlength="${columnInfo.maxLength?c}"</#if>  <#if columnInfo.nullable! !="1">required</#if> ></textarea>
									</div>
								</div>
							</#if>
							<#if columnInfo.inputType! == "picture">
								<div class="form-group">
									<label class="col-sm-2 control-label">${columnInfo.columnComment}<#if columnInfo.nullable! !="1"><font class="text-red">*</font></#if></label>
									<div class="col-sm-10">
										<div id="${columnInfo.javaFieldName}UploadFileContainer">
										</div>
										<label for="${columnInfo.javaFieldName}Upload" class="btn btn-default"><i class="fa fa-cloud-upload"></i>上传</label> 
										<input type="file" name="files" id="${columnInfo.javaFieldName}Upload" accept="image/*" multiple="multiple" style="display: none;">
									</div>
								</div>
							</#if>
							<#if columnInfo.inputType! == "file">
								<div class="form-group">
									<label class="col-sm-2 control-label">${columnInfo.columnComment}<#if columnInfo.nullable! !="1"><font class="text-red">*</font></#if></label>
									<div class="col-sm-10">
										<div id="${columnInfo.javaFieldName}UploadFileContainer">
										</div>
										<label for="${columnInfo.javaFieldName}Upload" class="btn btn-default"><i class="fa fa-cloud-upload"></i>上传</label> 
										<input type="file" name="files" id="${columnInfo.javaFieldName}Upload"  multiple="multiple" style="display: none;">
									</div>
								</div>
							</#if>
							</#if>
						</#list>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default pull-left"
							data-dismiss="modal">
							<i class="fa fa-times"></i> 取消
						</button>
						<button type="submit" class="btn btn-primary">
							<i class="fa fa-save"></i> 保存
						</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- 添加与修改end -->
	<!--查看  start-->
	<div class="modal fade" id="modal-view" role="dialog"
		way-scope="model.view">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">${menuName}</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal">
							<#list columnInfos as columnInfo>
								<#if columnInfo.inputType! != "hidden" && columnInfo.javaFieldName != "createBy" && columnInfo.javaFieldName != "updateBy">
								<div class="form-group">
								<label class="col-sm-2 control-label">${columnInfo.columnComment}:</label>
								    <div class="col-sm-10">
									<#if columnInfo.inputType! == "picture" || columnInfo.inputType! == "file">
									<p class="form-control-static" id="sf-view-${columnInfo.javaFieldName}-file"></p>
									<#else>
									<p class="form-control-static" way-data="${columnInfo.javaFieldName}" ${(columnInfo.inputType! == "richtext") ?string("way-html='true'","")}></p>
									</#if>
									</div>
								</div>	
								</#if>
							</#list>
					</form>
				</div>
			</div>
		</div>
	</div>
	@@include('../common/common-js.html')
	<script src="/admin/js/${moduleName}/${functionName}.js"></script>
</body>
</html>