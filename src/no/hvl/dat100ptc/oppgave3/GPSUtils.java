package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max; 
		
		max = da[0];
		
		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}
		
		return max;
	}

	public static double findMin(double[] da) {

		double min=da[0];
		for (double d:da) {
			min = Math.min(d, min);
		}
		return min;

	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {
		double [] latitudes = new double [gpspoints.length];
		for (int i = 0; i < latitudes.length; i++ ) {
			latitudes[i]=gpspoints[i].getLatitude();
		}
		return latitudes;
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {
		
		double [] longitudes = new double [gpspoints.length];
		for (int i = 0; i < longitudes.length; i++ ) {
			longitudes[i]=gpspoints[i].getLongitude();
		}
		return longitudes;
	}

	private static int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double d;
		double latitude1, longitude1, latitude2, longitude2;

		//finner verdiene for hvert punkt.
		latitude1=Math.toRadians(gpspoint1.getLatitude());
		latitude2=Math.toRadians(gpspoint2.getLatitude());
		longitude1=Math.toRadians(gpspoint1.getLongitude());
		longitude2=Math.toRadians(gpspoint2.getLongitude());
		
		//regner ut og returnerer d ved bruk av Haversine-formlen
		
		double deltaLatitude = latitude2 - latitude1;
		double deltaLongitude = longitude2- longitude1;
		
		double a = Math.pow((Math.sin(deltaLatitude/2)), 2) + Math.cos(latitude1) * Math.cos(latitude2) * Math.pow(Math.sin(deltaLongitude/2), 2);
		double c = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		return R*c;

	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		int secs;
		double speed;
		secs=gpspoint2.getTime()-gpspoint1.getTime();
		speed=(distance(gpspoint1,gpspoint2))/secs*3.6;
		return speed;
	}

	public static String formatTime(int secs) {

		String timestr;
		String TIMESEP = ":";

		int hh, mm, ss, rest;
		String hhStr, mmStr, ssStr;
		
		hh=secs/3600;	
		rest=secs%3600;
		mm=rest/60;
		rest=rest%60;
		ss=rest;
		//Skriver en if statement "one liner". 
		//F.eks variabel = (statement) ?  true value : false value;
		//hvis statement er sant vil variabel være lik true value.
		//samme gjelder for false
		
		hhStr = hh < 10 ? ("0"+hh) : (""+hh);
		mmStr = mm < 10 ? ("0"+mm) : (""+mm);
		ssStr = ss < 10 ? ("0"+ss) : (""+ss);
		
	
		timestr=hhStr+TIMESEP+mmStr+TIMESEP+ssStr;
		
		return String.format("%2s%s", "",timestr);

	}
	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {
		
		//if funksjon som setter str lik et avrundet tall med to desimaler
		//med x antall mellomrom forran tallet.
		String str = String.format("%1$10.2f", d);	
		String [] strTab = str.split("");
		strTab[str.indexOf(",")]=".";
		str=String.join("", strTab);

		return str;	
	}
}
