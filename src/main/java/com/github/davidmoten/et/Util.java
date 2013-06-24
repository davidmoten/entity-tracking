package com.github.davidmoten.et;

import java.util.Date;

public class Util {

	/**
	 * Returns the {@link Date} from a date string in format
	 * yyyy-MM-ddTHH:mm:ss.SSSZ.
	 * 
	 * @param date
	 * @return
	 */
	public static Date parseIsoDate(String date) {
		return javax.xml.bind.DatatypeConverter.parseDateTime(date).getTime();

	}
}
