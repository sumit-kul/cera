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
		
		<style type="">
			#container .left-panel .nav-list ul li.selected {
			    width: 200px;
			}
			
			.intHeader {
				height: 40px;
				clear: both;
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
			    width: 100px;
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
			
			.chkPVI {
				display: inline-block;
				position: absolute;
				top: 0px;
				right: 0px;
			}
			
			input[type="checkbox"] {
			    display:none;
			}
			
			input[type="checkbox"] + label span {
			    display:inline-block;
			    width:19px;
			    height:19px;
			    margin:-1px 4px 0 0;
			    vertical-align:middle;
			    background:url(img/check_radio_sheet.png) left top no-repeat;
			    cursor:pointer;
			}
			
			input[type="checkbox"]:checked + label span {
			    background:url(img/check_radio_sheet.png) -19px top no-repeat;
			}
			
			.secLev .btnmain {
				margin: 8px 2px;
				padding: 0px 6px;
			}
			
			.secLev .btnmaindisabled {
				background-color: #7b8b97;
				margin: 8px 2px;
				padding: 0px 6px;
			}
		</style>
		
		<script type="text/javascript">
			function addConnectionInner(userId, contactName){
				$(".qtip").hide();
				$.ajax({url:"${communityEraContext.contextUrl}/pers/addConnectionInner.ajx?id="+userId+"&userName="+contactName,success:function(result){
					$(".qtip").hide();
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
					$(".qtip").hide();
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

		    var count = 2;
			function infinite_scrolling_allConn(){
				if  ($(window).scrollTop()  >= $(document).height() - $(window).height() - 250){
			    		var total = document.getElementById('pgCount').value;
	                	if (count > total){
	                		return false;
			        	} else {
			            	var currentOwner = document.getElementById('userId').value;
			            	$.ajax({
			                	url:"${communityEraContext.contextUrl}/pers/visitMyProfile.ajx?jPage="+count+"&filterOption="+$("#filterOption").val()+"&sortByOption="+$("#sortByOption").val(),
			                	dataType: "json",
			                	success:function(result){ 
			    					var sName = "";
			   					 $.each(result.aData, function() {
			   						 var rowId = this['id'];
			   						 var trclass = '';
			   						 if(this['evenRow']){
			   							 trclass = 'Alt';
			   						 }
			   						 sName += "<div class='userList"+trclass+"' id='connResult-"+rowId+"'><div class='leftImg'>";
			   						 if(this['photoPresent']){
			   							 sName += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+rowId+"&backlink=ref' ><img src='${communityEraContext.contextUrl}/pers/userPhoto.img?showType=m&id="+rowId+"' width='120' height='120' /></a>"; }
			   						 else {
			   							 sName += "<img src='img/user_icon.png'  width='120' height='120' />";
			   						 }
			   						 sName += "</div><div class='details'><div class='heading'><a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+rowId+"&backlink=ref'>"+this['firstName']+" "+this['lastName']+"</a></div><br />";
			   						 //sName += "<div class='person'>Connected since "+this['connectionDate']+"</div>";
			   						 if(this['connectionCount'] == 0){
			   							 sName += "<div class='person'>"+this['connectionCount']+" Connection</a></div>";
			   						 }
			   						 if(this['connectionCount'] == 1){
			   							 sName += "<div class='person'><a href='${communityEraContext.contextUrl}/pers/connectionList.do?backlink=ref&id="+rowId+"'>"+this['connectionCount']+" Connection</a></div>";
			   						 }
			   						 if(this['connectionCount'] > 1){
			   							 sName += "<div class='person'><a href='${communityEraContext.contextUrl}/pers/connectionList.do?backlink=ref&id="+rowId+"'>"+this['connectionCount']+" Connections</a></div>";
			   						 }
			   						 if(this['communityCount'] == 0){
			   						 	sName += "<div class='person'>"+this['communityCount']+" Community</a></div>";
			   						 }
			   						 if(this['communityCount'] == 1){
			   							 sName += "<div class='person'><a href='${communityEraContext.contextUrl}/pers/connectionCommunities.do?backlink=ref&id="+rowId+"'>"+this['communityCount']+" Community</a></div>";
			   						 }
			   						 if(this['communityCount'] > 1){
			   							 sName += "<div class='person'><a href='${communityEraContext.contextUrl}/pers/connectionCommunities.do?backlink=ref&id="+rowId+"'>"+this['communityCount']+" Communities</a></div>";
			   						 }
			   						 sName += "<div id='connectionInfo-"+rowId+"' class='connectionInfo'>"+this['connectionInfo']+"</div>";
			   						 sName += "</div></div>";
			   							});
			   					 $("#innerSection").append(sName);
			   					$("#waitMessage").hide();
			   					dynamicDropDownQtip();
			                	} ,
			                	beforeSend: function () {
						             $("#waitMessage").show();
						         },
			                	complete: function () {
			                		count++;
		                    	} 
			            	});
				        }
				    }
				}

			// debounce multiple requests
			var _scheduledRequest = null;
			function infinite_scroll_debouncer(callback_to_run, debounce_time) {
			    debounce_time = typeof debounce_time !== 'undefined' ? debounce_time : 200;
			    if (_scheduledRequest) {
			        clearTimeout(_scheduledRequest);
			    }
			    _scheduledRequest = setTimeout(callback_to_run, debounce_time);
			}

			// usage
			$(window).scroll(function() {
				infinite_scroll_debouncer(infinite_scrolling_allConn, 400);
		    });
		</script>
		
		<script type="text/javascript">
			// Initial call
			$(document).ready(function(){
				$(".qtip").hide();
				toggleBtnStyle();
				normalQtip();
				memberInfoQtip();
				dynamicDropDownQtip();
			});
		</script>
		
		<script type="text/javascript">
			function toggleBtnStyle()
			{
				$(".qtip").hide();
				var somval = $( "input:checkbox[name=markVisit]:checked" ).length;
				if(somval > 0) {
				     document.getElementById('markasseen').disabled = false;
				     document.getElementById('markasseen').className = "";
				     document.getElementById('markasseen').className = "btnmain";
				     document.getElementById('removevisitors').disabled = false;
				     document.getElementById('removevisitors').className = "";
				     document.getElementById('removevisitors').className = "btnmain";
				} else {
					 var markasseen = document.getElementById('markasseen');
					 var removevisitors = document.getElementById('removevisitors');
					 if (markasseen != null) {
						 document.getElementById('markasseen').disabled = "disabled";
					     document.getElementById('markasseen').className = "";
					     document.getElementById('markasseen').className = "btnmaindisabled";
						}
					 if (removevisitors != null) {
						 document.getElementById('removevisitors').disabled = "disabled";
					     document.getElementById('removevisitors').className = "";
					     document.getElementById('removevisitors').className = "btnmaindisabled";
						}
				}
				var checkboxes = $( "input:checkbox[name=markVisit]" ).length;
				if(checkboxes == somval) {
					if(document.getElementById('unselectall') != null)
					document.getElementById('unselectall').style.display = "inline";
					if(document.getElementById('selectall') != null)
				    document.getElementById('selectall').style.display = "none";
				} else {
					if(document.getElementById('unselectall') != null)
					document.getElementById('unselectall').style.display = "none";
					if(document.getElementById('selectall') != null)
				    document.getElementById('selectall').style.display = "inline";
				}
			}
			
			function selectAll()
			{
				var checkboxes = document.getElementsByName('markVisit'), val = null;    
			     for (var i = 0; i < checkboxes.length; i++)
			     {
			         if (checkboxes[i].type == 'checkbox')
			         {
			             if (val === null) val = checkboxes[i].checked;
			             checkboxes[i].checked = true;
			         }
			     }
			     document.getElementById('unselectall').style.display = "inline";
			     document.getElementById('selectall').style.display = "none";
			     document.getElementById('markasseen').disabled = false;
			     document.getElementById('markasseen').className = "";
			     document.getElementById('markasseen').className = "btnmain";
			     document.getElementById('removevisitors').disabled = false;
			     document.getElementById('removevisitors').className = "";
			     document.getElementById('removevisitors').className = "btnmain";
			}

			function unselectAll()
			{
				var checkboxes = document.getElementsByName('markVisit'), val = null;    
			     for (var i = 0; i < checkboxes.length; i++)
			     {
			         if (checkboxes[i].type == 'checkbox')
			         {
			             if (val === null) val = checkboxes[i].checked;
			             checkboxes[i].checked = false;
			         }
			     }
			     document.getElementById('unselectall').style.display = "none";
			     document.getElementById('selectall').style.display = "inline";
			     document.getElementById('markasseen').disabled = "disabled";
			     document.getElementById('markasseen').className = "";
			     document.getElementById('markasseen').className = "btnmaindisabled";
			     document.getElementById('removevisitors').disabled = "disabled";
			     document.getElementById('removevisitors').className = "";
			     document.getElementById('removevisitors').className = "btnmaindisabled";
			}
			

			function markSelectedSeen(pVisitId, allClear)
			{
				var json = []; 
            	$('input:checkbox[name=markVisit]:checked').each(function(i, selected){ 
            		json[i] = $(selected).attr('id');
            	});
                $.ajax({
                	type:"POST",
                	url:"${communityEraContext.contextUrl}/pers/deleteProfileVisiting.ajx?action=markSelectedSeen",
                	data: {json:json},
		            success:function(result){
                		window.location.href = "${communityEraContext.contextUrl}/pers/visitMyProfile.do";
    			    }
    		    });
			}
		
			function clearVisitorsList(pVisitId, allClear)
			{
				var json = []; 
            	$('input:checkbox[name=markVisit]:checked').each(function(i, selected){ 
            		json[i] = $(selected).attr('id');
            	});
                $.ajax({
                	type:"POST",
                	url:"${communityEraContext.contextUrl}/pers/deleteProfileVisiting.ajx?action=deleteSelected",
                	data: {json:json},
		            success:function(result){
                		window.location.href = "${communityEraContext.contextUrl}/pers/visitMyProfile.do";
    			    }
    		    });
			}
			
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
				<input type="hidden" id="userId" name="userId" value="${command.user.id}" />
				<input type="hidden" id="isConnAllow" value="" /> 
				<input type="hidden" id="allConnCnt" value="${command.rowCount}" />
				<input type="hidden" id="tabName" value="allConn" />
				<input type="hidden" id="pgCount" value="${command.pageCount}" />
				<%-- <input type="hidden" id="pgCountcommConn" value="${command.commonConnPageCount}" /> --%>
				<input type="hidden" id="currentUser" value="${communityEraContext.currentUser.id}" />
				
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
							<c:choose>
								<c:when test="${communityEraContext.currentUser.id == command.user.id}">
									<li class="selected">
										<a href="${communityEraContext.contextUrl}/pers/visitMyProfile.do" class='dynaDropDown' 
											title='community/communityOptions.ajx?currId=3003'>Profile Visitors (${command.rowCount}) <span class='ddimgWht'/></a>
									</li>
								</c:when>
								<c:otherwise>
									<li class="selected">
										<a href="${communityEraContext.contextUrl}/pers/visitMyProfile.do" >Profile Visitors (${command.rowCount})</a>
									</li>
								</c:otherwise>
							</c:choose>
							<li><a href="${communityEraContext.contextUrl}/pers/connectionCommunities.do?id=${command.user.id}">Communities</a></li>
							<li><a href="${communityEraContext.contextUrl}/blog/personalBlog.do?id=${command.user.id}">Blogs</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/connectionList.do?id=${command.user.id}">Connections</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/mediaGallery.do?id=${command.user.id}">Gallery</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/interestArea.do?id=${command.user.id}">Interests</a></li>
						</ul>
					</div>
					
					<div class="inputForm" style="display: inline-block; width: 100%; padding: 0px;" id="inputForm">
						<div class="intHeader">
							<c:if test="${communityEraContext.userAuthenticated}">
								<c:if test="${command.user.id == communityEraContext.currentUser.id}">
									<span class='firLev'>
										<form:form showFieldErrors="true">
											<form:dropdown path="filterOption" fieldLabel="Filter By:" cssStyle="width: 130px;">
												<form:optionlist options="${command.filterOptions}" />
											</form:dropdown>
											<form:dropdown path="sortByOption" fieldLabel="Sort By:">
												<form:optionlist options="${command.sortByOptionOptions}" />
											</form:dropdown>
											<input type="hidden" name="page" value="${command.page}" />
											<input type="hidden" id="filterOption" name="filterOption" value="${command.filterOption}" />
											<input type="hidden" id="sortByOption" name="sortByOption" value="${command.sortByOption}" />
											<input type="submit" value="Go"/>
										</form:form>
									</span>
									<c:if test="${command.rowCount > 0}">
										<span class='secLev' id="">
											<input type="button" class="btnmain normalTip" onclick="selectAll();" id="selectall" value="Select All" />
											<input type="button" class="btnmain normalTip" onclick="unselectAll();" id="unselectall" value="Unselect All" style="display: none;"/>
											<c:if test="${command.filterOption != 'Seen Requests'}">
												<input type="button" class="btnmain normalTip" onclick="markSelectedSeen();" id="markasseen" value="Mark as Seen" disabled="disabled"/>
											</c:if>
											<c:if test="${command.filterOption == 'Seen Requests'}">
												<input id="markasseen" value="Mark as Seen" disabled="disabled" style="display: none;"/>
											</c:if>
											<input type="button" class="btnmain normalTip" onclick="clearVisitorsList();" id="removevisitors" value="Remove Selected" disabled="disabled"/>
										</span>
									</c:if>
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
										<%-- <div class='person'>Connected since ${row.connectionDate}</div> --%>
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
										<c:if test="${row.status == 0}">
											<div class="chkPVI"><input name='markVisit' id="${row.profileVisitId}" tabindex="2" type='checkbox' onclick="toggleBtnStyle();"/>
											<label for="${row.profileVisitId}"><span></span></label></div>
										</c:if>
										<c:if test="${row.status == 1}">
											<div class="chkPVI"><input name='markVisit' id="${row.profileVisitId}" tabindex="2" type='checkbox' checked onclick="toggleBtnStyle();"/>
											<label for="${row.profileVisitId}"><span></span></label></div>
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