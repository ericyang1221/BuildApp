package com.arvatoservice.arvatoshopping.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.arvatoservice.arvatoshopping.MyApplication;
import com.arvatoservice.arvatoshopping.view.BottomView;
import com.arvatoservice.arvatoshopping.view.CenterView;
import com.arvatoservice.arvatoshopping.view.MiddlePagerView;
import com.arvatoservice.arvatoshopping.view.RightView;
import com.arvatoservice.arvatoshopping.view.TopView;
import com.arvatoservice.arvatoshopping.view.UpdownScrollView;
import com.arvatoservice.arvatoshopping.view.UpdownViewPager;
import com.arvatoservice.arvatoshopping.view.abs.MovableView;

public class UpdownPagerAdapter extends PagerAdapter {
	private final int TOP_VIEW = 0;
	private final int MAIN_VIEW = 1;
	private final int BOTTOM_VIEW = 2;
	private boolean hasTop;
	private int pageCount;
	private UpdownViewPager uv;
	private MiddlePagerView mv;
	private MyApplication myApp;
	private MovableView topView;
	private MovableView bottomView;
	private MovableView centerView;

	public UpdownPagerAdapter(MiddlePagerView mv ,UpdownViewPager uv, int pageCount,boolean hasTop)
	{
		this.mv = mv;
		this.uv = uv;
		this.hasTop = hasTop;
		this.pageCount = pageCount;
		this.myApp = (MyApplication) ((Activity) uv.getContext())
				.getApplication();
	}

	@Override
	public int getCount()
	{
		return pageCount;
	}

	@Override
	public boolean isViewFromObject(View view, Object o)
	{
		return view == o;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object)
	{
		container.removeView((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position)
	{
		View newView;
		if (position == 0)
		{
			if (hasTop)
			{
				newView = getView(TOP_VIEW);
			}
			else
			{
				newView = getView(MAIN_VIEW);
			}
		}
		else if (position == 1)
		{
			if (hasTop)
			{
				newView = getView(MAIN_VIEW);
			}
			else
			{
				newView = getView(BOTTOM_VIEW);
			}
		}
		// postition == 2
		else
		{
			newView = getView(BOTTOM_VIEW);
		}
		container.addView(newView);
		return newView;
	}

	private View getView(int viewType)
	{
		View view = null;
		switch (viewType)
		{
		case TOP_VIEW:
			if (topView == null)
			{
				topView = new TopView(uv);
			}
			view = (View) topView;
			break;
		case MAIN_VIEW:
			if (centerView == null)
			{
				centerView = new CenterView(mv, uv);
			}
			view = (View) centerView;
			break;
		case BOTTOM_VIEW:
			if (bottomView == null)
			{
				bottomView = new BottomView(uv);
			}
			view = (View) bottomView;
			break;
		default:
			break;
		}
		return (View) view;
	}

	public MovableView getTopView()
	{
		return topView;
	}

	public MovableView getBottomView()
	{
		return bottomView;
	}

	public MovableView getCenterView()
	{
		return centerView;
	}

}
