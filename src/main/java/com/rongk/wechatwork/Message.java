package com.rongk.wechatwork;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Message {

	private int seq;
	private String msgid;
	private String publickey_ver;
	private String encrypt_random_key;
	private String encrypt_chat_msg;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getPublickey_ver() {
		return publickey_ver;
	}

	public void setPublickey_ver(String publickey_ver) {
		this.publickey_ver = publickey_ver;
	}

	public String getEncrypt_random_key() {
		return encrypt_random_key;
	}

	public void setEncrypt_random_key(String encrypt_random_key) {
		this.encrypt_random_key = encrypt_random_key;
	}

	public String getEncrypt_chat_msg() {
		return encrypt_chat_msg;
	}

	public void setEncrypt_chat_msg(String encrypt_chat_msg) {
		this.encrypt_chat_msg = encrypt_chat_msg;
	}

	public Message(int seq, String msgid, String publickey_ver, String encrypt_random_key, String encrypt_chat_msg) {
		super();
		this.seq = seq;
		this.msgid = msgid;
		this.publickey_ver = publickey_ver;
		this.encrypt_random_key = encrypt_random_key;
		this.encrypt_chat_msg = encrypt_chat_msg;
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
