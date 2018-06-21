package com.zerra.util;

/**
 * <em><b>Copyright (c) 2018 Ocelot5836.</b></em>
 * 
 * <br>
 * </br>
 * 
 * Shows the order that pipeline openGL rendering happens in.
 * 
 * @author Ocelot5836
 */
public @interface Pipeline {

	/**
	 * @return The step this part is
	 */
	int value();
}