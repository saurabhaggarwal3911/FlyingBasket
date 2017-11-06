<%@page import="com.utility.ApplicationConfigurationEnum"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title> <tiles:insertAttribute name="title" ignore="false"/></title>
<link href="${pageContext.request.contextPath}/public/img/favicon.ico?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>" rel="shortcut icon" type="image/x-icon" />
<!-- Vendor CSS -->
<link href="${pageContext.request.contextPath}/public/css/fullcalendar.min.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/css/animate.min.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/css/sweet-alert.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>" rel="stylesheet">

<link href="${pageContext.request.contextPath}/public/css/bootstrap-select.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/css/material-design-iconic-font.min.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/css/jquery.mCustomScrollbar.min.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>" rel="stylesheet">
<!-- CSS -->
<link href="${pageContext.request.contextPath}/public/css/bootstrap-datetimepicker.min.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/css/style1.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/css/style2.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/css/custom.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>" rel="stylesheet">

<!-- Script -->
<script src="${pageContext.request.contextPath}/public/js/jquery.min.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
<script src="${pageContext.request.contextPath}/public/js/bootstrap.min.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
<script	src="${pageContext.request.contextPath}/public/js/jquery-ui.min.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
<script src="${pageContext.request.contextPath}/public/js/jquery.form.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
<script src="${pageContext.request.contextPath}/public/js/bootstrap-select.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
<script src="${pageContext.request.contextPath}/public/js/fileinput.min.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
<script	src="${pageContext.request.contextPath}/public/js/ease_common.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
</head>
<body class="toggled sw-toggled">
 <tiles:insertAttribute name="header" ignore="false"/>
 <section id="main">
  <div>
     <tiles:insertAttribute name="leftmenu" ignore="false"/>
      
             <tiles:insertAttribute name="body" ignore="false"/>
             </div>
             </section>

 <tiles:insertAttribute name="footer" ignore="false"/>

</body>
</html>