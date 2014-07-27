package com.example.helpandgetfun;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;

public class MyAdapter extends SimpleAdapter{
	private int myCount;

    private List<? extends Map<String, ?>> mData;

	public MyAdapter(Context context, List<? extends Map<String, ?>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		mData = data;
		myCount = 10;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getCount() {
		if (mData == null)
			return 0;
		if (mData.size() < 10)
			return mData.size();
		else
			return myCount;
    }
	
	public void setCount(int c) {
		if (mData.size() < c)
			myCount = mData.size();
		else
			myCount = c;
	}
	
}
