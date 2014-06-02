package com.example.androidstackedview.views;

import com.example.androidstackedview.R;
import com.example.androidstackedview.views.StackedAnimation.OnHAnimationListener;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class SubTopicsView extends BaseView {

	StackedView stackView;
	Context context;
	private int screenHeight;
	private int screenWidth;
	BaseView detailsView;
	boolean isDetailsMoving = false;
	
	public SubTopicsView(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
		setBackgroundColor(0xFF888888);
		initScreenDimenstions();
	}

	public SubTopicsView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		// TODO Auto-generated constructor stub
		setBackgroundColor(0xFF888888);
		initScreenDimenstions();
	}

	public SubTopicsView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		// TODO Auto-generated constructor stub
		setBackgroundColor(0xFF888888);
		initScreenDimenstions();
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
			float width = 0;
			if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
				params = new FrameLayout.LayoutParams((int) (screenWidth/2 - screenWidth/2 * .30f), screenHeight);
				width = screenWidth/2 - screenWidth/2 * .30f;
			}
			else {
				params = new FrameLayout.LayoutParams((int) (screenWidth/2), screenHeight);
				width = screenWidth/2;
			}
			Log.d("Subtopics onMeasure", "Width: " + width);
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
			isDetailsMoving = false;
		}
		else if(action == MotionEvent.ACTION_MOVE) {
			float newX = event.getX();
			float dx = newX - px;
			if(Math.abs(dx) > 0) {
				float value = getLeft()+(dx*TOUCH_FACTOR);
				if(value < 0) {
					value = 0;
				}
				if(value+getWidth() > screenWidth) {
					value = screenWidth-getWidth();
				}
				layout((int)(value), 0, (int)(value+getWidth()), getHeight());
				if(mViewState == ViewState.DOCKED && detailsView.mViewState == ViewState.NORMAL && detailsView.getVisibility() == View.VISIBLE) {
					detailsView.layout(getLeft()+getWidth(), 0, getLeft()+getWidth()+detailsView.getWidth(), detailsView.getHeight());
					isDetailsMoving = true;
				}
			}

			px = newX;

		}
		else if(action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
			float toX = 0;
			if(mViewState == ViewState.NORMAL) {
				toX =xNormal-getLeft();
			}
			else if(mViewState == ViewState.DOCKED) {
				if(detailsView.mViewState == ViewState.NORMAL) {
					float dx = (xDocked+xNormal)/2;
					float cx = getLeft();
					if(cx + 140 > dx) {
						toX = xNormal - getLeft();
						mViewState = ViewState.NORMAL;
					}
					else {
						toX = xDocked-getLeft();
						mViewState = ViewState.DOCKED;

					}
				}
				else {
					toX = xNormal - getLeft();
				}
			}
			StackedAnimation anim = new StackedAnimation(this, 0, toX, ANIMATION_DURATION);
			anim.setListener(new OnHAnimationListener() {

				@Override
				public void onStart() {
					isAnimating = true;
					if(isDetailsMoving && mViewState == ViewState.NORMAL) {
						StackedAnimation anim1 = new StackedAnimation(detailsView, 0, xNormal-getLeft(), ANIMATION_DURATION);
						anim1.start();
					}
					else {
						if(isDetailsMoving) {
							StackedAnimation anim1 = new StackedAnimation(detailsView, 0, xDocked-getLeft(), ANIMATION_DURATION);
							anim1.start();
						}
					}
				}

				@Override
				public void onEnd() {
					isAnimating = false;
					changeState(mViewState, false);
					if(isDetailsMoving) {
						if(mViewState == ViewState.NORMAL) {
							detailsView.setVisibility(View.GONE);
						}
						else {
							detailsView.changeState(ViewState.NORMAL, false);
						}
					}
				}
			});
			anim.start();
		}
		return true;
	}

	public BaseView getDetailsView() {
		return detailsView;
	}

	public void setDetailsView(BaseView detailsView) {
		this.detailsView = detailsView;
	}

}
