$(function() {
	model = {
		path : adminContextPath ,
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
		init:function(){
			//iframe的高度100%  父容器必须是实际高度
			$("#main-content").height($("#main-content").height()-150);
			//菜单渲染
			$.post(model.path + "/user/getUserMenus.do",function(respone){
				var menu = "";
				var data = respone.data;
				//处理菜单
				$("#left-menu").empty().append(model.handLeftMenu(data,'0'));
				$("#left-menu li:eq(0)").addClass("active");
		    });
			
			//个人信息
			$.post(model.path + "/user/getUserInfo.do",function(respone){
				var data = respone.data;
				if (data) {
					$("#userName").text(data.name);
					$("#loginName").text(data.loginName + "[" + data.deptName + "]");
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
	//退出
	$("#loginOut").click(function(){
		 $.post(model.path + "/user/logout.do",function(respone){
				window.location.href="/pages/login.html";
		});
	});
});