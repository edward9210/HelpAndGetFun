package com.example.helpandgetfun.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.helpandgetfun.R;
import com.example.helpandgetfun.R.id;
import com.example.helpandgetfun.R.layout;
import com.example.helpandgetfun.adapter.MyAdapter;
import com.example.helpandgetfun.utils.DataUtils;
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
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentMyTaskPage extends Fragment implements OnRefreshListener<ListView>{
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
		
		DataUtils.myTaskList = new ArrayList<Map<String, Object>>();
		adapter = new MyAdapter(getActivity().getApplicationContext(), DataUtils.myTaskList, R.layout.pulldown_item,
					new String[]{"headImg", "userName", "date", "state", "taskContent", "executeTime", "Location", "postscript"},
					new int[]{R.id.item_head_image, R.id.item_username, R.id.item_date, R.id.item_state, R.id.item_task_content,  R.id.item_time_content, R.id.item_location_content, R.id.item_addition_content});
		
		mListView = (PullToRefreshListView) getView().findViewById(R.id.homepage_itemList);
		mListView.setOnRefreshListener(this);
		mListView.setAdapter(adapter);

		//ʹ�÷����ο��� http://blog.csdn.net/ueryueryuery/article/details/17440465
		mListView.setMode(Mode.BOTH);
		mListView.getLoadingLayoutProxy(false, true).setPullLabel("��������");
		mListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("������...");
		mListView.getLoadingLayoutProxy(false, true).setReleaseLabel("�����Լ���...");
		
		DataUtils.myTaskList.clear();
		if (adapter != null)
			adapter.notifyDataSetChanged(); 
		
		new GetDataTask().execute();
		
		new Thread(new Runnable() {
		    public void run() {
		    	while(true) {
		    		if (DataUtils.ADD_TASK_FLAG == true) {
		    			new GetDataTask().execute();
		    			DataUtils.ADD_TASK_FLAG = false;
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
		mListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				TextView stateTv = (TextView) view.findViewById(R.id.item_state);
				String state = stateTv.getText().toString();
				Button b;
				final TextView tc = (TextView) view.findViewById(R.id.item_task_content);
				if (state.equals("�ȴ���")) {
					 b = (Button) view.findViewById(R.id.task_info_delete);
					 if (b.getVisibility() == View.GONE)
							b.setVisibility(View.VISIBLE);
						else if (b.getVisibility() == View.VISIBLE)
							b.setVisibility(View.GONE);
					 b.setOnClickListener(new Button.OnClickListener(){
						@Override
						public void onClick(View v) {
							String taskContent = tc.getText().toString();
							String result = DataUtils.deleteMyTask(taskContent);
							if (result.equals(DataUtils.DELETETASK_SUCCESS)) {
								new GetDataTask().execute();
								Toast.makeText(getActivity().getApplicationContext(), "�ɹ�ɾ������" , Toast.LENGTH_SHORT).show();
							}
							else {
								Toast.makeText(getActivity().getApplicationContext(), "ɾ������ʧ��" , Toast.LENGTH_SHORT).show();
							}
							v.setVisibility(View.GONE);
						}
					});
				}
				else if (state.equals("������")){
					b = (Button) view.findViewById(R.id.task_info_accept);
					b.setText("���������");
					if (b.getVisibility() == View.GONE)
						b.setVisibility(View.VISIBLE);
					else if (b.getVisibility() == View.VISIBLE)
						b.setVisibility(View.GONE);
					b.setOnClickListener(new Button.OnClickListener(){
						@Override
						public void onClick(View v) {
							String taskContent = tc.getText().toString();
							String result = DataUtils.finishMyTask(taskContent);
							if (result.equals(DataUtils.FINISHTASK_SUCCESS)) {
								new GetDataTask().execute();
								Toast.makeText(getActivity().getApplicationContext(), "����ɹ����" , Toast.LENGTH_SHORT).show();
							}
							else {
								Toast.makeText(getActivity().getApplicationContext(), "������Ϣʧ��" , Toast.LENGTH_SHORT).show();
							}
							v.setVisibility(View.GONE);
						}
					});
				}		
				
				
			}
			
		});
		
	}
	
	private class GetDataTask extends AsyncTask<Void, Void, List<Map<String, Object> > > {

		@Override
		protected void onPostExecute(List<Map<String, Object> > result) {
			if (result != null) {
				DataUtils.myTaskList.clear();
				if (result.size() != 0) {
					DataUtils.myTaskList.addAll(result);
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
				return DataUtils.getMyTaskData();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}
	
	private class GetMoreTask extends AsyncTask<Void, Void, String[] > {

		@Override
		protected void onPostExecute(String[] result) {
			if (DataUtils.myTaskList.size() > adapter.getCount()) {
	    		if (DataUtils.myTaskList.size() >= adapter.getCount() + 10)
	    			adapter.setCount(adapter.getCount() + 10);
	    		else
	    			adapter.setCount(DataUtils.myTaskList.size());
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
