package com.era.community.admin.ui.action;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.admin.dao.BusinessParam;
import com.era.community.admin.dao.BusinessParamFinder;
import com.era.community.admin.ui.dto.BusinessParamDto;
import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;

/**
 *
 * Update a business parameter via an ajax call from an inline edit operation.
* @spring.bean name="/admin/param-inline-edit.do"
 */

public class ParamInlineEditAction extends AbstractCommandAction
{
    /* Injected */
 //   protected AppRequestContextHolder contextManager;
    protected CommunityEraContextManager contextManager;
    protected BusinessParamFinder paramFinder;


    @Override
    protected ModelAndView handle(Object data) throws IOException
    {
        CommunityEraContext context = contextManager.getContext();
        HttpServletResponse resp = context.getResponse();
        resp.setContentType("text/html");

        String result;
        try {
            result = updateParameter(data);
            resp.setStatus(200);
        }
        catch (Throwable t) {
            resp.setStatus(500);
            result = t.toString();
        }
        
        resp.setContentLength(result.length());
        Writer out = resp.getWriter();
        out.write(result);
        out.close();
        
        return null;
    }

    private String updateParameter(Object data) throws Exception
    {
        Command bean = (Command) data;
        
        BusinessParam param;
        
        try {
            param = paramFinder.getParamForCategoryAndName(BusinessParam.CATEGORY_STATIC_TEXT, bean.getFieldname());
        }
        catch (ElementNotFoundException x) {
            param = paramFinder.newBusinessParam();
            param.setCategory(BusinessParam.CATEGORY_STATIC_TEXT);
            param.setName(bean.getFieldname());
            param.setDescription("");
        }
        
        param.setValue(bean.getContent());     
        param.update();

        return param.getValue();
    }

    public static class Command extends BusinessParamDto implements CommandBean 
    {
        private String fieldname;
        private String content;
        
        public final String getContent()
        {
            return content;
        }
        public final void setContent(String content)
        {
            this.content = content;
        }
        public final String getFieldname()
        {
            return fieldname;
        }
        public final void setFieldname(String field)
        {
            this.fieldname = field;
        }
    }

            

    
    public void setParamFinder(BusinessParamFinder paramFinder)
    {
        this.paramFinder = paramFinder;
    }

    public final void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }


}
