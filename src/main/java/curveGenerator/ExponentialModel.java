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


public class ExponentialModel implements Model{

	double beta;
	double teta;
	double teta_s;
	double k0;
	
	


	public ExponentialModel(double waterContent, double saturated_waterContent, double saturated_conductivity,double beta_linearModel){

		this.teta=waterContent;
		this.beta=beta_linearModel;
		this.teta_s=saturated_waterContent;
		this.k0=saturated_conductivity;

	}



	public double conductivity() {
		// TODO Auto-generated method stub
		
		double k_teta=k0*Math.exp((beta*(teta-teta_s)));
		
		return k_teta;
	}

}
