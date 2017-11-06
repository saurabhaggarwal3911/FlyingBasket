<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<section id="content">
  <div class="container">
  

	<!-- ---------- -->
  
    <div class="card-body">
      
      <div class="card p-b-0 m-b-10 bg-hide">
        <div class="card-header p-0">
             <h2>User list</h2>
        </div>
      </div>
      
        <!-- Success message -->
       <c:if test="${not empty message}">
  <p class="alert alert-success alert-dismissible m-b-30" id="message-container">
   <spring:message code="${message}" />
   <script type="text/javascript">
   setTimeout(function() {
  $("#message-container").remove();
  setInnerBodyHeight();
   }, 3000);
  </script>
 </c:if>
      
      <div class="card p-b-0 m-b-15">
        <div class="card-header p-15 header-button">
          <div class="row">
          
            <div class="col-sm-4">
                <c:if test="${not empty userList}">
                  <div class="search head-search group-search">
                    <div class="fg-line">
                      <input type="text" class="form-control" id="usersearchfield" placeholder="Search user">
                    </div>
                  </div>
                </c:if>
            </div>
                
            <div class="col-sm-8 text-right">
               <button class="btn  btn-primary waves-effect"  data-toggle="modal" data-target="#create-audit" onclick="addUserPopup()"><i class="zmdi zmdi-plus"></i> Add User</button>
            </div>
          
          </div>
        </div>
         
        <div class="card-body">
          <div class="doclist-cont">
            <div class="list-group audit-list-cont m-b-10">
              <div class="p-r-15 p-l-15">
                   <ul class="tab-nav tn-justified tn-icon main-tabs" role="tablist" id="tabs_body">
                   <li role="presentation"  class="${all_user_active_tab}"> <a class="col-xs-3" href="<c:url value='/common/user/manage?userType=orgnaizationUser'/>"  aria-controls="tab-4" role="tab"  > All Users</a> </li>
                </ul>

                   <div class="tab-content m-b-0 p-b-0 p-t-10">
                     <div role="tabpanel" class="tab-pane animated fadeIn active " >
                          <c:choose>
		                     <c:when test="${empty userList}">
		                        <div class="height-cont new-audit-cont scroll-container mCustomScrollbar">
                                  <div class="no-item"> <i class="zmdi zmdi-block"></i>  No user to display.</div>
                                </div>
		                     </c:when>
		                  <c:otherwise>
		
		           <div class="grid-title m-b-5">
                     <div class="row m-0">
                       <div class="col-xs-3 ">Name </div>
                         <div class="col-xs-4 ">User ID</div>
                           <div class="col-xs-2 text-center">Is Active</div>
                           <div class="col-xs-1 ">Edit</div>
                     </div>
                   </div>
                   <div class="height-cont new-audit-cont scroll-container mCustomScrollbar">
                      	<c:forEach items="${userList}" var="userListVar" varStatus="userListVarStatus">
                           <div class="grid-row">
                             <div class="row m-0">
                               <div class="col-xs-3">${userListVar.name}</div>
                               <div class="col-xs-4 ">${userListVar.username}</div>
                               <div class="col-xs-2 text-center">
                               <div class="checkbox"><label>
                               
                        <c:choose>
						   <c:when test="${userListVar.valid}">
							  <input  type="checkbox" checked="checked"   class="select-box" disabled >
						   </c:when>
						<c:otherwise>
						      <input  type="checkbox" name="valid"  class="select-box" disabled>
						</c:otherwise>
						</c:choose>
                              <i class="input-helper"></i></label></div>
                                </div>
                                
                                
                                     <div class="col-xs-1 ">
											<a class="edit-icon" href="<c:url value='/common/user/edit/${userListVar.id}'/>"><i class="zmdi zmdi-edit"></i></a>
								     </div>
                             </div>
                              </div>
                              
                    	 </c:forEach>
                              
                         </div>
                           <div id="paginationDiv">
	<div style="text-align: center" class="paging-links">
		<a id="lnkFirst" class="float_left" href="javascript:firstPage();"><i
			class="firstI disabled-link fl"></i></a> <a id="lnkPrevious"
			class="float_left" href="javascript:prevPage();"><i
			class="pagerPrev disabled-link fl"></i></a> <label id="Label2"
			class="CustPageLabelClass">Page</label> <input type="text"
			id="txtPageNumber" class="display-page" value="${pageNum}"
			autocomplete="off"> <label id="Label1"
			class="CustPageLabelClass">of ${totalPages}</label> <a id="lnkNext"
			href="javascript:nextPage();"><i class="pagerNext disabled-link"></i></a>
		<a id="lnklast" href="javascript:lastPage();"><i
			class="lastI disabled-link"></i></a>
		<div class="clear"></div>
	</div>
</div>
		</c:otherwise>
		</c:choose>

                       </div>

                    </div>


                  </div>
              </div>
            </div>
              

          
    
        
    

        </div>
        <div class="clearfix"></div>
      </div>
    </div>
    <div class="clearfix"></div>
  </div>
</section>

<div class="modal app-model fade" id="addUserModal" tabindex="-1" role="dialog" aria-labelledby="addUserLabel" aria-hidden="true">
     <div class="modal-dialog">
          <div class="modal-content">
          </div>
     </div>
</div>

<script type="text/javascript">

/* Script for Pagination starts here */

function firstPage() { 
	var pageNum  =  ${pageNum};
	if(pageNum!=1){
	    		  window.location.href = window.location.protocol+"?userType=${userType}"+"&pageNum=1";
	    	  }
    	 
	}
function prevPage(e, pageNum) { 
	var pageNum  = ${pageNum};
	if(pageNum!=1){
		pageNum=pageNum-1;
    		  window.location.href =window.location.protocol+"?userType=${userType}"+"&pageNum="+pageNum;
    	  }
		
	}
function nextPage(e, pageNum) { 
	var pageNum  = ${pageNum};
	var total=${totalPages};
	if(pageNum!=total){
		pageNum=pageNum+1;
    		  window.location.href =window.location.protocol+"?userType=${userType}"+"&pageNum="+pageNum;
    	  }
		
	 }
function lastPage() { 
	 var total=${totalPages};
	 var pageNum  = ${pageNum};
	 if(pageNum!=total){
    		  window.location.href =window.location.protocol+"?userType=${userType}"+"&pageNum="+total;
    	  }
		
	 }
$('#txtPageNumber').keyup(function(e){
    if(e.keyCode == 13)
    {
        $(this).trigger("enterKey");
    }
});
 $('#txtPageNumber').bind('enterKey',function(e){ 
	 var pageNumIn  = $('#txtPageNumber').val();
 	var pageNum  = ${pageNum};
 	var pattern = /^\d*$/;     
     var total=${totalPages};
      if((pattern.test(pageNumIn)) && (pageNumIn<=total) && pageNumIn != pageNum){
    		  window.location.href =window.location.protocol+"?userType=${userType}"+"&pageNum="+pageNumIn;
    	  }
		
 });
 
 
 /* Script for Pagination ends here */
 
		$(document).ready(
				function() {
					
					if(sessionStorage){
					    // Store data
					    sessionStorage.setItem("list_url", window.location);
					 
					} 
					
					$('#usersearchfield').typeahead({
					    hint: true,
					    highlight: true,
					    minLength: 0
					  },
					  {
						  name: 'username',
						    display: 'name',
						    templates: {
						        suggestion: function (data) {
						            var html =  '<div>' + data.name + '</div>';
						            return html;
						        }
						    },
						    limit:50,
						    source: function (query, syncResults, asyncResults) {
						        return $.ajax({
						          url: "search?userType=${userType}", 
						          type: 'GET',
						          data: {query: query},
						          dataType: 'json',
						          success: function (json) {
						           
						            return asyncResults(json);
						          }
						        })
						    }
					  }).on('typeahead:selected',function(evt,data){
						  
						    	editRow("edit/"+data.id );
						    return false;
						    });
				});

	
		function tabclick(type){
			
	    		  editRow("/common/user/manage?userType="+type);
		}
		
		
		
		function addUserPopup(){
			$("#preview1").show();
			$("#addUserModal .modal-content").html("");
			$("#addUserModal .modal-content").load('<c:url value="/common/user/add"/>', function(response, status, xhr){
				if(xhr.status==401){
					window.location = '<c:url value="/login"/>';
				}
				$("#preview1").hide();
				//hideLoadingImage(document.getElementById("preview1"));
				$("#addUserModal").modal();
				
				
			});
		}
		  
		

		 function addUserSubmit(){
			 var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
			 var success = true;
			  if(isFieldBlank("adduserform_name", '<spring:message code="NotBlank.userDto.name"/>')){
				  success = false;
			  }
			  if(isFieldBlank("adduserform_email", '<spring:message code="NotBlank.userDto.email"/>')){
				  success = false;
			  }
			  else if(isFieldBlank("adduserform_username", '<spring:message code="NotBlank.userDto.username"/>')){
				  success = false;
			  }else if(isFieldsHaveValidValue("adduserform_email", emailReg, '<spring:message code="Email.userDto.email"/>')){
				  success = false;
			  }
			  if(isFieldBlank("adduserform_password", '<spring:message code="NotBlank.userDto.password"/>')){
				  success = false;
			  }
			  
			  else if(isFieldHaveLessCharacter("adduserform_password", 4, '<spring:message code="Size.userDto.password"/>')){
			    	success = false;
		      }
			  
			  if(isFieldBlank("adduserform_confirmpassword", '<spring:message code="NotBlank.userDto.confirmpassword"/>')){
				  success = false;
			  }
			  
			  else if(isFieldsHaveDifferValue("adduserform_password", "adduserform_confirmpassword", '<spring:message code="NotMatch.userDto.confirmpassword"/>')){
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
									//$("#preview1").show();
						    		$("#addUserModal .modal-content").html(data);
						    		
						    		
							 }
							 };
					$('#addUserForm').ajaxSubmit(options); 
					
		 }
		 } 	
			
</script>
