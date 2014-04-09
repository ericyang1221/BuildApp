package com.arvatoservice.arvatoshopping.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.arvatoservice.arvatoshopping.R;
import com.arvatoservice.arvatoshopping.beans.abs.HttpResponseBean;
import com.arvatoservice.arvatoshopping.view.abs.MovableView;

public class CenterView extends RelativeLayout implements MovableView {
	private MiddlePagerView mv;
	private UpdownViewPager uv;

	public CenterView(MiddlePagerView mv, UpdownViewPager uv)
	{
		super(mv.getContext());
		this.mv = mv;
		this.uv = uv;
		init(mv.getContext());
	}

	private void init(Context context)
	{
		LayoutInflater.from(context).inflate(R.layout.center_view, this, true);
		OnClickListener directionOnClickListener = new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				switch (v.getId())
				{
				case R.id.c_up:
					// uv.smoothScrollToTop();
					uv.setCurrentItem(0, true);
					break;
				case R.id.c_bottom:
					goBottom();
					break;
				case R.id.c_left:
					mv.setCurrentItem(0, true);
					break;
				case R.id.c_right:
					mv.setCurrentItem(2, true);
					break;
				default:
					break;
				}
			}

		};
		findViewById(R.id.c_up).setOnClickListener(directionOnClickListener);
		findViewById(R.id.c_bottom)
				.setOnClickListener(directionOnClickListener);
		findViewById(R.id.c_left).setOnClickListener(directionOnClickListener);
		findViewById(R.id.c_right).setOnClickListener(directionOnClickListener);
	}

	@Override
	public void initial(HttpResponseBean hrb)
	{
	}

	@Override
	public void recycle()
	{
	}

	public void goBottom()
	{
		// uv.smoothScrollToBottom();
		uv.setCurrentItem(uv.getAdapter().getCount() - 1, true);
	}
}
