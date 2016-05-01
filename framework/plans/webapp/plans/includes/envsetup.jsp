<%@ page import="java.util.*, java.net.*" %>
<%@ page import="org.ofbiz.security.*, org.ofbiz.entity.*, org.ofbiz.base.util.*, org.ofbiz.content.webapp.pseudotag.*, org.ofbiz.content.webapp.control.*" %>
<%@ page import="org.ofbiz.securityext.login.*, org.ofbiz.common.*" %>



<jsp:useBean id="security" type="org.ofbiz.security.Security" scope="request" />
<jsp:useBean id="delegator" type="org.ofbiz.entity.GenericDelegator" scope="request" />
<%
   //GenericValue userLogin = (GenericValue) session.getAttribute("userLogin");
    //if (userLogin != null){
		//request.setAttribute("userLogin", userLogin);
	//} 
	//Map layoutSettings = new HashMap();
    //layoutSettings.put("userName", userLogin.getString("passwordHint"));
%>
