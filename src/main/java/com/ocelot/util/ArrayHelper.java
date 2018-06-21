package com.ocelot.util;

public class ArrayHelper {

	public static byte[] toByteArray(Byte[] data) {
		byte[] returned = new byte[data.length];
		for (int i = 0; i < data.length; i++) {
			returned[i] = data[i];
		}
		return returned;
	}

	public static short[] toShortArray(Short[] data) {
		short[] returned = new short[data.length];
		for (int i = 0; i < data.length; i++) {
			returned[i] = data[i];
		}
		return returned;
	}

	public static int[] toIntArray(Integer[] data) {
		int[] returned = new int[data.length];
		for (int i = 0; i < data.length; i++) {
			returned[i] = data[i];
		}
		return returned;
	}

	public static float[] toFloatArray(Float[] data) {
		float[] returned = new float[data.length];
		for (int i = 0; i < data.length; i++) {
			returned[i] = data[i];
		}
		return returned;
	}

	public static double[] toDoubleArray(Double[] data) {
		double[] returned = new double[data.length];
		for (int i = 0; i < data.length; i++) {
			returned[i] = data[i];
		}
		return returned;
	}

	public static long[] toLongArray(Long[] data) {
		long[] returned = new long[data.length];
		for (int i = 0; i < data.length; i++) {
			returned[i] = data[i];
		}
		return returned;
	}

	public static boolean[] toBooleanArray(Boolean[] data) {
		boolean[] returned = new boolean[data.length];
		for (int i = 0; i < data.length; i++) {
			returned[i] = data[i];
		}
		return returned;
	}

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