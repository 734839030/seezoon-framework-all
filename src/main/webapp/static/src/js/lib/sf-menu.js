/**
 * 菜单选择组件
 * @param $
 * @returns
 */
;(function($) {
	$("body").append("<div id='menuLayer' class='undisplay pd10'><ul id='menuTree' class='ztree'></ul></div>");
	$.seezoon = {
	    index:0,
		chooseMenu:function(treeId,confirmCallback,clearCallback){
			var setting = {
					data : {
						simpleData : {
							enable : true,
							idKey : "id",
							pIdKey : "parentId",
						}
					},
					callback : {
						onDblClick : function(event, treeId, treeNode) {
							if (confirmCallback(treeNode)){
								layer.close($.seezoon.index);
							}
						}
					}
				};
				$.post(requestPath + "/sys/menu/qryAll.do", function(respone) {
					//选择上级tree
					$.fn.zTree.init($("#menuTree"), setting,respone.data);
					var treeObj = $.fn.zTree.getZTreeObj("menuTree");
					//treeObj.expandAll(true);
					if (treeId) {//默认反选
						treeObj.selectNode(treeObj.getNodeByParam("id",treeId));
					}
				});
				this.index = layer.open({
				 	  title:'上级菜单',
				 	  offset: '50px',
		              skin: 'layui-layer-molv',
		              shadeClose:true,
				 	  icon: 1,
				 	  area: ['300px', '450px'],
					  type: 1, 
					  btn: ['确认','清除'],
					  yes:function(){
						  var treeObj = $.fn.zTree.getZTreeObj("menuTree");
						  var nodes = treeObj.getSelectedNodes();
						  if (nodes.length == 0) {
							  layer.msg("请选择菜单");
							  return false;
						  }
						  if (confirmCallback(nodes[0])){
								layer.close($.seezoon.index);
						   }
					  },
					  btn2:function(){
						  clearCallback();
						  layer.close($.seezoon.index);
					  },
					  content:  $("#menuLayer") 
					});
		}
	}
})(jQuery);