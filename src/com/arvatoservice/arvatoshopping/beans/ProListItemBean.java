package com.arvatoservice.arvatoshopping.beans;

import com.arvatoservice.arvatoshopping.beans.abs.DataBean;
import com.arvatoservice.arvatoshopping.utils.Constants;


/**
 * 
 * @author Andy
 * 
 *
 */
public class ProListItemBean implements DataBean{
	
	private int proId;

	private String proName;
	
	private String proPrice;
	
	private String proImgMain;
	
	private boolean isError = false;
	private boolean isEmpty = false;
	
	public ProListItemBean(int beanMode)
	{
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

	public ProListItemBean(int _proId, String _proName, String _proPrice, String _proImgMain)
	{
		this.proId = _proId;
		this.proName = _proName;
		this.proPrice = _proPrice;
		this.proImgMain = _proImgMain;
	}
	
	public int getProId() {
		return proId;
	}

	public void setProId(int proId) {
		this.proId = proId;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getProPrice() {
		return proPrice;
	}

	public void setProPrice(String proPrice) {
		this.proPrice = proPrice;
	}

	public String getProImgMain() {
		return proImgMain;
	}

	public void setProImgMain(String proImgMain) {
		this.proImgMain = proImgMain;
	}
	
	@Override
	public boolean isError(){
		return isError;
	}

	@Override
	public boolean isEmpty() {
		return isEmpty;
	}
}
