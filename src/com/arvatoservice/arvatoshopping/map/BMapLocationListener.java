package com.arvatoservice.arvatoshopping.map;

import android.app.Activity;

import com.arvatoservice.arvatoshopping.BMapDialogActivity;
import com.arvatoservice.arvatoshopping.MyApplication;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

public class BMapLocationListener implements BDLocationListener {
	private Activity activity;

	public BMapLocationListener(Activity activity)
	{
		this.activity = activity;
	}

	@Override
	public void onReceiveLocation(BDLocation location)
	{
		if (location == null)
			return;
		MyApplication myApp = (MyApplication) activity.getApplication();
		myApp.setLastKnowLocation(location);
		if(activity instanceof BMapDialogActivity){
			((BMapDialogActivity)activity).doNav(location);
		}
	}

	public void onReceivePoi(BDLocation poiLocation)
	{
		if (poiLocation == null)
		{
			return;
		}
		StringBuffer sb = new StringBuffer(256);
		sb.append("Poi time : ");
		sb.append(poiLocation.getTime());
		sb.append("\nerror code : ");
		sb.append(poiLocation.getLocType());
		sb.append("\nlatitude : ");
		sb.append(poiLocation.getLatitude());
		sb.append("\nlontitude : ");
		sb.append(poiLocation.getLongitude());
		sb.append("\nradius : ");
		sb.append(poiLocation.getRadius());
		if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation)
		{
			sb.append("\naddr : ");
			sb.append(poiLocation.getAddrStr());
		}
		if (poiLocation.hasPoi())
		{
			sb.append("\nPoi:");
			sb.append(poiLocation.getPoi());
		}
		else
		{
			sb.append("noPoi information");
		}
	}
}
