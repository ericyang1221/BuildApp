package com.arvatoservice.arvatoshopping.asynctask.abs;

import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;

import com.arvatoservice.arvatoshopping.adapter.abs.AbstractListViewAdapter;
import com.arvatoservice.arvatoshopping.beans.abs.DataBean;
import com.arvatoservice.arvatoshopping.net.HttpRequestHelper;

/**
 * 
 * @author Eric
 * 
 */
public abstract class AbstractListItemBeanFetcher extends
		AsyncTask<Object, Void, Object> {
	protected AbstractListViewAdapter lvAdapter;
	protected Activity activity;

	public AbstractListItemBeanFetcher(Activity activity)
	{
		this.activity = activity;
	}

	protected abstract String getUrl(Object para);

	protected abstract int getType();

	protected abstract List<DataBean> getContentList(Object response);

	@Override
	protected Object doInBackground(Object... arg)
	{
		lvAdapter = (AbstractListViewAdapter) arg[0];
		HttpRequestHelper hth = new HttpRequestHelper(activity);
		JSONObject jsonObj = hth.sendRequestAndReturnJson(getUrl(arg[1]));
		return getContentList(jsonObj);
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
