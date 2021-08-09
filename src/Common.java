import java.text.NumberFormat;
import java.util.Random;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.GregorianCalendar;

public class Common {

	public static String getDateString(GregorianCalendar pcal) {
		String str = "";
		String strAM_PM = "";
		if (pcal.get(GregorianCalendar.AM_PM) == 0) {
			strAM_PM = "AM";
		} else {
			strAM_PM = "PM";
		}
		str = str + pcal.get(GregorianCalendar.DAY_OF_MONTH) + "/" +
				(pcal.get(GregorianCalendar.MONTH) + 1) + "/" +
				pcal.get(GregorianCalendar.YEAR) + " @ " +
				pcal.get(GregorianCalendar.HOUR_OF_DAY) + ":" + 
				pcal.get(GregorianCalendar.MINUTE) + ":" +
				pcal.get(GregorianCalendar.SECOND) + 
				strAM_PM;
		return str;
	}

	public static int[] loadFile(String pstrFile) {
		int[] ary = new int[100];
		try {
			BufferedReader fis = 
						new BufferedReader(new FileReader(pstrFile));
			int intCntr = 0;
			String str = null;
			while ((str = fis.readLine()) != null) {
				ary[intCntr] = Integer.decode(str).intValue();
				//System.out.println(intCntr + " : " + Integer.decode(str).intValue());
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
		NumberFormat nft = NumberFormat.getNumberInstance();
		nft.setGroupingUsed(false);
		nft.setMinimumIntegerDigits(5);
		nft.setMinimumFractionDigits(4);
		return nft;
	}

	/*
	* Get mutation factor
	*
	* @returns double
	*/
	public static double getMutationFactor() {
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
	* Mutates a value
	*
	* @returns double
	*/
	public static double mutate(double pdplvalueToMutate) {
		return pdplvalueToMutate = pdplvalueToMutate + 
				(pdplvalueToMutate * getMutationFactor());
	}

	/*
	* Returns a random number
	*
	* @returns int
	*/
	public static int getRandomNumber(int pintSeeds) {
		return new Random().nextInt(pintSeeds);
	}

}