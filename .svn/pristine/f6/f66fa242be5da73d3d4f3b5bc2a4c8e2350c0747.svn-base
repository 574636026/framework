/**
 * 
 * Http 工具类
 * 
 * get,post都只请求json数据
 * 
 * 
 * 说明： 1.请求路径只需要相对路径,此方法自动拼接上项目路径,如果要自己传入绝对路径,使用rootpath参数
 * 
 * 2.项目中请求的json数据进行了封装(例 : {resultCode :200,resultMessage :'',result : {} })
 * 
 * 使用此方法则自动处理错误信息,并解包真正的数据给使用者
 * 
 * Created by joy on 2015/11/20.
 */
!(function($) {

	'use strict';

	if (!$.IJ)
		$.IJ = {};
	var $CST = $.IJ.Const;
	var $dl = $.IJ.Dialog;
	var $tools = $.IJ.Tools;

	var Http = function() {

		// 显示错误信息
		var errorHandle = function(data) {
			
			var error = {};
			
			if($.isPlainObject(data) || $.type(data) == 'array'){
				error = data;
			}else if(data.substring(0,1) == '{'){
				// 默认为json string
				error = $.parseJSON(data);
			}else{
				// text,可能有ajax加载页面的情况
				return false;
			}
			
			if (error.resultCode != undefined && error.resultCode != $CST.STATUS.SUCCESS) {

				$dl.toast(error.resultMessage || $CST.STATUSMESSAGE[error.resultCode] || $CST.MESSAGE.UNKONW_STATUS);

				return true;
			}

			return false;
		}

		// http请求
		var _http = function(options) {
			
			var options = $.extend({
				type : 'get',
				// 默认的话，traditional为false，即jquery会深度序列化参数对象，以适应如PHP和Ruby on Rails框架，
				// 但servelt api无法处理，我们可以通过设置traditional 为true阻止深度序列化
				traditional : true,
				cache : false
			}, options);

			options.url = (options.rootpath || $CST.ROOTPATH) + options.url;

			var callback = options.callback;
			// loading 处理
			var loadindex = -1;
			var loadtype = $.type(options.loading);
			var loadtimeout = -1;
			
			if(loadtype === 'boolean' && options.loading == true){
				loadindex = $dl.loading();
			}else if(loadtype === 'object'){
				if($.isPlainObject(options.loading)){
					// 创建loading
					loadindex = $dl.loading(options.loading);
					loadtimeout = options.loading.loadtimeout;
					
				}else{
					// loding对象
					loadtimeout = 500;
					loadindex = options.loading;
				}
			}
			// 请求成功
			options.success = function(data) {
				
				if (!data) {
					callback && callback.call(this);
					return;
				}
				// 错误处理
				if (!errorHandle(data)) {
					
					if(data.resultCode){
						callback && callback.call(this, data.result);
					}else{
						callback && callback.call(this, data);
					}
					
				}
			}
			// 请求失败
			options.error = function(jqXHR, status) {
				
				var errorThrown = '';
				
				switch (jqXHR.status){
		            case(500):
		            	errorThrown = '服务器存在错误，未能正确处理你的请求！';
		                break;
		            case(403):
		            	errorThrown = '你无权进行此操作或页面访问！'
		                break;
		            case(404):
		            	errorThrown = '请求访问的地址或内容不存在！';
		                break;
		            default:
		            	errorThrown = "未知错误，错误代码：" + status;
		        }
				$dl.toast(errorThrown);
			}
			
			// 请求完成
			options.complete = function(XMLHttpRequest, textStatus){
				//关闭loading
				if(options.loading && loadindex != -1){
					$dl.close(loadindex,loadtimeout);
				}
			}
			
			return $.ajax(options);
		}
		
		var _httpJson = function(options){
			
			return _http($.extend(options,{dataType : 'json'}));
		}

		return {
			// ajax request
			ajax : function(options) {
				return _http(options);
			},
			// load html
			load : function(url,loading){
				var result;
				_http({
					url : url + "?t=" + new Date().getTime(),
					async : false,
					loading : (loading == undefined ? true : loading),
					callback:function(data){
						result = data;
					}
				});
				return result;
			},
			// get request
			get : function(url, data, callback,loading) {
				return _httpJson({
					url : url,
					data : data,
					type : 'get',
					callback : callback,
					loading : loading
				});
			},
			// sync get request
			getSync : function(url, data, callback,loading) {
				return _httpJson({
					url : url,
					data : data,
					type : 'get',
					async : false,
					callback : callback,
					loading : loading
				});
			},
			getSyncData : function(url, data, callback,loading) {
				
				var result;
				
				_httpJson({
					url : url,
					data : data,
					type : 'get',
					async : false,
					callback : function(json){
						result = json;
					},
					loading : loading
				});
				
				return result;
			},
			// post request
			post : function(url, data, callback,loading) {
				return _httpJson({
					url : url,
					data : data,
					type : 'post',
					callback : callback,
					loading : loading
				});
			},
			// sync post request
			postSync : function(url,data,callback,loading) {
				return _httpJson({
					url : url,
					data : data,
					type : 'post',
					async : false,
					callback : callback,
					loading : loading
				});
			},
			// post sync request
			postSyncData : function(url, data, callback,loading) {
				
				var result;
				
				_httpJson({
					url : url,
					data : data,
					type : 'post',
					async : false,
					callback : function(json){
						result = json;
					},
					loading : loading
				});
				
				return result;
			},
			// post json param
			postJSON : function(url, data, callback,loading) {
				return _httpJson({
					url : url,
					data : JSON.stringify(data),
					type : 'post',
					contentType : 'application/json',
					processData : false,
					callback : callback,
					loading : loading
				});
			},
			// validator submit handle
			formValidatorSubmit : function(object,url,data,callback){
				
				var $form = object.$element;
				
				var formValidity = object.isFormValid();
				
				var isUpload = $tools.isUI($form,'fileupload');
				
				var _submit = function(){
					
					var loadindex = $dl.loading({title : $CST.MESSAGE.DATA_SUBMIT_PROCESS});
					
					if(isUpload && $form.ijfileupload('hasFile')){
						
						$form.ijfileupload('url',$CST.ROOTPATH + url);
						
						callback && $form.ijfileupload('completed',callback,loadindex);
						
						data && $form.ijfileupload('formData',data);
						
						$form.ijfileupload('fileupload');
						
					}else{
						//提交
						_httpJson({
							url : url,
							data : data,
							type : 'post',
							callback : callback,
							loading: loadindex
						});
					}
					
				}
				
				if($.type(formValidity) === 'boolean'){
					
					
					formValidity && _submit();
					
				}else{
					
					$.when(formValidity).then(function(){
						
						_submit();
						
					});
				}
				
			}

		}

	}();

	$.IJ.Http = Http;

})(jQuery);