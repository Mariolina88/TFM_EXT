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

package generalizedTransferFunctionModel;

import java.util.ArrayList;

/**
 * 
 * The class GTFmodel computes the travel times pdf till the water depth in unknown layers
 * 
 * INPUTS: 
 * mean: the total mean of the above layers
 * variance: the total variance of the above layers
 * waterTableDepth: the water table depth
 * aboveLayersDepth: the total depth of the above layers
 * lambda_1: parameter of the GTFM
 * lambda_2: parameter of the GTFM
 * 
 * OUTPUTS:
 * mean_t: mean of the pdf 
 * variance_t: variance of the pdf 
 * pdfArray: travle times pdf
 * 
 */

public class GTFmodel {

	//INPUT
	public double mean;
	public double variance;
	public double waterTableDepth;
	public double aboveLayersDepth;
	public double lambda_1;
	public double lambda_2;
	
	
	//OUPUT
	public double mean_t;
	public double variance_t;
	public ArrayList <Double> pdfArray= new ArrayList <Double>();


	public void process() throws Exception { 



		double mean_z=Math.pow(waterTableDepth/aboveLayersDepth,lambda_1)*mean;

		double var_z=Math.pow(waterTableDepth/aboveLayersDepth,2*lambda_2)*variance;

		double sigma_z=Math.pow(Math.log(1+var_z/(Math.exp(Math.log(Math.pow(mean_z,2))))),0.5);

		double mu_z=Math.log(mean_z)-0.5*Math.pow(sigma_z, 2);


		for(int i=0;i<100;i++){

			//GTF model 
			double f_tz=1/(sigma_z*(i+0.25)*Math.pow(2*Math.PI, 0.5))*Math.exp(-Math.pow((Math.log(i+0.25)-mu_z),2)/(2*Math.pow(sigma_z, 2)));


			mean_t=mean_t+(i+0.25)*f_tz;
			
			pdfArray.add(f_tz);

		}

		for(int i=0;i<100;i++){

			variance_t=variance_t+Math.pow(((i+0.25)-mean_t),2)*pdfArray.get(i);
		}


	}

}
