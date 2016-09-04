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
			
			.searchResult {
				width: 600px;
			}
			
			.ico--search {
				top: 73px;
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
			
			.mainSection {
				padding: 4px
			}
			
			.jspVerticalBar .jspCap {
				height: 1px;
			}
			
			.subs-dialog .modal-body {
                padding: 15px;
                min-height: auto;
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
			    padding: 2px 4px;
				border-radius: 50%;
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
			
			#mainSection .dropzone {
			    border: 0;
			    background: transparent repeating-linear-gradient(45deg, #F6F6F6, #F6F6F6 10px, #F8F9F9 10px, #F8F9F9 20px) repeat scroll 0% 0%;
			    padding: 0px;
			    min-height: 360px;
			    border: thin dashed #555;
			}
			
			.scroll-pane2 {
            	height: 260px;
            }
			
			.scroll-pane
			{
				width: 100%;
				height: 360px;
				overflow: auto;
			}
			
			.searchResult {
			}
			
			#container .left-panel .commSection .paginatedList {
				border-radius: 0px;
				border-width: 0px 0px 1px 0px;
				box-shadow: inherit;
				width: 730px;
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
			
			.footer .box-grid-author-meta {
			    height: 60px;
			}
			
			.av-container .columns {
			    width: 30%;
			    margin-left: 10px;
			    padding: 0px;
			}
			
			#container .left-panel .image-section {
				max-height: 160px;
				width: 100%;
				padding: 0;
				border-radius: 0;
			}
			
			.image-section a img {
				border-radius: 0;
			}
			
			#container .left-panel .image-section a img.fileimg {
				min-height: 10px;
				width: 50%;
				height: 50%;
				margin: 40px 60px;
			}
			
			#container .left-panel .image-section div.text {
				width: 100%;
				bottom: 72px;
			}
			
			@media only screen and (max-width: 767px) {
				.av-container .columns {
					min-width: 300px;
				}
			}
 		</style>
		
		<script type="text/javascript">
			function subscribeDocLib(docLibId){
				var isAuthenticated = ${communityEraContext.userAuthenticated};
			    if (!isAuthenticated) {
			    	BootstrapDialog.show({
		                type: BootstrapDialog.TYPE_DANGER,
		                title: 'Follow this Library',
		                message: '<p class="addTagHeader">You are not logged-in.<br/> Please login first to follow this library. </p>',
		                cssClass: 'subs-dialog',
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
			    	$.ajax({url:"${communityEraContext.currentCommunityUrl}/library/followLibrary.ajx?id="+docLibId,success:function(result){
			    	    $("#subscribeDocLib").html(result);
			    	  }});
			    }
			}

			function unSubscribeDocLib(docLibId){
				var isAuthenticated = ${communityEraContext.userAuthenticated};
			    if (!isAuthenticated) {
			    	BootstrapDialog.show({
		                type: BootstrapDialog.TYPE_DANGER,
		                title: 'Stop Following this Library',
		                message: '<p class="addTagHeader">You are not logged-in.<br/> Please login first to stop following this library.</p>',
		                cssClass: 'subs-dialog',
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
		                title: 'Stop Following this Library',
		                message: '<p class="extraWord">You will no longer be able to receive email alerts when new documents are posted to this library.</p>',
		                cssClass: 'subs-dialog',
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
		                        alert(docLibId);
		                        $.ajax({url:"${communityEraContext.currentCommunityUrl}/library/stopFollowingLibrary.ajx?id="+docLibId,success:function(result){
		            	    	    $("#subscribeDocLib").html(result);
		            	    	  }});
		                    }
		                }]
		            });
			    }
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

			function addFiles(docLibId){
				BootstrapDialog.show({
	                title: 'Add Files to this Community',
	                message: function(dialog) {
	                	var mess = '<div id="main"></div>';
		                return mess;
		            },
		            closable: false,
	                closeByBackdrop: false,
	                closeByKeyboard: false,
		            onshow: function(dialog) {
		            	var mess = '<div class="radio-group">';
	                	mess += '<div class="leftRadio"><input name="category" id="myFiles" checked class="" tabindex="2" type="radio" onclick="changeCategory();" value="0"/><label for="myFiles"><span></span>My Files</label></div>';
	                	mess += '<div class="rightRadio"><input name="category" id="myComputer" class="" tabindex="2" type="radio" onclick="changeCategory();" value="1"/><label for="myComputer"><span></span>My Computer</label></div>';
		                mess += '</div>';
		                mess += '<div class="mainSection" id="mainSection">';
		                mess += '<form:form method="get"><div class="formelements"><form:dropdown path="typeId" id="typeId" onchange="changeOption()" >';
		                mess += '<form:option value="0" label="My Files" /><form:option value="1" label="Files from other Communities" /><form:option value="2" label="Files from my connections" /><form:option value="3" label="Other People&#39;s File" />';
		                mess += '</form:dropdown></div></form:form>';
		                mess += '<input id="intSearch" tabindex="0" value="" class="searchResult" placeholder="Search files..." maxlength="48" ';
		                mess += 'autocomplete="off" type="text" onkeydown="searchDocument()" />';
		                mess += '<i class="ico--search" onclick="searchDocument()"></i>';
		                mess += '<div class="lowerSection"><div class="lowerInfoSection"></div>';
		                mess += '<div class="scroll-pane2 horizontal-only"><div class="lowerDataSection"><ul id="pageList" class="fileInfo"></ul><p id="waitCloudMessageFile" style="margin-left: 340px; margin-top: 120px; display: none;" class="showCloudWaitMessage" ></p></div></div></div>';
		                mess += '</div>';
		                dialog.getModalBody().find('#main').html(mess);
		                dialog.getButton('button-add').hide();
		                dialog.getButton('button-done').disable();
		                dialog.getButton('button-done2').hide();
		                dref = dialog;
		                dialog.getModalBody().find('.scroll-pane2').bind(
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
		            },
		            onshown: function(dialogRef){
		            	loadFiles (0, 0, '');
		            },
	                buttons: [{
		            	id: 'button-close',
		                label: 'Close',
		                action: function(dialog){
		            		dialog.close();
		            	}
		            },
		            {
		            	id: 'button-done',
		                label: 'Done!',
		                cssClass: 'btn-primary', 
		                autospin: false,
		                action: function(dialog){
			            	var json = []; 
			            	$('input:checkbox[name=fileEntry]:checked').each(function(i, selected){ 
			            		json[i] = $(selected).attr('id');
			            	});
			            	var libId = document.getElementById('libraryId').value;
			            	var docId = document.getElementById('currentFolder').value;
			            	$.ajax({
			            		type:"POST",
			                    url: "${communityEraContext.contextUrl}/library/shareDocument.ajx?libraryId="+libId,
			                    data: {json:json},
			                    success:function(result){
			                    	dialog.close();
			                    	var somval = $( "input:checkbox[name=fileEntry]:checked" ).length;
			        				var dialogInstance = BootstrapDialog.show({
			        					title: 'Share Files',
			        					type: BootstrapDialog.TYPE_SUCCESS,
			        					message: function(dialog) {
			        					var $message = $('<div id="main"><p class="seccMsg">'+somval+' files shared sucessfully.</p></div>');
			        	                return $message;
			        	            },
			        	            cssClass: 'success-dialog',
			        	            closable: true,
			        	            closeByBackdrop: false,
			        	            buttons: [{
			        	                label: 'Close',
			        	                cssClass: 'btn-success',
			        	                action: function(dialog){
			        	            	var ref = '${communityEraContext.currentCommunityUrl}/library/showLibraryItems.do';
			        	            	window.location.href=ref;
			        	            	dialog.close();
			                        	}
			        	            	}]
			        				});
			        			}
			                });
		            		//dialog.close();
		            	}
		            },
		            {
		            	id: 'button-add',
		                label: 'Upload files',
		                cssClass: 'btn-primary',
		                action: function(dialog) {
		                }
		            },
		            {
		            	id: 'button-done2',
		            	label: 'Done!',
		                cssClass: 'btn-primary', 
		                autospin: false,
		                action: function(dialog){
			            	var ref = '${communityEraContext.currentCommunityUrl}/library/showLibraryItems.do';
	    	            	window.location.href=ref;
		            		dialog.close();
		            	}
		            }]
	            });
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
				var ref = '${communityEraContext.currentCommunityUrl}/library/showLibraryFolders.do';
            	window.location.href=ref;
			}

			function showCommunityFiles() {
				var ref = '${communityEraContext.currentCommunityUrl}/library/showLibraryItems.do';
            	window.location.href=ref;
			}
		</script>
		
		<script type="text/javascript">
			function applyFilter(filterUrl){
				var fTagList = document.getElementById("fTagList").value;
				var sortOption = document.getElementById("sortByOption").value;
				var toggleList = document.getElementById("toggleList").value;
				filterUrl += "&fTagList="+fTagList+"&sortByOption="+sortOption+"&toggleList="+toggleList;
				window.location.href = filterUrl;
			}

			var scrollCount = 2;
			function infinite_scrolling_main(){
				if  ($(window).scrollTop()  >= $(document).height() - $(window).height() - 250){
			    	var total = document.getElementById('pgCount').value;
	                	if (scrollCount > total){
	                		return false;
			        	} else {
							$.ajax({url:"${communityEraContext.currentCommunityUrl}/library/showLibraryItems.ajx?jPage="+scrollCount+"&sortByOption="+$("#sortByOption").val()+"&fTagList="+$("#fTagList").val()+"&toggleList="+$("#toggleList").val(),dataType: "json",success:function(result){ 
								var sName = "";
								var i = 1;
								 $.each(result.aData, function() {
									 var rowId = this['id'];
									 var trclass = '';
			   						 if(i%4 != 0){
			   							 trclass = 'Alt';
			   						 }
									 sName += "<div class='aniview reallyslow columns panelBlk"+trclass+"' av-animation='fadeInUp' id='viewSlide"+scrollCount+"' style='width: 23.5%;'>";
									 sName += "<div class='panelBox' >";
									 sName += "<div class='top-info-bar'>";
									 sName += "<div class='entry' id='reqId-"+rowId+"'><span><i class='fa fa-eye' style='margin-left: 6px;'></i> ${row.downloads}</span></div>";
									 sName += "</div>";
									 sName += "<div class='image-section' >";
									 sName += "<a href='${communityEraContext.currentCommunityUrl}/library/documentdisplay.do?backlink=ref&amp;id="+rowId+"'>";
									 if(this['iconType'] == 'xls'){
										 sName += "<img src='img/file/excelicon.gif' class='fileimg'/>";
									 }
									 if(this['iconType'] == 'zip'){
										 sName += "<img src='img/file/zipicon.gif' class='fileimg'/>";
									 }
									 if(this['iconType'] == 'pdf'){
										 sName += "<img src='img/file/pdficon.gif' class='fileimg'/>";
									 }
									 if(this['iconType'] == 'xml'){
										 sName += "<img src='img/file/xmlicon.gif' class='fileimg'/>";
									 }
									 if(this['iconType'] == 'img'){
										 sName += "<img src='${communityEraContext.contextUrl}/community/getCommunityMedia.img?id="+rowId+"' style='min-height: 160px;'/>";
									 }
									 if(this['iconType'] == 'doc'){
										 sName += "<img src='img/file/wordicon.gif' class='fileimg'/>";
									 }
									 if(this['iconType'] == 'ppt'){
										 sName += "<img src='img/file/powerpointicon.gif' class='fileimg'/>";
									 }
									 if(this['iconType'] == 'file'){
										 sName += "<img src='img/file/fileicon.gif' class='fileimg'/>";
									 }
									 sName += "<div class='text' ><p class='normalTip' title='"+this['title']+"'>"+this['title']+"</p></div>";
									 sName += "</a></div>";
									 
									 sName += "<div class='footer' style='min-width: 160px;'><div class='box-grid-author-meta'>";
									 if(this['photoPresent']){
										 sName += "<img src='${communityEraContext.contextUrl}/pers/userPhoto.img?id='"+this['posterId']+"' title='"+this['displayName']+"' class='normalTip' style='width: 25px; height: 25px; border-radius: 50%;' />";
									 } else {
										 sName += "<img src='img/user_icon.png' title='"+this['displayName']+"' class='normalTip' style='width: 25px; height: 25px; border-radius: 50%;' />";
									 }
									 sName += "<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id="+this['posterId']+"&backlink=ref' class='memberInfo author-name' ";
									 sName += " title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id="+this['posterId']+"'>"+this['displayName']+"</a>";
									 sName += "<p class='created-on'>"+this['datePostedOn']+"</p>";
									 sName += "</div></div>";
									 sName += "</div>";
									 sName += "</div>";
									 i++;
										}
									);
								 $("#rowSection").append(sName);
								// Hide message
						        $("#waitMessage").hide();
						        normalQtip();
								memberInfoQtip();
							    } ,
			                	complete: function () {
								     var options = {
											    animateThreshold: 100,
											    scrollPollInterval: 20
											}
											$('viewSlide'+scrollCount).AniView(options);
								     scrollCount++;
		                    	},
							 	// What to do before starting
						         beforeSend: function () {
						             $("#waitMessage").show();
						         } 
						    });
				        }
				    }
				}

			// debounce multiple requests
			var _scheduledRequest_main = null;
			function infinite_scroll_debouncer_main(callback_to_run, debounce_time) {
			    debounce_time = typeof debounce_time !== 'undefined' ? debounce_time : 200;
			    if (_scheduledRequest_main) {
			        clearTimeout(_scheduledRequest_main);
			    }
			    _scheduledRequest_main = setTimeout(callback_to_run, debounce_time);
			}
			// usage
			$(window).scroll(function() {
		        infinite_scroll_debouncer_main(infinite_scrolling_main, 400);
		    });
		</script>
		
		<script type="text/javascript">
		// Initial call
		$(document).ready(function(){
			normalQtip();
			memberInfoQtip();
			dynamicDropDownQtip();

			var options = {
				    animateThreshold: 100,
				    scrollPollInterval: 50
				}
				$('.aniview').AniView(options);
			
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
							<li><a href="${communityEraContext.currentCommunityUrl}/library/showLibraryItems.do"><i class="fa fa-folder-open" style="margin-right: 8px;"></i>Library</a></li>
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
						<div class='actions2' id='connectionInfo'>
							<div class='btngroup' >
								<div class='btnout' id='comaction1'><a href="${communityEraContext.currentCommunityUrl}/library/mediaGallery.do" class="normalTip"
										title="Show media gallery">Media Gallery</a></div>
									<div class='btnout' id='comaction2'><a href="${communityEraContext.currentCommunityUrl}/library/mediaGallery.do" class="normalTip"
										title="Show media gallery"><i class="fa fa-image" style="font-size: 14px;"></i></a></div>
								
								<c:if test="${command.currentUserSubscribed}">
									<div id="subscribeDocLib" class='btnout'><a onclick="unSubscribeDocLib(${command.documentLibrary.id});" href="javascript:void(0);" 
									class="normalTip" title="Unsubscribe from email alerts when new files are added">Unfollowing</a></div>
								</c:if>
								<c:if test="${not command.currentUserSubscribed}">
									<div id="subscribeDocLib" class='btnout'><a onclick="subscribeDocLib(${command.documentLibrary.id});" href="javascript:void(0);"  
									class="normalTip" title="Send me email alerts when new files are added to this Library">Follow</a></div>
								</c:if>
								<%--  TODO : feed will be opened later
									<div class='btnout'><a href="${communityEraContext.currentCommunityUrl}/doclib/feed.rss" title="System Administrator tool only - View this library as an RSS feed">RSS feed</a></div>
								
									<div class='btnout'><a href="#" class="ttip" onclick="return false" title="News feed :: The news feed requires a feed reader that supports password protected feeds. "></a></div>	
								--%>
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
										<input type="hidden" id="fTagList" name="fTagList" value="${command.filterTagList}" />
										<input type="hidden" id="toggleList" name="toggleList" value="${command.toggleList}" />
										<input type="hidden" id="pgCount" value="${command.pageCount}" />
										<input type="submit" value="Go"/>
									</form:form>
								</div>
							</div>
							<div class="menus" style="margin-top: 4px;">
								<ul>
									<c:if test="${command.member}">
										<li><a onclick="addFiles(&#39;${command.documentLibrary.id}&#39;);" href="javascript:void(0);" class="normalTip" title="Add a new file to this library">Add Files</a></li>
										<li><a onclick="addFolders(&#39;${command.documentLibrary.id}&#39;);" href="javascript:void(0);" class="normalTip" title="Add a new folder to this library">Add Folders</a></li>
									</c:if>
								</ul>
							</div>
						</div>
						
						<div class="rowLine" id="rowSection">
							<c:forEach items="${command.scrollerPage}" var="row">
								<c:if test="${row.oddRow}">
									<c:set var="trclass" value='Alt' />
								</c:if>
								<c:if test="${row.evenRow}">
									<c:set var="trclass" value='' />
								</c:if>
				      			<div class="aniview reallyslow columns panelBlk${trclass}" av-animation="fadeInUp" style="width: 30.0%;">
				      				<div class="panelBox">
				      					<div class='top-info-bar'>
					      					<div class="entry" id="reqId-${row.id}"><span><i class="fa fa-eye" style="margin-left: 6px;"></i> ${row.downloads}</span></div>
				      					</div>
				      					<div class="image-section" >
				      						<a href="${communityEraContext.currentCommunityUrl}/library/documentdisplay.do?backlink=ref&amp;id=${row.id}" class="normalTip" title="${row.title}">
				      							<c:if test="${row.iconType == 'xls'}">
													<img src='img/file/excelicon.gif' class="fileimg"/>
												</c:if>
												<c:if test="${row.iconType == 'zip'}">
													<img src='img/file/zipicon.gif' class="fileimg"/>
												</c:if>
												<c:if test="${row.iconType == 'pdf'}">
													<img src='img/file/pdficon.gif' class="fileimg"/>
												</c:if>
												<c:if test="${row.iconType == 'xml'}">
													<img src='img/file/xmlicon.gif' class="fileimg"/>
												</c:if>
												<c:if test="${row.iconType == 'img'}">
													<img src='${communityEraContext.contextUrl}/community/getCommunityMedia.img?id=${row.id}' style="min-height: 160px;"/>
												</c:if>
												<c:if test="${row.iconType == 'doc'}">
													<img src='img/file/wordicon.gif' class="fileimg"/>
												</c:if>
												<c:if test="${row.iconType == 'ppt'}">
													<img src='img/file/powerpointicon.gif' class="fileimg"/>
												</c:if>
												<c:if test="${row.iconType == 'file'}">
													<img src='img/file/fileicon.gif' class="fileimg"/>
												</c:if>
												<div class='text' ><p>${row.title}</p></div>
											</a>
				      					</div>
				      					<div class='footer' style="min-width: 160px;">
											<div class='box-grid-author-meta'>
												<c:choose>
													<c:when test="${row.photoPresent}">						 
														 <img src="${communityEraContext.contextUrl}/pers/userPhoto.img?id=${row.posterId}" title="${row.displayName}" class='normalTip' style="width: 25px; height: 25px; border-radius: 50%;" />
													</c:when>
													<c:otherwise>
														<img src="img/user_icon.png" title="${row.displayName}" class='normalTip' style="width: 25px; height: 25px; border-radius: 50%;" />
													</c:otherwise>
												</c:choose>
												<a href='${communityEraContext.contextUrl}/pers/connectionResult.do?id=${row.posterId}&backlink=ref' class='memberInfo author-name' 
													title='${communityEraContext.contextUrl}/pers/connectionInfo.do?id=${row.posterId}'>${row.displayName}</a>
												<p class='created-on'>${row.datePostedOn}</p>
											</div>
										</div>
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
					<%@ include file="/WEB-INF/jspf/sidebarFooter.jspf" %>
				</div>
			</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		<%@ include file="/WEB-INF/jspf/extraFooter.jspf" %>
	</body>
</html>