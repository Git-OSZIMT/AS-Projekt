package ASProjekt;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import ASProjekt.gui;

import ASProjekt.read_write;

@SuppressWarnings("unused")
public class methods {
	
	
	public static void main (String[] args) {
		
		//checkavailable("2018-05-01", "08:00", "17:00" , read_write.lesen("data.txt"), read_write.lesen("planes.txt"));
		
	}
	
	
	//checkavailable gibt erfolgreich eine Liste von freien Flugzeugen zurück !
	public static String[] checkavailable(String datum, String von, String bis, String[] datas, String[] planes) {
		System.out.println("Checkvalid gestartet");
		//von und bis kommen als yy:zz an, benötigt wird xx:yy:zz
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
		
		System.out.println(von_hour);
		System.out.println(bis_hour);
		//TODO : If Abfragen so integrieren das routine abbricht wenn ungültig. Am besten mit allen anderen Checks ein Boolean auf false setzen. Und dann eine IF für die ganze routine
		if (von_hour > bis_hour || von_hour == bis_hour) {
			//Hier erstmal FEHLER !
			if (von_hour ==bis_hour) {
				System.out.println("Leihe muss mindestens eine Stunde gehen");
				gui.setlabelimage("beginn_gleich_ende");
			}
			if (von_hour > bis_hour) {
				System.out.println("Leihe kann nicht mit einer früheren Uhrzeit enden, als sie startet.");
				gui.setlabelimage("ende_vor_beginn");
			}
			
		}else {
					
			System.out.println("Alles OK!");
			
			
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
						
						System.out.println("Leihe gültig? (True = ungültig) " + ischecknotvalid);
						
						if (Boolean.TRUE.equals(ischecknotvalid)) { 
												
							//Muss Flugzeug (datas2[i][1] aus einer liste löschen)
							Arrays.sort(planes);
							int z = Arrays.binarySearch(planes, datas2[i][1]);
							planes[z]=null;
							
						}
						
					}
					
				}		
				
				for (int y=0; y < planes.length ; y++) {
					
					System.out.println("Rückgabe von checkavailable: " + planes[y]);
					
					}
			
		}
		
		
		
		
	
		
		return planes;
		
	}

	public static void addplane(String plane){
		
		if (plane.equals("")) {
			System.out.println("Err: Kein Name angegeben. Bitte geben sie den neuen Flugzeug-Namen an.");
			return;
		}
		
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
		System.out.println("Neues Flugzeug hinzugefügt");
		temp=0;
		
		
	}
	
	public static void removeplane(int id) {

		
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
		//von und bis kommen als yy:zz an, benötigt wird xx:yy:zz
		von=von + ":00";
		bis=bis + ":00";
		
		if ((von.equals(":00")) || (plane.equals("null")) || (bis.equals(":00")) || (datum.equals("")) || (name.equals(""))) {
			
			if (plane.equals("null")) {
				System.out.println("Err: Kein Flugzeug ausgewählt. Bitte wählen sie ein Flugzeug aus.");
			}
			if (von.equals(":00")) {
				System.out.println("Err: \"Von\" nicht definiert. Bitte wählen sie eine Zeit für \"Von\" aus.");			
			}
			if (bis.equals(":00")) {
				System.out.println("Err: \"Bis\" nicht definiert. Bitte wählen sie eine Zeit für \"Bis\" aus.");
			}
			if (datum.equals("")) {
				System.out.println("Err: Kein Datum angegeben. Bitte geben sie ein Datum an.");
			}
			if (name.equals("")) {
				System.out.println("Err: Kein Name angegeben. Bitte geben sie einen Namen für die Buchung an (e.g. den Familiennamen)");
			}
			return;
		}
		
		
		//Array wird für alte Daten erstellt
		String data[] = read_write.lesen("data.txt");
		
		//Initialisierung der Länge für das neue Array
		int arraylength=0;
		arraylength=data.length +1;
		System.out.print("Arraylenght: "+arraylength+"\n");
		
		//Kopieren aller Werte von altem Array in neues Array
		
		String data1[]= new String[arraylength];

		for (int i=0; i < data.length; i++) {

			data1[i]=data[i];			
			System.out.println(data1[i]);
		}
		
		//hinzufügen des neuen Eintrages in das neue Array + Datei umschreiben
		
		data1[arraylength-1]="0"+String.valueOf(arraylength ) + ";" + plane + ";" + datum + ";" + von + ";" + bis + ";" + name + ";";	
		read_write.schreiben(data1, "data.txt");
		
		
	}

	public static int earningstoday(String[] datas, String datum) {
		//Datas = data.txt Arrays
		int diffg=0;
		//Muss herausfinden wieviel Differenz an Stunden insgesamt vorhanden sind
		//Von ist feld 1 , Bis Feld 2
		
		
		//Eindimensionales Array in Multidimensionales Array
				String datas2[][] = new String[datas.length][6];

				
				for(int i=0; i < datas.length; i++) {
					
			        String CSV = datas[i];

			        String[] values = CSV.split(";");
					
					for(int j=0; j < values.length; j++) {
					
						datas2[i][j]=values[j];
					
					}
				
				}
		
		
		//TODO Heute rausfiltern

				for (int i=0; i < datas2.length; i++) {
					
					if (datas2[i][2] == datum) {
					//Array in "I" und "2" - "I" und "1" = dauer dieser Leihe
					//TODO DIESE METHODE MUSS FAILEN ! Es wird 18:00 - 13:00 gerechnet. Beides muss gekürzt werden zu "18" und "13".
					//Strings bekommen Urzeit (zb. 16:00)
					String temp1=datas2[i][3];
					String temp2=datas2[i][4];
					//Strings werden bei ":" aufgeteilt. Erster Teil enthält die Stunde (zb. 16) und wird in temp1/2 zurück geschrieben
					String[] temp1_1 = temp1.split(":");
					temp1=temp1_1[0];
					String[] temp2_1 = temp2.split(":");
					temp2=temp2_1[0];
					
					System.out.println(temp1 + "tmp1");
					System.out.println(temp2 + "tmp2");
					
					int diffz= Integer.valueOf(temp2) - Integer.valueOf(temp1);
					//Adddiert die einzelnen Stunden
					diffg= diffg + diffz;
						
					}	
					
				}
				
		return diffg;
		
	}

	public static String[] occupiedtoday(String[] datas) {
		
		//Muss für jedes Flugzeug einzeln Berechnen wieviele Stunden gebucht sind, und dann davon einen Prozentwert bilden.
		
		//Datas = data.txt Arrays		
				
				//Eindimensionales Array in Multidimensionales Array
						String datas2[][] = new String[datas.length][6];
						String today = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
						String planes[] = read_write.lesen("planes.txt");
						String occupiedplanes[] = new String[planes.length];
												
						for(int i=0; i < datas.length; i++) {
							
					        String CSV = datas[i];

					        String[] values = CSV.split(";");
							
							for(int j=0; j < values.length; j++) {
							
								datas2[i][j]=values[j];
						
							}
						
						}
						
						
						System.out.println(Arrays.toString(planes));
						for (int i=0; i < planes.length; i++) {
							
							for (int j = 0; j < datas2.length; j++) {
								boolean bool1 = planes[i].equals(datas2[j][1]);
								boolean bool2 = today.equals(datas2[j][2]);
								
								/*
								//Debug
																
								System.out.println(i+"."+j+". Durchlauf | Plane in Planes.txt: "+planes[i]); 
								System.out.println(i+"."+j+". Durchlauf | Plane in Buchungen: "+datas2[j][1]); 
								System.out.println(i+"."+j+". Durchlauf | Heutiges Datum: "+today); 
								System.out.println(i+"."+j+". Durchlauf | Datum in Buchungen: "+datas2[j][2]);
								System.out.println(i+"."+j+". Durchlauf | Boolean PlaneName: "+bool1);								
								System.out.println(i+"."+j+". Durchlauf | Boolean Datum: "+bool2);								
								
								*/
								
								if (bool1 && bool2) {
									occupiedplanes[i] = planes[i];
								}
								
								
							}	
						}
						//System.out.print(Arrays.toString(occupiedplanes));
						return occupiedplanes;
		
		
	}
	
}