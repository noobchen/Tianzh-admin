<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <%--<button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">--%>
            <%--<span class="icon-bar"></span>--%>
            <%--<span class="icon-bar"></span>--%>
            <%--<span class="icon-bar"></span>--%>
            <%--</button>--%>
            <a class="brand" href="#"><font color="white">深圳天章网络科技</font></a>

            <div class="nav-collapse collapse">
                <ul class="nav navbar-nav">
                    <c:if test="${null != module}">
                        <c:forEach var="item" items="${module}" step="1">
                            <li class="dropdown">
                                <a href="${item.url}" data-toggle="dropdown"
                                   class="dropdown-toggle">${item.resourceName}<span class="caret"></span></a>
                                <c:if test="${null != item.subMenus}">
                                    <ul class="dropdown-menu">
                                        <c:forEach var="subItem" items="${item.subMenus}" step="1">
                                            <c:if test="${null == subItem.parentResource.baseUrl or subItem.parentResource.baseUrl ==''}">
                                                <li><a href="${subItem.url}">${subItem.resourceName}</a></li>
                                            </c:if>
                                            <c:if test="${null != subItem.parentResource.baseUrl and subItem.parentResource.baseUrl !=''}">
                                                <li>
                                                    <a href="${'request.do?rurl='}${subItem.encryptionUrlString}">${subItem.resourceName}</a>
                                                </li>
                                            </c:if>
                                        </c:forEach>
                                    </ul>
                                </c:if>
                            </li>
                        </c:forEach>
                    </c:if>
                    <li class="">
                        <a class="link" href="logout.do">[退出]</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>