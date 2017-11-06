package com.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.mail.MessagingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.controller.RestProductController;
import com.dto.CartCheckoutDto;
import com.dto.CategoryBrandProductDto;
import com.dto.CheckoutDto;
import com.dto.CurrentUserDto;
import com.dto.EmailDto;
import com.dto.FieldErrorDTO;
import com.dto.NameIdDto;
import com.dto.ProductDto;
import com.dto.PurchasedItemDto;
import com.entity.BrandEntity;
import com.entity.CategoryEntity;
import com.entity.ProductEntity;
import com.entity.ProductScreensEntity;
import com.entity.ProductTypeCategoryEntity;
import com.entity.ProductTypeEntity;
import com.entity.SubCategoryEntity;
import com.entity.UserEntity;
import com.entity.UserProductTransacationDetailEntity;
import com.entity.UserTransactionHistoryEntity;
import com.entity.WalletEntity;
import com.entity.WalletHistoryEntity;
import com.exception.DataNotExistException;
import com.exception.InsertUserException;
import com.repository.ProductRepository;
import com.repository.ProductTypeCategoryRepository;
import com.repository.UserEntityRepository;
import com.repository.UserProductTrsactionDetailRepository;
import com.repository.UserTransactionHistoryRepository;
import com.repository.WalletEntityRepository;
import com.repository.WalletHistoryEntityRepository;
import com.service.IEmailService;
import com.service.IProductService;
import com.service.ISmsService;
import com.utility.ApplicationConstants;
import com.utility.GeneralUtil;

@Service
public class ProductServiceImpl implements IProductService {
	
	private static final Logger LOGGER = LogManager.getLogger(RestProductController.class);
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductTypeCategoryRepository productTypeCategoryRepository;
	
	@Autowired
	private UserEntityRepository userEntityRepository;
//	@Autowired
//	private RoleEntityRepository roleEntityRepository;
	@Autowired
	private UserTransactionHistoryRepository userTransactionHistoryRepository;
	@Autowired
	private UserProductTrsactionDetailRepository userProductTrsactionDetailRepository;
	@Autowired
	private ISmsService smsService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private WalletEntityRepository walletEntityRepository;
	@Autowired
	private WalletHistoryEntityRepository walletHistoryEntityRepository;
	@Autowired
	private IEmailService emailService;
	
	@Transactional(timeout = 100000, value = "transactionManager", readOnly=true)
	@Override
	public List<ProductDto> getProductListBySubCategory(Integer subCategoryId) {
		List<ProductDto> productDtoList = null;
		List<ProductEntity> productEntityList = productRepository.findAllBySubCatgoryIdAndActiveAndProductIsNullAndStockIsNotNullOrderByTitleDesc(subCategoryId, true);
		if(productEntityList != null && !productEntityList.isEmpty()){
			productDtoList = new ArrayList<>();
			for (ProductEntity productEntity : productEntityList) {
				ProductDto productDto = new ProductDto();
				productEntityToProductDto(productEntity, productDto, true, true, true, true, false, false, false);
				productDtoList.add(productDto);
			}
		}
		return productDtoList;
	}

	@Transactional(timeout = 100000, value = "transactionManager",  readOnly=true)
	@Override
	public List<ProductDto> getProductListByCategory(Integer categoryId) {
		List<ProductDto> productDtoList = null;
		List<ProductEntity> productEntityList = productRepository.findAllByCatgoryIdAndActiveAndProductIsNullAndStockIsNotNullOrderByTitleDesc(categoryId, true);
		if(productEntityList != null && !productEntityList.isEmpty()){
			productDtoList = new ArrayList<>();
			for (ProductEntity productEntity : productEntityList) {
				ProductDto productDto = new ProductDto();
				productEntityToProductDto(productEntity, productDto, true, true, true, true, false, false, false);
				productDtoList.add(productDto);
			}
		}
		return productDtoList;
	}
	@Override
	@Transactional(timeout = 100000, value = "transactionManager", readOnly=true)
	public ProductDto findWithDetailsById(int productId) {
		ProductEntity productEntity = productRepository.findOneByIdAndActive(productId, true);
		if(productEntity != null){
			ProductDto productDto = new ProductDto();
			productEntityToProductDto(productEntity, productDto, true, true, true, true, true, true, true);
			return productDto;
		}
		return null;
	}
	@Override
	@Transactional(timeout = 100000, value = "transactionManager", readOnly=true)
	public List<CategoryBrandProductDto> getproductWithTypeListBySubCategory(Integer subCategoryId) {
		List<CategoryBrandProductDto> categoryBrandProductDtoList = null;
		List<ProductTypeCategoryEntity> ProductTypeCategoryEntityList = productTypeCategoryRepository.getAllDistinctByCompositeKey_SubCategoryIdAndActive(subCategoryId, true);
		if(ProductTypeCategoryEntityList != null && !ProductTypeCategoryEntityList.isEmpty()){
			categoryBrandProductDtoList = new ArrayList<>();
			Map<String, Set<ProductDto>> map = new HashMap<>();
			for (ProductTypeCategoryEntity productTypeCategoryEntity : ProductTypeCategoryEntityList) {
				try{
					Set<ProductDto> productHashSet;
					ProductTypeEntity productType = productTypeCategoryEntity.getProductType();
					SubCategoryEntity subCategory = productTypeCategoryEntity.getSubCategory();
					ProductEntity productEntity = productTypeCategoryEntity.getProduct();
					if(productEntity != null && productEntity.getStock() != null && productEntity.isActive() && productType != null && subCategory.isActive()){
						ProductDto productDto = new ProductDto();
						productEntityToProductDto(productEntity, productDto, false, false, false, false, false, false, false);
						
						if (map.get(productType.getName()) == null) { 
							productHashSet = new HashSet<>();
		                } else {  
		                	productHashSet = map.get(productType.getName());  
		                }  
						productHashSet.add(productDto);
	                    map.put(productType.getName(), productHashSet);
					}
				}catch(Exception e){
					LOGGER.info("error in productTypeCategoryEntity"+ productTypeCategoryEntity.toString()+", erro details:- "+e);
				}
			}
			
			Set<Entry<String, Set<ProductDto>>> entrySet = map.entrySet();
			if(entrySet != null && !entrySet.isEmpty()){
				for (Entry<String, Set<ProductDto>> entry : entrySet) {
					CategoryBrandProductDto categoryBrandProductDto = new CategoryBrandProductDto();
					categoryBrandProductDto.setType(entry.getKey()!=null?entry.getKey():"All");
					categoryBrandProductDto.setProductList(entry.getValue());
					if(!categoryBrandProductDtoList.contains(categoryBrandProductDto)){
						categoryBrandProductDtoList.add(categoryBrandProductDto);
					}
				}
			}
			
		}
		
		
		/*if(ProductTypeCategoryEntityList != null && !ProductTypeCategoryEntityList.isEmpty()){
			categoryBrandProductDtoList = new ArrayList<>();
			for (ProductTypeCategoryEntity productTypeCategoryEntity : ProductTypeCategoryEntityList) {
				try{
					ProductTypeEntity productType = productTypeCategoryEntity.getProductType();
					SubCategoryEntity subCategory = productTypeCategoryEntity.getSubCategory();
					if( productType != null && subCategory.isActive()){
						List<ProductEntity> productEntityList = productRepository.findAllBySubcategoryAndProductTypeAndActiveAndStockNotNullOrderByIdDesc(subCategory, productType, true);
						if(productEntityList != null && !productEntityList.isEmpty()){
							List<ProductDto> productDtoList = new ArrayList<>();
							for (ProductEntity productEntity : productEntityList) {
								if(productEntity.isActive() && productEntity.getStock() != null){
									ProductDto productDto = new ProductDto();
									productEntityToProductDto(productEntity, productDto, false, false, false, false, false, false);
									productDtoList.add(productDto);
								}
								
							}
							CategoryBrandProductDto categoryBrandProductDto = new CategoryBrandProductDto();
							categoryBrandProductDto.setType(productType.getName());
							categoryBrandProductDto.setProductList(productDtoList);
							if(!categoryBrandProductDtoList.contains(categoryBrandProductDto)){
								categoryBrandProductDtoList.add(categoryBrandProductDto);
							}
						}
					}
				}catch(Exception e){
					LOGGER.info("error in productTypeCategoryEntity"+ productTypeCategoryEntity.toString()+", erro details:- "+e);
				}
			}
		}*/
		return categoryBrandProductDtoList;
	}

	private void productEntityToProductDto(ProductEntity productEntity, ProductDto productDto,  boolean isProductTypeRequired, boolean isCategoryRequired, boolean isSubCategoryRequired,
			boolean isBrandRequired, boolean isImagesRequired, boolean isVarientImageRequired, boolean isVarientRequired) {
		BeanUtils.copyProperties(productEntity, productDto);
		if(isCategoryRequired){
			CategoryEntity categoryEntity = productEntity.getCategory();
			if(categoryEntity != null){
				NameIdDto categoryNameIdDto = new NameIdDto();
				categoryNameIdDto.setId(categoryEntity.getId());
				categoryNameIdDto.setName(categoryEntity.getName());
				productDto.setCategory(categoryNameIdDto);
			}
		}
		
		if(isProductTypeRequired){
			ProductTypeEntity productType = productEntity.getProductType();
			if(productType != null){
				NameIdDto productTypeNameIdDto = new NameIdDto();
				productTypeNameIdDto.setId(productType.getId());
				productTypeNameIdDto.setName(productType.getName());
				productDto.setProductType(productTypeNameIdDto);
			}
		}
		
		
		if(isSubCategoryRequired){
			SubCategoryEntity subCategoryEntity = productEntity.getSubcategory();
			if(subCategoryEntity != null){
				NameIdDto subCategoryNameIdDto = new NameIdDto();
				subCategoryNameIdDto.setId(subCategoryEntity.getId());
				subCategoryNameIdDto.setName(subCategoryEntity.getName());
				productDto.setSubcategory(subCategoryNameIdDto);
			}
		}
		
		if(isBrandRequired){
			BrandEntity brandEntity = productEntity.getBrand();
			if(brandEntity != null){
				NameIdDto brandNameIdDto = new NameIdDto();
				brandNameIdDto.setId(brandEntity.getId());
				brandNameIdDto.setName(brandEntity.getName());
				productDto.setBrand(brandNameIdDto);
			}
		}
		
		if(isImagesRequired && ((productEntity.getProduct()==null) || ((productEntity.getProduct() != null) && isVarientImageRequired))){
			List<ProductScreensEntity> screens = productEntity.getScreens();
			if(screens != null && !screens.isEmpty()){
				ArrayList<String> imageList = new ArrayList<>();
				for (ProductScreensEntity productScreensEntity : screens) {
					if(productScreensEntity.isActive()){
						imageList.add(productScreensEntity.getPath());
					}
				}
				productDto.setImages(imageList);
			}
		}
		
		if(productEntity.getStock()==null){
			productDto.setStock(ApplicationConstants.OUT_OF_STOCK);
			productDto.setPrice(null);
			productDto.setOldPrice(null);
		}
		
		if(isVarientRequired){
			List<ProductEntity> productVarientsEntityList = productEntity.getProductVarients();
			if(productVarientsEntityList != null && !productVarientsEntityList.isEmpty()){
				List<ProductDto> productVarientDtoList = new ArrayList<>();
				for (ProductEntity productVarientEntity : productVarientsEntityList) {
					if(productVarientEntity.isActive() && productVarientEntity != null){
						ProductDto productVarientDto = new ProductDto();
						productEntityToProductDto(productVarientEntity, productVarientDto, isProductTypeRequired, isCategoryRequired,  isSubCategoryRequired, isBrandRequired,  isImagesRequired,  isVarientImageRequired,  isVarientRequired);
						productVarientDtoList.add(productVarientDto);
					}
				}
				productDto.setProductVarientDtoList(productVarientDtoList);
			}
		}
	}
	
	/*private UserEntity userDtoToEntity(UserDto dto, UserEntity entity) {
	    if(entity==null){
	      entity=new UserEntity();
	    }
	    if(dto.getId() !=null&&dto.getId() != 0){
	        entity.setId(dto.getId());
	        entity.setRole(roleEntityRepository.findOne(ApplicationConstants.Roles.USER));
	    }
	    if(dto.getPassword() != null){
	        entity.setPassword(PasswordUtility.encodePassword(dto.getPassword()!=null?dto.getPassword():"user"));
	    }
	    if(dto.getEmail() != null){
	    	entity.setName(dto.getName());
	    }
	    if(dto.getEmail() != null){
	    	entity.setUsername(dto.getEmail());
	    }
	    entity.setValid(true);
	    entity.setDeleteUser(true);
	    entity.setAddress(dto.getAddress());
	    entity.setMobile(dto.getMobile());
	    return entity;
	  }*/

	@Override
	@Transactional(timeout = 100000, value = "transactionManager")
	public boolean checkoutCart(CartCheckoutDto checkoutCart) {
		 /*UserEntity userEntityByEmail =userEntityRepository.findByUsername(checkoutCart.getMobile());
		 if(userEntityByEmail == null && checkoutCart.getEmailId() != null && !checkoutCart.getEmailId().isEmpty()){
			 userEntityByEmail =userEntityRepository.findByUsername(checkoutCart.getEmailId());
		 }
		 UserDto userDto = new UserDto();
		 userDto.setAddress(checkoutCart.getAddress());
		 userDto.setMobile(checkoutCart.getMobile());
		 if(userEntityByEmail.getUsername() == null){
			 userDto.setEmail(checkoutCart.getEmailId());
		 }
		 userDto.setLoginInfo("BY_CHECKOUT");
		 userDto.setName(checkoutCart.getName());
		 UserEntity userEntity = userDtoToEntity(userDto, new UserEntity());
		 userEntityRepository.save(userEntity);
		List<PurchasedItemDto> items = checkoutCart.getItems();
		for (PurchasedItemDto purchasedItemDto : items) {
			ProductEntity productEntity= productRepository.findOne(purchasedItemDto.getId());
		}*/
		
		/*	ruuning 
		 * try{
		entity = userEntityRepository.findByUsername(cart.getMobileNum());
		if(entity == null && cart.getEmailId() != null && !cart.getEmailId().isEmpty()){
			entity =userEntityRepository.findByUsername(cart.getEmailId());
		}
		if(entity==null){
			entity=new UserEntity();
			entity.setLoginInfo("BY_CHECKOUT");
			entity.setPassword(PasswordUtility.encodePassword("user"));
			if(cart.getEmailId() != null && entity.getUsername()==null){
				entity.setUsername(cart.getEmailId());
			}else{
				entity.setUsername(cart.getMobileNum());
			}
		}
		if(cart.getMobileNum() != null && entity.getMobile()==null){
			entity.setMobile(cart.getMobileNum());
		}
		if(entity.getRole() == null){
			entity.setRole(roleEntityRepository.findOne(ApplicationConstants.Roles.USER));
		}
		if(entity.getName() == null){
			entity.setName(cart.getName()!= null?cart.getName():"User");
		}
		entity.setDeleteUser(false);
		entity.setValid(true);
		entity.setClient(clientRepository.findOne(1));
		entity.setWorkspace(workspaceRepositiry.findOne(1));
		entity.setAddress(cart.getAddress());
		userEntityRepository.save(entity);
	}catch(Exception e){
		throw new InsertUserException();
	}*/
		return false;
	}

	@Override
	@Transactional(value = "transactionManager")
	public boolean checkoutCart(CurrentUserDto currentUserDto, final CheckoutDto cart, List<FieldErrorDTO> fieldErrorDTOs) throws InsertUserException, DataNotExistException {
		UserEntity userEntity = userEntityRepository.findOne(currentUserDto.getId());
		if(userEntity == null){
			throw new DataNotExistException("User not found. Please login again");
		}
		fieldErrorDTOs = validateCheckoutData(userEntity, cart, fieldErrorDTOs);
		if(fieldErrorDTOs != null && !fieldErrorDTOs.isEmpty() && fieldErrorDTOs.size() >0){
			return false;
		}
		
		double cashbackAmount = 0;
		ArrayList<UserProductTransacationDetailEntity> userProductTransacationDetailEntityList =  new ArrayList<>();;
		List<PurchasedItemDto> items = cart.getData();
		if(items != null && !items.isEmpty()){
			for (int i=0; i< items.size(); i++) {
				PurchasedItemDto purchasedItemDto =items.get(i);
				ProductEntity productEntity= productRepository.findOne(purchasedItemDto.getId());
				if(productEntity != null && productEntity.isActive() && productEntity.getStock() != null){
					UserProductTransacationDetailEntity userProductTrsactionDetailEntity = new UserProductTransacationDetailEntity();
					userProductTrsactionDetailEntity.setPrice(purchasedItemDto.getPrice());
					userProductTrsactionDetailEntity.setTotal(purchasedItemDto.getTotal());
					userProductTrsactionDetailEntity.setProductId(purchasedItemDto.getId());
					userProductTrsactionDetailEntity.setProductName(productEntity.getTitle());
					userProductTrsactionDetailEntity.setQuanity(purchasedItemDto.getQty());	
					userProductTrsactionDetailEntity.setStatus("active");
					userProductTrsactionDetailEntity.setUserId(userEntity.getId());
					userProductTrsactionDetailRepository.save(userProductTrsactionDetailEntity);
					userProductTransacationDetailEntityList.add(userProductTrsactionDetailEntity);
					int stock = Integer.parseInt(productEntity.getStock())-purchasedItemDto.getQty();
					productEntity.setStock(stock >0?stock+"":null);
					productRepository.save(productEntity);
					cashbackAmount = cashbackAmount + (productEntity.getDiscountUpto() * purchasedItemDto.getQty());
				}
			}
		}
		if(userProductTransacationDetailEntityList != null && !userProductTransacationDetailEntityList.isEmpty()){
			UserTransactionHistoryEntity userTransactionHistoryEntity = new UserTransactionHistoryEntity();
			userTransactionHistoryEntity.setAmount(cart.getAmount());
			userTransactionHistoryEntity.setBillAddress(cart.getAddress() != null ?cart.getAddress() : userEntity.getAddress());
			userTransactionHistoryEntity.setBillMobile(cart.getMobileNum() != null?cart.getMobileNum() : userEntity.getMobile());
			userTransactionHistoryEntity.setBillName(cart.getName() != null ?cart.getName() : userEntity.getName());
			userTransactionHistoryEntity.setBillEmail(cart.getEmailId() != null ?cart.getEmailId() : userEntity.getUsername());
			if(cart.getUsedWallet() != null && cart.getUsedWallet() >0){
				userTransactionHistoryEntity.setPayMode("Cash On Delivery + wallet used");
				userTransactionHistoryEntity.setUsedWallet(cart.getUsedWallet());
			}else{
				userTransactionHistoryEntity.setPayMode("Cash On Delivery");
			}
			userTransactionHistoryEntity.setCashbackAllowed(cashbackAmount);
			if(cart.getShipping() != null){
				userTransactionHistoryEntity.setShippingCost(cart.getShipping());
			}
			userTransactionHistoryEntity.setStatus("pending");
			userTransactionHistoryEntity.setUserId(userEntity.getId());
			userTransactionHistoryEntity.setUserProductTransacationDetailList(userProductTransacationDetailEntityList);
			userTransactionHistoryRepository.save(userTransactionHistoryEntity);
				
			final int orderTransactionId = userTransactionHistoryEntity.getId();
			// remove wallet amount
			 if(cart.getUsedWallet() != null && cart.getUsedWallet() >0 && userEntity.getWallet()!= null){
				 WalletEntity wallet = userEntity.getWallet();
				 wallet.setValid(true);
				 wallet.setAmount(wallet.getAmount()- cart.getUsedWallet());
				 wallet.setRemarks(cart.getUsedWallet()+" wallet used for order "+orderTransactionId);
				 walletEntityRepository.saveAndFlush(wallet);
				
				 WalletHistoryEntity walletHistoryEntity = new WalletHistoryEntity();
				 walletHistoryEntity.setAmount(cart.getUsedWallet());
				 walletHistoryEntity.setRemarks(cart.getUsedWallet()+" wallet used for order "+orderTransactionId);
				 walletHistoryEntity.setCredit(false);
				 walletHistoryEntity.setWallet(wallet);
				 walletHistoryEntity.setValid(true);
				 walletHistoryEntityRepository.save(walletHistoryEntity);
			 }
			 
			
			final String mobileNumber=cart.getMobileNum() != null?cart.getMobileNum() : userEntity.getMobile();
			if(userEntity.getReferenceCode() == null){
				String referralCode = null;
				 long countByReferenceCode = 1;
				 while(countByReferenceCode !=0){
					 referralCode =	GeneralUtil.generateReferralCode().toString();
					 countByReferenceCode = userEntityRepository.countByReferenceCode(referralCode);
				 }
				 userEntity.setReferenceCode(referralCode);
				 userEntityRepository.saveAndFlush(userEntity);
				 
				 
				 final String referralCode1 = referralCode;
				 Thread t1 = new Thread(new Runnable() {

		                @Override
		                public void run() {
		                	
		                    try {
		                        smsService.sendSMS(mobileNumber,"Hi. You can use referral code "+referralCode1+ " for extra benefits and sharing.");
		                    } catch (IOException e) {
		                        LOGGER.error(e);
		                    
		                    }
		                }
		            });
		           
		            t1.start();
			}
			
			try{
				getCashbackToReferral(orderTransactionId, userEntity.getReferenceBy(), cashbackAmount);
			}catch(Exception e){
				LOGGER.info("error in getCashbackToReferral:- " +e.getStackTrace());
				EmailDto emailDto = new EmailDto();
				emailDto.setTo(new String[]{"info@flyingbasket.in"});
				emailDto.setMailBody("error in getCashbackToReferral while getCashbackToReferral with orderTransactionId:-"+orderTransactionId+", referenceBy:-"+userEntity.getReferenceBy()+", cashbackAmountTotal:-"+cashbackAmount +",with cart data:-"+ cart+", with error details:-"+e.getStackTrace());
				emailDto.setSubject("error in flyingbasket");
				try {
					emailService.sendMail(emailDto);
				} catch (MessagingException e1) {
					 
					LOGGER.error(e.getMessage(), e1);
				}
			}
			
			Thread t = new Thread(new Runnable() {

                @Override
                public void run() {
                	try {
                        smsService.sendSMS(mobileNumber,"Thank you for using Flying Basket. Your order number "+orderTransactionId +" has been placed successfully.");
                    } catch (IOException e) {
                        LOGGER.error(e);
                    }
                	
                	EmailDto emailDto = new EmailDto();
    				emailDto.setTo(new String[]{"info@flyingbasket.in"});
    				emailDto.setBcc(new String[]{"saurabhaggarwal05@gmail.com"});
    				emailDto.setMailBody(" orderTransactionId:-"+orderTransactionId+", with cart:-"+cart);
    				emailDto.setSubject("order in flyingbasket with orderId:-"+orderTransactionId);
    				try {
    					emailService.sendMail(emailDto);
    				} catch (MessagingException e1) {
    					 
    					LOGGER.error(e1.getMessage(), e1);
    				}
                    
                }
            });
            t.start();
            return true;
		}
		return false;
	}
	@Transactional(value = "transactionManager")
	private List<FieldErrorDTO> validateCheckoutData(UserEntity userEntity, CheckoutDto cart, List<FieldErrorDTO> fieldErrorDTOs) {
		double totalCheckoutAmount = cart.getShipping();
		List<PurchasedItemDto> items = cart.getData();
		Double usedWallet = cart.getUsedWallet()!=null?cart.getUsedWallet():0;
		double userWallet =userEntity.getWallet()!=null?userEntity.getWallet().getAmount():0;
		
		if(usedWallet-userWallet >0){
			FieldErrorDTO error = new FieldErrorDTO("checkoutDto.usedWallet", messageSource.getMessage("usedWallet.notenough", null, Locale.ENGLISH));
			fieldErrorDTOs.add(error);
			return fieldErrorDTOs;
		}
		if(items != null && !items.isEmpty()){
			for (int i=0; i< items.size(); i++) {
				PurchasedItemDto purchasedItemDto =items.get(i);
				ProductEntity productEntity= productRepository.findOne(purchasedItemDto.getId());
				if(productEntity != null && productEntity.isActive() && productEntity.getStock() != null){
					if(productEntity.getPrice() - purchasedItemDto.getPrice() != 0){
						FieldErrorDTO error = new FieldErrorDTO("checkoutDto.data["+i+"].price", messageSource.getMessage("price.updated", null, Locale.ENGLISH));
						fieldErrorDTOs.add(error);
						return fieldErrorDTOs;
					}
					if(purchasedItemDto.getPrice()*purchasedItemDto.getQty() -purchasedItemDto.getTotal() != 0){
						FieldErrorDTO error = new FieldErrorDTO("checkoutDto.data["+i+"].price", messageSource.getMessage("totalprice.different", null, Locale.ENGLISH));
						FieldErrorDTO error1 = new FieldErrorDTO("checkoutDto.data["+i+"].total", messageSource.getMessage("totalprice.different", null, Locale.ENGLISH));
						fieldErrorDTOs.add(error);
						fieldErrorDTOs.add(error1);
						return fieldErrorDTOs;
					}
					if(!GeneralUtil.isParsable(productEntity.getStock())){
						FieldErrorDTO error = new FieldErrorDTO("checkoutDto.data["+i+"].qty", "stock not avaiable for "+productEntity.getTitle());
						fieldErrorDTOs.add(error);
						return fieldErrorDTOs;
					}
					int stock = Integer.parseInt(productEntity.getStock());
					if(stock - purchasedItemDto.getQty()<0){
						FieldErrorDTO error = new FieldErrorDTO("checkoutDto.data["+i+"].qty", "Only "+stock+" stock avaiable for "+productEntity.getTitle());
						fieldErrorDTOs.add(error);
						return fieldErrorDTOs;
					}
					totalCheckoutAmount = totalCheckoutAmount + purchasedItemDto.getTotal();
				}else{
					FieldErrorDTO error = new FieldErrorDTO("checkoutDto.data["+i+"].id", messageSource.getMessage("checkoutDto.product.unavaiable", null, Locale.ENGLISH));
					fieldErrorDTOs.add(error);
					return fieldErrorDTOs;
				}
			}
			totalCheckoutAmount = totalCheckoutAmount - usedWallet;
			if(totalCheckoutAmount - cart.getAmount() != 0){
				FieldErrorDTO error = new FieldErrorDTO("total", messageSource.getMessage("checkoutDto.total.incorrect", null, Locale.ENGLISH));
				fieldErrorDTOs.add(error);
				return fieldErrorDTOs;
			}
		}
		return fieldErrorDTOs;
	}

	@Transactional(value = "transactionManager")
	private void getCashbackToReferral(final int orderTransactionId,	final Integer referenceBy, final double cashbackAmountTotal) {
		if(referenceBy != null && referenceBy >0){
			int referenceBy1 =referenceBy;
			double cashbackAmountTotalShare =cashbackAmountTotal;
//			long countByReferenceBy = userEntityRepository.countByReferenceByAndReferralCodeIsNotNull(referenceBy1);
//			while(countByReferenceBy >= 10 && cashbackAmountTotalShare > 0.01){
			while( cashbackAmountTotalShare > 0.01){
             	UserEntity user = userEntityRepository.findById(referenceBy);
             	WalletEntity wallet2 = user.getWallet();
             	boolean isNewWallet = false;
             	if(wallet2 == null){
             		wallet2 = new WalletEntity();
             		wallet2.setValid(true);
             		wallet2.setAmount(500d);
             		wallet2.setRemarks("by welcome 500 offer and cashbackAmountTotal of"+cashbackAmountTotalShare);
             		isNewWallet = true;
             	}
             	wallet2.setAmount(wallet2.getAmount()+ cashbackAmountTotalShare);
             	wallet2.setUserEntity(user);
             	walletEntityRepository.save(wallet2);
             	
             	if(user.getMembershipType() == null){
             		isNewWallet = true;
             		user.setMembershipType(ApplicationConstants.MembershipType.LOYALITY);
             	}
             	user.setWallet(wallet2);
             	userEntityRepository.saveAndFlush(user);
             	
             	
//             	List<WalletHistoryEntity> walletHistoryList = wallet2.getWalletHistoryList();
//             	if(walletHistoryList == null || walletHistoryList.isEmpty()){
//             		walletHistoryList = new ArrayList<>();
//             	}
             	if(isNewWallet){
             		WalletHistoryEntity walletHistoryEntity = new WalletHistoryEntity();
             		walletHistoryEntity.setAmount(500d);
             		walletHistoryEntity.setRemarks("by welcome 500 offer");
             		walletHistoryEntity.setCredit(true);
             		walletHistoryEntity.setWallet(wallet2);
             		walletHistoryEntity.setValid(true);
             		walletHistoryEntityRepository.save(walletHistoryEntity);
//             		walletHistoryList.add(walletHistoryEntity);
             	}
             	WalletHistoryEntity walletHistoryEntity = new WalletHistoryEntity();
         		walletHistoryEntity.setAmount(cashbackAmountTotalShare);
         		walletHistoryEntity.setRemarks("by cashback of refferal");
         		walletHistoryEntity.setCredit(true);
         		walletHistoryEntity.setWallet(wallet2);
         		walletHistoryEntity.setValid(true);
         		walletHistoryEntityRepository.save(walletHistoryEntity);
//         		walletHistoryList.add(walletHistoryEntity);
//         		wallet2.setUserEntity(user);
//             	wallet2.setWalletHistoryList(walletHistoryList);
             	

             	
             	
             	cashbackAmountTotalShare = cashbackAmountTotalShare*0.05  ;
             	if(user.getReferenceBy() != null && user.getReferenceBy() !=0 && cashbackAmountTotalShare >0.01){
             		referenceBy1 = user.getReferenceBy();
//             		countByReferenceBy = userEntityRepository.countByReferenceByAndReferralCodeIsNotNull(referenceBy1);
             	}else{
//             		countByReferenceBy = 0;
             	}
             }
		}
	}

	@Transactional(timeout = 100000, value = "transactionManager", readOnly=true)
	@Override
	public List<ProductDto> allProducts(Integer pageNum, int maxresult) {
	    int offset = pageNum - 1;
	    Pageable pageable = new PageRequest(offset, maxresult);
	    List<ProductDto> productDtoList = new ArrayList<>();
		List<ProductEntity> productEntityList = productRepository.findAllByWorkspaceIdPageable(1, pageable);
		for (ProductEntity productEntity : productEntityList) {
			ProductDto productDto = new ProductDto();
			productEntityToProductDto(productEntity, productDto, true, true, true, true, true, false, false);
			productDtoList.add(productDto);
		}
		return productDtoList;
	}


	@Transactional(timeout = 100000, value = "transactionManager", readOnly=true)
	@Override
	public List<ProductDto> searchProduct(String query) {
		final int maxResult = 30;
		List<ProductDto> allProductList = new ArrayList<>();
		Set<ProductEntity> allProductEntitySet = new HashSet<ProductEntity>();
		allProductEntitySet.addAll(productRepository.findAllByTitleLikeAndStockIsNotNullOrderByTitleAsc( query + "%", new PageRequest(0, maxResult)));
		
		if(allProductEntitySet.size() < maxResult){
			allProductEntitySet.addAll(productRepository.findAllByTitleLikeAndStockIsNotNullOrderByTitleAsc("% " + query + "%", new PageRequest(0, maxResult - allProductEntitySet.size())));
		}
		if(allProductEntitySet.size() < maxResult){
			query.replaceAll("\\s+","%");
			allProductEntitySet.addAll(productRepository.findAllByTitleLikeAndStockIsNotNullOrderByTitleAsc("% " + query + "%", new PageRequest(0, maxResult - allProductEntitySet.size())));
		}
		
		if (allProductEntitySet != null) {
			for (ProductEntity productEntity : allProductEntitySet) {
				if(productEntity != null && productEntity.getStock() != null && productEntity.isActive()){
					ProductDto productDto = new ProductDto();
					productEntityToProductDto(productEntity, productDto, true, true, true, true, true, false, false);
					allProductList.add(productDto);
				}
			}
		}
		Collections.sort(allProductList);
		return allProductList;
	}

}
