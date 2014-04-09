package com.arvatoservice.arvatoshopping.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.arvatoservice.arvatoshopping.MyApplication;
import com.arvatoservice.arvatoshopping.R;
import com.arvatoservice.arvatoshopping.adapter.CouponChartAdapter;
import com.arvatoservice.arvatoshopping.beans.abs.DataBean;
import com.arvatoservice.arvatoshopping.utils.Constants;

public class CouponListView extends RelativeLayout {
	private Activity activity;
	private MyApplication myApp;
	private int activeType = Constants.NO_NEED_ACTIVE;

	public CouponListView(Context context, int sellerId)
	{
		super(context);
		initLayout(context, sellerId);
	}

	private void initLayout(Context context, int sellerId)
	{
		LayoutInflater.from(context).inflate(R.layout.coupon_chart, this, true);
		activity = (Activity) context;
		myApp = (MyApplication) activity.getApplication();
		Log.d("TRACK", sellerId + "");
		init(sellerId);
	}

	protected void init(int sellerId)
	{
		List<DataBean> pliBeanList = getData();
		ListView productChartLv = (ListView) findViewById(R.id.coupon_chart_list_view);
		ListAdapter adapter = getAdapter(pliBeanList,
				new String[] { String.valueOf(sellerId) });
		productChartLv.setAdapter(adapter);
	}

	private List<DataBean> getData()
	{
		List<DataBean> liBeanList = new ArrayList<DataBean>();
		liBeanList.add(null);
		return liBeanList;
	}

	protected ListAdapter getAdapter(List<DataBean> pliBeanList, String[] para)
	{
		return new CouponChartAdapter(activity, pliBeanList, para, activeType);
	}

	protected int getActivityTypeId()
	{
		return Constants.MENU_COUPON;
	}

	public void showToast(String message)
	{
		Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
	}
}
