package com.injedu.supports.push.handle.ym.constant;

/**
 * 
 * 筛选类型
 *
 * @author joy.zhou
 * @date 2016年1月15日
 * @version 1.0
 *
 */
public enum FilterType {
	/** 应用版本 */
	VERSION("app_version"),
	/** 渠道 */
	CHANNEL("channel"),
	/** 设备型号 */
	DEVICEMODEL("device_model"),
	/** 省份 */
	PROVINCE("province"),
	/** 用户标签 */
	TAG("tag"),
	/** 国家 */
	COUNTRY("country"),
	/** 语言 */
	LANGUAGE("language"),
	/** 一段时间内活跃 */
	LAUNCHFROM("launch_from"),
	/** 一段时间内不活跃 */
	NOTLAUNCHFROM("not_launch_from");

	FilterType(String name) {
		this.name = name;
	}

	private String name;

	public String getName() {
		return name;
	}

}
