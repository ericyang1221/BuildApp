package com.arvatoservice.arvatoshopping.beans;

import com.arvatoservice.arvatoshopping.beans.abs.DataBean;
import com.arvatoservice.arvatoshopping.utils.Constants;

public class FavoriteItemBean implements DataBean {
	private int id;
	private String name;
	private String desc;
	private String imagePath;
	private String arvatoUrl;
	private String mark;
	private int productId;
	private int couponId;
	private int storeId;
	private int sellerId;
	private long addTime;
	private int mode;
	private boolean isError = false;
	private boolean isEmpty = false;

	public FavoriteItemBean(int beanMode)
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

	public FavoriteItemBean(String name, String desc, String imagePath,
			String arvatoUrl, String mark, int mode, int productId,
			int couponId, int storeId, int sellerId, long addTime)
	{
		this.name = name;
		this.desc = desc;
		this.imagePath = imagePath;
		this.arvatoUrl = arvatoUrl;
		this.mark = mark;
		this.mode = mode;
		this.productId = productId;
		this.couponId = couponId;
		this.storeId = storeId;
		this.sellerId = sellerId;
		this.addTime = addTime;
	}

	public FavoriteItemBean(int id, String name, String desc, String imagePath,
			String arvatoUrl, String mark, int mode, int productId,
			int couponId, int storeId, int sellerId, long addTime)
	{
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.imagePath = imagePath;
		this.arvatoUrl = arvatoUrl;
		this.mark = mark;
		this.mode = mode;
		this.productId = productId;
		this.couponId = couponId;
		this.storeId = storeId;
		this.sellerId = sellerId;
		this.addTime = addTime;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public String getImagePath()
	{
		return imagePath;
	}

	public void setImagePath(String imagePath)
	{
		this.imagePath = imagePath;
	}

	public String getArvatoUrl()
	{
		return arvatoUrl;
	}

	public void setArvatoUrl(String arvatoUrl)
	{
		this.arvatoUrl = arvatoUrl;
	}

	public String getMark()
	{
		return mark;
	}

	public void setMark(String mark)
	{
		this.mark = mark;
	}

	public int getMode()
	{
		return mode;
	}

	public void setMode(int mode)
	{
		this.mode = mode;
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

	public int getProductId()
	{
		return productId;
	}

	public void setProductId(int productId)
	{
		this.productId = productId;
	}

	public int getCouponId()
	{
		return couponId;
	}

	public void setCouponId(int couponId)
	{
		this.couponId = couponId;
	}

	public int getStoreId()
	{
		return storeId;
	}

	public void setStoreId(int storeId)
	{
		this.storeId = storeId;
	}

	public int getSellerId()
	{
		return sellerId;
	}

	public void setSellerId(int sellerId)
	{
		this.sellerId = sellerId;
	}

	public long getAddTime()
	{
		return addTime;
	}

	public void setAddTime(long addTime)
	{
		this.addTime = addTime;
	}

}
