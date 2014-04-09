package com.arvatoservice.arvatoshopping.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.util.Log;

public class BitmapModifier {

	// ����ͼƬ�϶�Ϊһ
	public static Bitmap combine2Bitmaps(Bitmap src, Bitmap watermark)
	{
		Log.d(BitmapModifier.class.getName(), "combine2Bitmaps");
		if (src == null)
		{
			return null;
		}
		int w = src.getWidth();
		int h = src.getHeight();
		if (w < 150 || h < 150)
		{
			w = w * 2;
			h = h * 2;
			src = scaleBitmap(src, w, h);

		}
		int scaledX;
		int margin;
		if (w >= h)
		{
			scaledX = h / 3;
			margin = h / 10;
		}
		else
		{
			scaledX = w / 3;
			margin = w / 10;
		}
		Bitmap newwatermark = scaleBitmap(watermark, scaledX, scaledX);
		// create the new blank bitmap
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// ����һ���µĺ�SRC���ȿ��һ���λͼ
		Canvas cv = new Canvas(newb);
		// draw src into
		cv.drawBitmap(src, 0, 0, null);// �� 0��0��꿪ʼ����src
		// draw watermark into
		cv.drawBitmap(newwatermark, margin,
				h - margin - newwatermark.getHeight(), null);// ��src�����½ǻ���ˮӡ
		// save all clip
		cv.save(Canvas.ALL_SAVE_FLAG);// ����
		// store
		cv.restore();// �洢
		return newb;
	}

	/**
	 * ����
	 * 
	 * @param bitmap
	 * @param roundPx
	 * @return
	 */
	public static Bitmap scaleBitmap(Bitmap src, float hopedWidth,
			float hopedHeight)
	{
		// ���ͼƬ�Ŀ��
		int width = src.getWidth();
		int height = src.getHeight();
		// �������ű���
		float scaleWidth = hopedWidth / width;
		float scaleHeight = hopedHeight / height;
		// ȡ����Ҫ���ŵ�matrix����
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// �õ��µ�ͼƬ
		Bitmap newbm = Bitmap.createBitmap(src, 0, 0, width, height, matrix,
				true);
		return newbm;
	}

	/**
	 * ���Բ��ͼƬ
	 * 
	 * @param bitmap
	 * @param roundPx
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx)
	{
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * ��ô�Ӱ��ͼƬ
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap getReflectionImageWithOrigin(Bitmap bitmap,
			int reflectionCoefficient)
	{
		final int reflectionGap = 4;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
		int reflectionHeight = height / reflectionCoefficient;
		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height
				- reflectionHeight, width, reflectionHeight, matrix, false);
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + reflectionHeight), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		Paint defaultPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);
		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
				bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
				0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);

		return bitmapWithReflection;
	}

	public static Bitmap toGray(Bitmap bmpOriginal)
	{
		Bitmap bmpGrayscale = null;
		int width, height;
		if (bmpOriginal != null)
		{
			height = bmpOriginal.getHeight();
			width = bmpOriginal.getWidth();

			bmpGrayscale = Bitmap.createBitmap(width, height,
					Bitmap.Config.RGB_565);
			Canvas c = new Canvas(bmpGrayscale);
			Paint paint = new Paint();
			ColorMatrix cm = new ColorMatrix();
			cm.setSaturation(0);
			ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
			paint.setColorFilter(f);
			c.drawBitmap(bmpOriginal, 0, 0, paint);
		}
		return bmpGrayscale;
	}
}
