package com.arvatoservice.arvatoshopping;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arvatoservice.arvatoshopping.beans.ProductDetailBean;
import com.arvatoservice.arvatoshopping.exceptions.BitmapLoadException;
import com.arvatoservice.arvatoshopping.utils.Utils;

/**
 * 
 * @author Eric
 * 
 */
public class ProductDetailActivity extends Activity {
	private ProductDetailBean pdb;
	private MyApplication myApp;
	private Bitmap shareBitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_detail);
		myApp = (MyApplication) getApplication();

		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.width = (int) (myApp.getScreenWidth() * 0.9);
		lp.gravity = Gravity.RIGHT;
		getWindow().setAttributes(lp);

		View border = findViewById(R.id.product_img_border);
		LinearLayout.LayoutParams blp = (LinearLayout.LayoutParams) border
				.getLayoutParams();
		blp.width = lp.width - 20;
		blp.height = blp.width;
		border.setLayoutParams(blp);

		Intent i = getIntent();
		pdb = (ProductDetailBean) i.getSerializableExtra("productDetailBean");
		init(pdb);
	}

	@SuppressLint("HandlerLeak")
	private void init(final ProductDetailBean pdb)
	{
		TextView productTitle = (TextView) findViewById(R.id.product_title);
		TextView productPrice = (TextView) findViewById(R.id.product_price);
		ImageView productBuy = (ImageView) findViewById(R.id.product_buy);
		ImageView productShare = (ImageView) findViewById(R.id.product_share);
		final ImageView productImg = (ImageView) findViewById(R.id.product_img);

		productTitle.setText(pdb.getName());
		productPrice.setText("￥" + pdb.getPrice());

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg)
			{
				super.handleMessage(msg);
				Bitmap b = (Bitmap) msg.obj;
				shareBitmap = b;
				productImg.setImageBitmap(b);
			}
		};
		new Thread(new Runnable() {
			@Override
			public void run()
			{
				String imgUrl = pdb.getImg();
				Bitmap b = null;
				try
				{
					b = myApp.getImageManager().getBitmap(imgUrl);
				} catch (BitmapLoadException e)
				{
					e.printStackTrace();
				}
				if (b != null)
				{
					Message msg = handler.obtainMessage();
					msg.obj = b;
					msg.sendToTarget();
				}
			}
		}).start();

		productShare.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view)
			{
				String shareText = pdb.getName();
				Utils.shareQrCode(ProductDetailActivity.this, shareText,
						shareBitmap);
			}
		});

		productBuy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view)
			{
				String urlStr = pdb.getInfoUrl();
				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				Uri url = Uri.parse(urlStr);
				intent.setData(url);
				startActivity(intent);
			}
		});

		// proImg.setImageBitmap(bm)

	}

	@Override
	protected void onResume()
	{
		super.onResume();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
	}
}
