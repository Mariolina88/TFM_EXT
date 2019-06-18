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

import travelTimes.Model;
import travelTimes.SimpleModelFactory;



/**
 * The class TravelTimesPdf computes the  pdf of the travel times for each soil layer
 * 
 * INPUTS:
 * inputFlux : the flux in input to the layer
 * layerThickness the layer thinkness;
 * beta_linearModel,tau_paramVanGenuchten, n_paramVanGenuchten, saturated_waterContent, residual_waterContent: model params
 * waterContent: measured water content
 * model: the model chosen for the simulation of the hydraulic conductivity; 
 * simualtedConductivity: the simulated hydraulic conductivity
 * 
 * OUTPUT: 
 * mean: of the pdf
 * variance: of the pdf 
 * odfArrey: TT pdf
 *
 */
public class TravelTimesPdf {

	//INPUT
	public double inputFlux;
	public double layerThickness;
	public double beta_linearModel;
	public double tau_paramVanGenuchten;
	public double n_paramVanGenuchten;	
	public double saturated_waterContent;
	public double residual_waterContent;
	public String model;
	public ArrayList <Double> waterContent;
	public ArrayList <Double> simualtedConductivity;
	

	//OUTPUT
	public double mean;	
	public double variance;
	public ArrayList <Double> pdfArray= new ArrayList <Double>();
	
	Model trasferModel;

	public void process() throws Exception { 

		int lengthSeries=waterContent.size();
		

		for(int i=0;i<lengthSeries;i++){

			double teta=waterContent.get(i);
			double k=simualtedConductivity.get(i);


			trasferModel=SimpleModelFactory.createModel(model,(i+0.25), inputFlux, layerThickness, teta,beta_linearModel, saturated_waterContent,
					residual_waterContent,  n_paramVanGenuchten, tau_paramVanGenuchten);

			double pdf=k<inputFlux?trasferModel.densityFunction():0;


				pdfArray.add(pdf);
				
				mean=mean+(i+0.25)*pdf;
				
		}
		
		
		
		for(int i=0;i<lengthSeries;i++){
			

			variance=variance+Math.pow(((i+0.25)-mean),2)*pdfArray.get(i);
		}


		}

	}
