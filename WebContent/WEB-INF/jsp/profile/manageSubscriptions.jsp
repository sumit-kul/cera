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
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf" %>
		<link rel="stylesheet" type="text/css" href="css/table.css" />
		<title>Jhapak | My Subscriptions</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<link rel="stylesheet" media="all" type="text/css" href="css/profile.css" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style>
			#container .inputForm .innerBlock form select {
				width: 90%;
			}
		</style>
		
		<script type="text/javascript">
		// Initial call
		$(document).ready(function () {
			$('.normalTip').qtip({ 
			    content: {
			        attr: 'title'
			    },
				style: {
			        classes: 'qtip-tipsy'
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
					$.ajax({url:"${communityEraContext.contextUrl}/pers/manageSubscriptions.ajx?jPage="+num,dataType: "json",success:function(result){ 
						var sName = "<table class='type1' summary='' ><tr><th width='28%' colspan='2'>Name</th><th width='14%'>Type</th><th width='28%'>Community</th><th width='14%'>Last update</th><th width='14%'>Frequency</th></tr>";
						 $.each(result.aData, function() {
							 var rowId = this['id'];
							 var rs2 = this['resultSetIndex2'];
							 var rs3 = this['resultSetIndex3'];
							 
							 var trclass = 'trclass';
							 if(this['evenRow']){
								 trclass = 'trclassAlt';
							 }
							 sName += "<tr class='"+trclass+"'>";
							 sName += "<td class='checkonly'><input type='checkbox' class='check' name='selectedIds' value='"+rowId+"'/></td>";
							 sName += "<td width='24%' ><a href='"+this['itemUrl']+"'>"+this['itemName']+"</a></td>";
							 sName += "<td class='nowrap' >"+this['itemType']+"</td>";
							 sName += "<td ><a href='${communityEraContext.contextUrl}/cid/"+this['communityId']+"/home.do?backlink=ref'>"+this['communityName']+"</a></td>";
							 sName += "<td class='nowrap' >"+this['itemLastUpdateDate']+"</td>";
							 sName += "<td class='nowrap'>"+rs2;
							 sName += rs3+"</td>";
				
							 
								}
							);
						 sName += "</table>";
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

		function profileSettings(){
			var acUrl = 'pers/profileSettings.do';
			window.location.href = acUrl;
		}

		function accountSettings(){
			var acUrl = '${communityEraContext.contextUrl}/pers/accountSettings.do';
			window.location.href = acUrl;
		}
		</script>
		
	</head>

	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div id="container" >
			<div class="left-panel" style="margin-top: -11px;">
				<div class="commSection">
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
					</div>
					<div class="nav-list">
						<ul>
							<li><a href="${communityEraContext.contextUrl}/pers/connectionCommunities.do?id=${command.user.id}">Communities</a></li>
							<li><a href="${communityEraContext.contextUrl}/blog/personalBlog.do?id=${command.user.id}">Blogs</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/connectionList.do?id=${command.user.id}">Connections</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/mediaGallery.do?id=${command.user.id}">Gallery</a></li>
						</ul>
					</div>
					
					<div class="inputForm" style="padding: 0px; display: inline-block; width: 100%;  margin-bottom: 10px;">
						<div class="intHeader" style="">
							<span class="settingHdr" onclick="profileSettings();">
								Profile Settings
							</span>
							<span class="settingHdr" onclick="accountSettings();" style="border-right: 2px solid #CCC; border-left: 2px solid #CCC;" >
								Account Settings
							</span>
							<span class="settingHdrSelected" onclick="emailSettings();" style="float: right;">
								E-mail Notifications
							</span>
						</div>
				 		<div class="innerBlock" style="padding: 20px 20px 20px;">
							<form:form action="${communityEraContext.requestUrl}" method="post" commandName="command" name="newEventForm">	
								<form:errors path="*"  />
								<div class="commSection">
									<div class="communities">
										
										<div class="menus">
											<c:if test="${command.rowCount == 0}">
												<p class="message">You currently have no email alerts set.</p>
											</c:if>
											<c:if test="${command.rowCount > 0}">
												<ul style="width: 690px;">
													<li style="margin:0px; float: left;"><input  type="submit" name="buttonRemoveSelected" style="margin: 0px; float : right;" value="Remove Selected" title="Remove selected items from bellow list" class="btnsec delete normalTip" />
													</li>
													
													<li style="margin: 0px 6px;  float: left;"><a href="${communityEraContext.contextUrl}/pers/removeSubscriptions.do" 
													title="Remove all my email alerts" class="btnsec delete normalTip" style="margin: 0px; float : right;">Remove All Alerts</a></li>
													
													<li style="margin: -6px 0px;  float: right;"><input  type="submit" name="buttonUpdateOptions" value="Save Settings" title="Save my current email frequency settings" 
													class="btnsec save normalTip" /></li>
												</ul>
											</c:if>
											
										</div>
									</div>
									<c:if test="${command.rowCount > 0}">
										<div id="page"></div>
										<div id="waitMessage" style="display: none;">
											<div class="cssload-square" >
												<div class="cssload-square-part cssload-square-green" ></div>
												<div class="cssload-square-part cssload-square-pink" ></div>
												<div class="cssload-square-blend" ></div>
											</div>
										</div>
										<%@ include file="/WEB-INF/jspf/pagination.jspf" %>
									</c:if>
								</div>
							</form:form> 
						</div>
					</div>
				</div>
			</div> 
			<div class="right-panel" style="margin-top: 0px;">
			</div>
			</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		
	</body>
</html>