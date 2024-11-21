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
 * <code>SimulationManager</code> class.
 *
 */
public class SimulationManager{
    /*-Brooklie-bryn.e.price@gmail.com-*/

    /**
     * <code>main</code> method.
     *
     * @param args String array of arguments.
     */
    public static void main(String[] args){
        /*-Brooklie-20050101-*/

        try {

            /* Defaults */
            int intSimulationsToRun = Integer.decode(args[0]);
            int intBiosphereSeedCount = Integer.decode(args[1]);
            int intMaxBiosphereSize = Integer.decode(args[2]);
            String strSunlightFile = "src\\003-S.climate";
            String strWaterFile = "src\\003-W.climate";
            boolean blnRandomiseWeather = false;
            String strSeperator = ("-").repeat(80);

            /* Print the simulation processor configuration details */
            System.out.println(strSeperator);
            System.out.println("SIMULATION PROCESSOR CONFIGURATION");
            System.out.println(strSeperator);
            System.out.println("Number of simulations scheduled to run : " + intSimulationsToRun);
            System.out.println("Seed for initial random biosphere size : " + intBiosphereSeedCount);
            System.out.println("Maximum biosphere size                 : " + intMaxBiosphereSize);
            System.out.println("Random weather enabled                 : " + blnRandomiseWeather);
            System.out.println("Sunlight weather pattern file          : " + strSunlightFile);
            System.out.println("Rainfall weather pattern file          : " + strWaterFile);
            System.out.println(strSeperator);

            /* Default settings */
            int intSimulationsRun = 1;

            /*
             * -----------------------------------------------------------------------
             * For each of the biosphere simulations scheduled to run...
             * -----------------------------------------------------------------------
             */
            while (intSimulationsRun <= intSimulationsToRun) {

                double dblMaxNumberOfPlants = 0;
                double dblMaxAgeDuringSimulation = 0;
                GregorianCalendar gclSimulationStart = new GregorianCalendar();
                /* Print simulation configuration */
                System.out.println("SIMULATION # " + intSimulationsRun);
                System.out.println("Start            : " + Brooklie.getDateString(gclSimulationStart));
                System.out.println(strSeperator);

                ArrayList<Plant> arlBiosphere = new ArrayList<>();
                for (int intCntr = 0; intCntr < intRandomSeedCount; intCntr++) {
                    arlBiosphere.add(new Plant());
                }


                System.out.println("BIOSPHERE IS DEAD... (" + dblBiosphereAge + ")" );
                intSimulationsRun ++;

            } catch (Exception _exc) {
                /* An exception has been thrown */
                System.out.println("An error has occurred, exiting...");
            }
    }

} /* End of Biosphere class */