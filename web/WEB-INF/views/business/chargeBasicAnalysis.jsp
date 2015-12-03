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
            $("#editCooperationType").val($(this).children("td").eq(6).text());
            $("#editChargeOffType").val($(this).children("td").eq(8).text());
            $("#editDiscount").val($(this).children("td").eq(10).text().trim());
//            $("#editSharing").val($(this).children("td").eq(9).text().trim());
            $("#editProductName").val($(this).children("td").eq(4).text());
            $("#editProductIdentification").val($(this).children("td").eq(5).text());
            $("#editUserCompanyName").val($(this).children("td").eq(1).text());
            $("#editUserId").val($(this).children("td").eq(13).text());


            $("#delProductName").val($(this).children("td").eq(4).text());
            $("#delProductIdentification").val($(this).children("td").eq(5).text());
            $("#delUserCompanyName").val($(this).children("td").eq(1).text());
            $("#deleteId").val($(this).children("td").eq(0).text());
        });

        $('#startDate').datepicker();
        $('#endDate').datepicker();

        $("#productId").change(function () {
            var productId = $("#productId").val();

            jQuery.post("queryProIdentiGroup.do",
                    {
                        "productId": productId
                    },
                    function (data) {
                        var dataObj = eval("(" + data + ")");

                        $("#userId").empty();
                        $("#userId").append("<option value=\"\">" + "请选择合作方" + "</option>");

                        $.each(dataObj, function (i, element) {
                            var item = "<option value=\"" + dataObj[i].userId + "\">" + dataObj[i].userCompanyName + "</option>";

                            $("#userId").append(item);
                        });
                    });
        });

        $("#userId").change(function () {
            var productId = $("#productId").val();
            var userId = $("#userId").val();

            jQuery.post("queryProIdentiGroup.do",
                    {
                        "productId": productId,
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

    })
</script>

<div class="mRadiusborder">
    <%-- 导航栏--%>
    <div class="navbar">
        <div class="navbar-inner">
            <form class="navbar-form pull-left" action="queryChargeBasicAnalysis.do" method="post" id="queryForm">
                <select id="productId" name="productId" class="span2">
                    <option value="">请选择产品</option>
                    <c:if test="${null != products}">
                        <c:forEach var="item" items="${products}" step="1">
                            <option value="${item.id}"
                                    <c:if test="${productId == item.id}">selected="selected"</c:if>>${item.name}</option>
                        </c:forEach>
                    </c:if>
                </select>
                <select id="userId" name="userId" class="span2">
                    <option value="">请选择合作方</option>
                    <c:if test="${null != userIds}">
                        <c:forEach var="item" items="${userIds}" step="1">
                            <option value="${item.userId}"
                                    <c:if test="${userId == item.userId}">selected="selected"</c:if>>${item.userCompanyName}</option>
                        </c:forEach>
                    </c:if>
                </select>
                <select id="prodIdentification" name="prodIdentification" class="span2">
                    <option value="">请选择渠道号</option>
                    <c:if test="${null != prodIdentifications}">
                        <c:forEach var="item" items="${prodIdentifications}" step="1">
                            <option value="${item.prodIdentification}"
                                    <c:if test="${prodIdentification == item.prodIdentification}">selected="selected"</c:if>>${item.prodIdentification}</option>
                        </c:forEach>
                    </c:if>
                </select>

                <select id="payId" name="payId" class="span2">
                    <option value="">请选择计费方</option>
                    <c:if test="${null != payIds}">
                        <c:forEach var="item" items="${payIds}" step="1">
                            <option value="${item.id}"
                                    <c:if test="${payId == item.id}">selected="selected"</c:if>>${item.name}</option>
                        </c:forEach>
                    </c:if>
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


        <div class="mod-header radius">
            <h4>SDK数据</h4>
        </div>
        <table class="table table-striped table-hover table-bordered" id="listTable"
                >
            <thead>
            <tr>
                <th>日期</th>
                <th>渠道</th>
                <th>产品</th>
                <th>渠道号</th>
                <th>新增用户</th>
                <th>请求用户</th>
                <th>请求金额</th>
                <th>人均请求金额</th>
                <th>成功计费金额</th>
                <th>计费成功率</th>
                <th>计费方式</th>
                <th>请求金额</th>
                <th>成功金额</th>
                <th>计费成功率</th>
                <th>详细</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${null != sdkBasicAnalysises}">
                <c:forEach var="item" items="${sdkBasicAnalysises}" step="1">
                    <tr>
                    <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd"/></td>
                    <td>${item.userCompany}</td>
                    <td>${item.productName}</td>
                    <td>${item.prodIdentification}</td>
                    <td>${item.newUsers}</td>
                    <td>${item.orderUsers}</td>
                    <td>${item.orderAmounts}</td>
                    <c:choose>
                        <c:when test="${item.orderUsers != 0}">
                            <td><fmt:formatNumber value="${item.orderAmounts/item.orderUsers}" type="NUMBER"
                                                  groupingUsed="FALSE"
                                                  maxFractionDigits="2"></fmt:formatNumber></td>
                        </c:when>
                        <c:otherwise>
                            <td><fmt:formatNumber value="0" type="NUMBER"
                                                  groupingUsed="FALSE"
                                                  maxFractionDigits="2"></fmt:formatNumber></td>
                        </c:otherwise>
                    </c:choose>
                    <td>${item.sucessAmounts}</td>
                    <c:choose>
                        <c:when test="${item.orderAmounts != 0}">
                            <td><fmt:formatNumber value="${item.sucessAmounts/item.orderAmounts}" type="PERCENT"
                                                  groupingUsed="FALSE"
                                                  maxFractionDigits="2"></fmt:formatNumber></td>
                        </c:when>
                        <c:otherwise>
                            <td><fmt:formatNumber value="0" type="PERCENT"
                                                  groupingUsed="FALSE"
                                                  maxFractionDigits="2"></fmt:formatNumber></td>
                        </c:otherwise>
                    </c:choose>

                    <c:forEach var="item2" items="${item.provinceAnalysis}" step="1" varStatus="index">
                        <c:if test="${index.count == 1}">
                            <td>${item2.payName}</td>
                            <td>${item2.orderAmounts}</td>
                            <td>${item2.sucessAmounts}</td>
                            <c:choose>
                                <c:when test="${item2.orderAmounts != 1}">
                                    <td><fmt:formatNumber value="${item2.sucessAmounts/item2.orderAmounts}"
                                                          type="PERCENT"
                                                          groupingUsed="FALSE"
                                                          maxFractionDigits="2"></fmt:formatNumber></td>
                                </c:when>
                                <c:otherwise>
                                    <td><fmt:formatNumber value="0" type="PERCENT"
                                                          groupingUsed="FALSE"
                                                          maxFractionDigits="2"></fmt:formatNumber></td>
                                </c:otherwise>
                            </c:choose>
                            <td><a href="${pageContext.request.contextPath}/queryBasicProvinceAnalysis.do?specifiedDate=<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd"/>&payType=${item2.payId}|${item2.payName}">+</a></td>

                        </c:if>
                        <c:if test="${index.count != 1}">
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
                                <td>${item2.payName}</td>
                                <td>${item2.orderAmounts}</td>
                                <td>${item2.sucessAmounts}</td>
                                <c:choose>
                                    <c:when test="${item2.orderAmounts != 0}">
                                        <td><fmt:formatNumber value="${item2.sucessAmounts/item2.orderAmounts}"
                                                              type="PERCENT"
                                                              groupingUsed="FALSE"
                                                              maxFractionDigits="2"></fmt:formatNumber></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td><fmt:formatNumber value="0" type="PERCENT"
                                                              groupingUsed="FALSE"
                                                              maxFractionDigits="2"></fmt:formatNumber></td>
                                    </c:otherwise>
                                </c:choose>
                                <td><a href="${pageContext.request.contextPath}/queryBasicProvinceAnalysis.do?specifiedDate=<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd"/>&payType=${item2.payId}|${item2.payName}">+</a></td>
                            </tr>
                        </c:if>
                    </c:forEach>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>


    </div>
</div>
