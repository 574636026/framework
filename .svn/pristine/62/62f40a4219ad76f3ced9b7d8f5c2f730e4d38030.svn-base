/**
 * 
 * ui echarts
 * 
 * 使用介绍 $element.ijecharts(options) Data API <input data-ij-echarts="options"/>
 * 参数说明
 * 
 * 
 * Created by joy on 2016/4/8.
 */
!(function($) {

	'use strict';

	var UI = $.AMUI || {};

	var $CST = $.IJ.Const;
	var $http = $.IJ.Http;

	var IJEcharts = function(element, options) {
		if (!echarts) {
			console.log('请载入echarts相关js...');
			return;
		}
		this.options = $.extend({}, IJEcharts.DEFAULTS, options);
		this.$element = $(element);
		this.charts = echarts.init(element, this.options.theme);
		this.init();
	}

	IJEcharts.DEFAULTS = {
		theme : 'default',
		url : '',
		data : {},
		options : {}
	}

	/**
	 * 
	 * 初始化
	 * 
	 */
	IJEcharts.prototype.init = function() {
		var options = this.options;
		if (!options.url) {
			this.charts.setOption(options.options);
		} else {
			this._load();
		}
	}

	IJEcharts.prototype._load = function() {
		var charts = this.charts;
		var options = this.options;
		var chartsOptions = options.options;

		charts.showLoading();
		$http.get(options.url, options.data, function(data) {
			charts.hideLoading();
			chartsOptions = $.extend({}, data, chartsOptions);
			charts.setOption(chartsOptions);
		});

	}

	/**
	 * 
	 * 刷新图表
	 * 
	 */
	IJEcharts.prototype.refresh = function(params) {

		if (!params)
			return;

		var options = this.options;
		
		this.options = $.extend(options,params);

		this._load();
	}

	UI.plugin('ijecharts', IJEcharts, {
		dataOptions : 'data-ij-echarts'
	});

	UI.ready(function(context) {
		$('[data-ij-echarts]', context).ijecharts();
	});

})(jQuery);