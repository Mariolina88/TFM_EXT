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

package convolution;

import java.util.ArrayList;

import org.apache.commons.math3.util.MathArrays;


/**
 * 
 * 
 * The class Convolution convolves the two signals (TT pdfs and input concentration) to obtain the output concentration
 * 
 * INPUTS:
 * TTpdf: the travel time pdf
 * inputConcentration: the input measured concentration
 * 
 * OUTPUTS:
 * outputConcentration: the output simulated concentration
 * 
 *
 */
public class Convolution {

	//INPUT
	public ArrayList <Double> TTpdf;
	public ArrayList <Double> inputConcentration;
	
	//OUPUT
	public double [] outputConcentration;


	public void convolution() throws Exception {

		int dim=TTpdf.size();

		outputConcentration=new double [dim];
		

		outputConcentration= MathArrays.convolve(toArray(TTpdf), toArray(inputConcentration));


	}


	private double [] toArray(ArrayList <Double> arrayList){

		double[] tempArray = new double[arrayList.size()];

		for(int i=0; i<arrayList.size();i++) {
			tempArray[i] = (double) arrayList.get(i);

		}
		return tempArray;
	}

}
