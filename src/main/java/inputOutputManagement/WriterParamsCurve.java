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


package inputOutputManagement;

import java.io.FileWriter;
import java.util.ArrayList;


/**
 * The  WriterParamsCurve creates a csv file with the optimezed params for 
 * the exponential model.
 * 
 * INPUTS:
 * 
 * pathToData: Path to folder containing the data.
 * depth: ArrayList with depth for all the layers.
 * profile_code: ArrayList with depth for all the layers.
 * beta_exponentialModel: ArrayList with the beta exponential model params for all the layers
 * saturated_conductivity_exponentialModel: ArrayList with saturated conductivity exponential model for all the layers.
 * saturated_waterContent_exponentialModel: ArrayList with saturated water content exponential model for all the layers. 
 * 
 */
public class WriterParamsCurve {

	//INPUT
	public ArrayList <String> depth;
	public ArrayList <String> profile_code;
	public String pathToData;
	public ArrayList <Double> beta_exponentialModel;
	public ArrayList <Double> saturated_conductivity_exponentialModel;
	public ArrayList <Double> saturated_waterContent_exponentialModel;




	public void process() throws Exception { 


		FileWriter csvWriter = new FileWriter(pathToData+"/Beta_params.csv");  

		int dim=depth.size();
		
		
		
		//header
		csvWriter.append("Profile_code,Depth,Beta_exponentialModel,Saturated_conductivity_exponentialModel,"
				+ "Saturated_waterContent_exponentialModel");	
		csvWriter.append('\n');



		for(int i=0;i<dim;i++){
			
			
			csvWriter.append(profile_code.get(i).toString());
			csvWriter.append(",");
			csvWriter.append(depth.get(i).toString());
			csvWriter.append(",");
			
			Double beta=Math.round(beta_exponentialModel.get(i)*100.0)/100.0;
			csvWriter.append(beta.toString());
			csvWriter.append(",");
			
			Double k0=Math.round(saturated_conductivity_exponentialModel.get(i)*100.0)/100.0;
			csvWriter.append(k0.toString());
			csvWriter.append(",");
			
			Double theta0=Math.round(saturated_waterContent_exponentialModel.get(i)*1000.0)/1000.0;
			csvWriter.append(theta0.toString());
			csvWriter.append('\n');

		}



		csvWriter.flush();  
		csvWriter.close(); 



	}




}
