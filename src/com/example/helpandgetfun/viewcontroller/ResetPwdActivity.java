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
				final String name = userNameEditText.getText().toString();
				final String realname = realNameEditText.getText().toString();
				final String mobliePhone = mobilePhoneEditText.getText().toString();
				final String password = pwdEditText.getText().toString();
				final String passwordConfig = pwdConfigEditText.getText().toString();
				if (name.length() == 0 || realname.length() == 0 || mobliePhone.length() == 0 || password.length() == 0 || passwordConfig.length() == 0)
					Toast.makeText(ResetPwdActivity.this, "������û��" , Toast.LENGTH_SHORT).show();
				else if (name.length() < 6 || name.length() > 20)
					Toast.makeText(ResetPwdActivity.this, "�û������ȴ���" , Toast.LENGTH_SHORT).show();
				else if (!password.equals(passwordConfig))
					Toast.makeText(ResetPwdActivity.this, "�����������벻һ��" , Toast.LENGTH_SHORT).show();
				else {
					resetpwdButton.setText("����������...");
					resetpwdButton.setClickable(false);
					new Thread(new Runnable() {
					    public void run() {
					    	String result = DataUtils.resetPwd(name, realname, mobliePhone, password);
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
				//Toast.makeText(ResetPwdActivity.this, "cancelButton!!!" , Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(); 
	        	intent.setClass(ResetPwdActivity.this, LoginActivity.class); /* ����һ���µ�Activity */
	        	startActivity(intent);
	        	/* �ر�ԭ����Activity */ 
	        	ResetPwdActivity.this.finish();
			}
		
		});
		
	}
	
	private Handler mUIHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			String result = bundle.getString("result");
			resetpwdButton.setText("��������");
			resetpwdButton.setClickable(true);
	    	if (result.equals(DataUtils.REGISTER_SUCCESS)) {
				Toast.makeText(ResetPwdActivity.this, "��������ɹ�" , Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(); 
	        	intent.setClass(ResetPwdActivity.this, LoginActivity.class); /* ����һ���µ�Activity */
	        	startActivity(intent);
	        	/* �ر�ԭ����Activity */ 
	        	ResetPwdActivity.this.finish();
			}
			else
				Toast.makeText(ResetPwdActivity.this, result , Toast.LENGTH_SHORT).show();
		}
	};
}
