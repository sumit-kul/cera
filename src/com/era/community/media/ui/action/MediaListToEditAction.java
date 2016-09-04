package com.era.community.media.ui.action;

import java.io.Writer;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.database.EntityWrapper;
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.doclib.dao.Document;
import com.era.community.doclib.dao.DocumentFinder;
import com.era.community.doclib.dao.DocumentLibrary;
import com.era.community.doclib.dao.DocumentLibraryFeature;
import com.era.community.doclib.dao.generated.DocumentEntity;
import com.era.community.media.dao.AlbumFinder;
import com.era.community.media.dao.PhotoFinder;
import com.era.community.media.dao.generated.PhotoEntity;
import com.era.community.media.ui.dto.AlbumDto;
import com.era.community.pers.dao.User;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/showMediaToEdit.ajx" 
 */
public class MediaListToEditAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private PhotoFinder photoFinder;
	private AlbumFinder albumFinder;
	private DocumentLibraryFeature doclibFeature;
	private DocumentFinder documentFinder;
	private CommunityFinder communityFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data; 
		CommunityEraContext context = contextManager.getContext();
		HttpServletResponse resp = context.getResponse();
		User currentUser = context.getCurrentUser();
		QueryScroller scroller = null;
		IndexedScrollerPage page = null;
		JSONObject json = new JSONObject();
		JSONArray grpLst = new JSONArray();
		try {
			if (cmd.getOwnerId() > 0 && "media".equalsIgnoreCase(cmd.getType())) {
				if (cmd.getPage() == 1 || cmd.getAlbumId() == 0) {
					scroller = photoFinder.getIndexedPhotosForUser(cmd.getOwnerId());
					scroller.setBeanClass(RowBean.class, this);
					scroller.setPageSize(8);
					page = scroller.readPage(cmd.getPage());
					JSONObject grp = toJsonString(page);
					grp.put("pgCount", scroller.readPageCount());
					grp.put("photoCount", scroller.readRowCount());
					grp.put("pNumber", cmd.getPage());
					grp.put("photoRemain", scroller.readRowCount() - cmd.getPage()*8);
					grp.put("albTitle", "Your Photos");
					grp.put("albId", 0);
					if (cmd.getPage() > 1 && cmd.getAlbumId() == 0) { //your all photo case
						grpLst.add(grp);
					} else if(cmd.getPage() == 1 ){
						grpLst.add(grp);
					}
				}
				if (cmd.getPage() == 1 || cmd.getAlbumId() > 0) {
					List<AlbumDto> albumList = albumFinder.listAlbumsForUser(cmd.getOwnerId(), cmd.getAlbumId());
					for (AlbumDto albumDto : albumList) {
						scroller = photoFinder.getPhotosToEditForUser(cmd.getOwnerId(), albumDto.getId());
						scroller.setBeanClass(RowBean.class, this);
						scroller.setPageSize(8);
						page = scroller.readPage(cmd.getPage());
						JSONObject albGrp = toJsonString(page);
						albGrp.put("pgCount", scroller.readPageCount());
						albGrp.put("photoCount", scroller.readRowCount());
						albGrp.put("pNumber", cmd.getPage());
						albGrp.put("photoRemain", scroller.readRowCount() - cmd.getPage()*8);
						albGrp.put("albTitle", albumDto.getTitle());
						albGrp.put("albId", albumDto.getId());
						if (cmd.getPage() > 1 && cmd.getAlbumId() > 0) { //your album case
							grpLst.add(albGrp);
						} else if(cmd.getPage() == 1 ){
							grpLst.add(albGrp);
						}
					}
				}
				json.put("pList", grpLst);
				String jsonString = json.serialize();
				resp.setContentType("text/json");
				Writer out = resp.getWriter();
				out.write(jsonString);
				out.close();
			}
			if (cmd.getOwnerId() > 0 && "community".equalsIgnoreCase(cmd.getType())) {
				if (cmd.getPage() == 1 || cmd.getLibraryId() > 0) {
					List<Community> list = communityFinder.getActiveCommunitiesForMember(currentUser);
					if (list != null) {
						for (Community community : list) {
							DocumentLibrary lib = (DocumentLibrary)doclibFeature.getFeatureForCommunity(community);
							scroller = documentFinder.getImagesForLibrary(lib.getId(), 0);
							scroller.setBeanClass(CommunityRowBean.class, this);
							scroller.setPageSize(8);
							page = scroller.readPage(cmd.getPage());
							JSONObject commImgs = toJsonString(page);
							commImgs.put("pgCount", scroller.readPageCount());
							commImgs.put("photoCount", scroller.readRowCount());
							commImgs.put("pNumber", cmd.getPage());
							commImgs.put("photoRemain", scroller.readRowCount() - cmd.getPage()*8);
							commImgs.put("communityName", community.getName());
							commImgs.put("libId", lib.getId());
							grpLst.add(commImgs);
						}
					}
				}
				json.put("pList", grpLst);
				String jsonString = json.serialize();
				resp.setContentType("text/json");
				Writer out = resp.getWriter();
				out.write(jsonString);
				out.close();
			}
		} catch (Exception e) {
		}
		return null;
	}

	public class RowBean extends PhotoEntity implements EntityWrapper
	{
		private int resultSetIndex;
		private int level = 1;

	}
	
	public class CommunityRowBean extends DocumentEntity implements EntityWrapper
	{
		private int resultSetIndex;
		private int level = 1;

	}

	private JSONObject toJsonString(IndexedScrollerPage page) throws Exception
	{
		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();
		for (Object o : page) {
			JSONObject row = new JSONObject();
			for (Method m : o.getClass().getMethods()) {
				String name = m.getName(); 
				if (!(name.startsWith("get") || name.startsWith("is"))) continue;
				if (name.equals("getClass")) continue;
				if (name.startsWith("get")) {
					row.put(name.substring(3,4).toLowerCase()+name.substring(4), m.invoke(o, new Object[] {}));
				}
				if (name.startsWith("is"))
					row.put(name.substring(2,3).toLowerCase()+name.substring(3), m.invoke(o, new Object[] {}));
			}            
			data.add(row);
		}
		json.put("aData", data);
		return json;
	}

	private JSONObject toJsonStringDocument(List<Document> photos) throws Exception
	{
		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();
		for (Document photo : photos) {
			JSONObject row = new JSONObject();
			row.put("id", photo.getId());
			row.put("title", photo.getTitle());
			row.put("width", photo.getWidth());
			row.put("height", photo.getHeight());
			row.put("description", photo.getDescription());
			row.put("userId", photo.getPosterId());
			data.add(row);
		}
		json.put("aData", data);
		return json;
	}

	public class Command extends CommandBeanImpl implements CommandBean
	{
		private int ownerId;
		private String type;
		private int libraryId;
		private int albumId;
		private int itemId;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public int getOwnerId() {
			return ownerId;
		}

		public void setOwnerId(int ownerId) {
			this.ownerId = ownerId;
		}

		public int getAlbumId() {
			return albumId;
		}

		public void setAlbumId(int albumId) {
			this.albumId = albumId;
		}

		public int getItemId() {
			return itemId;
		}

		public void setItemId(int itemId) {
			this.itemId = itemId;
		}

		public int getLibraryId() {
			return libraryId;
		}

		public void setLibraryId(int libraryId) {
			this.libraryId = libraryId;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public PhotoFinder getPhotoFinder() {
		return photoFinder;
	}

	public void setPhotoFinder(PhotoFinder photoFinder) {
		this.photoFinder = photoFinder;
	}

	public void setDocumentFinder(DocumentFinder documentFinder) {
		this.documentFinder = documentFinder;
	}

	public void setDoclibFeature(DocumentLibraryFeature doclibFeature) {
		this.doclibFeature = doclibFeature;
	}

	public void setAlbumFinder(AlbumFinder albumFinder) {
		this.albumFinder = albumFinder;
	}

	public void setCommunityFinder(CommunityFinder communityFinder) {
		this.communityFinder = communityFinder;
	}
}