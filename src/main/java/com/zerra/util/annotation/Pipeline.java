package com.zerra.util.annotation;

/**
 * <em><b>Copyright (c) 2018 Ocelot5836.</b></em>
 * 
 * <br>
 * </br>
 * 
 * Shows the order that pipeline openGL rendering happens in.
 * 
 * @author Ocelot5836
 * @author Hypeirochus
 */
public @interface Pipeline {

	/**
	 * @return The category this pipeline falls under. An example is "rendering".
	 */
	String label();
	
	/**
	 * @return The step this part is in the process.
	 */
	int value();
}