/**
 * 
 * ui select
 * 
 * 使用介绍
 * 		$element.ijselect(options)
 * Data API
 * 		<input data-ij-select="options"/>
 * 参数说明
 * 
 * 		url 		{string}  			-  数据请求地址
 * 		name 		{string}			-  对应数据(name key)
 * 		value 		{string}			-  对应数据(value key)
 * 		dataValue	{array|value type}	-  初始值
 * 		amoptions	{object}			-  amazeui options(具体可查看官网)
 * 
 * Created by joy on 2015/11/25.
 */
!(function($) {

	'use strict';

	var UI = $.AMUI || {};
	var $http = $.IJ.Http;
	var $CST = $.IJ.Const;
	
	var IJSelect = function(element, options) {
    	this.options = $.extend({}, IJSelect.DEFAULTS, options);
    	this.$element = $(element);
    	this.init();
    }
	
	IJSelect.DEFAULTS = {
		url : '',
		name : 'item',
		value : 'value',
		dataValue : [],
		amoptions : {
			btnWidth:'100%'
		}
	}
	
	// 生成options
	IJSelect.prototype.render = function(url){
		
		var options = this.options;
		var dataValue = options.dataValue;
		var $element = this.$element;
		var name = options.name, value = options.value;
		
		$http.get(url, {}, function(data) {

			var optionArr = [];

			$.each(data, function(i, o) {
				var option = '<option value="' + o[value] + '"';
				if (dataValue.length > 0 && $.inArray(o[value], dataValue) != -1) {
					option += ' selected="selected"';
				}
				option += '>' + o[name] + '</option>';

				optionArr.push(option);
			});

			$element.append(optionArr.join(''));

			$element.selected(options.amoptions);

		});
	}
	
	// 根据url重构options
	IJSelect.prototype.reRender = function(url){
		
		var $element = this.$element;
		// 清空
		$('option[value!=""]',$element).remove();
		
		this.render(url);
	}
	
	// 初始化
	IJSelect.prototype.init = function(){
		
		var options = this.options;
		var $element = this.$element;
		
		if (!options.url) {
			$element.selected(options.amoptions);
			return;
		}
		
		this.render(options.url);
		
		return this.$element;
	}
	
	IJSelect.prototype.destroy = function(){
		this.$element.removeData('amui.ijselect');
		this.$element.selected('destroy');
	}
	
	UI.plugin('ijselect', IJSelect , {dataOptions : 'data-ij-select'});
    
    UI.ready(function(context) {
    	 $('[data-ij-select]', context).ijselect();
    });
	
})(jQuery);