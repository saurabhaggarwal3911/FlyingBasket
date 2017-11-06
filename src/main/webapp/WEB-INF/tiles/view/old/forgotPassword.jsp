<%@page import="com.utility.ApplicationConfigurationEnum"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>

<!DOCTYPE html>
<html class="login-content">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title> <tiles:insertAttribute name="title" ignore="false"/></title>
<link href="${pageContext.request.contextPath}/public/img/favicon.ico?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>" rel="shortcut icon" type="image/x-icon" />
<link href="${pageContext.request.contextPath}/public/css/animate.min.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/css/sweet-alert.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/css/material-design-iconic-font.min.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>" rel="stylesheet">
<!-- CSS -->
<link href="${pageContext.request.contextPath}/public/css/style1.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/css/style2.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/css/custom.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>" rel="stylesheet">
<script src="${pageContext.request.contextPath}/public/js/jquery.min.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>

</head>
<body class="login-content" style="min-width:inherit;">


<!-- Forgot Password -->
<div class="lc-block toggled login-cont" id="l-forget-password">
   <h1><img src="${pageContext.request.contextPath}/public/img/fg-logo-p.png?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></h1>
  <h3 class=" m-b-20">Flying Basket</h3>
   <div class="login-title">Forgotten Password</div>
   <form class="form-login" name="forgotPasswordForm"
		onsubmit="return forgotPassword();"
		action="" method='POST' autocomplete="off" id="forgotPasswordForm">
<div class="login-bg p-30">
    <p class="text-left">Enter your email address below and we'll send you password reset instructions.</p>
   <%--   <div class="input-group m-b-20"> <span class="input-group-addon"><i class="zmdi zmdi-railway"></i></span>
          <div class="fg-line">
              <div class="select" style="position:initial;">
               <select name='toc'  class="form-control g-text">
			<c:forEach items="${tocList}" var="tocVar">
				<c:choose>
					<c:when test="${tocVar.code==toc}">
					<option value="${tocVar.code}" selected="selected">${tocVar.name}</option>
					</c:when>
					<c:otherwise>
				<option value="${tocVar.code}">${tocVar.name}</option>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			</select>
                          </div>
                           </div>
                  
  </div>
     --%>
  <div class="input-group m-b-20"> <span class="input-group-addon hide-effets"><i class="zmdi zmdi-email"></i></span>
    <div class="fg-line">
      <input type="text" class="form-control" placeholder="Email Address" id=emailId value="${email}" name="email">
    </div>
  </div>
  
  
   <a href="login" class="btn btn-login btn-danger btn-float forgot-btn-left"><i class="zmdi zmdi-arrow-left"></i></a> 
  <a href="javascript:formSubmit('forgotPasswordForm');" class="btn btn-login btn-danger btn-float"><i class="zmdi zmdi-arrow-forward"></i></a> 
  
  <div class="clearfix"></div>

      <c:if test="${error != null}">
		<div class="alert alert-danger alert-dismissible" role="alert"  id="message-container">
                             <spring:message code="${error }" />
                            </div>
		
			<script type="text/javascript">
				setTimeout(function() {
					$("#message-container").remove();
				}, 3000);
			</script>
		</c:if>
		<c:if test="${success != null}">
			
		
			<div class="alert alert-success alert-dismissible"  role="alert" id="message-container">
				<spring:message code="${success }" />
			</div>
			<script type="text/javascript">
				setTimeout(function() {
					$("#message-container").remove();
					location.href="login";
				}, 3000);
			</script>
		</c:if>
  
</div>
</form>
</div>


<script src="${pageContext.request.contextPath}/public/js/waves.min.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
<script src="${pageContext.request.contextPath}/public/js/functions.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
<script src="${pageContext.request.contextPath}/public/js/common.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
<script src="${pageContext.request.contextPath}/public/js/login.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
<script	src="${pageContext.request.contextPath}/public/js/ease_common.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>

<script type="text/javascript">
$(document).ready(function() {
	document.forgotPasswordForm.email.focus();
});

</script>
</body>
</html>
