package com.arvatoservice.arvatoshopping.beans;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.arvatoservice.arvatoshopping.beans.abs.DataBean;
import com.arvatoservice.arvatoshopping.exceptions.JSONObjectCastException;
import com.arvatoservice.arvatoshopping.utils.Constants;

/**
 * 
 * @author Andy
 * 
 *
 */
public class ProListBean implements DataBean{
	protected int ret = -100;
	protected String msg = "数据异常";
	protected int pageNum;
	protected int pageSize;
	protected int totalCount;
	protected int totalPage;
	protected ArrayList<ProListItemBean> proItemBeanList;
	
	private boolean isError = false;
	private boolean isEmpty = false;
	
	
	public ProListBean(int beanMode){
		switch (beanMode)
		{
		case Constants.MODE_EMPTY_DATA_BENA:
			this.isEmpty = true;
			break;
		case Constants.MODE_ERROR_DATA_BENA:
			this.isError = true;
			break;
		}
	}

	public ProListBean(JSONObject jo) throws JSONObjectCastException
	{
		try
		{
			ret = jo.getInt("ret");
			msg = jo.getString("msg");
			proItemBeanList = new ArrayList<ProListItemBean>();
			if (ret != 0)
			{
				// error occurred.
				proItemBeanList.add(new ProListItemBean(Constants.MODE_ERROR_DATA_BENA));
				return;
			}
			// Hack server error when use ids para.
			try
			{
				pageNum = jo.getInt("pageNum");
				pageSize = jo.getInt("pageSize");
			} catch (Exception e)
			{
				pageNum = 1;
				pageSize = 1;
			}
			totalCount = jo.getInt("totalCount");
			totalPage = jo.getInt("totalPage");
			if(totalCount == 0){
				proItemBeanList.add(new ProListItemBean(Constants.MODE_EMPTY_DATA_BENA));
				return;
			}
			JSONArray ja = jo.getJSONArray("productList");
			int proListItemSize = ja.length();
			int proId;
			String proName;
			String proPrice;
			String proImgMain;
			for (int i = 0; i < proListItemSize; i++)
			{
				JSONObject j = (JSONObject) ja.opt(i);
				proId = j.getInt("id");
				proName = j.getString("name");
				proPrice = j.getString("price");
				proImgMain = j.getString("img");
				ProListItemBean cliBean = new ProListItemBean(proId, proName, proPrice, proImgMain);
				proItemBeanList.add(cliBean);
			}
		} catch (Exception e)
		{
			throw new JSONObjectCastException(e.getMessage(),
					"JSONObject format error.");
		}
	}
	
	public int getRet()
	{
		return ret;
	}

	public void setRet(int ret)
	{
		this.ret = ret;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	

	public int getPageNum()
	{
		return pageNum;
	}

	public void setPageNum(int pageNum)
	{
		this.pageNum = pageNum;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	public int getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(int totalCount)
	{
		this.totalCount = totalCount;
	}

	public int getTotalPage()
	{
		return totalPage;
	}

	public void setTotalPage(int totalPage)
	{
		this.totalPage = totalPage;
	}
	
	public ArrayList<ProListItemBean> getProItemBeanList() {
		return proItemBeanList;
	}

	public void setProItemBeanList(ArrayList<ProListItemBean> proItemBeanList) {
		this.proItemBeanList = proItemBeanList;
	}

	@Override
	public boolean isError()
	{
		return isError;
	}
	
	@Override
	public boolean isEmpty()
	{
		return isEmpty;
	}
}
