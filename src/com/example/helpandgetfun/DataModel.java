package com.example.helpandgetfun;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class DataModel {
	public static List< Map<String, Object> > homePageList, otherList, taskAcceptedList, myTaskList, myFriendList;
	private static String ServerURL = "http://172.18.156.140";
	public static String mUserName, mRealName, mPhone;
	public static String CONECTION_ERROR = "Connection_Error";
	public static String CONECTION_FAIL = "Connection_Fail";
	public static String LOGIN_SUCCESS = "success";
	public static String REGISTER_SUCCESS = "success";
	public static String ADDTASK_SUCCESS = "success";
	public static String ADDFRIEND_SUCCESS = "success";
	
	public static List<Map<String, Object> > getHomePageData() {
		/* ����������
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
		 */
		if (homePageList == null)
			homePageList = new ArrayList<Map<String, Object>>();
		
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
	
	//��ȡ�ҵ������б�
	public static List<Map<String, Object> > getMyTaskData() throws JSONException {
		/* ��������
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
			myTaskList.add(map);*/
		
		List< Map<String, Object> > myTaskListTmp = new ArrayList<Map<String, Object>>();
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("label", "querycreated"));
		params.add(new BasicNameValuePair("name", mUserName));
		JSONArray jsonArray = sendMesToServerJSONArray(params);
		//String str = jsonArray.toString();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject json = (JSONObject)jsonArray.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("headImg", R.drawable.homepage_friendlist);
			map.put("userName", mUserName);
			map.put("date", json.getString("missiontime"));
			if (json.getString("missionstate").equals("0"))
				map.put("state", "�ȴ���");
			else
				map.put("state", "������");
			map.put("taskContent", json.getString("missionname"));
			map.put("executeTime", json.getString("missiondeadline"));
			map.put("Location", json.getString("missionplace"));
			map.put("postscript", json.getString("missionps"));
			myTaskListTmp.add(map);
		}
		if (myTaskList == null)
			myTaskList = myTaskListTmp;
		else {
			myTaskList.clear();
			myTaskList.addAll(myTaskListTmp);
		}
		return myTaskList;
	} 
	
	//��ȡ�����б�
	public static List<Map<String, Object> > getFriendList() throws JSONException {
		myFriendList = new ArrayList<Map<String, Object>>();
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("label", "queryfriend"));
		params.add(new BasicNameValuePair("name", mUserName));
		JSONArray jsonArray = sendMesToServerJSONArray(params);
		//String str = jsonArray.toString();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject json = (JSONObject)jsonArray.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("headImg", R.drawable.homepage_headimg);
			map.put("userName", json.getString("user2"));
			myFriendList.add(map);
		}
		return myFriendList;
	}
	
	
	//���е�½����
	public static String login(String name, String password) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("label", "login"));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("password", password));
		return sendMesToServer(params);
	}
	
	//����ע�����
	public static String register(String name, String realname, String mobliePhone, String password) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("label", "reg"));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("realname", realname));
		params.add(new BasicNameValuePair("phone", mobliePhone));
		params.add(new BasicNameValuePair("password", password));
		return sendMesToServer(params);
	}
	
	//��ȡ�û���Ϣ������JSONObject
	public static JSONObject getUserInfo(String name) throws JSONException  {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("label", "getuser"));
		params.add(new BasicNameValuePair("name", name));
		return sendMesToServerJSONObject(params).getJSONObject("");
	}
	
	//����������
	public static String addTask(String userName, String missonName, String startTime, String endTime, String place, String postScript) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("label", "createmission"));
		params.add(new BasicNameValuePair("uname", userName));
		params.add(new BasicNameValuePair("mname", missonName));
		params.add(new BasicNameValuePair("start", startTime));
		params.add(new BasicNameValuePair("end", endTime));
		params.add(new BasicNameValuePair("place", place));
		params.add(new BasicNameValuePair("ps", postScript));
		return sendMesToServer(params);
	}
	
	//��ע����
	public static String addFriend(String friendName) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("label", "makefriends"));
		params.add(new BasicNameValuePair("name1", mUserName));
		params.add(new BasicNameValuePair("name2", friendName));
		return sendMesToServer(params);
	}
	
	
	//�������ݣ�ʹ�õ�ʱhttp
	//List<NameValuePair> params ��ָ���ݵģ�key��value����list
	private static String sendMesToServer(List<NameValuePair> params) {
		HttpPost httpRequest = new HttpPost(ServerURL);
		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params,"GB2312"));
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				String strResult = EntityUtils.toString(httpResponse.getEntity(), "GB2312");
				System.out.print(strResult);
				return strResult;
			}
			else {
				System.out.print("error");
				return CONECTION_FAIL;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CONECTION_ERROR;
	}

	private static JSONObject sendMesToServerJSONObject(List<NameValuePair> params) {
		HttpPost httpRequest = new HttpPost(ServerURL);
		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params,"GB2312"));
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				httpResponse = new DefaultHttpClient().execute(httpRequest);
				InputStream inputStream = httpResponse.getEntity().getContent();
				byte[] buffer = new byte[4096];
				int ch = -1;
				String str = "";
				while ((ch = inputStream.read(buffer)) != -1) {
					String tmp = new String(buffer, "GB2312");
					str += tmp;
				}
				JSONObject json = new JSONObject(str);  
				return json;
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JSONObject();
	}

	private static JSONArray sendMesToServerJSONArray(List<NameValuePair> params) {
		HttpPost httpRequest = new HttpPost(ServerURL);
		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params,"GB2312"));
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				httpResponse = new DefaultHttpClient().execute(httpRequest);
				InputStream inputStream = httpResponse.getEntity().getContent();
				byte[] buffer = new byte[4096];
				int ch = -1;
				String str = "";
				while ((ch = inputStream.read(buffer)) != -1) {
					String tmp = new String(buffer, "GB2312");
					str += tmp;
				}
				if (str != null) {
					JSONArray jsonArray = new JSONArray(str);  
					return jsonArray;
				}
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JSONArray();
	}
}
