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
            $("#editName").val($(this).children("td").eq(1).text().trim());
            $("#editWeight").val($(this).children("td").eq(3).text());
            $("#editStatus").val($(this).children("td").eq(4).text());

            $("#deleteName").val($(this).children("td").eq(1).text().trim());
            $("#deleteId").val($(this).children("td").eq(0).text());
        });
    })
</script>
<%-- 导航栏--%>
<div class="navbar">
    <div class="navbar-inner">
        <c:if test="${null != operate}">
            <c:forEach var="item" items="${operate}" step="1">
                <c:if test="${item.operateType == 'ADD'}">
                    <a data-toggle="modal" href="#add" class="btn btn-success">新 建</a>
                </c:if>
            </c:forEach>
        </c:if>
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
            <h4>第三方支付列表</h4>
        </div>
        <table class="table table-striped table-hover table-bordered" id="listTable"
                >
            <thead>
            <tr>
                <th style="display: none">编号</th>
                <th>支付名称</th>
                <th>省份</th>
                <th>权重</th>
                <th style="display: none">状态</th>
                <th>状态</th>
                <th>创建时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${null != thPays}">
                <c:forEach var="item" items="${thPays}" step="1">
                    <tr>
                        <td style="display: none">${item.id}</td>
                        <td>
                            ${item.name}
                        </td>
                        <td><a href="${pageContext.request.contextPath}/thpayProManager.do?method=detial&thPayId=${item.id}">开通</a>
                            &nbsp;
                            &nbsp;
                            <a href="${pageContext.request.contextPath}/thpayProManager.do?method=advantage&thPayId=${item.id}">优势</a>
                        </td>
                        <td>${item.weight}</td>
                        <td style="display: none">${item.status}</td>
                        <td>
                            <c:if test="${item.status == 0}">正常</c:if>
                            <c:if test="${item.status == 1}">关闭</c:if>
                        </td>
                        <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>
                            <c:if test="${null != operate}">
                                <c:forEach var="item" items="${operate}" step="1">
                                    <c:if test="${item.operateType == 'EDIT'}">
                                        <a data-toggle="modal" href="#edit" class="btn btn-mini btn-warning "
                                           name="editButton">修
                                            改</a>
                                    </c:if>
                                    <c:if test="${item.operateType == 'DELETE'}">
                                        <a data-toggle="modal" href="#delete" class="btn btn-mini btn-danger ">删 除</a>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>

</div>

<%-- 新增--%>
<div id="add" class="modal hide">
    <div class="modal-header">
        <a class="close" data-dismiss="modal">&times;</a>

        <h3>新增</h3>
    </div>
    <div style="max-height: 300px;padding: 15px;overflow-y: auto;">
        <form class="form-horizontal" action="thPayAdd.do" method="post" id="addForm">
            <fieldset>
                <div class="control-group">
                    <label class="control-label" for="addName">支付名称</label>

                    <div class="controls">
                        <input type="text" name="addName" id="addName" placeholder="请输入支付名称"
                               class="span3">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="addWeight">权重</label>

                    <div class="controls">
                        <input type="text" name="addWeight" id="addWeight" placeholder="请输入数字，数字越大权重越高"
                               class="span3">
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="addStatus">状态</label>

                    <div class="controls">
                        <select id="addStatus" name="addStatus" class="span3">
                            <option value="0">正常</option>
                            <option value="1">关闭</option>
                        </select>
                    </div>
                </div>
                <div class="form-actions">
                    <input type="submit" class="btn btn-success"
                           value="新 建"/>
                    <input type="reset" class="btn" data-dismiss="modal"
                           value="关 闭"/>
                </div>
            </fieldset>
        </form>
    </div>
</div>


<%-- 修改--%>
<div id="edit" class="modal hide">
    <div class="modal-header">
        <a class="close" data-dismiss="modal">&times;</a>

        <h3>修改</h3>
    </div>
    <div style="max-height: 300px;padding: 15px;overflow-y: auto;">
        <form class="form-horizontal" action="thPayEdit.do" method="post" id="editForm">
            <fieldset>
                <div class="control-group" style="display: none">
                    <label class="control-label" for="editId">编号</label>

                    <div class="controls">
                        <input type="text" name="editId" id="editId"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editName">支付名称</label>

                    <div class="controls">
                        <input type="text" name="editName" id="editName"
                               class="span3">

                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editWeight">权重</label>

                    <div class="controls">
                        <input type="text" name="editWeight" id="editWeight"
                               class="span3">

                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editStatus">状态</label>

                    <div class="controls">
                        <select id="editStatus" name="editStatus" class="span3">
                            <option value="0">正常</option>
                            <option value="1">关闭</option>
                        </select>
                    </div>
                </div>
                <div class="form-actions">
                    <input type="submit" class="btn btn-warning"
                           value="修 改"/>
                    <input type="reset" class="btn" data-dismiss="modal"
                           value="关 闭"/>
                </div>
            </fieldset>
        </form>
    </div>
</div>

<%-- 删除--%>
<div id="delete" class="modal hide">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>

        <h3>删除</h3>
    </div>
    <div style="max-height: 300px;padding: 15px;overflow-y: auto;">
        <form class="form-horizontal" action="thPayDel.do" method="post" id="deleteForm">
            <fieldset>
                <div class="control-group" style="display: none">
                    <label class="control-label" for="deleteId">编号</label>

                    <div class="controls">
                        <input type="text" name="deleteId" id="deleteId"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="deleteName">支付名称</label>

                    <div class="controls">
                        <input type="text" name="deleteName" id="deleteName"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="form-actions">
                    <input type="submit" class="btn btn-danger"
                           value="删 除"/>
                    <input type="reset" class="btn" data-dismiss="modal"
                           value="关 闭"/>
                </div>
            </fieldset>
        </form>
    </div>
</div>