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
package travelTimes;

/**
 * The  Model interface.
 */
public interface Model {

	/**
	 * @return the double value of the clear sky emissivity
	 */
	abstract public double densityFunction ();

}