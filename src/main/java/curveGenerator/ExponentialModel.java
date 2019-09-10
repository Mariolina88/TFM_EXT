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

public class ExponentialModel implements Model{

	double beta;
	double teta;
	double teta_s;
	double k0;
	double inputFlux;
	
	


	public ExponentialModel(double waterContent, double saturated_waterContent, double saturated_conductivity,
			double beta_linearModel, double inputFlux){

		this.teta=waterContent;
		this.beta=beta_linearModel;
		this.teta_s=saturated_waterContent;
		this.k0=saturated_conductivity;
		this.inputFlux=inputFlux;

	}



	public double conductivity() {
		// TODO Auto-generated method stub
		
		double k_teta=k0*Math.exp((beta*(teta-teta_s)));
		
		return k_teta;
	}


	public double theta_i() {



		UnivariateFunction f = new UnivariateFunction() {
			public double value(double x) {

				return k0*Math.exp((beta*(x-teta_s)))-inputFlux;
			}
		};

		UnivariateSolver solver = new BrentSolver();
		double theta_i=0;
		
		try { solver.solve(100, f, 0, teta_s); 
		
		theta_i=solver.solve(100, f, 0 , teta_s);
		}
	    catch (Exception e) { 
		  System.out.println("theta_i set equal to theta_r");
		}

	
		return theta_i;

	}


}
