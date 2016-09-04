<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="form" uri="/WEB-INF/tlds/cera_form.tld"%>
<%@ taglib prefix="cera" uri="/WEB-INF/tlds/cera.tld"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="robots" content="noindex, nofollow" />
		<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
		<link rel="canonical" href="${communityEraContext.requestUrl}" />
		<%@ include file="/WEB-INF/jspf/header2.jspf" %>
		<title>Community Cloud</title>
		<link rel="icon" type="image/png" href="img/link_Image.png"/>
		<meta name="author" content="Jhapak">
		<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
		</head>
		
		<body id="all-communities">
				<%@ include file="/WEB-INF/jspf/open-container.jspf" %>
				<%@ include file="/WEB-INF/jspf/topnav.jspf" %>
				<%@ include file="/WEB-INF/jspf/sectionnav.jspf" %>
		
		<div class="padding">
					
				<div id="main">
					<%@ include file="/WEB-INF/jspf/community-breadcrumb.jspf" %>
					<div id="columnmain" >
		
		<div class="intro">
			
						<div class="lightgreenbox">
											
						<h2><strong>Community Cloud</strong></h2>
										
		<p>The tag cloud is a stylized way of visually representing occurrences of tags used within the communities. The most popular topics are highlighted in a larger, bolder font. 
		The tag index below provides additional information for each tag.</p>
					</div>
					
		
				</div>
		
		
										<div id="cloud" >
											
											<ul>	
											
											<c:forEach items="${command.popularTags}" var="tag" varStatus="status" >
												<li>									
												<c:url var="tagUrl" value="${communityEraContext.contextUrl}/comm/for-tag.do" >
		    							 	    <c:param name="selectedTag" value="${tag.value.tagText}" />
		    									</c:url>
														<a href="${tagUrl}"
													 		title="${tag.value.count} items tagged with '${tag.value.tagText}' " 
													 		class="size-${tag.value.cloudSet+1}">${tag.value.tagText} </a>
												</li>										
												</c:forEach>			
		
											</ul>
		
										</div>
										
		
										
										<div id="themes">
											
											<h2>Theme index</h2>
											
											<form id="form-filter" action="#">
												<label class="hidden" for="sel-filter">Filter by date</label>
												
												<input type="image" class="btnmain" src="img/b/go-orange.png" width="40" height="23" alt="Go" />
												
												<select id="sel-filter">
													<option>Filter by date</option>
												</select>
												
												
												
											</form>
											
										</div>
		
		
		
					</div>
						
				<div id="columnright">
				<div class="padding">	
					<%@ include file="/WEB-INF/jspf/font-size-switcher.jspf" %>
					<%@ include file="/WEB-INF/jspf/commsearch.jspf" %>	
									</div>
									
								</div>
		
		
				</div> 
		
				
		
				<div id="footer"  class="common">
		
					
				</div>  
				</div>
						
				</div>
					
		</div>
		</div>
		
		</div> 


</body>
</html>



		
