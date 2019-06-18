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

import java.util.Collection;

import org.apache.commons.math3.fitting.AbstractCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresBuilder;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresProblem;
import org.apache.commons.math3.linear.DiagonalMatrix;


public class FunctionFitter extends AbstractCurveFitter {
	
	public double[] initialGuess;

	/* (non-Javadoc)
	 * @see org.apache.commons.math3.fitting.AbstractCurveFitter#getProblem(java.util.Collection)
	 */
	protected LeastSquaresProblem getProblem(Collection<WeightedObservedPoint> points) {
		final int len = points.size();
		final double[] target  = new double[len];
		final double[] weights = new double[len];

		int i = 0;
		for(WeightedObservedPoint point : points) {
			target[i]  = point.getY();
			weights[i] = point.getWeight();
			i += 1;
		}

		final AbstractCurveFitter.TheoreticalValuesFunction model = new
				AbstractCurveFitter.TheoreticalValuesFunction(new Function(), points);

		return new LeastSquaresBuilder().
				maxEvaluations(Integer.MAX_VALUE).
				maxIterations(Integer.MAX_VALUE).
				start(initialGuess).
				target(target).
				weight(new DiagonalMatrix(weights)).
				model(model.getModelFunction(), model.getModelFunctionJacobian()).
				build();


	}



}
