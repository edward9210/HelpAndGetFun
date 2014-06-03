package com.example.helpandgetfun;


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
		hourEt = (EditText) findViewById(R.id.addtask_hour);
		minuteEt = (EditText) findViewById(R.id.addtask_minute);
		postScriptEt = (EditText) findViewById(R.id.addtask_task_postscript);
	}

	private void setAllListener() {
		addTaskButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(AddTaskActivity.this, "addTaskButton!!!" , Toast.LENGTH_SHORT).show();
			}
		
		});
		cancelButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(AddTaskActivity.this, "cancelButton!!!" , Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(); 
	        	intent.setClass(AddTaskActivity.this, MainActivity.class); /* 调用一个新的Activity */
	        	startActivity(intent);
	        	/* 关闭原本的Activity */ 
	        	AddTaskActivity.this.finish();
			}
		
		});
		
	}
}
