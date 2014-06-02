package com.example.androidstackedview;

import com.example.androidstackedview.views.ColumnsLoader;
import com.example.androidstackedview.views.BaseView.ColumnType;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class Subtopic2Fragment extends Fragment implements OnClickListener {

	Button btn1;
	ColumnsLoader loader;
	Button back;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.subtopic2, null);
		btn1 = (Button)v.findViewById(R.id.button1);
		back = (Button) v.findViewById(R.id.back);
		back.setOnClickListener(this);
		btn1.setOnClickListener(this);
		return v;
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.button1) {
			DetailsFragment df = new DetailsFragment();
			df.setLoader(loader);
			loader.loadDetailsFragment(df);
		}
		else if(v.getId() == R.id.back) {
			loader.onBack(ColumnType.SUBTOPIC);
		}
	}
	
	public ColumnsLoader getLoader() {
		return loader;
	}


	public void setLoader(ColumnsLoader loader) {
		this.loader = loader;
	}
}
