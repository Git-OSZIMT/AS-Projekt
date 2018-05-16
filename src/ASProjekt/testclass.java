package ASProjekt;

import java.time.LocalTime;
import java.util.ArrayList;

public class testclass {

	public static void main(String[] args) {
		//Array : id;Flugzeug;Datum;Von;Bis;Name;\n
		//         0    1       2    3   4   5    6?
		//01;Cessna01;30.04.2018;08:30:00;12:00:00;Familie Schmidt;
		
		boolean exists=true;
		
		ArrayList<String> myStrings = new ArrayList<>();
		myStrings.add("Alpha");
		myStrings.add("Beta");
		myStrings.add("Gamma");
		myStrings.add("Alpha");
		myStrings.add("Beta");
		
		System.out.println(myStrings);
		
		
		while(exists == true) {
			exists = myStrings.remove("LOL");
		}
		
		

		
		System.out.println(myStrings);
}
	
}