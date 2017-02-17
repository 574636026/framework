/**
 * 
 * 数字输入框 UI 组件
 * 
 * 封装(http://www.virtuosoft.eu/code/bootstrap-touchspin/)
 * 
 * 使用介绍
 * 		$element.ijnumber(options)
 * Data API
 * 		<input data-ij-number="options"/>
 * 
 * 参数说明
 * 		min						{number} 	- 最小值
 * 		max						{number}	- 最大值
 * 		step					{number} 	- 步长
 * 		decimals				{number} 	- 精度
 *    	verticalbuttons			{boolean}	- 是否垂直显示
 *    	prefix					{string}	- 后置显示(一般用于显示单位)
 *      postfix					{string}	- 前置显示(一般用于显示单位)
 * 		stepinterval			{number} 	- 步长间隔
 *    	stepintervaldelay		{number}	- 步长间隔等待
 *      forcestepdivisibility	{string} 	- 
 *      booster					{boolean} 	- 是否快速变动(鼠标按住不动根据boostat比率增长数值)
 *    	boostat					{number} 	- 快速变动速率
 *      maxboostedstep 			{boolean}: 	- 最大步长限制
 *     	mousewheel				{boolean}: 	- 鼠标滚轮
 * 
 * Created by joy on 2015/11/25.
 */
!(function ($) {

	//required amazeui、TouchSpin
	
    'use strict';

    var UI = $.AMUI || {};
    
    var IJNumber = function(element, options) {
    	this.options = $.extend({}, IJNumber.DEFAULTS, options);
    	this.$element = $(element);
    	this.$element.TouchSpin(this.options);
    }
    
    IJNumber.DEFAULTS = {
    	min: 0,
    	max: 10000,
    	initval: '',
    	step: 1,
    	decimals: 0,
    	stepinterval: 100,
    	forcestepdivisibility: 'round', // none | floor | round | ceil
        prefix: '',
        postfix: '',
    	stepintervaldelay: 500,
    	verticalbuttons: false,
    	booster: true,
    	boostat: 10,
    	maxboostedstep: false,
    	mousewheel: true
    }
    
    UI.plugin('ijnumber', IJNumber , {dataOptions : 'data-ij-number'});
    
    UI.ready(function(context) {
    	 $('[data-ij-number]', context).ijnumber();
    });

})(jQuery);