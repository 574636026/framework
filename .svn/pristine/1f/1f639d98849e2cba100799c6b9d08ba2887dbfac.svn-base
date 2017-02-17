/**
 * 
 * 自动补全UI
 * 
 * <input ij-autocomplete="{
 * 		url : '',	-请求地址(必须)
 * 		name : '',	-name key
 * 		value : '', -value key
 * 		target : '' -target (保持value值目标对象,一般为隐藏域name值)
 * }"/>
 * 
 * Created by joy on 2015/11/25.
 */
!(function($) {

	'use strict';
	
	var UI = $.AMUI || {};
	var ROOTPATH = $.IJ.Const.ROOTPATH;

	$.fn.extend({

		autocomplete : function() {

			var DATA_KEY = "ij-autocomplete-value";

			return this.each(function() {
				
				var $this = $(this);

				var _options = UI.utils.options($this.attr('data-ij-autocomplete')) || {};
				
				var options =$.extend({
					name : 'item',
					value : 'value'
				},options);
				
				if (!options.url) return;

				// 定义数据源
				var source = new Bloodhound({
					datumTokenizer : Bloodhound.tokenizers.obj
									.whitespace(options.name),
					queryTokenizer : Bloodhound.tokenizers.whitespace,
					remote : {
						url : ROOTPATH + options.url,
						prepare : function(query, settings){
							settings.method = 'post';
							settings.data = {'key' : query};
							return settings;
						},
						transform :function(response){
							return response.result;
						}
					}
				});

				// 初始化
				$this.typeahead({
					highlight : true,
					hint : false,
					minLength : 1
				}, {
					source : source,
					display : options.item
				}).on('typeahead:select', function(e, suggestion) {
					$this.data(DATA_KEY, suggestion);
					if(options.target){
						$this.parent().nextAll('input[name="'+options.target+'"]').val(suggestion[options.value]);
					}
				}).on('typeahead:close', function(e) {
					//console.log($(this).data(DATA_KEY));
				});

			});

		}

	});

	// Init code
	UI.ready(function(context) {
		$('[data-ij-autocomplete]', context).autocomplete();
	});

})(jQuery);