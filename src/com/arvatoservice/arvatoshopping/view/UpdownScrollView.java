package com.arvatoservice.arvatoshopping.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.arvatoservice.arvatoshopping.R;
import com.arvatoservice.arvatoshopping.view.abs.MovableView;

public class UpdownScrollView extends ScrollView {
	public static final int TOP = 1;
	public static final int BOTTOM = 3;
	public static final int MIDDLE = 2;
	private boolean hasTop = false;
	private boolean hasBottom = false;
	private ViewPagerParallax pager;
	private LinearLayout container;
	private int oneHeight = 816;
	private float lastY = 0;
	private float dy = 0;
	private MiddlePagerView mv;
	private MovableView topView;
	private MovableView bottomView;

	public UpdownScrollView(MiddlePagerView mv, int oneHeight)
	{
		super(mv.getContext());
		this.oneHeight = oneHeight;
		this.mv = mv;
		init(mv.getContext());
	}

	private void init(Context context)
	{
		this.setVerticalScrollBarEnabled(false);
		this.setHorizontalScrollBarEnabled(false);
		this.setVerticalFadingEdgeEnabled(false);
		this.setHorizontalFadingEdgeEnabled(false);
		LayoutInflater.from(context).inflate(R.layout.index, this, true);
		container = (LinearLayout) findViewById(R.id.sv_container);
	}

	@Override
	public void addView(View v, int type)
	{
		if (type == TOP)
		{
			hasTop = true;
			container.addView(v, new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, oneHeight));
			topView = (MovableView) v;
		}
		else if (type == MIDDLE)
		{
			container.addView(v, new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, oneHeight));
		}
		else if (type == BOTTOM)
		{
			hasBottom = true;
			container.addView(v, new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, oneHeight));
			bottomView = (MovableView) v;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		if (ev.getAction() == MotionEvent.ACTION_UP)
		{
			int sy = this.getScrollY();
			System.out.println(sy + "   " + dy);
			// Top&Middle page
			if (sy < oneHeight)
			{
				// go down
				if (dy < 0)
				{
					smoothScrollToMiddle();
				}
				// go up
				else
				{
					smoothScrollToTop();
				}
			}
			// Middle&Bottom page
			else if (oneHeight <= sy && sy < 2 * oneHeight)
			{
				// go down
				if (dy < 0)
				{
					smoothScrollToBottom();
				}
				// go up
				else
				{
					smoothScrollToMiddle();
				}
			}
			return true;
		}
		else if (ev.getAction() == MotionEvent.ACTION_MOVE)
		{
			float y = ev.getY();
			dy = y - lastY;
			lastY = y;
		}
		return super.onTouchEvent(ev);
	}
	
	public void smoothScrollToTop()
	{
		smoothScrollTo(0, 0);
		mv.setPagingEnabled(false);
		topView.initial(null);
	}

	public void smoothScrollToMiddle()
	{
		smoothScrollTo(0, oneHeight);
		mv.setPagingEnabled(true);
		topView.recycle();
		bottomView.recycle();
	}

	public void smoothScrollToBottom()
	{
		smoothScrollTo(0, 2 * oneHeight);
		mv.setPagingEnabled(false);
		bottomView.initial(null);
	}

	public void goMiddle()
	{
		scrollTo(0, oneHeight);
	}
}
