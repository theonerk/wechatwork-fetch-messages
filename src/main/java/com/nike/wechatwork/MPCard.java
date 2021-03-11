package com.nike.wechatwork;

public class MPCard {
	private String title;
	private String description;
	private String username;
	private String displayname;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[title=" + this.title + ", description=" + this.description + ", username=" + this.username
				+ ", displayname=" + this.displayname + "]";
	}

}
