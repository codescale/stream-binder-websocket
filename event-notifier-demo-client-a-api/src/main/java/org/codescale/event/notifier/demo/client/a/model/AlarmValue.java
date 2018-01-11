/**
 * Copyright (c) 2018 Matthias Kappeller<br/>
 * All rights reserved.
 */
package org.codescale.event.notifier.demo.client.a.model;

import java.util.Date;

/**
 * @author Matthias Kappeller
 *
 */
public class AlarmValue {

	private String name;
	private Date sourceTimestamp;
	private Double value;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the sourceTimestamp
	 */
	public Date getSourceTimestamp() {
		return sourceTimestamp;
	}

	/**
	 * @param sourceTimestamp
	 *            the sourceTimestamp to set
	 */
	public void setSourceTimestamp(Date sourceTimestamp) {
		this.sourceTimestamp = sourceTimestamp;
	}

	/**
	 * @return the value
	 */
	public Double getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(Double value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("AlarmValue [name=%s, sourceTimestamp=%s, value=%s]", name, sourceTimestamp, value);
	}

}
