package com.era.community.media.ui.dto; 


public class ThumbnailDto 
{
	private String title;
	private byte[] thumbnail;
	private String encoding;
	private int newSize;
	private int newWidth;
	private int newHeight;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public byte[] getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(byte[] thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public int getNewSize() {
		return newSize;
	}
	public void setNewSize(int newSize) {
		this.newSize = newSize;
	}
	public int getNewWidth() {
		return newWidth;
	}
	public void setNewWidth(int newWidth) {
		this.newWidth = newWidth;
	}
	public int getNewHeight() {
		return newHeight;
	}
	public void setNewHeight(int newHeight) {
		this.newHeight = newHeight;
	}
	
	
}