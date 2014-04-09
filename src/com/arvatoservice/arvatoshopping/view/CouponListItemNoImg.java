package com.arvatoservice.arvatoshopping.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arvatoservice.arvatoshopping.R;
import com.arvatoservice.arvatoshopping.beans.CouponBean;
import com.arvatoservice.arvatoshopping.beans.abs.DataBean;
import com.arvatoservice.arvatoshopping.view.abs.BaseListItem;

/**
 * 
 * @author Eric
 * 
 */
public class CouponListItemNoImg extends BaseListItem {
	private LinearLayout content;
	private RelativeLayout bar;
	private RelativeLayout newworkErrorBar;
	private RelativeLayout emptyBar;
	private CouponBean clib;
	public TextView contentText, emptyText;
	public LinearLayout contentBorder;
	public ImageView likeBtn, shareBtn;

	public CouponListItemNoImg(Context context)
	{
		super(context);
		setProgressBar();

	}

	public CouponListItemNoImg(Context context, CouponBean clib)
	{
		super(context);
		this.clib = clib;
		initContent();

	}

	private void init(Context context)
	{
		LayoutInflater.from(context).inflate(
				R.layout.new_coupon_list_item_no_img, this, true);
		content = (LinearLayout) findViewById(R.id.coupon_list_item_content);
		contentText = (TextView) findViewById(R.id.coupon_list_item_content_text);
		bar = (RelativeLayout) findViewById(R.id.coupon_list_item_bar);
		newworkErrorBar = (RelativeLayout) findViewById(R.id.coupon_list_network_error);
		contentBorder = (LinearLayout) findViewById(R.id.coupon_list_item_content_border);
		emptyBar = (RelativeLayout) findViewById(R.id.coupon_list_empty);
		emptyText = (TextView) findViewById(R.id.coupon_empty_text);
		likeBtn = (ImageView) findViewById(R.id.coupon_like_btn);
		shareBtn = (ImageView) findViewById(R.id.coupon_share_btn);
	}

	private void initContent()
	{
		init(context);
		bar.setVisibility(View.GONE);
		if (clib.isError())
		{
			content.setVisibility(View.GONE);
			emptyBar.setVisibility(View.GONE);
			newworkErrorBar.setVisibility(View.VISIBLE);
			this.setEnabled(false);
		}
		else if (clib.isEmpty())
		{
			content.setVisibility(View.GONE);
			emptyBar.setVisibility(View.VISIBLE);
			newworkErrorBar.setVisibility(View.GONE);
			this.setEnabled(false);
		}
		else
		{
			this.setEnabled(true);
			this.setTag(clib.getId());
			content.setVisibility(View.VISIBLE);
			newworkErrorBar.setVisibility(View.GONE);
			emptyBar.setVisibility(View.GONE);
			contentText.setText(clib.getDesc());
		}

		final Activity activity = (Activity) context;
		contentText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view)
			{
				final Dialog dialog = new Dialog(activity);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.text_dialog);
				dialog.setCanceledOnTouchOutside(true);
				TextView tv = (TextView) dialog.findViewById(R.id.text);
				tv.setText(clib.getDesc());
				tv.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view)
					{
						dialog.dismiss();
					}
				});
				dialog.show();
			}
		});
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

	public CouponBean getCouponBean()
	{
		return clib;
	}

	@Override
	public void setData(DataBean db)
	{
		this.clib = (CouponBean) db;
		initContent();
	}
}
