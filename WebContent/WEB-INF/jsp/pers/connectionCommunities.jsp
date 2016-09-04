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
		<meta name="robots" content="noindex,follow" />
		<meta name="author" content="${command.user.firstName} ${command.user.lastName}">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		<link rel="publisher" href="https://plus.google.com/+Jhapak4u"/>
		<meta property="article:publisher" content="https://www.facebook.com/EraOfCommunities" />
		
		<%@ include file="/WEB-INF/jspf/extraJs.jspf" %>
		
		<title>Jhapak - ${command.user.firstName} ${command.user.lastName}</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<link rel="stylesheet" media="all" type="text/css" href="css/profile.css" />
		
		<script type="text/javascript" src="js/qtip/dynamicDropDownQtip.js"></script>
		<script type="text/javascript" src="js/qtip/memberInfoTip.js"></script>
		<script type="text/javascript" src="js/qtip/optionListQtip.js"></script>
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style type="">
			#container .left-panel .nav-list ul li.selected {
			    width: 160px;
			}
		</style>
		
		<script type="text/javascript">
			$(document).ready(function () {
				normalQtip();
				dynamicDropDownQtip();

				function applyFilter(filterUrl){
					var fTagList = document.getElementById("fTagList").value;
					var toggleList = document.getElementById("toggleList").value;
					filterUrl += "&fTagList="+fTagList+"&toggleList="+toggleList;
					window.location.href = filterUrl;
				}

				$.ajax({url:"${communityEraContext.contextUrl}/community/allCommunitiesCloud.ajx?uid="+$("#userId").val()+"&fTagList="+$("#fTagList").val(),dataType: "json",success:function(result){
					var aName = "<ul>";
					var fTagList = $("#fTagList").val();
					 $.each(result.aData, function() {
						 var count = 'community';
						 if (this['count'] > 1) {
							 count = 'communities';
						 }
						 var content = "View "+this['count']+" "+count+" tagged with &#39;"+this['tagText']+"&#39;";
						 aName += "<li>";
						 aName += "<a href='javascript:void(0);' onclick='applyFilter(&#39;${communityEraContext.contextUrl}/community/showCommunities.do?filterTag="+this['tagText']+"&#39;);'";
						 aName += " title='"+content+"' class='normalTip size-"+this['cloudSet']+"' id='"+this['tagText']+"'>"+this['tagText']+"</a>";
						 aName += "</li>";
							});
					 if(aName == "<ul>"){
						$("#cloud").html("<span style ='font-size: 12px; padding-left: 80px;'>No tags yet</span>");
					}else{
						$("#cloud").html(aName+"</ul>");
					}
					 
					 var bName = "<table >";
					 $.each(result.bData, function() {
						 var count = 'community';
						 if (this['count'] > 1) {
							 count = 'communities';
						 }
						 var content = "View "+this['count']+" "+count+" tagged with &#39;"+this['tagText']+"&#39;";
						 bName += "<tr><td>";
						 bName += "<span><a id='"+this['tagText']+"' href='javascript:void(0);' onclick='applyFilter(&#39;${communityEraContext.contextUrl}/community/showCommunities.do?filterTag="+this['tagText']+"&#39;);' ";
						 bName += " title='"+content+"' class='normalTip size-1' >"+this['tagText']+"</a></span>";
						 bName += "</td><td style='color: rgb(42, 58, 71); float: right;'>["+this['count']+"]</td>";
						 bName += "</tr>";
							});
					 if(bName == "<table >"){
						$("#cloudList").html("<span style ='font-size: 12px; padding-left: 80px;'>No tags yet</span>");
					}else{
						$("#cloudList").html(bName+"</table>");
					}
					 
					// Hide message
			        $("#waitCloudMessage").hide();
					normalQtip();
				    },
				 	// What to do before starting
			         beforeSend: function () {
			             $("#waitCloudMessage").show();
			         } 
			    });

				$.ajax({url:"${communityEraContext.contextUrl}/community/communityPannel.ajx?type=top",dataType: "json",success:function(result){
					var temp = "<div class='pannel'>";
					$.each(result.aData, function() {
						temp += "<div class='pannelHeadder' style='display: inline-block; width: 100%;'>";
						temp += "<img src='${communityEraContext.contextUrl}/commLogoDisplay.img?communityId="+this['entryId']+"' width='50' height='50' style='padding: 3px; float: left;' />";
						temp += "<div class='pannelTitle' style='word-wrap: normal;'>";
							 temp += "<a href='${communityEraContext.contextUrl}/cid/"+this['entryId']+"/home.do'>"+this['name']+"</a>";
						 temp += "</div></div>";
						});
					temp += "</div>";
					$("#topStoriesList").html(temp);
			        $("#waitTpStories").hide();
			        $('.memberInfo').each(function() {
			        	$(".qtip").hide();
				         $(this).qtip({
				            content: {
				        	 button: true,
				                text: function(event, api) {
				                    $.ajax({
				                        url: api.elements.target.attr('title') // Use href attribute as URL
				                    })
				                    .then(function(content) {
				                        api.set('content.text', content);
				                    }, function(xhr, status, error) {
				                        api.set('content.text', 'Loading...');
				                    });
				                    return 'Loading...'; // Set some initial text
				                }
				            },
				            position: {
				            	viewport: $(window),
				            	my: 'top right',  // Position my top left...
				                at: 'bottom right', // at the bottom right of...
				                target: 'mouse', // Position it where the click was...
								adjust: { 
									mouse: false 
								} // ...but don't follow the mouse
							},
								style: {
							        classes: 'qtip-bootstrap myCustomClass'
							    },
							    hide: {
							    	target: $('a')
							    }
				         });
				     });
				    },
			         beforeSend: function () {
			             $("#waitTpStories").show();
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
						$.ajax({url:"${communityEraContext.contextUrl}/pers/connectionCommunities.ajx?jPage="+num+"&id="+$("#userId").val(),dataType: "json",success:function(result){ 
							var sName = "";
							 $.each(result.aData, function() {
								 sName += "<div class='paginatedList'><div class='leftImg'>";
						
								 var welcome = this['welcomeText'];
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
								 
								 if(this['taggedKeywords']){
									 sName += "<div class='person'><i class='fa fa-tags' style='font-size: 14px; margin-right: 4px;'></i>"+this['taggedKeywords']+"</div>";
								 }
								 
								 if(!this['member'] && this['type'] == "Private"){
									 if(this['membershipRequested']){
									 	sName += "<div class='entry' id='reqId-"+rowId+"' ><span>Membership requested</span></div>";
									 } else {
										 sName += "<div class='entry' id='reqId-"+rowId+"' ><a onclick='joinRequest("+rowId+","+ctype+")' href='javascript:void(0);' title='Apply to join "+this['name']+"'>Apply to join</a></div>";
									 }
								 }
								 
								 if(!this['member'] && this['type'] == "Public"){
									 sName += "<div class='entry' id='reqId-"+rowId+"' ><a onclick='joinRequest("+rowId+","+ctype+")' href='javascript:void(0);' title='Join "+this['name']+"'>Join</a></div>";
								 }
								 sName += "<p>"+welcome+"</p></div></div>";
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
		
		<script type="text/javascript">
			function joinRequest(rowId, ctype) { 
			    var ref = '${communityEraContext.contextUrl}/community/joinCommunityRequest.ajx?id='+rowId;
			    var mess = '';
			    var type = BootstrapDialog.TYPE_INFO;
			    var isAuthenticated = ${communityEraContext.userAuthenticated};
			    if (!isAuthenticated) {
			    	type = BootstrapDialog.TYPE_DANGER;
			    	mess = '<p class="addTagHeader">You are not logged-in.<br/> Please login first to join a community. </p>';
			    } else if (ctype == 2) {
			    	mess = '<p class="addTagHeader"><strong>This is a private community.</strong><br/><br/>'+		
					'To request membership click the Go button below.  '+
					'Your request to join the community will be actioned by the community admin.  '+
					'Notification of your acceptance to the community will be sent to your email address.<br/><br />Please write a suitable message'+
					'</p><br/><div id="statusText3"><textarea class="form-control" name="body" id="textareaId">I\'d like to join this community b\'coz... </textarea></div>';
					
			    } else {
			    	mess = '<p class="addTagHeader"><strong>This is a public community.</strong><br/><br/>'+
				    'To join this community click the Go button below. '+
					'You will be added to the community member list and will immediately have access to the community content. '+
					'</p>';
			    }
			    if (!isAuthenticated) {
			    	BootstrapDialog.show({
		                type: type,
		                title: 'Join a community',
		                message: mess,
		                closable: true,
		                closeByBackdrop: false,
		                closeByKeyboard: false,
		                buttons: [{
		                	label: 'Close',
		                	cssClass: 'btn-danger',
		                    action: function(dialogRef){
		                        dialogRef.close();
		                    }
		                }]
		            });
			    } else {
			    	var succBtn = 'Join';
				    if (ctype == 2) {
				    	succBtn = 'Apply';
				    }
			    	BootstrapDialog.show({
		                type: type,
		                title: 'Join a community',
		                message: mess,
		                closable: false,
		                closeByBackdrop: false,
		                closeByKeyboard: false,
		                buttons: [{
		                	label: 'Close',
		                	id: 'button-close',
		                    action: function(dialogRef){
		                        dialogRef.close();
		                    }
		                },
		                {
		                	label: succBtn,
		                	cssClass: 'btn-info',
		                	id: 'button-Go',
		                	action: function(dialogRef){
		                	dialogRef.getButton('button-Go').disable();
		                	dialogRef.getButton('button-close').disable();
		                	dialogRef.getButton('button-Go').spin();
		                	if (ctype == 2) {
			                	var fruit = dialogRef.getModalBody().find('#textareaId').val();
			                	ref = ref + '&optionalComment=' + fruit;
		                	}
		                	$.ajax({url:ref,success:function(result){
		                		$('#reqId-'+rowId).html(result.returnString);
	                        	dialogRef.close();
	            	    	  }});
	                    	}
		                }]
		            });
			    }
			}

			function addConnection(userId, contactName){
				$.ajax({url:"${communityEraContext.contextUrl}/pers/addConnection.ajx?id="+userId+"&userName="+contactName,success:function(result){
		    	    $("#connectionInfo").html(result);
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
	
			function updateConnection(userId, contactId, newStatus, contactName){
				$.ajax({url:"${communityEraContext.contextUrl}/pers/updateConnection.ajx?id="+contactId+"&newStatus="+newStatus+"&userId="+userId+"&userName="+contactName,success:function(result){
		    	    $("#connectionInfo").html(result);
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

			function stopFollowing(contactId, actionFor, userId, contactName){
				$.ajax({url:"${communityEraContext.contextUrl}/pers/toggleFollowing.ajx?contactId="+contactId+"&actionFor="+actionFor+"&actionType=0"+"&userId="+userId+"&contactName="+contactName,success:function(result){
		    	    $("#connectionInfo").html(result);
		    	  }});
			}
	
			function startFollowing(contactId, actionFor, userId, contactName){
				$.ajax({url:"${communityEraContext.contextUrl}/pers/toggleFollowing.ajx?contactId="+contactId+"&actionFor="+actionFor+"&actionType=1"+"&userId="+userId+"&contactName="+contactName,success:function(result){
		    	    $("#connectionInfo").html(result);
		    	  }});
			}
		</script>
		
	</head>
	
	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div id="container" >
			<div class="left-panel" style="margin-top: -11px;">
				<input type="hidden" name="page" value="${command.page}" />
				<input type="hidden" id="userId" name="userId" value="${command.id}" />
				<input type="hidden" id="fTagList" name="fTagList" value="${command.filterTagList}" />
				<input type="hidden" id="toggleList" name="toggleList" value="${command.toggleList}" />
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
						<c:if test="${communityEraContext.currentUser.id != command.user.id}">
							<c:if test="${command.contactionAllowed}">				
								<div class='actions3' id='connectionInfo'>${command.returnString}</div>
							</c:if>
                       	</c:if>
					</div>
					
					<div class="nav-list">
						<ul>
							<c:choose>
								<c:when test="${communityEraContext.currentUser.id == command.id}">
									<li class="selected">
										<a href="${communityEraContext.contextUrl}/pers/connectionCommunities.do?id=${command.id}" class='dynaDropDown' 
										title='community/communityOptions.ajx?currId=3001'>Communities (${command.rowCount}) <span class='ddimgWht'/></a>
									</li>
								</c:when>
								<c:otherwise>
									<li class="selected">
										<a href="${communityEraContext.contextUrl}/pers/connectionCommunities.do?id=${command.id}" >Communities (${command.rowCount})</a>
									</li>
								</c:otherwise>
							</c:choose>
							<li><a href="${communityEraContext.contextUrl}/blog/personalBlog.do?id=${command.id}">Blogs</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/connectionList.do?id=${command.id}">Connections</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/mediaGallery.do?id=${command.id}">Gallery</a></li>
						</ul>
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
				<a href="${communityEraContext.contextUrl}/community/createNewCommunity.do" class="btnmain normalTip" style="width: 100%; margin-bottom: 10px;" title="Start a new community">
					<i class="fa fa-users" ></i><i class="fa fa-plus" style="margin-right: 8px; font-size: 10px; margin-left: -1px;"></i>Start a Community</a>
				<div class="inbox" style="display: inline-block; width: 296px; float: right;">
					<div class="eyebrow">
						<span onclick="return false" ><i class="fa fa-users" style="margin-right: 8px;"></i>Most Active Communities</span>
					</div>
					<div id="topStoriesList" style="padding: 4px;" ></div>
					<div id="waitTpStories" style="display: none;">
						<div class="cssload-square" style="width: 13px; height: 13px;">
							<div class="cssload-square-part cssload-square-green" style="width: 13px; height: 13px;"></div>
							<div class="cssload-square-part cssload-square-pink" style="width: 13px; height: 13px;"></div>
							<div class="cssload-square-blend" style="width: 13px; height: 13px;"></div>
						</div>
					</div>
				</div>
				<div class="inbox" style="display: inline-block; width: 296px; float: right;">
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
								<h3 class="selectedCloudTab" id="hCloud" style="display: none; float:left;">Cloud<i class='a-icon-text-sep-Cloud'></i></h3>
								<a href="javascript: toggle()" style="float:left; color: #66799f;" id="aCloud">Cloud<i class='a-icon-text-sep-Cloud'></i></a>
								<h3 class="selectedCloudTab" style="float:right;" id="hCloudList">List</h3>
								<a href="javascript: toggle()" id="aCloudList" style="display: none; float:right; color: #66799f;">List</a>
							</c:when>
							<c:otherwise>
								<h3 class="selectedCloudTab" id="hCloud" style="float:left;">Cloud<i class='a-icon-text-sep-Cloud'></i></h3>
								<a href="javascript: toggle()" style="display: none; float:left; color: #66799f;" id="aCloud">Cloud<i class='a-icon-text-sep-Cloud'></i></a>
								<h3 class="selectedCloudTab" style="display: none; float:right;" id="hCloudList">List</h3>
								<a href="javascript: toggle()" id="aCloudList" style="float:right; color: #66799f;">List</a>
						  	</c:otherwise>
						</c:choose>
					</ul> <br/>
				</div>
			</div>

		</div> 
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		<%@ include file="/WEB-INF/jspf/extraFooter.jspf" %>
	</body>
</html>