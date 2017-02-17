/**
 * 
 * 富文本编辑器UI组件
 * 
 * 封装(https://github.com/wangfupeng1988/wangEditor)
 * 
 * 使用介绍 $element.ijeditor(options) Data API <input data-ij-editor="options"/>
 * 
 * 参数说明 simple{boolean} - true:简易菜单,false:完全菜单 mode{string} -
 * normal:正常模式,html:预览模式
 * 
 * Created by joy on 2016/3/16.
 */
!(function($) {

	'use strict';

	var UI = $.AMUI || {};
	var $CST = $.IJ.Const;

	var IJEditor = function(element, options) {
		this.options = $.extend({}, IJEditor.DEFAULTS, options);
		this.$element = $(element);
		this.editor = new wangEditor(element);
		this.init();
	};

	IJEditor.DEFAULTS = {
		config : {
			printLog : false,
			menus : [ 'source', '|', 'bold', 'underline', 'italic',
					'strikethrough', 'eraser', 'forecolor', 'bgcolor', '|',
					'quote', 'fontfamily', 'fontsize', 'head', 'unorderlist',
					'orderlist', 'alignleft', 'aligncenter', 'alignright', '|',
					'link', 'unlink', 'table', '|', 'img', 'video',
					'insertcode', '|', 'undo', 'redo', 'fullscreen' ],
			uploadImgUrl : $CST.ROOTPATH
					+ $CST.URL.UPLOAD.replace('%s', 'editor')
		},
		simple : true,
		mode : 'normal'
	}

	IJEditor.prototype.init = function() {

		if (this.options.simple) {
			this.options.config.menus = [ 'source', '|', 'bold', 'forecolor',
					'fontfamily', 'fontsize', 'head', 'unorderlist',
					'orderlist', 'alignleft', 'aligncenter', 'alignright',
					'img', '|', 'undo', 'fullscreen' ]
		}

		this.editor.config = $.extend(this.editor.config, this.options.config);

		// 优化加载速度,默认预览内容
		this.switchMode(this.options.mode);
	}

	// 切换展示模式(可优化)
	IJEditor.prototype.switchMode = function(mode) {

		switch (mode) {
		case 'html':
			var _this = this;
			this.$element.addClass('am-hide');
			var $val = this.$element.val();
			$val = $val ? $val : '编辑';
			var $preview = $('<div class="am-padding"></div>').css({width:'100%','height':this.$element.height(),'overflow-y' : 'auto',cursor:'pointer',border:'1px dotted #eee'});
			$preview.html($val).insertAfter(this.$element).one('click',function(){
				$preview.remove();
				_this.$element.removeClass('am-hide');
				_this.createEditor();
			});
			break;
		default :
			this.createEditor();
		}
	}

	IJEditor.prototype.createEditor = function() {

		var editor = this.editor;

		// 自定义load事件
		this.editor.config.uploadImgFns.onload = function(resultText, xhr) {
			var data = $.parseJSON(resultText);
			if (data.result && data.result.uploads) {
				var content = data.result.uploads[0];
				// 插入图片
				var img = '<img src="' + content.url + '" alt="' + content.name
						+ '" style="max-width:100%;"/>';
				editor.command(null, 'insertHtml', img);

			}
		};

		// 创建编辑器
		editor.create();

		// 编辑器内容处理
		var $val = editor.$txt.html();

		// 未用p包裹
		if ($val.substring(0, 3) != '<p>') {
			var idx = $val.lastIndexOf('<br>');
			if (idx != -1) {
				$val = $val.substring(0, idx);
			}
			editor.$txt.html('<p>' + $val + '</p>');
		}

	}

	IJEditor.prototype.html = function(html) {

		this.editor.$txt.html(html);
	}

	UI.plugin('ijeditor', IJEditor, {
		dataOptions : 'data-ij-editor'
	});

	UI.ready(function(context) {
		$('[data-ij-editor]', context).ijeditor();
	});

})(jQuery);