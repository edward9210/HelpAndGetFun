package com.example.helpandgetfun.viewcontroller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.helpandgetfun.R;
import com.example.helpandgetfun.R.id;
import com.example.helpandgetfun.R.layout;
import com.example.helpandgetfun.utils.DataUtils;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.Visibility;
import android.os.*;
import android.support.v4.app.Fragment;  
import android.support.v4.app.FragmentActivity;  
import android.support.v4.app.FragmentManager;  
import android.support.v4.app.FragmentTransaction; 
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
	private ImageView notification, aboutmeNotification, headimg;
	
	
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.main_page);
        
        initAndsetAllWidget();
        setAllListener();
        
        DataUtils.imgBm = null;
        
        new Thread(new Runnable() {
		    public void run() {
		    	JSONObject json;
				try {
					json = DataUtils.getUserInfo(DataUtils.mUserName);
					DataUtils.mRealName = json.getString("realname");
					DataUtils.mPhone = json.getString("phone");
					DataUtils.mPassword = json.getString("password");
					DataUtils.mheadimg = json.getString("headimg");
					
					Bundle bundle = new Bundle();
					bundle.putString("type", "GetUserInfo_Success");
					if (DataUtils.mheadimg.length() > 0) {
						try {
							DataUtils.imgBm = DataUtils.getImg(DataUtils.mheadimg);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
			    	Message mes = new Message();
			    	mes.setData(bundle);
			    	mUIHandler.sendMessage(mes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		    }
		}).start();
        
        new Thread(new Runnable() {
		    public void run() {
		    	while(true) {
		    		if (DataUtils.PERSON_INFO_UPDATE == true) {
		    			File file = new File (DataUtils.getSDPath() + '/' + DataUtils.mUserName + ".jpg");
				    	String result;
				    	if (file.exists()) {
				    		DataUtils.imgBm = BitmapFactory.decodeFile(DataUtils.getSDPath() + '/' + DataUtils.mUserName + ".jpg");
							Bundle bundle = new Bundle();
							bundle.putString("type", "GetUserInfo_Success");
							DataUtils.PERSON_INFO_UPDATE = false;
					    	Message mes = new Message();
					    	mes.setData(bundle);
					    	mUIHandler.sendMessage(mes);
						} 			
		    		}
		    		try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
		    	}
		    }
		}).start();
        
        if (DataUtils.LOGIN_FLAG == false) {
        	DataUtils.LOGIN_FLAG = true;
        	new Thread(new Runnable() {
    		    public void run() {
    		    	while(true) {
    		    		if (DataUtils.LOGIN_FLAG == false)
			    			break;
    			    	try {
    			    		Log.d("MainActivity", "get message from server");
    			    		if (DataUtils.getUpdateInfo() == true) {
    			    			DataUtils.NEW_MES_FLAG = true;
    			    			Bundle bundle = new Bundle();
    							bundle.putString("type", "NewMes");
    					    	Message mes = new Message();
    					    	mes.setData(bundle);
    					    	mUIHandler.sendMessage(mes);
    			    			Log.d("MainActivity", "You get a new message");
    			    		}
    			    		else {
    			    			DataUtils.NEW_MES_FLAG = false;
    			    			Bundle bundle = new Bundle();
    							bundle.putString("type", "NoNewMes");
    					    	Message mes = new Message();
    					    	mes.setData(bundle);
    					    	mUIHandler.sendMessage(mes);
    			    			Log.d("MainActivity", "No new message");
    			    		}
    						Thread.sleep(5000);
    					} catch (InterruptedException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
    		    	}
    		    }
    		}).start();
        }
        
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
	        	intent.setClass(MainActivity.this, AddTaskActivity.class); /* 调用一个新的Activity */
	        	//mTopBarUserName.setText("CD");
	        	startActivity(intent);
	        	/* 关闭原本的Activity */ 
	        	//MainActivity.this.finish();
			}
		
		});
		
		mFriendList.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(); 
	        	intent.setClass(MainActivity.this, FriendListActivity.class); /* 调用一个新的Activity */
	        	startActivity(intent);
	        	/* 关闭原本的Activity */ 
	        	//MainActivity.this.finish();
			}
		
		});
		
		mLogoutBnt.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DataUtils.LOGIN_FLAG = false;
				DataUtils.writeCache();
				Intent intent = new Intent(); 
	        	intent.setClass(MainActivity.this, LoginActivity.class); /* 调用一个新的Activity */
	        	startActivity(intent);
	        	/* 关闭原本的Activity */ 
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
		//初始化，先隐藏所有fragment,直接加载主页
		
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
		
		notification = (ImageView) findViewById(R.id.main_page_notification);
		
		aboutmeNotification = (ImageView) findViewById(R.id.aboutme_info_center_notification);
		
		headimg = (ImageView) findViewById(R.id.topbar_image);
		
		mTopBarUserName.setText(DataUtils.mUserName);
	}

	private void hideAllFragment() {
		// TODO Auto-generated method stub
		fragmentTransaction = fragmentManager.beginTransaction().hide(mHomePageFragment).hide(mOtherFragment).hide(mTaskAcceptedFragment).hide(mMyTaskFragment).hide(mAboutMeFragment);
	}
	
	private Handler mUIHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			String type = bundle.getString("type");
			if (DataUtils.imgBm != null)
				headimg.setImageBitmap(DataUtils.imgBm);
			if (type.equals("GetUserInfo_Success")) {
				mTopBarUserName.setText(DataUtils.mUserName);
			}
			else if (type.equals("NewMes")) {
				aboutmeNotification.setVisibility(View.VISIBLE);
				notification.setVisibility(View.VISIBLE);
				if (DataUtils.NEW_MES_CALL_FLAG == false) {
					DataUtils.NEW_MES_CALL_FLAG = true;
					Toast.makeText(getApplicationContext(), "您收到了新消息" , Toast.LENGTH_SHORT).show();
				}
			}
			else if (type.equals("NoNewMes")) {
				DataUtils.NEW_MES_CALL_FLAG = false;
				aboutmeNotification.setVisibility(View.INVISIBLE);
				notification.setVisibility(View.INVISIBLE);
			}
		}
	};
  
}  
