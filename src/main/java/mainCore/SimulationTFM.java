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


import curveGenerator.CurveGenerator;
import generalizedTransferFunctionModel.GTFmodel;
import generalizedTransferFunctionModel.GeneralMoments;
import inputOutputManagement.FileList;
import inputOutputManagement.ReaderInputClimate;
import inputOutputManagement.ReaderInputHydraulicData;
import inputOutputManagement.ReaderInputHydraulicParams;
import inputOutputManagement.WriterOUTttpdf;
import travelTimes.TravelTimesPdf;

public class SimulationTFM {

	public void trasferModel (String path, String profile_code,String pathToParams, String pathToClimate,
			double waterTableDepth,double lambda_1, double lambda_2, String pathToOutput) throws Exception {








		// from the specified folder, extracts the list of the files to process
		FileList list=new FileList();
		list.pathToInputFolder=path;
		list.profile_code=profile_code;
		list.process();

		// the number of the layers associated to the profile
		// is equal to the number of the files in the folder with a specified name

		Object[] fileList=list.list;



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
		
		// for each date--> value od precipitation 
		// computes the TT pdfs, doeas the convulution and
		// writes the output
		
		for(int i=0;i<readerPrecip.precipitation.size();i++){
			
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


				//read the data from the files
				ReaderInputHydraulicData reader=new ReaderInputHydraulicData();
				reader.pathToData=fileList[j].toString();	
				reader.process();
				


				
				CurveGenerator curve=new CurveGenerator();
				TravelTimesPdf tf=new TravelTimesPdf ();
				
				double thickness=readerInputsParams.thickness.get(j);

				// Beta exponential model curve
				if(readerInputsParams.Beta){


					double beta=readerInputsParams.beta_exponentialModel.get(j);
					double saturated_conductivity_exponentialModel=readerInputsParams.saturated_conductivity_exponentialModel.get(j);
					double saturated_waterContent_exponentialModel=readerInputsParams.saturated_waterContent_exponentialModel.get(j);


					curve.beta_linearModel=beta;
					curve.saturated_conductivity=saturated_conductivity_exponentialModel;
					curve.saturated_waterContent=saturated_waterContent_exponentialModel;
					curve.model="Beta";
					curve.waterContent=reader.waterContent;


					curve.process();


					tf.inputFlux=precipitation;
					tf.layerThickness=thickness;
					tf.beta_linearModel=beta;
					tf.saturated_waterContent=saturated_waterContent_exponentialModel;		
					tf.model="Beta";
					tf.waterContent=reader.waterContent;
					tf.simualtedConductivity=curve.simualtedConductivity;

					tf.process();


					// VG  model curve
				}else{




					double saturated_conductivity_VG=readerInputsParams.saturated_conductivity_VG.get(j);					
					double n_paramVanGenuchten=readerInputsParams.n_VG.get(j);
					double residual_waterContent=readerInputsParams.residual_waterContent_VG.get(j);
					double saturated_waterContent_VG=readerInputsParams.saturated_waterContent_VG.get(j);
					double tau_paramVanGenuchten=readerInputsParams.tau_VG.get(j);



					curve.saturated_conductivity=saturated_conductivity_VG;
					curve.n_paramVanGenuchten=n_paramVanGenuchten;
					curve.residual_waterContent=residual_waterContent;
					curve.saturated_waterContent=saturated_waterContent_VG;
					curve.tau_paramVanGenuchten=tau_paramVanGenuchten;
					curve.model="VG";
					curve.waterContent=reader.waterContent;


					curve.process();


					tf.inputFlux=precipitation;
					tf.layerThickness=thickness;			
					tf.saturated_waterContent=saturated_waterContent_VG;
					tf.residual_waterContent=residual_waterContent;
					tf.n_paramVanGenuchten=n_paramVanGenuchten;
					tf.tau_paramVanGenuchten=tau_paramVanGenuchten;
					tf.model="VG";
					tf.waterContent=reader.waterContent;
					tf.simualtedConductivity=curve.simualtedConductivity;

					tf.process();	

				}

				// compute the mean and variance 
				mean.add(tf.mean);
				variance.add(tf.variance);

				totalDepth=totalDepth+thickness;

			}

			// compute the mean and variance till a fixed known depth
			GeneralMoments gm = new GeneralMoments();

			gm.mean=mean;
			gm.variance=variance;
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
			
			//Convolution convolve=new Convolution();
			// ...
			//convolve.convolution();

			// write the output
			WriterOUTttpdf writer= new WriterOUTttpdf();
			
			writer.pathToOutput=pathToOutput;
			writer.writeHeader=writeHeader;
			writer.date=readerPrecip.date.get(i);
			writer.outputConcetration=gtf.pdfArray;
			writer.process();

		}





	}

}
