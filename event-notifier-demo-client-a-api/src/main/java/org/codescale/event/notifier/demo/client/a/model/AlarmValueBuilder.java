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
public class AlarmValueBuilder {

	private final AlarmValue alarmValue = new AlarmValue();

	/**
	 * @param name
	 * @return
	 */
	public AlarmValueBuilder withName(String name) {
		alarmValue.setName(name);
		return this;
	}

	/**
	 * @param sourceTimestamp
	 * @return
	 */
	public AlarmValueBuilder withSourceTimestamp(Date sourceTimestamp) {
		alarmValue.setSourceTimestamp(sourceTimestamp);
		return this;
	}

	/**
	 * @param value
	 * @return
	 */
	public AlarmValueBuilder withValue(Double value) {
		alarmValue.setValue(value);
		return this;
	}

	/**
	 * 
	 */
	public AlarmValue build() {
		return alarmValue;
	}
}
