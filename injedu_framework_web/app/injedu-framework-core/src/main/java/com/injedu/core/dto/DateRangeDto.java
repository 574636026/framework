package com.injedu.core.dto;

import java.io.Serializable;

/**
 * 时间范围
 *
 * @author joy.zhou
 * @date 2016年4月6日
 * @version 1.0
 *
 */
public class DateRangeDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 开始时间 */
	private String dateStart;
	/** 结束时间 */
	private String dateEnd;

	public DateRangeDto() {
	}

	public DateRangeDto(String dateStart, String dateEnd) {
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

}
