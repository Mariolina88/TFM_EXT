/*
 * Copyright (c) 2019 CNR ISAFOM. All rights reserved.
 * Do not remove these lines if you modify the code. Please communicate changes
 * to the address below.
 * If either part of this code is reused, or it is ported to other
 * languages/platforms, the origin must be cited and made visible to final users.
 *
 * For more information:
 * - marialaura.bancheri@cnr.isafom.it
 * - angelo.basile@cnr.it
 */

package monteCarlo;



import monteCarlo.ReaderConfiguration;

/** The main simulation class manages two simulations:
 - SimulationCurve class, which optimizes the curves of the unsaturated hydraulic conductivity
 - SimulationTFM, which applies the theory of the transfer model for the nitrate concentration
 Input of the class is the file with the configuration, where the paths to the inputs and outputs
 and the model params are defined.
*/

public class MainSimulation {

	public static void main(String[] args) throws Exception {
		
		
		ReaderConfiguration config =new ReaderConfiguration();
		config.setConfiguration("/Users/marialaura/Dropbox/Valle_telesina_LANDSUPPORT/TFM_test/MonteCarlo_VT/Configurazione.txt");
		
		// if the file with the params is not present in the specified folder
		// the SimulationCurve produces it in the case of the Beta_exponential curve model
		
		
			
		// if the file exists, the SimulationTFM class is launched  
		SimulationTFM simulationTFM = new 	SimulationTFM();	
		simulationTFM.trasferModel(
				config.getPathToOptimizedCurveParams(),
				config.getPathToPrecipitation(),
				config.pathToConcentration,
				config.getPathToOuput(),
				config.run,
				config.doConvol);
						

	}

}
