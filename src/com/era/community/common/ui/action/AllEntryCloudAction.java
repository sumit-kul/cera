package com.era.community.common.ui.action;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.User;
import com.era.community.tagging.dao.TagFinder;
import com.era.community.tagging.ui.dto.TagDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/common/allEntryCloud.ajx"
 */
public class AllEntryCloudAction extends AbstractCommandAction 
{
    protected CommunityEraContextManager contextManager;
    protected TagFinder tagFinder;
        
    protected ModelAndView handle(Object data) throws Exception
    {
        Command cmd = (Command) data;
        CommunityEraContext context = contextManager.getContext();
        User currentUser = context.getCurrentUser();
        
        List tagList = new ArrayList();
        tagList = tagFinder.getItemTagsForToolIdAndType(cmd.getToolId(), 30, cmd.getToolType());
        
        HttpServletResponse resp = context.getResponse();
        JSONObject json = new JSONObject();
        JSONArray aData = new JSONArray();
        JSONArray bData = new JSONArray();
        
        TreeMap map = getMostPopularTagsSorted(tagList);
        for (Iterator iterator = map.values().iterator(); iterator.hasNext();) {
			TagDto type = (TagDto) iterator.next();
			aData.add(toJsonString(type.getTagText(), type.getCount().intValue(), type.getCloudSet()));
		}
        json.put("aData", aData);
        
        for (Iterator iterator = tagList.iterator(); iterator.hasNext();) {
        	TagDto type = (TagDto) iterator.next();
			bData.add(toJsonString(type.getTagText(), type.getCount().intValue(), 0));
		}
        json.put("bData", bData);
        
		String jsonString = json.serialize();
		resp.setContentType("text/json");
		Writer out = resp.getWriter();
		out.write(jsonString);
		out.close();
		return null;
    }

    private TreeMap getMostPopularTagsSorted(List tagList) throws Exception
    {
        // TreeMap stores its elements in a tree and orders its elements based on their values
        TreeMap<String, TagDto> treemap = new TreeMap<String, TagDto>();      
        int maxPopularity = 0;
        int minPopularity = 0;
        int noOfSets = 6; // This is the number of different visual styles

        for (int i = 0; i < tagList.size(); i++) {
        	TagDto tag = (TagDto) tagList.get(i);
            if (tag.getCount() > maxPopularity) {
                maxPopularity = tag.getCount().intValue();
            }
            if (tag.getCount() < minPopularity || minPopularity == 0) {
                minPopularity = tag.getCount().intValue();
            }
        }

        int setSize = (int) Math.round((double) (maxPopularity - minPopularity) / noOfSets);
        if (setSize == 0) {
            setSize = 1;
        }
        for (int i = 0; i < tagList.size(); i++) {
        	TagDto tag = (TagDto) tagList.get(i);
            int cloudSet = Math.min(noOfSets, ((tag.getCount().intValue() / setSize) - minPopularity) + 1);
            tag.setCloudSet(cloudSet);
            treemap.put(tag.getTagText(), tag);
        }
        return treemap;
    }
    
    private JSONObject toJsonString(String tagText, int count, int cloudSet) throws Exception
    {
    	JSONObject row = new JSONObject();
    	row.put("tagText", tagText);
    	row.put("count", count);
    	row.put("cloudSet", cloudSet);
    	return row;
    }
        
    public class Command extends IndexCommandBeanImpl implements CommandBean
    {
        private TreeMap popularTags;
        private String toolType;
        private int toolId;

        public final TreeMap getPopularTags()
        {
            return popularTags;
        }

        public final void setPopularTags(TreeMap popularTags)
        {
            this.popularTags = popularTags;
        }

		public String getToolType() {
			return toolType;
		}

		public void setToolType(String toolType) {
			this.toolType = toolType;
		}

		public int getToolId() {
			return toolId;
		}

		public void setToolId(int toolId) {
			this.toolId = toolId;
		}
    }

    public void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }

    public void setTagFinder(TagFinder tagFinder)
    {
        this.tagFinder = tagFinder;
    }
}