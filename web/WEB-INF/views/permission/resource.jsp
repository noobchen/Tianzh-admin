<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<script type="text/javascript">
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

    $().ready(function () {
        // validate form on keyup and submit
        $("#addForm").validate({
//        默认用label显示错误消息，可以通过errorElement属性来修改，errorElement: 错误消息的html标签
            errorElement: 'span',
//        验证出错的css class默认是error，通过errorClass可以修改，errorClass: 验证出错时使用的css class
            errorClass: 'help-inline',

//            highlight: 高亮显示，默认是添加errorClass
            highlight: function (element, errorClass) {
                $(element).parent().parent().removeClass('success');
                $(element).parent().parent().addClass('error');
            },

            //            unhighlight: 和highlight对应，反高亮显示
            unhighlight: function (element, errorClass) {
                $(element).parent().parent().removeClass('error');
                $(element).parent().parent().addClass('success');
            },
            //校验规则
            rules: {
                resourceName: {
                    required: true,
                    minlength: 1,
                    maxlength: 50
                },
                baseUrl: {
                    required: false,
                    maxlength: 200
                },
                resourceUrl: {
                    required: true,
                    minlength: 1,
                    maxlength: 200
                },
                type: {
                    required: true,
                    min: 1
                },
                parentId: {
                    required: function () {
                        return $("#type").val() == "2";
                    },
                    min: 0
                },
                operateType: {
                    required: function () {
                        return $("#type").val() == "2";
                    },
                    minlength: 1
                },
                index: {
                    required: true,
                    digits: true,
                    min: 0,
                    max: 100
                }
            },
            //校验提示信息
            messages: {
                resourceName: {
                    required: "请输入资源名称",
                    minlength: "资源名称必须包含1个字符",
                    maxlength: "资源名称最多包含50个字符"
                },
                baseUrl: {
                    maxlength: "主路径最多包含200个字符"
                },
                resourceUrl: {
                    required: "请输入URL地址",
                    minlength: "URL必须包含1个字符",
                    maxlength: "URL最多包含200个字符"
                },
                type: {
                    required: "请选择类型",
                    min: "请选择类型"
                },
                parentId: {
                    required: "请选择所属模块",
                    min: "请选择所属模块"
                },
                operateType: {
                    required: "<font color='#b94a48'>请选择权限</font>",
                    minlength: "<font color='#b94a48'>请选择权限</font>"
                },
                index: {
                    required: "请输入排列序号",
                    digits: "排列序号必须为整数",
                    min: "排列序号最少为0",
                    max: "排列序号最大为100"
                }
            },

            //手动设置错误信息的显示方式
            errorPlacement: function (error, element) {
                if (element.is(':radio') || element.is(':checkbox')) {
                    var eid = element.attr('name');
                    error.appendTo(element.parent().parent());
                } else {
                    error.insertAfter(element);
                }
            }
        });


        $("#editForm").validate({
            errorElement: 'span',
            errorClass: 'help-inline',
            highlight: function (element, errorClass) {
                $(element).parent().parent().removeClass('success');
                $(element).parent().parent().addClass('error');
            },
            unhighlight: function (element, errorClass) {
                $(element).parent().parent().removeClass('error');
                $(element).parent().parent().addClass('success');
            },
            rules: {
                editResourceName: {
                    required: true,
                    minlength: 1,
                    maxlength: 50
                },
                editBaseUrl: {
                    required: false,
                    maxlength: 200
                },
                editResourceUrl: {
                    required: true,
                    minlength: 1,
                    maxlength: 200
                },
                editType: {
                    required: true,
                    min: 1
                },
                editParentId: {
                    required: function () {
                        return $("#editType").val() == "2";
                    },
                    min: 0
                },
                editOperateType: {
                    required: function () {
                        return $("#editType").val() == "2";
                    },
                    minlength: 1
                },
                editIndex: {
                    required: true,
                    digits: true,
                    min: 0,
                    max: 100
                }
            },
            messages: {
                editResourceName: {
                    required: "请输入资源名称",
                    minlength: "资源名称必须包含1个字符",
                    maxlength: "资源名称最多包含50个字符"
                },
                editBaseUrl: {
                    maxlength: "主路径最多包含200个字符"
                },
                editResourceUrl: {
                    required: "请输入URL地址",
                    minlength: "URL必须包含1个字符",
                    maxlength: "URL必须包含200个字符"
                },
                editType: {
                    required: "请选择类型",
                    min: "请选择类型"
                },
                editParentId: {
                    required: "请选择所属模块",
                    min: "请选择所属模块"
                },
                editOperateType: {
                    required: "<font color='#b94a48'>请选择权限</font>",
                    minlength: "<font color='#b94a48'>请选择权限</font>"
                },
                editIndex: {
                    required: "请输入排列序号",
                    digits: "排列序号必须为整数",
                    min: "排列序号最少为0",
                    max: "排列序号最大为100"
                }
            },
            errorPlacement: function (error, element) {
                if (element.is(':radio') || element.is(':checkbox')) {
                    var eid = element.attr('name');
                    error.appendTo(element.parent().parent());
                } else {
                    error.insertAfter(element);
                }
            }
        });

        $("#type").change(function () {
            if ($("#type").val() == "1") {
                document.getElementById("parentIdDiv").style.display = 'none';
                document.getElementById("operateTypeDiv").style.display = 'none';
                document.getElementById("baseUrlDiv").style.display = 'block';

            } else {
                document.getElementById("parentIdDiv").style.display = 'block';
                document.getElementById("operateTypeDiv").style.display = 'block';
                document.getElementById("baseUrlDiv").style.display = 'none';
            }

        });

        $("#editType").change(function () {
            if ($("#editType").val() == "1") {
                document.getElementById("editParentIdDiv").style.display = 'none';
                document.getElementById("editOperateTypeDiv").style.display = 'none';
                document.getElementById("editBaseUrlDiv").style.display = 'block';
            } else {
                document.getElementById("editParentIdDiv").style.display = 'block';
                document.getElementById("editOperateTypeDiv").style.display = 'block';
                document.getElementById("editBaseUrlDiv").style.display = 'none';
            }

        });

        $('a[name="editButton"]').click(function () {
            $("#editType").change();
        });


        $("#listTable").find("tr").hover(function () {
            $("#deleteId").val($(this).children("td").eq(0).html());
            $("#deleteName").val($(this).children("td").eq(1).html());

            $("#editId").val($(this).children("td").eq(0).html());
            $("#editResourceName").val($(this).children("td").eq(1).html());
            $("#editBaseUrl").val($(this).children("td").eq(4).html());
            $("#editResourceUrl").val($(this).children("td").eq(5).html());
            $("#editType").val($(this).children("td").eq(2).html());
            $("#editParentId").val($(this).children("td").eq(7).html());
            $("#editIndex").val($(this).children("td").eq(9).html());


            var content = $(this).children("td").eq(6).html();

            if (null != content) {
                var operateTypes = new Array();
                operateTypes = content.split(",");

                var tem = new Array();


                for (i = 0; i < operateTypes.length; i++) {
                    if (jQuery.trim(operateTypes[i]) == "QUERY") {
                        tem[0] = true;
                    }
                    if (jQuery.trim(operateTypes[i]) == "ADD") {
                        tem[1] = true;
                    }
                    if (jQuery.trim(operateTypes[i]) == "EDIT") {
                        tem[2] = true;
                    }
                    if (jQuery.trim(operateTypes[i]) == "DELETE") {
                        tem[3] = true;
                    }
                }
                if (tem[0] == true) {
                    $("#editOperateType_1").attr("checked", true);
                } else {
                    $("#editOperateType_1").attr("checked", false);
                }

                if (tem[1] == true) {
                    $("#editOperateType_2").attr("checked", true);
                } else {
                    $("#editOperateType_2").attr("checked", false);
                }

                if (tem[2] == true) {
                    $("#editOperateType_3").attr("checked", true);
                } else {
                    $("#editOperateType_3").attr("checked", false);
                }

                if (tem[3] == true) {
                    $("#editOperateType_4").attr("checked", true);
                } else {
                    $("#editOperateType_4").attr("checked", false);
                }
            }
        })
    });
</script>

<div class="navbar">
    <div class="navbar-inner">
        <form class="navbar-form pull-left" action="resourceQuery.do" method="post" id="queryForm">
            <input class="span2" type="text" id="name" name="name" placeholder="资源名称" value="${name}">
            <input class="span2" type="text" id="url" name="url" placeholder="URL地址" value="${url}">
            <select class="span2" id="resourceType" name="type">
                <option value="">请选择类型</option>
                <option value="1">模块</option>
                <option value="2">菜单</option>
            </select>
            <input type="hidden" id="typeVar" value="${type}">
            <script type="text/javascript">
                $("#resourceType").val($("#typeVar").val());
            </script>
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
                <th>资源名称</th>
                <th style="display: none">类型</th>
                <th>类型</th>
                <th>主路径</th>
                <th>URL</th>
                <th>权限</th>
                <th style="display: none">所属模块</th>
                <th>所属模块</th>
                <th>排序</th>
                <th>建立时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${null != pageInfo.result}">
                <c:forEach var="item" items="${pageInfo.result}" step="1">
                    <tr>
                        <td>${item.id}</td>
                        <td>${item.resourceName}</td>
                        <td style="display: none">${item.type}</td>
                        <td>
                            <c:if test="${item.type == 1}">
                                模块
                            </c:if>
                            <c:if test="${item.type == 2}">
                                菜单
                            </c:if>
                        </td>
                        <td>${item.baseUrl}</td>
                        <td>${item.url}</td>
                        <td>
                            <c:if test="${null != item.operates}">
                                <c:forEach var="operate" items="${item.operates}" step="1" varStatus="stat">
                                    ${operate.operateType}<c:if test="${!stat.last}">,</c:if>
                                </c:forEach>
                            </c:if>
                        </td>
                        <td style="display: none">${item.parentResource.id}</td>
                        <td>${item.parentResource.resourceName}</td>
                        <td>${item.index}</td>
                        <td>${item.createTime}</td>
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

        <h3>新建资源</h3>
    </div>
    <div style="max-height: 300px;padding: 15px;overflow-y: auto;">
        <form class="form-horizontal" action="resourceAdd.do" method="post" id="addForm">
            <fieldset>
                <div class="control-group">
                    <label class="control-label" for="resourceName">资源名称</label>

                    <div class="controls">
                        <input type="text" name="resourceName" id="resourceName" placeholder="请输入资源名称"
                               class="span3">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="type">类型</label>

                    <div class="controls">
                        <select id="type" name="type" class="span3">
                            <option value="1">模块</option>
                            <option value="2">菜单</option>
                        </select>
                    </div>
                </div>
                <div class="control-group" id="baseUrlDiv">
                    <label class="control-label" for="baseUrl">主路径</label>

                    <div class="controls">
                        <input type="text" name="baseUrl" id="baseUrl" placeholder="请输入主路径,为空表示本应用"
                               class="span3">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="resourceUrl">URL</label>

                    <div class="controls">
                        <input type="text" name="resourceUrl" id="resourceUrl" placeholder="请输入URL"
                               class="span3">

                    </div>
                </div>
                <div class="control-group" id="parentIdDiv" style="display: none">
                    <label class="control-label" for="parentId">所属模块</label>

                    <div class="controls">
                        <select id="parentId" name="parentId" class="span3">
                            <option value="">请选择所属模块</option>
                            <c:if test="${null != resourceInfos}">
                                <c:forEach var="item" items="${resourceInfos}" step="1">
                                    <option value="${item.id}">${item.resourceName}</option>
                                </c:forEach>
                            </c:if>
                        </select>

                    </div>
                </div>
                <div class="control-group" id="operateTypeDiv" style="display: none;">
                    <label class="control-label" for="operateType">操作权限</label>

                    <div class="controls">
                        <label class="checkbox inline">
                            <input type="checkbox" id="operateType_1" name="operateType" value="QUERY"
                                   checked="checked">查询</label>
                        <label class="checkbox inline">
                            <input type="checkbox" id="operateType_2" name="operateType" value="ADD" checked="checked">新增
                        </label>
                        <label class="checkbox inline">
                            <input type="checkbox" id="operateType_3" name="operateType" value="EDIT" checked="checked">修改
                        </label>
                        <label class="checkbox inline">
                            <input type="checkbox" id="operateType_4" name="operateType" value="DELETE"
                                   checked="checked">删除
                        </label>

                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="index">排列序号</label>

                    <div class="controls">
                        <input type="text" name="index" id="index" placeholder="数字越小越排列前"
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

<div id="edit" class="modal hide">
    <div class="modal-header">
        <a class="close" data-dismiss="modal">&times;</a>

        <h3>修改资源</h3>
    </div>
    <div style="max-height: 300px;padding: 15px;overflow-y: auto;">
        <form class="form-horizontal" action="resourceEdit.do" method="post" id="editForm">
            <fieldset>
                <div class="control-group">
                    <label class="control-label" for="editId">编号</label>

                    <div class="controls">
                        <input type="text" name="editId" id="editId"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editResourceName">资源名称</label>

                    <div class="controls">
                        <input type="text" name="editResourceName" id="editResourceName" placeholder="请输入资源名称"
                               class="span3">

                    </div>
                </div>
                <div class="control-group" id="editBaseUrlDiv">
                    <label class="control-label" for="editBaseUrl">主路径</label>

                    <div class="controls">
                        <input type="text" name="editBaseUrl" id="editBaseUrl" placeholder="请输入主路径,为空表示本应用"
                               class="span3">

                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editResourceUrl">URL</label>

                    <div class="controls">
                        <input type="text" name="editResourceUrl" id="editResourceUrl" placeholder="请输入URL"
                               class="span3">

                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editType">类型</label>

                    <div class="controls">
                        <select id="editType" name="editType" class="span3" disabled="disabled">
                            <option value="1">模块</option>
                            <option value="2">菜单</option>
                        </select>
                    </div>
                </div>
                <div class="control-group" id="editParentIdDiv" style="display: none">
                    <label class="control-label" for="editParentId">所属模块</label>

                    <div class="controls">
                        <select id="editParentId" name="editParentId" class="span3">
                            <option value="">请选择所属模块</option>
                            <c:if test="${null != resourceInfos}">
                                <c:forEach var="item" items="${resourceInfos}" step="1">
                                    <option value="${item.id}">${item.resourceName}</option>
                                </c:forEach>
                            </c:if>
                        </select>

                    </div>
                </div>
                <div class="control-group" id="editOperateTypeDiv" style="display: none;">
                    <label class="control-label" for="editOperateType">操作权限</label>

                    <div class="controls">
                        <label class="checkbox inline">
                            <input type="checkbox" id="editOperateType_1" name="editOperateType"
                                   value="QUERY">查询</label>
                        <label class="checkbox inline">
                            <input type="checkbox" id="editOperateType_2" name="editOperateType" value="ADD">新增
                        </label>
                        <label class="checkbox inline">
                            <input type="checkbox" id="editOperateType_3" name="editOperateType" value="EDIT">修改
                        </label>
                        <label class="checkbox inline">
                            <input type="checkbox" id="editOperateType_4" name="editOperateType" value="DELETE">删除
                        </label>

                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editIndex">排列序号</label>

                    <div class="controls">
                        <input type="text" name="editIndex" id="editIndex" placeholder="数字越小越排列前"
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


<div id="delete" class="modal hide">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>

        <h3>删除资源</h3>
    </div>
    <div style="max-height: 300px;padding: 15px;overflow-y: auto;">
        <form class="form-horizontal" action="resourceDelete.do" method="post" id="deleteForm">
            <fieldset>
                <div class="control-group">
                    <label class="control-label" for="deleteId">编号</label>

                    <div class="controls">
                        <input type="text" name="deleteId" id="deleteId"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="deleteName">资源名称</label>

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
