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

package pedoTransfer;



public class MainSimulationPTF {

	public static void main(String[] args) throws Exception {
		
		
	    String pathToShape="";	
		String P_code_id="";
		String depth_id="";
		String layer_number_id="";
		String clay_id="";
		String silt_id="";
		String sand_id="";
		String OM_id="";
		String bulk_density_id="";;
		String pathToOutput="";



		for ( int i = 0; i < args.length; i++ ){
			pathToShape=args[0];
			P_code_id=args[1];
			depth_id=args[2];
			layer_number_id=args[3];
			clay_id= args[4];
			silt_id=args[5];
			sand_id=args[6];
			OM_id=args[7];
			bulk_density_id=args[8];
			pathToOutput=args[9];
		}


		PTF ptf =new PTF();
		ptf.pathToShape=pathToShape;	
		ptf.P_code_id=P_code_id;
		ptf.depth_id=depth_id;
		ptf.layer_number_id=layer_number_id;
		ptf.clay_id=clay_id;
		ptf.silt_id=silt_id;
		ptf.sand_id=sand_id;
		ptf.OM_id=OM_id;
		ptf.bulk_density_id=bulk_density_id;
		ptf.pathToOutput=pathToOutput;
		ptf.process();

		

	}
}
