<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="cn">
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
    <link href="assets/buttonCaptcha/jquery.buttonCaptcha.styles.css" rel="stylesheet"/>
    <link href="assets/buttonCaptcha/jquery-ui-1.8.16.custom.css" rel="stylesheet"/>

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

<body data-spy="scroll" data-target=".bs-docs-sidebar">

<!-- Navbar
    ================================================== -->
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
                </ul>
            </div>
        </div>
    </div>
</div>


<div class="jumbotron masthead">
    <div class="container">
        <section id="misc">
            <div class="row-fluid">
                <div class="span3">
                    &nbsp;
                </div>
                <div class="span4">

                    <form class="form-horizontal" action="login.do" method="post" id="loginForm">
                        <div class="control-group">
                            <label class="control-label">&nbsp;</label>

                            <div class="controls">
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
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="account">账号</label>

                            <div class="controls">
                                <input type="text" id="account" name="account" placeholder="请输入账号">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="password">密码</label>

                            <div class="controls">
                                <input type="password" id="password" name="password" placeholder="请输入密码">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">
                                &nbsp;
                            </label>

                            <div class="controls">
                                <button id="demo" type="submit" class="btn"><font color="black">登 陆</font></button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </section>
    </div>
</div>

<script type="text/javascript">
//    $(document).ready(function () {
//        var isMobile = {
//            Android: function () {
//                return navigator.userAgent.match(/Android/i) ? true : false;
//            },
//            BlackBerry: function () {
//                return navigator.userAgent.match(/BlackBerry/i) ? true : false;
//            },
//            iOS: function () {
//                return navigator.userAgent.match(/iPhone|iPad|iPod/i) ? true : false;
//            },
//            Windows: function () {
//                return navigator.userAgent.match(/IEMobile/i) ? true : false;
//            },
//            any: function () {
//                return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Windows());
//            }
//        };
//        if (!isMobile.any()) {
//            $('#demo').buttonCaptcha({
//                codeWord: 3,
//                codeZone: false,
//                captchaHeader: '验证码?',
//                captchaTip: '拖动字母组成"%code_word%".',
//                verifyInput: true,
//                //verifyName:'hidden_field_my_site',
//                verifyMustBe: true,
//                //verifyMustName:'hidden_field_real_my_site',
//                captchaUnlocked: "解锁成功!",
//                hideCaptcha: true,
//                hideButton: true
//            });
//        }
//    });
</script>


<!-- Footer
================================================== -->
<footer class="footer">
    <div class="container">
        <%--<p class="pull-right"><a href="#">返回顶部</a></p>--%>

        <p style="text-align: center">&copy; 深圳市天章网络科技有限公司 2015</p>

        <ul style="margin: 10px 0;text-align:center;">
            <li style="display:inline;padding:0;"><a href="#" target="_blank">官方网站</a></li>
            <li style="display:inline;padding:0;"><a href="#" target="_blank">官方微博</a></li>
            <li style="display:inline;padding:0;"><a href="#" target="_blank">联系我们</a>
            </li>
        </ul>
    </div>
</footer>


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
