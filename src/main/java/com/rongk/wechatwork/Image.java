package com.rongk.wechatwork;

public class Image {

	private String md5sum;
	private long filesize;
	private String sdkfileid;

	public String getMd5sum() {
		return md5sum;
	}

	public void setMd5sum(String md5sum) {
		this.md5sum = md5sum;
	}

	public long getFilesize() {
		return filesize;
	}

	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}

	public String getSdkfileid() {
		return sdkfileid;
	}

	public void setSdkfileid(String sdkfileid) {
		this.sdkfileid = sdkfileid;
	}
}
