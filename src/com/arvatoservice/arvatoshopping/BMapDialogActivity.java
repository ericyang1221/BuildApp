package com.arvatoservice.arvatoshopping;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.arvatoservice.arvatoshopping.beans.ShopAddressListItemBean;
import com.arvatoservice.arvatoshopping.map.BMapLocationListener;
import com.arvatoservice.arvatoshopping.map.BMapSearchListener;
import com.arvatoservice.arvatoshopping.map.ShopaddressOverlay;
import com.arvatoservice.arvatoshopping.utils.Constants;
import com.arvatoservice.arvatoshopping.utils.Utils;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.platform.comapi.basestruct.GeoPoint;

/**
 * 
 * @author Eric
 * 
 */
public class BMapDialogActivity extends Activity {
	private BMapManager mBMapMan = null;
	private MapView mMapView = null;
	private MyApplication myApp;
	private ShopAddressListItemBean saliBean;
	private MKSearch mMKSearch = null;
	private LocationClient mLocationClient = null;
	private BDLocationListener myListener = new BMapLocationListener(this);
	private GeoPoint targetPoint;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init(new MyGeneralListener());
		// 注意：请在试用setContentView前初始化BMapManager对象，否则会报错
		setContentView(R.layout.b_map_dialog);

		myApp = (MyApplication) getApplication();
		saliBean = (ShopAddressListItemBean) getIntent().getSerializableExtra(
				"shopAddressListItemBean");
		TextView address = (TextView) findViewById(R.id.b_map_d_address);
		TextView phone = (TextView) findViewById(R.id.b_map_d_phone);
		TextView name = (TextView) findViewById(R.id.b_map_d_name);
		address.setText(saliBean.getAddress());
		phone.setText(saliBean.getPhone());
		name.setText(saliBean.getName());

		findViewById(R.id.doCall).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ saliBean.getPhone()));
				startActivity(intent);
			}
		});

		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.width = (int) (myApp.getScreenWidth() * 0.9);
		lp.gravity = Gravity.LEFT;
		getWindow().setAttributes(lp);

		mMapView = (MapView) findViewById(R.id.bmapsView);
		mMapView.setBuiltInZoomControls(false);
		// 设置启用内置的缩放控件
		MapController mMapController = mMapView.getController();
		// 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		targetPoint = new GeoPoint((int) (saliBean.getLatitude() * 1E6),
				(int) (saliBean.getLongitude() * 1E6));
		// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		mMapController.setCenter(targetPoint);// 设置地图中心点
		mMapController.setZoom(16);// 设置地图zoom级别

		// 用给定的经纬度构造GeoPoint，单位是微度 (度 * 1E6)
		// 准备overlay图像数据，根据实情情况修复
		Drawable mark = getResources().getDrawable(R.drawable.map_location);
		// 用OverlayItem准备Overlay数据
		OverlayItem item1 = new OverlayItem(targetPoint, "item1", "item1");
		// item1.setMarker(mark);

		// 创建IteminizedOverlay
		ShopaddressOverlay itemOverlay = new ShopaddressOverlay(mark, mMapView);
		// 将IteminizedOverlay添加到MapView中
		// 注意：目前IteminizedOverlay不支持多实例，MapView中只能有一个IteminizedOverlay实例
		mMapView.getOverlays().clear();
		mMapView.getOverlays().add(itemOverlay);

		// 现在所有准备工作已准备好，使用以下方法管理overlay.
		// 添加overlay, 当批量添加Overlay时使用addItem(List<OverlayItem>)效率更高
		itemOverlay.addItem(item1);
		mMapView.refresh();
		// 删除overlay .
		// itemOverlay.removeItem(itemOverlay.getItem(0));
		// mMapView.refresh();
		// 清除overlay
		// itemOverlay.removeAll();
		// mMapView.refresh();

		findViewById(R.id.doNav).setOnClickListener(new OnClickListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				showDialog(Constants.NAVIGATION_LOADING_DIALOG);
				BDLocation loc = myApp.getLastKnowLocation();
				if (loc != null) {
					doNav(loc);
				} else {
					mLocationClient = new LocationClient(
							getApplicationContext()); // 声明LocationClient类
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
				}
			}
		});

		MKMapViewListener ml = new MKMapViewListener() {
			@Override
			public void onClickMapPoi(MapPoi arg0) {
			}

			@Override
			public void onGetCurrentMap(Bitmap b) {
				String shareText = saliBean.getName() + "\n"
						+ saliBean.getAddress() + "\n" + saliBean.getPhone();
				Utils.shareQrCode(BMapDialogActivity.this, shareText, b);
			}

			@Override
			public void onMapAnimationFinish() {
			}

			@Override
			public void onMapMoveFinish() {
			}

			@Override
			public void onMapLoadFinish() {
				// TODO Auto-generated method stub

			}
		};
		mMapView.regMapViewListener(mBMapMan, ml);
		findViewById(R.id.doShare).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mMapView.getCurrentMap();
			}
		});
	}

	public void doNav(BDLocation loc) {
		mMKSearch = new MKSearch();
		mMKSearch.init(mBMapMan, new BMapSearchListener(
				BMapDialogActivity.this, mMapView));
		MKPlanNode start = new MKPlanNode();
		int startLat = (int) (loc.getLatitude() * 1E6);
		int startLon = (int) (loc.getLongitude() * 1E6);
		start.pt = new GeoPoint(startLat, startLon);
		MKPlanNode end = new MKPlanNode();
		end.pt = targetPoint;// 设置驾车路线搜索策略，时间优先、费用最少或距离最短
		mMKSearch.setDrivingPolicy(MKSearch.ECAR_TIME_FIRST);
		mMKSearch.drivingSearch(null, start, null, end);
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		if (mBMapMan != null) {
			mBMapMan.start();
		}
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		mMapView.destroy();
		if (mBMapMan != null) {
			mBMapMan.destroy();
			mBMapMan = null;
		}
		if (mLocationClient != null && mLocationClient.isStarted()) {
			mLocationClient.stop();
			mLocationClient = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		if (mBMapMan != null) {
			mBMapMan.stop();
		}
		super.onPause();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case Constants.NAVIGATION_LOADING_DIALOG:
			ProgressDialog navDialog = new ProgressDialog(this);
			navDialog
					.setMessage(getString(R.string.navigation_loading_dialog_info));
			navDialog.setIndeterminate(true);
			navDialog.setCancelable(true);
			navDialog.setCanceledOnTouchOutside(false);
			return navDialog;
		}
		return null;
	}

	class MyGeneralListener implements MKGeneralListener {

		@Override
		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
				Toast.makeText(getApplicationContext(), "您的网络出错啦！",
						Toast.LENGTH_LONG).show();
			} else if (iError == MKEvent.ERROR_NETWORK_DATA) {
				Toast.makeText(getApplicationContext(), "输入正确的检索条件！",
						Toast.LENGTH_LONG).show();
			}
			// ...
		}

		@Override
		public void onGetPermissionState(int iError) {
			// 非零值表示key验证未通过
			if (iError != 0) {
				// 授权Key错误：
				Toast.makeText(
						getApplicationContext(),
						"授权Key失败,并检查您的网络连接是否正常！error: "
								+ iError, Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplicationContext(), "key认证成功",
						Toast.LENGTH_LONG).show();
			}
		}
	}
}
