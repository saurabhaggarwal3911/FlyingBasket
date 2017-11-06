package com.service;

import java.util.List;

import com.dto.CheckoutDto;

public interface IUserTransactionHistoryEntityService {

	List<CheckoutDto> getOrderListWithStatus(String status, Integer pageNum, int maxresult);

	void deleteOrder(int orderId, Boolean isDeleteAll);
	
}
