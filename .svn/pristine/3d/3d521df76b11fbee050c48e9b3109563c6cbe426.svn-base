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
