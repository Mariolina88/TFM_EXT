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

package convolution;

import java.util.ArrayList;

import org.apache.commons.math3.util.MathArrays;

/**
 * 
 * 
 * The class Convolution convolves the two signals (TT pdfs and input
 * concentration) to obtain the output concentration
 * 
 * INPUTS: TTpdf: the travel time pdf inputConcentration: the input measured
 * concentration
 * 
 * OUTPUTS: outputConcentration: the output simulated concentration
 * 
 *
 */
public class Convolution {

	// INPUT
	public ArrayList<Double> TTpdf;
	public ArrayList<Double> inputConcentration;
	public double lambda;

	// OUPUT
	public double[] outputConcentrationV;
	public ArrayList<Double> outputConcentration = new ArrayList<Double>();
	public double mean;
	public double variance;
	public double mass_recovered;

	public void convolution() throws Exception {

		int dim = TTpdf.size();

		outputConcentrationV = new double[dim];

		outputConcentrationV = MathArrays.convolve(toArray(TTpdf), toArray(inputConcentration));

		double OUTmass = 0;
	    double totOUTmass = 0;
		double INmass = 0;

		double t_i = 0;
		double cdf_i = 0;
		double m0 = 0;
		double m1 = 0;
		double m2 = 0;
        double j_i = 0;
        int t_react = 0;
        double c_react = 0;

		for (int j = 0; j < inputConcentration.size(); j++) {

			INmass = INmass + inputConcentration.get(j);
			
            // compute the time of the first injection
            if (j_i == 0 & INmass > 0) {
                j_i = j;
            }
		}

		
        for (int j = 0; j < outputConcentrationV.length; j++) {

            // compute the total output mass without the term of reactive decay
            totOUTmass = totOUTmass + outputConcentrationV[j];

            if (totOUTmass / INmass < 0.98) {

                //store the information on the time relative to the 98%
                t_react = j;

                // consider the decay and compute the new output concentration vector
                outputConcentrationV[j] = outputConcentrationV[j] - outputConcentrationV[j] * lambda;

                // compute the new total output mass after the deacy 
                c_react = c_react + outputConcentrationV[j];

            }

        }
		
        // add the remaining 2% that was previously cut to the 98%
        c_react = c_react + 0.02 * c_react;
		
		
		
		for (int j = 0; j < outputConcentrationV.length; j++) {

            // compute the actual output concentration, after the decay
            double out_c = outputConcentrationV[j] - lambda * outputConcentrationV[j];

            // compute the actual output mass after the decay
            OUTmass = OUTmass + out_c;
			
			if (j <= t_react) {
				outputConcentration.add(out_c);

				m0 = (c_react - (cdf_i + out_c) / 2) * (j - t_i) + m0;
                m1 = (j + t_i) / 2 * (c_react - (cdf_i + out_c) / 2) * (j - t_i) + m1;
				
				
				mass_recovered=OUTmass / INmass;
			}

			t_i = j;
			cdf_i = out_c;
			
		}

		mean = m1 / m0;
		
		OUTmass = 0;
		t_i=0;
		cdf_i=0;

		for (int j = 0; j < outputConcentrationV.length; j++) {

			OUTmass = OUTmass + outputConcentrationV[j];

			if (j <= t_react) {

				 m2 = Math.pow((j + t_i) / 2 - mean, 2) * (c_react - (cdf_i + outputConcentrationV[j]) / 2) * (j - t_i) + m2;

			}

			t_i = j;
			cdf_i = outputConcentrationV[j];
		}

		variance = m2 / m0;

	}
	
	

	private double[] toArray(ArrayList<Double> arrayList) {

		double[] tempArray = new double[arrayList.size()];

		for (int i = 0; i < arrayList.size(); i++) {
			tempArray[i] = (double) arrayList.get(i);

		}
		return tempArray;
	}

}
