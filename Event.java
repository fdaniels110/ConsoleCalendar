import java.util.GregorianCalendar;
import java.util.Calendar;
import java.io.*;
/**
 * Event Class that holds Title, Starting date and Ending date
 * 
 * @author Frank Daniels
 *
 */
public class Event implements Comparable<Event>,  Serializable{
	/**
	 * Default serialization key
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private GregorianCalendar date;
	private GregorianCalendar endDate;
	
	enum Days
	{
		Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday;
	}
	enum Months
	{
		January, February, March, April, May, June, July, August, September, October, November, December;
	}
	
	/**
	 * Event constructor that takes description of event, start date, and end date
	 * @param desc - String Description of Event 
	 * @param startDate - GregorianCalendar of start date
	 * @param end - GregorianCalendar of end date
	 */
	public Event(String desc, GregorianCalendar startDate, GregorianCalendar end)
	{
		title = desc;
		date = startDate;
		endDate = end;
	}
	/**
	 * Event constructor that date description and start date only and creating an end date 24 hours after start date
	 * @param desc - String Description of Event.
	 * @param startDate - GregorianCalendar of start date
	 */
	public Event(String desc, GregorianCalendar startDate )
	{
		GregorianCalendar end = (GregorianCalendar) startDate.clone();
		end.add(Calendar.HOUR_OF_DAY, 24);
		title = desc;
		date = startDate;
		endDate = end;
		
	}
	/**
	 * start date mutator method
	 * @param start - GregorianCalendar changed start date
	 */
	public void changeStart(GregorianCalendar start){	date = start;	}
	
	/**
	 * end date mutator method
	 * @param end - GregorianCalendar changed end date
	 */
	public void changeEnd(GregorianCalendar end){	endDate = end;	}
	
	/**
	 * description mutator
	 * @param desc - String of changed description
	 */
	public void changeTitle(String desc){	title = desc;	}
	
	/**
	 * Description of Event accessor method
	 * @return String - title of Event
	 */
	public String getTitle(){	return title; 	}
	
	/**
	 * Start date accessor method
	 * @return GregorianCalendar - start date
	 */
	public GregorianCalendar getStartDate(){	return date;	}
	
	/**
	 * End Date accessor method
	 * @return GregorianCalendar - end date
	 */
	public GregorianCalendar getEndDate(){		return endDate;	}
	
	/**
	 * Compares two events by start date (GregorianCalendar compareTo method)
	 */
	public int compareTo(Event e)
	{
		return compareTo(e.getStartDate());
	}
	
	private int compareTo(GregorianCalendar c){
		return date.compareTo(c);
	}
	
	/** 
	 * toString method converting Event to a String
	 *
	 * @return String of DAY_OF_WEEK, START_MONTH, START_DATE, START_HOUR, START_MINUTE, END_HOUR, END_MINUTE format
	 */
	public String toString()
	{
		Days[] daysArray = Days.values();
		Months[] monthsArray = Months.values();
		String returnable = "";
		returnable += daysArray[date.get(Calendar.DAY_OF_WEEK) - 1] + ", ";
		returnable += monthsArray[date.get(Calendar.MONTH)] + " ";
		returnable += date.get(Calendar.DAY_OF_MONTH) + " ";
		returnable += date.get(Calendar.HOUR_OF_DAY) + ":";
		returnable += date.get(Calendar.MINUTE) + " ";
		returnable += endDate.get(Calendar.HOUR_OF_DAY) + ":";
		returnable += endDate.get(Calendar.MINUTE) + " ";
		returnable += title;
		return returnable;
	}
		
	
	
	
}
