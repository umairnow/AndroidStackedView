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

public class DetailsFragment extends Fragment implements OnClickListener {

	Button back;
	ColumnsLoader loader;
	
	
	public DetailsFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.detailsview, null);
		back = (Button) v.findViewById(R.id.back);
		back.setOnClickListener(this);
		return  v;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.back) {
			loader.onBack(ColumnType.DETAILS);
		}
	}

	public ColumnsLoader getLoader() {
		return loader;
	}

	public void setLoader(ColumnsLoader loader) {
		this.loader = loader;
	}

}
