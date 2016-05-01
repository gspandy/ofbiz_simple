<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="ofbizTags" prefix="ofbiz" %>
<!-- -----------------------------login----------------------- -->
<%
	//String username = session.getAttribute("autoUserLogin").userLoginId();
	//if(username == null){
		//username = "";
	//}
%>
<center>
    <div class="screenlet login-screenlet">
        <div class="screenlet-title-bar">
            <h3>登录</h3>
        </div>
        <div class="screenlet-body">
            <form method="post" action="" name="loginform">
                <table class="basic-table" cellspacing="0">
                    <tr>
                        <td class="label">用户名：</td>
                        <td><input type="text" name="USERNAME" value="" size="20"/></td>
                    </tr>
                    <tr>
                        <td class="label">密码：</td>
                        <td><input type="password" name="PASSWORD" value="" size="20"/></td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <input type="submit" value="登录"/>
                        </td>
                    </tr>
                </table>
                <input type="hidden" name="JavaScriptEnabled" value="N"/>
                <br/>
                <a href="">忘记密码？</a>
            </form>
        </div>
    </div>
</center>
