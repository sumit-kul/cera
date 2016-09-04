<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="cera" uri="/WEB-INF/tlds/cera.tld" %> 
 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		<meta name="robots" content="index, follow">
		<meta name="author" content="${command.user.firstName} ${command.user.lastName}">
		<link rel="publisher" href="https://plus.google.com/+Jhapak4u"/>
		<meta property="article:publisher" content="https://www.facebook.com/EraOfCommunities" />
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		<title>Jhapak - ${command.user.firstName} ${command.user.lastName}</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<link rel="stylesheet" media="all" type="text/css" href="css/profile.css" />
		<link rel="stylesheet" media="all" type="text/css" href="css/interest.css" />
		
		<%@ include file="/WEB-INF/jspf/extraJs.jspf" %>
		
		<script type="text/javascript" src="js/qtip/dynamicDropDownQtip.js"></script>
		<script type="text/javascript" src="js/qtip/memberInfoTip.js"></script>
		<link rel="stylesheet" media="all" type="text/css" href="css/commBnnr.css" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style type="">
			#myProfile {
				color: #27333E;
			}
				
			#container {
				padding-top: 0px;
			}
			
			.scroll-pane
			{
				width: 100%;
				height: 410px;
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
 			.login-dialog .modal-dialog {
                width: 510px;
            }
            
            .modal-body {
			    padding: 0px;
			    min-height: 140px;
			}
			
			.modal-footer {
			    padding: 8px;
			    text-align: right;
			    border-top: 1px solid #E5E5E5;
			}
			
			.btn {
				padding: 4px;
			}
			
			.copyLnk:hover{
				outline: medium none;
				color: #fff;
			}
			
			.large {
			    font-size: 1.25em;
			    line-height: 1.2667;
			    margin: 10px 0px 15px;
			    white-space: normal;
				text-align: left;
			}
			
			.columnLeft {
				width: 48%;
				float: left;
				font-size: 12px;
				word-wrap: break-word;
			}
			
			.columnRight {
				width: 48%;
				float: right;
				font-size: 12px;
				word-wrap: break-word;
			}
			
			.newButton {
			    color: #FFF;
			    background-color: #243F52;
			    border-color: #152531;
			    display: inline-block;
				padding: 4px;
				margin-bottom: 0px;
				margin-left: 5px;
				font-size: 14px;
				font-weight: 400;
				line-height: 1.42857;
				text-align: center;
				white-space: nowrap;
				vertical-align: middle;
				cursor: pointer;
				-moz-user-select: none;
				background-image: none;
				border: 1px solid transparent;
				border-radius: 4px;
			}
			
			.newButton:hover,.newButton:focus,.newButton:active
				{
				color: #fff;
				background-color: #192c39;
				border-color: #152531
			}
			
			.newButton.disabled, .newButton[disabled], .btn.disabled, .btn[disabled] {
				opacity: 0.25;
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
			
			label {
				display: inline-block;
				overflow: hidden;
				max-width: 135px;
				margin: 0px 0px 1px 1px;
				vertical-align: middle;
				white-space: nowrap;
				text-overflow: ellipsis;
				font-size: 11px;
			}
			
			p.seccMsg {
				font-size: 1.10em;
				line-height: 1.2667;
				white-space: normal;
				text-align: left;
				color: #3F4752;
				padding: 40px;
			}
			
			.count-txt {
			    display: inline-block;
			    overflow: hidden;
			    max-width: 135px;
			    margin: 0px 0px 1px 1px;
			    vertical-align: middle;
			    white-space: nowrap;
			    text-overflow: ellipsis;
			    padding-left: 20px;
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
			
			.intrst {
			    margin: 0px 0px 0px;
				padding: 3px 7px;
				border-radius: 4px;
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
			
			.promo-interests-new {
				display: inline-block;
			}
			
		</style>
	    
		<script type="text/javascript">
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

			function calculateSum(commonInterest){
				var somval = $( "input:checkbox[name=copyInterest]:checked" ).length - commonInterest;
				$( "#selectedInterest" ).html("<span class='count-txt'>"+somval+" selected</span>");

				var btn = document.getElementById('button-add');
				if (somval == 0) {
					document.getElementById('button-add').className = "newButton";
					document.getElementById('button-add').disabled = true;
				} else {
					document.getElementById('button-add').className = "";
					document.getElementById('button-add').className = "newButton";
					document.getElementById('button-add').disabled = false;
				}
				
			}

			function copyInterstToMe(name, profileId, commonInterest){
				var dialogInstance = BootstrapDialog.show({
					title: 'Copy '+name+'\'s interests',
					message: function(dialog) {
					var $message = $('<div id="main"><p id="waitCloudMessage" style="margin-left: 235px; margin-top: 180px; min-height:200px; display: none;" class="showCloudWaitMessage" ></p></div>');
	                return $message;
	            },
	            closable: true,
	            closeByBackdrop: false,
	            onshow: function(dialog) {
	                dialog.getButton('button-add').disable();
	            },
	            buttons: [{
	                label: 'Cancel',
	                action: function(dialog){
	            	dialog.close();
                }
	            }, {
	            	id: 'button-add',
	                label: 'Add interests',
	                cssClass: 'btn-primary',
	                action: function(dialog) {
	            	var json = []; 
	            	$('input:checkbox[name=copyInterest]:checked').each(function(i, selected){ 
	            		json[i] = $(selected).attr('id');
	            		var intid = 'ID'+json[i];
	            		document.getElementById(intid).className = "";
	            		document.getElementById(intid).className = "intrst-txt-seleceted";

	            		var outid = 'outInt'+json[i];
	            		document.getElementById(outid).className = "";
	            		document.getElementById(outid).className = "intrst-seleceted";
	            	});
	            	$.ajax({
	            		type:"POST",
	                    url: "${communityEraContext.contextUrl}/pers/makeMyInterest.ajx",
	                    data: {json:json},
	                    success:function(result){
	                    	dialog.close();
	                    	var somval = $( "input:checkbox[name=copyInterest]:checked" ).length;
	                    	var common = $( "#commonInterest" ).val();
	                    	var remain = Number(somval) - Number(common);
	                    	$( "#currCommInt" ).html(somval);
	                    	
	        				var dialogInstance = BootstrapDialog.show({
	        					title: 'Copy '+name+'\'s interests',
	        					type: BootstrapDialog.TYPE_SUCCESS,
	        					message: function(dialog) {
	        					var $message = $('<div id="main"><p class="seccMsg">'+remain+' interest of '+name+' was added to your profile.</p></div>');
	        	                return $message;
	        	            },
	        	            cssClass: 'success-dialog',
	        	            closable: true,
	        	            closeByBackdrop: false,
	        	            buttons: [{
	        	                label: 'Close',
	        	                cssClass: 'btn-success',
	        	                action: function(dialog){
	        	            	dialog.close();
	                        	}
	        	            	}]
	        				});
	        			}
	                });
	                	//dialog.close();
	                }
	            }],
	            cssClass: 'login-dialog',
	            onshown: function(dialogRef){
					$.ajax({url:"${communityEraContext.contextUrl}/pers/listInterestToCopy.ajx?profileId="+profileId ,dataType: "json",success:function(result){
						var sName = "";
						var lfcol = "<div class='columnLeft' style='margin:20px 5px 10px 10px; width:46%;'>";
						var rhcol = "<div class='columnRight' style='margin:20px 10px 10px 5px; width:46%;'>";
						$.each(result.aData, function() {
							var select = "";
							var checked = "";
							var stl = "max-width: 240px;";
							if (this['selected'] > 0) {
								select = "disabled";
								checked = "checked";
								stl = "color: #365BD4; font-weight:bold; max-width: 240px;";
							} 
							if(this['oddRow']){
								lfcol += "<div style='margin-bottom:10px;'><input name='copyInterest' id='"+this['id']+"' "+checked+" "+select+" tabindex='2' type='checkbox' onclick='calculateSum("+commonInterest+");'/><label for='"+this['id']+"' style='"+stl+"'><span></span>"+this['interest']+"</label></div>";
							} else {
								rhcol += "<div style='margin-bottom:10px;'><input name='copyInterest' id='"+this['id']+"' "+checked+" "+select+" tabindex='2' type='checkbox' onclick='calculateSum("+commonInterest+");'/><label for='"+this['id']+"' style='"+stl+"'><span></span>"+this['interest']+"</label></div>";
							}
						});
						lfcol += "</div>";
						rhcol += "</div>";
						sName += "<div  style='width:100%; display: inline-block; height:400px;'><div class='scroll-pane horizontal-only'>"+lfcol+rhcol+"";
						sName += "<div id='selectedInterest'  style='width:100%; float: left;'><span class='count-txt'>0 selected</span></div></div></div>";
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
	    		       // }
	    			    },
	    			 	// What to do before starting
	    		         beforeSend: function () {
	    			    	dialogRef.getModalBody().find('#waitCloudMessage').show();
	    		         } 
	    		    });
	            }
		        });
			}

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
				

		</script>
		
		<script type="text/javascript">
			$(document).ready(function () {
				dynamicDropDownQtip();
				$("#lnkCommunities").click(function() {
				    $('html,body').animate({
				        scrollTop: $("#A12345").offset().top-50},
				        'slow');
				});

				$("#lnkConnections").click(function() {
				    $('html,body').animate({
				        scrollTop: $("#B12345").offset().top-50},
				        'slow');
				});

				$.ajax({url:"${communityEraContext.contextUrl}/common/mediaPannel.ajx?profileId=${command.user.id}",dataType: "json",success:function(result){
					var temp = "";
					$.each(result.aData, function() {
						temp += "<img src='${communityEraContext.contextUrl}/pers/photoDisplay.img?id="+this['mediaId']+"' width='89' height='89' style='padding: 3px; border-radius: 10px;' />";
						});
					if(result.aData.length > 0){
						$("#mediaList").html(temp);
					} else {
						$("#mediaList").html("<span style ='font-size: 12px; padding-left: 64px;'>No Media found</span>");
					}
			        $("#waitMediaMessage").hide();
				    },
			         beforeSend: function () {
			             $("#waitMediaMessage").show();
			         } 
			    });
			    
				$.ajax({url:"${communityEraContext.contextUrl}/common/userPannel.ajx?profileId=${command.user.id}&profVisitors=Y",dataType: "json",success:function(result){
					var temp = "";
					$.each(result.aData, function() {
						temp += "<div style='width: 100%; display: inline-block;'>";
						if(this['photoPresant'] == "1"){
							temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfo' style='float:left;' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
							temp += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['userId']+"' width='38' height='38' style='padding: 3px;border-radius: 50%;' />";
							temp += "</a>";
							temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfo rightPannelName' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
							temp += this['name'];
							temp += "</a>";
						} else {
							 temp += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfo'  style='float:left;' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
							 temp += "<img src='img/user_icon.png' id='photoImg' width='38' height='38' style='padding: 3px;border-radius: 50%;' />";
							 temp += "</a>";
							 temp += "<div style=''><a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['userId']+"&backlink=ref' class='memberInfo rightPannelName' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['userId']+"'>";
							 temp += this['name'];
							 temp += "</a></div>";
						 }
						temp += "</div>";
						});
					if(result.aData.length > 0){
						$("#visitorsList").html(temp);
					} else {
						$("#visitorsList").html("<span style ='font-size: 12px; padding-left: 64px;'>No recent visitors</span>");
					}
					 
					// Hide message
			        $("#waitVisitMessage").hide();
			        memberInfoQtip();
				    },
				 	// What to do before starting
			         beforeSend: function () {
			             $("#waitVisitMessage").show();
			         } 
			    });
			    
				$.ajax({url:"${communityEraContext.contextUrl}/pers/connectionCommunities.ajx?jPage=1&id="+$("#userId").val(),dataType: "json",success:function(result){ 
					var sName = "";
					 $.each(result.aData, function() {
						 sName += "<div class='paginatedList'><div class='leftImg'>";
				
						 var ctype = 1;
						 var rowId = this['id'];
						 if(this['type'] == "Private"){
							 ctype = 2;
						 }
						 sName += "<a href='${communityEraContext.contextUrl}/cid/"+rowId+"/'home.do'>";
						 if(this['logoPresent'] == "true"){
							 sName += "<img src='${communityEraContext.contextUrl}/commLogoDisplay.img?communityId="+rowId+"'/>";
							} else {
							 sName += "<img src='img/community_Image.png' />";
						 }
						 sName += "</a>";

						 sName += "</div><div class='details'>";
						 sName += "<div class='heading'>";
						 sName += "<a href='${communityEraContext.contextUrl}/cid/"+rowId+"/home.do'>"+this['name']+"</a></div>";
						 
						 sName += "<div class='person'>";

						 if(this['type'] == "Private" && !this['member']){
							 if(this['memberCountString'] != "0 member"){
								 sName += "<span>"+this['memberCountString']+"</span>";
							 } else {
								 sName += this['memberCountString'];
							 }
						 } else {
							 if(this['memberCountString'] != "0 member"){
								 sName += "<span class='optionList' title='${communityEraContext.contextUrl}/pers/memberList.ajx?communityId="+rowId+"'>"+this['memberCountString']+"</span>";
							 } else {
								 sName += this['memberCountString'];
							 }
						 }
						 sName += "</div>";
						 
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
						 sName += "</div></div>";
							});
					 $("#lstCommunities").html(sName);
					// Hide message
			        $("#waitMessageCommunities").hide();
				    },
				 	// What to do before starting
			         beforeSend: function () {
			             $("#waitMessageCommunities").show();
			         } 
			    });

            	$.ajax({
                	url:"${communityEraContext.contextUrl}/pers/connectionList.ajx?jPage="+1+"&id="+$("#userId").val(),
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
   					 $("#lstConnections").html(sName);
   					$("#waitMessageConnections").hide();
                	} ,
                	beforeSend: function () {
			             $("#waitMessageConnections").show();
			         }
            	});
			});
		</script>
		
	</head>
	
	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div class="commBanr">
			<div class="bnrImg" style="background-image:url(${communityEraContext.contextUrl}/pers/userPhoto.img?showType=m&imgType=Cover&id=${command.user.id});" ></div>
		</div>
		<div id="container" style="margin-top: -100px;">
			<div class="left-panel" >
				<div class="commSection">
					<input type="hidden" id="userId" value="${command.id}" />
					<input type="hidden" id="interestListSize" name="interestListSize" value="${command.interestListSize}" />
					<input type="hidden" id="commonInterest" name="commonInterest" value="${command.commonInterest}" />
					
					<div class="displayCard" >
						<div class="topusrsec">
							<div class="topurl">
								<i class="fa fa-link" aria-hidden="true" style="margin-left: 8px; color: #fff;"></i>
								<span id="editProfile1" >
									<a href="${communityEraContext.contextUrl}/profile/${command.user.profileName}" id="prfileLnk" class="prohyplnk">
									${communityEraContext.contextUrl}/profile/<strong style="color: #fff;" class="changlnk" id="profileField1">${command.user.profileName}</strong>
									</a>
								</span>
							</div>
						</div>
						<div class="usrsec">
							<div class="picture">
								<c:choose>
									<c:when test="${command.user.photoPresent}">
										<c:url value="${communityEraContext.contextUrl}/pers/userPhoto.img?showType=m" var="photoUrl">
											<c:param name="id" value="${command.user.id}" />
										</c:url>
										<img src="${photoUrl}" style="width: 200px;"/> 
									</c:when>
									<c:otherwise>
										<img src='img/user_icon.png' style="width: 200px;"/>
									</c:otherwise>
								</c:choose>
							</div>
							<div class="infosec">
								<div id="editname1" style="display: inline-block;">
									<h2 class="changlnk" id="fNameEntry">${command.user.firstName}</h2> <h2 class="changlnk" id="lNameEntry">${command.user.lastName}</h2>
								</div>
								<div class="basicinfo" id="basicInfo">
									${command.basicInfo}
								</div>
								<div class="proNav">
									<ul class="proNavList">
										<li class="proNavItm" id="lnkCommunities">
											COMMUNITIES ${command.communitiesSize}
										</li>
										<li class="proNavItm" id="lnkConnections">
											CONNECTIONS ${command.connectionCount}
										</li>
									</ul>
								</div>
								<div class="proNav" id="connectionInfo">${command.returnString}</div>
							</div>
						</div>
						<div class="usrsec">
							<div class="about" id="aboutEntry1">
								<p class="changlnk">${command.bioInfo}</p>
							</div>
							<div class="proNav" style="text-align: center; margin: 10px;" >
								<span id="spotlightentry" >
									<c:if test="${command.spotlight == 'Attend my event'}">
										<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
											<i class="fa fa-calendar" style="margin-right: 8px;"></i>${command.spotlight}
										</a>
									</c:if>
									<c:if test="${command.spotlight == 'Book a consultation'}">
										<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
											<i class="fa fa-bullseye" style="margin-right: 8px;"></i>${command.spotlight}
										</a>
									</c:if>
									<c:if test="${command.spotlight == 'Hire me'}">
										<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
											<i class="fa fa-bullseye" style="margin-right: 8px;"></i>${command.spotlight}
										</a>
									</c:if>
									<c:if test="${command.spotlight == 'Visit my website'}">
										<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
											<i class="fa fa-globe" style="margin-right: 8px;"></i>${command.spotlight}
										</a>
									</c:if>
									<c:if test="${command.spotlight == 'Date me'}">
										<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
											<i class="fa fa-heart" style="margin-right: 8px;"></i>${command.spotlight}
										</a>
									</c:if>
									<c:if test="${command.spotlight == 'Schedule an appointment'}">
										<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
											<i class="fa fa-calendar" style="margin-right: 8px;"></i>${command.spotlight}
										</a>
									</c:if>
									<c:if test="${command.spotlight == 'Read my book'}">
										<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
											<i class="fa fa-book" style="margin-right: 8px;"></i>${command.spotlight}
										</a>
									</c:if>
									<c:if test="${command.spotlight == 'Work out with me'}">
										<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
											<i class="fa fa-bolt" style="margin-right: 8px;"></i>${command.spotlight}
										</a>
									</c:if>
									<c:if test="${command.spotlight == 'Sign up for my newsletter'}">
										<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
											<i class="fa fa-newspaper-o" style="margin-right: 8px;"></i>${command.spotlight}
										</a>
									</c:if>
									<c:if test="${command.spotlight == 'Read my blog'}">
										<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
											<i class="fa fa-quote-left" style="margin-right: 8px;"></i>${command.spotlight}
										</a>
									</c:if>
									<c:if test="${command.spotlight == 'Visit my galary'}">
										<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
											<i class="fa fa-photo" style="margin-right: 8px;"></i>${command.spotlight}
										</a>
									</c:if>
									<c:if test="${command.spotlight == 'Visit my company website'}">
										<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
											<i class="fa fa-building" style="margin-right: 8px;"></i>${command.spotlight}
										</a>
									</c:if>
									<c:if test="${command.spotlight == 'Watch my videos'}">
										<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
											<i class="fa fa-video-camera" style="margin-right: 8px;"></i>${command.spotlight}
										</a>
									</c:if>
									<c:if test="${command.spotlight == 'Download my app'}">
										<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
											<i class="fa fa-cloud-download" style="margin-right: 8px;"></i>${command.spotlight}
										</a>
									</c:if>
									<c:if test="${command.spotlight == 'Visit my store'}">
										<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
											<i class="fa fa-shopping-cart" style="margin-right: 8px;"></i>${command.spotlight}
										</a>
									</c:if>
									<c:if test="${command.spotlight == 'Support my campaign'}">
										<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
											<i class="fa fa-thumbs-up" style="margin-right: 8px;"></i>${command.spotlight}
										</a>
									</c:if>
									<c:if test="${command.spotlight == 'Support my charity'}">
										<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
											<i class="fa fa-leaf" style="margin-right: 8px;"></i>${command.spotlight}
										</a>
									</c:if>
									<c:if test="${command.spotlight == 'Take my class'}">
										<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
											<i class="fa fa-university" style="margin-right: 8px;"></i>${command.spotlight}
										</a>
									</c:if>
									<c:if test="${command.spotlight == 'Try my food'}">
										<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
											<i class="fa fa-birthday-cake" style="margin-right: 8px;"></i>${command.spotlight}
										</a>
									</c:if>
									<c:if test="${command.spotlight == 'Visit my restaurant'}">
										<a class='buttonSecondary changlnk2' href='${command.spotlightUrl}' target='_blank'>
											<i class="fa fa-cutlery" style="margin-right: 8px;"></i>${command.spotlight}
										</a>
									</c:if>
								</span>
							</div>
						</div>
						<div class="usrsec">
							<c:if test="${command.sociallinkcount > 0}">
								<ul class="sociLnks" id="sociLnksItems">
									<c:forEach items="${command.socialLinks}" var="row" >
										<li class="socialItem" >
											<a href='${row.link}' target='_blank' class="addSL"><i class="fa fa-${fn:toLowerCase(row.name)}" ></i></a>
										</li>
									</c:forEach>
								</ul>
							</c:if>
						</div>
					</div>
					
					<div class="displayCard" >
						<h2 class="headerSection" >Personal details</h2>
						<div class="usrsec">
							<ul class="itemNav">
								<li class="navItem">
									<span class="itmHdr">Birthday</span>
									<span id="dobItem1">
										<span class="itmEntry changlnk" id="mdobItem">
											<c:choose>
												<c:when test="${command.dateOfBirthAccess}">
													${command.dob}
												</c:when>
												<c:otherwise>
													<i class="fa fa-lock" style="margin-right: 6px;"></i>Not allowed to show
												</c:otherwise>
											</c:choose>
										</span>
									</span>
								</li>
								<li class="navItem">
									<span class="itmHdr">Gender</span>
									<span id="genderItem1">
										<span class="itmEntry changlnk" id="genderItem">
											<c:choose>
												<c:when test="${command.genderAccess}">
													${command.genderTodisplay}
												</c:when>
												<c:otherwise>
													<i class="fa fa-lock" style="margin-right: 6px;"></i>Not allowed to show
												</c:otherwise>
											</c:choose>
										</span>
									</span>
								</li>
								<li class="navItem">
									<span class="itmHdr">Mobile Phone</span>
									<span id="phoneItem1">
										<span class="itmEntry changlnk" id="mobItem">
											<c:choose>
												<c:when test="${command.mobileNumberAccess}">
													${command.mobPhoneCode} - ${command.mobileNumber}
												</c:when>
												<c:otherwise>
													<i class="fa fa-lock" style="margin-right: 6px;"></i>Not allowed to show
												</c:otherwise>
											</c:choose>
										</span>
									</span>
								</li>
								<li class="navItem">
									<span class="itmHdr">Address</span>
									<span id="locationtem1">
										<span class="itmEntry changlnk">
											<c:choose>
												<c:when test="${command.addressAccess}">
													${command.location}
												</c:when>
												<c:otherwise>
													<i class="fa fa-lock" style="margin-right: 6px;"></i>Not allowed to show
												</c:otherwise>
											</c:choose>
										</span>
									</span>
								</li>
								<li class="navItem">
									<span class="itmHdr">Relationship</span>
									<span id="relationshipItem1">
										<span class="itmEntry changlnk" id="relationshipItem">
											<c:choose>
												<c:when test="${command.relationshipAccess}">
													${command.relationshipDisplay}
												</c:when>
												<c:otherwise>
													<i class="fa fa-lock" style="margin-right: 6px;"></i>Not allowed to show
												</c:otherwise>
											</c:choose>
										</span>
									</span>
								</li>
								<%-- <li class="navItem">
									<span class="itmHdr">High school</span><span class="itmEntry"></span>
								</li>
								<li class="navItem">
									<span class="itmHdr">College</span><span class="itmEntry"></span>
								</li> --%>
								<li class="navItem">
									<span class="itmHdr">Work</span>
									<span id="OccupationItem1">
										<span class="itmEntry changlnk" id="companyName">${command.company}</span>
									</span>
								</li>
							</ul>
						</div>
						<h2 class="headerSection" >Interests (${command.interestListSize})
							<c:if test="${command.user.id != communityEraContext.currentUser.id}">
								<c:if test="${communityEraContext.userAuthenticated}">
									<span style="font-size:11px; margin-top:0px; padding:10px; float: right;">
										<c:if test="${command.commonInterest == 1}">
											<i id="currCommInt">${command.commonInterest}</i> INTEREST IN COMMON
										</c:if>
										<c:if test="${command.commonInterest > 1}">
											<i id="currCommInt">${command.commonInterest}</i> INTERESTS IN COMMON
										</c:if>
									</span>
								</c:if>
							</c:if>
						</h2>
						<div class="usrsec" style="margin: 0px 10px 10px 10px;" >
							<c:choose>
								<c:when test="${command.interestListSize == 0}">
									<c:if test="${command.user.id != communityEraContext.currentUser.id}">
										<div>
											<span style="font-size:13px; font-weight: bold; margin-top:0px; padding:10px; float: left; color: #AAA6A6;">Unfortunately, ${command.fullName} has not added any interests yet.</span>
										</div>
									</c:if>
								</c:when>
								<c:otherwise>
									<c:forEach items="${command.interestList}" var="row" >
										<div class='promo-interests-new' style='margin: 10px 0px 6px 6px;' id='myOldInterests${row.id}'>
											<c:choose>
												<c:when test="${row.selected > 0}">
													<c:if test="${command.user.id != communityEraContext.currentUser.id}">
														<div class='intrst-seleceted' id="outInt${row.id}"><span id='ID${row.id}' class='intrst-txt-seleceted toBeCopied'>${row.interest}</span><span class='blocker'></span></div>
													</c:if>
												</c:when>
												<c:otherwise>
													<div class='intrst' id="outInt${row.id}"><span id='ID${row.id}' class='intrst-txt toBeCopied'>${row.interest}</span><span class='blocker'></span></div>
												</c:otherwise>
											</c:choose>
										</div>
									</c:forEach>
									<c:if test="${communityEraContext.userAuthenticated}">
										<c:if test="${command.user.id != communityEraContext.currentUser.id}">
											 <br />
											 <p style="margin: 0px 0px 10px 6px;">See some shared interests? <span class="copyLnk" onclick="copyInterstToMe(&#39;${command.firstName}&#39;, &#39;${command.user.id}&#39;, &#39;${command.commonInterest}&#39;);">Add them to your profile!</span></p>
										</c:if>
									</c:if>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					<div class="displayCard" id="A12345">
						<h2 class="headerSection" >Communities<a href="${communityEraContext.contextUrl}/pers/connectionCommunities.do?backlink=ref&id=${command.id}">See All</a></h2>
						<div class="usrsec" id="lstCommunities">
							<div id="waitMessageCommunities" style="display: none;">
								<div class="cssload-square" >
									<div class="cssload-square-part cssload-square-green" ></div>
									<div class="cssload-square-part cssload-square-pink" ></div>
									<div class="cssload-square-blend" ></div>
								</div>
							</div>
						</div>
					</div>
					
					<div class="displayCard" id="B12345">
						<h2 class="headerSection" >Connections<a href="${communityEraContext.contextUrl}/pers/connectionList.do?backlink=ref&id=${command.id}">See All</a></h2>
						<div class="usrsec" id="lstConnections">
							<div id="waitMessageConnections" style="display: none;">
								<div class="cssload-square" >
									<div class="cssload-square-part cssload-square-green" ></div>
									<div class="cssload-square-part cssload-square-pink" ></div>
									<div class="cssload-square-blend" ></div>
								</div>
							</div>
						</div>
					</div>
				 </div> <!-- /myUpload --> 
				</div><!-- /#leftPannel -->
				
				<div class="right-panel" style="margin-top: 0px;">
					<c:if test="${communityEraContext.userAuthenticated}">
						<div class="inbox" style="display: inline-block; width: 296px; float: right;">
							<div class="eyebrow">
								<span onclick="return false" >People Also Viewed</span>
							</div>
							<div id="visitorsList" style="padding: 4px;" ></div>
							<div id="waitVisitMessage" style="display: none;">
								<div class="cssload-square" style="width: 13px; height: 13px;">
									<div class="cssload-square-part cssload-square-green" style="width: 13px; height: 13px;"></div>
									<div class="cssload-square-part cssload-square-pink" style="width: 13px; height: 13px;"></div>
									<div class="cssload-square-blend" style="width: 13px; height: 13px;"></div>
								</div>
							</div>
						</div>
					</c:if>
					
					<div class="inbox" style="display: inline-block; width: 296px; float: right;">
						<div class="eyebrow">
							<span onclick="return false" >Media Gallery</span>
						</div>
						<div id="mediaList" style="padding: 4px;" ></div>
						<div id="waitMediaMessage" style="display: none;">
							<div class="cssload-square" style="width: 13px; height: 13px;">
								<div class="cssload-square-part cssload-square-green" style="width: 13px; height: 13px;"></div>
								<div class="cssload-square-part cssload-square-pink" style="width: 13px; height: 13px;"></div>
								<div class="cssload-square-blend" style="width: 13px; height: 13px;"></div>
							</div>
						</div>
						<div class="view">
							<a href="${communityEraContext.contextUrl}/pers/mediaGallery.do?backlink=ref&id=${command.user.id}">Visit Gallery</a>
						</div>
					</div>
				</div>
			</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>