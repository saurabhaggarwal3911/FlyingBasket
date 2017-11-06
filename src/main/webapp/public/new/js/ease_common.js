/*This function is called in viewcustomparentaudit.jsp and viewaudit.jsp.*/
function fthumbImagePopup(url){
			$("#preview1").show();
			var html = '<div class="item active"><img src="'+url+'" alt="Image not available" style="margin: 0px auto; float: none;"></div>';
				$(".carousel-inner").html(html);
				$("#thumbAnswerImageModal").modal();
				$("#preview1").hide();
}

/*This function is called in viewcustomparentaudit.jsp and viewaudit.jsp.*/
function auditImagePopup(url,fileId){
			$("#preview1").show();
			var html = "";
			if(fileId){
			html = '<div class="item active"><img src="'+url+'" alt="Image not available" style="margin: 0px auto; float: none;"></div>';
			}else{
				html = '<div class="no-item"><i class="zmdi zmdi-block"></i> No Response to display.</div>';
			}	
			$(".carousel-inner").html(html);
				$("#thumbAnswerImageModal").modal();
				$("#preview1").hide();
}


/*This function is called in viewcustomparentaudit.jsp and viewaudit.jsp.*/
function elementExist(elem){
			if(elem==null || elem == 'undefined' || elem.length==0){
				return false;
			}else{
				return true;
			}
}


/*This function is called in viewauditresult.jsp.*/
function openAuditImages(url,imagesExist) {
	var a = document.createElement("a");
	if (!a.click) //for IE
	{
		window.location = url;
		return;
	}

	if(imagesExist){
		a.setAttribute("href", url);
	a.setAttribute("target", "_blank");

}else {
		a.setAttribute("href", "");
		a.setAttribute("data-toggle", "modal");
		a.setAttribute("data-target", "#no-image-modal");

}
	
	a.style.display = "none";
	document.body.appendChild(a);
	a.click();
}


/*This function is called in viewaudit.jsp.*/


/*This function is called in viewaudit.jsp.*/
function publishAudit(auditId){
	$("#preview1").show();
	window.location.href='../publish/'+auditId;
}

/*This function is called in viewaudit.jsp.*/
function convertAudit(auditId, auditType){
	$("#preview1").show();
	window.location.href='../convert/'+auditId+ '?auditType=' + auditType;
}


/*This function is called in viewaudit.jsp.*/
function addParentQuestion(url){

	if(sessionStorage){
	    // Store data
	    sessionStorage.setItem("editaudit_url", window.location);
	 
	} 
	
	editRow(url)
} 


/*This function is called in shareAudit.jsp.*/
function showGroupUserAutoComplete(source) {

	$('#groupusersearch')
			.typeahead('destroy')
			.typeahead(
					{
						hint : true,
						highlight : true,
						minLength : 0,

					},
					{
						name : 'groupusersearch',
						display : 'name',
						templates : {
							suggestion : function(data) {
								
								var html = '<li class="user-grp-list" >';
								if (data.id.indexOf("_group") != -1) {
									if (!data.subgroup) {
										html = html
												+ '<i class="zmdi zmdi-accounts-alt"></i><strong> '
												+ data.name
												+ ' </strong></li>';
									} else {
										html = html
												+ '<i class="zmdi zmdi-device-hub"></i><strong> '
												+ data.name
												+ ' </strong><ul class="sublist"><li>'
												+ data.path
												+ '</li></ul></li>';
									}
								} else {
									html = html
											+ '<i class="zmdi zmdi-account"></i><strong> '
											+ data.name + ' </strong></li>';
								}
								return html;
							}
						},
						limit : 50,
						source : substringMatcher(source)
					});
}

/*This function is called in shareAudit.jsp and removeUserFromShare(current, id, name) below.*/
function updateSourceForSearch(source) {
	if (elementExist($("#groupusersearch"))) {
		showGroupUserAutoComplete(source);
	}
}

/*This function is called in shareAudit.jsp.*/
function addToGroupList(username, parent, id, path, subgroup) {
	if (id.lastIndexOf("_group") != -1) {
		if (!subgroup) {
			parent
					.append('<div class="list-group-item" id="user_item_'+id+'"><i class="zmdi zmdi-accounts-alt "></i><span class="name">'
							+ username
							+ ' </span><span onclick="removeUserFromShare(this, \''
							+ id
							+ '\', \''
							+ username
							+ '\');"><i class="zmdi zmdi-close"></i></span></div>');
		} else {
			parent
					.append('<div class="list-group-item" id="user_item_'+id+'"><i class="zmdi zmdi-device-hub "></i><span class="name">'
							+ username
							+ ' </span><span onclick="removeUserFromShare(this, \''
							+ id
							+ '\', \''
							+ username
							+ '\');"><i class="zmdi zmdi-close"></i></span><br><span class="subgroup-path-txt">'
							+ path + '</span></div>');
		}
	} else {
		parent
				.append('<div class="list-group-item" id="user_item_'+id+'"><i class="zmdi zmdi-account  icongap5"></i><span class="name">'
						+ username
						+ ' </span><span onclick="removeUserFromShare(this, \''
						+ id
						+ '\', \''
						+ username
						+ '\');"><i class="zmdi zmdi-close"></i></span></div>');

	}
};

/*This function is called in shareAudit.jsp and function addToGroupList(username, parent, id, path, subgroup) above.*/
function removeUserFromShare(current, id, name) {
	var tempVal = $.grep(selectedSource, function(data, index) {
		return data.id == id;
	});
	selectedSource = $.grep(selectedSource, function(data, index) {
		return data.id != id;
	});
	source.push(tempVal[0]);
	$(current).parent().remove();
	updateSourceForSearch(source);
}

/*This function is called in shareAudit.jsp.*/
/*function shareAudit() {
	try{
		var isSuccess = true;
		var usercount = 0;

		$('#shareUserList .list-group-item').each(function(i) {
			usercount++;
		});

		if (usercount == 0) {
			if (elementExist($(".has-error"))) {
				$(".has-error").remove();
			}
			$("#shareUserList")
					.after(
							'<div class="has-error"><span class="help-block"><spring:message code="shareaudit.nouser"/></span></div>');

			isSuccess = false;

		}
		if (isSuccess) {
			$("#preview1").show();
			
			setTimeout(function() {
				console.log("typeof submitShare : %s",typeof(submitShare));
				submitShare();
			}, 100);
			

		}
	}catch(err){
		console.error("Err in fn.shareAudit : %s",err);
	}
	return false;
}*/
function getAssetId(e,noOfIterations){
	var tab=$(e).parent();
	for(var i=0;i<noOfIterations-1;i++){
		tab=$(tab).parent();
	}
	var assetId = $(tab).attr("id").split("tab")[1];
	return assetId;
}
function getSubAssetObject(e,noOfIterations){
	var obj=null;
	var listId=$('#tabs_body li.active').index();
	if(subAssetList[listId]==null||subAssetList[listId]=='undefined'){
		 obj=new Object();
		}else{
			obj=subAssetList[listId];
		}
	return obj;
}


/*This function is called in sectionandquestioncreate.jsp and sectionandquestion.jsp.*/
function checkAll(e) {
	var listId=$('#tabs_body li.active').index();
	var count = $(e).data("count");
	var obj=getSubAssetObject(e,10);
	var assetId = getAssetId(e,10);
	obj.subAssetId=assetId;
	var checkBoxes = $("#sAssetaccordionAmber-" + count+"tab"+assetId+" li input");
	
	var selectedSectionArray=obj.selectedParentSectionIds;
	if(selectedSectionArray==null||selectedSectionArray=="undefined"){
		selectedSectionArray=new Array();
	}
	if(selectedSectionArray.indexOf(parseInt($(e).attr("data-count"))) == -1){
	selectedSectionArray.push(parseInt($(e).attr("data-count")));
	obj.selectedParentSectionIds=selectedSectionArray;
	}
	var selectedParentQuestionIds=obj.selectedParentQuestionIds;
	if(selectedParentQuestionIds==null||selectedParentQuestionIds=="undefined"){
		selectedParentQuestionIds=new Array();
	}
	if(selectedParentQuestionIds.indexOf(parseInt($(e).attr("question-id"))) != -1){
		selectedParentQuestionIds.splice(selectedParentQuestionIds.indexOf(parseInt($(e).attr("question-id"))), 1);
	}else{
		selectedParentQuestionIds.push(parseInt($(e).attr("question-id")));
	}
	obj.selectedParentQuestionIds=selectedParentQuestionIds;
	subAssetList[listId]=obj;
	 console.log("checkAll");
	 console.log(subAssetList);
	 
	for ( var i = 0; i < checkBoxes.length; i++) {
		if (!$(checkBoxes[i]).is(":checked") && !$(checkBoxes[i]).is(":disabled")) {
			$('#sAssetdownload-'+count+'tab'+assetId+' input').prop("checked", false);
			return;
		}
	}
	$('#sAssetdownload-'+count+'tab'+assetId+' input').prop("checked", true);
}
 function removeSubasset(index){
	 subAssetList.splice(index, 1);
	 var listArray=$("#tabs_body li");
	 for(var i=0;i<listArray.length;i++){
		 $(listArray[i]).find(".zmdi.zmdi-close").click(function(){deleteSubasset(i,$(listArray[i]).attr("id").split("tab")[0]); });
	 }
 }
function deleteSubasset(index,subassetId ){
	
	$('#subAssetName').append($('<option>', {
	    value: subassetId,
	    text: $("#"+subassetId+" a").text(),
	    id:'subasset_'+subassetId
	}));
	
	$("#preview1").show();
	if(assetAuditId!=0){
	var url = '../deleteSubasset/' + assetAuditId + '/' + subassetId;
	$.ajax({
		url : url,
		type : 'POST',
		success : function(data) {
			if(data.success==true||data.success=="true"){
				removeSubasset(index);
				$("#"+subassetId).remove();
				$("#tab"+subassetId).remove();
				$(".tab-content .tab-pane").removeClass('active');
				$("#tabs_body li").removeClass('active');
				$("#tabs_body li:eq(0)").addClass('active');
				$(".tab-content .tab-pane:eq(0)").addClass('active');
				if($('.savedSubAsset').length == 1){
					 $('.savedSubAsset').find('.zmdi.zmdi-close').remove();
				 }
				var listArray=$("#tabs_body li");
				if(listArray.length==3){
					 $(".zmdi.zmdi-close").hide();
				 } else{
					 $(".zmdi.zmdi-close").css("display","");
				 }
			}
		}
	});
	}else{
		removeSubasset(index);
		$("#"+subassetId).remove();
		$("#tab"+subassetId).remove();
		$(".tab-content .tab-pane").removeClass('active');
		$("#tabs_body li").removeClass('active');
		$("#tabs_body li:eq(0)").addClass('active');
		$(".tab-content .tab-pane:eq(0)").addClass('active');
		var listArray=$("#tabs_body li");
		if(listArray.length==3){
			 $(".zmdi.zmdi-close").hide();
		 } else{
			 $(".zmdi.zmdi-close").css("display","");
		 }
	}
	if($('select#subAssetName option').length!=1){
		$("#dropdownParent").hide();
		 $("#addNewSubSet").show();
	 }
	$("#preview1").hide();
	
}

/*This function is called in sectionandquestioncreate.jsp and sectionandquestion.jsp.*/
function checkAllSections(e) {
	var listId=$('#tabs_body li.active').index();
	var obj=getSubAssetObject(e,7);
	var assetId = getAssetId(e,7);
	obj.subAssetId=assetId;
	
	var selectedSectionArray=obj.selectedParentSectionIds;
	if(selectedSectionArray==null||selectedSectionArray=="undefined"){
		selectedSectionArray=new Array();
	}
//	selectedSectionArray.push(parseInt($(e).attr("data-count")));
//	obj.selectedParentSectionIds=selectedSectionArray;
	if(selectedSectionArray.indexOf(parseInt($(e).attr("data-count"))) == -1){
		selectedSectionArray.push(parseInt($(e).attr("data-count")));
		obj.selectedParentSectionIds=selectedSectionArray;
		}
	
	var selectedParentQuestionIds=obj.selectedParentQuestionIds;
	if(selectedParentQuestionIds==null||selectedParentQuestionIds=="undefined"){
		selectedParentQuestionIds=new Array();
	}
	var flag=$(e).prop("checked");
	var count = $(e).data("count");
	var checkBoxes = $("#sAssetaccordionAmber-"+count+"tab"+assetId+" .question_checkbox_" + count);
	for (var i = 0; i < checkBoxes.length; i++) {
		if (!$(checkBoxes[i]).is(":disabled")) {
			if (selectedParentQuestionIds.indexOf(parseInt($(checkBoxes[i]).attr("question-id"))) == -1) {
				if (flag) {
					selectedParentQuestionIds.push(parseInt($(checkBoxes[i]).attr("question-id")));
				}
			} else {
				if (!flag) {
					selectedParentQuestionIds.splice(selectedParentQuestionIds.indexOf(parseInt($(checkBoxes[i]).attr("question-id"))), 1);
				}
			}
		$(checkBoxes[i]).prop("checked", flag);
		}
	}
	setTimeout(function(){$(e).prop("checked", flag);},20);
	obj.selectedParentQuestionIds=selectedParentQuestionIds;
	subAssetList[listId]=obj;
	 console.log("checkAllSections");
	 console.log(subAssetList);
}


/*This function is called in manageusergroup.jsp.*/
function copyFromManageGroupUser() {

	//  var rowselected = $('.grid-list').find('.row.select');
	var grpId = null;

	if ($(".panel-heading").hasClass("rowselected")) {
		//  alert("Hii");
		var obj = $(".panel-heading.rowselected");

		grpId = $(obj).attr("userGroupId");
		$("#groupName").val('');
		$('#copy-group').modal('show');

	} else {
		swal({
			title : "Please select group to copy",
			//text: "You will not be able to recover this imaginary file!",   
			// showCancelButton: true,   
			confirmButtonColor : "#f4006b",
			confirmButtonText : "OK",
			closeOnConfirm : false

		});
	}

}

/*This function is called in manageusergroup.jsp.*/
function copyGroupFromModal() {
	//var rowselected = $('.grid-list').find('.row.select');
	//var id = rowselected.attr('id');
	var grpId = null;

	if ($(".panel-heading").hasClass("rowselected")) {
	//	alert("Hii");
		var obj = $(".panel-heading.rowselected");

		grpId = $(obj).attr("userGroupId");
		var groupName = $("#groupName").val();
		if (!isFieldBlank("groupName", "Please enter group name.")) {
			var url = 'copyGroup/' + grpId + '/' + groupName;
			$.ajax({
						url : url,
						type : 'GET',
						data : 'application/json',
						success : function(response) {
							if (response != null) {
								var obj = JSON.parse(response);
								if (obj != null) {
									var isSuccess = obj.result;
									if (isSuccess == "true") {
										alert("Group already exist. Please enter different group name.");
									} else {
										$('#copy-group').modal('hide');
										editRowUserGroup('manage');
									}
								}
							}
						}
					});
		}
	}
}


/*This function is called in manageusergroup.jsp.*/


/*This function is called in login.jsp.*/
function fthumbImagePopup(url){
	$("#preview1").show();
	var html = '<div class="item active"><img src="'+url+'" alt="Image not available" style="margin: 0px auto; float: none;"></div>';
		$(".carousel-inner").html(html);
		$("#thumbAnswerImageModal").modal();
		$("#preview1").hide();
	}

/*This function is called in forgotPassword.jsp.*/
function forgotPassword() {
	var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
	var isSuccess = true;
	if (isFieldBlank("emailId",
			'<spring:message code="forgotpassowrd.blankemail"/>')) {
		isSuccess = false;
	} else if (isFieldsHaveValidValue("emailId", emailReg,
			'<spring:message code="forgotpassowrd.emailnotvalid"/>')) {
		isSuccess = false;
	}
	$("#emailId").focus();
	return isSuccess;
}


/*This function is called in editusergroup.jsp.*/
function deleteOnEditGroupUser(grpId,parentGroupId) {
	swal({
		title : "Are you sure you want to delete?",
		showCancelButton : true,
		confirmButtonColor : "#f4006b",
		confirmButtonText : "Delete",
		closeOnConfirm : false

	}, function() {
		$("#preview1").show();
	if(!parentGroupId){
		parentGroupId=0;
	}
		editRowUserGroup('../delete/' + grpId+'/'+parentGroupId);

		$("#preview1").hide();

	});
}


/*This function is called in editusergroup.jsp.*/
function addDropable() {
	
	 var $userdroparea = $("#userdroparea");
	   $userdroparea.droppable({
		      accept: $(".draggable.all-user-draggable .grid-row"),
		      activeClass: "ui-state-highlight",
		      drop: function( event, ui ) {
		    	 var id =$(ui.draggable).find(".allUserItem").prop("id");
	  		ui.draggable.remove();
		        addToGroupUserList($(ui.draggable).text(), $(this).find('ul'), id);
		        
		        allusersource = $.grep(allusersource, function(data, index) {
		        	return "group_"+data.id != id;
		        });
		        updateSourceForUserSearch(allusersource); 
		      }
		    });
	
}

/*This function is called in editusergroup.jsp.*/
	function adddragdrop(){
		  var $draggable = $( ".draggable.all-user-draggable" );
		  var $userdroparea = $("#userdroparea");
		  
		    $( ".grid-row", $draggable ).draggable({
		      cancel: "a.ui-icon", // clicking an icon won't initiate dragging
		      revert: "invalid", // when not dropped, the item will revert back to its initial position
		      containment: ".user-list",
		      helper: "clone",
		      cursor: "move",
		    //  handle:".dragicon"
		    });
			
			 addDropable();

		    
		    
		    $userdroparea.on('click', '.zmdi-close', function () {
				  var parentRow = $(this).closest('li');
				  if(parentRow != null) {
					  var id = parentRow.attr('id');
					  var user = parentRow.find('.col-xs-11').html();
					  parentRow.remove();
					  addToAllUserList(user, id);
					  
					  var obj = {};
					  obj.id = id.split("_")[1];
				      obj.name = user;
					  allusersource.push(obj);
					  
					  updateSourceForUserSearch(allusersource);
				  }
			  }); 
	}

	/*This function is called in editusergroup.jsp.*/
	function rowclickUserGroup(current, e) {
		$(".panel-heading").removeClass('rowselected');
		$(current).addClass('rowselected');
	}

	/*This function is called in editusergroup.jsp.*/
	function addUseronUserSearch(current){
		
		var userName = $(current).attr("name");
		var userId = $(current).attr("id");
		
		var id = "group_"+userId;
		var parent = $(".groupUsersList");
		//var parent = $(".groupUsersList li:last-child");

		addToGroupUserList(userName,parent,id);
		  allusersource = $.grep(allusersource, function(data, index) {
			          	return "group_"+data.id != id;
				        });
		  
	  updateSourceForUserSearch(allusersource); 
	  
	  deleteuserFromAllUsers(userId);
		
	}
	
	/*This function is called in editusergroup.jsp.*/
	function deleteuserFromAllUsers(userId){
		
		$("#grouprow_"+userId).remove();
	}

	/*This function is called in editusergroup.jsp.*/
	// initial typeAhead definition
	function initialUserTypeAhead(usersource){
		$('#usersearchfield').typeahead('destroy').typeahead({
	        hint: true,
	        highlight: true,
	        minLength: 0
	       
	      },
	      {
	       
	        name: 'username',
	        display: 'name',
	        templates: {
	            suggestion: function (data) {
	             
	            var html = '<li class="user-grp-list" >'
	            +'<i class="zmdi zmdi-account-o"></i><strong>'
	        + data.name
	        +'</strong>';
	      return html;     
	      
	            }
	        },
	        limit:50,
	        source: substringMatcher(usersource)
	      }).on('typeahead:selected',function(evt,data){
	    	 // alert("hi");
	    	  addUseronUserSearch(data);
	    	  return false;
	    	  }); 
	}
	
	/*This function is called in editusergroup.jsp.*/
function showUserAutoComplete(usersource){
$('#usersearchfield').typeahead('destroy').typeahead({
    hint: true,
    highlight: true,
    minLength: 0
   
  },
  {
   
    name: 'username',
    display: 'name',
    templates: {
        suggestion: function (data) {
         
        var html = '<li class="user-grp-list" >'
        +'<i class="zmdi zmdi-account-o"></i><strong>'
    + data.name
    +'</strong>';
  return html;     
  
        }
    },
    limit:50,
    source: substringMatcher(usersource)
  }); 
}

/*This function is called in editusergroup.jsp.*/
function editRowUserGroup(url) {
	var a = document.createElement("a");
	if (!a.click) //for IE
	{
		window.location = url;
		return;
	}
	a.setAttribute("href", url);
	a.style.display = "none";
	document.body.appendChild(a);
	a.click();
}

/*This function is called in editusergroup.jsp.*/
function updateSourceForUserSearch(usersource) {
	  if(elementExist($("#usersearchfield"))){
		  $("#usersearchfield").prop("value","");
		 showUserAutoComplete(usersource);
	  }
}

/*This function is called in editusergroup.jsp.*/
function addToGroupUserList(username, parent, id) {
	  parent.append('<li id="'+id+'"><div class="grid-row"><div class="row m-0"><div class="col-xs-11">'+username+'</div><div class="col-xs-1"><i class="zmdi zmdi-close x-crsr"></i></div></div> </div></li>');
	  //adddragdrop
}

/*This function is called in editusergroup.jsp.*/
function addToAllUserList(username, id) {
	var customUserId = id.replace("group_","");
	$('<div class="grid-row drag-crsr ui-draggable ui-draggable-handle" id="grouprow_'+customUserId+'"  style="position: relative;"><div class="row m-0"><div class="col-xs-1 "><i class="zmdi zmdi-account-o"></i></div><div id="'+id+'" class="col-xs-11 allUserItem">'+username+'</div></div></div>').draggable({cancel: "a.ui-icon",revert: "invalid",containment: ".user-list",cursor: "move", helper: "clone"}).appendTo('.draggable.all-user-draggable');
	  //$('<li id="'+id+'"><i class="user-move-icon "></i>'+username+'</li>').draggable({ cancel: "a.ui-icon",revert: "invalid",containment: "document", helper: "clone",cursor: "move"}).appendTo('.draggable.all-user-draggable');
	//adddragdrop();
	addDropable();
}

/*This function is called in editusergroup.jsp.*/
function validateUserGroupForm() {
	  if(isFieldBlank("name", "Please enter group name.")){
	    	return false;
	  }
	  return true;
}

/*This function is called in edituser.jsp.*/
function viewauditdetails(userId, AuditId, shareHistoryId) {
	if(sessionStorage){
	    // Store data
	    sessionStorage.setItem("edituser_url", window.location);
	 
	} 
	
	viewauditdetailsshared('../viewaudit/'+userId+'/'+AuditId+'/'+shareHistoryId);
}

/*This function is called in viewauditdetails(userId, AuditId) above.*/
function viewauditdetailsshared(url) {
	var a = document.createElement("a");
	if (!a.click) //for IE
	{
		window.location = url;
		return;
	}
	a.setAttribute("href", url);
	a.style.display = "none";
	document.body.appendChild(a);
	a.click();
}

/*This function is called in edituser.jsp.*/
function userInEditModeFun(current, value){
	  if(!value){
		 
		  $("#useremailField").removeAttr("readonly");
		  $("#useremailField").removeClass("r-only");
		  $(current).html(' <i class="zmdi zmdi-close"></i>');
		  $(current).attr("onclick", "userInEditModeFun(this, true)");
		  $("#userInEditMode").val("true");
	  }else{
		  $("#useremailField").val($("#oldUserName").val());
		  $("#useremailField").attr("readonly","true");
		  $("#useremailField").addClass("r-only");
		  $(current).html(' <i class="zmdi zmdi-edit"></i>');
		  $(current).attr("onclick", "userInEditModeFun(this, false)");
		  $("#userInEditMode").val("false");
	  } 
}


/*This function is called in createcustomparentaudit.jsp.*/
function clearCreateCustomParentAudit(){
	  $("#createCustomParentAuditForm input:not([readonly]):not([type='hidden']):not([type='checkbox'])").val("");
	  $("#createCustomParentAuditForm textarea").val("");
	  $("#auditSectionQuestionType").val("Select Question Type");
	  $("#auditQuestionOption").html("");
	  $(".chckbx").hide();
	  $('.chckbx input:checkbox').removeAttr('checked');
	  $('#scoredAudit').removeAttr('checked');
	  $("#createCustomParentAuditForm .has-error").removeClass("has-error");
	  $("#createCustomParentAuditForm .help-block").remove();
}

/*This function is called in createcustomparentaudit.jsp and createaudit.jsp*/
function addOptionInQuestion(current, rowNumber){
	  
	  var html = $("#auditQuestionOptionHidden table tr:last-child").html();
	  $("<tr>"+html+"</tr>").insertAfter("#auditQuestionOption table tr:last-child");
	  /*$("#auditQuestionOption table tr:last-child td:first-child").text((rowNumber+1)).append(close);*/
	
	  $("#auditQuestionOption table tr:last-child textarea").attr("id", "auditSectionQuestionOption"+rowNumber);
	  $("#auditQuestionOption table tr:last-child textarea").attr("name", "section.questions[0].options["+rowNumber+"].value");

	  if($("#auditSectionQuestionType").val()=='CHECKBOX'){
		  $("#auditQuestionOption .showRadioOption").remove();
	  }else{
	  $("#auditQuestionOption table tr:last-child input").attr("id", "auditSectionRadioDefaultOption_"+rowNumber);
	  $("#auditQuestionOption table tr:last-child input").attr("name", "section.questions[0].options["+rowNumber+"].defaultOption");
	 
	  }
	  
		$(current).attr("onclick", "addOptionInQuestion(this, "+(rowNumber+1)+")");
		
		if( $('#auditQuestionOption table tr').length > 2){
			$(".optiondel").show();
		} 
		addSerialNumber();
		
}

/*This function is called in addOptionInQuestion(current, rowNumber) above*/
function deleteclose(curElement){
	$(curElement).parents('tr').remove();
	if( $('#auditQuestionOption table tr').length <= 2){
		$(".optiondel").hide();
	}
	      addSerialNumber();
	      
}

/*This function is called in deleteclose(curElement) and addOptionInQuestion(current, rowNumber) above*/
var addSerialNumber = function () {
    var i = 1
    $('table tr').each(function(index) {
        $(this).find('td:nth-child(1)').html(index+1);
    });
};





/*This function is called in createcustomparentaudit.jsp and createaudit.jsp*/
function auditQuestionTypeChange(current,resetData){
	
	  $(".chckbx").show();
	  $(".passFail").hide();
	  $(".imageRequired").show();
	  $(".commentRequired").show();
	  $(".attachmentRequired").show();
	if(resetData){
	  $('.chckbx input:checkbox').removeAttr('checked');
	}

	  var value = $(current).val();
	  if(value=='RADIOBUTTON'){
		  
		  $("#addOptnBtnId").show();
		  $("#addOptnHiddenBtnId").show();
		  if(resetData){
		  $("#auditQuestionOption").html($("#auditQuestionOptionHidden").html());
		  $("#auditQuestionOption textarea").each(function(index, current){
			  $(current).attr("id", "auditSectionQuestionOption"+(index));
		  });
		  }
		  $("#auditQuestionOption .showRadioOption").find("input").each(function(index,current){
			  $(current).attr("id", "auditSectionRadioDefaultOption_"+(index));
			});
		  
	  }else if(value=='CHECKBOX'){
		  $("#addOptnBtnId").show();
		  $("#addOptnHiddenBtnId").show();
		  if(resetData){
		  $("#auditQuestionOption").html($("#auditQuestionOptionHidden").html());
		  $("#auditQuestionOption textarea").each(function(index, current){
			  $(current).attr("id", "auditSectionQuestionOption"+(index));
			  
		  });
		  }
		  $("#auditQuestionOption .showRadioOption").remove();
	  }
	  
	  else if(value=='COMMENT'){
		  $(".commentRequired").hide();
		  $("#auditQuestionOption").html("");
	  }
	  else if(value=='ATTACHMENT'){
		  $(".attachmentRequired").hide();
		  $("#auditQuestionOption").html("");
	  }
	  else if(value=='PHOTO'){
		  $(".imageRequired").hide();
		  $("#auditQuestionOption").html("");
	  }
	  
	  else if(value=='DATE'){
		  $(".commentRequired").show();
		  $(".imageRequired").show();
		  $("#auditQuestionOption").html("");
	  }
	  
	  else if(value=='PASS_FAIL'){
		  $("#auditQuestionOption").html($("#auditQuestionOptionHidden").html());
		  $("#auditQuestionOption textarea").each(function(index, current){
			  $(current).addClass("disabble-resize");
			  $(current).attr("id", "auditSectionQuestionOption"+(index));
		  });
		  
		  $("#auditQuestionOption .showRadioOption").remove();

		  $("#auditQuestionOption").find("#auditSectionQuestionOption0").val("Pass");
		  $("#auditQuestionOption").find("#auditSectionQuestionOption1").val("Fail");
		  $("#auditQuestionOption textarea").attr('readonly','readonly');
		  $(".passFail").show();
		  $("#addOptnBtnId").hide();
		  $("#addOptnHiddenBtnId").hide();
	  }
	  
	  
	  else{
		  $("#auditQuestionOption").html("");
		  $(".chckbx").hide();
	  }
	  if( $('#auditQuestionOption table tr').length < 3){
			$(".optiondel").hide();
		} 
	  addSerialNumber();
}


/*This function is called in addquestion.jsp.*/
function clearCreateAuditAQ(){
	  $("#addQuestionForm input:not([readonly]):not([type='hidden']):not([type='checkbox'])").val("");
	  $("#addQuestionForm textarea").val("");
	  $("#auditSectionQuestionType").val("Select Question Type");
	  $("#auditQuestionOption").html("");
	  $(".chckbx").hide();
	  $("#addQuestionForm .has-error").removeClass("has-error");
	  $("#addQuestionForm .help-block").remove();
	  $('.chckbx input:checkbox').removeAttr('checked');

}

/*This function is called in addquestion.jsp.*/
function auditInEditModeFun(current, value){
	  if(!value){
		 
		  $("#auditName").removeAttr("readonly");
		  $("#auditName").removeClass("r-only");
		  $(current).html(' <i class="zmdi zmdi-close"></i>');
		  $(current).attr("onclick", "auditInEditModeFun(this, true)");
		  $("#auditInEditMode").val("true");
	  }else{
		  $("#auditName").val($("#auditNameOld").val());
		  $("#auditName").attr("readonly","true");
		  $("#auditName").addClass("r-only");
		  $(current).html(' <i class="zmdi zmdi-edit"></i>');
		  $(current).attr("onclick", "auditInEditModeFun(this, false)");
		  $("#auditInEditMode").val("false");
	  } 
}


/*This function is called in viewauditdetails(userId, auditId) in myAudit.jsp.*/
function viewauditdetailsshared(url) {
	var a = document.createElement("a");
	if (!a.click) //for IE
	{
		window.location = url;
		return;
	}
	a.setAttribute("href", url);
	a.style.display = "none";
	document.body.appendChild(a);
	a.click();
}


/*This function is called in addReport.jsp.*/
function removeCondition(current, index){
	var isAddNextedCondition = false;
	var isAddInNextedCondition = false;
	if($(current).attr("alt")=='nested-row'){
		isAddNextedCondition = true;
	}
	var currentRow = $(current).closest('.row');
	if($(currentRow).hasClass("add-report-info-inner")){
		isAddInNextedCondition = true;
	}
	var nextRow = $(currentRow).next();
	var nextIndex  = index;
	while(elementExist(nextRow)){
		if($(nextRow).hasClass("row")){
			if($(nextRow).hasClass("add-report-info")){
			//	changeIndexTest($(nextRow), nextIndex, "conditionList", -1);
					changeIndex($(nextRow), nextIndex, "conditionList", false, 0, isAddInNextedCondition, true);
					nextIndex = nextIndex+1;
			}else if($(nextRow).hasClass("add-report-nested-row")){
				//changeIndexTest($($($(nextRow).children().children()[0]).children().children()[1]), nextIndex, "conditionList", -1);
				$($($(nextRow).children().children()[0]).children().children()).each(function(){
					changeIndex($(this), nextIndex, "conditionList", false, -1, isAddInNextedCondition, false);
				});
				
				
				$($(nextRow).children().children()[1]).find("img[alt=add]").attr("onclick", "addCondition(this,"+nextIndex+")");
				$($(nextRow).children().children()[1]).find("img[alt=delete]").attr("onclick", "removeCondition(this,"+nextIndex+")");
				$($(nextRow).children().children()[1]).find("img[alt=nested-row]").attr("onclick", "addCondition(this,"+nextIndex+")");
				$($(nextRow).children().children()[1]).children("input").each(function () {
					var inputID = $(this).attr("id");
					var inputName = $(this).attr("name");
					var indexOfDott = inputID.indexOf(".");
					inputID = "conditionList"+nextIndex+(inputID.substring(indexOfDott,inputID.length));
					indexOfDott = inputName.indexOf(".");
					inputName = "conditionList["+nextIndex+"]"+(inputName.substring(indexOfDott,inputName.length));
					$(this).attr("id", inputID);
					$(this).attr("name", inputName);
				});
				nextIndex = nextIndex+1;
			}else if(isAddInNextedCondition){
				var parentIndexMatchTypeId = $($(currentRow).parent().parent().next().children()[1]).attr('id');
				var indexOfDott = parentIndexMatchTypeId.indexOf(".");
				changeIndex($(nextRow), parentIndexMatchTypeId.substring((("conditionList").length), indexOfDott) , "conditionList", false, nextIndex, isAddInNextedCondition, true);
				nextIndex = nextIndex+1;
			}
		}
		nextRow = $(nextRow).next();
	}
	$(currentRow).prev().remove();
	$(currentRow).remove();
};


/*This function is called in addReport.jsp.*/
function addCondition(current, index){
	var isAddNextedCondition = false;
	var isAddInNextedCondition = false;
	if($(current).attr("alt")=='nested-row'){
		isAddNextedCondition = true;
	}
	
	var currentRow = $(current).closest('.row');
	var matchTypeWrapperClass="rpt-match-dd";
	if($(currentRow).hasClass("add-report-info-inner")){
		matchTypeWrapperClass="rpt-match-dd-inner";
		isAddInNextedCondition = true;
	}
	var matchTypevalue = $(currentRow).parent().find('.'+matchTypeWrapperClass).find('select').val();
	var nextRow = $(currentRow).next();
	var nextIndex  = index+2;
	while(elementExist(nextRow)){
		if($(nextRow).hasClass("row")){
			if($(nextRow).hasClass("add-report-info")){
			//	changeIndexTest($(nextRow), nextIndex, "conditionList", -1);
					changeIndex($(nextRow), nextIndex, "conditionList", false, 0, isAddInNextedCondition, true);
					nextIndex += 1;
			}else if($(nextRow).hasClass("add-report-nested-row")){
				//changeIndexTest($($($(nextRow).children().children()[0]).children().children()[1]), nextIndex, "conditionList", -1);
				$($($(nextRow).children().children()[0]).children().children()).each(function(){
					changeIndex($(this), nextIndex, "conditionList", false, -1, isAddInNextedCondition, false);
				});
				
				
				$($(nextRow).children().children()[1]).find("img[alt=add]").attr("onclick", "addCondition(this,"+nextIndex+")");
				$($(nextRow).children().children()[1]).find("img[alt=delete]").attr("onclick", "removeCondition(this,"+nextIndex+")");
				$($(nextRow).children().children()[1]).find("img[alt=nested-row]").attr("onclick", "addCondition(this,"+nextIndex+")");
				$($(nextRow).children().children()[1]).children("input").each(function () {
					var inputID = $(this).attr("id");
					var inputName = $(this).attr("name");
					var indexOfDott = inputID.indexOf(".");
					inputID = "conditionList"+nextIndex+(inputID.substring(indexOfDott,inputID.length));
					indexOfDott = inputName.indexOf(".");
					inputName = "conditionList["+nextIndex+"]"+(inputName.substring(indexOfDott,inputName.length));
					$(this).attr("id", inputID);
					$(this).attr("name", inputName);
				});
				nextIndex += 1;
			}else if(isAddInNextedCondition){
				var parentIndexMatchTypeId = $($(currentRow).parent().parent().next().children()[1]).attr('id');
				var indexOfDott = parentIndexMatchTypeId.indexOf(".");
				changeIndex($(nextRow), parentIndexMatchTypeId.substring((("conditionList").length), indexOfDott) , "conditionList", false, nextIndex, isAddInNextedCondition, true);
				nextIndex += 1;
			}
		}
		nextRow = $(nextRow).next();
	}
	var addedElement;
	if(!isAddNextedCondition && !isAddInNextedCondition){
		var addReportInfoClone = $("#add-report-info-clone").html();
		$(currentRow).after(addReportInfoClone);
		addedElement = $(currentRow).next().next();
		changeIndex($(addedElement), index+1, "defaultCondition", isAddNextedCondition, 0, isAddInNextedCondition, true);
	}else if(isAddInNextedCondition){
		var addReportInfoClone = $("#add-report-nested-row-clone .add-report-info-inner").clone();
		$(currentRow).after(addReportInfoClone);
		$(currentRow).after('<div class="col-xs-12 or-sep">'+matchTypevalue+'</div>');
		addedElement = $(currentRow).next().next();
		var parentIndexMatchTypeId = $($(currentRow).parent().parent().next().children()[1]).attr('id');
		var indexOfDott = parentIndexMatchTypeId.indexOf(".");
		changeIndex($(addedElement), parentIndexMatchTypeId.substring((("conditionList").length), indexOfDott), "defaultCondition", isAddNextedCondition, index+1, isAddInNextedCondition, true);
	}else{
		var addReportNestedRowClone = $("#add-report-nested-row-clone").html();
		 $(currentRow).after(addReportNestedRowClone);
		 addedElement = $(currentRow).next().next();
		changeIndex($($($(addedElement).children().children()[0]).children().children()[1]), index+1, "defaultCondition", isAddNextedCondition, 0, isAddInNextedCondition, false);
		var innerDiv = $($(addedElement).children().children()[1]).children();
		$(innerDiv).find("img[alt=add]").attr("onclick", "addCondition(this,"+(index+1)+")");
		$(innerDiv).find("img[alt=delete]").attr("onclick", "removeCondition(this,"+(index+1)+")");
		$(innerDiv).find("img[alt=nested-row]").attr("onclick", "addCondition(this,"+(index+1)+")");
		
		var rightSideFieldWrapper = $($($(addedElement).children().children()[1]));
		$(rightSideFieldWrapper).children("input[id*=defaultCondition]").each(function () {
			var inputID = $(this).attr("id");
			var inputName = $(this).attr("name");
			var indexOfDott = inputID.indexOf(".");
			inputID = "conditionList"+(index+1)+(inputID.substring(indexOfDott,inputID.length));
			indexOfDott = inputName.indexOf(".");
			inputName = "conditionList["+(index+1)+"]"+(inputName.substring(indexOfDott,inputName.length));
			$(this).attr("id", inputID);
			$(this).attr("name", inputName);
			if(($(this).attr('id'))==('conditionList'+(index+1)+'.group')){
				$(this).val('1');
			}
		});
	}
	
	
	if($(addedElement).prev().hasClass("or-sep")){
		$(addedElement).prev().text(matchTypevalue);
	}
	changeMatchTypeVal($(addedElement), matchTypevalue);
	$(".datepicker").datetimepicker({
    	format:"DD-MM-YYYY",
    	maxDate:new Date(),
    	 ignoreReadonly : true
    });
	
};


/*This function is called in addReport.jsp.*/
function reportEditSubmit(){
	
	var form=document.getElementById("reportForm");
	form.action = "/workspace/reports/edit";
	var success = true;
	if(isFieldBlank("name", '<spring:message code="NotBlank.addReportDto.name"/>')){
		  success = false;
	  }
	
	 $("input[id*=conditionList]").each(function () {
		 var id = $(this).attr("id");
		 var message = ($(this).attr("data-error"))+" can not be blank.";
		 if(isReportFieldBlank(id, message)){
			 success = false;
		  }
		 
	 });
	if(success){
		$(".preview1").show();
		$("#add-report-nested-row-clone").remove();	
		form.submit();
	}
	return success;
}


/*This function is called in addReport.jsp.*/
function changeMatchType(current){
	var currentVal = $(current).val();
	var currentRow = $(current).closest('.row').parent();
	var nextRow = $(currentRow).next();
	while(elementExist(nextRow)){
		if($(nextRow).hasClass("add-report-info")){
			changeMatchTypeVal($(nextRow), currentVal);
		}
		if($(nextRow).hasClass("add-report-info-inner")){
			changeMatchTypeVal($(nextRow), currentVal);
		}
		if($(nextRow).hasClass("or-sep")){
			$(nextRow).text(currentVal);
		}
		if($(nextRow).hasClass("add-report-nested-row")){
			changeMatchTypeVal($(nextRow), currentVal);
		}
		nextRow = $(nextRow).next();
	}
}

/*This function is called in function changeMatchType(current) above */
function changeMatchTypeVal(element, currentVal){
	if($(element).hasClass('add-report-nested-row')){
		$($(element).children().children()[1]).children("input[id*=matchType]").val(currentVal);
	}else{
		$(element).children('div').each(function () {
			$(this).children("input[id*=matchType]").each(function () {
				$(this).val(currentVal);
			});
		});
	}
	
}



/*This function is called in addReport.jsp.*/
function createTextContainer(obj, ele){
	var div = $('<div></div>',{
		class : obj.cls
	});
	var inputId = 'conditionList'+obj.row+'.valueList'+obj.idx,
		inputName = 'conditionList['+obj.row+'].valueList['+obj.idx+']';
	if(obj.nested && obj.innerIdx){
		inputId = 'conditionList'+obj.row+'.innerConditionList'+obj.innerIdx+'.valueList'+obj.idx,
		inputName = 'conditionList['+obj.row+'].innerConditionList['+obj.innerIdx+'].valueList['+obj.idx+']';
	}
	
	var columnSelectBox = ele.prev().find('select');
	var newInput = $('<input>',{
		id : inputId,
		name : inputName,
		class : 'form-control no-radius',
		'data-error' : columnSelectBox.find('option:selected').text(),
		type : 'text'			
	});
	div.append(newInput);
	var inputTypeAttr = columnSelectBox.attr('data-coltype');
	if(inputTypeAttr == 'date'){
		newInput.addClass('datepicker');
	} else if(inputTypeAttr == 'number') {
		newInput.attr('type', 'number');
	}
	
	return div;
}


/*This function is called in addReport.jsp.*/
function columnFilterValueChange(current, isNexted){
	var self = $(current),
		parent = self.parent();
	var columnNumber = $('option:selected', current).attr("data-col");

	//var elementParentClass="col-xs-"+(4/columnNumber);
	removeDatepickerInputs(self);
	var innerRowIdx = 0;
	if(isNexted){
		innerRowIdx = getCurrentRowInner(self);
	}
	
	if(columnNumber == 0) {
		$('<div></div>',{class:'col-xs-4'}).insertAfter(parent);
	} else if(columnNumber == 1){
		var html = createTextContainer({row: getCurrentRow(self), idx: 0, cls : 'col-xs-4', nested : isNexted, innerIdx : innerRowIdx}, parent);
		html.insertAfter(parent);			
	} else if(columnNumber ==2) {
		for(var i=0; i<2;i++){
			var html = createTextContainer({row: getCurrentRow(self), idx: i, cls :'col-xs-2', nested : isNexted, innerIdx : innerRowIdx}, parent);
			if(i==0)
				html.insertAfter(parent);
			else
				html.insertAfter(parent.next());
		}
	}
	$(".datepicker").datetimepicker({
   	format:"DD-MM-YYYY",
   	maxDate:new Date(),
   	 ignoreReadonly : true
   });
}

/*This function is called in function columnFilterValueChange(current, isNexted) above.*/
function removeDatepickerInputs(ele){
	 var pEle =ele.parent(); 
	pEle.next('.col-xs-4').remove();
	pEle.nextAll('.col-xs-2').find('input[type="text"]').parent().remove();
	pEle.nextAll('.col-xs-4').find('input[type="text"]').parent().remove(); 
}

/*This function is called in function columnFilterValueChange(current, isNexted) above.*/
function getCurrentRow(ele) {
	var cid = ele.attr('id');
	cid = cid.split('.')[0];
	return cid.substr(cid.length-1, cid.length);
}

/*This function is called in function columnFilterValueChange(current, isNexted) above.*/
function getCurrentRowInner(ele) {
	var cid = ele.attr('id');
	cid = cid.split('.')[1];
	return cid.substr(cid.length-1, cid.length);
}


/*This function is called in createcustomaudit.jsp.*/
function auditSectionAndQuestion(current) {
	 subAssetList=new Array();
	var auditId = $(current).val();
	$("#parentInfo").val($("#auditCustomParentOne option:selected").attr("id"));
	if(auditId != ""){
	$("#preview1").show();
	$.ajax({
		url : 'getCustomParentDetails/' + auditId,
		success : function(result) {
			$("#preview1").hide();
			//$("#create-audit").show();
			$("#create-audit").html(result);
		}
	});
	}else{
		$("#create-audit").html("");
	
	}
}


/*This function is called in questionRespondent.jsp.*/
function expandRow(current){
	 $("#popup_leftpanel tr").removeClass("selected");
	 $(current).addClass("selected");
	 var popup_leftpanel = $("#popup_leftpanel");
	 var popup_rightpanel = $("#popup_rightpanel");
	 if(elementExist($(popup_rightpanel))){
		   $(popup_rightpanel).remove();
	 }
	  $(popup_leftpanel).removeClass("col-xs-12");
	  $(popup_leftpanel).addClass("col-xs-7");
	  var rightPanel= '<div class="col-xs-5" id="popup_rightpanel"><div class="image-view "><div class="close-image">'+
	  '<button type="button" class="close jpopup-close-btn-cust" data-dismiss="modal" aria-label="Close" onclick="collapseRow(this)">'+
	  '<span aria-hidden="true">×</span></button></div><div class="fault-image-description">';
	  
	  var respondentname = $(current).find(".respondentname").text();
	  rightPanel+=' <div class="row m-t-10"><div class="col-xs-4">Name</div><div class="col-xs-6">'+respondentname+'</div></div>'
	  
	  var respondentemail = $(current).find(".respondentemail").text();
	  rightPanel+=' <div class="row m-t-10"><div class="col-xs-4">Email</div><div class="col-xs-8 word-break">'+respondentemail+'</div></div>';
	  
	  var respondentscoreElem = $(current).find(".respondentscore")
	  if(elementExist(respondentscoreElem)){
	  rightPanel+=' <div class="row m-t-10"><div class="col-xs-4">Score</div><div class="col-xs-8">'+$(respondentscoreElem).text()+'</div></div>'
	  }
	  
	  var respondentscoreanswerElem = $(current).find(".respondentscoreanswer")
	  if(elementExist(respondentscoreanswerElem)){
		  var qtype = $(respondentscoreanswerElem).attr("data-qtype")
		  if(qtype=='ATTACHMENT'){
			  var fileid = $(respondentscoreanswerElem).find("i").attr("data-fileid")
			  rightPanel+=' <div class="row m-t-10"><div class="col-xs-4">Answer</div><div class="col-xs-8">'+
			  '<a href="/common/download/file/'+fileid+'" target="_blank"><i class="zmdi zmdi-download zmdi-hc-lg"></i></a>'+
			  '</div></div>';
		  }
		  else if(qtype=='PHOTO'){
			  var fileid = $(respondentscoreanswerElem).find("i").attr("data-fileid")
			  rightPanel+=' <div class="row m-t-10"><div class="col-xs-4">Answer</div><div class="col-xs-8">'+
			  '<img src="/common/download/file/'+fileid+'" class="img-responsive"/>'+
			  '</div></div>';
		  }else{
			  rightPanel+=' <div class="row m-t-10"><div class="col-xs-4">Answer</div><div class="col-xs-8">'+$(respondentscoreanswerElem).find("span").text()+'</div></div>';
		  }
	  
	  }
	  
	  var respondentaddattachmentElem = $(current).find(".respondentaddattachment")
	  if(elementExist(respondentaddattachmentElem)){
			  var fileid = $(respondentaddattachmentElem).find("i").attr("data-fileid");
			  if( typeof(fileid) == "undefined" || fileid == null ){
				rightPanel+=' <div class="row m-t-10"><div class="col-xs-4">Additional Attachment</div><div class="col-xs-8">'+
      		   '</div></div>';
			  }else {
			   rightPanel+=' <div class="row m-t-10"><div class="col-xs-4">Additional Attachment</div><div class="col-xs-8">'+
   		   '<a href="/common/download/file/'+fileid+'" target="_blank"><i class="zmdi zmdi-download zmdi-hc-lg"></i></a>'+
   		   '</div></div>';
			  }
	  }
	  
	  var respondentaddimageElem = $(current).find(".respondentaddimage");
	  if(elementExist(respondentaddimageElem)){
			  var fileid = $(respondentaddimageElem).find("i").attr("data-fileid")
			  if(!( typeof(fileid) == "undefined" || fileid == null )){
			  rightPanel+=' <div class="row m-t-10"><div class="col-xs-4">Additional Image</div><div class="col-xs-8">'+
			  '<img src="/common/download/file/'+fileid+'" class="img-responsive"/>'+
			  '</div></div>';
			    }else{
			    	 rightPanel+=' <div class="row m-t-10"><div class="col-xs-4">Additional Image</div><div class="col-xs-8">'+
	    			  '</div></div>';
			    }
	  
	  }
	  
	  var respondentaddcommentElem = $(current).find(".respondentaddcomment  span");
	  if(elementExist(respondentaddcommentElem)){
			  rightPanel+=' <div class="row m-t-10"><div class="col-xs-4">Additional Comment</div><div class="col-xs-8">'+$(respondentaddcommentElem).text()+'</div></div>';
	  }
	  
	  var riskFactorElem = $(current).find(".riskFactor");
	  if(elementExist(riskFactorElem)){
		  rightPanel+=' <div class="row m-t-10"><div class="col-xs-4">Risk Rating</div><div class="col-xs-8">'+$(riskFactorElem).text()+'</div></div>';
	  }
	  var riskImageElem = $(current).find(".riskImage");
	  if(elementExist(riskImageElem)){
		  var fileid = $(riskImageElem).find("i").attr("data-fileid")
		  if(!( typeof(fileid) == "undefined" || fileid == null )){
		  rightPanel+=' <div class="row m-t-10"><div class="col-xs-4">Risk Image</div><div class="col-xs-8">'+
		  '<img src="/common/download/file/'+fileid+'" class="img-responsive"/>'+
		  '</div></div>';
		    }else{
		    	 rightPanel+=' <div class="row m-t-10"><div class="col-xs-4">Risk Image</div><div class="col-xs-8">'+
    			  '</div></div>';
		    }
		  
	  }
	  var riskCommentElem = $(current).find(".riskComment span");
	  if(elementExist(riskCommentElem)){
		  rightPanel+=' <div class="row m-t-10"><div class="col-xs-4">Risk Comment</div><div class="col-xs-8">'+$(riskCommentElem).text()+'</div></div>';
	  }
	  var submittedDateElem = $(current).find(".submittedDate span");
	   if(elementExist(submittedDateElem)){
	    rightPanel+=' <div class="row m-t-10"><div class="col-xs-4">Submitted Date</div><div class="col-xs-8">'+$(submittedDateElem).text()+'</div></div>';
	   }	  
	  rightPanel+='</div></div></div>';
	  $( current ).parent().parent().parent().parent().after( rightPanel );
 }

/*This function is called in function expandRow(current) above*/
function collapseRow(current){
	  $("#popup_leftpanel tr").removeClass("selected");
	  var popup_leftpanel = $("#popup_leftpanel");
	  var popup_rightpanel = $("#popup_rightpanel");
	  $(popup_rightpanel).remove();
	  $(popup_leftpanel).removeClass("col-xs-7");
	  $(popup_leftpanel).addClass("col-xs-12");
}

/*This function is called in auditResultImages.jsp*/
function expandRow1(current){
	 $("#popup_leftpanel_image tr").removeClass("selected");
	 $(current).addClass("selected");
	 var popup_leftpanel = $("#popup_leftpanel_image");
	 var popup_rightpanel = $("#popup_rightpanel_image");
	
	 if(elementExist($(popup_rightpanel))){
		   $(popup_rightpanel).remove();
	 } 
	  $(popup_leftpanel).removeClass("col-xs-12");
	  $(popup_leftpanel).addClass("col-xs-7");
	  var rightPanel= '<div class="col-xs-5" id="popup_rightpanel_image"><div class="image-view mCustomScrollbar"><div class="close-image">'+
	  '<button type="button" class="close jpopup-close-btn-cust" data-dismiss="modal" aria-label="Close" onclick="collapseRow1(this)">'+
	  '<span aria-hidden="true">×</span></button></div><div class="fault-image-description">';
	  var respondentname = $(current).find(".respondentname").text();
	  rightPanel+=' <div class="row m-t-10"><div class="col-xs-4">Name</div><div class="col-xs-6">'+respondentname+'</div></div>'
	  var respondentemail = $(current).find(".respondentemail").text();
	  rightPanel+=' <div class="row m-t-10"><div class="col-xs-4">Email</div><div class="col-xs-8 word-break">'+respondentemail+'</div></div>'
	  
	  
	  var respondentaddimageElem = $(current).find(".respondentaddimage")
	  if(elementExist(respondentaddimageElem)){
			  var fileid = $(respondentaddimageElem).attr("data-fileid");
			  if(!( typeof(fileid) == "undefined" || fileid == null )){
			  rightPanel+=' <div class="row m-t-10"><div class="col-xs-4">Image</div><div class="col-xs-8">'+
			  '<img src="/common/download/file/'+fileid+'" class="img-responsive"/>'+
			  '</div></div>';
			  }else{
				  rightPanel+=' <div class="row m-t-10"><div class="col-xs-4">Image</div><div class="col-xs-8">'+
   			  '</div></div>';
			  }
	  
	  }
	  var submittedDate = $(current).find(".submittedDate").text();
	   rightPanel+=' <div class="row m-t-10"><div class="col-xs-4">Submitted Date</div><div class="col-xs-8 word-break">'+submittedDate+'</div></div>';
	  rightPanel+='</div></div></div>';
	  $( current ).parent().parent().parent().parent().after( rightPanel );
 }

/*This function is called in function expandRow(current) above*/
 function collapseRow1(current){
	  $("#popup_leftpanel_image tr").removeClass("selected");
	  var popup_leftpanel = $("#popup_leftpanel_image");
	  var popup_rightpanel = $("#popup_rightpanel_image");
	  $(popup_rightpanel).remove();
	  $(popup_leftpanel).removeClass("col-xs-7");
	  $(popup_leftpanel).addClass("col-xs-12");
 }

 /*This function is called in createaudit.jsp*/
 function clearCreateAudit(){
	  $("#createAuditForm input:not([readonly]):not([type='hidden']):not([type='checkbox'])").val("");
	  $("#createAuditForm textarea").val("");
	  $("#auditSectionQuestionType").val("Select Question Type");
	  $("#auditQuestionOption").html("");
	  $(".chckbx").hide();
	  $('.chckbx input:checkbox').removeAttr('checked');
	  $('#scoredAudit').removeAttr('checked');
	  $('#riskAssociated').removeAttr('checked');
	  $("#createAuditForm .has-error").removeClass("has-error");
	  $("#createAuditForm .help-block").remove();
 }

 
 /*This function is called in manageaudit.jsp*/ 
 function rowClick(_this) {
		var showblueStrip = true;
		if ($(_this).next().hasClass("action-div-cont")) {
			showblueStrip = false;
		}
		$(".action-div-cont").each(function() {
			$(this).prev().append('<span class="right-more-icon"></span>');
			$(this).prev().find('.left-more-icon').remove();
			$(this).remove();
		});
		if (showblueStrip) {
			var ht = getOptions($(_this).attr("data-id"), $(_this).attr(
					"data-type"), $(_this).attr("data-shareid"));
			$(ht).insertAfter($(_this));
			$(_this).append('<span class="left-more-icon"></span>');
			$(_this).find('.right-more-icon').remove();
		}
	}
 
 /*This function is called in rowClick(_this) above*/ 
function getOptions(id, type, shareid) {
		if (type == 'PUBLISH') {
			if(shareid=='0'){
			 return '<div class="action-div-cont"> <ul> <li><a href="javascript:share('
					+ id
					+ ');" ><i class="zmdi zmdi-share"></i> Share </a></li><li><a href="view/'+id+'" ><i class="zmdi zmdi-eye"></i> View </a></li> <li><a href="javascript:createCopyAuditPopup('+id+')"><i class="zmdi zmdi-copy"></i> Copy </a></li></ul> <div class="clearfix"></div> </div>';
			}else{ 
			return '<div class="action-div-cont"> <ul> <li><a href="javascript:share('
					+ id
					+ ');" ><i class="zmdi zmdi-share"></i> Share </a></li><li><a href="view/'+id+'" ><i class="zmdi zmdi-eye"></i> View </a></li> <li><a href="viewresult/'+id+'" ><i class="zmdi zmdi-assignment"></i> View Results </a></li> <li><a href="javascript:createCopyAuditPopup('+id+')"><i class="zmdi zmdi-copy"></i> Copy </a></li></ul> <div class="clearfix"></div> </div>';

			 } 
		}

		else if (type == 'NOT_PUBLISH') {
			return '<div class="action-div-cont"> <ul> <li><a href="view/'+id+'" ><i class="zmdi zmdi-eye"></i> View </a></li>  <li><a  href="javascript:publishAudit1('+id+')"><i class="zmdi zmdi-upload"></i> Publish </a></li><li><a href="javascript:createCopyAuditPopup('+id+')"><i class="zmdi zmdi-copy"></i> Copy </a></li></ul> <div class="clearfix"></div> </div>';
		}

}


 
 /*This function is called in managecustomparentaudit.jsp*/
 function rowClick1(_this) {
		var showblueStrip = true;
		if ($(_this).next().hasClass("action-div-cont")) {
			showblueStrip = false;
		}
		$(".action-div-cont").each(function() {
			$(this).prev().append('<span class="right-more-icon"></span>');
			$(this).prev().find('.left-more-icon').remove();
			$(this).remove();
		});
		if (showblueStrip) {
			var ht = getOptions1($(_this).attr("data-id"), $(_this).attr(
					"data-type"), $(_this).attr("data-shareid"));
			$(ht).insertAfter($(_this));
			$(_this).append('<span class="left-more-icon"></span>');
			$(_this).find('.right-more-icon').remove();
		}
	}

 /*This function is called in function rowClick1(_this) above */
	function getOptions1(id, type, shareid) {
		
			return '<div class="action-div-cont"> <ul> <li><a href="viewcustomparentaudit/'+id+'" ><i class="zmdi zmdi-eye"></i> View </a><li><a href="javascript:createCopyAuditPopup1('+id+')"><i class="zmdi zmdi-copy"></i> Copy </a></li></ul> <div class="clearfix"></div> </div>';

	}
	
