package com.arvatoservice.arvatoshopping.beans;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.arvatoservice.arvatoshopping.beans.abs.AbstractListBean;
import com.arvatoservice.arvatoshopping.beans.abs.DataBean;
import com.arvatoservice.arvatoshopping.exceptions.JSONObjectCastException;
import com.arvatoservice.arvatoshopping.utils.Constants;

/**
 * 
 * @author Eric
 * 
 */
public class ShopAddressListBean extends AbstractListBean {
	public ShopAddressListBean()
	{
	}
	
	public ShopAddressListBean(JSONObject jo, String defaultDescOfOthers)
			throws JSONObjectCastException
	{
		try
		{
			ret = jo.getInt("ret");
			msg = jo.getString("msg");
			contentList = new ArrayList<DataBean>();
			if (ret != 0)
			{
				if(ret == 2){
					contentList.add(new ShopAddressListItemBean(
							Constants.MODE_EMPTY_DATA_BENA));
				}else{
					contentList.add(new ShopAddressListItemBean(
							Constants.MODE_ERROR_DATA_BENA));
				}
				// error occoured.
				return;
			}
			JSONArray ja = jo.getJSONArray("shopList");
			int size = ja.length();
			if (size == 0)
			{
				contentList.add(new ShopAddressListItemBean(
						Constants.MODE_EMPTY_DATA_BENA));
			}
			else
			{
				for (int i = 0; i < size; i++)
				{
					JSONObject j = ja.getJSONObject(i);
					int id = j.getInt("id");
					String phone = j.getString("phone");
					String address = j.getString("address");
					String name = j.getString("name");
					double longitude = j.getDouble("longitude");
					double latitude = j.getDouble("latitude");
					// TODO
					String city = defaultDescOfOthers;
					if (j.has("city"))
					{
						city = j.getString("city");
						if ("".equals(city))
						{
							city = defaultDescOfOthers;
						}
					}
					contentList.add(new ShopAddressListItemBean(id, phone,
							address, name, longitude, latitude, city));
				}
			}
		} catch (Exception e)
		{
			throw new JSONObjectCastException(e.getMessage(),
					"JSONObject format error.");
		}

	}
}
