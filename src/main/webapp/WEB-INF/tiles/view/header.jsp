<%@page import="com.utility.ApplicationConfigurationEnum"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 <sec:authentication var="roleId" property="principal.roleId" scope="request"/>
<header id="header" class="clearfix" data-current-skin="blue">
  <ul class="header-inner ">
     <li id="menu-trigger" data-trigger="#sidebar">
      <div class="line-wrap">
        <div class="line top"></div>
        <div class="line center"></div>
        <div class="line bottom"></div>
      </div>
    </li>
    <li class="logo">  
    <%-- <c:if test="${roleId==3 }">
    <a href="<c:url value='/workspace/audit/manage'/>"> 
    <img src="<c:url value='/public/img/fg-logo.png'/>" alt="" class="mCS_img_loaded">Flying Basket </a>
    </c:if>
     <c:if test="${roleId==1}"> --%>
     <a href="<c:url value='/common/user/manage'/>"> 
    <img src="<c:url value='/public/img/fg-logo.png'/>" alt="" class="mCS_img_loaded">Flying Basket </a>
    <%--  </c:if>
     <c:if test="${roleId==2}">
     <a href="<c:url value='/userAudit/myaudits/manage'/>"> 
     <img src="<c:url value='/public/img/fg-logo.png'/>" alt="" class="mCS_img_loaded">Flying Basket </a>
     </c:if> --%>
     </li>
    <li class="pull-right">
      <ul class=" top-menu ">
        <li class="dropdown"> <a data-toggle="dropdown" href="javascript:void(0)"><i class="tm-icon zmdi zmdi-more-vert"></i></a>
          <ul class="dropdown-menu dm-icon pull-right">
        <!--     <li class="hidden-xs"> <a  href="javascript:createProfilePopup()"><i class="zmdi zmdi-account"></i> Profile</a> </li>
<!--             <li class="hidden-xs"> <a  href="javascript:createFeedbackPopup()"><i class="zmdi zmdi-comments"></i> Feedback</a> </li>
 -->           
 
 <li> <a href="javascript:aboutPopup();"><i><img class="abouticon" src="<c:url value='/public/img/fg-icon-gray.svg'/>"></i> About</a> </li>
            <li> 
            <c:url value="/j_spring_security_logout" var="logoutUrl" />
<form id="logout" action="${logoutUrl}" method="post" >
</form>
	<a  id="logoutAnchor" href="javascript:void(0)"><i class="logout-icon"></i> Logout</a>
            
            </li>
          </ul>
        </li>
      </ul>
    </li>
     <li class="pull-right welcometext p-r-10"><small>Welcome</small>
      <h2>
      <sec:authentication property="principal.name"/>
      </h2>
    </li>
  </ul>
</header>