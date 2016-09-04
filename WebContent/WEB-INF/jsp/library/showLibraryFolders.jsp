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
		<meta name="author" content="Jhapak">
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<title>Jhapak - ${communityEraContext.currentCommunity.name}' library</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		<link rel="publisher" href="https://plus.google.com/+Jhapak4u"/>
		<meta property="article:publisher" content="https://www.facebook.com/EraOfCommunities" />
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		
		<link rel="stylesheet" media="all" type="text/css" href="css/commBnnr.css" />
		
		<link rel="stylesheet" media="all" type="text/css" href="css/libItems.css" />
		<link rel="stylesheet" media="all" type="text/css" href="css/input.css" />
				
		<link rel="stylesheet" media="all" type="text/css" href="css/profile.css" />
		
		<link rel="stylesheet" media="all" type="text/css" href="css/dropzone.css" />
		<link type="text/css" href="css/jquery.jscrollpane.css" rel="stylesheet" media="all" />
 		<script type="text/javascript" src="js/jquery.jscrollpane.min.js"></script>
 		<link type="text/css" href="css/jquery.jscrollpane.lozenge.css" rel="stylesheet" media="all" />
 		<script type="text/javascript" src="js/dropzone.js"></script>
 		
 		<script type="text/javascript" src="js/qtip/dynamicDropDownQtip.js"></script>
		<script type="text/javascript" src="js/qtip/memberInfoTip.js"></script>
		
		<script type="text/javascript" src="js/jquery.aniview.min.js"></script>
		<link rel="stylesheet" type="text/css" href="css/animateScroll.css" />
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
 		
 		<style type="">
 			.commBanr .nav-list ul li.selected {
			    width: 180px;
			}
			
 			.jspCap {
				display: block;
				background: #eeeef4;
			}
			
			.jspVerticalBar {
				width: 8px;
			}
			
			.jspHorizontalBar {
				height: 8px;
			}
			
			.jspVerticalBar .jspCap {
				height: 1px;
			}
			
			.success-dialog .modal-dialog {
                width: 400px;
                max-height: 80px;
            }
            
            .success-dialog .modal-body {
            	min-height: 80px;
            }
            
            .success-dialog p.seccMsg {
            	padding: 16px;
            }
            
            .scroll-pane2 {
            	height: 260px;
            }
            
            #container .left-panel .commSection .communitiesType {
			    display: inline-block;
			    padding: 10px 10px 0px;
			    float: right;
			}
			
			#container .left-panel .commSection .communitiesType form {
			    clear: both;
			    color: #008EFF;
			    font-size: 13px;
			    font-weight: normal;
			}
			
			#container .left-panel .commSection .communitiesType form label {
			    color: #707070;
			    float: left;
			    font-size: 12px;
			    font-weight: normal;
			    height: 20px;
			    line-height: 20px;
			    margin-right: 6px;
			}
			
			#container .left-panel .commSection .communitiesType form select {
			    border: 1px solid #D6D6D6;
			    background: #FFF none repeat scroll 0% 0%;
			    color: #2C3A47;
			    margin: 0px 12px 5px 0px;
			    padding: 2px 5px;
			    width: 150px;
			    float: left;
			}
			
			.communitiesType form input[type="submit"] {
			    background: #546E86 none repeat scroll 0% 0%;
			    border: medium none;
			    cursor: pointer;
			    color: #FFF;
			    font-size: 12px;
			    font-weight: bold;
			    padding: 5px;
			}
			
			.panelBlkAlt {
			    margin-right: 15px;
			}
			
			.modal-dialog {
			    width: 620px;
			    margin: 60px auto;
			    height: 300px;
			}
			
 			.modal-body {
			    padding: 0px;
			 }
			 
			 .dropzone {
			    border: 0;
			    background: transparent repeating-linear-gradient(45deg, #F6F6F6, #F6F6F6 10px, #F8F9F9 10px, #F8F9F9 20px) repeat scroll 0% 0%;
			    padding: 0px;
			    min-height: 256px;
			    border: thin dashed #555;
			}
			
			.scroll-pane
			{
				width: 100%;
				height: 310px;
				overflow: auto;
			}
			
			
			#container .left-panel .commSection .paginatedList {
				border-radius: 0px;
				border-width: 0px 0px 1px 0px;
				box-shadow: inherit;
				width: 730px;
			}
			
			.fileDetail {
				padding: 10px; 
				width: 700px; 
				margin: 0px 15px; 
				border-width: 0px 0px 1px;
				border-style: solid;
				border-color: #D8D8D8;
				-moz-border-top-colors: none;
				-moz-border-right-colors: none;
				-moz-border-bottom-colors: none;
				-moz-border-left-colors: none;
				border-image: none;
			}
			
			.fileDetail table.fileDtlTbl tr {
				line-height: 24px;
			}
			
			.fileDetail table.fileDtlTbl th {
				text-align: left;
				vertical-align: top;
				font-weight: normal;
				color: #666;
				padding: 6px 6px 0px 0px;
				font-size: 13px;
				font-weight: bold;
				min-width: 120px;
			}
			
			.fileDetail table.fileDtlTbl td {
				width: 84%;
				padding: 6px 0px 0px 0px;
				font-size: 13px;
			}
			
			#upperSectionAlbum {
				height: 150px;
				padding: 14px 0px 14px 14px;
				width: 98%;
			}
			
			#lowerSectionAlbum {
				width: 100%;
				float: left;
				height: 240px;
			}
			
			#upperSectionAlbum .createAlbumForm {
				height:150px;
				width: 50%;
				float: left;
			}
			
			#upperSectionAlbum2 {
				padding: 14px 0px 14px 14px;
				width: 98%;
				height: 200px;
			}
			
			#upperSectionAlbum span, #upperSectionAlbum2 span {
				word-wrap: break-word;
				font-weight: bold;
				font-size: 12px;
				color: #AAA6A6;
				float: left;
			}
			
			#upperSectionAlbum span.top, #upperSectionAlbum2 span.top {
				float: right;
				margin: 0pc 30px 16px 0px;
			}
			
			#upperSectionAlbum span a, #upperSectionAlbum span a {
				margin: -9px 0px 0px 94px;
				float: right;
				color: #4E5665;
				text-shadow: 0px 1px 0px #FFF;
				line-height: 20px;
				display: inline-block;
				height: 20px;
				padding: 0px 8px;
				background: none repeat-x scroll 0% 0% #F6F7F8;
				border: 1px solid;
				border-radius: 2px;
				box-shadow: 0px 1px 1px rgba(0, 0, 0, 0.05);
				box-sizing: content-box;
				font-weight: bold;
				font-size: 12px;
				text-align: center;
				vertical-align: middle;
			}
			
			#upperSectionAlbum input[type="text"], #upperSectionAlbum2 input[type="text"] {
				width: 280px;
				margin-bottom: 10px;
				padding: 3px;
				cursor: default;
				background-color: transparent;
				border: 1px solid #DADEE2;
				font-size: 13px;
				padding: 5px;
				color: #20394D;
				outline: 0px none;
				font-size: 13px;
				padding: 5px;
				color: #20394D;
			}
			
			#upperSectionAlbum input.searchResult, #upperSectionAlbum2 input.searchResult {
				width: 272px;
				margin-bottom: 2px;
			}
			
			#upperSectionAlbum .createAlbumForm textarea, #upperSectionAlbum2 .createAlbumForm2 textarea {
				width: 280px;
				height: 106px;
				line-height: 1.28;
				font-size: 12px;
				border: 1px solid rgb(215, 215, 215);
				word-wrap: break-word;
				padding: 3px;
				text-decoration: none;
				resize: none;
				overflow: hidden;
				text-align: left;
			}
			
			.addContr {
				width: 280px;
				border: 1px solid rgb(210, 210, 210); 
				height:90px; 
				margin-top: 6px;
				float: left;
				padding-left:3px;
			}
			
			.addContr img{
				padding: 2px 2px 2px 2px;
			}
			
			#mainSectionAlbum {
				padding-bottom: 20px;
				height: 200px;
				margin-top: 19px;
			}
			
			#createAlbum {
				height: 440px;
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
			    width: 270px;
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
			
 		</style>
		
		<script type="text/javascript">
			function subscribeDocLib(docLibId){
				$.ajax({url:"${communityEraContext.currentCommunityUrl}/library/followLibrary.ajx?id="+docLibId,success:function(result){
		    	    $("#subscribeDocLib").html(result);
		    	  }});
			}

			function unSubscribeDocLib(docLibId){
				BootstrapDialog.show({
	                type: BootstrapDialog.TYPE_WARNING,
	                title: 'Stop Following this Library',
	                message: '<p class="extraWord">You will no longer be able to receive email alerts when new documents are posted to this library.</p>',
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
	                        $.ajax({url:"${communityEraContext.currentCommunityUrl}/library/stopFollowingLibrary.ajx?id="+docLibId,success:function(result){
	            	    	    $("#subscribeDocLib").html(result);
	            	    	  }});
	                    }
	                }]
	            });
			}

			var dref;
			var count = 1;
			var pgCount = 1;

			function infinite_scrolling(){
				if (count > pgCount){
            		return false;
				} else{
					var selOpt = document.getElementById('typeId').value;
					if(selOpt == 0){
						loadFiles (0, 0, '');
					}else if(selOpt == 1){
						var commId = document.getElementById('communityId').value;
						loadFiles (1, commId, '');
					}else if(selOpt == 2){
						loadFiles (2, 0, '');
					}else if(selOpt == 3){
						loadFiles (3, 0, '');
					}
				}
			}

			var _scheduledRequest = null;
			function infinite_scroll_debouncer(callback_to_run, debounce_time) {
			    debounce_time = typeof debounce_time !== 'undefined' ? debounce_time : 200;
			    if (_scheduledRequest) {
			        clearTimeout(_scheduledRequest);
			    }
			    _scheduledRequest = setTimeout(callback_to_run, debounce_time);
			}
			
			function changeCategory () {
				count = 1;
				pgCount = 1;
				var radios = document.getElementsByName('category');
				for (var i = 0, length = radios.length; i < length; i++) {
				    if (radios[i].checked) {
				        var selVal = radios[i].value;
				        if(selVal == 0){
				        	var fStr = '<form:form method="get"><div class="formelements"><form:dropdown path="typeId" id="typeId" onchange="changeOption()" >';
			                fStr += '<form:option value="0" label="My Files" /><form:option value="1" label="Files from other Communities" /><form:option value="2" label="Files from my connections" /><form:option value="3" label="Other People&#39;s File" />';
			                fStr += '</form:dropdown></div></form:form>';
			                fStr += '<input id="intSearch" tabindex="0" value="" class="searchResult" placeholder="Search files..." maxlength="48" ';
			                fStr += 'autocomplete="off" type="text" onkeydown="searchDocument()" />';
				        	fStr += '<i class="ico--search" onclick"searchDocument()"></i>';
				        	fStr += '<div class="lowerSection"><div class="lowerInfoSection"></div>';
				        	fStr += '<div class="scroll-pane2 horizontal-only"><div class="lowerDataSection"><ul id="pageList" class="fileInfo"></ul><p id="waitCloudMessageFile" style="margin-left: 235px; margin-top: 180px; min-height:200px; display: none;" class="showCloudWaitMessage" ></p></div></div></div>';
				        	document.getElementById('mainSection').innerHTML = fStr;
				        	dref.getButton('button-add').hide();
		            		dref.getButton('button-done').show();
		            		loadFiles (0, 0, '');
		            		dref.getModalBody().find('.scroll-pane2').bind(
			        				'jsp-scroll-y',
			        				function(event, scrollPositionY, isAtTop, isAtBottom)
			        				{
				        				if(isAtBottom){
				        					infinite_scroll_debouncer(infinite_scrolling, 400);
				        				}
			        				}
			        			).jScrollPane(
       		        		{
       		        			verticalDragMinHeight: 20,
       		        			verticalDragMaxHeight: 200,
       		        			autoReinitialise: true
       		        			}
       	    		        );
				        } else if (selVal == 1){
				        	var cStr = "<div class='scroll-pane horizontal-only'><div id='dropSection' class='dropzone dz-clickable'></div></div>";
				        	document.getElementById('mainSection').innerHTML = cStr;
				        	dref.getButton('button-add').show();
				        	dref.getButton('button-add').disable();
		            		dref.getButton('button-done').hide();
			            	Dropzone.autoDiscover = false;
			            	var myDropzone = new Dropzone("#dropSection", { url: "${communityEraContext.currentCommunityUrl}/library/addDocument.ajx",
		            		paramName :'files',
		            		autoProcessQueue: false,
		            		parallelUploads: 20,
		            		uploadMultiple :true
		            		});
		            		
			            	myDropzone.on("addedfile", function (file) {
			            		dref.getButton('button-add').enable();
			                });

			            	myDropzone.on("queuecomplete", function (file) {
			            		dref.getButton('button-add').hide();
			            		dref.getButton('button-done').hide();
			            		dref.getButton('button-done2').show();
			            		dref.getButton('button-done2').enable();
			                });

			            	var $footerButton = dref.getButton('button-add');
			            	$footerButton.click(function () {
		                        myDropzone.processQueue();
		                        dref.getButton('button-add').disable();
		                        dref.getButton('button-add').spin();
		                        dref.getButton('button-close').hide();
			                });

			            	var counter = 1;
			            	myDropzone.on("sending", function (file, xhr, formData) {
			            		var albmId = document.getElementById('currentFolder').value;
				            	if(counter == 1 && albmId > 0){
					            	formData.append("folderId", albmId);
					            	counter = 0;
				            	}
			                });

			            	dref.getModalBody().find('.scroll-pane').jScrollPane(
		        		{
		        			verticalDragMinHeight: 20,
		        			verticalDragMaxHeight: 600,
		        			autoReinitialise: true
		        			}
	    		        );
				        }
				        
				        break;
				    }
				}
			}

			function clickFileEntry (entryId) {
				var somval = $( "input:checkbox[name=fileEntry]:checked" ).length;
				var elm = document.getElementById('fileId_'+entryId);
				if(elm.className == "fileEntry"){
					elm.className = "";
					elm.className = "fileEntrySelected";
				}else{
					elm.className = "";
					elm.className = "fileEntry";
				}
				
				if (somval != 0) {
					document.getElementById('button-done').className = "newButton";
					document.getElementById('button-done').disabled = false;
				}else{
					document.getElementById('button-done').disabled = true;
				}
			}
			
			function loadFiles (typeId, commId, searchString) {
				$.ajax({url:"${communityEraContext.contextUrl}/library/myFilesDisplay.ajx?typeId="+typeId+"&communityId="+commId+"&searchString="+searchString+"&mPage="+count ,dataType: "json",success:function(result){
					var sName = "";
					$.each(result.aData, function() {
						sName += "<li class='fileEntry' id='fileId_"+this['id']+"' >";
						sName += "<input name='fileEntry' id='"+this['id']+"' tabindex='2' type='checkbox' onclick='clickFileEntry(&#39;"+this['id']+"&#39;);'/><label for='"+this['id']+"' ><span></span><img src='img/contenticon/imgicon.gif' width='16' height='16' />"+this['fileName']+"</label></li>";
					});
					if(count == 1){
						dref.getModalBody().find('#pageList').html(sName);
					} else {
						if (searchString == "") {
							dref.getModalBody().find('#pageList').append(sName);
						} else {
							dref.getModalBody().find('#pageList').html(sName);
						}
					}
    		        dref.getModalBody().find('#waitCloudMessageFile').hide();
    		        pgCount = result.totalPage;
    			    },
                	complete: function () {
                		count++;
                	},
    		         beforeSend: function () {
    			    	//dref.getModalBody().find('.lowerDataSection').html('<p id="waitCloudMessageFile" style="margin-left: 340px; margin-top: 120px; display: none;" class="showCloudWaitMessage" ></p>');
    			    	dref.getModalBody().find('#waitCloudMessageFile').show();
    		         } 
    		    });
			}

			function changeOption(){
				count = 1;
				pgCount =1;
				var selOpt = document.getElementById('typeId').value;
				document.getElementById('intSearch').value = '';
				document.getElementById('intSearch').placeholder = "Search files...";
				if(selOpt == 0){
					loadFiles (0, 0, '');
				}else if(selOpt == 1){
					var commId = document.getElementById('communityId').value;
					loadFiles (1, commId, '');
				}else if(selOpt == 2){
					loadFiles (2, 0, '');
				}else if(selOpt == 3){
					document.getElementById('intSearch').placeholder = "Search files by name of a person...";
				}
			}

			function searchDocument(){
				var selOpt = document.getElementById('typeId').value;
				var searchString = document.getElementById('intSearch').value;
				if (searchString != ""){
					if(selOpt == 0){
						loadFiles (0, 0, searchString);
					}else if(selOpt == 1){
						var commId = document.getElementById('communityId').value;
						loadFiles (1, commId, searchString);
					}else if(selOpt == 2){
						loadFiles (2, 0, searchString);
					}else if(selOpt == 3){
						loadFiles (3, 0, searchString);
					}
				}
			}

			function addFolders(docLibId){
				$(".qtip").hide();
				var newalbId = 0;
				var msg = "<div id='createAlbum'><div id='upperSectionAlbum'><div class='createAlbumForm'>";
				msg += "<input id='intTitle' style='width: 576px;' tabindex='0' maxlength='100' value='' placeholder='Untitled Folder' maxlength='50' autocomplete='off' type='text' />";
				msg += "<br/><textarea id='intDesc' maxlength='300' style='width: 580px;' placeholder='Say something about this folder...' cols='60' rows='10'></textarea>";
				msg += "</div></div>";
				msg += "<div id='lowerSectionAlbum'><div class='scroll-pane2 horizontal-only'><div id='mainSectionDrop' class='dropzone dz-clickable'></div></div></div></div>";
				var myDropzone;
				var dialogInstance = BootstrapDialog.show({
				title: 'Add Folder',
				message: msg,
	            closable: false,
	            closeByBackdrop: false,
	            onshow: function(dialog) {
	                dialog.getButton('button-add').disable();
	                dialog.getButton('button-done').hide();
	            },
	            buttons: [{
	            	id: 'button-done',
	                label: 'Done!',
	                cssClass: 'btn-primary', 
	                autospin: false,
	                action: function(dialog){
	            	dialog.close();
	            	showFolderInfo(newalbId);
	            }
	            },{
	            	id: 'button-close',
	                label: 'Close',
	                action: function(dialog){
	            	dialog.close();
	            }
	            }, {
	            	id: 'button-add',
	                label: 'Upload photos',
	                cssClass: 'btn-primary',
	                action: function(dialog) {
	                }
	            }],
	            cssClass: 'login-dialog',
	            onshown: function(dialogRef){
	            	var title = document.getElementById('intTitle');
	            	var libraryId = document.getElementById('libraryId').value;
	            	Dropzone.autoDiscover = false;
	            		myDropzone = new Dropzone("#mainSectionDrop", { url: "${communityEraContext.currentCommunityUrl}/library/addDocument.ajx?type=folder"
		            		+"&action=createFolder&communityId="+communityId+"&libraryId="+libraryId,
	            		paramName :'files',
	            		autoProcessQueue: false,
	            		acceptedFiles: "image/*",
	            		parallelUploads: 100,
	            		maxFiles: 100,
	            		uploadMultiple :true
	            		});
	            		
		            	myDropzone.on("addedfile", function (file) {
		            		dialogRef.getButton('button-add').enable();
		                });
	
		            	myDropzone.on("queuecomplete", function (file) {
		            		dialogRef.getButton('button-add').hide();
		            		dialogRef.getButton('button-done').show();
		                });
	
		            	var $footerButton = dialogRef.getButton('button-add');
		            	$footerButton.click(function (e) {
		            		e.preventDefault();
		            	      e.stopPropagation();
		                    myDropzone.processQueue();
		                    dialogRef.getButton('button-add').disable();
		                    dialogRef.getButton('button-add').spin();
		                    dialogRef.getButton('button-close').hide();
		                });
						var counter = 1;
		            	myDropzone.on("sending", function (file, xhr, formData) {
			            	if(counter == 1){
			            		var title = document.getElementById('intTitle');
			            		var description = document.getElementById('intDesc');
			            		
				            	formData.append("title", title.value);
				            	formData.append("description", description.value);
				            	counter = 0;
			            	}
		                });
	
		            	myDropzone.on("successmultiple", function(files, response) {
		            		var obj = jQuery.parseJSON(response);
		            		newalbId = obj.albumId;
		            		var albmId = document.getElementById('currentAlbum').value = obj.albumId;
		            	});
	
		        		dialogRef.getModalBody().find('.scroll-pane2').jScrollPane(
		        		{
		        			autoReinitialise: true
		        		});
		        		dialogRef.getModalBody().find('.scroll-pane3').jScrollPane(
		        		{
		        			autoReinitialise: true
		        		});
	            	}
		        });
			}

			function showFolders() {
				document.getElementById('foldersContainer').style.display = 'inline';
				document.getElementById('folderInfoContainer').style.display = 'none';
				document.getElementById('communityType').style.display = 'none';
				document.getElementById('filesContainer').style.display = 'none';
				document.getElementById('communityFolders').className = "selected";
	       		document.getElementById('communityFiles').className = "";

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
						$.ajax({url:"${communityEraContext.currentCommunityUrl}/library/showLibraryFolders.ajx?jPage="+num,dataType: "json",success:function(result){
							var sName = "";
							
							$.each(result.aData, function() {
								sName += "<div class='paginatedList'><div class='leftImg'>";
								sName += "<img src='img/file/foldericon.gif'  />";
								sName += "</div><div class='details' style='width: 650px;'>";
								sName += "<div class='heading' style='width: 540px;word-wrap: normal;'>";
								sName += "<a class='ajax' pin_id='"+this['id']+"' href='javascript:void(0);' onclick='showFolderInfo(&#39;"+this['id']+"&#39;)'>"+this['title']+"</a></div>";
								sName += "<div class='person'>Updated by <a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['posterId']+"&backlink=ref' class='memberInfo' ";
								sName += "title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['posterId']+"'>"+this['firstName']+" "+this['lastName']+"</a> on "+this['createdOn'];
								sName += " <i class='a-icon-text-separator'></i> "+this['photoCount']+" files";
								sName += "</div></div></div>";
							 });
							 $("#page").html(sName);
	
							 $('.normalTip').qtip({ 
								    content: {
								        attr: 'title'
								    },
									style: {
								        classes: 'qtip-tipsy'
								    }
								});
							// Hide message
					        $("#waitMessage").hide();
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
			}

			function showCommunityFiles() {
				var ref = '${communityEraContext.currentCommunityUrl}/library/showLibraryItems.do';
            	window.location.href=ref;
			}

			function showFolderInfo(folderId) {
				document.getElementById('foldersContainer').style.display = 'none';
				document.getElementById('folderInfoContainer').style.display = 'inline';
				document.getElementById('filesContainer').style.display = 'inline';
				document.getElementById('communityType').style.display = 'none';
				document.getElementById('communityFolders').className = "selected";
	       		document.getElementById('communityFiles').className = "";
				document.getElementById('currentFolder').value = folderId;
				var currentCommunity = '${communityEraContext.currentCommunity.id}';
				var libraryId = document.getElementById('libraryId').value;
				$.ajax({url:"${communityEraContext.contextUrl}/pers/showGroupLibMediaInfo.ajx?communityId="+currentCommunity+"&groupMediaId="+folderId+"&type=folderOnly",dataType: "json",success:function(result){
					var fName = "<div class='fileDetail' >";
					fName += "<table class='fileDtlTbl' cellpadding='0' cellspacing='0'><tbody>";
					fName += "<tr><th><img src='img/file/foldericon.gif' style='max-height: 50px;'></th>";
					fName += "<td style='color: #184A72;'>"+result.title+"<a href='${communityEraContext.contextUrl}/cid/"+result.communityId+"/library/downloadFile.do?id=1199' ";
					fName += " class='btnmain' style='float: right;' title='Opens a new window containing attached file'><i class='fa fa-file-zip-o' style='font-size: 20px;'></i> Download</a></td></tr>";
					fName += "<tr><th>Description:</th><td><div class='richtext'><p>"+result.title+"</p></div></td></tr>";
					fName += "<tr><th>Created:</th><td>"+result.createdOn+" by <a title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+result.posterId+"' class='memberInfo' ";
					fName += " style='color: #66799f; font-weight: bold;'>"+result.posterName+"</a></td></tr>";
					fName += "<tr><th>Last Update:</th><td>"+result.lastUpdate+"</td></tr>";
					fName += "<tr><th>Files included:</th><td>"+result.fileCount+"</td></tr>";
					fName += "</tbody></table></div>";
					$("#folderInfoContainer").html(fName);
					// Hide message
			        $("#waitFolderInfo").hide();
					if (result.fileCount > 0) {
						var pFile = "";
						if (result.filePageCount > 5) {
							pFile += "<a class='jPaginatorMax' id='max_backward' style='border-left: medium none;' >First</a>";
							pFile += "<a class='jPaginatorOver' id='over_backward' style='border-left: 1px solid rgb(163, 176, 185);'>&lt;&lt;&lt;</a>";
						}
						pFile += "<div class='paginator_p_wrap'><div class='paginator_p_bloc'></div></div>";
						if (result.filePageCount > 5) {
							pFile += "<a class='jPaginatorOver' id='over_forward' style='border-left: 1px solid rgb(163, 176, 185);'>&gt;&gt;&gt;</a>";
							pFile += "<a class='jPaginatorMax' id='max_forward' >Last</a>";
						}
						$("#paginationFiles").html(pFile);
						$("#paginationFiles").jPaginator({ 
							nbPages:result.filePageCount,
							marginPx:5,
							length:6, 
							overBtnLeft:'#over_backward', 
							overBtnRight:'#over_forward', 
							maxBtnLeft:'#max_backward', 
							maxBtnRight:'#max_forward', 
							onPageClicked: function(a,num) { 
							$.ajax({url:"${communityEraContext.contextUrl}/pers/showGroupLibMediaInfo.ajx?communityId="+currentCommunity+"&groupMediaId="+folderId+"&type=folder&jFile="+num,dataType: "json",success:function(result){
								var sName = "";
								$.each(result.aData, function() {
									sName += "<div class='paginatedList'><div class='leftImg'>";
									if (this['iconType'] == 'xls') {
										sName += "<img src='img/file/excelicon.gif' style='max-height: 50px;'/>";
									}
									if (this['iconType'] == 'zip') {
										sName += "<img src='img/file/zipicon.gif' style='max-height: 50px;'/>";
									}
									if (this['iconType'] == 'pdf') {
										sName += "<img src='img/file/pdficon.gif' style='max-height: 50px;'/>";
									}
									if (this['iconType'] == 'xml') {
										sName += "<img src='img/file/xmlicon.gif' style='max-height: 50px;'/>";
									}
									if (this['iconType'] == 'img') {
										sName += "<img src='img/file/imgicon.gif' style='max-height: 50px;'/>";
									}
									if (this['iconType'] == 'doc') {
										sName += "<img src='img/file/wordicon.gif' style='max-height: 50px;'/>";
									}
									if (this['iconType'] == 'ppt') {
										sName += "<img src='img/file/powerpointicon.gif' style='max-height: 50px;'/>";
									}
									if (this['iconType'] == 'file' || this['iconType'] == '') {
										sName += "<img src='img/file/fileicon.gif' style='max-height: 50px;'/>";
									}
									sName += "</div><div class='details' style='width: 650px;'>";
									sName += "<div class='heading' style='width: 540px;word-wrap: normal;'>";
									sName += "<a class='ajax' pin_id='"+this['id']+"' href='${communityEraContext.contextUrl}/cid/"+currentCommunity+"/library/documentdisplay.do?backlink=ref&id="+this['id']+"' >"+this['title']+"</a></div>";
									sName += "<div class='person'>Added by <a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['posterId']+"&backlink=ref' class='memberInfo' ";
									sName += " title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['posterId']+"'>"+this['displayName']+"</a> on "+this['datePostedOn'];
									sName += " <i class='a-icon-text-separator'></i> "+this['downloads']+" downloads";
									sName += "</div></div></div>";
								 });
								 $("#pageFile").html(sName);
								 $('.normalTip').qtip({ 
									    content: {
									        attr: 'title'
									    },
										style: {
									        classes: 'qtip-tipsy'
									    }
									});
								// Hide message
						        $("#waitpageFileMessage").hide();
						        toggleOnLoad();
							    },
							 	// What to do before starting
						         beforeSend: function () {
						             $("#waitpageFileMessage").show();
						         } 
						      });
							} 
						});
					} else {
						$("#filesContainer").html("No files added");
					}
				    },
				 	// What to do before starting
			         beforeSend: function () {
			             $("#waitFolderInfo").show();
			         } 
			      });
			}
		</script>
		
		<script type="text/javascript">
		// Initial call
		$(document).ready(function(){
			
			normalQtip();
			memberInfoQtip();
			dynamicDropDownQtip();
			showFolders();
			
			$.ajax({url:"${communityEraContext.contextUrl}/common/mediaPannel.ajx?communityId=${communityEraContext.currentCommunity.id}",dataType: "json",success:function(result){
				var temp = "";
				$.each(result.aData, function() {
					temp += "<img src='${communityEraContext.contextUrl}/community/getCommunityMedia.img?id="+this['mediaId']+"' width='89' height='89' style='padding: 3px; border-radius: 10px;' />";
					});
				 $("#mediaList").html(temp);
		        $("#waitMediaMessage").hide();
			    },
		         beforeSend: function () {
		             $("#waitMediaMessage").show();
		         } 
		    });

			$.ajax({url:"${communityEraContext.currentCommunityUrl}/community/cloudCommunity.ajx",dataType: "json",success:function(result){
				var aName = "<ul>";
				var fTagList = $("#fTagList").val();
				 $.each(result.aData, function() {
					 aName += "<li>";
					 aName += "<a href='${communityEraContext.currentCommunityUrl}/search/searchByTagInCommunity.do?filterTag="+this['tagText']+"&fTagList="+fTagList+"' ";
					 aName += "title='View "+this['count']+" items tagged with &#39;"+this['tagText']+"&#39;' class='size-"+this['cloudSet']+"' >"+this['tagText']+"</a>";
					 aName += "</li>";
						});
				 $("#cloud").html(aName+"</ul>");
				 
				 var bName = "<table >";
				 $.each(result.bData, function() {
					 bName += "<tr><td>";
					 bName += "<span class='size-"+this['cloudSet']+"'  ><a href='${communityEraContext.currentCommunityUrl}/search/searchByTagInCommunity.do?filterTag="+this['tagText']+"&fTagList="+fTagList+"' ";
					 bName += "title='View "+this['count']+" items tagged with &#39;"+this['tagText']+"&#39;' class='size-"+this['cloudSet']+"' >"+this['tagText']+"</a></span>";
					 bName += "</td><td style='color: rgb(42, 58, 71); float: right;'>["+this['count']+"]</td>";
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
							<li><a href="${communityEraContext.currentCommunityUrl}/library/showLibraryItems.do" class="dynaDropDown" 
								title="community/communityOptions.ajx?currId=1005&commId=${communityEraContext.currentCommunity.id}">
									Library <i class="fa fa-sort-down" style="margin-left: 8px; font-size: 20px;"></i> 
									</a>
							</li>
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
												<c:if test="${feature == 'Members'}">
													<li><a href="${communityEraContext.currentCommunityUrl}/connections/showConnections.do"><i class="fa fa-user-plus" style="margin-right: 8px;"></i>Members</a></li>
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
					<div class="inputForm" style="padding: 0px; border-width: 0px;" id="inputForm">
						<div class="intHeader" style="min-height: 68px; border-bottom: none;">
							<div class="upperGrp">
								<span class='firLev'>
									<a onclick="showCommunityFiles();" href="javascript:void(0);" title="Community files" class="selected" id="communityFiles">Files</a>
								</span>
								<span class='firLev'>
									<a onclick="showFolders();" href="javascript:void(0);" title="Community folders" class="" id="communityFolders">Folders</a>
								</span>
								<div class="communitiesType" id="communityType">
									<input type="hidden" id="currentFolder" name="currentFolder" />
									<input type="hidden" id="communityId" name="communityId" value="${communityEraContext.currentCommunity.id}"/>
									<input type="hidden" id="libraryId" name="libraryId" value="${command.documentLibrary.id}"/>
									<form:form showFieldErrors="true">
											<form:dropdown path="sortByOption" fieldLabel="Sort By:">
												<form:optionlist options="${command.sortByOptionOptions}" />
											</form:dropdown>
										<input type="hidden" id="sortByOption" name="sortByOption" value="${command.sortByOption}" />
										<input type="submit" value="Go"/>
									</form:form>
								</div>
							<%-- <c:if test="${command.member}">
								<span class='secLev' id="addPhotos">
									<a class="btnmain normalTip" onclick="uploadPhotos();" href="javascript:void(0);" 
										title="Add Photos" ><i class="fa fa-photo" style="font-size: 18px; margin-right: 8px;"></i>Add Photos</a>
								</span>
								<span class='secLev'>
									<a class="btnmain normalTip" onclick="createAlbum('${communityEraContext.currentCommunity.id}');" href="javascript:void(0);" 
										title="Create Album" ><i class="fa fa-folder-o" style="font-size: 18px; margin-right: 8px;"></i>Create Album</a>
								</span>
							</c:if> --%>
							</div>
							<div class="menus" style="margin-top: 4px;">
								<ul>
									<c:if test="${command.member}">
										<li><a onclick="addFolders(&#39;${command.documentLibrary.id}&#39;);" href="javascript:void(0);" class="normalTip" title="Add a new folder to this library">Add Folders</a></li>
									</c:if>
									<c:if test="${command.currentUserSubscribed}">
										<li id="subscribeDocLib"><a onclick="unSubscribeDocLib(${command.documentLibrary.id});" href="javascript:void(0);" 
										class="normalTip" title="Unsubscribe from email alerts when new files are added">Stop Following</a></li>
									</c:if>
									<c:if test="${not command.currentUserSubscribed}">
										<li id="subscribeDocLib"><a onclick="subscribeDocLib(${command.documentLibrary.id});" href="javascript:void(0);"  
										class="normalTip" title="Send me email alerts when new files are added to this Library">Follow</a></li>
									</c:if>
									<%--  TODO : feed will be opened later
										<li><a href="${communityEraContext.currentCommunityUrl}/doclib/feed.rss" title="System Administrator tool only - View this library as an RSS feed">RSS feed</a></li>
									
										<li><a href="#" class="ttip" onclick="return false" title="News feed :: The news feed requires a feed reader that supports password protected feeds. "><img src="img/i/help-ongreen.gif" alt="Help" width="18" height="18" /></a></li>	
									--%>
								</ul>
							</div>
						</div>
						
						<div id="foldersContainer" style="display: none;">
							<div id="page"></div>
							<div id="waitMessage" style="display: none;">
								<div class="cssload-square" >
									<div class="cssload-square-part cssload-square-green" ></div>
									<div class="cssload-square-part cssload-square-pink" ></div>
									<div class="cssload-square-blend" ></div>
								</div>
							</div>
							<c:if test="${command.pageCount > 0}">
								<span class="euInfo" id="tRecordsCount">Total Records - ${command.pageCount}</span>
								<div id="pagination" style="width:auto;float:right; height: 34px;"> 
									<!-- optional left control buttons--> 	
									<c:if test="${command.pageCount > 5}">
										<a class="jPaginatorMax" id="max_backward" style="border-left: medium none;" >First</a>
										<a class="jPaginatorOver" id="over_backward" style="border-left: 1px solid rgb(163, 176, 185);">&lt;&lt;&lt;</a> 
									</c:if>
									<div class='paginator_p_wrap'> 
										<div class='paginator_p_bloc'> 
											<!--<a class='paginator_p'></a> // page number : dynamically added --> 
										</div> 
									</div> 
									<!-- optional right control buttons--> 
									<c:if test="${command.pageCount > 5}">
										<a class="jPaginatorOver" id="over_forward" style="border-left: 1px solid rgb(163, 176, 185);">&gt;&gt;&gt;</a> 
										<a class="jPaginatorMax" id="max_forward" >Last</a>
									</c:if>
								</div>
							</c:if>
						</div>
						<div id="folderInfoContainer" style="display: none;">
							<div id="waitFolderInfo" style="display: none;">
								<div class="cssload-square" >
									<div class="cssload-square-part cssload-square-green" ></div>
									<div class="cssload-square-part cssload-square-pink" ></div>
									<div class="cssload-square-blend" ></div>
								</div>
							</div>
						</div>
						<div id="filesContainer" style="display: none;">
							<div id="pageFile"></div>
							<div id="waitpageFileMessage" style="display: none;">
								<div class="cssload-square" >
									<div class="cssload-square-part cssload-square-green" ></div>
									<div class="cssload-square-part cssload-square-pink" ></div>
									<div class="cssload-square-blend" ></div>
								</div>
							</div>
							<div id="paginationFiles" style="width:auto;float:right; height: 34px;"> 
							</div>
						</div>
					</div>
					</div>
				</div> 
				
				<div class="right-panel">
					<c:if test="${command.mediaCount > 0}">
						<div class="inbox">
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
								<a href="${communityEraContext.currentCommunityUrl}/library/mediaGallery.do">View Gallery</a>
							</div>
						</div>
					</c:if>
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
		<%@ include file="/WEB-INF/jspf/extraFooter.jspf" %>
	</body>
</html>