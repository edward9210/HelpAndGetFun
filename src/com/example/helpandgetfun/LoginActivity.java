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

public class LoginActivity extends Activity {
	private EditText userNameEditText, pwdEditText;
	private ImageButton loginButton;
	private Button registerButton, forgetPwdButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		setAllWidget();
		setAllListener();
	}


	private void setAllWidget() {
		// TODO Auto-generated method stub
		userNameEditText = (EditText) findViewById(R.id.userNameET);
		pwdEditText = (EditText) findViewById(R.id.pwdET);
		loginButton = (ImageButton) findViewById(R.id.login_button);
		registerButton = (Button) findViewById(R.id.login_register);
		forgetPwdButton = (Button) findViewById(R.id.login_forgetpwd);
	}

	private void setAllListener() {
		loginButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(); 
	        	intent.setClass(LoginActivity.this, MainActivity.class); /* 调用一个新的Activity */
	        	startActivity(intent);
	        	/* 关闭原本的Activity */ 
	        	LoginActivity.this.finish();
				Toast.makeText(LoginActivity.this, "loginButton!!!" , Toast.LENGTH_SHORT).show();
			}
		
		});
		registerButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(LoginActivity.this, "registerButton!!!" , Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(); 
	        	intent.setClass(LoginActivity.this, RegisterActivity.class); /* 调用一个新的Activity */
	        	startActivity(intent);
	        	/* 关闭原本的Activity */ 
	        	LoginActivity.this.finish();
			}
		
		});
		forgetPwdButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(LoginActivity.this, "forgetPwdButton!!!" , Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(); 
	        	intent.setClass(LoginActivity.this, ResetPwdActivity.class); /* 调用一个新的Activity */
	        	startActivity(intent);
	        	/* 关闭原本的Activity */ 
	        	LoginActivity.this.finish();
			}
		
		});
	}
}
