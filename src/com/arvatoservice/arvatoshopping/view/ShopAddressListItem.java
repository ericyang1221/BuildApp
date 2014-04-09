package com.arvatoservice.arvatoshopping.view;

import java.text.NumberFormat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arvatoservice.arvatoshopping.R;
import com.arvatoservice.arvatoshopping.beans.ShopAddressListItemBean;
import com.arvatoservice.arvatoshopping.beans.abs.DataBean;
import com.arvatoservice.arvatoshopping.utils.Utils;
import com.arvatoservice.arvatoshopping.view.abs.BaseListItem;

/**
 * 
 * @author Eric
 * 
 */
public class ShopAddressListItem extends BaseListItem {
	private LinearLayout content;
	private RelativeLayout newworkErrorBar;
	private RelativeLayout bar;
	private RelativeLayout emptyBar;
	private TextView emptyText;
	private TextView title;
	private TextView phoneLabel;
	private TextView phone;
	private TextView address;
	private ImageView label;
	private TextView disTxt;

	public ShopAddressListItem(Context context)
	{
		super(context);
		setProgressBar();
	}

	public ShopAddressListItem(Context context, ShopAddressListItemBean salib)
	{
		super(context);
		initContent(context, salib);
	}

	private void init(Context context)
	{
		LayoutInflater.from(context).inflate(R.layout.shop_address_list_item,
				this, true);
		content = (LinearLayout) findViewById(R.id.shop_address_list_item_content);
		bar = (RelativeLayout) findViewById(R.id.shop_address_list_item_bar);
		newworkErrorBar = (RelativeLayout) findViewById(R.id.shop_address_list_network_error);
		title = (TextView) findViewById(R.id.shop_address_listviewitem_title);
		phone = (TextView) findViewById(R.id.shop_address_listviewitem_phone);
		address = (TextView) findViewById(R.id.shop_address_listviewitem_address);
		label = (ImageView) findViewById(R.id.shop_address_listviewitem_label);
		disTxt = (TextView) findViewById(R.id.dis_txt);
		emptyBar = (RelativeLayout) findViewById(R.id.shop_list_empty);
		emptyText = (TextView) findViewById(R.id.shop_empty_text);
	}

	private void initContent(Context context, ShopAddressListItemBean salib)
	{
		init(context);
		bar.setVisibility(View.GONE);
		Log.e("initContent", "salib.isError()=" + salib.isError()
				+ " salib.isEmpty()=" + salib.isEmpty());
		if (salib.isError())
		{
			content.setVisibility(View.GONE);
			newworkErrorBar.setVisibility(View.VISIBLE);
			emptyBar.setVisibility(View.GONE);
			this.setEnabled(false);
		}
		else if (salib.isEmpty())
		{
			content.setVisibility(View.GONE);
			newworkErrorBar.setVisibility(View.GONE);
			emptyBar.setVisibility(View.VISIBLE);
			this.setEnabled(false);
		}
		else
		{
			this.setEnabled(true);
			content.setVisibility(View.VISIBLE);
			newworkErrorBar.setVisibility(View.GONE);
			emptyBar.setVisibility(View.GONE);
			// LayoutInflater.from(context).inflate(
			// R.layout.shop_address_list_item, this, true);
			title.setText(salib.getName());
			phone.setText(salib.getPhone());
			address.setText(salib.getAddress());
			double dis = salib.getDistance();
			if (Utils.getLocation(context) != null)
			{
				String disStr = getDisStr(dis);
				disTxt.setText(disStr);
			}
			else
			{
				LinearLayout disLayout = (LinearLayout) findViewById(R.id.dis_layout);
				disLayout.setVisibility(View.GONE);
			}
			this.setTag(R.id.store_id, salib.getId());
			this.setTag(R.id.store_name, salib.getName());
			setTag(salib);
		}
	}

	private String getDisStr(double dis)
	{
		String disStr = "";
		disStr = dis + "";
		NumberFormat formater = java.text.DecimalFormat.getInstance();
		formater.setMaximumFractionDigits(1);
		formater.setMinimumFractionDigits(0);
		if (dis < 1000)
		{
			if (dis < 100)
			{
				disStr = "<100m";
			}
			else
			{
				disStr = ((int) dis) + "m";
			}

		}
		else if (dis >= 100000)
		{
			disStr = ">100km";
		}
		else
		{
			dis = dis / 1000;
			disStr = formater.format(dis) + "km";
		}
		return disStr;
	}

	@Override
	public void setProgressBar()
	{
		init(context);
		this.setEnabled(false);
		content.setVisibility(View.GONE);
		bar.setVisibility(View.VISIBLE);
		newworkErrorBar.setVisibility(View.GONE);
	}

	@Override
	public void setData(DataBean db)
	{
		ShopAddressListItemBean salib = (ShopAddressListItemBean) db;
		Log.e("setData", "" + "salib.isError()=" + salib.isError()
				+ " salib.isEmpty()=" + salib.isEmpty());
		initContent(context, salib);
	}

}
