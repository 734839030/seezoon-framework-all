$(function(){
	$.extend({
		//有些控件无数把数据绑定到way中，如bootstarpdate icheck select，需要覆盖此方法
		getSearchCondition : function() {
			$(".search").each(function(){
				way.set("model.search."+$(this).attr("way-data"),$(this).val());
			});
			return way.get("model.search");
		}
	});
	/**
	 * 验证框架默认属性
	 */
	$.extend($.fn.bootstrapValidator.DEFAULT_OPTIONS,{
		excluded: [':disabled'],
	});
	// 分页表格 默认值设置
	$.extend($.fn.bootstrapTable.defaults, {
		method : 'post',
		striped : true,
		pagination : true,
		pageSize : 10,
		paginationLoop : false,
		pageList : [ 20, 50, 100 ],
		sidePagination : 'server',
		idField : 'id',
		uniqueId : 'id',
		//sortName:'update_date', 默认顺序
		//sortOrder:'desc',
		singleSelect : true,
		clickToSelect : true,
		contentType : 'application/x-www-form-urlencoded',
		onPostBody:function(){//渲染完后执行
			$.bntPermissionHandler();
		},
		queryParams : function(params) {
			var param = {
				page : this.pageNumber,
				pageSize : this.pageSize,
				sortField : this.sortName,
				direction : this.sortOrder
			}
			var data = $.getSearchCondition();
			$.extend(param, data);
			return param;
		},
		onDblClickRow:function(row, $element, field){
			$('#table').bootstrapTable('checkBy', {field:'id',values:[row.id]});
			$("#edit:visible").click();
		},
		responseHandler : function(res) {
			if (res.data) {
				return {
					total : res.data.total,
					rows : res.data.list
				};
			} else {
				return {};
			}
		},
	});
});
/**
 * bootstart table bug 修改
 */

!function($) {
    'use strict';

    var BootstrapTable = $.fn.bootstrapTable.Constructor;
    BootstrapTable.prototype.onSort = function (event) {
        var $this = event.type === "keypress" ? $(event.currentTarget) : $(event.currentTarget).parent(),
            $this_ = this.$header.find('th').eq($this.index());

        this.$header.add(this.$header_).find('span.order').remove();

        if (this.options.sortName === $this.data('field')) {
            this.options.sortOrder = this.options.sortOrder === 'asc' ? 'desc' : 'asc';
        } else {
            this.options.sortName = $this.data('sort-name') ? $this.data('sort-name') : $this.data('field');
            this.options.sortOrder = $this.data('order') === 'asc' ? 'desc' : 'asc';
        }
        this.trigger('sort', this.options.sortName, this.options.sortOrder);

        $this.add($this_).data('order', this.options.sortOrder);

        // Assign the correct sortable arrow
        this.getCaret();

        if (this.options.sidePagination === 'server') {
            this.initServer(this.options.silentSort);
            return;
        }

        this.initSort();
        this.initBody();
    };
    BootstrapTable.prototype.getCaret = function () {
        var that = this;

        $.each(this.$header.find('th'), function (i, th) {
            var sort_class = $(th).data('field') === that.options.sortName || $(th).data('sort-name') === that.options.sortName ? that.options.sortOrder : 'both';
            $(th).find('.sortable').removeClass('desc asc').addClass(sort_class);
        });
    };
}(jQuery);


