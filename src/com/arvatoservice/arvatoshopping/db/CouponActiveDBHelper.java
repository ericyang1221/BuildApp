package com.arvatoservice.arvatoshopping.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.arvatoservice.arvatoshopping.beans.CouponActiveBean;
import com.arvatoservice.arvatoshopping.db.abs.BaseSQLiteOpenHelper;

/**
 * 
 * @author Eric
 * 
 */
public class CouponActiveDBHelper extends BaseSQLiteOpenHelper {

	public CouponActiveDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public CouponActiveBean selectByCouponIdAndSellerId(int couponId,
			int sellerId) {
		CouponActiveBean cab = null;
		String[] columns = new String[] { COUPONACTIVE_TBL_COUPONID,
				COUPONACTIVE_TBL_SELLERID, COUPONACTIVE_TBL_ACTIVE_TIME,
				COUPONACTIVE_TBL_TIME_RANGE, COUPONACTIVE_TBL_STORE_NAME,
				COUPONACTIVE_TBL_MARK };
		String whereClause = COUPONACTIVE_TBL_COUPONID + "=? and "
				+ COUPONACTIVE_TBL_SELLERID + "=?";
		String[] whereArgs = new String[] { String.valueOf(couponId),
				String.valueOf(sellerId) };
		String groupBy = null;
		String having = null;
		String orderBy = null;
		String limit = null;
		Cursor c = this.getReadableDatabase().query(
				COUPONACTIVE_TBL_TABLE_NAME, columns, whereClause, whereArgs,
				groupBy, having, orderBy, limit);
		if (c.getCount() >= 1) {
			c.moveToFirst();
			int cid = c.getInt(0);
			int sid = c.getInt(1);
			long activeTime = c.getLong(2);
			double timeRange = c.getDouble(3);
			String storeName = c.getString(4);
			String mark = c.getString(5);
			cab = new CouponActiveBean(cid, sid, activeTime, timeRange,
					storeName, mark);
		}
		c.close();
		c = null;
		this.getReadableDatabase().close();
		return cab;
	}

	public long insert(CouponActiveBean cab) {
		if (cab == null) {
			throw new IllegalArgumentException("CouponActiveBean");
		}
		ContentValues values = new ContentValues();
		values.put(COUPONACTIVE_TBL_COUPONID, cab.getCouponId());
		values.put(COUPONACTIVE_TBL_SELLERID, cab.getSellerId());
		values.put(COUPONACTIVE_TBL_ACTIVE_TIME, cab.getActiveTime());
		values.put(COUPONACTIVE_TBL_TIME_RANGE, cab.getTimeRange());
		values.put(COUPONACTIVE_TBL_STORE_NAME, cab.getStoreName());
		values.put(COUPONACTIVE_TBL_MARK, cab.getMark());
		long ret = this.getWritableDatabase().insert(
				COUPONACTIVE_TBL_TABLE_NAME, "", values);
		this.getWritableDatabase().close();
		return ret;
	}

	public int delete(CouponActiveBean cab) {
		if (cab == null) {
			throw new IllegalArgumentException("CouponActiveBean");
		}
		String whereClause = COUPONACTIVE_TBL_COUPONID + "=? and "
				+ COUPONACTIVE_TBL_SELLERID + "=?";
		String[] whereArgs = new String[] { String.valueOf(cab.getCouponId()),
				String.valueOf(cab.getSellerId()) };
		int ret = this.getWritableDatabase().delete(
				COUPONACTIVE_TBL_TABLE_NAME, whereClause, whereArgs);
		this.getWritableDatabase().close();
		return ret;
	}

	public int update(CouponActiveBean cab) {
		if (cab == null) {
			throw new IllegalArgumentException("CouponActiveBean");
		}
		String whereClause = COUPONACTIVE_TBL_COUPONID + "=? and "
				+ COUPONACTIVE_TBL_SELLERID + "=?";
		String[] whereArgs = new String[] { String.valueOf(cab.getCouponId()),
				String.valueOf(cab.getSellerId()) };
		ContentValues values = new ContentValues();
		values.put(COUPONACTIVE_TBL_COUPONID, cab.getCouponId());
		values.put(COUPONACTIVE_TBL_SELLERID, cab.getSellerId());
		values.put(COUPONACTIVE_TBL_ACTIVE_TIME, cab.getActiveTime());
		values.put(COUPONACTIVE_TBL_TIME_RANGE, cab.getTimeRange());
		values.put(COUPONACTIVE_TBL_STORE_NAME, cab.getStoreName());
		values.put(COUPONACTIVE_TBL_MARK, cab.getMark());
		int ret = this.getWritableDatabase().update(
				COUPONACTIVE_TBL_TABLE_NAME, values, whereClause, whereArgs);
		this.getWritableDatabase().close();
		return ret;
	}
}
