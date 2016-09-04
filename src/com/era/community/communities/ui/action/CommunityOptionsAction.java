package com.era.community.communities.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.UserRoleConstants;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.pers.dao.User;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/community/communityOptions.ajx" 
 */
public class CommunityOptionsAction extends AbstractCommandAction
{
	public static final String REQUIRES_AUTHENTICATION = "";
	private CommunityEraContextManager contextManager; 
	private CommunityFinder communityFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		HttpServletResponse resp = context.getResponse();
		Command cmd = (Command)data; 
		String info = getCommunityOption(context, cmd.getCommId(), cmd.getCurrId(), cmd.getImageId(), cmd.getAlbumId(), cmd.getAlbumName(), cmd.getItemId(), cmd.isMoveToAlbumAllowed());
		JSONObject json = new JSONObject();
		json.put("optionInfo", info);
		String jsonString = json.serialize();
		resp.setContentType("text/json");
		Writer out = resp.getWriter();
		out.write(jsonString);
		out.close();
		return null;
	}

	public String getCommunityOption(CommunityEraContext context, int commId, int currId, int imageId, int albumId, String albumName, int itemId, String move) throws Exception
	{
		Community currComm = null;
		String returnString = "<ul style='list-style: none outside none;'>";
		if (imageId > 0) {
			returnString += "<li onClick='downloadPhoto(&#39;"+imageId+"&#39;, &#39;"+albumId+"&#39;)' href='javascript:void(0);'>Download</li>";// +
			//"<li onClick='callInnerLink(&#39;makeProfilePhoto(&#39;"+imageId+"&#39;, &#39;"+albumId+"&#39;)' href='javascript:void(0);'>Make Profile Photo</li>";
			if ("true".equalsIgnoreCase(move)) {
				if (albumId > 0) {
					returnString += "<li onClick='moveToAlbum(&#39;"+imageId+"&#39;, &#39;"+albumId+"&#39;)' href='javascript:void(0);'>Move To Other Album</li>";
				} else {
					returnString += "<li onClick='moveToAlbum(&#39;"+imageId+"&#39;, &#39;"+albumId+"&#39;)' href='javascript:void(0);'>Move To Album</li>";
				}
			}
			returnString += "<li onClick='deletePhoto(&#39;"+imageId+"&#39;)' href='javascript:void(0);'>Delete Photo</li>";
			return returnString + "</ul>";
		} else if (albumId > 0) {
			returnString += "<li onClick='editAlbum(&#39;"+albumId+"&#39;)' href='javascript:void(0);'>Edit Album</li>" +
			"<li onClick='deleteAlbum(&#39;"+albumId+"&#39;)' href='javascript:void(0);'>Delete Album</li>";
			return returnString + "</ul>";
		}
		if (commId > 0) {
			currComm = communityFinder.getCommunityForId(commId);
		}
		if (currId == 1001) { //community home
			returnString = "" +
			"<li onClick='callInnerLink(&#39;community/createNewCommunity.do"+"&#39;)' href='javascript:void(0);'>Start a Community</li>";
		}
		if (currId == 1002) { // community home update
			returnString = "" +
			"<li onClick='callInnerLink(&#39;cid/"+commId+"/home.do"+"&#39;)' href='javascript:void(0);'>Community home</li>" +
			"<li onClick='callInnerLink(&#39;community/createNewCommunity.do"+"&#39;)' href='javascript:void(0);'>Start a Community</li>";
		}
		if (currId == 1007) { // Edit community 
			returnString = "" +
			"<li onClick='callInnerLink(&#39;cid/"+commId+"/home.do"+"&#39;)' href='javascript:void(0);'>Community home</li>";
		}
		if (currId == 1003) { // media Gallery
			returnString = "" +
			"<li onClick='callInnerLink(&#39;cid/"+commId+"/library/showLibraryItems.do"+"&#39;)' href='javascript:void(0);'>Library</li>";
		}
		if (currId == 1004) { // media Gallery
			returnString = "" +
			"<li onClick='callInnerLink(&#39;cid/"+commId+"/blog/viewBlog.do"+"&#39;)' href='javascript:void(0);'>Blogs</li>";
		}
		if (currId == 1005) { // Library
			returnString = "" +
			"<li onClick='callInnerLink(&#39;cid/"+commId+"/library/mediaGallery.do"+"&#39;)' href='javascript:void(0);'>Media Gallery</li>";
		}
		if (currId == 1006) { // Library Gallery
			returnString = "" +
			"<li onClick='callInnerLink(&#39;cid/"+commId+"/library/showLibraryItems.do"+"&#39;)' href='javascript:void(0);'>Library</li>";
		}
		if (currId == 2001) { //community home, community home update
			if (context.isUserAuthenticated() && isUserCommunityAdmin(context, currComm)) {
				returnString = returnString + "<li onClick='addEditTags(&#39;Community&#39;, &#39;"+commId+"&#39;)' href='javascript:void(0);'>Add/Edit tags</li>" +
				"<li onClick='callInnerLink(&#39;cid/"+commId+"/community/editCommunity.do"+"&#39;)' href='javascript:void(0);'>Edit community</li>";
				
				returnString = returnString + "<li onClick='addMoreFeatures(&#39;"+commId+"&#39;)' href='javascript:void(0);'>Add More features</li>";
			}
			if (context.isUserAuthenticated() && !isMember(context, currComm) 
					&& ("Public".equals(currComm.getCommunityType()) || (currComm.isProtected() && !currComm.isMemberShipRequestPending(contextManager.getContext().getCurrentUser())))) {
				returnString = returnString + "<li onclick='joinRequest(&#39;"+commId+"&#39; , &#39;"+currComm.getCommunityType()+"&#39;)' href='javascript:void(0);'>Join this community</li>";
			}else{
				if (context.isUserAuthenticated() && !isOwner(currComm)) {
					if (isMember(context, currComm)) {
						returnString = returnString + "<li onclick='leaveCommunity(&#39;"+currComm.isPrivate()+"&#39;);' href='javascript:void(0);'>Leave this community</li>";
					}
				}
				//returnString = returnString + "<li onClick='callInnerLink(&#39;community/inviteAFriend.do?id="+commId+"&#39;)' href='javascript:void(0);'>Invite a friend</li>";
			}
		}
		if (currId == 2002 && context.isUserAuthenticated() && isMember(context, currComm)) { // Manage Membership, show connections, show invitations
			if (isUserCommunityAdmin(context, currComm)) {
				returnString = returnString + "<li onClick='addMembers(&#39;"+commId+"&#39;)' href='javascript:void(0);'>Add Members</li>";
			}
			returnString = returnString + "<li onClick='inviteMembers(&#39;"+commId+"&#39;)' href='javascript:void(0);'>Invite Members</li>";
			if (isUserCommunityAdmin(context, currComm)) {
				/*returnString = returnString + "<li onClick='importMembers(&#39;"+commId+"&#39;)' href='javascript:void(0);'>Import Members</li>" +
				"<li onClick='exportMembers(&#39;"+commId+"&#39;)' href='javascript:void(0);'>Export Members</li>";
				if (currComm != null && currComm.isPrivate()) {
					returnString = returnString + "<li onClick='callInnerLink(&#39;cid/"+commId+"/connections/showJoiningRequests.do"+"&#39;)' href='javascript:void(0);'>Membership requests</li>";
				}*/
				returnString = returnString + "<li onClick='callInnerLink(&#39;cid/"+commId+"/connections/manageMembership.do"+"&#39;)' href='javascript:void(0);'>Manage Membership</li>";
			}
		}
		if (currId == 2003 && context.isUserAuthenticated() && isMember(context, currComm)) { // show connections
			returnString = "" +
			"<li onClick='callInnerLink(&#39;cid/"+commId+"/connections/showInvitations.do"+"&#39;)' href='javascript:void(0);'>Invitations</li>";
			if (currComm.isPrivate() && isUserCommunityAdmin(context, currComm)) {
				returnString = returnString + "<li onClick='callInnerLink(&#39;cid/"+commId+"/connections/showJoiningRequests.do"+"&#39;)' href='javascript:void(0);'>Membership requests</li>";
			}
		}
		if (currId == 2004 && context.isUserAuthenticated()) { // Show Invitations
			returnString = "" +
			"<li onClick='callInnerLink(&#39;cid/"+commId+"/connections/showConnections.do"+"&#39;)' href='javascript:void(0);'>Connections</li>";
			if (currComm.isPrivate() && isUserCommunityAdmin(context, currComm)) {
				returnString = returnString + "<li onClick='callInnerLink(&#39;cid/"+commId+"/connections/showJoiningRequests.do"+"&#39;)' href='javascript:void(0);'>Membership requests</li>";
			}
		}
		if (currId == 2005) { // show Joining Requests
			returnString = "" +
			"<li onClick='callInnerLink(&#39;cid/"+commId+"/connections/showConnections.do"+"&#39;)' href='javascript:void(0);'>Connections</li>";
			if (isMember(context, currComm)) {
				returnString = returnString + "<li onClick='callInnerLink(&#39;cid/"+commId+"/connections/showInvitations.do"+"&#39;)' href='javascript:void(0);'>Invitations</li>";
			}
		}

		if (currId == 3001 && context.isUserAuthenticated()) { // connectionResult -- profile
			User currUsr = context.getCurrentUser();
			if (currUsr != null) {
				returnString = returnString  + "<li onClick='callInnerLink(&#39;pers/communityInvitations.do"+"&#39;)' href='javascript:void(0);'>Community Invitations</li>" +
				"<li onClick='callInnerLink(&#39;pers/joiningRequests.do"+"&#39;)' href='javascript:void(0);'>Received Membership Requests</li>" +
				"<li onClick='callInnerLink(&#39;pers/myJoiningRequests.do"+"&#39;)' href='javascript:void(0);'>Sent Membership Requests</li>";
			}
		}
		
		if (currId == 3006 && context.isUserAuthenticated()) { // connectionResult -- received
			User currUsr = context.getCurrentUser();
			returnString = "" +
			"<li onClick='callInnerLink(&#39;pers/connectionCommunities.do"+"&#39;)' href='javascript:void(0);'>Communities</li>";
			if (currUsr != null) {
				returnString = returnString  + "<li onClick='callInnerLink(&#39;pers/communityInvitations.do"+"&#39;)' href='javascript:void(0);'>Community Invitations</li>" +
				"<li onClick='callInnerLink(&#39;pers/myJoiningRequests.do"+"&#39;)' href='javascript:void(0);'>Sent Membership Requests</li>";
			}
		}
		
		if (currId == 3007 && context.isUserAuthenticated()) { // connectionResult -- sent
			User currUsr = context.getCurrentUser();
			returnString = "" +
			"<li onClick='callInnerLink(&#39;pers/connectionCommunities.do"+"&#39;)' href='javascript:void(0);'>Communities</li>";
			if (currUsr != null) {
				returnString = returnString  + "<li onClick='callInnerLink(&#39;pers/communityInvitations.do"+"&#39;)' href='javascript:void(0);'>Community Invitations</li>" +
				"<li onClick='callInnerLink(&#39;pers/joiningRequests.do"+"&#39;)' href='javascript:void(0);'>Received Membership Requests</li>";
			}
		}
		
		if (currId == 3008 && context.isUserAuthenticated()) { // connectionResult -- sent
			User currUsr = context.getCurrentUser();
			returnString = "" +
			"<li onClick='callInnerLink(&#39;pers/connectionCommunities.do"+"&#39;)' href='javascript:void(0);'>Communities</li>";
			if (currUsr != null) {
				returnString = returnString  + "<li onClick='callInnerLink(&#39;pers/joiningRequests.do"+"&#39;)' href='javascript:void(0);'>Received Membership Requests</li>" +
				"<li onClick='callInnerLink(&#39;pers/myJoiningRequests.do"+"&#39;)' href='javascript:void(0);'>Sent Membership Requests</li>";
			}
		}

		if (currId == 3002 && context.isUserAuthenticated()) { // connectionResult -- main
			User currUsr = context.getCurrentUser();
			if (currUsr != null) {
				returnString = "<li onClick='callInnerLink(&#39;pers/visitMyProfile.do"+"&#39;)' href='javascript:void(0);'>Profile Visitors</li>";
			}
		}

		if (currId == 3003 && context.isUserAuthenticated()) { // connectionCommunities
			returnString = "" +
			"<li onClick='callInnerLink(&#39;pers/connectionResult.do"+"&#39;)' href='javascript:void(0);'>Profile</li>";
		}
		
		if (currId == 3004 && context.isUserAuthenticated()) { // message Actions
			returnString = "" +
			"<li onClick='callMessageLink(&#39;1&#39;)' href='javascript:void(0);'>Mark selected Read</li>" +
			"<li onClick='callMessageLink(&#39;2&#39;)' href='javascript:void(0);'>Mark selected Unread</li>" +
			"<li onClick='callMessageLink(&#39;3&#39;)' href='javascript:void(0);'>Mark all Read</li>" +
			"<li onClick='callMessageLink(&#39;4&#39;)' href='javascript:void(0);'>Mark all Unead</li>" +
			"<li class='lineseparaterTip'></li>" +
			"<li onClick='callMessageLink(&#39;5&#39;)' href='javascript:void(0);'>Delete selected Messages</li>" +
			"<li onClick='callMessageLink(&#39;6&#39;)' href='javascript:void(0);'>Delete all Messages</li>";
		}
		if (currId == 3005 && context.isUserAuthenticated()) { // message Actions
			returnString = "" +
			"<li onClick='callMessageLink(&#39;7&#39;)' href='javascript:void(0);' title='Restore selected messages from Archive'>Restore selected Messages</li>" +
			"<li onClick='callMessageLink(&#39;8&#39;)' href='javascript:void(0);' title='Restore all archived messages'>Restore all Messages</li>" +
			"<li class='lineseparaterTip'></li>" +
			"<li onClick='callMessageLink(&#39;9&#39;)' href='javascript:void(0);' title='Delete selected messages permanently'>Delete selected Messages</li>" +
			"<li onClick='callMessageLink(&#39;10&#39;)' href='javascript:void(0);' title='Deleted all messages permanently'>Delete all Messages</li>";
		}
		
		if (currId == 3009) { // connectionCommunities
			returnString = "" +
			"<li onClick='callInnerLink(&#39;blog/personalBlog.do?id="+itemId+"&#39;)' href='javascript:void(0);'>Blogs</li>";
		}
		return returnString + "</ul>";
	}

	public boolean isOwner(Community community) throws Exception
	{
		if (contextManager.getContext().getCurrentUser() == null ) return false;
		if (contextManager.getContext().isUserAuthenticated() == false) return false;
		return community.isCommunityOwner(contextManager.getContext().getCurrentUser());
	}

	public boolean isUserCommunityAdmin(CommunityEraContext context, Community community) throws Exception
	{
		User user = context.getCurrentUser();
		if (user==null) return false;
		if (context.isCurrentUserInRole(UserRoleConstants.ROLE_SUPER_ADMIN)) return true; 
		if (community==null) return false;
		return community.isAdminMember(user); 
	}

	private boolean isMember(CommunityEraContext context, Community community) throws Exception
	{
		if (contextManager.getContext().getCurrentUser() == null ) return false;
		if (contextManager.getContext().isUserAuthenticated() == false) return false;
		int currentUser = contextManager.getContext().getCurrentUser().getId();
		return community.isMember(currentUser);
	}
	
	public class Command extends IndexCommandBeanImpl implements CommandBean 
	{
		private int currId;
		private int commId;
		private int imageId;
		private int albumId;
		private int itemId;
		private String albumName;
		private String moveToAlbumAllowed;

		public int getCurrId() {
			return currId;
		}
		public void setCurrId(int currId) {
			this.currId = currId;
		}
		public int getCommId() {
			return commId;
		}
		public void setCommId(int commId) {
			this.commId = commId;
		}
		public int getImageId() {
			return imageId;
		}
		public void setImageId(int imageId) {
			this.imageId = imageId;
		}
		public int getAlbumId() {
			return albumId;
		}
		public void setAlbumId(int albumId) {
			this.albumId = albumId;
		}
		public String getAlbumName() {
			return albumName;
		}
		public void setAlbumName(String albumName) {
			this.albumName = albumName;
		}
		public int getItemId() {
			return itemId;
		}
		public void setItemId(int itemId) {
			this.itemId = itemId;
		}
		public String isMoveToAlbumAllowed() {
			return moveToAlbumAllowed;
		}
		public void setMoveToAlbumAllowed(String moveToAlbumAllowed) {
			this.moveToAlbumAllowed = moveToAlbumAllowed;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setCommunityFinder(CommunityFinder communityFinder) {
		this.communityFinder = communityFinder;
	}
}