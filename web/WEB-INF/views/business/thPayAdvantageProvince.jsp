<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript">
    <%-- 控制表单提交--%>
    $.validator.setDefaults({
        submitHandler: function (form) {
            if (form.id == 'mobileForm') {
                mobileForm.submit();
            }

            if (form.id == 'unicomForm') {
                unicomForm.submit();
            }

            if (form.id == 'telcomForm') {
                telcomForm.submit();
            }

        }
    });

    //Js逻辑处理
    $().ready(function () {
        $('#selectMobileAll').click(function () {
            var inputs = $("#mobileForm  :checkbox");

            inputs.each(function () {
                $(this).attr("checked", "checked");
            });

        });

        $('#selectUnicomAll').click(function () {
            var inputs = $("#unicomForm  :checkbox");

            inputs.each(function () {
                $(this).attr("checked", "checked");
            });

        });

        $('#selectTelcomAll').click(function () {
            var inputs = $("#telcomForm  :checkbox");

            inputs.each(function () {
                $(this).attr("checked", "checked");
            });

        });

        mobileProvince();
        unicomProvince();
        telcomProvince();

        function mobileProvince() {
            var inputs = $("#mobileForm  :checkbox");

            var openedCount = 0;
            var closedCount = 0;
            var closedProvince = "";
            inputs.each(function () {
                if ($(this).attr("checked") != "checked") {
                    closedCount = closedCount + 1;
                    var value = $(this).attr("value");

                    closedProvince = closedProvince + value.split("|")[1] + "，";
                }
                else {
                    openedCount = openedCount + 1;
                }
            });

            $("#openedMobileProvince").html("优势省份" + openedCount + "个;");
            $("#closedMobileProvince").html("一般省份" + closedCount + "个：" + closedProvince.substring(0, closedProvince.lastIndexOf("，")));
        };

        function unicomProvince() {
            var inputs = $("#unicomForm  :checkbox");

            var openedCount = 0;
            var closedCount = 0;
            var closedProvince = "";
            inputs.each(function () {
                if ($(this).attr("checked") != "checked") {
                    closedCount = closedCount + 1;
                    var value = $(this).attr("value");

                    closedProvince = closedProvince + value.split("|")[1] + "，";
                }
                else {
                    openedCount = openedCount + 1;
                }
            });

            $("#openedUnicomProvince").html("优势省份" + openedCount + "个;");
            $("#closedUnicomProvince").html("一般省份" + closedCount + "个：" + closedProvince.substring(0, closedProvince.lastIndexOf("，")));
        };

        function telcomProvince() {
            var inputs = $("#telcomForm  :checkbox");

            var openedCount = 0;
            var closedCount = 0;
            var closedProvince = "";
            inputs.each(function () {
                if ($(this).attr("checked") != "checked") {
                    closedCount = closedCount + 1;
                    var value = $(this).attr("value");

                    closedProvince = closedProvince + value.split("|")[1] + "，";
                }
                else {
                    openedCount = openedCount + 1;
                }
            });

            $("#openedTelcomProvince").html("优势省份" + openedCount + "个;");
            $("#closedTelcomProvince").html("一般省份" + closedCount + "个：" + closedProvince.substring(0, closedProvince.lastIndexOf("，")));
        };
    })
</script>

<div style="border: 1px solid #B4B4B4; border-radius: 8px;  padding: 10px">
    <%-- 内容--%>
    <div class="row-fluid"
            >
        <!--提示语-->
        <c:if test="${null != param.mobileresult}">
            <c:if test="${false == param.mobileresult}">
                <div class="alert alert-error">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                        ${param.mobileerrorMsg}
                </div>
            </c:if>
            <c:if test="${true == param.mobileresult}">
                <div class="alert alert-success">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                        ${param.mobileerrorMsg}
                </div>
            </c:if>
        </c:if>
        <div class="mod-header radius">
            <div>
                <h4 style="float: left">移动优势省份</h4>
                <input type="button" class="btn btn-info" id="selectMobileAll" value="全 选"
                       style="float: right;margin-right: 19px;margin-top: 5px;"/>
            </div>
        </div>
        <form action="thPayProvinceAdd.do" method="post" id="mobileForm">
            <input type="hidden" value="${thPayId}" name="thPayId"/>
            <input type="hidden" value="0" name="providerId"/>
            <input type="hidden" value="1" name="provinceType"/>
            <table class="table" style="border: 1px solid #B4B4B4; border-radius: 8px" id="mobileListTable"
                    >

                <tbody>
                <tr>
                    <c:forEach var="item" items="${mobileProvinces}" step="1" begin="0" end="16" varStatus="index">
                        <td>
                            <label><input type="checkbox" value="${item.provinceId}|${item.province}" name="provinces"
                            <c:if
                                    test="${fn:contains(advantageMobileProvinces,item.province)}">
                                          checked="checked" </c:if>>${item.province}</label>
                        </td>
                    </c:forEach>
                </tr>
                <tr>
                    <c:forEach var="item" items="${mobileProvinces}" step="1" begin="17" varStatus="index">
                        <td>
                            <label><input type="checkbox" value="${item.provinceId}|${item.province}" name="provinces"
                            <c:if
                                    test="${fn:contains(advantageMobileProvinces,item.province)}">
                                          checked="checked" </c:if>>${item.province}</label>
                        </td>
                    </c:forEach>
                </tr>

                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td>
                        <input type="submit" class="btn btn-primary"
                               value="提 交"/></td>
                </tr>
                </tbody>
            </table>
        </form>
        <div style="margin-left: 10px;margin-top: -30px">
            <h5 id="openedMobileProvince"></h5>
            <h5 id="closedMobileProvince"></h5>
        </div>
    </div>
</div>

<div style="border: 1px solid #B4B4B4; border-radius: 8px;  padding: 10px ;margin-top: 25px">
    <%-- 内容--%>
    <div class="row-fluid"
            >
        <!--提示语-->
        <c:if test="${null != param.unicomresult}">
            <c:if test="${false == param.unicomresult}">
                <div class="alert alert-error">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                        ${param.unicomerrorMsg}
                </div>
            </c:if>
            <c:if test="${true == param.unicomresult}">
                <div class="alert alert-success">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                        ${param.unicomerrorMsg}
                </div>
            </c:if>
        </c:if>
        <div class="mod-header radius">
            <h4 style="float: left">联通优势省份</h4>
            <input type="button" class="btn btn-info" id="selectUnicomAll" value="全 选"
                   style="float: right;margin-right: 19px;margin-top: 5px;"/>
        </div>
        <form action="thPayProvinceAdd.do" method="post" id="unicomForm">
            <input type="hidden" value="${thPayId}" name="thPayId"/>
            <input type="hidden" value="1" name="providerId"/>
            <input type="hidden" value="1" name="provinceType"/>
            <table class="table" style="border: 1px solid #B4B4B4; border-radius: 8px" id="unicomListTable"
                    >

                <tbody>
                <tr>
                    <c:forEach var="item" items="${unicomProvinces}" step="1" begin="0" end="16" varStatus="index">
                        <td>
                            <label><input type="checkbox" value="${item.provinceId}|${item.province}" name="provinces"
                            <c:if
                                    test="${fn:contains(advantageUnicomProvinces,item.province)}">
                                          checked="checked" </c:if>>${item.province}</label>
                        </td>
                    </c:forEach>
                </tr>
                <tr>
                    <c:forEach var="item" items="${unicomProvinces}" step="1" begin="17" varStatus="index">
                        <td>
                            <label><input type="checkbox" value="${item.provinceId}|${item.province}" name="provinces"
                            <c:if
                                    test="${fn:contains(advantageUnicomProvinces,item.province)}">
                                          checked="checked" </c:if>>${item.province}</label>
                        </td>
                    </c:forEach>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td>
                        <input type="submit" class="btn btn-primary"
                               value="提 交"/></td>
                </tr>
                </tbody>
            </table>
        </form>
        <div style="margin-left: 10px;margin-top: -30px">
            <h5 id="openedUnicomProvince"></h5>
            <h5 id="closedUnicomProvince"></h5>
        </div>
    </div>
</div>


<div style="border: 1px solid #B4B4B4; border-radius: 8px;  padding: 10px ;margin-top: 25px;margin-bottom: 50px">
    <%-- 内容--%>
    <div class="row-fluid"
            >
        <!--提示语-->
        <c:if test="${null != param.telecomresult}">
            <c:if test="${false == param.telecomresult}">
                <div class="alert alert-error">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                        ${param.telecomerrorMsg}
                </div>
            </c:if>
            <c:if test="${true == param.telecomresult}">
                <div class="alert alert-success">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                        ${param.telecomerrorMsg}
                </div>
            </c:if>
        </c:if>
        <div class="mod-header radius">
            <h4 style="float: left">电信优势省份</h4>
            <input type="button" class="btn btn-info" id="selectTelcomAll" value="全 选"
                   style="float: right;margin-right: 19px;margin-top: 5px;"/>
        </div>
        <form action="thPayProvinceAdd.do" method="post" id="telcomForm">
            <input type="hidden" value="${thPayId}" name="thPayId"/>
            <input type="hidden" value="2" name="providerId"/>
            <input type="hidden" value="1" name="provinceType"/>
            <table class="table" style="border: 1px solid #B4B4B4; border-radius: 8px" id="telecomListTable"
                    >

                <tbody>
                <tr>
                    <c:forEach var="item" items="${telecomProvinces}" step="1" begin="0" end="16" varStatus="index">
                        <td>
                            <label><input type="checkbox" value="${item.provinceId}|${item.province}" name="provinces"
                            <c:if
                                    test="${fn:contains(advantageTelecomProvinces,item.province)}">
                                          checked="checked" </c:if>>${item.province}</label>
                        </td>
                    </c:forEach>
                </tr>
                <tr>
                    <c:forEach var="item" items="${telecomProvinces}" step="1" begin="17" varStatus="index">
                        <td>
                            <label><input type="checkbox" value="${item.provinceId}|${item.province}" name="provinces"
                            <c:if
                                    test="${fn:contains(advantageTelecomProvinces,item.province)}">
                                          checked="checked" </c:if>>${item.province}</label>
                        </td>
                    </c:forEach>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td>
                        <input type="submit" class="btn btn-primary"
                               value="提 交"/></td>
                </tr>
                </tbody>
            </table>
        </form>
        <div style="margin-left: 10px;margin-top: -30px">
            <h5 id="openedTelcomProvince"></h5>
            <h5 id="closedTelcomProvince"></h5>
        </div>
    </div>
</div>

