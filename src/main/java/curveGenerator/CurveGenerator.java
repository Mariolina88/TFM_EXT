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

import java.util.ArrayList;



/**
 * 
 * The class CurveGenerator generates the series of the unsaturated hydraulic conductivity
 * function of the water content, according to the model specified 
 * 
 * 
 * INPUT:
 * waterContent: measured water content;
 * model: the name of the model --> Beta or VG
 * beta_linearModel, saturated_waterContent, residual_waterContent, saturated_conductivity,
 * n_paramVanGenuchten, tau_paramVanGenuchten: parameters of the models
 * 
 * OUTPUT:
 * simualtedConductivity: simulated hydraulic conductivity
 * 
 *
 */
public class CurveGenerator {
	
	//INPUT
	public ArrayList <Double> waterContent;
	public String model;
	public double beta_linearModel;
	public double saturated_waterContent;
	public double residual_waterContent;
	public double saturated_conductivity;
	public double n_paramVanGenuchten;
	public double tau_paramVanGenuchten;
	public double inputFlux;
	
	//OUTPUT
	public ArrayList <Double> simualtedConductivity= new ArrayList <Double>();
	public double mean_velocity;
	
	Model curveModel;
	
	
	
	public void process() throws Exception { 
		
		int lengthSeries=waterContent.size();
	
		
		for(int i=0;i<lengthSeries;i++){
		
		double teta=waterContent.get(i);
		
		//calculate the simualted conductivity according to the user defined model, more models coul be implemented
		curveModel=SimpleModelFactory.createModel(model,teta,beta_linearModel, saturated_waterContent,residual_waterContent, 
				saturated_conductivity,  n_paramVanGenuchten, tau_paramVanGenuchten,inputFlux);
		
		double conductivity=curveModel.conductivity();
		
		
		
		

		simualtedConductivity.add(conductivity);
		
		}
		

		//mean_velocity=inputFlux/curveModel.theta_i();
		
		
	}
	


}
