package com.zerra.util;

public interface ISerializable<T> {

	T serialize();
	
	void deserialize(T data);
}