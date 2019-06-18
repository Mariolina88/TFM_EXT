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
package curveFitting;


import org.apache.commons.math3.analysis.ParametricUnivariateFunction;



public class Function implements ParametricUnivariateFunction {



	/* (non-Javadoc)
	 * @see org.apache.commons.math3.analysis.ParametricUnivariateFunction#value(double, double[])
	 */
	@Override
	public double value(double x, double... parameters) {
		// TODO Auto-generated method stub
		return Math.log(parameters[0])+(parameters[1]*(x-parameters[2]));
	}




    /* (non-Javadoc)
     * @see org.apache.commons.math3.analysis.ParametricUnivariateFunction#gradient(double, double[])
     */
    public double[] gradient(double x, double... parameters) {
        final double a = parameters[0];
        final double b = parameters[1];
        final double c = parameters[2];


        // then return the partial derivatives required
        // notice the format, 3 arguments for the method since 3 parameters were
        // specified first order derivative of the first parameter, then the second, 
        // then the third
        return new double[] {
                1/a,(x-c),-b
                
        };
        
	}


}
