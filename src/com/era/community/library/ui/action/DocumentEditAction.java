package com.era.community.library.ui.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandValidator;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.Taggable;
import com.era.community.doclib.dao.Document;
import com.era.community.doclib.dao.DocumentFinder;
import com.era.community.doclib.dao.DocumentLibraryFinder;
import com.era.community.doclib.ui.dto.DocumentDto;
import com.era.community.doclib.ui.validator.DocumentValidator;

/**
 * @spring.bean name="/cid/[cec]/library/editDocument.do"
 */
public class DocumentEditAction extends AbstractFormAction
{
	private CommunityEraContextManager contextManager;
	protected DocumentLibraryFinder doclibFinder;
	private DocumentFinder documentFinder;

	protected CommandValidator createValidator()
	{
		return new Validator();
	}

	protected String getView()
	{
		return "library/editDocument";
	}

	protected void onDisplay(Object data) throws Exception
	{
		Command cmd = (Command) data;
		Document doc = documentFinder.getDocumentForId(cmd.getId());
		cmd.copyPropertiesFrom(doc);
		cmd.setSearchType("File");
        cmd.setQueryText(doc.getTitle());
	}

	protected ModelAndView onSubmit(Object data) throws Exception
	{
		Command cmd = (Command) data;
		Document doc = documentFinder.getDocumentForId(cmd.getId());
		cmd.copyRequestDataTo(doc); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String dt = sdf.format(now);
		Timestamp ts = Timestamp.valueOf(dt);
		doc.setDateRevised(ts);
		doc.setModified(ts);
		doc.update();

		MultipartFile file = cmd.getUpload();
		if (file != null && !file.isEmpty()) {
			doc.storeFile(file);
			doc.setRevisionNumber(doc.getRevisionNumber() + 1);
			doc.setDateRevised(ts);
			doc.setFileName(file.getOriginalFilename());
			doc.update();
		}
		CommunityEraContext context = contextManager.getContext();
		return new ModelAndView("redirect:" + context.getCurrentCommunityUrl() + "/library/documentdisplay.do?id="+ doc.getId());
	}

	public  class Command extends DocumentDto implements CommandBean, Taggable
	{
		public TreeMap getPopularTags()
		{
			return null;
		}

		/*public final List<String> getThemeOptions() throws Exception
		{
			CommunityEraContext context = contextManager.getContext();
			return  doclibFinder.getLibraryThemeOptionList(context.getCurrentCommunity().getId());
		}*/
	}

	public class Validator extends DocumentValidator
	{

	}

	public final CommunityEraContextManager getContextHolder()
	{
		return contextManager;
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final void setDocumentFinder(DocumentFinder documentFinder)
	{
		this.documentFinder = documentFinder;
	}

	public final void setDoclibFinder(DocumentLibraryFinder doclibFinder)
	{
		this.doclibFinder = doclibFinder;
	}
}
