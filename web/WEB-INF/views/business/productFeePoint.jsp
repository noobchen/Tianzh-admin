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
        $("#letuListTable").find("tr").hover(function () {
            $("#editLetuId").val($(this).children("td").eq(0).text());
            $("#editLetuPayPointNum").val($(this).children("td").eq(1).text());
            $("#editLetuPayPrice").val($(this).children("td").eq(2).text());
            $("#editLetuPayFeeDesc").val($(this).children("td").eq(3).text());
            $("#editLetuSdkChannelId").val($(this).children("td").eq(4).text());
            $("#editLetuShowUIKey").val($(this).children("td").eq(7).text());
            $("#editLetuMerchantKey").val($(this).children("td").eq(8).text());


            $("#delLetuPayPointNum").val($.trim($(this).children("td").eq(1).text()));
            $("#deleteLetuId").val($(this).children("td").eq(0).text());
        });

        $("#ylListTable").find("tr").hover(function () {
            $("#editYlId").val($(this).children("td").eq(0).text());
            $("#editYlGoodsId").val($(this).children("td").eq(2).text());
            $("#editYlPrice").val($(this).children("td").eq(3).text());
            $("#editYlFeeDesc").val($(this).children("td").eq(4).text());



            $("#delYlGoodsId").val($.trim($(this).children("td").eq(2).text()));
            $("#deleteYlId").val($(this).children("td").eq(0).text());
        });


        $("#zhangListTable").find("tr").hover(function () {
            $("#editzhangId").val($(this).children("td").eq(0).text());
            $("#editzhangKey").val($(this).children("td").eq(1).text());
            $("#editzhangPriceId").val($(this).children("td").eq(2).text());
            $("#editzhangPrice").val($(this).children("td").eq(3).text());
            $("#editzhangFeeDesc").val($(this).children("td").eq(4).text());
            $("#editzhangAppVersion").val($(this).children("td").eq(5).text());


            $("#delZhangPricePointId").val($.trim($(this).children("td").eq(2).text()));
            $("#deleteZhangId").val($(this).children("td").eq(0).text());
        });
    })
</script>

<div style="border: 1px solid #B4B4B4; border-radius: 8px;  padding: 10px">
    <%-- 内容--%>
    <div class="row-fluid"
            >
        <!--提示语-->
        <c:if test="${null != param.leturesult}">
            <c:if test="${false == param.leturesult}">
                <div class="alert alert-error">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                        ${param.letuerrorMsg}
                </div>
            </c:if>
            <c:if test="${true == param.leturesult}">
                <div class="alert alert-success">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                        ${param.letuerrorMsg}
                </div>
            </c:if>
        </c:if>


            <div class="mod-header radius">
                <h4>乐途支付</h4>
            </div>
            <table class="table table-striped table-hover table-bordered" id="letuListTable"
                    >
                <thead>
                <tr>
                    <th style="display: none">编号</th>
                    <th>计费点编号</th>
                    <th>计费点单价</th>
                    <th>计费点描述</th>
                    <th>SDK渠道ID</th>
                    <th>付费类型</th>
                    <th>游戏类型</th>
                    <th>界面KEY</th>
                    <th>商户秘钥</th>
                    <th>创建时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${null != feePointLetu}">
                        <tr>
                            <td style="display: none">${feePointLetu.id}</td>
                            <td>${feePointLetu.letuPayPointNum}</td>
                            <td>${feePointLetu.letuPrice}</td>
                            <td>${feePointLetu.letuFeeDesc}</td>
                            <td>${feePointLetu.letuSdkChannelId}</td>
                            <td>
                                <c:if test="${feePointLetu.letuPayType == 0}">激活</c:if>
                                <c:if test="${feePointLetu.letuPayType == 1}">道具</c:if>
                            </td>
                            <td>
                                <c:if test="${feePointLetu.letuGameType == 0}">单机</c:if>
                                <c:if test="${feePointLetu.letuGameType == 1}">强联网</c:if>
                                <c:if test="${feePointLetu.letuGameType == 2}">弱联网</c:if>
                            </td>
                            <td>${feePointLetu.letuShowUIKey}</td>
                            <td>${feePointLetu.letuMerchantKey}</td>
                            <td><fmt:formatDate value="${feePointLetu.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>
                                <c:if test="${null != operate}">
                                    <c:forEach var="item" items="${operate}" step="1">
                                        <c:if test="${item.operateType == 'EDIT'}">
                                            <a data-toggle="modal" href="#editletu" class="btn btn-mini btn-warning "
                                               name="editButton">修
                                                改</a>
                                        </c:if>
                                        <c:if test="${item.operateType == 'DELETE'}">
                                            <a data-toggle="modal" href="#delletu" class="btn btn-mini btn-danger ">删
                                                除</a>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                            </td>
                        </tr>
                </c:if>
                </tbody>
            </table>

    </div>
</div>


<div style="border: 1px solid #B4B4B4; border-radius: 8px;  padding: 10px ;margin-top: 35px">
    <%-- 内容--%>
    <div class="row-fluid"
            >
        <!--提示语-->
        <c:if test="${null != param.ylresult}">
            <c:if test="${false == param.ylresult}">
                <div class="alert alert-error">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                        ${param.ylerrorMsg}
                </div>
            </c:if>
            <c:if test="${true == param.ylresult}">
                <div class="alert alert-success">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                        ${param.ylerrorMsg}
                </div>
            </c:if>
        </c:if>


            <div class="mod-header radius">
                <h4>元朗支付</h4>
            </div>
            <table class="table table-striped table-hover table-bordered" id="ylListTable"
                    >
                <thead>
                <tr>
                    <th style="display: none">编号</th>
                    <th>游戏类型</th>
                    <th>商品编号</th>
                    <th>计费点单价</th>
                    <th>计费点描述</th>
                    <th>创建时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${null != feePointYL}">
                        <tr>
                            <td style="display: none">${feePointYL.id}</td>

                            <td>
                                <c:if test="${feePointYL.yLIsOnline == 0}">单机</c:if>
                                <c:if test="${feePointYL.yLIsOnline == 1}">网游</c:if>
                            </td>

                            <td>${feePointYL.yLGoodsId}</td>
                            <td>${feePointYL.yLPrice}</td>
                            <td>${feePointYL.yLFeeDesc}</td>
                            <td><fmt:formatDate value="${feePointYL.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>
                                <c:if test="${null != operate}">
                                    <c:forEach var="item" items="${operate}" step="1">
                                        <c:if test="${item.operateType == 'EDIT'}">
                                            <a data-toggle="modal" href="#yledit" class="btn btn-mini btn-warning "
                                               name="editButton">修
                                                改</a>
                                        </c:if>
                                        <c:if test="${item.operateType == 'DELETE'}">
                                            <a data-toggle="modal" href="#yldel" class="btn btn-mini btn-danger ">删
                                                除</a>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                            </td>
                        </tr>
                </c:if>
                </tbody>
            </table>

    </div>
</div>




<div style="border: 1px solid #B4B4B4; border-radius: 8px;  padding: 10px ;margin-top: 35px">
    <%-- 内容--%>
    <div class="row-fluid"
            >
        <!--提示语-->
        <c:if test="${null != param.zhangresult}">
            <c:if test="${false == param.zhangresult}">
                <div class="alert alert-error">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                        ${param.zhangerrorMsg}
                </div>
            </c:if>
            <c:if test="${true == param.zhangresult}">
                <div class="alert alert-success">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                        ${param.zhangerrorMsg}
                </div>
            </c:if>
        </c:if>


            <div class="mod-header radius">
                <h4>掌支付</h4>
            </div>
            <table class="table table-striped table-hover table-bordered" id="zhangListTable"
                    >
                <thead>
                <tr>
                    <th style="display: none">编号</th>
                    <th>用户私钥</th>
                    <th>计费点Id</th>
                    <th>计费点单价</th>
                    <th>计费点描述</th>
                    <th>应用版本</th>
                    <th>创建时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${null != feePointZhang}">
                        <tr>
                            <td style="display: none">${feePointZhang.id}</td>
                            <td>${feePointZhang.zhangKey}</td>
                            <td>${feePointZhang.zhangPricePointId}</td>
                            <td>${feePointZhang.zhPrice}</td>
                            <td>${feePointZhang.zhFeeDesc}</td>
                            <td>${feePointZhang.zhangAppVersion}</td>
                            <td><fmt:formatDate value="${feePointZhang.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>
                                <c:if test="${null != operate}">
                                    <c:forEach var="item" items="${operate}" step="1">
                                        <c:if test="${item.operateType == 'EDIT'}">
                                            <a data-toggle="modal" href="#zhangedit" class="btn btn-mini btn-warning "
                                               name="editButton">修
                                                改</a>
                                        </c:if>
                                        <c:if test="${item.operateType == 'DELETE'}">
                                            <a data-toggle="modal" href="#delzhang" class="btn btn-mini btn-danger ">删
                                                除</a>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                            </td>
                        </tr>
                </c:if>
                </tbody>
            </table>

    </div>
</div>


<%-- 修改--%>
<div id="editletu" class="modal hide">
    <div class="modal-header">
        <a class="close" data-dismiss="modal">&times;</a>

        <h3>修改</h3>
    </div>
    <div style="max-height: 300px;padding: 15px;overflow-y: auto;">
        <form class="form-horizontal" action="letuEdit.do" method="post" id="letueditForm">
            <fieldset>
                <input type="hidden" name="feePointStr" value="${feePointStr}" />
                <div class="control-group" style="display: none">
                    <label class="control-label" for="editLetuId">编号</label>

                    <div class="controls">
                        <input type="text" name="editLetuId" id="editLetuId"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editLetuPayPointNum">计费点编号</label>

                    <div class="controls">
                        <input type="text" name="editLetuPayPointNum" id="editLetuPayPointNum"
                               class="span3">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editLetuPayPrice">计费点单价</label>

                    <div class="controls">
                        <input type="text" name="editLetuPayPrice" id="editLetuPayPrice"
                               class="span3">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editLetuPayFeeDesc">计费点描述</label>

                    <div class="controls">
                        <input type="text" name="editLetuPayFeeDesc" id="editLetuPayFeeDesc"
                               class="span3">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editLetuSdkChannelId">SDK渠道ID</label>

                    <div class="controls">
                        <input type="text" name="editLetuSdkChannelId" id="editLetuSdkChannelId"
                               class="span3">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editLetuPayType">付费类型</label>

                    <div class="controls">
                        <input type="text" name="editLetuPayType" id="editLetuPayType"
                               class="span3" placeholder="付费类型，0：激活，1：道具">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editLetuGameType">游戏类型</label>

                    <div class="controls">
                        <input type="text" name="editLetuGameType" id="editLetuGameType"
                               class="span3" placeholder="0：单机，1：强联网，2：弱联网">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editLetuShowUIKey">界面KEY</label>

                    <div class="controls">
                        <input type="text" name="editLetuShowUIKey" id="editLetuShowUIKey"
                               class="span3">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editLetuMerchantKey">商户秘钥</label>

                    <div class="controls">
                        <input type="text" name="editLetuMerchantKey" id="editLetuMerchantKey"
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


<div id="yledit" class="modal hide">
    <div class="modal-header">
        <a class="close" data-dismiss="modal">&times;</a>

        <h3>修改</h3>
    </div>
    <div style="max-height: 300px;padding: 15px;overflow-y: auto;">
        <form class="form-horizontal" action="ylEdit.do" method="post" id="yleditForm">
            <input type="hidden" name="feePointStr" value="${feePointStr}" />
            <fieldset>
                <div class="control-group" style="display: none">
                    <label class="control-label" for="editYlId">编号</label>

                    <div class="controls">
                        <input type="text" name="editYlId" id="editYlId"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editYlIsOnline">游戏类型</label>

                    <div class="controls">
                        <input type="text" name="editYlIsOnline" id="editYlIsOnline"
                               class="span3" placeholder="1：联网游戏，0：单机游戏">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editYlGoodsId">商品编号</label>

                    <div class="controls">
                        <input type="text" name="editYlGoodsId" id="editYlGoodsId"
                               class="span3">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editYlPrice">计费点单价</label>

                    <div class="controls">
                        <input type="text" name="editYlPrice" id="editYlPrice"
                               class="span3">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editYlFeeDesc">计费点描述</label>

                    <div class="controls">
                        <input type="text" name="editYlFeeDesc" id="editYlFeeDesc"
                               class="span3" placeholder="元朗计费点描述,可不填，非必须">
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


<div id="zhangedit" class="modal hide">
    <div class="modal-header">
        <a class="close" data-dismiss="modal">&times;</a>

        <h3>修改</h3>
    </div>
    <div style="max-height: 300px;padding: 15px;overflow-y: auto;">
        <form class="form-horizontal" action="zhangEdit.do" method="post" id="zhangeditForm">
            <input type="hidden" name="feePointStr" value="${feePointStr}" />
            <fieldset>
                <div class="control-group" style="display: none">
                    <label class="control-label" for="editzhangId">编号</label>

                    <div class="controls">
                        <input type="text" name="editzhangId" id="editzhangId"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editzhangKey">用户私钥</label>

                    <div class="controls">
                        <input type="text" name="editzhangKey" id="editzhangKey"
                               class="span3">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editzhangPriceId">计费点Id</label>

                    <div class="controls">
                        <input type="text" name="editzhangPriceId" id="editzhangPriceId"
                               class="span3">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editzhangPrice">计费点单价</label>

                    <div class="controls">
                        <input type="text" name="editzhangPrice" id="editzhangPrice"
                               class="span3">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editzhangFeeDesc">计费点描述</label>

                    <div class="controls">
                        <input type="text" name="editzhangFeeDesc" id="editzhangFeeDesc"
                               class="span3">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="editzhangAppVersion">应用版本</label>

                    <div class="controls">
                        <input type="text" name="editzhangAppVersion" id="editzhangAppVersion"
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

<div id="delletu" class="modal hide">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>

        <h3>删除</h3>
    </div>
    <div style="max-height: 300px;padding: 15px;overflow-y: auto;">
        <form class="form-horizontal" action="letuDel.do" method="post" id="letuDelForm">
            <input type="hidden" name="feePointStr" value="${feePointStr}" />
            <fieldset>
                <div class="control-group" style="display: none">
                    <label class="control-label" for="deleteLetuId">编号</label>

                    <div class="controls">
                        <input type="text" name="deleteLetuId" id="deleteLetuId"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="delLetuPayPointNum">计费点编号</label>

                    <div class="controls">
                        <input type="text" name="delLetuPayPointNum" id="delLetuPayPointNum"
                               class="span3" readonly="true">
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


<div id="yldel" class="modal hide">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>

        <h3>删除</h3>
    </div>
    <div style="max-height: 300px;padding: 15px;overflow-y: auto;">
        <form class="form-horizontal" action="ylDel.do" method="post" id="ylDelForm">
            <input type="hidden" name="feePointStr" value="${feePointStr}" />
            <fieldset>
                <div class="control-group" style="display: none">
                    <label class="control-label" for="deleteYlId">编号</label>

                    <div class="controls">
                        <input type="text" name="deleteYlId" id="deleteYlId"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="delYlGoodsId">商品编号</label>

                    <div class="controls">
                        <input type="text" name="delYlGoodsId" id="delYlGoodsId"
                               class="span3" readonly="true">
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


<div id="delzhang" class="modal hide">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>

        <h3>删除</h3>
    </div>
    <div style="max-height: 300px;padding: 15px;overflow-y: auto;">
        <form class="form-horizontal" action="zhangDel.do" method="post" id="zhangDelForm">
            <input type="hidden" name="feePointStr" value="${feePointStr}" />
            <fieldset>
                <div class="control-group" style="display: none">
                    <label class="control-label" for="deleteZhangId">编号</label>

                    <div class="controls">
                        <input type="text" name="deleteZhangId" id="deleteZhangId"
                               class="span3 disabled" readonly="true">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="delZhangPricePointId">计费点Id</label>

                    <div class="controls">
                        <input type="text" name="delZhangPricePointId" id="delZhangPricePointId"
                               class="span3" readonly="true">
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