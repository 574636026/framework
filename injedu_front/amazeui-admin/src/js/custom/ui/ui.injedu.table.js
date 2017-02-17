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
