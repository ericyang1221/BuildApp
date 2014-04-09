package com.arvatoservice.arvatoshopping.beans;

import java.io.Serializable;

import com.arvatoservice.arvatoshopping.beans.abs.DataBean;
import com.arvatoservice.arvatoshopping.utils.Constants;

public class PushMsgListItemBean extends PushMsgBean implements DataBean,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isError = false;
	private boolean isEmpty = false;

	public PushMsgListItemBean(int beanMode)
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
	};

	public PushMsgListItemBean(PushMsgBean pmb)
	{
		super(pmb.getId(), pmb.getType(), pmb.getReceiveTime(), pmb.getTitle(),
				pmb.getMessage(), pmb.getRid(), pmb.getImgUrl());
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
