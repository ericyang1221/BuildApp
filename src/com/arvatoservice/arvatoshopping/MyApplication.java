package com.arvatoservice.arvatoshopping;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

import android.app.Application;

import com.arvatoservice.arvatoshopping.db.CouponActiveDBHelper;
import com.arvatoservice.arvatoshopping.db.FavoriteDBHelper;
import com.arvatoservice.arvatoshopping.db.PushMsgDBHelper;
import com.arvatoservice.arvatoshopping.utils.BitmapCache;
import com.arvatoservice.arvatoshopping.utils.ImageManager;
import com.baidu.location.BDLocation;

public class MyApplication extends Application {
	private final int timeoutConnection = 10000;
	private final int timeoutSocket = 30000;
	private int appHeight;
	private int screenWidth;
	private int screenHeight;
	private int shopId = 56;
	private DefaultHttpClient mClient;
	private String rightViewLeftTitle;
	private String leftViewLeftTitle;
	private String topViewLeftTitle;
	private String bottomViewLeftTitle;
	private BitmapCache l1Cache;
	private ImageManager imageManager;
	private CouponActiveDBHelper caDBHelper;
	private FavoriteDBHelper fDBHelper;
	private PushMsgDBHelper pmDBHelper;
	private BDLocation lastKnowLocation;

	@Override
	public void onCreate()
	{
		super.onCreate();
		BasicHttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				timeoutConnection);
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		mClient = new DefaultHttpClient(httpParameters);
	}

	public int getAppHeight()
	{
		return appHeight;
	}

	public void setAppHeight(int appHeight)
	{
		this.appHeight = appHeight;
	}

	public int getScreenWidth()
	{
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth)
	{
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight()
	{
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight)
	{
		this.screenHeight = screenHeight;
	}

	public int getShopId()
	{
		return shopId;
	}

	public void setShopId(int shopId)
	{
		this.shopId = shopId;
	}

	public DefaultHttpClient getClient()
	{
		return mClient;
	}

	public String getRightViewLeftTitle()
	{
		return rightViewLeftTitle;
	}

	public void setRightViewLeftTitle(String rightViewLeftTitle)
	{
		this.rightViewLeftTitle = rightViewLeftTitle;
	}

	public String getLeftViewLeftTitle()
	{
		return leftViewLeftTitle;
	}

	public void setLeftViewLeftTitle(String leftViewLeftTitle)
	{
		this.leftViewLeftTitle = leftViewLeftTitle;
	}

	public BitmapCache getCache()
	{
		if (l1Cache == null)
		{
			l1Cache = new BitmapCache();
		}
		return l1Cache;
	}

	public ImageManager getImageManager()
	{
		if (imageManager == null)
		{
			imageManager = new ImageManager(this);
		}
		return imageManager;
	}

	public CouponActiveDBHelper getCouponActiveDBHelper()
	{
		if (caDBHelper == null)
		{
			caDBHelper = new CouponActiveDBHelper(this);
		}
		return caDBHelper;
	}

	public FavoriteDBHelper getFavoriteDBHelper()
	{
		if (fDBHelper == null)
		{
			fDBHelper = new FavoriteDBHelper(this);
		}
		return fDBHelper;
	}

	public PushMsgDBHelper getPushMsgDBHelper()
	{
		if (pmDBHelper == null)
		{
			pmDBHelper = new PushMsgDBHelper(this);
		}
		return pmDBHelper;
	}

	public BDLocation getLastKnowLocation()
	{
		return lastKnowLocation;
	}

	public void setLastKnowLocation(BDLocation lastKnowLocation)
	{
		this.lastKnowLocation = lastKnowLocation;
	}

	public String getTopViewLeftTitle()
	{
		return topViewLeftTitle;
	}

	public void setTopViewLeftTitle(String topViewLeftTitle)
	{
		this.topViewLeftTitle = topViewLeftTitle;
	}

	public String getBottomViewLeftTitle()
	{
		return bottomViewLeftTitle;
	}

	public void setBottomViewLeftTitle(String bottomViewLeftTitle)
	{
		this.bottomViewLeftTitle = bottomViewLeftTitle;
	}
}
