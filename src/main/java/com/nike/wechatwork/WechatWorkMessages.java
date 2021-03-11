package com.nike.wechatwork;

import java.util.ArrayList;

public class WechatWorkMessages {
	private int errcode;
	private String errmsg;
	private ArrayList<Message> chatdata;

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public ArrayList<Message> getChatdata() {
		return chatdata;
	}

	public void setChatdata(ArrayList<Message> chatdata) {
		this.chatdata = chatdata;
	}

	public WechatWorkMessages(int errcode, String errmsg, ArrayList<Message> chatdata) {
		super();
		this.errcode = errcode;
		this.errmsg = errmsg;
		this.chatdata = chatdata;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.errmsg.toString();
	}

}
