package com.arvatoservice.arvatoshopping.beans.abs;

import java.util.List;


/**
 * 
 * @author Eric
 * 
 */
public abstract class AbstractListBean extends AbstractHttpResponseBean {
	protected List<DataBean> contentList;

	public List<DataBean> getContentList()
	{
		return contentList;
	}

	public void setContentList(List<DataBean> contentList)
	{
		this.contentList = contentList;
	}

}
