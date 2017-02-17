package com.injedu.core.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * 
 * 
 * @author joy.zhou
 * @date 2015年4月23日
 * @version 1.0
 *
 */
public abstract class AbstractDomain<PK extends Serializable> implements Serializable {

	private static final long serialVersionUID = 1L;

	/** get the id */
	public abstract PK getId();

	/** set the id */
	public abstract void setId(final PK id);

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
