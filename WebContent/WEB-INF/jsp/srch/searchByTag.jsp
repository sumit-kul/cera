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
		<meta name="author" content="Jhapak">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<title>${communityEraContext.currentCommunity.name} - Jhapak</title>	
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style type="text/css">
			#container .left-panel .nav-list ul li.selected {
			    width: 70px;
			}
			
			.type a:hover {
			    color: #2A3A47;
			    text-decoration: none;
			}
			.type a {
			    font-size: 18px;
			    font-weight: 500;
				line-height: 1.1;
			    color: #66799F;
			    text-decoration: none;
			    word-wrap: break-word;
			}
		
		</style>
		
		<script type="text/javascript">
			function submitSearch(submitType){
				 var ref = "${communityEraContext.currentCommunityUrl}/search/searchByTagInCommunity.do?submitType="+submitType;
				 var fTagList = $("#fTagList").val();
				 var filterTag = $("#filterTag").val();
				 if (filterTag != '') {
					 ref = ref + "&filterTag="+filterTag;
					}
				 if (fTagList != '') {
					 ref = ref + "&fTagList="+fTagList;
					}
				 window.location.href=ref;
			}
		</script>
		
		<script type="text/javascript">
		// Initial call
		$(document).ready(function(){

			$.ajax({url:"${communityEraContext.currentCommunityUrl}/community/cloudCommunity.ajx",dataType: "json",success:function(result){
				var aName = "<ul>";
				var fTagList = $("#fTagList").val();
				 $.each(result.aData, function() {
					 var count = 'item';
					 if (this['count'] > 1) {
						 count = 'items';
					 }
					 var content = "View "+this['count']+" "+count+" tagged with &#39;"+this['tagText']+"&#39;";
					 aName += "<li>";
					 aName += "<a href='${communityEraContext.currentCommunityUrl}/search/searchByTagInCommunity.do?filterTag="+this['tagText']+"&fTagList="+fTagList+"' ";
					 aName += "onmouseover='tip(this,&quot;"+content+"&quot;)' class='size-"+this['cloudSet']+"' >"+this['tagText']+"</a>";
					 aName += "</li>";
						});
				 $("#cloud").html(aName+"</ul>");
				 
				 var bName = "<table >";
				 $.each(result.bData, function() {
					 var count = 'item';
					 if (this['count'] > 1) {
						 count = 'items';
					 }
					 var content = "View "+this['count']+" "+count+" tagged with &#39;"+this['tagText']+"&#39;";
					 bName += "<tr><td>";
					 bName += "<span class='size-"+this['cloudSet']+"'  ><a href='${communityEraContext.currentCommunityUrl}/search/searchByTagInCommunity.do?filterTag="+this['tagText']+"&fTagList="+fTagList+"' ";
					 bName += "onmouseover='tip(this,&quot;"+content+"&quot;)' class='size-"+this['cloudSet']+"' >"+this['tagText']+"</a></span>";
					 bName += "</td><td style='color: rgb(42, 58, 71); float: right;'>["+this['count']+"]</td>";
					 bName += "</tr>";
						});
				 $("#cloudList").html(bName+"</table>");

				// Hide message
		        $("#waitCloudMessage").hide();
			    },
			 	// What to do before starting
		         beforeSend: function () {
		             $("#waitCloudMessage").show();
		         } 
		    });

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
						var sentUrl = "${communityEraContext.currentCommunityUrl}/search/searchByTagInCommunity.ajx";
					$.ajax({url:sentUrl+"?jPage="+num+"&fTagList="+$("#fTagList").val()+"&toggleList="+$("#toggleList").val()+"&submitType="+$("#submitType").val(),dataType: "json",success:function(result){
						var sName = "";
						 $.each(result.aData, function() {
							 var rowId = this['itemId'];
							 var posterid = this['posterid'];
							 sName += "<div class='paginatedList'><div class='leftImg'>";
							 
							 if(this['photoPresent']){
								 sName += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+posterid+"&backlink=ref' ><img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+posterid+"'/></a>"; }
							 else {
								 sName += "<img src='img/user_icon.png'  />";
							 }
	
							 sName += "</div><div class='details'>";
							 sName += "<div class='heading'><a href='${communityEraContext.contextUrl}/"+this['itemUrl']+"' >"+this['itemTitle']+"</a></div>";
							 sName += "<div class='person'>By <a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+posterid+"&backlink=ref'>"+this['firstName'] +" "+ this['lastName'] +"</a> <i class='a-icon-text-separator'></i> "+this['postedOn']+"</div>";
							 sName += "<div class='person'>";
							 if(this['likeCount'] > 1){
								 sName += this['likeCount'] + " likes <i class='a-icon-text-separator'></i>";
							 } else {
								 sName += this['likeCount'] + " like <i class='a-icon-text-separator'></i>";
							 }
							 sName += " 0 views <i class='a-icon-text-separator'></i>";
							 if(this['commentCount'] > 0){
								 sName += "<a href='${communityEraContext.contextUrl}/cid/"+this['communityid']+"/blog/blogEntry.do?id="+rowId;
								 if(this['userId'] > 0){
									 sName += "&amp;userId="+this['userId'];
								 }
								 sName += "'>"+this['commentCount'];
								 if(this['commentCount'] = 1){
								 sName += " comment</a>";
								 } else {
									 sName += " comments</a>";
								 }
							 }
							 if(this['commentCount'] == 0){
								 sName += "0 comment";
							 }
							 sName += "<i class='a-icon-text-separator'></i><a href='${communityEraContext.contextUrl}/cid/"+this['communityid']+"/blog/blogEntry.do?id="+rowId;
							 if(this['userId'] > 0){
								 sName += "&amp;userId="+this['userId'];
							 }
							sName += "'>Add Comment</a></div>";
							if(this['taggedKeywords']){
								 sName += "<div class='person'><i class='fa fa-tags' style='font-size: 14px; margin-right: 4px;'></i>"+this['taggedKeywords']+"</div>";
							 }
							sName += "<div class='entry'><a href='${communityEraContext.contextUrl}"+this['itemBaseTitle']+"' >"+this['itemType']+"</a></div>";
							sName += "<p>"+this['bodyDisplay']+"</p></div></div>";
							});
						 $("#page").html(sName);
						// Hide message
					        $("#waitMessage").hide();
					        toggleOnLoad();
					    },
					 	// What to do before starting
				         beforeSend: function () {
				             $("#waitMessage").show();
				         } 
				    });
					} 
				});
			}
			});
		</script>
	</head>

	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div id="container">
			<div class="left-panel">
				<div class="commSection">
					<div class="communities">
						<div class="headerIcon">
							<i class="fa fa-users" ></i>
						</div>
						<div class="type">
							<a href="${communityEraContext.currentCommunityUrl}/home.do">${communityEraContext.currentCommunity.name}</a>
							<input type="hidden" id="fTagList" name="fTagList" value="${command.filterTagList}" />
							<input type="hidden" id="filterTag" name="filterTag" value="${command.filterTag}" />
							<input type="hidden" id="submitType" name="submitType" value="${command.submitType}" />
							<input type="hidden" id="toggleList" name="toggleList" value="${command.toggleList}" />
						</div>
						<div class="filterBox" style="padding: 0px; border: 0px;">
							<div class="nav-list" style="margin-bottom:0px;">
								<ul>
									<c:if test="${command.submitType == 'all'}">
										<li class="selected" style="padding: 3px 40px 6px;"><a href="javascript:void(0);" onclick="return submitSearch('all')">All Items</a></li>
									</c:if>
									<c:if test="${command.submitType != 'all'}">
										<li style="padding: 3px 40px 6px;"><a href="javascript:void(0);" onclick="return submitSearch('all')">All Items</a></li>
									</c:if>
									<c:if test="${command.submitType == 'BlogEntry'}">
										<li class="selected" style="padding: 3px 40px 6px;"><a href="javascript:void(0);" onclick="return submitSearch('BlogEntry')">Blog</a></li>
									</c:if>
									<c:if test="${command.submitType != 'BlogEntry'}">
										<li style="padding: 3px 40px 6px;"><a href="javascript:void(0);" onclick="return submitSearch('BlogEntry')">Blog</a></li>
									</c:if>
									<c:if test="${command.submitType == 'Event'}">
										<li class="selected" style="padding: 3px 40px 6px;"><a href="javascript:void(0);" onclick="return submitSearch('Event')">Events</a></li>
									</c:if>
									<c:if test="${command.submitType != 'Event'}">
										<li style="padding: 3px 40px 6px;"><a href="javascript:void(0);" onclick="return submitSearch('Event')">Events</a></li>
									</c:if>
									<c:if test="${command.submitType == 'Document'}">
										<li class="selected" style="padding: 3px 40px 6px;"><a href="javascript:void(0);" onclick="return submitSearch('Document')">Files</a></li>
									</c:if>
									<c:if test="${command.submitType != 'Document'}">
										<li style="padding: 3px 40px 6px;"><a href="javascript:void(0);" onclick="return submitSearch('Document')">Files</a></li>
									</c:if>
									<c:if test="${command.submitType == 'ForumTopic'}">
										<li class="selected" style="padding: 3px 40px 6px;"><a href="javascript:void(0);" onclick="return submitSearch('ForumTopic')">Forums</a></li>
									</c:if>
									<c:if test="${command.submitType != 'ForumTopic'}">
										<li style="padding: 3px 40px 6px;"><a href="javascript:void(0);" onclick="return submitSearch('ForumTopic')">Forums</a></li>
									</c:if>
									<c:if test="${command.submitType == 'WikiEntry'}">
										<li class="selected" style="padding: 3px 52px 6px;"><a href="javascript:void(0);" onclick="return submitSearch('WikiEntry')">Wiki</a></li>
									</c:if>
									<c:if test="${command.submitType != 'WikiEntry'}">
										<li style="padding: 3px 40px 6px;"><a href="javascript:void(0);" onclick="return submitSearch('WikiEntry')">Wiki</a></li>
									</c:if>
								</ul>
							</div>
						
							<c:if test="${not empty command.displayedFilterTag}">
								<label style="overflow: hidden; position: relative; padding: 10px; display: inline-block;">
								     Filtered by: 
								    <span>
								    	${command.displayedFilterTag}
								    </span>
								</label>
							</c:if>
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
	</body>
</html>