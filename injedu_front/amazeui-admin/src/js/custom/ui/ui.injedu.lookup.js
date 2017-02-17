/**
 * 
 * 数据查找带回 UI 组件
 * 
 * 使用介绍 $element.ijlookup(options) Data API <input data-ij-lookup="options"/>
 * 
 * 参数说明
 * 		url 		{string}  			-  地址
 * 		lookType	{string}			-  查找类型：single 单选,mulitiple 多选
 * 		displayType	{string}			-  显示类型: input 输入框,defalut 默认
 * 		dataKey		{string}			-  查找带回属性,默认为ID
 * 		onSelected	{function}	-  		-  回调函数
 * Created by joy on 2015/11/25.
 */
!(function($) {

	'use strict';

	var UI = $.AMUI || {};
	var $winapi = $.IJ.Window;
	var $CST = $.IJ.Const;
	var $dl = $.IJ.Dialog;
	var $tools = $.IJ.Tools;

	var IJLookUp = function(element, options) {
		this.options = $.extend({}, IJLookUp.DEFAULTS, options);
		this.$element = $(element);
		this.$table = [];
		this.tableApi = undefined;

		this.winOptions = $.extend({}, IJLookUp.WINDEFAULTS);
		this.winkey = 'lookup-win';
		this.rememberDatakey = 'lookup_remember_data';
		this.winOptions.target = this.$element;
		this.$window = {};

		this.init();
	}

	IJLookUp.prototype.init = function() {
		var $element = this.$element;
		var options = this.options;
		// 准备参数
		this.prepare();
		// 渲染
		this.render();
		// 创建lookup窗口
		this.$window = this.$element.data(this.winkey);
		if (!this.$window) {
			this.$window = $winapi.sidebar(this.winOptions);
			this.$window.css('z-index', 1111);
			this.$element.data(this.winkey, this.$window);
		}
		// 绑定默认事件
		this.bindEvents();
	}

	/**
	 * 参数准备
	 */
	IJLookUp.prototype.prepare = function() {

		var options = this.options;
		var $element = this.$element;
		var displayType = options.displayType;
		var _datakey = options.dataKey;

		this.winOptions.url = options.url;

		if (displayType != 'input') {
			return;
		}

		if (options.onSelected) {
			return;
		}

		if (options.lookType == 'single') {
			options.onSelected = function(data) {
				$element.val(data[_datakey]);
				this.close();
			}
		} else {
			options.onSelected = function(data) {
				$element.val($tools.tranArray(data,_datakey).join(','));
			}
		}
	}

	// 记忆选择并排除重复
	IJLookUp.prototype.rememberData = function(data) {
		var $element = this.$element;
		var rememberDatakey = this.rememberDatakey;
		var _datakey = this.options.dataKey;
		var cache = $element.data(rememberDatakey) || {};
		$.each(data, function(i, temp) {
			var id = temp[_datakey];
			cache[id] = temp;
		});
		$element.data(rememberDatakey, cache);
		var result = [];
		$.each(cache, function(i, temp) {
			result.push(temp);
		});
		return result;
	}

	/**
	 * 渲染
	 */
	IJLookUp.prototype.render = function() {

		var displayType = this.options.displayType;

		if (displayType == 'input') {

			this.$wrapper = $(this.options.tpl.wrapper);
			this.$element.wrap(this.$wrapper);
			this.$actions = $(this.options.tpl.actions).insertAfter(
					this.$element);
		}

	}

	IJLookUp.prototype.bindEvents = function() {

		var options = this.options;
		var that = this;

		if(this.$actions){
			// 打开
			this.$actions.on('click', '.am-lookup-open', function() {
				that.open();
				return false;
			});
			// 清除
			this.$actions.on('click', '.am-lookup-clear', function() {
				that.$element.removeData(that.rememberDatakey);
				that.$element.val('');
				return false;
			});
		}
		
	}

	/**
	 * 重置状态
	 */
	IJLookUp.prototype.reset = function() {
		this.$element.removeData(this.winkey);
		this.$window.remove();
		this.$table = [];
		this.tableApi = undefined;
	}

	// 初始化表格
	IJLookUp.prototype.initTable = function() {

		if (this.tableApi) {
			return;
		}
		var that = this;
		var options = this.options;
		var displayType = options.displayType;
		var $table = this.$table = $(options.render.current, this.$window);
		var onSelected = $.proxy(options.onSelected, this);

		this.tableApi = $table.ijtable($.extend({
			toolBarDisplay : 'vertical',
			actions : [ 'refresh', 'selected' ],
			toolEvents : {
				'selected' : {
					title : '确认选择',
					icon : 'am-icon-reply',
					fn : function(e) {
						var data = $table.ijtable('findSelectRowData');
						if (data.length == 0) {
							$dl.alert($CST.MESSAGE.DATA_NEED_SELECTED);
							return;
						}
						if (options.lookType === 'single') {
							if (data.length > 1) {
								$dl.alert($CST.MESSAGE.DATA_SELECT_ONE);
								return;
							}
							onSelected(data[0]);
						} else {
							if (displayType == 'input') {
								data = that.rememberData(data);
							}
							onSelected(data);
						}
					}
				}
			},
			tableOptions : {
				pagingType : 'simple'
			}
		}, this.options.renderOptions));
	}

	// 打开
	IJLookUp.prototype.open = function(receiveData) {
		var $window = this.$window;
		// 回传数据
		this.receiveData = receiveData;
		$window.offCanvas('open');
		// off touch events
		this.$window.hammer().off('swipeleft swipeleft');
		// fix a bug
		$window.off('click.offcanvas.amui');
		$window.on('click.offcanvas.amui', function(e) {
			var $target = $(e.target);
			if ($target.hasClass('am-offcanvas-bar')) {
				return;
			}
			// 使用datatables在翻页时会关闭offcanvas,原因在于翻页后触发的按钮重新生成,不属于原dom
			if ($target.attr('aria-controls')) {
				return;
			}

			if ($target.parents('.am-offcanvas-bar').first().length) {
				return;
			}
			e.stopImmediatePropagation();
			$window.offCanvas('close');
		});
		// 表格未初始化
		this.initTable();
	}

	// 关闭
	IJLookUp.prototype.close = function() {
		if (this.options.displayType == 'input') {
			this.$element.trigger('change');
		}
		this.$window && this.$window.offCanvas('close');
	}

	IJLookUp.WINDEFAULTS = {
		isRemove : false,
		loading : false
	}

	IJLookUp.DEFAULTS = {
		url : '/commom/ui/lookup/lookup.html',
		lookType : 'single', // single,mulitiple
		render : {// 当前只实现table数据带回
			type : 'table',
			current : 'table'
		},
		renderOptions : {},// 内容组件初始化参数
		tpl : {
			wrapper : '<div class="am-input-group am-input-group-sm"></div>',
			actions : '<span class="am-input-group-btn"><a class="am-btn am-btn-primary am-lookup-open"> <i class="am-icon-search" /></a><a class="am-btn am-btn-primary am-lookup-clear"> <i class="am-icon-close" /></a></span>'
		},
		onOpen : undefined,
		onSelected : undefined,
		dataKey : 'id',
		displayType : 'input' // 显示类型 'input','default'
	}

	UI.plugin('ijlookup', IJLookUp, {
		dataOptions : 'data-ij-lookup'
	});

	UI.ready(function(context) {
		$('[data-ij-lookup]', context).ijlookup();
	});

})(jQuery);