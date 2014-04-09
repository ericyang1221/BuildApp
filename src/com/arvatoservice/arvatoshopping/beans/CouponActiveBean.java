package com.arvatoservice.arvatoshopping.beans;

public class CouponActiveBean {
	private int couponId;
	private int sellerId;
	private long activeTime;
	private double timeRange;
	private String storeName;
	private String mark;

	public CouponActiveBean()
	{
	}

	public CouponActiveBean(int couponId, int sellerId, long activeTime,
			double timeRange, String storeName, String mark)
	{
		this.couponId = couponId;
		this.sellerId = sellerId;
		this.activeTime = activeTime;
		this.timeRange = timeRange;
		this.storeName = storeName;
		this.mark = mark;
	}

	public int getCouponId()
	{
		return couponId;
	}

	public void setCouponId(int couponId)
	{
		this.couponId = couponId;
	}

	public int getSellerId()
	{
		return sellerId;
	}

	public void setSellerId(int sellerId)
	{
		this.sellerId = sellerId;
	}

	public long getActiveTime()
	{
		return activeTime;
	}

	public void setActiveTime(long activeTime)
	{
		this.activeTime = activeTime;
	}

	public double getTimeRange()
	{
		return timeRange;
	}

	public void setTimeRange(double timeRange)
	{
		this.timeRange = timeRange;
	}

	public String getStoreName()
	{
		return storeName;
	}

	public void setStoreName(String storeName)
	{
		this.storeName = storeName;
	}

	public String getMark()
	{
		return mark;
	}

	public void setMark(String mark)
	{
		this.mark = mark;
	}

}
