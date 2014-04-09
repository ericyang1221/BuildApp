package com.arvatoservice.arvatoshopping.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.arvatoservice.arvatoshopping.beans.abs.AbstractHttpResponseBean;
import com.arvatoservice.arvatoshopping.beans.abs.HttpResponseBean;
import com.arvatoservice.arvatoshopping.exceptions.JSONObjectCastException;

/**
 * 
 * @author Eric
 * 
 */
public class ProductDetailBean extends AbstractHttpResponseBean implements
		HttpResponseBean, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private double price;
	private String desc;
	private String img;
	private int sellerId;
	private String sellerName;
	private String infoUrl;

	private boolean hasUrl1;
	private boolean hasUrl2;
	private boolean hasUrl3;
	private boolean hasUrl4;
	private boolean hasUrl5;
	private boolean hasUrl6;
	private boolean hasBuy;
	private boolean hasCoupon;
	private boolean hasVideo;
	private boolean hasStore;
	private boolean hasCall;
	private String url1;
	private String url2;
	private String url3;
	private String url4;
	private String url5;
	private String url6;
	private String call;

	private List<ProductDetailImgBean> productDetailImgList;
	private ProductDetailVideoBean productDetailVideoBean;

	public ProductDetailBean()
	{
	}

	public ProductDetailBean(JSONObject jo, String productId)
			throws JSONObjectCastException
	{
		try
		{
			ret = jo.getInt("ret");
			msg = jo.getString("msg");
			if (ret != 0)
			{
				return;
			}
			// modify to pass in the product id
			// id = jo.getInt("id");
			id = Integer.valueOf(productId);
			name = jo.getString("name");
			price = jo.getDouble("price");
			if (jo.has("desc"))
			{
				desc = jo.getString("desc");
			}
			else
			{
				desc = "产品无介绍";
			}
			img = jo.getString("img");
			sellerId = jo.getInt("sellerId");
			sellerName = jo.getString("sellerName");

			if (jo.has("infoUrl"))
			{
				infoUrl = jo.getString("infoUrl");
			}
			if (jo.has("hasUrl1"))
			{
				hasUrl1 = jo.getBoolean("hasUrl1");
			}
			else
			{
				hasUrl1 = false;
			}
			if (hasUrl1)
			{
				// modify
				if (jo.has("url1"))
				{
					url1 = jo.getString("url1");
				}
			}

			if (jo.has("hasUrl2"))
			{
				hasUrl2 = jo.getBoolean("hasUrl2");
			}
			else
			{
				hasUrl2 = false;
			}
			if (hasUrl2)
			{
				// modify
				if (jo.has("url2"))
				{
					url2 = jo.getString("url2");
				}
			}

			if (jo.has("hasUrl3"))
			{
				hasUrl3 = jo.getBoolean("hasUrl3");
			}
			else
			{
				hasUrl3 = false;
			}
			if (hasUrl3)
			{
				// modify
				if (jo.has("url3"))
				{
					url3 = jo.getString("url3");
				}
			}

			if (jo.has("hasUrl4"))
			{
				hasUrl4 = jo.getBoolean("hasUrl4");
			}
			else
			{
				hasUrl4 = false;
			}
			if (hasUrl4)
			{
				// modify
				if (jo.has("url4"))
				{
					url4 = jo.getString("url4");
				}
			}

			if (jo.has("hasUrl5"))
			{
				hasUrl5 = jo.getBoolean("hasUrl5");
			}
			else
			{
				hasUrl5 = false;
			}
			if (jo.has("hasUrl5"))
			{
				// modify
				if (jo.has("url5"))
				{
					url5 = jo.getString("url5");
				}
			}

			if (jo.has("hasUrl6"))
			{
				hasUrl6 = jo.getBoolean("hasUrl6");
			}
			else
			{
				hasUrl6 = false;
			}
			if (hasUrl6)
			{
				// modify
				if (jo.has("url6"))
				{
					url6 = jo.getString("url6");
				}
			}

			if (jo.has("hasBuy"))
			{
				hasBuy = jo.getBoolean("hasBuy");
			}
			if (jo.has("hasCoupon"))
			{
				hasCoupon = jo.getBoolean("hasCoupon");
			}
			if (jo.has("hasVideo"))
			{
				hasVideo = jo.getBoolean("hasVideo");
			}

			if (hasVideo)
			{
				JSONObject j = jo.getJSONObject("video");
				String thumbnail = j.getString("thumbnail");
				String video = j.getString("video");
				productDetailVideoBean = new ProductDetailVideoBean(thumbnail,
						video);
			}

			if (jo.has("hasStore"))
			{
				hasStore = jo.getBoolean("hasStore");
			}

			if (jo.has("hasCall"))
			{
				hasCall = jo.getBoolean("hasCall");
			}
			if (hasCall)
			{
				call = jo.getString("call");
			}

			if (jo.has("imgList"))
			{
				JSONArray ja = jo.getJSONArray("imgList");
				int size = ja.length();
				productDetailImgList = new ArrayList<ProductDetailImgBean>();
				for (int i = 0; i < size; i++)
				{
					JSONObject j = ja.getJSONObject(i);
					String midImg = j.getString("middleImg");
					String bigImg = j.getString("bigImg");
					productDetailImgList.add(new ProductDetailImgBean(midImg,
							bigImg));
				}
			}

		} catch (Exception e)
		{
			throw new JSONObjectCastException(e.getMessage(),
					"JSONObject format error.");
		}
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public String getImg()
	{
		return img;
	}

	public void setImg(String img)
	{
		this.img = img;
	}

	public int getSellerId()
	{
		return sellerId;
	}

	public void setSellerId(int sellerId)
	{
		this.sellerId = sellerId;
	}

	public String getSellerName()
	{
		return sellerName;
	}

	public void setSellerName(String sellerName)
	{
		this.sellerName = sellerName;
	}

	public List<ProductDetailImgBean> getProductDetailImgList()
	{
		return productDetailImgList;
	}

	public void setProductDetailImgList(
			List<ProductDetailImgBean> productDetailImgList)
	{
		this.productDetailImgList = productDetailImgList;
	}

	public boolean hasUrl1()
	{
		return hasUrl1;
	}

	public void setHasUrl1(boolean hasUrl1)
	{
		this.hasUrl1 = hasUrl1;
	}

	public boolean hasUrl2()
	{
		return hasUrl2;
	}

	public void setHasUrl2(boolean hasUrl2)
	{
		this.hasUrl2 = hasUrl2;
	}

	public boolean hasUrl3()
	{
		return hasUrl3;
	}

	public void setHasUrl3(boolean hasUrl3)
	{
		this.hasUrl3 = hasUrl3;
	}

	public boolean hasBuy()
	{
		return hasBuy;
	}

	public void setHasBuy(boolean hasBuy)
	{
		this.hasBuy = hasBuy;
	}

	public boolean hasCoupon()
	{
		return hasCoupon;
	}

	public void setHasCoupon(boolean hasCoupon)
	{
		this.hasCoupon = hasCoupon;
	}

	public boolean hasVideo()
	{
		return hasVideo;
	}

	public void setHasVideo(boolean hasVideo)
	{
		this.hasVideo = hasVideo;
	}

	public boolean hasStore()
	{
		return hasStore;
	}

	public void setHasStore(boolean hasStore)
	{
		this.hasStore = hasStore;
	}

	public boolean hasCall()
	{
		return hasCall;
	}

	public void setHasCall(boolean hasCall)
	{
		this.hasCall = hasCall;
	}

	public String getUrl1()
	{
		return url1;
	}

	public void setUrl1(String url1)
	{
		this.url1 = url1;
	}

	public String getUrl2()
	{
		return url2;
	}

	public void setUrl2(String url2)
	{
		this.url2 = url2;
	}

	public String getUrl3()
	{
		return url3;
	}

	public void setUrl3(String url3)
	{
		this.url3 = url3;
	}

	public boolean hasUrl4()
	{
		return hasUrl4;
	}

	public void setHasUrl4(boolean hasUrl4)
	{
		this.hasUrl4 = hasUrl4;
	}

	public boolean hasUrl5()
	{
		return hasUrl5;
	}

	public void setHasUrl5(boolean hasUrl5)
	{
		this.hasUrl5 = hasUrl5;
	}

	public boolean hasUrl6()
	{
		return hasUrl6;
	}

	public void setHasUrl6(boolean hasUrl6)
	{
		this.hasUrl6 = hasUrl6;
	}

	public String getUrl4()
	{
		return url4;
	}

	public void setUrl4(String url4)
	{
		this.url4 = url4;
	}

	public String getUrl5()
	{
		return url5;
	}

	public void setUrl5(String url5)
	{
		this.url5 = url5;
	}

	public String getUrl6()
	{
		return url6;
	}

	public void setUrl6(String url6)
	{
		this.url6 = url6;
	}

	public String getCall()
	{
		return call;
	}

	public void setCall(String call)
	{
		this.call = call;
	}

	public ProductDetailVideoBean getProductDetailVideoBean()
	{
		return productDetailVideoBean;
	}

	public void setProductDetailVideoBean(
			ProductDetailVideoBean productDetailVideoBean)
	{
		this.productDetailVideoBean = productDetailVideoBean;
	}

	public String getInfoUrl()
	{
		return infoUrl;
	}

	public void setInfoUrl(String infoUrl)
	{
		this.infoUrl = infoUrl;
	}

}
