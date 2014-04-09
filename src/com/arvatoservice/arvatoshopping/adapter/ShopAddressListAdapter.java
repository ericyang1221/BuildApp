package com.arvatoservice.arvatoshopping.adapter;

import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.arvatoservice.arvatoshopping.adapter.abs.AbstractListViewAdapter;
import com.arvatoservice.arvatoshopping.asynctask.ShopAddressListItemBeanFetcher;
import com.arvatoservice.arvatoshopping.beans.ShopAddressListItemBean;
import com.arvatoservice.arvatoshopping.beans.abs.DataBean;
import com.arvatoservice.arvatoshopping.utils.Constants;
import com.arvatoservice.arvatoshopping.utils.DisAscComparator;
import com.arvatoservice.arvatoshopping.utils.Utils;
import com.arvatoservice.arvatoshopping.view.ShopAddressListItem;
import com.baidu.location.BDLocation;

/**
 * 
 * @author Eric
 * 
 */
public class ShopAddressListAdapter extends AbstractListViewAdapter {
	protected BDLocation myLocation;

	public ShopAddressListAdapter(Activity activity, List<DataBean> liBeanList,
			String[] para)
	{
		super(activity, liBeanList, para);
		myLocation = Utils.getLocation(activity);
	}

	@Override
	public View createItemView(View convertView, DataBean bean, int pos)
	{
		ShopAddressListItem sali;
		ShopAddressListItemBean saliBean = (ShopAddressListItemBean) bean;
		if (saliBean != null)
		{
			if (convertView == null)
			{
				sali = new ShopAddressListItem(activity, saliBean);
			}
			else
			{
				sali = (ShopAddressListItem) convertView;
				sali.setData(saliBean);
			}
		}
		else
		{
			if (convertView == null)
			{
				sali = new ShopAddressListItem(activity);
			}
			else
			{
				sali = (ShopAddressListItem) convertView;
				sali.setProgressBar();
			}
		}
		// sali.startAnimation(alphaAnimation);
		return sali;
	}

	@Override
	public void fetchNewData()
	{
		ShopAddressListItemBeanFetcher salbf = new ShopAddressListItemBeanFetcher(
				activity);
		salbf.execute(this, para);
	}

	@Override
	public DataBean getErrorDataBean()
	{
		return new ShopAddressListItemBean(Constants.MODE_ERROR_DATA_BENA);
	}

	@Override
	protected boolean getDefaultReachEnd()
	{
		return true;
	}

	@Override
	public void addData(List<DataBean> liBeanList)
	{
		Log.e("ShopAddressListAdapter", "" + liBeanList);
		// if(liBeanList == null || liBeanList.size() == 0){
		// super.addData(liBeanList);
		// return;
		// }

		List<DataBean> retLiBeanList = liBeanList;
		if (myLocation != null)
		{
			if(liBeanList!=null&&!liBeanList.isEmpty()){
				Collections.sort(retLiBeanList, new DisAscComparator(myLocation));
				retLiBeanList = setDisForEveryList(retLiBeanList);
			}
		}
		super.addData(retLiBeanList);
	}

	private List<DataBean> setDisForEveryList(List<DataBean> liBeanList)
	{
		if (liBeanList != null)
		{
			for (int i = 0; i < liBeanList.size(); i++)
			{
				ShopAddressListItemBean item = (ShopAddressListItemBean) liBeanList
						.get(i);
				double lat_a = item.getLatitude();
				double lng_a = item.getLongitude();
				double lat_c = myLocation.getLatitude();
				double lng_c = myLocation.getLongitude();
				Log.e("check", lat_a + "-" + lng_a + "-" + lat_c + "-" + lng_c);
				double d = Utils
						.getDistanceFromXtoY(lat_a, lng_a, lat_c, lng_c);
				item.setDistance(d);
			}
		}
		return liBeanList;
	}

	@Override
	public DataBean getEmptyDataBean()
	{
		return new ShopAddressListItemBean(Constants.MODE_EMPTY_DATA_BENA);
	}
}
