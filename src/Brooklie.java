/*
 *   Copyright (c) 2022 Bryn E. Price (Brooklie)
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

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.NumberFormat;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * Brooklie class.
 *
 * @author Bryn E. Price
 * @version 2.0.0
 */
public class Brooklie {
    /*-Brooklie-20180921-*/

    /**
     * Class variables
     */
    public static final String DATE_COMPONENT = "DATE";
    public static final String TIME_COMPONENT = "TIME";

    /**
     * <code>getDateTimeComponent</code> method.
     *
     * @author Bryn E. Price
     * @param _strComponent <code>String</code> of the date/time component to be returned, either DATE_COMPONENT or TIME_COMPONENT
     * @param _strDateTime <code>String</code> of a date and time value
     * @return <code>String</code> of the date or time component
     */
    public static String getDateTimeComponent(
            String _strComponent,
            String _strDateTime) {
        /*-Brooklie-20220305T2226-*/
        // TODO Add check to validate the date/time string is valid
        // TODO !!! Create a test suite for this method!!!
        String strRV = "";
        int intSpaceLocation = _strDateTime.indexOf(" ");
        if  (_strComponent.equals(DATE_COMPONENT)) {
            strRV = _strDateTime.substring(0, intSpaceLocation - 1);
        }
        return strRV;
    }

    /**
     *
     *
     * @param _intValue
     * @return
     */
    public static boolean convertIntegerToBoolean(
            int _intValue) {
        /*-20220611-*/
        // TODO Make this a single line if statement on return
        boolean blnRV = false;
        if (_intValue == 1) {
            blnRV = true;
        }
        else {
            blnRV = false;
        }
        return blnRV;

    }

//    public final void setTextViewProperties(@Nullable TextView pobjTextView) {
//    }

    /**
     * Converts a <code>String</code> representation of a boolean value to a <Code>boolean</Code> value. <br>
     * Default return value is <code>false</code>.
     *
     * @param _strBoolean <code>String</code> representation of a <code>boolean</code> value e.g. 'true' or 'TRUE'.
     *                    No other <Code>String</Code> variations are allowed.
     * @return <code>boolean</code> representation of <code>_strBoolean</code>
     */
    public static boolean convertStringToBoolean(
            String _strBoolean) {
        /*-Brooklie-20220611-*/
        // TODO Add 'T' and 'F' functionality
        boolean blnRV = false;
        if (_strBoolean.toUpperCase().equals("TRUE")) {
            blnRV = true;
        }
        return blnRV;

    }

    /**
     * Prints formatted exception text to the system.out stream.
     *
     * @param _objException Exception object.
     */
    public static void log(
            Exception _objException) {
        /*-Brooklie-20220611-*/

        log(9, _objException.toString());
        StackTraceElement[] steElements = _objException.getStackTrace();
        StackTraceElement steElement = null;

        for(int intCount = 0; intCount < (steElements.length - 1); ++intCount) {

            steElement = steElements[intCount];
            StringBuilder strExceptionString = new StringBuilder();

            Brooklie.log(9, strExceptionString.append(
                            steElement.getClassName())
                    .append(".")
                    .append(steElement.getMethodName())
                    .append("(")
                    .append(steElement.getFileName())
                    .append(":")
                    .append(steElement.getLineNumber())
                    .append(")").toString());
        }

        Brooklie.log(9, "End of exception stack dump");

    }

    /**
     * Prints formatted text to the system.out stream.
     *
     * @param _intLogLevel Log level (indent)
     * @param _strText String to print
     */
    public static void log(
            int _intLogLevel,
            String _strText) {
        /*-Brooklie-20220611-*/

        String strIndent = "";

        switch(_intLogLevel) {
            case 0:
                strIndent = "--> ";
                break;
            case 1:
                strIndent = "----> ";
                break;
            case 2:
                strIndent = "--------> ";
                break;
            case 3:
                strIndent = "----------> ";
                break;
            case 99:
                strIndent = "===-->";
                break;
            default:
                strIndent = "--> ";
        }

        System.out.println(strIndent + _intLogLevel + " " + _strText);
    }

    /**
     * <code>getSeparator</code> method.<br>
     * <br>
     * @return String of characters.
     */
    public static String getSeparator() {
        /*-Brooklie-20241115-*/
        return ("-").repeat(80);
    }

    /**
     * <code>getDateString</code> method.<br>
     * <br>
     * @param _calDateTime <code>GregorianCalendar</code> object.
     * @return <code>String</code> representing the date and time derived from the <code>GregorianCalendar</code>
     * parameter.
     */
    public static String getDateString(
            GregorianCalendar _calDateTime) {
        /*-Brooklie-20050101-*/
        return 	_calDateTime.get(GregorianCalendar.DAY_OF_MONTH) + "/" +
               (_calDateTime.get(GregorianCalendar.MONTH) + 1) + "/" +
                _calDateTime.get(GregorianCalendar.YEAR) + " @ " +
                _calDateTime.get(GregorianCalendar.HOUR_OF_DAY) + ":" +
                _calDateTime.get(GregorianCalendar.MINUTE) + ":" +
                _calDateTime.get(GregorianCalendar.SECOND) + "." +
                _calDateTime.get(GregorianCalendar.MILLISECOND);
    }

    /**
     * <code>loadFile</code> method.<br>
     * Expects a text file with a single column of 100 numbers.<br>
     * <br>
     * @param pstrFile directory and name of file to be loaded.
     * @return An int array with 100 elements.
     */
    public static int[] loadFile(String pstrFile) {
        /*-Brooklie-20050101-*/
        int[] ary = new int[100];
        try {
            BufferedReader fis =
                        new BufferedReader(new FileReader(pstrFile));
            int intCntr = 0;
            String str = null;
            while ((str = fis.readLine()) != null) {
                ary[intCntr] = Integer.decode(str);
                intCntr++;
            }
                fis.close();
        } catch (Exception exc){
            /* Do nothing */
            System.out.println(exc.getMessage());
        }
        return ary;
    }

    /**
    * Get NumberFormat object with defaults
    *
    * @return NumberFormatter
    */
    public static NumberFormat getNumberFormatter() {
        /*-Brooklie-20050101-*/
        NumberFormat nft = NumberFormat.getNumberInstance();
        nft.setGroupingUsed(false);
        nft.setMinimumIntegerDigits(5);
        nft.setMinimumFractionDigits(4);
        return nft;
    }

    /**
     * <code>getMutationFactor</code> method.<br>
     * <br>
     * @return Mutation factor.
     */
    public static double getMutationFactor() {
        /*-Brooklie-20050101-*/
        Random rnd = new Random();
        double dblMutationFactor = rnd.nextDouble();
        if (dblMutationFactor >= 0.5) {
            dblMutationFactor = dblMutationFactor - 0.5;
        } else {
            dblMutationFactor = dblMutationFactor * -1;
        }
        return dblMutationFactor;
    }

    /*
    * Mutates a value.<br>
    * <br>
    * @returns double
    */
    public static double mutate(double _dplValueToMutate) {
        /*-Brooklie-20050101-*/
        return _dplValueToMutate +  (_dplValueToMutate * getMutationFactor());
    }

    /*
    * Returns a random number
    *
    * @returns int
    */
    public static int getRandomNumber(int pintSeeds) {
        return new Random().nextInt(pintSeeds);
    }

    /**
     * <code>getRandomWeatherFactor</code> method.<br>
     * Returns a <code>double</code> value representing a multiplication
     * factor for all weather calculations.<br>
     * <br>
     * @return Random weather factor.
     */
    static double getRandomWeatherFactor() {
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
    static void printStats(
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

        NumberFormat nft = getNumberFormatter();
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
}