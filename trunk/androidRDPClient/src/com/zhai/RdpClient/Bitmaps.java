package com.zhai.RdpClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Bitmap.Config;
import android.util.Log;

public final class Bitmaps implements IBitmap {

	private android.graphics.Bitmap bm;

	public Bitmaps(int width, int height) {
		bm = android.graphics.Bitmap.createBitmap(width, height, Config.RGB_565);
	}

	public  Bitmaps (byte[] data, int offset, int length) {
		bm = BitmapFactory.decodeByteArray(data, offset, length);
	}


	public Bitmaps(int[] bb, int w, int h) {
		int[] colors = new int[bb.length];
		int value = 0;
		for (int i=0;i<bb.length;i++){
			value = new   Integer(bb[i]).intValue();
			colors[i] = Color.argb(Color.alpha(value), Color.red(value),Color.green(value),Color.blue(value));
		}	
		bm =  android.graphics.Bitmap.createBitmap(colors, w, h, Config.RGB_565);
	}
	
	public Bitmaps(IBitmap bim, int x, int y, int width, int height,
			Matrix matrix, boolean filter) {
		
		
		bm = android.graphics.Bitmap.createBitmap((android.graphics.Bitmap) bim.getBitmap(true), x, y, width, height, matrix, filter);
	}

	public Bitmaps(IBitmap bim, int width, int height, boolean filter) {
		bm = android.graphics.Bitmap.createScaledBitmap((android.graphics.Bitmap) bim.getBitmap(true), width, height, filter);
	}


	public int getWidth() {
		return bm.getWidth();
	}

	public int getHeight() {
		return bm.getHeight();
	}

	public int getPixel(int x, int y) {
		return bm.getPixel(x, y);
	}

	public android.graphics.Bitmap getBitmap(boolean isWriteable) {
		if (isWriteable != bm.isMutable()) return bm.copy(getConfig(), isWriteable);
		return bm;
	}
	

	public void setRGB(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scansize){
		bm.setPixels(rgbArray, scansize, offset, startX, startY, w, h);
	}
	
	public int[] getRGB(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scansize){
		bm.getPixels(rgbArray, offset, scansize, startX, startY, w, h);
		return rgbArray;
	}

	public void eraseColor(int bgColor) {
		bm.eraseColor(bgColor);
	}

	public IBitmap getBitmap(int width, int height) {
		bm = android.graphics.Bitmap.createBitmap(width, height, Config.RGB_565);
		return this;
	}

	public IBitmap getBitmap(byte[] data, int offset, int length) {
		bm = BitmapFactory.decodeByteArray(data, offset, length);
		return this;
	}

	public IBitmap getBitmap(IBitmap source, int x, int y, int width,
			int height, Matrix matrix, boolean filter) {
		bm = android.graphics.Bitmap.createBitmap((android.graphics.Bitmap) source.getBitmap(false), x, y, width, height, matrix, filter);
		return this;
	}

	public IBitmap getBitmap(IBitmap source, int width, int height,
			boolean filter) {
		bm = android.graphics.Bitmap.createScaledBitmap((android.graphics.Bitmap) source.getBitmap(false), width, height, filter);
		return this;
	}

	public Bitmaps(Context ctx, int resId, int width, int height) {
		 bm = BitmapFactory.decodeResource(ctx.getResources(),resId);
		 resizeBitmap(width,height);
	}
	
	private void resizeBitmap(int w, int h) {

		  int width = bm.getWidth();
		  int height = bm.getHeight();
		  int newWidth = w;
		  int newHeight = h;

		  // calculate the scale
		  float scaleWidth = ((float) newWidth) / width;
		  float scaleHeight = ((float) newHeight) / height;

		  // create a matrix for the manipulation
		  Matrix matrix = new Matrix();
		  // resize the Bitmap
		  matrix.postScale(scaleWidth, scaleHeight);
		  // if you want to rotate the Bitmap
		  // matrix.postRotate(45);

		  // recreate the new Bitmap
		  bm = android.graphics.Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
	}

	public  String storeBitmapFromURL(URL url, String pathName,
			final Context ctx,String tag) {
		try {
			bm = BitmapFactory.decodeStream(url.openStream());
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(tag, e.toString(), e);
			return null;
		}
		return storeBitmap(ctx,pathName,tag);
	}
	
	public String storeBitmap(final Context ctx, String name, String tag) {
		boolean success = false;
		String qualifiedName = name + ".png";
		FileOutputStream fos = null;
		try {
			
			fos = ctx.openFileOutput(qualifiedName, Context.MODE_PRIVATE);
			success = bm.compress(android.graphics.Bitmap.CompressFormat.PNG, 75, fos);
			if (success) return qualifiedName;
		} catch (IOException e) {
			Log.e(tag, e.toString(), e);
		} catch (IllegalArgumentException e) {
			Log.e(tag, e.toString());
		} catch (NullPointerException e) {
			Log.e(tag, e.toString(), e);
		} finally {
			try {
				if (fos != null) fos.close();
			} catch (IOException e) {
				Log.e(tag, e.toString(), e);
			}
		}
		return qualifiedName;
	}

	public  Bitmaps(File img_file) throws FileNotFoundException {
		bm = android.graphics.BitmapFactory.decodeStream(new FileInputStream(img_file));
	}

	public Bitmaps(android.graphics.Bitmap bm) {
		this.bm = bm;
	}


	public IBitmap copy(Config config, boolean isMutable) {
		return new Bitmaps(bm.copy(config, isMutable));
	}

	public Config getConfig() {
		return bm.getConfig();
	}

	public boolean isMutable() {
		return bm.isMutable();
	}

	public void resize(int width, int height) {
		resizeBitmap(width, height);
	}

	public void setPixel(int x, int y, int color) {
		bm.setPixel(x, y, color);
	}

	@Override
	public Object getBitmap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void recycle() {
		// TODO Auto-generated method stub
		
	}

}
