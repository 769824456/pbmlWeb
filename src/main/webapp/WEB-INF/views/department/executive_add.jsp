<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>

<form id="form" action="${__url__}" method="post">
    <table align="center" class="form-table">
		<tr>
			<td>所属区域：</td>
			<td>
				<input class="jq-combotree" type="text"  id="areaId"  readonly="readonly"  value="${areaId}" name="areaId" url="${z:u('public/area_list?ui=combo')}" data-options="{
						required:true,
						method:'post',
						onLoadSuccess:function(){$('#areaId').combotree('tree').tree('collapseAll')},				
						valueField:'id'
					}" />
			</td>
		</tr>
		<tr>
			<td>部门名称：</td>
			<td>
				<input name="name" class="jq-validatebox" value="" type="text" data-options="required:true">
			</td>
		</tr>
		<tr>
			<td>联系地址：</td>
			<td>
				<input name="address" class="jq-validatebox" value="" type="text" >
			</td>
		</tr>
		<tr>
			<td>办公室电话：</td>
			<td>
				<input name="officeTel" class="jq-validatebox" value="" type="text" >
			</td>
		</tr>
		<tr>
			<td>排序值：</td>
			<td>
				<input name="sortId" class="jq-numberspinner"  value="0" type="text" data-options="required:true" >
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