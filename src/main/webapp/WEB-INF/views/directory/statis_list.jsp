<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>

<div class="jq-layout rel" data-options="fit:true">
	<div data-options="region:'north',border:false">
        <div id="grid-toolbar" class="fix p5">
		</div>
    </div>
	<div data-options="region:'center',border:false">
		<div class="jq-layout rel" data-options="fit:true">
			<div data-options="region:'west',border:false,split:true" style="width: 570px;">
				<!-- 项目列表，无需传人分页 -->
           <table id="area_grid" class="jq-treegrid"   title="区域列表"   fit="true" data-options="{
			url:'${z:u('directory/area_list')}',
			idField:'id',
			toolbar:'#gridTbar2',
			treeField:'name',
			pagination: false,
			method:'post',
			onLoadSuccess:function(){$('#area_grid').treegrid('collapseAll')},
			columns: [[
				{field:'id',checkbox:true},
				{field:'name',title:'名称',width:120},
				{field:'level',title:'区域类型',width:50,align:'center',formatter:typeFormatter},
				{field:'sum',title:'干部总数',width:50,align:'center'}
			]]}">
				</table>
				<div class="p5" id="gridTbar2">
		            <a id="look_department" class="btn btn-xs btn-info"><i class="icon icon-search"></i>查看部门</a>
				</div>
			</div>
			<div data-options="region:'center',border:false">
				<table class="jq-datagrid" id="department_grid" fit="true" title="部门信息"
					data-options="
					toolbar:'#gridTbar3',
					columns: [[
						{field:'id',checkbox:true},
						{field:'name',align:'center',title:'部门名称',width:60},
						{field:'type',align:'center',title:'部门类别',formatter:rankFormatter,width:50},
						{field:'staffCount',align:'center',title:'干部总数',width:50},
					]]
					">
				</table>
				<div class="p5" id="gridTbar3">
				    <a id="staff_list" class="btn btn-xs btn-success"><i class="icon icon-search"></i>人员列表</a>
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
	
	$('#area_grid').treegrid({
	    onClickRow:function(row){
	    getDepartment(row.id);
	
	}});
	

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
			url:'${z:u('/directory/staff_department_list')}?areaId='+id,
			method:'post'
		});
	}
	
		$("#staff_list").click(function() {
		var row = $("#department_grid").datagrid("getSelected");
		if (row == null) {
			$.messager.alert("提示", "请先选择一条记录", "warning");
		} else {
			App.popup('${z:u("directory/staff_list")}?departmentId=' + row.id, {
				title : "人员列表",
				width : 800,
				height : 600
			});
		}
	});
	

</script>
