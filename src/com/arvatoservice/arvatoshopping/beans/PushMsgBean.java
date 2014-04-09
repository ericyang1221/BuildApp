package com.arvatoservice.arvatoshopping.beans;

public class PushMsgBean {
	private int id;
	private int type;
	private long receiveTime;
	private String title;
	private String message;
	private int rid;
	private String imgUrl;

	public PushMsgBean()
	{
	}

	public PushMsgBean(int id, int type, long receiveTime, String title,
			String message, int rid, String imgUrl)
	{
		this.id = id;
		this.type = type;
		this.receiveTime = receiveTime;
		this.title = title;
		this.message = message;
		this.rid = rid;
		this.imgUrl = imgUrl;
	}

	public PushMsgBean(int type, long receiveTime, String title,
			String message, int rid, String imgUrl)
	{
		this.type = type;
		this.receiveTime = receiveTime;
		this.title = title;
		this.message = message;
		this.rid = rid;
		this.imgUrl = imgUrl;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public long getReceiveTime()
	{
		return receiveTime;
	}

	public void setReceiveTime(long receiveTime)
	{
		this.receiveTime = receiveTime;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public int getRid()
	{
		return rid;
	}

	public void setRid(int rid)
	{
		this.rid = rid;
	}

	public String getImgUrl()
	{
		return imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}
}
