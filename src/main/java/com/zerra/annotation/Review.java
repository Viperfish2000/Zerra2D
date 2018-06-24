package com.zerra.annotation;

/**
 * <em><b>Copyright (c) 2018 The Zerra Team.</b></em>
 * 
 * <br>
 * </br>
 * 
 * An interface that is helpful when documenting. It allows the person who made the class/method/system document their work to better suit a person who doesn't know how their work functions.
 * 
 * @author Ocelot5836
 */
public @interface Review {
	
	/**
	 * @return A description of what needs to be reviewed
	 */
	String desc();
}