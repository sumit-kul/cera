package com.era.community.faqs.ui.dto; 

import org.springframework.web.multipart.MultipartFile;

public class FaqDto extends com.era.community.faqs.dao.generated.FaqEntity
{
	MultipartFile upload;
	private int resultSetIndex;

	public boolean isEvenRow()
	{
		return resultSetIndex%2==0;
	}
	public boolean isOddRow()
	{
		return resultSetIndex%2==1;
	}

	public final int getResultSetIndex()
	{
		return resultSetIndex;
	}
	public final void setResultSetIndex(int resultSetIndex)
	{
		this.resultSetIndex = resultSetIndex;
	}

	public final MultipartFile getUpload()
	{
		return upload;
	}

	public final void setUpload(MultipartFile file)
	{
		this.upload = file;
	}

	// No setter here as it's a read only property
	public String getBodyTranslated() throws Exception
	{
		return getBody().replaceAll("\r\n", "<br/>");
	}
}
