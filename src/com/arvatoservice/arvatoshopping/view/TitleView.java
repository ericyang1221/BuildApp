package com.arvatoservice.arvatoshopping.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arvatoservice.arvatoshopping.R;

public class TitleView extends LinearLayout {
	private ImageView leftImg;
	private ImageView rightImg;
	private TextView leftTitle;
	private TextView rightTitle;

	public TitleView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(context);
	}

	public void init(Context context)
	{
		LayoutInflater.from(context).inflate(R.layout.title_view, this, true);
		leftImg = (ImageView) findViewById(R.id.left_img);
		rightImg = (ImageView) findViewById(R.id.right_img);
		leftTitle = (TextView) findViewById(R.id.left_title);
		rightTitle = (TextView) findViewById(R.id.right_title);
	}

	public void setLeftImg(int res)
	{
		leftImg.setImageResource(res);
	}

	public void setRightImg(int res)
	{
		rightImg.setImageResource(res);
	}

	public void setLeftTitle(String title)
	{
		leftTitle.setText(title);
	}

	public void setRightTitle(String title)
	{
		rightTitle.setText(title);
	}
}
