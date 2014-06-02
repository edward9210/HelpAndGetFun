package com.example.helpandgetfun;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class SplashScreen extends Activity {
	private final int SPLASH_DISPLAY_LENGHT = 1000; //�ӳ�����   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		/* ʹ��Handler������SplashScreen, �� http://www.iteye.com/topic/1122412 ���ο����� */
		 new Handler().postDelayed(new Runnable() {  
	            // Ϊ�˼��ٴ���ʹ������Handler����һ����ʱ�ĵ���  
	            public void run() {  
	                Intent i = new Intent(SplashScreen.this, LoginActivity.class);  
	                // ͨ��Intent������������������Main���Activity  
	                SplashScreen.this.startActivity(i); // ����Main����  
	                SplashScreen.this.finish(); // �ر��Լ����������  
	            }  
	        }, SPLASH_DISPLAY_LENGHT);  
		
	}

}