<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page language="java" contentType="application/vnd.ms-excel" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="cera" uri="/WEB-INF/tlds/cera.tld" %> 

<h3>Users in community '${command.commName}'</h3>
<h4>Generated: <fmt:formatDate pattern="dd MMM yyyy" value="<%= new java.util.Date() %>" /></h4>

<table>
<thead>
	<tr>
	<th>Member Name</th>
	<th>Email address</th>
	<th>Job Title</th>
	<th>Local Authority</th>
	<th>Organisation</th>
	<th>Role</th>
	<th>Join Date</th>
	<th>Last Visit Date</th>
	</tr>
</thead>

<tbody>

<c:forEach items="${command.scrollerPage}" var="row">
	<tr>
	<td>${row.fullName}</td>
	<td>${row.emailAddress}</td>
	<td>${row.jobTitle}</td>
	<td>${row.organisation}</td>
	<td>${row.role}</td>
	<td><fmt:formatDate pattern="dd MMM yyyy" value="${row.dateJoined}" /></td>
	<td>${row.lastVisitDate}	</td>
	</tr>
</c:forEach>

</tbody>

</table>