package com.example.helpandgetfun.viewcontroller;


import com.example.helpandgetfun.R;
import com.example.helpandgetfun.R.id;
import com.example.helpandgetfun.R.layout;
import com.example.helpandgetfun.utils.DataUtils;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.os.Build;

public class FriendInfoActivity extends Activity {
	private TextView realNameTv ,mobilePhoneTv, userNameTv;
	private ImageButton cancelButton;
	private Button deleteButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_info);
		Bundle bundle = this.getIntent().getExtras();
		setAllWidget();
		setAllListener();
		
		userNameTv.setText(bundle.getString("name"));
		realNameTv.setText(bundle.getString("realname"));
		mobilePhoneTv.setText(bundle.getString("phone"));
	}


	private void setAllWidget() {
		// TODO Auto-generated method stub
		
		realNameTv = (TextView) findViewById(R.id.friend_info_realname);
		mobilePhoneTv = (TextView) findViewById(R.id.friend_info_mobilephone);
		userNameTv = (TextView) findViewById(R.id.friend_info_username);
		deleteButton = (Button) findViewById(R.id.friend_info_delete);
		cancelButton = (ImageButton) findViewById(R.id.friend_info_cancelbutton);
	}

	private void setAllListener() {
		deleteButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				deleteButton.setText("删除中...");
		    	deleteButton.setClickable(false);
				new Thread(new Runnable() {
				    public void run() {
				    	String friendName = userNameTv.getText().toString();
				    	String result = DataUtils.deleteFriend(friendName);
				    	Bundle bundle = new Bundle();
				    	bundle.putString("result", result);
				    	Message mes = new Message();
				    	mes.setData(bundle);
				    	mUIHandler.sendMessage(mes);
				    }
				}).start();

			}
		
		});
		cancelButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(FriendInfoActivity.this, "cancelButton!!!" , Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(); 
	        	intent.setClass(FriendInfoActivity.this, FriendListActivity.class); /* 调用一个新的Activity */
	        	startActivity(intent);
	        	/* 关闭原本的Activity */ 
	        	FriendInfoActivity.this.finish();
			}
		
		});
		
	}
	
	private Handler mUIHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			String result = bundle.getString("result");
			deleteButton.setText("删除");
	    	deleteButton.setClickable(true);
	    	if (result.equals(DataUtils.DELETEFRIEND_SUCCESS)) {
				Toast.makeText(FriendInfoActivity.this, "删除成功" , Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(); 
	        	intent.setClass(FriendInfoActivity.this, FriendListActivity.class); /* 调用一个新的Activity */
	        	startActivity(intent);
	        	/* 关闭原本的Activity */ 
	        	FriendInfoActivity.this.finish();
			}
			else
				Toast.makeText(FriendInfoActivity.this, "删除失败" , Toast.LENGTH_SHORT).show();
		}
	};
}
