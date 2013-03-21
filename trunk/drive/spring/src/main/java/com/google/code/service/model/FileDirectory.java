package com.google.code.service.model;

public class FileDirectory {
	private String absolutePath;	// key
	private String description;	// from persistence
	private String creator;		// from persistence
	private String createdTime;	// from persistence
	private Boolean isFile;
	
	public FileDirectory(String path)
	{
		this.absolutePath = path;
	}
	
	public String getAbsolutePath() {
		return absolutePath;
	}
	public FileDirectory setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
		return this;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public Boolean getIsFile() {
		return isFile;
	}
	public FileDirectory setIsFile(Boolean isFile) {
		this.isFile = isFile;
		return this;
	}
}
