package com.arvatoservice.arvatoshopping.exceptions;

import android.util.Log;

/**
 * 
 * @author Eric
 * 
 */
public class JSONObjectCastException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msg;

	public JSONObjectCastException(String upLevelMsg, String msg)
	{
		if (upLevelMsg != null && !"".equals(upLevelMsg))
		{
			Log.e(this.getClass().getName(), upLevelMsg);
		}
		if (msg != null && !"".equals(msg))
		{
			Log.e(this.getClass().getName(), msg);
		}
		this.msg = msg;
	}

	@Override
	public String getMessage()
	{
		return msg;
	}

}
