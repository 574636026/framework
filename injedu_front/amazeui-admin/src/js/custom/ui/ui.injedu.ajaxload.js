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