package com.era.community.pers.ui.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.framework.RunAsServerCallback;

import com.era.community.base.CommunityFeature;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;

/**
 * @spring.bean name="/test/test.do"
 */
public class TestController extends AbstractCommandAction
{
    private CommunityEraContextManager contextManager;
    private UserFinder userFinder;
    private CommunityFinder communityFinder;

    public ModelAndView handleRequestInternal2() throws Exception 
    {
       
        User user = userFinder.newUser();
        user.setFirstName("Boot");
        user.setLastName("User");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date now = new Date();
    	String dt = sdf.format(now);
    	Timestamp ts = Timestamp.valueOf(dt);
        user.setDateRegistered(ts);
        user.setEmailAddress("boot2@abc.com");
        user.setPassword("waterloo");
        //user.setCommunityCreator(true);
        user.setSystemAdministrator(true);
        user.setValidated(true);
        user.update();
        
/*
        user = userFinder.getUserForId(10103); 
        logger.debug("xxxxgwfgfwgfwgfwgfgwfgwfgwfwg"+user.getClass());
        user.update();
        user.setInactive(true);
        user.setEmailAddress(null);
        user.update();
        
        byte[] data = new byte[] {  1, 2 ,3, 4 };
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        user.storePhoto(in, data.length);
     
        user.setEmailAddress("support@jhapak.com");
        user.update();
         
         InputStream is = user.getPhoto();
         for (;;) {
           int b = is.read();
           if (b<0) break;
           logger.debug("byte:"+b);
         }
   */  
    //    userFinder.deleteUserForId(260);
        

        
        return null;
    }


    private void enableFeatures(Community comm) throws Exception
    {
        Iterator i = contextManager.getContext().getFeatures().iterator();
        while (i.hasNext()) {
            CommunityFeature f = (CommunityFeature)i.next();
            f.setFeatureEnabledForCommunity(comm, true);
        }
            
        
        
    }
    
    /**
     * @param userFinder The userFinder to set.
     */
    public void setUserFinder(UserFinder userFinder)
    {
        this.userFinder = userFinder;
    }

    public final void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }

    public final void setCommunityFinder(CommunityFinder communityFinder)
    {
        this.communityFinder = communityFinder;
    }

    protected ModelAndView handle(Object data) throws Exception
    {
        return (ModelAndView)getRunServerAsTemplate().execute(new RunAsServerCallback() {
            public Object doInSecurityContext() throws Exception
            {
                return handleRequestInternal2();
            }
        });
    }
    
    public static class Command extends CommandBeanImpl implements CommandBean
    {
        
    }
    
    

}
