<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!-- Create Profile Modal 
<div class="modal fade bs-example-modal-lg" id="profileModal" tabindex="-1" role="dialog" aria-labelledby="profileLabel" aria-hidden="true" style="display: block;">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content"> -->
      
      <div class="modal-header">
        <button type="button" class="close jpopup-close-btn  " data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h3 class="modal-title" id="myModalLabel">Profile</h3>
      </div>

      <div class="modal-body">
 
        <c:if test="${success != null}">
			<div class="alert alert-success alert-dismissible m-b-30"  role="alert" id="message-container">
				<spring:message code="${success}"/>
			</div>
			<script type="text/javascript">
		setTimeout(function() {
			window.location.href = window.location.href;
		}, 1000);
	        </script>
		</c:if>     
		<c:if test="${error != null}">
		<div class="alert alert-danger alert-dismissible m-b-30" role="alert"  id="message-container">
             <spring:message code="${error}"/>
        </div>
		<script type="text/javascript">
			setTimeout(function() {
				$("#message-container").remove();
			}, 1000);
		</script>
	</c:if>

        <div class="audit-popup-cont">
         <div class="row">
          <div class="col-xs-12">
            
            <div class="card p-0 m-b-20" style="box-shadow:none">                
                <div class="card-header  p-0">
                    <h2 class="p-b-15">Name <small><sec:authentication property="principal.name"/></small></h2>
                    <h2>Email <small><sec:authentication property="principal.username"/></small></h2>
                </div> 
            </div>
          
	
	
	
	<form:form id="changePasswordForm"  method="POST" commandName="changePasswordDto" autocomplete="off">
    <c:set var="oldPasswordHasBindError"><form:errors path="oldPassword"/></c:set>
    <c:set var="newPasswordHasBindError"><form:errors path="newPassword"/></c:set>
	
    <h4 class="h4-custom m-t-25">Reset Password</h4>
             <div class="form-group  ${not empty oldPasswordHasBindError? 'has-error' : ''}">
                <div class="fg-line">
                    <form:password path="oldPassword" class="form-control m-t-0" placeholder="Old Password" id="oldpassword" autocomplete="off"/>
                </div>
                <form:errors path="oldPassword" class="help-block" element="small"/>
             </div>
                       
             <div class="form-group ${not empty newPasswordHasBindError? 'has-error' : ''}">
                <div class="fg-line">
                    <form:password path="newPassword" class="form-control" placeholder="New Password" id="newpassword" autocomplete="off"/>
                </div>
                <form:errors path="newPassword" class="help-block" element="small"/>
             </div>
             
             <div class="form-group">
                <div class="fg-line">
                    <input type="password" class="form-control" placeholder="Confirm Password" id="confirmpassword">
                </div>
             </div>

    </form:form>	
	

              	     
          	 
         
          </div>
        
        </div>
       </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" onclick="return resetpassword()">Save</button>
        <button type="button" class="btn btn-primary" data-dismiss="modal">Cancel</button>
      </div>
    </div>
  


<script type="text/javascript">


$("#confirmpassword").keypress(function(event) {
    if (event.which == 13) {
        event.preventDefault();
        return resetpassword();
    }
});


  
    function resetpassword()
    {
	    var success = true;
	    if(isFieldBlank("oldpassword", '<spring:message code="NotBlank.userDto.password"/>')){
		success = false;
	    }
	  
	    if(isFieldBlank("newpassword", '<spring:message code="NotBlank.userDto.password"/>')){
	    success = false;
	    }
	  
        else if(isFieldHaveLessCharacter("newpassword", 4, '<spring:message code="Size.userDto.password"/>')){
    	success = false;
	    }
	  
        else if(isFieldsHaveSameValue("oldpassword", "newpassword", '<spring:message code="Match.userDto.newAndOldpassword"/>')){
 		success = false;		 
 	    }
	  
	    if(isFieldBlank("confirmpassword", '<spring:message code="NotBlank.userDto.password"/>')){
	    success = false;
	    }
	  
        else if(isFieldsHaveDifferValue("newpassword", "confirmpassword", '<spring:message code="NotMatch.userDto.confirmpassword"/>')){
    	success = false;
	    }
	  
	  
	    if(success){
		  
		    $("#preview1").show();
				var options = {
			    cache: false,
				error: function(xhr){
				    	$("#preview1").hide();
							if(xhr.status==401){
							window.location = '<c:url value="/login"/>';
							}
						},
				success: function(data, sta, xhr) {
				         $("#preview1").hide();
						    if(xhr.status==401){
						    window.location = '<c:url value="/login"/>';
							}
						$("#profileModal .modal-content").html(data);
				}
				};
				    	$('#changePasswordForm').ajaxSubmit(options); 
	    }
		return false;
	}
	
	
</script>          	    						
					
