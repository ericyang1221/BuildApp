package com.arvatoservice.arvatoshopping.asynctask;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.AsyncTask;

import com.arvatoservice.arvatoshopping.beans.ProductDetailBean;
import com.arvatoservice.arvatoshopping.beans.abs.HttpResponseBean;
import com.arvatoservice.arvatoshopping.exceptions.JSONObjectCastException;
import com.arvatoservice.arvatoshopping.net.HttpRequestHelper;
import com.arvatoservice.arvatoshopping.utils.Constants;
import com.arvatoservice.arvatoshopping.view.abs.SetableView;

/**
 * 
 * @author Eric
 * 
 */
public class ProductDetailBeanFetcher extends AsyncTask<Object, Void, Object> {
	protected SetableView sv;
	protected Activity activity;
	protected int id;

	public ProductDetailBeanFetcher(Activity activity, int id)
	{
		this.activity = activity;
		this.id = id;
	}

	protected HttpResponseBean getDetailBean(JSONObject jsonObj,
			String productId)
	{
		ProductDetailBean pdb;
		try
		{
			pdb = new ProductDetailBean(jsonObj, productId);
		} catch (JSONObjectCastException e)
		{
			pdb = new ProductDetailBean();
		}
		return pdb;
	}

	@Override
	protected Object doInBackground(Object... arg)
	{
		sv = (SetableView) arg[0];
		String[] para = (String[]) arg[1];
//		String url = genUrl(para);
//		HttpRequestHelper hth = new HttpRequestHelper(activity);
//		JSONObject jsonObj = hth.sendRequestAndReturnJson(url);
		AssetManager am = activity.getAssets();
		InputStream is;
		JSONObject jsonObj = new JSONObject();
		try {
			is = am.open("product_detail_" + para[0]+".json");
			byte[] buffer = new byte[is.available()];
			is.read(buffer);
			String json = new String(buffer, "utf8");
			jsonObj = new JSONObject(json);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return getDetailBean(jsonObj, para[0]);
	}

	protected String genUrl(String[] para)
	{
		return Constants.BASE_URL + "json/" + "10001/" + para[0] + ".json";
	}

	@Override
	protected void onPostExecute(Object result)
	{
		HttpResponseBean hrb = (HttpResponseBean) result;
		sv.setData(hrb);
		super.onPostExecute(result);
	}
}
