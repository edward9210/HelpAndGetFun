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

public class AddFriendActivity extends Activity {
	private EditText userNameEt;
	private ImageButton cancelButton;
	private Button addFriendButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addfriend);
		setAllWidget();
		setAllListener();
	}


	private void setAllWidget() {
		// TODO Auto-generated method stub
		
		userNameEt = (EditText) findViewById(R.id.addfriend_username);
		addFriendButton = (Button) findViewById(R.id.addfriend_bnt);
		cancelButton = (ImageButton) findViewById(R.id.addfriend_cancelbutton);
	}

	private void setAllListener() {
		addFriendButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(AddFriendActivity.this, "addFriendButton!!!" , Toast.LENGTH_SHORT).show();
				final String friendName = userNameEt.getText().toString();
				if (friendName.length() > 0) {
					new Thread(new Runnable() {
					    public void run() {
					    	String result = DataUtils.addFriend(friendName);
					    	Bundle bundle = new Bundle();
					    	bundle.putString("result", result);
					    	Message mes = new Message();
					    	mes.setData(bundle);
					    	mUIHandler.sendMessage(mes);
					    }
					}).start();
					
				}
				else {
					Toast.makeText(AddFriendActivity.this, "请输入想关注人的名字" , Toast.LENGTH_SHORT).show();
				}
			}
		
		});
		cancelButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(AddFriendActivity.this, "cancelButton!!!" , Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(); 
	        	intent.setClass(AddFriendActivity.this, FriendListActivity.class); /* 调用一个新的Activity */
	        	startActivity(intent);
	        	/* 关闭原本的Activity */ 
	        	AddFriendActivity.this.finish();
			}
		
		});
		
	}
	private Handler mUIHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			String result = bundle.getString("result");
			if (result.equals(DataUtils.ADDFRIEND_SUCCESS)) {
				Toast.makeText(AddFriendActivity.this, "关注成功" , Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(); 
	        	intent.setClass(AddFriendActivity.this, FriendListActivity.class); /* 调用一个新的Activity */
	        	startActivity(intent);
	        	/* 关闭原本的Activity */ 
	        	AddFriendActivity.this.finish();
			}
			else
				Toast.makeText(AddFriendActivity.this, "关注失败" , Toast.LENGTH_SHORT).show();
		}
	};
}
