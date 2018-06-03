package ASProjekt;

import java.io.File;
import java.text.DecimalFormat;
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
	
	

	public static String[] checkavailable(String datum, String von, String bis, String[] datas, String[] planes) {
		//System.out.println("Checkvalid gestartet");
		//von und bis kommen als yy:zz an, benötigt wird xx:yy:zz
		von=von + ":00";
		Integer von_hour = Integer.valueOf(von.substring(0, 2));
		//System.out.println("von_hour = " + von_hour);
		Integer bis_hour = Integer.valueOf(bis.substring(0, 2));
		//System.out.println("bis_hour =" + bis_hour);
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
		
		//System.out.println(von_hour);
		//System.out.println(bis_hour);
	
		
		String today = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		
		System.out.println(today + datum);
		
		if (von_hour > bis_hour || von_hour == bis_hour || today.compareTo(datum)>0) {
			//Hier erstmal FEHLER !
			if (von_hour ==bis_hour) {
			//	System.out.println("Leihe muss mindestens eine Stunde gehen");
				gui.setlabelimage("beginn_gleich_ende");
			}
			if (von_hour > bis_hour) {
			//	System.out.println("Leihe kann nicht mit einer früheren Uhrzeit enden, als sie startet.");
				gui.setlabelimage("ende_vor_beginn");
			}
			if (today.compareTo(datum)>0) {
			//	System.out.println("Datum liegt in der Vergangenheit.");
				gui.setlabelimage("datum_vergangenheit");
			}
			
		}else {
					
			//System.out.println("Alles OK!");
			
			
			for (int i=0; i < datas2.length; i++) {
				Boolean ischecknotvalid = false;
				
					//System.out.println("Schleife " + i);
					
					if(datas2[i][2].equals(datum)) {
						
						
						
					//	System.out.println("Datum Check : gefunden");

						LocalTime start = LocalTime.parse( datas2[i][3] );  //gespeichertes von
						LocalTime stop = LocalTime.parse( datas2[i][4] );	  //gespeichertes bis
						
						for (int x= von_hour; x < bis_hour ; x++) {
							String time = String.valueOf(x) + ":00:00";
							if (time.length() !=8) {
								time= "0" + time;
								
							}
							
					//		System.out.println("Zeit " + time);
							
							if (Boolean.FALSE.equals(ischecknotvalid)) {
								LocalTime check = LocalTime.parse( time ); //Variable die alle Zeiten zwischen von und bis abfragt
								ischecknotvalid = ( check.isAfter( start ) && check.isBefore( stop ) ) ;	
							}		
							
					//		System.out.println("Ergebnis des Checks : " + ischecknotvalid);
							
						}
						
				//		System.out.println("Leihe gültig? (True = ungültig) " + ischecknotvalid);
						
						if (Boolean.TRUE.equals(ischecknotvalid)) { 
												
							//Muss Flugzeug (datas2[i][1] aus einer liste löschen)
							Arrays.sort(planes);
							int z = Arrays.binarySearch(planes, datas2[i][1]);
							planes[z]=null;
							
						}
						
					}
					
				}		
				
				for (int y=0; y < planes.length ; y++) {
					
			//		System.out.println("Rückgabe von checkavailable: " + planes[y]);
					
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
			//System.out.println(planes1[i]);
		}	
		
		//Planes [temp - 1] = letzter eintrag von dem original Array
		//planes1[temp] = letzter Eintrag +1 des neuen Arrays
		planes1[temp -1]=plane;
		
		read_write.schreiben(planes1, "planes.txt");
		//System.out.println("Neues Flugzeug hinzugefügt");
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
		//System.out.println(id + "ist id");
		planes[id]=name;
		read_write.schreiben(planes, "planes.txt");
	}
	
	public static void addleihe(String plane, String von, String bis, String datum, String name) {
		
		//System.out.println("addleihe gestartet");
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

	public static float earningstoday(String[] datas) {
		//Datas = data.txt Arrays
		int diffg=0;
		float sum=0; 
		//Muss herausfinden wieviel Differenz an Stunden insgesamt vorhanden sind
		//Von ist feld 1 , Bis Feld 2
		
		String datum = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		
		//Eindimensionales Array in Multidimensionales Array
				String datas2[][] = new String[datas.length][6];

				
				for(int i=0; i < datas.length; i++) {
					
			        String CSV = datas[i];

			        String[] values = CSV.split(";");
					
					for(int j=0; j < values.length; j++) {
					
						datas2[i][j]=values[j];
					
					}
				
				}
		
				
				for (int i=0; i < datas2.length; i++) {
					
					
					if (datas2[i][2].contentEquals(datum)) {
					//	System.out.println("HEUTE GEFUNDEN!");
					//Array in "I" und "2" - "I" und "1" = dauer dieser Leihe
					//Strings bekommen Urzeit (zb. 16:00)
					String temp1=datas2[i][3];
					String temp2=datas2[i][4];
					//Strings werden bei ":" aufgeteilt. Erster Teil enthält die Stunde (zb. 16) und wird in temp1/2 zurück geschrieben
					String[] temp1_1 = temp1.split(":");
					temp1=temp1_1[0];
					String[] temp2_1 = temp2.split(":");
					temp2=temp2_1[0];
					
					
					int diffz= Integer.valueOf(temp2) - Integer.valueOf(temp1);
					//Adddiert die einzelnen Stunden
					diffg= diffg + diffz;
					sum=diffg*60;
					}	
					
				}
				
		return sum;
		
	}

	public static String[] occupiedtoday(String[] datas) {
						
					String datas2[][] = new String[datas.length][6];
					String today = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
					String planes[] = read_write.lesen("planes.txt");
					String occupiedplanes[] = new String[planes.length];
					String occupiedplanes2[][] = new String[occupiedplanes.length][2];
						
						//Eindimensionales Array in Multidimensionales Array
						for(int i=0; i < datas.length; i++) {
							
					        String CSV = datas[i];
					        String[] values = CSV.split(";");	
							for(int j=0; j < values.length; j++) {
								datas2[i][j]=values[j];
							}	
						}
						
						
					//	System.out.println(Arrays.toString(planes));
						for (int i=0; i < planes.length; i++) {
							
							for (int j = 0; j < datas2.length; j++) {
								
								//bool1 = stimmt das geprüfte plane mit dem ausgeliehenen
								boolean bool1 = planes[i].equals(datas2[j][1]);
								//bool2 = stimmt das Datum
								boolean bool2 = today.equals(datas2[j][2]);
						
								// Wenn beides Stimmt wird das aktuelle plane in occupiedplanes geschrieben.
								if (bool1 && bool2) {
									occupiedplanes[i] = planes[i];
								}
								
								
							}	
						}
						
						//
						//Enthält nun alle heute verliehenen Flugzeuge. Es folgt : Wie lange sind diese ausgeliehen?\\
						//
						
						
						//Eindimensionales Array in Multidimensionales Array
						for(int i=0; i < occupiedplanes.length; i++) {
							
					      occupiedplanes2[i][0]=occupiedplanes[i];
					      occupiedplanes2[i][1]="0";
					      
							System.out.println(occupiedplanes2[i][0]);
						}
						
						
						for (int i = 0; i < datas2.length; i++) {
						
							String von=datas2[i][3];
							String bis=datas2[i][4];
							//Strings werden bei ":" aufgeteilt. Erster Teil enthält die Stunde (zb. 16) und wird in temp1/2 zurück geschrieben
							String[] von_1 = von.split(":");
							von=von_1[0];
							String[] bis_1 = bis.split(":");
							bis=bis_1[0];
						
							int diff = Integer.valueOf(bis) - Integer.valueOf(von);
							System.out.println("diff: " + diff);
							double proz=(100/12.0)*diff;
							
							DecimalFormat df = new DecimalFormat("#.##");
							String prozent=df.format(proz);
							
							
							System.out.println("Prozent " + prozent);
							//System.out.println("diff " + diff + " bis " + bis + "von " + von);
							//System.out.println("!!!" + occupiedplanes2[1][0]);
							
							for (int z=0; z < occupiedplanes.length; z++) {
								
								//System.out.println("Z IST : " + z);
								
								if (occupiedplanes2[z][0] != null) {
									
									System.out.println(occupiedplanes2[z][0] + " vgl. " + datas2[i][1] + " Z: " + z + " I: " + i);
									
									if (occupiedplanes2[z][0].contentEquals(datas2[i][1]) ){
										
										//Prozentumerechnen
										//12 = 100%
										//Integer.valueOf(occupiedplanes2[z][1]) + diff
										
										occupiedplanes2[z][1]= String.valueOf((Integer.valueOf(occupiedplanes2[z][1]) + prozent));
										
										System.out.println("Flugzeug " + occupiedplanes2[z][0] + " geliehene Stunden "+ occupiedplanes2[z][1]);
										
									}
								
								}
							}
					
							
						}		//Hier enthält occupedplanes2 jeweils im ersten element das Flugzeug und im 2. die geliehenen Stunden				
						
						
						//Rückumwandlung von occupiedplanes2 zu occupiedplanes
						for (int x=0; x < occupiedplanes2.length; x++) {
						
							
							try {
								if (occupiedplanes[x] != null){
									occupiedplanes[x]= occupiedplanes2[x][0] + " [ Ausgelastet zu " + occupiedplanes2[x][1] + "% ]";
								}
								
								
							} catch (Exception e) {
								e.printStackTrace();
							}
							
							
						}
						
						
						//System.out.print(Arrays.toString(occupiedplanes));
						return occupiedplanes;
		
		
	}
	

	
}