package com.arvatoservice.arvatoshopping.view;

import android.content.Context;
import android.util.AttributeSet;

import com.arvatoservice.arvatoshopping.adapter.MiddlePagerAdapter;

public class MiddlePagerView extends ViewPagerParallax {
	private int pageCount = 1;
	private MiddlePagerAdapter md;

	public MiddlePagerView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public MiddlePagerView(Context context)
	{
		super(context);
	}

	public void initView(boolean hasLeft, boolean hasRight, int backgroundAsset)
	{
		pageCount = hasLeft ? ++pageCount : pageCount;
		pageCount = hasRight ? ++pageCount : pageCount;

		set_max_pages(pageCount);
		setBackgroundAsset(backgroundAsset);
		md = new MiddlePagerAdapter(MiddlePagerView.this, pageCount, hasLeft);
		setAdapter(md);
		if ((hasLeft && hasRight) || (hasLeft && !hasRight))
		{
			setCurrentItem(1);
		}
		else if (!hasLeft && hasRight)
		{
			setCurrentItem(0);
		}

		OnPageChangeListener opl = new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0)
			{
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{
			}

			@Override
			public void onPageSelected(int arg0)
			{
				if (arg0 == 0)
				{
					md.getLeftView().initial(null);
				}
				else if (arg0 == 1)
				{
					md.getRightView().recycle();
					md.getLeftView().recycle();
				}
				else if (arg0 == 2)
				{
					md.getRightView().initial(null);
				}
			}

		};

		this.setOnPageChangeListener(opl);
	}
}
