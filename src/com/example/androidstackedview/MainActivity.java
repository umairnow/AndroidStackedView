package com.example.androidstackedview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.androidstackedview.views.ColumnsLoader;
import com.example.androidstackedview.views.StackedView;
import com.example.androidstackedview.views.BaseView.ColumnType;
public class MainActivity extends FragmentActivity implements OnClickListener, ColumnsLoader {

	StackedView stackedView;
	View topicsBtn;
	View toolsBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		stackedView = (StackedView) findViewById(R.id.stackedView);
		topicsBtn = findViewById(R.id.topics);
		topicsBtn.setOnClickListener(this);
		toolsBtn = findViewById(R.id.tools);
		toolsBtn.setOnClickListener(this);
		
		TopicsFragment f = new TopicsFragment();
		f.setFragmentLoader(this);
		loadTopicsFragment(f);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.topics:
			TopicsFragment f = new TopicsFragment();
			f.setFragmentLoader(this);
			loadTopicsFragment(f);
			break;
		case R.id.tools:
			ToolsFragment f1 = new ToolsFragment();
			loadTopicsFragment(f1);
			break;
		}

	}

	@Override
	public void loadSubtopicsFragment(Fragment f) {
		// TODO Auto-generated method stub
		stackedView.loadFragmentInSubtopicsFrame(f);
	}

	@Override
	public void loadTopicsFragment(Fragment f) {
		// TODO Auto-generated method stub
		stackedView.loadFragmentInTopicsFrame(f);
	}

	@Override
	public void loadDetailsFragment(Fragment f) {
		// TODO Auto-generated method stub
		stackedView.loadFragmentInDetailsFrame(f);
	}

	@Override
	public void onBack(ColumnType type) {
		// TODO Auto-generated method stub
		switch(type) {
		case DETAILS:
			stackedView.resetTo2ndPhase();
			break;
		case SUBTOPIC:
			stackedView.resetTo1stPhase();
			break;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(stackedView.onBackKeyPressed(keyCode, event)) {
			return true;
		}
		else {
			finish();
			return false;
		}
	}

}
