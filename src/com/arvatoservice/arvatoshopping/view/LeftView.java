package com.arvatoservice.arvatoshopping.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.arvatoservice.arvatoshopping.MyApplication;
import com.arvatoservice.arvatoshopping.R;
import com.arvatoservice.arvatoshopping.beans.abs.HttpResponseBean;
import com.arvatoservice.arvatoshopping.utils.Utils;
import com.arvatoservice.arvatoshopping.view.abs.MovableView;

public class LeftView extends LinearLayout implements MovableView {
	private MiddlePagerView mv;
	private LinearLayout container;
	private Context context;
	private MyApplication myApp;

	public LeftView(MiddlePagerView mv)
	{
		super(mv.getContext());
		this.mv = mv;
		this.context = mv.getContext();
		this.myApp = (MyApplication) ((Activity) context).getApplication();
		init(mv.getContext());
	}

	private void init(Context context)
	{
		LayoutInflater.from(context).inflate(R.layout.left_view, this, true);
		TitleView lt = (TitleView) findViewById(R.id.l_title);
		lt.setLeftImg(R.drawable.left_view_left_title_img);
		lt.setLeftTitle(myApp.getLeftViewLeftTitle());
		container = (LinearLayout) findViewById(R.id.l_container);
		findViewById(R.id.l_right).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0)
			{
				mv.setCurrentItem(1, true);
			}
		});

	}

	@Override
	public void initial(HttpResponseBean hrb)
	{
		int sellerId;
		try
		{
			sellerId = Integer.valueOf(Utils.getProperty(context, "shop_id"));
		} catch (Exception e)
		{
			e.printStackTrace();
			Toast.makeText(context, context.getString(R.string.no_seller_id),
					Toast.LENGTH_SHORT).show();
			return;
		}
		container.addView(new CouponListView(context, sellerId));
	}

	@Override
	public void recycle()
	{
		container.removeAllViews();
	}
}
