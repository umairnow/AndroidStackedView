package com.example.androidstackedview.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;

import com.example.androidstackedview.R;

public class TopicsView extends BaseView {

	StackedView stackView;
	Context context;
	private int screenHeight;
	private int screenWidth;


	public TopicsView(Context context) {
		super(context);
		this.context = context;
		initScreenDimenstions();
	}

	public TopicsView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initScreenDimenstions();
	}

	public TopicsView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		initScreenDimenstions();

	}

	public StackedView getStackView() {
		return stackView;
	}

	public void setStackView(StackedView parent) {
		this.stackView = parent;
	}

	private void initScreenDimenstions() {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		screenHeight = displaymetrics.heightPixels;
		screenWidth = displaymetrics.widthPixels;

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if(findViewById(R.id.parent) != null) {
			int orientation = context.getResources().getConfiguration().orientation;
			FrameLayout.LayoutParams params;
			if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
				params = new FrameLayout.LayoutParams((int) (screenWidth/2 - screenWidth/2 * .30f), screenHeight);
			}
			else {
				params = new FrameLayout.LayoutParams((int) (screenWidth/2), screenHeight);
			}
			findViewById(R.id.parent).setLayoutParams(params);

		}
	}


}
