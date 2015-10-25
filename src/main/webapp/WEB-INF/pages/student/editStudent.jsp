<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<form id="form1" action="<%=basePath%>student/saveStudent" method="post">
    <input type="hidden" value="${student.id}" name="id"/>
    <table class="form-table">
        <tr>
            <th>*姓名：</th>
            <td><input type="text" value="${student.name}" name="name"
                       class="input-text width190 validate[required,maxSize[20]]"/></td>
        </tr>
        <tr>
            <th>*学号：</th>
            <td><input type="text" value="${student.number}" name="number"
                       class="input-text width190 validate[required,maxSize[20]]"/></td>
        </tr>
        <tr>
            <th>性别：</th>
            <td><select name="gender" class="width190">
                <option value="MALE">男</option>
                <option value="FEMALE"
                        <c:if test="${student.gender.id eq 1}">selected</c:if>>女
                </option>
            </select></td>
        </tr>
        <tr>
            <th>*出生日期：</th>
            <td>
                <input type="text" value="<fmt:formatDate value='${student.birthday}' pattern='yyyy-MM-dd'/> "
                       name="date"
                       class="input-text width190 validate[required,dateFormat]"
                       onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',isShowClear:false,readOnly:true})"/>
            </td>
        </tr>
        <tr>
            <th>*班级名称：</th>
            <td><select name="orgClass.id" class="width195 validate[required]" id="orgClass">
                <c:forEach var="clz" items="${classes}">
                    <option value="${clz.id}"
                            <c:if test="${student.orgClass.id}==${clz.id}">selected</c:if>>${clz.name}</option>
                </c:forEach>
            </select></td>
        </tr>
        <tr>
            <th></th>
            <td>
                <input type="button" value="提交" class="btn" id="save" style="width:auto"/>
                <input type="button" value="取消" class="btn" id="cancel" style="width:auto"/>
            </td>
        </tr>
    </table>
</form>

<script type="text/javascript">
    $(function () {
        $("#cancel").click(function () {
            layer.close(layerItem);
        });

        $("#save").click(function () {
            if ($("#form1").validationEngine("validate")) {
                $("#form1").ajaxSubmit(function (data) {
                    if (data.message == "success") {
                        layer.msg("保存成功", 2, 1);
                        reloadGrid();
                        layer.close(layerItem);
                    } else {
                        layer.msg(data.message, 2);
                    }
                });
                return false;
            }
        });
    });
</script>
<%--
<script type="text/javascript">
    //初始化班级下拉单
    var orgClassList = ${orgClassList};
    var $selec = $("#orgClass");
    var $opt = $("<option></option>");
    for (var i = 0; i < (len = orgClassList.length); i++) {
        var id = orgClassList[i].id;
        var name = orgClassList[i].name;
        var $opt = $("<option></option>").text(name).val(id);
        $selec.append($opt);
    }
    //修改用户回显数据初始化
    $(function () {
        var org = "${student.orgClass.id}";
        $("#orgClass").val(org);
        var gende = "${gender}";
        $("#gender").val(gende);

    });
</script>
--%>
