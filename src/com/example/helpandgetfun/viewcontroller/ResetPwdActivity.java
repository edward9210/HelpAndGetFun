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
				//Toast.makeText(ResetPwdActivity.this, "resetpwdButton!!!" , Toast.LENGTH_SHORT).show();
				String name = userNameEditText.getText().toString();
				String realname = realNameEditText.getText().toString();
				String mobliePhone = mobilePhoneEditText.getText().toString();
				String password = pwdEditText.getText().toString();
				String passwordConfig = pwdConfigEditText.getText().toString();
				if (name.length() == 0 || realname.length() == 0 || mobliePhone.length() == 0 || password.length() == 0 || passwordConfig.length() == 0)
					Toast.makeText(ResetPwdActivity.this, "必填项没填" , Toast.LENGTH_SHORT).show();
				else if (name.length() < 6 || name.length() > 20)
					Toast.makeText(ResetPwdActivity.this, "用户名长度错误" , Toast.LENGTH_SHORT).show();
				else if (!password.equals(passwordConfig))
					Toast.makeText(ResetPwdActivity.this, "两次密码输入不一样" , Toast.LENGTH_SHORT).show();
				else {
					String result = DataUtils.resetPwd(name, realname, mobliePhone, password);
					if (result.equals(DataUtils.REGISTER_SUCCESS)) {
						Toast.makeText(ResetPwdActivity.this, "重置密码成功" , Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(); 
			        	intent.setClass(ResetPwdActivity.this, LoginActivity.class); /* 调用一个新的Activity */
			        	startActivity(intent);
			        	/* 关闭原本的Activity */ 
			        	ResetPwdActivity.this.finish();
					}
					else
						Toast.makeText(ResetPwdActivity.this, result , Toast.LENGTH_SHORT).show();
				}
			}
		
		});
		cancelButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(ResetPwdActivity.this, "cancelButton!!!" , Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(); 
	        	intent.setClass(ResetPwdActivity.this, LoginActivity.class); /* 调用一个新的Activity */
	        	startActivity(intent);
	        	/* 关闭原本的Activity */ 
	        	ResetPwdActivity.this.finish();
			}
		
		});
		
	}
}
