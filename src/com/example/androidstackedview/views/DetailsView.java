package com.example.androidstackedview.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.example.androidstackedview.R;
import com.example.androidstackedview.views.StackedAnimation.OnHAnimationListener;

public class DetailsView extends BaseView {

	private Context context;
	private int screenHeight;
	private int screenWidth;

	public DetailsView(Context context) {
		super(context);
		this.context = context;
		initScreenDimenstions();
		// TODO Auto-generated constructor stub
		setBackgroundColor(0xFFFFFFFF);
	}

	public DetailsView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initScreenDimenstions();
		// TODO Auto-generated constructor stub
		setBackgroundColor(0xFFFFFFFF);
	}

	public DetailsView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.context = context;
		initScreenDimenstions();
		setBackgroundColor(0xFFFFFFFF);
	}

	
	private void initScreenDimenstions() {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		screenHeight = displaymetrics.heightPixels;
		screenWidth = displaymetrics.widthPixels;

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if(findViewById(R.id.parent) != null) {
			FrameLayout.LayoutParams params = null;
			int orientation = context.getResources().getConfiguration().orientation;
			if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
				params = new FrameLayout.LayoutParams((int) (screenWidth-xDocked), screenHeight);
			}
			else if(orientation == Configuration.ORIENTATION_PORTRAIT) {
				params = new FrameLayout.LayoutParams((int) (screenWidth-xDocked), screenHeight);
			}
			findViewById(R.id.parent).setLayoutParams(params);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		if(isAnimating) {
			return true;
		}
		if(action == MotionEvent.ACTION_DOWN) {
			px = event.getX();
		}
		else if(action == MotionEvent.ACTION_MOVE) {
			float newX = event.getX();
			float dx = newX - px;
			if(Math.abs(dx) > 10) {
				float value = getLeft()+(dx*TOUCH_FACTOR);
				if(value < 0) {
					value = 0;

				}
				if(mViewState == ViewState.NORMAL && dx > 0) {
					layout((int)(getLeft()), 0, (int)(getLeft()+getWidth()), getHeight());
				}
				else {
					layout((int)(value), 0, (int)(value+getWidth()), getHeight());
				}
			}

			px = newX;

		}
		else if(action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
			float toX = 0;
			float dx = (xDocked+xNormal)/2;
			float cx = getLeft();
			if(cx > dx) {
				toX = xNormal - getLeft();
				mViewState = ViewState.NORMAL;
			}
			else {
				toX = xDocked-getLeft();
				mViewState = ViewState.DOCKED;
				
			}
			StackedAnimation anim = new StackedAnimation(this, 0, toX, ANIMATION_DURATION);
			anim.setListener(new OnHAnimationListener() {

				@Override
				public void onStart() {
					isAnimating = true;
				}

				@Override
				public void onEnd() {
					isAnimating = false;
					changeState(mViewState, false);
				}
			});
			anim.start();
		}
		return true;
	}
}
