<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

	<table>
		<thead>
			<tr>
			<th><spring:message code="label.contact.name"/></th>
			<th><spring:message code="label.contact.email"/></th>
			<th><spring:message code="label.contact.telephone"/></th>
			<th>&nbsp;</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="a" items="${contactList}">
			<tr>
			<td><c:out value="${a.firstName}" /> <c:out value="${a.lastName}" /></td>
			<td><c:out value="${a.email}" /></td>
			<td><c:out value="${a.telephone}" /></td>
			<td class="action-pannel"><a href="javascript:void(0)" onclick="removeContact(<c:out value="${a.id}" />);"><spring:message code="label.contact.delete"/></a></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>