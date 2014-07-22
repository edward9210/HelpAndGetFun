package com.example.helpandgetfun;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
				String missonName = taskContentEt.getText().toString();
				int year = Integer.parseInt(yearEt.getText().toString());
				int month = Integer.parseInt(monthEt.getText().toString());
				int day = Integer.parseInt(dayEt.getText().toString());
				int hour = Integer.parseInt(hourEt.getText().toString());
				int minute = Integer.parseInt(minuteEt.getText().toString());
				String startTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
				Calendar calendar = Calendar.getInstance();
				calendar.set(year, month - 1, day, hour, minute);		
				String endTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(calendar.getTime());
				String place = locationEt.getText().toString();
				String postScript = postScriptEt.getText().toString();
				
				String result = DataModel.addTask(DataModel.mUserName, missonName, startTime, endTime, place, postScript);
				if (result.equals(DataModel.ADDTASK_SUCCESS)) {
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
}
