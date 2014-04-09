package com.arvatoservice.arvatoshopping.adapter.abs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.arvatoservice.arvatoshopping.MyApplication;
import com.arvatoservice.arvatoshopping.asynctask.abs.AbstractDetailGalleryPicFetcher;
import com.arvatoservice.arvatoshopping.utils.ImageManager;

/**
 * 
 * @author Eric
 * 
 */
public abstract class AbstractDetailImageAdapter extends BaseAdapter {
	protected List<?> list;
	protected Activity activity;
	protected Map<Integer, View> container;

	public AbstractDetailImageAdapter(Activity activity, List<?> list)
	{
		this.activity = activity;
		this.list = list;
		container = new HashMap<Integer, View>();
	}

	@Override
	public int getCount()
	{
		return list.size();
	}

	@Override
	public Object getItem(int pos)
	{
		return null;
	}

	@Override
	public long getItemId(int arg0)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int pos, View v, ViewGroup arg2)
	{
		if (container.containsKey(pos))
		{
			return container.get(pos);
		}
		else
		{
			String imgUrl = getImgUrl(pos);
			MyApplication myApp = (MyApplication) activity.getApplication();
			ImageManager im = myApp.getImageManager();
			View view = createView(pos);
			AbstractDetailGalleryPicFetcher dgpf = getDetailGalleryPicFetcher();
			dgpf.execute(im, getImageView(), imgUrl);
			container.put(pos, view);
			setTag(view, pos);
			return view;
		}
	}

	protected Object getBean(int pos)
	{
		return list.get(pos);
	}

	protected abstract String getImgUrl(int pos);

	protected abstract View createView(int pos);

	protected abstract ImageView getImageView();

	protected abstract void setTag(View v, int pos);

	protected abstract AbstractDetailGalleryPicFetcher getDetailGalleryPicFetcher();
}
