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
 * The Class ReaderInputData reads the climatic input files extracting the data of interests.
 * INPUTS:
 * pathToData: Path to the file with the data
 * csvSplitBy: defines the separator in the  csv file. Comma is set as default
 * 
 * OUTPUTS:
 * Arraylist with:
 * - precipitation
 * - date 
 */
public class ReaderInputClimate {

	//INPUT
	public String pathToData;	
	public String csvSplitBy=",";
	
	//OUTPUT
	public ArrayList <Double> precipitation= new ArrayList <Double>();	
	public ArrayList <String> date= new ArrayList <String>();

	private String line = "";
	


	/**
	 * Process.
	 *
	 * @throws Exception the exception
	 */
	public void process() throws Exception { 
		
		//TODO add no value, no file and other exceptions
		
		BufferedReader br = null;

		br = new BufferedReader(new FileReader(pathToData));
		int i=0;
		
		while ((line = br.readLine()) != null) {
			

			if(i>0){

			date.add(line.split(csvSplitBy)[0]);
			precipitation.add(Double.parseDouble(line.split(csvSplitBy)[1]));
				
			}
		
			
			i++;		

		}

		br.close();




	}
	

	



}
