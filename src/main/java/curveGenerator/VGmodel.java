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

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.solvers.BrentSolver;
import org.apache.commons.math3.analysis.solvers.UnivariateSolver;

public class VGmodel implements Model{

	double teta;
	double teta_s;
	double teta_r;
	double k0;	
	double n;
	double tau;
	double inputFlux;







	public VGmodel(double waterContent, double residual_waterContent,double saturated_waterContent,double saturated_conductivity,
			double n_paramVanGenuchten,double tau_paramVanGenuchten, double inputFlux){

		this.teta=waterContent;
		this.teta_s=saturated_waterContent;
		this.teta_r=residual_waterContent;
		this.k0=saturated_conductivity;
		this.n=n_paramVanGenuchten;
		this.tau=tau_paramVanGenuchten;
		this.inputFlux=inputFlux;



	}



	public double conductivity() {
		// TODO Auto-generated method stub

		double Se=(teta-teta_r)/(teta_s-teta_r);

		double m=1-1/n;

		double k_teta=k0*Math.pow(Se, tau)*Math.pow(1-(Math.pow((1-Math.pow(Se,1/m)),m)),2);

		return k_teta;
	}


	public double theta_i() {


		double m=1-1/n;

		UnivariateFunction f = new UnivariateFunction() {
			public double value(double x) {

				return k0*Math.pow((x-teta_r)/(teta_s-teta_r), tau)
						*Math.pow(1-(Math.pow((1-Math.pow((x-teta_r)/(teta_s-teta_r),1/m)),m)),2)-inputFlux;
			}
		};

		UnivariateSolver solver = new BrentSolver();
		
		double theta_i=teta_s;
		
		try { solver.solve(100, f, teta_r, teta_s); 
		
		theta_i=solver.solve(100, f, teta_r, teta_s);
		}
	    catch (Exception e) { 
		  System.out.println("theta_i set equal to theta_s");
		}

	
		return theta_i;

	}

}
