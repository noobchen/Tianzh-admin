<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="cn">
<head>
    <meta charset="utf-8">
    <title>深圳市天章网络科技有限公司</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="assets/css/login-cas.css" rel="stylesheet">
    <link href="assets/css/login-base.css" rel="stylesheet">
    <style type="text/css">
        .alert {
            width: 233px;
            padding: 8px 35px 8px 14px;
            margin-bottom: 20px;
            text-shadow: 0 1px 0 rgba(255, 255, 255, 0.5);
            background-color: #fcf8e3;
            border: 1px solid #fbeed5;
            -webkit-border-radius: 4px;
            -moz-border-radius: 4px;
            border-radius: 4px;
        }

        .alert-error {
            color: #b94a48;
            background-color: #f2dede;
            border-color: #eed3d7;
        }

        .alert-success {
            color: #468847;
            background-color: #dff0d8;
            border-color: #d6e9c6;
        }

        button.close {
            padding: 0;
            cursor: pointer;
            background: transparent;
            border: 0;
            -webkit-appearance: none;
        }

        .alert .close {
            position: relative;
            top: -2px;
            right: -21px;
            line-height: 20px;
        }

        .close {
            float: right;
            font-size: 20px;
            font-weight: bold;
            line-height: 20px;
            color: #000000;
            text-shadow: 0 1px 0 #ffffff;
            opacity: 0.2;
            filter: alpha(opacity=20);
        }
    </style>

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->


    <script src="assets/js/jquery.js"></script>
    <script src="assets/js/jquery.validate.js"></script>
    <script src="assets/buttonCaptcha/jquery-ui-1.8.16.custom.min.js"></script>
    <script src="assets/buttonCaptcha/jquery.buttonCaptcha.min.js"></script>

    <script type="text/javascript">
        $.validator.setDefaults({
            submitHandler: function () {
                loginForm.submit();
            }
        });

        $().ready(function () {
            // validate login form on keyup and submit
            /*$("#loginForm").validate({
             errorElement: 'span',
             errorClass: 'help-inline',
             highlight: function (element, errorClass) {
             $(element).parent().removeClass('success');
             $(element).parent().addClass('error');
             },
             unhighlight: function (element, errorClass) {
             $(element).parent().removeClass('error');
             $(element).parent().addClass('success');
             },
             rules: {
             account: {
             required: true,
             minlength: 5,
             maxlength: 30
             },
             password: {
             required: true,
             minlength: 5,
             maxlength: 30
             }
             },
             messages: {
             account: {
             required: "请输入账号",

             maxlength: "账号最多包含30个字符"
             },
             password: {
             required: "请输入密码",

             maxlength: "密码最多包含30个字符"
             }
             }
             });*/
        });
    </script>
</head>

<body onload="if (document.getElementById('account')) document.getElementById('account').focus()">

<div class="panel">
    <div style="min-height:100%;">
        <div style="min-height:50px"></div>
        <div class="contentpanel">
            <div class="formpanel">


                <c:if test="${null != result}">
                    <c:if test="${false == result}">
                        <div class="alert alert-error">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                                ${errorMsg}
                        </div>
                    </c:if>
                    <c:if test="${true == result}">
                        <div class="alert alert-success">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                                ${errorMsg}
                        </div>
                    </c:if>
                </c:if>

                <form class="login" method="post" action="login.do" id="loginForm">
                    <p><span class="input-bg"><input name="account" id="account" placeholder="账号" autocomplete="on"
                                                     type="text"></span></p>

                    <p><span class="input-bg"><input name="password" id="password" placeholder="密码"
                                                     type="password"></span></p>

                    <p class="btnpanel">
                        <input value="登&nbsp;录" class="submit" id="login-submit" accesskey="l" type="submit">
                    </p>
                </form>

            </div>
        </div>
    </div>
    <div class="footer">
        <p>
            <span>&copy; 深圳市天章网络科技有限公司 2015</span>
            <a href="#" target="_blank">关于我们</a>|
            <a href="@" target="_blank">联系我们</a>|
            <a href="#" target="_blank">微博</a>
        </p>
    </div>
</div>


<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<%--<script type="text/javascript" src="http://platform.twitter.com/widgets.js"></script>--%>
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
</body>
</html>
