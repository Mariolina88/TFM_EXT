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
import convolution.Convolution;
import curveGenerator.CurveGenerator;
import curveGenerator.WaterContentGenerator;
import generalizedTransferFunctionModel.GTFmodel;
import generalizedTransferFunctionModel.GeneralMoments;
import inputOutputManagement.ReaderInputConcentration;
import inputOutputManagement.ReaderInputHydraulicData;
import inputOutputManagement.ReaderInputHydraulicParams;
import travelTimes.TravelTimesPdfBeta;
import travelTimes.TravelTimesPdfVG;

public class SimulationTFM {

	public void trasferModel (String pathToShape, String codeProfile, double netPrecipitation,
			double waterTableDepth,String pathToConcentration,
			double lambda_1, double lambda_2, boolean layerIndependence, String model) throws Exception {




		// reads the params of the unsaturated hydraulic conductivity curve

		ReaderInputHydraulicParams readerInputsParams = new ReaderInputHydraulicParams();	
		readerInputsParams.pathToShape=pathToShape;	
		readerInputsParams.codeProfile=codeProfile;	
		readerInputsParams.process();




		ArrayList <String> layer= new ArrayList <String>();
		boolean warning_mean=false;




		ArrayList <Double> mean=new ArrayList <Double>();
		ArrayList <Double> variance=new ArrayList <Double>();


		double totalDepth=0;




		// for each layer computes the TTpdfs till the end of the known depth
		for(int j=0;j<readerInputsParams.thickness.size();j++){







			CurveGenerator curve=new CurveGenerator();
			TravelTimesPdfVG tf_VG=new TravelTimesPdfVG ();
			TravelTimesPdfBeta tf_Beta=new TravelTimesPdfBeta ();


			double thickness=readerInputsParams.thickness.get(j);

			// Beta exponential model curve
			if(model.contains("Beta")){



				//read the data from the files
				ReaderInputHydraulicData reader=new ReaderInputHydraulicData();
				reader.pathToData=readerInputsParams.pathToHydraData.get(j);	
				reader.process();


				double beta=readerInputsParams.beta_exponentialModel.get(j);
				double saturated_conductivity_exponentialModel=readerInputsParams.saturated_conductivity_exponentialModel.get(j);
				double saturated_waterContent_exponentialModel=readerInputsParams.saturated_waterContent_exponentialModel.get(j);


				curve.inputFlux=netPrecipitation;
				curve.beta_linearModel=beta;
				curve.saturated_conductivity=saturated_conductivity_exponentialModel;
				curve.saturated_waterContent=saturated_waterContent_exponentialModel;
				curve.model="Beta";
				curve.waterContent=reader.waterContent;


				curve.process();


				tf_Beta.inputFlux=netPrecipitation;
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


				curve.inputFlux=netPrecipitation;
				curve.saturated_conductivity=saturated_conductivity_VG;
				curve.n_paramVanGenuchten=n_paramVanGenuchten;
				curve.residual_waterContent=residual_waterContent;
				curve.saturated_waterContent=saturated_waterContent_VG;
				curve.tau_paramVanGenuchten=tau_paramVanGenuchten;
				curve.model="VG";
				curve.waterContent=wc.simualtedTheta;


				curve.process();



				tf_VG.inputFlux=netPrecipitation;
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
		gm.independence=layerIndependence;
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


		System.out.println("Mean arrival time at depth "+waterTableDepth+" cm= "+convolve.mean+ " day, with a mass recovery of "+convolve.mass_recovered);

	}





}


