package com.arvatoservice.arvatoshopping.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.arvatoservice.arvatoshopping.beans.PushMsgBean;
import com.arvatoservice.arvatoshopping.db.abs.BaseSQLiteOpenHelper;

/**
 * 
 * @author Eric
 * 
 */
public class PushMsgDBHelper extends BaseSQLiteOpenHelper {

	public PushMsgDBHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public List<PushMsgBean> selectAll()
	{
		List<PushMsgBean> pmBeanList = new ArrayList<PushMsgBean>();
		String[] columns = new String[] { PUSHMSG_TBL_ID, PUSHMSG_TBL_TYPE,
				PUSHMSG_TBL_TITLE, PUSHMSG_TBL_MESSAGE,
				PUSHMSG_TBL_RECEIVE_TIME, PUSHMSG_TBL_RID, PUSHMSG_TBL_IMG_URL };
		String whereClause = null;
		String[] whereArgs = null;
		String groupBy = null;
		String having = null;
		String orderBy = PUSHMSG_TBL_RECEIVE_TIME + " DESC";
		String limit = null;
		Cursor c = this.getReadableDatabase().query(PUSHMSG_TBL_TABLE_NAME,
				columns, whereClause, whereArgs, groupBy, having, orderBy,
				limit);
		c.moveToFirst();
		for (int i = 0; i < c.getCount(); i++)
		{
			int id = c.getInt(0);
			int type = c.getInt(1);
			String title = c.getString(2);
			String message = c.getString(3);
			long receiveTime = c.getLong(4);
			int rid = c.getInt(5);
			String imgUrl = c.getString(6);
			PushMsgBean pmBean = new PushMsgBean(id, type, receiveTime, title,
					message, rid, imgUrl);
			pmBeanList.add(pmBean);
			c.moveToNext();
		}
		c.close();
		c = null;
		this.getReadableDatabase().close();
		return pmBeanList;
	}

	public long insert(PushMsgBean pmBean)
	{
		if (pmBean == null)
		{
			throw new IllegalArgumentException("PushMsgBean");
		}
		ContentValues values = new ContentValues();
		// values.put(ID, fiBean.getId());
		values.put(PUSHMSG_TBL_TYPE, pmBean.getType());
		values.put(PUSHMSG_TBL_TITLE, pmBean.getTitle());
		values.put(PUSHMSG_TBL_MESSAGE, pmBean.getMessage());
		values.put(PUSHMSG_TBL_RECEIVE_TIME, pmBean.getReceiveTime());
		values.put(PUSHMSG_TBL_RID, pmBean.getRid());
		values.put(PUSHMSG_TBL_IMG_URL, pmBean.getImgUrl());
		long ret = this.getWritableDatabase().insert(PUSHMSG_TBL_TABLE_NAME,
				"", values);
		this.getWritableDatabase().close();
		return ret;
	}

	public int delete(PushMsgBean pmBean)
	{
		if (pmBean == null)
		{
			throw new IllegalArgumentException("PushMsgBean");
		}
		String whereClause = PUSHMSG_TBL_ID + "=? ";
		String[] whereArgs = new String[] { String.valueOf(pmBean.getId()) };
		int ret = this.getWritableDatabase().delete(PUSHMSG_TBL_TABLE_NAME,
				whereClause, whereArgs);
		this.getWritableDatabase().close();
		return ret;
	}

	public int update(PushMsgBean pmBean)
	{
		if (pmBean == null)
		{
			throw new IllegalArgumentException("PushMsgBean");
		}
		String whereClause = PUSHMSG_TBL_ID + "=?";
		String[] whereArgs = new String[] { String.valueOf(pmBean.getId()) };
		ContentValues values = new ContentValues();
		values.put(PUSHMSG_TBL_ID, pmBean.getId());
		values.put(PUSHMSG_TBL_TYPE, pmBean.getType());
		values.put(PUSHMSG_TBL_TITLE, pmBean.getTitle());
		values.put(PUSHMSG_TBL_MESSAGE, pmBean.getMessage());
		values.put(PUSHMSG_TBL_RECEIVE_TIME, pmBean.getReceiveTime());
		values.put(PUSHMSG_TBL_RID, pmBean.getRid());
		values.put(PUSHMSG_TBL_IMG_URL, pmBean.getImgUrl());
		int ret = this.getWritableDatabase().update(PUSHMSG_TBL_TABLE_NAME,
				values, whereClause, whereArgs);
		this.getWritableDatabase().close();
		return ret;
	}

	public int getCount(int mode)
	{
		String statement;
		statement = "select count(*) from " + FAVORITE_TBL_TABLE_NAME;
		Cursor mCount = this.getReadableDatabase().rawQuery(statement, null);
		mCount.moveToFirst();
		int count = mCount.getInt(0);
		mCount.close();
		return count;
	}
}
