<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="brand" href="#"><font color="white">深圳天章网络科技</font></a>

            <div class="nav-collapse collapse">
                <ul class="nav">
                    <c:if test="${null != module}">
                        <c:forEach var="item" items="${module}" step="1">
                            <li class="">
                                <a href="${item.url}">${item.resourceName}</a>
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