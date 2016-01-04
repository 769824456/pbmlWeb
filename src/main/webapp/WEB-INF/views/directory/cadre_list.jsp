<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>

<div class="jq-layout rel" data-options="fit:true">
	<div data-options="region:'north',border:false">
        <div id="grid-toolbar" class="clearfix p5">
            <a id="add" class="btn btn-sm btn-success"><i class="icon icon-add"></i>增加</a>
            <a id="import" class="btn btn-sm btn-primary"><i class="icon icon-edit"></i>Excel导入</a>
        	<a id="detail" class="btn btn-sm btn-warning"><i class="icon icon-search"></i>详情</a>
            <a id="edit" class="btn btn-sm btn-info"><i class="icon icon-edit"></i>编辑</a>
            <a id="delete" class="btn btn-sm btn-danger"><i class="icon icon-delete"></i>删除</a>
            <a id="export" class="btn btn-sm btn-primary"><i class="icon icon-edit"></i>Excel导出</a>
            <br /><font size="7"> </font>
        <span>所属区域：</span>
        <input class="jq-combotree" type="text"  id='q_areaId' url="${z:u('public/area_list')}" data-options="{
						method:'post',
						onLoadSuccess:function(){$('#q_areaId').combotree('tree').tree('collapseAll')},
						valueField:'id'
					}" />
		<span>所属部门：</span>
			<select class="jq-combobox"  id="q_departmentId"  disabled="disabled">
					</select>
	    <span>人员姓名：</span>
			<input class="mr10" type="text" id="name" placeholder="输入姓名搜索" />
		<span>人员性别：</span>
			<select class="jq-combobox" id="sex"  value="${department.type}">
				    <option value="">不限</option>
        		    <option value ="1">男</option>
  					<option value ="0">女</option>
  			</select>   
            <button class="btn btn-sm btn-info" id="search_btn">搜索</button>
        </div>
    </div>
	<div data-options="region:'center',border:false">
		<table class="jq-datagrid" id="case_grid" fit="true" data-options="{
			url: '${z:u('directory/cadre_list')}',
			method:'post',
			columns: [[
				{field:'id',checkbox:true},
				{field:'cadreName',title:'姓名',width:40},
				{field:'cadreSex',title:'性别',width:30,formatter:sexFormatter},
				{field:'areaName',title:'区域',width:50},
				{field:'departmentName',title:'处室',width:50},
				{field:'dutiesName',title:'职务',width:50},
				{field:'officeTel',title:'办公室电话',width:80},
				{field:'moblePhone',title:'手机',width:80},
				{field:'remark',title:'备注',width:50},
			]]}">
		</table>
	</div>
</div>
<script type="text/javascript">
	function sexFormatter(v) {
	 	var val;
	   if (v == 1) {
			val = '<font color="blue">男</font>';
		} else if (v == 0) {
			val = '<font color="red">女</font>';
			}
		return val;
	}
	
	$("#search_btn").on("click",
			function() {
				var departmentId = $("#q_departmentId").combobox("getValue");
				var cadreName = $("#name").val();
				var cadreSex = $("#sex").combobox("getValue");
				var areaId = $("#q_areaId").combotree("getValue");
/* 				alert(departmentId +cadreName + cadreSex + areaId);
 */				$("#case_grid").datagrid(
						{
							url : '${z:u("/directory/cadre_list")}?cadreName='
									+ cadreName + "&cadreSex=" + cadreSex
									+ "&departmentId=" + departmentId
									+ "&areaId=" + areaId
						});
			});
			

	$("#export").on("click", function() {
	   	App.alert("导出一个Excel（待开发）", "info");
	});

	$("#add").click(function() {
		App.popup('${z:u("directory/cadre_add")}', {
			title : "新增",
			width : 610,
			height : 310
		});
	});

	$("#import").click(function() {
		App.popup('${z:u("directory/cadre_import")}', {
			title : "导入",
			width : 610,
			height : 220
		});
	});
	$("#detail").click(function() {
		var row = $(".jq-datagrid").datagrid("getSelected");
		if (row == null) {
			$.messager.alert("提示", "请先选择一条记录", "warning");
		} else {
			App.popup('${z:u("directory/cadre_detail")}?id=' + row.id, {
				title : "详情",
				width : 610,
				height : 310
			});
		}
	});
	$("#edit,#delete").click(function() {
		var row = $(".jq-datagrid").datagrid("getSelected");
		if (row == null) {
			App.alert("请先选择一条记录", "warning");
		} else {
			var eleId = $(this).attr("id");
			if (eleId == "edit") {
				App.popup('${z:u("/directory/cadre_edit")}?id=' + row.id, {
					title : "编辑",
					width : 610,
					height : 320
				});
			} else if (eleId == "delete") {
				App.ajax('${z:u("/directory/cadre_delete")}?id=' + row.id, {
					type : "POST"
				});

			}
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
