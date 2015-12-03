<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="span2 bs-docs-sidebar">
    <ul class="nav nav-list bs-docs-sidenav">
        <c:if test="${null != menu}">
            <c:forEach var="item" items="${menu}" step="1">
                <c:if test="${null == item.parentResource.baseUrl or item.parentResource.baseUrl ==''}">
                    <li><a href="${item.url}"><i class="icon-chevron-right"></i>${item.resourceName}</a></li>
                </c:if>
                <c:if test="${null != item.parentResource.baseUrl and item.parentResource.baseUrl !=''}">
                    <li><a href="${'request.do?rurl='}${item.encryptionUrlString}"><i class="icon-chevron-right"></i>${item.resourceName}</a></li>
                </c:if>
            </c:forEach>
        </c:if>

    </ul>
</div>