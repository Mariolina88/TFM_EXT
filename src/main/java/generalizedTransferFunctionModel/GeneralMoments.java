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
 * The class GeneralMoments computes the total mean and variance of all layers
 * INPUTS:
 * Arraylist with mean and variance of each layer;
 * 
 * OUPUT:
 * total mean and variance
 *
 */
public class GeneralMoments {

	//INPUT
	public ArrayList <Double> mean;	
	public ArrayList <Double> variance;	

	//OUTPUT
	public double totalMean;
	public double totalVar;
	public boolean independence;

	public void process() throws Exception { 

		totalMean=mean.get(0);
		totalVar=variance.get(0);


		for (int i=1;i<mean.size();i++){

			totalMean=totalMean+mean.get(i);
			double R=mean.get(i)/mean.get(i-1);

			if(independence==false){
				totalVar=Math.pow(R,2)*totalVar;
			}else{
				totalVar=totalVar+variance.get(i);
			}


		}






	}
}
