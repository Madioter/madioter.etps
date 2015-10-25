<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=8">
    <meta charset="UTF-8">
    <title>企业信息列表</title>

    <link rel="stylesheet" href="${base}/resources/css/web.css">
    <link rel="stylesheet" href="${base}/resources/easyui/themes/default/easyui.css">
    <link rel="stylesheet" href="${base}/resources/easyui/themes/icon.css">
    <script src="${base}/resources/easyui/jquery.min.js"></script>
    <script src="${base}/resources/easyui/jquery.easyui.min.js"></script>
    <script src="${base}/resources/easyui/easyui-lang-zh_CN.js"></script>
</head>
<body class="easyui-layout" style="visibility:hidden;">
<div region="center" border="false">
    <table class="easyui-datagrid" title="员工列表" style="width:800px;height:250px" id="enterpriseList"
           data-options="fit:true,pagination:true,rownumbers:true,singleSelect:false,url:'${base}/enterprise/enterpriseListJson',method:'post',toolbar:'#tb',queryParams: getParams()">
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
           onclick="doEdit()">新增</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
           onclick="batchDelete()">删除</a>
        <br/>
        企业名称: <input class="easyui-text" style="width:110px" id="name">
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="reloadList();">查询</a>
    </div>
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

    /*function optionRenderer(val, rec) {
        var button = "<a href='javascript:void(0);' class='easyui-linkbutton' onclick='editEmployee(" + rec.id + ")'>修改</a>&nbsp;&nbsp;";
        button += "<a href='javascript:void(0);' class='easyui-linkbutton' onclick='editUser(" + rec.id + ")'>关联用户</a>&nbsp;&nbsp;";
        button += "<a href='javascript:void(0);' class='easyui-linkbutton' onclick='deleteEmployee(" + rec.id + ")'>删除</a>";
        return button;
    }*/

    /*function editEmployee(id) {
        doEdit(id);
    }*/

    /*function editUser(id){
        var url = '$/{base}/user/editUser?employeeId=' + id;
        $('#user').window({title: "编辑用户"});
        $('#user').html("<iframe src='" + url + "' scrolling='auto' style='width:100%;height:100%;border:0;'></iframe>");
        $('#user').window('open');
    }*/

    /*function deleteEmployee(id) {
        $.post("$\{base}/employee/deleteEmployee", {id: id}, function (data) {
            if (data == "success") {
                $.messager.alert("消息", "删除成功");
                reloadList();
            } else {
                $.messager.alert("错误", data);
            }
        });
    }*/

    /*function closeUserWin(data) {
        $('#user').window('close');
        if (data == "success") {
            $.messager.alert("消息", "保存成功");
        }
    }*/

    /*function closeEmployeeWin(data) {
        $('#dd').window('close');
        if (data == "success") {
            $.messager.alert("消息", "保存成功");
            reloadList();
        }
    }*/

    //公用的函数放到闭包外面
    /*function doEdit(id) {
        var url = '$、{base}/employee/editEmployee';
        if (id) {
            url += "?id=" + id;
            $('#dd').window({title: "编辑员工"});
        } else {
            $('#dd').window({title: "新增员工"});
        }
        $('#dd').html("<iframe src='" + url + "' scrolling='auto' style='width:100%;height:100%;border:0;'></iframe>");
        $('#dd').window('open');
    }

    function batchDelete() {
        if (checkSelection() == false) return false;

        var ids = [];
        var items = $('#employeeList').datagrid('getSelections');
        for (var i = 0; i < items.length; i++) {
            ids.push(items[i].id);
        }
        $.post("$、{base}/employee/deleteEmployee", {ids: ids.join(",")}, function (data) {
            if (data == "success") {
                $.messager.alert("消息", "删除成功");
                reloadList();
            } else {
                $.messager.alert("错误", data);
            }
        });
    }

    function checkSelection() {
        var items = $('#employeeList').datagrid('getSelections');
        if (items.length == 0) {
            $.messager.alert("提示", '请先选择记录');
            return false;
        }
        return true;
    }*/

    function getParams() {
        return {name: $("#name").val()};
    }


    $(function () {
        // TIP: 配合body解决页面跳动和闪烁问题
        $("body").css({visibility: "visible"});
    });

</script>
</html>
