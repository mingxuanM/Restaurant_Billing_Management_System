package application;

import java.text.SimpleDateFormat;

/**
* This class get current date and time for this app in format "yyyy-MM-dd HH:mm:ss".
* 
* @author      Mingxuan Mei
* @version     1.0
* @since       2016-12-16         
*/

public class GetTime {
	
	/**
	* This method get current date and time for this app.
	* 
	* @return String This returns the current date and time as a string in format "yyyy-MM-dd HH:mm:ss".
	* 
	* @author      Mingxuan Mei
	* @version     1.0
	* @since       2016-12-16         
	*/
	
	static public String now(){
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		System.out.println(timeStamp);
		return timeStamp;
	}
	
}
