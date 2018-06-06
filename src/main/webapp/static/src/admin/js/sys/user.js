$(function() {
	var model = {
		path : adminContextPath + "/sys/user",
		resetDataForm : function() {
			$("#data-form").bootstrapValidator('resetForm', true);
			way.set("model.form.data",{
				status:'1',
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
		setFormDataById:function(id,callback){
			$.get(this.path + "/get.do",{id:id},function(respone){
				way.set("model.form.data",respone.data);
				if (callback) {
					callback(respone.data);
				}
			})
		},
		init:function(){
			//渲染角色
			$.post(adminContextPath + "/sys/role/qryAllWithScope.do",function(respone){
				$("#roles-temlate").tmpl(respone.data).appendTo($("#roles"));
			});
			//左侧部门树
			var setting = {
					data : {
						simpleData : {
							enable : true,
							idKey : "id",
							pIdKey : "parentId",
						}
					},
					callback : {
						onClick : function(event, treeId, treeNode) {
							way.set("model.search.deptId",treeNode.id);
							model.tableRefresh();
						}
					}
				};
				$.post(adminContextPath + "/sys/dept/qryAllWithScope.do", function(respone) {
					//选择上级tree
					$.fn.zTree.init($("#deptMenuTree"), setting,respone.data);
					var treeObj = $.fn.zTree.getZTreeObj("deptMenuTree");
					treeObj.expandAll(true);
				});
		}
	};
	model.init();
	// 校验
	$("#data-form").bootstrapValidator({
		fields:{
			password:{
				validators:{
					identical:{
						message:'两次密码不一致',
						field:'conFirmPassword'
					},
					different:{
						message:'密码不能和用户名相同',
						field:'loginName'
					},
					callback: {
						message:'新用户必须填密码',
						callback:function(value, validator){//框架验证完回调
							//修改不做处理，新增value 为空即为false
							return model.getFormData().id?true:$.trim(value) != '';
						}
					}
				}
			},
			conFirmPassword:{
				validators:{
					identical:{
						message:'两次密码不一致',
						field:'password'
					},
					different:{
						message:'密码不能和用户名相同',
						field:'loginName'
					},
					callback: {
						message:'新用户必须填密码',
						callback:function(value, validator){//框架验证完回调
							//修改不做处理，新增value 为空即为false
							return model.getFormData().id?true:$.trim(value)!='';
						}
					}
				}
			},
			loginName:{
				threshold:5,
				validators:{
					remote: {
	                    url: model.path + "/checkLoginName.do",//验证登录用户名
	                    data:{
		                    	id:function(){
		                    		return model.getFormData().id;
		                    },
		                    loginName:function(){
		                    		return model.getFormData().loginName;
		                    	},
	                    },
	                    message: '用户名已存在',//提示消息
	                    type:'post',
	                    delay: 500,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
	                },
				}
			}
		}
	}).on("success.form.bv", function(e) {// 提交
		e.preventDefault();
		//请选择角色
//		if ($("input[name='roleIds']:checked").length == 0) {
//			layer.msg("请勾选角色");
//			$('#data-form').bootstrapValidator('disableSubmitButtons', false);  
//			return false;
//		}
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
	//选择部门
	$("#dept-input").click(function(){
		//readOnly 不会触发bootstrap valiator 重新校验，所以选择部门后，手动修改校验状态
		$.seezoon.chooseDept(way.get("model.form.data.deptId"),function(treeNode){
			way.set("model.form.data.deptId",treeNode.id);
			way.set("model.form.data.deptName",treeNode.name);
			$("#data-form").data('bootstrapValidator').updateStatus('deptName', 'VALID');
			return true;
		},function(){
			way.set("model.form.data.deptId",null);
			way.set("model.form.data.deptName",null);
			$("#data-form").data('bootstrapValidator').updateStatus('deptName', 'INVALID');
		},true);
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
			model.setFormDataById(rows[0].id,function(data){
				$.checkBoxCheck($("input[name='roleIds']"),data.roleIds);
			});
			model.setFormTitle("<i class='fa fa-edit'>编辑</i>");
			$("#form-panel").modal('toggle');
		}
	});
	//解锁
	$("#unLock-user").dblclick(function(){
		var id = $("#form-panel input[name='id']").val();
		if (id) {
			$.post(model.path + "/unlock.do",{id:id},function(respone){
				if (respone.responeCode == '0') {
					layer.msg("解锁成功");
				}
			});
		}
	});
	//设置状态
	$("body").on("click", ".setStatus", function() {
		var id = $(this).data("id");
		var status = $(this).data("status");
		$.post(model.path + "/setStatus.do",{id:id,status:status},function(respone){
			model.tableRefresh();
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
			field : 'loginName',
			title : '用户名',
		},{
			field : 'photoFullUrl',
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
		}, {
			field : 'updateDate',
			title : '更新时间',
			sortName : 'u.update_date',
			sortable : true,
			order : 'desc'
		},{
			field : 'oper',
			title : '操作',
			formatter : function(value, row, index) {
				var oper;
				if (row.status == '0') {//禁用状态
					oper = "<a  href='#' class='text-success sf-permission-ctl setStatus' data-sf-permission='sys:user:update' data-id='" + row.id  + "' data-status='1'>启用</a>";
				} else {//正常状态
					oper = "<a  href='#' class='text-danger sf-permission-ctl setStatus' data-sf-permission='sys:user:update' data-id='" + row.id  + "' data-status='0'>禁用</a>";
				} 
				return oper;
			}
		}]
	});
	//图像上传
	 $('#userImageUpload').fileupload({
		 url:adminContextPath + "/file/uploadImage.do",
		 type:'POST',
		 formData:null,
		 change: function (e, data) {
			 var file = data.files[0];
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
		  },
		 done: function (e, response) {//设置文件上传完毕事件的回调函数 
			 if (response.result.responeCode == '0') {
				 way.set("model.form.data.photo",response.result.data.relativePath);
				 way.set("model.form.data.photoFullUrl",response.result.data.fullUrl);
			 } 
       }, 
	 });
});