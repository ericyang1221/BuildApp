package com.arvatoservice.arvatoshopping.view.abs;

import com.arvatoservice.arvatoshopping.beans.abs.HttpResponseBean;


public interface MovableView {
	public void initial(HttpResponseBean hrb);

	public void recycle();
}
