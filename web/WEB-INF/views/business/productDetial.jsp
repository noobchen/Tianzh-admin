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
            $("#editKeyType").val($(this).children("td").eq(2).text());
            $("#editThirdPartyKey").val($(this).children("td").eq(5).text());
            $("#editThirdPartyAccount").val($(this).children("td").eq(6).text());
            $("#editThirdPartyPwd").val($(this).children("td").eq(7).text());
            $("#editStatus").val($(this).children("td").eq(6).text());

            $("#delThirdPartyKey").val($(this).children("td").eq(5).text());
            $("#deleteKeyType").val($.trim($(this).children("td").eq(4).text()));
            $("#deleteId").val($(this).children("td").eq(0).text());
        });


        $("#feeListTable").find("tr").hover(function () {
            $("#editFeePointId").val($(this).children("td").eq(0).text());
            $("#editFeePointName").val($(this).children("td").eq(3).text());
            $("#editFeePointStr").val($(this).children("td").eq(4).text());
            $("#editFeePointPrice").val($(this).children("td").eq(5).text());
            $("#editFeePointDesc").val($(this).children("td").eq(6).text());


            $("#delFeePointId").val($(this).children("td").eq(0).text());
            $("#delFeePointStr").val($(this).children("td").eq(4).text());
            $("#delFeePointName").val($.trim($(this).children("td").eq(3).text()));
        });
    })
</script>

<div style="border: 1px solid #B4B4B4; border-radius: 8px;  padding: 10px">
    <%-- 导航栏--%>
    <div class="navbar">
        <div class="navbar-inner">
            <form class="navbar-form pull-left" action="product.do" method="post" id="queryForm">
                <input class="span2" type="text" name="method" value="productDetial" style="display: none">
                <c:if test="${null != operate}">
                    <c:forEach var="item" items="${operate}" step="1">
                        <c:if test="${item.operateType == 'ADD'}">
                            <a data-toggle="modal" href="#add" class="btn btn-success">新增配置</a>
                        </c:if>
                    </c:forEach>
                </c:if>
            </form>
        </div>
    </div>


    <%-- 内容--%>
    <div class="row-fluid"
            >
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
                <h4>产品配置</h4>
            </div>
            <table class="table table-striped table-hover table-bordered" id="listTable"
                    >
                <thead>
                <tr>
                    <th style="display: none">编号</th>
                    <th style="display: none">产品编号</th>
                    <th style="display: none">类型</th>
                    <th>产品</th>
                    <th>类型</th>
                    <th>key</th>
                    <th>账号</th>
                    <th>密码</th>
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
                            <td style="display: none">${productId}</td>
                            <td style="display: none">${item.keyType}</td>
                            <td>${item.productName}</td>
                            <td>
                                <c:if test="${item.keyType == 0}">友盟</c:if>
                                <c:if test="${item.keyType == 1}">TalkingData</c:if>
                                <c:if test="${item.keyType == 2}">易接</c:if>
                                <c:if test="${item.keyType == 3}">乐途</c:if>
                                <c:if test="${item.keyType == 4}">元朗</c:if>
                                <c:if test="${item.keyType == 5}">掌支付</c:if>
                                <c:if test="${item.keyType == 6}">朱雀</c:if>
                            </td>
                            <td>${item.thirdPartyKey}</td>
                            <td>${item.thirdPartyAccount}</td>
                            <td>${item.thirdPartyPwd}</td>
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
                                            <a data-toggle="modal" href="#delete" class="btn btn-mini btn-danger ">删
                                                除</a>
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
</div>


<div style="border: 1px solid #B4B4B4; border-radius: 8px;  padding: 10px ;margin-top: 35px;margin-bottom: 50px">
    <%-- 导航栏--%>
    <div class="navbar">
        <div class="navbar-inner">
            <form class="navbar-form pull-left" action="product.do" method="post" id="queryForm2">
                <input class="span2" type="text" name="method" value="productDetial" style="display: none">
                <c:if test="${null != operate}">
                    <c:forEach var="item" items="${operate}" step="1">
                        <c:if test="${item.operateType == 'ADD'}">
                            <a data-toggle="modal" href="#addFee" class="btn btn-success">新增计费点</a>
                        </c:if>
                    </c:forEach>
                </c:if>
            </form>
        </div>
    </div>


    <%-- 内容--%>
    <div class="row-fluid">
        <!--提示语-->
        <c:if test="${null != param.feePointresult}">
            <c:if test="${false == param.feePointresult}">
                <div class="alert alert-error">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                        ${param.feePointerrorMsg}
                </div>
            </c:if>
            <c:if test="${true == param.feePointresult}">
                <div class="alert alert-success">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                        ${param.feePointerrorMsg}
                </div>
            </c:if>
        </c:if>

        <c:if test="${null != feePoints}">
            <div class="mod-header radius">
                <h4>计费点配置</h4>
            </div>
            <table class="table table-striped table-hover table-bordered" id="feeListTable"
                    >
                <thead>
                <tr>
                    <th style="display: none">编号</th>
                    <th style="display: none">产品编号</th>
                    <th style="display: none">产品名</th>
                    <th>道具名称</th>
                    <th>道具Id</th>
                    <th>创建时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${null != feePoints}">
                    <c:forEach var="item" items="${feePoints}" step="1">
                        <tr>
                            <td style="display: none">${item.id}</td>
                            <td style="display: none">${productId}</td>
                            <td style="display: none">${productName}</td>
                            <td><a href="${pageContext.request.contextPath}/product.do?method=feepoint&feepointId=${item.id}&feePointStr=${item.feePointId}">${item.feePointName}</a></td>
                            <td>${item.feePointId}</td>

                            <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>
                                <c:if test="${null != operate}">
                                    <c:forEach var="item" items="${operate}" step="1">
                                        <c:if test="${item.operateType == 'EDIT'}">
                                            <a data-toggle="modal" href="#editFee" class="btn btn-mini btn-warning "
                                               name="editButton">修
                                                改</a>
                                        </c:if>
                                        <c:if test="${item.operateType == 'DELETE'}">
                                            <a data-toggle="modal" href="#delFee" class="btn btn-mini btn-danger ">删
                                                除</a>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>

        </c:if>
    </div>
</div>

<%-- 新增--%>
<div id="add" class="modal hide">
    <div class="modal-header">
        <a class="close" data-dismiss="modal">&times;</a>

        <h3>新增</h3>
    </div>
    <div style="max-height: 300px;padding: 15px;overflow-y: auto;">
        <form class="form-horizontal" action="productDetialAdd.do" method="post" id="addForm">
            <fieldset>
                <div class="control-group" style="display: none">
                    <label class="control-label" for="addProductId">产品Id</label>

                    <div class="controls">
                        <input type="text" name="addProductId" id="addProductId"
                               class="span3" value="${productId}">
                    </div>
                </div>
                <div class="control-group" style="display: none">
                    <label class="control-label" for="addProductName">产品名</label>

                    <div class="controls">
                        <input type="text" name="addProductName" id="addProductName"
                               class="span3" value="${productName}">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="addKeyType">类型</label>

                    <div class="controls">
                        <select id="addKeyType" name="addKeyType" class="span3">
                            <option value="0">友盟</option>
                            <option value="1">TalkingData</option>
                            <option value="2">易接</option>
                            <option value="3">乐途</option>
                            <option value="4">元朗</option>
                            <option value="5">掌支付</option>
                            <option value="6">朱雀</option>
                        </select>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="addThirdPartyKey">key</label>

                    <div class="controls">
                        <input type="text" name="addThirdPartyKey" id="addThirdPartyKey"
                               class="span3">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="addThirdPartyAccount">账号</label>

                    <div class="controls">
                        <input type="text" name="addThirdPartyAccount" id="addThirdPartyAccount"
                               class="span3">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="addThirdPartyPwd">密码</label>

                    <div class="controls">
                        <input type="text" name="addThirdPartyPwd" id="addThirdPartyPwd"
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


<div id="addFee" class="modal hide">
    <div class="modal-header">
        <a class="close" data-dismiss="modal">&times;</a>

        <h3>新增计费点</h3>
    </div>
    <div style="max-height: 300px;padding: 15px;overflow-y: auto;">
        <form class="form-horizontal" action="feePointAdd.do" method="post" id="addFeeForm">
            <fieldset>
                <div class="control-group" style="display: none">
                    <label class="control-label" for="addFeeProductId">产品Id</label>

                    <div class="controls">
                        <input type="text" name="addFeeProductId" id="addFeeProductId"
                               class="span3" value="${productId}">
                    </div>
                </div>
                <div class="control-group" style="display: none">
                    <label class="control-label" for="addFeeProductName">产品名</label>

                    <div class="controls">
                        <input type="text" name="addFeeProductName" id="addFeeProductName"
                               class="span3" value="${productName}">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="addFeeName">道具名称</label>

                    <div class="controls">
                        <input type="text" name="addFeeName" id="addFeeName"
                               class="span3">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="addFeePrice">默认单价</label>

                    <div class="controls">
                        <input type="text" name="addFeePrice" id="addFeePrice"
                               class="span3" placeholder="单位：分">
                    </div>
                </div>

                <div class="control-group">
                    <div style="text-align:center;font-size: 17px;font-weight: bold;line-height: 20px; ">
                        &lt;-------乐途支付计费参数--------&gt;
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="addLetuPayPointNum">计费点编号</label>

                    <div class="controls">
                        <input type="text" name="addLetuPayPointNum" id="addLetuPayPointNum"
                               class="span3" placeholder="接入SDK时提交的计费点编号">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="addLetuPrice">计费点单价</label>

                    <div class="controls">
                        <input type="text" name="addLetuPrice" id="addLetuPrice"
                               class="span3" placeholder="假如价格同默认单价，可不输入">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="addLetuFeeDesc">计费点描述</label>

                    <div class="controls">
                        <input type="text" name="addLetuFeeDesc" id="addLetuFeeDesc"
                               class="span3" placeholder="乐途计费点描述">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="addLetuSdkChannelId">SDK渠道ID</label>

                    <div class="controls">
                        <input type="text" name="addLetuSdkChannelId" id="addLetuSdkChannelId"
                               class="span3" placeholder="SDK渠道ID，默认值1000">
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="addLetuPayType">付费类型</label>

                    <div class="controls">
                        <input type="text" name="addLetuPayType" id="addLetuPayType"
                               class="span3" placeholder="付费类型，0：激活，1：道具">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="addLetuGameType">游戏类型</label>

                    <div class="controls">
                        <input type="text" name="addLetuGameType" id="addLetuGameType"
                               class="span3" placeholder="0：单机，1：强联网，2：弱联网">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="addLetuShowUIKey">付费界面KEY值</label>

                    <div class="controls">
                        <input type="text" name="addLetuShowUIKey" id="addLetuShowUIKey"
                               class="span3" placeholder="付费界面KEY值,没有则不填">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="addLetuMerchantKey">商户秘钥</label>

                    <div class="controls">
                        <input type="text" name="addLetuMerchantKey" id="addLetuMerchantKey"
                               class="span3" placeholder="乐途提供的商户秘钥">
                    </div>
                </div>
                <div class="control-group">
                    <div style="text-align:center;font-size: 17px;font-weight: bold;line-height: 20px; ">
                        &lt;-------元朗支付计费参数--------&gt;
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="addYLIsOnline">联网游戏</label>

                    <div class="controls">
                        <input type="text" name="addYLIsOnline" id="addYLIsOnline"
                               class="span3" placeholder="1：联网游戏，0：单机游戏">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="addYLGoodsId">商品编号</label>

                    <div class="controls">
                        <input type="text" name="addYLGoodsId" id="addYLGoodsId"
                               class="span3" placeholder="元朗后台生成商品编号">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="addYlPrice">计费点单价</label>

                    <div class="controls">
                        <input type="text" name="addYlPrice" id="addYlPrice"
                               class="span3" placeholder="假如价格同默认单价，可不输入">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="addYlFeeDesc">计费点描述</label>

                    <div class="controls">
                        <input type="text" name="addYlFeeDesc" id="addYlFeeDesc"
                               class="span3" placeholder="元朗计费点描述,可不填，非必须">
                    </div>
                </div>
                <div class="control-group">
                    <div style="text-align:center;font-size: 17px;font-weight: bold;line-height: 20px; ">
                        &lt;-------掌支付计费参数--------&gt;
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="addZhangKey">用户私钥</label>

                    <div class="controls">
                        <input type="text" name="addZhangKey" id="addZhangKey"
                               class="span3" placeholder="掌支付提供用户私钥">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="addZhangPricePointId">计费点Id</label>

                    <div class="controls">
                        <input type="text" name="addZhangPricePointId" id="addZhangPricePointId"
                               class="span3" placeholder="掌支付提供计费点Id">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="addZhPrice">计费点单价</label>

                    <div class="controls">
                        <input type="text" name="addZhPrice" id="addZhPrice"
                               class="span3" placeholder="假如价格同默认单价，可不输入">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="addZhFeeDesc">计费点描述</label>

                    <div class="controls">
                        <input type="text" name="addZhFeeDesc" id="addZhFeeDesc"
                               class="span3" placeholder="掌支付计费点描述">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="addZhangAppVersion">应用版本号</label>

                    <div class="controls">
                        <input type="text" name="addZhangAppVersion" id="addZhangAppVersion"
                               class="span3" placeholder="掌支付提供应用版本号，默认为1000">
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
        <form class="form-horizontal" action="productDetialEdit.do" method="post" id="editForm">
            <fieldset>
                <div class="control-group" style="display: none">
                    <label class="control-label" for="editId">编号</label>

                    <div class="controls">
                        <input type="text" name="editId" id="editId"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="control-group" style="display: none">
                    <label class="control-label" for="editProductId">产品Id</label>

                    <div class="controls">
                        <input type="text" name="editProductId" id="editProductId"
                               class="span3" value="${productId}">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editKeyType">类型</label>

                    <div class="controls">
                        <select id="editKeyType" name="editKeyType" class="span3">
                            <option value="0">友盟</option>
                            <option value="1">TalkingData</option>
                            <option value="2">易接</option>
                            <option value="3">乐途</option>
                            <option value="4">元朗</option>
                            <option value="5">掌支付</option>
                            <option value="6">朱雀</option>

                        </select>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editThirdPartyKey">key</label>

                    <div class="controls">
                        <input type="text" name="editThirdPartyKey" id="editThirdPartyKey"
                               class="span3">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editThirdPartyAccount">账号</label>

                    <div class="controls">
                        <input type="text" name="editThirdPartyAccount" id="editThirdPartyAccount"
                               class="span3">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editThirdPartyPwd">密码</label>

                    <div class="controls">
                        <input type="text" name="editThirdPartyPwd" id="editThirdPartyPwd"
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

<div id="editFee" class="modal hide">
    <div class="modal-header">
        <a class="close" data-dismiss="modal">&times;</a>

        <h3>修改</h3>
    </div>
    <div style="max-height: 300px;padding: 15px;overflow-y: auto;">
        <form class="form-horizontal" action="feePointEdit.do" method="post" id="editFeePoint">
            <fieldset>
                <div class="control-group" style="display: none">
                    <label class="control-label" for="editFeePointId">编号</label>

                    <div class="controls">
                        <input type="text" name="editFeePointId" id="editFeePointId"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="control-group" style="display: none">
                    <label class="control-label" for="editFeePointStr">计费点编号</label>

                    <div class="controls">
                        <input type="text" name="editFeePointStr" id="editFeePointStr"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="control-group" style="display: none">
                    <label class="control-label" for="editFeeProductId">产品Id</label>

                    <div class="controls">
                        <input type="text" name="editFeeProductId" id="editFeeProductId"
                               class="span3" value="${productId}">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editFeePointName">道具名称</label>

                    <div class="controls">
                        <input type="text" name="editFeePointName" id="editFeePointName"
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
        <form class="form-horizontal" action="productDetialDel.do" method="post" id="deleteForm">
            <fieldset>
                <div class="control-group" style="display: none">
                    <label class="control-label" for="deleteId">编号</label>

                    <div class="controls">
                        <input type="text" name="deleteId" id="deleteId"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="control-group" style="display: none">
                    <label class="control-label" for="delProductId">产品Id</label>

                    <div class="controls">
                        <input type="text" name="delProductId" id="delProductId"
                               class="span3" value="${productId}">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="deleteKeyType">类型</label>

                    <div class="controls">
                        <input type="text" name="deleteKeyType" id="deleteKeyType"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="delThirdPartyKey">Key</label>

                    <div class="controls">
                        <input type="text" name="delThirdPartyKey" id="delThirdPartyKey"
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


<div id="delFee" class="modal hide">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>

        <h3>删除</h3>
    </div>
    <div style="max-height: 300px;padding: 15px;overflow-y: auto;">
        <form class="form-horizontal" action="feePointDel.do" method="post" id="delFeePoint">
            <fieldset>
                <div class="control-group" style="display: none">
                    <label class="control-label" for="delFeePointId">编号</label>

                    <div class="controls">
                        <input type="text" name="delFeePointId" id="delFeePointId"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="control-group" style="display: none">
                    <label class="control-label" for="delFeePointStr">计费点编号</label>

                    <div class="controls">
                        <input type="text" name="delFeePointStr" id="delFeePointStr"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="control-group" style="display: none">
                    <label class="control-label" for="delFeeProductId">产品Id</label>

                    <div class="controls">
                        <input type="text" name="delFeeProductId" id="delFeeProductId"
                               class="span3" value="${productId}">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="delFeePointName">道具名称</label>

                    <div class="controls">
                        <input type="text" name="delFeePointName" id="delFeePointName"
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