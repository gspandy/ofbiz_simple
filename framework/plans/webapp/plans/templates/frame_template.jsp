<!doctype HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/includes/envsetup.jsp" %>
<%@ taglib uri='regions' prefix='region' %>
<html>
<head>
	<meta charset="UTF-8">
	<title>Demo</title>
	<link rel="stylesheet" type="text/css" href="/images/themeUI/jquery-easyui-1.4.5/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="/images/themeUI/jquery-easyui-1.4.5/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="/images/themeUI/jquery-easyui-1.4.5/demo/demo.css">
	<script type="text/javascript" src="/images/themeUI/jquery-easyui-1.4.5/jquery.min.js"></script>
	<script type="text/javascript" src="/images/themeUI/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
</head>
<region:render section='appbar'/>
<region:render section='error'/>
<region:render section='content'/>
<region:render section='footer'/>
</html>