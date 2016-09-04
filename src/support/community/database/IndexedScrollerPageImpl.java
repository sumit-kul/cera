package support.community.database;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.era.community.doclib.ui.dto.DocumentHeaderDto;
import com.era.community.forum.dao.ForumResponse;
import com.era.community.media.ui.dto.MediaHeaderDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

public class IndexedScrollerPageImpl extends ArrayList<Object> implements IndexedScrollerPage
{
	private int m_rowCount;
	private int m_pageCount;
	private int m_scrollerPageSize;
	private int m_currentPageNumber;

	public IndexedScrollerPageImpl()
	{
		super();
	}

	public IndexedScrollerPageImpl(int n)
	{
		super(n);
	}

	public String toJsonString() throws Exception
	{
		JSONObject json = new JSONObject();

		json.put("rowCount", m_rowCount);
		json.put("pageCount", m_pageCount);
		json.put("pageSize", m_scrollerPageSize);
		json.put("pageNumber", m_currentPageNumber);

		JSONArray data = new JSONArray();
		for (Object o : this) {
			JSONObject row = new JSONObject();
			for (Method m : o.getClass().getDeclaredMethods()) {
				String name = m.getName(); 
				if (!name.startsWith("get")) continue;
				row.put(name.substring(3,4).toLowerCase()+name.substring(4), m.invoke(o, new Object[] {}));
			}            
			data.add(row);
		}
		json.put("data", data);

		return json.serialize();
	}

	public JSONObject toJsonString(int pageNumber) throws Exception
	{
		JSONObject json = new JSONObject();
		json.put("rowCount", m_rowCount);
		json.put("pageCount", m_pageCount);
		json.put("pageSize", m_scrollerPageSize);
		json.put("pageNumber", pageNumber);

		JSONArray data = new JSONArray();
		for (Object o : this) {
			JSONObject row = new JSONObject();
			for (Method m : o.getClass().getMethods()) {
				String name = m.getName(); 
				if (!(name.startsWith("get") || name.startsWith("is"))) continue;
				if (name.equals("getClass") || name.equals("getItemfilename")) continue;
				if (name.startsWith("get")) {
					if (name.equals("getAuthors")) {
						Object[] obj = new Object[] {pageNumber};
						row.put(name.substring(3,4).toLowerCase()+name.substring(4), m.invoke(o, obj));  
					} else {
						row.put(name.substring(3,4).toLowerCase()+name.substring(4), m.invoke(o, new Object[] {}));
					}
				}
				if (name.startsWith("is"))
					row.put(name.substring(2,3).toLowerCase()+name.substring(3), m.invoke(o, new Object[] {}));
			}            
			data.add(row);
		}
		json.put("aData", data);

		return json;
	}

	public JSONObject toJsonStringMyHome(int pageNumber) throws Exception
	{
		JSONObject json = new JSONObject();
		json.put("rowCount", m_rowCount);
		json.put("pageCount", m_pageCount);
		json.put("pageSize", m_scrollerPageSize);
		json.put("pageNumber", pageNumber);

		int userId = 0;
		JSONArray data = new JSONArray();
		for (Object o : this) {
			JSONObject row = new JSONObject();
			Method mt = o.getClass().getMethod("getPrepareRow");
			mt.invoke(o, new Object[] {});
			
			Method mt2 = o.getClass().getMethod("getUserId");
			userId = (Integer)mt2.invoke(o, new Object[] {});
			if (pageNumber > 1) {
				for (Method m : o.getClass().getMethods()) {
					String name = m.getName(); 
					if (!(name.startsWith("get") || name.startsWith("is"))) continue;
					if (name.equals("getClass") || name.equals("getItemfilename") || name.equals("getPrepareRow")) continue;
					if (name.equals("getPhotoList")) {
						List<DocumentHeaderDto> phlst = (List<DocumentHeaderDto>)m.invoke(o, new Object[] {});
						if (phlst != null && phlst.size() > 0) {
							JSONArray photos = new JSONArray();
							for (DocumentHeaderDto hdto : phlst) {
								JSONObject photo = new JSONObject();
								photo.put("itemUrl", "cid/"+hdto.getCommunityId()+"/library/documentdisplay.do?id="+hdto.getDocId());
								photo.put("itemBaseTitle", "/cid/"+hdto.getCommunityId()+"/library/showLibraryItems.do");
								photo.put("docId", hdto.getDocId());
								photos.add(photo);
							}
							row.put("pData", photos);
						}
					} else if (name.equals("getSecondPhotoList")) {
						List<DocumentHeaderDto> phlst = (List<DocumentHeaderDto>)m.invoke(o, new Object[] {});
						if (phlst != null && phlst.size() > 0) {
							JSONArray secPhotos = new JSONArray();
							for (DocumentHeaderDto hdto : phlst ) {
								JSONObject photo = new JSONObject();
								photo.put("itemUrl", "cid/"+hdto.getCommunityId()+"/library/documentdisplay.do?id="+hdto.getDocId());
								photo.put("itemBaseTitle", "/cid/"+hdto.getCommunityId()+"/library/showLibraryItems.do");
								photo.put("docId", hdto.getDocId());
								secPhotos.add(photo);
							}
							row.put("tData", secPhotos);
						}
					} else if (name.equals("getMediaList")) {
						List<MediaHeaderDto> phlst = (List<MediaHeaderDto>)m.invoke(o, new Object[] {});
						if (phlst != null && phlst.size() > 0) {
							JSONArray photos = new JSONArray();
							for (MediaHeaderDto hdto : phlst) {
								JSONObject photo = new JSONObject();
								photo.put("itemUrl", "pers/photoDisplay.img?id="+hdto.getDocId());
								photo.put("itemBaseTitle", "/pers/mediaGallery.do?id="+userId);
								photo.put("docId", hdto.getDocId());
								photos.add(photo);
							}
							row.put("pData", photos);
						}
					} else if (name.equals("getSecondMediaList")) {
						List<MediaHeaderDto> phlst = (List<MediaHeaderDto>)m.invoke(o, new Object[] {});
						if (phlst != null && phlst.size() > 0) {
							JSONArray secPhotos = new JSONArray();
							for (MediaHeaderDto hdto : phlst ) {
								JSONObject photo = new JSONObject();
								photo.put("itemUrl", "pers/photoDisplay.img?id="+hdto.getDocId());
								photo.put("itemBaseTitle", "/pers/mediaGallery.do?id="+userId);
								photo.put("docId", hdto.getDocId());
								secPhotos.add(photo);
							}
							row.put("tData", secPhotos);
						}
					} else {
						if (name.startsWith("get")) {
							if (name.equals("getAuthors")) {
								Object[] obj = new Object[] {pageNumber};
								row.put(name.substring(3,4).toLowerCase()+name.substring(4), m.invoke(o, obj));  
							} else {
								System.out.println("********************************"+name);
								row.put(name.substring(3,4).toLowerCase()+name.substring(4), m.invoke(o, new Object[] {}));
							}
						}
						if (name.startsWith("is"))
							row.put(name.substring(2,3).toLowerCase()+name.substring(3), m.invoke(o, new Object[] {}));
					}
				}     
			}
			data.add(row);
		}
		json.put("aData", data);

		return json;
	}

	public JSONObject toJsonStringForFiles(int folderId) throws Exception
	{
		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();
		for (Object o : this) {
			JSONObject row = new JSONObject();
			for (Method m : o.getClass().getMethods()) {
				String name = m.getName(); 
				if (!(name.startsWith("get") || name.startsWith("is")) || name.equals("getClass")) continue;
				if (name.startsWith("get")) {
					row.put(name.substring(3,4).toLowerCase()+name.substring(4), m.invoke(o, new Object[] {}));
				}
				if (name.startsWith("is")) {
					if (name.equals("isMoveToFolderAllowed")) {
						Object[] obj = new Object[] {folderId};
						row.put(name.substring(2,3).toLowerCase()+name.substring(3), m.invoke(o, obj));  
					} else {
						row.put(name.substring(2,3).toLowerCase()+name.substring(3), m.invoke(o, new Object[] {}));
					}
				} 
			}
			data.add(row);
		}
		json.put("aData", data);
		return json;
	}

	public JSONObject toJsonStringForCommunityHome(int pageNumber, String filterTag, String toggleList) throws Exception
	{
		JSONObject json = new JSONObject();
		json.put("rowCount", m_rowCount);
		json.put("pageCount", m_pageCount);
		json.put("pageSize", m_scrollerPageSize);
		json.put("pageNumber", pageNumber);

		JSONArray data = new JSONArray();
		for (Object o : this) {
			JSONObject row = new JSONObject();
			for (Method m : o.getClass().getMethods()) {
				String name = m.getName(); 
				if (!(name.startsWith("get") || name.startsWith("is"))) continue;
				if (name.equals("getClass") ) continue;
				if (name.startsWith("get")) {
					if (name.equals("getForumResponses")) {
						IndexedScrollerPage resps = (IndexedScrollerPage)m.invoke(o, new Object[] {});
						JSONArray forumResponses = new JSONArray();
						for (Iterator iterator = resps.iterator(); iterator.hasNext();) {
							ForumResponse res = (ForumResponse) iterator.next();
							JSONObject fres = new JSONObject();
							fres.put("id", res.getId());
							fres.put("topicId", res.getTopicId());
							fres.put("subject", res.getSubject());
							fres.put("body", res.getBody());
							fres.put("authorId", res.getAuthorId());
							fres.put("authorName", res.getAuthor().getFullName());
							fres.put("authorPhoto", res.getAuthor().isPhotoPresent());
							fres.put("repliedOn", res.getRepliedOn());
							fres.put("likeString", res.getLikeString());
							fres.put("likeCount", res.getLikeCount());
							forumResponses.add(fres);
						}
						row.put("forumResponses", forumResponses);
					} else if (name.equals("getTaggedKeywords")) {
						Object[] obj = new Object[] {pageNumber, filterTag, toggleList};
						row.put(name.substring(3,4).toLowerCase()+name.substring(4), m.invoke(o, obj));  
					} else {
						row.put(name.substring(3,4).toLowerCase()+name.substring(4), m.invoke(o, new Object[] {}));
					}
				}
				if (name.startsWith("is"))
					row.put(name.substring(2,3).toLowerCase()+name.substring(3), m.invoke(o, new Object[] {}));
			}            
			data.add(row);
		}
		json.put("aData", data);

		return json;
	}

	public JSONObject toCommonJsonString(int pageNumber, String filterTag, String sortOption, String toggleList) throws Exception
	{
		JSONObject json = new JSONObject();
		json.put("rowCount", m_rowCount);
		json.put("pageCount", m_pageCount);
		json.put("pageSize", m_scrollerPageSize);
		json.put("pageNumber", pageNumber);

		JSONArray data = new JSONArray();
		for (Object o : this) {
			JSONObject row = new JSONObject();
			for (Method m : o.getClass().getMethods()) {
				String name = m.getName(); 
				if (!(name.startsWith("get") || name.startsWith("is"))) continue;
				if (name.equals("getClass") ) continue;
				if (name.startsWith("get")) {
					if (name.equals("getTaggedKeywords")) {
						Object[] obj = new Object[] {pageNumber, filterTag, sortOption, toggleList};
						row.put(name.substring(3,4).toLowerCase()+name.substring(4), m.invoke(o, obj));  
					} else {
						row.put(name.substring(3,4).toLowerCase()+name.substring(4), m.invoke(o, new Object[] {}));
					}
				}
				if (name.startsWith("is"))
					row.put(name.substring(2,3).toLowerCase()+name.substring(3), m.invoke(o, new Object[] {}));
			}            
			data.add(row);
		}
		json.put("aData", data);

		return json;
	}

	public JSONObject toJsonString(int pageNumber, int userId) throws Exception
	{
		JSONObject json = new JSONObject();
		json.put("rowCount", m_rowCount);
		json.put("pageCount", m_pageCount);
		json.put("pageSize", m_scrollerPageSize);
		json.put("pageNumber", pageNumber);

		JSONArray data = new JSONArray();
		for (Object o : this) {
			JSONObject row = new JSONObject();
			for (Method m : o.getClass().getMethods()) {
				String name = m.getName(); 
				if (!(name.startsWith("get") || name.startsWith("is"))) continue;
				if (name.equals("getClass")) continue;
				if (name.startsWith("get")) {
					row.put(name.substring(3,4).toLowerCase()+name.substring(4), m.invoke(o, new Object[] {}));
				}
				if (name.startsWith("is")) {
					if (name.equals("isAlreadyCommentLike") || name.equals("isCommentLikeAllowed") 
							|| name.equals("isLikeAllowed") || name.equals("isAlreadyLike") 
							|| name.equals("isHost") || name.equals("isInvitee") || name.equals("isReplied")) {
						Object[] obj = new Object[] {userId};
						row.put(name.substring(2,3).toLowerCase()+name.substring(3), m.invoke(o, obj));  
					} else {
						row.put(name.substring(2,3).toLowerCase()+name.substring(3), m.invoke(o, new Object[] {}));
					}
				}
			}            
			data.add(row);
		}
		json.put("aData", data);

		return json;
	}

	public JSONObject toJsonString(int pageNumber, String filterTag, String commOption, String sortOption, String toggleList) throws Exception
	{
		JSONObject json = new JSONObject();
		json.put("rowCount", m_rowCount);
		json.put("pageCount", m_pageCount);
		json.put("pageSize", m_scrollerPageSize);
		json.put("pageNumber", pageNumber);

		JSONArray data = new JSONArray();
		for (Object o : this) {
			JSONObject row = new JSONObject();
			for (Method m : o.getClass().getMethods()) {
				String name = m.getName(); 
				if (!(name.startsWith("get") || name.startsWith("is"))) continue;
				if (name.equals("getClass")) continue;
				if (name.startsWith("get")) {
					if (name.equals("getTaggedKeywords")) {
						Object[] obj = new Object[] {pageNumber, filterTag, commOption, sortOption, toggleList};
						row.put(name.substring(3,4).toLowerCase()+name.substring(4), m.invoke(o, obj));  
					} else {
						row.put(name.substring(3,4).toLowerCase()+name.substring(4), m.invoke(o, new Object[] {}));
					}
				}
				if (name.startsWith("is"))
					row.put(name.substring(2,3).toLowerCase()+name.substring(3), m.invoke(o, new Object[] {}));
			}            
			data.add(row);
		}
		json.put("aData", data);

		return json;
	}

	public JSONObject toJsonString(int pageNumber, String filterTag, String toggleList) throws Exception
	{
		JSONObject json = new JSONObject();
		json.put("rowCount", m_rowCount);
		json.put("pageCount", m_pageCount);
		json.put("pageSize", m_scrollerPageSize);
		json.put("pageNumber", pageNumber);

		JSONArray data = new JSONArray();
		for (Object o : this) {
			JSONObject row = new JSONObject();
			for (Method m : o.getClass().getMethods()) {
				String name = m.getName(); 
				if (!(name.startsWith("get") || name.startsWith("is"))) continue;
				if (name.equals("getClass")) continue;
				if (name.startsWith("get")) {
					if (name.equals("getTaggedKeywords")) {
						Object[] obj = new Object[] {pageNumber, filterTag, toggleList};
						row.put(name.substring(3,4).toLowerCase()+name.substring(4), m.invoke(o, obj));  
					} else {
						row.put(name.substring(3,4).toLowerCase()+name.substring(4), m.invoke(o, new Object[] {}));
					}
				}
				if (name.startsWith("is"))
					row.put(name.substring(2,3).toLowerCase()+name.substring(3), m.invoke(o, new Object[] {}));
			}            
			data.add(row);
		}
		json.put("aData", data);

		return json;
	}

	public JSONObject toJsonString(int pageNumber, String filterTag, String commOption, String roleOption, String sortOption, String toggleList) throws Exception
	{
		JSONObject json = new JSONObject();
		json.put("rowCount", m_rowCount);
		json.put("pageCount", m_pageCount);
		json.put("pageSize", m_scrollerPageSize);
		json.put("pageNumber", pageNumber);

		JSONArray data = new JSONArray();
		for (Object o : this) {
			JSONObject row = new JSONObject();
			for (Method m : o.getClass().getMethods()) {
				String name = m.getName(); 
				if (!(name.startsWith("get") || name.startsWith("is"))) continue;
				if (name.equals("getClass")) continue;
				if (name.startsWith("get")) {
					if (name.equals("getTaggedKeywords")) {
						Object[] obj = new Object[] {pageNumber, filterTag, commOption, roleOption, sortOption, toggleList};
						row.put(name.substring(3,4).toLowerCase()+name.substring(4), m.invoke(o, obj));  
					} else {
						row.put(name.substring(3,4).toLowerCase()+name.substring(4), m.invoke(o, new Object[] {}));
					}
				}
				if (name.startsWith("is"))
					row.put(name.substring(2,3).toLowerCase()+name.substring(3), m.invoke(o, new Object[] {}));
			}            
			data.add(row);
		}
		json.put("aData", data);

		return json;
	}

	public JSONObject toJsonSearchContent() throws Exception
	{
		JSONObject json = new JSONObject();
		json.put("rowCount", m_rowCount);

		JSONArray data = new JSONArray();
		for (Object o : this) {
			JSONObject row = new JSONObject();
			for (Method m : o.getClass().getDeclaredMethods()) {
				String name = m.getName(); 
				if (!name.startsWith("get")) continue;
				row.put(name.substring(3,4).toLowerCase()+name.substring(4), m.invoke(o, new Object[] {}));
			}            
			data.add(row);
		}
		json.put("aData", data);

		return json;
	}

	public final int getCurrentPageNumber()
	{
		return m_currentPageNumber;
	}
	public final void setCurrentPageNumber(int currentPageNumber)
	{
		m_currentPageNumber = currentPageNumber;
	}
	public final int getPageCount()
	{
		return m_pageCount;
	}
	public final void setPageCount(int pageCount)
	{
		m_pageCount = pageCount;
	}
	public final int getScrollerPageSize()
	{
		return m_scrollerPageSize;
	}
	public final void setScrollerPageSize(int scrollerPageSize)
	{
		m_scrollerPageSize = scrollerPageSize;
	}
	public int getRowCount()
	{
		return m_rowCount;
	}
	public void setRowCount(int rowCount)
	{
		m_rowCount = rowCount;
	}
}