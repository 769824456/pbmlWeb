<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>

<div class="jq-layout rel" data-options="fit:true">
	<div data-options="region:'north',border:false">
        <div id="grid-toolbar" class="fix p5">
<%--         	<span>停车场选择：</span>
			<select class="jq-combobox" id="carParkId" data-options="{
					method:'post',
					url: '${z:u('public/parklists')}'}">
			</select> --%>
<!-- 			<span>区域名称：</span>
			<input class="mr10" type="text" id="photoId" placeholder="输入区域名称搜索" />
            <button class="btn btn-sm btn-info" id="search_btn">搜索</button> -->
		</div>
    </div>
	<div data-options="region:'center',border:false">
		<div class="jq-layout rel" data-options="fit:true">
			<div data-options="region:'west',border:false,split:true" style="width: 350px;">
				<!-- 项目列表，无需传人分页 -->
		<table id="area_grid" class="jq-treegrid"   title="区域列表"   fit="true" data-options="{
			url:'${z:u('department/area_list')}',
			idField:'id',
			toolbar:'#gridTbar2',
			treeField:'name',
			pagination: false,
			method:'post',
			onLoadSuccess:function(){$('#area_grid').treegrid('collapseAll')},
			columns: [[
				{field:'id',checkbox:true},
				{field:'name',title:'名称',width:100},
				{field:'level',title:'区域类型',width:50,align:'center',formatter:typeFormatter},
				{field:'count',title:'部门数',width:30,align:'center'}
				
			]]}">
				</table>
				<div class="p5" id="gridTbar2">
		            <a id="look_department" class="btn btn-xs btn-info"><i class="icon icon-search"></i>查看部门</a>
				</div>
			</div>
			<div data-options="region:'center',border:false">
				<table class="jq-datagrid" id="department_grid" fit="true" title="部门列表"
					data-options="
					toolbar:'#gridTbar3',
					columns: [[
						{field:'id',checkbox:true},
						{field:'name',align:'center',title:'部门名称',width:60},
						{field:'type',align:'center',title:'部门类别',formatter:rankFormatter,width:50},
						{field:'address',align:'center',title:'联系地址',width:120},
						{field:'officeTel',align:'center',title:'办公室电话',width:80},
						{field:'sortId',align:'center',title:'排序值',width:20}
					]]
					">
				</table>
				<div class="p5" id="gridTbar3">
				    <a id="add_department" class="btn btn-xs btn-success"><i class="icon icon-add"></i>增加</a>
    				<a id="edit_department" class="btn btn-xs btn-info"><i class="icon icon-edit"></i>编辑</a>
    				<a id="delete_department" class="btn btn-xs btn-danger"><i class="icon icon-delete"></i>删除</a>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	function typeFormatter(v) {
	 	var val;
	   if (v == 3) {
			val = '<font color="blue">区县</font>';
		} else if (v == 2) {
			val = '<font color="green">市（地区）</font>';
		}else if (v == 1){
	        val = '<font color="black">省</font>';
		}else if (v == 4){
	        val = '<font color="blue">乡镇</font>';
		}
		return val;
	}
	
	function rankFormatter(v) {
	 	var val;
	   if (v == 3) {
			val = '<font color="blue">区县级</font>';
		} else if (v == 2) {
			val = '<font color="green">市（地区）级</font>';
		}else if (v == 1){
	        val = '<font color="black">省级</font>';
		}else if (v == 4){
	        val = '<font color="blue">乡镇级</font>';
		}
		return val;
	}
	
	
	
	$("#look_department").click(function(){
		var row = $(".jq-treegrid").datagrid("getSelected");
	    if(row==null){
			$.messager.alert("提示","请先选择一条记录","warning");
		}else{
		    getDepartment(row.id);
		}
	});
	
	function getDepartment(id) {
       // alert(id);
		$("#department_grid").datagrid({
			url:'${z:u('/department/department_list')}?areaId='+id,
			method:'post'
		});
	}
	
	
	$("#add_department").click(function(){
		var row = $(".jq-treegrid").datagrid("getSelected");
		if(row==null){
			$.messager.alert("提示","请先选择一个地区","warning");
		}else{
			App.popup('${z:u("department/executive_add")}?areaId='+row.id, {
				title: "新增部门",
			});
		}
	});
	
	$('#area_grid').treegrid({
	    onClickRow:function(row){
	    getDepartment(row.id);
	
	}});
	
	$("#edit_department,#delete_department").click(function(){
		var row = $(".jq-datagrid").datagrid("getSelected");
		if(row == null){
			App.alert("请先选择一条记录","warning");
		}else{
			var eleId = $(this).attr("id");
			if(eleId=="edit_department"){
				App.popup('${z:u("/department/executive_edit")}?id='+row.id,"编辑节点");
			}else if(eleId=="delete_department"){
	            App.ajax('${z:u("/department/executive_delete")}?id='+row.id,{
	            type: "POST"
	            });
				
			}
		}
	});
	
</script>
