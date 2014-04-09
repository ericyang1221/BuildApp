package com.arvatoservice.arvatoshopping.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.arvatoservice.arvatoshopping.BMapDialogActivity;
import com.arvatoservice.arvatoshopping.MyApplication;
import com.arvatoservice.arvatoshopping.R;
import com.arvatoservice.arvatoshopping.adapter.ShopAddressListAdapter;
import com.arvatoservice.arvatoshopping.beans.abs.DataBean;

public class ShopAddressListView extends ListView {
	private Activity activity;
	private MyApplication myApp;
	private UpdownViewPager uv;

	public ShopAddressListView(Context context, UpdownViewPager uv)
	{
		super(context);
		this.uv = uv;
		initLayout(context);
	}

	private void initLayout(Context context)
	{
		activity = (Activity) context;
		myApp = (MyApplication) activity.getApplication();
		this.setCacheColorHint(Color.TRANSPARENT);
		this.setDivider(null);
		this.setSelector(R.drawable.shop_address_list_selector);
		int sellerId = myApp.getShopId();
		init(sellerId);
	}

	private void init(int sellerId)
	{
		List<DataBean> saliBeanList = getData();

		final ShopAddressListAdapter adapter = new ShopAddressListAdapter(
				activity, saliBeanList,
				new String[] { String.valueOf(sellerId) });
		setAdapter(adapter);
		setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int pos,
					long arg3)
			{
				if (v.isEnabled())
				{
					Intent i = new Intent(activity, BMapDialogActivity.class);
					i.putExtra("shopAddressListItemBean",
							(Serializable) v.getTag());
					activity.startActivity(i);
				}
			}
		});
	}

	private List<DataBean> getData()
	{
		List<DataBean> liBeanList = new ArrayList<DataBean>();
		liBeanList.add(null);
		return liBeanList;
	}

	public void showToast(String message)
	{
		Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev)
	{
		switch (ev.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			setParentScrollAble(false);// 当手指触到listview的时候，让父ScrollView交出ontouch权限，也就是让父scrollview
										// 停住不能滚动
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			setParentScrollAble(true);// 当手指松开时，让父ScrollView重新拿到onTouch权限
			break;
		default:
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}

	/**
	 * 是否把滚动事件交给父scrollview
	 * 
	 * @param flag
	 */
	private void setParentScrollAble(boolean flag)
	{
		uv.requestDisallowInterceptTouchEvent(!flag);// 这里的parentScrollView就是listview外面的那个scrollview
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		System.out.println(ev.getAction());
		return super.onTouchEvent(ev);

	}
}
