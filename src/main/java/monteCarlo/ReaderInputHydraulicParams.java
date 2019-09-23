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


package monteCarlo;

import java.io.*;



public class ReaderInputHydraulicParams {

	//INPUT
	public String pathToData;
	public String csvSplitBy=",";
	public int j;


	public double saturated_waterContent_VG=0;
	public double residual_waterContent_VG=0;
	public double saturated_conductivity_VG=0;
	public double n_VG=0;
	public double tau_VG=0;
	public double alpha_VG=0;


	private String line = "";



	public void process() throws Exception { 




		// reads the data and extracts the values of the 
		// variables of interest (i.e. water content, potential and hydraulic conductivity)

		BufferedReader br = null;

		br = new BufferedReader(new FileReader(pathToData));
		int i=0;

		while ((line = br.readLine()) != null) {
			


			// first line is the header
			if(i==j+1){
			
				residual_waterContent_VG=Double.parseDouble(line.split(csvSplitBy)[0]);
				residual_waterContent_VG= residual_waterContent_VG<0?0:residual_waterContent_VG;
				residual_waterContent_VG= residual_waterContent_VG>0.10?0.10:residual_waterContent_VG;
				
				saturated_waterContent_VG=Double.parseDouble(line.split(csvSplitBy)[1]);
				saturated_waterContent_VG= saturated_waterContent_VG<0.2?0.3:saturated_waterContent_VG;
				saturated_waterContent_VG= saturated_waterContent_VG>0.75?0.75:saturated_waterContent_VG;
				
				alpha_VG=Double.parseDouble(line.split(csvSplitBy)[2]);
				alpha_VG=alpha_VG<0.01?0.02:alpha_VG;
				alpha_VG=alpha_VG>0.5?0.5:alpha_VG;
				
								
				n_VG=Double.parseDouble(line.split(csvSplitBy)[3]);
				n_VG=n_VG<1.1?1.3:n_VG;
				n_VG=n_VG>3?3:n_VG;
				
				
				saturated_conductivity_VG=Double.parseDouble(line.split(csvSplitBy)[4]);
				saturated_conductivity_VG=saturated_conductivity_VG<1?20:saturated_conductivity_VG;
				saturated_conductivity_VG=saturated_conductivity_VG>1000?1000:saturated_conductivity_VG;
				
				
				tau_VG=Double.parseDouble(line.split(csvSplitBy)[5]);
				tau_VG=tau_VG<-30?-30:tau_VG;
				tau_VG=tau_VG>30?30:tau_VG;
			}


			i++;

		}

		br.close();


	}

}







