package com.era.community.events.ui.dto;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TreeMap;

import support.community.framework.CommandBean;

import com.era.community.base.Taggable;

public class EventCommandDto extends EventDto implements CommandBean, Taggable
{
	private String tags;
	
	private String inStartDate;
	private String inEndDate;
	
	private String inStartTime;
	private String inEndTime;

	private String startDay;
	private String startMonth;
	private int startYear;        
	private String startHour;
	private String startMin;

	private String endDay;
	private String endMonth;
	private int endYear;
	private String endHour;
	private String endMin;

	private TreeMap popularTags;

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getYearOptions() {

		/* Return string with current year + next 2 years*/
		Calendar cal = new GregorianCalendar();
		String yearOptions = "";
		int year= cal.get(Calendar.YEAR);

		for (int i=0; i<3; i++) {
			if (i >0) yearOptions += ",";
			yearOptions += year + i;
		}

		return yearOptions;

	}

	public String getEndDay()
	{
		return endDay;
	}

	public void setEndDay(String endDay)
	{
		this.endDay = endDay;
	}

	public String getEndMonth()
	{
		return endMonth;
	}

	public void setEndMonth(String endMonth)
	{
		this.endMonth = endMonth;
	}

	public int getEndYear()
	{
		return endYear;
	}

	public void setEndYear(int endYear)
	{
		this.endYear = endYear;
	}

	public String getStartDay()
	{
		return startDay;
	}

	public void setStartDay(String startDay)
	{
		this.startDay = startDay;
	}

	public String getStartMonth()
	{
		return startMonth;
	}

	public void setStartMonth(String startMonth)
	{
		this.startMonth = startMonth;
	}

	public int getStartYear()
	{
		return startYear;
	}

	public void setStartYear(int startYear)
	{
		this.startYear = startYear;
	}

	public String getEndHour()
	{
		return endHour;
	}

	public void setEndHour(String endHour)
	{
		this.endHour = endHour;
	}

	public String getEndMin()
	{
		return endMin;
	}

	public void setEndMin(String endMin)
	{
		this.endMin = endMin;
	}

	public String getStartHour()
	{
		return startHour;
	}

	public void setStartHour(String startHour)
	{
		this.startHour = startHour;
	}

	public String getStartMin()
	{
		return startMin;
	}

	public void setStartMin(String startMin)
	{
		this.startMin = startMin;
	}

	public void setStartDateTimeForEvent(String inDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timef = new SimpleDateFormat("HH:mm a");
		try {
			Date date = sdf.parse(inDate);
			String returnDate = datef.format(date);
			this.inStartDate = returnDate;
			String returnTime = timef.format(date);
			this.inStartTime = returnTime;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void setEndDateTimeForEvent(String inDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timef = new SimpleDateFormat("HH:mm a");
		try {
			Date date = sdf.parse(inDate);
			String returnDate = datef.format(date);
			this.inEndDate = returnDate;
			String returnTime = timef.format(date);
			this.inEndTime = returnTime;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public Date getStartAsDate() {

		DateFormat formatter = new SimpleDateFormat("dd MMM yyyy/kk:mm");
		Date date;
		try {
			date = (Date)formatter.parse(getStartDay() + " " + getStartMonth() + " " + getStartYear() + "/" + 
					getStartHour() + ":" + getStartMin());
		} catch (ParseException e) {
			return null;
		}

		return date;                      

	}

	public Date getEndAsDate() {

		DateFormat formatter = new SimpleDateFormat("dd MMM yyyy/kk:mm");
		Date date;
		try {
			date = (Date)formatter.parse(getEndDay() + " " + getEndMonth() + " " + getEndYear() + "/" + 
					getEndHour() + ":" + getEndMin());
		} catch (ParseException e) {
			return null;
		}

		return date;

	}

	public  int convertMonthToInt(String month) {

		// TODO: Month string values should be constants used here and in JSP 
		if (month.equals("Jan")) return Calendar.JANUARY;
		if (month.equals("Feb")) return Calendar.FEBRUARY;
		if (month.equals("Mar")) return Calendar.MARCH;
		if (month.equals("Apr")) return Calendar.APRIL;
		if (month.equals("May")) return Calendar.MAY;
		if (month.equals("Jun")) return Calendar.JUNE;
		if (month.equals("Jul")) return Calendar.JULY;
		if (month.equals("Aug")) return Calendar.AUGUST;
		if (month.equals("Sep")) return Calendar.SEPTEMBER;
		if (month.equals("Oct")) return Calendar.OCTOBER;
		if (month.equals("Nov")) return Calendar.NOVEMBER;
		if (month.equals("Dec")) return Calendar.DECEMBER;
		return 9999;

	}

	public TreeMap getPopularTags()
	{
		return popularTags;
	}

	public void setPopularTags(TreeMap popularTags)
	{
		this.popularTags = popularTags;
	}

	public String getInStartDate() {
		return inStartDate;
	}

	public void setInStartDate(String inStartDate) {
		this.inStartDate = inStartDate;
	}

	public String getInEndDate() {
		return inEndDate;
	}

	public void setInEndDate(String inEndDate) {
		this.inEndDate = inEndDate;
	}

	public String getInStartTime() {
		return inStartTime;
	}

	public void setInStartTime(String inStartTime) {
		this.inStartTime = inStartTime;
	}

	public String getInEndTime() {
		return inEndTime;
	}

	public void setInEndTime(String inEndTime) {
		this.inEndTime = inEndTime;
	}
}
