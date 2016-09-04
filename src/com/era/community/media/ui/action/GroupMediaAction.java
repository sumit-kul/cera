package com.era.community.media.ui.action;

import java.io.Writer;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.doclib.dao.FolderFinder;
import com.era.community.doclib.ui.dto.FolderDto;
import com.era.community.media.dao.AlbumFinder;
import com.era.community.media.ui.dto.AlbumDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/showGroupMedia.ajx" 
 */
public class GroupMediaAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private AlbumFinder albumFinder;
	private FolderFinder folderFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data; 
		CommunityEraContext context = contextManager.getContext();
		HttpServletResponse resp = context.getResponse();
		try {
			if (cmd.getOwnerId() > 0) {
				if ("album".equals(cmd.getType())) {
					List<AlbumDto> albums = albumFinder.listAlbumsForUser(cmd.getOwnerId(), cmd.getAlbumId());
					int rowCount = albums.size();
					int pgCount = rowCount==0 ? 0 : ((int)((rowCount-1)/10))+2;
					JSONObject json = toJsonString(albums);
					json.put("pgCount", pgCount);
					String jsonString = json.serialize();
					resp.setContentType("text/json");
					Writer out = resp.getWriter();
					out.write(jsonString);
					out.close();
				}
			}
			if (cmd.getLibraryId() > 0) {
				if ("album".equals(cmd.getType())) {
					List<FolderDto> folders = folderFinder.listAlbumsForLibrary(cmd.getLibraryId(), cmd.getFolderId());
					int rowCount = folders.size();
					int pgCount = rowCount==0 ? 0 : ((int)((rowCount-1)/10))+2;
					JSONObject json = toJsonStringForFolder(folders);
					json.put("pgCount", pgCount);
					String jsonString = json.serialize();
					resp.setContentType("text/json");
					Writer out = resp.getWriter();
					out.write(jsonString);
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private JSONObject toJsonString(List<AlbumDto> albums) throws Exception
	{
		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();
		for (AlbumDto album : albums) {
			JSONObject row = new JSONObject();
			for (Method m : album.getClass().getMethods()) {
				String name = m.getName(); 
				if (name.startsWith("get")) {
					if (name.equals("getClass")) continue;
					row.put(name.substring(3,4).toLowerCase()+name.substring(4), m.invoke(album, new Object[] {}));
				}            
			}
			data.add(row);
		}
		json.put("aData", data);
		return json;
	}

	private JSONObject toJsonStringForFolder(List<FolderDto> folders) throws Exception
	{
		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();
		for (FolderDto folder : folders) {
			JSONObject row = new JSONObject();
			for (Method m : folder.getClass().getMethods()) {
				String name = m.getName(); 
				if (name.startsWith("get")) {
					if (name.equals("getClass")) continue;
					row.put(name.substring(3,4).toLowerCase()+name.substring(4), m.invoke(folder, new Object[] {}));
				}            
			}
			data.add(row);
		}
		json.put("aData", data);
		return json;
	}

	public class Command extends CommandBeanImpl implements CommandBean
	{
		private int ownerId;
		private String type;
		private int albumId;
		private int folderId;
		private int libraryId;

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

		public int getFolderId() {
			return folderId;
		}

		public void setFolderId(int folderId) {
			this.folderId = folderId;
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

	public void setAlbumFinder(AlbumFinder albumFinder) {
		this.albumFinder = albumFinder;
	}

	public void setFolderFinder(FolderFinder folderFinder) {
		this.folderFinder = folderFinder;
	}
}