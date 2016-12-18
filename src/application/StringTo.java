package application;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
* This class gives a method to convert a time string into a Timestamp.
* 
* @author      Mingxuan Mei
* @version     1.0
* @since       2016-12-16         
*/

public class StringTo {
	
	/**
	 * <code>time</code> This method convert the time string into a Timestamp.
	 * 
	 * @param str_date The input string is a time string in format of "yyyy-MM-dd HH:mm:ss".
	 * 
	 * @return Timestamp This returns the converted Timestamp from the string in the same format.
	 * 
	 * @author      Mingxuan Mei
	 * @version     1.0
	 * @since       2016-12-16        
	 */
	
	public static Timestamp time(String str_date) {
	    try {
	      DateFormat formatter;
	      formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	      Date date = (Date) formatter.parse(str_date);
	      java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());

	      return timeStampDate;
	    } catch (ParseException e) {
	      System.out.println("Exception :" + e);
	      return null;
	    }
	  }
}
