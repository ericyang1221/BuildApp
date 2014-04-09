package com.arvatoservice.arvatoshopping.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.arvatoservice.arvatoshopping.MyApplication;
import com.arvatoservice.arvatoshopping.R;
import com.arvatoservice.arvatoshopping.beans.abs.HttpResponseBean;
import com.arvatoservice.arvatoshopping.view.abs.MovableView;

public class TopView extends LinearLayout implements MovableView {
	private UpdownViewPager uv;
	private LinearLayout container;
	private Context context;
	private MyApplication myApp;

	public TopView(UpdownViewPager uv)
	{
		super(uv.getContext());
		this.uv = uv;
		this.context = uv.getContext();
		this.myApp = (MyApplication) ((Activity) context).getApplication();
		init(uv.getContext());
	}

	private void init(Context context)
	{
		LayoutInflater.from(context).inflate(R.layout.top_view, this, true);
		TitleView tt = (TitleView) findViewById(R.id.t_title);
		tt.setLeftImg(R.drawable.top_view_left_title_img);
		tt.setLeftTitle(myApp.getTopViewLeftTitle());
		container = (LinearLayout) findViewById(R.id.t_container);
		findViewById(R.id.t_bottom).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0)
			{
				// uv.smoothScrollToMiddle();
				uv.setCurrentItem(uv.getAdapter().getCount() == 2 ? 0 : 1,
						false);
			}
		});
	}

	@Override
	public void initial(HttpResponseBean hrb)
	{
		ShopAddressListView salv = new ShopAddressListView(context, uv);
		container.addView(salv, new LinearLayout.LayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)));
	}

	@Override
	public void recycle()
	{
		container.removeAllViews();
	}
}
