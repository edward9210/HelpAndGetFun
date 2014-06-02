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
	private final int SPLASH_DISPLAY_LENGHT = 1000; //延迟五秒   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		/* 使用Handler来创建SplashScreen, 从 http://www.iteye.com/topic/1122412 处参考而来 */
		 new Handler().postDelayed(new Runnable() {  
	            // 为了减少代码使用匿名Handler创建一个延时的调用  
	            public void run() {  
	                Intent i = new Intent(SplashScreen.this, LoginActivity.class);  
	                // 通过Intent打开最终真正的主界面Main这个Activity  
	                SplashScreen.this.startActivity(i); // 启动Main界面  
	                SplashScreen.this.finish(); // 关闭自己这个开场屏  
	            }  
	        }, SPLASH_DISPLAY_LENGHT);  
		
	}

}