package com.example.androidstackedview;

import com.example.androidstackedview.views.ColumnsLoader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class TopicsFragment extends Fragment implements OnClickListener {

	ColumnsLoader loader;
	View p;
	View topic2;
	
	
	public TopicsFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.topics_fragment, null);
		topic2 = v.findViewById(R.id.topic2);
		topic2.setOnClickListener(this);
		return  v;
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.topic2) {
			Subtopic2Fragment f2 = new Subtopic2Fragment();
			f2.setLoader(loader);
			loader.loadSubtopicsFragment(f2);
		}
	}

	public ColumnsLoader getLoader() {
		return loader;
	}

	public void setFragmentLoader(ColumnsLoader loader) {
		this.loader = loader;
	}

}

