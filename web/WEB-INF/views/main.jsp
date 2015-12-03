<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>深圳天章网络科技</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="assets/css/bootstrap.css" rel="stylesheet">
    <link href="assets/css/bootstrap-responsive.css" rel="stylesheet">
    <link href="assets/css/docs.css" rel="stylesheet">
    <link href="assets/js/google-code-prettify/prettify.css" rel="stylesheet">
    <link href="assets/css/datepicker.css" rel="stylesheet">
    <link href="assets/css/accumulated-analysis.css" rel="stylesheet">

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" href="assets/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="assets/ico/apple-touch-icon-57-precomposed.png">

    <script src="assets/js/jquery.js"></script>
    <script src="assets/js/jquery.validate.js"></script>
</head>

<body data-spy="scroll" data-target=".bs-docs-sidebar">

<!-- Navbar
================================================== -->
<tiles:insertAttribute name="navbar"/>

<div class="container">

    <!-- Docs nav
   ================================================== -->
    <div class="row">
        <div class="span12">


            <!-- Content
           ================================================== -->
            <section id="download-bootstrap">
                <tiles:insertAttribute name="breadcrumb"/>
                <c:if test="${null == tilesFrameFlag or false == tilesFrameFlag}">
                    <tiles:insertAttribute name="content"/>
                </c:if>

                <c:if test="${true == tilesFrameFlag}">
                    <iframe id="myFrame" src="<tiles:getAsString name= "body"/>" width="100%" height="800px"
                            scrolling="yes" marginwidth="0" marginheight="0" frameborder="0"></iframe>
                </c:if>
            </section>
        </div>
    </div>
</div>


<!-- Footer
================================================== -->
<%--<tiles:insertAttribute name="footer"/>--%>


<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="assets/js/google-code-prettify/prettify.js"></script>
<script src="assets/js/bootstrap-transition.js"></script>
<script src="assets/js/bootstrap-alert.js"></script>
<script src="assets/js/bootstrap-modal.js"></script>
<script src="assets/js/bootstrap-dropdown.js"></script>
<script src="assets/js/bootstrap-scrollspy.js"></script>
<script src="assets/js/bootstrap-tab.js"></script>
<script src="assets/js/bootstrap-tooltip.js"></script>
<script src="assets/js/bootstrap-popover.js"></script>
<script src="assets/js/bootstrap-button.js"></script>
<script src="assets/js/bootstrap-collapse.js"></script>
<script src="assets/js/bootstrap-carousel.js"></script>
<script src="assets/js/bootstrap-typeahead.js"></script>
<script src="assets/js/bootstrap-affix.js"></script>
<script src="assets/js/application.js"></script>
<script src="assets/js/jquery.placeholder.js"></script>
<script src="assets/js/bootstrap-datepicker.js"></script>
<script src="assets/js/highcharts.js"></script>
<script src="assets/js/jquery.freezeheader.js"></script>
<%--<script>--%>
    <%--$('input[placeholder], textarea[placeholder]').placeholder();--%>
<%--</script>--%>
</body>
</html>
