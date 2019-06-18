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

package travelTimes;

public class SimpleModelFactory {


	public static Model createModel(String type,double time, double flux, double layerThickness,
			double waterContent,double beta_linearModel, double saturated_waterContent,double residual_waterContent,
			double n_paramVanGenuchten, double tau_paramVanGenuchten){
		Model model=null;


		if (type.equals("Beta")){
			model=new ExponentialModel(time, flux, layerThickness, beta_linearModel);


		}else if (type.equals("VG")){
			model=new VGmodel( time, flux, layerThickness,waterContent, residual_waterContent,saturated_waterContent,
					  n_paramVanGenuchten, tau_paramVanGenuchten);


		}
		return model;

	}

}
