package com.example.helpandgetfun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import android.os.Build;

public class FragmentAboutMePage extends Fragment{
	RelativeLayout mInfoRL, mInfoCenterRL;
	
	
	@Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
		return inflater.inflate(R.layout.aboutme, container, false); 
    }  
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		mInfoRL = (RelativeLayout) getView().findViewById(R.id.aboutme_info_RL);
		mInfoCenterRL = (RelativeLayout) getView().findViewById(R.id.aboutme_info_center_RL);
		
		mInfoRL.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(); 
	        	intent.setClass(getActivity().getApplicationContext(), PersonInfoActivity.class); /* 调用一个新的Activity */
	        	startActivity(intent);
	        	/* 关闭原本的Activity */ 
	        	//MainActivity.this.finish();
			}
		});
		
		mInfoCenterRL.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(); 
	        	intent.setClass(getActivity().getApplicationContext(), InfoCenterActivity.class); /* 调用一个新的Activity */
	        	startActivity(intent);
	        	/* 关闭原本的Activity */ 
	        	//MainActivity.this.finish();
			}
		});
	}
}

