package ASProjekt;

import java.io.*;

public class read_write {


	
	public static void main (String[] args) {		
	}
	
		
		static void schreiben(String feld[], String datname)
		  //Methode zur Speicherung von Daten auf der Festplatte
		  //String feld[] für die Übertragung eine Arrays deren Daten in einer Datei
		  //gespeichert werden sollen.
		  //String datname für die Festlegung des Dateinamens
		  {

		    //Objektdeklaration Variablen
		    File datei = null;
		    FileWriter fileWriter = null;
		    BufferedWriter writer = null;
		  
		    try
		    {
		      //Anfang  Dateischreibvorbereitungen => Objektdefinition
		      datei = new File(datname);//Objekt zur Bereitstellung der Datei
		      fileWriter = new FileWriter(datei);//Objekt zur Öffnung der Datei für Schreiboperationen
		      writer = new BufferedWriter(fileWriter);//Objekt zum Datenschreiben in einer Datei
		      //Ende  Dateischreibvorbereitungen

		  
		      writer.write(Integer.toString(feld.length)); //Erste Dateizeile zur
		                                                    //Speicherung der Arraygröße
		      writer.write("\n");                           //Neue Zeile in Datei

		      for (int i = 0; i < feld.length; i++)
		      {
		          if (feld[i] != null)
		          {
		             writer.write(feld[i]);
		             writer.write("\n");
		          }
		          else
		          {
		             writer.write("\n");
		          }
		      }

		      writer.close();

		    }
		    catch(IOException e)
		    {
		      //System.out.println(e);
		    }
		    finally
		    {
		      //System.out.println("Schreiben der Daten ist beendet");
		    }

		  }
		  
		  static String[] lesen(String datname)
		  //Methode zum Lesen von Daten von der Festplatte
		  //String datname für die Festlegung des Dateinamens
		  //String [] ermöglicht die Ergenisübergabe des Arrays in dem die gelesenen
		  //Daten gespeichert wurden
		  {
		    @SuppressWarnings("unused")
			File datei = null;
		    FileReader fileReader = null;
		    BufferedReader reader = null;
		    String text [] = null;
		    int i = 0;
		    try
		    {
		      //Anfang  Dateilesevorbereitungen
		      datei = new File(datname); //Objekt zur Bereitstellung der Datei
		      fileReader = new FileReader(datname); //Objekt zur Öffnung der Datei für Leseoperationen
		      reader = new BufferedReader(fileReader);//Objekt zum Datenlesen aus einer Datei
		      //Ende  Dateilesevorbereitungen
		      String zeile;

		      zeile = reader.readLine();
		      //Leist 1. Dateizeile aus und speichert Daten als String ab

		      final int ANZ = Integer.valueOf(zeile);//Wandelt Text in int um
		      text = new String[ANZ];  //Größe des Stringarrays wird auf ANZ festgelegt
		                               //Objektdefinition
		      //Leist 2. Dateizeile aus und speichert Daten als String ab
		      //Nur wenn Zeile in Datei vorhanden und nicht größer als das Array
		      for (i = 0; i < ANZ; i++)
		      {
		         zeile = reader.readLine();
		         if ( zeile != null)//DateiEnde noch nicht erreicht?
		           if ( zeile.equals("") == false )
		           {
		              text[i] = zeile;
		           }
		           else //Wenn keine Zeichen in eingelesener Zeile
		           {
		              System.out.println("Keine Daten in Dateizeile: " + i);
		              text[i] = null; //Textarrayelement wird komplett leer gesetzt
		           }
		      }
		      reader.close();
		    }
		    catch(IOException e)
		    {
		      System.out.println("Dateifehler: " + e);
		    }
		    finally
		    {
		      //System.out.println("Lesen der Daten ist beendet");
		    }
		  
		    return text;
		  }
		  //************************************************************************************

		  static void verzeichnislesen()
		  {
		    File datei = new File(".\\");//(".\\Test")RelativesVerzeichniss sonst direkt
		                                 // und komplett z.B. "C:\\" eingeben
		    String dateienVerzeichnisse[] = datei.list();
		    if(dateienVerzeichnisse != null)
		    {
		      for (int i = 0; i < dateienVerzeichnisse.length; i++)
		      {
		        System.out.println(dateienVerzeichnisse[i]);
		      }
		    }

		  }
		  static boolean dateiNamenPruefung(String dateiname)
		  {
		    File datei = new File(".\\");
		    boolean gefunden = false;
		    String dateienVerzeichnisse[] = datei.list();
		    if(dateienVerzeichnisse != null)
		    {
		      for (int i = 0; i < dateienVerzeichnisse.length; i++)
		      {
		        if (dateienVerzeichnisse[i].equals(dateiname)==true)
		        {
		          gefunden = true;
		        }
		      }
		    }

		    return gefunden;
		  }


		  


		
}

