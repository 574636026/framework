package com.injedu.core.constant;

/**
 * 
 * MyBatis 默认查询参数
 *
 * @author joy.zhou
 * @date 2015年11月24日
 * @version 1.0
 *
 */
public interface QueryConstant {

	/** 是否删除参数 */
	public static final String QUERY_IS_DELETE = "proParamIsDelete";
	/** 启用分页查询 */
	public static final String QUERY_PAGE_ENABLED = "proParamPageable";
	/** 分页查询参数 */
	public static final String QUERY_PAGE = "proParamPager";
	/** 排序参数 */
	public static final String ORDERED = "proParamOrder";
	/** 多个排序参数 */
	public static final String ORDERED_LIST = "proParamOrderList";
	/**主键查询参数*/
	public static final String QUERY_ID = "id";
}
