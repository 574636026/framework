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