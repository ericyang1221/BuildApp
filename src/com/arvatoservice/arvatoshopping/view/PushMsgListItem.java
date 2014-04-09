package com.arvatoservice.arvatoshopping.view;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arvatoservice.arvatoshopping.MyApplication;
import com.arvatoservice.arvatoshopping.R;
import com.arvatoservice.arvatoshopping.beans.PushMsgListItemBean;
import com.arvatoservice.arvatoshopping.beans.abs.DataBean;
import com.arvatoservice.arvatoshopping.exceptions.BitmapLoadException;
import com.arvatoservice.arvatoshopping.utils.AnimFactory;
import com.arvatoservice.arvatoshopping.utils.Constants;
import com.arvatoservice.arvatoshopping.utils.ImageManager;
import com.arvatoservice.arvatoshopping.utils.Utils;
import com.arvatoservice.arvatoshopping.view.abs.BaseListItem;

/**
 * 
 * @author Eric
 * 
 */
public class PushMsgListItem extends BaseListItem {
	private LinearLayout content;
	private RelativeLayout newworkErrorBar;
	private RelativeLayout bar;
	private RelativeLayout emptyBar;
	private TextView typeText;
	private TextView msgContent;
	private ImageView label;
	private TextView time;
	private ImageView img;
	private int type = -1;

	public PushMsgListItem(Context context)
	{
		super(context);
		setProgressBar();
	}

	public PushMsgListItem(Context context, PushMsgListItemBean pmlib)
	{
		super(context);
		initContent(context, pmlib);
	}

	private void init(Context context)
	{
		LayoutInflater.from(context).inflate(R.layout.push_msg_list_item, this,
				true);
		content = (LinearLayout) findViewById(R.id.push_msg_list_item_content);
		bar = (RelativeLayout) findViewById(R.id.push_msg_list_item_bar);
		newworkErrorBar = (RelativeLayout) findViewById(R.id.push_msg_list_network_error);
		typeText = (TextView) findViewById(R.id.push_msg_type_text);
		msgContent = (TextView) findViewById(R.id.push_msg_content);
		time = (TextView) findViewById(R.id.push_msg_receive_time);
		label = (ImageView) findViewById(R.id.push_msg_type_label);
		img = (ImageView) findViewById(R.id.push_msg_img);
		emptyBar = (RelativeLayout) findViewById(R.id.push_msg_list_empty);
	}

	private void initContent(final Context context,
			final PushMsgListItemBean pmlib)
	{
		init(context);
		bar.setVisibility(View.GONE);
		Log.e("initContent", "salib.isError()=" + pmlib.isError()
				+ " salib.isEmpty()=" + pmlib.isEmpty());
		if (pmlib.isError())
		{
			content.setVisibility(View.GONE);
			newworkErrorBar.setVisibility(View.VISIBLE);
			emptyBar.setVisibility(View.GONE);
			this.setEnabled(false);
		}
		else if (pmlib.isEmpty())
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
			type = pmlib.getType();
			switch (type)
			{
			default:
			case Constants.PUSH_MSG_TYPE_INFO:
				label.setImageResource(R.drawable.push_msg_type_info);
				typeText.setText(R.string.push_msg_type_info);
				typeText.setTextColor(0xfff9cf0b);
				break;
			case Constants.PUSH_MSG_TYPE_PRODUCT:
				label.setImageResource(R.drawable.push_msg_type_product);
				typeText.setText(R.string.push_msg_type_product);
				typeText.setTextColor(0xfff36fdf);
				break;
			case Constants.PUSH_MSG_TYPE_COUPON:
				label.setImageResource(R.drawable.push_msg_type_coupon);
				typeText.setText(R.string.push_msg_type_coupon);
				typeText.setTextColor(0xff10bbec);
				break;
			}
			msgContent.setText(pmlib.getMessage());
			time.setText(Utils.getPushTime(pmlib.getReceiveTime()));

			final Activity activity = (Activity) context;
			final String imgUrl = pmlib.getImgUrl();
			if (imgUrl != null && !"".equals(imgUrl))
			{
				new Thread(new Runnable() {
					@Override
					public void run()
					{
						ImageManager im = ((MyApplication) activity
								.getApplication()).getImageManager();
						Bitmap b = null;
						try
						{
							b = im.getBitmap(imgUrl);
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
			else
			{
				img.setImageBitmap(null);
				img.setVisibility(View.GONE);
			}
			
			if (pmlib.getType() == Constants.PUSH_MSG_TYPE_COUPON)
			{
				this.setEnabled(false);
				msgContent.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view)
					{
						final Dialog dialog = new Dialog(activity);
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialog.setContentView(R.layout.text_dialog);
						dialog.setCanceledOnTouchOutside(true);
						TextView tv = (TextView) dialog.findViewById(R.id.text);
						tv.setText(pmlib.getMessage());
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
			}else if(pmlib.getType() == Constants.PUSH_MSG_TYPE_PRODUCT){
				msgContent.setClickable(false);
				this.setEnabled(true);
			}
			else
			{
				this.setEnabled(false);
				msgContent.setClickable(false);
			}
			setTag(pmlib);
		}
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
		PushMsgListItemBean pmlib = (PushMsgListItemBean) db;
		Log.e("setData", "" + "salib.isError()=" + pmlib.isError()
				+ " salib.isEmpty()=" + pmlib.isEmpty());
		initContent(context, pmlib);
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg)
		{
			final Bitmap b = (Bitmap) msg.obj;
			if (b != null)
			{
				img.setImageBitmap(b);
				img.setVisibility(View.VISIBLE);
				img.startAnimation(AnimFactory.createAlphaAnimation(0.2f, 1,
						300));
				if (type == Constants.PUSH_MSG_TYPE_COUPON)
				{
					img.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View view)
						{
							final Dialog dialog = new Dialog(context);
							dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
							dialog.setContentView(R.layout.img_dialog);
							dialog.setCanceledOnTouchOutside(true);
							ImageView dialogImage = (ImageView) dialog
									.findViewById(R.id.img);
							dialogImage.setImageBitmap(b);
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
				}
				else
				{
					img.setClickable(false);
				}
			}
		}
	};
}
