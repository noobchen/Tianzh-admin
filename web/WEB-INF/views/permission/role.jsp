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
                roleName:{
                    required:true,
                    minlength:1,
                    maxlength:50
                },
                moduleName:{
                    required:true,
                    min:0
                }
            },
            messages:{
                roleName:{
                    required:"请输入角色名称",
                    minlength:"角色名称必须包含1个字符",
                    maxlength:"角色名称最多包含50个字符"
                },
                moduleName:{
                    required:"请选择管理模块",
                    min:"请选择管理模块"
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
                editRoleName:{
                    required:true,
                    minlength:1,
                    maxlength:50
                }
            },
            messages:{
                editRoleName:{
                    required:"请输入角色名称",
                    minlength:"角色名称必须包含1个字符",
                    maxlength:"角色名称最多包含50个字符"
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
                editRoleName:{
                    required:true,
                    minlength:1,
                    maxlength:50
                }
            },
            messages:{
                editRoleName:{
                    required:"请输入角色名称",
                    minlength:"角色名称必须包含1个字符",
                    maxlength:"角色名称最多包含50个字符"
                }
            }
        });

        $("#moduleName").change(function () {
            if ($("#moduleName").val() == '') {
                //去除下面的选项
                $("#menuNameBody").empty();
            } else {
                loadMenuData($("#menuNameBody"),$("#moduleName").val(),"false");
            }
        });

        $('a[name="editButton"]').click(function () {
            //去除下面的选项
            $("#editMenuNameBody").empty();
            loadMenuData($("#editMenuNameBody"),$("#editModuleId").val(),"true");
        });

        $("#listTable").find("tr").hover(function () {
            $("#deleteId").val($(this).children("td").eq(0).html());
            $("#deleteName").val($(this).children("td").eq(1).html());
            $("#editId").val($(this).children("td").eq(0).html());
            $("#editRoleName").val($(this).children("td").eq(1).html());

            $("#powerId").val($(this).children("td").eq(0).html());
            $("#powerName").val($(this).children("td").eq(1).html());

            $("#editModuleName").val(jQuery.trim($(this).children("td").eq(3).html()));
            $("#editModuleId").val(jQuery.trim($(this).children("td").eq(4).html()));
        })

        function loadSelectMenuData(roleId){
            function update(respdata) {
                for (var i = 0; i < respdata.length; i++) {
                    for (var j = 0; j < respdata[i].operates.length; j++) {
                        if (respdata[i].operates[j].operateType == "QUERY") {
                            var name = "#operateType_query"+ respdata[i].id;
                            $(name).attr("checked", true);
                        }
                        if (respdata[i].operates[j].operateType == "ADD") {
                            var name = "#operateType_add"+ respdata[i].id;
                            $(name).attr("checked", true);
                        }
                        if (respdata[i].operates[j].operateType == "EDIT") {
                            var name = "#operateType_edit"+ respdata[i].id;
                            $(name).attr("checked", true);
                        }
                        if (respdata[i].operates[j].operateType == "DELETE") {
                            var name = "#operateType_delete"+ respdata[i].id;
                            $(name).attr("checked", true);
                        }
                    }
                }
            }
            // ajax
            ajaxSend("getSelectMenuInfo.do", {'roleId':roleId}, update);
        }

        function loadMenuData(compent,moduleId,selectFlag) {
            function update(respdata) {
                compent.empty();
                for (var i = 0; i < respdata.length; i++) {
                    var s = "";
                    var flag = new Array();
                    for (var j = 0; j < respdata[i].operates.length; j++) {
                        if (respdata[i].operates[j].operateType == "QUERY") {
                            flag[0] = true;
                        }
                        if (respdata[i].operates[j].operateType == "ADD") {
                            flag[1] = true;
                        }
                        if (respdata[i].operates[j].operateType == "EDIT") {
                            flag[2] = true;
                        }
                        if (respdata[i].operates[j].operateType == "DELETE") {
                            flag[3] = true;
                        }
                    }

                    if (flag[0] == true) {
                        s += "<input type=\"checkbox\" id=\"operateType_query" + respdata[i].id +"\" name=\"query\" value='" + respdata[i].id + "'>查询";
                    } else {
                        s += "<input type=\"checkbox\" id=\"operateType_query" + respdata[i].id +"\" name=\"query\" value='' disabled='disabled'>查询";
                    }

                    if (flag[1] == true) {
                        s += "<input type=\"checkbox\" id=\"operateType_add" + respdata[i].id +"\" name=\"add\" value='" + respdata[i].id + "'>新增";
                    } else {
                        s += "<input type=\"checkbox\" id=\"operateType_add" + respdata[i].id +"\" name=\"add\" value=value='' disabled='disabled'>新增";
                    }

                    if (flag[2] == true) {
                        s += "<input type=\"checkbox\" id=\"operateType_edit" + respdata[i].id +"\" name=\"edit\" value='" + respdata[i].id + "'>修改";
                    } else {
                        s += "<input type=\"checkbox\" id=\"operateType_edit" + respdata[i].id +"\" name=\"edit\" value='' disabled='disabled'>修改";
                    }

                    if (flag[3] == true) {
                        s += "<input type=\"checkbox\" id=\"operateType_delete" + respdata[i].id +"\" name=\"delete\" value='" + respdata[i].id + "'>删除";
                    } else {
                        s += "<input type=\"checkbox\" id=\"operateType_delete" + respdata[i].id +"\" name=\"delete\" value='' disabled='disabled'>删除";
                    }

                    compent.append("<tr>"
                            + "<td>" + respdata[i].resourceName + "<input type=\"hidden\" name='menuId' value='" + respdata[i].id + "'>" + "</td>"
                            + "<td>"
                            + s
                            + "</td>"
                            + "</tr>");
                }

                if(selectFlag == "true"){
                    loadSelectMenuData($("#editId").val());
                }
            }

            // ajax
            ajaxSend("getMenuInfo.do", {'moduleId':moduleId}, update);
        }

        function ajaxSend(url, data, callback) {
            $.ajax({
                type:"POST",
                url:url,
                data:data,
                timeout:5000,
                success:function (msg) {
                    callback(msg);
                }
            });
        }
    });
</script>

<div class="navbar">
    <div class="navbar-inner">
        <form class="navbar-form pull-left" action="roleQuery.do" method="post" id="queryForm">
            <input class="span2" type="text" id="name" name="name" placeholder="角色名称" value="${name}">
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
        <table class="table table-striped table-hover table-condensed" id="listTable"
               >
            <thead>
            <tr>
                <th>编号</th>
                <th>角色名称</th>
                <th>建立时间</th>
                <th style="display: none">模块</th>
                <th style="display: none">模块</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${null != pageInfo.result}">
                <c:forEach var="item" items="${pageInfo.result}" step="1">
                    <tr>
                        <td>${item.id}</td>
                        <td>${item.name}</td>
                        <td>${item.createTime}</td>
                        <td style="display: none">
                            <c:if test="${null != item.resources}">
                               <c:forEach var="resource" items="${item.resources}" step="1">
                                   <c:if test="${resource.type == '1'}">
                                       ${resource.resourceName}
                                   </c:if>
                               </c:forEach>
                            </c:if>
                        </td>
                        <td style="display: none">
                            <c:if test="${null != item.resources}">
                                <c:forEach var="resource" items="${item.resources}" step="1">
                                    <c:if test="${resource.type == '1'}">
                                        ${resource.id}
                                    </c:if>
                                </c:forEach>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${null != operate}">
                                <c:forEach var="item" items="${operate}" step="1">
                                    <c:if test="${item.operateType == 'EDIT'}">
                                        <a data-toggle="modal" href="#edit" class="btn btn-warning" name="editButton">修 改</a>
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

        <h3>新建角色</h3>
    </div>
    <div style="max-height: 300px;padding: 15px;overflow-y: auto;">
        <form class="form-horizontal" action="roleAdd.do" method="post" id="addForm">
            <fieldset>
                <div class="control-group">
                    <label class="control-label" for="roleName">角色名称</label>

                    <div class="controls">
                        <input type="text" name="roleName" id="roleName" placeholder="请输入角色名称"
                               class="span3">
                        
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="moduleName">管理模块</label>

                    <div class="controls">
                        <select id="moduleName" name="moduleName" class="span3">
                            <option value="">请选择模块</option>
                            <c:if test="${null != moduleInfos}">
                                <c:forEach var="item" items="${moduleInfos}" step="1">
                                    <option value="${item.id}">${item.resourceName}</option>
                                </c:forEach>
                            </c:if>
                        </select>
                        
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="menuName">选择菜单</label>

                    <div class="controls">
                        <table class="table table-hover" id="menuName">
                            <thead>
                            <tr>
                                <th>资源名称</th>
                                <th>权限</th>
                            </tr>
                            </thead>
                            <tbody id="menuNameBody">
                            </tbody>
                        </table>
                        
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

        <h3>修改角色</h3>
    </div>
    <div style="max-height: 300px;padding: 15px;overflow-y: auto;">
        <form class="form-horizontal" action="roleEdit.do" method="post" id="editForm">
            <fieldset>
                <div class="control-group">
                    <label class="control-label" for="editId">编号</label>

                    <div class="controls">
                        <input type="text" name="editId" id="editId"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editRoleName">角色名称</label>

                    <div class="controls">
                        <input type="text" name="editRoleName" id="editRoleName" placeholder="请输入角色名称"
                               class="span3">
                        
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editModuleName">管理模块</label>

                    <div class="controls">
                        <input type="text" class="span3" id="editModuleName" name="editModuleName" value="" readonly="readonly">
                        <input type="hidden" class="span3" id="editModuleId" name="editModuleId" value="" readonly="readonly">
                        
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editMenuName">选择菜单</label>

                    <div class="controls">
                        <table class="table table-hover" id="editMenuName">
                            <thead>
                            <tr>
                                <th>资源名称</th>
                                <th>权限</th>
                            </tr>
                            </thead>
                            <tbody id="editMenuNameBody">
                            </tbody>
                        </table>
                        
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

        <h3>删除角色</h3>
    </div>
    <div style="max-height: 300px;padding: 15px;overflow-y: auto;">
        <form class="form-horizontal" action="roleDelete.do" method="post" id="deleteForm">
            <fieldset>
                <div class="control-group">
                    <label class="control-label" for="deleteId">编号</label>

                    <div class="controls">
                        <input type="text" name="deleteId" id="deleteId"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="deleteName">角色名称</label>

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
