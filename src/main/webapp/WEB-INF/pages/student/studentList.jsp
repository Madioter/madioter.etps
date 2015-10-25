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
        var tool = [{
            caption: "新增", buttonicon: "ui-icon-plus", onClickButton: function () {
                var options = {
                    title: "新增学生",  //弹出页的标题
                    offset: ['200px', ''],   //弹窗相对于左上角的位置，空字符串表示默认居中，不写为默认居中
                    shade: [0.5, '#000'], //设置遮罩，[遮罩透明度, 遮罩颜色]，shade: [0]为不适用遮罩
                    area: ['400px', '400px'],
                    type: 1,
                    page: {
                        url: '<%=basePath%>student/editStudent'
                    }
                };
                layerItem = $.layer(options);
            }
        }, {
            caption: '删除', buttonicon: 'ui-icon-trash', onClickButton: function () {
                //获取表格中选中的行的ID值
                var rowIds = $("#jqGrid").jqGrid('getGridParam', 'selarrrow');
                if (rowIds.length == 0) {
                    layer.msg("请选择批量删除的学生", 2);
                    return;
                }
                $.post("<%=basePath%>student/deleteStudent", {ids: rowIds.join(",")}, function (data) {
                    if (data.message == "success") {
                        layer.msg("删除成功", 2, 1);
                        reloadGrid();
                    } else {
                        layer.msg(data.message, 2);
                    }
                });
            }
        }];
        var option = {
            url: '<%=basePath%>student/studentListJson',  //数据获取路径
            postData: getData(),  //追加额外的请求参数
            mtype: "POST",       //请求发送方式
            datatype: "json",   //数据类型
            multiselect: true,   //每行复选框
            colNames: ['ID', '姓名', '学号', '班级', '性别', '出生日期', "操作"],  //具有的列名
            //列设置，按顺序对应列
            colModel: [
                {name: 'id', key: true, width: 75},
                {name: 'name', width: 150},
                {name: 'number', width: 150}, //不支持对象类型数据的处理
                {name: 'className', width: 150}, //不支持对象类型数据的处理
                {
                    name: 'gender',
                    width: 80,
                    align: 'center',
                    formatter: 'select',
                    editoptions: {value: 'MALE:男;FEMALE:女', defaultValue: ''}
                },
                {
                    name: 'birthday', //数据来源（默认为name值）
                    width: 150,     //列宽
                    align: 'center',    //对齐方式
                    formatter: 'date',  //通用的格式化方案
                    formatoptions: {newformat: 'Y年m月d日'},    //格式化后的文本样式
                    datefmt: 'Y-M-d'        //数据格式
                },
                {
                    name: 'option',
                    index: 'option',
                    width: 200,
                    align: 'center',
                    formatter: optionFormatter  //自定义单元格
                }
            ],
            rownumbers: true,       //是否显示行号
            viewrecords: true,   //是否显示总记录数信息
            width: 780,         //表格总宽度
            height: 250,        //表格总高度
            rowNum: 10,         //每页显示条数
            jsonReader: {root: "rows", total: "totalPages", records: "total", repeatitems: false},  //数据结果集格式
            pager: "#jqGridPager",      //分页DIV的ID
            toolbar: [true, "top", tool] //2568行，增加了toolbar的按钮和事件
        };
        //初始化jqGrid
        $("#jqGrid").jqGrid(option);
        //给分页DIV增加按钮事件，如新增，修改等（jqGrid支持）,其中编辑会自动弹出一个输入框，并且所有标注为editable：true的列都会出现在编辑弹窗中
        $("#jqGrid").navGrid('#jqGridPager', {
            edit: true,
            add: false,
            del: false,
            search: false
        });

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

    //自定义单元格方法
    function editItem(id) {
        var options = {
            title: "修改学生",  //弹出页的标题
            shade: [0.5, '#000'], //设置遮罩，[遮罩透明度, 遮罩颜色]，shade: [0]为不适用遮罩
            area: ['400px', '400px'],
            type: 1,
            page: {
                url: '<%=basePath%>student/editStudent?id=' + id
            }
        };
        layerItem = $.layer(options);
    }

    function deleteItem(id) {
        $.post("<%=basePath%>student/deleteStudent", {id: id}, function (data) {
            if (data.message == "success") {
                layer.msg("删除成功", 2, 1);
                reloadGrid();
            } else {
                layer.msg(data.message, 2);
            }
        });
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
