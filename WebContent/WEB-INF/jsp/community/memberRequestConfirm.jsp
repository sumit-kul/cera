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
		<meta name="description" content="Jhapak, ${communityEraContext.currentCommunity.name}" />
		<meta name="keywords" content="Jhapak, community, join-community, community-membership, joining request" />
		<title>Join A Community: ${communityEraContext.currentCommunity.name} - Jhapak</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>    
		<title>Join A Community - ${communityEraContext.currentCommunity.name}</title>
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style type="">
			.topHeader .heading {
			    width: 600px;
			    color: #184A72;
			    margin-bottom: 2px;
			    background: transparent none repeat scroll 0% 0%;
			    font-family: "Linux Libertine",Georgia,Times,serif;
			}
			
			#container .left-panel p {
				text-align: justify;
				font-size: 20px;
			    font-weight: normal;
			    color: #484F56;
			    line-height: 22px;
			    width: 100%;
			    word-wrap: break-word;
			    margin-left: 140px;
			}
			
			#container .left-panel p a {
				margin-right: 0px;
			}
			
			.fa-sendIcon {
				font-size: 80px;
				float: left;
				margin: 30px;
				color: lightblue;
			}
			
		</style>
		
		<script type="text/javascript">
		function joinRequest(rowId, ctype) { 
		    var ref = '${communityEraContext.contextUrl}/community/joinCommunityRequest.ajx?id='+rowId;
		    var mess = '';
		    var type = BootstrapDialog.TYPE_INFO;
		    var isAuthenticated = ${communityEraContext.userAuthenticated};
	    	mess = '<p class="addTagHeader"><strong>This is a private community.</strong><br/><br/>'+		
			'To request membership click the Go button below.  '+
			'Your request to join the community will be actioned by the community admin.  '+
			'Notification of your acceptance to the community will be sent to your email address.<br/><br />Please write a suitable message'+
			'</p><br/><div id="statusText3"><textarea class="form-control" name="body" id="textareaId">I\'d like to join this community b\'coz... </textarea></div>';
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
	                	label: 'Apply',
	                	cssClass: 'btn-info',
	                	id: 'button-Go',
	                	action: function(dialogRef){
	                	dialogRef.getButton('button-Go').disable();
	                	dialogRef.getButton('button-close').disable();
	                	dialogRef.getButton('button-Go').spin();
	                	var fruit = dialogRef.getModalBody().find('#textareaId').val();
	                	ref = ref + '&optionalComment=' + fruit;
	                	$.ajax({url:ref,success:function(result){
	                		$('#reqId-'+rowId).html(result.returnString);
                        	dialogRef.close();
            	    	  }});
                    	}
	                }]
	            });
		    }
		</script>
	</head>


	<body id="communityEra">
			<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
			<div id="container">
				<div class="left-panel">
					<div class="commSection">
						<div class="paginatedList" style="margin: 0px 0px 10px auto; padding: 0px;">
						<div class="commentBlock" style="height: 414px;">
							<div class="topSection">
								<div class="topHeader" >
									<div class="heading" style="font-family: Arial,Helvetica,sans-serif;">
										<span style="font-size: 20px; font-weight: bold;" id="topTitle">Join: ${command.communityName}</span>
									</div>
								</div>
							 </div>
							 <div style="margin-top: 60px;">
								 <div class="fa-sendIcon "><i class="fa fa-send-o"></i></div>
								 <div style="padding: 30px; text-align: center; font-size: 20px; line-height: 30px;">
									<p style="font-size: 20px; font-weight: bold;">${command.message}</p>
									<br />
									<div style="float: right;">
										<p style="line-height: 26px; margin-right: 100px; font-size: 18px;">
											Click <a href="${communityEraContext.contextUrl}/community/showCommunities.do" class="backarrow">here</a> to return to the Community list</a>.
										</p>
									</div>
								</div>
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