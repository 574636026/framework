/*! Injedu UI v0.0.1 | by Joy Zhou | Date 2016 | Licensed under MIT | 2016-11-02T18:34:58+0800 */ 
/**
 * 
 * 常量信息
 * 
 * get,post都只请求json数据
 * 
 * required dialog
 * 
 * Created by joy on 2015/11/20.
 */
!(function($,w) {
	
	'use strict';

	if (!$.IJ)
		$.IJ = {};
	
	var cst = {
		ROOTPATH : w.ROOTPATH || '',
		// http 状态码
		STATUS : {
			SUCCESS : 200,
			ACCESSDECISION : 403,
			ERROR : 500
		},
		// 异常消息
		STATUSMESSAGE :{
			403 : '访问被拒绝,您当前没有权限！',
			500 : '系统内部错误,请联系管理员!',
			1000 : '当前账户失效,请重新登录！'
		},
        // 表单远程验证状态码
        VALIDATE : {
            SUCCESS : 'success',
            FAILD : 'faild'
        },
		// 文本信息
		MESSAGE : {
			UNKONW_STATUS : '未知异常,请联系管理员!',
			DATA_NEED_SELECTED : '请选择操作列！',
			DATA_SELECT_ONE : '只能选择一个列！',
			DATA_ADD_SUCCESS : '数据创建成功,是否关闭?',
			DATA_EDIT_SUCCESS : '数据更新成功,是否关闭?',
			DATA_SUBMIT_PROCESS : '数据提交中,请稍后...',
			DATA_CONFIRM_DELETE : '确认要删除选中的数据？',
			DATA_DELETE_PROCESS : '数据删除中,请稍后...',
			DATA_CONFIRM_SORT_TOP : '确认要置顶选中的数据？',
			DATA_CONFIRM_SORT_DOWN : '确认要置底选中的数据？',
			
		},
		// 缓存key
		STORY_KEY:{
			DIC: '_IJDIC_KEY_',
			MENU: '_IJ_MENU_',
			MENU_ACTIVE : '_IJ_MENU_ACTIVE_'
		},
		URL : {
			// 获取字典信息
			DIC : '/sys/config/dic/getSubDic/%s',
			// 文件上传
			UPLOAD : '/sys/file/upload/%s'
		}
	}
	
	$.IJ.Const = cst;
	
})(jQuery,window);
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
/**
 * 
 * 消息窗工具
 * 
 * Created by joy on 2015/11/20.
 */
!(function($) {

	'use strict';

	if (!$.IJ)
		$.IJ = {};

    var customDailog = {

        alert : function(options) {
        	var options = $.extend({
        		layer : 1200,
        		content : '',
        		onConfirm : function(){}
        	},options);
        	var layer = options.layer;
            var html = [];
            html.push('<div class="am-modal am-modal-alert" tabindex="-1" style="z-index:'+layer+'">');
            html.push('<div class="am-modal-dialog am-modal-self-content">');
            if(options.title){
                html.push('<div class="am-modal-self-header">' + options.title + '</div>');
            }
            html.push('<div class="am-modal-self-body">' + options.content + '</div>');
            html.push('<div class="am-modal-self-footer am-cf"><button class="am-modal-btn am-btn am-btn-primary am-radius">确定</button>');
            html.push('</div>');
            html.push('</div>');
            return $(html.join('')).appendTo('body').modal().on('closed.modal.amui', function() {
                $(this).remove();
                options.onConfirm();
            });
        },
        confirm : function(options) {
        	
        	var options = $.extend({
        		layer : 1200,
        		content : '',
        		onConfirm : function(){},
        		onCancel : function(){}
        	},options);
        	var layer = options.layer;
            var html = [];
            html.push('<div class="am-modal am-modal-confirm" tabindex="-1" style="z-index:'+layer+'">');
            html.push('<div class="am-modal-dialog am-modal-self-content">');
            if(options.title){
                html.push('<div class="am-modal-self-header">' + options.title + '</div>');
            }
            html.push('<div class="am-modal-self-body">' + options.content + '</div>');
            html.push('<div class="am-modal-self-footer am-cf">');
            html.push('<button class="am-modal-btn am-btn am-btn-default am-radius" data-am-modal-cancel>取消</button>');
            html.push('<button class="am-modal-btn am-btn am-btn-primary am-radius" data-am-modal-confirm>确定</button>');
            html.push('</div>');
            html.push('</div>');
            html.push('</div>');

            return $(html.join('')).appendTo('body').modal({
                onConfirm: function(opt) {
                    options.onConfirm(opt);
                },
                onCancel: function() {
                    options.onCancel();
                }
            }).on('closed.modal.amui', function() {
                $(this).remove();
            });
        },
        loading : function(options) {
        	
        	var options = $.extend({
        		layer : 1500,
        		title : '正在载入...'
        	},options);
        	var layer = options.layer;
            var html = [];
            html.push('<div class="am-modal am-modal-loading am-modal-no-btn" tabindex="-1" style="z-index:'+layer+'">');
            html.push('<div class="am-modal-dialog">');
            html.push('<div class="am-modal-hd">' + options.title + '</div>');
            html.push('<div class="am-modal-bd">');
            html.push('<span class="am-icon-spinner am-icon-spin"></span>');
            html.push('</div>');
            html.push('</div>');
            html.push('</div>');

            var modalOptions = {};

            return $(html.join('')).appendTo('body').modal().on('closed.modal.amui', function() {
                    $(this).remove();
                });
        },
        actions : function(options) {

            var options = $.extend({
                items : [],
                onSelected : function(index,obj){}
            },options);

            var html = [];
            html.push('<div class="am-modal-actions">');
            html.push('<div class="am-modal-actions-group">');
            html.push('<ul class="am-list">');
            if(options.title){
                html.push('<li class="am-modal-actions-header">' + options.title + '</li>');
            }
            options.items.forEach(function(item, index) {
                html.push('<li><button class="am-btn am-btn-block '+(item.css || '')+'">' + item.content + '</button></li>');
            });
            html.push('</ul>');
            html.push('</div>');
            html.push('<div class="am-modal-actions-group">');
            html.push('<button class="am-btn am-btn-secondary am-btn-block" data-am-modal-close>取消</button>');
            html.push('</div>');
            html.push('</div>');

            var $acions = $(html.join('')).appendTo('body');

            $('.am-list > li > button',$acions).on('click',function(){
                options.onSelected($(this).parent().index(), this);
            });

            return $acions.modal().on('closed.modal.amui', function() {
                $(this).remove();
            });
        },
        prompt : function(options){

        	var options = $.extend({
        		content : '',
        		onConfirm : function(){},
        		onCancel : function(){}
        	},options);
        	
            var html = [];
            html.push('<div class="am-modal am-modal-prompt" tabindex="-1">');
            html.push('<div class="am-modal-dialog">');
            if(options.title){
                html.push('<div class="am-modal-hd">' + options.title + '</div>');
            }
            html.push('<div class="am-modal-bd">' + options.content + '</div>');
            html.push('<input type="text" class="am-modal-prompt-input">');
            html.push('<div class="am-modal-footer am-cf">');
            html.push('<span class="am-modal-btn am-btn am-btn-default am-radius" data-am-modal-cancel>取消</span>');
            html.push('<span class="am-modal-btn am-btn am-btn-default am-radius" data-am-modal-confirm>提交</span>');
            html.push('</div>');
            html.push('</div>');
            html.push('</div>');

            return $(html.join('')).appendTo('body').modal({
                onConfirm: function(e) {
                    options.onConfirm(e);
                },
                onCancel: function(e) {
                    options.onCancel(e);
                }
            }).on('closed.modal.amui', function() {
                $(this).remove();
            });

        },
        popup : function(options) {
        	var options = $.extend({
        		content : '',
        		onClose : function(){}
        	},options);

            var html = [];
            html.push('<div class="am-popup">');
            html.push('<div class="am-popup-inner">');
            html.push('<div class="am-popup-hd">');
            if(options.title){
                html.push('<h4 class="am-popup-title">' + options.title + '</h4>');
            }
            html.push('<span data-am-modal-close  class="am-close">&times;</span>');
            html.push('</div>');
            html.push('<div class="am-popup-bd">' + options.content + '</div>');
            html.push('</div> ');
            html.push('</div>');
            return $(html.join('')).appendTo('body').modal().on('closed.modal.amui', function() {
                $(this).remove();
                options.onClose();
            });
        },
        toast : function(options){
            options = options || {};
            if($.type(options) == 'string'){
                options = {content : options};
            }
            options.content = options.content || '';
            options.time = options.time || 1000;

            // add toast panel
            var $toastPanel = $('#in-toast-panel');
            if($toastPanel.length == 0){
                $toastPanel = $('<div id="in-toast-panel" class="am-toast-panel"></div>');
                $toastPanel.appendTo('body');
            }
            var html = [];

            html.push('<div class="am-alert am-toast am-animation-slide-top" data-am-alert>');
            html.push('<button type="button" class="am-close">&times;</button>');
            html.push('<div class="am-panel-bd">');
            html.push(options.content);
            html.push('</div>');
            html.push('</div>');

            var $html = $(html.join(''));
            $html.appendTo($toastPanel);
            setTimeout(function(){
                $html.remove();
            },options.time);
        },
        pureView : function(src){

            var html = [];
            html.push('<div class="am-modal am-modal-no-btn" tabindex="-1">');
            html.push('<div class="am-modal-dialog">');
            html.push('<div class="am-modal-hd">');
            html.push('<span data-am-modal-close  class="am-close"><i class="am-icon-times-circle"></i></span>');
            html.push('</div>');
            html.push('<div class="am-modal-bd">');
            html.push('<img class="am-img-thumbnail" src="'+src+'"/>');
            html.push('</div>');
            html.push('</div>');
            html.push('</div>');
            return $(html.join('')).appendTo('body').modal().on('closed.modal.amui', function() {
                $(this).remove();
            });

        }

    }

	var dialog = function() {
		return {
			// 关闭遮罩层,可用于alert、toast、loading、confirm
			close : function(el,timeout) {
				timeout > 0 ? setTimeout(function() {el.modal('close')},timeout) : el.modal('close');
			},
			// 提示框
			alert : function(msg) {
				return customDailog.alert({
					title : '系统消息',
					content : msg
				});
			},
			// 确认框
			confirm : function(options){
				return customDailog.confirm(options);
			},
			// 提示框(一定时间后自动消失)
			toast : function(msg) {
				return customDailog.toast({
					content : msg,
					time : 5000
				});
			},
			// 载入框
			loading : function(options) {
				if($.type(options) == 'string'){
					return customDailog.loading({
						'title' : options
					});
				}else{
					return customDailog.loading(options);
				}
			},
            // 弹出层(建议为内页html元素)
            popup : function(content){
                return customDailog.popup({content : content});
            },
            // 预览框
            pureView : function(src){
                return customDailog.pureView(src);
            },
            actions : function(options){

                return customDailog.actions(options);
            }
		}
	}();

	$.IJ.Dialog = dialog;

})(jQuery);

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
/**
 * 
 * 消息窗工具
 * 
 * Created by joy on 2015/11/20.
 */
!(function($) {

	'use strict';

	if (!$.IJ)
		$.IJ = {};
    
    var $http = $.IJ.Http;
    var $tools = $.IJ.Tools;


	var ijWindow = function() {
		
		var modalPage = function(object,options){
			
			object.modal({
                closeViaDimmer : options.closeViaDimmer,
                cancelable : false
            }).on('close.modal.amui',function(){
                options.animation && $(this).removeClass(options.animationClass).addClass(options.animationRemoveClass);
            }).on('closed.modal.amui', function() {
                var $this = $(this);
                setTimeout(function(){
                    options.isRemove && $this.remove();
                    options.onClose();
                },500);
            }).on('opened.modal.amui',function(){
                options.onOpen();
                $tools.refreshUI($(this));
            });
			
		}
		
        var popupPage = function(options){
        	var options = $.extend({
        		target : false,
        		content : '',
        		isRemove : true,
        		animation : true,
        		animationClass : 'am-animation-scale-up',
        		animationRemoveClass : 'am-animation-scale-down am-animation-reverse',
        		winClass : 'am-popup am-popup-lg',
        		closeViaDimmer : false,
        		onClose : function(){},
        		onOpen : function(){}
        	},options);
        	
        	if(options.direction == 'bottom'){
        		options.animation = false;
        		options.winClass = 'am-modal-actions am-modal-bottom';
        	}
        	
            var html = [];
            html.push('<div class="'+options.winClass+'">');
            html.push('<div class="am-popup-inner">');
            html.push('<div class="am-popup-hd am-popup-hd-fixed">');
            if(options.title) {
                html.push('<h4 class="am-popup-title">' + options.title + '</h4>');
            }
            html.push('<span data-am-modal-close  class="am-close">&times;</span>');
            html.push('</div>');
            html.push('<div class="am-popup-bd">' + options.content + '</div>');
            html.push('</div> ');
            html.push('</div>');

            var $html = $(html.join(''));

            options.animation && $html.addClass(options.animationClass);
            
            $html.appendTo('body');
            
            if(options.target){
            	options.target.on('click',function(){
            		
            		modalPage($html,options);
            	});
            }else{
            	modalPage($html,options);
            }
            return $html;
        }
        
        var prompt = function(options){

        	var options = $.extend({
        		content : '',
        		items : ['<input type="text" class="am-modal-prompt-input">'],
        		onConfirm : function(){},
        		onCancel : function(){}
        	},options);

            var html = [];
            html.push('<div class="am-modal am-modal-prompt" tabindex="-1">');
            html.push('<div class="am-modal-dialog">');
            if(options.title){
                html.push('<div class="am-modal-hd">' + options.title + '</div>');
            }
            html.push('<div class="am-modal-bd">' + options.content );
            options.items.forEach(function(item, index) {
            	html.push(item);
            });
            html.push('</div>');
            html.push('<div class="am-modal-footer am-cf">');
            html.push('<span class="am-modal-btn am-btn am-btn-default am-radius" data-am-modal-cancel>取消</span>');
            html.push('<span class="am-modal-btn am-btn am-btn-default am-radius" data-am-modal-confirm>提交</span>');
            html.push('</div>');
            html.push('</div>');
            html.push('</div>');

            return $(html.join('')).appendTo('body').modal({
            	relatedTarget: this,
            	cancelable : false,
                onConfirm: function(e) {
                    options.onConfirm(e);
                },
                onCancel: function(e) {
                    options.onCancel(e);
                }
            }).on('closed.modal.amui', function() {
                var $this = $(this);
                setTimeout(function(){
                    $this.remove();
                },500);

            }).on('opened.modal.amui',function(){
                $tools.refreshUI($(this));
            });

        }
        
        var sidebar = function(options){
        	
        	var options = $.extend({
        		direction : 'right',
        		content : '',
        		onOpen : function(){},
        		onClose : function(){}
        	},options);
        	
        	var html = [];
            html.push('<div class="am-offcanvas">');
            html.push('<div class="am-offcanvas-bar am-offcanvas-bar-auto');
            if('right' == options.direction){
            	html.push(' am-offcanvas-bar-flip');
            }
            html.push('">');
            html.push('<div class="am-offcanvas-content">');
            html.push('</div></div></div>');
            var $object = $(html.join('')).appendTo('body')
            .on('open.offcanvas.amui',function(){
            	var _this = $(this);
            	options.onOpen(_this);
            	$tools.refreshUI(_this);
            }).on('close.offcanvas.amui',function(){
            	options.onClose($(this));
            });
        	
        	$('.am-offcanvas-content',$object).html(options.content);
        	
        	return $object;
        }

		return {
			// 弹出窗口(居中)
			open : function(options) {
				if (!options.url)
					return;
				options.content = $http.load(options.url);
				
				return popupPage(options);
			},
			// 弹出窗口(居中高度随内容变化)
			openAuto : function(options){
				if (!options.url)
					return;
				options.content = $http.load(options.url);
				options.winClass = 'am-popup am-popup-auto';
				return popupPage(options);
			},
			// 弹出窗口(底部)
			bottom : function(options){
				if (!options.url)
					return;
				options.content = $http.load(options.url,options.loading);
				options.direction = 'bottom';
				return popupPage(options);
			},
			// 侧边栏
			sidebar : function(options){
				
				if(options.url){
					options.content = $http.load(options.url,options.loading);
				}
				
				if(!options.content){
					return;
				}
				
				return sidebar(options);
			},
			// 输入窗口
			prompt : function(options){
				return prompt(options);
			},
			// 弹出排序窗口
			sorted : function(options){
				
				var options = $.extend({
					title : '调整顺序',
					sort : 0
				},options);

				var $input = '<input name="sort" type="number" value="'+options.sort+'" class="am-modal-prompt-input"/>';
				options.items = [$input];
				
				return prompt(options);
			}
		}
	}();

	$.IJ.Window = ijWindow;

})(jQuery);

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
/**
 * 
 * Socket 工具类
 * 
 * Created by joy on 2016/10/24.
 * 
 */
!(function($,w) {
	
	'use strict';
	
	if (!$.IJ)
		$.IJ = {};
	
	var $CST = $.IJ.Const;
	
	var SocketUtils = {
		
		init : function(options){

			var options = $.extend({},options);
			
			if(!options.server){
				
				var ROOT = $CST.ROOTPATH;
				var schme = 'http:',ctx = ROOT;
				var idx = ROOT.indexOf(schme);
				if(idx > -1){
					ctx = ROOT.substring(idx + schme.length);
				}
				
				options.server = 'ws:' + ctx + '/webSocketServer';
			}
			
			return new WebSocket(options.server);
		}
			
	}
	
	$.IJ.SocketUtils = SocketUtils;
	
})(jQuery,window);
/**
 * Created by joy on 2015/11/25.
 */
!(function ($) {

    'use strict';

    var UI = $.AMUI || {};

    var $tools = $.IJ.Tools;
    var $dl = $.IJ.Dialog;
    
    var Ajaxload = function(element, options) {
    	this.options = $.extend({}, Ajaxload.DEFAULTS, options);
    	this.$element = $(element);
    	this.init();
    }
    
    Ajaxload.DEFAULTS = {
    	target : '#page-context'
    }
    
    Ajaxload.prototype.init = function(){
    	
    	var options = this.options;
    	
    	var url = options.url || this.$element.attr('href');
    	
    	if(!url) return;
    	
    	this.$element.on('click',function(){

            var load = $dl.loading();
            
            $(options.target).load(url + "?t="+new Date().getTime(),function(){
            	$tools.refreshUI($(this));
                load.modal('close');
            });
            
            return false;
        });
    }
    
    UI.plugin('ajaxload', Ajaxload , {dataOptions : 'data-ij-ajaxload'});
    
    // Init code
    UI.ready(function(context) {
        $('[data-ij-ajaxload]', context).ajaxload();
    });


})(jQuery);
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
/**
 * 
 * ui datetimepicker(日期时间选择)
 * 
 * 使用介绍
 * 		$element.ijdatetimepicker(options)
 * Data API
 * 		<input data-ij-datetimepicker="options"/>
 * 参数说明
 * 
 * 		format 				{string}  			-  日期格式
 * 		autoApply 			{boolean}			-  是否自动确认(选择完成后,默认关闭)
 * 		showEasyBtn 		{boolean}			-  是否显示便捷按钮(当天,昨天,7天前,14天前,30天前,当月)
 * 		singleDatePicker	{boolean}			-  是否显示当个日期(默认是,否则选择日期区间)
 * 		minuteStep			{int}				-  步长(分钟)
 * 		alwaysShowCalendars	{boolean}			-  一直显示日期框(便捷按钮存在时)
 * 		startDate			{string}			-  开始日期
 * 		endDate				{string}			-  结束日期
 * 		drops				{string}			-  显示方向('down','up')
 * 
 * Created by joy on 2015/11/25.
 */
!(function($) {

	'use strict';

	var UI = $.AMUI || {};
	
	var IJDatetimepicker = function(element, options) {
    	this.options = $.extend({}, IJDatetimepicker.DEFAULTS, options);
    	this.$element = $(element);
    	this.init();
    }
	
	IJDatetimepicker.DEFAULTS = {
		format : 'YYYY-MM-DD',
		autoApply: true,
		showEasyBtn : 'default',
		singleDatePicker : true,
		showDropdowns:false,
		alwaysShowCalendars:false,
		minuteStep : 5,
		startDate : undefined,
		endDate : undefined,
		drops : 'down'
	}
	
	IJDatetimepicker.RANGES = {
		'default' : {
			'今天'	: [moment().startOf('day'), moment().endOf('day')],
			'昨天'	: [moment().startOf('day').subtract(1, 'days'), moment().endOf('day').subtract(1, 'days')],
			'7天前'	: [moment().startOf('day').subtract(6, 'days'), moment().endOf('day')],
			'14天前'	: [moment().startOf('day').subtract(13, 'days'), moment().endOf('day')],
			'30天前'	: [moment().startOf('day').subtract(29, 'days'), moment().endOf('day')],
			'当前月'	: [moment().startOf('month'), moment().endOf('month')]
		},	
		'after' : {
			'今天'	: [moment().startOf('day'), moment().endOf('day')],
			'明天'	: [moment().startOf('day').add(1, 'days'), moment().endOf('day').add(1, 'days')],
			'7天后'	: [moment().startOf('day'), moment().endOf('day').add(6, 'days')],
			'14天后'	: [moment().startOf('day'), moment().endOf('day').add(13, 'days')],
			'一个月' 	: [moment().startOf('day'), moment().endOf('day').add(1, 'month')],
			'三个月'	: [moment().startOf('day'), moment().endOf('day').add(3, 'month')]
		}
	}
	
	// 原控件设置
	IJDatetimepicker.SOURCE_DEFAULTS = {
		// 是否单个日期
		singleDatePicker: true,
		// 显示下拉框(年、月选择)
		showDropdowns: false,
		// 自动确认
		autoApply: true,
		// 一直显示日期框(ranges存在时)
		alwaysShowCalendars: false,
		// 时间选择器
		timePicker: false,
		// 24小时制
		timePicker24Hour: false,
		// 选择秒
		timePickerSeconds: false,
		// 步长(分钟)
		timePickerIncrement: 1,
		// 显示方向
		drops : 'down',
		// 本地化
		locale : {
			format: 'YYYY-MM-DD HH:mm:ss',
			separator: ' ~ ',
			applyLabel: '确认',
			cancelLabel: '取消',
			fromLabel: 'From',
			toLabel: 'To',
			customRangeLabel: '选择',
			daysOfWeek: ['日', '一', '二', '三', '四', '五','六'],
			monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
			firstDay: 1
		}
	}
	
	// 初始化
	IJDatetimepicker.prototype.init = function(){
		
		var options = this.genConfig();
		
		this.$element.daterangepicker(options);
		
		return this.$element;
	}
	
	/**
	 * 生成控件配置
	 */
	IJDatetimepicker.prototype.genConfig = function(){
		
		var options = this.options;
		
		var daterangepickerOptions = $.extend({}, IJDatetimepicker.SOURCE_DEFAULTS);
		
		daterangepickerOptions.singleDatePicker = options.singleDatePicker;
		daterangepickerOptions.autoApply = options.autoApply;
		daterangepickerOptions.showDropdowns = options.showDropdowns;
		daterangepickerOptions.alwaysShowCalendars = options.alwaysShowCalendars;
		daterangepickerOptions.timePickerIncrement = options.minuteStep;
		daterangepickerOptions.drops = options.drops;
		daterangepickerOptions.startDate = options.startDate;
		daterangepickerOptions.endDate = options.endDate;
		// 设置快捷日期
		if(options.showEasyBtn){
			daterangepickerOptions.ranges = IJDatetimepicker.RANGES[options.showEasyBtn];
		}
		// 时间选择器
		daterangepickerOptions.locale.format = options.format;
		
		if(options.format.indexOf('H') != -1){
			daterangepickerOptions.timePicker = true;
			daterangepickerOptions.timePicker24Hour = true;
		}
		
		if(options.format.indexOf('s') != -1){
			daterangepickerOptions.timePickerSeconds = true;
		}
		
		return daterangepickerOptions;
	}
	
	IJDatetimepicker.prototype.destroy = function(){
		this.$element.removeData('amui.ijdatetimepicker');
		this.$element.removeData('datetimepicker');
	}
	
	UI.plugin('ijdatetimepicker', IJDatetimepicker , {dataOptions : 'data-ij-datetimepicker'});
    
    UI.ready(function(context) {
    	 $('[data-ij-datetimepicker]', context).ijdatetimepicker();
    });
	
})(jQuery);
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
/**
 * 
 * 数据查找带回 UI 组件
 * 
 * 使用介绍 $element.ijlookup(options) Data API <input data-ij-lookup="options"/>
 * 
 * 参数说明
 * 		url 		{string}  			-  地址
 * 		lookType	{string}			-  查找类型：single 单选,mulitiple 多选
 * 		displayType	{string}			-  显示类型: input 输入框,defalut 默认
 * 		dataKey		{string}			-  查找带回属性,默认为ID
 * 		onSelected	{function}	-  		-  回调函数
 * Created by joy on 2015/11/25.
 */
!(function($) {

	'use strict';

	var UI = $.AMUI || {};
	var $winapi = $.IJ.Window;
	var $CST = $.IJ.Const;
	var $dl = $.IJ.Dialog;
	var $tools = $.IJ.Tools;

	var IJLookUp = function(element, options) {
		this.options = $.extend({}, IJLookUp.DEFAULTS, options);
		this.$element = $(element);
		this.$table = [];
		this.tableApi = undefined;

		this.winOptions = $.extend({}, IJLookUp.WINDEFAULTS);
		this.winkey = 'lookup-win';
		this.rememberDatakey = 'lookup_remember_data';
		this.winOptions.target = this.$element;
		this.$window = {};

		this.init();
	}

	IJLookUp.prototype.init = function() {
		var $element = this.$element;
		var options = this.options;
		// 准备参数
		this.prepare();
		// 渲染
		this.render();
		// 创建lookup窗口
		this.$window = this.$element.data(this.winkey);
		if (!this.$window) {
			this.$window = $winapi.sidebar(this.winOptions);
			this.$window.css('z-index', 1111);
			this.$element.data(this.winkey, this.$window);
		}
		// 绑定默认事件
		this.bindEvents();
	}

	/**
	 * 参数准备
	 */
	IJLookUp.prototype.prepare = function() {

		var options = this.options;
		var $element = this.$element;
		var displayType = options.displayType;
		var _datakey = options.dataKey;

		this.winOptions.url = options.url;

		if (displayType != 'input') {
			return;
		}

		if (options.onSelected) {
			return;
		}

		if (options.lookType == 'single') {
			options.onSelected = function(data) {
				$element.val(data[_datakey]);
				this.close();
			}
		} else {
			options.onSelected = function(data) {
				$element.val($tools.tranArray(data,_datakey).join(','));
			}
		}
	}

	// 记忆选择并排除重复
	IJLookUp.prototype.rememberData = function(data) {
		var $element = this.$element;
		var rememberDatakey = this.rememberDatakey;
		var _datakey = this.options.dataKey;
		var cache = $element.data(rememberDatakey) || {};
		$.each(data, function(i, temp) {
			var id = temp[_datakey];
			cache[id] = temp;
		});
		$element.data(rememberDatakey, cache);
		var result = [];
		$.each(cache, function(i, temp) {
			result.push(temp);
		});
		return result;
	}

	/**
	 * 渲染
	 */
	IJLookUp.prototype.render = function() {

		var displayType = this.options.displayType;

		if (displayType == 'input') {

			this.$wrapper = $(this.options.tpl.wrapper);
			this.$element.wrap(this.$wrapper);
			this.$actions = $(this.options.tpl.actions).insertAfter(
					this.$element);
		}

	}

	IJLookUp.prototype.bindEvents = function() {

		var options = this.options;
		var that = this;

		if(this.$actions){
			// 打开
			this.$actions.on('click', '.am-lookup-open', function() {
				that.open();
				return false;
			});
			// 清除
			this.$actions.on('click', '.am-lookup-clear', function() {
				that.$element.removeData(that.rememberDatakey);
				that.$element.val('');
				return false;
			});
		}
		
	}

	/**
	 * 重置状态
	 */
	IJLookUp.prototype.reset = function() {
		this.$element.removeData(this.winkey);
		this.$window.remove();
		this.$table = [];
		this.tableApi = undefined;
	}

	// 初始化表格
	IJLookUp.prototype.initTable = function() {

		if (this.tableApi) {
			return;
		}
		var that = this;
		var options = this.options;
		var displayType = options.displayType;
		var $table = this.$table = $(options.render.current, this.$window);
		var onSelected = $.proxy(options.onSelected, this);

		this.tableApi = $table.ijtable($.extend({
			toolBarDisplay : 'vertical',
			actions : [ 'refresh', 'selected' ],
			toolEvents : {
				'selected' : {
					title : '确认选择',
					icon : 'am-icon-reply',
					fn : function(e) {
						var data = $table.ijtable('findSelectRowData');
						if (data.length == 0) {
							$dl.alert($CST.MESSAGE.DATA_NEED_SELECTED);
							return;
						}
						if (options.lookType === 'single') {
							if (data.length > 1) {
								$dl.alert($CST.MESSAGE.DATA_SELECT_ONE);
								return;
							}
							onSelected(data[0]);
						} else {
							if (displayType == 'input') {
								data = that.rememberData(data);
							}
							onSelected(data);
						}
					}
				}
			},
			tableOptions : {
				pagingType : 'simple'
			}
		}, this.options.renderOptions));
	}

	// 打开
	IJLookUp.prototype.open = function(receiveData) {
		var $window = this.$window;
		// 回传数据
		this.receiveData = receiveData;
		$window.offCanvas('open');
		// off touch events
		this.$window.hammer().off('swipeleft swipeleft');
		// fix a bug
		$window.off('click.offcanvas.amui');
		$window.on('click.offcanvas.amui', function(e) {
			var $target = $(e.target);
			if ($target.hasClass('am-offcanvas-bar')) {
				return;
			}
			// 使用datatables在翻页时会关闭offcanvas,原因在于翻页后触发的按钮重新生成,不属于原dom
			if ($target.attr('aria-controls')) {
				return;
			}

			if ($target.parents('.am-offcanvas-bar').first().length) {
				return;
			}
			e.stopImmediatePropagation();
			$window.offCanvas('close');
		});
		// 表格未初始化
		this.initTable();
	}

	// 关闭
	IJLookUp.prototype.close = function() {
		if (this.options.displayType == 'input') {
			this.$element.trigger('change');
		}
		this.$window && this.$window.offCanvas('close');
	}

	IJLookUp.WINDEFAULTS = {
		isRemove : false,
		loading : false
	}

	IJLookUp.DEFAULTS = {
		url : '/commom/ui/lookup/lookup.html',
		lookType : 'single', // single,mulitiple
		render : {// 当前只实现table数据带回
			type : 'table',
			current : 'table'
		},
		renderOptions : {},// 内容组件初始化参数
		tpl : {
			wrapper : '<div class="am-input-group am-input-group-sm"></div>',
			actions : '<span class="am-input-group-btn"><a class="am-btn am-btn-primary am-lookup-open"> <i class="am-icon-search" /></a><a class="am-btn am-btn-primary am-lookup-clear"> <i class="am-icon-close" /></a></span>'
		},
		onOpen : undefined,
		onSelected : undefined,
		dataKey : 'id',
		displayType : 'input' // 显示类型 'input','default'
	}

	UI.plugin('ijlookup', IJLookUp, {
		dataOptions : 'data-ij-lookup'
	});

	UI.ready(function(context) {
		$('[data-ij-lookup]', context).ijlookup();
	});

})(jQuery);
/**
 * Created by joy on 2015/11/25.
 */
!(function ($) {

    'use strict';

    var UI = $.AMUI || {};
    
    var Store = UI.store;
    
    var $CST = $.IJ.Const;
    
    var $http = $.IJ.Http;
    
    var PARENT_RIGHT_OPEN = "am-icon-angle-down",
    	PARENT_RIGHT_CLOSED = "am-icon-angle-left";
    
    var CACHE_MENU = $CST.STORY_KEY.MENU,CACHE_MENU_ACTIVE = $CST.STORY_KEY.MENU_ACTIVE;
    
    
    var IJMenu = function(element, options){
    	
    	this.options = $.extend({}, IJMenu.DEFAULTS, options);
    	this.$element = $(element);
    	this.$container = $('.ij-menu-container',this.$element);
    	this.initTemplate($CST.ROOTPATH);
    	this.init();
    }
    
    IJMenu.DEFAULTS = {
    	url : ''
    }
    
    // use handlebars
    IJMenu.TEMPLATE ='{{#smenu menus}}'
					+'		{{#if hasChilds}}'
					+'		<a href="{{shref url}}">'
					+'			<i class="am-icon-reorder"></i>'
					+'			<span>{{name}}</span>'
					+'			<i class="am-icon-angle-left am-fr am-margin-right"></i>'
					+'		</a>'
					+'		{{else}}'
					+'		<a href="{{shref url}}">'
					+'			<span>{{name}}</span>'
					+'			<i class="am-icon-angle-left am-fr am-margin-right"></i>'
					+'		</a>'
					+'		{{/if}}'
					+'{{/smenu}}';
    
    IJMenu.prototype.initTemplate = function(ROOT){
    	
    	this.template = Handlebars.compile(IJMenu.TEMPLATE);
    	
    	var menuActives = Store.get(CACHE_MENU_ACTIVE) || [];
    	
    	Handlebars.registerHelper('smenu', function(items, options) {
    		  
    		 var _render_menu = function(items,options){
    			 
    			 var out = '',item;
    			 
    			 for(var i=0, l=items.length; i<l; i++) {
        			 
    				 item = items[i];
    				 
        			 out += '<li>'+options.fn(item);
        			 
        			 if(item.childs.length){
        				 out += '<ul class="am-list am-collapse admin-sidebar-sub';
        				 if(menuActives.length > 0 && $.inArray('nav-' + item.id,menuActives) == 0){
        					 out += ' am-in';
        				 }
        				 out += '"';
        				 out += ' id="nav-' + item.id + '">';
        				 out += _render_menu(item.childs,options);
        				 out +='</ul>';
        			 }
        			 
        			 out += '</li>'
        		 }
    			 
    			 return out;
    		 }
    		 
    		 return _render_menu(items,options);
    	});
    	
    	Handlebars.registerHelper('shref', function(url) {
    		
    		if(url == '' || url == '#'){
    			return 'javascript:;';
    		}else{
    			return ROOT + url;
    		}
    		
    	});
    }
    
    // 初始化菜单数据
    IJMenu.prototype.initData = function(){
    	
    	var $this = this;
    	
    	var url = this.options.url;
    	
    	if(!url){
    		console.log('not init the url to get menus.');
    		return;
    	}
    	
    	if(Store.has(CACHE_MENU)){
			
			$this.render(Store.get(CACHE_MENU));
			
		}else{
			// 数据获取
			$http.get(url, {}, function(data) {
				Store.set(CACHE_MENU,data);
				$this.render(data);
			});
		}
    	
    }
    
    // 绑定默认事件
    IJMenu.prototype.initEvent = function(){
    	
    	var options = this.options;
    	
    	var $element = this.$element;
    	
    	this.$element.on('click','a',function(){
    		
    		var $this = $(this);
    		
    		var _href = $this.attr('href');
    		
    		if(!_href || _href == '#' || _href == 'javascript:;'){
    			
    			var $target = $this.next('ul');
    			
    			if(!$target.length){
    				return;
    			}
    			
    				
    			if($target.data('amui.collapse') == undefined){
    					
    	    		$target.on('open.collapse.amui', function() {
    	    			$('.'+PARENT_RIGHT_CLOSED,$this).removeClass(PARENT_RIGHT_CLOSED).addClass(PARENT_RIGHT_OPEN);
    	    		}).on('close.collapse.amui', function() {
    	    			$('.'+PARENT_RIGHT_OPEN,$this).removeClass(PARENT_RIGHT_OPEN).addClass(PARENT_RIGHT_CLOSED);
    	    		});
    	    		
    			}
	    			
	    		$target.collapse('toggle');
    			
    		}else{
    			
    			var arr = [];
            	
            	// 得到父菜单
            	$this.parentsUntil('.admin-sidebar-list').filter('.admin-sidebar-sub').each(function(){
            		arr.push($(this).attr('id'));
            	});
            	
            	// 保存cookie
            	Store.set(CACHE_MENU_ACTIVE,arr);
    			
    			
    		}
    		
    	});
    	
    }
    
    //
    IJMenu.prototype.init = function(){
    	
    	this.initData();
    	this.initEvent();
    	
    }
    
    // render the menu list
    IJMenu.prototype.render = function(menus){
    	
    	if(!menus || menus.length <=0 ){
    		return;
    	}
    	
        var html = this.template({'menus' : menus});
        	
        this.$container.replaceWith(html);
    	
    }
    
    UI.plugin('ijmenu', IJMenu , {dataOptions : 'data-ij-menu'});
    // Init code
    UI.ready(function(context) {
    	$('[data-ij-menu]', context).ijmenu();
    });


})(jQuery);
/**
 * 
 * 数字输入框 UI 组件
 * 
 * 封装(http://www.virtuosoft.eu/code/bootstrap-touchspin/)
 * 
 * 使用介绍
 * 		$element.ijnumber(options)
 * Data API
 * 		<input data-ij-number="options"/>
 * 
 * 参数说明
 * 		min						{number} 	- 最小值
 * 		max						{number}	- 最大值
 * 		step					{number} 	- 步长
 * 		decimals				{number} 	- 精度
 *    	verticalbuttons			{boolean}	- 是否垂直显示
 *    	prefix					{string}	- 后置显示(一般用于显示单位)
 *      postfix					{string}	- 前置显示(一般用于显示单位)
 * 		stepinterval			{number} 	- 步长间隔
 *    	stepintervaldelay		{number}	- 步长间隔等待
 *      forcestepdivisibility	{string} 	- 
 *      booster					{boolean} 	- 是否快速变动(鼠标按住不动根据boostat比率增长数值)
 *    	boostat					{number} 	- 快速变动速率
 *      maxboostedstep 			{boolean}: 	- 最大步长限制
 *     	mousewheel				{boolean}: 	- 鼠标滚轮
 * 
 * Created by joy on 2015/11/25.
 */
!(function ($) {

	//required amazeui、TouchSpin
	
    'use strict';

    var UI = $.AMUI || {};
    
    var IJNumber = function(element, options) {
    	this.options = $.extend({}, IJNumber.DEFAULTS, options);
    	this.$element = $(element);
    	this.$element.TouchSpin(this.options);
    }
    
    IJNumber.DEFAULTS = {
    	min: 0,
    	max: 10000,
    	initval: '',
    	step: 1,
    	decimals: 0,
    	stepinterval: 100,
    	forcestepdivisibility: 'round', // none | floor | round | ceil
        prefix: '',
        postfix: '',
    	stepintervaldelay: 500,
    	verticalbuttons: false,
    	booster: true,
    	boostat: 10,
    	maxboostedstep: false,
    	mousewheel: true
    }
    
    UI.plugin('ijnumber', IJNumber , {dataOptions : 'data-ij-number'});
    
    UI.ready(function(context) {
    	 $('[data-ij-number]', context).ijnumber();
    });

})(jQuery);
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
/**
 * 
 * 开关切换 UI 组件
 * 
 * 封装(http://www.bootstrap-switch.org)
 * 
 * 使用介绍
 * 		$element.ijswitch(options)
 * Data API
 * 		<input data-ij-switch="options"/>
 * 
 * 参数说明
 * 		state					{boolean} 	- 默认状态
 * 		size					{string}	- 大小
 * 		animate					{boolean} 	- 是否开启动画
 * 		disabled				{boolean} 	- 是否禁用
 *    	readonly				{boolean}	- 是否只读
 *    	indeterminate			{boolean}	- 是否不确定
 *      inverse					{boolean}	- 是否反向
 * 		radioAllOff				{boolean} 	- 
 *    	onColor					{string}	- on颜色
 *      offColor				{string} 	- off颜色
 *      onText					{string} 	- on文字
 *    	offText					{string} 	- off文字
 *      labelText 				{string}: 	- label
 * 
 * Created by joy on 2015/11/25.
 */
!(function ($) {

    'use strict';

    var UI = $.AMUI || {};
    
    var IJSwitch = function(element, options) {
    	this.options = $.extend({}, IJSwitch.DEFAULTS, options);
    	this.$element = $(element);
    	this.$element.bootstrapSwitch(this.options);
    }
   
    IJSwitch.DEFAULTS = {
    	size: 'sm',			//null, 'xs', 'sm', 'normal', 'lg'
    	animate: true,
    	disabled: false,
    	readonly: false,
    	indeterminate: false,
    	inverse: false,
    	radioAllOff: false,
    	onColor: "primary",
    	offColor: "default",
    	onText: "ON",
    	offText: "OFF",
    	labelText: "&nbsp;"
    }
    
    IJSwitch.prototype.state = function(value, skip){
    	return this.$element.bootstrapSwitch('state',value, skip);
    }
    
    IJSwitch.prototype.toggleState = function(skip){
    	return this.$element.bootstrapSwitch('toggleState',skip);
    }
    
    IJSwitch.prototype.destroy = function(){
    	return this.$element.bootstrapSwitch('destroy');
    }
    
    UI.plugin('ijswitch', IJSwitch , {dataOptions : 'data-ij-switch'});
    
    UI.ready(function(context) {
    	 $('[data-ij-switch]', context).ijswitch();
    });

})(jQuery);
/**
 * 
 * table UI组件
 * 
 * 使用jquery datatables(http://datatables.net/)
 * 
 * 使用介绍
 * 		$element.ijtable(options)
 * Data API
 * 		<input data-ij-table="options"/>
 * 参数说明
 *		actionsMap 		{object}
 * 								- query	{string} 	表格查询路径
 *								- get	{string} 	获取详情路径
 *								- save	{string} 	保存路径
 *								- edit	{string}	编辑路径
 *								- del	{string}	删除路径
 *		root			{string}	URL前缀(必须)
 *		isCompact		{boolean}	是否为紧凑型(padding更小)
 *		isScrollX		{boolean}	是否显示横向滚动条
 * 		isRemove		{boolean}	如果为false,则使用逻辑删除,否则为物理删除,默认物理删除
 * 		pageSuffix		{string}	弹出层页面后缀(由于项目使用使用sitemesh,因此默认为.layer)
 * 		actions			{array}		默认功能设置,默认为['del','detail','edit','plus','refresh'],倒序显示
 * 		onAddOpen		{function}	新增页面加载后触发,可完成一些初始化操作
 * 		onEditOpen		{function} 	编辑页面加载后触发,可完成一些初始化操作
 * 		onDetailOpen	{function} 	详情页面加载后触发,可完成一些初始化操作
 * 		toolEvents		{object}	扩展工具栏,可使用actions控制显示顺序(注：此功能与fn参数的区别只在于元素显示的位置)
 * 								- [object key]		对应action name
 * 								- [object value]
 * 										- title		工具title
 * 										- icon		工具icon
 * 										- fn		工具执行方法
 * 		fn 				{object}	自定义功能(只初始化.ij-table内的元素)
 * 									例: 页面存在元素 '<a class="ij-fn-test"></a>'
 * 										可配置 fn : {'test' : function(data,api){}}
 * 		columnsRender	{object}  	自定义列显示
 * 								-[object key]	对应字段名称
 * 								-[object value]	{function(data)}返回最终显示的结果
 * 		toolBarDisplay	{string}	工具栏显示方向 : 'horizontal','vertical'
 *  	tableOptions	{object}	datatables options(具体参数可参考官网)
 * 			
 * 
 * Created by joy on 2015/11/20.
 */
!(function($) {
	'use strict';

	if (!$.IJ)
		$.IJ = {};

	var UI = $.AMUI || {};
	var $CST = $.IJ.Const;
	var $http = $.IJ.Http;
	var $dl = $.IJ.Dialog;
	var $tools = $.IJ.Tools;
	var $window = $.IJ.Window;
	var Store = UI.store;
	var ROOTPATH = $CST.ROOTPATH;
	
	
	var wrapTableClass = 'ij-table';
	
	// 查到table wrapper
	var _findWrapTable = function(grid){
		return grid.parents('.' + wrapTableClass);
	}
	// 查找到工具栏
	var _findToolBar = function(grid) {
		return $('.am-toolbar',_findWrapTable(grid));
	}
	// 查找到搜索栏
	var _findSearchBar = function(grid){
		return $('.am-searchbar',_findWrapTable(grid));
	}
	
	/**
	 * 
	 * 表格工具类
	 * 
	 */
	var tableToolsHandle = {
		//查找选择行数据
		findSelectRowData : function(grid,api){
			var datas = [];
			
			var $check_colum = api.column(0).nodes().to$();
			
			$(':checkbox:checked',$check_colum).each(function(){
				var $tr = $(this).parents('tr');
				var index = api.row($tr).index();
				datas.push(api.rows(index).data()[0]);
			});
			return datas;
		},
		 // 查找选择列数据
		findSelectRowChecked : function(grid,api) {
			
			var key = api.columns(0).dataSrc()[0];
			
			var datas = tableToolsHandle.findSelectRowData(grid,api);
			
			if(datas.length <= 0){
				return [];
			}
			return $.map(datas,function(data){
				return data[key];
			});
		},
		// 只能选中一条操作数据校验
		dataOnlySelected : function(datas){
			if (datas.length == 1) {
				return true
			}else if (datas.length == 0) {
				$dl.alert($CST.MESSAGE.DATA_NEED_SELECTED);
			} else {
				$dl.alert($CST.MESSAGE.DATA_SELECT_ONE);
			}
			return false;
		}
			
	}
	
	/**
	 * 
	 * 表格处理函数
	 * 
	 * @param grid
	 * 			表格对象
	 * @param options
	 * 			表格参数
	 * @param actionsMap
	 * 			表格操作设置
	 * 
	 */
	var dataTableHandle = function() {

		// 表格列生成器
		var columnFactory = function(){


            var renderDom = {
                'checkbox' : '<input type="checkbox" name="{0}" value="{1}"/>',
                'img' : '<img class="am-img-thumbnail ij-img-preview" src="{0}"/>',
                'link' : '<a href="{0}">{1}</a>',
                'bool' : '<span class="am-badge am-radius am-badge-{color}"><i class="am-icon-{icon}"></i></span>',
                'disbool' : '<span class="am-badge am-radius am-badge-{color}"><i class="am-icon-{icon}"></i></span>'
            }
			
			var settings = {
					grid : {},
					columns : [],
					columnDefs : [],
					defaultOrder : [],
                    columnsRender : {},
                    init : function(grid , options){
                        this.grid = grid;
                        this.columns = [];
                        this.columnDefs = [];
                        this.defaultOrder = [];
                        this.columnsRender = options.columnsRender;
                    },
					// 生成选择列
					selectColumn : function(index, name, object){
						var grid = this.grid;
						
						this.columns.push(name ? {'data' : name} : {});
						this.columnDefs.push({
							targets : index,
							render : function(data, type, row) {
								if (type === 'display') {
									return '<input type="checkbox" name="' + name
											+ '" value="' + data + '"/>';
								}
								return data;
							},
							orderable : false,
							searchable : false
							
						});
						
					},
					// TODO 生成操作列
					operationColumn : function(index,name,object){
						
						this.columns.push(name ? {'data' : name} : {});
						this.columnDefs.push( {
							targets : -1,
							data : null,
							render : function(data, type, row) {
								if (type === 'display') {
									return '';
								}
								return data;
							},
							orderable : false,
							searchable : false
						});
					},
                    displayColumn : function(name,renderType,object){

                        var render;

                        switch(renderType){
                            case 'checkbox':
                                render = function(data,type,row){
                                    if (type === 'display') {
                                        return $tools.formatString(renderDom[renderType],name,data);
                                    }
                                    return data;
                                }
                                break;
                            case 'link':
                            	render = function(data,type,row){
	                                if (type === 'display') {
	                                	var show = object.attr('type-link-icon');
	                                	show = show ? '<i class="' + show +'"></i>' : data;
	                                    return data ? $tools.formatString(renderDom[renderType],data,show) : '';
	                                }
	                                return data;
	                            }
                            	 break;
                            case 'img':
                                render = function(data,type,row){
                                    if (type === 'display') {
                                        return data ? $tools.formatString(renderDom[renderType],data) : '';
                                    }
                                    return data;
                                }
                                break;
                            case 'bool':
                            	render = function(data,type,row){
	                                if (type === 'display') {
	                                	
	                                	if(data){
	                                		return $tools.formatString(renderDom[renderType],{color : 'success',icon : 'check'});
	                                	}else{
	                                		return $tools.formatString(renderDom[renderType],{color : 'danger',icon : 'remove'});
	                                	}
	                                	
	                                }
	                                return data;
	                            }
                            	break;
                            case 'disbool' :
                            	render = function(data,type,row){
	                                if (type === 'display') {
	                                	if(data){
	                                		return $tools.formatString(renderDom[renderType],{color : 'danger',icon : 'ban'});
	                                	}else{
	                                		return $tools.formatString(renderDom[renderType],{color : 'success',icon : 'check'});
	                                	}
	                                }
	                                return data;
	                            }
                            	break;
                            case 'dic' :
                            	var dicCode = object.attr('type-dic');
                            	if(dicCode){           
                            		var store_key = '_IJDIC_KEY_' + dicCode;
                            		var dicList;
                            		if(!Store.has(store_key)){
                            			Store.set(store_key,$http.getSyncData($CST.URL.DIC.replace('%s',$CST.dic[dicCode])));
                            		}
                            		dicList = Store.get(store_key).sub;
                            		var dicMap = {},sep=',';
                            		$.each(dicList, function(i, o) {
                            			dicMap[o['value']] = o['item'];
                            		});
                            		render = function(data,type,row){
                            			var text = [],datas  = [];
                            			if($.type(data) == 'string'){
                            				datas = data.split(sep);
                            			}else{
                            				datas.push(data);
                            			}
                            			$.each(datas, function(i, o) {
                            				dicMap[o] != undefined && text.push(dicMap[o]);
                            			});
                            			
    	                                return text.join(sep);
    	                            }
                            	}
                            	break;
                            case 'html':
                            	break;
                            default :
                            	render = function(data,type,row){
	                                if (type === 'display') {
	                                	var _data = $tools.format(object.attr('ij-format'),data);
	                                	return $tools.htmlencode(_data);
	                                }
	                                return data;
	                            }
                                break;
                        }

                        return render;

                    },
					// 生成普通列
					normalColumn : function(index,name,object){
						// 该列是否排序
						var sortable = object.attr('ij-no-sort') != undefined ? false : true;
						// 默认排序列
						var defaultSort = object.attr('ij-default-sort');
                        // 显示类型,默认为文本
                        var renderType = object.attr('ij-type') || 'text';
						
						if (defaultSort != undefined) {
							this.defaultOrder = [ index, defaultSort || 'asc' ];
						}

						var cl = name ? { 'data' : name, 'orderable' : sortable} : {'orderable' : sortable};

                        if(this.columnsRender  && this.columnsRender[name]){

                            var render = this.columnsRender[name];

                            if(render && $.type(render) == 'function'){

                                cl['render'] = function(data,type,row){
                                    if (type === 'display') {
                                        return render(data,type,row);
                                    }
                                    return data;
                                }
                            }

                        }else{

                            var render = this.displayColumn(name,renderType,object);

                            if(render){
                            	cl['render'] = render;
                            }
                            
                        }

						this.columns.push(cl);
						
					}
				}
			return {
				init : function(grid , options){
					settings.init(grid , options);
					$('thead tr th', grid).each(function(i,data){
						var $this = $(this);
						var name = $this.attr('ij-cloumn');
						if ($this.hasClass('ij-check')) {
							settings.selectColumn(i, name, $this);
						} else if ($this.hasClass('ij-operation')) {
							settings.operationColumn(i, name, $this)
						}else{
							settings.normalColumn(i,name,$this);
						}
					});
					return settings;
				}
			}
		}();
		
		// 重构请求参数,以适应项目要求
		var convertRequest = function(grid,data, columns) {

			var selfData = {};
			// 重构分页参数
			selfData["pager.pageSize"] = data.length;
			selfData["pager.currentPage"] = (data.start / data.length) + 1;
			// 重构排序参数
			if (data.order && data.order[0]) {
				var column = columns[data.order[0].column].data;
				// 获取列名
				selfData["order.column"] = column;
				selfData["order.sortType"] = data.order[0].dir;
			}
			// 获得查询参数
			var searchDom = ':input:not(:button)';
			$(searchDom,_findSearchBar(grid)).each(function(){
				var $this = $(this),
					value = $.trim($this.val());
				value && (selfData["query[" + $this.attr('name')+"]"] = value);
			});
			
			return selfData;
		}
		
		return {
			
			init : function(settings, options){
				var grid = settings.grid;
				var options = $.extend({},options);
				var scrollY = 392;
				// 紧凑表格
				if(settings.options.isCompact){
					scrollY = 311;
					!grid.hasClass('am-table-compact') && grid.addClass('am-table-compact');
				}
				// 横向滚动条
				if(settings.options.isScrollX){
					scrollY += 16;
					options.scrollX = true;
				}
				// 默认参数
				var options = $.extend({
					// 纵向滚动条(超过此高度出现滚动条)
					scrollY : scrollY + 'px',
					// 隐藏横向滚动条
                    scrollX : false,
					// 语言设置
					oLanguage : {
						sLengthMenu : '<div class="am-form-group">显示</div> <div class="am-form-group am-form-select">_MENU_</div> <div class="am-form-group">条</div>',
						sInfo : "<label>- 共 _TOTAL_ 条 -</label>",
						sInfoEmpty : "共 0 条",
						sProcessing : '<div class="ij-loading am-center"></div>',
						sInfoThousands:  ","
					},
					// 是否开启过滤功能
					searching : false,
					// 是否自动计算宽度
					autoWidth : false,
					// 是否显示遮罩过程
					processing : true,
					// 
					lengthMenu : [ [ 10, 20, 50, -1 ], [ "10", "20", "50", "ALL" ] ],
					// 排序设置
					order : [ [1,'asc'] ],
					// 列设置
					columns : [],
					// 特殊列设置
					columnDefs : []
				},options);
				
				// 表格行列配置
				if(options.columns.length == 0){
					var columnSettings = columnFactory.init(grid,settings.options);
					options.columns = columnSettings.columns;
					options.columnDefs = columnSettings.columnDefs;
					if(columnSettings.defaultOrder.length > 0){
						options.order = columnSettings.defaultOrder;
					}
				}
				// 表格数据配置
				var query = settings.action.query;
				
				if(typeof query == 'string'){
					
					// 设置请求服务端
					options.serverSide = true;
					// ajax 请求
					options.ajax = function(data, callback, settings) {

						$http.post(query, convertRequest(grid,data, settings.aoColumns), function(res) {
							if (!res)return;
							var datatablesJson = {};
							var pager = res.pager;
							datatablesJson.recordsTotal = pager.totalSize;
							datatablesJson.recordsFiltered = pager.totalSize;
							datatablesJson.data = res.resultList || [];
							callback(datatablesJson);
						});
						
					};
					
				}else{
					options.data = query;
				}
				
				return grid.DataTable(options);
			}
			
		}
		
	}();

	/***
	 * 
	 * 表格操作处理(默认绑定增、删、改方法)
	 * 
	 */
	var gridOperationHandle = function(){
		
		/**
		 * 
		 * 工具栏生成
		 * 
		 */
		var toolActionFactory = function(){
			
			// 工具栏dom
			var dom = '<a title="{title}" href="javascript:;"><span class="{icon}"></span></a>';
			
			// 默认工具栏配置
			var toolEvent = {
				// 表格刷新功能
				'refresh' : {
					title : '刷新',
					icon : 'am-icon-refresh',
					fn : function(e) {
						e.preventDefault();
						e.data.api.draw();
					}
				},	
				// 表格新增功能
				'plus' : {
					title : '新增',
					icon : 'am-icon-plus',
					fn : function(e) {
						e.preventDefault();
						var action = e.data.action,
							api = e.data.api,
							target = 'save',
							onAddOpen = e.data.options.onAddOpen,
							prepareData = e.data.options.prepareData;
						var page = $window.open({
								url : e.data.page[target],
								onOpen : function(obj) {
									var form = $('.ij-edit', obj);
									// 绑定提交功能
									form.validator({
										submit : function(){
											$http.formValidatorSubmit(
													this,
													action[target],
													prepareData ? prepareData(form) : form.serialize(),
													function(data){
														$tools.clearForm(form);
														api.draw();
														$dl.confirm({'content' : $CST.MESSAGE.DATA_ADD_SUCCESS,onConfirm : function(){$dl.close(page)}});
													}
												);
											
											return false;
										}
									});
									// 触发自定义事件
									onAddOpen && onAddOpen(form);
							}
						});
					}
				},
				// 表格编辑功能
				'edit' : {
					title : '编辑',
					icon : 'am-icon-edit',
					fn : function(e) {
						e.preventDefault();
						var id = e.data.grid.ijtable('findOnlyRowChecked');
						if(!id){return;}
						var action = e.data.action,
							api = e.data.api,
							target = 'edit',
							onEditOpen = e.data.options.onEditOpen,
							prepareData = e.data.options.prepareData;
						// 获取表单数据
						var detail = $http.getSyncData(action['get'] + '/' + id);
						
						if(!detail) return;
						
						var page = $window.open({
							url : e.data.page[target],
							onOpen : function(obj) {
							var form = $('.ij-edit', obj);
							// 绑定提交功能
							form.validator({
								submit : function(){
									$http.formValidatorSubmit(
											this,
											action[target] + '/' + id,
											prepareData ? prepareData(form) : form.serialize(),
											function(data){
												api.draw(false);
												$dl.confirm(
													{'content' : $CST.MESSAGE.DATA_EDIT_SUCCESS,
														onConfirm : function(){
															$dl.close(page);
													}
												});
											}
										);
									
									return false;
								}
							});
							// 触发自定义事件
							onEditOpen ? onEditOpen(form,detail) : $tools.fillForm(form, detail);
						}
					});
					
				}
			},
			// 表格详情功能
			'detail' : {
				title : '详情',
				icon : 'am-icon-tasks',
				fn : function(e) {
					e.preventDefault();
					var id = e.data.grid.ijtable('findOnlyRowChecked');
					if(!id){return;}
					var action = e.data.action,
					 	target = 'detail',
					 	onDetailOpen = e.data.options.onDetailOpen;
					// 获取表单数据
					var detail = $http.getSyncData(action['get'] + '/' + id);
					
					if(!detail) return;
					
					$window.open({
						url : e.data.page[target],
						onOpen : function(form) {
							// 触发自定义事件
							if(onDetailOpen){
								onDetailOpen(form,detail);
							}else{
								// 填充表单数据
								$tools.fillObject(form, detail);
							}
						}
					});
				}
			},
			'del' : {
				title : '删除',
				icon : 'am-icon-trash-o',
				fn : function(e) {
					e.preventDefault();
					var api = e.data.api,
						ids = tableToolsHandle.findSelectRowChecked(e.data.grid, e.data.api);
					if (ids.length > 0) {
						var action = e.data.action;
						$dl.confirm({
							content : $CST.MESSAGE.DATA_CONFIRM_DELETE,
							onConfirm : function() {
								$http.post(action['del'], {
									'ids' : ids
								}, function(data) {
									api.draw();
								},
								{title : $CST.MESSAGE.DATA_DELETE_PROCESS});
							}
						});
					} else {
						$dl.alert($CST.MESSAGE.DATA_NEED_SELECTED);
					}
				}
			 },
			 'sort' : {
				 title : '排序',
				 icon : 'am-icon-paixu',
				 fn : function(e){
					 e.preventDefault();
					 var api = e.data.api,
					 	 ids = tableToolsHandle.findSelectRowChecked(e.data.grid, e.data.api);
					 if(!tableToolsHandle.dataOnlySelected(ids)){return;}
					 var action = e.data.action,
					 	id = ids[0],
					 	target = 'sort',
					 	onSortOpen = e.data.options.onSortOpen;
					 
					var detail = $http.getSyncData(action['get'] + '/' + id);
					
					if(!detail) return;
					
					$window.sorted({
						sort : detail['sort'],
						onConfirm : function(e){
							
							$http.post(action[target]+ '/' + id,{'sort' : e.data},
									function(data) {api.draw()},
								{title : $CST.MESSAGE.DATA_SUBMIT_PROCESS});
							
						}
					});
				 }
			 },
			 'sortTop' : {
				 title : '置顶',
				 icon : 'am-icon-zhiding',
				 fn : function(e){
					 e.preventDefault();
						var api = e.data.api,
							ids = tableToolsHandle.findSelectRowChecked(e.data.grid, e.data.api);
						if(!tableToolsHandle.dataOnlySelected(ids)){return;}
						var action = e.data.action,
							id = ids[0];
						$dl.confirm({
							content : $CST.MESSAGE.DATA_CONFIRM_SORT_TOP,
							onConfirm : function() {
								$http.post(action['sortTop'] + '/' + id,{},function(data) {
									api.draw();
								},
								{title : $CST.MESSAGE.DATA_SUBMIT_PROCESS});
							}
						});
				 }
			 },
			 'sortDown' : {
				 title : '置底',
				 icon : 'am-icon-zhidi',
				 fn : function(e){
					 e.preventDefault();
						var api = e.data.api,
							ids = tableToolsHandle.findSelectRowChecked(e.data.grid, e.data.api);
						if(!tableToolsHandle.dataOnlySelected(ids)){return;}
						var action = e.data.action,
							id = ids[0];
						$dl.confirm({
							content : $CST.MESSAGE.DATA_CONFIRM_SORT_DOWN,
							onConfirm : function() {
								$http.post(action['sortDown'] + '/' + id, {},function(data) {
									api.draw();
								},
								{title : $CST.MESSAGE.DATA_SUBMIT_PROCESS});
							}
						});
				 }
			 }
			}
			
			return {
				register : function(options){
					toolEvent = $.extend(toolEvent,options);
				},
				/**
				 * 
				 * 工具类初始化
				 * @param toolBar
				 * 			工具类对象
				 * @param action
				 * 			功能
				 * @param settings
				 * 			表格配置
				 * 
				 */
				init : function(toolBar,action,settings){
					var toolAction = toolEvent[action];
					toolAction &&
					$($tools.formatString(dom, {title : toolAction.title , icon : toolAction.icon}))
					.prependTo(toolBar)
					.on('click',settings, toolAction.fn);
				}
				
			}
		}();
		
		/**
		 * 
		 * 表格默认事件
		 * 
		 */
		var defaultEvent = {
			// 单元格事件
			'cells' : function(api) {

				var grid = this;
                
				var $headers = api.tables().header().to$();
				var $containers = api.tables().containers().to$();
				// 全选
				$headers.on('click','.ij-check :checkbox',function(e){
					
					var $this = $(this);
					
					var name = $this.parent().attr('ij-cloumn');
					
					var $cells = api.cells().nodes().to$();
					
					$(':checkbox[name="'+ name + '"]',$cells).prop('checked',$this.prop('checked'));
					
				});
				
                // img pureView
				$containers.on('click', 'td:has(.ij-img-preview)', function(e) {

					var url = $('.ij-img-preview',$(this)).attr('src');
					
                    $dl.pureView(url);

                });

			},
			// 表格搜索事件
			'search' : function(api){
				$('.ij-search',_findSearchBar(this)).on('click',function(){
					api.draw();
				});
			},
			// 表格重置事件
			'reset' : function(api){
				var bar = _findSearchBar(this);
				$('.ij-reset',bar).on('click',function(){
					$tools.resetForm(bar);
					api.draw();
				});
			}
		}
		
		return {

			init : function(settings , api) {

				settings.api = api;
				var options = settings.options;
				var $grid = settings.grid;
				var $tableWrapper = _findWrapTable($grid);
				var $toolBar = _findToolBar($grid);
				// 绑定表格默认事件
				for(var event in defaultEvent){
					defaultEvent[event].call($grid,api);
				}
				// 注册工具栏
				if(options.toolEvents){
					toolActionFactory.register(options.toolEvents);
				}
				var toolBarDisplay = settings.options.toolBarDisplay;
				if(!$toolBar.hasClass('am-toolbar-' + toolBarDisplay)){
					$toolBar.addClass('am-toolbar-' + toolBarDisplay);
				}
				// 初始化工具栏
				if(options.actions){
					for (var i=0;i<options.actions.length;i++) {
						toolActionFactory.init($toolBar,options.actions[i],settings);
					}
				}
				// 绑定自定义工具
				if(options.fn){
					var targetFn = '.ij-fn';
					var toolFunction = $(targetFn,$tableWrapper);
					if(toolFunction.length){
						for (var action in options.fn) {
							$(targetFn + '-' + action,toolFunction).on('click',function(){
								options.fn[action] && options.fn[action].call(this,tableToolsHandle.findSelectRowData($grid,api),api);
							});
						}
					}
				}
				
			}

		}
		
		
	}();
	
	
	var DataTableSupport = function(dataTableHandle,gridOperationHandle,tableToolsHandle){
		
		/**
		 * 
		 * 参数准备
		 * @param obj
		 * 			表格对象或者ID
		 * @param actionsMap
		 * 			功能设置
		 * @param options
		 * 			自定义参数
		 */
		var prepare = function(obj,options){
			
			var settings = {};
			// 表格对象
			var grid = (typeof obj == 'string') ? $('#' + obj) : obj;
			
			var root = options.root;
			
			// 配置
			var options = $.extend({
				isCompact : false,
				isRemove : true,
                pageSuffix : 'html',
				actions : ['del','detail','edit','plus','refresh'],
				toolBarDisplay : 'horizontal'
			},options);

			// 默认功能
			var action = $.extend({
				query : 	root + '/list',
				get : 		root + '/get',
				save : 		root + '/save',
				edit : 		root + '/edit',
				del : 		root + '/remove',
				sort : 		root + '/sort',
				sortTop:	root + '/sortTop',
				sortDown:	root + '/sortDown'
			},options.actionsMap);

            var suffix = options.pageSuffix;
			// 子页面设置
			var page = {
				save : 		'/page'+root + '/edit.'+ suffix,
				edit :	 	'/page'+root + '/edit.'+ suffix,
				detail: 	'/page'+root + '/detail.' + suffix
			}

			// 使用逻辑删除
			if (options.isRemove == false) {
				action.del = root + '/delete';
			}
			
			options.tableOptions = undefined;
			
			return {
				'grid' : 		grid,
				'page' : 		page,
				'action' :		action,
				'options' : 	options
			};
		}
		
		
		return {
			init : function(obj, options) {
				
				var settings = prepare(obj , options);
				
				var api = dataTableHandle.init(settings,options.tableOptions);
				
				gridOperationHandle.init(settings,api);
				
				return api;
			}
		}
	}(dataTableHandle,gridOperationHandle,tableToolsHandle);

	// init
	var IJTable = function(element, options) {
    	this.options = $.extend({}, options);
    	this.$element = $(element);
    	if(!$.fn.DataTable.isDataTable(this.$element)){
    		this.api = DataTableSupport.init(this.$element,this.options);
    	}
    }
	
	// table tools
	IJTable.prototype.findSelectRowData = function(){
		return tableToolsHandle.findSelectRowData(this.$element,this.api);
	}
	
	// table tools find only one data
	IJTable.prototype.findOnlyRowData = function(){
		var datas = tableToolsHandle.findSelectRowData(this.$element,this.api);
		return tableToolsHandle.dataOnlySelected(datas) ? datas[0] : false;
	}
	
	// table tools find only one checked data
	IJTable.prototype.findOnlyRowChecked = function(){
		var ids = tableToolsHandle.findSelectRowChecked(this.$element,this.api);
		return tableToolsHandle.dataOnlySelected(ids) ? ids[0] : false;
	}
	
	UI.plugin('ijtable', IJTable , {dataOptions : 'data-ij-table'});
	    
	 // Init code
    UI.ready(function(context) {
        $('[data-ij-table]', context).ijtable();
    });
	
})(jQuery);

/**
 * 
 * ui tags
 * 
 * 使用介绍
 * 		$element.ijtags(options)
 * Data API
 * 		<input data-ij-tags="options"/>
 * 参数说明
 * 
 * 		dataValue			{string}			-  初始值
 * 
 * Created by joy on 2015/11/25.
 */
!(function($) {

	'use strict';

	var UI = $.AMUI || {};
	var $http = $.IJ.Http;
	var $CST = $.IJ.Const;
	
	var IJTags = function(element, options) {
    	this.options = $.extend({}, IJTags.DEFAULTS, options);
    	this.$element = $(element);
    	this.init();
    }
	
	IJTags.DEFAULTS = {
		dataValue: undefined,
			// 分隔符,默认为逗号
		sep : ',',
			// 回车键、空格键
		confirmKeys: [13, 32],
			// 是否移除标签左右的空格
		trimValue: false,
			// 是否允许重复的标签
		allowDuplicates: false
	}
	
	// 初始化
	IJTags.prototype.init = function(){
		
		this.$element.tagsinput(this.options);
		
		if(this.options.dataValue){
			this.$element.tagsinput('add', this.options.dataValue);
		}
		
		return this.$element;
	}
	
	IJTags.prototype.destroy = function(){
		this.$element.removeData('amui.ijtags');
		this.$element.selected('destroy');
	}
	
	UI.plugin('ijtags', IJTags , {dataOptions : 'data-ij-tags'});
    
    UI.ready(function(context) {
    	 $('[data-ij-tags]', context).ijtags();
    });
	
})(jQuery);
/**
 * 
 * Tree UI 组件
 * 
 * 使用介绍
 * 		$element.ijtree(options)
 * Data API
 * 		<input data-ij-tree="options"/>
 * 
 * 参数说明
 * 		itemSelect				{boolean}	- 节点是否可选
 * 		multiSelect				{boolean}	- 是否多选
 * 		cacheItems				{boolean}	- 是否缓存节点
 * 		folderSelect			{boolean}	- 父节点是否可选
 * 		source					{object} 	- 数据源
 * 									- root		根节点
 * 									- childs	子节点
 * 		actions					{object}	- 自定义点击事件
 * events
 * 		selected.tree.amui
 * 
 * Created by joy on 2015/11/25.
 */
!(function ($) {

	//required amazeui
	
    'use strict';

    var UI = $.AMUI || {};
    var $http = $.IJ.Http;
    var $tools = $.IJ.Tools;
    
    var IJTree = function(element, options) {
    	this.options = $.extend({}, IJTree.DEFAULTS, options);
    	this.$element = $(element);
    	this.init();
    	this.bindActions();
    }
    
    IJTree.DEFAULTS = {
    	root : '',
    	childs: '',
    	itemSelect:true,
        multiSelect: false,
        cacheItems: true,
        folderSelect: false	
    }
    
    IJTree.TPL = {
    	PARENT : '<li class="am-tree-branch am-hide"  data-template="treebranch" role="treeitem" aria-expanded="false">'+
				 	'<div class="am-tree-branch-header">' +
				 	'<button class="am-tree-branch-name">' +
				 	'<span class="am-tree-icon am-tree-icon-folder"></span> ' +
				 	'<span class="am-tree-label"></span>'+
				 	'</button>'+
				 	'</div>'+
				 	'<ul class="am-tree-branch-children" role="group"></ul>'+
				 	'<div class="am-tree-loader" role="alert">Loading...</div>'+
				  '</li>',
		CHILDREN:'<li class="am-tree-item am-hide" data-template="treeitem" role="treeitem">' +
					'<button class="am-tree-item-name">'+
				 	'<span class="am-tree-icon am-tree-icon-item"></span> '+
				 	'<span class="am-tree-label"></span>'+
				 	'</button>'+
			 	 '</li>'    		
    }
    
    // 初始化
    IJTree.prototype.init = function(){
    	
    	var $element = this.$element;
    	var options = this.options;
    	
    	if(!$element.has('[data-template=treebranch]').length){
    		$element.append(IJTree.TPL.PARENT);
    	}
    	
    	if(!$element.has('[data-template=treeitem]').length){
    		$element.append(IJTree.TPL.CHILDREN);
    	}
    	
    	if(!options.dataSource){
    		
    		var _call = function(opt,callback,data){
    			
    			var type = $.type(opt);
    			
    			if(type == 'string'){
    				
    				var url = $tools.formatString(opt,data);
    				
    				$http.get(url,{},function(_json){
    					
    					if($.type(_json) == 'array'){
    						callback({data : _json});
    					}else{
    						callback({data : [_json]});
    					}
    					
    				});
    				
    			}else if(type == 'function'){
    				
    				opt(data,callback);
    				
    			}else{
    				
    				callback({data : opt});
    			}
    		}
    		
    		options.dataSource = function(data,callback){
    			
    			if(!data.title){
    				
    				_call(options.root,callback,data);
    				
    			}else{
    				
    				_call(options.childs,callback,data);
    				
    			}
    			
    		}
    		
    	}
    	
    	$element.tree(options);
    	
    }
    
    // 绑定自定义事件
    IJTree.prototype.bindActions = function(){
    	
    	if(!this.options.actions){
    		return;
    	}
    	
    	var $element = this.$element;
    	
    	$.each(this.options.actions,function(act,fn){
    		
    		$element.on('click','.ij-' + act , function(e){
    			var $item = $(this).closest('[role=treeitem]');
				var data = $item.data();
				fn(data,$item);
    			
    			return false;
    		});
    	});
    	
    	
    }
    
    // 刷新节点
    IJTree.prototype.refresh = function($item){
    	
    	if(!$item.length){
    		this.$element.tree('refresh');
    	}else{
    		var $childs = $item.find('[role=group]').children();
    		if($childs.length){
    			$childs.remove();
    		}
    		this.$element.tree('openFolder',$item);
    	}
    	
    }
    // 刷新父节点
    IJTree.prototype.refreshParent = function($item){
    	this.refresh($item.parents('[role=treeitem]:first'));
    }
    
    UI.plugin('ijtree', IJTree , {dataOptions : 'data-ij-tree'});
    
    UI.ready(function(context) {
    	 $('[data-ij-tree]', context).ijtree();
    });

})(jQuery);