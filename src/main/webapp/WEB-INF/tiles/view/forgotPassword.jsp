<%@page import="com.utility.ApplicationConfigurationEnum"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title><tiles:insertAttribute name="title" ignore="false" /></title>
<meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
 <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/css/materialize.min.css">
 <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
 <link href="${pageContext.request.contextPath}/public/new/css/materialdesignicons.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>" rel="stylesheet" type="text/css" />
 <link href="${pageContext.request.contextPath}/public/new/css/custom.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>" rel="stylesheet" type="text/css" />

</head>
  <style type="text/css">


html,
body {
    height: 100%;
}
html {
    display: table;
    margin: auto;
}
body {
    display: table-cell;
    vertical-align: middle;
}
    .login-form {
    width: 280px;
}
.green {
    background-color: #259b24 !important;
}
.profile-image-login{
  margin-top: 10px;
}
#login-page{
  font-size: 14px;
}
.help-block {
    display: block;
    margin-top: 5px;
    margin-bottom: 10px;
    color: #9e9e9e;
}
.has-error .help-block{
    color: #f6675d;
}
  </style>
<body class="green" style="min-width: inherit;">


	  <div id="login-page" class="row">
    <div class="col s12 z-depth-4 card-panel">
      <form class="login-form" name="loginForm"
			onsubmit="return loginsubmit();" action="j_spring_security_check"
			method='POST' autocomplete="off" id="loginForm">
        <div class="row">
          <div class="input-field col s12 center">
          <img src="${pageContext.request.contextPath}/public/new/img/login-logo.png?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>" alt="" class=" profile-image-login responsive-img ">
          </div>
        </div>
        <div class="row m-b-0">
          <div class="input-field col s12"> 
            <i class="mdi mdi-account-outline prefix"></i>
            <input id="username" type="text" name="username">
            <label for="username">Username</label>
          </div>
        </div>
        <div class="row m-b-0">
          <div class="input-field col s12"> 
             <i class="mdi mdi-lock-open-outline prefix"></i>
            <input id="password" type="password">
            <label for="password" class="" name="password">Password</label>
          </div>
        </div>
        
        <div class="row  m-b-0">
          <div class="input-field col s12">
            <a href="javascript:formSubmit('loginForm');" class="btn waves-effect waves-light col s12">Login</a>
          </div>
        </div>
        <div class="row  m-b-0">
          <div class="input-field col s12">
	        <c:if test="${error != null}">
				<div class="alert alert-danger alert-dismissible" role="alert" id="message-container">
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
		  </div>
        </div>
        <div class="row">
          <div class="input-field col s6 m6 l6">
           <!--  <p class="margin medium-small"><a href="#">Register Now!</a></p> -->
          </div>
          <div class="input-field col s6 m6 l6">
              <p class="margin right-align medium-small"><a href="forgotpassword">Forgot password ?</a></p>
          </div>          
        </div>

      </form>
    </div>
  </div>
     <script
		src="${pageContext.request.contextPath}/public/new/js/jquery-1.9.1.min.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
	
	<script
		src="${pageContext.request.contextPath}/public/new/js/common.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
	<script
		src="${pageContext.request.contextPath}/public/new/js/login.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>

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

	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/js/materialize.min.js"></script>
 
   
</body>
</html>