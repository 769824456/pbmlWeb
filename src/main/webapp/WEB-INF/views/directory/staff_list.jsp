<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>

<div class="jq-layout rel" data-options="fit:true">
	<div data-options="region:'north',border:false">
		<div class="tools fix" style="padding-bottom: 5px;">
			<ul class="fl">
				<a id="close" class="btn btn-xs  btn-primary"><i
					class="icon icon-back"></i>返回</a>
			</ul>
			<ul class="toolbar fr">
<!-- 				<font style="clolor:red">注：点击"增加"或"编辑"时编辑好内容点击操作框确定即可</font>
 -->			</ul>
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<table id="staff_grtid" title="部门成员列表" fit="true" class="jq-datagrid"
			data-options="{
			url:'${z:u('/directory/cadre_list')}?departmentId='+${departmentId },
			method:'post',
			columns: [[
						{field:'id',checkbox:true},
						{field:'cadreName',title:'姓名',width:30},
						{field:'cadreSex',title:'性别',width:15,formatter:sexFormatter},
						{field:'dutiesName',title:'职务',width:30},
						{field:'officeTel',title:'办公室电话',width:80},
						{field:'moblePhone',title:'手机',width:80},
						{field:'remark',title:'备注',width:50},
			]]
			}">
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
	
	$("#close").on("click", function() {
		$(".panel-tool-close:eq(0)").click(); //eq(i) 其中i表示要关闭的层数
	});
</script>