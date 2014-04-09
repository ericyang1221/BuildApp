package com.arvatoservice.arvatoshopping.db.abs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.arvatoservice.arvatoshopping.utils.Constants;

public class BaseSQLiteOpenHelper extends SQLiteOpenHelper {
	protected final static String DATABASE_NAME = "BUILDAPP";
	protected final static int DATABASE_VERSION = Constants.DATABASE_VERSION;

	protected final static String COUPONACTIVE_TBL_TABLE_NAME = "COUPONACTIVE_TBL";
	protected final static String COUPONACTIVE_TBL_COUPONID = "COUPONID";
	protected final static String COUPONACTIVE_TBL_SELLERID = "SELLERID";
	protected final static String COUPONACTIVE_TBL_ACTIVE_TIME = "ACTIVE_TIME";
	protected final static String COUPONACTIVE_TBL_TIME_RANGE = "TIME_RANGE";
	protected final static String COUPONACTIVE_TBL_STORE_NAME = "STORE_NAME";
	protected final static String COUPONACTIVE_TBL_MARK = "MARK";

	protected final static String FAVORITE_TBL_TABLE_NAME = "FAVORITE_TBL";
	protected final static String FAVORITE_TBL_ID = "ID";
	protected final static String FAVORITE_TBL_NAME = "NAME";
	protected final static String FAVORITE_TBL_DESC = "DESC";
	protected final static String FAVORITE_TBL_IMAGE_PATH = "IMAGE_PATH";
	protected final static String FAVORITE_TBL_ARVATOURL = "ARVATOURL";
	protected final static String FAVORITE_TBL_MARK = "MARK";
	protected final static String FAVORITE_TBL_MODE = "MODE";
	protected final static String FAVORITE_TBL_PRODUCT_ID = "PRODUCT_ID";
	protected final static String FAVORITE_TBL_COUPON_ID = "COUPON_ID";
	protected final static String FAVORITE_TBL_STORE_ID = "STORE_ID";
	protected final static String FAVORITE_TBL_SELLER_ID = "SELLER_ID";
	protected final static String FAVORITE_TBL_ADD_TIME = "ADD_TIME";

	protected final static String SCANHISTORY_TBL_TABLE_NAME = "SCANHISTORY_TBL";
	protected final static String SCANHISTORY_TBL_ID = "ID";
	protected final static String SCANHISTORY_TBL_ARVATOURL = "ARVATOURL";
	protected final static String SCANHISTORY_TBL_MARK = "MARK";
	protected final static String SCANHISTORY_TBL_IMG_PATH = "IMG_PATH";
	protected final static String SCANHISTORY_TBL_NAME = "NAME";
	protected final static String SCANHISTORY_TBL_BROWSE_TIME = "BROWSE_TIME";

	protected final static String SHOPPINGCART_TBL_TABLE_NAME = "SHOPPINGCART_TBL";
	protected final static String SHOPPINGCART_TBL_PRODUCT_ID = "PRODUCT_ID";
	protected final static String SHOPPINGCART_TBL_USER_ID = "USER_ID";
	protected final static String SHOPPINGCART_TBL_COUNT = "COUNT";

	protected final static String PUSHMSG_TBL_TABLE_NAME = "PUSHMSG_TBL";
	protected final static String PUSHMSG_TBL_ID = "ID";
	protected final static String PUSHMSG_TBL_TYPE = "TYPE";
	protected final static String PUSHMSG_TBL_MESSAGE = "MESSAGE";
	protected final static String PUSHMSG_TBL_TITLE = "TITLE";
	protected final static String PUSHMSG_TBL_RID = "RID";
	protected final static String PUSHMSG_TBL_IMG_URL = "IMG_URL";
	protected final static String PUSHMSG_TBL_RECEIVE_TIME = "RECEIVE_TIME";

	public BaseSQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version)
	{
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		createCouponActiveTbl(db);
		createFavoriteTbl(db);
		createScanHistoryTbl(db);
		createShoppingcartTbl(db);
		createPushMsgTbl(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		upgradeCouponActiveTbl(db, oldVersion, newVersion);
		upgradeFavoriteTbl(db, oldVersion, newVersion);
		upgradeScanHistoryTbl(db, oldVersion, newVersion);
		upgradeShoppingcartTbl(db, oldVersion, newVersion);
		upgradePushMsgTbl(db, oldVersion, newVersion);
	}

	protected void createCouponActiveTbl(SQLiteDatabase db)
	{
		String sql = "Create table IF NOT EXISTS "
				+ COUPONACTIVE_TBL_TABLE_NAME + "(" + COUPONACTIVE_TBL_COUPONID
				+ " integer," + COUPONACTIVE_TBL_SELLERID + " integer,"
				+ COUPONACTIVE_TBL_ACTIVE_TIME + " integer,"
				+ COUPONACTIVE_TBL_TIME_RANGE + " integer,"
				+ COUPONACTIVE_TBL_STORE_NAME + " text,"
				+ COUPONACTIVE_TBL_MARK + " text);";
		db.execSQL(sql);
	}

	protected void upgradeCouponActiveTbl(SQLiteDatabase db, int oldVersion,
			int newVersion)
	{
		Log.d(COUPONACTIVE_TBL_TABLE_NAME, "From " + oldVersion + " to "
				+ newVersion);
		String sql = "DROP TABLE IF EXISTS " + COUPONACTIVE_TBL_TABLE_NAME;
		db.execSQL(sql);
		createCouponActiveTbl(db);
	}

	protected void createFavoriteTbl(SQLiteDatabase db)
	{
		String sql = "Create table IF NOT EXISTS " + FAVORITE_TBL_TABLE_NAME
				+ "(" + FAVORITE_TBL_ID + " integer PRIMARY KEY,"
				+ FAVORITE_TBL_NAME + " text," + FAVORITE_TBL_DESC + " text,"
				+ FAVORITE_TBL_IMAGE_PATH + " text," + FAVORITE_TBL_ARVATOURL
				+ " text UNIQUE," + FAVORITE_TBL_MARK + " text,"
				+ FAVORITE_TBL_MODE + " integer," + FAVORITE_TBL_PRODUCT_ID
				+ " text," + FAVORITE_TBL_COUPON_ID + " text,"
				+ FAVORITE_TBL_STORE_ID + " text," + FAVORITE_TBL_SELLER_ID
				+ " text," + FAVORITE_TBL_ADD_TIME + " integer);";
		db.execSQL(sql);
	}

	protected void upgradeFavoriteTbl(SQLiteDatabase db, int oldVersion,
			int newVersion)
	{
		String sql = "DROP TABLE IF EXISTS " + FAVORITE_TBL_TABLE_NAME;
		db.execSQL(sql);
		createFavoriteTbl(db);
	}

	protected void createScanHistoryTbl(SQLiteDatabase db)
	{
		String sql = "Create table IF NOT EXISTS " + SCANHISTORY_TBL_TABLE_NAME
				+ "(" + SCANHISTORY_TBL_ID + " integer PRIMARY KEY,"
				+ SCANHISTORY_TBL_ARVATOURL + " text UNIQUE,"
				+ SCANHISTORY_TBL_MARK + " text," + SCANHISTORY_TBL_IMG_PATH
				+ " text," + SCANHISTORY_TBL_NAME + " text,"
				+ SCANHISTORY_TBL_BROWSE_TIME + " integer);";
		db.execSQL(sql);
	}

	protected void upgradeScanHistoryTbl(SQLiteDatabase db, int oldVersion,
			int newVersion)
	{
		String sql = "DROP TABLE IF EXISTS " + SCANHISTORY_TBL_TABLE_NAME;
		db.execSQL(sql);
		createScanHistoryTbl(db);
	}

	protected void createShoppingcartTbl(SQLiteDatabase db)
	{
		String sql = "Create table IF NOT EXISTS "
				+ SHOPPINGCART_TBL_TABLE_NAME + "("
				+ SHOPPINGCART_TBL_PRODUCT_ID + " integer,"
				+ SHOPPINGCART_TBL_USER_ID + " integer,"
				+ SHOPPINGCART_TBL_COUNT + " integer );";
		db.execSQL(sql);
	}

	protected void upgradeShoppingcartTbl(SQLiteDatabase db, int oldVersion,
			int newVersion)
	{
		String sql = "DROP TABLE IF EXISTS " + SHOPPINGCART_TBL_TABLE_NAME;
		db.execSQL(sql);
		createShoppingcartTbl(db);
	}

	protected void createPushMsgTbl(SQLiteDatabase db)
	{
		String sql = "Create table IF NOT EXISTS " + PUSHMSG_TBL_TABLE_NAME
				+ "(" + PUSHMSG_TBL_ID + " integer PRIMARY KEY,"
				+ PUSHMSG_TBL_TYPE + " integer," + PUSHMSG_TBL_MESSAGE
				+ " text," + PUSHMSG_TBL_TITLE + " text," + PUSHMSG_TBL_IMG_URL
				+ " text," + PUSHMSG_TBL_RID + " integer,"
				+ PUSHMSG_TBL_RECEIVE_TIME + " integer);";
		db.execSQL(sql);
	}

	protected void upgradePushMsgTbl(SQLiteDatabase db, int oldVersion,
			int newVersion)
	{
		String sql = "DROP TABLE IF EXISTS " + PUSHMSG_TBL_TABLE_NAME;
		db.execSQL(sql);
		createPushMsgTbl(db);
	}
}
