package com.example.helpandgetfun.viewcontroller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import com.example.helpandgetfun.R;
import com.example.helpandgetfun.R.id;
import com.example.helpandgetfun.R.layout;
import com.example.helpandgetfun.adapter.MyAdapter;
import com.example.helpandgetfun.utils.DataUtils;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class InfoCenterActivity extends Activity{
	private final int MORE_FINISHED = 0;
	private boolean touchFlag = false;
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
		
		mMoreBnt.setText("加载中...");
		new GetDataTask().execute();
	}

	private void setAllWidget() {
		// TODO Auto-generated method stub
		mCancelbnt = (ImageButton) findViewById(R.id.info_center_cancelbutton);
		if (DataUtils.infoList == null)
			DataUtils.infoList = new ArrayList<Map<String, Object>>();
		
		adapter = new MyAdapter(this, DataUtils.infoList , R.layout.info_item,
				new String[]{"senderName", "senderMes"},
				new int[]{R.id.info_sender_name, R.id.info_sender_mes});
		
		
		mListView = (ListView) findViewById(R.id.info_center_lv);
		mListView.setAdapter(adapter);
		
		DataUtils.infoList.clear();
		if (adapter != null)
			adapter.notifyDataSetChanged();  
		
		mMoreBnt = new Button(this);
		mMoreBnt.setText("更多");
		mMoreBnt.setBackgroundColor(Color.TRANSPARENT);
		mMoreBnt.setTextSize((float) 20.0);
		mMoreBnt.setGravity(Gravity.CENTER);
		mListView.addFooterView(mMoreBnt);
		
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
		
		mListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (touchFlag == false) {
					touchFlag = true;
					final Button sendMesBnt = (Button) view.findViewById(R.id.info_send_mes_bnt);
					final Button cancelMesBnt = (Button) view.findViewById(R.id.info_cancel_bnt);
					final EditText eT = (EditText) view.findViewById(R.id.info_reply_mes);
					final TextView sender = (TextView) view.findViewById(R.id.info_sender_name);
					eT.setText("");
					if (sendMesBnt.getVisibility() == View.GONE && eT.getVisibility() == View.GONE && cancelMesBnt.getVisibility() == View.GONE) {
						sendMesBnt.setVisibility(View.VISIBLE);
						eT.setVisibility(View.VISIBLE);
						eT.requestFocus();  
						cancelMesBnt.setVisibility(View.VISIBLE);
					}
					else if (sendMesBnt.getVisibility() == View.VISIBLE && eT.getVisibility() == View.VISIBLE && cancelMesBnt.getVisibility() == View.VISIBLE) {
						sendMesBnt.setVisibility(View.GONE);
						cancelMesBnt.setVisibility(View.GONE);
						eT.setVisibility(View.GONE);
					}
					sendMesBnt.setOnClickListener(new Button.OnClickListener(){
						@Override
						public void onClick(View v) {
							String senderName = sender.getText().toString();
							String content = eT.getText().toString();
							if (content.length() == 0)
								Toast.makeText(InfoCenterActivity.this, "发送内容为空" , Toast.LENGTH_SHORT).show();
							else if (content.length() > 100)
								Toast.makeText(InfoCenterActivity.this, "发送内容过长，请保持在100个字符以内" , Toast.LENGTH_SHORT).show();
							else {
								sendMesBnt.setVisibility(View.GONE);
								eT.setVisibility(View.GONE);
								cancelMesBnt.setVisibility(View.GONE);
								touchFlag = false;
								new SendDataTask(senderName, content).execute();
							}
						}
					});
					cancelMesBnt.setOnClickListener(new Button.OnClickListener(){
						@Override
						public void onClick(View v) {
							sendMesBnt.setVisibility(View.GONE);
							eT.setVisibility(View.GONE);
							cancelMesBnt.setVisibility(View.GONE);
							touchFlag = false;
						}
					});
				}
			}
			
		});
		
		mMoreBnt.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mMoreBnt.setText("加载中...");
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}				
						Message msg = mUIHandler.obtainMessage(MORE_FINISHED);
						msg.sendToTarget();
					}
				}).start();
			}
		});
	}
	
	private class GetDataTask extends AsyncTask<Void, Void, List<Map<String, Object> > > {

		@Override
		protected void onPostExecute(List<Map<String, Object> > result) {
			if (result != null) {
				DataUtils.infoList.clear();
				if (result.size() != 0) {
					mMoreBnt.setText("更多");
					DataUtils.infoList.addAll(result);
				}
				else if (DataUtils.NEW_MES_FLAG == true && result.size() == 0)
					new GetDataTask().execute();
				else
					mMoreBnt.setText("更多");
				adapter.notifyDataSetChanged();
			}
			else if (DataUtils.NEW_MES_FLAG == true)
				new GetDataTask().execute();
			super.onPostExecute(result);
		}

		@Override
		protected List<Map<String, Object> > doInBackground(Void... params) {
			try {
				return DataUtils.getInfoList();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}
	
	private class SendDataTask extends AsyncTask<String, Void, String> {
		private String senderName, content;
		public SendDataTask(String sn, String c) {
			senderName = sn;
			content = c;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result.equals(DataUtils.SENDMES_SUCCESS))
				Toast.makeText(InfoCenterActivity.this, "发送成功" , Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(InfoCenterActivity.this, "发送失败" , Toast.LENGTH_SHORT).show();
			super.onPostExecute(result);
		}

		@Override
		protected String doInBackground(String... params) {
			return DataUtils.sendMes(senderName, content);
		}
	}
	
	/* UIHandler负责更新页面 */
	private Handler mUIHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
		    switch (msg.what) {
		    case MORE_FINISHED:
		    	if (DataUtils.infoList.size() > adapter.getCount()) {
		    		if (DataUtils.infoList.size() >= adapter.getCount() + 10)
		    			adapter.setCount(adapter.getCount() + 10);
		    		else
		    			adapter.setCount(DataUtils.infoList.size());
			    	adapter.notifyDataSetChanged();
		    	}
				mMoreBnt.setText("更多");
		    	break;
		    }
		}
	};
}

