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