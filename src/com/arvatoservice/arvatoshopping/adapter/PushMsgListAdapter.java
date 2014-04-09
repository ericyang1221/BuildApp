package com.arvatoservice.arvatoshopping.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;

import com.arvatoservice.arvatoshopping.adapter.abs.AbstractListViewAdapter;
import com.arvatoservice.arvatoshopping.asynctask.PushMsgListItemBeanFetcher;
import com.arvatoservice.arvatoshopping.beans.PushMsgListItemBean;
import com.arvatoservice.arvatoshopping.beans.abs.DataBean;
import com.arvatoservice.arvatoshopping.utils.Constants;
import com.arvatoservice.arvatoshopping.view.PushMsgListItem;

/**
 * 
 * @author Eric
 * 
 */
public class PushMsgListAdapter extends AbstractListViewAdapter {

	public PushMsgListAdapter(Activity activity, List<DataBean> liBeanList,
			String[] para)
	{
		super(activity, liBeanList, para);
	}

	@Override
	public View createItemView(View convertView, DataBean bean, int pos)
	{
		PushMsgListItem pmli;
		PushMsgListItemBean pmliBean = (PushMsgListItemBean) bean;
		if (pmliBean != null)
		{
			if (convertView == null)
			{
				pmli = new PushMsgListItem(activity, pmliBean);
			}
			else
			{
				pmli = (PushMsgListItem) convertView;
				pmli.setData(pmliBean);
			}
		}
		else
		{
			if (convertView == null)
			{
				pmli = new PushMsgListItem(activity);
			}
			else
			{
				pmli = (PushMsgListItem) convertView;
				pmli.setProgressBar();
			}
		}
		// sali.startAnimation(alphaAnimation);
		return pmli;
	}

	@Override
	public void fetchNewData()
	{
		PushMsgListItemBeanFetcher pmlbf = new PushMsgListItemBeanFetcher();
		pmlbf.execute(this);
	}

	@Override
	public DataBean getErrorDataBean()
	{
		return new PushMsgListItemBean(Constants.MODE_ERROR_DATA_BENA);
	}

	@Override
	protected boolean getDefaultReachEnd()
	{
		return true;
	}

	@Override
	public DataBean getEmptyDataBean()
	{
		return new PushMsgListItemBean(Constants.MODE_EMPTY_DATA_BENA);
	}
}
