package com.example.helpandgetfun.fragment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import com.example.helpandgetfun.R;
import com.example.helpandgetfun.R.id;
import com.example.helpandgetfun.R.layout;
import com.example.helpandgetfun.adapter.MyAdapter;
import com.example.helpandgetfun.utils.DataUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentHomePage extends Fragment implements OnRefreshListener<ListView>{
	private PullToRefreshListView mListView;
	private MyAdapter adapter;
	private boolean isRefreshing = false;
	
	@Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
		return inflater.inflate(R.layout.task_page, container, false);  
    }  
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		DataUtils.homePageList = new ArrayList<Map<String, Object>>();
		adapter = new MyAdapter(getActivity().getApplicationContext(), DataUtils.homePageList, R.layout.pulldown_item,
					new String[]{"headImg", "userName", "date", "state", "taskContent", "executeTime", "Location", "postscript"},
					new int[] {R.id.item_head_image, R.id.item_username, R.id.item_date, R.id.item_state, R.id.item_task_content,  R.id.item_time_content, R.id.item_location_content, R.id.item_addition_content});
		
		mListView = (PullToRefreshListView) getView().findViewById(R.id.homepage_itemList);
		mListView.setOnRefreshListener(this);
		mListView.setAdapter(adapter);

		//使用方法参考于 http://blog.csdn.net/ueryueryuery/article/details/17440465
		mListView.setMode(Mode.BOTH);
		mListView.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载");
		mListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("加载中...");
		mListView.getLoadingLayoutProxy(false, true).setReleaseLabel("松手以加载...");
		
		DataUtils.homePageList.clear();
		if (adapter != null)
			adapter.notifyDataSetChanged(); 
		
		new GetDataTask().execute();
		
		mListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Button b = (Button) view.findViewById(R.id.task_info_accept);
				final TextView tc = (TextView) view.findViewById(R.id.item_task_content);
				final TextView on = (TextView) view.findViewById(R.id.item_username);
				if (b.getVisibility() == View.GONE)
					b.setVisibility(View.VISIBLE);
				else if (b.getVisibility() == View.VISIBLE)
					b.setVisibility(View.GONE);
				b.setOnClickListener(new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						String taskContent = tc.getText().toString();
						String ownername = on.getText().toString();
						String result = DataUtils.acceptTask(taskContent, ownername);
						if (result.equals(DataUtils.ACCEPTTASK_SUCCESS)) {
							new GetDataTask().execute();
							Toast.makeText(getActivity().getApplicationContext(), "成功接受任务" , Toast.LENGTH_SHORT).show();
						}
						else {
							Toast.makeText(getActivity().getApplicationContext(), "接受任务失败" , Toast.LENGTH_SHORT).show();
						}
						v.setVisibility(View.GONE);
					}
				});
			}
			
		});
		
	}
	
	private class GetDataTask extends AsyncTask<Void, Void, List<Map<String, Object> > > {

		@Override
		protected void onPostExecute(List<Map<String, Object> > result) {
			if (result != null) {
				DataUtils.homePageList.clear();
				if (result.size() != 0) {
					DataUtils.homePageList.addAll(result);
				}
				adapter.notifyDataSetChanged();	
				mListView.onRefreshComplete();
				isRefreshing = false;
				
			}
			else
				new GetDataTask().execute();		
			super.onPostExecute(result);
		}

		@Override
		protected List<Map<String, Object> > doInBackground(Void... params) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			try {
				return DataUtils.getHomePageData();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}
	
	private class GetMoreTask extends AsyncTask<Void, Void, String[] > {

		@Override
		protected void onPostExecute(String[] result) {
			if (DataUtils.homePageList.size() > adapter.getCount()) {
	    		if (DataUtils.homePageList.size() >= adapter.getCount() + 10)
	    			adapter.setCount(adapter.getCount() + 10);
	    		else
	    			adapter.setCount(DataUtils.homePageList.size());
		    	adapter.notifyDataSetChanged();
	    	}
			mListView.onRefreshComplete();
			isRefreshing = false;
			super.onPostExecute(result);
		}

		@Override
		protected String[] doInBackground(Void... params) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			return null;
		}
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		if (!isRefreshing) {
            isRefreshing = true;
            if (mListView.isHeaderShown()) {
            	new GetDataTask().execute();
            } else if (mListView.isFooterShown()) {
            	new GetMoreTask().execute();
            }
        } else {
            mListView.onRefreshComplete();
        }
	}
}

