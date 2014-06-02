package com.example.androidstackedview.views;

import com.example.androidstackedview.views.BaseView.ColumnType;
import android.support.v4.app.Fragment;

public interface ColumnsLoader {

	public void loadSubtopicsFragment(Fragment f);
	public void loadTopicsFragment(Fragment f);
	public void loadDetailsFragment(Fragment f);
	public void onBack(ColumnType type);
	
}
