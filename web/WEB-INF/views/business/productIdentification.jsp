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
            $("#editProductId").val($(this).children("td").eq(15).text());
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

        $("#addCooperationType").change(function () {
            if ($(this).val() == 0) {
                $("#addSharingDiv").css("display", "none");
            } else {
                $("#addSharingDiv").css("display", 'block');
            }
        });

        $("#addChargeOffType").change(function () {
            if ($(this).val() == 1) {
                $("#addDiscountDiv").css("display", "none");
            } else {
                $("#addDiscountDiv").css("display", 'block');
            }
        });

        $("#editCooperationType").change(function () {
            if ($(this).val() == 0) {
                $("#editSharingDiv").css("display", "none");
            } else {
                $("#editSharingDiv").css("display", 'block');
            }
        });

        $("#editChargeOffType").change(function () {
            if ($(this).val() == 1) {
                $("#editDiscountDiv").css("display", "none");
            } else {
                $("#editDiscountDiv").css("display", 'block');
            }
        });

        $("#addUserId").change(function () {
            var accountType = $(this).val().split("|")[2];

            if (accountType == 3) {
                $("#addProdIdentificationDiv").css("display", 'block');
            } else {
                $("#addProdIdentificationDiv").css("display", "none");
            }
        });

        $(".editButton").click(function () {
            var cooperation = $(this).parent().parent().children().eq(6).html();
            var chargeOff = $(this).parent().parent().children().eq(8).html();
            var accountType = $(this).parent().parent().children().eq(2).html();


            if (cooperation == 0) {
                $("#editSharingDiv").css("display", "none");
            } else {
                $("#editSharingDiv").css("display", "block");
            }

            if (chargeOff == 1) {
                $("#editDiscountDiv").css("display", "none");
            } else {
                $("#editDiscountDiv").css("display", 'block');
            }

            if (accountType == 3) {
                $("#editProdIdentificationDiv").css("display", "block");
            } else {
                $("#editProdIdentificationDiv").css("display", 'none');
            }

        });

    })
</script>
<%-- 导航栏--%>
<div class="navbar">
    <div class="navbar-inner">
        <form class="navbar-form pull-left" action="productIdentification.do" method="post" id="queryForm">
            <input class="span2" type="text" id="prodIdentification" name="prodIdentification" placeholder="请输入产品标号"
                   value="${prodIdentification}">
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
                        <option value="${item.id}"
                                <c:if test="${userId == item.id}">selected="selected"</c:if>>${item.operatorName}</option>
                    </c:forEach>
                </c:if>
            </select>

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
            <h4>合作方列表</h4>
        </div>
        <table class="table table-striped table-hover table-bordered" id="listTable"
                >
            <thead>
            <tr>
                <th style="display: none">编号</th>
                <th>合作方</th>
                <th style="display: none">类型</th>
                <th>类型</th>
                <th>产品</th>
                <th>标号</th>
                <th style="display: none">合作类型</th>
                <th>合作类型</th>
                <th style="display: none">出账类型</th>
                <th>出账类型</th>
                <th>扣量</th>
                    <%--<th>分成</th>--%>
                <th style="display: none">状态</th>
                <th>状态</th>
                <th style="display: none">合作方号</th>
                <th>创建时间</th>
                <th style="display: none">产品Id</th>
                <th>操作</th>

            </tr>
            </thead>
            <tbody>
            <c:if test="${null != pageInfo.result}">
                <c:forEach var="item" items="${pageInfo.result}" step="1">
                    <tr>
                        <td style="display: none">${item.id}</td>
                        <td>${item.userCompanyName}</td>
                        <td style="display: none">${item.accountType}</td>
                        <td>
                            <c:if test="${item.accountType == 3}">渠道</c:if>
                            <c:if test="${item.accountType == 1}">C P</c:if>
                        </td>
                        <td>${item.productName}</td>

                        <c:if test="${item.accountType == 3}">
                            <td>${item.prodIdentification}</td>
                        </c:if>
                        <c:if test="${item.accountType == 1}">
                            <td>-/-</td>
                        </c:if>

                        <td style="display: none">${item.cooperationType}</td>
                        <td>
                            <c:if test="${item.cooperationType == 0}">CPA</c:if>
                            <c:if test="${item.cooperationType == 1}">CPS</c:if>
                        </td>
                        <td style="display: none">${item.chargeOffType}</td>
                        <td>
                            <c:if test="${item.chargeOffType == 0}">实时</c:if>
                            <c:if test="${item.chargeOffType == 1}">隔天</c:if>
                        </td>
                        <td>
                            <c:if test="${item.chargeOffType == 0}">${item.discount}</c:if>
                            <c:if test="${item.chargeOffType == 1}">-/-</c:if>

                        </td>
                            <%--<td>--%>
                            <%--<c:if test="${item.cooperationType == 0}">-/-</c:if>--%>
                            <%--<c:if test="${item.cooperationType == 1}">${item.sharing}</c:if>--%>
                            <%--</td>--%>
                        <td style="display: none">${item.status}</td>
                        <td>
                            <c:if test="${item.status == 0}">正常</c:if>
                            <c:if test="${item.status == 1}">关闭</c:if>
                        </td>
                        <td style="display: none">${item.userId}</td>
                        <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td style="display: none">${item.productId}</td>
                        <td>
                            <c:if test="${null != operate}">
                                <c:forEach var="item" items="${operate}" step="1">
                                    <c:if test="${item.operateType == 'EDIT'}">
                                        <a data-toggle="modal" href="#edit" class="editButton btn btn-mini btn-warning "
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
        <form class="form-horizontal" action="productIdentificationAdd.do" method="post" id="addForm">
            <fieldset>
                <div class="control-group">
                    <label class="control-label" for="addUserId">合作方</label>

                    <div class="controls">
                        <select id="addUserId" name="addUserId" class="span3">
                            <option value="">请选择合作方</option>
                            <c:if test="${null != userIds}">
                                <c:forEach var="item" items="${userIds}" step="1">
                                    <option value="${item.id}|${item.operatorName}|${item.accountType}">${item.operatorName}</option>
                                </c:forEach>
                            </c:if>
                        </select>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="addProductId">产品</label>

                    <div class="controls">
                        <select id="addProductId" name="addProductId" class="span3">
                            <option value="">请选择产品</option>
                            <c:if test="${null != products}">
                                <c:forEach var="item" items="${products}" step="1">
                                    <option value="${item.id}|${item.name}">${item.name}</option>
                                </c:forEach>
                            </c:if>
                        </select>
                    </div>
                </div>

                <div class="control-group" id="addProdIdentificationDiv">
                    <label class="control-label" for="addProdIdentification">产品标号</label>

                    <div class="controls">
                        <input type="text" name="addProdIdentification" id="addProdIdentification"
                               placeholder="请输入产品标号"
                               class="span3" value="-/-">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="addCooperationType">合作类型</label>

                    <div class="controls">
                        <select id="addCooperationType" name="addCooperationType" class="span3">
                            <option value="">请选择合作类型</option>
                            <option value="0">CPA</option>
                            <option value="1" selected="selected">CPS</option>
                        </select>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="addChargeOffType">出账类型</label>

                    <div class="controls">
                        <select id="addChargeOffType" name="addChargeOffType" class="span3">
                            <option value="">请选择出账类型</option>
                            <option value="0">实时</option>
                            <option value="1" selected="selected">隔天</option>
                        </select>
                    </div>
                </div>
                <%--<div class="control-group" style="display: none" id="addSharingDiv">--%>
                <%--<label class="control-label" for="addSharing">分成</label>--%>

                <%--<div class="controls">--%>
                <%--<input type="text" name="addSharing" id="addSharing" placeholder="请输入分成率"--%>
                <%--class="span3">--%>
                <%--</div>--%>
                <%--</div>--%>
                <div class="control-group" style="display: none" id="addDiscountDiv">
                    <label class="control-label" for="addDiscount">扣量</label>

                    <div class="controls">
                        <input type="text" name="addDiscount" id="addDiscount" placeholder="请输入扣量"
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
        <form class="form-horizontal" action="productIdentificationEdit.do" method="post" id="editForm">
            <fieldset>
                <div class="control-group" style="display: none">
                    <label class="control-label" for="editId">编号</label>

                    <div class="controls">
                        <input type="text" name="editId" id="editId"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="control-group" style="display: none">
                    <label class="control-label" for="editProductId">产品编号</label>

                    <div class="controls">
                        <input type="text" name="editProductId" id="editProductId"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="control-group" style="display: none">
                    <label class="control-label" for="editUserId">合作方号</label>

                    <div class="controls">
                        <input type="text" name="editUserId" id="editUserId"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editProductName">游戏</label>

                    <div class="controls">
                        <input type="text" name="editProductName" id="editProductName"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="control-group" id="editProdIdentificationDiv">
                    <label class="control-label" for="editProductIdentification">标号</label>

                    <div class="controls">
                        <input type="text" name="editProductIdentification" id="editProductIdentification"
                               class="span3 disabled">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editUserCompanyName">合作方</label>

                    <div class="controls">
                        <input type="text" name="editUserCompanyName" id="editUserCompanyName"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editCooperationType">合作类型</label>

                    <div class="controls">
                        <select id="editCooperationType" name="editCooperationType" class="span3">
                            <option value="">请选择合作类型</option>
                            <option value="0">CPA</option>
                            <option value="1">CPS</option>
                        </select>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editChargeOffType">出账类型</label>

                    <div class="controls">
                        <select id="editChargeOffType" name="editChargeOffType" class="span3">
                            <option value="">请选择出账类型</option>
                            <option value="0">实时</option>
                            <option value="1">隔天</option>
                        </select>
                    </div>
                </div>
                <%--<div class="control-group" id="editSharingDiv">--%>
                <%--<label class="control-label" for="editSharing">分成</label>--%>

                <%--<div class="controls">--%>
                <%--<input type="text" name="editSharing" id="editSharing" placeholder="请输入分成"--%>
                <%--class="span3">--%>
                <%--</div>--%>
                <%--</div>--%>
                <div class="control-group" id="editDiscountDiv">
                    <label class="control-label" for="editDiscount">扣量</label>

                    <div class="controls">
                        <input type="text" name="editDiscount" id="editDiscount" placeholder="请输入扣量"
                               class="span3">
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
        <form class="form-horizontal" action="productIdentificationDel.do" method="post" id="deleteForm">
            <fieldset>
                <div class="control-group" style="display: none">
                    <label class="control-label" for="deleteId">编号</label>

                    <div class="controls">
                        <input type="text" name="deleteId" id="deleteId"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="delProductName">游戏</label>

                    <div class="controls">
                        <input type="text" name="delProductName" id="delProductName"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="delProductIdentification">标号</label>

                    <div class="controls">
                        <input type="text" name="delProductIdentification" id="delProductIdentification"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="delUserCompanyName">合作方</label>

                    <div class="controls">
                        <input type="text" name="delUserCompanyName" id="delUserCompanyName"
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