/**
* The base Plant class
*
* @author Bryn Price
*/
import java.text.NumberFormat;
import java.awt.Color;

public class Plant
		extends Object {

	/**
	* Metabolic cycle
	*/
	private double dblMetabolicCycle;

	/**
	* Metabolic rating
	*/
	private int intMetabolicRating;

	/**
	* Height of the plant
	*/
	private double dblHeight;

	/**
	* Maximum height
	*/
	private double dblMaxHeight;

	/**
	* Maximum age
	*/
	private int intMaxAge;

	/**
	* Amount of reserve energy
	*/
	private double dblEnergyReserve;

	/**
	* Amount of reserve water
	*/
	private double dblWaterReserve;

	/** 
	* Minimum amount of sunlight required for growth
	*/
	private double dblMinEnergyForGrowth;

	/** 
	* Maximum amount of sunlight required for growth
	*/
	private double dblMaxEnergyForGrowth;

	/** 
	* Minimum amount of water required for growth
	*/
	private double dblMinWaterForGrowth;

	/** 
	* Maximum amount of water required for growth
	*/
	private double dblMaxWaterForGrowth;

	/**
	* Factor by which sunlight is converted into energy
	*/
	private double dblSunlightConversionFactor;

	/**
	* Factor by which water is taken up by the root system
	*/
	private double dblWaterUptakeFactor;
	
	/**
	* Factor by which the plant will grow when all growth conditions are met
	*/
	private double dblGrowthFactor;

	/**
	* Factor by which the metabolic cycle requirements are calculated
	*/
	private double dblMetabolicCycleFactor;

	/**
	* Maximum energy reserve
	*/
	double dblMaxEnergyReserve;

	/**
	* Maximum water reserve
	*/
	double dblMaxWaterReserve;

	/**
	* Energy required to seed
	*/
	double dblEnergyRequiredToSeed;

	/**
	* Water required to seed
	*/
	double dblWaterRequiredToSeed;

	/**
	* Has germinated
	*/
	boolean blnHasGerminated;

	private int intSeedingInterval;

	/**
	* Base constructor
	*/
	public Plant() {

		dblMetabolicCycle = 0;
		dblHeight = 0;
		blnHasGerminated = false;

		dblMaxHeight = Common.mutate(500);
		intMaxAge = (int) Common.mutate(500);

		intSeedingInterval = (int) Common.mutate(10);

		dblEnergyReserve = Common.mutate(100);
		dblWaterReserve = Common.mutate(100);
		dblSunlightConversionFactor = Common.mutate(0.50);
		dblWaterUptakeFactor = Common.mutate(0.75);
		dblGrowthFactor = Common.mutate(0.5);
		dblMetabolicCycleFactor = Common.mutate(0.5);

		dblMinWaterForGrowth = Common.mutate(1);
		dblMaxWaterForGrowth = Common.mutate(100);
		dblMinEnergyForGrowth = Common.mutate(1);
		dblMaxEnergyForGrowth = Common.mutate(100);
		dblMaxEnergyReserve = Common.mutate(50);
		dblMaxWaterReserve = Common.mutate(50);

		dblEnergyRequiredToSeed = Common.mutate(10);
		dblWaterRequiredToSeed = Common.mutate(10);

	}

	/**
	* Preforms metabolic cycle functions
	*
	* @param pdblSunlight Sunlight level
	* @param pdblWater    Water availability
	*/
	public void executeMetabolicCycle(double pdblSunlight, double pdblWater) {

		intMetabolicRating = 0;

		if (getHasGerminated() || canGerminate(pdblSunlight, pdblWater)) {
			dblMetabolicCycle = dblMetabolicCycle + 1;
			if (!getHasGerminated()) {
				intMetabolicRating = 10;
				dblHeight = 0.1;
				setHasGerminated(true);
			}
			double dblCycleEnergy = pdblSunlight * dblSunlightConversionFactor;
			double dblCycleWater = pdblWater * dblWaterUptakeFactor;
			executeMaintenanceCycle(dblCycleEnergy, dblCycleWater);
			executeGrowthCycle(dblCycleEnergy, dblCycleWater);
		} 
	}

	private boolean canGerminate(double pdblSunlight, double pdblWater) {
		boolean blnCanGerminate = false;
		if (pdblSunlight > 0 && pdblWater > 0) {
			blnCanGerminate = true;
		}
		return blnCanGerminate;
	}

	/**
	* Performs a maintenance cycle and updates energy and water reserves accordingly
	*
	* @param pdblCycleEnergy Energy
	* @param pdblCycleWater  Water
	*/
	private void executeMaintenanceCycle(double pdblCycleEnergy, double pdblCycleWater) {
		dblEnergyReserve = dblEnergyReserve + pdblCycleEnergy;
		dblWaterReserve = dblWaterReserve + pdblCycleWater;

		if (dblEnergyReserve > dblMaxEnergyReserve){
			dblEnergyReserve = dblMaxEnergyReserve;
		}

		if (dblWaterReserve > dblMaxWaterReserve) {
			dblWaterReserve = dblMaxWaterReserve;
		}

		dblEnergyReserve = dblEnergyReserve - getMetabolicCycleEnergyRequirement();
		dblWaterReserve = dblWaterReserve - getMetabolicCycleWaterRequirement();
	}

	/**
	* Execute a growth cycle
	*
	* @param pdblCycleEnergy   Amount of sunlight
	* @param pdblCycleWater    Amount of water
	*/
	private void executeGrowthCycle(double pdblCycleEnergy, double pdblCycleWater){
		//System.out.println("can grow = " + canGrow(pdblCycleEnergy, pdblCycleWater));
		if (canGrow(pdblCycleEnergy, pdblCycleWater)) {
			/* Calculate whether growth outputs are greater than cycle inputs */	
			if (!getHasGerminated()) {
				dblHeight = 0.01;
				setHasGerminated(true);
			} else {
				dblHeight = dblHeight + (dblHeight * dblGrowthFactor);
			}
			dblEnergyReserve = dblEnergyReserve - dblMinEnergyForGrowth;
			dblWaterReserve = dblWaterReserve - dblMinWaterForGrowth;
		}
	}

	/**
	* Calculates whether water and energy requirements for growth are met
	*
	* @param dblNewEnergy Amount of sunlight
	* @param dblNewWater  Amount of water
	* @return boolean
	*/
	private boolean canGrow(double dblNewEnergy, double dblNewWater) {
		boolean blnRV = false;
		if ((dblNewWater >= dblMinWaterForGrowth) && (dblNewWater <= dblMaxWaterForGrowth) &&
		    (dblNewEnergy >= dblMinEnergyForGrowth) && (dblNewEnergy <= dblMaxEnergyForGrowth) &&
		    (dblNewEnergy > (getMetabolicCycleEnergyRequirement() + dblMinEnergyForGrowth)) && 
		    (dblNewWater > (getMetabolicCycleWaterRequirement() + dblMinWaterForGrowth)) &&
		    (dblHeight < dblMaxHeight)) {
			blnRV = true;
		}
		return blnRV;
	}

	/**
	* Calculates the base water requirement for the next metabolic cycle
	*
	* @return double
	*/
	private double getMetabolicCycleWaterRequirement() {
		return getMetabolicCycleWaterRequirement(dblHeight);
	}

	/**
	* Calculates the base water requirement for the next metabolic cycle for a
	* specific height
	*
	* @param pdblHeight Height
	* @return double
	*/
	private double getMetabolicCycleWaterRequirement(double pdblHeight) {
		return pdblHeight * dblMetabolicCycleFactor;
	}


	/**
	* Calculates the base energy requirement for the next metabolic cycle
	*
	* @return double
	*/
	private double getMetabolicCycleEnergyRequirement() {
		return getMetabolicCycleEnergyRequirement(dblHeight);
	}

	/**
	* Calculates the base energy requirement for the next metabolic cycle for a
	* specific height
	*
	* @param pdblHeight Height
	* @return double
	*/
	private double getMetabolicCycleEnergyRequirement(double pdblHeight) {
		return pdblHeight * dblMetabolicCycleFactor;
	}


	/**
	* Returns the height of the plant
	*
	* @return double
	*/
	public double getHeight() {
		return dblHeight;
	}

	/**
	* Returns the available energy of the plant
	*
	* @return double
	*/
	public double getEnergy() {
		return dblEnergyReserve;
	}	

	/**
	* Returns the available water of the plant
	*
	* @return double
	*/
	public double getWater() {
		return dblWaterReserve;
	}

	/**
	* Returns the metabolic cycle number
	*
	* @return double
	*/
	public double getMetabolicCycleNumber() {
		return dblMetabolicCycle;
	}

	/**
	* Print the plants vital signs
	*
	* @param pblnVerboseOutput Print verbose output
	*/
	public void printVitalSigns(boolean pblnVerboseOutput) {
		NumberFormat nft = NumberFormat.getNumberInstance();
		nft.setGroupingUsed(false);
		nft.setMinimumIntegerDigits(5);
		nft.setMinimumFractionDigits(0);
		String strCycle = nft.format(getMetabolicCycleNumber());
		nft.setMinimumFractionDigits(4);
		String strHeight = nft.format(getHeight());
		String strEnergy = nft.format(getEnergy());
		String strWater = nft.format(getWater());
		if (pblnVerboseOutput == true) {
			System.out.println("-----------------------");
			System.out.println("Cycle :" + strCycle);
			System.out.println("Height:" + strHeight);
			System.out.println("Energy:" + strEnergy);
			System.out.println("Water :" + strWater);
		} else {
			System.out.println("C:" + strCycle + " " + 
						 "H:" + strHeight + " " + 
				             "E:" + strEnergy + " " + 
						 "W:" + strWater);
		}
	}

	/**
	* Executes the seeding process
	* This may or may not return a seed
	*
	* @return Plant
	*/
	public Plant executeSeedingProcess() {
		Plant pltSeed = null;
		if (canSeed()) {
			pltSeed = new Plant();
			}
		return pltSeed;
	}

	/**
	* Determines whether the plant can seed
	*
	* @return boolean
	*/
	private boolean canSeed() {
		boolean blnCanSeed = false;
//		if ((dblMetabolicCycle / 23) == (int) (dblMetabolicCycle / 23)) {
//		if ((dblEnergyReserve - dblEnergyRequiredToSeed) > (getMetabolicCycleEnergyRequirement() * 4) &&
//		    (dblWaterReserve - dblWaterRequiredToSeed) > (getMetabolicCycleWaterRequirement() * 4) &&
//		    (dblMetabolicCycle > 50) && (getHasGerminated() && (getHeight() > (dblMaxHeight / 20)))) {
		if ((dblEnergyReserve - dblEnergyRequiredToSeed) > (getMetabolicCycleEnergyRequirement() * 4) &&
		    (dblWaterReserve - dblWaterRequiredToSeed) > (getMetabolicCycleWaterRequirement() * 4) &&
		    (dblMetabolicCycle > (intMaxAge / 20)) && (getHasGerminated()) &&
		    (dblMetabolicCycle / intSeedingInterval) == (int) (dblMetabolicCycle / intSeedingInterval) &&
		    (getHeight() > (1))) {


			blnCanSeed = true;
		}
		return blnCanSeed;
	}

	/**
	* Determines whether the plant is dead
	*
	* @return boolean
	*/
	public boolean isDead() {
		boolean blnIsDead = false;
		if (getEnergy() < 0 || 
			getWater() < 0 ||
			dblMetabolicCycle > intMaxAge) {
			blnIsDead = true;
		}
		return blnIsDead;
	}

	/**
	* Returns a value depending on whether the plant has germinated
	*
	* @return boolean
	*/
	public boolean getHasGerminated() {
		return blnHasGerminated;
	}

	private void setHasGerminated(boolean pbln) {
		blnHasGerminated = pbln;
	}

	public int getMetabolicRating() {
		return intMetabolicRating;
	}

	public Color getMetabolicRatingColour() {
		Color colRV = null;
		if (intMetabolicRating == 0) {
			colRV = Color.RED;
		}
		if (intMetabolicRating == 10) {
			colRV = Color.GREEN;
		}
		return colRV;
	}

}