package com.arvatoservice.arvatoshopping.beans;

import java.io.Serializable;

import com.arvatoservice.arvatoshopping.beans.abs.DataBean;
import com.arvatoservice.arvatoshopping.beans.abs.LinkedNodeBean;
import com.arvatoservice.arvatoshopping.utils.Constants;

/**
 * 
 * @author Eric
 * 
 */
public class CouponBean extends LinkedNodeBean implements DataBean,
		Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int sellerId;
	private String sellerName;
	private String desc;
	private String img;
	private int activeType;
	private boolean isError = false;
	private boolean isEmpty = false;

	public CouponBean(int beanMode)
	{
		switch (beanMode)
		{
		case Constants.MODE_EMPTY_DATA_BENA:
			this.isEmpty = true;
			break;
		case Constants.MODE_ERROR_DATA_BENA:
			this.isError = true;
			break;
		}
	}

	public CouponBean(int id, int sellerId, String sellerName, String desc,
			String img, int activeType)
	{
		this.id = id;
		this.sellerId = sellerId;
		this.sellerName = sellerName;
		this.desc = desc;
		this.img = img;
		this.activeType = activeType;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public String getImg()
	{
		return img;
	}

	public void setImg(String img)
	{
		this.img = img;
	}

	public int getSellerId()
	{
		return sellerId;
	}

	public void setSellerId(int sellerId)
	{
		this.sellerId = sellerId;
	}

	public String getSellerName()
	{
		return sellerName;
	}

	public void setSellerName(String sellerName)
	{
		this.sellerName = sellerName;
	}

	public int getActiveType()
	{
		return activeType;
	}

	public void setActiveType(int activeType)
	{
		this.activeType = activeType;
	}

	@Override
	public boolean isError()
	{
		return isError;
	}

	@Override
	public boolean isEmpty()
	{
		return isEmpty;
	}

}
