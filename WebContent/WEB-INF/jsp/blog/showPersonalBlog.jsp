<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="/WEB-INF/tlds/cera_form.tld"%>
<%@ taglib prefix="cera" uri="/WEB-INF/tlds/cera.tld"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<meta name="description" content="Jhapak, blog, ${command.cons.name}" />
		<meta name="keywords" content="Jhapak, blog, community, personal" />
		<title>${command.cons.name} - Jhapak</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<meta name="author" content="Jhapak">
		<meta name="robots" content="index, follow">
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		<link rel="publisher" href="https://plus.google.com/+Jhapak4u"/>
		<meta property="article:publisher" content="https://www.facebook.com/EraOfCommunities" />
	
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		<link rel="stylesheet" media="all" type="text/css" href="css/profile.css" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<%@ include file="/WEB-INF/jspf/extraJs.jspf" %>

		<script type="text/javascript">
			function applyFilter(filterUrl){
				var sortOption = document.getElementById("sortByOption").value;
				var toggleList = document.getElementById("toggleList").value;
				filterUrl += "&sortByOption="+sortOption+"&toggleList="+toggleList;
				window.location.href = filterUrl;
			}
			function likeEntry(rowId) 
			{ 
			    var isAuthenticated = $("#isAuthenticated").val();
			    if (isAuthenticated == 'false') {
			    	BootstrapDialog.show({
		                type: BootstrapDialog.TYPE_DANGER,
		                title: 'Error',
		                message: '<p class="extraWord">You are not logged-in.<br/> Please login first to like / unlike. </p>',
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
			    	$.ajax({url:"${communityEraContext.contextUrl}/blog/likeBlogEntry.ajx?blogEntryId="+rowId,success:function(result){
			    	    $("#div-"+rowId).html(result);
			    	  }});
			    }
			}
			
			function subscribeBlog(consId){
				var isAuthenticated = ${communityEraContext.userAuthenticated};
			    if (!isAuthenticated) {
			    	BootstrapDialog.show({
		                type: BootstrapDialog.TYPE_DANGER,
		                title: 'Following this Blog',
		                message: '<p class="extraWord">You are not logged-in.<br/> Please login first to start following this blog. </p>',
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
					$.ajax({url:"${communityEraContext.contextUrl}/blog/followBlogCons.ajx?id="+consId,success:function(result){
			    	    $("#subscribeBlog").html(result);
			    	    $('.normalTip').qtip({ 
						    content: {
						        attr: 'title'
						    },
							style: {
						        classes: 'qtip-tipsy'
						    }
						});
			    	  }
			    	});
			    }
			}
		
			function unSubscribeBlog(consId){
				var isAuthenticated = ${communityEraContext.userAuthenticated};
			    if (!isAuthenticated) {
			    	BootstrapDialog.show({
		                type: BootstrapDialog.TYPE_DANGER,
		                title: 'Stop following this Blog',
		                message: '<p class="extraWord">You are not logged-in.<br/> Please login first to stop following this blog. </p>',
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
					BootstrapDialog.show({
		                type: BootstrapDialog.TYPE_WARNING,
		                title: 'Stop following this Blog',
		                message: '<p class="extraWord">You will no longer be able to receive email alerts for blog entries published to this Blog.</p>',
		                closable: true,
		                closeByBackdrop: false,
		                closeByKeyboard: false,
		                buttons: [{
		                	label: 'Close',
		                    action: function(dialogRef){
		                        dialogRef.close();
		                    }
		                },{
		                	label: 'Go',
		                	cssClass: 'btn-warning',
		                    action: function(dialogRef){
		                        dialogRef.close();
		                        $.ajax({url:"${communityEraContext.contextUrl}/blog/stopFollowingBlogCons.ajx?id="+consId,success:function(result){
		            	    	    $("#subscribeBlog").html(result);
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
		                }]
		            });
			    }
			}
		</script>
		
		<script type="text/javascript">
		// Initial call
		$(document).ready(function(){

			$.ajax({url:"${communityEraContext.contextUrl}/common/userPannel.ajx?persBlogId=${command.cons.id}",dataType: "json",success:function(result){
				var temp = "";
				
				$.each(result.aData, function() {
					
					if(this['photoPresant'] == "1"){
						temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfo'";
						temp += "title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
						temp += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['userId']+"' width='38' height='38' style='padding: 3px;border-radius: 50%;' />";
						temp += "</a>";
					} else {
						 temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfo'";
						 temp += "title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
						 temp += "<img src='img/user_icon.png' id='photoImg' width='38' height='38' style='padding: 3px;border-radius: 50%;' />";
						 temp += "</a>";
					 }
					});
				 $("#authorsList").html(temp);
				 
				// Hide message
		        $("#waitBLAthMessage").hide();
		        $('.memberInfo').each(function() {
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
			 	// What to do before starting
		         beforeSend: function () {
		             $("#waitBLAthMessage").show();
		         } 
		    });

			$.ajax({url:"${communityEraContext.contextUrl}/common/allEntryCloud.ajx?toolType=PersonalBlog&toolId=${command.cons.id}",dataType: "json",success:function(result){
				var aName = "<ul>";
				var sortOption = $("#sortByOption").val();
				$.each(result.aData, function() {
					 var count = 'blog entry';
					 if (this['count'] > 1) {
						 count = 'blog entries';
					 }
					 var content = "View "+this['count']+" "+count+" tagged with &#39;"+this['tagText']+"&#39;";
					 aName += "<li>";
					 aName += "<a href='javascript:void(0);' onclick='applyFilter(&#39;${communityEraContext.contextUrl}/blog/allBlogs.do?filterTag="+this['tagText']+"&#39;);'";
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
					 var count = 'blog entry';
					 if (this['count'] > 1) {
						 count = 'blog entries';
					 }
					 var content = "View "+this['count']+" "+count+" tagged with &#39;"+this['tagText']+"&#39;";
					 bName += "<tr><td>";
					 bName += "<span><a id='"+this['tagText']+"' href='javascript:void(0);' onclick='applyFilter(&#39;${communityEraContext.contextUrl}/blog/allBlogs.do?filterTag="+this['tagText']+"&#39;);' ";
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
					$.ajax({url:"${communityEraContext.contextUrl}/blog/viewBlog.do?jPage="+num+"&bid=${command.bid}&sortByOption="+$("#sortByOption").val(),dataType: "json",success:function(result){
						var sName = "";
						var isinactive = ${command.commentAllowed};
						 $.each(result.aData, function() {
							 var rowId = this['id'];
							 sName += "<div class='paginatedList'><div class='leftImg'>";
							 
							 if(this['photoPresent'] == "Y"){
								 sName += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['posterId']+"&backlink=ref' ><img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['posterId']+"' /></a>"; }
							 else {
								 sName += "<img src='img/user_icon.png'  />";
							 }
							 sName += "</div><div class='details'>";
							 sName += "<div class='heading' style='width: 540px;word-wrap: normal;'>";
							 sName += "<a href='${communityEraContext.contextUrl}/blog/blogEntry.do?id="+rowId;
							 if(this['userId'] > 0){
								 sName += "&amp;userId="+this['userId']; 
							 }
	
							 sName += "'>"+this['title']+"</a></div>";
	
							 sName += "<div class='person'>Posted by <a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['posterId']+"&backlink=ref' class='memberInfo' ";
							 sName += "title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['posterId']+"'>"+this['displayName']+"</a> <i class='a-icon-text-separator'></i> "+this['postedOn'];
							 
							 if(this['userId'] <= 0){
								 	sName += "View blogs from <a href='${communityEraContext.contextUrl}/pers/blog-personal-index.do?userId="+this['posterId']+"'>"+this['displayName']+"</a>";
							 }
							 sName += "</div><div class='person'>"+this['visitors']+" view";
							 if(this['visitors'] > 1){
								 sName += "s";
							 }
							 sName += "<i class='a-icon-text-separator'></i>";
							 if(this['visitors'] > 0){
								 sName += "Last viewed at "+this['lastVisitTime']+"<i class='a-icon-text-separator'></i>";
							 }
							 
							 if(this['commentCount'] > 0){
								 sName += "<a href='${communityEraContext.contextUrl}/blog/blogEntry.do?id="+rowId+"#showComments";
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
							 
							 if(isinactive){
								 sName += "<i class='a-icon-text-separator'></i><a href='${communityEraContext.contextUrl}/blog/blogEntry.do?id="+rowId+"#addAComment";
								 if(this['userId'] > 0){
									 sName += "&amp;userId="+this['userId'];
								 }
								 sName += "'>Add Comment</a>";
							 }
							 
							 sName += "</div>";
	
							 if(this['taggedKeywords']){
								 sName += "<div class='person'><i class='fa fa-tags' style='font-size: 14px; margin-right: 4px;'></i>"+this['taggedKeywords']+"</div>";
							}
	
							 if(this['likeCount'] > 1){
								 sName += "<div class='entry' id='div-"+rowId+"'><span>"+this['likeCount'] + " likes</span>";
							 } else {
								 sName += "<div class='entry' id='div-"+rowId+"'><span>"+this['likeCount'] + " like</span>";
							 }
							 
							 sName += "</div><p>"+this['displayBody']+"</p></div></div>";
								});
						 $("#page").html(sName);
						// Hide message
					        $("#waitMessage").hide();
	
					        $('.memberInfo').each(function() {
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
							<c:if test="${command.owner.coverPresent}">
								<img src="${communityEraContext.contextUrl}/pers/userPhoto.img?imgType=Cover&showType=m&id=${command.owner.id}" 
						 			width="750px" height="270px" />
						 	</c:if>
						</div>
						<div class='detailsConnection'>
							<h2 >
								<a href="${communityEraContext.contextUrl}/pers/connectionResult.do?id=${command.owner.id}&backlink=ref" >${command.owner.firstName} ${command.owner.lastName}</a>
							</h2>
						</div>
						<div class="groups">
							<div class="picture">
								<c:choose>
									<c:when test="${command.owner.photoPresent}">
										<c:url value="${communityEraContext.contextUrl}/pers/userPhoto.img?showType=m" var="photoUrl">
											<c:param name="id" value="${command.owner.id}" />
										</c:url>
										<img src="${photoUrl}"  width='160' height='160' style=""/> 
									</c:when>
									<c:otherwise>
										<img src='img/user_icon.png'  width='160' height='160'/>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<c:if test="${communityEraContext.currentUser.id != command.owner.id}">
							<c:if test="${command.contactionAllowed}">				
								<div class='actions3' id='connectionInfo'>${command.returnString}</div>
							</c:if>
                       	</c:if>
					</div>
					<div class="nav-list">
						<ul>
							<li><a href="${communityEraContext.contextUrl}/pers/connectionCommunities.do?id=${command.owner.id}">Communities</a></li>
							<li class="selected"><a href="${communityEraContext.contextUrl}/blog/personalBlog.do?id=${command.owner.id}">Blogs</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/connectionList.do?id=${command.owner.id}">Connections</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/mediaGallery.do?id=${command.owner.id}">Gallery</a></li>
						</ul>
					</div>
					<div class="communities" style="padding: 10px;">
						<span style="font-weight: bold; font-size: 14px; color: #66799F; margin: 6px 0px; display: inline-block;">${command.cons.name}</span>
						<c:if test="${not empty command.cons.description}">
							<div class="subHeader" style="margin-bottom: 10px;">
							${command.cons.description}
							</div>
						</c:if>
						<div class="type" style="width: 240px; float: left; padding: 0px;">
							<form:form showFieldErrors="true">
								<form:dropdown path="sortByOption" fieldLabel="Sort By:" >
									<form:optionlist options="${command.sortByOptionOptions}" />
								</form:dropdown>
								<input type="hidden" id="bid" name="bid" value="${command.bid}" />
								<input type="hidden" id="sortByOption" name="sortByOption" value="${command.sortByOption}" />
								<input type="hidden" id="isAuthenticated" name="isAuthenticated" value="${communityEraContext.userAuthenticated}" />
								<input type="hidden" id="toggleList" name="toggleList" value="${command.toggleList}" />
								<input type="submit" value="Go" class="search_btn" />
							</form:form>
						</div>
						<div class="menus">
							<ul>
								<%@ include file="/WEB-INF/jspf/entire-btn-row.jspf"%>
								<c:if test="${command.currentUserSubscribed}">
									<li id="subscribeBlog"><a href="javascript:void(0);" onclick="unSubscribeBlog(${command.cons.id})" class="normalTip" 
											title="Unsubscribe from user blog entries published to this blog">Stop Following this Blog</a></li>
								</c:if>
								<c:if test="${not command.currentUserSubscribed}">
									<li id="subscribeBlog"><a href="javascript:void(0);" onclick="subscribeBlog(${command.cons.id})" class="normalTip" 
									 		title="Subscribe for email alerts for blog entries published to this blog">Follow this Blog</a></li>
								</c:if>
							</ul>
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

					<div class="inbox" style="display: inline-block; width: 296px; float: right;">
						<div class="eyebrow">
							<span onclick="return false" class="normalTip" title="Blog Authors">Blog Authors</span>
						</div>
						<div id="authorsList" style="padding: 4px;" ></div>
						<div id="waitBLAthMessage" style="display: none;">
							<div class="cssload-square" style="width: 13px; height: 13px;">
								<div class="cssload-square-part cssload-square-green" style="width: 13px; height: 13px;"></div>
								<div class="cssload-square-part cssload-square-pink" style="width: 13px; height: 13px;"></div>
								<div class="cssload-square-blend" style="width: 13px; height: 13px;"></div>
							</div>
						</div>
						<c:if test='${command.cons.userId == communityEraContext.currentUser.id}'>
							<div class="view">
								<a href="${communityEraContext.contextUrl}/blog/blogAuthors.do?bid=${command.cons.id}">view blog authors</a>
							</div>
						</c:if>
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
							</ul> <br/>
					</div>
				</div>
			</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		<%@ include file="/WEB-INF/jspf/extraFooter.jspf" %>
	</body>
</html>