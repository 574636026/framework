/**
 * 
 * 
 * Created by joy on 2015/12/8.
 */
!(function($) {

	'use strict';
	var UI = $.AMUI;
	var $http = $.IJ.Http;
	var $CST = $.IJ.Const;

	if (UI && UI.validator) {

		// 扩展正则库
		$.AMUI.validator.patterns = $.extend(
			$.AMUI.validator.patterns,
						{
							// 用户名
							userName : /^[a-zA-Z]\w{3,16}$/,
							// 手机号,13,15,18,170号段
							mobile : /^1((3|5|8){1}\d{1}|70)\d{8}$/,
							// 中文字符
							chinese : /[\u4e00-\u9fa5]/,
							// 邮编
							zipCode : /^\d{6}$/,
							// 字符编码
							charCode : /^[a-zA-Z_]{4,20}$/,
							// 腾讯QQ
							qq : /^[1-9][0-9]{4,}$/,
							// IP
							ip : /^((([1-9]\d?)|(1\d{2})|(2[0-4]\d)|(25[0-5]))\.){3}(([1-9]\d?)|(1\d{2})|(2[0-4]\d)|(25[0-5]))$/
						});

		// 添加tabs隐藏面板忽略
		$.AMUI.validator.DEFAULTS.ignore = ":hidden:not([data-am-selected], .am-validate),.am-tabs-bd-wizard .am-tab-panel:not(.am-active) :input";

		// 得到错误消息显示位置
		var findMessageLocation = function($field) {
			var $message = $field.parent();
			if ($message.is('.am-input-group')) {

			} else if ($message.is('.am-switch-container')) {
				$message = $field.parents('.am-switch');
			} else {
				$message = $field;
			}
			return $message;
		}

		// 自定义验证提示
		$.AMUI.validator.DEFAULTS.onValid = function(validity) {
			findMessageLocation($(validity.field)).next('.am-alert').hide();
		}

		$.AMUI.validator.DEFAULTS.onInValid = function(validity) {
			var $field = $(validity.field);
			var $message = findMessageLocation($field);
			var $alert = $message.next('.am-alert');
			// 使用自定义的提示信息 或 插件内置的提示信息

			var msg = $field.data('validationMessage')
					|| this.getValidationMessage(validity);

			if (!$alert.length) {
				$alert = $('<div class="am-alert am-alert-danger"></div>')
						.hide().insertAfter($message);
			}

			msg && $alert.html(msg).show();
		}

		// 添加ajax验证消息

		$.AMUI.validator.validationMessages.zh_CN.customError = $.extend(
				$.AMUI.validator.validationMessages.zh_CN.customError, {
					'remote' : '%s 校验失败',
					'remoteunique' : '%s 必须唯一',
					'fileuploadrequired' : '请上传文件',
					'fileupload' : '文件上传失败'
				});

		$.AMUI.validator.ERROR_MAP = $.extend($.AMUI.validator.ERROR_MAP, {
			'remote' : 'name',
			'remoteunique' : 'name'
		});

		// 添加ajax验证
		$.AMUI.validator.DEFAULTS.validate = function(validity) {

			// 验证不通过则不继续验证
			if (!validity.valid) {
				return;
			}

			var $field = $(validity.field);
			var $element = this.$element;
			var $value = $.trim($field.val());

			if ($field.is('[remote]')) {
				// 添加远程验证
				return selfvalidate.remote($field, $element,$value,validity);
			}
			
		}

		var selfvalidate = {

			remote : function($field, $element, $value, validity) {

				if($value == undefined || $value == null || $value === ''){
					return validity;
				}
				
				var options = $.extend({}, UI.utils.options($field.attr('remote')));

				validity['customError'] = 'remote';
				
				if(options.message){
					validity['customError'] = 'remote' + options.message;
				}

				var url = options.url;

				var data = {
					key : $value
				};

				if (options.data && options.data.length > 0) {

					for (var i = 0; i < options.data.length; i++) {

						var _input = $('[name="' + options.data[i] + '"]',
								$element);

						if (_input.length > 0 && _input.attr('value')) {

							data[_input.attr('name')] = _input.attr('value');
						}

					}
				}

				return $http.post(url, data).then(function(data) {

					if (!data) {
						validity.valid = false;
						return validity;
					}

					var status = $CST.VALIDATE.SUCCESS;

					if (data['resultCode']) {

						if (data['resultCode'] == $CST.STATUS.SUCCESS) {
							status = data.result.status;
						} else {
							status = $CST.VALIDATE.FAILD;
						}

					} else {
						status = data.status
					}

					if (status == $CST.VALIDATE.FAILD) {
						validity.valid = false;
					}
					
					return validity;

				}, function() {
					validity.valid = false;
					return validity;
				});

			}

		}

	}

})(jQuery);