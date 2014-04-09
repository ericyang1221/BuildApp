package com.arvatoservice.arvatoshopping.asynctask.abs;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import com.arvatoservice.arvatoshopping.exceptions.BitmapLoadException;
import com.arvatoservice.arvatoshopping.utils.AnimFactory;
import com.arvatoservice.arvatoshopping.utils.ImageManager;

/**
 * 
 * @author Eric
 * 
 */
public abstract class AbstractDetailGalleryPicFetcher extends
		AsyncTask<Object, Void, Object> {
	protected ImageView iv;
	protected ImageManager im;

	@Override
	protected Object doInBackground(Object... arg)
	{
		im = (ImageManager) arg[0];
		iv = (ImageView) arg[1];
		String imgUrl = (String) arg[2];
		Bitmap b = null;
		try
		{
			b = im.getBitmap(imgUrl);
		} catch (BitmapLoadException e)
		{
			e.printStackTrace();
		}
		return b;
	}

	@Override
	protected void onPostExecute(Object result)
	{
		if (iv != null)
		{
			Bitmap b = (Bitmap) result;
			iv.setImageBitmap(b);
			iv.setVisibility(View.VISIBLE);
			iv.startAnimation(AnimFactory
					.createAlphaAnimation(0.1f, 1.0f, 200f));
			doPost();
		}
		super.onPostExecute(result);
	}
	
	protected abstract void doPost();
}