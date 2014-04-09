package com.arvatoservice.arvatoshopping.beans.abs;

public abstract class LinkedNodeBean {
	private LinkedNodeBean privousBean;
	private LinkedNodeBean nextBean;
	private boolean hasPrivous = false;
	private boolean hasNext = false;

	public LinkedNodeBean getPrivousBean()
	{
		return privousBean;
	}

	public void setPrivousBean(LinkedNodeBean privousBean)
	{
		this.privousBean = privousBean;
		hasPrivous = true;
	}

	public LinkedNodeBean getNextBean()
	{
		return nextBean;
	}

	public void setNextBean(LinkedNodeBean nextBean)
	{
		this.nextBean = nextBean;
		hasNext = true;
	}

	public boolean hasPrivous()
	{
		return hasPrivous;
	}

	public boolean hasNext()
	{
		return hasNext;
	}

}
