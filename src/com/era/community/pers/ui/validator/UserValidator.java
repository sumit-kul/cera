package com.era.community.pers.ui.validator; 

import org.springframework.web.multipart.MultipartFile;

import support.community.framework.CommandBeanImpl;

public class UserValidator extends com.era.community.pers.dao.generated.UserValidator 
{

    public String validateEmailAddress(Object value, CommandBeanImpl cmd) throws Exception
    {
    	if (isNullOrWhitespace(value) || value.toString().equals("E-mail Address")) return "Please enter Email address";
        if (!emailValidator.isValid(value.toString())) return "Email address is invalid";
        
        if (value.toString()!=null && value.toString().trim().length()> 120)
            return ("Email address length cannot exceed 120 characters");
        
        return null;
    }

    /*
     * FirstName field is mandatory
     */
    public String validateFirstName(Object value, CommandBeanImpl cmd) throws Exception
    {
        if (isNullOrWhitespace(value) || value.toString().equals("First Name")) return "Please enter your first name";
        
        if (value.toString().trim().length()> 80)
            return ("First name length cannot exceed 80 characters");
        
        return null;
    }
    
    public String validateLastName(Object value, CommandBeanImpl cmd) throws Exception
    {
        if (isNullOrWhitespace(value) || value.toString().equals("Last Name")) return "Please enter your last name";
        
        if ( value.toString().trim().length()> 80)
            return ("Last name length cannot exceed 80 characters");
        
        return null;
    }
    
    public String validateOrganisation(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 120)
            return ("Organisation length cannot exceed 120 characters");
        
        return null;
    }
    
    public String validatePassword(Object value, CommandBeanImpl cmd) throws Exception
    {
        if (isNullOrWhitespace(value) || value.toString().equals("Password")) return "Please enter a password";
        if (value.toString().length()<6) return "Please enter a password of at least 6 characters";
        if (value.toString().trim().length()> 12) return "Password length cannot exceed 12 characters";
        return null;
    }
        
    public String validatePhoto(Object value, CommandBeanImpl data) throws Exception
    {
        if (value==null) return null;

        if (!(value instanceof MultipartFile))
            return "Value is "+value.getClass().getName()+" when a MutipartFile is expected. The enclosing form tag may not be set to multipart";
        
        MultipartFile photo = (MultipartFile)value;
        if (photo.isEmpty()) return null;
        
        if (photo.getSize()>50*1024) return "Image is too large. Please select an image no larger than 50k.";
        
        if (!photo.getContentType().equalsIgnoreCase("image/jpeg")
             && !photo.getContentType().equalsIgnoreCase("image/pjpeg")
             && !photo.getContentType().equalsIgnoreCase("image/gif")
             && !photo.getContentType().equalsIgnoreCase("image/png")
        )
        return "Invalid image type. Please select a JPEG, GIF or PNG image.";

        return null;
   }
    
    public String validateWebSiteAddress(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 120)
            return ("Website length cannot exceed 120 characters");

        return null;
    }
    
    public String validateJobTitle(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 60)
            return ("Job title length cannot exceed 60 characters");

        return null;
    }
       
}
