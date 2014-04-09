package com.arvatoservice.arvatoshopping.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arvatoservice.arvatoshopping.MyApplication;
import com.arvatoservice.arvatoshopping.R;
import com.arvatoservice.arvatoshopping.beans.abs.HttpResponseBean;
import com.arvatoservice.arvatoshopping.view.abs.MovableView;

public class BottomView extends LinearLayout implements MovableView {
	private UpdownViewPager uv;private LinearLayout container;
	private MyApplication myApp;
	private Context context;

	public BottomView(UpdownViewPager uv)
	{
		super(uv.getContext());
		this.uv = uv;
		this.context = uv.getContext();
		this.myApp = (MyApplication) ((Activity) context).getApplication();
		init(uv.getContext());
	}

	private void init(Context context)
	{
		LayoutInflater.from(context).inflate(R.layout.bottom_view, this, true);
		TextView tt = (TextView) findViewById(R.id.b_title);
		tt.setText(myApp.getBottomViewLeftTitle());
		container = (LinearLayout) findViewById(R.id.b_container);
		findViewById(R.id.b_top).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0)
			{
				// uv.smoothScrollToMiddle();
				uv.setCurrentItem(uv.getAdapter().getCount() == 2 ? 0 : 1, true);
			}
		});

	}

	@Override
	public void initial(HttpResponseBean hrb)
	{
		PushMsgListView pmlv = new PushMsgListView(context, uv);
		container.addView(pmlv, new LinearLayout.LayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)));
	}

	@Override
	public void recycle()
	{
		container.removeAllViews();
	}
}
