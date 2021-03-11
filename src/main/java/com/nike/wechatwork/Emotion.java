package com.nike.wechatwork;

public class Emotion {
	private int type;
	private int width;
	private int height;
	private int imagesize;
	private String md5sum;
	private String sdkfileid;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getImagesize() {
		return imagesize;
	}

	public void setImagesize(int imagesize) {
		this.imagesize = imagesize;
	}

	public String getMd5sum() {
		return md5sum;
	}

	public void setMd5sum(String md5sum) {
		this.md5sum = md5sum;
	}

	public String getSdkfileid() {
		return sdkfileid;
	}

	public void setSdkfileid(String sdkfileid) {
		this.sdkfileid = sdkfileid;
	}

}
