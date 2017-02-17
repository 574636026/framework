package com.injedu.core.exception.constant;

public interface ExceptionConstants {

	/** 数据库异常 : 插入 */
	final String DATA_INSERT_ERROR = "data.insert.error";
	/** 数据库异常 : 重复插入 */
	final String DATA_INSERT_DUPLICATEKEY = "data.insert.duplicateKey";
	/** 数据库异常 : 删除 */
	final String DATA_DELETE_ERROR = "data.delete.error";
	/** 数据库异常 : 更新 */
	final String DATA_UPDATE_ERROR = "data.update.error";
	/** 数据库异常 : 查询 */
	final String DATA_SELECT_ERROR = "data.select.error";
	/** 系统异常 */
	final String SYSTEM_ERROR = "system.error";
	/** 文件上传错误 */
	final String SYSTEM_FILEUPLOAD_ERROR = "system.fileupload.error";
	/** 上传目录不支持 */
	final String SYSTEM_FILEUPLOAD_DIR_UNSUPPORT = "system.fileupload.dir.unsupport";
	/** 上传目录未知 */
	final String SYSTEM_FILEUPLOAD_DIR_UNKNOW = "system.fileupload.dir.unkonw";
	/** 图片压缩错误 */
	final String SYSTEM_FILEUPLOAD_COMPRESS_ERROR = "system.fileupload.compress.error";
}
