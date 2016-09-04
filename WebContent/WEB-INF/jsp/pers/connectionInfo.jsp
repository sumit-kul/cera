<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:choose>

	<c:when test="${empty communityEraContext.currentUser || command.user.id == communityEraContext.currentUser.id}">
		<div class="tipGroup">
			<div class="tipImg">
				<c:choose>
					<c:when test="${command.user.photoPresent}">
						<c:url value="${communityEraContext.contextUrl}/pers/userPhoto.img?showType=m" var="photoUrl">
							<c:param name="id" value="${command.user.id}" />
						</c:url>
						<img src="${photoUrl}"  width='120' height='120' style=""/> 
					</c:when>
					<c:otherwise>
						<img src='img/user_icon.png'  width='120' height='120'/>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="tipInfo">
				<a href="${communityEraContext.contextUrl}/pers/connectionResult.do?id=${command.user.id}" >${command.user.fullName}</a>
				<p style="margin-top: 5px; font-size: 12px;">
				<strong>Member since:</strong>&nbsp;${command.registeredOn}
				</p>
				<%--<c:if test="${not empty command.user.mobileNumber}">
					<p style="font-size: 12px;">
					<strong>Mobile:</strong>&nbsp;${command.user.mobileNumber}
					</p>
				</c:if>
				<c:if test="${not empty command.address}">
					<p style="font-size: 12px;">
					<strong>Location:</strong>&nbsp;${command.address}
					</p>
				</c:if> --%>
			</div>
		</div> 
	</c:when>
	<c:otherwise>
		<div class="tipGroup2">
			<div class="tipImg">
				<c:choose>
					<c:when test="${command.user.photoPresent}">
						<c:url value="${communityEraContext.contextUrl}/pers/userPhoto.img?showType=m" var="photoUrl">
							<c:param name="id" value="${command.user.id}" />
						</c:url>
						<img src="${photoUrl}"  width='120' height='120' style=""/> 
					</c:when>
					<c:otherwise>
						<img src='img/user_icon.png'  width='120' height='120'/>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="tipInfo">
				<a href="${communityEraContext.contextUrl}/pers/connectionResult.do?id=${command.user.id}" >${command.user.fullName}</a>
				<%-- <c:if test="${command.user.mobileNumber > 0}">
					<p style="font-size: 12px;">
						<strong>Mobile:</strong>&nbsp;${command.user.mobileNumber}
					</p>
				</c:if>
				<c:if test="${not empty command.address}">
					<p style="font-size: 12px;">
					<strong>Location:</strong>&nbsp;${command.address}
					</p>
				</c:if> --%>
				<c:if test="${not empty command.connectionDate}">
					<p style="font-size: 12px;">
					<strong>Connected since:</strong>&nbsp;${command.connectionDate}
					</p>
				</c:if>
			</div>
		</div>
		<div class="tipFooter">
			<div class='detailsConnection'>
				<div class='actions' id='connectionInfo-${command.id}'>${command.returnString}</div>
			</div>
			<%--<span id='connectionInfo-${command.user.id}'>
				${command.connectionInfo}
			</span> --%>
		</div>
	</c:otherwise>
</c:choose>