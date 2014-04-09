package com.arvatoservice.arvatoshopping.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.arvatoservice.arvatoshopping.beans.FavoriteItemBean;
import com.arvatoservice.arvatoshopping.db.abs.BaseSQLiteOpenHelper;
import com.arvatoservice.arvatoshopping.utils.Constants;

/**
 * 
 * @author Eric
 * 
 */
public class FavoriteDBHelper extends BaseSQLiteOpenHelper {

	public FavoriteDBHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public List<FavoriteItemBean> selectAllByMode(int mode)
	{
		List<FavoriteItemBean> fiBeanList = new ArrayList<FavoriteItemBean>();
		String[] columns = new String[] { FAVORITE_TBL_ID, FAVORITE_TBL_NAME,
				FAVORITE_TBL_DESC, FAVORITE_TBL_IMAGE_PATH,
				FAVORITE_TBL_ARVATOURL, FAVORITE_TBL_MARK, FAVORITE_TBL_MODE,
				FAVORITE_TBL_PRODUCT_ID, FAVORITE_TBL_COUPON_ID,
				FAVORITE_TBL_STORE_ID, FAVORITE_TBL_SELLER_ID,
				FAVORITE_TBL_ADD_TIME };
		String whereClause = null;
		String[] whereArgs = null;
		if (Constants.FAV_MODE_ALL != mode)
		{
			whereClause = FAVORITE_TBL_MODE + "=?";
			whereArgs = new String[] { String.valueOf(mode) };
		}
		String groupBy = null;
		String having = null;
		String orderBy = FAVORITE_TBL_ADD_TIME + " DESC";
		String limit = null;
		Cursor c = this.getReadableDatabase().query(FAVORITE_TBL_TABLE_NAME,
				columns, whereClause, whereArgs, groupBy, having, orderBy,
				limit);
		c.moveToFirst();
		for (int i = 0; i < c.getCount(); i++)
		{
			int id = c.getInt(0);
			String name = c.getString(1);
			String desc = c.getString(2);
			String imagePath = c.getString(3);
			String arvatoUrl = c.getString(4);
			String mark = c.getString(5);
			int rmode = c.getInt(6);
			int productId = c.getInt(7);
			int couponId = c.getInt(8);
			int storeId = c.getInt(9);
			int sellerId = c.getInt(10);
			long addTime = c.getLong(11);
			FavoriteItemBean bhiBean = new FavoriteItemBean(id, name, desc,
					imagePath, arvatoUrl, mark, rmode, productId, couponId,
					storeId, sellerId, addTime);
			fiBeanList.add(bhiBean);
			c.moveToNext();
		}
		c.close();
		c = null;
		this.getReadableDatabase().close();
		return fiBeanList;
	}

	public List<FavoriteItemBean> selectByArvatoUrl(String arvatoUrl)
	{
		List<FavoriteItemBean> fiBeanList = new ArrayList<FavoriteItemBean>();
		String[] columns = new String[] { FAVORITE_TBL_ID, FAVORITE_TBL_NAME,
				FAVORITE_TBL_DESC, FAVORITE_TBL_IMAGE_PATH,
				FAVORITE_TBL_ARVATOURL, FAVORITE_TBL_MARK, FAVORITE_TBL_MODE,
				FAVORITE_TBL_PRODUCT_ID, FAVORITE_TBL_COUPON_ID,
				FAVORITE_TBL_STORE_ID, FAVORITE_TBL_SELLER_ID,
				FAVORITE_TBL_ADD_TIME };
		String whereClause = FAVORITE_TBL_ARVATOURL + "=?";
		String[] whereArgs = new String[] { arvatoUrl.trim() };
		String groupBy = null;
		String having = null;
		String orderBy = null;
		String limit = null;
		Cursor c = this.getReadableDatabase().query(FAVORITE_TBL_TABLE_NAME,
				columns, whereClause, whereArgs, groupBy, having, orderBy,
				limit);
		c.moveToFirst();
		for (int i = 0; i < c.getCount(); i++)
		{
			int id = c.getInt(0);
			String name = c.getString(1);
			String desc = c.getString(2);
			String imagePath = c.getString(3);
			String mArvatoUrl = c.getString(4);
			String mark = c.getString(5);
			int mode = c.getInt(6);
			int productId = c.getInt(7);
			int couponId = c.getInt(8);
			int storeId = c.getInt(9);
			int sellerId = c.getInt(10);
			long addTime = c.getLong(11);
			FavoriteItemBean bhiBean = new FavoriteItemBean(id, name, desc,
					imagePath, mArvatoUrl, mark, mode, productId, couponId,
					storeId, sellerId, addTime);
			fiBeanList.add(bhiBean);
			c.moveToNext();
		}
		c.close();
		c = null;
		this.getReadableDatabase().close();
		return fiBeanList;
	}

	public List<FavoriteItemBean> selectByCouponId(int cid)
	{
		List<FavoriteItemBean> fiBeanList = new ArrayList<FavoriteItemBean>();
		String[] columns = new String[] { FAVORITE_TBL_ID, FAVORITE_TBL_NAME,
				FAVORITE_TBL_DESC, FAVORITE_TBL_IMAGE_PATH,
				FAVORITE_TBL_ARVATOURL, FAVORITE_TBL_MARK, FAVORITE_TBL_MODE,
				FAVORITE_TBL_PRODUCT_ID, FAVORITE_TBL_COUPON_ID,
				FAVORITE_TBL_STORE_ID, FAVORITE_TBL_SELLER_ID,
				FAVORITE_TBL_ADD_TIME };
		String whereClause = FAVORITE_TBL_COUPON_ID + "=?";
		String[] whereArgs = new String[] { String.valueOf(cid) };
		String groupBy = null;
		String having = null;
		String orderBy = null;
		String limit = null;
		Cursor c = this.getReadableDatabase().query(FAVORITE_TBL_TABLE_NAME,
				columns, whereClause, whereArgs, groupBy, having, orderBy,
				limit);
		c.moveToFirst();
		for (int i = 0; i < c.getCount(); i++)
		{
			int id = c.getInt(0);
			String name = c.getString(1);
			String desc = c.getString(2);
			String imagePath = c.getString(3);
			String mArvatoUrl = c.getString(4);
			String mark = c.getString(5);
			int mode = c.getInt(6);
			int productId = c.getInt(7);
			int couponId = c.getInt(8);
			int storeId = c.getInt(9);
			int sellerId = c.getInt(10);
			long addTime = c.getLong(11);
			FavoriteItemBean bhiBean = new FavoriteItemBean(id, name, desc,
					imagePath, mArvatoUrl, mark, mode, productId, couponId,
					storeId, sellerId, addTime);
			fiBeanList.add(bhiBean);
			c.moveToNext();
		}
		c.close();
		c = null;
		this.getReadableDatabase().close();
		return fiBeanList;
	}

	public List<FavoriteItemBean> selectByCouponIdAndSellerId(int cid, int sid)
	{
		List<FavoriteItemBean> fiBeanList = new ArrayList<FavoriteItemBean>();
		String[] columns = new String[] { FAVORITE_TBL_ID, FAVORITE_TBL_NAME,
				FAVORITE_TBL_DESC, FAVORITE_TBL_IMAGE_PATH,
				FAVORITE_TBL_ARVATOURL, FAVORITE_TBL_MARK, FAVORITE_TBL_MODE,
				FAVORITE_TBL_PRODUCT_ID, FAVORITE_TBL_COUPON_ID,
				FAVORITE_TBL_STORE_ID, FAVORITE_TBL_SELLER_ID,
				FAVORITE_TBL_ADD_TIME };
		String whereClause = FAVORITE_TBL_COUPON_ID + "=? and "
				+ FAVORITE_TBL_SELLER_ID + "=?";
		String[] whereArgs = new String[] { String.valueOf(cid),
				String.valueOf(sid) };
		String groupBy = null;
		String having = null;
		String orderBy = null;
		String limit = null;
		Cursor c = this.getReadableDatabase().query(FAVORITE_TBL_TABLE_NAME,
				columns, whereClause, whereArgs, groupBy, having, orderBy,
				limit);
		c.moveToFirst();
		for (int i = 0; i < c.getCount(); i++)
		{
			int id = c.getInt(0);
			String name = c.getString(1);
			String desc = c.getString(2);
			String imagePath = c.getString(3);
			String mArvatoUrl = c.getString(4);
			String mark = c.getString(5);
			int mode = c.getInt(6);
			int productId = c.getInt(7);
			int couponId = c.getInt(8);
			int storeId = c.getInt(9);
			int sellerId = c.getInt(10);
			long addTime = c.getLong(11);
			FavoriteItemBean bhiBean = new FavoriteItemBean(id, name, desc,
					imagePath, mArvatoUrl, mark, mode, productId, couponId,
					storeId, sellerId, addTime);
			fiBeanList.add(bhiBean);
			c.moveToNext();
		}
		c.close();
		c = null;
		this.getReadableDatabase().close();
		return fiBeanList;
	}

	public long insert(FavoriteItemBean fiBean)
	{
		if (fiBean == null)
		{
			throw new IllegalArgumentException("FavoriteItemBean");
		}
		ContentValues values = new ContentValues();
		// values.put(ID, fiBean.getId());
		values.put(FAVORITE_TBL_NAME, fiBean.getName());
		values.put(FAVORITE_TBL_DESC, fiBean.getDesc());
		values.put(FAVORITE_TBL_IMAGE_PATH, fiBean.getImagePath());
		values.put(FAVORITE_TBL_ARVATOURL, fiBean.getArvatoUrl());
		values.put(FAVORITE_TBL_MARK, fiBean.getMark());
		values.put(FAVORITE_TBL_MODE, fiBean.getMode());
		values.put(FAVORITE_TBL_PRODUCT_ID, fiBean.getProductId());
		values.put(FAVORITE_TBL_COUPON_ID, fiBean.getCouponId());
		values.put(FAVORITE_TBL_STORE_ID, fiBean.getStoreId());
		values.put(FAVORITE_TBL_SELLER_ID, fiBean.getSellerId());
		values.put(FAVORITE_TBL_ADD_TIME, fiBean.getAddTime());
		long ret = this.getWritableDatabase().insert(FAVORITE_TBL_TABLE_NAME,
				"", values);
		this.getWritableDatabase().close();
		return ret;
	}

	public int delete(FavoriteItemBean fiBean)
	{
		if (fiBean == null)
		{
			throw new IllegalArgumentException("FavoriteItemBean");
		}
		String whereClause = FAVORITE_TBL_ID + "=? ";
		String[] whereArgs = new String[] { String.valueOf(fiBean.getId()) };
		int ret = this.getWritableDatabase().delete(FAVORITE_TBL_TABLE_NAME,
				whereClause, whereArgs);
		this.getWritableDatabase().close();
		return ret;
	}

	public int update(FavoriteItemBean fiBean)
	{
		if (fiBean == null)
		{
			throw new IllegalArgumentException("FavoriteItemBean");
		}
		String whereClause = FAVORITE_TBL_ID + "=?";
		String[] whereArgs = new String[] { String.valueOf(fiBean.getId()) };
		ContentValues values = new ContentValues();
		values.put(FAVORITE_TBL_ID, fiBean.getId());
		values.put(FAVORITE_TBL_NAME, fiBean.getName());
		values.put(FAVORITE_TBL_DESC, fiBean.getDesc());
		values.put(FAVORITE_TBL_IMAGE_PATH, fiBean.getImagePath());
		values.put(FAVORITE_TBL_ARVATOURL, fiBean.getArvatoUrl());
		values.put(FAVORITE_TBL_MARK, fiBean.getMark());
		values.put(FAVORITE_TBL_MODE, fiBean.getMode());
		values.put(FAVORITE_TBL_PRODUCT_ID, fiBean.getProductId());
		values.put(FAVORITE_TBL_COUPON_ID, fiBean.getCouponId());
		values.put(FAVORITE_TBL_STORE_ID, fiBean.getStoreId());
		values.put(FAVORITE_TBL_SELLER_ID, fiBean.getSellerId());
		values.put(FAVORITE_TBL_ADD_TIME, fiBean.getAddTime());
		int ret = this.getWritableDatabase().update(FAVORITE_TBL_TABLE_NAME,
				values, whereClause, whereArgs);
		this.getWritableDatabase().close();
		return ret;
	}

	public int getCount(int mode)
	{
		String statement;
		if (Constants.FAV_MODE_ALL != mode)
		{
			statement = "select count(*) from " + FAVORITE_TBL_TABLE_NAME
					+ " where " + FAVORITE_TBL_MODE + "=" + mode;
		}
		else
		{
			statement = "select count(*) from " + FAVORITE_TBL_TABLE_NAME;
		}
		Cursor mCount = this.getReadableDatabase().rawQuery(statement, null);
		mCount.moveToFirst();
		int count = mCount.getInt(0);
		mCount.close();
		return count;
	}
}
