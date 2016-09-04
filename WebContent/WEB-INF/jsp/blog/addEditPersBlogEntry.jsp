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
		<meta name="robots" content="noindex,nofollow" /><!--default_cached-->
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<meta name="description" content="Add or edit a personal blog entry" />
		<meta name="keywords" content="Jhapak, blog, edit-blog, add-blog, how-to, personal" />
		<title>Jhapak - ${command.blogActionLabel} blog entry</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<meta name="author" content="Jhapak">
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		<link rel="publisher" href="https://plus.google.com/+Jhapak4u"/>
		<meta property="article:publisher" content="https://www.facebook.com/EraOfCommunities" />
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		
		<link type="text/css" rel="stylesheet" href="css/input.css" />
		<link rel="stylesheet" media="all" type="text/css" href="css/profile.css" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
	 	<style type="text/css">
			
			div.tag-picker {
				background-color: #F0F9FF; 
				border-color: #0099FF;
			}
					
			.tag-dialog .modal-dialog .modal-body {
                height: 50px;
                padding : 20px;
            }
            
            form .btnmain {
            	margin: 0px 8px;
            }
            
            .cmName {
	            max-width: 1px;
				text-overflow: ellipsis;
				overflow: hidden;
				white-space: nowrap;
			}
			
			.cmName a {
	            max-width: 1px;
				text-overflow: ellipsis;
				overflow: hidden;
				white-space: nowrap;
			}
		</style>
		<cera:taggingJS context="${communityEraContext}"/>
		<script src="ckeditor/ckeditor.js"></script>
		
		<script type="text/javascript">
			// Initial call
			$(document).ready(function () {
				$.ajax({url:"${communityEraContext.contextUrl}/common/userPannel.ajx?blogId=${command.personalBlog.id}",dataType: "json",success:function(result){
					var temp = "";
					
					$.each(result.aData, function() {
						
						if(this['photoPresant'] == "1"){
							temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' onmouseover='tip(this, &#39;"+this['name']+"&#39;)'>";
							temp += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['userId']+"' width='38' height='38' style='padding: 3px;border-radius: 50%;' />";
							temp += "</a>";
						} else {
							 temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' onmouseover='tip(this, &#39;"+this['name']+"&#39;)'>";
							 temp += "<img src='img/user_icon.png' id='photoImg' width='38' height='38' style='padding: 3px;border-radius: 50%;' />";
							 temp += "</a>";
						 }
						});
					 $("#authorsList").html(temp);
					 
					// Hide message
			        $("#waitBLAthMessage").hide();
			        
				    },
				 	// What to do before starting
			         beforeSend: function () {
			             $("#waitBLAthMessage").show();
			         } 
			    });
			    
				$.ajax({url:"${communityEraContext.contextUrl}/community/allCommunitiesCloud.ajx?parentType=Community",dataType: "json",success:function(result){
					var aName = "<ul>";
					var fTagList = $("#fTagList").val();
					var commOption = $("#communityOption").val();
					var sortOption = $("#sortByOption").val();
					 $.each(result.aData, function() {
						 var count = 'community';
						 if (this['count'] > 1) {
							 count = 'communities';
						 }
						 var content = "View "+this['count']+" "+count+" tagged with &#39;"+this['tagText']+"&#39;";
						 aName += "<li>";
						 aName += "<a href='javascript:void(0);' onclick='applyFilter(&#39;${communityEraContext.contextUrl}/community/showCommunities.do?filterTag="+this['tagText']+"&#39;);'";
						 aName += " onmouseover='tip(this,&quot;"+content+"&quot;)' class='size-"+this['cloudSet']+"' id='"+this['tagText']+"'>"+this['tagText']+"</a>";
						 aName += "</li>";
							});
					 $("#cloud").html(aName+"</ul>");
					 
					 var bName = "<table >";
					 $.each(result.bData, function() {
						 var count = 'community';
						 if (this['count'] > 1) {
							 count = 'communities';
						 }
						 var content = "View "+this['count']+" "+count+" tagged with &#39;"+this['tagText']+"&#39;";
						 bName += "<tr><td>";
						 bName += "<span class='size-"+this['cloudSet']+"' ><a id='"+this['tagText']+"' href='javascript:void(0);' onclick='applyFilter(&#39;${communityEraContext.contextUrl}/community/showCommunities.do?filterTag="+this['tagText']+"&#39;);' ";
						 bName += " onmouseover='tip(this,&quot;"+content+"&quot;)' class='size-"+this['cloudSet']+"' >"+this['tagText']+"</a></span>";
						 bName += "</td><td style='color: rgb(42, 58, 71);  float: right;'>["+this['count']+"]</td>";
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

				normalQtip();
			});
			
			function submitBlog(){
				var toElm = document.getElementById('tags');
				var toArray = toElm.value.split(' ');
				var isSpecialCharPresent = false;
				for (i=0; i<toArray.length; i++) { 		
					var str = toArray[i].toLowerCase();				
					if (str.match("[&,?]")) {
						isSpecialCharPresent = true;
						break;
					}
				}
				if (isSpecialCharPresent) {
					var type = BootstrapDialog.TYPE_DANGER;
			    	BootstrapDialog.show({
		                type: type,
		                title: 'Error',
		                message: 'Special characters like &, \',\' and ?, etc are not allowed in tags',
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
					document.submitBlogEntry.action = "${communityEraContext.requestUrl}";
					document.submitBlogEntry.submit();
			    }
			}
		</script>
		
		<script type="text/javascript">
			function addBlogAuthors(communityId) 
			{ 
				var dialogInstance = BootstrapDialog.show({
					title: 'Blog Authors',
					message: function(dialog) {
					var $message = $('<div id="main"><p id="waitCloudMessage" style="margin-left: 380px; margin-top: 180px; min-height:250px; display: none;" class="showCloudWaitMessage" ></p></div>');
	                return $message;
	            },
	            buttons: [{
	                label: 'Cancel',
	                action: function(dialog){
	            	dialog.close();
                }
	            }, {
	                label: 'Save',
	                cssClass: 'btn-primary',
	                action: function(dialog) {
	                var flist = $( ".clearfix2" ).map(function() {return this.id;}).get().join();
	                    processSave(flist);
	                    dialog.close();
	                }
	            }],
	            cssClass: 'login-dialog',
	            onshown: function(dialogRef){
					$.ajax({url:"${communityEraContext.contextUrl}/event/makeInviteeList.ajx?communityId="+communityId ,dataType: "json",success:function(result){
						var stoggle = "";
						var sName = "<div style='overflow: hidden;'><div class='finallist' style='padding-right: 7px;'><div style='padding:10px;'>Final list</div><div id='scrollpane' class='scroll-pane2'><form id='formset' method='post' action=''></form></div>";
						sName += "</div><div><div style='width:70%;'>";						
						//sName += "<div class='search'>";
						//sName += "<input tabindex='0' value='' class='' type='text' />";
						//sName += "</div>";
						
						sName += "<div class='lev'><div class='scroll-pane horizontal-only'><ul>";
						$.each(result.aData, function() {
							if(this['commId'] == communityId){
								sName += "<li class='lientrySelected' style='margin:4px 0px;' id='commID-"+this['commId']+"' onclick='refreshCommunities(&#39;"+this['commId']+"&#39;, &#39;"+dialogRef+"&#39;);'>"+this['commName']+"</li>";
							} else {
								sName += "<li class='lientry' style='margin:4px 0px; padding:2px;' id='commID-"+this['commId']+"' onclick='refreshCommunities(&#39;"+this['commId']+"&#39;, &#39;"+dialogRef+"&#39;);'>"+this['commName']+"</li>";
							}
						});

						sName += "</ul></div></div>";
						
						var temp = "";
						var counter = 0;
						$.each(result.bData, function() {
							counter += 1;
							var rid = this['userId'];
							stoggle += rid + "#" + this['photoPresant'] + "#" +this['name']+ ",";
							temp += "<li class='lientry'><div class='clearfix' id='"+rid+"' onclick='expand(&#39;"+rid+"&#39;, &#39;"+this['photoPresant']+"&#39; , &#39;"+this['name']+"&#39;)'>";
							if(this['photoPresant'] == "1"){
								temp += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['userId']+"' />"; }
							 else {
								 temp += "<img src='img/user_icon.png' />";
							 }
							temp += "<span id='selection-"+rid+"' class='selection2'></span><div class='header'>"+this['name']+"</div><div class='loc'>"+this['location']+"</div></div></li>";
							});

						sName += "<div class='rev'><div class='clearfix'><div class='tohele'>INVITEES ("+counter+")</div><div class='toheri'><a href='javascript:void(0);' onclick='toggleSelect(&#39;0&#39;,&#39;"+stoggle+"&#39;);'>Select All</a></div></div><div class='scroll-pane horizontal-only'><ul>";
						sName += temp;
						sName += "</ul></div></div>";

						
						sName += "</div></div>";
						sName += "</div>";
						
						 dialogRef.getModalBody().find('#main').html(sName);
						// var ii = 8;
	                	// Hide message
	    		        dialogRef.getModalBody().find('#waitCloudMessage').hide();
		    		       // if(ii > 8){
	    		        dialogRef.getModalBody().find('.scroll-pane').jScrollPane(
	    		        		{
	    		        			verticalDragMinHeight: 20,
	    		        			verticalDragMaxHeight: 200,
	    		        			autoReinitialise: true
	    		        			}
	    	    		        );
	    		        dialogRef.getModalBody().find('.scroll-pane2').jScrollPane(
	    		        		{
	    		        			verticalDragMinHeight: 20,
	    		        			verticalDragMaxHeight: 200,
	    		        			autoReinitialise: true
	    		        			}
	    	    		        );
	    		       // }
	    		        $.each(result.bData, function() {
		    		        var id = this['userId'];
		    		        var photoPresant = this['photoPresant'];
		    		        var name = this['name'];
	    		        	var fset = document.getElementById('formset');
	    					var spanTag = document.getElementById('selection-'+id);
	    					var ss = id+"#"+photoPresant+"#"+name;
    					   spanTag.className = 'selection2';
    					   
    					   var tag = "<div class='clearfix2' id='"+ss+"' onclick='rmFlist(&#39;"+id+"&#39;, &#39;"+ss+"&#39;)'>";
    					   if(photoPresant == "1"){
    						   tag += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+id+"'  width='30' height='30' style='float: left;'/>"; }
    						 else {
    							 tag += "<img src='img/user_icon.png'  width='30' height='30' style='float: left;'/>";
    						 }
    					   tag += "<span id='finalSelection-"+id+"' class='selection3'></span><div class='header2'>"+name+"</div></div>";
    					   document.getElementById('formset').innerHTML += tag;
    					   dialogRef.getModalBody().find('.scroll-pane').jScrollPane(
   	    		        		{
   	    		        			verticalDragMinHeight: 20,
   	    		        			verticalDragMaxHeight: 200,
   	    		        			autoReinitialise: true
   	    		        			}
   	    	    		        );
    					   dialogRef.getModalBody().find('.scroll-pane2').jScrollPane(
      	    		        		{
      	    		        			verticalDragMinHeight: 20,
      	    		        			verticalDragMaxHeight: 200,
      	    		        			autoReinitialise: true
      	    		        			}
      	    	    		        );
	    		        });
						
	    			    },
	    			 	// What to do before starting
	    		         beforeSend: function () {
	    			    	dialogRef.getModalBody().find('#waitCloudMessage').show();
	    		         } 
	    		    });
	            }
		        });
			}

			function refreshCommunities(communityId, dialogRef) {
				$.ajax({url:"${communityEraContext.contextUrl}/event/makeInviteeList.ajx?communityId="+communityId ,dataType: "json",success:function(result){
					var stoggle = "";
					var sName = "";						
					$.each(result.aData, function() {
						if(this['commId'] == communityId){
							var spanTag = document.getElementById('commID-'+this['commId']);
							   spanTag.className = 'lientrySelected';
						} else {
							var spanTag = document.getElementById('commID-'+this['commId']);
							   spanTag.className = 'lientry';
						}
					});

					
					var temp = "";
					var counter = 0;
					$.each(result.bData, function() {
						counter += 1;
						var rid = this['userId'];
						stoggle += rid + "#" + this['photoPresant'] + "#" +this['name']+ ",";
						temp += "<li class='lientry'><div class='clearfix' id='"+rid+"' onclick='expand(&#39;"+rid+"&#39;, &#39;"+this['photoPresant']+"&#39; , &#39;"+this['name']+"&#39;)'>";
						if(this['photoPresant'] == "1"){
							temp += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['userId']+"' />"; }
						 else {
							 temp += "<img src='img/user_icon.png' />";
						 }
						var trigerList = $("div[class='clearfix2']");
						var selCss = "selection";
						var temp2 = rid + "#" + this['photoPresant'] + "#" +this['name'];
						 $.each(trigerList, function() {
	  					   if ( $( this ).attr("id") ==  temp2) {
	  						 selCss = "selection2";
	  					   }
						   });
						temp += "<span id='selection-"+rid+"' class='"+selCss+"'></span><div class='header'>"+this['name']+"</div><div class='loc'>"+this['location']+"</div></div></li>";
						});

					sName += "<div class='clearfix'><div class='tohele'>INVITEES ("+counter+")</div><div class='toheri'><a href='javascript:void(0);' onclick='toggleSelect(&#39;0&#39;,&#39;"+stoggle+"&#39;);'>Select All</a></div></div><div class='scroll-pane horizontal-only'><ul>";
					sName += temp;
					sName += "</ul></div></div>";

					
					sName += "</div></div>";

					$(".rev").html(sName);
					$('.scroll-pane').jScrollPane(
    		        		{
    		        			verticalDragMinHeight: 20,
    		        			verticalDragMaxHeight: 200,
    		        			autoReinitialise: true
    		        			}
    	    		        );
    		       // }
    			    }
    			 	
    		    });
			}

			function expand(id, photoPresant, name){
				var fset = document.getElementById('formset');
				var spanTag = document.getElementById('selection-'+id);
				var ss = id+"#"+photoPresant+"#"+name;
			   if(spanTag.className == "selection") {
				   if (spanTag.parentNode != fset){
				   spanTag.className = 'selection2';
				   
				   var tag = "<div class='clearfix2' id='"+ss+"' onclick='rmFlist(&#39;"+id+"&#39;, &#39;"+ss+"&#39;)'>";
				   if(photoPresant == "1"){
					   tag += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+id+"'  width='30' height='30' style='float: left;'/>"; }
					 else {
						 tag += "<img src='img/user_icon.png'  width='30' height='30' style='float: left;'/>";
					 }
				   tag += "<span id='finalSelection-"+id+"' class='selection3'></span><div class='header2'>"+name+"</div></div>";
				   document.getElementById('formset').innerHTML += tag;
				   }
			   } else {
				   spanTag.className = 'selection';
				   fset.removeChild(document.getElementById(ss));
			   }

			   $('#scrollpane').jScrollPane(
		        		{
		        			verticalDragMinHeight: 20,
		        			verticalDragMaxHeight: 200,
		        			autoReinitialise: true
		        			}
	    		        );
			}

			function rmFlist(id, fid){
				var fset = document.getElementById('formset');
				fset.removeChild(document.getElementById(fid));
				var spanTag = document.getElementById('selection-'+id);
				spanTag.className = 'selection';
			}

			function processSave(ids){
				var invtList = document.getElementById('invtList');
				var fset = document.getElementById('finalList');
				
				if (ids != "") {
				var array = ids.split(',');
				if (array.length > 0){
					var tag = "";
					var temp ="";
					for(var x=0; x < array.length ;x++){
						var inner = array[x];
						var innerArray = inner.split('#');
						var spanTag = document.getElementById('selection-'+innerArray[0]);
						if (x < 15) {
						   if(innerArray[1] == "1"){
							   tag += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+innerArray[0]+"&backlink=ref' onmouseover='tip(this, &#39;"+innerArray[2]+"&#39;)' >";
							   tag += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+innerArray[0]+"'  width='43' height='43' style='float: left;padding: 0px 3px 3px 0px;'/></a>"; 
							   } else {
								   tag += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+innerArray[0]+"&backlink=ref' onmouseover='tip(this, &#39;${row.firstName} ${row.lastName}&#39;)' >";
								   tag += "<img src='img/user_icon.png'  width='43' height='43' style='float: left;padding: 0px 3px 3px 0px;'/></a>"; 
							 }
						}
						temp += innerArray[0]+",";
					}
					var fc = invtList.firstChild;
					while( fc ) {
						invtList.removeChild( fc );
					    fc = invtList.firstChild;
					}
					invtList.innerHTML += tag;
					fset.value = ids;
				}
				} else {
					var fc2 = invtList.firstChild;
					while( fc2 ) {
						invtList.removeChild( fc2 );
					    fc2 = invtList.firstChild;
					}
					fset.value = ids;
				}
			}

			
	function toggleSelect(isAllSelect, ids) {
		var array = ids.split(',');
		var invtList = document.getElementById('invtList');
		var fset = document.getElementById('formset');
		if (array.length > 0) {
			
			for ( var x = 0; x < array.length; x++) {
				var tag = "";
				var ss = array[x];
				var innerArray = ss.split('#');
				var spanTagleft = document.getElementById('selection-' + innerArray[0]);
				var spanTagright = document.getElementById('finalSelection-' + innerArray[0]);
				
				spanTagleft.className = 'selection2';
				if (!spanTagright) {
					tag += "<div class='clearfix2' id='" + ss
							+ "' onclick='rmFlist(&#39;" + innerArray[0] + "&#39;, &#39;"
							+ ss + "&#39;)'>";
					if (innerArray[1] == "1") {
						tag += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="
								+ innerArray[0]
								+ "'  width='30' height='30' style='float: left;'/>";
					} else {
						tag += "<img src='img/user_icon.png'  width='30' height='30' style='float: left;'/>";
					}
					tag += "<span id='finalSelection-"
							+ innerArray[0]
							+ "' class='selection3'></span><div class='header2'>"
							+ innerArray[2] + "</div></div>";
					document.getElementById('formset').innerHTML += tag;
						}
						
					}
					
				}
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
								<li class="selected"><a href="${communityEraContext.contextUrl}/blog/personalBlog.do">Blogs</a></li>
								<li><a href="${communityEraContext.contextUrl}/pers/connectionList.do?id=${communityEraContext.currentUser.id}">Connections</a></li>
								<li><a href="${communityEraContext.contextUrl}/pers/mediaGallery.do?id=${communityEraContext.currentUser.id}">Gallery</a></li>
							</ul>
						</div>
					
						 <div class="inputForm" style="display: inline-block;">
						 	<div class="intHeaderMain">
						 	${command.blogActionLabel}
						 	</div>
						 	<div class="innerBlock">
								<form:form showFieldErrors="true" commandName="command" multipart="true" name="submitBlogEntry">
									<form:hidden path="id" />
									<form:hidden path="bid" />
									<form:errors message="Please correct the errors below" cssClass="errorText" /> <br />
											<label for="title">Title (100 characters max.) <img src="img/required.gif" title="This field is required" width="8" height="8" class="required normalTip" /></label>
											<br />
											<form:input id="title" path="title" cssClass="editor" maxlength="100" cssStyle="width: 714px;"/>
											<br /><br />
											<label for="entry">Blog entry</label>
											<form:textarea cssClass="ckeditor" path="body" id="body"></form:textarea> <br />
											<cera:taggingSelection tags="${command.tags}" context="${communityEraContext}" maxTags="20" parentType="blog entry"/>
											<br />
											<c:if test="${command.mode == 'c'}">
												<div class="left" style="margin-bottom: 20px;">
													<a href="javascript:void(0);" onclick="submitBlog();" class="btnmain" >Submit entry</a>
												</div>
											</c:if>	
											<c:if test="${command.mode != 'c'}">
												<div class="left" style="margin-bottom: 20px;">
													<a href="javascript:void(0);" onclick="submitBlog();" class="btnmain" >Update entry</a>
												</div>
											</c:if>
										</form:form>
									</div>
								</div>
							</div> 
						</div>
						
						<div class="right-panel" style="margin-top: 0px;">
							<div class="inbox">
								<div class="eyebrow">
									<span onclick="return false" title="Blog Authors">Blog Authors</span>
								</div>
								<div id="authorsList" style="padding: 4px;" ></div>
								<div id="waitBLAthMessage" style="display: none;">
									<div class="cssload-square" style="width: 13px; height: 13px;">
										<div class="cssload-square-part cssload-square-green" style="width: 13px; height: 13px;"></div>
										<div class="cssload-square-part cssload-square-pink" style="width: 13px; height: 13px;"></div>
										<div class="cssload-square-blend" style="width: 13px; height: 13px;"></div>
									</div>
								</div>
								<div class="view">
								<%-- <c:if test='${command.cons.ownerId == communityEraContext.currentUser.id}'>
									<a href="${communityEraContext.contextUrl}/communityTags.do?backlink=ref">Add/Edit authors</a>
								</c:if> --%>
								</div>
							</div>
							
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