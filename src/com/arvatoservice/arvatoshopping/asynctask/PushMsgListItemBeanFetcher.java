package com.arvatoservice.arvatoshopping.asynctask;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

import com.arvatoservice.arvatoshopping.adapter.abs.AbstractListViewAdapter;
import com.arvatoservice.arvatoshopping.beans.PushMsgBean;
import com.arvatoservice.arvatoshopping.beans.PushMsgListItemBean;
import com.arvatoservice.arvatoshopping.beans.abs.DataBean;
import com.arvatoservice.arvatoshopping.db.PushMsgDBHelper;

/**
 * 
 * @author Eric
 * 
 */
public class PushMsgListItemBeanFetcher extends AsyncTask<Object, Void, Object> {
	protected AbstractListViewAdapter lvAdapter;

	@Override
	protected Object doInBackground(Object... arg)
	{
		lvAdapter = (AbstractListViewAdapter) arg[0];
		PushMsgDBHelper pmdbh = lvAdapter.getMyApplication()
				.getPushMsgDBHelper();
		List<PushMsgBean> shibList = pmdbh.selectAll();
		List<PushMsgListItemBean> pmlibList = new ArrayList<PushMsgListItemBean>();
		for (PushMsgBean b : shibList)
		{
			pmlibList.add(new PushMsgListItemBean(b));
		}
		return pmlibList;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onPostExecute(Object result)
	{
		List<DataBean> liBeanList = (List<DataBean>) result;
		lvAdapter.addData(liBeanList);
		super.onPostExecute(result);
	}
}