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

public class RegisterActivity extends Activity {
	private EditText userNameEditText, realNameEditText ,mobilePhoneEditText, pwdEditText, pwdConfigEditText;
	private ImageButton registerButton, cancelButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		setAllWidget();
		setAllListener();
	}


	private void setAllWidget() {
		// TODO Auto-generated method stub
		userNameEditText = (EditText) findViewById(R.id.register_userNameET);
		realNameEditText = (EditText) findViewById(R.id.register_realNameET);
		mobilePhoneEditText = (EditText) findViewById(R.id.register_mobilephoneET);
		pwdEditText = (EditText) findViewById(R.id.register_pwdET);
		pwdConfigEditText = (EditText) findViewById(R.id.register_pwdConfigET);
		registerButton = (ImageButton) findViewById(R.id.register_registerbutton);
		cancelButton = (ImageButton) findViewById(R.id.register_cancelbutton);
	}

	private void setAllListener() {
		registerButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(RegisterActivity.this, "registerButton!!!" , Toast.LENGTH_SHORT).show();
			}
		
		});
		cancelButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(RegisterActivity.this, "cancelButton!!!" , Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(); 
	        	intent.setClass(RegisterActivity.this, LoginActivity.class); /* 调用一个新的Activity */
	        	startActivity(intent);
	        	/* 关闭原本的Activity */ 
	        	RegisterActivity.this.finish();
			}
		
		});
		
	}
}
