package com.arvatoservice.arvatoshopping.asynctask;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.res.AssetManager;
import android.util.Log;

import com.arvatoservice.arvatoshopping.R;
import com.arvatoservice.arvatoshopping.adapter.abs.AbstractListViewAdapter;
import com.arvatoservice.arvatoshopping.asynctask.abs.AbstractListItemBeanFetcher;
import com.arvatoservice.arvatoshopping.beans.CouponListBean;
import com.arvatoservice.arvatoshopping.beans.abs.DataBean;
import com.arvatoservice.arvatoshopping.exceptions.JSONObjectCastException;
import com.arvatoservice.arvatoshopping.utils.Constants;
import com.arvatoservice.arvatoshopping.utils.SecondaryUrl;

public class CouponListItemBeanFetcher extends AbstractListItemBeanFetcher {
	protected final String SECONDARY_URL = "cuopon/list?sellerId=?";

	public CouponListItemBeanFetcher(Activity activity) {
		super(activity);
	}

	@Override
	protected String getUrl(Object para) {
		String[] paras = (String[]) para;
		SecondaryUrl su = new SecondaryUrl(SECONDARY_URL, paras,
				Constants.SECONDARYURL_MODE_PARA);

		String url = Constants.HTTP_URL + su.toString();
		Log.d("TRACK!!!", url);
		return url;
	}

	@Override
	protected int getType() {
		return 0;
	}

	@Override
	protected Object doInBackground(Object... arg) {
		lvAdapter = (AbstractListViewAdapter) arg[0];
		AssetManager am = activity.getAssets();
		InputStream is = null;
		try {
			is = am.open("coupon.json");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		JSONObject jsonObject = new JSONObject();
		try {
			if (is != null) {
				byte[] buffer = new byte[is.available()];
				is.read(buffer);
				String json = new String(buffer, "utf8");
				jsonObject = new JSONObject(json);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return getContentList(jsonObject);
	}

	@Override
	protected List<DataBean> getContentList(Object response) {
		JSONObject jsonObj = (JSONObject) response;
		CouponListBean clBean;
		try {
			clBean = new CouponListBean(jsonObj);
		} catch (JSONObjectCastException e) {
			clBean = new CouponListBean();
		}
		return clBean.getCuoponBeanList();
	}
}
