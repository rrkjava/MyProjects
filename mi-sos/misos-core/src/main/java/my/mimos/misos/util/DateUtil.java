package my.mimos.misos.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateUtil {
	
	/**
	 * This method id used to convert string to date
	 * @param String dateAsString
	 * @return Date convertedDate
	 */
     public Date convertStringToDate(String dateAsString) {
    	 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	 Date convertedDate = null;
			try {
				convertedDate = sdf.parse(dateAsString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
    	 
		return convertedDate;
     }
     
     
	public Date addDaysToDate(Date date, int numOfdays) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, numOfdays);
		Date newDate = c.getTime();
		return newDate;
	}

}
