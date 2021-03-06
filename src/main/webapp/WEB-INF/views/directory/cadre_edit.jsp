<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>

<form id="form" action="${__url__}" method="post">
    <table align="center" class="form-table">
    	<tr>
			<td>姓名：</td>
			<td>
			<input type="text" class="jq-validatebox" name="cadreName" value="${entity.cadreName}"   data-options="required:true"   />           			
			</td>
			<td class="tr">性别：</td>
			<td>
			<select class="jq-combobox" name ="cadreSex"    data-options="required:true" >
        		    <option value ="1">男</option>
  					<option value ="0">女</option>
  			<c:if test="${entity.cadreSex!=null }"><option value="${ entity.cadreSex}" selected="selected"></option></c:if>
  			
  			
  			</select> 
  			</td>  
		</tr>
		<tr>
			<td>所属区域：</td>
			<td>
           <input class="jq-combotree" type="text" id="areaId"  value="${entity.areaId}"  name="areaId" url="${z:u('public/area_list?ui=combo')}" data-options="{
						required:true,
						method:'post',
						onLoadSuccess:function(){$('#areaId').combotree('tree').tree('collapseAll')},				
						valueField:'id'
					}" />
			</td>
			<td class="tr">所属部门：</td> 
			<td>
					<select class="jq-combobox"  id="departmentId"  name="departmentId"  data-options="{
					required:true,
					method:'post',
					url: '${z:u('public/department_list')}?areaId=${entity.areaId}'}">
				    <c:if test="${entity.departmentId!=null }"><option value="${entity.departmentId}" selected="selected"></option></c:if>
					</select>
			</td>
		</tr>
		<tr>
		 <td>职务：</td>
			<td>
            <select class="jq-combobox" id="dutiesId" name="dutiesId"   data-options="{
					required:true,
					method:'post',
					url: '${z:u('public/duties_list')}'}">
					<c:if test="${entity.dutiesId!=null }"><option value="${ entity.dutiesId}" selected="selected"></option></c:if>
					</select>
			</td>
            <td class="tr">办公室电话：</td>
			<td>
				<input name="officeTel" value="${entity.officeTel}"    type="text" >
			</td>
		</tr>
		<tr>
			<td>手机：</td>
			<td>
				<input name="moblePhone" class="jq-validatebox" value="${entity.moblePhone}"   type="text">
			</td>
			<td class="tr">备注：</td>
			<td>
				<input name="remark" class="jq-validatebox" value="${entity.remark}"   type="text" >
			</td>
		</tr>		
		<tr>
			<td>&nbsp;</td>
			<td>
				<button type="submit"  class="btn btn-small btn-success">确定</button>
				<button class="btn btn-primary btn-small J_close" type="button">返回</button>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">

	$(document).ready(function() {
		$("#areaId").combotree({
			onChange : function(n, o) {
				var areaId = $("#areaId").combotree("getValue");
				initDeparment(areaId);
				$('#departmentId').combobox('enable');
				$('#departmentId').combobox('clear');
			}
		});
	});
	

	function initDeparment(areaId) {
		$('#departmentId').combobox({
			method : 'post',
			url : '${z:u("public/department_list")}?areaId='+areaId,
			valueField : 'id',
			textField : 'text'
		});
	}
</script>
