package com.arvatoservice.arvatoshopping.beans;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.arvatoservice.arvatoshopping.beans.abs.AbstractHttpResponseBean;
import com.arvatoservice.arvatoshopping.beans.abs.DataBean;
import com.arvatoservice.arvatoshopping.beans.abs.HttpResponseBean;
import com.arvatoservice.arvatoshopping.beans.abs.LinkedNodeBean;
import com.arvatoservice.arvatoshopping.exceptions.JSONObjectCastException;
import com.arvatoservice.arvatoshopping.utils.Constants;

/**
 * 
 * @author Eric
 * 
 */
public class CouponListBean extends AbstractHttpResponseBean implements
		HttpResponseBean {
	private List<DataBean> cuoponBeanList;

	public CouponListBean()
	{
	}

	public CouponListBean(JSONObject jo) throws JSONObjectCastException
	{
		try
		{
			ret = jo.getInt("ret");
			msg = jo.getString("msg");
			cuoponBeanList = new ArrayList<DataBean>();
			if (ret != 0)
			{
				if (ret == 2)
				{
					cuoponBeanList.add(new CouponBean(
							Constants.MODE_EMPTY_DATA_BENA));
				}
				else
				{
					cuoponBeanList.add(new CouponBean(
							Constants.MODE_ERROR_DATA_BENA));
				}
				return;
			}
			JSONArray ja = jo.getJSONArray("couponList");
			int size = ja.length();
			for (int i = 0; i < size; i++)
			{
				JSONObject j = ja.getJSONObject(i);
				int id = j.getInt("id");
				String desc = j.getString("desc");
				int sellerId = j.getInt("sellerId");
				String img = "";
				if (j.has("img"))
				{
					img = j.getString("img");
				}
				String sellerName = j.getString("sellerName");
				int activeType = Constants.NO_NEED_ACTIVE;
				CouponBean cb = new CouponBean(id, sellerId, sellerName, desc,
						img, activeType);
				cuoponBeanList.add(cb);
				// Make the node linked.
				if (i - 1 >= 0)
				{
					LinkedNodeBean previous = (LinkedNodeBean) cuoponBeanList
							.get(i - 1);
					cb.setPrivousBean(previous);
					previous.setNextBean(cb);
				}
			}
		} catch (Exception e)
		{
			throw new JSONObjectCastException(e.getMessage(),
					"JSONObject format error.");
		}
	}

	public List<DataBean> getCuoponBeanList()
	{
		return cuoponBeanList;
	}

	public void setCuoponList(List<DataBean> cuoponBeanList)
	{
		this.cuoponBeanList = cuoponBeanList;
	}

}
