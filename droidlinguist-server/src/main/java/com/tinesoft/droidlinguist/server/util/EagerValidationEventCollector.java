package com.tinesoft.droidlinguist.server.util;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

/**
 * {@link javax.xml.bind.ValidationEventHandler ValidationEventHandler}
 * implementation that collects all events (even all fatal errors).
 * 
 * @author Tine Kondo
 *
 */
public class EagerValidationEventCollector implements ValidationEventHandler {
	private final List<ValidationEvent> events = new ArrayList<ValidationEvent>();

	/**
	 * Return an array of ValidationEvent objects containing a copy of each of
	 * the collected errors and warnings.
	 *
	 * @return a copy of all the collected errors and warnings or an empty array
	 *         if there weren't any
	 */
	public ValidationEvent[] getEvents() {
		return events.toArray(new ValidationEvent[events.size()]);
	}

	/**
	 * Clear all collected errors and warnings.
	 */
	public void reset() {
		events.clear();
	}

	/**
	 * Returns true if this event collector contains at least one
	 * ValidationEvent.
	 *
	 * @return true if this event collector contains at least one
	 *         ValidationEvent, false otherwise
	 */
	public boolean hasEvents() {
		return !events.isEmpty();
	}

	@Override
	public boolean handleEvent(ValidationEvent event) {
		events.add(event);

		boolean retVal = true;
		switch (event.getSeverity()) {
		case ValidationEvent.WARNING:
			retVal = true; // continue validation
			break;
		case ValidationEvent.ERROR:
			retVal = true; // continue validation
			break;
		case ValidationEvent.FATAL_ERROR:
			retVal = true; // continue validation
			break;

		}

		return retVal;
	}

}
