package br.ita.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateManagement {

	public static String getCurrentTimeStamp(String format) {
		
	    SimpleDateFormat sdfDate = new SimpleDateFormat(format);
	    
	    Date now = new Date();
	    
	    String strDate = sdfDate.format(now);
	    
	    return strDate;
	}
}
