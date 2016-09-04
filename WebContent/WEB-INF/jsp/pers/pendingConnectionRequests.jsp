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
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		<title>Jhapak - ${command.user.firstName} ${command.user.lastName}</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<link rel="stylesheet" media="all" type="text/css" href="css/profile.css" />
		
		<script type="text/javascript" src="js/qtip/dynamicDropDownQtip.js"></script>
		<script type="text/javascript" src="js/qtip/memberInfoTip.js"></script>
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style type="text/css">
	 		#container .left-panel .nav-list ul li.selected {
			    width: 180px;
			}
		</style>
		
		<script type="text/javascript">
			function addConnectionInner(userId, contactName){
				$(".qtip").hide();
				$.ajax({url:"${communityEraContext.contextUrl}/pers/addConnectionInner.ajx?id="+userId+"&userName="+contactName,success:function(result){
		    	    $("#connectionInfo-"+userId).html(result);
		    	    $('.normalTip').qtip({ 
					    content: {
					        attr: 'title'
					    },
						style: {
					        classes: 'qtip-tipsy'
					    }
					});

		    	    dynamicDropDownQtip();
		    	  }});
			}
	
			function updateConnectionInner(userId, contactId, newStatus, contactName){
				$(".qtip").hide();
				$.ajax({url:"${communityEraContext.contextUrl}/pers/updateConnectionInner.ajx?id="+contactId+"&newStatus="+newStatus+"&userId="+userId+"&userName="+contactName,success:function(result){
					$("#connectionInfo-"+userId).html(result);
		    	    $('.normalTip').qtip({ 
					    content: {
					        attr: 'title'
					    },
						style: {
					        classes: 'qtip-tipsy'
					    }
					});

		    	    dynamicDropDownQtip();
		    	  }});
			}

			function stopFollowingInner(contactId, actionFor, userId, contactName){
				$(".qtip").hide();
				$.ajax({url:"${communityEraContext.contextUrl}/pers/toggleFollowingInner.ajx?contactId="+contactId+"&actionFor="+actionFor+"&actionType=0"+"&userId="+userId+"&contactName="+contactName,success:function(result){
		    	    $("#followId"+userId).replaceWith(result);
		    	    $('.normalTip').qtip({ 
					    content: {
					        attr: 'title'
					    },
						style: {
					        classes: 'qtip-tipsy'
					    }
					});
		    	  }});
			}

			function startFollowingInner(contactId, actionFor, userId, contactName){
				$(".qtip").hide();
				$.ajax({url:"${communityEraContext.contextUrl}/pers/toggleFollowingInner.ajx?contactId="+contactId+"&actionFor="+actionFor+"&actionType=1"+"&userId="+userId+"&contactName="+contactName,success:function(result){
		    	    $("#followId"+userId).replaceWith(result);
		    	    $('.normalTip').qtip({ 
					    content: {
					        attr: 'title'
					    },
						style: {
					        classes: 'qtip-tipsy'
					    }
					});
		    	  }});
			}

			function receivedConnectionRequests(){
				var isAuthenticated = ${communityEraContext.userAuthenticated};
			    if (!isAuthenticated) {
					window.location.href = "${communityEraContext.contextUrl}/pers/pendingConnectionRequests.ajx";
			    }
			}

			function sentConnectionRequests(){
				var isAuthenticated = ${communityEraContext.userAuthenticated};
			    if (!isAuthenticated) {
					window.location.href = "${communityEraContext.contextUrl}/pers/pendingConnectionRequests.ajx?type=sent";
			    }
			}
		</script>
		
		<script type="text/javascript">
			// Initial call
			$(document).ready(function(){
				$(".qtip").hide();
				dynamicDropDownQtip();
			});
		</script>
		
		<script type="text/javascript">
			function sendMessage(userId, contactName) 
			{
				$(".qtip").hide();
				 var ref = 'pers/sendMessage.do';
				    var mess = "";
				    var type = BootstrapDialog.TYPE_INFO;
				    var isAuthenticated = $("#isAuthenticated").val();
				    if (isAuthenticated == 'false') {
				    	type = BootstrapDialog.TYPE_DANGER;
				    	mess = '<p class="extraWord">You are not logged-in.<br/> Please login first to send a message. </p>';
				    	BootstrapDialog.show({
			                type: type,
			                title: 'New Message',
			                message: mess,
			                closable: true,
			                closeByBackdrop: false,
			                closeByKeyboard: false,
			                buttons: [{
			                	label: 'Close',
			                    action: function(dialogRef){
			                        dialogRef.close();
			                    }
			                }]
			            });
			    } else {
	
			    	var $textAndPic = $('<div id="composePart2"></div>');
			    	$textAndPic.append('<form action="${communityEraContext.contextUrl}/jlogin.do" method="post"><p class="message"><input type="text" class="text2" id="toNames" value="" name="toNames" size="65"/></p>');
			    	$textAndPic.append('<p class="message"><input type="text" class="text2" id="subject" value="" name="subject" size="51"/></p>');
			    	$textAndPic.append('<div id="statusText"><fieldset><textarea class="ckeditor" fieldLabel=""  name="body" id="body" cols="60" rows="12" ></textarea></fieldset></div>');
			    	$textAndPic.append('<input type="hidden" name="toUserId" /><input type="hidden" name="toIdList" /></form>');
			    	
			    	BootstrapDialog.show({
		                type: type,
		                title: 'Send Message',
		                message: $textAndPic,
		                closable: true,
		                closeByBackdrop: false,
		                closeByKeyboard: false,
		                cssClass: 'login-dialog',
		                buttons: [{
		                	label: 'Go',
		                    action: function(dialogRef){
		                        dialogRef.close();
		                        window.location.href=ref;
		                    }
		                }, {
		                	label: 'Close',
		                    action: function(dialogRef){
		                        dialogRef.close();
		                    }
		                }]
		            });
			    }
			}
		</script>
	</head>
	
	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div id="container" >
			<div class="left-panel" style="margin-top: -11px;">
				<input type="hidden" name="page" value="${command.page}" />
				<input type="hidden" id="allConnCnt" value="${command.rowCount}" />
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
							<li class="selected"><a href="${communityEraContext.contextUrl}/pers/connectionList.do?id=${command.user.id}">Connections</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/mediaGallery.do?id=${command.user.id}">Gallery</a></li>
						</ul>
					</div>
					
					<div class="inputForm" style="display: inline-block; width: 100%; padding: 0px;" id="inputForm">
						<div class="intHeader">
							<c:if test="${command.selectedType == 'received'}">
								<span class='firLev'>
									<span id="yourConnections" class="firLevselected">Received Requests <span id="allConnectionCount">(${command.countReceived})</span></span>
								</span>
								<c:if test="${command.countSent > 0}">
									<span class='secLev' id="">
										<a onclick="sentConnectionRequests();" href="javascript:void(0);" class="normalTip btnmain" >Sent Requests <span class="redMark">${command.countSent}</span></a>
									</span>
								</c:if>
							</c:if>
							<c:if test="${command.selectedType == 'sent'}">
								<span class='firLev'>
									<span id="yourConnections" class="firLevselected">Sent Requests <span id="allConnectionCount">(${command.countSent})</span></span>
								</span>
								<c:if test="${command.countReceived > 0}">
									<span class='secLev' id="">
										<a onclick="receivedConnectionRequests();" href="javascript:void(0);" class="normalTip btnmain" >Received Requests <span class="redMark">${command.countReceived}</span></a>
									</span>
								</c:if>
							</c:if>
						</div>
						
						<div id="innerSection">
							<c:forEach items="${command.scrollerPage}" var="row">
								<c:if test="${row.oddRow}"> <c:set var="trclass" value='class="userList"' /></c:if>
								<c:if test="${row.evenRow}">	<c:set var="trclass" value='class="userListAlt"' /></c:if>
								<div ${trclass} id='connResult-${row.id}'>
									<div class='leftImg'>
										<c:choose>
											<c:when test="${row.photoPresent}">
											<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id=${row.id}&backlink=ref' >
												<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?showType=m&id=${row.id}' width='120' height='120' />
											</a>
											</c:when>
											<c:otherwise>
												<img src='img/user_icon.png'  width='120' height='120' />
											</c:otherwise>
										</c:choose>
									</div>
									<div class='details'>
										<div class='heading'>
											<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id=${row.id}&backlink=ref'>${row.firstName} ${row.lastName}</a>
										</div>
										<div class='person'>Connected since ${row.connectionDate}</div>
										<c:if test="${row.connectionCount  == 0}">
											<div class='person'>${row.connectionCount} Connection</a></div>
										</c:if>
										<c:if test="${row.connectionCount  == 1}">
											<div class='person'>
												<a href='${communityEraContext.contextUrl}/pers/connectionList.do?backlink=ref&id=${row.id}'>${row.connectionCount} Connection</a>
											</div>
										</c:if>
										<c:if test="${row.connectionCount  > 1}">
											<div class='person'>
												<a href='${communityEraContext.contextUrl}/pers/connectionList.do?backlink=ref&id=${row.id}'>${row.connectionCount} Connections</a>
											</div>
										</c:if>
										<c:if test="${row.communityCount  == 0}">
											<div class='person'>${row.communityCount} Community</a></div>
										</c:if>
										<c:if test="${row.communityCount  == 1}">
											<div class='person'>
												<a href='${communityEraContext.contextUrl}/pers/connectionCommunities.do?backlink=ref&id=${row.id}'>${row.communityCount} Community</a>
											</div>
										</c:if>
										<c:if test="${row.communityCount  > 1}">
											<div class='person'>
												<a href='${communityEraContext.contextUrl}/pers/connectionCommunities.do?backlink=ref&id=${row.id}'>${row.communityCount} Communities</a>
											</div>
										</c:if>
								 		<div id='connectionInfo-${row.id}' class='connectionInfo'>${row.connectionInfo}</div>
								 	</div>
								</div>
							</c:forEach>
						</div>
						<div id="waitMessage" style="display: none;">
							<div class="cssload-square" >
								<div class="cssload-square-part cssload-square-green" ></div>
								<div class="cssload-square-part cssload-square-pink" ></div>
								<div class="cssload-square-blend" ></div>
							</div>
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