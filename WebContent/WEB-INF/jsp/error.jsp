<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta name="robots" content="noindex,nofollow" />
	<meta name="author" content="Jhapak">
	<meta name="google-site-verification" content="JC96M8paj34ChCcPK0EAKWSzDJqocRo6rdSqnzPbIm4" />
	<meta name="robots" content="noindex,nofollow" /><!--default_cached-->
	<%@ include file="/WEB-INF/jspf/commHeader.jspf" %>
	<title>Jhapak - Error</title>
	<link rel="icon" type="image/png" href="img/link_Image.png"/>
	<link rel="stylesheet" type="text/css" href="css/allMedia.css" />
</head>

	<body id="communityEra">
		<div class="mainBody">
			<%@ include file="/WEB-INF/jspf/topHeader.jspf" %>
	
			<div class="containerMain">
				<div class="leftPannel">
					<div class="commActionBlock" style="margin: 6px 0px 6px 0px;">
			
						<c:if test="${exception.class.simpleName == 'ElementNotFoundException'}">			
							<h1>Item not found</h1>
							<br />
							<p>
							The item targeted by the link you selected could not be found.<br/><br/>  
							This could be caused by the item being deleted, or by an invalid link.<br/><br/>
							If you think you are seeing this message in error please use the <a href="${communityEraContext.contextUrl}/feedback.do">feedback section</a>
							to report the problem.
							</p>
							<br />
							<br />
							<br />
							<br />
							<br />
							<br />
							<br />
							<br />
						</c:if>
						
						<c:if test="${exception.class.simpleName == 'AuthorizationFailedException'}">
						
						<h1 style="padding: 50px 200px 20px;">Authorization failed!!!</h1>
							<br />
							<p class="errorText">
								${exception.message}  
							</p>
							<br />
							<br />
							<br />
						</c:if>
						

						<c:if test="${exception.class.simpleName != 'ElementNotFoundException' && exception.class.simpleName != 'AuthorizationFailedException'}">			
							<h1>Operation could not be completed</h1>
							<br />
							<p>
								<c:out  value="${exception.message}"></c:out>
							</p>
							<br />
							<br />
							<br />
							<br />
							<br />
							<br />
							<br />
							<br />
						</c:if>
					</div>
				</div><!-- /#leftPannel -->
				
				<div class="rightOuterPannel" style="margin-top: 0px;">
				</div> 
				<%-- tag-cloud will be adjusted in last 	
				<div class="padding">	
				<%@ include file="/WEB-INF/jspf/tag-cloud.jspf" %>
				</div> --%>
			</div> 
			<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		</div> 
	</body>
</html>