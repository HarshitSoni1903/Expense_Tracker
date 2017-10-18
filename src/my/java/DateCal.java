package my.java;

import java.sql.Date;
import java.util.Calendar;

public class DateCal {
	public static Date getStartDate(int days){
		Date date;
		String sdate;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, (0 - days));
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH)+1;
		int year = cal.get(Calendar.YEAR);
		sdate = (String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day));
		date = Date.valueOf(sdate);
		return date;
	}
}
