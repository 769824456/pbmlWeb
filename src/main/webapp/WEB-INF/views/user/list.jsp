<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>

<div class="jq-layout rel" data-options="fit:true">
	<div data-options="region:'north',border:false">
        <div id="grid-toolbar" class="clearfix p5">
        <a id="add" class="btn btn-sm btn-success"><i class="icon icon-add"></i>增加</a>
        <a id="edit" class="btn btn-sm btn-info"><i class="icon icon-edit"></i>编辑</a>
        <a id="reset_pwd" class="btn btn-sm btn-warning"><i class="icon icon-edit"></i>重置密码</a>
        <a id="delete" class="btn btn-sm btn-danger"><i class="icon icon-delete"></i>删除</a>
            <br /><font size="7"> </font>
            <span>用户角色：</span>
             <select class="jq-combobox" id="q_roleId"   data-options="{
					method:'post',
					url: '${z:u('public/role_list')}?ui=query'}">
					</select>
<%--   		    <c:if test="${sessionScope.roleId =='5'}"><option value="1" selected="selected"></option></c:if>		
 --%>			</select>
        <span>所属区域：</span>
        <input class="jq-combotree" type="text"  id='q_areaId' url="${z:u('public/area_list')}" data-options="{
						method:'post',
						onLoadSuccess:function(){$('#q_areaId').combotree('tree').tree('collapseAll')},
						valueField:'id'
					}" />
		<span>所属部门：</span>
			<select class="jq-combobox"  id="q_departmentId"  disabled="disabled">
					</select>
			<span>账号：</span>
			<input class="mr10" type="text" id="name" placeholder="输入账号搜索" />
		<!-- 	<input type="hidden" name="ida" id="ida" /> -->
		<!-- 	<button class="btn btn-xs btn-primary" id="look" type="button">查询名称</button> -->
	   	<a id="search_btn" class="btn btn-sm btn-info"><i class="icon icon-search"></i>搜索</a>

        </div>
    </div>
	<div data-options="region:'center',border:false">
		<table id="user_grid" class="jq-datagrid" fit="true" data-options="{
			url: '${z:u('user/list')}',
			method:'post',
			columns: [[
				{field:'id',checkbox:true},
				{field:'name',title:'帐号',width:100},
				{field:'roleName',title:'用户角色',width:100},
				{field:'areaName',title:'区域',width:100},
                {field:'departmentName',title:'部门',width:100},
 				{field:'addTime',title:'添加时间',width:160},
 				{field:'isActive',title:'是否启用',align:'center',width:60,formatter:App.statusFmt}
			]]}">
		</table>
	</div>
</div>
<script type="text/javascript">
	$("#add").click(function() {
		App.popup('${z:u("user/user_add")}', {
			title : "新增",
			width : 610,
			height : 270
		});
	});

	$("#edit,#delete").click(function(){
		var row = $(".jq-datagrid").datagrid("getSelected");
		if(row == null){
			App.alert("请先选择一条记录","warning");
		}else{
			var eleId = $(this).attr("id");
			if(eleId=="edit"){
				App.popup('${z:u("user/user_edit")}?id='+row.id, {
				title : "编辑",
				width : 610,
				height : 270
			});
			}else if(eleId=="delete"){
	            App.ajax('${z:u("/user/deletes")}?id='+row.id,{
	            type: "POST"
	            });
				
			}
		}
	});


	$("#search_btn").on("click",
			function() {
				var departmentId = $("#q_departmentId").combobox("getValue");
				var name = $("#name").val();
				var roleId = $("#q_roleId").combobox("getValue");
				var areaId = $("#q_areaId").combotree("getValue");
/* 				alert(departmentId +cadreName + cadreSex + areaId);
 */				$("#user_grid").datagrid(
						{
							url : '${z:u("/user/list")}?name='
									+ name + "&roleId=" + roleId
									+ "&departmentId=" + departmentId
									+ "&areaId=" + areaId
						});
			});
	
	$("#reset_pwd").click(function() {
		var row = $(".jq-datagrid").datagrid("getSelected");
		if (row == null) {
			$.messager.alert("提示", "请先选择一条记录", "warning");
		} else {
			App.popup('${z:u("user/reset_pass")}?id=' + row.id, {
				title : "重置密码"
			});
		}
	});
	
	
	$(document).ready(function() {
		$("#q_areaId").combotree({
			onChange : function(n, o) {
				var areaId = $("#q_areaId").combotree("getValue");
				//alert(areaId);
				initDeparment(areaId);
				$('#q_departmentId').combobox('enable');
				$('#q_departmentId').combobox('clear');
			}
		});
	});

	function initDeparment(areaId) {
		$('#q_departmentId').combobox({
			method : 'post',
			url : '${z:u("public/department_list")}?ui=combo&areaId=' + areaId,
			valueField : 'id',
			textField : 'text'
		});
	}
</script>