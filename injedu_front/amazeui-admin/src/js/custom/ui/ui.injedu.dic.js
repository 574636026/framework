/**
 * 
 * ui dic
 * 
 * 使用介绍
 * 		$element.ijdic(options)
 * Data API
 * 		<input data-ij-dic="options"/>
 * 参数说明
 * 
 * 		code 		{string}  			-  字典编码
 * 		display		{string}			-  显示方式,只作用于radio、checkbox('inline','')
 * 		dataValue	{array|value type}	-  初始值
 * 
 * Created by joy on 2016/1/11.
 */
!(function($) {
	
	'use strict';
	
	var UI = $.AMUI ||{};
	
	var Store = UI.store;
	
	var $CST = $.IJ.Const;
	
	var $http = $.IJ.Http;
	
	var STORY_KEY = $CST.STORY_KEY.DIC;
	
	var IJDic = function(element, options){
		
		this.options = $.extend({}, IJDic.DEFAULTS, options);
    	this.$element = $(element);
    	if($.type(options.dataValue) != 'array'){
    		this.options.dataValue = [options.dataValue];
    	}
    	this.init();
		
	}
	
	IJDic.DEFAULTS = {
		// 生成类型
		type : 'text',
		// 字典地址
		url : $CST.URL.DIC,
		// 字典码
		code : '',
		// name key
		name : 'item', 
		// value key
		value : 'value',
		// sub key
		sub : 'sub',
		// 只作用于radio、checkbox(可选值 'inline'[行内排列列],其他值为 换行排列)
		display : 'inline',
		// value
		dataValue : [] ,
		// class
		labelClass : 'am-u-sm-4 am-form-label',
		// 
		wrapper : 'true',
		//
		inputWrapperClass :'am-u-sm-8',
		amoptions : {
			btnWidth:'100%',
			maxHeight: 100
		}
	}
	
	// 生成html
	IJDic.prototype.render = function(dic){
		
		var options = this.options;
		var list = dic[options.sub];
		this.$input = this.$element;
		
		
		if(options.type == 'text' || !options.wrapper || options.wrapper == 'false'){
			this.renderOption(list);
		}else{
			this.$label = $('<label class="'+options.labelClass+'">'+dic[options.name]+'</label>');
			this.$input.parent().prepend(this.$label);
			var $inputWrapper = $('<div class="'+options.inputWrapperClass+'"></div>');
			this.$input.wrap($inputWrapper);
			this.$element = $inputWrapper;
			
			this.renderOption(list);
		}
		
	}
	
	// 生成项目
	IJDic.prototype.renderOption = function(list){
		
		if(!list || list.length < 0){
			return;
		}
		var options = this.options;
		var dataValue = options.dataValue;
		var name = options.name;
		var value = options.value;
		
		var arr = [];
		
		switch(options.type){
		case 'select':
			$.each(list, function(i, o) {
				var option = '<option value="' + o[value] + '"';
				if (dataValue.length > 0 && $.inArray(o[value], dataValue) != -1) {
					option += ' selected="selected"';
				}
				option += '>' + o[name] + '</option>';
				arr.push(option);
			});
			this.$input.append(arr.join(''));
			this.$input.selected(options.amoptions);
			break;
		case 'radio' :
			var inputName = this.$input.attr('name');
			var wClass = "am-radio";
			if(options.display == 'inline'){
				wClass = "am-radio-inline";
			}
			$.each(list, function(i, o) {
				var option = '<label class="'+wClass+'">';
				option += '<input type="radio" name="'+inputName+'" value="' + o[value] + '"';
				if (dataValue.length > 0 && $.inArray(o[value], dataValue) != -1) {
					option += ' checked="checked"';
				}
				option += '/> '+o[name] + '</label>';
				arr.push(option);
			});
			var $result = $(arr.join(''));
			this.$input.replaceWith($result);
			$(':input',$result).uCheck();
			this.$input = $result;
			break;
		case 'checkbox' :
			var inputName = this.$input.attr('name');
			var wClass = "am-checkbox";
			if(options.display == 'inline'){
				wClass = "am-checkbox-inline";
			}
			$.each(list, function(i, o) {
				var option = '<label class="'+wClass+'">';
					option += '<input type="checkbox" name="'+inputName+'" value="' + o[value] + '"';
				if (dataValue.length > 0 && $.inArray(o[value], dataValue) != -1) {
					option += ' checked="checked"';
				}
				option += '/>' + o[name] + '</label>';
				arr.push(option);
			});
			var $result = $(arr.join(''));
			this.$input.replaceWith($result);
			$(':input',$result).uCheck();
			this.$input = $result;
			break;
		case 'text' :
			if(dataValue.length <= 0){
				return;
			}
			var _value = dataValue[0];
			
			$.each(list, function(i, o) {
				if (o[value] == _value) {
					arr.push(o[name]);
				}
			});
			this.$input.append(arr.join(''));
			break;
		}
		
	}
	
	// 
	IJDic.prototype.renderWrapper = function(){
		
		var $this = this;
		var options = this.options;
		var code = options.code;
		var store_key = STORY_KEY + code;
		
		if(Store.has(store_key)){
			
			$this.render(Store.get(store_key));
			
		}else{
			// 数据获取
			$http.get(options.url, {}, function(data) {
				$this.render(data);
				Store.set(store_key,data);
			});
		}
	}
	
	//选中项
	IJDic.prototype.select = function(item){
		
		var $input = this.$input;
		
		switch(this.options.type){
			case 'select' :
				$input.selected('select',item);
				break;
			default:
				$(':input',$input).eq(item).uCheck('check');
				break;
		}
	}
	
	IJDic.prototype.init = function(){
		
		var options = this.options;
		var $element = this.$element;
		if(!options.code){
			return;
		}
		
		this.options.url = options.url.replace('%s',$CST.dic[options.code]);
		
		var tagName = $element.is('input') ? $element.attr('type') : $element[0].tagName;
		
		switch(tagName.toLowerCase()){
		case 'select':
			this.options.type = 'select';
			break;
		case 'radio':
			this.options.type = 'radio';
			break;
		case 'checkbox':
			this.options.type = 'checkbox';
			break;
		}
		
		this.renderWrapper();
	}
	
	UI.plugin('ijdic', IJDic , {dataOptions : 'data-ij-dic'});
    
    UI.ready(function(context) {
    	 $('[data-ij-dic]', context).ijdic();
    });
	
})(jQuery);