package support.community.framework.taglib;

/**
 *  Does nothing but include the body. The point if the tag is that
 *  the body will be suppressed if tag gets hidden by hide-when attributes. 
 */
public class BlockTag extends AbstractFormTag 
{
    protected int doStartTagInternal() throws Exception 
    {
        return EVAL_BODY_INCLUDE;
    }

}
