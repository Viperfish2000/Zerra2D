package com.zerra.util.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ByteDataTypeString extends ByteDataBase {

	private String data;

	ByteDataTypeString() {
	}

	public ByteDataTypeString(String data) {
		this.data = data;
	}

	@Override
	void write(DataOutput output) throws IOException {
		output.writeUTF(data);
	}

	@Override
	void read(DataInput input) throws IOException {
		this.data = input.readUTF();
	}

	protected String getString() {
		return data;
	}

	@Override
	public byte getId() {
		return 8;
	}

	@Override
	public ByteDataBase copy() {
		return new ByteDataTypeString(data);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && ((ByteDataTypeString) obj).data == this.data;
	}

	@Override
	public String toString() {
		return data;
	}
}