package com.injedu.core.validate;

/**
 * 
 * 验证正则
 *
 * @author joy.zhou
 * @date 2015年12月14日
 * @version 1.0
 *
 */
public class ValidateRegexp {

	/** 用户名 */
	public static final String USER_NAME = "[a-zA-Z]\\w{4,9}";
	/** 手机 */
	public static final String MOBILE = "1((3|5|8){1}\\d{1}|70)\\d{8}";
	/** 中文字符 */
	public static final String CHINESE = "[\u4e00-\u9fa5]";
	/** 邮编 */
	public static final String ZIP_CODE = "\\d{6}";
	/** 邮箱 */
	public static final String EMAIL = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
	/** 字符编码 */
	public static final String CHAR_CODE = "[a-zA-Z_]{4,20}";
	/** 腾讯QQ */
	public static final String QQ = "[1-9][0-9]{4,}";
	/** IP */
	public static final String IP = "((([1-9]\\d?)|(1\\d{2})|(2[0-4]\\d)|(25[0-5]))\\.){3}(([1-9]\\d?)|(1\\d{2})|(2[0-4]\\d)|(25[0-5]))";

}
