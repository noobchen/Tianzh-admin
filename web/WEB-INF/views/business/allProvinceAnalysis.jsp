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
    })
</script>


<%-- 内容--%>
<div class="row-fluid">
    <div class="mRadiusborder">
        <table class="table table-striped table-hover table-bordered" id="titleTable"
                >
            <thead>
            <tr>
                <th>产品名：${productName}</th>
                <th>渠道：${companyName}</th>
                <th>渠道号：${prodIdentification}</th>
                <th>SDK：${payName}</th>
                <th>运营商：${providerName}</th>
            </tr>
            </thead>
        </table>


        <div class="mod-header radius">
        </div>
        <table class="table table-striped table-hover table-bordered" id="provinceTable"
                >
            <thead>
            <tr>
                <th>省份</th>
                <th>请求金额</th>
                <th>成功金额</th>
                <th>占比</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${provinceAnalysis}" step="1" varStatus="index">
                <tr>
                    <td>${item.provinceName}</td>
                    <td>${item.orderAmounts}</td>
                    <td>${item.sucessAmounts}</td>
                    <c:choose>
                        <c:when test="${item.orderAmounts != 0}">
                            <td><fmt:formatNumber value="${item.sucessAmounts/item.orderAmounts}"
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
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
