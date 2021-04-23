package curveGenerator;

import java.util.ArrayList;

public class WaterContentGenerator {


	public double saturated_waterContent;
	public double residual_waterContent;
	public double n_paramVanGenuchten;
	public double alfa_paramVanGenuchten;


	// OUTPUT
	public ArrayList<Double> simualtedTheta = new ArrayList<Double>();

	public void process() throws Exception { 
		double x_i=0.1;

		for(int i=0;i<99;i++){

			double h=Math.pow(10,x_i+i*0.05);

			simualtedTheta.add(generateTheta(h));

		}
	}


	public double generateTheta(double h) {

		double m = 1 - 1 / n_paramVanGenuchten;

		double theta = residual_waterContent + (saturated_waterContent - residual_waterContent)
				* Math.pow((1 + Math.pow(Math.abs(alfa_paramVanGenuchten * h), n_paramVanGenuchten)), -m);

		return theta;
	}

}
