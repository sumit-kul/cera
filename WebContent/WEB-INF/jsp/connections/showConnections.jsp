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
		<meta name="author" content="Jhapak">
		<meta name="robots" content="index, follow">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<meta name="description" content="Jhapak, ${communityEraContext.currentCommunity.name}. ${communityEraContext.currentCommunity.welcomeText}" />
		<meta name="keywords" content="Jhapak, connection, community members, community, ${communityEraContext.currentCommunity.keywords}" />
		<title>${communityEraContext.currentCommunity.name}'s connections - Jhapak</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		<link rel="publisher" href="https://plus.google.com/+Jhapak4u"/>
		<meta property="article:publisher" content="https://www.facebook.com/EraOfCommunities" />
	
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		
		<%@ include file="/WEB-INF/jspf/extraJs.jspf" %>
		
		<script type="text/javascript" src="js/qtip/dynamicDropDownQtip.js"></script>
		
		<link type="text/css" href="css/jquery.jscrollpane.css" rel="stylesheet" media="all" />
 		<script type="text/javascript" src="js/jquery.jscrollpane.min.js"></script>
 		<link type="text/css" href="css/jquery.jscrollpane.lozenge.css" rel="stylesheet" media="all" />
 		
 		<link rel="stylesheet" media="all" type="text/css" href="css/commBnnr.css" />
 		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style type="text/css">
			.commBanr .nav-list ul li.selected {
			    width: 200px;
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
		</style>
		
		<script type="text/javascript">
			var selectedUserIds = [];
			
			function addMembers(communityId)
			{ 
				$(".qtip").hide();
				var dialogInstance = BootstrapDialog.show({
					title: 'Add Members',
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
	                	url:"${communityEraContext.contextUrl}/connections/addNewMember.ajx?communityId="+communityId,
	                	data: {json:selectedUserIds},
			            success:function(result){
	                	dialog.close();
	                	window.location.href='${communityEraContext.currentCommunityUrl}/connections/showConnections.do';
	    			    }
	    		    });
	                }
	            }],
	            cssClass: 'login-dialog',
	            onshown: function(dialogRef){
						var sName = "<div class='searchInt' id='searchInt'>";
						sName += "<p class='addListHead' >Add members or admins to this community by name or email address.</p>";
						sName += "<p class='secLine' >(Max 50 members in one go)</p>";
						sName += "<label class='mrolelab' for='userRole'>Member Role</label><select name='userRole' id='userRole' class='mrole'>";
						sName += "<option value='0'>Member</option><option value='1'>Community Admin</option></select>";
						sName += "<input id='intSearch' tabindex='0' value='' class='searchResult' placeholder='Type to find person...' maxlength='50' autocomplete='off' type='text' onkeydown='searchPerson("+communityId+", 0)' onmousedown='searchPerson("+communityId+", 0)' />";
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

			function inviteMembers(communityId)
			{ 
				$(".qtip").hide();
				var dialogInstance = BootstrapDialog.show({
					title: 'Invite Members',
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
	                	url:"${communityEraContext.contextUrl}/connections/inviteMembers.ajx?communityId="+communityId,
	                	data: {json:selectedUserIds},
			            success:function(result){
	                	dialog.close();
	                	window.location.href='${communityEraContext.currentCommunityUrl}/connections/showInvitations.do';
	    			    }
	    		    });
	                }
	            }],
	            cssClass: 'login-dialog',
	            onshown: function(dialogRef){
						var sName = "<div class='searchInt' id='searchInt'>";
						sName += "<p class='addListHead' >Invite members to this community by name or email address.</p>";
						sName += "<p class='secLine' >(Max 50 invitations in one go)</p>";
						sName += "<label class='mrolelab' for='intSearch'>Name or email:</label>";
						sName += "<input id='intSearch' tabindex='0' value='' class='searchResult' style='width: 450px;' placeholder='Type to find person...' maxlength='50' autocomplete='off' type='text' onkeydown='searchPerson("+communityId+", 1)' onmousedown='searchPerson("+communityId+", 1)' />";
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

			function searchPerson(communityId, option)
			{
				var searchString = document.getElementById('intSearch');
				$('.searchResult').each(function() {
			         $(this).qtip({
			            content: {
			                text: function(event, api) {
			                    $.ajax({
			                        url: '${communityEraContext.contextUrl}/search/searchRegisteredUser.ajx?searchString='+searchString.value+"&communityId="+communityId // Use href attribute as URL
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
				delete element1;
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
			// Initial call
			$(document).ready(function(){
				$.ajax({url:"${communityEraContext.contextUrl}/common/userPannel.ajx?communityId=${command.community.id}&memRequest=Y",dataType: "json",success:function(result){
					var temp = "";
					$.each(result.aData, function() {
						if(this['photoPresant'] == "1"){
							temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfo' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
							temp += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['userId']+"' width='38' height='38' style='padding: 3px;border-radius: 50%;' />";
							temp += "</a>";
						} else {
							 temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfo' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
							 temp += "<img src='img/user_icon.png' id='photoImg' width='38' height='38' style='padding: 3px;border-radius: 50%;' />";
							 temp += "</a>";
						 }
						});
					 $("#memReqList").html(temp);
					 
					// Hide message
			        $("#waitMEMMessage").hide();

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
			             $("#waitMEMMessage").show();
			         } 
			    });

				$.ajax({url:"${communityEraContext.contextUrl}/common/userPannel.ajx?communityId=${command.community.id}&memInvt=Y",dataType: "json",success:function(result){
					var temp = "";
					$.each(result.aData, function() {
						if(this['photoPresant'] == "1"){
							temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfo' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
							temp += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['userId']+"' width='38' height='38' style='padding: 3px;border-radius: 50%;' />";
							temp += "</a>";
						} else {
							 temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfo' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
							 temp += "<img src='img/user_icon.png' id='photoImg' width='38' height='38' style='padding: 3px;border-radius: 50%;' />";
							 temp += "</a>";
						 }
						});
					 $("#memInvList").html(temp);
					 
					// Hide message
			        $("#waitINVMessage").hide();

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
			             $("#waitINVMessage").show();
			         } 
			    });
				dynamicDropDownQtip();

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
	
		<script type="text/javascript">
		function addConnectionInner(userId, contactName){
			$.ajax({url:"${communityEraContext.contextUrl}/pers/addConnectionInner.ajx?id="+userId+"&userName="+contactName,success:function(result){
	    	    $("#connectionInfo-"+userId).html(result);
	    	    $(".qtip").hide();
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
			$.ajax({url:"${communityEraContext.contextUrl}/pers/updateConnectionInner.ajx?id="+contactId+"&newStatus="+newStatus+"&userId="+userId+"&userName="+contactName,success:function(result){
		    	    $("#connectionInfo-"+userId).html(result);
		    	    $(".qtip").hide();
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
			$.ajax({url:"${communityEraContext.contextUrl}/pers/toggleFollowingInner.ajx?contactId="+contactId+"&actionFor="+actionFor+"&actionType=0"+"&userId="+userId+"&contactName="+contactName,success:function(result){
	    	    $("#followId"+userId).replaceWith(result);
	    	    $(".qtip").hide();
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
			$.ajax({url:"${communityEraContext.contextUrl}/pers/toggleFollowingInner.ajx?contactId="+contactId+"&actionFor="+actionFor+"&actionType=1"+"&userId="+userId+"&contactName="+contactName,success:function(result){
	    	    $("#followId"+userId).replaceWith(result);
	    	    $(".qtip").hide();
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
		            	$.ajax({
		                	url:"${communityEraContext.currentCommunityUrl}/connections/showConnections.do?jPage="+count,
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
		   						 sName += "</div><div class='details'><div class='heading'><a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+rowId+"&backlink=ref'>"+this['firstName']+" "+this['lastName']+"</a></div>";
		   						 sName += "<div class='person'>Joined as "+this['role']+" on "+this['connectionDate']+"</div>";
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

		   		        $('.normalTip').qtip({ 
		   				    content: {
		   				        attr: 'title'
		   				    },
		   					style: {
		   				        classes: 'qtip-tipsy'
		   				    }
		   				});
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
	
	</head>

	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div class="commBanr">
			<div class="bnrImg" style="background-image:url(${communityEraContext.contextUrl}/commLogoDisplay.img?showType=m&imgType=Banner&communityId=${communityEraContext.currentCommunity.id});" >
				<div class="innerPanl">
					<div class="carousel_caption">
						<c:if test="${communityEraContext.currentCommunity.communityType == 'Private'}">
							<h4><i class="fa fa-lock" style="margin-right: 8px;"></i>${communityEraContext.currentCommunity.name}</h4>
						</c:if>
						<c:if test="${communityEraContext.currentCommunity.communityType == 'Protected'}">
							<h4><i class="fa fa-shield" style="margin-right: 8px;"></i>${communityEraContext.currentCommunity.name}</h4>
						</c:if>
						<c:if test="${communityEraContext.currentCommunity.communityType == 'Public'}">
							<h4><i class="fa fa-globe" style="margin-right: 8px;"></i>${communityEraContext.currentCommunity.name}</h4>
						</c:if>
						<p>${communityEraContext.currentCommunity.welcomeText}</p>							
					</div>
					
					<div class="menuContainer">
						<ul id="gn-menu" class="gn-menu-main">
							<li><a href="${communityEraContext.currentCommunityUrl}/connections/showConnections.do"><i class="fa fa-user-plus" style="margin-right: 8px;"></i>Members</a></li>
							<li>
								<nav id="menu">
						    		<input type="checkbox" id="toggle-nav"/>
						    		<label id="toggle-nav-label" for="toggle-nav"><i class="fa fa-bars"></i></label>
						    		<div class="box">
							    		<ul>
							    			<li><a href="${communityEraContext.currentCommunityUrl}/home.do"><i class="fa fa-home" style="margin-right: 8px;"></i>Home</a></li>
							    			<c:forEach items="${communityEraContext.enabledFeatureNamesForCurrentCommunity}" var="feature">
												<c:if test="${feature == 'Assignments'}">
													<li><a href="${communityEraContext.currentCommunityUrl}/assignment/showAssignments.do"><i class="fa fa-briefcase" style="margin-right: 8px;"></i>Assignments</a></li>
												</c:if>
												<c:if test="${feature == 'Blog'}">
													<li><a href="${communityEraContext.currentCommunityUrl}/blog/viewBlog.do"><i class="fa fa-quote-left" style="margin-right: 8px;"></i>Blogs</a></li>
												</c:if>
												<c:if test="${feature == 'Forum'}">
													<li><a href="${communityEraContext.currentCommunityUrl}/forum/showTopics.do"><i class="fa fa-comments-o" style="margin-right: 8px;"></i>Forums</a></li>
												</c:if>
												<c:if test="${feature == 'Wiki'}">
													<li><a href="${communityEraContext.currentCommunityUrl}/wiki/showWikiEntries.do"><i class="fa fa-book" style="margin-right: 8px;"></i>Wikis</a></li>
												</c:if>
												<c:if test="${feature == 'Library'}">
													<li><a href="${communityEraContext.currentCommunityUrl}/library/showLibraryItems.do"><i class="fa fa-folder-open" style="margin-right: 8px;"></i>Library</a></li>
												</c:if>
												<c:if test="${feature == 'Events'}">
													<li><a href="${communityEraContext.currentCommunityUrl}/event/showEvents.do"><i class="fa fa-calendar-check-o" style="margin-right: 8px;"></i>Events</a></li>
												</c:if>
											</c:forEach>
							    		</ul>
						    		</div>
						    	</nav>
					    	</li>
						</ul>
						<div class='actions2' id='connectionInfo'>
							<div class='btngroup' >
								<c:if test="${command.member}">
									<div class='btnout' >
										<a href="javascript:void(0);" class='dynaDropDown' id='comaction2' 
										title='community/communityOptions.ajx?currId=2003&commId=${communityEraContext.currentCommunity.id}'><i class="fa fa-th-large" style="font-size: 16px;"></i></a>
									</div>
								</c:if>
							</div>
						</div>
					</div><!-- /container -->
					
					<div class="groups">
						<div class="picture">
							<c:choose>
								<c:when test="${communityEraContext.currentCommunity.logoPresent}">		
									<img src="${communityEraContext.contextUrl}/commLogoDisplay.img?showType=m&communityId=${communityEraContext.currentCommunity.id}" width="290" height="290" /> 
								</c:when>
								<c:otherwise>
									<img src="img/community_Image.png" id="photoImg" width="290" height="290" />
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="container">
			<div class="left-panel">
				<div class="commSection">
					<div class="inputForm" style="display: inline-block; width: 100%; padding: 0px;" id="inputForm">
						<div class="intHeader">
							<div class="menus" style="padding-top:10px;">
								<ul>
									<li>
										<c:if test="${command.member}">
											<a href="javascript:void(0);" class='dynaDropDown' title='community/communityOptions.ajx?currId=2002&commId=${communityEraContext.currentCommunity.id}'>Membership Actions <span class='ddimgBlk'/></a>
										</c:if>
									</li>
								</ul>
							</div>
							<span class='firLev'>
								<form:form showFieldErrors="true">
									<form:dropdown path="sortByOption" fieldLabel="Sort By:">
										<form:optionlist options="${command.sortByOptionOptions}" />
									</form:dropdown>
									<input type="hidden" id="sortByOption" name="sortByOption" value="${command.sortByOption}" />
									<input type="submit" value="Go" class="search_btn" />
								</form:form>
							</span>
						</div>
						<input type="hidden" id="pgCount" value="${command.pageCount}" />
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
										<div class='person'>Joined as ${row.role} on ${row.connectionDate}</div>
										<c:if test="${row.connectionCount  == 0}">
											<div class='person'>${row.connectionCount} Connection</div>
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
								 		<div id='connectionInfo-${row.id}' class='connectionInfo' style="float: left; margin-right: 10px;">${row.connectionInfo}</div>
								 		<div id='settingInfo-${row.id}' class='connectionInfo' style="margin-left: 10px; float: left;"><i class="fa fa-cog" style="font-size: 22px;"></i></div>
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
				
				<div class="right-panel">
					<c:if test="${command.community.memberShipRequestCount > 0 && command.adminMember}">
						<div class="inbox">
							<div class="eyebrow">
								<span onclick="return false" >Membership Requests</span>
							</div>
							<div id="memReqList" style="padding: 4px;" ></div>
							<div id="waitMEMMessage" style="display: none;">
								<div class="cssload-square" style="width: 13px; height: 13px;">
									<div class="cssload-square-part cssload-square-green" style="width: 13px; height: 13px;"></div>
									<div class="cssload-square-part cssload-square-pink" style="width: 13px; height: 13px;"></div>
									<div class="cssload-square-blend" style="width: 13px; height: 13px;"></div>
								</div>
							</div>
							<div class="view">
								<a href="${communityEraContext.currentCommunityUrl}/connections/showJoiningRequests.do?backlink=ref">View All (${command.community.memberShipRequestCount} pending requests)</a>
							</div>
						</div>
					</c:if>
					<c:if test="${command.community.memberInvitationCount > 0 && command.member}">
						<div class="inbox">
							<div class="eyebrow">
								<span onclick="return false" >Membership Invitations</span>
							</div>
							<div id="memInvList" style="padding: 4px;" ></div>
							<div id="waitINVMessage" style="display: none;">
								<div class="cssload-square" style="width: 13px; height: 13px;">
									<div class="cssload-square-part cssload-square-green" style="width: 13px; height: 13px;"></div>
									<div class="cssload-square-part cssload-square-pink" style="width: 13px; height: 13px;"></div>
									<div class="cssload-square-blend" style="width: 13px; height: 13px;"></div>
								</div>
							</div>
							<div class="view">
								<a href="${communityEraContext.currentCommunityUrl}/connections/showInvitations.do?backlink=ref">View All (${command.community.memberInvitationCount} invitations)</a>
							</div>
						</div>
					</c:if>
				</div>
			</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		<%@ include file="/WEB-INF/jspf/extraFooter.jspf" %>
	</body>
</html>