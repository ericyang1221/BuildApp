package com.arvatoservice.arvatoshopping.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;

import com.arvatoservice.arvatoshopping.adapter.abs.AbstractListViewAdapter;
import com.arvatoservice.arvatoshopping.asynctask.CouponListItemBeanFetcher;
import com.arvatoservice.arvatoshopping.beans.CouponBean;
import com.arvatoservice.arvatoshopping.beans.abs.DataBean;
import com.arvatoservice.arvatoshopping.utils.Constants;
import com.arvatoservice.arvatoshopping.view.CouponListItem;
import com.arvatoservice.arvatoshopping.view.CouponListItemNoImg;

/**
 * 
 * @author Eric
 * 
 */
public class CouponChartAdapter extends AbstractListViewAdapter {
	private final int VIEW_TYPE_COUNT = 2;
	private final int TYPE_IMG = 0;
	private final int TYPE_NO_IMG = 1;
	private int activeType;

	public CouponChartAdapter(Activity activity, List<DataBean> pliBeanList,
			String[] para, int activeType)
	{
		super(activity, pliBeanList, para);
		this.activeType = activeType;
	}

	@Override
	public void fetchNewData()
	{
		CouponListItemBeanFetcher plbf = new CouponListItemBeanFetcher(activity);
		plbf.execute(this, para);
	}

	@Override
	public int getItemViewType(int position)
	{
		CouponBean cliBean = (CouponBean) liBeanList.get(position);
		if (cliBean != null
				&& (cliBean.getImg() == null || "".equals(cliBean.getImg())))
		{
			return TYPE_NO_IMG;
		}
		else
		{
			return TYPE_IMG;
		}

	}

	@Override
	public int getViewTypeCount()
	{
		return VIEW_TYPE_COUNT;
	}

	@Override
	public View createItemView(View convertView, DataBean bean, int pos)
	{
		View ret;
		CouponListItem cli;
		CouponListItemNoImg clini;
		final CouponBean cliBean = (CouponBean) bean;
		int type = getItemViewType(pos);
		if (cliBean != null)
		{
			cliBean.setActiveType(activeType);
			if (convertView == null)
			{
				switch (type)
				{
				default:
				case TYPE_IMG:
					ret = new CouponListItem(activity, cliBean);
					break;
				case TYPE_NO_IMG:
					ret = new CouponListItemNoImg(activity, cliBean);
					break;
				}
			}
			else
			{
				switch (type)
				{
				default:
				case TYPE_IMG:
					cli = (CouponListItem) convertView;
					cli.setData(cliBean);
					ret = cli;
					break;
				case TYPE_NO_IMG:
					clini = (CouponListItemNoImg) convertView;
					clini.setData(cliBean);
					ret = clini;
					break;
				}
			}
		}
		else
		{
			if (convertView == null)
			{
				switch (type)
				{
				default:
				case TYPE_IMG:
					ret = new CouponListItem(activity);
					break;
				case TYPE_NO_IMG:
					ret = new CouponListItemNoImg(activity);
					break;
				}
			}
			else
			{
				switch (type)
				{
				default:
				case TYPE_IMG:
					cli = (CouponListItem) convertView;
					cli.setProgressBar();
					ret = cli;
					break;
				case TYPE_NO_IMG:
					clini = (CouponListItemNoImg) convertView;
					clini.setProgressBar();
					ret = clini;
					break;
				}
			}
		}
		// ret.startAnimation(alphaAnimation);
		return ret;
	}

	@Override
	public DataBean getErrorDataBean()
	{
		return new CouponBean(Constants.MODE_ERROR_DATA_BENA);
	}

	@Override
	public DataBean getEmptyDataBean()
	{
		return new CouponBean(Constants.MODE_EMPTY_DATA_BENA);
	}

}
