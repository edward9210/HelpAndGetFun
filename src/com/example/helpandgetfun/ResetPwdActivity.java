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

public class ResetPwdActivity extends Activity {
	private EditText userNameEditText, realNameEditText ,mobilePhoneEditText, pwdEditText, pwdConfigEditText;
	private ImageButton cancelButton;
	private Button resetpwdButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resetpwd);
		setAllWidget();
		setAllListener();
	}


	private void setAllWidget() {
		// TODO Auto-generated method stub
		userNameEditText = (EditText) findViewById(R.id.resetpwd_userNameET);
		realNameEditText = (EditText) findViewById(R.id.resetpwd_realNameET);
		mobilePhoneEditText = (EditText) findViewById(R.id.resetpwd_mobilephoneET);
		pwdEditText = (EditText) findViewById(R.id.resetpwd_pwdET);
		pwdConfigEditText = (EditText) findViewById(R.id.resetpwd_pwdConfigET);
		resetpwdButton = (Button) findViewById(R.id.resetpwd_button);
		cancelButton = (ImageButton) findViewById(R.id.resetpwd_cancelbutton);
	}

	private void setAllListener() {
		resetpwdButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(ResetPwdActivity.this, "resetpwdButton!!!" , Toast.LENGTH_SHORT).show();
			}
		
		});
		cancelButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(ResetPwdActivity.this, "cancelButton!!!" , Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(); 
	        	intent.setClass(ResetPwdActivity.this, LoginActivity.class); /* 调用一个新的Activity */
	        	startActivity(intent);
	        	/* 关闭原本的Activity */ 
	        	ResetPwdActivity.this.finish();
			}
		
		});
		
	}
}
