$(function() {
	model = {
		path : adminContextPath + "/user",
		//递归菜单
		handLeftMenu:function(allMenus,parentId){
			var menuHtml = "";
			var active = "";
			$.each(allMenus,function(i,v){//
				if (v.parentId == parentId) {//先处理第一级的
					 if (v.type == '0') {//目录
						 menuHtml +="<li class='treeview " + active+ "'>" +
						 "<a href='#'>" +
				            "<i class='"+ v.icon +"'></i> <span>" + v.name+ "</span>"+
				            "<span class='pull-right-container'>"+
				             " <i class='fa fa-angle-left pull-right'></i>"+
				           " </span>"+
				          "</a>" +
				          "<ul class='treeview-menu'>" +
				          model.handLeftMenu(allMenus,v.id) + 
				          "</ul>" +
				          "</li>";
					 } else if (v.type == '1') {//菜单
						 if (!v.target) {
							 v.target = 'main';
						 }
						 if (!v.href) {
							 v.href = 'javascript:void(0)';
						 }
						 menuHtml += "<li class='" + active + "'>" +
				          "<a href='" + v.href + "' target='" + v.target  + "'>" +
				            "<i class='" + v.icon + "'></i> <span>" + v.name+ "</span>" +
				          "</a>" +
				        "</li>";
					 }
				}
			});
			return menuHtml;
		},
		getUserInfo:function(callback){
			//个人中心
			$.post(model.path + "/getUserInfo.do",function(respone){
				var data = respone.data;
				callback(data);
		    });
		},
		resetFormValidateStatus:function() {
			$("#data-form,#passoword-form").bootstrapValidator('resetForm', false);
		},
		//设置个人中心数据
		setFormData:function(data){
			way.set("model.form.data",data);
		},
		userCenterLayerIndex:0,
		init:function(){
			//iframe的高度100%  父容器必须是实际高度
			$("#main-content").height($("#main-content").height()-150);
			//菜单渲染
			$.post(model.path + "/getUserMenus.do",function(respone){
				var menu = "";
				var data = respone.data;
				//处理菜单
				$("#left-menu").empty().append(model.handLeftMenu(data,'0'));
				$("#left-menu li:eq(0)").addClass("active");
		    });
			//右上角
			model.getUserInfo(function(data){
				if (data) {
					$("#userName").text(data.name);
					$("#loginName").text(data.loginName + "[" + data.deptName + "]");
				}
				model.setFormData(data);
			});
		}
	}
	model.init();
	/**
	 * 左侧菜单点击选中效果
	 */
	$("body").on("click","#left-menu > li", function() {
		var $this = $(this);
		$this.addClass("active");
		$this.siblings().removeClass("active");
	});
	//退出
	$("#login-out").click(function(){
		 $.post(model.path + "/logout.do",function(respone){
				window.location.href="/pages/login.html";
		});
	});
	//个人中心
	$("#user-center").click(function(){
		model.resetFormValidateStatus();
		$('#user-center-tabs a[href="#tab_info"]').tab('show');
		//个人中心
		model.userCenterLayerIndex = layer.open({
		 	  title:'个人中心',
              shadeClose:true,
		 	  icon: 1,
		 	  offset: '100px',
		 	  area: ['640px'],
			  type: 1, 
			  content:  $("#user-center-wrapper") 
			});
	});
	// 校验
	$("#data-form").bootstrapValidator().on("success.form.bv", function(e) {// 提交
		e.preventDefault();
		$.post(model.path + "/updateInfo.do", $("#data-form").serialize(), function(respone) {
			if (respone.responeCode == '0') {
				layer.msg("保存成功");
				layer.close(model.userCenterLayerIndex);
			}
		});
	});
	// 密码校验
	$("#passoword-form").bootstrapValidator({
		fields:{
			password:{
				validators:{
					identical:{
						message:'两次密码不一致',
						field:'conFirmPassword'
					}
				}
			},
			conFirmPassword:{
				validators:{
					identical:{
						message:'两次密码不一致',
						field:'password'
					}
				}
			},
			oldPassword:{
				threshold:5,
				validators:{
					remote: {
	                    url: model.path + "/checkPassword.do",//验证原密码
	                    message: '原密码错误',//提示消息
	                    type:'post',
	                    delay: 500,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
	                },
				}
			}
		}
	}).on("success.form.bv", function(e) {// 提交
		e.preventDefault();
		$.post(model.path + "/updatePwd.do", $("#passoword-form").serialize(), function(respone) {
			if (respone.responeCode == '0') {
				layer.msg("保存成功");
				layer.close(model.userCenterLayerIndex);
				$("#passoword-form")[0].reset();
			}
		});
	});
});