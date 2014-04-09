package com.arvatoservice.arvatoshopping.beans.abs;

/**
 * 
 * @author Eric
 * 
 */
public abstract class AbstractHttpResponseBean implements HttpResponseBean {
	protected int ret = -100;
	protected String msg = "数据异常";

	public int getRet()
	{
		return ret;
	}

	public void setRet(int ret)
	{
		this.ret = ret;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

}
