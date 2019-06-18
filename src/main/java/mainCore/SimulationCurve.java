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

package mainCore;


import java.util.ArrayList;

import curveFitting.CurveFitting;
import inputOutputManagement.FileList;
import inputOutputManagement.ReaderInputHydraulicData;
import inputOutputManagement.WriterParamsCurve;


/**
 * The Class MainSimulationCurve optimizes the params of the unsaturated conductivity curves
 * in the case of the Beta exponential model.
 */
public class SimulationCurve {


	public void curveGeneration(String path, String profileCode) throws Exception {
		
		
		// from the specified folder, extracts the list of the files to process
		FileList list=new FileList();
		list.pathToInputFolder=path;	
		list.profile_code=profileCode;
		list.process();

		// the number of the layers associated to the profile
		// is equal to the number of the files in the folder with a specified name
		int layers=list.list.length;	
		Object[] fileList=list.list;
		
		ArrayList <Double> beta_exponentialModel=new ArrayList <Double>();
		ArrayList <Double> saturated_conductivity_exponentialModel=new ArrayList <Double>();
		ArrayList <Double> saturated_waterContent_exponentialModel=new ArrayList <Double>();
		ArrayList <String> depth=new ArrayList <String>();
		ArrayList <String> profile_code=new ArrayList <String>();
		

		
		// for each layer, the curves k(theta) are extracted 
		for(int i=0;i<layers;i++){
			
			
		//read the data from the files
		ReaderInputHydraulicData reader=new ReaderInputHydraulicData();
		reader.pathToData=fileList[i].toString();	
		reader.process();
		
		// initial params guess
		double [] params={0.2,10,0.5};
		
		CurveFitting cf=new CurveFitting ();
		cf.conductivity=reader.conductivity;
		cf.waterContent=reader.waterContent;
		cf.initialParams=params;
		
		cf.process();
		
		//population of the array list with the optimized params
		beta_exponentialModel.add(cf.beta_exponentialModel);
		saturated_conductivity_exponentialModel.add(cf.saturated_conductivity_exponentialModel);
		saturated_waterContent_exponentialModel.add(cf.saturated_waterContent_exponentialModel);
		depth.add(reader.depth);
		profile_code.add(reader.profile_code);
			
		}
		
		//writer of the file with the optimized params
		WriterParamsCurve writer =new WriterParamsCurve();
		
		writer.beta_exponentialModel=beta_exponentialModel;
		writer.depth=depth;
		writer.saturated_conductivity_exponentialModel=saturated_conductivity_exponentialModel;
		writer.saturated_waterContent_exponentialModel=saturated_waterContent_exponentialModel;
		writer.pathToData=path;
		writer.profile_code=profile_code;
		writer.process();


	}

}
