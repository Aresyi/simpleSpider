package com.ydj.zhuaqu.ali1688;

/**  
 *
 * @author : Ares.yi
 * @createTime : 2014-11-10 上午11:13:42 
 * @version : 1.0 
 * @description : 
 *
 */
public class Ali1688Data {

	/**抓取的数据类型*/
	private int typeOf;
	
	/**公司名称*/
	private String company;
	
	/**店铺地址*/
	private String storeURL;
	
	/**主要经营*/
	private String mainProduct;
	
	/**地址*/
	private String address;
	
	/**生产类型*/
	private String bussModel;

	/**联系人*/
	private String contact="";
	
	/**联系电话*/
	private String tel="" ;
	
	public int getTypeOf() {
		return typeOf;
	}

	public void setTypeOf(int typeOf) {
		this.typeOf = typeOf;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getStoreURL() {
		return storeURL;
	}

	public void setStoreURL(String storeURL) {
		this.storeURL = storeURL;
	}

	public String getMainProduct() {
		return mainProduct;
	}

	public void setMainProduct(String mainProduct) {
		this.mainProduct = mainProduct;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBussModel() {
		return bussModel;
	}

	public void setBussModel(String bussModel) {
		this.bussModel = bussModel;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @param typeOf
	 * @param company
	 * @param storeURL
	 * @param mainProduct
	 * @param address
	 * @param bussModel
	 */
	public Ali1688Data(int typeOf, String company, String storeURL,
			String mainProduct, String address, String bussModel) {
		super();
		this.typeOf = typeOf;
		this.company = company;
		this.storeURL = storeURL;
		this.mainProduct = mainProduct;
		this.address = address;
		this.bussModel = bussModel;
	}

	/**
	 * 
	 */
	public Ali1688Data() {
	}
	
	
	
	
}
