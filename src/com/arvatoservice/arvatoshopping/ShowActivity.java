package com.arvatoservice.arvatoshopping;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Display;

import com.arvatoservice.arvatoshopping.map.BMapLocationListener;
import com.arvatoservice.arvatoshopping.utils.BPushUtils;
import com.arvatoservice.arvatoshopping.utils.Utils;
import com.baidu.android.common.logging.Log;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class ShowActivity extends Activity {
	private MyApplication myApp;
	private Handler handler = new Handler();
	private LocationClient mLocationClient = null;
	private BDLocationListener myListener = new BMapLocationListener(this);

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show);
		myApp = (MyApplication) this.getApplication();
		init();
	}

	@SuppressWarnings("deprecation")
	private void init()
	{
		Display display = getWindowManager().getDefaultDisplay();
		myApp.setScreenHeight(display.getHeight());
		myApp.setScreenWidth(display.getWidth());
		myApp.setRightViewLeftTitle(Utils.getProperty(this,
				"right_view_left_title"));
		myApp.setLeftViewLeftTitle(Utils.getProperty(this,
				"left_view_left_title"));
		myApp.setTopViewLeftTitle(Utils
				.getProperty(this, "top_view_left_title"));
		myApp.setBottomViewLeftTitle(Utils.getProperty(this,
				"bottom_view_left_title"));
		initLocation();
		initApiKey();
	}

	private void initApiKey()
	{
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		String appId = sp.getString("appid", "");
		String channelId = sp.getString("channel_id", "");
		String clientId = sp.getString("user_id", "");

		if (!"".equals(appId) && !"".equals(channelId) && !"".equals(clientId))
		{
			handler.postDelayed(runnable, 1000);
		}
		else
		{
			PushManager.startWork(getApplicationContext(),
					PushConstants.LOGIN_TYPE_API_KEY,
					BPushUtils.getMetaValue(ShowActivity.this, "api_key"));
		}
	}

	private void initLocation()
	{
		mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
		mLocationClient.registerLocationListener(myListener); // 注册监听函数
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
		option.disableCache(true);// 禁止启用缓存定位
		option.setPoiExtraInfo(true); // 是否需要POI的电话和地址等详细信息
		mLocationClient.setLocOption(option);
		mLocationClient.start();

//		if (mLocationClient != null && mLocationClient.isStarted())
//		{
			mLocationClient.requestLocation();
//		}
//		else
//		{
//			Log.d("LocSDK3", "locClient is null or not started");
//		}
	}

	@Override
	protected void onDestroy()
	{
		if (mLocationClient != null && mLocationClient.isStarted())
		{
			mLocationClient.stop();
			mLocationClient = null;
		}
		super.onDestroy();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		super.onWindowFocusChanged(hasFocus);
		myApp.setAppHeight(findViewById(R.id.waiting_img).getHeight());
	}

	Runnable runnable = new Runnable() {
		public void run()
		{
			goActivity();
		}
	};

	private void goActivity()
	{
		Intent itent = new Intent(ShowActivity.this, MainActivity.class);
		startActivity(itent);
		finish();
	}

	@Override
	protected void onNewIntent(Intent intent)
	{
		// 如果要统计Push引起的用户使用应用情况，请实现本方法，且加上这一个语句
		setIntent(intent);
		handleIntent(intent);
	}

	private void handleIntent(Intent intent)
	{
		String action = intent.getAction();

		if (BPushUtils.ACTION_RESPONSE.equals(action))
		{

			String method = intent.getStringExtra(BPushUtils.RESPONSE_METHOD);

			if (PushConstants.METHOD_BIND.equals(method))
			{
				String toastStr = "";
				int errorCode = intent.getIntExtra(BPushUtils.RESPONSE_ERRCODE,
						0);
				if (errorCode == 0)
				{
					String content = intent
							.getStringExtra(BPushUtils.RESPONSE_CONTENT);
					String appid = "";
					String channelid = "";
					String userid = "";

					try
					{
						JSONObject jsonContent = new JSONObject(content);
						JSONObject params = jsonContent
								.getJSONObject("response_params");
						appid = params.getString("appid");
						channelid = params.getString("channel_id");
						userid = params.getString("user_id");
					} catch (JSONException e)
					{
						Log.e(BPushUtils.TAG, "Parse bind json infos error: "
								+ e);
					}

					SharedPreferences sp = PreferenceManager
							.getDefaultSharedPreferences(this);
					Editor editor = sp.edit();
					editor.putString("appid", appid);
					editor.putString("channel_id", channelid);
					editor.putString("user_id", userid);
					editor.commit();
				}
				else
				{
					toastStr = "Bind Fail, Error Code: " + errorCode;
					if (errorCode == 30607)
					{
						Log.d("Bind Fail", "update channel token-----!");
					}
				}
				Log.d("Bind result", toastStr);
			}
		}
		else
		{
			Log.i(BPushUtils.TAG, "Activity normally start!");
		}
		goActivity();
	}
}
