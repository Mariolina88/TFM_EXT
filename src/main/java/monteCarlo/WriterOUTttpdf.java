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

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/**
 * 
 * WriterOUTttpdf writes the output concentration computed for each date and input flux
 * 
 *  INPUT:
 *  pathToOutput:path to the file in output
 *	output Concetration : computed by the GTFM
 *	date of simulation
 */
public class WriterOUTttpdf {

	// INPUT
	public String pathToOutput;

	public ArrayList <Double> outputMeanTT;
	public ArrayList <Double> outputVarTT;




	public void process() throws IOException{


		FileWriter csvWriter = new FileWriter(pathToOutput, true);

		int dim=outputMeanTT.size();




		for(int i=0;i<dim;i++){

			csvWriter.append(outputMeanTT.get(i).toString());
			if(i<dim-1)csvWriter.append(",");
			csvWriter.append('\n');
		}

		/**csvWriter.append('\n');
		
		for(int i=0;i<dim;i++){

			csvWriter.append(outputVarTT.get(i).toString());
			if(i<dim-1)csvWriter.append(",");

		} */

		csvWriter.flush();  
		csvWriter.close();



	}
}
