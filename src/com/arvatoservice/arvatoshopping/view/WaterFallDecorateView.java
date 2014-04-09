package com.arvatoservice.arvatoshopping.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.arvatoservice.arvatoshopping.BuildConfig;
import com.arvatoservice.arvatoshopping.MyApplication;
import com.arvatoservice.arvatoshopping.ProductDetailActivity;
import com.arvatoservice.arvatoshopping.R;
import com.arvatoservice.arvatoshopping.asynctask.ProductDetailBeanFetcher;
import com.arvatoservice.arvatoshopping.beans.ProListBean;
import com.arvatoservice.arvatoshopping.beans.ProListItemBean;
import com.arvatoservice.arvatoshopping.beans.ProductDetailBean;
import com.arvatoservice.arvatoshopping.beans.abs.HttpResponseBean;
import com.arvatoservice.arvatoshopping.exceptions.JSONObjectCastException;
import com.arvatoservice.arvatoshopping.handlers.FlowViewHandler;
import com.arvatoservice.arvatoshopping.utils.Constants;
import com.arvatoservice.arvatoshopping.utils.SecondaryUrl;
import com.arvatoservice.arvatoshopping.utils.Utils;
import com.arvatoservice.arvatoshopping.utils.WaterFallOption;
import com.arvatoservice.arvatoshopping.view.WaterFallView.OnScrollListener;
import com.arvatoservice.arvatoshopping.view.abs.SetableView;

public class WaterFallDecorateView extends RelativeLayout implements
		OnScrollListener, SetableView {
	private final String SECONDARY_URL = "product/list?type=?&pageNum=?&pageSize=?&sellerId=?&ids=?";
	private String TAG = getClass().getSimpleName();
	private Activity activity;
	private final int COLUMN_COUNT = 2;
	private MyApplication myApp;
	private WaterFallView mWaterFallView;
	private Handler mFlowViewHandler;
	private int PageNum = 1;
	private int toastCount = 0;

	public WaterFallDecorateView(Context context, int layoutWidth) {
		super(context);
		initLayout(context, layoutWidth);
	}

	private void initLayout(Context context, int layoutWidth) {
		LayoutInflater.from(context).inflate(R.layout.waterfall_view, this,
				true);
		activity = (Activity) context;
		myApp = (MyApplication) activity.getApplication();
		// 所有布局在设置间隙的时候都用了padding ,所以在计算宽度的时候,不需要考虑 间隙.
		int item_width = layoutWidth / COLUMN_COUNT;

		// 1 初始化waterfall
		mWaterFallView = (WaterFallView) findViewById(R.id.waterfall_scroll);
		// 2 初始化显示容器
		LinearLayout waterfall_container = (LinearLayout) findViewById(R.id.waterfall_container);
		// 3,设置滚动监听
		mWaterFallView.setOnScrollListener(this);
		// 4,实例一个设置
		WaterFallOption mOption = new WaterFallOption(item_width, COLUMN_COUNT);

		// 5,初始化android瀑布流
		mWaterFallView.initWaterFall(mOption, waterfall_container);

		mFlowViewHandler = new FlowViewHandler(mWaterFallView);

		onLoadData(PageNum);

		findViewById(R.id.search_submit).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						mWaterFallView.reset();
					}
				});
	}

	private void onLoadData(final int PageNum) {
		mWaterFallView.State = WaterFallView.State_Running;
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// 此处应改为发送请求，获取图片url的list
					// Thread.sleep(3000);

					// HttpRequestHelper hth = new HttpRequestHelper(activity);
					// JSONObject jsonObj = hth
					// .sendRequestAndReturnJson(getUrl(PageNum));
					AssetManager am = activity.getAssets();
					InputStream is = am.open("product_" + PageNum+".json");
					JSONObject jsonObj = new JSONObject();
					try {
						byte[] buffer = new byte[is.available()];
						is.read(buffer);
						String json = new String(buffer, "utf8");
						jsonObj = new JSONObject(json);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
					ProListBean plBean;
					try {
						plBean = new ProListBean(jsonObj);
					} catch (JSONObjectCastException e) {
						activity.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(
										activity,
										activity.getString(R.string.no_product),
										Toast.LENGTH_LONG).show();
							}
						});
						mWaterFallView.State = WaterFallView.State_Empty;
						plBean = new ProListBean(Constants.MODE_ERROR_DATA_BENA);
					} finally {
						activity.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								findViewById(R.id.water_fall_progressbar)
										.setVisibility(View.GONE);
							}
						});
					}

					ArrayList<ProListItemBean> mProItemBeanList;
					if (!plBean.isError() && !plBean.isEmpty()) {
						mProItemBeanList = plBean.getProItemBeanList();
					} else {
						mProItemBeanList = null;
					}
					addItemToContainer(mProItemBeanList);
				} catch (Exception e) {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							mWaterFallView.State = WaterFallView.State_Empty;
							// netError.setVisibility(View.VISIBLE);
							// emptyBar.setVisibility(View.GONE);
							// dismissDialogIfExist(Constants.LOADING_DIALOG);
						}
					});
				}

				// 模拟生成数据.
				// ArrayList<ProListItemBean> mProItemBeanList = new
				// ArrayList<ProListItemBean>();
				// for (int i = 0; i < PageSize ; i++) {
				// mProItemBeanList.add(new
				// ProListItemBean(ImageMock.imageThumbUrls[i],"第" + i));
				// }

				// mWaterFallView.State = WaterFallView.State_OK;

			}
		}).start();

	}

	/**
	 * 添加了点击删除事件. 但是需要考虑数据同步问题.
	 * 
	 * @param pageindex
	 * @param mPageSize
	 */
	private void addItemToContainer(ArrayList<ProListItemBean> mProItemBeanList) {
		int currentIndex = mWaterFallView.getSize();
		// 限制最大加载值.
		int MaxPictureCount = mWaterFallView.mOption.MaxPictureCount;

		if (mProItemBeanList == null || mProItemBeanList.size() == 0
				|| mProItemBeanList.get(0).isEmpty()
				|| mProItemBeanList.get(0).isError()) {
			PageNum--;
			Log.e("AddItemToContainer", "mProItemBeanList=" + mProItemBeanList);
			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Log.e("AddItemToContainer", "AddItemToContainer PageNum="
							+ PageNum);
					if (PageNum == 0) {
						mWaterFallView.State = WaterFallView.State_Empty;
						Log.e("AddItemToContainer", "PageNum=1");
						// netError.setVisibility(View.GONE);
						// emptyBar.setVisibility(View.VISIBLE);
					} else {
						// netError.setVisibility(View.GONE);
						// emptyBar.setVisibility(View.GONE);
						mWaterFallView.State = WaterFallView.State_OK;
						Log.e("AddItemToContainer", "PageNum=" + PageNum);
						showToast("没有更多的商品...");
					}
				}
			});
			return;
		} else {
			mWaterFallView.State = WaterFallView.State_OK;
			Log.e("AddItemToContainer", mProItemBeanList.size() + "");
			for (int i = currentIndex, j = 0; j < mProItemBeanList.size()
					&& i < MaxPictureCount; i++, j++) {
				Log.e("AddItemToContainer", "State_OK" + i);
				addImageLayout(mProItemBeanList.get(j), i);
			}
		}

	}

	/**
	 * @param filename
	 * @param position
	 */
	private void addImageLayout(ProListItemBean mSimple, int id) {
		// FlowViewLayout item = new FlowViewLayout(this ,
		// mSimple.getContent());
		FlowViewLayout item = new FlowViewLayout(activity, mSimple);
		// 唯一标识.
		item.setId(id);
		item.setPadding(0, 2, 0, 2);
		// item.setFilePath(Constants.IMG_URL + mSimple.getProImgMain());
		// TODO hack new api
		item.setFilePath(mSimple.getProImgMain());
		item.setItemWidth(mWaterFallView.mOption.Column_Width);

		item.setErrorImage(mWaterFallView.mOption.ErrorImage);
		item.setViewHandler(mFlowViewHandler);
		item.setItemListener(mItemListener);

		item.LoadImage(mWaterFallView.mExecutorService, false);
	}

	public void showToast(String message) {
		Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
	}

	protected String getUrl(int pageNum) {
		int pageSize = Constants.PRODUCT_LIST_PAGE_SIZE;
		SecondaryUrl su = new SecondaryUrl(SECONDARY_URL, new String[] { "0",
				String.valueOf(pageNum), String.valueOf(pageSize),
				String.valueOf(myApp.getShopId()), "" },
				Constants.SECONDARYURL_MODE_PARA);
		String url = Constants.HTTP_URL + su.toString();
		// TODO hack new api
		url = "http://118.145.20.146/buildapp/json/10001/10001_10_" + pageNum
				+ ".json";
		return url;
	}

	FlowViewLayout.onItemListener mItemListener = new FlowViewLayout.onItemListener() {
		@Override
		public void onItemLongClick(int id) {
			// showToast("id:" + id);
			// mWaterFallView.mFlowList.get(id);
		}

		@Override
		public void onItemClick(int id) {
			ProductDetailBeanFetcher pdbf = new ProductDetailBeanFetcher(
					activity, id);
			pdbf.execute(WaterFallDecorateView.this,
					new String[] { String.valueOf(id), null });
			activity.showDialog(Constants.LOADING_DIALOG);
		}
	};

	@Override
	public void onBottom() {
		Log.i(TAG, "onBottom");
		if (mWaterFallView.State == WaterFallView.State_OK) {
			showToast("正在加载请稍后...");
			Log.e("onBottom", "PageNum" + (int) (PageNum + 1));
			onLoadData(++PageNum);
		} else if (mWaterFallView.State == WaterFallView.State_Running) {
			if (toastCount % 2 == 0) {
				showToast("拼了老命努力加载中...");
			} else {
				showToast("加载中，请歇息...");
			}
			toastCount++;
		} else {

		}

	}

	@Override
	public void onTop() {
		if (BuildConfig.DEBUG)
			Log.i(TAG, "onTop");
	}

	@Override
	public void onScroll() {
		if (BuildConfig.DEBUG)
			Log.i(TAG, "onScroll");
	}

	@Override
	public void onAutoScroll(int l, int t, int oldl, int oldt) {
	}

	@Override
	public void setData(HttpResponseBean hrb) {
		Utils.dismissDialogIfExist(activity, Constants.LOADING_DIALOG);
		ProductDetailBean pdb = (ProductDetailBean) hrb;
		if (pdb.getRet() == 0) {
			Intent intent = new Intent(activity, ProductDetailActivity.class);
			intent.putExtra("productDetailBean", (Serializable) pdb);
			activity.startActivity(intent);
		} else {
			Toast.makeText(activity, pdb.getMsg(), Toast.LENGTH_SHORT).show();
		}
	}

}
