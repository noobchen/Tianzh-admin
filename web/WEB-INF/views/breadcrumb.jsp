<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${null != moduleName}">
    <ul class="breadcrumb">
        <li><a href="#">${moduleName.resourceName}</a> <span class="divider">/</span></li>
        <c:if test="${null != menuName}">
            <li class="active">${menuName.resourceName}</li>
        </c:if>
    </ul>
</c:if>
