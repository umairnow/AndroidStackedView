package com.example.androidstackedview.views;

import android.os.Handler;
import android.util.Log;
import android.view.View;

public class StackedAnimation {

	float fromX = 0;
	float toX = 0;
	long duration = 0;
	float totalDistance = 0;
	float distanceCovered = 0;
	float distanceIncrement = 0;
	long updateInterval = 10;
	boolean animationEnded = false;
	BaseView mView;
	Handler mHandler;
	OnHAnimationListener listener;
	
	public StackedAnimation(View v, float fromX, float toX, long duration) {
		this.mView = (BaseView) v;
		this.fromX = fromX;
		this.toX = toX;
		this.totalDistance = toX - fromX;
		this.duration = duration;
		this.distanceIncrement = totalDistance/duration; 
		mHandler = new Handler();
	}
	
	public void start() {
		if(listener != null) {
			listener.onStart();
		}
		doUpdate();
	}
	
	Runnable updateRunnable = new Runnable() {
		public void run() {
			if(animationEnded) {
				return;
			}
			if(distanceCovered < Math.abs(totalDistance)) {
				mView.layout((int)(mView.getLeft() + distanceIncrement*updateInterval), 0, (int)(mView.getLeft() + distanceIncrement*updateInterval + mView.getWidth()), mView.getHeight());
				distanceCovered = distanceCovered + Math.abs(distanceIncrement*updateInterval);
			}
			else {
				// end of animation. call onEnd of listener
				animationEnded = true;
				if(listener != null) {
					listener.onEnd();
				}
			}
			doUpdate();
		}
	};
	
	private void doUpdate() {
		mHandler.postDelayed(updateRunnable, updateInterval);
	}
	
	public float getFromX() {
		return fromX;
	}

	public void setFromX(float fromX) {
		this.fromX = fromX;
	}

	public float getToX() {
		return toX;
	}

	public void setToX(float toX) {
		this.toX = toX;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public View getmView() {
		return mView;
	}

	public void setmView(View mView) {
		this.mView = (BaseView) mView;
	}

	public OnHAnimationListener getListener() {
		return listener;
	}

	public void setListener(OnHAnimationListener listener) {
		this.listener = listener;
	}

	public interface OnHAnimationListener {
		public void onStart();
		public void onEnd();
	}

}
