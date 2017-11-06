<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<aside id="sidebar" class="sidebar c-overflow mCustomScrollbar">

    <div class="mCustomScrollBox mCS-minimal-dark " tabindex="0">
      <div>
        <div class="profile-menu"> <a href="javascript:void(0)" style="cursor: default;">
          <div class="profile-pic"> <img src="<c:url value='/public/img/1.jpg'/>" alt=""> </div>
        
          </a>
          
        </div>
        <ul class="main-menu">
           <li class="${user_active_class}"> <a href="<c:url value='/common/user/manage'/>"><i class="zmdi zmdi-account-o"></i> Users</a> </li>
          <li class="${order_active_class}"> <a href="<c:url value='/common/order/manage'/>"><i class="zmdi zmdi-assignment-check"></i> Orders</a> </li>
          <%--  <li class="${chain_active_class}"> <a href="<c:url value='/common/chain/manage'/>"><i class="zmdi zmdi-accounts-outline"></i> Chain Tree</a> </li> --%>
         --%> </ul>
      </div>
    </div>
  </aside>
  
  