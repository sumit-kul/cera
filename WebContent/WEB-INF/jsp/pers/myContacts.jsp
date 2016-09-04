<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="/WEB-INF/tlds/cera_form.tld" %> 
<%@ taglib prefix="cera" uri="/WEB-INF/tlds/cera.tld" %> 

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="robots" content="noindex,nofollow" />
		<meta name="author" content="${communityEraContext.currentUser.fullName}">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<title>Jhapak - My connections</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style type="text/css">
			#container .left-panel .commSection .userList .details .entry a, 
			#container .left-panel .commSection .userListAlt .details .entry a {
			    padding-right: 0px;
			    padding-left: 10px;
			}
			
			#container .left-panel .commSection .userList .details .entry span {
			padding-left: 10px;
			}
		</style>
		
		<script type="text/javascript">
		// Initial call
		$(document).ready(function(){
			if(${command.pageCount} > 0){
				$("#pagination").jPaginator({ 
					nbPages:${command.pageCount},
					marginPx:5,
					length:6, 
					overBtnLeft:'#over_backward', 
					overBtnRight:'#over_forward', 
					maxBtnLeft:'#max_backward', 
					maxBtnRight:'#max_forward', 
					onPageClicked: function(a,num) { 
					$.ajax({url:"${communityEraContext.contextUrl}/pers/myContacts.ajx?jPage="+num+"&sortByOption="+$("#sortByOption").val(),dataType: "json",success:function(result){
						var sName = "";
						 $.each(result.aData, function() {
							 var rowId = this['id'];
							 var trclass = '';
							 if(this['evenRow']){
								 trclass = 'Alt';
							 }
							 					 
							 sName += "<div class='userList"+trclass+"'><div class='leftImg'>";
							 
							 if(this['photoPresent']){
								 sName += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+rowId+"&backlink=ref' ><img src='${communityEraContext.contextUrl}/pers/userPhoto.img?showType=mid="+rowId+"' width='130' height='110' /></a>"; }
							 else {
								 sName += "<img src='img/user_icon.png'  width='130' height='110' />";
							 }
	
							 sName += "</div><div class='details'><div class='heading'><a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+rowId+"&backlink=ref'>"+this['firstName']+" "+this['lastName']+"</a></div>";
	
							 sName += "<div class='person'>Connected since "+this['connectionDate']+"</div>";
							 sName += "<div class='person'><a href='${communityEraContext.contextUrl}/pers/showConnections.do?backlink=ref&connectionId="+rowId+"'>"+this['connectionCount']+" Connections</a></div>";
							 sName += "<div id='connectionInfo-"+rowId+"' class='entry'>"+this['connectionInfo']+"</div>";
							 sName += "</div></div>";
	
							 
								});
						 $("#page").html(sName);
					    }});
					} 
				});
			}
		});

	</script>
	
	<script type="text/javascript">
		function addConnection(userId, contactName){
			$.ajax({url:"${communityEraContext.contextUrl}/pers/addConnection.ajx?id="+userId+"&userName="+contactName,success:function(result){
	    	    $("#connectionInfo-"+userId).html(result);
	    	  }});
		}
	
		function updateConnection(userId, contactId, newStatus, contactName){
			$.ajax({url:"${communityEraContext.contextUrl}/pers/updateConnection.ajx?id="+contactId+"&newStatus="+newStatus+"&userId="+userId+"&userName="+contactName,success:function(result){
	    	    $("#connectionInfo-"+userId).html(result);
	    	  }});
		}
	
		function stopFollowing(contactId, actionFor, userId, contactName){
			$.ajax({url:"${communityEraContext.contextUrl}/pers/toggleFollowing.ajx?contactId="+contactId+"&actionFor="+actionFor+"&actionType=0"+"&userId="+userId+"&contactName="+contactName,success:function(result){
	    	    $("#connectionInfo-"+userId).html(result);
	    	  }});
		}

		function startFollowing(contactId, actionFor, userId, contactName){
			$.ajax({url:"${communityEraContext.contextUrl}/pers/toggleFollowing.ajx?contactId="+contactId+"&actionFor="+actionFor+"&actionType=1"+"&userId="+userId+"&contactName="+contactName,success:function(result){
	    	    $("#connectionInfo-"+userId).html(result);
	    	  }});
		}
	</script>
	
	</head>

	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div id="container">
			<div class="left-panel">
				<div class="commSection">
					<h4>My connections (${command.rowCount})</h4>
					<div class="communities">
						<div class="type">
							<form:form showFieldErrors="true">
								<form:dropdown path="sortByOption" fieldLabel="Sort By:">
									<form:optionlist options="${command.sortByOptionOptions}" />
								</form:dropdown>
								<input type="hidden" id="sortByOption" name="sortByOption" value="${command.sortByOption}" />
								<input type="submit" value="Go" class="search_btn" />
							</form:form>
						</div>
					</div>
					
					<div id="page"></div>
					<div id="waitMessage" style="display: none;">
						<div class="cssload-square" >
							<div class="cssload-square-part cssload-square-green" ></div>
							<div class="cssload-square-part cssload-square-pink" ></div>
							<div class="cssload-square-blend" ></div>
						</div>
					</div>
					<%@ include file="/WEB-INF/jspf/pagination.jspf" %>
				</div> 	
			</div> 	
			
			<div class="right-panel" style="margin-top: 0px;">
				<div class="inbox">
					<div class="eyebrow">
						<span><i class="fa fa-tags" style="margin-right: 2px;"></i>Popular Tags</span>
					</div>
					<div id="cloud" class="communities"></div>
					<div id="waitCloudMessage" style="display: none;">
						<div class="cssload-square" style="width: 13px; height: 13px;">
							<div class="cssload-square-part cssload-square-green" style="width: 13px; height: 13px;"></div>
							<div class="cssload-square-part cssload-square-pink" style="width: 13px; height: 13px;"></div>
							<div class="cssload-square-blend" style="width: 13px; height: 13px;"></div>
						</div>
					</div>
					<div id="cloudList" style="display: none; " class="communities"></div>
					<ul id="cloudTabs">
						<c:choose>
							<c:when test="${command.toggleList == 'true'}">
								<h3 class="selectedCloudTab" id="hCloud" style="display: none; float:left;">Cloud<i class='a-icon-text-separator'></i></h3>
								<a href="javascript: toggle()" style="float:left; color: #66799f;" id="aCloud">Cloud<i class='a-icon-text-separator'></i></a>
								<h3 class="selectedCloudTab" style="float:right;" id="hCloudList">List</h3>
								<a href="javascript: toggle()" id="aCloudList" style="display: none; float:right; color: #66799f;">List</a>
							</c:when>
							<c:otherwise>
								<h3 class="selectedCloudTab" id="hCloud" style="float:left;">Cloud<i class='a-icon-text-separator'></i></h3>
								<a href="javascript: toggle()" style="display: none; float:left; color: #66799f;" id="aCloud">Cloud<i class='a-icon-text-separator'></i></a>
								<h3 class="selectedCloudTab" style="display: none; float:right;" id="hCloudList">List</h3>
								<a href="javascript: toggle()" id="aCloudList" style="float:right; color: #66799f;">List</a>
						  	</c:otherwise>
						</c:choose>
					</ul>
				</div>
			</div>
		</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		<%@ include file="/WEB-INF/jspf/extraFooter.jspf" %>
	</body>
</html>