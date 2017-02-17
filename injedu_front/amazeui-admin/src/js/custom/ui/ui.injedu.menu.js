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