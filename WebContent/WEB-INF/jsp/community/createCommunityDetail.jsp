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
		<meta name="description" content="Jhapak, Communities are groups of people having common interests. A community provides an excellent way for like-minded people to stay in touch, share information, and exchange ideas. A public community is available for all to join, while membership of a privatecommunity is limited to a particular group. Public communities are visible to all users, but information shared in private communities can only be seen by community members. Starting a community can help you to build a valuable repository of information and expertise about a specific subject. Use the community features to reach out, make connections, get organized, and start sharing information. As a community owner, you can invite others to join, and manage the content and membership for the community." />
		<meta name="keywords" content="Jhapak, blog, community, start community, create community, confirm community, Connections, Event, Forum, Library, File, Wiki" />
		<title>Jhapak - Create a Community: Details</title>
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf" %>
		
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<%@ include file="/WEB-INF/jspf/extraJs.jspf" %>
		<link rel="stylesheet" media="all" type="text/css" href="css/profile.css" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style>
			.tag-dialog .modal-dialog .modal-body {
                height: 50px;
                padding : 20px;
            }
            
            input[type="radio"] {
				opacity: 0;
			  	position: absolute;
			}
			
			input[type="radio"] + label span {
			    display:inline-block;
			    width:19px;
			    height:19px;
			    margin:-1px 4px 0 0;
			    vertical-align:middle;
			    background:url(img/check_radio_sheet.png) -38px top no-repeat;
			    cursor:pointer;
			}
			input[type="radio"]:checked + label span {
			    background:url(img/check_radio_sheet.png) -56.5px top no-repeat;
			}
			
			input[type="text"]:focus,textarea:focus {
				outline: medium none;
				border: 1px solid #B9B9B9;
				box-shadow: 0 1px 1px rgba(34, 29, 29, 0.1) inset;
			}
	    </style>
		
		<script type="text/javascript">
			var editor_0;
			function publishNewCommunity(){
				var toElm = document.getElementById('tags');
				var toArray = toElm.value.split(' ');
				var isSpecialCharPresent = false;
				for (i=0; i<toArray.length; i++) { 	
					var str = toArray[i].toLowerCase();					
					if (str.match("[$&+,:;=?@#|'<>.-^*()%!]")) {
						isSpecialCharPresent = true;
						break;
					}
				}
				if (isSpecialCharPresent) {
					var type = BootstrapDialog.TYPE_DANGER;
			    	BootstrapDialog.show({
		                type: type,
		                title: 'Error',
		                message: 'Special characters like &, #, @, etc are not allowed in tags',
		                cssClass: 'tag-dialog',
		                closable: true,
		                closeByBackdrop: true,
		                closeByKeyboard: true,
		                draggable: true,
		                buttons: [{
		                	label: 'Close',
		                    action: function(dialogRef){
		                        dialogRef.close();
		                    }
		                }]
		            });
				} else {
					document.getElementById('descriptionEd').value = editor_0.getData();
					document.newCommunityForm.action = "${communityEraContext.requestUrl}";
					document.newCommunityForm.submit();
			    }
			}
		</script>	
		
		<script type="text/javascript">
			// Initial call
			$(document).ready(function () {
				var config = {};
	        	config.toolbarGroups = [
										{ name: 'colors', groups: [ 'colors' ] },
										{ name: 'insert', groups: [ 'insert' ] },
										{ name: 'basicstyles', groups: [ 'bold'] },
										{ name: 'styles', groups: [ 'size' ] },
	                            	];
	        	config.toolbarLocation = 'top';
	        	config.removeButtons = 'Subscript,Superscript,Image,Flash,Table,Iframe,SpecialChar,HorizontalRule,PageBreak';
	        	editor_0 = CKEDITOR.appendTo( 'editor', config, '' );
				$('.normalTip').qtip({ 
				    content: {
				        attr: 'title'
				    },
					style: {
				        classes: 'qtip-tipsy'
				    }
				});
			});
		</script>
		
	</head>		

	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div id="container" >
			<div class="left-panel" style="margin-top: -11px;">
				<div class="commSection">
					<div class="abtMe">
						<div class="cvrImg">
							<c:if test="${communityEraContext.currentUser.coverPresent}">
								<img src="${communityEraContext.contextUrl}/pers/userPhoto.img?imgType=Cover&showType=m&id=${communityEraContext.currentUser.id}" 
						 			width="750px" height="270px" />
						 	</c:if>
						</div>
						<div class='detailsConnection'>
							<h2 >
								<a href="${communityEraContext.contextUrl}/pers/connectionResult.do?id=${communityEraContext.currentUser.id}&backlink=ref" >${communityEraContext.currentUser.firstName} ${communityEraContext.currentUser.lastName}</a>
							</h2>
						</div>
						<div class="groups">
							<div class="picture">
								<c:choose>
									<c:when test="${communityEraContext.currentUser.photoPresent}">
										<c:url value="${communityEraContext.contextUrl}/pers/userPhoto.img?showType=m" var="photoUrl">
											<c:param name="id" value="${communityEraContext.currentUser.id}" />
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
							<li class="selected"><a href="${communityEraContext.contextUrl}/pers/connectionCommunities.do?id=${communityEraContext.currentUser.id}">Communities</a></li>
							<li><a href="${communityEraContext.contextUrl}/blog/personalBlog.do?id=${communityEraContext.currentUser.id}">Blogs</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/connectionList.do?id=${communityEraContext.currentUser.id}">Connections</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/mediaGallery.do?id=${communityEraContext.currentUser.id}">Gallery</a></li>
						</ul>
					</div>
					 <div class="inputForm">
					 	<div class="intHeaderMain">
					 	Start a new Community
					 	</div>
					 	<div class="innerBlock">
							<form:form showFieldErrors="true" multipart="true" name="newCommunityForm">
								<input type="hidden" id="fTagList" name="fTagList" value="" />
								<input type="hidden" id="toggleList" name="toggleList" value="${command.toggleList}" />
								<form:errors message="Please correct the errors below" cssClass="errorText" /> <br />
								<div>
									<label for="name" >Name (100 characters max.) <img src="img/required.gif" class="normalTip" title="This field is required" width="8" height="8"/></label>
									<form:input id="name" path="name" maxlength="100" cssStyle="width: 716px; padding: 5px; margin: 0px;"></form:input>
								</div>
								<br />
								<div >
									<label for="welcomeText">Introductory text (500 characters max.) <img src="img/required.gif" class="normalTip" title="This field is required" width="8" height="8"/></label>
									<textarea id="editorWT" name="welcomeText"  rows="14" tabindex="1" class="newCommTextArea" style="width: 720px; height: 150px;" maxlength="500"></textarea>
									<br/> <br/>
									
									<label for="description">Description</label>
									<textarea name='description' id='descriptionEd' style='display:none;'></textarea><div id='editor'></div>
									<br/> <br/>
									
									<label style="font-size: 12px;">Type of community <img src="img/required.gif" class="normalTip" title="This field is required" width="8" height="8"/></label>
									
									<div style='margin:10px; word-break: keep-all;'>
										<input name='communityType' type='radio' id='radioOpt2' checked="checked" value="Public"/>
										<label for='radioOpt2'><span></span>Public community.</label> Anyone can join without approval and invite others to join. Anyone can see the community information and its content. 
									</div>
									<div style='margin:10px; word-break: keep-all;'>
										<input name='communityType' type='radio' id='radioOpt3' value="Protected"/>
										<label for='radioOpt3'><span></span>Protected community.</label> Anyone can see the community information and its content but joining is protected by approval. 
									</div>
									<div style='margin:10px; word-break: keep-all;'>
										<input name='communityType' type='radio' id='radioOpt1' value="Private"/>
										<label for='radioOpt1'><span></span>Private community.</label> The community will not appear in search results or in the profiles of its members. Membership is by invitation only, and only members can see the community information and its content.
									</div>
								</div>
									
								<cera:taggingSelection tags="${command.tags}" context="${communityEraContext}" maxTags="15" parentType="community"/>
								<div style="margin-bottom: 30px; width:100%;">
									<a href="javascript:void(0);" onclick="publishNewCommunity();" class="btnmain" style="float:right;"><i class="fa fa-check" style="margin-right: 4px;"></i>Publish</a>
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