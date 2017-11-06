<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="container">


	<!-- ---------- -->

	<div class="card-body">

		<div class="card p-b-0 m-b-10 bg-hide">
			<div class="card-header p-0">
				<h2>Order list</h2>
			</div>
		</div>

		<!-- Success message -->

		<div class="card p-b-0 m-b-15">
			<div class="card-header p-15 header-button">
				<div class="row">

					<div class="col-sm-4">

						<div class="search head-search group-search"></div>

					</div>

					<div class="col-sm-8 text-right">
						<!-- <button class="btn  btn-primary waves-effect" data-toggle="modal" data-target="#create-audit" onclick="addUserPopup()"><i class="zmdi zmdi-plus"></i> Add User</button> -->
					</div>

				</div>
			</div>

			<div class="card-body">
				<div class="doclist-cont">
					<div class="list-group audit-list-cont m-b-10">
						<div class="p-r-15 p-l-15 ">
							<!-- <div class="m-b-15 text-center">
								<label class="radio radio-inline m-r-20"> <input
									type="radio" name="inlineRadioOptions" value="option1">
									<i class="input-helper"></i> Complete
								</label> <label class="radio radio-inline m-r-20"> <input
									type="radio" name="inlineRadioOptions" value="option2">
									<i class="input-helper"></i> Pending
								</label> <label class="radio radio-inline m-r-20"> <input
									type="radio" name="inlineRadioOptions" value="option3">
									<i class="input-helper"></i> Hold
								</label>
							</div> -->

							<ul class="tab-nav tn-justified tn-icon main-tabs" role="tablist"
								id="tabs_body">
								<li class="active"><a href="#"> All Orders</a></li>
							</ul>
							<c:choose>
								<c:when test="${empty orderList}">
									<div
										class="height-cont new-audit-cont scroll-container mCustomScrollbar">
										<div class="no-item">
											<i class="zmdi zmdi-block"></i> No Order to display.
										</div>
									</div>
								</c:when>
								<c:otherwise>

									<table class="table">
										<tr>
											<th>Name</th>
											<th>Email Id</th>
											<th>Date</th>
											<th>Number</th>
											<th>Address</th>
											<th>Total Amount</th>
											<th class="text-right">Status</th>

										</tr>
										<c:forEach items="${orderList}" var="orderListVar"
											varStatus="orderListVarStatus">
											<tr id="${orderListVar.id}" onclick="javascript:showDetails(${orderListVar.id})">
												<td>${orderListVar.name}</td>
												<td>${orderListVar.emailId}</td>
												<td>${orderListVar.date}</td>
												<td>${orderListVar.mobileNum}</td>
												<td>${orderListVar.address}</td>
												<td>${orderListVar.amount}</td>
												<td class="text-right">${orderListVar.status}</td>
											</tr>
										</c:forEach>
									</table>
								</c:otherwise>
							</c:choose>
						</div>
					</div>


				</div>

			</div>
			<div class="clearfix"></div>
		</div>

		<c:if test="${not empty orderList}">
			<!-- card2 -->

			<div class="card p-b-0 m-b-15 orderlist-details-data" style="display: none;">
				<div class="card-body">
					<div class="doclist-cont">
						<div class="list-group audit-list-cont m-b-10">
							<div class="p-r-15 p-l-15 ">


								<ul class="tab-nav tn-justified tn-icon main-tabs">
									<li class="active"><a href="#">User Details</a></li>
								</ul>

								<c:forEach items="${orderList}" var="orderListVar"	varStatus="orderListVarStatus">
								<div id="info_${orderListVar.id}" class="order-details"  style="display: none;">
									<table class="table">
										<tr class="info" id="info_">
											
												<td>${orderListVar.name}</td>
												<td>${orderListVar.emailId}</td>
												<td>${orderListVar.date}</td>
												<td>${orderListVar.mobileNum}</td>
												<td>${orderListVar.address}</td>
												<td class="text-right">${orderListVar.status}</td>
											</tr>

									</table>

									<table class="table  m-t-15" style="width: 50%">
										<tr>
											<th>Product</th>
											<th>Price</th>
											<th>Quantity</th>
											<th>Cost</th>

										</tr>
										<c:forEach items="${orderListVar.data}" var="orderListDataVar"	varStatus="orderListDataVarStatus">
											<tr>
												<td>${orderListDataVar.title}</td>
												<td>${orderListDataVar.price}</td>
												<td>${orderListDataVar.qty}</td>
												<td>${orderListDataVar.total}</td>
											</tr>
											</c:forEach>
											<tfoot>
												<tr>
													<th></th>
													<th></th>
													<th>Total</th>

													<th>${orderListVar.amount}</th>

												</tr>
											</tfoot>


										</table>
									</div>
								</c:forEach>

							</div>
						</div>


					</div>

				</div>
				<div class="clearfix"></div>
			</div>
		</c:if>
	</div>

</div>
<script>
function showDetails(oderId){
	$(".orderlist-details-data").show();
	$(".order-details").hide();
	$("#info_"+oderId).show();
	}
	</script>