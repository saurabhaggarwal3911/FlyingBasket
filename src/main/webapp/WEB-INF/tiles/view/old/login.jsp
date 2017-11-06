<%@page import="com.utility.ApplicationConfigurationEnum"%>
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
<title><tiles:insertAttribute name="title" ignore="false" /></title>
<link
	href="${pageContext.request.contextPath}/public/img/favicon.ico?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"
	rel="shortcut icon" type="image/x-icon" />
<link
	href="${pageContext.request.contextPath}/public/css/animate.min.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/public/css/sweet-alert.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/public/css/material-design-iconic-font.min.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"
	rel="stylesheet">
<!-- CSS -->
<link
	href="${pageContext.request.contextPath}/public/css/style1.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/public/css/style2.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/public/css/custom.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"
	rel="stylesheet">
<!-- Not required  css file added just for cache -->
<link
	href="${pageContext.request.contextPath}/public/css/fullcalendar.min.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/public/css/bootstrap-datetimepicker.min.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/public/css/jquery.mCustomScrollbar.min.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"
	rel="stylesheet">
	
<script	src="${pageContext.request.contextPath}/public/js/ease_common.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>

</head>
<body class="login-content" style="min-width: inherit;">



	<div class="lc-block toggled p-t-10 login-cont" id="l-login">
		<h1>
			<img
				src="${pageContext.request.contextPath}/public/img/fg-logo-p.png?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>">
		</h1>
		<h3 class=" m-b-20">Flying Basket</h3>
		<div class="login-title">Login</div>
		<form class="form-login" name="loginForm"
			onsubmit="return loginsubmit();" action="j_spring_security_check"
			method='POST' autocomplete="off" id="loginForm">
			<div class="login-bg">
				<div class="form-group "></div>
				<%-- 
  <div class="input-group m-b-20"> <span class="input-group-addon"><i class="zmdi zmdi-railway"></i></span>
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
				<div class="input-group m-b-20">
					<span class="input-group-addon hide-effets"><i
						class="zmdi zmdi-account"></i></span>

					<div class="fg-line">
						<input type="text" class="form-control" placeholder="Username"
							id="username" value="" name="username">
					</div>
				</div>
				<div class="input-group m-b-20">
					<span class="input-group-addon hide-effets"><i
						class="zmdi zmdi-lock-open"></i></span>
					<div class="fg-line">
						<input type="password" class="form-control" placeholder="Password"
							id="password" value="" name="password">
					</div>
				</div>

				<div class="clearfix"></div>
				<a href="javascript:formSubmit('loginForm');"
					class="btn btn-login btn-float"><i
					class="zmdi zmdi-arrow-forward"></i></a>

				<c:if test="${error != null}">
					<div class="alert alert-danger alert-dismissible" role="alert"
						id="message-container">
						<spring:message code="${error }" />
					</div>

					<script type="text/javascript">
						setTimeout(function() {
							$("#message-container").remove();
						}, 3000);
					</script>
				</c:if>
				<c:if test="${logout != null}">


					<div class="alert alert-success alert-dismissible" role="alert"
						id="message-container">
						<spring:message code="${logout }" />
					</div>
					<script type="text/javascript">
						setTimeout(function() {
							$("#message-container").remove();
						}, 3000);
					</script>
				</c:if>


				<ul class="login-navigation">
					<li class="bgm-orange large-text"><a href="forgotpassword">Forgotten
							password?</a></li>
				</ul>

			</div>

		</form>

	</div>



	<script
		src="${pageContext.request.contextPath}/public/js/jquery.min.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
	<script
		src="${pageContext.request.contextPath}/public/js/waves.min.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
	<script
		src="${pageContext.request.contextPath}/public/js/functions.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
	<script
		src="${pageContext.request.contextPath}/public/js/common.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
	<script
		src="${pageContext.request.contextPath}/public/js/login.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>

	<script type="text/javascript">
		 function isLoginFieldBlank(fieldId, errormessage) {
			var field = document.getElementById(fieldId);
			var fieldValue = field.value;
			var nextElem = $(field).parent().next();
			if (elementExist(nextElem) && nextElem.is("small")) {
				$(nextElem).remove();
				$("#" + fieldId).parent().parent().removeClass("has-error");
			}
			if (isBlank(fieldValue)) {
				$(field).parent().after(
						'<small class="help-block">' + errormessage
								+ '</small>');
				$("#" + fieldId).parent().parent().addClass("has-error");
				return true;
			}
			return false;
		}
		function loginsubmit() {
			var isSuccess = true;
			if (isLoginFieldBlank("username",
					'<spring:message code="login.blankusername"/>')) {
				isSuccess = false;
			}
			if (isLoginFieldBlank("password",
					'<spring:message code="login.blankpassword"/>')) {
				isSuccess = false;
			}
			return isSuccess;
		} 
		$(document).ready(function() {

			setTimeout(function() {
				$(".form-control").focus();
				$("#username").focus();
			}, 50);

			$("#password").click(function() {
				$("#message-container").remove();
			});
			$("#username").click(function() {
				$("#message-container").remove();
			});

		});

		$("#loginForm input").keypress(function(e) {
			if (e.keyCode == 13) {
				$(this).closest("form").submit();
			}
		});
	</script>

	<!-- Not required js file added just for cache -->
	<script
		src="${pageContext.request.contextPath}/public/js/bootstrap.min.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
	<script
		src="${pageContext.request.contextPath}/public/js/jquery.form.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
	<script
		src="${pageContext.request.contextPath}/public/js/jquery.flot.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
	<script
		src="${pageContext.request.contextPath}/public/js/jquery.flot.resize.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
	<script
		src="${pageContext.request.contextPath}/public/js/curvedLines.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
	<script
		src="${pageContext.request.contextPath}/public/js/jquery.sparkline.min.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
	<script
		src="${pageContext.request.contextPath}/public/js/moment.min.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
	<script
		src="${pageContext.request.contextPath}/public/js/fullcalendar.min.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
	<script
		src="${pageContext.request.contextPath}/public/js/bootstrap-growl.min.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
	<script
		src="${pageContext.request.contextPath}/public/js/jquery.mCustomScrollbar.concat.min.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
	<script
		src="${pageContext.request.contextPath}/public/js/bootstrap-datetimepicker.min.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
	<script
		src="${pageContext.request.contextPath}/public/js/welcome-text.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
	<script
		src="${pageContext.request.contextPath}/public/js/typeahead.jquery.min.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>


</body>
</html>
