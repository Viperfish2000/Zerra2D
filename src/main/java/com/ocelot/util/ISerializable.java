package com.ocelot.util;

public interface ISerializable<T> {

	T serialize();
	
	void deserialize(T data);
}