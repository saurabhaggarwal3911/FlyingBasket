<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

      <div class="modal-header">
        <button type="button" class="close jpopup-close-btn  "  data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Add User</h4>
      </div>
      <div class="modal-body">
      <c:if test="${error != null}">
		<div class="alert alert-danger alert-dismissible m-b-30" role="alert"  id="message-container">
                             ${error}
                            </div>
			<script type="text/javascript">
				setTimeout(function() {
					$("#message-container").remove();
				}, 3000);
			</script>
		</c:if>
		<c:if test="${success != null}">
			<div class="alert alert-success alert-dismissible m-b-30"  role="alert" id="message-container">
				${success}
			</div>
			<script type="text/javascript">
		setTimeout(function() {
			window.location.href = window.location.href;
		}, 1000);
	</script>
		</c:if>
		
        
        <div class="audit-popup-cont">
		<form:form id="addUserForm" modelAttribute="userDto" action=""
		method="post" onsubmit="return createAudit();">
		<c:set var="nameHasBindError"><form:errors path="name"/></c:set>
		<c:set var="emailHasBindError"><form:errors path="email"/></c:set>
		<c:set var="mobileHasBindError"><form:errors path="mobile"/></c:set>
		<c:set var="passwordHasBindError"><form:errors path="password"/></c:set>
		<div class="row">
          <div class="col-xs-12 ${not empty nameHasBindError? 'has-error' : ''}">
            <div class="fg-line">
            	<form:input path="name" class="form-control m-t-0" placeholder="Name" id="adduserform_name"/>
            </div>
            <form:errors path="name" class="help-block" element="small"/>
          </div>
        </div>
        <div class="row">
         <div class="col-xs-12 ${not empty emailHasBindError? 'has-error' : ''}">
            <div class="fg-line">
            	<form:input path="email" class="form-control m-t-0" placeholder="Email Id" id="adduserform_email"/>
            </div>
            <form:errors path="email" class="help-block" element="small"/>
          </div>
          
        </div>
		<div class="row">
         <div class="col-xs-12 ${not empty mobileHasBindError? 'has-error' : ''}">
            <div class="fg-line">
            	<form:input path="mobile" class="form-control m-t-0" placeholder="Mobile Number" id="adduserform_mobile"/>
            </div>
            <form:errors path="mobile" class="help-block" element="small"/>
          </div>
          
        </div>
        
        
        <div class="row">
         <div class="col-xs-12 ${not empty passwordHasBindError? 'has-error' : ''}">
            <div class="fg-line">
            	<form:password path="password" class="form-control" placeholder="Password" id="adduserform_password"/>
            </div>
            <form:errors path="password" class="help-block" element="small"/>
          </div>
          
        </div>
       
         <div class="row">
         <div class="col-xs-12 ">
            <div class="fg-line">
            	<input type="password" name="confirmpassword" class="form-control" placeholder="Confirm password" id="adduserform_confirmpassword"/>
            </div>
          </div>
          
        </div>
        
         <div class="row">
         <div class="col-xs-6 m-t-15">
            <div class="fg-line">
            	<label class="fg-label">Role <span class="pre-field-cont">User</span></label>
            </div>
          </div>
          
        </div>
      
        
		</form:form>
        </div>
        <div>
       
        </div>
       </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary waves-effect" onclick="return addUserSubmit()">Save</button>
        <button type="button" class="btn btn-primary waves-effect"
			data-dismiss="modal">Cancel</button>
      </div>
      <script type="text/javascript">
  	$("#adduserform_confirmpassword").keyup(function(event) {
		
		 if (event.which == 13) {
	        event.preventDefault();
	        return addUserSubmit();
	        
	    } 
	});

			</script>