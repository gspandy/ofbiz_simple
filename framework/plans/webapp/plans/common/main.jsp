<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="ofbizTags" prefix="ofbiz" %>
<%
	String userName = "1";
%>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" style="height:80px;background:#E4EEFF;padding:20px">
	<span style="float:right;margin-right:50px"><a href="logout">登出</a></span>
	</div>
	<div data-options="region:'west',split:true,title:'工具导航'" style="width:250px;">
		<div class="easyui-accordion" fit="true" border='0'>
			<div title="About" data-options="iconCls:'icon-ok'">
				<br/>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onClick="addTab('工具箱','')" style="width:100%;text-align:left">工具箱</a>
			</div>
			<div title="Help">
				22222222222222	
			</div>
			<div title="About">
				33333333333333
			</div>
			<div title="Help">
				444444444444444	
			</div>
			<div title="About" >
				5555555555555
			</div>
			<div title="Help">
				6666666666666666	
			</div>
			<div title="About" >
				777777777777777
			</div>
			<div title="工具箱">
				<br/>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onClick="addTab('工具箱','')" style="width:100%;text-align:left">工具箱</a>
			</div>
		</div>
	</div>
	<div data-options="region:'south',border:false" style="height:18px;background:#E4EEFF;padding:0px;">
		<span style="float:right;margin-right:50px">PYJ工作室</span>
	</div>
	<div data-options="region:'center'">
		<div id="tt" class="easyui-tabs" data-options="tools:'#tab-tools',fit:true,plain:true,border:false">
		</div>
	</div>
</body>
<script type="text/javascript">
	function addTab(title,url){
		var name = 'iframe_'+title; 
		//判断title选项卡是否存在
		if($('#tt').tabs('exists',title)){
			$('#tt').tabs('select',title);
  			return ;
		}else{
			$('#tt').tabs('add',{
			title:title,
			content : '<iframe name="'+name+'" FRAMEBORDER=no id="'+name+'" src="" width="100%" height="100%" frameborder="5" border="0" scrolling="auto" tar=tar></iframe>',
			closable:true,
			cache:false
			});
		}
	}
</script>

