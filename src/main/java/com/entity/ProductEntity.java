package com.entity;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

 
@Entity
@Table(name = "Products")
public class ProductEntity extends WorkspaceBaseEntity{
 
 
    private int id;
    private String title;
    private String code;
    private String description;
    private CategoryEntity category;
    private SubCategoryEntity subcategory;
    private BrandEntity brand;
    private ProductTypeEntity productType;
    private boolean active;
    private String smallImagePath;
    private String largeImagePath; 
    private Date expDate;
    private Date mfdDate;
    private String size;
    private String offer;
    private Double oldPrice;
    private Double price;
    private Double buyCost;
    private Double discountUpto;
    private String stock;
	private List<ProductScreensEntity> screens= new ArrayList<>();
	private ProductEntity product;
	private List<ProductEntity> productVarients;
/*	private UserTransactionHistoryEntity transactionHistory;
*/    
	 public ProductEntity() {
		
	 }
	 
	 public ProductEntity(int id, String title) {
		 this.id = id;
		 this.title = title;
	 }
 
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 public int getId() {
		 return id;
	 }

	 public void setId(int id) {
		 this.id = id;
	 }
    
    @Column(name = "code", length = 20, nullable = false)
    public String getCode() {
        return code;
    }
 
    public void setCode(String code) {
        this.code = code;
    }
 
    
    @Column(name = "price", nullable = false, columnDefinition="Decimal(10,2) default '00.00'")
    public Double getPrice() {
        return price;
    }
 
    public void setPrice(Double price) {
        this.price = price;
    }
    
    @Column(name = "old_price", nullable = false, columnDefinition="Decimal(10,2) default '00.00'")
    public Double getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(Double oldPrice) {
		this.oldPrice = oldPrice;
	}
	@Column(name = "buy_cost", nullable = false, columnDefinition="Decimal(10,2) default '00.00'")
	public Double getBuyCost() {
		return buyCost;
	}

	public void setBuyCost(Double buyCost) {
		this.buyCost = buyCost;
	}
	@Column(name = "discount_upto", nullable = false, columnDefinition="Decimal(10,2) default '00.00'")
	public Double getDiscountUpto() {
		return discountUpto;
	}

	public void setDiscountUpto(Double discountUpto) {
		this.discountUpto = discountUpto;
	}
	
	@Column(name = "offer")
	public String getOffer() {
		return offer;
	}

	public void setOffer(String offer) {
		this.offer = offer;
	}

	@Column(name = "title", length = 255, nullable = false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name = "small_image_path", length = 255)
	public String getSmallImagePath() {
		return smallImagePath;
	}

	public void setSmallImagePath(String smallImagePath) {
		this.smallImagePath = smallImagePath;
	}
	@Column(name = "large_image_path", length = 255)
	public String getLargeImagePath() {
		return largeImagePath;
	}

	public void setLargeImagePath(String largeImagePath) {
		this.largeImagePath = largeImagePath;
	}
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", nullable = false)
	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "brand_id", nullable = false)
	public BrandEntity getBrand() {
		return brand;
	}

	public void setBrand(BrandEntity brand) {
		this.brand = brand;
	}
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expiry_date")
	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "mfg_date")
	public Date getMfdDate() {
		return mfdDate;
	}

	public void setMfdDate(Date mfdDate) {
		this.mfdDate = mfdDate;
	}
	@Column(name = "size")
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	@Column(name = "stock", length = 255)
	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	@OrderBy("id asc")
	public List<ProductScreensEntity> getScreens() {
		return screens;
	}

	public void setScreens(List<ProductScreensEntity> screens) {
		this.screens = screens;
	}
	
	
	
	@Column(name="is_active", columnDefinition="bit default 0", nullable=false)
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="product_id")
	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}
	@OneToMany(mappedBy="product", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	public List<ProductEntity> getProductVarients() {
		return productVarients;
	}

	public void setProductVarients(List<ProductEntity> productVarients) {
		this.productVarients = productVarients;
	}
	
	/*@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(
			name="Product__Transaction_History",
			joinColumns = @JoinColumn(name = "product_id"),
			inverseJoinColumns = @JoinColumn(name="bill_id")
	)
	public UserTransactionHistoryEntity getTransactionHistory() {
		return transactionHistory;
	}

	public void setTransactionHistory(UserTransactionHistoryEntity transactionHistory) {
		this.transactionHistory = transactionHistory;
	}*/

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subcategory_id", nullable = false)
	public SubCategoryEntity getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(SubCategoryEntity subcategory) {
		this.subcategory = subcategory;
	}
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_type_id", nullable = false)
	public ProductTypeEntity getProductType() {
		return productType;
	}

	public void setProductType(ProductTypeEntity productType) {
		this.productType = productType;
	}
}