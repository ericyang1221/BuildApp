package com.arvatoservice.arvatoshopping.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.arvatoservice.arvatoshopping.MyApplication;
import com.arvatoservice.arvatoshopping.R;
import com.arvatoservice.arvatoshopping.beans.abs.HttpResponseBean;
import com.arvatoservice.arvatoshopping.view.abs.MovableView;

public class RightView extends LinearLayout implements MovableView {
	private MiddlePagerView mv;
	private LinearLayout container;
	private Context context;
	private MyApplication myApp;

	public RightView(MiddlePagerView mv)
	{
		super(mv.getContext());
		this.mv = mv;
		this.context = mv.getContext();
		this.myApp = (MyApplication) ((Activity) context).getApplication();
		init(context);
	}

	private void init(Context context)
	{
		LayoutInflater.from(context).inflate(R.layout.right_view, this, true);
		TitleView rt = (TitleView) findViewById(R.id.r_title);
		rt.setLeftImg(R.drawable.right_view_left_title_img);
		rt.setLeftTitle(myApp.getRightViewLeftTitle());
		container = (LinearLayout) findViewById(R.id.r_container);
		findViewById(R.id.r_left).setOnClickListener(new OnClickListener() {
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
		container.addView(new WaterFallDecorateView(context, container
				.getWidth()));
	}

	@Override
	public void recycle()
	{
		container.removeAllViews();
	}
}
