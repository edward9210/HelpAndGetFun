package com.example.helpandgetfun;


import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


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
	}

	private void setAllWidget() {
		// TODO Auto-generated method stub
		mCancelbnt = (ImageButton) findViewById(R.id.friendlist_cancelbutton);
		mAddFriend = (ImageButton) findViewById(R.id.friendlist_addfriend_bnt);
		
		adapter = new MyAdapter(this, DataModel.getFriendList(), R.layout.friendlist_item,
				new String[]{"headImg", "userName"},
				new int[]{R.id.friendlist_headimg, R.id.friendlist_username});
	
		mListView = (ListView) findViewById(R.id.friendlist_lv);
		mMoreBnt = new Button(this);
		mMoreBnt.setText("更多");
		mMoreBnt.setBackgroundColor(Color.TRANSPARENT);
		mMoreBnt.setTextSize((float) 20.0);
		mMoreBnt.setGravity(Gravity.CENTER);
		mListView.addFooterView(mMoreBnt);
		mListView.setAdapter(adapter);
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
	}
	
	/* UIHandler负责更新页面 */
	private Handler mUIHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
		    switch (msg.what) {
		    case MORE_FINISHED:
		    	adapter.setCount(adapter.getCount() + 10);
				adapter.notifyDataSetChanged();
				mMoreBnt.setText("更多");
				//Toast.makeText(HomePage.this, "more!!!" , Toast.LENGTH_SHORT).show();
		    	break;
		    }
		}
	};
}

