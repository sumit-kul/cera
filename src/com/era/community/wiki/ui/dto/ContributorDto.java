package com.era.community.wiki.ui.dto; 

public class ContributorDto extends support.community.framework.CommandBeanImpl 
{
	private int contributorId;
	private int contributionCount;
	private String contributorName;
	private boolean contributorPhotoPresent;
	
	public int getContributorId() {
		return contributorId;
	}
	public void setContributorId(int contributorId) {
		this.contributorId = contributorId;
	}
	public int getContributionCount() {
		return contributionCount;
	}
	public void setContributionCount(Long contributionCount) {
		this.contributionCount = contributionCount.intValue();
	}
	public String getContributorName() {
		return contributorName;
	}
	public void setContributorName(String contributorName) {
		this.contributorName = contributorName;
	}
	public boolean isContributorPhotoPresent() {
		return contributorPhotoPresent;
	}
	public void setContributorPhotoPresent(boolean contributorPhotoPresent) {
		this.contributorPhotoPresent = contributorPhotoPresent;
	}
}