import java.util.*;
import java.io.*;

/**
 * Object that handles adding, deleting, and printing of Event objects and a digital calendar. 
 * USES relationship with Event class
 * 
 * 
 * @author Frank Daniels
 * @version 1.1
 *
 */
public class ProgramCalendar implements Serializable
{
	/**
	 * default serial version to serialize to. 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Event> eventList;
	private GregorianCalendar currentDate;
	private HashMap<GregorianCalendar, ArrayList<Event>> eventMap;
	private GregorianCalendar viewedDate;
	
	enum Months {January, February, March, April, May, June, July, August,
		September, October, November, December};
	enum Days {Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday};
	/**
	 * Default constructor for ProgramCalendar.
	 * Initialized internal ArrayLists, HashMap, and GregorianCalendar. 
	 */
	public ProgramCalendar()
	{
		eventList = new ArrayList<Event>();
		currentDate = new GregorianCalendar();
		viewedDate = new GregorianCalendar();
		eventMap = new HashMap<GregorianCalendar, ArrayList<Event>>(365); //Not a realistic size but for simplistic purposes used
	}
	/**
	 * Used to add Event to ArrayList and HashMap variables.
	 * 
	 * @param e - Event to be added. 
	 */
	public void addEvent(Event e)
	{
		ArrayList<Event> temp = new ArrayList<Event>();
		GregorianCalendar key = resolveHashKey(e.getStartDate());
		
		if(eventMap.containsKey(key)){
			temp = eventMap.get(e.getStartDate());
			temp.add(e);
			Collections.sort(temp);
			eventMap.put(key, temp);
		}else{
			temp.add(e);
			eventMap.put(key, temp);
		}
		eventList.add(e);
		Collections.sort(eventList);
	}
	/**
	 * Deletes all Events on a specified date.  (Can be improved by implementing Event title parameter)
	 * 
	 * @param c - GregorianCalendar of specified date
	 */
	public void deleteEvent(GregorianCalendar c)
	{
		ArrayList<Event> temp;
		GregorianCalendar key = resolveHashKey(c);
		if(eventMap.containsKey(key)){
			temp = eventMap.get(c);
			for(int i = 0; i < temp.size(); i++)
				eventList.remove(temp.get(i));
			eventMap.remove(key);
		}else
			System.out.println("No events at such date");
	}
	/**
	 * Prints ArrayList of Events using Event.toString() method.
	 */
	public void printEventList()
	{
		for(int i = 0; i < eventList.size(); i++)
		{
			if(i == 0)
				System.out.println(eventList.get(0).getStartDate().get(Calendar.YEAR));
			else if (eventList.get(i).getStartDate().get(Calendar.YEAR) != 
					eventList.get(i-1).getStartDate().get(Calendar.YEAR))
			{
				System.out.println(eventList.get(i).getStartDate().get(Calendar.YEAR));
			}
			
			System.out.println(eventList.get(i));	
		}
	}
	/**
	 * Changes viewed date to inputed date in mm/dd/yyyy form (Can be improved by changing String parsing to include other forms of dates.)
	 * 
	 * @param input String of Date in specific mm/dd/yyyy form. 
	 */
	public void goToDate(String input){
		String subString = input.substring(0, 2);
		int month = Integer.parseInt(subString) - 1;
		if(input.charAt(2) == '/')
			input = input.substring(3);
		else
			input = input.substring(2);
		int day = Integer.parseInt(input.substring(0, 2));
		
		int year; 
		if(input.charAt(2) == '/')
			year = Integer.parseInt(input.substring(3));
		else
			year = Integer.parseInt(input.substring(2));
		
		viewedDate = new GregorianCalendar(year, month, day);		
	}
	
	/**
	 * Prints viewed Date and any Event that occurs at that date by checking internal HashMap.
	 */
	public void printDate(){
		String[] months = {"Jan", "Fed", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
		String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		System.out.println();
		System.out.print( days[viewedDate.get(Calendar.DAY_OF_WEEK)] + ",  ");
		System.out.print( months[viewedDate.get(Calendar.MONTH) ] + " " + viewedDate.get(Calendar.DATE) + " " + viewedDate.get(Calendar.YEAR) );
		if(eventMap.containsKey(resolveHashKey(viewedDate))){
			ArrayList<Event> temp = eventMap.get(resolveHashKey(viewedDate));
			for(int i = 0; i < temp.size(); i++)
				System.out.println(temp.get(i));
		}
		System.out.println();
	}
	
	/**
	 * Prints viewed date in viewable month form using [] to determine today's date and () for Event. [] override () on a date.
	 */
	public void printCalendar()
	{
		String[] month = {"January", "February", "March",  "April", "May", "June", "July", "August",
				"September", "October", "November", "December"};
		String year = Integer.toString(currentDate.get(Calendar.YEAR));
		System.out.println("      " + month[viewedDate.get(Calendar.MONTH)]  +  " " + year);
		System.out.println(" Su  Mo  Tu  We  Th  Fr  Sa ");
		GregorianCalendar temp = 
				new GregorianCalendar(viewedDate.get(Calendar.YEAR), viewedDate.get(Calendar.MONTH), 1);
		GregorianCalendar abridgedCurrentDate =
				new GregorianCalendar(currentDate.get(Calendar.YEAR),currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE));
		int dayOfWeek = 1;
		do{
			if(temp.get(Calendar.DAY_OF_WEEK) == 1 &&  dayOfWeek != 1){
				dayOfWeek = 1;
				System.out.println();
			}
			else if(temp.get(Calendar.DAY_OF_WEEK) != dayOfWeek){
				dayOfWeek++;
				System.out.print("    ");
			}else if(temp.equals(abridgedCurrentDate)){
				if(Integer.toString(temp.get(Calendar.DATE)).length() == 1)
					System.out.print("[ " + temp.get(Calendar.DATE) + "]");
				else
					System.out.print("[" + temp.get(Calendar.DATE) + "]") ;
				
				temp.add(Calendar.DATE, 1);
				dayOfWeek++;
			}
			else if(eventMap.containsKey(resolveHashKey(temp))){
				if(Integer.toString(temp.get(Calendar.DATE)).length() == 1)
					System.out.print("( " + temp.get(Calendar.DATE) + ")");
				else
					System.out.print("(" + temp.get(Calendar.DATE) + ")") ;
				
				temp.add(Calendar.DATE, 1);
				dayOfWeek++;
			}
			else{
				if(Integer.toString(temp.get(Calendar.DATE)).length() == 1)
					System.out.print("  " + temp.get(Calendar.DATE) + " ");
				else
					System.out.print(" " + temp.get(Calendar.DATE) + " ") ;
				temp.add(Calendar.DATE, 1);
				dayOfWeek++;
			}
		 
		}while (temp.get(Calendar.MONTH) == viewedDate.get(Calendar.MONTH) );
		System.out.println();
	}
	
	/** 
	 * Increments viewing date by specified amount in specified field. 
	 * @param dataType - Integer that denotes field to change (0 for month, 1 for day, else year)
	 * @param increment
	 */
	public void changeDate(int dataType, int increment){
		if(dataType == 0)
			viewedDate.add(Calendar.MONTH, increment);
		else if(dataType == 1)
			viewedDate.add(Calendar.DATE, increment);
		else
			viewedDate.add(Calendar.YEAR, increment);
	}
	
	/**
	 * Changes internal currentDate to specified date
	 * @param c - date to change to. 
	 */
	public void changeCurrentDate(GregorianCalendar c){ currentDate = c;	}
	
	private GregorianCalendar resolveHashKey(GregorianCalendar c){
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DATE);
		return new GregorianCalendar(year, month, day);
	}
	
	
}
