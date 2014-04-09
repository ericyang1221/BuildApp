package com.arvatoservice.arvatoshopping.utils;

import java.util.Comparator;

import android.util.Log;

import com.arvatoservice.arvatoshopping.beans.ShopAddressListItemBean;
import com.arvatoservice.arvatoshopping.beans.abs.DataBean;
import com.baidu.location.BDLocation;

public class DisAscComparator implements Comparator<DataBean> {
	private BDLocation myLocation;

	public DisAscComparator(BDLocation myLocation)
	{
		this.myLocation = myLocation;
	}

	@Override
	public int compare(DataBean lhs, DataBean rhs)
	{
		int ret = 0;
		ShopAddressListItemBean salib1 = (ShopAddressListItemBean) lhs;
		ShopAddressListItemBean salib2 = (ShopAddressListItemBean) rhs;
		float lat_a = (float) salib1.getLatitude();
		float lng_a = (float) salib1.getLongitude();
		float lat_b = (float) salib2.getLatitude();
		float lng_b = (float) salib2.getLongitude();

		if (countDis(lat_a, lng_a, lat_b, lng_b))
		{
			ret = 1;
		}
		else
		{
			ret = -1;
		}
		return ret;
	}

	/*
	 * ������false ��֮����true
	 */
	private boolean countDis(float lat_a, float lng_a, float lat_b, float lng_b)
	{
		boolean dis = false;
		float lat_c = (float) myLocation.getLatitude();
		float lng_c = (float) myLocation.getLongitude();

		double d1 = Utils.gps2m(lat_a, lng_a, lat_c, lng_c);
		double d2 = Utils.gps2m(lat_b, lng_b, lat_c, lng_c);
		if (d1 > d2)
		{
			dis = true;
		}
		Log.e("check", "d1:" + d1 + " d2:" + d2);
		return dis;

	}
}
