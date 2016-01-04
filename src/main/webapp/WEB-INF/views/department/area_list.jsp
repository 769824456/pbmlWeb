<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>

<div class="jq-layout rel" data-options="fit:true">
	<div data-options="region:'north',border:false">
        <div id="grid-toolbar" class="clearfix p5">
        	<a id="add" class="btn btn-sm btn-success"><i class="icon icon-add"></i>增加</a>
            <a id="edit" class="btn btn-sm btn-info"><i class="icon icon-edit"></i>编辑</a>
            <a id="delete" class="btn btn-sm btn-danger"><i class="icon icon-delete"></i>删除</a>
        </div>
    </div>
	<div data-options="region:'center',border:false">
		<table id="department_grid" class="jq-treegrid" fit="true" data-options="{
			url:'${z:u('department/area_list')}',
			idField:'id',
			treeField:'name',
			pagination: false,
			method:'post',
			onLoadSuccess:function(){$('#department_grid').treegrid('collapseAll')},
			columns: [[
				{field:'id',checkbox:true},
				{field:'name',title:'名称',width:100},
				{field:'level',title:'区域类型',width:100,formatter:typeFormatter},
				{field:'sortId',title:'排序值',width:50},
				{field:'isShow',title:'是否显示',width:60,formatter: App.statusFmt}
			]]}">
		</table>
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
	
	$("#add").click(function(){
		App.popup('${z:u('department/area_add')}');
	});
	
	$("#edit,#delete").click(function(){
		var row = $(".jq-treegrid").datagrid("getSelected");
		if(row == null){
			App.alert("请先选择一条记录","warning");
		}else{
			var eleId = $(this).attr("id");
			if(eleId=="edit"){
				App.popup('${z:u("/department/area_edit")}?id='+row.id,"编辑节点");
			}else if(eleId=="delete"){
	            App.ajax('${z:u("/department/area_delete")}?id='+row.id,{
	            type: "POST"
	            });
				
			}
		}
	});
	
	
</script>
