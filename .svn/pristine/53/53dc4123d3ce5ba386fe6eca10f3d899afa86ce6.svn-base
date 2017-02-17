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
    	
    	if($item && $item.length){
    		var $childs = $item.find('[role=group]').children();
    		if($childs.length){
    			$childs.remove();
    		}
    		this.$element.tree('openFolder',$item);
    	}else{
    		this.$element.tree('refresh');
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