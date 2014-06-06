package com.example.helpandgetfun;


import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class InfoCenterActivity extends Activity{
	private final int MORE_FINISHED = 0;
	
	private MyAdapter adapter;
	private ListView mListView;
	private Button mMoreBnt;
	private ImageButton mCancelbnt;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_center);
		
		setAllWidget();
		setAllListener();
	}

	private void setAllWidget() {
		// TODO Auto-generated method stub
		mCancelbnt = (ImageButton) findViewById(R.id.info_center_cancelbutton);
		
	}

	private void setAllListener() {
		// TODO Auto-generated method stub
		mCancelbnt.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(); 
	        	intent.setClass(InfoCenterActivity.this, MainActivity.class); /* 调用一个新的Activity */
	        	startActivity(intent);
	        	/* 关闭原本的Activity */ 
	        	InfoCenterActivity.this.finish();
			}
		
		});
	}

}

