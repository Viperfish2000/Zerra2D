package com.ocelot.util.data;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.util.Arrays;

import org.apache.commons.io.output.ByteArrayOutputStream;

public class DataTest {

	public static void main(String[] args) throws Exception {
		ByteDataContainer data = new ByteDataContainer();
		data.setShort("test", (short) 0);
		data.setShort("test1", (short) 2);

		ByteArrayOutputStream io = new ByteArrayOutputStream();
		DataOutput output = new DataOutputStream(io);
		data.write(output);
		System.out.println(Arrays.toString(io.toByteArray()));

		ByteArrayInputStream inputStream = new ByteArrayInputStream(io.toByteArray());
		DataInput input = new DataInputStream(inputStream);
		data.read(input);
		System.out.println(data.keySet());
	}
}