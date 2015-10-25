<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=8">
    <!-- The link to the CSS that the grid needs -->
    <link rel="stylesheet" type="text/css" media="screen"
          href="<%=basePath%>/resources/css/themes/overcast/jquery-ui.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="<%=basePath%>/resources/js/jqGrid/css/ui.jqgrid.css"/>
    <!-- 弹窗控件Layer -->
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/resources/js/layer/skin/layer.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/resources/css/style.css">
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>/resources/js/validationEngine2/validationEngine.jquery.css" media="all">
    <!-- The jQuery library is a prerequisite for all jqSuite products -->
    <script type="text/javascript" src="<%=basePath%>/resources/js/jquery-1.7.2.min.js"></script>
    <!-- We support more than 40 localizations -->
    <script type="text/javascript" src="<%=basePath%>/resources/js/jqGrid/js/i18n/grid.locale-cn.js"></script>
    <!-- This is the Javascript file of jqGrid -->
    <script type="text/javascript" src="<%=basePath%>/resources/js/jqGrid/js/jquery.jqGrid.src.js"></script>
    <script type="text/javascript" src="<%=basePath%>/resources/js/jqGrid/plugins/grid.addons.js"></script>
    <script type="text/javascript" src="<%=basePath%>/resources/js/layer/layer.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/resources/js/datePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=basePath%>/resources/js/jquery.form.js"></script>
    <script type="text/javascript"
            src="<%=basePath%>/resources/js/validationEngine2/jquery.validationEngine-zh_CN.js"></script>
    <script type="text/javascript"
            src="<%=basePath%>/resources/js/validationEngine2/jquery.validationEngine.js"></script>
</head>
<body>
<div id="mysearch"></div>
<table id="jqGrid"></table>
<div id="jqGridPager"></div>
<%--<input type="button" value="刷新" id="reflush"/>--%>
<%--<input type="text" id="sd"/>--%>
<script type="text/javascript">
    var layerItem;
    $(document).ready(function () {
        //工具栏按钮写法
        var tool = [];
        var option = {
            url: '<%=basePath%>enterprise/enterpriseListJson',  //数据获取路径
            postData: getData(),  //追加额外的请求参数
            mtype: "POST",       //请求发送方式
            datatype: "json",   //数据类型
            //multiselect: true,   //每行复选框
            colNames: ["ID", "企业名称", "行业","法人代表人","注册地址","经营范围", "注册机关", "注册日期", "联系方式"],  //具有的列名
            //列设置，按顺序对应列
            colModel: [
                {name: 'id', key: true, hidden:true},
                {name: 'name', width: 150},
                {name: 'industry', width: 150},
                {name: 'legalPerson', width: 150},
                {name: 'regAddress', width: 150},
                {name: 'businessScope', width: 150},
                {name: 'regAuth', width: 150},
                {name: 'regDate', width: 150},
                {name: 'phone', width: 150}//,
                /*{
                    name: 'option',
                    index: 'option',
                    width: 200,
                    align: 'center',
                    formatter: optionFormatter  //自定义单元格
                }*/
            ],
            rownumbers: true,
            viewrecords: true,   //是否显示总记录数信息
            width: 850,         //表格总宽度
            height: 500,        //表格总高度
            rowNum: 5,         //每页显示条数
            jsonReader: {root: "rows", total: "totalPages", records: "total", repeatitems: false},  //数据结果集格式
            pager: "#jqGridPager"//,      //分页DIV的ID
            //toolbar: [true, "top", tool] //2568行，增加了toolbar的按钮和事件
        };
        //初始化jqGrid
        $("#jqGrid").jqGrid(option);
        //给分页DIV增加按钮事件，如新增，修改等（jqGrid支持）,其中编辑会自动弹出一个输入框，并且所有标注为editable：true的列都会出现在编辑弹窗中
        /*$("#jqGrid").navGrid('#jqGridPager', {
         edit: true,
         add: false,
         del: false,
         search: false
         });*/

        //设置搜索框
        $("#mysearch").filterGrid('#jqGrid', {
            gridModel: false,   //所有列条件的搜索模式
            formtype: 'horizontal', //搜索框排列方式：横向排列
            //搜索项定义
            filterModel: [{
                id: 'name',
                label: '姓名:', //标签名
                name: 'name', //参数名
                stype: 'text',  //输入框类型
                defval: ''  //默认值
            }, {
                id: 'className',
                label: '班级名称:', //标签名
                name: 'className', //参数名
                stype: 'text',  //输入框类型
                defval: ''  //默认值
            }],
            searchButton: '搜索',     //搜索按钮文字
            enableSearch: true     //是否启用搜索
        });
    });

    function getData(){
        return {name: $("#sg_name").val(), className: $("#sg_className").val()};
    }


    //自定义单元格
    function optionFormatter(rowId, cellval, rowObject) {
        return '<a href="javascript:void(0);" onclick="deleteItem(' + rowObject.id + ')">删除</a>'
                + '&nbsp;&nbsp;<a href="javascript:void(0);" onclick="editItem(' + rowObject.id + ')">编辑</a>';
    }

    $("#reflush").click(function () {
        reloadGrid();
    });

    function reloadGrid(page) {
        if (page) {
            $("#jqGrid").jqGrid('setGridParam', {
                page: page,
                postData: getData()
            }).trigger("reloadGrid");
        } else {
            $("#jqGrid").jqGrid('setGridParam', {
                postData: getData()
            }).trigger("reloadGrid");
        }
    }
</script>
</body>
</html>
