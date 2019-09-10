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

import java.io.File;
import java.util.ArrayList;

import convolution.Convolution;
import curveGenerator.CurveGenerator;
import curveGenerator.WaterContentGenerator;
import generalizedTransferFunctionModel.GTFmodel;
import generalizedTransferFunctionModel.GeneralMoments;
import inputOutputManagement.FileList;
import inputOutputManagement.ReaderInputClimate;
import inputOutputManagement.ReaderInputConcentration;
import inputOutputManagement.ReaderInputHydraulicData;
import inputOutputManagement.ReaderInputHydraulicParams;
import inputOutputManagement.WriterOUTttpdf;
import travelTimes.TravelTimesPdfBeta;
import travelTimes.TravelTimesPdfVG;

public class SimulationTFM {

	public void trasferModel (String path, String profile_code,String pathToParams, String pathToClimate,
			double waterTableDepth,String pathToConcentration,
			double lambda_1, double lambda_2, boolean independence,
			String pathToOutput) throws Exception {




		// reads the params of the unsaturated hydraulic conductivity curve

		ReaderInputHydraulicParams readerInputsParams = new ReaderInputHydraulicParams();	
		readerInputsParams.pathToData=pathToParams;	
		readerInputsParams.process();

		// reads the climatic data
		// TODO: add the ET
		ReaderInputClimate readerPrecip =new ReaderInputClimate();
		readerPrecip.pathToData=pathToClimate;	
		readerPrecip.process();

		// if the file already exists, it is delated
		// in order to avoid to add results to the 
		// already existing file
		File file = new File(pathToOutput); 		
		if(file.exists()) file.delete();
		

		ArrayList <String> layer= new ArrayList <String>();
		boolean warning_mean=false;
		

		// for each date--> value of precipitation 
		// computes the TT pdfs, does the convulution and
		// writes the output

		//for(int i=0;i<readerPrecip.precipitation.size();i++){
		//****ATTENZIONE AL FOR, FORSE SI PUO' TOGLIERE
		for(int i=0;i<1;i++){

			boolean writeHeader=true;

			ArrayList <Double> mean=new ArrayList <Double>();
			ArrayList <Double> variance=new ArrayList <Double>();

			double precipitation=readerPrecip.precipitation.get(i);

			double totalDepth=0;

			// only when the file is empty adds the header
			if(i>0){

				writeHeader=false;
			}

			
			
			// for each layer computes the TTpdfs till the end of the known depth
			for(int j=0;j<readerInputsParams.thickness.size();j++){







				CurveGenerator curve=new CurveGenerator();
				TravelTimesPdfVG tf_VG=new TravelTimesPdfVG ();
				TravelTimesPdfBeta tf_Beta=new TravelTimesPdfBeta ();


				double thickness=readerInputsParams.thickness.get(j);

				// Beta exponential model curve
				if(readerInputsParams.Beta){

					// from the specified folder, extracts the list of the files to process
					FileList list=new FileList();
					list.pathToInputFolder=path;
					list.profile_code=profile_code;
					list.process();

					// the number of the layers associated to the profile
					// is equal to the number of the files in the folder with a specified name

					Object[] fileList=list.list;


					//read the data from the files
					ReaderInputHydraulicData reader=new ReaderInputHydraulicData();
					reader.pathToData=fileList[j].toString();	
					reader.process();


					double beta=readerInputsParams.beta_exponentialModel.get(j);
					double saturated_conductivity_exponentialModel=readerInputsParams.saturated_conductivity_exponentialModel.get(j);
					double saturated_waterContent_exponentialModel=readerInputsParams.saturated_waterContent_exponentialModel.get(j);


					curve.inputFlux=precipitation;
					curve.beta_linearModel=beta;
					curve.saturated_conductivity=saturated_conductivity_exponentialModel;
					curve.saturated_waterContent=saturated_waterContent_exponentialModel;
					curve.model="Beta";
					curve.waterContent=reader.waterContent;


					curve.process();


					tf_Beta.inputFlux=precipitation;
					tf_Beta.layerThickness=thickness;
					tf_Beta.beta_exponentialModel=beta;
					tf_Beta.mean_velocity=curve.mean_velocity;
					tf_Beta.saturated_waterContent_exponentialModel=saturated_waterContent_exponentialModel;
					tf_Beta.saturated_conductivity_exponentialModel=saturated_conductivity_exponentialModel;
					tf_Beta.waterContent=reader.waterContent;
					tf_Beta.waterContent=reader.waterContent;
					tf_Beta.simualtedConductivity=curve.simualtedConductivity;

					tf_Beta.process();

					warning_mean=tf_VG.warning_mean;

					

					// compute the mean and variance 
					mean.add(tf_Beta.mean);
					variance.add(tf_Beta.variance);


					// VG  model curve
				}else{




					double saturated_conductivity_VG=readerInputsParams.saturated_conductivity_VG.get(j);					
					double n_paramVanGenuchten=readerInputsParams.n_VG.get(j);
					double residual_waterContent=readerInputsParams.residual_waterContent_VG.get(j);
					double saturated_waterContent_VG=readerInputsParams.saturated_waterContent_VG.get(j);
					double tau_paramVanGenuchten=readerInputsParams.tau_VG.get(j);
					double alpha_paramVanGenuchten=readerInputsParams.alpha_VG.get(j);

					WaterContentGenerator wc= new WaterContentGenerator();

					wc.alfa_paramVanGenuchten=alpha_paramVanGenuchten;
					wc.n_paramVanGenuchten=n_paramVanGenuchten;
					wc.residual_waterContent=residual_waterContent;
					wc.saturated_waterContent=saturated_waterContent_VG;
					wc.process();


					curve.inputFlux=precipitation;
					curve.saturated_conductivity=saturated_conductivity_VG;
					curve.n_paramVanGenuchten=n_paramVanGenuchten;
					curve.residual_waterContent=residual_waterContent;
					curve.saturated_waterContent=saturated_waterContent_VG;
					curve.tau_paramVanGenuchten=tau_paramVanGenuchten;
					curve.model="VG";
					curve.waterContent=wc.simualtedTheta;


					curve.process();



					tf_VG.inputFlux=precipitation;
					tf_VG.layerThickness=thickness;			
					tf_VG.saturated_waterContent_VG=saturated_waterContent_VG;
					tf_VG.residual_waterContent_VG=residual_waterContent;
					tf_VG.n_VG=n_paramVanGenuchten;
					tf_VG.tau_VG=tau_paramVanGenuchten;
					tf_VG.waterContent=wc.simualtedTheta;
					tf_VG.saturated_conductivity_VG=saturated_conductivity_VG;
					tf_VG.simualtedConductivity=curve.simualtedConductivity;
					tf_VG.mean_velocity=curve.mean_velocity;

					tf_VG.process();
					
					warning_mean=tf_VG.warning_mean;

					// compute the mean and variance 
					mean.add(tf_VG.mean);
					variance.add(tf_VG.variance);
				}



				
				
				
				if(warning_mean==true){			
					thickness=0;
					layer.add("KO");
				}else{
					layer.add("OK");
				}
				totalDepth=totalDepth+thickness;
			}

			
			// compute the mean and variance till a fixed known depth
			GeneralMoments gm = new GeneralMoments();

			gm.mean=mean;
			gm.variance=variance;
			gm.independence=independence;
			gm.process();


			// GTF model till the water table depth
 			GTFmodel gtf =new GTFmodel();

			gtf.mean=gm.totalMean;
			gtf.variance=gm.totalVar;
			gtf.waterTableDepth=waterTableDepth;
			gtf.aboveLayersDepth=totalDepth;
			gtf.lambda_1=lambda_1;
			gtf.lambda_2=lambda_2;
			gtf.process();


			// convoluzione 
			// output 

			ReaderInputConcentration readerC=new ReaderInputConcentration();
			readerC.pathToData=pathToConcentration;	
			readerC.process();


			Convolution convolve=new Convolution();
			convolve.inputConcentration=readerC.concentration;
			convolve.TTpdf=gtf.pdfArray;
			convolve.convolution();

			// write the output
			WriterOUTttpdf writer= new WriterOUTttpdf();
			

			writer.warning_mean=warning_mean;
			writer.mass_recovered=convolve.mass_recovered;
			writer.layer=layer;
			writer.pathToOutput=pathToOutput;
			writer.writeHeader=writeHeader;
			writer.depth=waterTableDepth;
			writer.meanArrivalTime=convolve.mean;
			writer.date=readerPrecip.date.get(i);
			writer.outputConcetration=convolve.outputConcentration;
			writer.process();

		}





	}

}
