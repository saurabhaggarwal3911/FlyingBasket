package com.service;

import java.util.List;

import com.dto.CartCheckoutDto;
import com.dto.CategoryBrandProductDto;
import com.dto.CheckoutDto;
import com.dto.CurrentUserDto;
import com.dto.FieldErrorDTO;
import com.dto.ProductDto;
import com.exception.DataNotExistException;
import com.exception.InsertUserException;

public interface IProductService {

	List<ProductDto> getProductListBySubCategory(Integer subCategoryId);

	List<ProductDto> getProductListByCategory(Integer categoryId);

	List<CategoryBrandProductDto> getproductWithTypeListBySubCategory(Integer subCategoryId);

	ProductDto findWithDetailsById(int productId);

	boolean checkoutCart(CartCheckoutDto checkoutCart);

	boolean checkoutCart(CurrentUserDto currentUserDto, CheckoutDto cart, List<FieldErrorDTO> fieldErrorDTOs) throws InsertUserException, DataNotExistException;

	List<ProductDto> allProducts(Integer pageNum, int maxresult);

	List<ProductDto> searchProduct(String query);
	
}
