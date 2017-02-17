/**
 * 
 * ui relaction select
 * 级联下拉框
 * 
 * 使用介绍
 * 		$element.ijrelactionselect(options)
 * Data API
 * 		<input data-ij-relactionselect="options"/>
 * 参数说明
 * 
 * Created by joy on 2016/4/1.
 */
!(function($) {

	'use strict';

	var UI = $.AMUI || {};
	var $http = $.IJ.Http;
	
	var SELECT_URL = 'ij-relactionselect-url';
	
	var IJRelactionSelect = function(element, options) {
    	this.options = $.extend({}, IJRelactionSelect.DEFAULTS, options);
    	this.$element = $(element);
    	this.init();
    }
	
	IJRelactionSelect.DEFAULTS = {
		url : [],
		dataValue : [],
		amoptions : {}
	}
	
	// 初始化
	IJRelactionSelect.prototype.init = function(){
		
		var options = this.options,
			amoptions = options.amoptions,
			$element = this.$element,
			$selects = this.$selects = $('select',$element);
		
		if(!options.url.length){
			return;
		}
		
		var len = $selects.length;
		
		if(!len){
			return;
		}
			
		$.each($selects,function(i,select){
				
			var $select = $(select);
				
			var _opt = {amoptions:amoptions};
				
			if(i == 0){
				_opt.url = options.url[0];
			}
			
			$select.ijselect(_opt);
			
		});
		
		this.bindEvent();
			
		
		return this.$element;
	}
	
	// 事件初始化
	IJRelactionSelect.prototype.bindEvent = function(){
			
		var options = this.options,
			$element = this.$element,
			$selects = this.$selects;
		
		var len = $selects.length;
		
		$element.on('change','select:lt('+ (len - 1)+')',function(){
			
			var $this = $(this),
				value = $this.val(),
				index = $selects.index($this),
				next  = index + 1;
			
			if(index < 0){
				return;
			}
			
			if(value == null || value == ""){
				
				$('option[value!=""]',$selects.eq(next)).remove();
				return;
			}
			
			var url = options.url[next].replace('%s',value);
				
			if(!url){
				return;
			}
				
			$selects.eq(next).ijselect('reRender',url);
					
		});
			
	}
	
	
	IJRelactionSelect.prototype.destroy = function(){
		this.$element.removeData('amui.ijrelactionselect');
		this.$element.selected('destroy');
	}
	
	UI.plugin('ijrelactionselect', IJRelactionSelect , {dataOptions : 'data-ij-relactionselect'});
    
    UI.ready(function(context) {
    	 $('[data-ij-relactionselect]', context).ijrelactionselect();
    });
	
})(jQuery);