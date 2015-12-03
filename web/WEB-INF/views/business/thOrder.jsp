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

        $("#appId").change(function () {
            loadChannelIds();

            loadFeepoints();
        });

        function loadChannelIds() {
            var appId = $("#appId").val();

            function callback(msg) {
                $("#channelId").empty();
                $("#channelId").append("<option value=\"\">请选择产品包</option>");
                for (var i = 0; i < msg.length; i++) {
                    $("#channelId").append("<option value=\"" + msg[i].prodIdentification + "\">" + msg[i].prodIdentification + "<\/option>");
                }

            }

            ajaxSend("channelIdsload.do", {'appId': appId}, callback);
        }

        function loadFeepoints() {
            var appId = $("#appId").val();

            function callback(msg) {
                $("#feepointId").empty();
                $("#feepointId").append("<option value=\"\">请选择道具</option>");
                for (var i = 0; i < msg.length; i++) {
                    $("#feepointId").append("<option value=\"" + msg[i].feePointId + "\">" + msg[i].feePointName + "<\/option>");
                }

            }

            ajaxSend("feepointIdsload.do", {'appId': appId}, callback);
        }

        function ajaxSend(url, data, callback) {
            $.ajax({
                type: "POST",
                url: url,
                data: data,
                timeout: 5000,
                success: function (msg) {
                    callback(msg);
                }
            });
        }

        $('#startDate').datepicker();
        $('#endDate').datepicker();
    });
</script>
<%-- 导航栏--%>
<div class="navbar">
    <div class="navbar-inner">
        <form class="navbar-form " action="queryThOrders.do" method="post" id="queryForm">
            <select id="appId" name="appId" class="span2">
                <option value="">产品</option>
                <c:if test="${null != products}">
                    <c:forEach var="item" items="${products}" step="1">
                        <option value="${item.id}|${item.appId}"
                                <c:if test="${selectProductId == item.appId}">selected="selected"</c:if>>${item.name}</option>
                    </c:forEach>
                </c:if>
            </select>
            <select id="channelId" name="channelId" class="span2">
                <option value="">产品包</option>
                <c:if test="${null != channelIds}">
                    <c:forEach var="item" items="${channelIds}" step="1">
                        <option value="${item.prodIdentification}"
                                <c:if test="${selectChannelId == item.prodIdentification}">selected="selected"</c:if>>${item.prodIdentification}</option>
                    </c:forEach>
                </c:if>
            </select>
            <select id="feepointId" name="feepointId" class="span2">
                <option value="">道具</option>
                <c:if test="${null != feepointIds}">
                    <c:forEach var="item" items="${feepointIds}" step="1">
                        <option value="${item.feePointId}"
                                <c:if test="${selectFeepointId == item.feePointId}">selected="selected"</c:if>>${item.feePointName}</option>
                    </c:forEach>
                </c:if>
            </select>
            <select id="payId" name="payId" class="span2">
                <option value="">计费方</option>
                <c:if test="${null != payIds}">
                    <c:forEach var="item" items="${payIds}" step="1">
                        <option value="${item.id}"
                                <c:if test="${selectPayId == item.id}">selected="selected"</c:if>>${item.name}</option>
                    </c:forEach>
                </c:if>
            </select>
            <select id="provinceId" name="provinceId" class="span2">
                <option value="">省份</option>
                <option value="1" <c:if test="${selectProvinceId == 1}">selected="selected"</c:if>>北京</option>
                <option value="2" <c:if test="${selectProvinceId == 2}">selected="selected"</c:if>>天津</option>
                <option value="3" <c:if test="${selectProvinceId == 3}">selected="selected"</c:if>>重庆</option>
                <option value="4" <c:if test="${selectProvinceId == 4}">selected="selected"</c:if>>上海</option>
                <option value="5" <c:if test="${selectProvinceId == 5}">selected="selected"</c:if>>河北</option>
                <option value="6" <c:if test="${selectProvinceId == 6}">selected="selected"</c:if>>山西</option>
                <option value="7" <c:if test="${selectProvinceId == 7}">selected="selected"</c:if>>辽宁</option>
                <option value="8" <c:if test="${selectProvinceId == 8}">selected="selected"</c:if>>吉林</option>
                <option value="9" <c:if test="${selectProvinceId == 9}">selected="selected"</c:if>>黑龙江</option>
                <option value="10" <c:if test="${selectProvinceId == 10}">selected="selected"</c:if>>江苏</option>
                <option value="11" <c:if test="${selectProvinceId == 11}">selected="selected"</c:if>>浙江</option>
                <option value="12" <c:if test="${selectProvinceId == 12}">selected="selected"</c:if>>安徽</option>
                <option value="13" <c:if test="${selectProvinceId == 13}">selected="selected"</c:if>>福建</option>
                <option value="14" <c:if test="${selectProvinceId == 14}">selected="selected"</c:if>>江西</option>
                <option value="15" <c:if test="${selectProvinceId == 15}">selected="selected"</c:if>>山东</option>
                <option value="16" <c:if test="${selectProvinceId == 16}">selected="selected"</c:if>>河南</option>
                <option value="17" <c:if test="${selectProvinceId == 17}">selected="selected"</c:if>>湖北</option>
                <option value="18" <c:if test="${selectProvinceId == 18}">selected="selected"</c:if>>湖南</option>
                <option value="19" <c:if test="${selectProvinceId == 19}">selected="selected"</c:if>>广东</option>
                <option value="20" <c:if test="${selectProvinceId == 20}">selected="selected"</c:if>>海南</option>
                <option value="21" <c:if test="${selectProvinceId == 21}">selected="selected"</c:if>>四川</option>
                <option value="22" <c:if test="${selectProvinceId == 22}">selected="selected"</c:if>>贵州</option>
                <option value="23" <c:if test="${selectProvinceId == 23}">selected="selected"</c:if>>云南</option>
                <option value="24" <c:if test="${selectProvinceId == 24}">selected="selected"</c:if>>陕西</option>
                <option value="25" <c:if test="${selectProvinceId == 25}">selected="selected"</c:if>>甘肃</option>
                <option value="26" <c:if test="${selectProvinceId == 26}">selected="selected"</c:if>>青海</option>
                <option value="27" <c:if test="${selectProvinceId == 27}">selected="selected"</c:if>>台湾</option>
                <option value="28" <c:if test="${selectProvinceId == 28}">selected="selected"</c:if>>内蒙古</option>
                <option value="29" <c:if test="${selectProvinceId == 29}">selected="selected"</c:if>>广西</option>
                <option value="30" <c:if test="${selectProvinceId == 30}">selected="selected"</c:if>>宁夏</option>
                <option value="31" <c:if test="${selectProvinceId == 31}">selected="selected"</c:if>>新疆</option>
                <option value="32" <c:if test="${selectProvinceId == 32}">selected="selected"</c:if>>西藏</option>
                <option value="33" <c:if test="${selectProvinceId == 33}">selected="selected"</c:if>>香港</option>
                <option value="34" <c:if test="${selectProvinceId == 34}">selected="selected"</c:if>>澳门</option>
            </select>
            <select id="statusCode" name="statusCode" class="span2">
                <option value="">状态</option>
                <option value="0" <c:if test="${selectStatusCode == 0}">selected="selected"</c:if>>计费成功</option>
                <option value="1" <c:if test="${selectStatusCode == 1}">selected="selected"</c:if>>计费失败</option>
                <option value="2" <c:if test="${selectStatusCode == 2}">selected="selected"</c:if>>发送成功</option>
                <option value="3" <c:if test="${selectStatusCode == 3}">selected="selected"</c:if>>发送失败</option>
                <option value="4" <c:if test="${selectStatusCode == 4}">selected="selected"</c:if>>支付中</option>
                <option value="5" <c:if test="${selectStatusCode == 5}">selected="selected"</c:if>>支付异常</option>
            </select>

            <div class="input-append date" id="startDate" data-date="${startDate}" data-date-format="yyyy-mm-dd">
                <input class="span2" type="text" name="startDate" value="${startDate}" readonly>
                <span class="add-on"><i class="icon-th"></i></span>
            </div>

            <div class="input-append date" id="endDate" data-date="${endDate}" data-date-format="yyyy-mm-dd">
                <input class="span2" type="text" name="endDate" value="${endDate}" readonly>
                <span class="add-on"><i class="icon-th"></i></span>
            </div>
            <input type="submit" class="btn btn-primary"
                   value="查 询"/>


        </form>
    </div>
</div>


<%-- 内容--%>
<div class="row-fluid">
    <!--提示语-->
    <div class="alert-msg">
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
    </div>

    <c:if test="${null != pageInfo}">
        <div class="mod-header radius">
            <h4>订单列表</h4>
        </div>
        <table class="table table-striped table-hover table-bordered" id="listTable"
                >
            <thead>
            <tr>
                <th>游戏名</th>
                <th>游戏包</th>
                <th>道具名</th>
                <th>单 价</th>
                <th>省 份</th>
                <th>运营商</th>
                <th>机 型</th>
                <th>支付名</th>
                <th>状 态</th>
                <th>状态描述</th>
                <th>创建时间</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${null != pageInfo.result}">
                <c:forEach var="item" items="${pageInfo.result}" step="1">
                    <tr>
                        <td>${item.appName}</td>
                        <td>${item.channelId}</td>
                        <td>${item.productName}</td>
                        <td>${item.productPrice}</td>
                        <td>${item.province}</td>
                        <c:if test="${item.providerId == 0}">
                            <td>移动</td>
                        </c:if>
                        <c:if test="${item.providerId == 1}">
                            <td>联通</td>
                        </c:if>
                        <c:if test="${item.providerId == 2}">
                            <td>电信</td>
                        </c:if>
                        <td>${item.model}</td>
                        <td>${item.thpayName}</td>
                        <c:if test="${item.orderStatus == 0}">
                            <td class="font-green">计费成功</td>
                        </c:if>
                        <c:if test="${item.orderStatus == 1}">
                            <td class="font-red">计费失败</td>
                        </c:if>
                        <c:if test="${item.orderStatus == 2}">
                            <td class="font-green">发送成功</td>
                        </c:if>
                        <c:if test="${item.orderStatus == 3}">
                            <td class="font-red">发送失败</td>
                        </c:if>
                        <c:if test="${item.orderStatus == 4}">
                            <td>支付中</td>
                        </c:if>
                        <c:if test="${item.orderStatus == 5}">
                            <td class="font-red">支付异常</td>
                        </c:if>
                        <td>${item.statusDesc}</td>
                        <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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


