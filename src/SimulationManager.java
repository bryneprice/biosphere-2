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
     * <code>main</code> method.<br>
     *<br>
     * @param args String array of arguments.
     */
    public static void main(String[] args) {
        /*-Brooklie-20050101-*/

        /*
         * Start biosphere simulations.
         *
         */
        try {

            int intSimulationsToRun = 3;

            /*
             * -----------------------------------------------------------------------
             * For each of the biosphere simulations scheduled to run...
             * -----------------------------------------------------------------------
             */
            for (int intSimulationsRun = 0; intSimulationsRun < intSimulationsToRun; intSimulationsRun++) {

                /* Print simulation configuration */
                System.out.println("SIMULATION #     : " + intSimulationsRun);
                System.out.println("Start            : " + Brooklie.getDateString(new GregorianCalendar()));
                System.out.println(Brooklie.getSeparator());
                Biosphere bio = new Biosphere();
                bio.run();
                System.out.println("BIOSPHERE IS DEAD... :-(");
            }

            /*
             * Exception, oops...
             *
             */
            } catch (Exception _exc) {
                /* An exception has been thrown */
                System.out.println("An error has occurred, exiting...");
                _exc.printStackTrace(System.out);
            }

        }

} /* End of Biosphere class */