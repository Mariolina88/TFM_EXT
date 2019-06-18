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
 * The Class ReaderInputData reads the input files extracting the measured data of interests.
 * 
 * INPUTS:
 * pathToData: Path to the file with the meaasured data 
 * - csvSplitBy defines the separator in the  csv file. Comma is set as default
 * 
 * OUTPUTS:
 * Arraylist with: 
 * 
 * - measured water content
 * - measured potential
 * - measured hydraulic conductivity
 * - the depth of each layer
 * - the profile code 
 */
public class ReaderInputHydraulicData {


	//INPUT
	public String pathToData;
	public String csvSplitBy=",";
	
	//OUTPUT
	public ArrayList <Double> waterContent= new ArrayList <Double>();
	public ArrayList <Double> potential= new ArrayList <Double>();
	public ArrayList <Double> conductivity= new ArrayList <Double>();
	public String depth;	
	public String profile_code;

	
	private String line = "";
	


	/**
	 * Process.
	 *
	 * @throws Exception the exception
	 */
	public void process() throws Exception { 
		

		
		// reads the data and extracts the values of the 
		// variables of interest (i.e. water content, potential and hydraulic conductivity)
		
		BufferedReader br = null;

		br = new BufferedReader(new FileReader(pathToData));
		int i=0;
		
		profile_code=pathToData.split("_")[2]+"_"+pathToData.split("_")[1];
		
		
		while ((line = br.readLine()) != null) {
			
			// first line is the depth of the layer
			if(line.contains("depth")){
				
				depth=line.split(":")[1];
			}

			// second line is the header
			if(i>=2){

			
			waterContent.add(Double.parseDouble(line.split(csvSplitBy)[0]));
			potential.add(Double.parseDouble(line.split(csvSplitBy)[1]));
			conductivity.add(Double.parseDouble(line.split(csvSplitBy)[2]));


				
			}
		
			
			i++;		

		}

		br.close();




	}
	

	



}
