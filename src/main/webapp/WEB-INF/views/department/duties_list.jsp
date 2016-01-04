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
		<table class="jq-datagrid" fit="true" data-options="{
			url: '${z:u('department/duties_list')}',
			method:'post',
			columns: [[
				{field:'id',checkbox:true},
				{field:'dutiesName',title:'职务名称',width:50},
				{field:'remark',title:'职务备注',width:120},
				{field:'addTime',title:'添加时间',width:70},
				{field:'sortId',title:'排序值',width:50}
			]]}">
		</table>
	</div>
</div>
<script type="text/javascript">
	$("#add").click(function(){
		App.popup('${z:u("department/duties_add")}', {
				title: "新增",
				width : 310,
				height : 260
			});
	});
	
	$("#edit,#delete").click(function(){
		var row = $(".jq-datagrid").datagrid("getSelected");
		if(row == null){
			App.alert("请先选择一条记录","warning");
		}else{
			var eleId = $(this).attr("id");
			if(eleId=="edit"){
				App.popup('${z:u("/department/duties_edit")}?id='+row.id,"编辑节点");
			}else if(eleId=="delete"){
	            App.ajax('${z:u("/department/duties_delete")}?id='+row.id,{
	            type: "POST"
	            });
				
			}
		}
	});
</script>
