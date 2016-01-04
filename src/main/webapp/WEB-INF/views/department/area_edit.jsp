<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>

<form id="form" action="${__url__}" method="post">
    <table align="center" class="form-table">
		<tr>
			<td>父节点：</td>
			<td>
				<input class="jq-combotree" type="text" id="pid"   value="${entity.pid}"  
                 name="pid" url="${z:u('department/area_list?ui=combo')}" data-options="{
						required:true,
						method:'post',
						onLoadSuccess:function(){$('#pid').combotree('tree').tree('collapseAll')},				
						valueField:'id'
					}" />
			</td>
		</tr>
		<tr>
			<td>区域名称：</td>
			<td>
				<input name="areaName" class="jq-validatebox" value="${entity.areaName}" type="text" data-options="required:true">
			</td>
		</tr>
		<tr>
			<td>排序值：</td>
			<td>
				<input name="sortId" class="jq-numberspinner" value="${entity.sortId}" type="text" data-options="required:true" >
			</td>
		</tr>
		<tr>
			<td>是否显示：</td>
			<td>
				<label class="radio inline">
				  <input type="radio" name="isShow" value="1" ${entity.isShow==1?"checked":""}>
				  是
				</label>
				<label class="radio inline">
				  <input type="radio" name="isShow" value="0"  ${entity.isShow==0?"checked":""}>
				  否
				</label>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>
				<button type="submit" class="btn btn-small btn-success">确定</button>
			    <button class="btn btn-primary btn-small J_close" type="button">返回</button>
			</td>
		</tr>
	</table>
</form>