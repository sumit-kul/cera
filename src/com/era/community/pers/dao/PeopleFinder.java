package com.era.community.pers.dao;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 'Find similar profiles'
 */
public class PeopleFinder
{
	private StringBuffer where = new StringBuffer(1024);
	private Object[] params = new Object[0];

	private String[] firstNames;
	private String[] lastNames;    
	private String[] jobTitles;
	private String[] keywords;
	private String[] regions;
	private Integer[] expertises;
	private Integer[] interests;

	/* If this value is not 0, then this is a 'Find People Like Me' search */    
	private int likeMeUserId;

	private final String view = "TBUSER";
	private final String prefixDefault =" U";

	protected Log logger = LogFactory.getLog(getClass());

	public PeopleFinder()
	{
		super();
	}

	public PeopleFinder(User user)  throws Exception {
		int[] exps=user.getExpertiseIds();
		if(exps.length==0) return;
		this.setLikeMeUserId(user.getId());
		expertises =new Integer[exps.length];
		for(int n=0;n<exps.length;n++)
			expertises[n]=new Integer(exps[n]);
	}

	private String prefix(String col) 
	{
		return prefixDefault + "." + col;        
	}    

	private boolean hasSearchTerms() throws Exception 
	{
		if(!isNullorWhiteSpace(firstNames)) return true;
		if(!isNullorWhiteSpace(lastNames)) return true;
		if(!isNullorWhiteSpace(jobTitles)) return true;
		if(!isNullorWhiteSpace(regions)) return true;
		if(!isNullorWhiteSpace(expertises)) return true;
		if(!isNullorWhiteSpace(keywords)) return true;
		return false;
	}


	public String getQueryString() throws Exception
	{        
		setWhereClause();
		StringBuffer buf=new StringBuffer(1024);        
		buf.append(getBaseQuery());
		buf.append(where);
		return buf.toString();               
	}    

	public Object[] getParameters() throws Exception
	{         
		setWhereClause();
		for(int n=0;n<params.length;n++) 
			logger.debug(("Parameter Value for Profile Search : ="  + params[n]));
		return params;
	}


	private String getBaseQuery() {
		StringBuffer buf=new StringBuffer(1024);
		buf.append("select" + prefix("*") +", case when " 
				+ prefix("photo") + " is null then ' ' else 'Y' end as PhotoPresent from " 
				+ view + prefixDefault + " where ");
		return buf.toString();        
	}

	private void setWhereClause() throws Exception 
	{
		where=new StringBuffer(1024);
		params=new Object[0];

		if(!hasSearchTerms()) {
			logger.debug("==== Profile Search : No search terms found ===");
			where.append(prefix("id") + "=-1");
			return;
		}
		where.append("Inactive <> 'Y' and Validated = 'Y'");
		if(likeMeUserId>0) {
			if(where.length()>0) where.append(" and ");
			where.append(prefix("id") + "<>?");
			params=ArrayUtils.add(params, new Integer(likeMeUserId));
		}

		if(!isNullorWhiteSpace(firstNames)) {         
			if(where.length()>0) where.append(" and ");
			appendFirstNamesClause();
		}
		if(!isNullorWhiteSpace(lastNames)) {         
			if(where.length()>0) where.append(" and ");
			appendLastNamesClause();
		}                
		if(!isNullorWhiteSpace(jobTitles)) {
			if(where.length()>0) where.append(" and ");
			appendJobTitlesClause();
		}
		if(!isNullorWhiteSpace(keywords)) {
			if(where.length()>0) where.append(" and ");
			appendKeywordsClause();
		}
		if(!isNullorWhiteSpace(regions)) {
			if(where.length()>0) where.append(" and ");
			appendRegionsClause();
		}
		if(!isNullorWhiteSpace(expertises)) {
			if(where.length()>0) where.append(" and ");
			appendExpertisesClause();
		} 
	}

	private void appendFirstNamesClause()
	{
		appendStringLikeClause("FirstName", firstNames);               
	}

	private void appendLastNamesClause()
	{
		appendStringLikeClause("LastName", lastNames);                               
	}

	private void appendJobTitlesClause()
	{
		appendStringLikeClause("jobTitle", jobTitles);
	}

	private void appendKeywordsClause()
	{
		appendLongVarcharClause("about", keywords);
	}

	private void appendRegionsClause()
	{
		appendStringClause("region", regions);
	}

	private void appendExpertisesClause()
	{
		StringBuffer buf=new StringBuffer(1024);
		buf.append("Exists (select * from TBUEXL L where L.UserExpertiseId in ("); 
		for(int n=0;n<expertises.length;n++) {
			if(n>0) buf.append(",");
			buf.append("?");
		}
		buf.append(") and L.UserId=" + prefix("Id") + ")" );
		where.append(buf.toString());
		params= ArrayUtils.addAll(params,expertises);
	}

	private void appendLongVarcharClause(String col, String[] values)
	{
		/* DB2 does not support UCASE on long VarChars */

		StringBuffer buf=new StringBuffer(1024);
		buf.append(" (");

		for(int n=0;n<values.length;n++) {

			if(n>0) buf.append(" or ");
			buf.append(" (" + prefix(col) + " like ?)"); 
			buf.append(" or (" + prefix(col) + " like ?)"); 
			buf.append(" or (" + prefix(col) + " like ?)");            
			buf.append(" or (" + prefix(col) + " like ?)"); // first char in upper case

			String s=("%" + values[n].trim() +"%");
			params= ArrayUtils.add(params,"%" +s+"%");

			s=(values[n]).toUpperCase().trim();
			params= ArrayUtils.add(params,"%"+s+"%");

			s=(values[n]).toLowerCase().trim();
			params= ArrayUtils.add(params,"%"+s+"%");

			s=(values[n].trim());
			params= ArrayUtils.add(params,"%" + s.substring(0, 1).toUpperCase() + s.substring(1, s.length()).toLowerCase() + "%");
		}
		buf.append(") ");
		where.append(buf.toString());
	}

	private void appendStringLikeClause(String col, String[] values)
	{
		StringBuffer buf=new StringBuffer(1024);
		buf.append(" ( ");
		for(int n=0;n<values.length;n++) {
			if(n>0) buf.append(" or ");
			buf.append(" UCASE(" + prefix(col) + ") like ?");
			params = ArrayUtils.add(params,"%" + values[n].toUpperCase().trim() + "%");
		}
		buf.append(" ) ");
		where.append(buf.toString());
	}

	private void appendStringClause(String col, String[] values)
	{
		StringBuffer buf=new StringBuffer(1024);
		buf.append("UCASE(" + prefix(col) + ") in (");
		for(int n=0;n<values.length;n++) {
			if(n>0) buf.append(",");
			buf.append("?");
			String s=(values[n]).toUpperCase();
			params= ArrayUtils.add(params,s);     
		}
		buf.append(" ) ");
		where.append(buf.toString());
	}

	private void appendIntegerClause(String col, String[] values)
	{
		StringBuffer buf=new StringBuffer(1024);
		buf.append(prefix(col) + " =");
		for(int n=0;n<values.length;n++) {
			if(n>0) buf.append(",");
			buf.append("?");
			params= ArrayUtils.add(params,values[n]);     
		}
		where.append(buf.toString());
	}

	public void setExpertise(String expertise)    
	{
		if(isNullorWhiteSpace(expertise)) {return;}
		String[] s=expertise.split(",");
		this.expertises=new Integer[s.length];
		for(int n=0;n<s.length;n++) {
			this.expertises[n]=new Integer(s[n]);
		}
	}

	public void setJobTitle(String jobTitle)
	{
		if(isNullorWhiteSpace(jobTitle)) return;
		this.jobTitles = (jobTitle).split(",");
	}

	public void setKeyword(String keyword)
	{
		if(isNullorWhiteSpace(keyword)) return;
		this.keywords = (keyword).split(",");
	}

	public void setFirstName(String firstName)
	{
		if(isNullorWhiteSpace(firstName)) return;
		this.firstNames = (firstName).split(",");
	}

	public void setLastName(String lastName)
	{
		if(isNullorWhiteSpace(lastName)) return;
		this.lastNames = (lastName).split(",");
	}

	public void setRegion(String region)
	{
		if(isNullorWhiteSpace(region)) return;
		this.regions = (region).split(",");
	}

	public void setExpertises(int[] expertises)
	{
		this.expertises=new Integer[0];
		if(expertises==null || expertises.length==0) return;
		this.expertises=new Integer[expertises.length];
		for(int n=0;n<expertises.length;n++)
			this.expertises[n]=new Integer(expertises[n]);       
		logger.debug("Expertises now has some value :" + this.expertises.length);
	}

	public void setJobTitles(String[] jobTitles)
	{
		if(isNullorWhiteSpace(jobTitles)) return;
		this.jobTitles = jobTitles;
	}

	public void setKeywords(String[] keywords)
	{
		this.keywords = keywords;
	}

	public void setRegions(String[] regions)
	{
		this.regions = regions;
	}

	private boolean isNullorWhiteSpace(Object object) 
	{    
		return isNullorWhiteSpace(new Object[] {object});
	}
	
	private boolean isNullorWhiteSpace(Object[] object) {
		if(object==null) return true;
		if(object.length==0) return true;
		if(object.length==1 && object[0]==null) return true;
		if(object[0] instanceof String && object[0].equals("")) return true;
		return false;        
	}

	public void setLikeMeUserId(int id)
	{
		this.likeMeUserId = id;
	}

	public void setFirstNames(String[] firstNames)
	{
		this.firstNames = firstNames;
	}

	public void setLastNames(String[] lastNames)
	{
		this.lastNames = lastNames;
	}

	public void setExpertises(Integer[] expertises)
	{
		this.expertises = expertises;
	}
}