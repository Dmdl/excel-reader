package com.meetplanner.util;

import java.util.Date;
import java.util.List;

public class CommonUtill {

	public static boolean isDateBetween(Date toCheck,Date fromDate,Date toDate){
		return toCheck.after(fromDate) && toCheck.before(toDate);
	}
	
	public static int findMax(List<Integer> list){
		int max = Integer.MIN_VALUE;
		for(int each:list){
			if(each>max){
				max = each;
			}
		}
		return max;
	}
	
	public static String toCommaSeparatedString(List<Integer> list){
		if(list !=null && list.size()>0){
			StringBuilder builder = new StringBuilder();
			for(int e:list){
				builder.append(e);
				builder.append(",");
			}
			builder.setLength(builder.length()-1);
			return builder.toString();
		}
		return null;
	}
}
