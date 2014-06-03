package com.example.helpandgetfun;

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

public class LoginActivity extends Activity {
	private EditText userNameEditText, pwdEditText;
	private Button loginButton, registerButton, forgetPwdButton;
	private HttpURLConnection urlconn;
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
		loginButton = (Button) findViewById(R.id.login_button);
		registerButton = (Button) findViewById(R.id.login_register);
		forgetPwdButton = (Button) findViewById(R.id.login_forgetpwd);
	}

	private void setAllListener() {
		loginButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/* ���͵�½��Ϣ */
				String name = "", pwd = "";
				name = userNameEditText.getText().toString();
				pwd = pwdEditText.getText().toString();
				if (name.length() != 0 && pwd.length() != 0) {
					if(DataModel.login(name, pwd)) {
					
						Intent intent = new Intent(); 
			        	intent.setClass(LoginActivity.this, MainActivity.class); /* ����һ���µ�Activity */
			        	startActivity(intent);
			        	/* �ر�ԭ����Activity */ 
			        	LoginActivity.this.finish();
						//Toast.makeText(LoginActivity.this, "loginButton!!!" , Toast.LENGTH_SHORT).show();
					}
					else {
						Toast.makeText(LoginActivity.this, "��½ʧ��" , Toast.LENGTH_SHORT).show();
					}
				}
				else if (name.length() == 0) 
					Toast.makeText(LoginActivity.this, "�û���Ϊ��" , Toast.LENGTH_SHORT).show();
				else if (pwd.length() == 0)
					Toast.makeText(LoginActivity.this, "����Ϊ��" , Toast.LENGTH_SHORT).show();
			}
		
		});
		registerButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(LoginActivity.this, "registerButton!!!" , Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(); 
	        	intent.setClass(LoginActivity.this, RegisterActivity.class); /* ����һ���µ�Activity */
	        	startActivity(intent);
	        	/* �ر�ԭ����Activity */ 
	        	LoginActivity.this.finish();
			}
		
		});
		forgetPwdButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(LoginActivity.this, "forgetPwdButton!!!" , Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(); 
	        	intent.setClass(LoginActivity.this, ResetPwdActivity.class); /* ����һ���µ�Activity */
	        	startActivity(intent);
	        	/* �ر�ԭ����Activity */ 
	        	LoginActivity.this.finish();
			}
		
		});
	}
	
}
