/*!
 * jQuery twitter bootstrap wizard plugin
 * Examples and documentation at: http://github.com/VinceG/twitter-bootstrap-wizard
 * version 1.0
 * Requires jQuery v1.3.2 or later
 * Supports Bootstrap 2.2.x, 2.3.x, 3.0
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
 * Authors: Vadim Vincent Gabriel (http://vadimg.com), Jason Gill (www.gilluminate.com)
 *
 * update by Soy_meng
 *
 */
;(function($) {
var bootstrapWizardCreate = function(element, options) {
	var element = $(element);
	var obj = this;

	// selector skips any 'li' elements that do not contain a child with a tab data-toggle
    // SELF UPDATE
	var baseItemSelector = 'li';
	var historyStack = [];

	// Merge options with defaults
	var $settings = $.extend({}, FormWizard.DEFAULTS, options);
	var $activeTab = null;
	var $navigation = null;

    this.isTabShow = true;

    //tab show , the 'index' may by number or object
    var tabShow = function(index){
        $navigation.tabs('open',index);
        // the bootstrap tab
        // $navigation.find('a:eq('+index+')').tab('show')
    }

	this.rebindClick = function(selector, fn)
	{
		selector.unbind('click', fn).bind('click', fn);
	}
    

	this.fixNavigationButtons = function() {
		// Get the current active tab
		if(!$activeTab.length) {
			// Select first one
            // SELF UPDATE
            tabShow(0);
			//$navigation.find('a:first').tab('show');
			$activeTab = $navigation.find(baseItemSelector + ':first');
		}

		// See if we're currently in the first/last then disable the previous and last buttons
		$($settings.previousSelector, element).toggleClass($settings.disabledClass, (obj.firstIndex() >= obj.currentIndex()));
		$($settings.nextSelector, element).toggleClass($settings.disabledClass, (obj.currentIndex() >= obj.navigationLength()));
		$($settings.nextSelector, element).toggleClass($settings.hideClass, (obj.currentIndex() >= obj.navigationLength() && $($settings.finishSelector, element).length > 0));
		$($settings.lastSelector, element).toggleClass($settings.hideClass, (obj.currentIndex() >= obj.navigationLength() && $($settings.finishSelector, element).length > 0));
		$($settings.finishSelector, element).toggleClass($settings.hideClass, (obj.currentIndex() < obj.navigationLength()));
		$($settings.backSelector, element).toggleClass($settings.disabledClass, (historyStack.length == 0));
		$($settings.backSelector, element).toggleClass($settings.hideClass, (obj.currentIndex() >= obj.navigationLength() && $($settings.finishSelector, element).length > 0));

		// We are unbinding and rebinding to ensure single firing and no double-click errors
		obj.rebindClick($($settings.nextSelector, element), obj.next);
		obj.rebindClick($($settings.previousSelector, element), obj.previous);
		obj.rebindClick($($settings.lastSelector, element), obj.last);
		obj.rebindClick($($settings.firstSelector, element), obj.first);
		obj.rebindClick($($settings.finishSelector, element), obj.finish);
		obj.rebindClick($($settings.backSelector, element), obj.back);

		if($settings.onTabShow && typeof $settings.onTabShow === 'function' && $settings.onTabShow($activeTab, $navigation, obj.currentIndex())===false){
			return false;
		}
	};

	this.next = function(e) {

		// If we clicked the last then dont activate this
		if(element.hasClass('last')) {
			return false;
		}

		if($settings.onNext && typeof $settings.onNext === 'function' && $settings.onNext($activeTab, $navigation, obj.nextIndex())===false){
			return false;
		}

        if($activeTab.data('isTabShow') == false){
            return false;
        }

		var formerIndex = obj.currentIndex();
		var $index = obj.nextIndex();

	  // Did we click the last button
		if($index > obj.navigationLength()) {
		} else {
		  historyStack.push(formerIndex);
          tabShow($index);
		  // SELF UPDATE
		  // $navigation.find(baseItemSelector + ':visible:eq(' + $index + ') a').tab('show');
		}
	};

	this.previous = function(e) {

		// If we clicked the first then dont activate this
		if(element.hasClass('first')) {
			return false;
		}

		if($settings.onPrevious && typeof $settings.onPrevious === 'function' && $settings.onPrevious($activeTab, $navigation, obj.previousIndex())===false){
			return false;
		}

		var formerIndex = obj.currentIndex();
		var $index = obj.previousIndex();

		if($index < 0) {
		} else {
		  historyStack.push(formerIndex);
           // SELF UPDATE
            tabShow($index);
		  //$navigation.find(baseItemSelector + ':visible:eq(' + $index + ') a').tab('show');
		}
	};

	this.first = function (e) {
		if($settings.onFirst && typeof $settings.onFirst === 'function' && $settings.onFirst($activeTab, $navigation, obj.firstIndex())===false){
			return false;
		}

		// If the element is disabled then we won't do anything
		if(element.hasClass($settings.disabledClass)) {
			return false;
		}

		historyStack.push(obj.currentIndex());
        // SELF UPDATE
        tabShow(0);
		//$navigation.find(baseItemSelector + ':eq(0) a').tab('show');
	};

	this.last = function(e) {
		if($settings.onLast && typeof $settings.onLast === 'function' && $settings.onLast($activeTab, $navigation, obj.lastIndex())===false){
			return false;
		}

		// If the element is disabled then we won't do anything
		if(element.hasClass($settings.disabledClass)) {
			return false;
		}

		historyStack.push(obj.currentIndex());
        // SELF UPDATE
        tabShow(obj.navigationLength());
		//$navigation.find(baseItemSelector + ':eq(' + obj.navigationLength() + ') a').tab('show');
	};

	this.finish = function (e) {
	  if ($settings.onFinish && typeof $settings.onFinish === 'function') {
	    $settings.onFinish($activeTab, $navigation, obj.lastIndex());
	  }
	};

	this.back = function () {
	  if (historyStack.length == 0) {
	    return null;
	  }

	  var formerIndex = historyStack.pop();
	  if ($settings.onBack && typeof $settings.onBack === 'function' && $settings.onBack($activeTab, $navigation, formerIndex) === false) {
	    historyStack.push(formerIndex);
	    return false;
	  }

	  element.find(baseItemSelector + ':eq(' + formerIndex + ') a').tab('show');
	};

	this.currentIndex = function() {
		return $navigation.find(baseItemSelector).index($activeTab);
	};

	this.firstIndex = function() {
		return 0;
	};

	this.lastIndex = function() {
		return obj.navigationLength();
	};
	this.getIndex = function(e) {
		return $navigation.find(baseItemSelector).index(e);
	};
	this.nextIndex = function() {
		return $navigation.find(baseItemSelector).index($activeTab) + 1;
	};
	this.previousIndex = function() {
		return $navigation.find(baseItemSelector).index($activeTab) - 1;
	};
	this.navigationLength = function() {
		return $navigation.find(baseItemSelector).length - 1;
	};
	this.activeTab = function() {
		return $activeTab;
	};
	this.nextTab = function() {
		return $navigation.find(baseItemSelector + ':eq('+(obj.currentIndex()+1)+')').length ? $navigation.find(baseItemSelector + ':eq('+(obj.currentIndex()+1)+')') : null;
	};
	this.previousTab = function() {
		if(obj.currentIndex() <= 0) {
			return null;
		}
		return $navigation.find(baseItemSelector + ':eq('+parseInt(obj.currentIndex()-1)+')');
	};
	this.show = function(index) {
	  var tabToShow = isNaN(index) ?
      element.find(baseItemSelector + ' a[href=#' + index + ']') :
      element.find(baseItemSelector + ':eq(' + index + ') a');
	  if (tabToShow.length > 0) {
	    historyStack.push(obj.currentIndex());
        tabShow(index);
	  }
	};
	this.disable = function (index) {
		$navigation.find(baseItemSelector + ':eq('+index+')').addClass($settings.disabledClass);
	};
	this.enable = function(index) {
		$navigation.find(baseItemSelector + ':eq('+index+')').removeClass($settings.disabledClass);
	};
	this.hide = function(index) {
		$navigation.find(baseItemSelector + ':eq('+index+')').addClass('am-hide');
	};
	this.display = function(index) {
		$navigation.find(baseItemSelector + ':eq('+index+')').removeClass('am-hide');
	};
	this.remove = function(args) {
		var $index = args[0];
		var $removeTabPane = typeof args[1] != 'undefined' ? args[1] : false;
		var $item = $navigation.find(baseItemSelector + ':eq('+$index+')');

		// Remove the tab pane first if needed
		if($removeTabPane) {
			var $href = $item.find('a').attr('href');
			$($href).remove();
		}

		// Remove menu item
		$item.remove();
	};

	var innerTabClick = function (e) {
		// Get the index of the clicked tab
		var $ul = $navigation.find(baseItemSelector);
		var clickedIndex = $ul.index($(e.currentTarget).parent(baseItemSelector));
		var $clickedTab = $( $ul[clickedIndex] );
		if($settings.onTabClick && typeof $settings.onTabClick === 'function' && $settings.onTabClick($activeTab, $navigation, obj.currentIndex(), clickedIndex, $clickedTab)===false){
		    return false;
		}
	};

	var innerTabShown = function (e) {  // use shown instead of show to help prevent double firing
		var $element = $(e.target).parent();
		var nextTab = $navigation.find(baseItemSelector).index($element);

		// If it's disabled then do not change
		if($element.hasClass($settings.disabledClass)) {
			return false;
		}

		if($settings.onTabChange && typeof $settings.onTabChange === 'function' && $settings.onTabChange($activeTab, $navigation, obj.currentIndex(), nextTab)===false){
				return false;
		}

		$activeTab = $element; // activated tab
		obj.fixNavigationButtons();
	};

	this.resetWizard = function() {

		// remove the existing handlers
		//$('a[data-toggle="tab"]', $navigation).off('click', innerTabClick);
		//$('a[data-toggle="tab"]', $navigation).off('shown shown.bs.tab', innerTabShown);
        //SELF UPDATE
        $($settings.tagSelector, $navigation).off('click.tabs.amui', innerTabClick);
        $($settings.tagSelector, $navigation).off('opened.tabs.amui', innerTabShown);

		// reset elements based on current state of the DOM
		$navigation = element.find('ul:first', element);
		$activeTab = $navigation.find(baseItemSelector + $settings.activeSelector, element);

		// re-add handlers
		//$('a[data-toggle="tab"]', $navigation).on('click', innerTabClick);
		//$('a[data-toggle="tab"]', $navigation).on('shown shown.bs.tab', innerTabShown);
        $($settings.tagSelector, $navigation).on('click.tabs.amui', innerTabClick);
        $($settings.tagSelector, $navigation).on('opened.tabs.amui', innerTabShown);

		obj.fixNavigationButtons();
	};

	$navigation = element.find('ul:first', element);
	$activeTab = $navigation.find(baseItemSelector + $settings.activeSelector, element);

	if(!$navigation.hasClass($settings.tabClass)) {
		$navigation.addClass($settings.tabClass);
	}

	// Load onInit
	if($settings.onInit && typeof $settings.onInit === 'function'){
		$settings.onInit($activeTab, $navigation, 0);
	}

	// Load onShow
	if($settings.onShow && typeof $settings.onShow === 'function'){
		$settings.onShow($activeTab, $navigation, obj.nextIndex());
	}

	//$('a[data-toggle="tab"]', $navigation).on('click', innerTabClick);
	// attach to both shown and shown.bs.tab to support Bootstrap versions 2.3.2 and 3.0.0
	// $('a[data-toggle="tab"]', $navigation).on('shown shown.bs.tab', innerTabShown);
    // SELF UPDATE
    // add amazeui
    $($settings.tagSelector,$navigation).on('click.tabs.amui',innerTabClick);
    $($settings.tagSelector,$navigation).on('opened.tabs.amui',innerTabShown);
};
$.fn.bootstrapWizard = function(options) {
	//expose methods
	if (typeof options == 'string') {
		var args = Array.prototype.slice.call(arguments, 1)
		if(args.length === 1) {
			args.toString();
		}
		return this.data('bootstrapWizard')[options](args);
	}
	return this.each(function(index){
		var element = $(this);
		// Return early if this element already has a plugin instance
		if (element.data('bootstrapWizard')) return;
		// pass options to plugin constructor
		var wizard = new bootstrapWizardCreate(element, options);
		// Store plugin object in this element's data
		element.data('bootstrapWizard', wizard);
		// and then trigger initial change
		wizard.fixNavigationButtons();
	});
};


    var UI = $.AMUI || {};

    var FormWizard = function(element, options) {
        this.options = $.extend({}, FormWizard.DEFAULTS, options);

        this.$evemts = FormWizard.EVENTS;
        this.$element = $(element);
        this.$form = $(this.options.formSelector,this.$element);
        this.$tabs = $(this.options.tabSelector,this.$element);
        this.$tabsNav = $(this.options.tabNavSelector,this.$element);
        this.$tabNavLength = $(this.options.tabNavChildSelector,this.$tabsNav).length;

        this.init();
    };

    FormWizard.DEFAULTS = {
        theme : '',
        tabClass : 'am-nav am-nav-tabs-wizard',
        nextSelector: '.form-actions .button-next',
        previousSelector: '.form-actions .button-previous',
        finishSelector : '.form-actions .button-finish',
        isValidate : 1,
        isProgress : 1,
        formSelector : 'form',
        tabSelector : '.am-wizard-tabs',
        tabNavSelector : '.am-wizard-tabs > .am-tabs-nav',
        tabNavChildSelector :'li',
        tabPanelSelector : '.am-tabs-bd-wizard > .am-tab-panel',
        progressClass : 'am-progress',
        progressDom :       '<div class="am-progress-striped am-margin-vertical"> <div class="am-progress-bar"></div></div>',
        firstSelector:    '.wizard li.first',
        lastSelector:     '.wizard li.last',
        backSelector:     '.wizard li.back',
        onShow:           null,
        onInit:           null,
        onNext:           null,
        onPrevious:       null,
        onLast:           null,
        onFirst:          null,
        onFinish:         null,
        onBack:           null,
        onTabChange:      null,
        onTabClick:       null,
        onTabShow:        null,
        // SELF CLASS
        activeSelector:    '.am-active',//active
        tagSelector :      'li > a',   //a[data-toggle="tab"]
        disabledClass :    'am-disabled',
        hideClass  :       'am-hide'
    }

    // 自定义事件
    FormWizard.EVENTS = {
        next : 'next.wizard.amui',
        previous : 'pre.wizard.amui',
        show : 'show.wizard.amui',
        finish : 'finish.wizard.amui'
    }

    FormWizard.prototype.init = function() {
        var _this = this;
        var $element = this.$element;
        var $form = this.$form;
        var $tabs = this.$tabs;
        var $events = this.$evemts;
        var options = this.options;

        // 初始化 formValidate
        if($form && options.isValidate){
            $form.validator();
        }
        // 初始化tabs
        $tabs.tabs({noSwipe: 1});

        // 初始化进度条
        if(options.isProgress){
            this.$progress = this.getProgress();
        }
        // 初始化formwizard

        var wizard = new bootstrapWizardCreate($element, $.extend(options,{
            onTabClick : function(){return false},
            onNext : function(tab, navigation, index){
                var flag = $element.trigger($events.next, [tab,navigation,index]);
                if(flag && options.isValidate){
                    flag = _this.isFormValid(tab);
                }
                return flag;
            },
            onPrevious : function (tab, navigation, index) {
                $element.trigger($events.previous, [tab,navigation,index]);
            },
            onTabShow : function(tab, navigation, index){
                options.isProgress && _this.drawProgress(index);
                $element.trigger($events.show,[tab,navigation,index]);
            },
            onFinish : function(tab, navigation, index){
                $element.trigger($events.finish,[tab,navigation,index]);
            }
        }));

        wizard.fixNavigationButtons();

        // 初始化事件
        this.bindEvents();
    }

    FormWizard.prototype.isFormValid = function(tab){

        var valid = this.$form.validator('isFormValid');
        
        if($.type(valid) == 'boolean'){
            tab.data('isTabShow',valid);
            return valid;
        }

        $.when(valid).then(function() {
            tab.data('isTabShow',true);
        }, function() {
            tab.data('isTabShow',false);
        });

    }

    // 重置输入状态
    FormWizard.prototype.clearInput = function(object){
        if(!object) return;
        //重置验证结果
        $(':input:not(:submit, :button, :disabled, .am-novalidate)',object).removeData('validity amui.dfdValidity');

    }

    FormWizard.prototype.getProgress = function(){
        var $progress = $('.'+this.options.progressClass,this.$element);
        if($progress.length == 0){
            $progress = $(this.options.progressDom).addClass(this.options.progressClass).insertAfter(this.$tabsNav);
        }
        return $progress;
    }

    FormWizard.prototype.drawProgress = function(index){
        var $total = this.$tabNavLength;
        var $current = index + 1;
        var $percent = ($current/$total) * 100;
        $('.am-progress-bar',this.$progress).css({'width' : $percent + '%'});
    }

    FormWizard.prototype.bindEvents = function(){
        var _this = this;
        var options = this.options;
        var $element = this.$element;
        var $events = this.$evemts;

        // 绑定默认函数
        $element.on($events.previous , function(e,tab, navigation, index){
            _this.$form.validator('removeMark');
            _this.clearInput($(options.tabPanelSelector+ ':eq(' + index + ')'),$element);
        });
        $element.on($events.next , function(e,tab, navigation, index){
        	return true;
        });
       
    }

    FormWizard.prototype.destroy = function() {
        this.$form.validator('destroy');
        this.$tabs.tabs('destroy');
    };


    UI.plugin('formWizard', FormWizard);

    // init code
    UI.ready(function(context) {
        $('[data-am-formWizard]', context).formWizard();
    });


})(jQuery);
