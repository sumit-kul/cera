package com.era.community.base;

import java.text.DecimalFormat;

public class FileManager
{
	private FileManager()
	{
	}

	/**
	 * @return String Relative URI to image 
	 */
	public static String  computeIconImage(String contentType) {

		String base = "/img/contenticon/"; /* Base for icon images*/
		String retVal = "fileicon.gif"; /* Default icon if content type not recognised */

		if (contentType != null) {

			//PDF
			if (contentType.equals("application/pdf")) {
				retVal= "pdficon.gif";
			}

			//Images 
			if (contentType.equals("image/jpeg") || 
					contentType.equals("image/pjpeg") ||
					contentType.equals("image/gif") ||
					contentType.equals("image/x-png") ) {
				retVal= "imgicon.gif";
			}

			//MS Excel
			if (contentType.equals("application/vnd.ms-excel")) {
				retVal= "excelicon.gif";
			}

			//MS PowerPoint
			if (contentType.equals("application/vnd.ms-powerpoint")) {
				retVal= "powerpointicon.gif";
			}

			//MS Word
			if (contentType.equals("application/msword")) {
				retVal= "wordicon.gif";
			}

			//PKZip file
			if (contentType.equals("application/zip") ||
					contentType.equals("application/x-zip-compressed") ) {
				retVal= "zipicon.gif";
			}
		}                    
		return base + retVal;
	}

	/**
	 * Returns a string showing the size in Kb for a given number of bytes.  If the size is smaller than 1Kb, 
	 * a fractional number will be returned in the form 0.0
	 * 
	 * @param sizeInBytes
	 * @return String Containing size in Kb
	 */
	public static String getSizeInKb(long sizeInBytes) {
		float size = (sizeInBytes / 1024);
		String format = "0";
		/* Show a fraction if the size is very small */ 
		if (size < 1) {
			format = "0.0";    
		}            
		DecimalFormat decFormat= new DecimalFormat(format);
		return decFormat.format(size);
	}

}
