
package travelTimes;


public class ExponentialModel implements Model{

	double beta;
	double layerThickness;
	double time;
	double flux;
	
	


	public ExponentialModel(double time, double flux, double layerThickness,double beta_linearModel){

		this.beta=beta_linearModel;
		this.time=time;
		this.layerThickness=layerThickness;
		this.flux=flux;

	}



	public double densityFunction() {
		// TODO Auto-generated method stub
		
		double f=1/flux*layerThickness/beta*1/Math.pow(time, 2);
		
		return f;
	}

}
