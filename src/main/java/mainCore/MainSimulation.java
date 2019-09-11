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

package mainCore;




/** The main simulation class manages two simulations:
 - SimulationCurve class, which optimizes the curves of the unsaturated hydraulic conductivity
 - SimulationTFM, which applies the theory of the transfer model for the nitrate concentration
 Input of the class is the file with the configuration, where the paths to the inputs and outputs
 and the model params are defined.
*/

public class MainSimulation {

	public static void main(String[] args) throws Exception {
		
		long startTime = System.nanoTime();


		
		String pathToShape="";
		String codeProfile="";
		String pathToConcetrationIn="";
		double netPrecip=0;
		double waterTableDepth=150;
		double lambda1=1;
		double lambda2=1;
		boolean doOptimization=false;
		boolean layerIndependence=true;
		String model="";
		
		
		for ( int i = 0; i < args.length; i++ ){
			pathToShape=args[0];
			codeProfile=args[1];
			pathToConcetrationIn=args[2];
			netPrecip= Double.parseDouble(args[3]);
			waterTableDepth=Double.parseDouble(args[4]);
			lambda1=Double.parseDouble(args[5]);
			lambda2=Double.parseDouble(args[6]);
			doOptimization=Boolean.parseBoolean(args[7]);
			layerIndependence=Boolean.parseBoolean(args[8]);
			model=args[9];
		}
		
		
		// if the file with the params is not present in the specified folder
		// the SimulationCurve produces it in the case of the Beta_exponential curve model
		
		if(doOptimization){
			
			SimulationCurve simulationCurve = new 	SimulationCurve();				
			simulationCurve.curveGeneration(codeProfile,pathToShape);
					
			
		} else {

		}
			
		// if the file exists, the SimulationTFM class is launched  
		SimulationTFM simulationTFM = new 	SimulationTFM();	
		simulationTFM.trasferModel(
				pathToShape,
				codeProfile,
				netPrecip, 
				waterTableDepth, 
				pathToConcetrationIn,
				lambda1, 
				lambda2,
				layerIndependence,
				model); 
				
		long endTime   = System.nanoTime();
		long totalTime = endTime - startTime;
		System.out.println(totalTime/1000000000);	

	}

}
