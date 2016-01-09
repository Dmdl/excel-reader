package com.meetplanner.util;

import java.util.Date;

public class CommonUtill {

	public static boolean isDateBetween(Date toCheck,Date fromDate,Date toDate){
		return toCheck.after(fromDate) && toCheck.before(toDate);
	}
}
