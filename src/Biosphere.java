import java.text.NumberFormat;
import java.util.Random;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Date;
import javax.swing.*;
import java.awt.Color;

public class Biosphere{
	/*-Brooklie-bryn.e.price@gmail.com-*/

	public static void main(String[] args){
		/*
		x - Number of simulations to run
		y - Print log output
		Arguments passed as parameters
		0 - Initial seed
		1 - Max size
		2 - Sunlight climate file
		3 - Water climate file
		4 - Randomise weather
		5 - Print output headings
		*/
		if (args.length != 6) {
			System.out.println("Missing parameter(s)...");
		}

		int intSimulations = 1;
		boolean blnPrintLogOutput = false;
		int intBiosphereSeedCount = Integer.decode(args[0]);
		int intMaxBiosphereSize = Integer.decode(args[1]);
		String strSunlightFile = args[2];
		String strWaterFile = args[3];
		boolean blnRandomiseWeather = args[4].equals("true");
		boolean blnPrintHeadings = args[5].equals("true");

		String strSeperator = 
			"----------------------------------------------------------------------------";

		System.out.println(strSeperator);
		System.out.println("Max Size       : " + intMaxBiosphereSize);
		System.out.println("Random weather : " + blnRandomiseWeather);
		System.out.println("Sunlight file  : " + strSunlightFile);
		System.out.println("Rainfall file  : " + strWaterFile);
		System.out.println(strSeperator);

		/* ---- */
		String strLogString;

		int intSimulationsRun = 1;

		double dblMaxNumberOfPlants = 0;

		double dblMaxAgeDuringSimulation = 0;

		GregorianCalendar gclSimulationStart = null;

		GregorianCalendar gclSimulationFinish = null;			

		/* Create the UI */
//		JFrame frmMain = new JFrame("Biosphere");
//		JPanel pnlMain = new JPanel();
		
//		frmMain.setSize(500, 500);
//		frmMain.setVisible(true);
//		frmMain.add(pnlMain);

//		JLabel[] lblArray = new JLabel[intMaxBiosphereSize  + 1];


//		for (int intPlant = 0; intPlant <= intMaxBiosphereSize; intPlant ++){
//			lblArray[intPlant] = new JLabel();
//			lblArray[intPlant].setSize(10, 10);
//			lblArray[intPlant].setOpaque(true);
//			pnlMain.add(lblArray[intPlant]);
//		}

		/* Start the simulations */
		while (intSimulationsRun <= intSimulations) {

			gclSimulationStart = new GregorianCalendar();
			gclSimulationStart.setTime(new Date());

			int intRandomSeedCount = (int) Common.getRandomNumber(intBiosphereSeedCount);
		
			double dblMaxAge = 0;
			double dblAvgAge = 0;
			dblMaxNumberOfPlants = 0;
			dblMaxAgeDuringSimulation = 0;
			double dblBiosphereAge = 0;
			double dblPlantNumber = 0;

			ArrayList<Plant> arlBiosphere = new ArrayList<Plant>();
			for (int intCntr = 0; intCntr < intRandomSeedCount; intCntr++) {
				arlBiosphere.add(new Plant());
			}

			int[] arySunlight = Common.loadFile(strSunlightFile);
			int[] aryWater = Common.loadFile(strWaterFile);
			int intDayOfYear = 0;
			double dblWeatherFactor = 1;
	
			double dblSunlight;
			double dblWater;
			double dblPopulationSunlight;
			double dblPopulationWater;
			double dblHeightSunlight;
			double dblHeightWater;

			/* Print simulation number and seed count */
			System.out.println("Simulation #   : " + intSimulationsRun);
			System.out.println("Seed           : " + intRandomSeedCount);
			System.out.println("Start          : " + Common.getDateString(gclSimulationStart));

			while (arlBiosphere.size() > 0){ /* START */

				if (blnRandomiseWeather == true) {
					dblWeatherFactor = getRandomWeatherFactor();
				} else {
					dblWeatherFactor = 1;
				}

				dblBiosphereAge = dblBiosphereAge + 1;
				int intBiosphereSize = arlBiosphere.size() - 1;

				dblSunlight = arySunlight[intDayOfYear] + 
							(arySunlight[intDayOfYear] * dblWeatherFactor);
				dblWater = aryWater[intDayOfYear] + 
							(aryWater[intDayOfYear] * dblWeatherFactor);

				if ((blnPrintLogOutput == true)) {
					printStats((int) (dblBiosphereAge - 1),
						     (int) (intBiosphereSize + 1),
						     (int) dblAvgAge,
						     (int) dblMaxAge,
						     intDayOfYear,
						     dblWeatherFactor,
						     (double) dblSunlight,
						     (double) dblWater,
						     blnPrintHeadings);
				}

				//if ( ((dblBiosphereAge - 1) / 1000000) == (int) ((dblBiosphereAge - 1) / 1000000) ) {
					if ((dblBiosphereAge - 1) == 0) {
						strLogString = "Log            : ";
					} else {
						strLogString = "               : ";
					}
					System.out.println(
							strLogString + "BIO-AGE:" + ((int) (dblBiosphereAge - 1) +
										  " POP: " + (int) (intBiosphereSize + 1) + 
										  " AVG-AGE: " + (int) dblAvgAge) + 
										  " MAX-AGE: " + (int) dblMaxAge);
				//}

				dblMaxAge = 0;
				dblAvgAge = 0;

				/* Calc total height and population count */
				double dblTotalHeight = 0;
				int intPopulation = 0;
	
				for (intPopulation = 0; intPopulation <= arlBiosphere.size() - 1; intPopulation++){
					dblTotalHeight = dblTotalHeight + arlBiosphere.get(intPopulation).getHeight();
				}

				intPopulation = intPopulation;
	
				if (dblTotalHeight == 0) {
					dblTotalHeight = 1;
				}

				//System.out.println(dblTotalHeight);

				dblHeightSunlight = (dblSunlight * 0.8) / dblTotalHeight;
				dblHeightWater = (dblWater * 0.8) / dblTotalHeight;

				dblPopulationSunlight = (dblSunlight * 0.2) / intPopulation;
				dblPopulationWater = (dblWater * 0.2) / intPopulation;

				//System.out.println("PO:" + intPopulation + 
				//		"PS:" + dblPopulationSunlight + "PW:" + dblPopulationWater);
	
				for (int intPlant = 0; intPlant <= arlBiosphere.size() - 1; intPlant ++){

					/* Execute a metabolic cycle */
					arlBiosphere.get(intPlant).executeMetabolicCycle(
										(dblHeightSunlight * 
										arlBiosphere.get(intPlant).getHeight()) + 
										dblPopulationSunlight, 
										(dblHeightWater * 
										arlBiosphere.get(intPlant).getHeight()) + 
										dblPopulationWater);
					//lblArray[intPlant].setBackground(arlBiosphere.get(intPlant).getMetabolicRatingColour());
					//System.out.println(arlBiosphere.get(intPlant).getMetabolicRatingColour().toString());
					

					/* If the plant is not dead then execute the seeding process */
					if (!arlBiosphere.get(intPlant).isDead() &&
					     arlBiosphere.size() <= intMaxBiosphereSize) {
						Plant p = arlBiosphere.get(intPlant).executeSeedingProcess();
						if (p != null) {
							arlBiosphere.add(p);
						}
					}

					/* Calculate if plant has max age greater than current max age */
					if (arlBiosphere.get(intPlant).getMetabolicCycleNumber() > dblMaxAge) {
						dblMaxAge = arlBiosphere.get(intPlant).getMetabolicCycleNumber();
					}

					dblAvgAge = dblAvgAge + arlBiosphere.get(intPlant).getMetabolicCycleNumber();

					if (dblMaxAge > dblMaxAgeDuringSimulation) {
						dblMaxAgeDuringSimulation = dblMaxAge;
					}

					if (intPlant > dblMaxNumberOfPlants) {
						dblMaxNumberOfPlants = intPlant;
					}

				} /* END FOR */
			
				dblAvgAge =  dblAvgAge / arlBiosphere.size();

				/* Clean up the dead plants */
				for (int intPlant = 0; intPlant <= arlBiosphere.size() - 1; intPlant ++){
					if (arlBiosphere.get(intPlant).isDead()) {
						//arlBiosphere.get(intPlant).printVitalSigns(false);
						arlBiosphere.remove(intPlant);
					}
				}

				intDayOfYear ++;

				if (intDayOfYear == 100) {
					intDayOfYear = 0;
				}

				arlBiosphere.trimToSize();

				//pause(1000000000);

			} /* END WHILE */

			gclSimulationFinish = new GregorianCalendar();
			gclSimulationFinish.setTime(new Date());

			System.out.println("Finish         : " + Common.getDateString(gclSimulationFinish));
			System.out.println("Biosphere age  : " + dblBiosphereAge);
			System.out.println("Max plants     : " + dblMaxNumberOfPlants);
			System.out.println("Max plant age  : " + dblMaxAgeDuringSimulation);
			System.out.println(strSeperator);		
			
			//System.out.println("BIOSPHERE IS DEAD... (" + dblBiosphereAge + ")" );
			intSimulationsRun = intSimulationsRun + 1;
		}
		/* ---- */

	}

	private static double getRandomWeatherFactor() {
		Random rnd = new Random();
		double dblFactor = rnd.nextDouble();
		if (dblFactor >= 0.5) {
			dblFactor = dblFactor - 0.5;
		} else {
			dblFactor = dblFactor * -1;
		} 
		return dblFactor;
	}


	private static void printStats(int intBiosphereAge,
						 int intBiospherePopulation,
						 int intBiospherePopulationAverageAge,
						 int intBiospherePopulationMaximumAge,
						 int intDayOfYear,
						 double dblWeatherFactor,
						 double dblSunlight,
						 double dblWater,
						 boolean blnPrintHeadings) {
		String strAGE = "AGE:";
		String strPOP = "POP:";
		String strAAG = "AAG:";
		String strMAG = "MAG:";
		String strDOY = "DOY:";
		String strFAC = "FAC:";
		String strSUN = "SUN:";
		String strWTR = "WTR:";

 		if (blnPrintHeadings == false) {
			strAGE = "";
			strPOP = ",";
			strAAG = ",";
			strMAG = ",";
			strDOY = ",";
			strFAC = ",";
			strSUN = ",";
			strWTR = ",";
		}
		NumberFormat nft = Common.getNumberFormatter();
		nft.setMinimumFractionDigits(2);
		nft.setMinimumIntegerDigits(2);
		System.out.println(strAGE + intBiosphereAge + 
					" " + strPOP + intBiospherePopulation + 
					" " + strAAG + intBiospherePopulationAverageAge + 
					" " + strMAG + intBiospherePopulationMaximumAge + 
					" " + strDOY + intDayOfYear + 
					" " + strFAC + nft.format(dblWeatherFactor) + 
					" " + strSUN + nft.format(dblSunlight) +
					" " + strWTR + nft.format(dblWater));
	}

	private static void pause(int pintWaitLoops) {

		for (int intCounter = 0; intCounter <= pintWaitLoops; intCounter++) {

			/* Do nothing */

		}

	}

}