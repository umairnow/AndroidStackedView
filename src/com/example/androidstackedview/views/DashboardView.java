package com.example.androidstackedview.views;

import com.example.androidstackedview.R;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class DashboardView extends RelativeLayout {

	public DashboardView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setBackgroundColor(Color.RED);
	}

	public DashboardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setBackgroundColor(Color.RED);
	}

	public DashboardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setBackgroundColor(Color.RED);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if(findViewById(R.id.parent) != null) {
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(StackedView.screenWidth/4, StackedView.screenHeight);
			findViewById(R.id.parent).setLayoutParams(params);

		}
	}
	
}
