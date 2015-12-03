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
<%-- 导航栏--%>
<div class="navbar">
    <div class="navbar-inner">
        <form class="navbar-form pull-left" action="product.do" method="post" id="queryForm">
            <input class="span2" type="text" id="name" name="name" placeholder="请输入游戏名称" value="${name}">
            <input class="span2" type="text" name="method" value="product" style="display: none">
            <input type="submit" class="btn btn-primary"
                   value="查 询"/>
            <c:if test="${null != operate}">
                <c:forEach var="item" items="${operate}" step="1">
                    <c:if test="${item.operateType == 'ADD'}">
                        <a data-toggle="modal" href="#add" class="btn btn-success">新 建</a>
                    </c:if>
                </c:forEach>
            </c:if>
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

    <c:if test="${null != pageInfo}">
        <div class="mod-header radius">
            <h4>游戏列表</h4>
        </div>
        <table class="table table-striped table-hover table-bordered" id="listTable"
               >
            <thead>
            <tr>
                <th style="display: none">编号</th>
                <th>游戏名称</th>
                <th>游戏Id</th>
                <th style="display: none">状态</th>
                <th>状态</th>
                <th>创建时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${null != pageInfo.result}">
                <c:forEach var="item" items="${pageInfo.result}" step="1">
                    <tr>
                        <td style="display: none">${item.id}</td>
                        <td><a href="${pageContext.request.contextPath}/product.do?method=detial&productId=${item.id}">${item.name}</a></td>
                        <td>${item.appId}</td>
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

        <!--分页-->
        <div class="pagination" align="center">
            <ul>
                <li><a href="${pageInfo.firstPageUrl}">首页</a></li>
                <li><a href="${pageInfo.prevPageUrl}">前一页</a></li>
                <li class="active">
                    <a>
                        总共${pageInfo.totalRow}行;
                        每页${pageInfo.pageSize}条;
                        总共${pageInfo.totalPageSize}页;
                        <c:if test="${0 == pageInfo.totalPageSize}">
                            当前第0页
                        </c:if>
                        <c:if test="${0 != pageInfo.totalPageSize}">
                            当前第${pageInfo.startPageIndex+1}页
                        </c:if>
                    </a>
                </li>
                <li><a href="${pageInfo.nextPageUrl}">下一页</a></li>
                <li><a href="${pageInfo.lastPageUrl}">末页</a></li>
            </ul>
        </div>
    </c:if>
</div>

<%-- 新增--%>
<div id="add" class="modal hide">
    <div class="modal-header">
        <a class="close" data-dismiss="modal">&times;</a>

        <h3>新增</h3>
    </div>
    <div style="max-height: 300px;padding: 15px;overflow-y: auto;">
        <form class="form-horizontal" action="productAdd.do" method="post" id="addForm">
            <fieldset>
                <div class="control-group">
                    <label class="control-label" for="addName">游戏名称</label>

                    <div class="controls">
                        <input type="text" name="addName" id="addName" placeholder="请输入游戏名称"
                               class="span3">
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
        <form class="form-horizontal" action="productEdit.do" method="post" id="editForm">
            <fieldset>
                <div class="control-group" style="display: none">
                    <label class="control-label" for="editId">编号</label>

                    <div class="controls">
                        <input type="text" name="editId" id="editId"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editProductName">游戏名称</label>

                    <div class="controls">
                        <input type="text" name="editProductName" id="editProductName" placeholder="请输入游戏名称"
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
        <form class="form-horizontal" action="productDel.do" method="post" id="deleteForm">
            <fieldset>
                <div class="control-group" style="display: none">
                    <label class="control-label" for="deleteId">编号</label>

                    <div class="controls">
                        <input type="text" name="deleteId" id="deleteId"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="deleteName">游戏名称</label>

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