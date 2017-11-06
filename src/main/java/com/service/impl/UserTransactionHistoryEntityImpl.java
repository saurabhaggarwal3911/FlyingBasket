package com.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.CheckoutDto;
import com.dto.PurchasedItemDto;
import com.entity.UserProductTransacationDetailEntity;
import com.entity.UserTransactionHistoryEntity;
import com.repository.UserTransactionHistoryRepository;
import com.service.IUserTransactionHistoryEntityService;

@Service
public class UserTransactionHistoryEntityImpl implements IUserTransactionHistoryEntityService {
	private static final Logger LOGGER = LogManager.getLogger(UserTransactionHistoryEntityImpl.class);

	@Autowired
	private UserTransactionHistoryRepository userTransactionHistoryRepository;
	
	@Transactional(readOnly = true, timeout = 100000, value = "transactionManager")
	@Override
	public List<CheckoutDto> getOrderListWithStatus(String status, Integer pageNum, int maxresult) {
		List<UserTransactionHistoryEntity> userTransactionHistoryEntitiesList = null;
		if(status != null){
			userTransactionHistoryEntitiesList = userTransactionHistoryRepository.findAllByStatus(status);
		}else{
			userTransactionHistoryEntitiesList = userTransactionHistoryRepository.findAll();
		}
		if(userTransactionHistoryEntitiesList != null && !userTransactionHistoryEntitiesList.isEmpty()){
			List<CheckoutDto> CheckoutDtoList= new ArrayList<>();
			for (UserTransactionHistoryEntity userTransactionHistoryEntity : userTransactionHistoryEntitiesList) {
				List<UserProductTransacationDetailEntity> userProductTransacationDetailList = userTransactionHistoryEntity.getUserProductTransacationDetailList();
				CheckoutDto checkoutDto = new CheckoutDto();
				checkoutDto.setId(userTransactionHistoryEntity.getId());
				checkoutDto.setAddress(userTransactionHistoryEntity.getBillAddress());
				checkoutDto.setAmount(userTransactionHistoryEntity.getAmount());
				checkoutDto.setDate(userTransactionHistoryEntity.getCreatedOn()+"");
				checkoutDto.setMobileNum(userTransactionHistoryEntity.getBillMobile());
				checkoutDto.setName(userTransactionHistoryEntity.getBillName());
				checkoutDto.setPayMode(userTransactionHistoryEntity.getPayMode());
				checkoutDto.setStatus(userTransactionHistoryEntity.getStatus());
				List<PurchasedItemDto> PurchasedItemDtoList = new ArrayList<>();
				if(userProductTransacationDetailList != null && !userProductTransacationDetailList.isEmpty()){
					for (UserProductTransacationDetailEntity userProductTransacationDetailEntity : userProductTransacationDetailList) {
						PurchasedItemDto purchasedItemDto = new PurchasedItemDto();
						purchasedItemDto.setId(userProductTransacationDetailEntity.getProductId());
						purchasedItemDto.setPrice(userProductTransacationDetailEntity.getPrice());
						purchasedItemDto.setTitle(userProductTransacationDetailEntity.getProductName());
						purchasedItemDto.setQty(userProductTransacationDetailEntity.getQuanity());
						purchasedItemDto.setTotal(userProductTransacationDetailEntity.getTotal());
						PurchasedItemDtoList.add(purchasedItemDto);
					}
				}
				checkoutDto.setData(PurchasedItemDtoList);
				CheckoutDtoList.add(checkoutDto);
			}
			return CheckoutDtoList;
		}
		return null;
	}

	@Transactional(timeout = 100000, value = "transactionManager")
	@Override
	public void deleteOrder(int orderId, Boolean isDeleteAll) {
		if (isDeleteAll) {
			userTransactionHistoryRepository.deleteAll();
	    } else {
	    	userTransactionHistoryRepository.delete(orderId);
	    }

		
	}
	
}
