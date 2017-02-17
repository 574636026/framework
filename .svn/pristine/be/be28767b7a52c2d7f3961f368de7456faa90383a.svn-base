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