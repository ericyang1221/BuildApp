package com.arvatoservice.arvatoshopping.view;

import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arvatoservice.arvatoshopping.MyApplication;
import com.arvatoservice.arvatoshopping.R;
import com.arvatoservice.arvatoshopping.beans.CouponActiveBean;
import com.arvatoservice.arvatoshopping.beans.CouponBean;
import com.arvatoservice.arvatoshopping.beans.FavoriteItemBean;
import com.arvatoservice.arvatoshopping.beans.abs.DataBean;
import com.arvatoservice.arvatoshopping.db.FavoriteDBHelper;
import com.arvatoservice.arvatoshopping.exceptions.BitmapLoadException;
import com.arvatoservice.arvatoshopping.utils.BitmapModifier;
import com.arvatoservice.arvatoshopping.utils.Constants;
import com.arvatoservice.arvatoshopping.utils.ImageManager;
import com.arvatoservice.arvatoshopping.utils.Utils;
import com.arvatoservice.arvatoshopping.view.abs.BaseListItem;

/**
 * 
 * @author Eric
 * 
 */
public class CouponListItem extends BaseListItem {
	private LinearLayout content;
	private RelativeLayout bar;
	private RelativeLayout newworkErrorBar;
	private RelativeLayout emptyBar;
	private CouponBean clib;
	public TextView nameTv, emptyText;
	public LinearLayout photoBorder;
	public ImageView likeBtn, shareBtn;
	private MyApplication myApp;
	private Bitmap couponBitmap;

	public CouponListItem(Context context)
	{
		super(context);
		setProgressBar();

	}

	public CouponListItem(Context context, CouponBean clib)
	{
		super(context);
		this.clib = clib;
		initContent();

	}

	private void init(Context context)
	{
		myApp = (MyApplication) ((Activity) context).getApplication();
		LayoutInflater.from(context).inflate(R.layout.new_coupon_list_item,
				this, true);
		content = (LinearLayout) findViewById(R.id.coupon_list_item_content);
		bar = (RelativeLayout) findViewById(R.id.coupon_list_item_bar);
		newworkErrorBar = (RelativeLayout) findViewById(R.id.coupon_list_network_error);
		nameTv = (TextView) findViewById(R.id.coupon_list_item_name);
		photoBorder = (LinearLayout) findViewById(R.id.coupon_list_item_photo_border);
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
			this.setEnabled(false);
			this.setTag(clib.getId());
			content.setVisibility(View.VISIBLE);
			newworkErrorBar.setVisibility(View.GONE);
			emptyBar.setVisibility(View.GONE);
			nameTv.setText(clib.getDesc());
			new Thread(new Runnable() {
				@Override
				public void run()
				{
					ImageManager im = myApp.getImageManager();
					Bitmap b = null;
					try
					{
						String img = Constants.IMG_URL + clib.getImg();
						b = im.getBitmap(img);
					} catch (BitmapLoadException e)
					{
						e.printStackTrace();
					}
					Message msg = handler.obtainMessage();
					msg.obj = b;
					msg.sendToTarget();
				}
			}).start();
		}

		final Activity activity = (Activity) context;
		final Dialog dialog = new Dialog(activity);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.img_dialog);
		dialog.setCanceledOnTouchOutside(true);
		final ImageView dialogImage = (ImageView) dialog.findViewById(R.id.img);
		final Handler dialogHandler = new Handler() {
			@Override
			public void handleMessage(Message msg)
			{
				Bitmap b = (Bitmap) msg.obj;
				if (b != null)
				{
					dialogImage.setImageBitmap(b);
				}
			}
		};
		photoBorder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view)
			{
				// TODO Auto-generated method stub
				Log.e("photoBorder.setOnClickListener",
						"photoBorder" + clib.getImg());

				new Thread(new Runnable() {
					@Override
					public void run()
					{
						ImageManager im = myApp.getImageManager();
						Bitmap b = null;
						try
						{
							String img = Constants.IMG_URL + clib.getImg();
							b = im.getBitmap(img);
						} catch (BitmapLoadException e)
						{
							e.printStackTrace();
						}
						Message msg = dialogHandler.obtainMessage();
						msg.obj = b;
						msg.sendToTarget();
					}
				}).start();
				RelativeLayout rl = (RelativeLayout) dialog
						.findViewById(R.id.img_container);
				rl.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view)
					{
						dialog.dismiss();
					}
				});
				dialog.show();
			}
		});

		nameTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view)
			{
				// TODO Auto-generated method stub
				Log.e("nameTv.setOnClickListener", "nameTv" + clib.getDesc());
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

		likeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view)
			{
				// TODO Auto-generated method stub
				Log.e("likeBtn.setOnClickListener",
						"likeBtn" + clib.getSellerName());

				boolean needInsert = false;
				String arvatoUrl = "";
				FavoriteItemBean flib = null;
				FavoriteDBHelper fDBHelper = myApp.getFavoriteDBHelper();
				List<FavoriteItemBean> cList = fDBHelper
						.selectByCouponIdAndSellerId(clib.getId(),
								clib.getSellerId());

				if (cList.size() > 0)
				{
					showToastMessage(activity,
							activity.getString(R.string.fav_exist));
				}
				else
				{
					arvatoUrl = Utils.genCodeFromCouponIdAndSellerId(
							clib.getId(), clib.getSellerId(),
							clib.getSellerName(), clib.getDesc());
					needInsert = true;
				}

				if (needInsert)
				{
					flib = new FavoriteItemBean(clib.getSellerName(), clib
							.getDesc(), "", arvatoUrl, "",
							Constants.FAV_MODE_COUPON, Constants.NO_INT_VALUE,
							clib.getId(), Constants.NO_INT_VALUE, clib
									.getSellerId(), System.currentTimeMillis());
					long ret = fDBHelper.insert(flib);
					if (ret == -1)
					{
						showToastMessage(activity,
								activity.getString(R.string.fav_exist));
					}
					else
					{
						showToastMessage(activity,
								activity.getString(R.string.add_fav_success));
					}
				}

			}

		});

		shareBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view)
			{
				String msg = clib.getDesc()
						+ activity.getString(R.string.share_info);
				Utils.shareQrCode(activity, msg, couponBitmap);
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

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg)
		{
			Bitmap b = (Bitmap) msg.obj;
			couponBitmap = b;
			if (b != null)
			{
				ImageView imgIv = (ImageView) findViewById(R.id.coupon_list_item_img);
				if (clib.getActiveType() != Constants.NO_NEED_ACTIVE)
				{

					CouponActiveBean cab = myApp.getCouponActiveDBHelper()
							.selectByCouponIdAndSellerId(clib.getId(),
									clib.getSellerId());
					if (cab != null)
					{
						if ((Utils.hourToMillis(cab.getTimeRange()) - (Calendar
								.getInstance().getTimeInMillis() - cab
								.getActiveTime())) < 0)
						{
							myApp.getCouponActiveDBHelper().delete(cab);
							b = BitmapModifier.toGray(b);
						}
					}
					else
					{
						b = BitmapModifier.toGray(b);
					}
				}
				imgIv.setImageBitmap(b);
			}
		}
	};

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

	private void showToastMessage(Activity activity, String msg)
	{
		Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
	}
}
