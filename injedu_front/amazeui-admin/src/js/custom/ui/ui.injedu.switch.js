/**
 * 
 * 开关切换 UI 组件
 * 
 * 封装(http://www.bootstrap-switch.org)
 * 
 * 使用介绍
 * 		$element.ijswitch(options)
 * Data API
 * 		<input data-ij-switch="options"/>
 * 
 * 参数说明
 * 		state					{boolean} 	- 默认状态
 * 		size					{string}	- 大小
 * 		animate					{boolean} 	- 是否开启动画
 * 		disabled				{boolean} 	- 是否禁用
 *    	readonly				{boolean}	- 是否只读
 *    	indeterminate			{boolean}	- 是否不确定
 *      inverse					{boolean}	- 是否反向
 * 		radioAllOff				{boolean} 	- 
 *    	onColor					{string}	- on颜色
 *      offColor				{string} 	- off颜色
 *      onText					{string} 	- on文字
 *    	offText					{string} 	- off文字
 *      labelText 				{string}: 	- label
 * 
 * Created by joy on 2015/11/25.
 */
!(function ($) {

    'use strict';

    var UI = $.AMUI || {};
    
    var IJSwitch = function(element, options) {
    	this.options = $.extend({}, IJSwitch.DEFAULTS, options);
    	this.$element = $(element);
    	this.$element.bootstrapSwitch(this.options);
    }
   
    IJSwitch.DEFAULTS = {
    	size: 'sm',			//null, 'xs', 'sm', 'normal', 'lg'
    	animate: true,
    	disabled: false,
    	readonly: false,
    	indeterminate: false,
    	inverse: false,
    	radioAllOff: false,
    	onColor: "primary",
    	offColor: "default",
    	onText: "ON",
    	offText: "OFF",
    	labelText: "&nbsp;"
    }
    
    IJSwitch.prototype.state = function(value, skip){
    	return this.$element.bootstrapSwitch('state',value, skip);
    }
    
    IJSwitch.prototype.toggleState = function(skip){
    	return this.$element.bootstrapSwitch('toggleState',skip);
    }
    
    IJSwitch.prototype.destroy = function(){
    	return this.$element.bootstrapSwitch('destroy');
    }
    
    UI.plugin('ijswitch', IJSwitch , {dataOptions : 'data-ij-switch'});
    
    UI.ready(function(context) {
    	 $('[data-ij-switch]', context).ijswitch();
    });

})(jQuery);