package com.zerra.util;

/**
 * <em><b>Copyright (c) 2018 The Zerra Team.</b></em>
 * 
 * <br>
 * </br>
 * 
 * Allows the easy ability to convert certain object arrays into a primitive array and the ability to append data to the ends of arrays.
 * 
 * @author Ocelot5836
 */
public class ArrayHelper {

	/**
	 * Converts the data from a {@link Byte} array into a byte array.
	 * 
	 * @param data
	 *            The data to convert into a byte array
	 * @return The converted data
	 */
	public static byte[] toByteArray(Byte[] data) {
		byte[] returned = new byte[data.length];
		for (int i = 0; i < data.length; i++) {
			returned[i] = data[i];
		}
		return returned;
	}

	/**
	 * Converts the data from a {@link Short} array into a short array.
	 * 
	 * @param data
	 *            The data to convert into a short array
	 * @return The converted data
	 */
	public static short[] toShortArray(Short[] data) {
		short[] returned = new short[data.length];
		for (int i = 0; i < data.length; i++) {
			returned[i] = data[i];
		}
		return returned;
	}

	/**
	 * Converts the data from a {@link Integer} array into an int array.
	 * 
	 * @param data
	 *            The data to convert into an int array
	 * @return The converted data
	 */
	public static int[] toIntArray(Integer[] data) {
		int[] returned = new int[data.length];
		for (int i = 0; i < data.length; i++) {
			returned[i] = data[i];
		}
		return returned;
	}

	/**
	 * Converts the data from a {@link Float} array into a float array.
	 * 
	 * @param data
	 *            The data to convert into a float array
	 * @return The converted data
	 */
	public static float[] toFloatArray(Float[] data) {
		float[] returned = new float[data.length];
		for (int i = 0; i < data.length; i++) {
			returned[i] = data[i];
		}
		return returned;
	}

	/**
	 * Converts the data from a {@link Double} array into a double array.
	 * 
	 * @param data
	 *            The data to convert into a double array
	 * @return The converted data
	 */
	public static double[] toDoubleArray(Double[] data) {
		double[] returned = new double[data.length];
		for (int i = 0; i < data.length; i++) {
			returned[i] = data[i];
		}
		return returned;
	}

	/**
	 * Converts the data from a {@link Long} array into a long array.
	 * 
	 * @param data
	 *            The data to convert into a long array
	 * @return The converted data
	 */
	public static long[] toLongArray(Long[] data) {
		long[] returned = new long[data.length];
		for (int i = 0; i < data.length; i++) {
			returned[i] = data[i];
		}
		return returned;
	}

	/**
	 * Converts the data from a {@link Boolean} array into a boolean array.
	 * 
	 * @param data
	 *            The data to convert into a boolean array
	 * @return The converted data
	 */
	public static boolean[] toBooleanArray(Boolean[] data) {
		boolean[] returned = new boolean[data.length];
		for (int i = 0; i < data.length; i++) {
			returned[i] = data[i];
		}
		return returned;
	}

	/**
	 * Appends the specified data to the end of the supplied array.
	 * 
	 * @param data
	 *            The base data array
	 * @param newData
	 *            The data to append to the end of the array
	 * @return The data array with the new data added to the end
	 */
	public static byte[] addToByteArray(byte[] data, byte... newData) {
		byte[] returned = new byte[data.length + newData.length - 2];
		for (int i = 0; i < returned.length; i++) {
			if (i < data.length) {
				returned[i] = data[i];
			} else {
				returned[i] = newData[i];
			}
		}
		return returned;
	}

	/**
	 * Appends the specified data to the end of the supplied array.
	 * 
	 * @param data
	 *            The base data array
	 * @param newData
	 *            The data to append to the end of the array
	 * @return The data array with the new data added to the end
	 */
	public static short[] addToShortArray(short[] data, short... newData) {
		short[] returned = new short[data.length + newData.length - 2];
		for (int i = 0; i < returned.length; i++) {
			if (i < data.length) {
				returned[i] = data[i];
			} else {
				returned[i] = newData[i];
			}
		}
		return returned;
	}

	/**
	 * Appends the specified data to the end of the supplied array.
	 * 
	 * @param data
	 *            The base data array
	 * @param newData
	 *            The data to append to the end of the array
	 * @return The data array with the new data added to the end
	 */
	public static int[] addToIntArray(int[] data, int... newData) {
		int[] returned = new int[data.length + newData.length - 2];
		for (int i = 0; i < returned.length; i++) {
			if (i < data.length) {
				returned[i] = data[i];
			} else {
				returned[i] = newData[i];
			}
		}
		return returned;
	}

	/**
	 * Appends the specified data to the end of the supplied array.
	 * 
	 * @param data
	 *            The base data array
	 * @param newData
	 *            The data to append to the end of the array
	 * @return The data array with the new data added to the end
	 */
	public static float[] addToFloatArray(float[] data, float... newData) {
		float[] returned = new float[data.length + newData.length - 2];
		for (int i = 0; i < returned.length; i++) {
			if (i < data.length) {
				returned[i] = data[i];
			} else {
				returned[i] = newData[i];
			}
		}
		return returned;
	}

	/**
	 * Appends the specified data to the end of the supplied array.
	 * 
	 * @param data
	 *            The base data array
	 * @param newData
	 *            The data to append to the end of the array
	 * @return The data array with the new data added to the end
	 */
	public static double[] addToDoubleArray(double[] data, double... newData) {
		double[] returned = new double[data.length + newData.length - 2];
		for (int i = 0; i < returned.length; i++) {
			if (i < data.length) {
				returned[i] = data[i];
			} else {
				returned[i] = newData[i];
			}
		}
		return returned;
	}

	/**
	 * Appends the specified data to the end of the supplied array.
	 * 
	 * @param data
	 *            The base data array
	 * @param newData
	 *            The data to append to the end of the array
	 * @return The data array with the new data added to the end
	 */
	public static boolean[] addToBooleanArray(boolean[] data, boolean... newData) {
		boolean[] returned = new boolean[data.length + newData.length - 2];
		for (int i = 0; i < returned.length; i++) {
			if (i < data.length) {
				returned[i] = data[i];
			} else {
				returned[i] = newData[i];
			}
		}
		return returned;
	}
}