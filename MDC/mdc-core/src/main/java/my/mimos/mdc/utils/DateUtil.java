package my.mimos.mdc.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateUtil {
	
	/**
	 * Convert date to string data type
	 * @param date
	 * @return
	 */
	public String dateToString(Date date){
		String dateInString = null;
		try{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dateInString = formatter.format(date);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return dateInString;
	}
	
	/**
	 * Convert string to date type
	 * @param date
	 * @return
	 */
	public Date stringToDate(String dateStr){
		Date date = null;
		try{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = formatter.parse(dateStr);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return date;
	}

}
