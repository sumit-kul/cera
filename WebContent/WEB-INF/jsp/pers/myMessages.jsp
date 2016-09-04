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
		<title>Jhapak - My Messages</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		
		<%@ include file="/WEB-INF/jspf/extraJs.jspf" %>

		<script type="text/javascript" src="js/accordion.js"></script>
		
		<link type="text/css" href="css/jquery.jscrollpane.css" rel="stylesheet" media="all" />
 		<script type="text/javascript" src="js/jquery.jscrollpane.min.js"></script>
 		<link type="text/css" href="css/jquery.jscrollpane.lozenge.css" rel="stylesheet" media="all" />
		
		<link rel="stylesheet" media="all" type="text/css" href="css/profile.css" />
		<link type="text/css" href="css/message.css" rel="stylesheet" media="all" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style>
			span.profileMe a#myMessage {
				color: #27333E;
			}
			
			.nMessage-dialog .modal-dialog {
				width: 720px;
				margin: 30px auto
			}
			
			.paginator_p_wrap, .paginator_p, .paginator_p.selected {
				height: 20px;
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
			
			.scroll-pane
			{
				width: 100%;
				height: 310px;
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
			
			#cke_editor {
				width: 478px;
			}
		</style>
		
		<script type="text/javascript">
			var selectedUserIds = [];

			function callMessageLink(actionType) {
				$(".qtip").hide();
				var json = []; 
            	$('input:checkbox[name=markChkMsg]:checked').each(function(i, selected){ 
            		json[i] = $(selected).attr('id');
            	});
            	var messCount = json.length;
				if (messCount > 0 || actionType == 3 || actionType == 4 || actionType == 6 || actionType == 8 || actionType == 10) {
					if (actionType == 1 || actionType == 2 || actionType == 3 || actionType == 4) { // Read / Unread action
						$.ajax({url:"${communityEraContext.contextUrl}/pers/messageUpdate.ajx?actionType="+actionType,
							data: {json:json},
							success:function(result){
								$.each(result.aData, function() {
									updateMessageResponse(this['retMessId'], this);
								});
						}});
					} else if (actionType == 7) { // Restore selected messages
						deleteMessage(json, 7);
					} else if (actionType == 8) { // Restore all messages
						deleteMessage(null, 8);
					} else {
						if (actionType == 5 || actionType == 6 || actionType == 9 || actionType == 10){ // Delete messages
							var mess = "";
							if (actionType == 5) {
								if (messCount == 1) {
									mess = messCount + " message will be deleted. <br />It can be restored from Archive with in one month.";
								} else {
									mess = messCount + " messages will be deleted. <br />These messages can be restored from Archive with in one month.";
								}
							}

							if (actionType == 6){
								mess = "All messages will be deleted. All these messages can be restored from Archive with in one month.";
							}

							if (actionType == 9) {
								if (messCount == 1) {
									mess = messCount + " message will be deleted permanently.";
								} else {
									mess = messCount + " messages will be deleted permanently.";
								}
								mess = messCount + " messages will be deleted permanently.";
							}

							if (actionType == 10){
								mess = "All messages will be deleted permanently.";
							}
							BootstrapDialog.show({
				                type: BootstrapDialog.TYPE_WARNING,
				                title: 'Delete Message',
				                message: mess,
				                closable: true,
				                closeByBackdrop: false,
				                closeByKeyboard: false,
				                buttons: [{
				                	label: 'Close',
				                    action: function(dialogRef){
				                        dialogRef.close();
				                    }
				                },
				                {
				                	id: 'button-delete',
				                	label: 'Delete',
				                	autospin: false,
				                    action: function(dialogRef){
					                	dialogRef.getButton('button-delete').disable();
					                    dialogRef.getButton('button-delete').spin();

					                    if (actionType == 5){ // Delete selected messages
					                    	deleteMessage(json, 5);
					                    } else if (actionType == 6) {  // Delete all messages
					                    	deleteMessage(null, 6);
					                    } else if (actionType == 9){ // Delete selected messages permanently
					                    	deleteMessage(json, 9);
					                    } else if (actionType == 10) {  // Delete all messages permanently
					                    	deleteMessage(null, 10);
					                    }
				       					dialogRef.close();
				                    }
				                }
				                ]
				            });
						}
					}
				} else {
					BootstrapDialog.show({
		                type: BootstrapDialog.TYPE_DANGER,
		                title: 'Error!',
		                message: 'Oops! You haven&#39;t selected a message.',
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
				}
			}

			function replyMessage(to, contactName, photoPresent){
				$(".qtip").hide();
				var editor;
				selectedUserIds = [];
				selectedUserIds.push(to);
				var dialogInstance = BootstrapDialog.show({
					title: 'Send Message',
					message: function(dialog) {
					var $message = $('<div id="main" style="display: inline-block;width: 700px;"></div>');
		            return $message;
		        },
		        closable: false,
		        closeByBackdrop: false,
		        onshow: function(dialog) {
		        	var form = "<div style='display:inline-block;'>";
		        	form += "<div id='sendMessageForm' style='float: left; width: 700px;'>";
		            form += "<div style='width: 700px; float: left;'>";
		            form += "<div class='type'><label for='toNames'>To:</label><br /><div id='toNames' class='divText2'>";
		            form += "<span id='cont-"+to+"' class='innerName normalTip' >"+contactName;
					if(photoPresent){
						form += "<img src='pers/userPhoto.img?id="+to+"' alt='"+contactName+"' width='21' height='21'/></span>";
					}else {
						form += "<img src='img/user_icon.png' alt='"+contactName+"' width='21' height='21'/></span>";
					}
		            form += "</div>";
		            form += "<label for='subject'>Subject:</label><br /><input id='subject' maxlength='100' autocomplete='off' type='text' class='text2' style='width: 97%; margin:0px;'/>";
		            form += "<br /><br /><label for='editor'>Message:</label><br /><textarea name='body' id='body' style='display:none;'></textarea><div id='editor' style='width: 680px;'></div></div></div></div>";
		            form += "<p id='waitCloudMessage' style='margin-left: 235px; margin-top: 180px; min-height:200px; display: none;' class='showCloudWaitMessage'></p></div>";
		            //form += "</div>";
		            dialog.getModalBody().find('#main').html(form);
		        },
		        buttons: [{
		        	id: 'button-cancel',
		            label: 'Cancel',
		            action: function(dialog){
		        	dialog.close();
		        }
		        }, {
		        	id: 'button-add',
		            label: 'Send',
		            cssClass: 'btn-primary',
		            action: function(dialog) {
		        	dialog.getButton('button-add').disable();
		        	dialog.getButton('button-cancel').disable();
		        	dialog.getButton('button-add').spin();
		        	var ssubject = document.getElementById("subject").value;
		        	var sbody = editor.getData();
		        	$.ajax({
		            	type:"POST",
		            	url:"${communityEraContext.contextUrl}/pers/sendMessage.ajx",
		            	data: {json:selectedUserIds, subject:ssubject, body:sbody},
			            success:function(result){
		            	window.location.href="${communityEraContext.contextUrl}/pers/myMessages.do?msgType="+$("#msgType").val()+"&order="+$("#order").val();
		        		dialog.close();
		        		//var dialogInstance = BootstrapDialog.show({
						//	title: 'Message sent successfully',
						//	type: BootstrapDialog.TYPE_SUCCESS,
						//	message: function(dialog) {
						//	var $message = $('<div id="main"><p class="seccMsg">Message has been successfully sent to '+contactName+'.</p></div>');
			            //    return $message;
			           // },
			           // cssClass: 'successMsg-dialog',
			           // closable: true,
			            //closeByBackdrop: false,
			            //buttons: [{
			            //    label: 'Close',
			             //   cssClass: 'btn-success',
			            //    action: function(dialog){
			            //	dialog.close();
		                //	}
			            //	}]
						//});
					    }
				    });
		            }
		        }],
		        cssClass: 'sendMessage-dialog',
		        onshown: function(dialogRef){
		        	var config = {};
		        	config.toolbarGroups = [
		                            		{ name: 'clipboard',   groups: [ 'clipboard', 'undo' ] },
		                            		{ name: 'editing',     groups: [ 'spellchecker' ] },
		                            		{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
		                            		{ name: 'paragraph',   groups: [ 'list', 'blocks' ] },
		                            		{ name: 'styles' },
		                            		{ name: 'colors' }
		                            	];
					editor = CKEDITOR.appendTo( 'editor', config, '' );
		        }
		        });
			}

			function inArray(arr, obj) {
			    for(var i=0; i<arr.length; i++) {
			        if (arr[i] == obj) return true;
			    }
			    return false;
			}

			function deleteAllMessages(){
				var deleteLink = "${communityEraContext.contextUrl}/pers/myMessages.ajx?msgType="+$("#msgType").val()+"&order="+$("#order").val()+"&actionType=4";
				$.ajax({url: deleteLink,type: "POST",dataType: "json",success:function(response){
						$("#page").html("");
	       				$('#totalMsgCount').html('My Messages (0)');
						$('#max_backward').remove();
						$('#over_backward').remove();
						$('#over_forward').remove();
						$('#max_forward').remove();
						$('#newPagination').html("<span class='euInfo'>No result found</span>");
					}
	       		});
			}
			
			function deleteMessage(json, actionType){
                var totalMessageCount = "0"; var pageNumber = "0"; var pageCount = "0"; var num = '${command.pageNumber}';
                var msgType;
                var order;
                var dbMessageCount = -1;
                var deleteLink = "${communityEraContext.contextUrl}/pers/myMessages.ajx?msgType="+$("#msgType").val()+"&order="+$("#order").val()+"&actionType="+actionType+"&jPage="+num;
					$.ajax({url: deleteLink,data: {json:json},type: "POST",dataType: "json",success:function(response){ // first call will delete the messages
						totalMessageCount = response['totalMessageCount'];
						pageCount = parseInt((totalMessageCount%10) != 0 ? (totalMessageCount/10)+1 :totalMessageCount/10);
						pageNumber = response['pageNumber'];
						msgType = response['msgType'];
						order = response['order'];
						num = pageNumber;
						dbMessageCount = response['dbMessageCount'];

						if (dbMessageCount > -1) {
				    	    if (dbMessageCount > 0) {
					    	    var messKey = "message";
				    	    	if (dbMessageCount > 1) {
				    	    		messKey ="messages";
				    	    	}
				    	    	var chDB = "<a title='There are "+dbMessageCount+" new "+messKey+" you haven&#39;t checked yet' id='dbRecMesId' class='normalTip' ";
					    	    chDB += "href='pers/myMessages.do?msgType=Unread'>You have received <span class='redMark'>"+dbMessageCount+"</span> new messages</a>";
				    	    	$( "#dbRecMesId" ).replaceWith( chDB );
							} else {
				    	    	$( "#dbRecMesId" ).replaceWith( "" );
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

						if(pageCount > 0){
							$("#pagination").jPaginator({ 
							nbPages:pageCount,
							selectedPage:pageNumber,
							marginPx:5,
							length:6, 
							overBtnLeft:'#over_backward', 
							overBtnRight:'#over_forward', 
							maxBtnLeft:'#max_backward', 
							maxBtnRight:'#max_forward', 
							onPageClicked: function(a,pageNumber){ // this second ajax will update the pagination
								var URL = "${communityEraContext.contextUrl}/pers/myMessages.ajx?msgType="+$("#msgType").val()+"&order="+$("#order").val()+"&jPage="+num;
								$.ajax({url:URL,dataType: "json",success:function(result){
									$("#page").html("");
									var sName = "<ul>";
									var arrayOfMsgIds = "";
									if (result.aData.length == 0) {
										$("#page").html("");
										$('#newPagination').html("<span class='euInfo'>No result found</span>");
									}
									 $.each(result.aData, function() {
										 var sClass = "paginatedList";
	   								 if(this['sysType']=='ReceivedMessage' && this['alreadyRead']){
	   									 sClass = "messReadList";
	   								 }
	   								 sName += "<div class='"+sClass+"' id='commresultRow"+this['id']+"' ><div class='leftImg'>";
										 
	   								if(this['photoPresent']){
	          							 sName += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['fromUserId']+"&backlink=ref' ><img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['fromUserId']+"' /></a></div>"; }
	          						 else {
	          							 sName += "<img src='img/user_icon.png' /></div>";
	          						 }
	   							 	var varBody = this['body'];
	   							 	var trimBody = varBody.substring(0,150);
	   							    var delete_URL = '${communityEraContext.contextUrl}/pers/myMessages.ajx?msgType=${command.msgType}&order=${command.order}&msgAction=delete&jPage='+num+'&selectedIds=';
	       							 sName += "<div class='details'><div class='heading' id='subject"+this['id']+"'";
	    							 if(this['sysType']=='SentMessage'){
	    									sName += " style='color: #09F; width:640px;'";
	    								} 
	    							 sName += ">"+this['subject']+"";
	    							 sName += "<div class='entry'>";
	    							 if(this['sysType']=='ReceivedMessage' && this['deleteFlag'] == 0){
	    								 if(this['alreadyRead']){
	    									 sName += "<span id='isMessageRead"+this['id']+"' style='width:50px;'><a class='commIndexRight' style='padding: 0px 30px 0px;font-weight: bold;' href='javascript:void(0);' onClick='return updateMessage("+this['id']+")'>";
	    									 sName += "Unread</span></a>";
	    								 }else{
	    									 sName += "<span id='isMessageRead"+this['id']+"' style='width:50px;'><a class='commIndexRight' style='padding: 0px 30px 0px;font-weight: bold;' href='javascript:void(0);' onClick='return updateMessage("+this['id']+")'>";
	    									 sName += "Read</span></a>";
	    								 }
	    							 }
	    							 
	    							 sName += "<div class='chkMSG'><input name='markChkMsg' id='"+this['id']+"' tabindex='2' type='checkbox' onclick='toggleBtnStyle();'/><label for='"+this['id']+"' ><span></span></label></div>";
	    							 sName += "</div>";
	    							 sName += "</div><div class='person'>";
	    							 if(this['sysType']=='ReceivedMessage'){
	    								 sName += " Received from ";
	    								 sName += "<a style='display: inline;' href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['fromUserId']+"&backlink=ref' ";
	    								 sName += " class='memberInfo' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['fromUserId']+"'>"+this['displayName']+"</a>";	
	    							 }
	    							 else{
	    								 sName += " Sent to ";
	    								 sName += "<a style='display: inline;' href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['toId']+"&backlink=ref' "
	    								 sName += " class='memberInfo' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['toId']+"'>"+this['toName']+"</a>";
	    								if (this['toCount'] > 0) {
	    									sName += " and "+this['toCount']+" others" ;
	    								} else if (this['toCount'] > 1) { 
	    									sName += " one other";
	    								}
	    							 }
	    							 
	    							 sName += " on "+this['createdOn'];
	    							 sName += "<div class='entry' style='float:right;top:30px;'><a style='padding: 0px 6px 0px;' href='javascript:void(0);' onclick='sendMessage(&#39;"+this['id']+"&#39;, &#39;Share&#39;, &#39; &#39;, &#39; &#39;, &#39; &#39;, &#39;"+this['subject']+"&#39;);' >Share</a>";
	    							 if(this['sysType']=='ReceivedMessage'){
	    							 	sName += "<a style='padding: 0px 6px 0px;' href='javascript:void(0);' onclick='replyMessage(&#39;"+this['fromUserId']+"&#39;, &#39;"+this['displayName']+"&#39;, &#39;"+this['photoPresent']+"&#39;);' >Reply</a>";
	    							 }
	    							 
	    							 sName += "</div></div>";
	    							 
	    							 sName += "<span class='commDesc' id='commDesc"+this['id']+"'><p>"+trimBody+"</p></span>";
	    							 sName += "<a style='padding: 0px 6px 0px;' href='javascript:void(0);' onClick='return getFullMessage("+this['id']+")' id='commIndexRight"+this['id']+"'><img id='img"+this['id']+"' src='img/icon-link-expand-dark.gif' width='15' height='15' class='levelImg2' /></a>";
	    							 sName += "</div></div>";
	    							 sName += "<input type='hidden' id='bodyValue"+this['id']+"' value='"+this['body']+"'>";
	    							 sName += "<input type='hidden' id='messageRead"+this['id']+"' value='"+this['read']+"'>";
	    							 if(arrayOfMsgIds == ""){
	    								 arrayOfMsgIds += this['id'];
	    							 }
	    							 else{
	    								 arrayOfMsgIds += ","+this['id'];
	    							 }
										});
										 sName += "<input type='hidden' id='arrayOfMsgIds' value='"+arrayOfMsgIds+"'>";
									 $("#page").html(sName+"</ul>");
								    }});
							   } 
		   				}); 
						}
       				$('#totalMsgCount').html('My Messages ('+totalMessageCount+')');
       				if (totalMessageCount > 0) {
       					$('#tRecordsCount').html('Total Records - '+totalMessageCount);
					}
					
					if (pageCount < 0) {
						$('#max_backward').remove();
						$('#over_backward').remove();
						$('#over_forward').remove();
						$('#max_forward').remove();
					} else if (pageCount == 0) {
						$('#newPagination').html("<span class='euInfo'>No result found</span>");
					}
       			}
       		});
			}

			function addContact(contactName, contactId, photoPresent) 
			{
				if (!inArray(selectedUserIds, contactId)) {
					$('#errorMsg').html('');
					var toElm = $('#toNames').html();
					var ss = "<span id='cont-"+contactId+"' class='innerName normalTip' title='Remove "+contactName+"' onclick=\"removeContact(&#39;"+contactName+"&#39;, &#39;"+contactId+"&#39;)\">"+contactName;
					if(photoPresent){
						ss += "<img src='pers/userPhoto.img?id="+contactId+"' alt='"+contactName+"' width='21' height='21'/>";
					}else {
						ss += "<img src='img/user_icon.png' alt='"+contactName+"' width='21' height='21'/>";
					}
					ss += "</span>";
					if (toElm != "") {
						$('#toNames').html(toElm + ss);
						selectedUserIds.push(contactId);
					} else {
						$('#toNames').html(ss);
						selectedUserIds.push(contactId);
					}
				}
			}

			function removeContact(contactName, contactId) 
			{
				var index = selectedUserIds.indexOf(contactId);
				if(index > -1){
					delete selectedUserIds[ index ];
					$( "#cont-"+contactId ).remove("");
				}
			}

			function sendMessage(messageId, key, to, name, photoPresent, subject){
				var editor;
				selectedUserIds = [];
				var dialogInstance = BootstrapDialog.show({
					title: key+' Message',
					message: function(dialog) {
					var $message = $('<div id="main" style="display: inline-block;width: 700px;"></div>');
	                return $message;
	            },
	            closable: false,
	            closeByBackdrop: false,
	            onshow: function(dialog) {
	            	var form = "<div style='display:inline-block;'>";
	            	form += "<div id='sendMessageForm' style='float: left; width: 490px;'>";
		            form += "<div style='width: 490px; float: left;'><p class='messHead'>Click from &#39;My Connections&#39; to add them to the below &#39;To&#39; field.</p>";
		            form += "<div class='type'><label for='toNames'>To:</label><br /><div id='toNames' class='divText2'></div><div id='errorMsg' class='errorText' style='width:480px;margin-bottom: 6px;'></div>";
		            form += "<label for='subject'>Subject:</label><br /><input id='subject' maxlength='100' autocomplete='off' type='text' class='text2' style='width: 97%; margin:0px;' />";
		            form += "<br /><br /><label for='editor'>Message:</label><br /><textarea name='body' id='editor' ></textarea></div></div></div>";
		            form += "<div id='myContactPicker' ><div id='header'>My Connections</div><div id='pickerSection'></div>";
		            form += "<p id='waitCloudMessage' style='margin-left: 235px; margin-top: 180px; min-height:200px; display: none;' class='showCloudWaitMessage'></p></div>";
		            form += "</div>";
		            dialog.getModalBody().find('#main').html(form);
	            },
	            buttons: [{
	            	id: 'button-cancel',
	                label: 'Cancel',
	                action: function(dialog){
		            	selectedUserIds = [];
		            	dialog.close();
               		}
	            }, {
	            	id: 'button-add',
	                label: key,
	                cssClass: 'btn-primary',
	                action: function(dialog) {
	                if (selectedUserIds.length == 0) {
						$('#errorMsg').html("<p>Oops! You have&#39;t added any recepient in &#39;To&#39; field.</p>")
					} else {
			            	dialog.getButton('button-add').disable();
			            	dialog.getButton('button-cancel').disable();
			            	dialog.getButton('button-add').spin();
			            	var ssubject = document.getElementById("subject").value;
			            	var sbody = editor.getData();
			            	$.ajax({
			                	type:"POST",
			                	url:"${communityEraContext.contextUrl}/pers/sendMessage.ajx",
			                	data: {json:selectedUserIds, subject:ssubject, body:sbody},
					            success:function(result){
				                	window.location.href="${communityEraContext.contextUrl}/pers/myMessages.do?msgType="+$("#msgType").val()+"&order="+$("#order").val();
				                	selectedUserIds = [];
				            		dialog.close();
			    			    }
			    		    });
	            		}
	                }
	            }],
	            cssClass: 'nMessage-dialog',
	            onshown: function(dialogRef){
		            if ('Share' == key) {
		            	$("#subject").val(subject);
		            	var nbody = document.getElementById('bodyValue'+messageId).value;
						var config = {};
						config.toolbarGroups = [
			                            		{ name: 'clipboard',   groups: [ 'clipboard', 'undo' ] },
			                            		{ name: 'editing',     groups: [ 'spellchecker' ] },
			                            		{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
			                            		{ name: 'paragraph',   groups: [ 'list', 'blocks' ] },
			                            		{ name: 'styles' },
			                            		{ name: 'colors' }
			                            	];
						editor = CKEDITOR.replace( 'editor', config, '' );
						editor.setData(nbody);
		            	//var bdy = "<div  style='width:100%; display: inline-block; height:300px;'><div class='scroll-pane horizontal-only'><span class='commDesc' id='commDesc"+messageId+"'>"+nbody+"</span></div></div>";
						//$("#editor").html(bdy);
						dialogRef.getModalBody().find('.scroll-pane').jScrollPane(
    		        		{
    		        			verticalDragMinHeight: 20,
    		        			verticalDragMaxHeight: 200,
    		        			autoReinitialise: true
    		        			}
    	    		        );
					} else {
		            	var config = {};
		            	config.toolbarGroups = [
			                            		{ name: 'clipboard',   groups: [ 'clipboard', 'undo' ] },
			                            		{ name: 'editing',     groups: [ 'spellchecker' ] },
			                            		{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
			                            		{ name: 'paragraph',   groups: [ 'list', 'blocks' ] },
			                            		{ name: 'styles' },
			                            		{ name: 'colors' }
			                            	];
						editor = CKEDITOR.replace( 'editor', config, '' );
					}
					$.ajax({url:"${communityEraContext.contextUrl}/pers/contactPicker.ajx" ,dataType: "json",success:function(result){
						var sList = "<ul>";
						$.each(result.aData, function() {
							sList += "<li class='srchRslt' onclick=\"addContact(&#39;"+this['contactName']+"&#39;, &#39;"+this['contactId']+"&#39;, &#39;"+this['photoPresent']+"&#39;)\" title='Send message to "+this['contactName']+"'>";
							if(this['photoPresent']){
								sList += "<img src='pers/userPhoto.img?id="+this['contactId']+"' alt='"+this['contactName']+"' width='35' height='35'/>&nbsp;&nbsp;";
							}else {
								sList += "<img src='img/user_icon.png' alt='"+this['contactName']+"' width='35' height='35'/>&nbsp;&nbsp;";
							}
							sList += this['contactName']+"</li>";
						 });
						sList +="</ul>";
						dialogRef.getModalBody().find('#pickerSection').html(sList);
					}
					});
	            }
		        });
			}
		</script>
		
		<script type="text/javascript">
			// Initial call
			var varBody1;
			$(document).ready(function(){
				if(${command.pageCount} > 0){
					$("#pagination").jPaginator({  
						nbPages:${command.pageCount},
						selectedPage:${command.pageNumber},
						marginPx:5,
						length:8, 
						overBtnLeft:'#over_backward', 
						overBtnRight:'#over_forward', 
						maxBtnLeft:'#max_backward', 
						maxBtnRight:'#max_forward', 
						onPageClicked: function(a,num) {
							$.ajax({url:"${communityEraContext.contextUrl}/pers/myMessages.ajx?msgType="+$("#msgType").val()+"&order="+$("#order").val()+"&jPage="+num,dataType: "json",success:function(result){
								var sName = "";
								var arrayOfMsgIds = "";
								 $.each(result.aData, function() {
									 var sClass = "paginatedList";
									 if(this['sysType']=='ReceivedMessage' && this['alreadyRead']){
										 sClass = "messReadList";
									 }
									 sName += "<div class='"+sClass+"' id='commresultRow"+this['id']+"' ><div class='leftImg'>";
									 
									 if(this['photoPresent']){
		       							 sName += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['fromUserId']+"&backlink=ref' ><img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id="+this['fromUserId']+"' /></a></div>"; 
		       						} else {
		       							 sName += "<img src='img/user_icon.png' /></div>";
		       						 }
									 var varBody = this['body'];
									 var trimBody = varBody.substring(0,150);
									 
									 sName += "<div class='details'><div class='heading' id='subject"+this['id']+"'";
									 if(this['sysType']=='SentMessage'){
											sName += " style='color: #09F; width:640px;'";
										} 
									 sName += ">"+this['subject']+"";
									 sName += "<div class='entry'>";
									 if(this['sysType']=='ReceivedMessage' && this['deleteFlag'] == 0){
										 if(this['alreadyRead']){
											 sName += "<span id='isMessageRead"+this['id']+"' style='width:50px;'><a class='commIndexRight' style='padding: 0px 30px 0px;font-weight: bold;' href='javascript:void(0);' onClick='return updateMessage("+this['id']+")'>";
											 sName += "Unread</span></a>";
										 }else{
											 sName += "<span id='isMessageRead"+this['id']+"' style='width:50px;'><a class='commIndexRight' style='padding: 0px 30px 0px;font-weight: bold;' href='javascript:void(0);' onClick='return updateMessage("+this['id']+")'>";
											 sName += "Read</span></a>";
										 }
									 }
									 
									 sName += "<div class='chkMSG'><input name='markChkMsg' id='"+this['id']+"' tabindex='2' type='checkbox' onclick='toggleBtnStyle();'/><label for='"+this['id']+"' ><span></span></label></div>";
									 sName += "</div>";
									 sName += "</div><div class='person'>";
									 if(this['sysType']=='ReceivedMessage'){
										 sName += " Received from ";
										 sName += "<a style='display: inline;' href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['fromUserId']+"&backlink=ref' ";
										 sName += " class='memberInfo' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['fromUserId']+"'>"+this['displayName']+"</a>";	
									 }
									 else{
										 sName += " Sent to ";
										 sName += "<a style='display: inline;' href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['toId']+"&backlink=ref' "
										 sName += " class='memberInfo' title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['toId']+"'>"+this['toName']+"</a>";
										if (this['toCount'] > 0) {
											sName += " and "+this['toCount']+" others" ;
										} else if (this['toCount'] > 1) { 
											sName += " one other";
										}
									 }
									 
									 sName += " on "+this['createdOn'];
									 sName += "<div class='entry' style='float:right;top:30px;'><a style='padding: 0px 6px 0px;' href='javascript:void(0);' onclick='sendMessage(&#39;"+this['id']+"&#39;, &#39;Share&#39;, &#39; &#39;, &#39; &#39;, &#39; &#39;, &#39;"+this['subject']+"&#39;);' >Share</a>";
									 if(this['sysType']=='ReceivedMessage'){
									 	sName += "<a style='padding: 0px 6px 0px;' href='javascript:void(0);' onclick='replyMessage(&#39;"+this['fromUserId']+"&#39;, &#39;"+this['displayName']+"&#39;, &#39;"+this['photoPresent']+"&#39;);' >Reply</a>";
									 }
									 
									 sName += "</div></div>";
	
									 sName += "<a style='padding: 0px 6px 0px;' href='javascript:void(0);' onClick='return getFullMessage("+this['id']+")' id='commIndexRight"+this['id']+"'><img id='img"+this['id']+"' src='img/icon-link-expand-dark.gif' width='15' height='15' class='levelImg2' /></a>";
									 sName += "<span class='commDesc' id='commDesc"+this['id']+"'>";
									 sName += "<p>"+trimBody+"</p></span>";
									 
									 sName += "</div></div>";
									 sName += "<input type='hidden' id='bodyValue"+this['id']+"' value='"+this['body']+"' />";
									 sName += "<input type='hidden' id='messageRead"+this['id']+"' value='"+this['read']+"' />";
									 if(arrayOfMsgIds == ""){
										 arrayOfMsgIds += this['id'];
									 }
									 else{
										 arrayOfMsgIds += ","+this['id'];
									 }
									  
									});
								 	sName += "<input type='hidden' id='arrayOfMsgIds' value='"+arrayOfMsgIds+"' />";
								 	sName += "<input type='hidden' id='selectedPage' value='"+num+"' />";
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
								     
								    },
								    
								 	// What to do before starting
							         beforeSend: function () {
							             $("#waitMessage").show();
							         } 
						    });
						} 
					});
				}

				$('.dynaDropDown').each(function() {
			         $(this).qtip({
			            content: {
			                text: function(event, api) {
			                    $.ajax({
			                        url: api.elements.target.attr('title'), // Use href attribute as URL
			                        type: 'GET', // POST or GET
			                        dataType: 'json', // Tell it we're retrieving JSON
			                        data: {
			                        }
			                    })
			                    .then(function(data) {
			                    	var mName = "";
			                        api.set('content.text', data.optionInfo);
			                    }, function(xhr, status, error) {
			                        api.set('content.text', 'Loading...');
			                    });
			                    return 'Loading...'; // Set some initial text
			                }
			            },
			            position: {
			            	viewport: $(window),
			            	my: 'top center',  // Position my top left...
			                at: 'bottom center', // at the bottom right of...
			                target: '', // Position it where the click was...
							adjust: { 
								mouse: false 
							} // ...but don't follow the mouse
						},
						style: {
					        classes: 'qtip-light myCustomClass3'
					    },
					    hide: {
			                fixed: true,
			                delay: 300
			            }
			         });
			     });

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
			function getFullMessage(obj){
				var varBody11 = document.getElementById('bodyValue'+obj).value;
				messageRead = document.getElementById('messageRead'+obj).value;
				document.getElementById("commDesc"+obj).innerHTML = varBody11;
				document.getElementById("img"+obj).setAttribute("src","img/icon-link-collapse-dark.gif");
				document.getElementById("commIndexRight"+obj).onclick = function(){backToShortMessage(obj);};
				if(messageRead=="false"){
					$.ajax({url:"${communityEraContext.contextUrl}/pers/messageUpdate.ajx?messageId="+obj,success:function(result){
						document.getElementById("isMessageRead"+obj).innerHTML = result['returnString'];
			    	    document.getElementById('messageRead'+obj).value =  result['alreadyRead'];
			    	    document.getElementById('subject'+obj).style.fontWeight = "normal";
			    	  }});
				}
				return false;
			}
			
			function backToShortMessage(obj){
				var varBody22 = document.getElementById('bodyValue'+obj).value;
				var trimBody = varBody22.substring(0,150);
				document.getElementById("commDesc"+obj).innerHTML = trimBody;
				document.getElementById("img"+obj).setAttribute("src","img/icon-link-expand-dark.gif");
				document.getElementById("commIndexRight"+obj).onclick = function(){getFullMessage(obj);} ;
				return false;
			}
			
			function updateMessage(obj){
				$.ajax({url:"${communityEraContext.contextUrl}/pers/messageUpdate.ajx?messageId="+obj,success:function(result){
					$.each(result.aData, function() {
						updateMessageResponse(obj, this);
					});
		    	  }});
				return false;
			}

			function updateMessageResponse(obj, result){
				$("#isMessageRead"+obj).html(result['returnString']);
	    	    document.getElementById('messageRead'+obj).value = result['alreadyRead'];
	    	    if(result['alreadyRead']){
	    	    	document.getElementById('commresultRow'+obj).className = "messReadList";
	    	    }else{
	    	    	document.getElementById('commresultRow'+obj).className = "paginatedList";
	    	    }
	    	    if (result['readFlag']) {
		    	    if (result['dbMessageCount'] > 0) {
			    	    var messKey = "message";
		    	    	if (result['dbMessageCount'] > 1) {
		    	    		messKey ="messages";
		    	    	}
		    	    	var chDB = "<a title='There are "+result['dbMessageCount']+" new "+messKey+" you haven&#39;t checked yet' id='dbRecMesId' class='normalTip' ";
			    	    chDB += "href='pers/myMessages.do?msgType=Unread'>You have received <span class='redMark'>"+result['dbMessageCount']+"</span> new messages</a>";
		    	    	$( "#dbRecMesId" ).replaceWith( chDB );
					} else {
		    	    	$( "#dbRecMesId" ).replaceWith( "" );
					}
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
			
			function toggleBtnStyle()
			{
				$(".qtip").hide();
				var somval = $( "input:checkbox[name=markChkMsg]:checked" ).length;
				var checkboxes = $( "input:checkbox[name=markChkMsg]" ).length;
				if(checkboxes == somval) {
					document.getElementById('unselectall').style.display = "inline";
				    document.getElementById('selectall').style.display = "none";
				} else {
					document.getElementById('unselectall').style.display = "none";
				    document.getElementById('selectall').style.display = "inline";
				}
			}
			
			function selectAll()
			{
				var checkboxes = document.getElementsByName('markChkMsg'), val = null;    
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
			}

			function unselectAll()
			{
				var checkboxes = document.getElementsByName('markChkMsg'), val = null;    
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
			}

			function toggleMsgMailAlert()
			{
				$.ajax({url:"${communityEraContext.contextUrl}/pers/messageMailAlert.ajx",success:function(result){
					var chNode = "";
					if (result['msgAlert'] == false) {
						chNode = "<a href='javascript:void(0);' class='normalTip' onclick='toggleMsgMailAlert();' title='Get email alerts when community members send you a message'>Get email alerts</a>";
					} else {
						chNode = "<a href='javascript:void(0);' class='normalTip' onclick='toggleMsgMailAlert();' title='Unsubscribe from email alerts for messages from community members'>Unsubscribe from mail alerts</a>";
					}

					$('#msgAlertId').html(chNode);
					$('.normalTip').qtip({ 
					    content: {
					        attr: 'title'
					    },
						style: {
					        classes: 'qtip-tipsy'
					    }
					});
		    	  }});
				return false;
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
							<li><a href="${communityEraContext.contextUrl}/pers/connectionList.do?id=${command.user.id}">Connections</a></li>
							<li><a href="${communityEraContext.contextUrl}/pers/mediaGallery.do?id=${command.user.id}">Gallery</a></li>
						</ul>
					</div>
					<div class="communities">
						<div class="type">
							<h4>My Messages (${command.totalMsgCount})</h4>
							<form:form showFieldErrors="true" name="messageSearch" action="${communityEraContext.contextUrl}/pers/myMessages.do">
								<form:dropdown id="msgType" path="msgType" fieldLabel="Type:">
									<form:optionlist options="${command.msgTypeOptions}" />
								</form:dropdown>
								
								<form:dropdown id="order" path="order" fieldLabel="Sort By:">
									<form:optionlist options="${command.orderOptions}" />
								</form:dropdown>
								<input type="hidden" id="msgType" name="msgType" value="${command.msgType}" />
								<input type="submit" value="Go" class="search_btn" style="margin-top: 0px;"/>
							</form:form>
							<span class='secLev' id="">
								<c:if test="${command.totalMsgCount > 0}">
									<input type="button" class="btnmain normalTip" onclick="selectAll();" id="selectall" value="Select All" />
								</c:if>
								<c:if test="${command.totalMsgCount == 0}">
									<input type="button" class="btnmain normalTip" onclick="selectAll();" id="selectall" value="Select All" style="display: none;"/>
								</c:if>
								<input type="button" class="btnmain normalTip" onclick="unselectAll();" id="unselectall" value="Unselect All" style="display: none;"/>
							</span>
						</div>
						<div class="menus">
							<ul>
								<li><a href="javascript:void(0);" class='normalTip' onclick="sendMessage('0', 'Send', '', '', '', '');" title="Send a message to your connections">New message</a></li>
								<c:if test="${communityEraContext.currentUser.msgAlert == false}">
									<li id="msgAlertId"><a href="javascript:void(0);" class='normalTip' onclick="toggleMsgMailAlert();" title="Get email alerts when community members send you a message">Get email alerts</a></li>
								</c:if>			
								<c:if test="${communityEraContext.currentUser.msgAlert == true}">
									<li id="msgAlertId"><a href="javascript:void(0);" class='normalTip' onclick="toggleMsgMailAlert();" title="Unsubscribe from email alerts for messages from community members">Unsubscribe from mail alerts</a></li>
								</c:if>
								<li>
									<c:if test="${command.msgType == 'Archived'}">
										<c:if test="${command.totalMsgCount > 0}">
											<a href="javascript:void(0);" class='dynaDropDown' title='community/communityOptions.ajx?currId=3005'>Message Actions <span class='ddimgBlk'/></a>
										</c:if>
										<c:if test="${command.totalMsgCount == 0}">
											<a href="javascript:void(0);" class='dynaDropDown' title='community/communityOptions.ajx?currId=3005' style="display: none;">Message Actions <span class='ddimgBlk'/></a>
										</c:if>
									</c:if>
									<c:if test="${command.msgType != 'Archived'}">
										<c:if test="${command.totalMsgCount > 0}">
											<a href="javascript:void(0);" class='dynaDropDown' title='community/communityOptions.ajx?currId=3004'>Message Actions <span class='ddimgBlk'/></a>
										</c:if>
										<c:if test="${command.totalMsgCount == 0}">
											<a href="javascript:void(0);" class='dynaDropDown' title='community/communityOptions.ajx?currId=3004' style="display: none;">Message Actions <span class='ddimgBlk'/></a>
										</c:if>
									</c:if>
								</li>
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
					<div id="newPagination">
						<%@ include file="/WEB-INF/jspf/pagination.jspf" %> 
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