package com.era.community.communities.ui.dto; 

public class CommunityFeatureDto 
{
	private String title;
	private String label;
	private String name;
	private boolean anabled;
	private boolean mandatory;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isAnabled() {
		return anabled;
	}
	public void setAnabled(boolean anabled) {
		this.anabled = anabled;
	}
	public boolean isMandatory() {
		return mandatory;
	}
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
}
