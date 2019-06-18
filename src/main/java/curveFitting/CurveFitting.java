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


package curveFitting;

import java.util.ArrayList;

import org.apache.commons.math3.fitting.WeightedObservedPoint;

/**
 * The Class CurveFitting fits the exponential model to the data, giving in output
 * the 3 params: beta, the saturated_conductivity, the saturated_waterContent.
 * 
 * INPUTS:
 * water content measured 
 * conductivity: measured hydraulic conductivity
 * initialParams: initial params guess
 * 
 * OUTPUTS:
 *  beta_exponentialModel
 *  saturated_conductivity_exponentialModel
 *  saturated_waterContent_exponentialModel
 *  
 */
public class CurveFitting {


	//INPUT
	public ArrayList <Double> waterContent;
	public ArrayList <Double> conductivity;	
	public double [] initialParams= new double [3];

	//OUTPUT
	public double beta_exponentialModel;
	public double saturated_conductivity_exponentialModel;
	public double saturated_waterContent_exponentialModel;


	
	public void process() throws Exception { 


		FunctionFitter fitter = new FunctionFitter();
		fitter.initialGuess=initialParams;


		ArrayList<WeightedObservedPoint> points = new ArrayList<WeightedObservedPoint>();


		for(int i=0;i<waterContent.size();i++){
			WeightedObservedPoint point = new WeightedObservedPoint(
					1,waterContent.get(i),Math.log(conductivity.get(i)));
			points.add(point);
		}


		final double coeffs[] = fitter.fit(points);

		System.out.println("K0= "+coeffs[0]+"  beta= "+coeffs[1]+"  teta0= "+coeffs[2]);

		saturated_conductivity_exponentialModel=coeffs[0];
		beta_exponentialModel=coeffs[1];
		saturated_waterContent_exponentialModel=coeffs[2];


	}



}

