package com.arvatoservice.arvatoshopping.view.abs;

import android.content.Context;
import android.widget.LinearLayout;

import com.arvatoservice.arvatoshopping.beans.abs.DataBean;

public abstract class BaseListItem extends LinearLayout {
	protected Context context;

	public BaseListItem(Context context)
	{
		super(context);
		this.context = context;
	}

	public abstract void setData(DataBean db);

	public abstract void setProgressBar();
}
