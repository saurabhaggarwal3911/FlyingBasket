<!-- Javascript Libraries -->
<%@page import="com.utility.ApplicationConfigurationEnum"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="${pageContext.request.contextPath}/public/js/common.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
<script src="${pageContext.request.contextPath}/public/js/jquery.flot.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
<script src="${pageContext.request.contextPath}/public/js/jquery.flot.resize.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
<script src="${pageContext.request.contextPath}/public/js/curvedLines.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
<script src="${pageContext.request.contextPath}/public/js/jquery.sparkline.min.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
<script src="${pageContext.request.contextPath}/public/js/moment.min.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
<script src="${pageContext.request.contextPath}/public/js/fullcalendar.min.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
<script src="${pageContext.request.contextPath}/public/js/waves.min.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
<script src="${pageContext.request.contextPath}/public/js/bootstrap-growl.min.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
<script src="${pageContext.request.contextPath}/public/js/sweet-alert.min.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
<script src="${pageContext.request.contextPath}/public/js/jquery.mCustomScrollbar.concat.min.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
<script src="${pageContext.request.contextPath}/public/js/bootstrap-datetimepicker.min.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
<script src="${pageContext.request.contextPath}/public/js/typeahead.jquery.min.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
<!-- Placeholder for IE9 -->
<!--[if IE 9 ]>
            <script src="${pageContext.request.contextPath}/public/js/jquery.placeholder.min.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
        <![endif]-->
<script src="${pageContext.request.contextPath}/public/js/functions.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
<script src="${pageContext.request.contextPath}/public/js/welcome-text.js?version=<%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())%>"></script>
<style>
	.scroll-container{overflow:auto;}
</style>
<script type="text/javascript">
function setInnerBodyHeight(){
    if(elementExist($(".scroll-container"))){
    	var paginationHeight = 0;
    	 if(elementExist($("#paginationDiv"))){
    		 paginationHeight = $("#paginationDiv").outerHeight();
    		 $(".scroll-container").height(($(window).height())-($(".scroll-container").offset().top)-35-paginationHeight);
    	 }else{
    		 $(".scroll-container").height(($(window).height())-($(".scroll-container").offset().top)-25-paginationHeight);
    	 }
    	if(elementExist($(".summaries"))){
    		 $(".scroll-container").height( $(".scroll-container").height()-150);
    	}
    	if(elementExist($(".resultPage"))){
   		 $(".scroll-container").height( $(".scroll-container").height()-00);
   	}
   
    }
    if(elementExist($(".modal-body")) && !elementExist($(".no-height-change"))){
    	/* $(".modal-body").height(($(window).height())-($(".modal-body").offset().top)-120); */
    	$(".modal-body").removeClass("mCustomScrollbar m-b-0");
    	$(".modal-body").addClass(" mCustomScrollbar m-b-0"); 
    	
    	$(".modal-body").css({'height': 'auto'}); 
    	if(elementExist($(".min-audit-result-height"))){
    		$(".min-audit-result-height").attr("style",'min-height:200px !important; height:auto;'); 
        }
    
    	$(".modal-body").css({'max-height':($(window).height())-($(".modal-body").offset().top)-150}); 
    	
    	
    }
      
	}
/*            $(".report-scroll").height(($(window).height())-($(".report-scroll").offset().top)-30);          
 */      
  $(document).ready(function () {
                setInnerBodyHeight();
              $( window ).resize(function() {
                setInnerBodyHeight();
            });
              
              //to show welcome message
              <%
              if((Integer)request.getSession().getAttribute("count")==0){
            	  request.getSession().setAttribute("count", -1);
              %>
              if (!$('.login-content')[0]) {
            	  <sec:authorize access="isAuthenticated()">
              	 notify('Welcome <sec:authentication property="principal.name"/>', 'inverse');
              	</sec:authorize>
              } 
              <%}%>
              
        });
  
  $('#logoutAnchor').click(function(){
      swal({   
          title: "Are you sure you want to logout?",   
          //text: "You will not be able to recover this imaginary file!",   
          //type: "info",
          imageUrl: '/public/img/info.png',
          showCancelButton: true,   
          confirmButtonColor: "#f4006b",   
          confirmButtonText: "Logout",   
          closeOnConfirm: false 
      }, function(){   
      	document.getElementById('logout').submit();
      });
  });
</script>





<div class="modal app-model fade" id="aboutUsModal" tabindex="-1"
		role="dialog" aria-labelledby="aboutUsLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close jpopup-close-btn  "
						data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">About</h4>
				</div>
				<div class="modal-body aboutus-cont text-center">
				     <img class="logo-img" src="<c:url value='/public/img/fg-logo-p.png'/>">
				
				     <%=getServletContext().getAttribute(ApplicationConfigurationEnum.APP_NAME.toString())%> HT <%=getServletContext().getAttribute(ApplicationConfigurationEnum.HT_VERSION.toString())%> has been developed by
					 Flying Basket.
				</div>
			</div>
		</div>
</div>


  <div class="modal app-model fade" id="profileModal" tabindex="-1"
		role="dialog" aria-labelledby="profileLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				
			</div>
		</div>
</div>

<div class="modal app-model fade" id="feedbackModal" tabindex="-1"
		role="dialog" aria-labelledby="feedbackLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				
			</div>
		</div>
</div>
	
		
		
		<div id="preview1" class="custom-overlay" style="display:none">
<div class="custom-spinner">
   <i class="zmdi zmdi-hc-spin zmdi-spinner"></i>
   

</div> 
</div>