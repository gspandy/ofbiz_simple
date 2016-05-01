<!doctype HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/includes/envsetup.jsp" %>
<%@ taglib uri='regions' prefix='region' %>
<html>
<head>

</head>
<body>
<region:render section='appbar'/>
<div class="centerarea">
  <region:render section='header'/>
  <div class="contentarea">
    <div style='border: 0; margin: 0; padding: 0; width: 100%;'>
      <table style='border: 0; margin: 0; padding: 0; width: 100%;' cellpadding='0' cellspacing='0'>
        <tr>
          <td width='100%' valign='top' align='left'>
            <region:render section='error'/>
            <region:render section='content'/>
          </td>
        </tr>
      </table>       
    </div>
    <div class='spacer'></div>
  </div>
</div>
<region:render section='footer'/>
</body>
</html>