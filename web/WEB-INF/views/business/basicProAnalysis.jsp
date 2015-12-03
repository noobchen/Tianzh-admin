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
        <h4>基础数据</h4>
    </div>
    <table class="table table-striped table-hover table-bordered" id="listTable"
            >
        <thead>
        <tr>
            <th style="display: none">编号</th>
            <th>游戏名称</th>
            <th>今日新增用户</th>
            <th>今日充值（元）</th>
            <th>ARPU（元）</th>
            <th>本月累计用户</th>
            <th>本月累计充值</th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${null != basicProAnalysises}">
            <c:forEach var="item" items="${basicProAnalysises}" step="1">
                <tr>
                    <td style="display: none">${item.appId}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/productAnalysis.do?productId=${item.appId}&productName=${item.appName}&newUsers=${item.newUsers}&amounts=${item.amounts}&arup=${item.arup}">${item.appName}</a>
                    </td>
                    <td>${item.newUsers}</td>
                    <td>${item.amounts}</td>
                    <td>${item.arup}</td>
                    <td>${item.totalNewUsers}</td>
                    <td>${item.totalAmounts}</td>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>


</div>