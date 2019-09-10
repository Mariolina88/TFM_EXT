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
public class TravelTimesPdfVG {

	// INPUT
	public double inputFlux;
	public double layerThickness;
	public double tau_VG;
	public double n_VG;
	public double saturated_waterContent_VG;
	public double residual_waterContent_VG;
	public double saturated_conductivity_VG;
	public double mean_velocity;

	public ArrayList<Double> waterContent;
	public ArrayList<Double> simualtedConductivity;

	// OUTPUT
	public double mean;
	public double variance;
	public ArrayList<Double> pdfArray = new ArrayList<Double>();
	public ArrayList<Double> timeArray = new ArrayList<Double>();
	public boolean warning_mean = false;


	public void process() throws Exception {

		int lengthSeries = waterContent.size();

		double t_i = 0;
		double cdf_i = 0;
		double vm_i = 0;
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

			//double vm = computeVm(t, teta);

			if (cdf < 0.98 & k < inputFlux) {

				m0 = (1 - (cdf_i + cdf) / 2) * (t - t_i) + m0;
				m1 = (t + t_i) / 2 * (1 - (cdf_i + cdf) / 2) * (t - t_i) + m1;

			}

			t_i = t;
			k_i = k;
			cdf_i = cdf;
			//vm_i = vm;

		}

		mean = m1 / m0;

		if (Double.isNaN(mean)) {
			warning_mean = true;
			mean=0;
		}

		t_i = 0;
		cdf_i = 0;
		k_i = 0;
		vm_i = 0;

		for (int i = 0; i < lengthSeries; i++) {

			double teta = waterContent.get(i);
			double k = simualtedConductivity.get(i);

			double t = k < inputFlux ? computeTime(teta) : 0;
			double cdf = k < inputFlux ? computeCdf(k) : 0;
			//double vm = computeVm(t, teta);

			if (cdf < 0.98 & k < inputFlux) {

				m2 = Math.pow((t + t_i) / 2 - mean, 2) * (1 - (cdf_i + cdf) / 2) * (t - t_i) + m2;

			}

			t_i = t;
			cdf_i = cdf;
			k_i = k;
			//vm_i = vm;

		}

		variance = m2 / m0;
		
		if (Double.isNaN(variance)) {
			variance=0;
		}
	}

	public double computeTime(double teta) {
		// TODO Auto-generated method stub

		double Se = (teta - residual_waterContent_VG) / (saturated_waterContent_VG - residual_waterContent_VG);

		double m = 1 - 1 / n_VG;

		double A = 1 - Math.pow(Se, 1 / m);

		double t = layerThickness / (saturated_conductivity_VG * Math.pow(Se, tau_VG - 1) * (1 - Math.pow(A, m))
				* (tau_VG + 2 * Math.pow(Se, 1 / m) * Math.pow(A, m - 1) - tau_VG * Math.pow(A, m))
				/ ((saturated_waterContent_VG - residual_waterContent_VG)));

		return t;
	}

	public double computePdf(double time, double teta) {
		// TODO Auto-generated method stub

		double Se = (teta - residual_waterContent_VG) / (saturated_waterContent_VG - residual_waterContent_VG);

		double m = 1 - 1 / n_VG;

		double A = 1 - Math.pow(Se, 1 / m);

		double f = (1 - Math.pow(A, m)) / inputFlux * layerThickness
				/ ((tau_VG + 2 * Math.pow(Se, 1 / m) * Math.pow(A, m - 1) - tau_VG * Math.pow(A, m))
						/ (Se * (saturated_waterContent_VG - residual_waterContent_VG)))
				* 1 / Math.pow(time, 2);
		return f;
	}

/*	public double computeVm(double time, double teta) {
		// TODO Auto-generated method stub

		double Se = (teta - residual_waterContent_VG) / (saturated_waterContent_VG - residual_waterContent_VG);

		double m = 1 - 1 / n_VG;

		double A = 1 - Math.pow(Se, 1 / m);

		double vm = saturated_conductivity_VG * Math.pow(Se, tau_VG - 1) * (1 - Math.pow(A, m))
				* (tau_VG + 2 * Math.pow(Se, 1 / m) * Math.pow(A, m - 1) - tau_VG * Math.pow(A, m))
				/ ((saturated_waterContent_VG - residual_waterContent_VG));
		return vm;
	}*/

	public double computeCdf(double simualtedConductivity) {
		// TODO Auto-generated method stub

		double Cf = 1 - simualtedConductivity / inputFlux;
		return Cf;
	}

}
