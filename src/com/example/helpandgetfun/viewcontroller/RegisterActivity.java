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

public class RegisterActivity extends Activity {
	private EditText userNameEditText, realNameEditText ,mobilePhoneEditText, pwdEditText, pwdConfigEditText;
	private ImageButton cancelButton;
	private Button registerButton;
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
		registerButton = (Button) findViewById(R.id.register_registerbutton);
		cancelButton = (ImageButton) findViewById(R.id.register_cancelbutton);
	}

	private void setAllListener() {
		registerButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String name = userNameEditText.getText().toString();
				final String realname = realNameEditText.getText().toString();
				final String mobliePhone = mobilePhoneEditText.getText().toString();
				final String password = pwdEditText.getText().toString();
				final String passwordConfig = pwdConfigEditText.getText().toString();
				if (name.length() == 0 || realname.length() == 0 || mobliePhone.length() == 0 || password.length() == 0 || passwordConfig.length() == 0)
					Toast.makeText(RegisterActivity.this, "必填项没填" , Toast.LENGTH_SHORT).show();
				else if (name.length() < 6 || name.length() > 20)
					Toast.makeText(RegisterActivity.this, "用户名长度错误" , Toast.LENGTH_SHORT).show();
				else if (!name.matches("[0-9A-Za-z_]*"))
					Toast.makeText(RegisterActivity.this, "用户名字符错误,只允许数字字母下划线" , Toast.LENGTH_SHORT).show();
				else if (!mobliePhone.matches("[0-9]*") || (mobliePhone.length() != 11 && mobliePhone.length() != 6 && mobliePhone.length() != 4))
					Toast.makeText(RegisterActivity.this, "手机号错误" , Toast.LENGTH_SHORT).show();
				else if (!password.equals(passwordConfig))
					Toast.makeText(RegisterActivity.this, "两次密码输入不一样" , Toast.LENGTH_SHORT).show();
				else {
					registerButton.setText("注册中...");
					registerButton.setClickable(false);
					new Thread(new Runnable() {
					    public void run() {
					    	String result = DataUtils.register(name, realname, mobliePhone, password);
					    	Bundle bundle = new Bundle();
					    	bundle.putString("result", result);
					    	Message mes = new Message();
					    	mes.setData(bundle);
					    	mUIHandler.sendMessage(mes);
					    }
					}).start();
					
					
				}
				
			}
		
		});
		cancelButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(RegisterActivity.this, "cancelButton!!!" , Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(); 
	        	intent.setClass(RegisterActivity.this, LoginActivity.class); /* 调用一个新的Activity */
	        	startActivity(intent);
	        	/* 关闭原本的Activity */ 
	        	RegisterActivity.this.finish();
			}
		
		});
		
	}
	
	private Handler mUIHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			String result = bundle.getString("result");
			registerButton.setText("注册");
			registerButton.setClickable(true);
			if (result.equals(DataUtils.REGISTER_SUCCESS)) {
				Toast.makeText(RegisterActivity.this, "注册成功" , Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(); 
	        	intent.setClass(RegisterActivity.this, LoginActivity.class); /* 调用一个新的Activity */
	        	startActivity(intent);
	        	/* 关闭原本的Activity */ 
	        	RegisterActivity.this.finish();
			}
			else
				Toast.makeText(RegisterActivity.this, result , Toast.LENGTH_SHORT).show();
		}
	};
}
