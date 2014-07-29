package com.example.helpandgetfun.viewcontroller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.helpandgetfun.R;
import com.example.helpandgetfun.R.id;
import com.example.helpandgetfun.R.layout;
import com.example.helpandgetfun.utils.DataUtils;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.os.Build;

public class LoginActivity extends Activity {
	private EditText userNameEditText, pwdEditText;
	private Button loginButton, registerButton, forgetPwdButton;
	private HttpURLConnection urlconn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads()  
	            .detectDiskWrites().detectNetwork().penaltyLog().build());  
	    StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()  
	            .detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());  
	            
		setAllWidget();
		setAllListener();
	}


	private void setAllWidget() {
		// TODO Auto-generated method stub
		userNameEditText = (EditText) findViewById(R.id.userNameET);
		pwdEditText = (EditText) findViewById(R.id.pwdET);
		loginButton = (Button) findViewById(R.id.login_button);
		registerButton = (Button) findViewById(R.id.login_register);
		forgetPwdButton = (Button) findViewById(R.id.login_forgetpwd);
	}

	private void setAllListener() {
		loginButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/* 发送登陆信息 */
				final String name = userNameEditText.getText().toString();
				final String pwd = pwdEditText.getText().toString();
				loginButton.setText("登陆中...");
		    	loginButton.setClickable(false);
				if (name.length() != 0 && pwd.length() != 0) {
					new Thread(new Runnable() {
					    public void run() {
					    	String result = DataUtils.login(name, pwd);
					    	Bundle bundle = new Bundle();
					    	bundle.putString("result", result);
					    	bundle.putString("name", name);
					    	Message mes = new Message();
					    	mes.setData(bundle);
					    	mUIHandler.sendMessage(mes);
					    }
					}).start();
					
					
				}
				else if (name.length() == 0) 
					Toast.makeText(LoginActivity.this, "用户名为空" , Toast.LENGTH_SHORT).show();
				else if (pwd.length() == 0)
					Toast.makeText(LoginActivity.this, "密码为空" , Toast.LENGTH_SHORT).show();
			}
		
		});
		registerButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(LoginActivity.this, "registerButton!!!" , Toast.LENGTH_SHORT).show();
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
				//Toast.makeText(LoginActivity.this, "forgetPwdButton!!!" , Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(); 
	        	intent.setClass(LoginActivity.this, ResetPwdActivity.class); /* 调用一个新的Activity */
	        	startActivity(intent);
	        	/* 关闭原本的Activity */ 
	        	LoginActivity.this.finish();
			}
		
		});
	}
	
	private Handler mUIHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			String result = bundle.getString("result");
			String name = bundle.getString("name");
			loginButton.setText("登陆");
	    	loginButton.setClickable(true);
			if (result.equals(DataUtils.LOGIN_SUCCESS)) {
				Toast.makeText(LoginActivity.this, "登陆成功" , Toast.LENGTH_SHORT).show();
				
				DataUtils.mUserName = name;
				
				Intent intent = new Intent(); 
	        	intent.setClass(LoginActivity.this, MainActivity.class); /* 调用一个新的Activity */
	        	startActivity(intent);
	        	/* 关闭原本的Activity */ 
	        	LoginActivity.this.finish();
				//Toast.makeText(LoginActivity.this, "loginButton!!!" , Toast.LENGTH_SHORT).show();
			}
			else {
				Toast.makeText(LoginActivity.this, result , Toast.LENGTH_SHORT).show();
			}
		}
	};

		
}
