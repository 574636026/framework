package com.injedu.imagesearch.constant;

import java.util.regex.Pattern;

/**
 * 
 *
 * @author joy.zhou
 * @date 2016年3月12日
 * @version 1.0
 *
 */
public interface AppConst {

	/** 提取image src */
	public static final Pattern PATTERN_IMAGE_SRC = Pattern.compile("(?<=src=&#34;)([^\\s]+?)(?=&#34;)");

	/** 德志图片根路径 */
	public static final String NETROOT_DEZHI = "http://www.dezhi.com";
}
