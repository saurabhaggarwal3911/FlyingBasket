function editRow(url) {
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


$.valHooks.textarea = {
  get: function( elem ) {
    return elem.value.replace( /\r?\n/g, "\r\n" );
  }
};



var substringMatcher = function (strs) {
    return function findMatches(q, cb) {
        var matches;
        matches = [];
        $.each(strs, function (i, str) {
        	if(elementExist(str)){
        		if (str.name.toLowerCase().startsWith(q.toLowerCase()) || str.name.toLowerCase().indexOf(' '+q.toLowerCase()) != -1) {
        			matches.push(str);
        		}
        	}
        });
        cb(matches);
    };
};

var substringMatcherAjax = function (url, inputData) {
    return function findMatches(q, cb) {
        
        doJsonAjax(url, inputData, function(data){
        	cb(data);
        })
        
    };
};


$(document).ready(function(){
	
	$.ajaxSetup({
	    cache: false
	});
	
});

if(typeof String.prototype.trim !== 'function') {
  String.prototype.trim = function() {
    return this.replace(/^\s+|\s+$/g, ''); 
  }
}


function hideElement(elemId){
	document.getElementById(elemId).style.display="none";
}

function showElement(elemId){
	document.getElementById(elemId).style.display="block";
}





function isBlank(value){
	if(value==null || value.trim().length==0){
		return true;
	}else{
		return false;
	}
}

function elementExist(elem){
	if(elem==null || elem == 'undefined' || elem.length==0){
		return false;
	}else{
		return true;
	}
}

function isFieldHaveValue(fieldId, value, errormessage){
	var field = document.getElementById(fieldId);
	var fieldValue = field.value;
	var nextElem = $(field).parent().next();
	  if(elementExist(nextElem) && nextElem.is("small")){
		  $(nextElem).remove();
		  $("#"+fieldId).parent().parent().removeClass("has-error");
	  }
	  if(fieldValue==value){
		  $(field).parent().after('<small class="help-block">'+errormessage+'</small>');
		  $("#"+fieldId).parent().parent().addClass("has-error");
		  return true;
	  }
	  return false;
}

function isFieldBlank(fieldId, errormessage){
	var field = document.getElementById(fieldId);
	var fieldValue = field.value;
	var nextElem = $(field).parent().next();
	  if(elementExist(nextElem) && nextElem.is("small")){
		  $(nextElem).remove();
		  $("#"+fieldId).parent().parent().removeClass("has-error");
	  }
	  if(isBlank(fieldValue)){
		  $(field).parent().after('<small class="help-block">'+errormessage+'</small>');
		  $("#"+fieldId).parent().parent().addClass("has-error");
		  return true;
	  }
	  return false;
}

function showErrorMessage(fieldId, errormessage){
	var field = document.getElementById(fieldId);
	var nextElem = $(field).parent().next();
	  if(elementExist(nextElem) && nextElem.is("small")){
		  $(nextElem).remove();
		  $("#"+fieldId).parent().parent().removeClass("has-error");
	  }
	  $(field).parent().after('<small class="help-block">'+errormessage+'</small>');
	  $("#"+fieldId).parent().parent().addClass("has-error");
	
}

function clearField(fieldId){
	var field = document.getElementById(fieldId);
	field.value="";
	var nextElem = $(field).parent().next();
	  if(elementExist(nextElem) && nextElem.is("small")){
		  $(nextElem).remove();
		  $("#"+fieldId).parent().parent().removeClass("has-error");
	  }
}

function isFieldHaveLessCharacter(fieldId, fieldLength, errormessage){
	
	var field = document.getElementById(fieldId);
	var fieldValue = document.getElementById(fieldId).value;
	 var nextElem = $(field).parent().next();
	  if(elementExist(nextElem) && nextElem.is("small")){
		  $(nextElem).remove();
		  $("#"+fieldId).parent().parent().removeClass("has-error");
	  }
	  if(fieldValue.length<fieldLength){
		 
		  $(field).parent().after('<small class="help-block">'+errormessage+'</small>');
		  $("#"+fieldId).parent().parent().addClass("has-error");
		  return true;
	  }
	  return false;
}

function isFieldHaveMoreCharacter(fieldId, fieldLength, errormessage){
	var field = document.getElementById(fieldId);
	var fieldValue = document.getElementById(fieldId).value;
	 var nextElem = $(field).parent().next();
	  if(elementExist(nextElem) && nextElem.is("small")){
		  $(nextElem).remove();
		  $("#"+fieldId).parent().parent().removeClass("has-error");
	  }
	  if(fieldValue.length>fieldLength){
		 
		  $(field).parent().after('<small class="help-block">'+errormessage+'</small>');
		  $("#"+fieldId).parent().parent().addClass("has-error");
		  return true;
	  }
	  return false;
}



function isFieldsHaveDifferValue(fieldId, fieldId2, errormessage){
	var field2 = document.getElementById(fieldId2);
	var fieldValue = document.getElementById(fieldId).value;
	var fieldValue2 = document.getElementById(fieldId2).value;
	 var nextElem = $("#"+fieldId2).parent().next();
	  if(elementExist(nextElem) && nextElem.is("small")){
		  $(nextElem).remove();
		  $("#"+fieldId2).parent().parent().removeClass("has-error");
	  }
	  if(fieldValue!=fieldValue2){
		  $(field2).parent().after('<small class="help-block">'+errormessage+'</small>');
		  $("#"+fieldId2).parent().parent().addClass("has-error");
		  return true;
	  }
	  return false;
}
function isFieldsHaveSameValue(fieldId, fieldId2, errormessage){
	var field2 = document.getElementById(fieldId2);
	var fieldValue = document.getElementById(fieldId).value;
	var fieldValue2 = document.getElementById(fieldId2).value;
	 var nextElem = $("#"+fieldId2).parent().next();
	  if(elementExist(nextElem) && nextElem.is("small")){
		  $(nextElem).remove();
		  $("#"+fieldId2).parent().parent().removeClass("has-error");
	  }
	  if(fieldValue==fieldValue2){
		  $(field2).parent().after('<small class="help-block">'+errormessage+'</small>');
		  $("#"+fieldId2).parent().parent().addClass("has-error");
		  return true;
	  }
	  return false;
}


function isFieldsHaveValidValue(fieldId, regExp, errormessage){
	var field = document.getElementById(fieldId);
	var fieldValue = document.getElementById(fieldId).value;
	 var nextElem = $(field).parent().next();
	  if(elementExist(nextElem) && nextElem.is("small")){
		  $(nextElem).remove();
		  $("#"+fieldId).parent().parent().removeClass("has-error");
	  }
	if(!regExp.test(fieldValue)){
		 
		  $(field).parent().after('<small class="help-block">'+errormessage+'</small>');
		  $("#"+fieldId).parent().parent().addClass("has-error");
		  return true;
	  }
	  return false;
}




function getInternetExplorerVersion()
{
    var rv = -1; // Return value assumes failure.

    if (navigator.appName == 'Microsoft Internet Explorer')
    {
        var ua = navigator.userAgent;
        var re  = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
        if (re.exec(ua) != null)
            rv = parseFloat( RegExp.$1 );
    }

    return rv;
}

function stopParentEventCommon(e){
	if(e){
		 var ver = getInternetExplorerVersion();
	if (ver == 8.0) {
		 window.event.cancelBubble = true;
		}
	else if(e.preventDefault()){
		 e.preventDefault
		();}
		else if(e.stopPropagation){
		 e.stopPropagation();
		}
		else{
		 window.event.cancelBubble = true;
		}
		}
		else{
			 window.event.cancelBubble = true;
		}
}


function doJsonAjax(url, inputData, callBack){
		//This line added to prevent caching on IE 8
		$.ajaxSetup({ cache: false });
		
		$.getJSON(url, inputData, function (data, status, xhr){
			callBack(xhr, status, data);
		});

	}
function doAjax(url, inputData, callBack,callbackfn) {
		$.ajax({
			  url: url,
			  data: (inputData),
			  success: function (data){ 
			callBack(data);
			}
		
		});
	}



function hasPressEnterKey(e){
	 if (e.keyCode == 13) {
		 return true;
	 }else{
		 return false;
	 }
}


function formSubmit(id){
	$("#"+id).submit();
}

function aboutPopup(){
//	$("#preview1").show();
	$("#aboutUsModal").modal();
	//$("#preview1").hide();
	
}

function profilePopup(){
	$("#profileModal").modal();
}

function createFeedbackPopup(){
	$("#feedbackModal").modal();
}

function createProfilePopup(){
	$("#preview1").show();
	$("#profileModal .modal-content").load('/common/user/profile', function(response, status, xhr){
		$("#preview1").hide();
		 if(xhr.statusText == "success"){
			 $("#profileModal").modal(); 
	      }
        /* if(xhr.statusText == "error"){ alert("Error: " + xhr.status + ": " + xhr.statusText);
         }*/

		 if(xhr.status==401){
				window.location = '<c:url value="/login"/>';
			}
            });
	}

function createFeedbackPopup(){
	$("#preview1").show();
	$("#feedbackModal .modal-content").load('/common/feedback/send', function(response, status, xhr){
		$("#preview1").hide();	 
		if(xhr.statusText == "success"){
			 $("#feedbackModal").modal(); 
	      }
         /*if(xhr.statusText == "error"){ alert("Error: " + xhr.status + ": " + xhr.statusText);
         }*/
         
         if(xhr.status==401){
				window.location = '<c:url value="/login"/>';
			}
            });
	}
	

























