<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<script type="text/javascript">
    $.validator.setDefaults({
       submitHandler:function (form) {
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

    $().ready(function () {
        // validate form on keyup and submit
        $("#addForm").validate({
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
                operatorName:{
                    required:true,
                    minlength:1,
                    maxlength:100
                },
                operatorCompany:{
                    required:true,
                    minlength:1,
                    maxlength:100
                },
                operatorEmail:{
                    required:false,
                    maxlength:100
                },
                operatorPhone:{
                    required:false,
                    maxlength:20
                },
                roles:{
                    required:true,
                    min:0
                },
                accountType:{
                    required:true,
                    min:0
                },
                accountTypeId:{
                    required:function () {
                        return $("#accountType").val() != "0";
                    },
                    digits:true,
                    min:0,
                    max:100000000
                },
                remark:{
                    required:false,
                    maxlength:1024
                }
            },
            messages:{
                operatorName:{
                    required:"请输入用户名称",
                    minlength:"用户名称必须包含1个字符",
                    maxlength:"用户名称最多包含50个字符"
                },
                operatorCompany:{
                    required:"请输入公司名称",
                    minlength:"公司名称必须包含1个字符",
                    maxlength:"公司名称最多包含100个字符"
                },
                operatorEmail:{
                    maxlength:"邮箱地址最多包含100个字符"
                },
                operatorPhone:{
                    maxlength:"联系电话最多包含20个字符"
                },
                roles:{
                    required:"请选择用户角色",
                    min:"请选择用户角色"
                },
                accountType:{
                    required:"请选择账户类型",
                    min:"请选择账户类型"
                },
                accountTypeId:{
                    required:"请输入账户类型编号",
                    digits:"账户类型编号必须为整数",
                    min:"账户类型编号最小值为0",
                    max:"账户类型编号最大值为100000000"
                },
                remark:{
                    maxlength:"备注最多包含1024个字符"
                }
            }
        });

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
                editRoles:{
                    required:true,
                    min:0
                },
                editAccountType:{
                    required:true,
                    min:0
                },
                editAccountTypeId:{
                    required:function () {
                        return $("#editAccountType").val() != "0";
                    },
                    digits:true,
                    min:0,
                    max:100000000
                },
                editRemark:{
                    required:false,
                    maxlength:1024
                },
                editStatus:{
                    required:true,
                    min:0
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
                editRoles:{
                    required:"请选择用户角色",
                    min:"请选择用户角色"
                },
                editAccountType:{
                    required:"请选择账户类型",
                    min:"请选择账户类型"
                },
                editAccountTypeId:{
                    required:"请输入账户类型编号",
                    digits:"账户类型编号必须为整数",
                    min:"账户类型编号最小值为0",
                    max:"账户类型编号最大值为100000000"
                },
                editRemark:{
                    maxlength:"备注最多包含1024个字符"
                },
                editStatus:{
                    required:"请选择用户状态",
                    min:"请选择用户状态"
                }
            }
        });


        $("#accountType").change(function(){
            if ($("#accountType").val() == "0") {
                document.getElementById("accountTypeIdDiv").style.display = 'none';
            } else {
                document.getElementById("accountTypeIdDiv").style.display = 'block';
            }
        });

        $("#editAccountType").change(function(){
            if ($("#editAccountType").val() == "0") {
                document.getElementById("editAccountTypeIdDiv").style.display = 'none';
            } else {
                document.getElementById("editAccountTypeIdDiv").style.display = 'block';
            }
        });


        $('a[name="editButton"]').click(function () {
            $("#editAccountType").change();
        });


        $("#listTable").find("tr").hover(function () {
            $("#deleteId").val($(this).children("td").eq(0).html());
            $("#deleteName").val($(this).children("td").eq(1).html());
            $("#editId").val($(this).children("td").eq(0).html());
            $("#editOperatorName").val($(this).children("td").eq(1).html());
            $("#editOperatorCompany").val($(this).children("td").eq(6).html());
            $("#editOperatorEmail").val($(this).children("td").eq(7).html());
            $("#editOperatorPhone").val($(this).children("td").eq(8).html());
            $("#editRemark").val($(this).children("td").eq(9).html());

            $("#editStatus").val($(this).children("td").eq(13).html());
            $("#editAccountType").val($(this).children("td").eq(14).html());
            $("#editAccountTypeId").val($(this).children("td").eq(5).html());

            var content = $(this).children("td").eq(12).html();


            if (null != content) {
                var operateTypes = new Array();
                operateTypes = content.split(",");
                for (i = 0; i < operateTypes.length; i++) {
                    operateTypes[i] = jQuery.trim(operateTypes[i]);
                }
                $("#editRoles").val(operateTypes);
            }
        })
    });
</script>

<div class="navbar">
    <div class="navbar-inner">
        <form class="navbar-form pull-left" action="operatorQuery.do" method="post" id="queryForm">
            <input class="span2" type="text" id="name" name="name" placeholder="用户名称" value="${name}">
            <input class="span2" type="text" id="company" name="company" placeholder="公司名称" value="${company}">
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
        <table class="table table-striped  table-hover table-condensed" id="listTable"
               >
            <thead>
            <tr>
                <th>编号</th>
                <th>用户名称</th>
                <th>登陆账号</th>
                <th>角色</th>
                <th>账户类型</th>
                <th style="display: none">账户类型编号</th>
                <th>公司名称</th>
                <th>电子邮箱</th>
                <th>电话号码</th>
                <th>备注</th>
                <th>状态</th>
                <th>建立时间</th>
                <th style="display: none;">角色</th>
                <th style="display: none;">状态</th>
                <th style="display: none;">账户类型</th>
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
                        <td>
                            <c:if test="${item.accountType == '0'}">
                                管理员
                            </c:if>
                            <c:if test="${item.accountType == '1'}">
                                CP
                            </c:if>
                            <c:if test="${item.accountType == '2'}">
                                SP
                            </c:if>
                            <c:if test="${item.accountType == '3'}">
                                渠道
                            </c:if>
                            <c:if test="${item.accountType == '4'}">
                                下游通道商
                            </c:if>
                        </td>
                        <td style="display: none">${item.accountTypeId}</td>
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
                        <td style="display: none;">${item.accountType}</td>
                        <td>
                            <c:if test="${null != operate}">
                                <c:forEach var="item" items="${operate}" step="1">
                                    <c:if test="${item.operateType == 'EDIT'}">
                                        <a data-toggle="modal" href="#edit" class="btn btn-warning" name="editButton">修
                                            改</a>
                                    </c:if>
                                    <c:if test="${item.operateType == 'DELETE'}">
                                        <a data-toggle="modal" href="#delete" class="btn btn-danger">删 除</a>
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


<div id="add" class="modal hide">
    <div class="modal-header">
        <a class="close" data-dismiss="modal">&times;</a>

        <h3>新建用户</h3>
    </div>
    <div style="max-height: 300px;padding: 15px;overflow-y: auto;">
        <form class="form-horizontal" action="operatorAdd.do" method="post" id="addForm">
            <fieldset>
                <div class="control-group">
                    <label class="control-label" for="operatorName">用户名称</label>

                    <div class="controls">
                        <input type="text" name="operatorName" id="operatorName" placeholder="请输入用户名称"
                               class="span3">
                        
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="operatorCompany">公司名称</label>

                    <div class="controls">
                        <input type="text" name="operatorCompany" id="operatorCompany" placeholder="请输入公司名称"
                               class="span3">
                        
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="account">账号</label>

                    <div class="controls">
                        <input type="text" name="account" id="account" placeholder="请输入用户账号"
                               class="span3">

                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="password">密码</label>

                    <div class="controls">
                        <input type="text" name="password" id="password" placeholder="请输入用户密码"
                               class="span3">

                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="operatorEmail">邮箱地址</label>

                    <div class="controls">
                        <input type="text" name="operatorEmail" id="operatorEmail" placeholder="请输入邮箱地址"
                               class="span3">
                        
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="operatorPhone">联系电话</label>

                    <div class="controls">
                        <input type="text" name="operatorPhone" id="operatorPhone" placeholder="请输入联系电话"
                               class="span3">
                        
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="roles">用户角色</label>

                    <div class="controls">
                        <select id="roles" name="roles" multiple="multiple" class="span3" size="10">
                            <c:if test="${null != roleList}">
                                <c:forEach var="item" items="${roleList}" step="1">
                                    <option value="${item.id}">${item.name}</option>
                                </c:forEach>
                            </c:if>
                        </select>
                        
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="accountType"> 账户类型</label>

                    <div class="controls">
                        <select id="accountType" name="accountType" class="span3">
                            <option value="0">管理员</option>
                            <option value="1">CP</option>
                            <option value="2">SP</option>
                            <option value="3">渠道</option>
                            <option value="4">下游通道商</option>
                        </select>
                        
                    </div>
                </div>
                <div class="control-group" id="accountTypeIdDiv" style="display: none">
                    <label class="control-label" for="accountTypeId"> 账户类型编号</label>

                    <div class="controls">
                        <input type="text" name="accountTypeId" id="accountTypeId" placeholder="请输入账户类型编号"
                               class="span3">
                        
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="remark">备注</label>

                    <div class="controls">
                        <textarea rows="3" class="span3" id="remark" name="remark"></textarea>
                        
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

<div id="edit" class="modal hide">
    <div class="modal-header">
        <a class="close" data-dismiss="modal">&times;</a>

        <h3>修改用户</h3>
    </div>
    <div style="max-height: 300px;padding: 15px;overflow-y: auto;">
        <form class="form-horizontal" action="operatorEdit.do" method="post" id="editForm">
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
                               class="span3">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editOperatorCompany">公司名称</label>

                    <div class="controls">
                        <input type="text" name="editOperatorCompany" id="editOperatorCompany" placeholder="请输入公司名称"
                               class="span3">
                        
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
                    <label class="control-label" for="editRoles">用户角色</label>

                    <div class="controls">
                        <select id="editRoles" name="editRoles" multiple="multiple" class="span3" size="10">
                            <c:if test="${null != roleList}">
                                <c:forEach var="item" items="${roleList}" step="1">
                                    <option value="${item.id}">${item.name}</option>
                                </c:forEach>
                            </c:if>
                        </select>
                        
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editAccountType"> 账户类型</label>

                    <div class="controls">
                        <select id="editAccountType" name="editAccountType" class="span3">
                            <option value="0">管理员</option>
                            <option value="1">CP</option>
                            <option value="2">SP</option>
                            <option value="3">渠道</option>
                            <option value="4">下游通道商</option>
                        </select>
                        
                    </div>
                </div>
                <div class="control-group" id="editAccountTypeIdDiv" style="display: none">
                    <label class="control-label" for="editAccountTypeId"> 账户类型编号</label>

                    <div class="controls">
                        <input type="text" name="editAccountTypeId" id="editAccountTypeId" placeholder="请输入账户类型编号"
                               class="span3">
                        
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editRemark">备注</label>

                    <div class="controls">
                        <textarea rows="3" class="span3" id="editRemark" name="editRemark"></textarea>
                        
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


<div id="delete" class="modal hide">
    <div class="modal-header">
        <a class="close" data-dismiss="modal">&times;</a>

        <h3>删除用户</h3>
    </div>
    <div style="max-height: 300px;padding: 15px;overflow-y: auto;">
        <form class="form-horizontal" action="operatorDelete.do" method="post" id="deleteForm">
            <fieldset>
                <div class="control-group">
                    <label class="control-label" for="deleteId">编号</label>

                    <div class="controls">
                        <input type="text" name="deleteId" id="deleteId"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="deleteName">用户名称</label>

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
