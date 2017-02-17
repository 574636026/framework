/**
 * 
 * 工具类
 * 
 * Created by joy on 2015/11/25.
 */
!(function($) {

	'use strict';

	if (!$.IJ)
		$.IJ = {};

	var UI = $.AMUI || {};
	var $CST = $.IJ.Const;

	var tools = {
		/**
		 * 
		 * 清除所有缓存
		 * 
		 * 当前使用
		 * _IJDIC_KEY_+code: 数据字典
		 * 
		 * 当前项目中使用$.AMUI.store(LocalStorage),作为前端缓存
		 * 
		 */
		clearCache : function(){
			if($.AMUI.store.enabled){
				$.AMUI.store.clear();
			}
		},
		/**
		 * 
		 * 获取字典缓存
		 * 
		 */
		getDicName : function(code,value){
			var dic = $.AMUI.store.get($CST.DIC + code);
			if(!dic){
				return;
			}
			var dicMap = {},sep=',';
    		$.each(dic.sub, function(i, o) {
    			dicMap[o['value']] = o['item'];
    		});
			return dicMap[value];
		},
		/**
		 * 
		 * 刷新UI(使用dataAPI加载的UI)
		 * @param dom
		 *            表单对象(jQuery对象)
		 */
		refreshUI : function(dom) {
			$.each(UI.DOMWatchers, function(i, watcher) {
				watcher(dom);
			});
		},
		/**
		 * 
		 * 判断对象是否是amaze ui对象(包括自己定义的组件)
		 * 
		 * @param object
		 *            要判断的对象(jQuery对象)
		 * @param uiname
		 * 			  ui组件名称(如selected)
		 */
		isUI : function(object,uiname){
			return object.is('[data-ij-' + uiname +'],[data-am-' + uiname+']')||object.data('amui.' + uiname) != undefined;
		},
		/**
		 * 
		 * 修改UI组件变量(遵循UI初始化规则)
		 * 
		 * @param object
		 *            要判断的对象(jQuery对象)
		 * @param uiname
		 * 			  ui组件名称(如selected)
		 * @param value {object}
		 * 			需要变更的参数
		 */
		setUIData : function(object,uiname,value){
			var dataOptionsName = 'data-ij-' + uiname;
			var options = $.extend(UI.utils.options(object.attr(dataOptionsName)),value);
			object.attr(dataOptionsName,JSON.stringify(options));
		},
		/**
		 * 
		 * html encode
		 * 
		 * @param str
		 * 			需要转义的字符串
		 * 
		 */
		htmlencode : function(str){
			if($.type(str) === 'string'){
				return str.replace(/&/g, '&amp;').replace(/"/g, '&quot;').replace(/'/g, '&#39;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
			}
			return str;
		},
		/**
		 * 
		 * 链接跳转
		 * 
		 * @param url
		 * 			跳转地址
		 * @param options
		 * 		- target
		 * 			href blank
		 * 		- params
		 * 			请求参数
		 * 		- metohd
		 * 			get | post
		 * 		- abs
		 * 			是否绝对地址
		 * 
		 */
		linkUrl:function(url,options){
			if(!url){
				return;
			}
			var options = options || {};
			//safari的a标签没有onclick事件
			var form = document.getElementById('_linkform_');
			if(form == undefined || form == null){
				form = document.createElement('form');  
				form.id = '_linkform_';
				form.method = options.metohd || 'get';
				document.body.appendChild(form);
			}
			if(options.target){
				form.target = options.target;
			}
			
			var params = options.params;
			
			if(params){
				for (var x in params) {        
			        var opt = document.createElement("input");
			        opt.type = 'hidden';
			        opt.name = x;        
			        opt.value = params[x];
			        form.appendChild(opt);        
			    }
			}
			if(!options.abs){
				url = $CST.ROOTPATH + url;
			}
			form.action = url;
			form.submit();
			$(form).remove();
		},
		/**
		 * 
		 * 填充表单数据
		 * 
		 * @param form
		 *            表单对象(jQuery对象)
		 * @param data
		 *            表单数据(JSON)
		 */
		fillForm : function(object, data) {

			if (!data)
				return;
			var that = this;
			var inputs = $(':input:not(:button)' , object);
			
			$.each(data,function(name,value){
				var $input = inputs.filter('[name="'+name+'"]');
				if($input && $input.length > 0){
					if(that.isUI($input,'select')){
						that.setUIData($input,'select',{dataValue : value});
					}else if(that.isUI($input,'datepicker')){
						$input.attr('value',value);
					}else if(that.isUI($input,'switch')){
						$input.prop('checked',value);
					}else if(that.isUI($input,'dic')){
						that.setUIData($input,'dic',{dataValue : value});
					}else if(that.isUI($input,'datetimepicker')){
						value && that.setUIData($input,'datetimepicker',{startDate : value['dateStart'],endDate:value['dateEnd']});
					}else if($input.is('textarea')){
						if(value != undefined){
							$input.val(value.replace(new RegExp('<br/>','g'),'\r\n'));
						}
					}else if(that.isUI($input,'tags')){
						that.setUIData($input,'tags',{dataValue : value});
					}else{
						$input.val(value);
					}
				}
			});

		},
		/**
		 * 
		 * 填充数据(替换属性值为'ij-bind'的元素)
		 * 
		 * @param object
		 *            jQuery对象
		 * @param data
		 *            表单数据(JSON)
		 */
		fillObject : function(object, data) {
			if (!data)
				return;
			var that = this;
			
			var binds = $('[ij-bind]', object);

			$.each(data,function(name,value){
				var $bind = binds.filter('[ij-bind="'+name+'"]');
				if($bind && $bind.length > 0){
					
					value = that.format($bind.attr('ij-format'),value);
					
					if(that.isUI($bind,'dic')){
						that.setUIData($bind,'dic',{dataValue : value});
					}else if($bind.is('img')){
						$bind.attr('src',value);
					}else{
						var _value = value;
						if($.isArray(value)){
							_value = value.join('');
						}
						if($bind.attr('ij-escape') != 'false'){
							_value = that.htmlencode(_value);
						}
						$bind.replaceWith(_value);
					}
				}
				
			});
		},
		format : function(key,data){
			if(key == undefined){
				return data;
			}
			if(data == undefined){
				return '';
			}
			switch (key) {
				case 'unix':
					if(data < 10000000000){
						data = data * 1000;
					}
					data = moment(data).format('YYYY-MM-DD HH:mm:ss');
					break;
				case 'byte':
					var _byte_fun = function(data){
						var _typeArray = ['B','KB','MB','GB'];
						var _unit = 1024;
						var result = '';
						for(var i=0;i<_typeArray.length;i++){
							if(data > _unit){
								data = data / _unit;
							}else{
								result = data.toFixed(2) +  _typeArray[i];
								break;
							}
						}
						return result;
					}
					data = _byte_fun(data);
					break;
			}
			return data;
		},
		/**
		 * 
		 * 重置表单
		 * 
		 * @param object
		 * 		jQuery对象
		 */
		resetForm : function(object){
			if(!object) return;
			var that = this;
			$(':input:not(:submit, :button)',object).each(function(){
				var $this = $(this);
				if(that.isUI($this,'datepicker')){
					$this.attr('value','');
				}else if(that.isUI($this,'selected')){
					$this.val('');
					$this.trigger('changed.selected.amui');
				}else{
					$this.val('');
				}
			});
		},
		/**
		 * 
		 * 清空表单
		 * 
		 * @param object
		 *            jQuery对象
		 */
		clearForm : function(object){
			if(!object) return;
			// 重置表单数据
			this.resetForm(object);
			// 重置验证结果
			object.removeData('amui.validator amui.checked')
			.find(':input:not(:submit, :button, :disabled, .am-novalidate)')
			.removeData('validity amui.dfdValidity');
		},
		/**
		 * 
		 * 数组转换
		 * 
		 * 	例: 
		 * 		arr ==> [{name : 'tt',des : 'dd'},{name : 't1',des : 'd1'}..]
		 * 
		 * 		tranArray(arr,name) == ['tt','t1'...]
		 * 
		 * @param arr
		 * 			数组对象
		 * @param field
		 * 			转化对象中的字段名
		 * @returns
		 */
		tranArray : function(arr,field){
			if(!arr || arr.length <=0 ) return;

			return $.map(arr,function(n){return n[field]});
		},
		/**
		 * 
		 * 字符串格式化
		 * 		
		 * 例：
		 * 	formatString('test{0},test{1}','key1','key2')
		 *  formatString('test{title},test{name}',{'title' : '111','name' : '2222'})
		 * @param result
		 * 			需要格式化的字符串
		 * @param args{object or array}
		 * 			参数列表
		 * @returns
		 */
		formatString : function(result,args){
			if(arguments.length <= 1){
				return result;
			}
			if (arguments.length == 2 && typeof (args) == "object") {
			       for (var key in args) {
			                if(args[key]!=undefined){
			                    var reg = new RegExp("({" + key + "})", "g");
			                    result = result.replace(reg, args[key]);
			                }
			            }
			        }
			 else {
			        for (var i = 0; i < arguments.length - 1; i++) {
			                if (arguments[i + 1] != undefined) {
			                	var reg= new RegExp("({)" + i + "(})", "g");
			                    result = result.replace(reg, arguments[i + 1]);
			                }
			            }
			  }
			return result;
		},
		/**
		 * 
		 * 得到表单对象数据
		 * 
		 * @param $form
		 * 
		 */
		serializeObject : function($form) {
			
			var serializeObj = {};
			
			$(':input:not(:submit, :button)',$form).each(function(){
				
				var tvalue = serializeObj[this.name];
				if(tvalue){
					if($.type(tvalue) != 'array'){
						serializeObj[this.name] = [tvalue];
					}
					serializeObj[this.name].push(this.value);
				}else{
					serializeObj[this.name] = this.value;
				}
				
			});
			return serializeObj;
		},
		/**
		 * 
		 * 得到表单提交数据
		 * 
		 * (主要是数组数据的处理,如果有参数param.name值为[],则提交的参数为param[0].name,param[1].name)
		 * 
		 * @param $form
		 * 
		 */
		serializeFormParam : function($form) {
			
			var data = this.serializeObject($form);
			
			var result = {};
			
			$.each(data,function(name,value){
				
				if($.type(value) == 'array' && name.indexOf('.') != -1){
					
					var idx = name.indexOf('.'),
						namePre = name.substring(0,idx),
						nameAft = name.substring(idx + 1);
					
					$.each(value,function(i,data){
						
						result[namePre + '[' + i +'].' + nameAft] = data;
						
					});
					
				}else{
					
					result[name] = value;
					
				}
				
			});
			
			return result;
		}
	};

	$.IJ.Tools = tools;
})(jQuery);