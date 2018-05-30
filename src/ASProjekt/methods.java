package ASProjekt;

import java.time.LocalTime;
import java.util.Arrays;
import ASProjekt.read_write;

public class methods {
	
	
	public static void main (String[] args) {
		
		//checkavailable("2018-05-01", "08:00", "17:00" , read_write.lesen("data.txt"), read_write.lesen("planes.txt"));
		
	}
	
	
	//checkavailable gibt erfolgreich eine Liste von freien Flugzeugen zur�ck !
	public static String[] checkavailable(String datum, String von, String bis, String[] datas, String[] planes) {
		System.out.println("Checkvalid gestartet");
		//von und bis kommen als yy:zz an, ben�tigt wird xx:yy:zz
		von=von + ":00";
		Integer von_hour = Integer.valueOf(von.substring(0, 2));
		System.out.println("von_hour = " + von_hour);
		Integer bis_hour = Integer.valueOf(bis.substring(0, 2));
		System.out.println("bis_hour =" + bis_hour);
		bis=bis + ":00";
		
		
		
		//Eindimensionales Array in Multidimensionales Array
		String datas2[][] = new String[datas.length][6];

		
		for(int i=0; i < datas.length; i++) {
			
	        String CSV = datas[i];

	        String[] values = CSV.split(";");
			
			for(int j=0; j < values.length; j++) {
			
				datas2[i][j]=values[j];
			
			}
		
		}
		
		
		//TODO : If Abfragen so integrieren das routine abbricht wenn ung�ltig. Am besten mit allen anderen Checks ein Boolean auf false setzen. Und dann eine IF f�r die ganze routine
		if (von_hour > bis_hour) {
			System.out.println("Leihe kann nicht mit einer fr�heren Uhrzeit enden, als sie startet.");
			
		}
		
		if (von_hour == bis_hour) {
			System.out.println("Leihe muss mindestens eine Stunde gehen");			
		}
		
		
	for (int i=0; i < datas2.length; i++) {
		Boolean ischecknotvalid = false;
		
			//System.out.println("Schleife " + i);
			
			if(datas2[i][2].equals(datum)) {
				
				
				
				System.out.println("Datum Check : gefunden");

				LocalTime start = LocalTime.parse( datas2[i][3] );  //gespeichertes von
				LocalTime stop = LocalTime.parse( datas2[i][4] );	  //gespeichertes bis
				
				for (int x= von_hour; x < bis_hour ; x++) {
					String time = String.valueOf(x) + ":00:00";
					if (time.length() !=8) {
						time= "0" + time;
						
					}
					
					System.out.println("Zeit " + time);
					
					if (Boolean.FALSE.equals(ischecknotvalid)) {
						LocalTime check = LocalTime.parse( time ); //Variable die alle Zeiten zwischen von und bis abfragt
						ischecknotvalid = ( check.isAfter( start ) && check.isBefore( stop ) ) ;	
					}		
					
					System.out.println("Ergebnis des Checks : " + ischecknotvalid);
					
				}
				
				System.out.println("Leihe g�ltig? (True = ung�ltig) " + ischecknotvalid);
				
				if (Boolean.TRUE.equals(ischecknotvalid)) { 
										
					//Muss Flugzeug (datas2[i][1] aus einer liste l�schen)
					Arrays.sort(planes);
					int z = Arrays.binarySearch(planes, datas2[i][1]);
					planes[z]=null;
					
				}
				
			}
			
		}		
		
		for (int y=0; y < planes.length ; y++) {
			
			System.out.println("R�ckgabe von checkavailable: " + planes[y]);
			
			}
		
		return planes;
		
	}

	public static void addplane(String plane){
		
		//TODO: Muss verhindern, das "" eingegeben werden kann.
		
		String planes[]=read_write.lesen("planes.txt");
		int temp = 0;
		temp = planes.length +1;
		String planes1[]= new String[temp];
		
		for (int i=0; i < planes.length; i++) {

			planes1[i]=planes[i];			
			System.out.println(planes1[i]);
		}	
		
		//Planes [temp - 1] = letzter eintrag von dem original Array
		//planes1[temp] = letzter Eintrag +1 des neuen Arrays
		planes1[temp -1]=plane;
		
		read_write.schreiben(planes1, "planes.txt");
		System.out.println("Neues Flugzeug hinzugef�gt");
		temp=0;
		
		
	}
	
	public static void removeplane(int id) {
		//TODO: �berarbeiten
		
		String planes[]=read_write.lesen("planes.txt");
		int size = planes.length;
		
		//Erstelle ein Array das um wert "1" kleiner als das alte Array ist.
		String[] resultplanes = new String[size -1];
		int resulArrayCount = 0;
		
		for (int i = 0; i < size; i++) {
			if (i!=id){
				resultplanes[resulArrayCount] = planes[i];
				resulArrayCount++;
			}
		}
		
		read_write.schreiben(resultplanes, "planes.txt");		
	}
	
	public static void renameplane(int id, String name) {
		String[] planes=read_write.lesen("planes.txt");
		System.out.println(id + "ist id");
		planes[id]=name;
		read_write.schreiben(planes, "planes.txt");
	}
	
	public static void addleihe(String plane, String von, String bis, String datum, String name) {
		
		System.out.println("addleihe gestartet");
		//von und bis kommen als yy:zz an, ben�tigt wird xx:yy:zz
		von=von + ":00";
		bis=bis + ":00";
		
		
		//Array wird f�r alte Daten erstellt
		String data[] = read_write.lesen("data.txt");
		
		//Initialisierung der L�nge f�r das neue Array
		int arraylength=0;
		arraylength=data.length +1;
		System.out.print("Arraylenght: "+arraylength+"\n");
		
		//Kopieren aller Werte von altem Array in neues Array
		
		String data1[]= new String[arraylength];

		for (int i=0; i < data.length; i++) {

			data1[i]=data[i];			
			System.out.println(data1[i]);
		}
		
		//hinzuf�gen des neuen Eintrages in das neue Array + Datei umschreiben
		
		data1[arraylength -1]="0"+String.valueOf(arraylength -1) + ";" + plane + ";" + datum + ";" + von + ";" + bis + ";" + name + ";";	
		read_write.schreiben(data1, "data.txt");
		
		
	}

	
}