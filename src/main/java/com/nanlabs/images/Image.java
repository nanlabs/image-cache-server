package com.nanlabs.images;


public class Image {
	
	public static final int DEFAULT_WIDTH = 1024;
	private String name;
	private int width;
	private byte[] data;
	
	public Image(String name, byte[] data, Integer width){
		this.name = name;
		this.width = width;
		this.data = data;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}

}
