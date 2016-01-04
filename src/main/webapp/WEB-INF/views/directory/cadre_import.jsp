<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>

<form id="form" action="${__url__}" method="post">
    <table align="left" class="form-table" style="margin:10px 0px">
		<tr>
			<td>所属区域：</td>
			<td>
           <input class="jq-combotree" type="text" id="areaId"  name="areaId" url="${z:u('public/area_list?ui=combo')}" data-options="{
						required:true,
						method:'post',
						onLoadSuccess:function(){$('#areaId').combotree('tree').tree('collapseAll')},				
						valueField:'id'
					}" />
			</td>
			<td class="tr">Excel文件：</td>
				<td>
					<input name="file" type="file">
				</td>
		</tr>
		<tr>

		<tr>
			<td>&nbsp;</td>
			<td>
				<button type="submit"  class="btn btn-small btn-success">确定</button>
				<button class="btn btn-primary btn-small J_close" type="button">返回</button>
				<a id="download_model" class="btn btn-small btn-info"><i class="icon icon-edit"></i>下载模板</a>
				
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
	$("#download_model").on("click", function() {
	    var areaId = $("#areaId").combotree("getValue");
	    if(areaId == ''){
	    	 App.alert("请先选择一个区域", "info");
	    }else{
	         App.alert("导出一个Excel（待开发）", "info");
	    }
	});
</script>
