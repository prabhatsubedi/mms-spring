<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:include page="header.jsp"/>

<div id='contact-form'>
	<h3><spring:message code="label.contact.contactManager"/></h3>
	<label><spring:message code="label.contact.firstName"/></label><input type="text" id="firstName" name="firstName" />
	<label><spring:message code="label.contact.lastName"/></label><input type="text" id="lastName" name="lastName" />
	<label><spring:message code="label.contact.email"/></label><input type="text" id="email" name="email" />
	<label><spring:message code="label.contact.telephone"/></label><input type="text" id="telephone" name="telephone" />
	<input type="button" value="<spring:message code="label.contact.addContact"/>" id="addContact" />
</div>

<h3><spring:message code="label.contact.contacts"/></h3>
<div id='contact-grid-view'>
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
</div>
<script src="../js/jquery-1.8.2.js"></script>
<script type="text/javascript">
	// add contact
	$(document).ready(function(){
		$("#addContact").click(function(){
			//get the form data using another method
	        var firstName = $.trim($("input#firstName").val());
	        var lastName = $.trim($("input#lastName").val());
	        var email = $.trim($("input#email").val());
	        var telephone = $.trim($("input#telephone").val());
	         
	       /// alert("go"+ uid + groupID);
	        var data = "json={'firstName':'" + firstName +"','lastName':'" + lastName +"','email':'" + email +"','telephone':'" + telephone+"'}";
	        
	     	// setup some local variables
	     	$('#contact-form input[type="text"]').val('');
	        var $inputs = $('#contact-form input, #contact-form button');
	
	        // let's disable the inputs for the duration of the ajax request        
	        $inputs.prop("disabled", true);
	       
			$.post("/MySkillTest/web/proxy/contact/add", data, function(resp){
				if (resp.result != undefined && resp.result == 1){
					reflashGridView();
				}
				$inputs.prop("disabled", false);
			}, 'json');	
		});	
	});	
	
	// get contact
	function reflashGridView(){
		$.post("/MySkillTest/web/proxy/contact/get", function(resp){
			$('#contact-grid-view').html(resp);
		});		
	}
	
	// delete contact
	function removeContact(id){
		var data = "json={'id':"+id+"}";
		$.post("/MySkillTest/web/proxy/contact/delete", data, function(resp) {
			if (resp.result != undefined && resp.result == 1){
				reflashGridView();
			}
		}, 'json');
	}
</script>
<jsp:include page="footer.jsp"/>