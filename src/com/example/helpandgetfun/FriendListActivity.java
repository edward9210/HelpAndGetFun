package com.example.helpandgetfun;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class FriendListActivity extends Activity{
	private final int MORE_FINISHED = 0;

	private MyAdapter adapter;
	private ListView mListView;
	private Button mMoreBnt;
	private ImageButton mCancelbnt, mAddFriend;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friendlist);

		setAllWidget();
		setAllListener();
		
		mMoreBnt.setText("加载中...");
		new GetDataTask().execute();
	}

	private void setAllWidget() {
		// TODO Auto-generated method stub
		mCancelbnt = (ImageButton) findViewById(R.id.friendlist_cancelbutton);
		mAddFriend = (ImageButton) findViewById(R.id.friendlist_addfriend_bnt);

		if (DataModel.myFriendList == null)
			DataModel.myFriendList = new ArrayList<Map<String, Object>>();
		
		adapter = new MyAdapter(this, DataModel.myFriendList , R.layout.friendlist_item,
				new String[]{"headImg", "userName"},
				new int[]{R.id.friendlist_headimg, R.id.friendlist_username});

		mListView = (ListView) findViewById(R.id.friendlist_lv);
		mListView.setAdapter(adapter);
		
		mMoreBnt = new Button(this);
		mMoreBnt.setText("更多");
		mMoreBnt.setBackgroundColor(Color.TRANSPARENT);
		mMoreBnt.setTextSize((float) 20.0);
		mMoreBnt.setGravity(Gravity.CENTER);
		mListView.addFooterView(mMoreBnt);
	}

	private void setAllListener() {
		// TODO Auto-generated method stub
		mCancelbnt.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(); 
	        	intent.setClass(FriendListActivity.this, MainActivity.class); /* 调用一个新的Activity */
	        	startActivity(intent);
	        	/* 关闭原本的Activity */ 
	        	FriendListActivity.this.finish();
			}

		});

		mAddFriend.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(); 
	        	intent.setClass(FriendListActivity.this, AddFriendActivity.class); /* 调用一个新的Activity */
	        	startActivity(intent);
	        	/* 关闭原本的Activity */ 
	        	FriendListActivity.this.finish();
			}

		});


		mMoreBnt.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mMoreBnt.setText("加载中...");
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

		});

		mListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//封装bundle，传到FriendInfoActivity
				TextView friendName = (TextView) view.findViewById(R.id.friendlist_username);
				try {
					JSONObject json = DataModel.getUserInfo(friendName.getText().toString());
					Bundle bundle = new Bundle();
					bundle.putString("name", friendName.getText().toString());
					bundle.putString("realname", json.getString("realname"));
					bundle.putString("phone", json.getString("phone"));
					Intent intent = new Intent(); 
					intent.putExtras(bundle);
		        	intent.setClass(FriendListActivity.this, FriendInfoActivity.class); /* 调用一个新的Activity */
		        	startActivity(intent);
		        	FriendListActivity.this.finish();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	/* UIHandler负责更新页面 */
	private Handler mUIHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
		    switch (msg.what) {
		    case MORE_FINISHED:
		    	if (DataModel.myFriendList.size() > adapter.getCount()) {
		    		if (DataModel.myFriendList.size() >= adapter.getCount() + 10)
		    			adapter.setCount(adapter.getCount() + 10);
		    		else
		    			adapter.setCount(DataModel.myFriendList.size());
			    	adapter.notifyDataSetChanged();
		    	}
				mMoreBnt.setText("更多");
		    	break;
		    	
		    }
		}
	};
	
	private class GetDataTask extends AsyncTask<Void, Void, List<Map<String, Object> > > {

		@Override
		protected void onPostExecute(List<Map<String, Object> > result) {
			if (result != null) {
				DataModel.myFriendList.clear();
				if (result.size() != 0) {
					mMoreBnt.setText("更多");
					DataModel.myFriendList.addAll(result);
				}
				else
					mMoreBnt.setText("更多");
				adapter.notifyDataSetChanged();
			}
			else
				new GetDataTask().execute();
			super.onPostExecute(result);
		}

		@Override
		protected List<Map<String, Object> > doInBackground(Void... params) {
			try {
				return DataModel.getFriendList();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}
}
