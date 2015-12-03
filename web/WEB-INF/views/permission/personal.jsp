<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<script type="text/javascript">
    $.validator.setDefaults({
        submitHandler:function () {
            editForm.submit();
        }
    });

    $().ready(function () {
        // validate form on keyup and submit
        $("#editForm").validate({
            errorElement:'span',
            errorClass:'help-inline',
            highlight:function (element, errorClass) {
                $(element).parent().parent().removeClass('success');
                $(element).parent().parent().addClass('error');
            },
            unhighlight:function (element, errorClass) {
                $(element).parent().parent().removeClass('error');
                $(element).parent().parent().addClass('success');
            },
            rules:{
                editOperatorName:{
                    required:true,
                    minlength:1,
                    maxlength:100
                },
                editOperatorCompany:{
                    required:true,
                    minlength:1,
                    maxlength:100
                },
                editOperatorEmail:{
                    required:false,
                    maxlength:100
                },
                editOperatorPhone:{
                    required:false,
                    maxlength:20
                },
                editPassword:{
                    required:false,
                    minlength:5,
                    maxlength:50
                },
                editRemark:{
                    required:false,
                    maxlength:1024
                }
            },
            messages:{
                editOperatorName:{
                    required:"请输入用户名称",
                    minlength:"用户名称必须包含1个字符",
                    maxlength:"用户名称最多包含50个字符"
                },
                editOperatorCompany:{
                    required:"请输入公司名称",
                    minlength:"公司名称必须包含1个字符",
                    maxlength:"公司名称最多包含100个字符"
                },
                editOperatorEmail:{
                    maxlength:"邮箱地址最多包含100个字符"
                },
                editOperatorPhone:{
                    maxlength:"联系电话最多包含20个字符"
                },
                editPassword:{
                    minlength:"用户密码必须包含5个字符",
                    maxlength:"用户密码最多包含50个字符"
                },
                editRemark:{
                    maxlength:"备注最多包含1024个字符"
                }
            }
        });


        $("#listTable").find("tr").hover(function () {
            $("#editId").val($(this).children("td").eq(0).html());
            $("#editOperatorName").val($(this).children("td").eq(1).html());
            $("#editOperatorCompany").val($(this).children("td").eq(4).html());
            $("#editOperatorEmail").val($(this).children("td").eq(5).html());
            $("#editOperatorPhone").val($(this).children("td").eq(6).html());
            $("#editRemark").val($(this).children("td").eq(7).html());
        })
    });
</script>

<div class="navbar">
    <div class="navbar-inner">
        <%--<form class="navbar-form pull-left" action="personalQuery.do" method="post" id="queryForm">--%>
            <%--<input class="span2" type="text" id="name" name="name" placeholder="用户名称" value="${name}">--%>
            <%--<input class="span2" type="text" id="company" name="company" placeholder="公司名称" value="${company}">--%>
            <%--<input type="submit" class="btn btn-primary"--%>
                   <%--value="查 询"/>--%>
        <%--</form>--%>
    </div>
</div>


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
        <table class="table table-striped table-hover table-condensed" id="listTable"
               >
            <thead>
            <tr>
                <th>编号</th>
                <th>用户名称</th>
                <th>登陆账号</th>
                <th>角色</th>
                <th>公司名称</th>
                <th>电子邮箱</th>
                <th>电话号码</th>
                <th>备注</th>
                <th>状态</th>
                <th>建立时间</th>
                <th style="display: none;">角色</th>
                <th style="display: none;">状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${null != pageInfo.result}">
                <c:forEach var="item" items="${pageInfo.result}" step="1">
                    <tr>
                        <td>${item.id}</td>
                        <td>${item.operatorName}</td>
                        <td>${item.account}</td>
                        <td>
                            <c:if test="${null != item.roles}">
                                <c:forEach var="role" items="${item.roles}" step="1" varStatus="stat">
                                    ${role.name}<c:if test="${!stat.last}">,</c:if>
                                </c:forEach>
                            </c:if>
                        </td>
                        <td>${item.operatorCompany}</td>
                        <td>${item.operatorEmail}</td>
                        <td>${item.operatorPhone}</td>
                        <td>${item.remark}</td>
                        <td>
                            <c:if test="${item.status=='0'}">
                                正常
                            </c:if>
                            <c:if test="${item.status=='1'}">
                                关闭
                            </c:if>
                        </td>
                        <td>${item.createTime}</td>
                        <td style="display: none;">
                            <c:if test="${null != item.roles}">
                                <c:forEach var="role" items="${item.roles}" step="1" varStatus="stat">
                                    ${role.id}<c:if test="${!stat.last}">,</c:if>
                                </c:forEach>
                            </c:if>
                        </td>
                        <td style="display: none;">${item.status}</td>
                        <td>
                            <c:if test="${null != operate}">
                                <c:forEach var="item" items="${operate}" step="1">
                                    <c:if test="${item.operateType == 'EDIT'}">
                                        <a data-toggle="modal" href="#edit" class="btn btn-warning" name="editButton">修 改</a>
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

<div id="edit" class="modal hide">
    <div class="modal-header">
        <a class="close" data-dismiss="modal">&times;</a>

        <h3>修改用户</h3>
    </div>
    <div style="max-height: 300px;padding: 15px;overflow-y: auto;">
        <form class="form-horizontal" action="personalEdit.do" method="post" id="editForm">
            <fieldset>
                <div class="control-group">
                    <label class="control-label" for="editId">编号</label>

                    <div class="controls">
                        <input type="text" name="editId" id="editId"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editOperatorName">用户名称</label>

                    <div class="controls">
                        <input type="text" name="editOperatorName" id="editOperatorName" placeholder="请输入用户名称"
                               class="span3" readonly="true">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editOperatorCompany">公司名称</label>

                    <div class="controls">
                        <input type="text" name="editOperatorCompany" id="editOperatorCompany" placeholder="请输入公司名称"
                               class="span3" readonly="true">
                        
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editOperatorEmail">邮箱地址</label>

                    <div class="controls">
                        <input type="text" name="editOperatorEmail" id="editOperatorEmail" placeholder="请输入邮箱地址"
                               class="span3">
                        
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editOperatorPhone">联系电话</label>

                    <div class="controls">
                        <input type="text" name="editOperatorPhone" id="editOperatorPhone" placeholder="请输入联系电话"
                               class="span3">
                        
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editPassword">用户密码</label>

                    <div class="controls">
                        <input type="text" name="editPassword" id="editPassword" placeholder="输入用户密码,输入则修改"
                               class="span3">
                        
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editRemark">备注</label>

                    <div class="controls">
                        <textarea rows="3" class="span3" id="editRemark" name="editRemark"></textarea>
                        
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
