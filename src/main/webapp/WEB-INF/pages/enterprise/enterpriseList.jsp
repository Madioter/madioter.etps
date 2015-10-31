<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=8">
    <meta charset="UTF-8">
    <title>企业信息列表</title>

    <link rel="stylesheet" href="<%=path%>/resources/css/web.css">
    <link rel="stylesheet" href="<%=path%>/resources/js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" href="<%=path%>/resources/js/easyui/themes/icon.css">
    <script src="<%=path%>/resources/js/easyui/jquery.min.js"></script>
    <script src="<%=path%>/resources/js/easyui/jquery.easyui.min.js"></script>
    <script src="<%=path%>/resources/js/easyui/easyui-lang-zh_CN.js"></script>
</head>
<body class="easyui-layout" style="visibility:hidden;">
<div region="center" border="false">
    <table class="easyui-datagrid" title="企业列表" style="width:800px;height:250px" id="enterpriseList"
           data-options="fit:true,pagination:true,rownumbers:true,singleSelect:false,url:'<%=path%>/enterprise/enterpriseListJson',method:'post',toolbar:'#tb',queryParams: getParams()">
        <thead>
        <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th data-options="field:'id',hidden:true">ID</th>
            <th data-options="field:'name',width:100,align:'left'">企业名称</th>
            <th data-options="field:'industry',width:80,align:'left'">行业</th>
            <th data-options="field:'legalPerson',width:100,align:'left'">法人代表人</th>
            <th data-options="field:'regAddress',width:50,align:'left'">注册地址</th>
            <th data-options="field:'businessScope',width:150,align:'left'">经营范围</th>
            <th data-options="field:'regAuth',width:150,align:'left'">注册机关</th>
            <th data-options="field:'regDate',width:150,align:'left'">注册日期</th>
            <th data-options="field:'phone',width:150,align:'left'">联系方式</th>
            <%--<th data-options="field:'option',width:150,align:'center',formatter:optionRenderer">操作</th>--%>
        </tr>
        </thead>
    </table>
    <div id="tb" style="padding:2px 5px;">
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true"
           onclick="doImport()">Excel数据导入</a>
        <br/>
        企业名称: <input class="easyui-text" style="width:110px" id="name">
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="reloadList();">查询</a>
    </div>
</div>

<div id="import" class="easyui-dialog" closed="true" model="true" closable="true" title="数据导入" buttons="#dlg-buttons"
     style="width:350px;height:150px;padding:5px;">
    <form id="importForm" method="post" enctype="multipart/form-data" action="<%=path%>/enterprise/excelImport">
        <table cellpadding="5">
            <tr>
                <td>EXCEL文件:</td>
                <td><input class="easyui-filebox" name="excelFile" style="width:200px"></td>
            </tr>
        </table>
    </form>
</div>
<div id="dlg-buttons">
    <table cellpadding="0" cellspacing="0" style="width: 100%">
        <tr>
            <td style="text-align: right">
                <a href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-save" onclick="doSave();">
                    提交</a>
                <a href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-cancel"
                   onclick="javascript:$('#import').dialog('close');">
                    取消</a>
            </td>
        </tr>
    </table>
</div>
<div id="message" class="easyui-dialog" closed="true" closable="true" title="提示信息"
     style="width:350px;height:150px;padding:5px;">
    成功导入：${result.data}条数据
    <c:if test="${!result.success}">
        ，发生异常的数据行：${result.message}
    </c:if>
</div>
<%--<div id="dd" class="easyui-window" closed="true" style="width:450px;height:400px;padding:5px;"></div>
<div id="user" class="easyui-window" closed="true" style="width:430px;height:200px;padding:5px;"></div>--%>
</body>
<script type="text/javascript">
    function reloadList(page) {
        $('#enterpriseList').datagrid('options').queryParams = getParams();
        if (page) {
            $('#enterpriseList').datagrid('options').pageNumber = page;
        }
        $('#enterpriseList').datagrid("reload");
    }

    function getParams() {
        return {name: $("#name").val()};
    }

    function doSave() {
        $("#importForm").submit();
    }

    function doImport() {
        $("#import").dialog("open");
    }

    $(function () {
        <c:if test="${result != null}">
        $("#message").dialog("open");
        </c:if>
        // TIP: 配合body解决页面跳动和闪烁问题
        $("body").css({visibility: "visible"});
    });

</script>
</html>
