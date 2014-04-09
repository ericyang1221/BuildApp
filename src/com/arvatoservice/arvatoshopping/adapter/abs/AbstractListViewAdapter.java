package com.arvatoservice.arvatoshopping.adapter.abs;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.BaseAdapter;

import com.arvatoservice.arvatoshopping.MyApplication;
import com.arvatoservice.arvatoshopping.beans.abs.DataBean;
import com.arvatoservice.arvatoshopping.utils.AnimFactory;
import com.arvatoservice.arvatoshopping.utils.Constants;

/**
 * 
 * @author Eric
 * 
 */
public abstract class AbstractListViewAdapter extends BaseAdapter {

	protected AlphaAnimation alphaAnimation;
	protected List<DataBean> liBeanList;
	protected Activity activity;
	protected boolean hasProgressBar = true;
	protected boolean reachEnd;
	protected int pageNum = 0;
	protected String[] para;
	private MyApplication myApp;
	
	public AbstractListViewAdapter(Activity activity,
			List<DataBean> liBeanList, String[] para)
	{
		this.activity = activity;
		this.liBeanList = liBeanList;
		this.para = para;
		alphaAnimation = AnimFactory.createAlphaAnimation(0.1f, 1.0f, 200f);
		reachEnd = getDefaultReachEnd();
		fetchNewData();
	}

	public void addData(List<DataBean> liBeanList)
	{
		if (liBeanList != null)
		{
			int resultSize = liBeanList.size();
			if (resultSize > 0)
			{
				this.liBeanList.remove(null);
				hasProgressBar = false;
				this.liBeanList.addAll(liBeanList);
				if (resultSize < Constants.PRODUCT_LIST_PAGE_SIZE)
				{
					reachEnd = true;
				}
			}
			else
			{
				this.liBeanList.remove(null);
				hasProgressBar = false;
				DataBean db = getEmptyDataBean();
				this.liBeanList.add(db);
				reachEnd = true;
			}
		}
		else
		{
			this.liBeanList.remove(null);
			hasProgressBar = false;
			DataBean db = getErrorDataBean();
			this.liBeanList.add(db);
			reachEnd = true;
		}
		notifyDataSetChanged();
	}

	public void clearData()
	{
		liBeanList.clear();
	}

	@Override
	public int getCount()
	{
		return liBeanList.size();
	}

	@Override
	public Object getItem(int arg0)
	{
		return null;
	}

	@Override
	public long getItemId(int arg0)
	{
		return 0;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup arg2)
	{
		if (pos == liBeanList.size() - 1 && !hasProgressBar && !reachEnd)
		{
			liBeanList.add(null);
			hasProgressBar = true;
			notifyDataSetChanged();
			fetchNewData();
		}
		// View v = null;
		// if (listItemMap.containsKey(pos))
		// {
		// v = listItemMap.get(pos);
		// }
		// else
		// {
		DataBean liBean = liBeanList.get(pos);
		// v = createItemView(activity, liBean, pos);
		return createItemView(convertView, liBean, pos);
		// }
		// return v;
	}

	public abstract View createItemView(View convertView, DataBean bean, int pos);

	public abstract void fetchNewData();

	public abstract DataBean getErrorDataBean();
	
	public abstract DataBean getEmptyDataBean();

	protected boolean getDefaultReachEnd()
	{
		return false;
	}

	public MyApplication getMyApplication()
	{
		if (myApp == null)
		{
			myApp = (MyApplication) activity.getApplication();
		}
		return myApp;
	}
}
