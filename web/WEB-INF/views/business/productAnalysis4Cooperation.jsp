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

        $('#startDate').datepicker();
        $('#endDate').datepicker();

        $("#productId").change(function () {
            var productId = $("#productId").val();

            jQuery.post("queryProIdenti.do",
                    {
                        "productId": productId
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

<%-- 导航栏--%>
<div class="mradiusborder">
    <div class="navbar">
        <div class="navbar-inner">
            <form class="navbar-form " action="productAnalysis.do" method="post" id="queryForm">
                <select id="productId" name="productId" class="span2">
                    <option value="">请选择产品</option>
                    <c:if test="${null != products}">
                        <c:forEach var="item" items="${products}" step="1">
                            <option value="${item.id}"
                                    <c:if test="${productId == item.id}">selected="selected"</c:if>>${item.name}</option>
                        </c:forEach>
                    </c:if>
                </select>
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
                <h4>账单列表</h4>
            </div>
            <table class="table table-striped table-hover table-condensed table-bordered" id="listTable"
                    >
                <thead>
                <tr>
                    <th style="display: none">编号</th>
                    <th></th>
                    <th>日期</th>
                    <th>合作方</th>
                    <th>产品</th>
                    <th>渠道号</th>
                    <th style="display: none">合作类型</th>
                    <th>合作类型</th>
                    <th>ARPU</th>
                    <th>新增用户</th>
                    <th>付费金额</th>
                </tr>
                </thead>
                <tbody>

                <c:set var="totalShowNewUsers" value="0"/>
                <c:set var="totalShowOrderAmounts" value="0"/>
                <c:if test="${null != pageInfo.result}">
                    <c:forEach var="item" items="${pageInfo.result}" step="1">
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

                    </c:forEach>
                </c:if>
                <tf>
                    <td>总计</td>
                    <td></td>
                    <td></td>
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
                </tf>
                <c:if test="${null != pageInfo.result}">
                    <c:forEach var="item" items="${pageInfo.result}" step="1">
                        <tr>
                            <td style="display: none">${item.id}</td>
                            <td></td>
                            <td><fmt:formatDate value="${item.createTime}"
                                                pattern="yyyy-MM-dd"></fmt:formatDate></td>
                            <td>${item.userCompany}</td>
                            <td>${item.productName}</td>
                            <td>${item.prodIdentification}</td>
                            <td style="display: none">${item.cooperationType}</td>
                            <td>
                                <c:if test="${item.cooperationType == 0}">A</c:if>
                                <c:if test="${item.cooperationType == 1}">S</c:if>
                                /
                                <c:if test="${item.chargeOffType == 0}">实时</c:if>
                                <c:if test="${item.chargeOffType == 1}">隔天</c:if>

                            </td>
                            <c:if test="${item.cooperationType == 1}">
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
                            </c:if>
                            <c:if test="${item.cooperationType == 0}"><td>-/-</td></c:if>
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


                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
        </c:if>
    </div>
</div>


