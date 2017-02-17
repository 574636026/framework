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
