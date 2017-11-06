<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
   

    <div class="container">
    <div class="card-body">
<!-- Success message -->
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
	<!-- ---------- -->
    
      <div class="card p-b-0 m-b-10 bg-hide ">

      	<div class="row card-header p-0">
            <div class="col-md-5">  
              <h2>Edit User</h2>
            </div> 
            <div class="col-md-7 text-right">
            <button type="button" class="btn  btn-primary waves-effect bck-btn" onclick="goBackInEdituser()"><i class="zmdi zmdi-arrow-left"></i>Back </button>
           <!--  <button type="button" class="btn btn-primary waves-effect m-l-5" onclick="editUserSubmit()">Save</button> -->
		    </div>
        </div>

      </div>

          <div class="card p-b-0 m-b-10">
          <div class="card-body card-padding p-b-0 p-t-10">
        <%--   <c:set scope="page" var="noneditable" value="${userDto.adUser}"></c:set> --%>
          
            <form:form role="form" id="editUserForm" class="form-horizontal" modelAttribute="userDto" action="" method="post">
            <c:set var="mobileHasBindError"><form:errors path="mobile"/></c:set>
             <c:set var="emailHasBindError"><form:errors path="email"/></c:set>
              <div class="row">
              
              <div class="col-sm-6">
              
                <div class="form-group">
                                    <label class="col-sm-3 control-label" for="usernameField">Name</label>
                                    <div class="col-sm-8">
                                        <div class="fg-line">
<c:choose>
			                               <c:when test="${noneditable}">
			                               		<form:input type="text" placeholder="User Name" path="name" id="usernameField" class="form-control input-sm r-only" readonly="${noneditable}"></form:input>
			                               </c:when>
			                               <c:otherwise>
			                                <form:input type="text" placeholder="User Name" path="name" id="usernameField" class="form-control input-sm" ></form:input>
											<span class="zmdi form-control-feedback c-red">*</span>
			                               </c:otherwise>
			                               </c:choose>
                                           
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group ${not empty mobileHasBindError? 'has-error' : ''}">
                                    <label class="col-sm-3 control-label" for="mobileField">Mobile Number</label>
                                    <div class="col-sm-8">
                                        
                                        <div class="fg-line">
                                        <c:choose>
			                               <c:when test="${userInEditMode}">
                                            <form:input type="text" path="mobile" id="mobileField" class="form-control input-sm "></form:input>
                                            <button class="btn btn-default btn-icon waves-effect waves-circle waves-float edit-name" type="button" onclick="userInEditModeFun(this, true)"><i class="zmdi zmdi-close"></i></button>
                                        </c:when>
			                              <c:otherwise>
			                              <form:input type="text" path="mobile" id="mobileField" readonly="true" class="form-control input-sm r-only" ></form:input>
                                          
                                        <%--   <c:if test="${not noneditable}">
                                          <button class="btn btn-default btn-icon waves-effect waves-circle waves-float edit-name" type="button" onclick="userInEditModeFun(this, false)"><i class="zmdi zmdi-edit"></i></button>
                                          </c:if> --%>
                                        </c:otherwise>
		                               </c:choose>
                                        </div>
                                        <form:errors path="mobile" class="help-block  has-error" element="small"/>
                                    </div>
                                </div>
                                <div class="form-group ${not empty emailHasBindError? 'has-error' : ''}">
                                    <label class="col-sm-3 control-label" for="useremailField">Email</label>
                                    <div class="col-sm-8">
                                        
                                        <div class="fg-line">
                                        <c:choose>
			                               <c:when test="${userInEditMode}">
                                            <form:input type="email" path="email" id="useremailField" class="form-control input-sm "></form:input>
                                            <button class="btn btn-default btn-icon waves-effect waves-circle waves-float edit-name" type="button" onclick="userInEditModeFun(this, true)"><i class="zmdi zmdi-close"></i></button>
                                        </c:when>
			                              <c:otherwise>
			                              <form:input type="email" path="email" id="useremailField" readonly="true" class="form-control input-sm r-only" ></form:input>
                                          
                                          <%-- <c:if test="${not noneditable}">
                                          <button class="btn btn-default btn-icon waves-effect waves-circle waves-float edit-name" type="button" onclick="userInEditModeFun(this, false)"><i class="zmdi zmdi-edit"></i></button>
                                          </c:if> --%>
                                        </c:otherwise>
		                               </c:choose>
                                        </div>
                                        <form:errors path="email" class="help-block  has-error" element="small"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label" for="userroleField">Role
                                    </label>
                                    <div class="col-sm-8">
                                        <div class="fg-line">
                                           <form:hidden path="role" value="${userRole.id }"/>
                                            
										<span class="pre-field-cont">${fn:replace(userRole.name, "_", " ")}
										<%-- c:if test="${not empty userDto.adminForClientName or not empty userDto.adminForWorkspaceName}">
            for ${userDto.adminForClientName} ${userDto.adminForWorkspaceName}
            </c:if> --%>
										 </span>
                                        </div>
                                    </div>
                                </div>
              
              </div>
              <div class="col-sm-6">
              
              
                               
                                
                               <div class="form-group">
                                  <label class="col-sm-5 control-label" for="statusField">Status</label>
                                    <div class="col-sm-7">
                                        <div class="checkbox">
                                        	<label>
                                        	
									           			<c:choose>
												             <c:when test="${userDto.valid}">
													              <input id="statusField" type="checkbox" checked="checked"
													               name="valid" >
													              <i class="input-helper"></i>
												             </c:when>
												             <c:otherwise>
													              <input id="statusField" type="checkbox" name="valid" >
													              <i class="input-helper"></i>
												             </c:otherwise>
												           </c:choose>
									           
           									</label>
                                        </div>
                                    </div>
                                </div> 
                               <%--  <c:if test="${roleId==1 }">
                                 <div class="form-group">
                                  <label class="col-sm-5 control-label" for="associateWorkspaceField">Associate with workspaces</label>
                                    <div class="col-sm-7">
                                        <form:select class="form-control mulitselect-list" multiple="true" path="workspaceList">
				  	  <form:options items="${workspaceList}" itemValue="id" itemLabel="name"/>
					</form:select>
                                    </div>
                                </div> 
                                 </c:if> --%>
                                 <%-- <c:if test="${roleId==3 }">
                                 <div class="form-group">
                                  <label class="col-sm-5 control-label" for="associateWorkspaceField">Associate with workspace</label>
                                    <div class="col-sm-7">
                                        <div class="checkbox">
                                          <label>${WorkspaceName}
                                       
														<c:choose>
                                        		<c:when test="${userDto.availableInCurrentWorkspace}">
                                        		<input id="availableInCurrentWorkspace" type="checkbox" checked="checked"
													               name="availableInCurrentWorkspace" >
													              <i class="input-helper"></i>
                                        		</c:when>
                                        		<c:otherwise>
                                        		
                                        		<input id="availableInCurrentWorkspace" type="checkbox" 
													               name="availableInCurrentWorkspace" >
													              <i class="input-helper"></i>
												
                                        		</c:otherwise>
                                        		</c:choose>
														
														
														
										  
										  </label>
                                        </div>
                                    </div>
                                </div>  
                                </c:if> --%>
                                </div>
                                 
              </div> 
        <input type="hidden" name="userInEditMode" id="userInEditMode" value="${userInEditMode}">
        
      <input type="hidden" name="oldUserName" id="oldUserName" value="${oldUserName}"/>
      <form:hidden path="id" />
      <form:hidden path="address" />
            </form:form>
            </div>
            <div class="clearfix"></div>
          </div>
       
   
    <!-- <div class="card p-b-0 m-b-10 bg-hide">
        <div class="card-header p-0">
             <h2>Shared Audits</h2>
        </div>
      </div> -->
   
      <%-- div class="card p-b-0 m-b-15">
        <div class="card-body">
          <div class="doclist-cont">
            <div class="list-group audit-list-cont m-b-10">
              <div class="p-r-15 p-l-15 ">  
                     <c:choose>
               <c:when test="${empty auditShareList}">
                      <div class="height-cont new-audit-cont">
                                <div class="no-item"> <i class="zmdi zmdi-block"></i>  No audits to display.</div>
                            </div>
               </c:when>
               <c:otherwise>
  
             <div class="grid-title">
                     <div class="row m-0 p-t-10 p-b-10">
                       <div class="col-xs-2">Audit Name </div>
                        <div class="col-xs-2">Shared On</div>
                        <div class="col-xs-2">Shared Till</div>
                        <div class="col-xs-2">Status</div>
                        <div class="col-xs-2">Submitted On</div>
                        <div class="col-xs-1">Download</div>
                        <div class="col-xs-1 text-center">History</div>
                     </div>
                   </div>
                   <div class="height-cont new-audit-cont scroll-container mCustomScrollbar p-0">
                        <c:forEach items="${auditShareList}" var="auditShareListVar" varStatus="userListVarStatus">
                           <div class="grid-row handCoursor" ondblclick="viewauditdetails(${userDto.id},${auditShareListVar.auditId},${auditShareListVar.auditHistoryId})" >
                             <div class="row m-0 ">
                               <div class="col-xs-2 ">
                               <c:choose>
                                                  <c:when test="${auditShareListVar.auditType.toString() == 'Asset' || auditShareListVar.auditType.toString() == 'ScoredAsset' }">
                                                   <i class="zmdi p-audit-icon"></i>
                                                  </c:when>
                                                  <c:otherwise>
                                                  <i class="zmdi audit-icon"></i>
                                                  </c:otherwise>
                                                  </c:choose>
                               
                               ${auditShareListVar.auditName}</div>
                               <div class="col-xs-2 ">${auditShareListVar.startTimeStr}</div>
                               
                               <c:choose>
                               <c:when test="${not empty auditShareListVar.endTimeStr}">
                                    <div class="col-xs-2 ">${auditShareListVar.endTimeStr}</div>
				               </c:when>
				               <c:otherwise>
				                   <div class="col-xs-2">-</div>
				               </c:otherwise>
				               </c:choose>
                               
                               <div class="col-xs-2 ">${auditShareListVar.sharedAuditStatus.toString()}</div>
                               
                               <c:choose>
                               <c:when test="${not empty auditShareListVar.submissionDateStr}">
                                    <div class="col-xs-2 ">${auditShareListVar.submissionDateStr}</div>
				               </c:when>
				               <c:otherwise>
				                   <div class="col-xs-2">-</div>
				               </c:otherwise>
				               </c:choose>
				               
				               <c:choose>
                               <c:when test="${not empty auditShareListVar.submissionDateStr}">
				              	 <div class="col-xs-1 text-center"><a href= "javascript:void(0);" onclick="prepareAndDownloadDocument('/common/user/preparesharedaudit/${userDto.id}/${auditShareListVar.auditId}/${auditShareListVar.auditHistoryId}','/downloadsharedaudit');">
				              	 
				              	 <i class=" zmdi zmdi-download download" style="cursor: pointer;"></i></a></div>
				              	 
                               </c:when>
				               <c:otherwise>
				                 <div class="col-xs-1 text-center"></div>
				               </c:otherwise>
				               </c:choose>
				               <div class="col-xs-1 text-center">
				               <c:choose>
				               	<c:when test="${not empty auditShareListVar.oldUserShareAudits}">
				               <a data-toggle="collapse" class="f-18" data-parent="#accordion" href="#collapseHistory_${auditShareListVar.auditId}" aria-expanded="true" aria-controls="collapseOne"><i class="zmdi zmdi-time-restore"></i></a>
				               	</c:when>
				               	<c:otherwise>
				               	-
				               	</c:otherwise>
				               </c:choose>
				               
				               </div>
				               
                             </div>
                             
                           </div>
                           <c:if test="${not empty auditShareListVar.oldUserShareAudits}">
                           <div id="collapseHistory_${auditShareListVar.auditId}" class="collapse row  collapsed-history-grid" role="tabpanel" aria-labelledby="headingOne">
                           <c:forEach items="${auditShareListVar.oldUserShareAudits}" var="auditShareListOldVar" varStatus="userListOldVarStatus">
                           <div class="panel-body pointer-cursor p-t-10 p-b-10 b-b-1" ondblclick="viewauditdetails(${userDto.id},${auditShareListOldVar.auditId},${auditShareListOldVar.auditHistoryId})">
                                        	
                               <div class="col-xs-2 ">
                               
                                                  
                                                  
                                                  <c:choose>
                                                  <c:when test="${auditShareListOldVar.auditType.toString() == 'Asset' || auditShareListOldVar.auditType.toString() == 'ScoredAsset' }">
                                                   <i class="zmdi p-audit-icon"></i>
                                                  </c:when>
                                                  <c:otherwise>
                                                  <i class="zmdi audit-icon"></i>
                                                  </c:otherwise>
                                                  </c:choose>
                                                  
                                                  
                               
                               ${auditShareListOldVar.auditName}</div>
                               <div class="col-xs-2 ">${auditShareListOldVar.startTimeStr}</div>
                               
                               
                               
				               
				                  <c:choose>
                               <c:when test="${not empty auditShareListOldVar.endTimeStr}">
                                    <div class="col-xs-2 ">${auditShareListOldVar.endTimeStr}</div>
				               </c:when>
				               <c:otherwise>
				                   <div class="col-xs-2">-</div>
				               </c:otherwise>
				               </c:choose>
				               
				               
                               
                               <div class="col-xs-2 ">${auditShareListOldVar.sharedAuditStatus.toString()}</div>
                               
                               <c:choose>
                               <c:when test="${not empty auditShareListOldVar.submissionDateStr}">
                                    <div class="col-xs-2 ">${auditShareListOldVar.submissionDateStr}</div>
				               </c:when>
				               <c:otherwise>
				                   <div class="col-xs-2">-</div>
				               </c:otherwise>
				               </c:choose>
				               
				               <c:choose>
                               <c:when test="${not empty auditShareListOldVar.submissionDateStr}">
				              	 <div class="col-xs-1 text-center"><a href= "javascript:void(0);" onclick="prepareAndDownloadDocument('/common/user/preparesharedaudit/${userDto.id}/${auditShareListOldVar.auditId}/${auditShareListOldVar.auditHistoryId}','/downloadsharedaudit');">
				              	 
				              	 <i class=" zmdi zmdi-download download" style="cursor: pointer;"></i></a></div>
				              	 
                               </c:when>
				               <c:otherwise>
				                 <div class="col-xs-1 text-center"></div>
				               </c:otherwise>
				               </c:choose>
				               
				               
				               <div class="col-xs-1 text-center">-</div>
				               
                             
                                        </div>
                           </c:forEach>
                                        
                                        
                                    </div>
                                    </c:if>
                        </c:forEach>
                        
                   </div>
  </c:otherwise>
  </c:choose>



                  </div>
              </div>
            </div>
            
               
        </div>
        <div class="modal app-model fade" id="downloadDocModal"
	tabindex="-1" role="dialog" aria-labelledby="feedbackLabel"
	aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close jpopup-close-btn  "
					data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body aboutus-cont text-center p-b-30"
				style="text-align: left; word-wrap: break-word;"></div>
		</div>
	</div>
        
         <div class="clearfix">    </div>
      </div>
    </div> --%>
</div>

<script type="text/javascript">
function editUserSubmit(){
	var form=document.getElementById("editUserForm");
	form.action = "/common/user/edit/"+${userDto.id};
	
  var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
	var success = true;
	if(isFieldBlank("usernameField", '<spring:message code="NotBlank.userDto.name"/>')){
		  success = false;
	  }
	if(isFieldBlank("mobileField", '<spring:message code="NotBlank.userDto.mobile"/>')){
		  success = false;
	  }
	if(isFieldBlank("useremailField", '<spring:message code="NotBlank.userDto.email"/>')){
		  success = false;
	  }else if(isFieldsHaveValidValue("useremailField", emailReg, '<spring:message code="Email.userDto.email"/>')){
		  success = false;
	  }
	if(success){
	form.submit();
	$("#preview1").show();
	}
	
}

function goBackInEdituser(){
	
	var url = null
	if(sessionStorage){
	    // Store data
	    url = sessionStorage.getItem("list_url");sessionStorage.removeItem("list_url");
	} 
	var referer = document.referrer;
	
	if(isBlank(url) || isBlank(referer) ){
		url = '../manage';
	}
	window.location.href=url;
}

function prepareAndDownloadDocument(prepURL,downloadURL){
	$("#downloadDocModal").modal();
	$("#downloadDocModal .modal-body").html("Please wait while the document is being prepared...");
	 $.ajax({
	type: "GET",
	url: prepURL,
	cache: false,    
	success: function(data){
		if(data.isSuccess==true||data.isSuccess=='true'){
			$('#downloadDocModal').modal('hide');
			window.location.href=".."+downloadURL+"?fileName="+data.fileName+"&finalFileName="+data.finalFileName;
		}else{
			$("#downloadDocModal .modal-body").html("Error while downloading the document.");
		}
	},
	error: function(){ 
		$("#downloadDocModal .modal-body").html("Error while preparing the document.");
	console.log('Error while request..');
	}
}); 
	
	
}
</script>
