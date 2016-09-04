package com.era.community.faqs.ui.dto; 

import java.util.List;

public class HelpQDto extends com.era.community.faqs.dao.generated.HelpQEntity 
{
	private List <HelpQDto> headers;
	private int subHeaderCnt;

	public List<HelpQDto> getHeaders() {
		return headers;
	}

	public void setHeaders(List<HelpQDto> headers) {
		this.headers = headers;
	}

	public int getSubHeaderCnt() {
		return subHeaderCnt;
	}

	public void setSubHeaderCnt(int subHeaderCnt) {
		this.subHeaderCnt = subHeaderCnt;
	}
}
