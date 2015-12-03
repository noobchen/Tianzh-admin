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

            $("#openedMobileProvince").html("开通省份"+openedCount+"个;");
            $("#closedMobileProvince").html("屏蔽省份"+closedCount+"个："+closedProvince.substring(0,closedProvince.lastIndexOf("，")));
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

            $("#openedUnicomProvince").html("开通省份"+openedCount+"个;");
            $("#closedUnicomProvince").html("屏蔽省份"+closedCount+"个："+closedProvince.substring(0,closedProvince.lastIndexOf("，")));
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

            $("#openedTelcomProvince").html("开通省份"+openedCount+"个;");
            $("#closedTelcomProvince").html("屏蔽省份"+closedCount+"个："+closedProvince.substring(0,closedProvince.lastIndexOf("，")));
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
                <h4 style="float: left">移动开通省份</h4>
                <input type="button" class="btn btn-info" id="selectMobileAll" value="全 选"
                       style="float: right;margin-right: 19px;margin-top: 5px;"/>
            </div>
        </div>
        <form action="thPayProvinceAdd.do" method="post" id="mobileForm">
            <input type="hidden" value="${thPayId}" name="thPayId"/>
            <input type="hidden" value="0" name="providerId"/>
            <input type="hidden" value="0" name="provinceType"/>
            <table class="table" style="border: 1px solid #B4B4B4; border-radius: 8px" id="mobileListTable"
                    >

                <tbody>
                <tr>
                    <td><label><input type="checkbox" value="1|北京" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'北京')}"> checked="checked" </c:if>>北京</label></td>
                    <td><label><input type="checkbox" value="2|天津" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'天津')}"> checked="checked" </c:if>>天津</label></td>
                    <td><label><input type="checkbox" value="3|重庆" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'重庆')}"> checked="checked" </c:if>>重庆</label></td>
                    <td><label><input type="checkbox" value="4|上海" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'上海')}"> checked="checked" </c:if>>上海</label></td>
                    <td><label><input type="checkbox" value="5|河北" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'河北')}"> checked="checked" </c:if>>河北</label></td>
                    <td><label><input type="checkbox" value="6|山西" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'山西')}"> checked="checked" </c:if>>山西</label></td>
                    <td><label><input type="checkbox" value="7|辽宁" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'辽宁')}"> checked="checked" </c:if>>辽宁</label></td>
                    <td><label><input type="checkbox" value="8|吉林" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'吉林')}"> checked="checked" </c:if>>吉林</label></td>
                    <td><label><input type="checkbox" value="9|黑龙江" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'黑龙江')}"> checked="checked" </c:if>>黑龙江</label></td>
                    <td><label><input type="checkbox" value="10|江苏" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'江苏')}"> checked="checked" </c:if>>江苏</label></td>
                    <td><label><input type="checkbox" value="11|浙江" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'浙江')}"> checked="checked" </c:if>>浙江</label></td>
                    <td><label><input type="checkbox" value="12|安徽" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'安徽')}"> checked="checked" </c:if>>安徽</label></td>
                    <td><label><input type="checkbox" value="13|福建" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'福建')}"> checked="checked" </c:if>>福建</label></td>
                    <td><label><input type="checkbox" value="14|江西" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'江西')}"> checked="checked" </c:if>>江西</label></td>
                    <td><label><input type="checkbox" value="15|山东" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'山东')}"> checked="checked" </c:if>>山东</label></td>
                    <td><label><input type="checkbox" value="16|河南" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'河南')}"> checked="checked" </c:if>>河南</label></td>
                    <td><label><input type="checkbox" value="17|湖北" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'湖北')}"> checked="checked" </c:if>>湖北</label></td>


                </tr>
                <tr>
                    <td><label><input type="checkbox" value="18|湖南" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'湖南')}"> checked="checked" </c:if>>湖南</label></td>
                    <td><label><input type="checkbox" value="19|广东" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'广东')}"> checked="checked" </c:if>>广东</label></td>
                    <td><label><input type="checkbox" value="20|海南" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'海南')}"> checked="checked" </c:if>>海南</label></td>
                    <td><label><input type="checkbox" value="21|四川" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'四川')}"> checked="checked" </c:if>>四川</label></td>
                    <td><label><input type="checkbox" value="22|贵州" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'贵州')}"> checked="checked" </c:if>>贵州</label></td>
                    <td><label><input type="checkbox" value="23|云南" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'云南')}"> checked="checked" </c:if>>云南</label></td>
                    <td><label><input type="checkbox" value="24|陕西" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'陕西')}"> checked="checked" </c:if>>陕西</label></td>
                    <td><label><input type="checkbox" value="25|甘肃" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'甘肃')}"> checked="checked" </c:if>>甘肃</label></td>
                    <td><label><input type="checkbox" value="26|青海" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'青海')}"> checked="checked" </c:if>>青海</label></td>
                    <td><label><input type="checkbox" value="27|台湾" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'台湾')}"> checked="checked" </c:if>>台湾</label></td>
                    <td><label><input type="checkbox" value="28|内蒙古" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'内蒙古')}"> checked="checked" </c:if>>内蒙古</label></td>
                    <td><label><input type="checkbox" value="29|广西" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'广西')}"> checked="checked" </c:if>>广西</label></td>
                    <td><label><input type="checkbox" value="30|宁夏" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'宁夏')}"> checked="checked" </c:if>>宁夏</label></td>
                    <td><label><input type="checkbox" value="31|新疆" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'新疆')}"> checked="checked" </c:if>>新疆</label></td>
                    <td><label><input type="checkbox" value="32|西藏" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'西藏')}"> checked="checked" </c:if>>西藏</label></td>
                    <td><label><input type="checkbox" value="33|香港" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'香港')}"> checked="checked" </c:if>>香港</label></td>
                    <td><label><input type="checkbox" value="34|澳门" name="provinces" <c:if
                            test="${fn:contains(mobileProvinces,'澳门')}"> checked="checked" </c:if>>澳门</label></td>
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
            <h4 style="float: left">联通开通省份</h4>
            <input type="button" class="btn btn-info" id="selectUnicomAll" value="全 选"
                   style="float: right;margin-right: 19px;margin-top: 5px;"/>
        </div>
        <form action="thPayProvinceAdd.do" method="post" id="unicomForm">
            <input type="hidden" value="${thPayId}" name="thPayId"/>
            <input type="hidden" value="1" name="providerId"/>
            <input type="hidden" value="0" name="provinceType"/>
            <table class="table" style="border: 1px solid #B4B4B4; border-radius: 8px" id="unicomListTable"
                    >

                <tbody>
                <tr>
                    <td><label><input type="checkbox" value="1|北京" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'北京')}"> checked="checked" </c:if>>北京</label></td>
                    <td><label><input type="checkbox" value="2|天津" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'天津')}"> checked="checked" </c:if>>天津</label></td>
                    <td><label><input type="checkbox" value="3|重庆" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'重庆')}"> checked="checked" </c:if>>重庆</label></td>
                    <td><label><input type="checkbox" value="4|上海" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'上海')}"> checked="checked" </c:if>>上海</label></td>
                    <td><label><input type="checkbox" value="5|河北" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'河北')}"> checked="checked" </c:if>>河北</label></td>
                    <td><label><input type="checkbox" value="6|山西" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'山西')}"> checked="checked" </c:if>>山西</label></td>
                    <td><label><input type="checkbox" value="7|辽宁" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'辽宁')}"> checked="checked" </c:if>>辽宁</label></td>
                    <td><label><input type="checkbox" value="8|吉林" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'吉林')}"> checked="checked" </c:if>>吉林</label></td>
                    <td><label><input type="checkbox" value="9|黑龙江" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'黑龙江')}"> checked="checked" </c:if>>黑龙江</label></td>
                    <td><label><input type="checkbox" value="10|江苏" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'江苏')}"> checked="checked" </c:if>>江苏</label></td>
                    <td><label><input type="checkbox" value="11|浙江" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'浙江')}"> checked="checked" </c:if>>浙江</label></td>
                    <td><label><input type="checkbox" value="12|安徽" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'安徽')}"> checked="checked" </c:if>>安徽</label></td>
                    <td><label><input type="checkbox" value="13|福建" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'福建')}"> checked="checked" </c:if>>福建</label></td>
                    <td><label><input type="checkbox" value="14|江西" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'江西')}"> checked="checked" </c:if>>江西</label></td>
                    <td><label><input type="checkbox" value="15|山东" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'山东')}"> checked="checked" </c:if>>山东</label></td>
                    <td><label><input type="checkbox" value="16|河南" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'河南')}"> checked="checked" </c:if>>河南</label></td>
                    <td><label><input type="checkbox" value="17|湖北" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'湖北')}"> checked="checked" </c:if>>湖北</label></td>


                </tr>
                <tr>
                    <td><label><input type="checkbox" value="18|湖南" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'湖南')}"> checked="checked" </c:if>>湖南</label></td>
                    <td><label><input type="checkbox" value="19|广东" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'广东')}"> checked="checked" </c:if>>广东</label></td>
                    <td><label><input type="checkbox" value="20|海南" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'海南')}"> checked="checked" </c:if>>海南</label></td>
                    <td><label><input type="checkbox" value="21|四川" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'四川')}"> checked="checked" </c:if>>四川</label></td>
                    <td><label><input type="checkbox" value="22|贵州" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'贵州')}"> checked="checked" </c:if>>贵州</label></td>
                    <td><label><input type="checkbox" value="23|云南" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'云南')}"> checked="checked" </c:if>>云南</label></td>
                    <td><label><input type="checkbox" value="24|陕西" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'陕西')}"> checked="checked" </c:if>>陕西</label></td>
                    <td><label><input type="checkbox" value="25|甘肃" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'甘肃')}"> checked="checked" </c:if>>甘肃</label></td>
                    <td><label><input type="checkbox" value="26|青海" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'青海')}"> checked="checked" </c:if>>青海</label></td>
                    <td><label><input type="checkbox" value="27|台湾" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'台湾')}"> checked="checked" </c:if>>台湾</label></td>
                    <td><label><input type="checkbox" value="28|内蒙古" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'内蒙古')}"> checked="checked" </c:if>>内蒙古</label></td>
                    <td><label><input type="checkbox" value="29|广西" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'广西')}"> checked="checked" </c:if>>广西</label></td>
                    <td><label><input type="checkbox" value="30|宁夏" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'宁夏')}"> checked="checked" </c:if>>宁夏</label></td>
                    <td><label><input type="checkbox" value="31|新疆" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'新疆')}"> checked="checked" </c:if>>新疆</label></td>
                    <td><label><input type="checkbox" value="32|西藏" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'西藏')}"> checked="checked" </c:if>>西藏</label></td>
                    <td><label><input type="checkbox" value="33|香港" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'香港')}"> checked="checked" </c:if>>香港</label></td>
                    <td><label><input type="checkbox" value="34|澳门" name="provinces" <c:if
                            test="${fn:contains(unicomProvinces,'澳门')}"> checked="checked" </c:if>>澳门</label></td>
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
            <h4 style="float: left">电信开通省份</h4>
            <input type="button" class="btn btn-info" id="selectTelcomAll" value="全 选"
                   style="float: right;margin-right: 19px;margin-top: 5px;"/>
        </div>
        <form action="thPayProvinceAdd.do" method="post" id="telcomForm">
            <input type="hidden" value="${thPayId}" name="thPayId"/>
            <input type="hidden" value="2" name="providerId"/>
            <input type="hidden" value="0" name="provinceType"/>
            <table class="table" style="border: 1px solid #B4B4B4; border-radius: 8px" id="telecomListTable"
                    >

                <tbody>
                <tr>
                    <td><label><input type="checkbox" value="1|北京" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'北京')}"> checked="checked" </c:if>>北京</label></td>
                    <td><label><input type="checkbox" value="2|天津" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'天津')}"> checked="checked" </c:if>>天津</label></td>
                    <td><label><input type="checkbox" value="3|重庆" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'重庆')}"> checked="checked" </c:if>>重庆</label></td>
                    <td><label><input type="checkbox" value="4|上海" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'上海')}"> checked="checked" </c:if>>上海</label></td>
                    <td><label><input type="checkbox" value="5|河北" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'河北')}"> checked="checked" </c:if>>河北</label></td>
                    <td><label><input type="checkbox" value="6|山西" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'山西')}"> checked="checked" </c:if>>山西</label></td>
                    <td><label><input type="checkbox" value="7|辽宁" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'辽宁')}"> checked="checked" </c:if>>辽宁</label></td>
                    <td><label><input type="checkbox" value="8|吉林" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'吉林')}"> checked="checked" </c:if>>吉林</label></td>
                    <td><label><input type="checkbox" value="9|黑龙江" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'黑龙江')}"> checked="checked" </c:if>>黑龙江</label></td>
                    <td><label><input type="checkbox" value="10|江苏" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'江苏')}"> checked="checked" </c:if>>江苏</label></td>
                    <td><label><input type="checkbox" value="11|浙江" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'浙江')}"> checked="checked" </c:if>>浙江</label></td>
                    <td><label><input type="checkbox" value="12|安徽" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'安徽')}"> checked="checked" </c:if>>安徽</label></td>
                    <td><label><input type="checkbox" value="13|福建" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'福建')}"> checked="checked" </c:if>>福建</label></td>
                    <td><label><input type="checkbox" value="14|江西" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'江西')}"> checked="checked" </c:if>>江西</label></td>
                    <td><label><input type="checkbox" value="15|山东" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'山东')}"> checked="checked" </c:if>>山东</label></td>
                    <td><label><input type="checkbox" value="16|河南" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'河南')}"> checked="checked" </c:if>>河南</label></td>
                    <td><label><input type="checkbox" value="17|湖北" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'湖北')}"> checked="checked" </c:if>>湖北</label></td>


                </tr>
                <tr>
                    <td><label><input type="checkbox" value="18|湖南" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'湖南')}"> checked="checked" </c:if>>湖南</label></td>
                    <td><label><input type="checkbox" value="19|广东" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'广东')}"> checked="checked" </c:if>>广东</label></td>
                    <td><label><input type="checkbox" value="20|海南" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'海南')}"> checked="checked" </c:if>>海南</label></td>
                    <td><label><input type="checkbox" value="21|四川" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'四川')}"> checked="checked" </c:if>>四川</label></td>
                    <td><label><input type="checkbox" value="22|贵州" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'贵州')}"> checked="checked" </c:if>>贵州</label></td>
                    <td><label><input type="checkbox" value="23|云南" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'云南')}"> checked="checked" </c:if>>云南</label></td>
                    <td><label><input type="checkbox" value="24|陕西" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'陕西')}"> checked="checked" </c:if>>陕西</label></td>
                    <td><label><input type="checkbox" value="25|甘肃" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'甘肃')}"> checked="checked" </c:if>>甘肃</label></td>
                    <td><label><input type="checkbox" value="26|青海" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'青海')}"> checked="checked" </c:if>>青海</label></td>
                    <td><label><input type="checkbox" value="27|台湾" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'台湾')}"> checked="checked" </c:if>>台湾</label></td>
                    <td><label><input type="checkbox" value="28|内蒙古" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'内蒙古')}"> checked="checked" </c:if>>内蒙古</label></td>
                    <td><label><input type="checkbox" value="29|广西" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'广西')}"> checked="checked" </c:if>>广西</label></td>
                    <td><label><input type="checkbox" value="30|宁夏" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'宁夏')}"> checked="checked" </c:if>>宁夏</label></td>
                    <td><label><input type="checkbox" value="31|新疆" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'新疆')}"> checked="checked" </c:if>>新疆</label></td>
                    <td><label><input type="checkbox" value="32|西藏" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'西藏')}"> checked="checked" </c:if>>西藏</label></td>
                    <td><label><input type="checkbox" value="33|香港" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'香港')}"> checked="checked" </c:if>>香港</label></td>
                    <td><label><input type="checkbox" value="34|澳门" name="provinces" <c:if
                            test="${fn:contains(telecomProvinces,'澳门')}"> checked="checked" </c:if>>澳门</label></td>
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

