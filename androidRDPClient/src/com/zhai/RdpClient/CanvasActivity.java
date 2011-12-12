package com.zhai.RdpClient;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.antlersoft.android.zoomer.ZoomControls;

public class CanvasActivity extends Activity {

	RdpCanvas ca;
	ZoomControls mZoomControls;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.canvas);

		ca = (RdpCanvas) findViewById(R.id.vnc_canvas);
		ca.setBackgroundColor(Color.RED);

		mZoomControls = (ZoomControls) findViewById(R.id.zoomer);
	}
}
