import java.text.NumberFormat;
import java.util.Random;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Date;

/**
 * <code>Biosphere</code> class.
 *
 */
public class Biosphere{
	/*-Brooklie-bryn.e.price@gmail.com-*/

	/**
	 * <code>main</code> method.
	 *
	 * @param args String array of arguments.
	 */
	public static void main(String[] args){
		/*-Brooklie-20050101-*/

		try {
			/*
			 * Arguments passed as parameters:
			 *   0 - Number of simulations to run
			 * 	 1 - Initial seed
			 * 	 2 - Max size
			 * 	 3 - Sunlight climate file
			 *   4 - Water climate file
			 *   5 - Randomise weather
			 * Sample:
			 *   1 3 500 004-S.climate 004-W.climate true
			 */

			/* Verify correct number of parameters have been passed */
			if (args.length != 7) {
				System.out.println("Missing parameter(s)...");
				// Throw exception.
			}

			/* Parameters */
			int intSimulationsToRun = Integer.decode(args[0]);
			int intBiosphereSeedCount = Integer.decode(args[1]);
			int intMaxBiosphereSize = Integer.decode(args[2]);
			String strSunlightFile = args[3];
			String strWaterFile = args[4];
			boolean blnRandomiseWeather = args[5].equals("true");
			String strSeperator = "----------------------------------------------------------------------------";

			/*
			 * Print the simulation processor configuration details.
			 */
			System.out.println(strSeperator);
			System.out.println("SIMULATION PROCESSOR CONFIGURATION");
			System.out.println(strSeperator);
			System.out.println("Number of simulations: " + intSimulationsToRun);
			System.out.println("Max biosphere size   : " + intMaxBiosphereSize);
			System.out.println("Random weather       : " + blnRandomiseWeather);
			System.out.println("Sunlight file        : " + strSunlightFile);
			System.out.println("Rainfall file        : " + strWaterFile);
			System.out.println(strSeperator);

			/* Default settings */
			int intSimulationsRun = 1;

			/*
			 * -----------------------------------------------------------------------
			 * For each of the nominated number of biosphere simulations to run...
			 * -----------------------------------------------------------------------
			 */
			while (intSimulationsRun <= intSimulationsToRun) {

				double dblMaxNumberOfPlants = 0;
				double dblMaxAgeDuringSimulation = 0;
				GregorianCalendar gclSimulationStart = new GregorianCalendar();

				int intRandomSeedCount = Common.getRandomNumber(intBiosphereSeedCount);
				double dblMaxAge = 0;
				double dblAvgAge = 0;
				double dblBiosphereAge = 0;
				//double dblPlantNumber = 0;

				/* Print simulation configuration */
				System.out.println("SIMULATION # " + intSimulationsRun);
				System.out.println(strSeperator);
				System.out.println("Initial # plants : " + intRandomSeedCount);
				System.out.println("Start            : " + Common.getDateString(gclSimulationStart));
				System.out.println(strSeperator);

				ArrayList<Plant> arlBiosphere = new ArrayList<>();
				for (int intCntr = 0; intCntr < intRandomSeedCount; intCntr++) {
					arlBiosphere.add(new Plant());
				}

				int[] arySunlightCycle = Common.loadFile(strSunlightFile);
				int[] aryWaterCycle = Common.loadFile(strWaterFile);
				int intDayOfYear = 0;
				double dblWeatherFactor = 1;

				double dblSunlight = 0;
				double dblWater = 0;
				double dblPopulationSunlight = 0;
				double dblPopulationWater = 0;
				double dblHeightSunlight = 0;
				double dblHeightWater = 0;

				/*
				 * -----------------------------------------------------------------------
				 * While there are more than zero plants in the biosphere...
				 * -----------------------------------------------------------------------
				 */
				while (!arlBiosphere.isEmpty()) {

					if (blnRandomiseWeather) {
						dblWeatherFactor = getRandomWeatherFactor();
					} else {
						dblWeatherFactor = 1;
					}

					dblBiosphereAge ++;
					int intBiosphereSize = arlBiosphere.size() - 1;

					dblSunlight = arySunlightCycle[intDayOfYear] +
							(arySunlightCycle[intDayOfYear] * dblWeatherFactor);
					dblWater = aryWaterCycle[intDayOfYear] +
							(aryWaterCycle[intDayOfYear] * dblWeatherFactor);


					printStats(
							(int) (dblBiosphereAge - 1),
							(int) (intBiosphereSize + 1),
							(int) dblAvgAge,
							(int) dblMaxAge,
							intDayOfYear,
							dblWeatherFactor,
							(double) dblSunlight,
							(double) dblWater);

					dblMaxAge = 0;
					dblAvgAge = 0;

					/*
					 * Calculate the total height and population count.
					 */
					double dblTotalHeight = 0;
					int intPopulation = 0;

					for (int intPlant = 0; intPlant <= arlBiosphere.size() - 1; intPlant++) {
						dblTotalHeight = dblTotalHeight + arlBiosphere.get(intPlant).getHeight();
					}

					if (dblTotalHeight == 0) {
						dblTotalHeight = 1;
					}

					dblHeightSunlight = (dblSunlight * 0.8) / dblTotalHeight;
					dblHeightWater = (dblWater * 0.8) / dblTotalHeight;

					dblPopulationSunlight = (dblSunlight * 0.2) / intPopulation;
					dblPopulationWater = (dblWater * 0.2) / intPopulation;

					/*
					 * -----------------------------------------------------------------------
					 * For each plant in the biosphere, execute a metabolic cycle.
					 * -----------------------------------------------------------------------
					 */
					for (int intPlant = 0; intPlant <= arlBiosphere.size() - 1; intPlant++) {

						/* Execute a metabolic cycle */
						arlBiosphere.get(intPlant).executeMetabolicCycle(
								(dblHeightSunlight * arlBiosphere.get(intPlant).getHeight()) +  dblPopulationSunlight,
								  (dblHeightWater * arlBiosphere.get(intPlant).getHeight()) + dblPopulationWater);

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

					dblAvgAge = dblAvgAge / arlBiosphere.size();

					/* Clean up the dead plants */
					for (int intPlant = 0; intPlant <= arlBiosphere.size() - 1; intPlant++) {
						if (arlBiosphere.get(intPlant).isDead()) {
							//arlBiosphere.get(intPlant).printVitalSigns(false);
							arlBiosphere.remove(intPlant);
						}
					}

					intDayOfYear++;

					if (intDayOfYear == 100) {
						intDayOfYear = 0;
					}

					arlBiosphere.trimToSize();

					//pause(1000000000);

				} /* END WHILE */

				GregorianCalendar gclSimulationFinish = new GregorianCalendar();
				gclSimulationFinish.setTime(new Date());
				System.out.println(strSeperator);
				System.out.println("Finish         : " + Common.getDateString(gclSimulationFinish));
				System.out.println("Biosphere age  : " + dblBiosphereAge);
				System.out.println("Max plants     : " + dblMaxNumberOfPlants);
				System.out.println("Max plant age  : " + dblMaxAgeDuringSimulation);
				System.out.println(strSeperator);

				//System.out.println("BIOSPHERE IS DEAD... (" + dblBiosphereAge + ")" );
				intSimulationsRun = intSimulationsRun + 1;
			}

		} catch(Exception _exc) {
			/* An exception has been thrown */
			System.out.println("An error has occurred, exiting...");
		}
	}

	/**
	 * <code>getRandomWeatherFactor</code> method.<br>
	 * Returns a <code>double</code> value representing a multiplication
	 * factor for all weather calculations.<br>
	 * <br>
	 * @return Random weather factor.
	 */
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

	/**
	 * <code>printStats</code> method.<br>
	 * Prints the current biosphere stats to the standard output.<br>
	 * <br>
	 * @param intBiosphereAge Age of the biosphere.
	 * @param intBiospherePopulation Biosphere population.
	 * @param intBiospherePopulationAverageAge Average age of the biosphere population.
	 * @param intBiospherePopulationMaximumAge Maximum age of the biosphere population.
	 * @param intDayOfYear Day of year.
	 * @param dblWeatherFactor Weather factor.
	 * @param dblSunlight Sunlight available to tbe biosphere.
	 * @param dblWater Water available to tbe biosphere.
	 */
	private static void printStats(
			int intBiosphereAge,
			int intBiospherePopulation,
			int intBiospherePopulationAverageAge,
			int intBiospherePopulationMaximumAge,
			int intDayOfYear,
			double dblWeatherFactor,
			double dblSunlight,
			double dblWater) {
		/*-Brooklie-20050101-*/

		String strAGE = "AGE:";
		String strPOP = "POP:";
		String strAAG = "AAG:";
		String strMAG = "MAG:";
		String strDOY = "DOY:";
		String strFAC = "FAC:";
		String strSUN = "SUN:";
		String strWTR = "WTR:";

		NumberFormat nft = Common.getNumberFormatter();
		nft.setMinimumFractionDigits(2);
		nft.setMinimumIntegerDigits(2);
		System.out.println(
				strAGE + intBiosphereAge + " " +
				strPOP + intBiospherePopulation + " " +
				strAAG + intBiospherePopulationAverageAge +  " " +
				strMAG + intBiospherePopulationMaximumAge +  " " +
				strDOY + intDayOfYear +  " " +
				strFAC + nft.format(dblWeatherFactor) +  " " +
				strSUN + nft.format(dblSunlight) + " " +
				strWTR + nft.format(dblWater));
	}

} /* End of Biosphere class */