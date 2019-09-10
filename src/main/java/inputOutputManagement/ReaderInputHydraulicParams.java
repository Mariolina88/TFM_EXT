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

import java.util.ArrayList;
import java.io.*;


/**
 * The Class ReaderInputData reads the input files extracting the data of interests.
 * INPUTS:
 * pathToData: Path to the file with the params
 * csvSplitBy: defines the separator in the  csv file. Comma is set as default
 * 
 * OUTPUTS:
 * Arraylist with:
 * 
 * thickness of each layer
 * 
 * In case of Beta model:
 * beta_exponentialModel
 * saturated_conductivity_exponentialModel
 * saturated_waterContent_exponentialModel
 * 
 * In case of VG model:
 * saturated_waterContent_VG
 * residual_waterContent_VG
 * saturated_conductivity_VG
 * n_VG
 * tau_VG
 */
public class ReaderInputHydraulicParams {

	//INPUT
	public String pathToData;
	public String csvSplitBy=",";
	public boolean Beta=true;
	
	//OUTPUT
	public ArrayList <Double> thickness= new ArrayList <Double>();

	public ArrayList <Double> beta_exponentialModel= new ArrayList <Double>();
	public ArrayList <Double> saturated_conductivity_exponentialModel= new ArrayList <Double>();
	public ArrayList <Double> saturated_waterContent_exponentialModel= new ArrayList <Double>();

	public ArrayList <Double> saturated_waterContent_VG= new ArrayList <Double>();
	public ArrayList <Double> residual_waterContent_VG= new ArrayList <Double>();
	public ArrayList <Double> saturated_conductivity_VG= new ArrayList <Double>();
	public ArrayList <Double> n_VG= new ArrayList <Double>();
	public ArrayList <Double> tau_VG= new ArrayList <Double>();
	public ArrayList <Double> alpha_VG= new ArrayList <Double>();


	private String line = "";



	public void process() throws Exception { 



		// reads the data and extracts the values of the 
		// variables of interest (i.e. water content, potential and hydraulic conductivity)

		BufferedReader br = null;

		br = new BufferedReader(new FileReader(pathToData));
		int i=0;

		while ((line = br.readLine()) != null) {

			// first line is the header
			if(i>0&line != ""){

				thickness.add(Double.parseDouble(line.split(csvSplitBy)[1].split("-")[1])-
						Double.parseDouble(line.split(csvSplitBy)[1].split("-")[0]));

				if(pathToData.contains("Beta")){

					beta_exponentialModel.add(Double.parseDouble(line.split(csvSplitBy)[2]));
					saturated_conductivity_exponentialModel.add(Double.parseDouble(line.split(csvSplitBy)[3]));
					saturated_waterContent_exponentialModel.add(Double.parseDouble(line.split(csvSplitBy)[4]));



				} else if (pathToData.contains("VG")){


					n_VG.add(Double.parseDouble(line.split(csvSplitBy)[2]));
					tau_VG.add(Double.parseDouble(line.split(csvSplitBy)[3]));
					saturated_conductivity_VG.add(Double.parseDouble(line.split(csvSplitBy)[4]));
					saturated_waterContent_VG.add(Double.parseDouble(line.split(csvSplitBy)[5]));
					residual_waterContent_VG.add(Double.parseDouble(line.split(csvSplitBy)[6]));
					alpha_VG.add(Double.parseDouble(line.split(csvSplitBy)[7]));

					Beta=false;
				}




			}


			i++;		

		}

		br.close();




	}






}
