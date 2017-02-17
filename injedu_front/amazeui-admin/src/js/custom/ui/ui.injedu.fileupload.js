/**
 * 
 * ui fileupload
 * 
 * 使用介绍 $element.ijfileupload(options) 
 * 
 * Data API 
 * <input data-ij-fileupload="options"/> 
 * 
 * 参数说明
 * 		url					{string}	-	文件上传地址
 * 		dir					{string}	-	文件上传目录(对应项目配置的key)
 * 		maxNumberOfFiles	{number}	-	文件最大上传数量
 * 		autoUpload			{boolean}	-	是否自动上传
 * 		acceptFileTypes		{reg}		-	文件上传类型(正则表达式)
 * Created by joy on 2015/11/25.
 */
!(function($) {

	'use strict';

	var UI = $.AMUI || {};
	var $CST = $.IJ.Const;
	var $dl = $.IJ.Dialog;

	var IJFileupload = function(element, options) {
		this.options = $.extend({}, IJFileupload.DEFAULTS, options);
		this.$element = $(element);
		this.init();
	}

	// 其他参数请查看jquery.fileupload
	IJFileupload.DEFAULTS = {
		url : $CST.ROOTPATH + $CST.URL.UPLOAD,
		dir : 'upload.dir.test',
		type : 'post',
		dataType : 'json',
		autoUpload : false,
		singleFileUploads : true,
		maxNumberOfFiles : undefined,
		filesContainer : undefined,
		disableImageResize : /Android(?!.*Chrome)|Opera/
				.test(window.navigator.userAgent),
		acceptFileTypes : /(\.|\/)(gif|jpe?g|png)$/i,
		previewMaxWidth : 80,
		getFilesFromResponse : function(data) {
			var data = data.result;
			if (data.result && data.result.uploads && $.isArray(data.result.uploads)) {
				return data.result.uploads;
			}
			return [];
		}
	}
	// 初始化
	IJFileupload.prototype.init = function() {

		var $element = this.$element;
		var options = this.options;
		
		this.options.url = options.url.replace('%s',options.dir);

		if (!options.filesContainer) {
			options.filesContainer = $('[data-trigger="fileupload"]', $element);
		}

		if ($.type(options.maxNumberOfFiles) == 'number'
				&& options.maxNumberOfFiles == 1) {

			options.previewMaxWidth = 120;

		}
		
		if(!options.getFilesFromResponse){
			options.getFilesFromResponse = function(data){
				var result = this.getResponse(data);
				if (result && result.uploads && $.isArray(result.uploads)) {
					return result.uploads;
				}
				return [];
			}
		}
		
		$element.fileupload(options);
		
		// 绑定失败处理
		$element.on('fileuploadfailed',function(e,error){
			if (error.resultCode != undefined && error.resultCode != $CST.STATUS.SUCCESS) {
				$dl.toast(error.resultMessage || $CST.STATUSMESSAGE[error.resultCode] || $CST.MESSAGE.UNKONW_STATUS);
			}
		});

		return this;
	}
	
	// 获取返回数据
	IJFileupload.prototype.getResponse = function(data){
		
		var _data = data.result;
		
		return _data.result ? _data.result : _data;
	}
	
	// 判断是否存在上传文件
	IJFileupload.prototype.hasFile = function(){
		
		var template = $('.template-upload',this.options.filesContainer);
		
		return template.length > 0;
	}
	
	// 上传文件
	IJFileupload.prototype.fileupload = function() {
		
		var options = this.options;
		var $element = this.$element;
		
		var template = $('.template-upload',options.filesContainer);
		
		var masterDfd = new $.Deferred();
		
		if(!this.hasFile()){
			masterDfd.resolve();
			return masterDfd;
		}
		
		var files = [];
		
		template.each(function(){
			var data = $(this).data('data');
			$.merge(files,data.files);
		});
		
		var promises = $element.fileupload('send',{files: files});
		
	    $.when.apply(null,promises).then(function() {
	      masterDfd.resolve();
	    }, function() {
	      masterDfd.reject();
	    });
		
		return masterDfd;
	}
	
	
	// 提交失败事件
	IJFileupload.prototype.failed = function(callback){
		this.$element.off('fileuploadfailed');
		this.$element.on('fileuploadfailed',function(e,data){
			data && callback(data);
		});
	}
	
	// 提交完成事件
	IJFileupload.prototype.completed = function(callback,loadindex){
		
		var that = this;
		
		this.$element.off('fileuploadcompleted');
		
		this.$element.on('fileuploadcompleted',function(e,data){
			
			loadindex && $dl.close(loadindex);
			
			var result = that.getResponse(data);
			
			result && callback(result);
		});
	}
	// 变更传输地址
	IJFileupload.prototype.url = function(url){
		this.options.url = url;
		this.$element.fileupload('option','url',url);
	}
	// 变更传输数据
	IJFileupload.prototype.formData = function(formData){
		this.options.formData = formData;
	}

	IJFileupload.prototype.destroy = function() {
		this.$element.removeData('amui.ijfileupload');
		this.$element.removeData('blueimp-fileupload');
	}

	UI.plugin('ijfileupload', IJFileupload, {
		dataOptions : 'data-ij-fileupload'
	});

	UI.ready(function(context) {
		$('[data-ij-fileupload]', context).ijfileupload();
	});

})(jQuery);