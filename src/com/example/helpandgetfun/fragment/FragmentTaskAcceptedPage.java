package com.example.helpandgetfun.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import com.example.helpandgetfun.R;
import com.example.helpandgetfun.R.id;
import com.example.helpandgetfun.R.layout;
import com.example.helpandgetfun.adapter.MyAdapter;
import com.example.helpandgetfun.utils.DataUtils;
import com.example.helpandgetfun.viewcontroller.InfoCenterActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentTaskAcceptedPage extends Fragment implements OnRefreshListener<ListView>{
	private PullToRefreshListView mListView;
	private MyAdapter adapter;
	private boolean isRefreshing = false;
	
	@Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
		return inflater.inflate(R.layout.task_page, container, false);  
    }  
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		DataUtils.taskAcceptedList = new ArrayList<Map<String, Object>>();
		adapter = new MyAdapter(getActivity().getApplicationContext(), DataUtils.taskAcceptedList, R.layout.pulldown_item,
					new String[]{"headImg", "userName", "date", "state", "taskContent", "executeTime", "Location", "postscript"},
					new int[]{R.id.item_head_image, R.id.item_username, R.id.item_date, R.id.item_state, R.id.item_task_content,  R.id.item_time_content, R.id.item_location_content, R.id.item_addition_content});
		
		mListView = (PullToRefreshListView) getView().findViewById(R.id.homepage_itemList);
		mListView.setOnRefreshListener(this);
		mListView.setAdapter(adapter);

		//使用方法参考于 http://blog.csdn.net/ueryueryuery/article/details/17440465
		mListView.setMode(Mode.BOTH);
		mListView.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载");
		mListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("加载中...");
		mListView.getLoadingLayoutProxy(false, true).setReleaseLabel("松手以加载...");
		
		DataUtils.taskAcceptedList.clear();
		if (adapter != null)
			adapter.notifyDataSetChanged(); 
		
		new GetDataTask().execute();
		
		mListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				final Button deleteTaskBnt = (Button) view.findViewById(R.id.task_info_delete);
				final Button sendMesBnt = (Button) view.findViewById(R.id.task_send_mes);
				final Button cancelMesBnt = (Button) view.findViewById(R.id.task_mes_cancel);
				final EditText mesEt = (EditText) view.findViewById(R.id.task_reply_mes);
				final TextView tc = (TextView) view.findViewById(R.id.item_task_content);
				final TextView name = (TextView) view.findViewById(R.id.item_username); 
				if (deleteTaskBnt.getVisibility() == View.GONE && sendMesBnt.getVisibility() == View.GONE) {
					deleteTaskBnt.setVisibility(View.VISIBLE);
					sendMesBnt.setVisibility(View.VISIBLE);
				}
				else if (deleteTaskBnt.getVisibility() == View.VISIBLE && sendMesBnt.getVisibility() == View.VISIBLE) {
					deleteTaskBnt.setVisibility(View.GONE);
					sendMesBnt.setVisibility(View.GONE);
				}
				deleteTaskBnt.setOnClickListener(new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						String taskContent = tc.getText().toString();
						deleteTaskBnt.setText("删除中...");
						deleteTaskBnt.setClickable(false);
						new DeleteTask(deleteTaskBnt, sendMesBnt).execute(taskContent);
					}
				});
				
				sendMesBnt.setOnClickListener(new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						if (mesEt.getVisibility() == View.GONE) {
							mesEt.setVisibility(View.VISIBLE);
							mesEt.requestFocus();
							deleteTaskBnt.setVisibility(View.GONE);
							cancelMesBnt.setVisibility(View.VISIBLE);
						}
						else {
							String mes = mesEt.getText().toString();
							if (mes.length() == 0)
								Toast.makeText(getActivity().getApplicationContext(), "输入内容为空" , Toast.LENGTH_SHORT).show();
							else {
								sendMesBnt.setText("发送中...");
								sendMesBnt.setClickable(false);
								new SendDataTask(name.getText().toString(), mes, sendMesBnt, cancelMesBnt, mesEt).execute();
							}
						}
					}
				});
				cancelMesBnt.setOnClickListener(new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						mesEt.setVisibility(View.GONE);
						cancelMesBnt.setVisibility(View.GONE);
						deleteTaskBnt.setVisibility(View.VISIBLE);
						mesEt.setText("");
					}
				});
			}
			
		});
		
	}
	
	private class GetDataTask extends AsyncTask<Void, Void, List<Map<String, Object> > > {

		@Override
		protected void onPostExecute(List<Map<String, Object> > result) {
			if (result != null) {
				DataUtils.taskAcceptedList.clear();
				if (result.size() != 0) {
					DataUtils.taskAcceptedList.addAll(result);
				}
				adapter.notifyDataSetChanged();
				mListView.onRefreshComplete();
				isRefreshing = false;
			}
			else
				new GetDataTask().execute();		
			super.onPostExecute(result);
		}

		@Override
		protected List<Map<String, Object> > doInBackground(Void... params) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			try {
				return DataUtils.getTaskAcceptedData();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}
	
	private class GetMoreTask extends AsyncTask<Void, Void, String[] > {

		@Override
		protected void onPostExecute(String[] result) {
			if (DataUtils.taskAcceptedList.size() > adapter.getCount()) {
	    		if (DataUtils.taskAcceptedList.size() >= adapter.getCount() + 10)
	    			adapter.setCount(adapter.getCount() + 10);
	    		else
	    			adapter.setCount(DataUtils.taskAcceptedList.size());
		    	adapter.notifyDataSetChanged();
	    	}
			mListView.onRefreshComplete();
			isRefreshing = false;
			super.onPostExecute(result);
		}

		@Override
		protected String[] doInBackground(Void... params) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			return null;
		}
	}
	
	private class DeleteTask extends AsyncTask<String, Void, String > {
		Button deleteTaskBnt, sendMesBnt;
		public DeleteTask(Button dtBnt, Button smBnt) {
			deleteTaskBnt = dtBnt;
			sendMesBnt = smBnt;
		}

		@Override
		protected void onPostExecute(String result) {
			deleteTaskBnt.setText("删除");
			deleteTaskBnt.setClickable(true);
			if (result.equals(DataUtils.DELETETASK_SUCCESS)) {
				new GetDataTask().execute();
				Toast.makeText(getActivity().getApplicationContext(), "成功删除任务" , Toast.LENGTH_SHORT).show();
			}
			else {
				Toast.makeText(getActivity().getApplicationContext(), "删除任务失败" , Toast.LENGTH_SHORT).show();
			}
			deleteTaskBnt.setVisibility(View.GONE);
			sendMesBnt.setVisibility(View.GONE);
			super.onPostExecute(result);
		}

		@Override
		protected String doInBackground(String... params) {
			return DataUtils.quitAcceptedTask(params[0]);
		}
	}
	
	private class SendDataTask extends AsyncTask<String, Void, String> {
		private String senderName, content;
		private Button sendMesBnt, cancelMesBnt;
		private EditText mesEt;
		public SendDataTask(String sn, String c, Button sm, Button cancel, EditText et) {
			senderName = sn;
			content = c;
			sendMesBnt = sm;
			cancelMesBnt = cancel;
			mesEt = et;
		}

		@Override
		protected void onPostExecute(String result) {
			sendMesBnt.setText("发消息");
			sendMesBnt.setClickable(true);
			sendMesBnt.setVisibility(View.GONE);
			cancelMesBnt.setVisibility(View.GONE);
			mesEt.setVisibility(View.GONE);
			mesEt.setText("");
			if (result.equals(DataUtils.SENDMES_SUCCESS))
				Toast.makeText(getActivity().getApplicationContext(), "发送成功" , Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(getActivity().getApplicationContext(), "发送失败" , Toast.LENGTH_SHORT).show();
			super.onPostExecute(result);
		}

		@Override
		protected String doInBackground(String... params) {
			return DataUtils.sendMes(senderName, content);
		}
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		if (!isRefreshing) {
            isRefreshing = true;
            if (mListView.isHeaderShown()) {
            	new GetDataTask().execute();
            } else if (mListView.isFooterShown()) {
            	new GetMoreTask().execute();
            }
        } else {
            mListView.onRefreshComplete();
        }
	}
}

