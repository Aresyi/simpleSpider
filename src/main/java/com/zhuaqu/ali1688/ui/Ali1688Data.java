package com.zhuaqu.ali1688.ui;

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
	int typeOf;
	
	/**公司名称*/
	String company;
	
	/**店铺地址*/
	String storeURL;
	
	/**主要经营*/
	String mainProduct;
	
	/**地址*/
	String address;
	
	/**生产类型*/
	String bussModel;

	/**联系人*/
	String contact="";
	
	/**联系电话*/
	String tel="" ;
	
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
