package com.arvatoservice.arvatoshopping.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.arvatoservice.arvatoshopping.MyApplication;
import com.arvatoservice.arvatoshopping.view.BottomView;
import com.arvatoservice.arvatoshopping.view.CenterView;
import com.arvatoservice.arvatoshopping.view.LeftView;
import com.arvatoservice.arvatoshopping.view.MiddlePagerView;
import com.arvatoservice.arvatoshopping.view.RightView;
import com.arvatoservice.arvatoshopping.view.TopView;
import com.arvatoservice.arvatoshopping.view.UpdownScrollView;
import com.arvatoservice.arvatoshopping.view.UpdownViewPager;
import com.arvatoservice.arvatoshopping.view.abs.MovableView;

public class MiddlePagerAdapter extends PagerAdapter {
	private final int LEFT_VIEW = 0;
	private final int MAIN_VIEW = 1;
	private final int RIGHT_VIEW = 2;
	private int pageCount;
	private boolean hasLeft;
	private MiddlePagerView mv;
	private MyApplication myApp;
	private MovableView leftView;
	private MovableView rightView;
	private MovableView topView;
	private MovableView bottomView;
	private MovableView centerView;
	private UpdownViewPager uv;

	public MiddlePagerAdapter(MiddlePagerView mv, int pageCount, boolean hasLeft)
	{
		this.mv = mv;
		this.pageCount = pageCount;
		this.hasLeft = hasLeft;
		this.myApp = (MyApplication) ((Activity) mv.getContext())
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
			if (hasLeft)
			{
				newView = getView(LEFT_VIEW);
			}
			else
			{
				newView = getView(MAIN_VIEW);
			}
		}
		else if (position == 1)
		{
			if (hasLeft)
			{
				newView = getView(MAIN_VIEW);
			}
			else
			{
				newView = getView(RIGHT_VIEW);
			}
		}
		// postition == 2
		else
		{
			newView = getView(RIGHT_VIEW);
		}
		container.addView(newView);
		return newView;
	}

	private View getView(int viewType)
	{
		View view = null;
		switch (viewType)
		{
		case LEFT_VIEW:
			if (leftView == null)
			{
				leftView = new LeftView(mv);
			}
			view = (View) leftView;
			break;
		case MAIN_VIEW:
			if (uv == null)
			{
				// uv = new UpdownViewPager(mv, myApp.getAppHeight());
				uv = new UpdownViewPager(mv);
				uv.initView(3, true, true);
			}
			// if (topView == null)
			// {
			// topView = new TopView(uv);
			// }
			// uv.addView((View) topView, UpdownScrollView.TOP);
			// if (centerView == null)
			// {
			// centerView = new CenterView(mv, uv);
			// }
			// uv.addView((View) centerView, UpdownScrollView.MIDDLE);
			// uv.post(new Runnable() {
			// @Override
			// public void run()
			// {
			// uv.goMiddle();
			// }
			// });
			// if (bottomView == null)
			// {
			// bottomView = new BottomView(uv);
			// }
			// uv.addView((View) bottomView, UpdownScrollView.BOTTOM);
			view = uv;
			break;
		case RIGHT_VIEW:
			if (rightView == null)
			{
				rightView = new RightView(mv);
			}
			view = (View) rightView;
			break;
		default:
			break;
		}
		return (View) view;
	}

	public MovableView getRightView()
	{
		return rightView;
	}

	public MovableView getLeftView()
	{
		return leftView;
	}

	public MovableView getTopView()
	{
		MovableView ret;
		if (uv == null)
		{
			ret = null;
		}
		else
		{
			ret = ((UpdownPagerAdapter) uv.getAdapter()).getTopView();
		}
		return ret;
	}

	public MovableView getBottomView()
	{
		MovableView ret;
		if (uv == null)
		{
			ret = null;
		}
		else
		{
			ret = ((UpdownPagerAdapter) uv.getAdapter()).getBottomView();
		}
		return ret;
	}

	public MovableView getCenterView()
	{
		MovableView ret;
		if (uv == null)
		{
			ret = null;
		}
		else
		{
			ret = ((UpdownPagerAdapter) uv.getAdapter()).getCenterView();
		}
		return ret;
	}

}
