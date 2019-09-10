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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * The class ReaderConfiguration reads from the txt file the path to the files containing:
 * pathToFolderToHydraulicData: file with the measured K, theta, h;
 * pathToOptimizedCurveParams: file with optimized values of the unsaturated conductivity curve models;
 * pathToPrecipitation: path to the climatic data;
 * profile_code: the code of the soil profile;
 * pathToOutput: path to the output file;
 * waterTableDepth: the value of the water table depth;
 * lambda_1, lambda_2 are the params of the GTFM;
 * doOptimization: is true in the case of the Beta params file missing.
 * 
 */
public class ReaderConfiguration {

	//OUTPUT
	String pathToFolderToHydraulicData;
	String pathToOptimizedCurveParams;
	String pathToPrecipitation;
	String profile_code;
	String pathToOutput;
	String pathToConcetration;
	double waterTableDepth;
	double lambda_1;
	double lambda_2;
	boolean doOptimization=false;
	boolean independence;


	public void setConfiguration (String pathToConfig) throws IOException{

		String line = "";
		BufferedReader br = new BufferedReader(new FileReader(pathToConfig));
		int i=0;

		while ((line = br.readLine()) != null) {

			if(i==0){
				
				pathToFolderToHydraulicData=line.split("\t")[1];
				
			} else if(i==1){
				
				profile_code=line.split("\t")[1];
				
			}else if(i==2){
				
				pathToOptimizedCurveParams=line.split("\t")[1];
				
				// if the file doesn't exist and the model is the beta exponential
				// optimization is true
				// else the user must provide the file with VG params
				
				File f = new File(pathToOptimizedCurveParams);
				if(!f.isFile() & pathToOptimizedCurveParams.contains("Beta")) { 
					doOptimization=true;
					System.out.println("The file with parameters doesn't exist. Calibration is going to be done.");
				} else if(!f.isFile() & pathToOptimizedCurveParams.contains("VG")){
					System.out.println("The file with parameters doesn't exist. Please provide it!");
					System.exit(1);
				}

			}else if(i==3){
				
				pathToPrecipitation=line.split("\t")[1];

			} else if(i==4){
				
				waterTableDepth=Double.parseDouble(line.split("\t")[1]);
				
			}else if(i==5){
				
				pathToConcetration=line.split("\t")[1];
				
			}else if(i==6){
				
				lambda_1=Double.parseDouble(line.split("\t")[1]);
				
			}else if(i==7){
				
				lambda_2=Double.parseDouble(line.split("\t")[1]);
			}else if(i==8){
				
				if(line.contains("true")){
				independence=true;}
				
			}else if(i==9){
				pathToOutput=line.split("\t")[1];
			}
			
			

			i++;

		}

		br.close();

	}

	
	public boolean doOptimization(){
		return doOptimization;
	
	}
	
	public String getPathToFolderToHydraulicData(){
		return pathToFolderToHydraulicData;
	
	}
	
	
	public String getProfileCode(){
		return profile_code;
	
	}
	
	public String getPathToOptimizedCurveParams(){
		return pathToOptimizedCurveParams;
		
	}
	
	public String getPathToPrecipitation(){
		return pathToPrecipitation;
		
	}
	
	
	public String getPathToConcentration(){
		return pathToConcetration;
		
	}
	
	public double getWaterTableDepth(){
		return waterTableDepth;
		
	}
	
	public double getLambda_1(){
		return lambda_1;		
	}

	
	public double getLambda_2(){
		return lambda_2;		
	}
	
	public boolean isIndependent(){
		return independence;
	
	}
	
	public String getPathToOuput(){
		return pathToOutput;		
	}
}
