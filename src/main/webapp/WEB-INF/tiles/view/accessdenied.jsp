<%@page import="com.utility.ApplicationConfigurationEnum"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html >
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Flying Basket</title>
<link href="${pageContext.request.contextPath}/public/img/favicon.ico?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>" rel="shortcut icon" type="image/x-icon" />
<link href="${pageContext.request.contextPath}/public/css/animate.min.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/css/sweet-alert.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/css/material-design-iconic-font.min.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>" rel="stylesheet">
<!-- CSS -->
<link href="${pageContext.request.contextPath}/public/css/style1.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/css/style2.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/css/custom.css?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>" rel="stylesheet">
</head>
 <body class="four-zero-content" style="min-height: 100vh;">        
        <div class="four-zero">
            <h2>403</h2>
            <small>Access Denied</small>
            
            <footer>
                <a href="javascript:void(0)" onclick="window.history.back();return false;"><i class="zmdi zmdi-arrow-back"></i></a>
                <a href="<c:out value='/login'/>"><i class="zmdi zmdi-home"></i></a>
            </footer>
        </div>

        <!-- Older IE warning message -->
        <!--[if lt IE 9]>
            <div class="ie-warning">
                <h1 class="c-white">Warning!!</h1>
                <p>You are using an outdated version of Internet Explorer, please upgrade <br/>to any of the following web browsers to access this website.</p>
                <div class="iew-container">
                    <ul class="iew-download">
                        <li>
                            <a href="http://www.google.com/chrome/">
                                <img src="img/browsers/chrome.png" alt="">
                                <div>Chrome</div>
                            </a>
                        </li>
                        <li>
                            <a href="https://www.mozilla.org/en-US/firefox/new/">
                                <img src="img/browsers/firefox.png" alt="">
                                <div>Firefox</div>
                            </a>
                        </li>
                        <li>
                            <a href="http://www.opera.com">
                                <img src="img/browsers/opera.png" alt="">
                                <div>Opera</div>
                            </a>
                        </li>
                        <li>
                            <a href="https://www.apple.com/safari/">
                                <img src="img/browsers/safari.png" alt="">
                                <div>Safari</div>
                            </a>
                        </li>
                        <li>
                            <a href="http://windows.microsoft.com/en-us/internet-explorer/download-ie">
                                <img src="img/browsers/ie.png" alt="">
                                <div>IE (New)</div>
                            </a>
                        </li>
                    </ul>
                </div>
                <p>Sorry for the inconvenience!</p>
            </div>   
        <![endif]-->



<script src="${pageContext.request.contextPath}/public/js/jquery.min.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
<script src="${pageContext.request.contextPath}/public/js/waves.min.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
<script src="${pageContext.request.contextPath}/public/js/functions.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>

</body>
</html>
