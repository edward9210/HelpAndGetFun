package com.example.helpandgetfun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import com.example.helpandgetfun.RefreshListView.RefreshListener;

import android.app.Activity;
import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

public class FragmentOtherPage extends Fragment implements RefreshListener{
	private final int MORE_FINISHED = 0;
	private final int REFRESHED = 1;
	
	private RefreshListView mListView;
	private MyAdapter adapter;
	
	@Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
		return inflater.inflate(R.layout.task_page, container, false); 
    }  
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		DataModel.otherList = new ArrayList<Map<String, Object>>();
		adapter = new MyAdapter(getActivity().getApplicationContext(), DataModel.otherList, R.layout.pulldown_item,
					new String[]{"headImg", "userName", "date", "state", "taskContent", "executeTime", "Location", "postscript"},
					new int[]{R.id.item_head_image, R.id.item_username, R.id.item_date, R.id.item_state, R.id.item_task_content,  R.id.item_time_content, R.id.item_location_content, R.id.item_addition_content});
		
		mListView = (RefreshListView) getView().findViewById(R.id.homepage_itemList);
		mListView.setOnRefreshListener(this);
		mListView.setAdapter(adapter);
		setListViewHeightBasedOnChildren(mListView);
		
		refreshing();
		
		mListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Button b = (Button) view.findViewById(R.id.task_info_accept);
				final TextView tc = (TextView) view.findViewById(R.id.item_task_content);
				if (b.getVisibility() == View.GONE)
					b.setVisibility(View.VISIBLE);
				else if (b.getVisibility() == View.VISIBLE)
					b.setVisibility(View.GONE);
				b.setOnClickListener(new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						String taskContent = tc.getText().toString();
						String result = DataModel.acceptTask(taskContent);
						if (result.equals(DataModel.ACCEPTTASK_SUCCESS)) {
							Toast.makeText(getActivity().getApplicationContext(), "成功接受任务，请刷新一下任务列表" , Toast.LENGTH_SHORT).show();
						}
						else {
							Toast.makeText(getActivity().getApplicationContext(), "接受任务失败，请刷新一下任务列表" , Toast.LENGTH_SHORT).show();
						}
						v.setVisibility(View.GONE);
					}
				});
			}
			
		});
	}
	
	@Override
	public Object refreshing() {
		// TODO Auto-generated method stub
		//Toast.makeText(HomePage.this, "refreshing!!!" , Toast.LENGTH_SHORT).show();
		new GetDataTask().execute();
		
		return null;
	}



	@Override
	public void refreshed(Object obj) {
		// TODO Auto-generated method stub
		//Toast.makeText(HomePage.this, "refreshed!!!" , Toast.LENGTH_SHORT).show();
		new Thread(new Runnable() {
			@Override
			public void run() {		
				
				Message msg = mUIHandler.obtainMessage(REFRESHED);
				msg.sendToTarget();
			}
		}).start();
	}



	@Override
	public void more() {
		// TODO Auto-generated method stub
		//载入时效果跟获取数据
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}				
				Message msg = mUIHandler.obtainMessage(MORE_FINISHED);
				msg.sendToTarget();
			}
		}).start();
		
		
	}

	/* 参考 http://blog.sina.com.cn/s/blog_6dc41baf010193zi.html
	 * 并进行了修改，修补了当listview的item过少时，显示的bug 
	 * ----注意：每次更新数据时，要调用一次----*/
	public void setListViewHeightBasedOnChildren(ListView listView) {
	
		  ListAdapter listAdapter = listView.getAdapter();
	
		  if (listAdapter == null) {
			  return;
		  }
	
		  int totalHeight = 0;
		  
		  //不计算headView，所以从1开始
		  for (int i = 1; i < listAdapter.getCount(); i++) {
			   View listItem = listAdapter.getView(i, null, listView);
			   listItem.measure(0, 0);
			   totalHeight += listItem.getMeasuredHeight();
		  }
		  //获取屏幕高度
		  Display display = getActivity().getWindowManager().getDefaultDisplay();
		  Point size = new Point();
		  display.getSize(size);
		  int height = size.y;
		  
		  ViewGroup.LayoutParams params = listView.getLayoutParams();
		  //如果listview高度大于屏幕大小，则不更新
		  if (totalHeight < height) {
			  params.height = totalHeight
			    + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		  }
		  else {
			  params.height = ViewGroup.LayoutParams.WRAP_CONTENT;  
		  }
		  listView.setLayoutParams(params);
	}
	
	
	/* UIHandler负责更新页面 */
	private Handler mUIHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
		    switch (msg.what) {
			    case MORE_FINISHED:
			    	adapter.setCount(adapter.getCount() + 10);
					adapter.notifyDataSetChanged();
					mListView.finishFootView();
					//Toast.makeText(HomePage.this, "more!!!" , Toast.LENGTH_SHORT).show();
			    	break;
			    case REFRESHED:
			    	adapter.notifyDataSetChanged();
			    	setListViewHeightBasedOnChildren(mListView);
			    	break;
		    }
		}
	};
	
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected void onPostExecute(String[] result) {
			
			adapter.notifyDataSetChanged();
	    	setListViewHeightBasedOnChildren(mListView);

			super.onPostExecute(result);
		}

		@Override
		protected String[] doInBackground(Void... params) {
			try {
				DataModel.getOtherData();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String[] result = {"result", "ok"};
			return result;
		}
	}
}

