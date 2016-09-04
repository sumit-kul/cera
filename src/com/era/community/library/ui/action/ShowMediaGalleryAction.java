package com.era.community.library.ui.action;

import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.database.EntityWrapper;
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.IndexCommandBean;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.doclib.dao.DocumentCommentFinder;
import com.era.community.doclib.dao.DocumentFinder;
import com.era.community.doclib.dao.DocumentLibrary;
import com.era.community.doclib.dao.DocumentLibraryFeature;
import com.era.community.doclib.dao.Folder;
import com.era.community.doclib.dao.FolderFinder;
import com.era.community.doclib.dao.generated.DocumentEntity;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.ibm.json.java.JSONObject;

/** 
 * @spring.bean name="/cid/[cec]/library/mediaGallery.do"
 */
public class ShowMediaGalleryAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected DocumentLibraryFeature doclibFeature;
	protected SubscriptionFinder subscriptionFinder;
	protected DocumentCommentFinder documentCommentFinder;
	protected DocumentFinder documentFinder;
	protected FolderFinder folderFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		DocumentLibrary lib = (DocumentLibrary)doclibFeature.getFeatureForCommunity(context.getCurrentCommunity());
		if (lib == null) {
			return new ModelAndView("/pageNotFound");
		}else {
			cmd.setLibraryId(lib.getId());
		}

		String pageNum = context.getRequest().getParameter("jPage");
		int pNumber = 0;
		if (pageNum != null && !"".equals(pageNum)) {
			pNumber = Integer.parseInt(pageNum);
		}
		QueryScroller scroller = documentFinder.getImagesForLibrary(lib.getCommunityId(), cmd.getFolderId());
		if (pageNum != null) {
			HttpServletResponse resp = contextManager.getContext().getResponse();
			JSONObject json = null;
			if (pNumber > 0) {
				scroller.setBeanClass(RowBean.class, this);
				scroller.setPageSize(cmd.getPageSize());
				IndexedScrollerPage page = scroller.readPage(pNumber);
				json = page.toJsonString(pNumber);
				json.put("userSysAdmin", Boolean.toString(context.isUserSysAdmin()));
				cmd.setPage(cmd.getPage() + 1);
			} 
			String jsonString = json.serialize();
			resp.setContentType("text/json");
			Writer out = resp.getWriter();
			out.write(jsonString);
			out.close();
			return null;
		} else {
			scroller.setPageSize(cmd.getPageSize());
			cmd.setPageCount(scroller.readPageCount());
			cmd.setRowCount(scroller.readRowCount());
			scroller.setBeanClass(RowBean.class, this);
			cmd.setScrollerPage(scroller.readPage(cmd.getPage()));
			cmd.setPage(cmd.getPage() + 1);
			cmd.setSearchType("File");
			cmd.setQueryText(lib.getName());
		}
		return new ModelAndView("library/showMediaGallery");
	}

	public class Command extends IndexCommandBeanImpl implements IndexCommandBean
	{
		private List photoItems;
		private int libraryId;
		private int folderId;

		public boolean isMember() throws Exception
		{
			if (contextManager.getContext().getCurrentUser() == null ) return false;
			if (contextManager.getContext().isUserAuthenticated() == false) return false;
			Community community = contextManager.getContext().getCurrentCommunity();
			if (community == null) return false;
			int currentUser = contextManager.getContext().getCurrentUser().getId();
			return community.isMember(currentUser);
		}

		public List getPhotoItems() {
			return photoItems;
		}

		public void setPhotoItems(List photoItems) {
			this.photoItems = photoItems;
		}

		public int getLibraryId() {
			return libraryId;
		}

		public void setLibraryId(int libraryId) {
			this.libraryId = libraryId;
		}

		public int getFolderId() {
			return folderId;
		}

		public void setFolderId(int folderId) {
			this.folderId = folderId;
		}
	}

	public class RowBean extends DocumentEntity implements EntityWrapper
	{
		private int resultSetIndex;
		private String posterName;
		private int documentCommentCount;
		private boolean moveToAlbumAllowed;

		public int getResultSetIndex()
		{
			return resultSetIndex;
		}

		public void setResultSetIndex(int resultSetIndex)
		{
			this.resultSetIndex = resultSetIndex;
		}

		public boolean isEvenRow()
		{
			return resultSetIndex%2==0;
		}

		public boolean isOddRow()
		{
			return resultSetIndex%2==1;
		}

		public int getDocumentCommentCount() throws Exception
		{
			return documentCommentFinder.getDocumentCommentCountForDocument(getId());
		}

		public void setDocumentCommentCount(int documentCommentCount)
		{
			this.documentCommentCount = documentCommentCount;
		}

		public boolean isMoveToAlbumAllowed() {
			boolean move = false;
			if (this.getFolderId() > 0) {
				try {
					Folder folder = folderFinder.getFolderForId(this.getFolderId());
					if (!(folder.getBannerFolder() == 1 || folder.getProfileFolder() == 1)) {
						move = true;
					}
				} catch (Exception e) {
				}
			} else {
				move = true;
			}
			return move;
		}

		public void setMoveToAlbumAllowed(boolean moveToAlbumAllowed) {
			this.moveToAlbumAllowed = moveToAlbumAllowed;
		}

		public String getPosterName() {
			return posterName;
		}

		public void setPosterName(String posterName) {
			this.posterName = posterName;
		}
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final void setDoclibFeature(DocumentLibraryFeature doclibFeature)
	{
		this.doclibFeature = doclibFeature;
	}

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder)
	{
		this.subscriptionFinder = subscriptionFinder;
	}

	public void setDocumentCommentFinder(DocumentCommentFinder documentCommentFinder)
	{
		this.documentCommentFinder = documentCommentFinder;
	}

	public void setDocumentFinder(DocumentFinder documentFinder) {
		this.documentFinder = documentFinder;
	}

	public void setFolderFinder(FolderFinder folderFinder) {
		this.folderFinder = folderFinder;
	}
}