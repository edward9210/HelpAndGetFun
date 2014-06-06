package com.example.helpandgetfun;


import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.os.Build;

public class PersonInfoActivity extends Activity {
	private EditText realNameEditText ,mobilePhoneEditText;
	private TextView userNameTextView;
	private ImageButton cancelButton;
	private Button saveButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_info);
		setAllWidget();
		setAllListener();
		init();
	}


	private void init() {
		// TODO Auto-generated method stub
		userNameTextView.setText(DataModel.mUserName);
		realNameEditText.setText(DataModel.mRealName);
		mobilePhoneEditText.setText(DataModel.mPhone);
	}


	private void setAllWidget() {
		// TODO Auto-generated method stub
		
		userNameTextView = (TextView) findViewById(R.id.person_info_username);
		realNameEditText = (EditText) findViewById(R.id.person_info_realname);
		mobilePhoneEditText = (EditText) findViewById(R.id.person_info_mobilephone);
		saveButton = (Button) findViewById(R.id.person_info_save);
		cancelButton = (ImageButton) findViewById(R.id.person_info_cancelbutton);
	}

	private void setAllListener() {
		saveButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(PersonInfoActivity.this, "saveButton!!!" , Toast.LENGTH_SHORT).show();
			}
		
		});
		cancelButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(PersonInfoActivity.this, "cancelButton!!!" , Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(); 
	        	intent.setClass(PersonInfoActivity.this, MainActivity.class); /* 调用一个新的Activity */
	        	startActivity(intent);
	        	/* 关闭原本的Activity */ 
	        	PersonInfoActivity.this.finish();
			}
		
		});
		
	}
}
