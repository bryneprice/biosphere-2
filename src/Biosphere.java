/*
 *   Copyright (c) 2024 Bryn E. Price (Brooklie)
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses
 */
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * <code>Biosphere</code> class.
 *
 */
public class Biosphere{
	/*-Brooklie-bryn.e.price@gmail.com-*/

	ArrayList<Plant> arlBiosphere = null;
	private double  dblBiosphereAge = 0;
	String strSunlightFile = "src\\003-S.climate";
	String strWaterFile = "src\\003-W.climate";
	private int[]   arySunlightCycle = Brooklie.loadFile(strSunlightFile);
	private int[]   aryWaterCycle = Brooklie.loadFile(strWaterFile);
	private int     intDayOfYear = 0;
	private double  dblWeatherFactor = 1;
    private double  dblWater = 0;
	private double  dblPopulationSunlight = 0;
	private double  dblPopulationWater = 0;
	private double  dblHeightSunlight = 0;
	private double  dblHeightWater = 0;
	private boolean blnRandomiseWeather = false;

	/**
	 * <code>Biosphere</code> constructor.
	 */
	public Biosphere() {
		/*-Brooklie-20241116-*/

		try {
			GregorianCalendar gclSimulationStart = new GregorianCalendar();
			/* Print simulation configuration */
			int intSimulationsToRun = 3;
			int intBiosphereSeedCount = 100;
			int intMaxBiosphereSize = 10000;
			boolean blnRandomiseWeather = false;

			/* Print the simulation processor configuration details */
			System.out.println(Brooklie.getSeparator());
			System.out.println("SIMULATION PROCESSOR CONFIGURATION");
			System.out.println(Brooklie.getSeparator());
			System.out.println("Number of simulations scheduled to run : " + intSimulationsToRun);
			System.out.println("Seed for initial random biosphere size : " + intBiosphereSeedCount);
			System.out.println("Maximum biosphere size                 : " + intMaxBiosphereSize);
			System.out.println("Random weather enabled                 : " + blnRandomiseWeather);
			System.out.println("Sunlight weather pattern file          : " + strSunlightFile);
			System.out.println("Rainfall weather pattern file          : " + strWaterFile);
			System.out.println(Brooklie.getSeparator());
			Brooklie.log(0, Brooklie.getSeparator());
			System.out.println("Start  : " + Brooklie.getDateString(gclSimulationStart));
			System.out.println(Brooklie.getSeparator());

			/*
			 *Create the baseline biosphere
			 */
			arlBiosphere = new ArrayList<>();
			for (int intCntr = 0; intCntr < 100; intCntr++) {
				arlBiosphere.add(new Plant());
			}

		} catch(Exception _exc) {
			/* An exception has been thrown */
			System.out.println("Oops, an error has occurred, exiting...");
		}
	}

	/**
	 * <code>run</code> method to execute a simulation.<br>
	 * <br>
	 * @return
	 */
	public boolean run() {
		/*-Brooklie-20241124-*/

		/*
		 * While there are 1 or more plants in the biosphere...
		 */
		while (!arlBiosphere.isEmpty()) {

			if (blnRandomiseWeather) {
				dblWeatherFactor = Brooklie.getRandomWeatherFactor();
			} else {
				dblWeatherFactor = 1;
			}

			double dblSunlight = arySunlightCycle[intDayOfYear] + (arySunlightCycle[intDayOfYear] * dblWeatherFactor);
			dblWater = aryWaterCycle[intDayOfYear] + (aryWaterCycle[intDayOfYear] * dblWeatherFactor);

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

			dblPopulationWater = (dblWater * 0.2) / intPopulation;
			dblPopulationSunlight = (dblSunlight * 0.2) / intPopulation;


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
				if (!arlBiosphere.get(intPlant).isDead()) {
					Plant pltSeedling = arlBiosphere.get(intPlant).executeSeedingProcess();
					/* Check to see if the seeding process was successful */
					if (pltSeedling != null) {
						arlBiosphere.add(pltSeedling);
					}
				}

			} /* END FOR */

			/* Clean up the dead plants */
			for (int intPlant = 0; intPlant <= arlBiosphere.size() - 1; intPlant++) {
				if (arlBiosphere.get(intPlant).isDead()) {
					arlBiosphere.remove(intPlant);
				}
			}

			intDayOfYear++;
			dblBiosphereAge++;

			if (intDayOfYear == 100) {
				intDayOfYear = 0;
			}

			arlBiosphere.trimToSize();

			Brooklie.log(0, String.valueOf(dblBiosphereAge));

		} /* END WHILE */

		return true;

	}
} /* End of Biosphere class */