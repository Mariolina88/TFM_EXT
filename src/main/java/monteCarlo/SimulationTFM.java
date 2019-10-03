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


import java.io.File;
import java.util.ArrayList;

import convolution.Convolution;
import curveGenerator.CurveGenerator;
import curveGenerator.WaterContentGenerator;
import inputOutputManagement.ReaderInputClimate;
import inputOutputManagement.ReaderInputConcentration;
import travelTimes.TravelTimesPdfVG;


import monteCarlo.ReaderInputHydraulicParams;
import monteCarlo.WriterOUTttpdf;

public class SimulationTFM {

	public void trasferModel (String pathToParams, String pathToClimate, String pathToConcentration,
			String pathToOutput, double run, boolean doConvol) throws Exception {


		// if the file already exists, it is delated
		// in order to avoid to add results to the 
		// already existing file
		File file = new File(pathToOutput); 		
		if(file.exists()) file.delete();

		ReaderInputClimate readerPrecip =new ReaderInputClimate();
		readerPrecip.pathToData=pathToClimate;	
		readerPrecip.process();

		
		ArrayList <Double> mean=new ArrayList <Double>();
		ArrayList <Double> variance=new ArrayList <Double>();
		
		// for each param set
		for(int j=0;j<run;j++){


			// reads the params of the unsaturated hydraulic conductivity curve

			ReaderInputHydraulicParams readerInputsParams = new ReaderInputHydraulicParams();	
			readerInputsParams.pathToData=pathToParams;
			readerInputsParams.j=j;
			readerInputsParams.process();







			double precipitation=readerPrecip.precipitation.get(1);



			CurveGenerator curve=new CurveGenerator();
			TravelTimesPdfVG tf_VG=new TravelTimesPdfVG ();


				


			WaterContentGenerator wc= new WaterContentGenerator();

			wc.alfa_paramVanGenuchten=readerInputsParams.alpha_VG;
			wc.n_paramVanGenuchten=readerInputsParams.n_VG;
			wc.residual_waterContent=readerInputsParams.residual_waterContent_VG;
			wc.saturated_waterContent=readerInputsParams.saturated_waterContent_VG;
			wc.process();


			curve.inputFlux=precipitation;
			curve.saturated_conductivity=readerInputsParams.saturated_conductivity_VG;
			curve.n_paramVanGenuchten=readerInputsParams.n_VG;
			curve.residual_waterContent=readerInputsParams.residual_waterContent_VG;
			curve.saturated_waterContent=readerInputsParams.saturated_waterContent_VG;
			curve.tau_paramVanGenuchten=readerInputsParams.tau_VG;
			curve.model="VG";
			curve.waterContent=wc.simualtedTheta;


			curve.process();



			tf_VG.inputFlux=precipitation;
			tf_VG.layerThickness=151;			
			tf_VG.saturated_waterContent_VG=readerInputsParams.saturated_waterContent_VG;
			tf_VG.residual_waterContent_VG=readerInputsParams.residual_waterContent_VG;
			tf_VG.n_VG=readerInputsParams.n_VG;
			tf_VG.tau_VG=readerInputsParams.tau_VG;
			tf_VG.waterContent=wc.simualtedTheta;
			tf_VG.saturated_conductivity_VG=readerInputsParams.saturated_conductivity_VG;
			tf_VG.simualtedConductivity=curve.simualtedConductivity;
			tf_VG.mean_velocity=curve.mean_velocity;

			tf_VG.process();


			
			if(doConvol==false){
			// compute the mean and variance 
			mean.add(tf_VG.mean);
			variance.add(tf_VG.variance);
			
			}else{
					
			ReaderInputConcentration readerC=new ReaderInputConcentration();
			readerC.pathToData=pathToConcentration;	
			readerC.process();
			
	
			Convolution convolve=new Convolution();
			convolve.inputConcentration=readerC.concentration;
			convolve.TTpdf=tf_VG.pdfArray;
			convolve.convolution();
			
			mean.add(convolve.mean);
			variance.add(convolve.variance);
			
			}

		}

		// write the output
		WriterOUTttpdf writer= new WriterOUTttpdf();



		writer.pathToOutput=pathToOutput;
		writer.outputMeanTT=mean;
		writer.outputVarTT=variance;
		writer.process();

	}







}


