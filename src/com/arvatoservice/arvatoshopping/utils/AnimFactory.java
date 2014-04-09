package com.arvatoservice.arvatoshopping.utils;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * 动画工厂类
 * 
 * @author Eric
 *
 */
public class AnimFactory {
	/**
	 * 第一个参数fromAlpha为 动画开始时候透明度 
	 * 第二个参数toAlpha为 动画结束时候透明度
	 * 第三个参数为setDuration时间
	 * 
	 * @return
	 */
	public static AlphaAnimation createAlphaAnimation(float... fs)
	{
		AlphaAnimation alphaAnimation = null;
		if (fs == null || fs.length == 0)
		{
			alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
			alphaAnimation.setDuration(500);
		} 
		else 
		{
			try
			{
				alphaAnimation = new AlphaAnimation(fs[0], fs[1]);
				alphaAnimation.setDuration((long)fs[2]);
			}
			catch(Exception e)
			{
				//TODO
				e.printStackTrace();
				throw new IllegalArgumentException("createAlphaAnimation");
			}
		}
		return alphaAnimation;
	}

	/**
	 * 第一个参数fromX为动画起始时 X坐标上的伸缩尺寸 
	 * 第二个参数toX为动画结束时 X坐标上的伸缩尺寸
	 * 第三个参数fromY为动画起始时Y坐标上的伸缩尺寸 
	 * 第四个参数toY为动画结束时Y坐标上的伸缩尺寸 
	 * 	说明: 
	 * 		以上四种属性值 0.0表示收缩到没有
	 * 		1.0表示正常无伸缩 
	 * 		值小于1.0表示收缩 
	 * 		值大于1.0表示放大 
	 * 第五个参数pivotXType为动画在X轴相对于物件位置类型
	 * 第六个参数pivotXValue为动画相对于物件的X坐标的开始位置 
	 * 第七个参数pivotYType为动画在Y轴相对于物件位置类型
	 * 第八个参数pivotYValue为动画相对于物件的Y坐标的开始位置
	 * 第九个参数为setDuration时间
	 * 
	 * @return
	 */
	public static ScaleAnimation createScaleAnimation(float...fs)
	{
		ScaleAnimation scaleAnimation = null;
		if (fs == null || fs.length == 0)
		{
			scaleAnimation = new ScaleAnimation(0.0f, 1.4f, 0.0f,
					1.4f, Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			scaleAnimation.setDuration(500);
		} 
		else 
		{
			try
			{
				scaleAnimation = new ScaleAnimation(fs[0], fs[1], fs[2],
						fs[3], (int)fs[4], fs[5],(int)fs[6], fs[7]);
				scaleAnimation.setDuration((long)fs[8]);
			}
			catch(Exception e)
			{
				//TODO
				e.printStackTrace();
				throw new IllegalArgumentException("createScaleAnimation");
			}
		}
		return scaleAnimation;
	}

	/**
	 * 第一个参数fromXDelta为动画起始时 X坐标上的移动位置 
	 * 第二个参数toXDelta为动画结束时 X坐标上的移动位置
	 * 第三个参数fromYDelta为动画起始时Y坐标上的移动位置 
	 * 第四个参数toYDelta为动画结束时Y坐标上的移动位置
	 * 第五个参数为setDuration时间
	 * 
	 * @return
	 */
	public static TranslateAnimation createTranslateAnimation(float fromX, float toX,
			float fromY, float toY, long duration)
	{
		TranslateAnimation translateAnimation = new TranslateAnimation(fromX,
				toX, fromY, toY);
		translateAnimation.setDuration(duration);
		return translateAnimation;
	}

	/**
	 * 第一个参数fromDegrees为动画起始时的旋转角度 
	 * 第二个参数toDegrees为动画旋转到的角度
	 * 第三个参数pivotXType为动画在X轴相对于物件位置类型 
	 * 第四个参数pivotXValue为动画相对于物件的X坐标的开始位置
	 * 第五个参数pivotXType为动画在Y轴相对于物件位置类型 第六个参数pivotYValue为动画相对于物件的Y坐标的开始位置
	 * 第六个参数为setDuration时间
	 * 
	 * @return
	 */
	public static RotateAnimation createRotateAnimation(float...fs)
	{
		RotateAnimation rotateAnimation = null;
		if (fs == null || fs.length == 0)
		{
			rotateAnimation = new RotateAnimation(0.0f, +350.0f,
					Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
					0.5f);
			rotateAnimation.setDuration(500);
		} 
		else 
		{
			try
			{
				rotateAnimation = new RotateAnimation(fs[0], fs[1], (int)fs[2], 
						fs[3],(int)fs[4], fs[5]);
				rotateAnimation.setDuration((long)fs[6]);
			}
			catch(Exception e)
			{
				//TODO
				e.printStackTrace();
				throw new IllegalArgumentException("createRotateAnimation");
			}
		}
		return rotateAnimation;
	}
}
