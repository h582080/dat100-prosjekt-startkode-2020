package no.hvl.dat100ptc.oppgave2;

import java.util.Arrays;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSDataConverter {

	// Eksempel - tidsinformasjon (som String): 2017-08-13T08:52:26.000Z
    // skal omregnes til sekunder (som int): 8 * 60 * 60 + 52 * 60 + 26 
	
	private static int TIME_STARTINDEX = 11; // startindex for tidspunkt i timestr

	public static int toSeconds(String timestr) {
		
		int secs = 0;
		int hr, min, sec;
		
		
		//Setter stringen inn i en ny String tabell
		String [] timeStrTab=timestr.split("");
		String [] timeStrNum= { 
				timeStrTab[11],timeStrTab[12], //timer 
				timeStrTab[14],timeStrTab[15], //minutter
				timeStrTab[17],timeStrTab[18]  //sekunder
		};
		
		//gjør om timer til sekunder, og legger sekunder i secs variabelen
		if (timeStrNum[0].equals("0")) {
			hr=Integer.parseInt(timeStrNum[1]);
			secs+=(hr*3600);
		} else {
			hr=Integer.parseInt(timeStrNum[0]+timeStrNum[1]);
			secs+=(hr*3600);
		}
		
		
		//gjør om minutter til sekunder, og legger sekunder i secs variabelen
		if (timeStrNum[2].equals("0")) {
			min=Integer.parseInt(timeStrNum[3]);
			secs+=(min*60);
		} else {
			min=Integer.parseInt(timeStrNum[2]+timeStrNum[3]);
			secs+=(min*60);
		}
		
		//legger sekunder inn i secs variabelen
		if (timeStrNum[4].equals("0")) {
			sec=Integer.parseInt(timeStrNum[5]);
			secs+=(sec);
		} else {
			sec=Integer.parseInt(timeStrNum[4]+timeStrNum[5]);
			secs+=(sec);
		}
		return secs;		
	}

	public static GPSPoint convert(String timeStr, String latitudeStr, String longitudeStr, String elevationStr) {

		GPSPoint gpspoint=new GPSPoint(toSeconds(timeStr), Double.parseDouble(latitudeStr),
				Double.parseDouble(longitudeStr) , Double.parseDouble(elevationStr));
		return gpspoint;
	    
	}
	
}
