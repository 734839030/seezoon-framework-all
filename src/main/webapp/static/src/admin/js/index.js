$(function() {
	var singePage = !localStorage.getItem("mutiPage") || localStorage.getItem("mutiPage") == '0';
	//iframe的高度100%  父容器必须是实际高度
	$("#main-content").height($(".main-sidebar").height() - $(".main-footer").height()-42);
	if (!singePage) {
		//多页签
		$("iframe").height($(".main-sidebar").height() - $(".main-footer").height()-90);
	}
	model = {
		path : adminContextPath + "/user",
		//递归菜单
		handLeftMenu:function(allMenus,parentId){
			var menuHtml = "";
			$.each(allMenus,function(i,v){//
				if (v.parentId == parentId) {//先处理第一级的
					 if (v.type == '0') {//目录
						 menuHtml +="<li class='treeview'>" +
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
						 } else {
							 var regx = /^https?:/;
							 if (!regx.test(v.href)) {
								 v.href = "/admin" + v.href;
							 }
						 }
						 menuHtml += "<li>" +
				          "<a class='menu' href='javascript:void(0)' data-href='" + v.href + "' target='" + v.target  + "'>" +
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
		resetForm:function() {
			//false 表示不清除数据
			$("#data-form,#passoword-form").bootstrapValidator('resetForm', false);
			$("#passoword-form")[0].reset();
		},
		//设置个人中心数据
		setFormData:function(data){
			way.set("model.form.data",data);
		},
		getFormData : function() {
			var data =  way.get("model.form.data");
			return data?data:{};
		},
		userCenterLayerIndex:0,
		//按钮权限初始化
		bntPermissionData:function(data){
			sessionStorage.clear();
			if (data) {
				$.each(data,function(i,v){
					if (v.permission) {
						var pers = $.trim(v.permission).split(",");
						$.each(pers,function(i,v){
							if (v) {
								sessionStorage.setItem("model.permission." + v,"1");
							}
						});
					}
				});
			}
		},
		init:function(){
			if (singePage) {
				$("#single-page").show();
			} else {
				$("#layui-tab").show();
			}
			//菜单渲染
			$.post(model.path + "/getUserMenus.do",function(respone){
				var menu = "";
				var data = respone.data;
				//按钮权限
				model.bntPermissionData(data);
				//处理菜单
				$("#left-menu").empty().append(model.handLeftMenu(data,'0'));
				$("#left-menu li:eq(0)").addClass("active");
		    });
			//右上角
			model.getUserInfo(function(data){
				if (data) {
					if (data.photoFullUrl) {
						$(".user-photo").attr("src",data.photoFullUrl);
					}
					$("#userName").text(data.name);
					$("#loginName").text(data.loginName +  (data.deptName ? "[" + data.deptName + "]":""));
					$("#lastLoginTime").text(data.lastLoginTime);
					//$("#lastLoginIp").text(data.lastLoginIp);
					$("#lastLoginIp").text(data.lastLoginIp  +  (data.lastLoginArea ? "(" + data.lastLoginArea + ")":"") );
				}
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
	//菜单处理
	$("body").on("click",".menu",function(){
		var $this = $(this);
		var href = $(this).data("href");
		var target = $(this).attr("target");
		$(".menu").parent().removeClass("active");
		$this.parent().addClass("active");
		if ("main" != target) {
			window.open(href,target);
			//window.location.href = href;
		} else { 
			if (singePage) {
				if ("main" == target) {
					window.main.location.href = href;
				} else {
					window.open(href,target);
				}
			} else {
				var mainHeight = $(".main-sidebar").height()-$(".main-footer").height()-90;
				//lay tabs 需要
				layui.use('element', function(){
					 var element = layui.element;
					 if ( $("li[lay-id='" + href +"']").length == 0) {
						 element.tabAdd('main', {
							 title: $this.html(),
							 content: "<iframe  src='" + href +"'  width='100%' height='" + mainHeight +"px' scrolling='auto' frameborder='0'></iframe>", //支持传入html
							 id: $this.data("href"),
						 })
						 element.tabChange('main', href); 
					 } else {
						 element.tabChange('main', href); 
						 //刷新
						 $("iframe[src='"+ href+ "']").attr("src",href);
					 }
				});
			}
		}
	});
	//退出
	$("#login-out").click(function(){
		 $.post(model.path + "/logout.do",function(respone){
				window.location.href="/admin/pages/login.html";
		});
	});
	//个人中心
	$("#user-center").click(function(){
		model.resetForm();
		$('#user-center-tabs a[href="#tab_info"]').tab('show');
		model.getUserInfo(function(data){
			model.setFormData(data);
		});
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
				var photoFullUrl = model.getFormData().photoFullUrl;
				//如果图像存在
				if (photoFullUrl) {
					$(".user-photo").attr("src",photoFullUrl);
				};
				$("#userName").text(model.getFormData().name);
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
			}
		});
	});
	// $("#user-center").click();
	 $("#userImageUpload").sfCrop(function(data){
		 way.set("model.form.data.photo",data.relativePath);
		 way.set("model.form.data.photoFullUrl",data.fullUrl);
	 },{
		 minCropBoxWidth:200,
		 minCropBoxHeight:200,
		 cropBoxResizable:false,
		 data:{
			 width:200,
			 heght:200,
		 }
	 });
});
