package com.arvatoservice.arvatoshopping.view;

import android.support.v4.view.DirectionalViewPager;

import com.arvatoservice.arvatoshopping.adapter.UpdownPagerAdapter;

public class UpdownViewPager extends DirectionalViewPager {
	private MiddlePagerView mv;
	private UpdownPagerAdapter ud;

	public UpdownViewPager(MiddlePagerView mv)
	{
		super(mv.getContext());
		this.mv = mv;
		setOrientation(VERTICAL);
	}

	public void initView(int pageCount, boolean hasTop, boolean hasBottom)
	{
		ud = new UpdownPagerAdapter(mv, UpdownViewPager.this, pageCount, true);
		setAdapter(ud);
		if ((hasTop && hasBottom) || (hasTop && !hasBottom))
		{
			setCurrentItem(1);
		}
		else if (!hasTop && hasBottom)
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
					ud.getTopView().initial(null);
					mv.setPagingEnabled(false);
				}
				else if (arg0 == 1)
				{
					ud.getBottomView().recycle();
					ud.getTopView().recycle();
					mv.setPagingEnabled(true);
				}
				else if (arg0 == 2)
				{
					ud.getBottomView().initial(null);
					mv.setPagingEnabled(false);
				}
			}

		};
		this.setOnPageChangeListener(opl);
	}
}
