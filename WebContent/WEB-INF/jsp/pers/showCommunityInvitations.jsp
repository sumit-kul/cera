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
		<meta name="author" content="${command.user.firstName} ${command.user.lastName}">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<title>Jhapak - ${command.user.firstName} ${command.user.lastName}</title>	
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		
		<%@ include file="/WEB-INF/jspf/extraJs.jspf" %>
		
		<link rel="stylesheet" media="all" type="text/css" href="css/profile.css" />
		
		<script type="text/javascript" src="js/qtip/dynamicDropDownQtip.js"></script>
		<script type="text/javascript" src="js/qtip/memberInfoTip.js"></script>
		<script type="text/javascript" src="js/qtip/optionListQtip.js"></script>
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style type="text/css">
			#container .left-panel .commSection .paginatedList {
			    border: 1px solid #F4F4F4;
			    margin: 6px;
			}
			
			#container .left-panel .commSection .paginatedList .details {
			    width: 624px;
			}
			
			#container .left-panel .commSection .paginatedList .details .entry a {
			    margin: 8px;
			}
			
			#container .left-panel .commSection .paginatedList .details .entry span {
				color: #A7A9AB;
				float: right;
			}
			
			#container .left-panel .commSection .paginatedList .details .entry {
			    position: relative;
			    right: 0px;
			    top: 6px;
			}
			
			.intHeader {
				text-decoration: none;
			}
			
			.intHeader form {
			    color: #008EFF;
			    font-size: 13px;
			    font-weight: normal;
			    padding: 8px;
			}
			
			.intHeader form label {
			    color: #707070;
			    float: left;
			    font-size: 12px;
			    font-weight: normal;
			    height: 20px;
			    line-height: 20px;
			    margin-right: 6px;
			}
			
			.intHeader form select {
			    border: 1px solid #D6D6D6;
			    background: none repeat scroll 0% 0% #F2F2F2;
			    color: #2C3A47;
			    margin: 0px 12px 5px 0px;
			    padding: 2px 5px;
			    width: 150px;
			    float: left;
			}
			
			.intHeader form input[type="submit"] {
			    background: none repeat scroll 0% 0% #8E9BA4;
			    border: medium none;
			    cursor: pointer;
			    color: #FFF;
			    font-size: 12px;
			    font-weight: bold;
			    padding: 3px 5px;
			}
		</style>
		
		<script type="text/javascript">
			function handleMemberInvitation(invitationId, actionType){
				$.ajax({url:"${communityEraContext.contextUrl}/community/handleMemberShipInvitation.ajx?selectedId="+invitationId+"&actionType="+actionType,success:function(result){
		    	    $("#invitationId-"+invitationId).html("<span>"+result.returnString+"</span>");
		    	  }});
			}
		</script>
		
		<script type="text/javascript">
			$(document).ready(function () {
				normalQtip();
				dynamicDropDownQtip();
				
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
						$.ajax({url:"${communityEraContext.contextUrl}/pers/communityInvitations.ajx?jPage="+num+"&id="+$("#userId").val(),dataType: "json",success:function(result){ 
							var sName = "";
							 $.each(result.aData, function() {
								 sName += "<div class='paginatedList'><div class='leftImg'>";
						
								 var ctype = 1;
								 var rowId = this['id'];
								 if(this['type'] == "Private"){
									 ctype = 2;
								 }
								 
								 if(this['logoPresent'] == "true"){
									 sName += "<a href='${communityEraContext.contextUrl}/cid/"+rowId+"/'home.do'><img src='${communityEraContext.contextUrl}/commLogoDisplay.img?communityId="+rowId+"'/></a>";
									} else {
									 sName += "<img src='img/community_Image.png' />";
								 }
		
								 sName += "</div><div class='details'>";
								 sName += "<div class='heading'>";
								 if(this['type'] == "Private"){
									 	sName += "<a href='${communityEraContext.contextUrl}/cid/"+rowId+"/home.do'><i class='fa fa-shield' style='margin-right: 8px; font-size: 16px;'></i>"+this['name']+"</a></div>";
									 } else {
										 sName += "<a href='${communityEraContext.contextUrl}/cid/"+rowId+"/home.do'><i class='fa fa-globe' style='margin-right: 8px; font-size: 16px;'></i>"+this['name']+"</a></div>";
									 }
								 sName += "<div class='person'>";
		
								 if(this['type'] == "Private" && !this['member']){
									 if(this['memberCountString'] != "0 member"){
										 sName += "<span>"+this['memberCountString']+"</span>";
									 } else {
										 sName += this['memberCountString'];
									 }
									 sName += "<i class='a-icon-text-separator'></i>";
									 sName += "Created on "+this['createdOn']+"</div>";
									 
								 } else {
									 if(this['memberCountString'] != "0 member"){
										 sName += "<span class='optionList' title='${communityEraContext.contextUrl}/pers/memberList.ajx?communityId="+rowId+"'>"+this['memberCountString']+"</span>";
									 } else {
										 sName += this['memberCountString'];
									 }
									 sName += "<i class='a-icon-text-separator'></i>";
									 sName += "Created by <a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['creatorId']+"&backlink=ref' class='memberInfo' ";
									 sName += " title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['creatorId']+"'>"+this['createdBy']+"</a><i class='a-icon-text-separator'></i>"+this['createdOn']+"</div>";
								 }
		
								 sName += "<div class='person' style='font-size:13px;'>";
								 sName += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['invitorId']+"&backlink=ref' class='memberInfo' style='font-size:13px;' ";
								 sName += " title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['invitorId']+"'>"+this['firstName']+ " " + this['lastName']+"</a> invited you on "+this['registredDateOn'];
								 sName += "</div>";
		
								 sName += "<div class='entry' id='invitationId-"+this['invitationId']+"' >"+this['requestInfo']+"</div>";
								 sName += "</div></div>";
									});
							 $("#page").html(sName);
							// Hide message
					        $("#waitMessage").hide();
					        normalQtip();
					        memberInfoQtip();
					        optionListQtip();
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
			<div id="container" >
				<div class="left-panel" style="margin-top: -11px;">
					<div class="commSection">
						<input type="hidden" name="page" value="${command.page}" />
						<div class="abtMe">
							<div class="cvrImg">
								<c:if test="${command.user.coverPresent}">
									<img src="${communityEraContext.contextUrl}/pers/userPhoto.img?showType=m&imgType=Cover&id=${command.user.id}" 
							 			width="750px" height="270px" />
							 	</c:if>
							</div>
							<div class='detailsConnection'>
								<h2 >
									<a href="${communityEraContext.contextUrl}/pers/connectionResult.do?id=${command.user.id}&backlink=ref" >${command.user.firstName} ${command.user.lastName}</a>
								</h2>
							</div>
							<div class="groups">
								<div class="picture">
									<c:choose>
										<c:when test="${command.user.photoPresent}">
											<c:url value="${communityEraContext.contextUrl}/pers/userPhoto.img?showType=m" var="photoUrl">
												<c:param name="id" value="${command.user.id}" />
											</c:url>
											<img src="${photoUrl}"  width='160' height='160' style=""/> 
										</c:when>
										<c:otherwise>
											<img src='img/user_icon.png'  width='160' height='160'/>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
							<c:if test="${communityEraContext.currentUser.id != command.user.id}">
								<c:if test="${command.contactionAllowed}">				
									<div class='actions3' id='connectionInfo'>${command.returnString}</div>
								</c:if>
	                       	</c:if>
						</div>
						
						<div class="nav-list">
							<ul>
								<c:choose>
									<c:when test="${communityEraContext.currentUser.id == command.user.id}">
										<li class="selected">
											<a href="${communityEraContext.contextUrl}/pers/connectionCommunities.do?id=${command.user.id}" class='dynaDropDown' 
												title='community/communityOptions.ajx?currId=3008'>Invitations (${command.rowCount}) <span class='ddimgWht'/></a>
										</li>
									</c:when>
									<c:otherwise>
										<li class="selected">
											<a href="${communityEraContext.contextUrl}/pers/connectionCommunities.do?id=${command.user.id}" >Invitations (${command.rowCount})</a>
										</li>
									</c:otherwise>
								</c:choose>
								<li><a href="${communityEraContext.contextUrl}/blog/personalBlog.do?id=${command.user.id}">Blogs</a></li>
								<li><a href="${communityEraContext.contextUrl}/pers/connectionList.do?id=${command.user.id}">Connections</a></li>
								<li><a href="${communityEraContext.contextUrl}/pers/mediaGallery.do?id=${command.user.id}">Gallery</a></li>
							</ul>
						</div>
					
						<div class="inputForm" style="display: inline-block; width: 100%; padding: 0px;" id="inputForm">
							<div class="intHeader">
								<span style="font-size:12px; margin-top:0px; padding:10px; float: right; color: #AAA6A6; font-weight: bold;">
									<c:if test="${command.rowCount  == 1}">
									${command.rowCount} Invitation
									</c:if>
									<c:if test="${command.rowCount  > 1}">
									${command.rowCount} Invitations
									</c:if>
								</span>
								<span class='firLev'>
									<form:form showFieldErrors="true">
										<form:dropdown path="sortByOption" fieldLabel="Sort By:">
											<form:optionlist options="${command.sortByOptionOptions}" />
										</form:dropdown>
										<input type="hidden" name="page" value="${command.page}" />
										<input type="hidden" id="sortByOption" name="sortByOption" value="${command.sortByOption}" />
										<input type="submit" value="Go"/>
									</form:form>
								</span>
							</div>
							<input type="hidden" id="pgCount" value="${command.pageCount}" />
							<div id="innerSection">
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
					</div>
				</div>
				
				<div class="right-panel" style="margin-top: 0px;">
				</div>	
			</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		<%@ include file="/WEB-INF/jspf/extraFooter.jspf" %>
	</body>
</html>