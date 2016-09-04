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
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<meta name="description" content="Jhapak, blog, start" />
		<meta name="keywords" content="Jhapak, blog, community, start, new-blog" />
		<title>Jhapak - Start a Blog</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		<meta name="author" content="Jhapak">
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		<link rel="stylesheet" media="all" type="text/css" href="css/profile.css" />
		<%@ include file="/WEB-INF/jspf/extraJs.jspf" %>
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
	 	<style type="text/css">
			div.tag-picker {
				background-color: #F0F9FF; border-color: #0099FF;
			}		
			
			.tag-dialog .modal-dialog .modal-body {
                height: 50px;
                padding : 20px;
            }
            
            #container .inputForm .innerBlock {
			    margin: 0px auto;
			    padding: 10px 10px 30px;
			}
		</style>
		<cera:taggingJS context="${communityEraContext}"/>
		
		<script type="text/javascript">
			function applyFilter(filterUrl){
				var toggleList = document.getElementById("toggleList").value;
				filterUrl += "&toggleList="+toggleList;
				window.location.href = filterUrl;
			}
			
			function submitBlog(){
				document.submitNewBlog.action = "${communityEraContext.requestUrl}";
				document.submitNewBlog.submit();
			}
		</script>
		
		<script type="text/javascript">
		// Initial call
		$(document).ready(function () {
			$.ajax({url:"${communityEraContext.contextUrl}/blog/blogPannel.ajx?type=top",dataType: "json",success:function(result){
				var temp = "<div class='pannel'>";
				$.each(result.aData, function() {
					temp += "<div class='pannelHeadder' style='display: inline-block; width: 100%;'>";
					temp += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['userId']+"' width='30' height='30' style='padding: 3px; float: left;' ";
					temp += " class='memberInfo' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'/>";
					temp += "<div class='pannelTitle' style='width: 100%; word-wrap: normal;'>";
					 if(this['communityId'] > 0){
						 temp += "<a href='${communityEraContext.contextUrl}/cid/"+this['communityId']+"/blog/blogEntry.do?id="+this['entryId']+"'>"+this['title']+"</a>";
					 } else {
						 temp += "<a href='${communityEraContext.contextUrl}/blog/blogEntry.do?id="+this['entryId']+"'>"+this['title']+"</a>";
					 }
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
							<li><a href="${communityEraContext.contextUrl}/pers/connectionCommunities.do?id=${communityEraContext.currentUser.id}">Communities</a></li>
							<li class="selected"><a href="${communityEraContext.contextUrl}/blog/personalBlog.do?id=${communityEraContext.currentUser.id}">Blogs</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/connectionList.do?id=${communityEraContext.currentUser.id}">Connections</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/mediaGallery.do?id=${communityEraContext.currentUser.id}">Gallery</a></li>
						</ul>
					</div>
					 <div class="inputForm" style="padding: 0px; display: inline-block; width: 100%;">
					 	<div class="intHeaderMain">
					 	Start a Blog
					 	</div>
					 	<div style="padding: 10px 10px 20px;">
							<form:form showFieldErrors="true" commandName="command" multipart="true" name="submitNewBlog">
								<form:errors message="Please correct the errors below" cssClass="errorText" /> <br />
								<label for="title">Name: <img src="img/required.gif" title="This field is required. The name of your blog will display at the top of your blog page. (Maximum length: 100 characters)." width="8" height="8" class="required normalTip" /></label>
								<br />
								<form:input id="name" path="name" cssClass="editor" maxlength="100" />
								<br /><br />
								<label for="entry">Description:</label>
								<form:textarea cssClass="editor" path="description" id="description" maxlength="255"></form:textarea> <br />
								<br />
								<div style="margin-bottom: 20px;">
									<a href="javascript:void(0);" onclick="submitBlog();" class="btnmain" style="float:right; margin:0px 2px;"><i class="fa fa-check" style="margin-right: 4px;"></i>Start Blog</a>
								</div>
								<input type="hidden" id="toggleList" name="toggleList" value="${command.toggleList}" />
							</form:form>
						</div>
					</div>
				</div> 
			</div>
				
			<div class="right-panel" style="margin-top: 0px;">
				<div class="inbox" style="display: inline-block;">
					<div class="eyebrow">
						<span onclick="return false" >Top Stories</span>
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
			</div>
		</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>