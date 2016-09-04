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
		<meta name="robots" content="noindex, follow" />
		<meta name="author" content="Jhapak">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<meta name="description" content="${command.cons.name}. ${command.cons.description}" />
		<meta name="keywords" content="Jhapak, blog, edit-blog, community, ${command.cons.keywords}" />
		<title>${command.cons.name} - Jhapak</title>
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		<link rel="publisher" href="https://plus.google.com/+Jhapak4u"/>
		<meta property="article:publisher" content="https://www.facebook.com/EraOfCommunities" />
		
		<link rel="stylesheet" media="all" type="text/css" href="css/profile.css" />
		
		<script type="text/javascript" src="js/qtip/dynamicDropDownQtip.js"></script>
		<script type="text/javascript" src="js/qtip/memberInfoTip.js"></script>
		
		<link type="text/css" href="css/jquery.jscrollpane.css" rel="stylesheet" media="all" />
 		<script type="text/javascript" src="js/jquery.jscrollpane.min.js"></script>
 		<link type="text/css" href="css/jquery.jscrollpane.lozenge.css" rel="stylesheet" media="all" />
 		
 		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style type="">
			.connectionInfo {
			    top: unset;
				bottom: -40px;
			    right: 20px;
			}
			
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
			
			.secLev .btnmain, .secLev .btnmain {
				margin: 4px 2px;
				padding: 0px 10px;
			}
			
			.secLev .btnmain:hover {
				box-shadow: none;
			}
			
			.scroll-pane
			{
				width: 100%;
				height: 330px;
				overflow: auto;
			}
			
			.jspCap
			{
				display: block;
				background: #eeeef4;
			}
			
			.jspVerticalBar {
			    width: 8px;
			}
			
			.jspVerticalBar .jspCap
			{
				height: 1px;
			}
 			
 			.addMember-dialog .modal-dialog {
                height: 200px;
            }
            
            .modal-body {
			    min-height: 140px;
			    height: 400px;
			}
			
			.intHeader {
				height: 40px;
				background: none repeat scroll 0% 0% #EEF3F6;
				clear: both;
				text-decoration: none;
			}
			
			.intHeader form {
			    color: #008EFF;
			    font-size: 13px;
			    font-weight: normal;
			    padding: 8px;
			}
			
			.intHeader form label, .mrolelab {
			    color: #707070;
			    float: left;
			    font-size: 12px;
			    font-weight: normal;
			    height: 20px;
			    line-height: 20px;
			    margin-right: 6px;
			}
			
			.intHeader form select, .mrole {
			    border: 1px solid #D6D6D6;
			    background: none repeat scroll 0% 0% #F2F2F2;
			    color: #2C3A47;
			    margin: 0px 12px 5px 0px;
			    padding: 2px 5px;
			    width: 150px;
			    float: left;
			}
			
			input.searchResult {
				border: 1px solid #D6D6D6;
			    background: none repeat scroll 0% 0% #F2F2F2;
			    color: #2C3A47;
			    margin: 0px 12px 5px 0px;
			    padding: 2px 5px;
			    width: 300px;
			    font-size: 12px;
			    font-weight: normal;
			    height: 18px;
			}
			
			.searchResult {
				width: 408px;
				height: 18px;
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
			
			p.addListHead {
				font-size: 12px;
				color: #898F9C;
				font-weight: bold;
				padding-bottom: 2px;
			}
			
			p.secLine {
				font-size: 11px;
				color: #898F9C;
				font-weight: normal;
				padding-bottom: 16px;
			}
			
			.myCustomClass31 li.srchRslt {
				padding: 2px;
				font-size: 12px;;
			}
			
			.myCustomClass31 li.srchRslt:hover {
				padding: 2px;
				background: none repeat scroll 0% 0% #F7F7F7;
			}
			
			.myCustomClass31 {
			    width: 320px;
			    left: 386px;
			    border-radius: 6px;
			    margin: 1px 0px 10px 8px;
				box-shadow: 0px 1px 3px rgba(0, 0, 0, 0.1);
				white-space: nowrap;
				text-align: left;
				background-color: #F0EDED;
				border: 2px solid #BBB;
				color: #524D4D;
				line-height: 2em;
				list-style: none outside none;
				padding: 4px;
			}
			
			.myCustomClass31 li {
				cursor: pointer;
				padding: 2px 4px;
				white-space: nowrap;
				overflow: hidden;
				text-overflow: ellipsis;
			}
			
			.myCustomClass31 li:hover {
				color: #66799f;
				text-decoration: none;
				background-color: #243F52;
			}
			
			.promo-user-new {
				display: inline-block;
			}
			
			.intrst {
			    margin: 0px 0px 0px;
				padding: 3px 7px;
				border: 1px solid #D3D5D7;
				border-radius: 4px;
				background: none repeat scroll 0% 0% #FFF;
				font-size: 0.917em;
				line-height: 1.364;
				display: inline-block;
				position: relative;
			}
			
			.intrst-seleceted {
			    margin: 0px 0px 5px;
				padding: 3px 7px;
				border: 1px solid #D3D5D7;
				border-radius: 4px;
				background: none repeat scroll 0% 0% #F6F8F7;
				font-size: 0.917em;
				line-height: 1.364;
				display: inline-block;
				position: relative;
			}
			
			.intrst-txt {
			    display: inline-block;
			    overflow: hidden;
			    max-width: 135px;
			    text-overflow: ellipsis;
			    margin: 0px 0px 1px 1px;
			    vertical-align: middle;
			    white-space: nowrap;
			    font-size: 11px;
			}
			
			.intrst-txt-seleceted {
			    display: inline-block;
			    overflow: hidden;
			    max-width: 135px;
			    margin: 0px 0px 1px 1px;
			    vertical-align: middle;
			    white-space: nowrap;
			    text-overflow: ellipsis;
			    color: #365BD4;
			}
			
			.rmInst {
				padding:3px 1px 2px 4px; 
				font-weight:bold; 
				color: rgb(137, 143, 156); 
				font-size: 12px;
				cursor: pointer;
			}
			
			.qtip {
			    max-width: 380px;
			    max-height: unset;
			}
			
			.connectionInfo span.staticDropDown {
			    background-color: #F0EDED;
			    border: 1px solid #BBB;
			    border-radius: 2px;
			    color: #524D4D;
			    display: inline-block;
			    float: right;
			    font-weight: bold;
			    line-height: 1em;
			    margin-right: 5px;
			    padding: 5px 9px;
			    text-align: center;
			    text-shadow: 0px 1px rgba(255, 255, 255, 0.9);
			}
		</style>
		
		<script type="text/javascript">
			var selectedUserIds = [];
			function addAuthors(consId)
			{ 
				$(".qtip").hide();
				var dialogInstance = BootstrapDialog.show({
					title: 'Add Authors',
					message: function(dialog) {
					var $message = $('<div id="main"><p id="waitCloudMessage" style="margin-left: 235px; margin-top: 180px; min-height:250px; display: none;" class="showCloudWaitMessage" ></p></div>');
	                return $message;
	            },
	            cssClass: 'addMember-dialog',
	            closable: false,
	            closeByBackdrop: false,
	            buttons: [{
	                label: 'Close',
	                action: function(dialog){
	            	var selectedUserId = [];
	            	dialog.close();
	            }
	            }, {
	                label: 'Save',
	                cssClass: 'btn-primary',
	                action: function(dialog) {
	                $.ajax({
	                	type:"POST",
	                	url:"${communityEraContext.contextUrl}/blog/addAuthors.ajx?consId="+consId,
	                	data: {json:selectedUserIds},
			            success:function(result){
	                	dialog.close();
	                	window.location.href='${communityEraContext.contextUrl}/blog/manageBlogAuthors.do?bid='+consId;
	    			    }
	    		    });
	                }
	            }],
	            cssClass: 'login-dialog',
	            onshown: function(dialogRef){
						var sName = "<div class='searchInt' id='searchInt'>";
						sName += "<p class='addListHead' >Add authors to this blog by name or email address.</p>";
						sName += "<p class='secLine' >(Max 50 authors in one go)</p>";
						sName += "<label class='mrolelab' for='userRole'>Permissions</label><select name='userRole' id='userRole' class='mrole'>";
						sName += "<option value='1'>Owner</option><option value='2'>Author</option></select>";
						sName += "<input id='intSearch' tabindex='0' value='' class='searchResult' placeholder='Type to find person...' maxlength='50' autocomplete='off' type='text' onkeydown='searchPerson("+consId+", 0)' onmousedown='searchPerson("+consId+", 0)' />";
						sName += "</div>";
						
						sName += "<div  style='width:100%; display: inline-block; height:320px;'><div class='scroll-pane horizontal-only'>";
						sName += "<div id='newMemberLst'></div></div></div>";
						dialogRef.getModalBody().find('#main').html(sName);
	                	// Hide message
	    		        dialogRef.getModalBody().find('#waitCloudMessage').hide();
	    		        dialogRef.getModalBody().find('.scroll-pane').jScrollPane(
			        		{
			        			verticalDragMinHeight: 20,
			        			verticalDragMaxHeight: 200,
			        			autoReinitialise: true
			        			}
		    		        );
	            	}
		        });
			}

			function searchPerson(consId, option)
			{
				var searchString = document.getElementById('intSearch');
				$('.searchResult').each(function() {
			         $(this).qtip({
			            content: {
			                text: function(event, api) {
			                    $.ajax({
			                        url: '${communityEraContext.contextUrl}/search/searchRegisteredUser.ajx?searchString='+searchString.value+"&pconsId="+consId // Use href attribute as URL
			                    })
			                    .then(function(content) {
				                    if (content.bData.length == 0) {
				                    	$(".qtip").hide();
									}
			                    	var sList = "<ul>";
			                    	$.each(content.bData, function() {
			                    		sList += "<li class='srchRslt' onclick=\"addToMyList(&#39;"+this['userId']+"&#39;, &#39;"+this['firstName']+"&#39;, &#39;"+this['lastName']+"&#39;, &#39;"+option+"&#39;)\" >";
			                    		if(this['photoPresent'] == 'Y'){
			                    			sList += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['userId']+"' width='30' height='30' />&nbsp;&nbsp;";
			                    		}else{
			                    			sList += "<img src='img/user_icon.png'  width='30' height='30'/>&nbsp;&nbsp;";
			                    		}
			                    		sList += this['firstName']+" "+this['lastName']+" "+"&lt;"+this['emailAddress']+"&gt;"+"</li>";
			                    		
			                    	});
			                    	sList += "</ul>";
			                        api.set('content.text', sList);
			                    }, function(xhr, status, error) {
			                        api.set('content.text', 'Loading...');
			                    });
			                    return '<div class=\'footer-hint\' style=\'margin-bottom:10px;\'></div>'; // Set some initial text
			                }
			            },
			            show: {
			            	event: 'keyup mouseenter',
			                target: $('.searchResult')
			            },
			            position: {
			                my: 'top left',  // Position my top left...
			                at: 'bottom left', // at the bottom left of...
			                target: $('.searchResult') // my target
			            },
			            hide: {
			                event: 'unfocus'
			            },
						style: {
					        classes: 'qtip-bootstrap myCustomClass31'
					    }
			         });
			     });
			}

			function inArray(arr, obj) {
			    for(var i=0; i<arr.length; i++) {
			        if (arr[i] == obj) return true;
			    }
			    return false;
			}

			function removeNewMember(userId) {
				var id = "myOldMembers"+userId;
				var element1 = document.getElementById(id);
				element1.outerHTML = "";
				var index1 = selectedUserIds.indexOf(userId+"#0");
				var index2 = selectedUserIds.indexOf(userId+"#1");
				if(index1 > -1){
					delete selectedUserIds[ index1 ];
				}
				if(index2 > -1){
					delete selectedUserIds[ index2 ];
				}
			}
			
			function addToMyList(userId, firstName, lastName, option) {
				$(".qtip").hide();
				if(userId > 0 && selectedUserIds.length < 51 && !inArray(selectedUserIds, userId+"#0") && !inArray(selectedUserIds, userId+"#1")) {
					var uRoleID = $("#userRole").val();
					var uRoleTxt = $("#userRole option:selected").text();
					var sName = "<div class='promo-user-new' style='margin: 10px 0px 6px 6px;' id='myOldMembers"+userId+"'><div class='intrst' id='"+userId+"'>";
					if(option == 0){
						sName += "<span class='intrst-txt'>"+firstName+" "+lastName+" ("+uRoleTxt+")</span>";
					}else{
						sName += "<span class='intrst-txt'>"+firstName+" "+lastName+"</span>";
					}
					sName += "<span class='normalTip rmInst' onclick='removeNewMember(&#39;"+userId+"&#39;);' title='Remove &#39;"+firstName+" "+lastName+"&#39; from your new members list' >X</span></div></div>";
					$( "#newMemberLst" ).append(sName);
					if(option == 0){
						selectedUserIds.push(userId+"#"+uRoleID);
					}else{
						selectedUserIds.push(userId+"#0"); // can be invite as member only
					}
					
					$('.normalTip').qtip({ 
					    content: {
					        attr: 'title'
					    },
						style: {
					        classes: 'qtip-tipsy'
					    }
					});
				}
			}
		</script>
		
		<script type="text/javascript">
			function updateAuthorRole(authId, role) {
				$(".qtip").hide();
				$.ajax({url:"${communityEraContext.contextUrl}/blog/updateAuthors.ajx?authId="+authId+"&role="+role,success:function(result){
		    	    $("#authId-"+authId).html(result);
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
				normalQtip();
				memberInfoQtip();
				dynamicDropDownQtip();
			});

			function removeAuthors(pconsId)
			{
				var json = []; 
            	$('input:checkbox[name=markVisit]:checked').each(function(i, selected){ 
            		json[i] = $(selected).attr('id');
            	});
                $.ajax({
                	type:"POST",
                	url:"${communityEraContext.contextUrl}/blog/deleteAuthors.ajx",
                	data: {json:json},
		            success:function(result){
                		window.location.href = "${communityEraContext.contextUrl}/blog/manageBlogAuthors.do?bid="+pconsId;
    			    }
    		    });
			}

			function toggleBtnStyle()
			{
				$(".qtip").hide();
				var somval = $( "input:checkbox[name=markVisit]:checked" ).length;
				if(somval > 0) {
				     document.getElementById('removevisitors').disabled = false;
				     document.getElementById('removevisitors').className = "";
				     document.getElementById('removevisitors').className = "btnmain";
				} else {
					 var removevisitors = document.getElementById('removevisitors');
					 if (removevisitors != null) {
						 document.getElementById('removevisitors').disabled = "disabled";
					     document.getElementById('removevisitors').className = "";
					     document.getElementById('removevisitors').className = "btnmain";
						}
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
								<img src="${communityEraContext.contextUrl}/pers/userPhoto.img?imgType=Cover&showType=m&id=${command.user.id}" 
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
							<li><a href="${communityEraContext.contextUrl}/pers/connectionResult.do?id=${command.user.id}">Profile</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/connectionCommunities.do?id=${command.user.id}">Communities</a></li>
							<li class="selected">
								<a href="${communityEraContext.contextUrl}/blog/manageBlogAuthors.do?bid=${command.cons.id}" class='dynaDropDown' 
									title='community/communityOptions.ajx?currId=3009&itemId=${command.user.id}'>Blog Authors (${command.rowCount}) <span class='ddimgWht'/></a>
							</li>
							<li><a href="${communityEraContext.contextUrl}/pers/connectionList.do?id=${command.user.id}">Connections</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/mediaGallery.do?id=${command.user.id}">Gallery</a></li>
						</ul>
					</div>
					
					<div class="inputForm" style="display: inline-block; width: 100%; padding: 0px;" id="inputForm">
						<div class="intHeader">
							<span class='firLevAlt' title="Blog authors list" class="normalTip" >
									Manage Blog Authors <span id="allConnectionCount">(${command.rowCount})</span>
								</span>
							<c:if test="${communityEraContext.userAuthenticated}">
								<c:if test="${command.user.id == communityEraContext.currentUser.id}">
									<%-- <span class='firLev'>
										<form:form showFieldErrors="true">
											<form:dropdown path="sortByOption" fieldLabel="Sort By:">
												<form:optionlist options="${command.sortByOptionOptions}" />
											</form:dropdown>
											<input type="hidden" name="page" value="${command.page}" />
											<input type="hidden" id="sortByOption" name="sortByOption" value="${command.sortByOption}" />
											<input type="submit" value="Go"/>
										</form:form>
									</span> --%>
									<c:if test="${command.rowCount > 0}">
										<span class='secLev' id="">
											<input type="button" class="btnmain normalTip" onclick="addAuthors('${command.cons.id}');" id="addAuthors" value="Add Authors" />
											<input type="button" class="btnmain normalTip" onclick="removeAuthors('${command.cons.id}');" id="removevisitors" value="Remove Selected" disabled="disabled"/>
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
											<c:if test="${command.cons.userId == row.id}">
												<div class='person'>Started at ${command.startedOn}</div>
											</c:if>
										</div>
										
										<c:choose>
											<c:when test="${command.cons.userId == row.id}">
												<div class='connectionInfo'><span class='staticDropDown'>Owner</span></div>
											</c:when>
											<c:otherwise>
												<div class="chkPVI"><input name='markVisit' id="${row.authId}" tabindex="2" type='checkbox' onclick="toggleBtnStyle();"/>
												<label for="${row.authId}"><span></span></label></div>
												<div id='authId-${row.authId}' class='connectionInfo'>${row.roleInfo}</div>
											</c:otherwise>
										</c:choose>
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
	</body>
</html>