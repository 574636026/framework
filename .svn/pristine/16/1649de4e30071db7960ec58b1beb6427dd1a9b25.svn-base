/**
 * 
 * ui tags
 * 
 * 使用介绍
 * 		$element.ijtags(options)
 * Data API
 * 		<input data-ij-tags="options"/>
 * 参数说明
 * 
 * 		dataValue			{string}			-  初始值
 * 
 * Created by joy on 2015/11/25.
 */
!(function($) {

	'use strict';

	var UI = $.AMUI || {};
	var $http = $.IJ.Http;
	var $CST = $.IJ.Const;
	
	var IJTags = function(element, options) {
    	this.options = $.extend({}, IJTags.DEFAULTS, options);
    	this.$element = $(element);
    	this.init();
    }
	
	IJTags.DEFAULTS = {
		dataValue: undefined,
			// 分隔符,默认为逗号
		sep : ',',
			// 回车键、空格键
		confirmKeys: [13, 32],
			// 是否移除标签左右的空格
		trimValue: false,
			// 是否允许重复的标签
		allowDuplicates: false
	}
	
	// 初始化
	IJTags.prototype.init = function(){
		
		this.$element.tagsinput(this.options);
		
		if(this.options.dataValue){
			this.$element.tagsinput('add', this.options.dataValue);
		}
		
		return this.$element;
	}
	
	IJTags.prototype.destroy = function(){
		this.$element.removeData('amui.ijtags');
		this.$element.selected('destroy');
	}
	
	UI.plugin('ijtags', IJTags , {dataOptions : 'data-ij-tags'});
    
    UI.ready(function(context) {
    	 $('[data-ij-tags]', context).ijtags();
    });
	
})(jQuery);