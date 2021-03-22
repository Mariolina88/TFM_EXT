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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * The class ReaderConfiguration reads from the txt file the path to the files
 * containing: pathToFolderToHydraulicData: file with the measured K, theta, h;
 * pathToOptimizedCurveParams: file with optimized values of the unsaturated
 * conductivity curve models; pathToPrecipitation: path to the climatic data;
 * profile_code: the code of the soil profile; pathToOutput: path to the output
 * file; waterTableDepth: the value of the water table depth; lambda_1, lambda_2
 * are the params of the GTFM; doOptimization: is true in the case of the Beta
 * params file missing.
 * 
 */
public class ReaderConfiguration {

	// OUTPUT

	String pathToOptimizedCurveParams;
	String pathToPrecipitation;
	String pathToConcentration;
	String pathToOutput;
	double run;
	boolean doConvol;
	double distribution_coeff;
	double decayFactor;

	public void setConfiguration(String pathToConfig) throws IOException {

		String line = "";
		BufferedReader br = new BufferedReader(new FileReader(pathToConfig));
		int i = 0;

		while ((line = br.readLine()) != null) {

			if (i == 0) {

				pathToOptimizedCurveParams = line.split("\t")[1];

			} else if (i == 1) {

				pathToPrecipitation = line.split("\t")[1];

			} else if (i == 2) {
				
				pathToConcentration = line.split("\t")[1];
				
			}	else if (i == 3) {
				
				pathToOutput = line.split("\t")[1];
				
			}else if (i == 4) {
				
				run = Double.parseDouble(line.split("\t")[1]);
				
			}else if (i == 5) {
				
				doConvol=Boolean.parseBoolean(line.split("\t")[1]);
			}else if (i == 6) {
				
				distribution_coeff = Double.parseDouble(line.split("\t")[1]);
				
			}else if (i == 7) {
				
				decayFactor = Double.parseDouble(line.split("\t")[1]);
				
			}

			i++;

		}

		br.close();

	}

	

	public String getPathToOptimizedCurveParams() {
		return pathToOptimizedCurveParams;

	}

	public String getPathToPrecipitation() {
		return pathToPrecipitation;

	}

	
	public String getPathToOuput() {
		return pathToOutput;
	}
}
