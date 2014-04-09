package com.arvatoservice.arvatoshopping.asynctask;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.res.AssetManager;

import com.arvatoservice.arvatoshopping.R;
import com.arvatoservice.arvatoshopping.adapter.abs.AbstractListViewAdapter;
import com.arvatoservice.arvatoshopping.asynctask.abs.AbstractListItemBeanFetcher;
import com.arvatoservice.arvatoshopping.beans.ShopAddressListBean;
import com.arvatoservice.arvatoshopping.beans.abs.AbstractListBean;
import com.arvatoservice.arvatoshopping.beans.abs.DataBean;
import com.arvatoservice.arvatoshopping.exceptions.JSONObjectCastException;
import com.arvatoservice.arvatoshopping.utils.Constants;
import com.arvatoservice.arvatoshopping.utils.SecondaryUrl;

/**
 * 
 * @author Eric
 * 
 */
public class ShopAddressListItemBeanFetcher extends AbstractListItemBeanFetcher {

	private final String SECONDARY_URL = "shop/list?sellerId=?";

	public ShopAddressListItemBeanFetcher(Activity activity)
	{
		super(activity);
	}

	@Override
	protected String getUrl(Object para)
	{
		String[] p = (String[]) para;
		String sellerId = p[0];
		SecondaryUrl su = new SecondaryUrl(SECONDARY_URL,
				new String[] { String.valueOf(sellerId) },
				Constants.SECONDARYURL_MODE_PARA);
		String url = Constants.HTTP_URL + su.toString();
		return url;
	}

	@Override
	protected int getType()
	{
		return 0;
	}
	
	@Override
	protected Object doInBackground(Object... arg)
	{
		lvAdapter = (AbstractListViewAdapter) arg[0];
		AssetManager am = activity.getAssets();
		InputStream is = null;
		try {
			is = am.open("shop.json");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		JSONObject jsonObject = new JSONObject();
		try {
			byte[] buffer = new byte[is.available()];
			is.read(buffer);
			String json = new String(buffer, "utf8");
			jsonObject = new JSONObject(json);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return getContentList(jsonObject);
	}

	@Override
	protected List<DataBean> getContentList(Object response)
	{
		JSONObject jsonObj = (JSONObject) response;
		AbstractListBean lBean;
		try
		{
			lBean = new ShopAddressListBean(jsonObj,
					activity.getString(R.string.city_others));
		} catch (JSONObjectCastException e)
		{
			lBean = new ShopAddressListBean();
		}
		return lBean.getContentList();
	}

}
