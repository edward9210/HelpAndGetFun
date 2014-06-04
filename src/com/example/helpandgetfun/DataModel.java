package com.example.helpandgetfun;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class DataModel {
	private static List< Map<String, Object> > homePageList, otherList, taskAcceptedList, myTaskList, myFriendList;
	private static String ServerURL = "http://172.18.156.140";
	
	public static List<Map<String, Object> > getHomePageData() {
		homePageList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("headImg", R.drawable.homepage_headimg2);
		map.put("userName", "Edward");
		map.put("date", "2014.5.20");
		map.put("state", "waiting");
		map.put("taskContent", "123");
		map.put("executeTime", "12:30");
		map.put("Location", "Go Go Xintiandi");
		map.put("postscript", "=.=");
		
		for (int i = 0; i < 100; i++)
			homePageList.add(map);
		
		return homePageList;
	} 
	
	
	//��ȡİ���˷���������
	public static List<Map<String, Object> > getOtherData() {
		otherList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("headImg", R.drawable.homepage_friendlist);
		map.put("userName", "��¬");
		map.put("date", "2014.5.20");
		map.put("state", "waiting");
		map.put("taskContent", "123");
		map.put("executeTime", "12:30");
		map.put("Location", "Go Go Xintiandi");
		map.put("postscript", "=.=");
		
		for (int i = 0; i < 2; i++)
			otherList.add(map);
		
		return otherList;
	} 
	
	//��ȡ�ѽ������б�
	public static List<Map<String, Object> > getTaskAcceptedData() {
		taskAcceptedList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("headImg", R.drawable.homepage_friendlist);
		map.put("userName", "�ѽ�����");
		map.put("date", "2014.5.20");
		map.put("state", "waiting");
		map.put("taskContent", "123");
		map.put("executeTime", "12:30");
		map.put("Location", "Go Go Xintiandi");
		map.put("postscript", "=.=");
		
		for (int i = 0; i < 4; i++)
			taskAcceptedList.add(map);
		
		return taskAcceptedList;
	} 
	
	//��ȡ�ѽ������б�
	public static List<Map<String, Object> > getMyTaskData() {
		myTaskList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("headImg", R.drawable.homepage_friendlist);
		map.put("userName", "�ҵ�����");
		map.put("date", "2014.5.20");
		map.put("state", "waiting");
		map.put("taskContent", "123");
		map.put("executeTime", "12:30");
		map.put("Location", "Go Go Xintiandi");
		map.put("postscript", "=.=");
		
		for (int i = 0; i < 5; i++)
			myTaskList.add(map);
		
		return myTaskList;
	} 
	
	//��ȡ�����б�
	public static List<Map<String, Object> > getFriendList() {
		myFriendList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("headImg", R.drawable.homepage_headimg);
		map.put("userName", "XuBin");
		
		for (int i = 0; i < 50; i++)
			myFriendList.add(map);
		
		return myFriendList;
	}
	
	
	//���е�½����
	public static boolean login(String name, String password) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("laolu", "sasad"));
		if (sendMesToServer(params, "")) {
			return true;
		}
		else
			return false;
	}
	
	//�������ݣ�ʹ�õ�ʱhttp
	//List<NameValuePair> params ��ָ���ݵģ�key��value����list
	//whichUrl��ָ�����ĸ����д���
	private static boolean sendMesToServer(List<NameValuePair> params, String whichUrl) {
		HttpPost httpRequest = new HttpPost(ServerURL + whichUrl);
		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				//..
				String strResult = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
				System.out.print(strResult);
				return true;
			}
			else {
				return false;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//ע�����Ϊ�˵�����Ϊtrue��Ĭ��Ӧ�÷���false
		return true;
	}
}
