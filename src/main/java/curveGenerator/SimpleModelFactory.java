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

package curveGenerator;

public class SimpleModelFactory {


	public static Model createModel(String type,double waterContent,double beta_linearModel, 
			double saturated_waterContent,double residual_waterContent, double saturated_conductivity, double n_paramVanGenuchten, 
			double tau_paramVanGenuchten, double inputFlux){
		Model model=null;


		if (type.equals("Beta")){
			model=new ExponentialModel(waterContent, saturated_waterContent,saturated_conductivity,beta_linearModel, inputFlux);


		}else if (type.equals("VG")){
			model=new VGmodel( waterContent, residual_waterContent,saturated_waterContent,saturated_conductivity,
					  n_paramVanGenuchten, tau_paramVanGenuchten, inputFlux);


		}
		return model;

	}

}
