package com.ocelot.util.data;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.commons.io.output.ByteArrayOutputStream;

public class ByteDataUtil {

//	public static ByteDataContainer serializeData(byte[] bytes) {
//		return serializeData(new ByteDataContainer(), bytes);
//	}
//
//	public static ByteDataContainer serializeData(ByteDataContainer container, byte[] bytes) {
//		try {
//			ByteArrayOutputStream io = new ByteArrayOutputStream();
//			DataOutput output = new DataOutputStream(io);
//			container.write(output);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return container;
//	}
//
//	public static ByteDataContainer deserializeData(byte[] bytes) {
//		return deserializeData(new ByteDataContainer(), bytes);
//	}
//
//	public static ByteDataContainer deserializeData(ByteDataContainer container, byte[] bytes) {
//		try {
//			ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
//			DataInput input = new DataInputStream(inputStream);
//			container.read(input);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return container;
//	}

	public static byte[] readBytesFromData(ByteDataContainer container) {
		try {
			ByteArrayOutputStream io = new ByteArrayOutputStream();
			DataOutput output = new DataOutputStream(io);
			container.write(output);
			return io.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new byte[0];
	}
	
	public static ByteDataContainer createContainer(byte[] bytes) {
		ByteDataContainer container = new ByteDataContainer();
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
			DataInput input = new DataInputStream(inputStream);
			container.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return container;
	}
}