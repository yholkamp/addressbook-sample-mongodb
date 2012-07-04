<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<content tag="title">Contacts</content>
<content tag="tagline">Delete contact</content>
<content tag="breadcrumb">
    <ul class="breadcrumb">
        <li><a href="${ctx}/">Home</a> <span class="divider">/</span></li>
        <li><a href="${ctx}/contacts">Contacts</a> <span class="divider">/</span></li>
        <li class="active">Confirm deletion of <c:out value='${contact.firstName} ${contact.lastName}'/></li>
    </ul>
</content>

<form:form commandName="contact">
    <form:errors path="*" cssClass="errorBox"/>
    <form:hidden path="identifier"/>
    <p>Are you sure?</p>
    <input type="submit" name="submit" value="Delete" class="btn btn-danger"/> <a href="/contacts" class="btn">Cancel</a>
</form:form>