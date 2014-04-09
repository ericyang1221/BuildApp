package com.arvatoservice.arvatoshopping.map;

import android.app.Activity;

import com.arvatoservice.arvatoshopping.utils.Constants;
import com.arvatoservice.arvatoshopping.utils.Utils;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;

public class BMapSearchListener implements MKSearchListener {
	private Activity activity;
	private MapView mMapView;

	public BMapSearchListener(Activity activity, MapView mMapView)
	{
		this.activity = activity;
		this.mMapView = mMapView;
	}

	@Override
	public void onGetAddrResult(MKAddrInfo result, int iError)
	{
	}

	@Override
	public void onGetDrivingRouteResult(MKDrivingRouteResult result, int iError)
	{
		if (result == null)
		{
			return;
		}
		RouteOverlay routeOverlay = new RouteOverlay(activity, mMapView); // 此处仅展示一个方案作为示例
		routeOverlay.setData(result.getPlan(0).getRoute(0));
		mMapView.getOverlays().add(routeOverlay);
		mMapView.refresh();
		mMapView.getController().animateTo(result.getStart().pt);
		Utils.dismissDialogIfExist(activity,
				Constants.NAVIGATION_LOADING_DIALOG);
	}

	@Override
	public void onGetPoiResult(MKPoiResult result, int type, int iError)
	{
	}

	@Override
	public void onGetTransitRouteResult(MKTransitRouteResult result, int iError)
	{
	}

	@Override
	public void onGetWalkingRouteResult(MKWalkingRouteResult result, int iError)
	{
	}

	@Override
	public void onGetBusDetailResult(MKBusLineResult arg0, int arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetPoiDetailSearchResult(int arg0, int arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}
