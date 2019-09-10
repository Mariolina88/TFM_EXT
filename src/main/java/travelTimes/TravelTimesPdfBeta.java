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

import java.util.ArrayList;

/**
 * The class TravelTimesPdf computes the pdf of the travel times for each soil
 * layer
 * 
 * INPUTS: inputFlux : the flux in input to the layer layerThickness the layer
 * thinkness; beta_linearModel,tau_paramVanGenuchten, n_paramVanGenuchten,
 * saturated_waterContent, residual_waterContent: model params waterContent:
 * measured water content model: the model chosen for the simulation of the
 * hydraulic conductivity; simualtedConductivity: the simulated hydraulic
 * conductivity
 * 
 * OUTPUT: mean: of the pdf variance: of the pdf odfArrey: TT pdf
 *
 */
public class TravelTimesPdfBeta {

	// INPUT
	public double inputFlux;
	public double layerThickness;
	public double beta_exponentialModel;
	public double saturated_waterContent_exponentialModel;
	public double saturated_conductivity_exponentialModel;
	public double mean_velocity;
	public int n_i = 0;

	public ArrayList<Double> waterContent;
	public ArrayList<Double> simualtedConductivity;

	// OUTPUT
	public double mean;
	public double variance;
	public ArrayList<Double> pdfArray = new ArrayList<Double>();
	public ArrayList<Double> timeArray = new ArrayList<Double>();
	public boolean warning_mean = false;
	
	int n = 0;


	public void process() throws Exception {

		int lengthSeries = waterContent.size();

		double t_i = 0;
		double cdf_i = 0;
		double m0 = 0;
		double m1 = 0;
		double m2 = 0;
		double k_i = 0;

		for (int i = 0; i < lengthSeries; i++) {

			double teta = waterContent.get(i);
			double k = simualtedConductivity.get(i);

			double t = k < inputFlux ? computeTime(teta) : 0;

			double pdf = k < inputFlux ? computePdf(t, teta) : 0;

			double cdf = k < inputFlux ? computeCdf(k) : 0;

			pdfArray.add(pdf);
			timeArray.add(t);

			double vm = computeVm(teta);

			if (cdf < 0.98 & k_i < inputFlux) {

				m0 = (1 - (cdf_i + cdf) / 2) * (t - t_i) + m0;
				m1 = (t + t_i) / 2 * (1 - (cdf_i + cdf) / 2) * (t - t_i) + m1;

			}

			t_i = t;
			k_i = k;
			cdf_i = cdf;

		}

		mean = m1 / m0;
		

		if (Double.isNaN(mean)) {
			warning_mean = true;
			mean=0;
			n_i = n;
		}
		
		t_i = 0;
		cdf_i = 0;
		k_i = 0;

		for (int i = 0; i < lengthSeries; i++) {

			double teta = waterContent.get(i);
			double k = simualtedConductivity.get(i);

			double t = k < inputFlux ? computeTime(teta) : 0;
			double cdf = k < inputFlux ? computeCdf(k) : 0;
			double vm = computeVm(teta);

			if (cdf < 0.98 & k_i < inputFlux) {

				m2 = Math.pow((t + t_i) / 2 - mean, 2) * (1 - (cdf_i + cdf) / 2) * (t - t_i) + m2;

			}

			t_i = t;
			cdf_i = cdf;
			k_i = k;

		}

		variance = m2 / m0;
	}

	public double computeTime(double teta) {
		// TODO Auto-generated method stub

		double t = layerThickness / (beta_exponentialModel * saturated_conductivity_exponentialModel
				* Math.exp(beta_exponentialModel * (teta - saturated_waterContent_exponentialModel)));

		return t;
	}

	public double computePdf(double time, double teta) {
		// TODO Auto-generated method stub

		double f = 1 / inputFlux * layerThickness / beta_exponentialModel * 1 / Math.pow(time, 2);
		return f;
	}

	public double computeVm(double teta) {

		double vm = (beta_exponentialModel * saturated_conductivity_exponentialModel
				* Math.exp(beta_exponentialModel * (teta - saturated_waterContent_exponentialModel)));
		return vm;
	}

	public double computeCdf(double simualtedConductivity) {
		// TODO Auto-generated method stub

		double Cf = 1 - simualtedConductivity / inputFlux;
		return Cf;
	}

}
