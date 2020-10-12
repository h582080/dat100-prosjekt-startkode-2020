package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {
	
	private GPSPoint[] gpspoints;
	
	public GPSComputer(String filename) {
		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();
	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}
	
	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	// beregn total distances (i meter)
	public double totalDistance() {
		double distance = 0;
		for (int i = 0; i<gpspoints.length-1; i++) {
			distance += GPSUtils.distance(gpspoints[i], gpspoints[i+1]);
		}
		return distance;
		
	}

	// beregn totale høydemeter (i meter)
	public double totalElevation() {
		
		double elevation = 0;
		for (int i = 0; i<gpspoints.length-1; i++) {
				if (gpspoints[i].getElevation()<gpspoints[i+1].getElevation()) {
						elevation=gpspoints[i+1].getElevation();
				}	
		}
		return elevation;

	}

	// beregn total tiden for hele turen (i sekunder)
	public int totalTime() {
		return (gpspoints[gpspoints.length-1].getTime()-gpspoints[0].getTime());
	}
		
	// beregn gjennomsnitshastighets mellom hver av gps punktene
	public double[] speeds() {
		double [] speedsTab = new double [gpspoints.length-1];
		for (int i = 0; i<gpspoints.length-1; i++) {
			speedsTab[i]=GPSUtils.speed(gpspoints[i],gpspoints[i+1]);
		}
		return speedsTab;
	}
	
	public double maxSpeed() {
		
		double maxspeed = 0;
		
		return GPSUtils.findMax(speeds());
		
	}

	public double averageSpeed() {
		return totalDistance()/totalTime()*3.6;
	}

	/*
	 * bicycling, <10 mph, leisure, to work or for pleasure 4.0 bicycling,
	 * general 8.0 bicycling, 10-11.9 mph, leisure, slow, light effort 6.0
	 * bicycling, 12-13.9 mph, leisure, moderate effort 8.0 bicycling, 14-15.9
	 * mph, racing or leisure, fast, vigorous effort 10.0 bicycling, 16-19 mph,
	 * racing/not drafting or >19 mph drafting, very fast, racing general 12.0
	 * bicycling, >20 mph, racing, not drafting 16.0
	 */

	// conversion factor m/s to miles per hour
	public static double MS = 2.236936;

	// beregn kcal gitt weight og tid der kjøres med en gitt hastighet
	public double kcal(double weight, int secs, double speed) {

		double kcal;

		//kcal = MET * bodyweight(kg) * time (h)
		double met = 0;		
		double speedmph = speed * MS;

		if (speedmph<10 && speedmph>0) {
			met=4.0;
		} else if (10<=speedmph && speedmph<12) {
			met=6.0;
		} else if (12<=speedmph && speedmph<14) {
			met=8.0;
		}else if (14<=speedmph && speedmph<16) {
			met=10.0;
		}else if (16<=speedmph && speedmph<20) {
			met=12.0;
		}else if (20<=speedmph) {
			met=16.0;
		}
		
		kcal= met * weight * secs/3600;
		
		return kcal;
		
	}

	public double totalKcal(double weight) {
		return kcal(weight, totalTime(), averageSpeed());		
	}
	
	private static double WEIGHT = 80.0;
	
	public void displayStatistics() {
		System.out.println("==============================================");
		System.out.println("Total Time"+"\t:     "+ GPSUtils.formatTime(totalTime()));
		System.out.println("Total distance"+"\t:"+String.format("%1$12.2f", totalDistance()/1000)+"km");
		System.out.println("Total elevation"+"\t:"+String.format("%1$12.2f", totalElevation())+"m");
		System.out.println("Max speed"+"\t:"+String.format("%1$12.2f", maxSpeed())+"km/t");
		System.out.println("Average speed"+"\t:"+String.format("%1$12.2f", averageSpeed())+"km/t");
		System.out.println("Energy"+"\t\t:"+String.format("%1$14.2f", totalKcal(WEIGHT))+"kcal");
		System.out.println("==============================================");		
	}

}
