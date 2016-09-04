package com.era.community.doclib.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.doclib.dao.DocumentComment;
import com.era.community.doclib.dao.DocumentCommentFinder;
import com.era.community.doclib.ui.dto.DocumentCommentDto;

/**
 * @spring.bean name="/cid/[cec]/doclib/delete-comment.do"
 */
public class DocumentCommentDeleteAction extends AbstractCommandAction
{
    private DocumentCommentFinder docCommentFinder;
    
    @Override
    protected ModelAndView handle(Object data) throws Exception
    {
        Command cmd = (Command) data;
        
        DocumentComment comment = docCommentFinder.getDocumentCommentForId(cmd.getId());
  
        comment.delete();
        return REDIRECT_TO_BACKLINK;
    }


    public static class Command extends DocumentCommentDto implements CommandBean 
    {
    }
    
    /* Used by Spring to inject reference */
    public void setCommentFinder(DocumentCommentFinder docCommentFinder)
    {
        this.docCommentFinder = docCommentFinder;
    }
    


}
