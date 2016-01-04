<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>

<form id="form" action="${__url__}" method="post">
    <table align="left" class="form-table" style="margin:10px 0px">
    	<tr>
			<td>职务名称：</td>
			<td>
			<input type="text" class="jq-validatebox" name="dutiesName"  data-options="required:true"   />           			
			</td>
		</tr>
		<tr>
            <td >职务备注：</td>
			<td>
				<input name="remark"   type="text" >
			</td>
		</tr>	
		<tr>
			<td>排序值：</td>
			<td>
				<input name="sortId" class="jq-numberspinner"  type="text">
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
