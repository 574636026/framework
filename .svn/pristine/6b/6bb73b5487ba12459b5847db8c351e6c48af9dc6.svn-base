/**
 * 
 * ui datetimepicker(日期时间选择)
 * 
 * 使用介绍
 * 		$element.ijdatetimepicker(options)
 * Data API
 * 		<input data-ij-datetimepicker="options"/>
 * 参数说明
 * 
 * 		format 				{string}  			-  日期格式
 * 		autoApply 			{boolean}			-  是否自动确认(选择完成后,默认关闭)
 * 		showEasyBtn 		{boolean}			-  是否显示便捷按钮(当天,昨天,7天前,14天前,30天前,当月)
 * 		singleDatePicker	{boolean}			-  是否显示当个日期(默认是,否则选择日期区间)
 * 		minuteStep			{int}				-  步长(分钟)
 * 		alwaysShowCalendars	{boolean}			-  一直显示日期框(便捷按钮存在时)
 * 		startDate			{string}			-  开始日期
 * 		endDate				{string}			-  结束日期
 * 		drops				{string}			-  显示方向('down','up')
 * 
 * Created by joy on 2015/11/25.
 */
!(function($) {

	'use strict';

	var UI = $.AMUI || {};
	
	var IJDatetimepicker = function(element, options) {
    	this.options = $.extend({}, IJDatetimepicker.DEFAULTS, options);
    	this.$element = $(element);
    	this.init();
    }
	
	IJDatetimepicker.DEFAULTS = {
		format : 'YYYY-MM-DD',
		autoApply: true,
		showEasyBtn : 'default',
		singleDatePicker : true,
		showDropdowns:false,
		alwaysShowCalendars:false,
		minuteStep : 5,
		startDate : undefined,
		endDate : undefined,
		drops : 'down'
	}
	
	IJDatetimepicker.RANGES = {
		'default' : {
			'今天'	: [moment().startOf('day'), moment().endOf('day')],
			'昨天'	: [moment().startOf('day').subtract(1, 'days'), moment().endOf('day').subtract(1, 'days')],
			'7天前'	: [moment().startOf('day').subtract(6, 'days'), moment().endOf('day')],
			'14天前'	: [moment().startOf('day').subtract(13, 'days'), moment().endOf('day')],
			'30天前'	: [moment().startOf('day').subtract(29, 'days'), moment().endOf('day')],
			'当前月'	: [moment().startOf('month'), moment().endOf('month')]
		},	
		'after' : {
			'今天'	: [moment().startOf('day'), moment().endOf('day')],
			'明天'	: [moment().startOf('day').add(1, 'days'), moment().endOf('day').add(1, 'days')],
			'7天后'	: [moment().startOf('day'), moment().endOf('day').add(6, 'days')],
			'14天后'	: [moment().startOf('day'), moment().endOf('day').add(13, 'days')],
			'一个月' 	: [moment().startOf('day'), moment().endOf('day').add(1, 'month')],
			'三个月'	: [moment().startOf('day'), moment().endOf('day').add(3, 'month')]
		}
	}
	
	// 原控件设置
	IJDatetimepicker.SOURCE_DEFAULTS = {
		// 是否单个日期
		singleDatePicker: true,
		// 显示下拉框(年、月选择)
		showDropdowns: false,
		// 自动确认
		autoApply: true,
		// 一直显示日期框(ranges存在时)
		alwaysShowCalendars: false,
		// 时间选择器
		timePicker: false,
		// 24小时制
		timePicker24Hour: false,
		// 选择秒
		timePickerSeconds: false,
		// 步长(分钟)
		timePickerIncrement: 1,
		// 显示方向
		drops : 'down',
		// 本地化
		locale : {
			format: 'YYYY-MM-DD HH:mm:ss',
			separator: ' ~ ',
			applyLabel: '确认',
			cancelLabel: '取消',
			fromLabel: 'From',
			toLabel: 'To',
			customRangeLabel: '选择',
			daysOfWeek: ['日', '一', '二', '三', '四', '五','六'],
			monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
			firstDay: 1
		}
	}
	
	// 初始化
	IJDatetimepicker.prototype.init = function(){
		
		var options = this.genConfig();
		
		this.$element.daterangepicker(options);
		
		return this.$element;
	}
	
	/**
	 * 生成控件配置
	 */
	IJDatetimepicker.prototype.genConfig = function(){
		
		var options = this.options;
		
		var daterangepickerOptions = $.extend({}, IJDatetimepicker.SOURCE_DEFAULTS);
		
		daterangepickerOptions.singleDatePicker = options.singleDatePicker;
		daterangepickerOptions.autoApply = options.autoApply;
		daterangepickerOptions.showDropdowns = options.showDropdowns;
		daterangepickerOptions.alwaysShowCalendars = options.alwaysShowCalendars;
		daterangepickerOptions.timePickerIncrement = options.minuteStep;
		daterangepickerOptions.drops = options.drops;
		daterangepickerOptions.startDate = options.startDate;
		daterangepickerOptions.endDate = options.endDate;
		// 设置快捷日期
		if(options.showEasyBtn){
			daterangepickerOptions.ranges = IJDatetimepicker.RANGES[options.showEasyBtn];
		}
		// 时间选择器
		daterangepickerOptions.locale.format = options.format;
		
		if(options.format.indexOf('H') != -1){
			daterangepickerOptions.timePicker = true;
			daterangepickerOptions.timePicker24Hour = true;
		}
		
		if(options.format.indexOf('s') != -1){
			daterangepickerOptions.timePickerSeconds = true;
		}
		
		return daterangepickerOptions;
	}
	
	IJDatetimepicker.prototype.destroy = function(){
		this.$element.removeData('amui.ijdatetimepicker');
		this.$element.removeData('datetimepicker');
	}
	
	UI.plugin('ijdatetimepicker', IJDatetimepicker , {dataOptions : 'data-ij-datetimepicker'});
    
    UI.ready(function(context) {
    	 $('[data-ij-datetimepicker]', context).ijdatetimepicker();
    });
	
})(jQuery);