package com.example.helpandgetfun;

import android.app.Activity;
import android.content.Intent;
import android.os.*;
import android.support.v4.app.Fragment;  
import android.support.v4.app.FragmentActivity;  
import android.support.v4.app.FragmentManager;  
import android.support.v4.app.FragmentTransaction; 
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends FragmentActivity {  
	private Fragment mHomePageFragment, mTaskAcceptedFragment, mMyTaskFragment, mAddTaskFragment, mOtherFragment, mAboutMeFragment;  
	private ImageButton mHomePageBnt, mTaskBnt, mAddTaskBnt, mOtherBnt, mAboutMeBnt, mFriendList;
	private FragmentManager fragmentManager; 
	private FragmentTransaction fragmentTransaction;  
	private LinearLayout mTaskPageLayout;
	private RelativeLayout mPageLayout;
	private RadioGroup mButtomRg;
	private RadioButton mTaskAcceptedRb, mMyTaskRb;
	private TextView mTopBarUserName;
	private Button mLogoutBnt;
	
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.main_page);
        
        initAndsetAllWidget();
        setAllListener();
    }
    
	private void setAllListener() {
		// TODO Auto-generated method stub
		mHomePageBnt.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mTaskPageLayout.setVisibility(View.GONE);
				mPageLayout.setVisibility(View.VISIBLE);
				hideAllFragment();
				fragmentTransaction.show(mHomePageFragment).commit();  
			}
		
		});
		
		mTaskBnt.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mTaskPageLayout.setVisibility(View.VISIBLE);
				mPageLayout.setVisibility(View.GONE);
				mTaskAcceptedRb.setChecked(true);
				hideAllFragment();
				fragmentTransaction.show(mTaskAcceptedFragment).commit();  
			}
		
		});
		
		mOtherBnt.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mTaskPageLayout.setVisibility(View.GONE);
				mPageLayout.setVisibility(View.VISIBLE);
				hideAllFragment();
				fragmentTransaction.show(mOtherFragment).commit();  
			}
		
		});
		
		mAboutMeBnt.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mTaskPageLayout.setVisibility(View.GONE);
				mPageLayout.setVisibility(View.VISIBLE);
				hideAllFragment();
				fragmentTransaction.show(mAboutMeFragment).commit();  
			}
		
		});
		
		mAddTaskBnt.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(); 
	        	intent.setClass(MainActivity.this, AddTaskActivity.class); /* ����һ���µ�Activity */
	        	//mTopBarUserName.setText("CD");
	        	startActivity(intent);
	        	/* �ر�ԭ����Activity */ 
	        	//MainActivity.this.finish();
			}
		
		});
		
		mFriendList.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(); 
	        	intent.setClass(MainActivity.this, FriendListActivity.class); /* ����һ���µ�Activity */
	        	startActivity(intent);
	        	/* �ر�ԭ����Activity */ 
	        	//MainActivity.this.finish();
			}
		
		});
		
		mLogoutBnt.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(); 
	        	intent.setClass(MainActivity.this, LoginActivity.class); /* ����һ���µ�Activity */
	        	startActivity(intent);
	        	/* �ر�ԭ����Activity */ 
	        	MainActivity.this.finish();
			}
		
		});
		
		mButtomRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				hideAllFragment();
				switch (checkedId) {
				case R.id.rb_task_accepted:
					fragmentTransaction.show(mTaskAcceptedFragment).commit();  
					break;
				case R.id.rb_mytask:
					fragmentTransaction.show(mMyTaskFragment).commit();  
				}
			}
		});
		
	}

	private void initAndsetAllWidget() {
		// TODO Auto-generated method stub
		//��ʼ��������������fragment,ֱ�Ӽ�����ҳ
		
		fragmentManager = getSupportFragmentManager();
		
		mHomePageFragment = fragmentManager.findFragmentById(R.id.fragement_homepage);
		mOtherFragment = fragmentManager.findFragmentById(R.id.fragement_other);
		mTaskAcceptedFragment = fragmentManager.findFragmentById(R.id.fragement_task_accepted);
		mMyTaskFragment = fragmentManager.findFragmentById(R.id.fragement_mytask);
		mAboutMeFragment = fragmentManager.findFragmentById(R.id.fragement_aboutme);
		
		hideAllFragment();
		 
		fragmentTransaction.show(mHomePageFragment).commit();  

		mHomePageBnt = (ImageButton) findViewById(R.id.tab_homepage_bnt);
		mTaskBnt = (ImageButton) findViewById(R.id.tab_task_bnt);
		mAddTaskBnt	= (ImageButton) findViewById(R.id.tab_addtask_bnt);
		mOtherBnt = (ImageButton) findViewById(R.id.tab_other_bnt);
		mAboutMeBnt = (ImageButton) findViewById(R.id.tab_aboutme_bnt);
		mFriendList = (ImageButton) findViewById(R.id.friendlist_bnt);
		
		mTopBarUserName = (TextView) findViewById(R.id.topbar_username);
		
		mTaskPageLayout = (LinearLayout)findViewById(R.id.layout_taskpage);
		mPageLayout = (RelativeLayout) findViewById(R.id.layout_page);
		
		mButtomRg = (RadioGroup) findViewById(R.id.task_bottomRg);
		mTaskAcceptedRb = (RadioButton) findViewById(R.id.rb_task_accepted);
		mMyTaskRb = (RadioButton) findViewById(R.id.rb_mytask);
		
		mLogoutBnt = (Button) findViewById(R.id.about_logout);
	}

	private void hideAllFragment() {
		// TODO Auto-generated method stub
		fragmentTransaction = fragmentManager.beginTransaction().hide(mHomePageFragment).hide(mOtherFragment).hide(mTaskAcceptedFragment).hide(mMyTaskFragment).hide(mAboutMeFragment);
	}
  
}  
