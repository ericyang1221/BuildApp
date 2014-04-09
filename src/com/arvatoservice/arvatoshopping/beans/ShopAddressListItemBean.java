package com.arvatoservice.arvatoshopping.beans;

import java.io.Serializable;

import com.arvatoservice.arvatoshopping.beans.abs.DataBean;
import com.arvatoservice.arvatoshopping.utils.Constants;

/**
 * 
 * @author Eric
 * 
 */
public class ShopAddressListItemBean implements DataBean, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String city;
	private String phone;
	private String address;
	private String name;
	private double longitude;
	private double latitude;
	private boolean isError = false;
	private boolean isEmpty = false;
	private double distance = 0;

	public ShopAddressListItemBean(int beanMode)
	{
		switch (beanMode)
		{
		case Constants.MODE_EMPTY_DATA_BENA:
			this.isEmpty = true;
			break;
		case Constants.MODE_ERROR_DATA_BENA:
			this.isError = true;
			break;
		}
	};

	public ShopAddressListItemBean(int id, String phone, String address,
			String name, double longitude, double latitude, String city)
	{
		this.id = id;
		this.phone = phone;
		this.address = address;
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.city = city;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(double longitude)
	{
		this.longitude = longitude;
	}

	public double getDistance()
	{
		return distance;
	}

	public void setDistance(double _distance)
	{
		this.distance = _distance;
	}

	public double getLatitude()
	{
		return latitude;
	}

	public void setLatitude(double latitude)
	{
		this.latitude = latitude;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	@Override
	public boolean isError()
	{
		return isError;
	}

	@Override
	public boolean isEmpty()
	{
		return isEmpty;
	}

}
