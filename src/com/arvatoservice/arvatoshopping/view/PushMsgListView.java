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

import com.arvatoservice.arvatoshopping.MyApplication;
import com.arvatoservice.arvatoshopping.ProductDetailActivity;
import com.arvatoservice.arvatoshopping.R;
import com.arvatoservice.arvatoshopping.adapter.PushMsgListAdapter;
import com.arvatoservice.arvatoshopping.asynctask.ProductDetailBeanFetcher;
import com.arvatoservice.arvatoshopping.beans.ProductDetailBean;
import com.arvatoservice.arvatoshopping.beans.PushMsgListItemBean;
import com.arvatoservice.arvatoshopping.beans.abs.DataBean;
import com.arvatoservice.arvatoshopping.beans.abs.HttpResponseBean;
import com.arvatoservice.arvatoshopping.utils.Constants;
import com.arvatoservice.arvatoshopping.utils.Utils;
import com.arvatoservice.arvatoshopping.view.abs.SetableView;

public class PushMsgListView extends ListView implements SetableView {
	private Activity activity;
	private MyApplication myApp;
	private UpdownViewPager uv;

	public PushMsgListView(Context context, UpdownViewPager uv)
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
		this.setVerticalScrollBarEnabled(false);
		init();
	}

	private void init()
	{
		List<DataBean> pmliBeanList = getData();
		final PushMsgListAdapter adapter = new PushMsgListAdapter(activity,
				pmliBeanList, null);
		setAdapter(adapter);
		setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int pos,
					long arg3)
			{
				if (v.isEnabled())
				{
					PushMsgListItemBean pmlib = (PushMsgListItemBean) v
							.getTag();
					int id = pmlib.getRid();
					ProductDetailBeanFetcher pdbf = new ProductDetailBeanFetcher(
							activity, id);
					pdbf.execute(PushMsgListView.this,
							new String[] { String.valueOf(id), null });
					activity.showDialog(Constants.LOADING_DIALOG);
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

	@Override
	public void setData(HttpResponseBean hrb)
	{
		Utils.dismissDialogIfExist(activity, Constants.LOADING_DIALOG);
		ProductDetailBean pdb = (ProductDetailBean) hrb;
		if (pdb.getRet() == 0)
		{
			Intent intent = new Intent(activity, ProductDetailActivity.class);
			intent.putExtra("productDetailBean", (Serializable) pdb);
			activity.startActivity(intent);
		}
		else
		{
			Toast.makeText(activity, pdb.getMsg(), Toast.LENGTH_SHORT).show();
		}
	}
}
