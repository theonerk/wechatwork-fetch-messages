package com.rongk.wechatwork;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Video {
	private String md5sum;
	private long filesize;
	private int play_length;
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

	public int getPlay_length() {
		return play_length;
	}

	public void setPlay_length(int play_length) {
		this.play_length = play_length;
	}

	public String getSdkfileid() {
		return sdkfileid;
	}

	public void setSdkfileid(String sdkfileid) {
		this.sdkfileid = sdkfileid;
	}

	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return this.toString();
	}
}
