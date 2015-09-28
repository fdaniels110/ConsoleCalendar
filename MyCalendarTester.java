import java.io.*;
import java.util.*;
/**
 * 
 * @author Frank Daniels
 * @version 1.0
 * Program to replicate a digital calendar and display dates and events in a viewable manner. 
 *
 */
public class MyCalendarTester {

	public static ProgramCalendar usedCal = null;
	public static Scanner keyboard = new Scanner(System.in);
	
	/**
	 * Main program that connects Events and ProgramCalendar classes and handles user input and console output.
	 * HAS-A relationship with ProgramCalendar
	 * USES relationship with Event
	 * 
	 * @param NOT USED
	 * @throws ClassNotFoundException thrown by Serializable methods
	 * @throws IOException thrown by Serializable methods
	 */
	public static void main(String[] args) throws ClassNotFoundException, IOException
	{
		ProgramCalendar cal = new ProgramCalendar();
		if(usedCal == null)
			cal.printCalendar();
		else
			usedCal.printCalendar();
		
		System.out.println("Select one of the following options:");
		System.out.println("[L]oad   [V]iew by  [C]reate, [G]o to [E]vent list [D]elete  [Q]uit");
		
		String input = keyboard.nextLine();
		
		if(input.equalsIgnoreCase("L")){
			open();
			main(args);
		}else if(input.equalsIgnoreCase("V")){
			System.out.println("[D]ay view or [M]onth view ? ");
			if(keyboard.nextLine().equalsIgnoreCase("M")){
				do{
					usedCal.printCalendar();
					
					System.out.println("[P]revious or [N]ext or [M]ain menu ?");
					
					input = keyboard.nextLine();
					
					if(input.equals("P"))
						usedCal.changeDate(0, -1);
					else if(input.equals("N"))
						usedCal.changeDate(0, 1);
					
				}while(!input.equalsIgnoreCase("M"));
				
			}else
				do{
					usedCal.printDate();
					
					System.out.println("[P]revious or [N]ext or [M]ain menu ?");	
					
					input = keyboard.nextLine();
					
					if(input.equalsIgnoreCase("P"))
						usedCal.changeDate(1, -1);
					else if(input.equalsIgnoreCase("N"))
						usedCal.changeDate(1, 1);
					
				}while(!input.equalsIgnoreCase("M"));
			main(args);
			
		}else if(input.equalsIgnoreCase("C")){
			
			System.out.println("Description of Event");
			
			String title = keyboard.nextLine();
			
			System.out.println("Enter date mm/dd/yyyy");
			
			input = keyboard.nextLine();
			int month = Integer.parseInt(input.substring(0, 2))-1;
			int day = Integer.parseInt(input.substring(3, 5));
			int year = Integer.parseInt(input.substring(6)); 
			
			System.out.println("Enter time: hh:mm 24 hour time.");
			
			input = keyboard.nextLine();
			int hour = Integer.parseInt(input.substring(0,2));
			int minute = Integer.parseInt(input.substring(3));
			GregorianCalendar tempStart = new GregorianCalendar(year, month, day, hour, minute);
			
			System.out.println("Enter end date mm/dd/yyyy or hit enter if end date need not apply.");
			input = keyboard.nextLine();
			
			if(!input.isEmpty()){
				
				month = Integer.parseInt(input.substring(0, 2)) -1;
				day = Integer.parseInt(input.substring(3, 5));
				year = Integer.parseInt(input.substring(6)); 
				
				System.out.println("Enter time: hh:mm 24 hour time.");
				
				input = keyboard.nextLine();
				hour = Integer.parseInt(input.substring(0,2));
				minute = Integer.parseInt(input.substring(3));
				GregorianCalendar tempEnd = new GregorianCalendar(year, month, day, hour,minute);
				Event tempEvent = new Event(title, tempStart, tempEnd);
				usedCal.addEvent(tempEvent);
			}else{
				Event tempEvent = new Event(title, tempStart);
				usedCal.addEvent(tempEvent);
			}
			main(args);
		}else if(input.equalsIgnoreCase("G")){
			System.out.println("Enter desired date");
			
			input = keyboard.nextLine();
			usedCal.goToDate(input);
			usedCal.printDate();
			main(args);
		}else if(input.equalsIgnoreCase("E")){
			usedCal.printEventList();
			main(args);
		}else if(input.equalsIgnoreCase("D")){
			System.out.println("Enter date to delete events mm/dd/yyyy");
			input = keyboard.nextLine();
			int month = Integer.parseInt(input.substring(0, 2)) -1;
			int day = Integer.parseInt(input.substring(3, 5));
			int year = Integer.parseInt(input.substring(6)); 
			GregorianCalendar tempdelete = new GregorianCalendar(year, month, day);
			
			usedCal.deleteEvent(tempdelete);
			main(args);
		}else{
			quit();
		}
	}
	
	/**
	 * Used to handle loading and creation of internal ProgramCalendar object from serialized form. 
	 * Also updates internal current date of ProgramCalendar.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void open() throws IOException, ClassNotFoundException{
		File filePath = new File("event.txt");
		if(!filePath.exists()){
			System.out.println("New Calendar Created.");
			usedCal = new ProgramCalendar();
		}else{
		FileInputStream input = new FileInputStream(filePath.getPath());
		ObjectInputStream ioStream = new ObjectInputStream(input);
		
		usedCal = (ProgramCalendar) ioStream.readObject();
		usedCal.changeCurrentDate(new GregorianCalendar());
		input.close();
		ioStream.close();
		}
		
	}
	
	/**
	 * Used to handle saving of internal ProgramCalendar from Serialized form 
	 * @throws IOException
	 */
	public static void quit() throws IOException{
		File filePath = new File("event.txt");
		FileOutputStream outStream = new FileOutputStream(filePath.getPath());
		ObjectOutputStream output = new ObjectOutputStream(outStream);
		
		output.writeObject(usedCal);
		output.close();
	}
}
