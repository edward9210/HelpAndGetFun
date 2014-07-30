package com.example.helpandgetfun.viewcontroller;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.helpandgetfun.R;
import com.example.helpandgetfun.R.id;
import com.example.helpandgetfun.R.layout;
import com.example.helpandgetfun.utils.DataUtils;

import android.R.integer;
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

public class AddTaskActivity extends Activity {
	private EditText taskContentEt, locationEt , yearEt, monthEt, dayEt, hourEt, minuteEt, postScriptEt;
	private ImageButton cancelButton;
	private Button addTaskButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addtask);
		setAllWidget();
		setAllListener();
	}


	private void setAllWidget() {
		// TODO Auto-generated method stub
		addTaskButton = (Button) findViewById(R.id.addtask_bnt);
		cancelButton = (ImageButton) findViewById(R.id.addtask_cancelbutton);
		
		taskContentEt = (EditText) findViewById(R.id.addtask_task_content);
		locationEt = (EditText) findViewById(R.id.addtask_task_location);
		yearEt = (EditText) findViewById(R.id.addtask_year);
		monthEt = (EditText) findViewById(R.id.addtask_month);
		dayEt = (EditText) findViewById(R.id.addtask_day);
		hourEt = (EditText) findViewById(R.id.addtask_hour);
		minuteEt = (EditText) findViewById(R.id.addtask_minute);
		postScriptEt = (EditText) findViewById(R.id.addtask_task_postscript);
	}

	private void setAllListener() {
		addTaskButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(AddTaskActivity.this, "addTaskButton!!!" , Toast.LENGTH_SHORT).show();
				final String missonName = taskContentEt.getText().toString();
				int flag = 0;
				long year = 0, month = 0, day = 0, hour = 0, minute = 0;
				try {
					if (yearEt.getText().toString().length() > 0)
						year = Integer.parseInt(yearEt.getText().toString());
					else
						flag = 1;
					if (monthEt.getText().toString().length() > 0)
						month = Integer.parseInt(monthEt.getText().toString());
					else
						flag = 1;
					if (dayEt.getText().toString().length() > 0)
						day = Integer.parseInt(dayEt.getText().toString());
					else
						flag = 1;
					if (hourEt.getText().toString().length() > 0)
						hour = Integer.parseInt(hourEt.getText().toString());
					else
						flag = 1;
					if (minuteEt.getText().toString().length() > 0)
						minute = Integer.parseInt(minuteEt.getText().toString());
					else
						flag = 1;
					String startTimeTmp = "", endTimeTmp = "";
					if (flag == 0) {
						startTimeTmp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
						Calendar calendar = Calendar.getInstance();
						calendar.set((int)year, (int)month - 1, (int)day, (int)hour, (int)minute, 0);		
						endTimeTmp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(calendar.getTime());
					}
					final String place = locationEt.getText().toString();
					final String postScript = postScriptEt.getText().toString();
					
					if (missonName.length() > 0 && place.length() > 0 && flag == 0) {
						
				    	final String startTime = startTimeTmp, endTime = endTimeTmp;
				    	if (DataUtils.isDateValid((int)year, (int)month, (int)day) && year >= 2014 && month > 0 && month <= 12 && day > 0 && day <= 31 && minute >= 0 && minute <= 59 && hour >= 0 && hour < 24) {
				    		addTaskButton.setText("发布任务中...");
					    	addTaskButton.setClickable(false);
							new Thread(new Runnable() {
							    public void run() {
							    	String result = DataUtils.addTask(DataUtils.mUserName, missonName, startTime, endTime, place, postScript);
							    	Bundle bundle = new Bundle();
							    	bundle.putString("result", result);
							    	Message mes = new Message();
							    	mes.setData(bundle);
							    	mUIHandler.sendMessage(mes);
							    }
							}).start();
				    	}
				    	else {
				    		Toast.makeText(AddTaskActivity.this, "输入日期错误" , Toast.LENGTH_SHORT).show();
						}
						
						
					}
					else
						Toast.makeText(AddTaskActivity.this, "必填项没填" , Toast.LENGTH_SHORT).show();
				}
				catch (Exception e) {
					Toast.makeText(AddTaskActivity.this, "输入日期错误" , Toast.LENGTH_SHORT).show();
				}
			}
		
		});
		cancelButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(AddTaskActivity.this, "cancelButton!!!" , Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(); 
	        	intent.setClass(AddTaskActivity.this, MainActivity.class); /* 调用一个新的Activity */
	        	startActivity(intent);
	        	/* 关闭原本的Activity */ 
	        	AddTaskActivity.this.finish();
			}
		
		});
		
	}
	
	private Handler mUIHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			String result = bundle.getString("result");
			addTaskButton.setText("发布任务");
	    	addTaskButton.setClickable(true);
	    	if (result.equals(DataUtils.ADDTASK_SUCCESS)) {
	    		DataUtils.ADD_TASK_FLAG = true;
				Toast.makeText(AddTaskActivity.this, "发布成功" , Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(); 
	        	intent.setClass(AddTaskActivity.this, MainActivity.class); /* 调用一个新的Activity */
	        	startActivity(intent);
	        	/* 关闭原本的Activity */ 
	        	AddTaskActivity.this.finish();
			}
			else {
				Toast.makeText(AddTaskActivity.this, result , Toast.LENGTH_SHORT).show();
			}
		}
	};
}
