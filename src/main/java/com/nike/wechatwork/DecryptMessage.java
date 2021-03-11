package com.nike.wechatwork;

import java.util.ArrayList;

public class DecryptMessage {
	private String msgid;
	private String action;
	private String from;
	private ArrayList<String> tolist;
	private String roomid;
	private long msgtime;
	private String msgtype;
	private Text text;
	private Image image;
	private Emotion emotion;
	private Video video;
	private MPCard weapp;

	public MPCard getWeapp() {
		return weapp;
	}

	public void setWeapp(MPCard weapp) {
		this.weapp = weapp;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public Emotion getEmotion() {
		return emotion;
	}

	public void setEmotion(Emotion emotion) {
		this.emotion = emotion;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public ArrayList<String> getTolist() {
		return tolist;
	}

	public void setTolist(ArrayList<String> tolist) {
		this.tolist = tolist;
	}

	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public long getMsgtime() {
		return msgtime;
	}

	public void setMsgtime(long msgtime) {
		this.msgtime = msgtime;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

}
