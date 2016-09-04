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
		<meta name="description" content="Jhapak, ${command.community.name}. ${command.community.welcomeText}" />
		<meta name="keywords" content="Jhapak, blog, community, start community, create community, confirm community, ${command.community.keywords}" />
		<title>${command.community.name} - Jhapak</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
				
		<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
		<%@ include file="/WEB-INF/jspf/header2.jspf"%>
		<%@ include file="/WEB-INF/jspf/headerTag.jspf"%>
		
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		
		<style type="">
			#container .left-panel p a.orange {
				color: #FF8800;
			}
			
			.topHeader .heading {
			    width: 600px;
			    color: #184A72;
			    margin-bottom: 2px;
			    background: transparent none repeat scroll 0% 0%;
			    font-family: "Linux Libertine",Georgia,Times,serif;
			}
			
			#container .left-panel p {
				text-align: justify;
			}
			
			#container .left-panel p a {
				margin-right: 0px;
			}
			
			.featureLnk {
				text-decoration: none;
				width: auto;
				display: inline-block;
				white-space: normal;
				border-color: #546E86;
				border-bottom-color: #374757;
				background-color: #546E86;
				border-width: 0px 0px 2px 0px;
				border-style: solid;
				color: #fff;
				padding: 10px;
				font-size: 16px;
				margin-top: 16px;
				margin-bottom: 8px;
				margin-left: 10px;
				margin-right: 10px;
				border-radius: 2px;
				webkit-border-radius: 2px;
				-moz-border-radius: 2px;
				text-align: center;
				vertical-align: middle;
				font-weight: bold;
				cursor: pointer;
			}
			
			.featureLnkMand {
				text-decoration: none;
				width: auto;
				display: inline-block;
				white-space: normal;
				border-color: #546E86;
				border-bottom-color: #374757;
				background-color: #a3b5c5;
				border-width: 0px 0px 2px 0px;
				border-style: solid;
				color: #fff;
				padding: 10px;
				font-size: 16px;
				margin-top: 16px;
				margin-bottom: 8px;
				margin-left: 10px;
				margin-right: 10px;
				border-radius: 2px;
				webkit-border-radius: 2px;
				-moz-border-radius: 2px;
				text-align: center;
				vertical-align: middle;
				font-weight: bold;
				cursor: text;
			}
			
			.featureLnk:hover, #main .featureLnk:focus {
				border-color: #009aab;
				border-bottom-color: #006671;
				background-color: #64829e;
				color: #fff;
			}
			
			#container .left-panel .commSection .userListAlt {
			    font-size: 16px;
			    line-height: 22px;
			    font-weight: 500;
			    border-radius: 3px;
				border: 2px solid #E9EAED;
				background: #fff;
				position: relative;
				overflow: hidden;
				float: left;
				width: 98%;
				margin: 8px;
				display: inline-table;
			}
			
			#container .left-panel .commSection .userListAlt .title {
			    background: #F5F5F5 none repeat scroll 0% 0%;
			    border-bottom: 1px solid #D2D4D7;
			    font-size: 16px;
			    font-weight: 500;
			    line-height: 22px;
			    color: #184A72;
			    overflow: hidden;
			    padding: 6px 11px 7px 14px;
			}
			#container .left-panel .commSection .userListAlt .title p {
				width: 100%;
				color: #184A72;
				font-size: 13px;
				font-weight: normal;
			}
		</style>
		
		<script type="text/javascript">
			function addNewFeature(feature, communityId) {
				$.ajax({url:"${communityEraContext.contextUrl}/communities/addCommunityFeatures.ajx?featureName="+feature+"&communityId="+communityId,success:function(result){
					$("#"+feature+"-2").show();
					$("#"+feature+"-1").hide();
		    	  }});
			}
		</script>
	</head>	

	<c:set var="comlabel" value="Community" />			

	<body id="communityEra">
		<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
		<div id="container">
			<div class="left-panel">
				<div class="commSection">
					<div class="paginatedList" style="margin: 0px 0px 10px auto; padding: 0px;">
						<div class="commentBlock" style="display: inline-block;">
							<div class="topSection">
								<div class="topHeader" >
									<div class="heading" style="font-family: Arial,Helvetica,sans-serif;">
										<span style="font-size: 20px; font-weight: bold;" id="topTitle">Congratulations!</span>
									</div>
								</div>
							 </div>
							 <div style="margin-top: 20px;">
								 <div class="congrates" ></div>
								 <div style="padding: 30px; text-align: center; font-size: 20px; line-height: 30px;">
									<p style="font-size: 20px; font-weight: bold;">you've successfully created a new ${command.community.communityType} community '<a class="orange" href="${communityEraContext.contextUrl}/cid/${command.communityId}/home.do">${command.community.name}</a>'.</p>
									<br />
									<div>
										<p style="line-height: 26px; width: 570px; float: right; font-size: 18px;">
										<%--To assist you in making your community successful read the <a class="orange" href="/faq/showAllFaqs.do#A109358">Guide to online facilitation</a>.<br /> --%>
										To add/update a community logo/banner, please go to <a class="orange" href="${communityEraContext.contextUrl}/cid/${command.communityId}/community/editCommunity.do">Edit Community</a>.
										</p>
										<p style="line-height: 26px; width: 570px; float: right;  font-size: 18px;">
										To manage membership for the community, please go to <a class="orange" href="${communityEraContext.contextUrl}/cid/${command.communityId}/connections/showConnections.do">Connections</a>.
										</p>
									</div>
								</div>
							</div>
						</div>
						
						<div class="userListAlt" >
							<div class="title" >Community Features
							<p>Add more features to the community by clicking them.</p>
							</div>
							<div style="padding: 20px;" >
							<c:forEach items="${command.features}" var="row" varStatus="status">
								<c:choose>
									<c:when test="${row.anabled}">	
										<a class='featureLnkMand' href='javascript:void(0);' onclick='' title='${row.title}'>${row.label}<i class='fa fa-check' style='margin: 0px 4px; font-size: 18px; color: #32cd32;'></i></a>	
									</c:when>
									<c:otherwise>
										<a class='featureLnk' href='javascript:void(0);' onclick='addNewFeature(&#39;${row.name}&#39;, &#39;${command.communityId}&#39;)' 
											title='${row.title}' id='${row.name}-1'>${row.label}</a>
										<a class='featureLnkMand' href='javascript:void(0);' onclick='' title='${row.title}' style='display:none;' id='${row.name}-2'>
											${row.label}<i class='fa fa-check' style='margin: 0px 4px; font-size: 18px; color: #32cd32;' id=''></i></a>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							</div>
						</div>
					</div>
				</div> 	
			</div> 	
			
			<div class="right-panel" style="margin-top: 0px;">
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
						</ul> <br/>
					</div>
				</div>

			</div> 
			<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>