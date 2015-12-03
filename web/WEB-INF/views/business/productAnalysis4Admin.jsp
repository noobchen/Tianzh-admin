<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
    <%-- 控制表单提交--%>
    $.validator.setDefaults({
        submitHandler: function (form) {
            if (form.id == 'queryForm') {
                queryForm.submit();
            }
            else if (form.id == 'addForm') {
                addForm.submit();
            }
            else if (form.id == 'editForm') {
                editForm.submit();
            }
            else {
                deleteForm.submit();
            }
        }
    });

    //Js逻辑处理
    $().ready(function () {
        $("#listTable").find("tr").hover(function () {
            $("#editId").val($(this).children("td").eq(0).text());
            $("#editProductName").val($(this).children("td").eq(1).text());
            $("#deleteName").val($(this).children("td").eq(1).text());
            $("#deleteId").val($(this).children("td").eq(0).text());
        });

        function chargeOff(btn) {
            var newUsers = btn.parent().parent().children().eq(7).html();
            var orderAmounts = parseInt(btn.parent().parent().children().eq(11).html());

            var button = btn;

            var userDiscount = btn.parent().parent().children().eq(15).children().val();
            var amountsDiscount = btn.parent().parent().children().eq(16).children().val();
            var prodIdenti = btn.parent().parent().children().eq(4).html();

            var showNewUsers = btn.parent().parent().children().eq(17).html();
            if (!(showNewUsers == "-/-")) {
                showNewUsers = parseInt(showNewUsers);

                if (showNewUsers == 0) {
                    showNewUsers = parseInt((1 - userDiscount) * newUsers);

                    btn.parent().parent().children().eq(17).html(showNewUsers);
                }
            } else {
                showNewUsers = 0;
            }

            var showOrderAmounts = btn.parent().parent().children().eq(18).html();
            if (!(showOrderAmounts == "-/-")) {
                showOrderAmounts = parseInt(showOrderAmounts);

                if (showOrderAmounts == 0) {
                    showOrderAmounts = parseInt((1 - amountsDiscount) * orderAmounts);
                    btn.parent().parent().children().eq(18).html(showOrderAmounts);
                }
            } else {
                showOrderAmounts = 0;
            }

            var id = btn.parent().parent().children().eq(0).html();

            jQuery.post("productAnalysisEdit.do",
                    {
                        "userDiscount": userDiscount,
                        "amountsDiscount": amountsDiscount,
                        "showNewUsers": showNewUsers,
                        "showOrderAmounts": showOrderAmounts,
                        "newUsers": newUsers,
                        "editId": id
                    },
                    function (data) {
                        var dataObj = eval("(" + data + ")");

                        if (dataObj.result == "sucess") {
                            button.parent().parent().children().eq(19).html("<h6 style=\"color: red\">已出</h6>");
                            button.parent().parent().children().eq(15).html("<span style=\"padding: 4px 6px;\">" + userDiscount + "</span>")
                            button.parent().parent().children().eq(16).html("<span style=\"padding: 4px 6px;\">" + amountsDiscount + "</span>")
                            button.addClass("disabled");

                            if (!(prodIdenti == "-/-")) {
                                var totalShowOrderAmounts = parseInt($(".totalShowOrderAmounts").html());
                                var totalShowNewUsers = parseInt($(".totalShowNewUsers").html());

                                totalShowNewUsers = totalShowNewUsers + parseInt(showNewUsers);
                                totalShowOrderAmounts = totalShowOrderAmounts + parseInt(showOrderAmounts);

                                $(".totalShowNewUsers").html(totalShowNewUsers);
                                $(".totalShowOrderAmounts").html(totalShowOrderAmounts);
                            }
                            var alertMsg = "<div class=\"alert alert-success\">" + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\">" + "&times;" + "</button>" + "出账成功！" + "</div>";

                            $(".alert-msg").html(alertMsg);
                        } else {
                            var alertMsg = "<div class=\"alert alert-error\">" + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\">" + "&times;" + "</button>" + "出账失败，请联系管理员！" + "</div>";

                            $(".alert-msg").html(alertMsg);
                        }
                    });


        };


        function delBill(btn){
            var delId = btn.parent().parent().children().eq(0).html();

            var tr = btn.parent().parent();


            jQuery.post("productAnalysisDel.do", {"delId": delId}, function (data) {
                var dataObj = eval("(" + data + ")");

                if (dataObj.result == "sucess") {
                    tr.remove();

                    var totalNewUsers = parseInt($(".totalNewUsers").html());
                    var totalUsefullUsers = parseInt($(".totalUsefullUsers").html());
                    var totalActiveUsers = parseInt($(".totalActiveUsers").html());
                    var totalOrderAmounts = parseInt($(".totalOrderAmounts").html());
                    var totalNewOrderAmounts = parseInt($(".totalNewOrderAmounts").html());
                    var totalShowOrderAmounts = parseInt($(".totalShowOrderAmounts").html());
                    var totalShowNewUsers = parseInt($(".totalShowNewUsers").html());

                    var newUsers = parseInt(btn.parent().parent().children().eq(7).html());
                    var userfullUsers = parseInt(btn.parent().parent().children().eq(8).html());
                    var activesUsers = parseInt(btn.parent().parent().children().eq(10).html());
                    var orderAmounts = parseInt(btn.parent().parent().children().eq(11).html());
                    var newAmounts = parseInt(btn.parent().parent().children().eq(12).html());
                    var showOrderAmounts = parseInt(btn.parent().parent().children().eq(18).html());
                    var showNewUsers = parseInt(btn.parent().parent().children().eq(17).html());

                    var prodIdenti = btn.parent().parent().children().eq(4).html();

                    if (!(prodIdenti == "-/-")) {
                        totalNewUsers = totalNewUsers - newUsers;
                        totalUsefullUsers = totalUsefullUsers - userfullUsers;
                        totalActiveUsers = totalActiveUsers - activesUsers;
                        totalOrderAmounts = totalOrderAmounts- orderAmounts;
                        totalNewOrderAmounts = totalNewOrderAmounts - newAmounts;
                        totalShowOrderAmounts = totalShowOrderAmounts - showOrderAmounts;
                        totalShowNewUsers = totalShowNewUsers - showNewUsers;

                        $(".totalNewUsers").html(totalNewUsers);
                        $(".totalUsefullUsers").html(totalUsefullUsers);
                        $(".totalActiveUsers").html(totalActiveUsers);
                        $(".totalOrderAmounts").html(totalOrderAmounts);
                        $(".totalNewOrderAmounts").html(totalNewOrderAmounts);
                        $(".totalShowOrderAmounts").html(totalShowOrderAmounts);
                        $(".totalShowNewUsers").html(totalShowNewUsers);
                    }

                    var alertMsg = "<div class=\"alert alert-success\">" + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\">" + "&times;" + "</button>" + "删除成功！" + "</div>";

                    $(".alert-msg").html(alertMsg);
                } else {
                    var alertMsg = "<div class=\"alert alert-error\">" + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\">" + "&times;" + "</button>" + "删除失败，请联系管理员！" + "</div>";

                    $(".alert-msg").html(alertMsg);
                }
            })
        }

        $(".chargeOffBtn").click(function () {
            chargeOff($(this));
        });

        $(".delBtn").click(function () {
            delBill($(this));
        });

        $(".usersDiscountInput").blur(function () {
            var newUsers = $(this).parent().parent().children().eq(7).html();
            var discount = $(this).parent().parent().children().eq(15).children().val();

            var showNewUsers = (1 - discount) * newUsers;
            var showNewUsersNum = new Number(showNewUsers);

            $(this).parent().parent().children().eq(17).html(showNewUsersNum.toFixed(0));
        });

        $(".amountsDiscountInput").blur(function () {
            var amounts = $(this).parent().parent().children().eq(11).html();
            var discount = $(this).parent().parent().children().eq(16).children().val();

            var showAmounts = (1 - discount) * amounts;
            var showAmountsNum = new Number(showAmounts);

            $(this).parent().parent().children().eq(18).html(showAmountsNum.toFixed(0));
        });

        $(".usersDiscountInput").focus(function () {
            $(this).val("0.");
        });

        $(".amountsDiscountInput").focus(function () {
            $(this).val("0.");
        });

        $('#startDate').datepicker();
        $('#endDate').datepicker();

        loadAnalyChart(0, 10);

        function loadAnalyChart(dateType, timeType) {

            jQuery.post("queryProAnalyChart.do",
                    {
                        "dateType": dateType,
                        "timeType": timeType
                    },
                    function (data) {
                        var dataObj = eval("(" + data + ")");

                        $('#container').highcharts({                   //图表展示容器，与div的id保持一致
                            chart: {
                                type: 'line'                         //指定图表的类型，默认是折线图（line）
                            },
                            title: {
                                text: null      //指定图表标题
                            },
                            xAxis: {
                                categories: dataObj.categories  //['10-01', '10-02', '10-03']   指定x轴分组
                            },
                            yAxis: {
                                title: {
                                    text: null                  //指定y轴的标题
                                }
                            },
                            series: dataObj.series

                        });
                    });


        };

        $('.dataType  li').click(function () {
            // TODO
            $('.dataType li').removeClass('on');
            $(this).addClass('on');

            var dataType = $(this).val();
            var timeType = $('.timeType .on').val();

//            alert("dataType = " + dataType + " timeType = " + timeType);
            loadAnalyChart(dataType, timeType);
        });

        $('.timeType  li').click(function () {
            // TODO
            $('.timeType li').removeClass('on');
            $(this).addClass('on');

            var timeType = $(this).val();
            var dataType = $('.dataType .on').val();

//            alert("dataType = " + dataType + " timeType = " + timeType);
            loadAnalyChart(dataType, timeType);
        });

        $('#selectall').click(function () {
            if ($('.billcb').attr("checked") != "checked") {
                $('.billcb').attr("checked", "checked");
            } else {
                $('.billcb').removeAttrs("checked");
            }
        });

        $('#chargeoffall').click(function () {

            $('input:checkbox[class=billcb]:checked').each(function () {
                var btn = $(this).parent().parent().children().eq(20).children().eq(0);

                chargeOff(btn);
            });
        });

        $("#listTable").freezeHeader({'height': '700px'});

        $("#userId").change(function () {
            var userId = $("#userId").val();

            jQuery.post("queryProIdenti.do",
                    {
                        "userId": userId
                    },
                    function (data) {
                        var dataObj = eval("(" + data + ")");

                        $("#prodIdentification").empty();

                        $.each(dataObj, function (i, element) {
                            var item = "<option value=\"" + dataObj[i].prodIdentification + "\">" + dataObj[i].prodIdentification + "</option>";

                            $("#prodIdentification").append(item);
                        });
                    });
        });
    });
</script>

<div class="mRadiusborder">
    <div class="mod-header radius">
        <h4>基础指标-${productAnalysisName}</h4>
    </div>
    <table class="table table-striped table-hover table-bordered" id="basicTable"
            >
        <thead>
        <tr>
            <th>今日新增用户</th>
            <th>今日付费金额</th>
            <th>今日ARPU（元）</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${productAnalysisNewUsers}</td>
            <td>${productAnalysisAmounts}</td>
            <td>${productAnalysisArup}</td>
        </tr>
        </tbody>
    </table>
    <div style="margin-top: -20px;margin-bottom: 10px">
        <ul class="dataType borders">
            <li class="on" particle="newUsers" default="on" value="0">新增</li>
            <li particle="orderAmounts" value="1">付费</li>
            <li particle="arpu" value="2">ARPU</li>
        </ul>
        <ul class="timeType borders" style="float: right;margin-right: 40px">
            <li class="on" particle="installation" default="on" value="10">7天内</li>
            <li particle="active_user" value="11">30天内</li>
        </ul>
    </div>
    <div id="container" style="min-width:800px;height:300px"></div>
</div>

<%-- 导航栏--%>
<div class="mRadiusborder">
    <div class="navbar">
        <div class="navbar-inner">
            <form class="navbar-form " action="productAnalysis.do" method="post" id="queryForm">
                <input style="display: none;" value="${productAnalysisId}" name="productId"/>
                <c:if test="${user.accountType==0}">
                    <select id="userId" name="userId" class="span2">
                        <option value="">请选择合作方</option>
                        <c:if test="${null != userIds}">
                            <c:forEach var="item" items="${userIds}" step="1">
                                <option value="${item.userId}"
                                        <c:if test="${userId == item.userId}">selected="selected"</c:if>>${item.userCompanyName}</option>
                            </c:forEach>
                        </c:if>
                    </select>
                </c:if>
                <select id="prodIdentification" name="prodIdentification" class="span2">
                    <option value="">请选择渠道号</option>
                    <c:forEach var="item" items="${prodIdentifications}" step="1">
                        <option value="${item.prodIdentification}"
                                <c:if test="${prodIdentification == item.prodIdentification}">selected="selected"</c:if>>${item.prodIdentification}</option>
                    </c:forEach>
                </select>

                <div class="input-append date" id="startDate" data-date="${startDate}" data-date-format="yyyy-mm-dd">
                    <input class="span2" type="text" name="startDate" value="${startDate}" readonly>
                    <span class="add-on"><i class="icon-th"></i></span>
                </div>

                <div class="input-append date" id="endDate" data-date="${endDate}" data-date-format="yyyy-mm-dd">
                    <input class="span2" type="text" name="endDate" value="${endDate}" readonly>
                    <span class="add-on"><i class="icon-th"></i></span>
                </div>
                <input type="submit" class="btn btn-primary"
                       value="查 询"/>

                <input type="button" class="btn btn-danger" id="chargeoffall" value="一键出账" style="float: right; "/>
                <input type="button" class="btn btn-info" id="selectall" value="全 选"
                       style="float: right;margin-right: 19px"/>

            </form>
        </div>
    </div>


    <%-- 内容--%>
    <div class="row-fluid">
        <!--提示语-->
        <div class="alert-msg">
            <c:if test="${null != param.result}">
                <c:if test="${false == param.result}">
                    <div class="alert alert-error">
                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                            ${param.errorMsg}
                    </div>
                </c:if>
                <c:if test="${true == param.result}">
                    <div class="alert alert-success">
                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                            ${param.errorMsg}
                    </div>
                </c:if>
            </c:if>
        </div>

        <c:if test="${null != pageInfo}">
            <div class="mod-header radius">
                <h4>${productAnalysisName}</h4>
            </div>
            <table class="table table-striped table-hover table-condensed table-bordered" id="listTable"
                    >
                <thead>
                <tr style="background-color: white">

                    <th style="display: none">编号</th>
                    <th></th>
                    <th>日期</th>
                    <th>合作方</th>
                    <th>渠道号</th>
                    <th style="display: none">合作类型</th>
                    <th>合作类型</th>
                    <th>新增用户</th>
                    <th>有效用户</th>
                    <th>有效用户占比</th>
                    <th>活跃用户</th>
                    <th>总付费金额</th>
                    <th>新增用户付费</th>
                    <th>ARUP</th>
                    <th>新增ARUP</th>
                    <th>新增扣量</th>
                    <th>付费扣量</th>
                    <th>显示新增</th>
                    <th>显示金额</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <c:set var="totalNewUsers" value="0"/>
                <c:set var="totalUsefullUsers" value="0"/>
                <c:set var="totalActiveUsers" value="0"/>
                <c:set var="totalOrderAmounts" value="0"/>
                <c:set var="totalNewOrderAmounts" value="0"/>
                <c:set var="totalShowNewUsers" value="0"/>
                <c:set var="totalShowOrderAmounts" value="0"/>
                <c:if test="${null != pageInfo.result}">
                    <c:forEach var="item" items="${pageInfo.result}" step="1">
                        <%--非CP数据--%>
                        <c:if test="${item.prodIdentification != '-/-'}">
                            <c:set var="totalNewUsers" value="${totalNewUsers + item.newUsers}"/>
                            <c:set var="totalUsefullUsers" value="${totalUsefullUsers + item.usefullUsers}"/>
                            <c:set var="totalActiveUsers" value="${totalActiveUsers + item.activeUsers}"/>
                            <c:set var="totalOrderAmounts" value="${totalOrderAmounts + item.orderAmounts}"/>
                            <c:set var="totalNewOrderAmounts" value="${totalNewOrderAmounts + item.newAmounts}"/>
                            <%--隔天--%>
                            <c:if test="${item.chargeOffStatus == 1}">
                                <c:set var="totalShowNewUsers"
                                       value="${totalShowNewUsers +  item.showNewUsers}"></c:set>
                                <c:set var="totalShowOrderAmounts"
                                       value="${totalShowOrderAmounts + item.showOrderAmounts}"></c:set>
                            </c:if>
                            <%--实时--%>
                            <c:if test="${item.chargeOffType == 0}">
                                <%--S--%>
                                <c:if test="${item.cooperationType==1}">
                                    <c:set var="totalShowNewUsers"
                                           value="${totalShowNewUsers +  (item.newUsers * (1-item.usersDiscount) - (item.newUsers * (1-item.usersDiscount) % 1))}"/>

                                    <c:set var="totalShowOrderAmounts"
                                           value="${totalShowOrderAmounts + (item.orderAmounts * (1-item.amountsDiscount) - ((item.orderAmounts * (1-item.amountsDiscount)) % 1))}"/>
                                </c:if>
                                <%--A--%>
                                <c:if test="${item.cooperationType==0}">
                                    <c:set var="totalShowNewUsers"
                                           value="${totalShowNewUsers +  (item.newUsers * (1-item.usersDiscount) - (item.newUsers * (1-item.usersDiscount) % 1))}"/>
                                </c:if>
                            </c:if>
                        </c:if>
                    </c:forEach>
                </c:if>
                <tf>
                    <td>总计</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td>
                        <h6 style="color: blue" class="totalNewUsers"><fmt:formatNumber value="${totalNewUsers}"
                                                                                        groupingUsed="FALSE"
                                                                                        type="NUMBER"></fmt:formatNumber></h6>
                    </td>
                    <td>
                        <h6 style="color: blue" class="totalUsefullUsers"><fmt:formatNumber value="${totalUsefullUsers}"
                                                                                            groupingUsed="FALSE"
                                                                                            type="NUMBER"></fmt:formatNumber></h6>
                    </td>
                    <td></td>
                    <td>
                        <h6 style="color: blue" class="totalActiveUsers"><fmt:formatNumber
                                value="${totalActiveUsers}"
                                groupingUsed="FALSE"
                                type="NUMBER"></fmt:formatNumber></h6>
                    </td>
                    <td>
                        <h6 style="color: blue" class="totalOrderAmounts"><fmt:formatNumber
                                value="${totalOrderAmounts}"
                                groupingUsed="FALSE"
                                type="NUMBER"></fmt:formatNumber></h6>
                    </td>
                    <td>
                        <h6 style="color: blue" class="totalNewOrderAmounts"><fmt:formatNumber
                                value="${totalNewOrderAmounts}"
                                groupingUsed="FALSE"
                                type="NUMBER"></fmt:formatNumber></h6>
                    </td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td>
                        <h6 style="color: blue" class="totalShowNewUsers"><fmt:formatNumber value="${totalShowNewUsers}"
                                                                                            groupingUsed="FALSE"
                                                                                            type="NUMBER"></fmt:formatNumber>
                        </h6>
                    </td>
                    <td>
                        <h6 style="color: blue" class="totalShowOrderAmounts"><fmt:formatNumber
                                value="${totalShowOrderAmounts}"
                                groupingUsed="FALSE"
                                type="NUMBER"></fmt:formatNumber>
                        </h6>
                    </td>
                    <td></td>
                    <td></td>
                </tf>
                <tbody>
                <c:if test="${null != pageInfo.result}">
                    <c:forEach var="item" items="${pageInfo.result}" step="1">
                        <tr>
                            <td style="display: none">${item.id}</td>
                            <td><input type="checkbox" class="billcb" value="${item.id}"/></td>
                            <td><fmt:formatDate value="${item.createTime}"
                                                pattern="yyyy-MM-dd"></fmt:formatDate></td>
                            <td>${item.userCompany}</td>
                            <td>${item.prodIdentification}</td>
                            <td style="display: none">${item.cooperationType}</td>
                            <td>
                                <c:if test="${item.cooperationType == 0}">A</c:if>
                                <c:if test="${item.cooperationType == 1}">S</c:if>
                                /
                                <c:if test="${item.chargeOffType == 0}">实时</c:if>
                                <c:if test="${item.chargeOffType == 1}">隔天</c:if>

                            </td>
                            <td>${item.newUsers}</td>
                            <td>${item.usefullUsers}</td>
                            <c:choose>
                                <c:when test="${item.newUsers != 0}">
                                    <td><fmt:formatNumber value="${item.usefullUsers/item.newUsers}" type="PERCENT"
                                                          groupingUsed="FALSE"
                                                          maxFractionDigits="2"></fmt:formatNumber></td>
                                </c:when>
                                <c:otherwise>
                                    <td><fmt:formatNumber value="0" type="PERCENT"
                                                          groupingUsed="FALSE"
                                                          maxFractionDigits="2"></fmt:formatNumber></td>
                                </c:otherwise>
                            </c:choose>
                            <td>${item.activeUsers}</td>
                            <td>${item.orderAmounts}</td>
                            <td>${item.newAmounts}</td>
                                <%--arpu--%>
                            <c:choose>
                                <c:when test="${item.newUsers != 0}">
                                    <td><fmt:formatNumber value="${item.orderAmounts/item.newUsers}" type="NUMBER"
                                                          groupingUsed="FALSE"
                                                          maxFractionDigits="2"></fmt:formatNumber></td>
                                </c:when>
                                <c:otherwise>
                                    <td><fmt:formatNumber value="0" type="NUMBER"
                                                          groupingUsed="FALSE"
                                                          maxFractionDigits="2"></fmt:formatNumber></td>
                                </c:otherwise>
                            </c:choose>
                                <%--narpu--%>
                            <c:choose>
                                <c:when test="${item.newUsers != 0}">
                                    <td><fmt:formatNumber value="${item.newAmounts/item.newUsers}" type="NUMBER"
                                                          groupingUsed="FALSE"
                                                          maxFractionDigits="2"></fmt:formatNumber></td>
                                </c:when>
                                <c:otherwise>
                                    <td><fmt:formatNumber value="0" type="NUMBER"
                                                          groupingUsed="FALSE"
                                                          maxFractionDigits="2"></fmt:formatNumber></td>
                                </c:otherwise>
                            </c:choose>
                            <td>
                                <c:if test="${item.chargeOffStatus==0}">
                                    <c:choose>
                                        <c:when test="${item.chargeOffType == 0}">
                                            <span style="padding: 4px 6px;">${item.usersDiscount}</span>
                                        </c:when>
                                        <c:otherwise>
                                            <input type="text" class="usersDiscountInput" value="0.0" maxlength="4"
                                                   style="width: 20px"/>
                                        </c:otherwise>
                                    </c:choose>

                                </c:if>
                                <c:if test="${item.chargeOffStatus==1}">
                                    <span style="padding: 4px 6px;">${item.usersDiscount}</span>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${item.chargeOffStatus==0}">
                                    <c:choose>
                                        <c:when test="${item.chargeOffType == 0}">
                                            <span style="padding: 4px 6px;">${item.amountsDiscount}</span>
                                        </c:when>
                                        <c:otherwise>
                                            <input type="text" class="amountsDiscountInput" value="0.0" maxlength="4"
                                                   style="width: 20px"/>
                                        </c:otherwise>
                                    </c:choose>

                                </c:if>
                                <c:if test="${item.chargeOffStatus==1}">
                                    <span style="padding: 4px 6px;">${item.amountsDiscount}</span>
                                </c:if>
                            </td>
                                <%--实时--%>
                            <c:if test="${item.chargeOffType == 0}">
                                <%--S--%>
                                <c:if test="${item.cooperationType==1}">
                                    <td><fmt:formatNumber
                                            value="${item.newUsers * (1-item.usersDiscount) - (item.newUsers * (1-item.usersDiscount) % 1)}"
                                            type="NUMBER"
                                            groupingUsed="FALSE"></fmt:formatNumber>
                                    </td>
                                    <td><fmt:formatNumber
                                            value="${item.orderAmounts * (1-item.amountsDiscount)  - ((item.orderAmounts * (1-item.amountsDiscount)) % 1)}"
                                            type="NUMBER" groupingUsed="FALSE"
                                            maxFractionDigits="2"></fmt:formatNumber>
                                    </td>
                                </c:if>
                                <%--A--%>
                                <c:if test="${item.cooperationType==0}">
                                    <td><fmt:formatNumber
                                            value="${item.newUsers * (1-item.usersDiscount) - (item.newUsers * (1-item.usersDiscount) % 1)}"
                                            type="NUMBER"
                                            groupingUsed="FALSE"></fmt:formatNumber></td>
                                    <td>-/-</td>
                                </c:if>
                            </c:if>
                                <%--隔天--%>
                            <c:if test="${item.chargeOffType == 1}">
                                <%--S--%>
                                <c:if test="${item.cooperationType==1}">
                                    <td><fmt:formatNumber value="${item.showNewUsers}" type="NUMBER"
                                                          groupingUsed="FALSE"></fmt:formatNumber>
                                    </td>
                                    <td><fmt:formatNumber value="${item.showOrderAmounts}" type="NUMBER"
                                                          groupingUsed="FALSE"
                                                          maxFractionDigits="2"></fmt:formatNumber></td>
                                </c:if>
                                <%--A--%>
                                <c:if test="${item.cooperationType==0}">
                                    <td><fmt:formatNumber value="${item.showNewUsers}" type="NUMBER"
                                                          groupingUsed="FALSE"></fmt:formatNumber></td>
                                    <td>-/-</td>
                                </c:if>
                            </c:if>
                            <td>
                                <c:if test="${item.chargeOffStatus == 0}">
                                    <c:choose>
                                        <c:when test="${item.chargeOffType == 0}">
                                            <h6 style="color: red">已出</h6>
                                        </c:when>
                                        <c:otherwise>
                                            <h6 style="color: green">未出</h6>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                                <c:if test="${item.chargeOffStatus == 1}">
                                    <h6 style="color: red">已出</h6>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${null != operate}">
                                    <c:forEach var="opItem" items="${operate}" step="1">
                                        <c:if test="${opItem.operateType == 'EDIT'}">

                                            <c:if test="${item.chargeOffStatus==1}"><a href="javascript:void(0);"
                                                                                       class="chargeOffBtn btn btn-mini btn-warning disabled"
                                                                                       name="editButton">出
                                                账</a></c:if>
                                            <c:if test="${item.chargeOffStatus==0}"><a href="javascript:void(0);"
                                                                                       class="chargeOffBtn btn btn-mini btn-warning"
                                                                                       name="editButton">出
                                                账</a></c:if>
                                        </c:if>
                                        <c:if test="${opItem.operateType == 'DELETE'}">
                                            <a data-toggle="modal" href="javascript:void(0);"
                                               class="delBtn btn btn-mini btn-danger ">删
                                                除</a>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                            </td>


                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
        </c:if>
    </div>
</div>


