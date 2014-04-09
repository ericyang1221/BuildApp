package com.arvatoservice.arvatoshopping.beans;

import java.io.Serializable;

/**
 * 
 * @author Eric
 * 
 */
public class ProductDetailImgBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String midImg;
	private String bigImg;

	public ProductDetailImgBean()
	{
	}

	public ProductDetailImgBean(String midImg, String bigImg)
	{
		this.midImg = midImg;
		this.bigImg = bigImg;
	}

	public String getMidImg()
	{
		return midImg;
	}

	public void setMidImg(String midImg)
	{
		this.midImg = midImg;
	}

	public String getBigImg()
	{
		return bigImg;
	}

	public void setBigImg(String bigImg)
	{
		this.bigImg = bigImg;
	}

}
