package com.example.androidstackedview.views;

import com.example.androidstackedview.views.StackedAnimation.OnHAnimationListener;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class BaseView extends RelativeLayout {

	float xNormal;
	float xDocked;
	float px;
	boolean isAnimating = false;
	public ViewState mViewState = ViewState.NORMAL;
	final float TOUCH_FACTOR = 0.60f;
	final long ANIMATION_DURATION = 100;


	public BaseView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public BaseView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public BaseView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		if(isAnimating) {
			return true;
		}
		if(mViewState == ViewState.DOCKED) {
			return true;
		}
		if(action == MotionEvent.ACTION_DOWN) {
			px = event.getX();
		}
		else if(action == MotionEvent.ACTION_MOVE) {
			float newX = event.getX();
			float dx = newX - px;
			if(Math.abs(dx) > 0) {
				float value = getLeft()+(dx*TOUCH_FACTOR);
				if(value < 0) {
					value = 0;
				}
				layout((int)(value), 0, (int)(value+getWidth()), getHeight());
			}

			px = newX;

		}
		else if(action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
			float toX = 0;
			if(mViewState == ViewState.NORMAL) {
				toX =xNormal-getLeft();
			}
			else if(mViewState == ViewState.DOCKED) {
				toX = xDocked-getLeft();
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

	public void changeState(ViewState state, boolean animate) {
		mViewState = state;
		if(mViewState == ViewState.NORMAL) {
			if(animate) {
				StackedAnimation anim = new StackedAnimation(this, getLeft(), xNormal, ANIMATION_DURATION);
				anim.setListener(new OnHAnimationListener() {

					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						isAnimating = true;
					}

					@Override
					public void onEnd() {
						// TODO Auto-generated method stub
						isAnimating = false;
						layout((int) xNormal, 0, (int) (xNormal+getWidth()), getHeight());
					}
				});
				anim.start();
			} else {
				layout((int) xNormal, 0, (int) (xNormal+getWidth()), getHeight());
			}

		}
		else if(mViewState == ViewState.DOCKED) {
			if(animate) {
				StackedAnimation anim = new StackedAnimation(this, getLeft(), xDocked, ANIMATION_DURATION);
				anim.setListener(new OnHAnimationListener() {

					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						isAnimating = true;
					}

					@Override
					public void onEnd() {
						// TODO Auto-generated method stub
						isAnimating = false;
						layout((int) xDocked, 0, (int) (xDocked+getWidth()), getHeight());
					}
				});
				anim.start();
			} else {
				layout((int) xDocked, 0, (int) (xDocked+getWidth()), getHeight());
			}
		}
	}

	public float getxNormal() {
		return xNormal;
	}

	public void setxNormal(float xNormal) {
		this.xNormal = xNormal;
	}

	public float getxDocked() {
		return xDocked;
	}

	public void setxDocked(float xLocked) {
		this.xDocked = xLocked;
	}

	public boolean isAnimating() {
		return isAnimating;
	}

	public void setAnimating(boolean isAnimating) {
		this.isAnimating = isAnimating;
	}

	public enum ViewState {
		NORMAL,
		DOCKED,
		NONE;
	}

	public enum ColumnType {
		TOPIC,
		SUBTOPIC,
		DETAILS;
	}




}
