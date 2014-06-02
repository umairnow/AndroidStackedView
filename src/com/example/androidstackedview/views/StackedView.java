package com.example.androidstackedview.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.androidstackedview.R;
import com.example.androidstackedview.views.BaseView.ViewState;

public class StackedView extends RelativeLayout {

	Context context;
	View dashboardView;
	TopicsView topicsView;
	SubTopicsView subtopicsView;
	DetailsView detailsView;
	float widthFactor = 10;
	public static int screenHeight;
	public static int screenWidth;
	public static float xDocked = 0;
	public StackedView(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public StackedView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	private void init() {
		initScreenDimenstions();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);

		int orientation = context.getResources().getConfiguration().orientation;

		initColumnViews();
		if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
			topicsView.layout(dashboardView.getWidth(), 0, (int) (dashboardView.getWidth()+screenWidth/2 - screenWidth/2 * .30f), screenHeight);
		}
		else {
			topicsView.layout(dashboardView.getWidth(), 0, (int) (dashboardView.getWidth()+screenWidth/2), screenHeight);
		}
		topicsView.setxNormal(dashboardView.getWidth());
		topicsView.setxDocked(xDocked);
		if(!topicsView.isAnimating) {
			topicsView.changeState(topicsView.mViewState, false);
		}
		
		if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
			subtopicsView.layout((int) (topicsView.getLeft()+topicsView.getWidth()), 0, (topicsView.getLeft()+topicsView.getWidth()) + topicsView.getWidth(), screenHeight);
			subtopicsView.setxNormal(topicsView.getLeft()+topicsView.getWidth());
		}
		else {
			subtopicsView.layout((int) (screenWidth/2), 0, screenWidth, screenHeight);
			subtopicsView.setxNormal(screenWidth/2);
		}
		
		subtopicsView.setxDocked(xDocked);
		subtopicsView.changeState(subtopicsView.mViewState, false);

		//		detailsView.layout((int) (xDocked), 0, screenWidth, screenHeight);
		//		detailsView.setxNormal(xDocked);
		//		detailsView.setxDocked(xDocked);
		//		detailsView.changeState(detailsView.mViewState, false);
		if(orientation == Configuration.ORIENTATION_PORTRAIT) {
			detailsView.layout((int) (xDocked), 0, screenWidth, screenHeight);
			detailsView.setxNormal(xDocked + topicsView.getWidth());
			detailsView.setxDocked(xDocked);
			detailsView.changeState(ViewState.DOCKED, false);
		}
		else if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
			detailsView.layout((int) (xDocked + topicsView.getWidth()/2), 0, screenWidth, screenHeight);
			detailsView.setxNormal(xDocked + topicsView.getWidth());
			detailsView.setxDocked(xDocked + topicsView.getWidth()/2);
			detailsView.changeState(ViewState.DOCKED, false);
		}



	}

	public void initColumnViews() {
		if(dashboardView == null) {
			dashboardView = findViewWithTag("dashboard");

		}
		if(dashboardView != null) {
			View v = dashboardView.findViewById(R.id.testimg);
			if(v != null) {
				xDocked = v.getLeft() + v.getWidth();
			}
		}
		if(topicsView == null) {
			topicsView = (TopicsView) findViewWithTag("topics");
		}
		if(subtopicsView == null) {
			subtopicsView = (SubTopicsView) findViewWithTag("subtopics");
		}
		if(detailsView == null) {
			detailsView = (DetailsView) findViewWithTag("details");
			subtopicsView.setDetailsView(detailsView);
		}
	}

	private void initScreenDimenstions() {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		screenHeight = displaymetrics.heightPixels;
		screenWidth = displaymetrics.widthPixels;

	}

	public void resetTo1stPhase() {

		topicsView.setVisibility(View.VISIBLE);
		//		if(topicsView.mViewState == ViewState.DOCKED) {
		//			topicsView.changeState(ViewState.NORMAL, true);
		//		} else {
		topicsView.changeState(ViewState.NORMAL, false);
		//		}

		subtopicsView.setVisibility(View.GONE);
		subtopicsView.changeState(ViewState.NORMAL, false);

		detailsView.setVisibility(View.GONE);
		detailsView.changeState(ViewState.NORMAL, false);

	}

	public void resetTo2ndPhase() {
		topicsView.setVisibility(View.VISIBLE);
		int orientation = context.getResources().getConfiguration().orientation;
		if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
			topicsView.changeState(ViewState.NORMAL, false);
		}
		else {
			if(topicsView.mViewState == ViewState.DOCKED) {
				topicsView.changeState(ViewState.DOCKED, false);
			} else {
				topicsView.changeState(ViewState.DOCKED, true);
			}
		}

		subtopicsView.setVisibility(View.VISIBLE);
		subtopicsView.changeState(ViewState.NORMAL, false);

		detailsView.setVisibility(View.GONE);
		detailsView.changeState(ViewState.NORMAL, false);
	}

	public void resetTo3rdPhase() {
		// TODO Auto-generated method stub
		subtopicsView.setVisibility(View.VISIBLE);
		subtopicsView.changeState(ViewState.DOCKED, false);

		detailsView.setVisibility(View.VISIBLE);
		detailsView.changeState(ViewState.NORMAL, false);
	}

	public void loadFragmentInTopicsFrame(Fragment f) {
		initColumnViews();
		FragmentManager fm = ((FragmentActivity)context).getSupportFragmentManager();
		FragmentTransaction t = fm.beginTransaction();
		t.replace(topicsView.findViewById(R.id.fr1).getId(), f);
		t.commit();
		resetTo1stPhase();
	}

	public void loadFragmentInSubtopicsFrame(Fragment f) {
		initColumnViews();
		FragmentManager fm = ((FragmentActivity)context).getSupportFragmentManager();
		FragmentTransaction t = fm.beginTransaction();
		t.replace(subtopicsView.findViewById(R.id.fr2).getId(), f);
		t.commit();
		resetTo2ndPhase();
	}

	public void loadFragmentInDetailsFrame(Fragment f) {
		initColumnViews();
		FragmentManager fm = ((FragmentActivity)context).getSupportFragmentManager();
		FragmentTransaction t = fm.beginTransaction();
		t.replace(detailsView.findViewById(R.id.fr3).getId(), f);
		t.commit();
		resetTo3rdPhase();
	}

	public boolean onBackKeyPressed(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			if(detailsView.getVisibility() == View.VISIBLE) {
				resetTo2ndPhase();
				return true;
			}
			else if(subtopicsView.getVisibility() == View.VISIBLE) {
				resetTo1stPhase();
				return true;
			}
		}
		return false;
	}
}
