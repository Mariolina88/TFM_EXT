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


public class VGmodel implements Model{

	double teta;
	double teta_s;
	double teta_r;
	double n;
	double tau;	
	double layerThickness;
	double time;
	double flux;




	public VGmodel(double time, double flux, double layerThickness, double waterContent, 
			double residual_waterContent,double saturated_waterContent,
			 double n_paramVanGenuchten,double tau_paramVanGenuchten){

		this.teta=waterContent;
		this.teta_s=saturated_waterContent;
		this.teta_r=residual_waterContent;
		this.n=n_paramVanGenuchten;
		this.tau=tau_paramVanGenuchten;
		this.time=time;
		this.layerThickness=layerThickness;
		this.flux=flux;

		
		

	}



	public double densityFunction() {
		// TODO Auto-generated method stub
		
		double Se=(teta-teta_r)/(teta_s-teta_r);
		
		double m=1-1/n;
		
		double A=1-Math.pow(Se,1/m);
		
		double f=(1-Math.pow(A, m))/flux*
				layerThickness/((tau+2*Math.pow(Se, 1/m)*Math.pow(A, m-1)-tau*Math.pow(A, m))/(Se*(teta_s-teta_r)))
				*1/Math.pow(time, 2);		
		return f;
	}

}
