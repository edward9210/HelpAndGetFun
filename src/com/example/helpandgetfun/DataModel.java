package com.example.helpandgetfun;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

import android.R.bool;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class DataModel {
	public static List< Map<String, Object> > homePageList, otherList, taskAcceptedList, myTaskList, myFriendList;
	private static String ServerURL = "http://172.18.157.167/phpsrc/";
	public static String mUserName, mRealName, mPhone, mPassword;
	public static String CONECTION_ERROR = "Connection_Error";
	public static String CONECTION_FAIL = "Connection_Fail";
	public static String LOGIN_SUCCESS = "success";
	public static String REGISTER_SUCCESS = "success";
	public static String ADDTASK_SUCCESS = "success";
	public static String ADDFRIEND_SUCCESS = "success";
	public static String ACCEPTTASK_SUCCESS = "success";
	public static String DELETETASK_SUCCESS = "success";
	public static String DELETEFRIEND_SUCCESS = "success";
	public static String UPDATE_SUCCESS = "success";
	public static boolean LOGIN_FLAG = false;
	
	public static List<Map<String, Object> > getHomePageData() throws JSONException{
		/* 测试用数据
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
		List< Map<String, Object> > homePageListTmp = new ArrayList<Map<String, Object>>();
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("label", "queryfriendmission"));
		params.add(new BasicNameValuePair("name", mUserName));
		JSONArray jsonArray = sendMesToServerJSONArray(params);
		//String str = jsonArray.toString();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject json = (JSONObject)jsonArray.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("headImg", R.drawable.homepage_friendlist);
			map.put("userName", json.getString("user"));
			map.put("date", json.getString("missiontime"));
			if (json.getString("missionstate").equals("0"))
				map.put("state", "等待中");
			else
				map.put("state", "进行中");
			map.put("taskContent", json.getString("missionname"));
			map.put("executeTime", json.getString("missiondeadline"));
			map.put("Location", json.getString("missionplace"));
			map.put("postscript", json.getString("missionps"));
			homePageListTmp.add(map);
		}
		if (homePageList == null)
			homePageList =homePageListTmp;
		else {
			homePageList.clear();
			homePageList.addAll(homePageListTmp);
		}
		return homePageList;
	} 
	
	
	//获取陌生人发布的任务
	public static List<Map<String, Object> > getOtherData() throws JSONException{
		/* 测试用数据
		otherList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("headImg", R.drawable.homepage_friendlist);
		map.put("userName", "老卢");
		map.put("date", "2014.5.20");
		map.put("state", "waiting");
		map.put("taskContent", "123");
		map.put("executeTime", "12:30");
		map.put("Location", "Go Go Xintiandi");
		map.put("postscript", "=.=");
		
		for (int i = 0; i < 2; i++)
			otherList.add(map);*/
		List< Map<String, Object> > otherListTmp = new ArrayList<Map<String, Object>>();
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("label", "queryothermission"));
		params.add(new BasicNameValuePair("name", mUserName));
		JSONArray jsonArray = sendMesToServerJSONArray(params);
		//String str = jsonArray.toString();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject json = (JSONObject)jsonArray.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("headImg", R.drawable.homepage_friendlist);
			map.put("userName", json.getString("user"));
			map.put("date", json.getString("missiontime"));
			if (json.getString("missionstate").equals("0"))
				map.put("state", "等待中");
			else
				map.put("state", "进行中");
			map.put("taskContent", json.getString("missionname"));
			map.put("executeTime", json.getString("missiondeadline"));
			map.put("Location", json.getString("missionplace"));
			map.put("postscript", json.getString("missionps"));
			otherListTmp.add(map);
		}
		if (otherList == null)
			otherList =otherListTmp;
		else {
			otherList.clear();
			otherList.addAll(otherListTmp);
		}
		
		return otherList;
	} 
	
	//获取已接任务列表
	public static List<Map<String, Object> > getTaskAcceptedData() throws JSONException {
		/*
		taskAcceptedList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("headImg", R.drawable.homepage_friendlist);
		map.put("userName", "已接任务");
		map.put("date", "2014.5.20");
		map.put("state", "waiting");
		map.put("taskContent", "123");
		map.put("executeTime", "12:30");
		map.put("Location", "Go Go Xintiandi");
		map.put("postscript", "=.=");
		
		for (int i = 0; i < 4; i++)
			taskAcceptedList.add(map);*/
		List< Map<String, Object> > taskAcceptedListTmp = new ArrayList<Map<String, Object>>();
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("label", "queryparticipated"));
		params.add(new BasicNameValuePair("uname", mUserName));
		JSONArray jsonArray = sendMesToServerJSONArray(params);
		//String str = jsonArray.toString();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject json = (JSONObject)jsonArray.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("headImg", R.drawable.homepage_friendlist);
			map.put("userName", json.getString("user"));
			map.put("date", json.getString("missiontime"));
			if (json.getString("missionstate").equals("0"))
				map.put("state", "等待中");
			else
				map.put("state", "进行中");
			map.put("taskContent", json.getString("missionname"));
			map.put("executeTime", json.getString("missiondeadline"));
			map.put("Location", json.getString("missionplace"));
			map.put("postscript", json.getString("missionps"));
			taskAcceptedListTmp.add(map);
		}
		if (taskAcceptedList == null)
			taskAcceptedList = taskAcceptedListTmp;
		else {
			taskAcceptedList.clear();
			taskAcceptedList.addAll(taskAcceptedListTmp);
		}
		
		return taskAcceptedList;
	} 
	
	//获取我的任务列表
	public static List<Map<String, Object> > getMyTaskData() throws JSONException {
		/* 测试数据
		myTaskList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("headImg", R.drawable.homepage_friendlist);
		map.put("userName", "我的任务");
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
				map.put("state", "等待中");
			else
				map.put("state", "进行中");
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
	
	//获取好友列表
	public static List<Map<String, Object> > getFriendList() throws JSONException {
		List< Map<String, Object> > myFriendListTmp = new ArrayList<Map<String, Object>>();
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("label", "queryfriend"));
		params.add(new BasicNameValuePair("uname", mUserName));
		JSONArray jsonArray = sendMesToServerJSONArray(params);
		//String str = jsonArray.toString();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject json = (JSONObject)jsonArray.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("headImg", R.drawable.homepage_headimg);
			map.put("userName", json.getString("user2"));
			myFriendListTmp.add(map);
		}
		if (myFriendList == null)
			myFriendList = myFriendListTmp;
		else {
			myFriendList.clear();
			myFriendList.addAll(myFriendListTmp);
		}
		return myFriendList;
	}
	
	
	//进行登陆操作
	public static String login(String name, String password) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("label", "login"));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("password", password));
		return sendMesToServer(params);
	}
	
	//进行注册操作
	public static String register(String name, String realname, String mobliePhone, String password) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("label", "reg"));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("realname", realname));
		params.add(new BasicNameValuePair("phone", mobliePhone));
		params.add(new BasicNameValuePair("password", password));
		return sendMesToServer(params);
	}
	
	//进行重置密码操作
	public static String resetPwd(String name, String realname, String mobliePhone, String password) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("label", "forgetpassword"));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("realname", realname));
		params.add(new BasicNameValuePair("phone", mobliePhone));
		params.add(new BasicNameValuePair("password", password));
		return sendMesToServer(params);
	}
	
	//更新信息
	public static String updateInfo(String name, String realname, String mobliePhone) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("label", "changeinfo"));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("realname", realname));
		params.add(new BasicNameValuePair("phone", mobliePhone));
		return sendMesToServer(params);
	}
	
	//获取用户信息，返回JSONObject
	public static JSONObject getUserInfo(String name) throws JSONException  {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("label", "getuser"));
		params.add(new BasicNameValuePair("name", name));
		return sendMesToServerJSONObject(params).getJSONObject("");
	}
	
	//添加任务操作
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
	
	//关注好友
	public static String addFriend(String friendName) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("label", "makefriends"));
		params.add(new BasicNameValuePair("name1", mUserName));
		params.add(new BasicNameValuePair("name2", friendName));
		return sendMesToServer(params);
	}
	
	//取消关注好友
	public static String deleteFriend(String friendName) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("label", "deletefriends"));
		params.add(new BasicNameValuePair("name1", mUserName));
		params.add(new BasicNameValuePair("name2", friendName));
		return sendMesToServer(params);
	}
	
	public static String acceptTask(String taskName, String ownername) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("label", "participatemission"));
		params.add(new BasicNameValuePair("uname", mUserName));
		params.add(new BasicNameValuePair("mname", taskName));
		params.add(new BasicNameValuePair("ownername", ownername));
		return sendMesToServer(params);
	}
	
	public static String quitAcceptedTask(String taskName) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("label", "quitmission"));
		params.add(new BasicNameValuePair("uname", mUserName));
		params.add(new BasicNameValuePair("mname", taskName));
		return sendMesToServer(params);
	}
	
	public static String deleteMyTask(String taskName) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("label", "deletemission"));
		params.add(new BasicNameValuePair("uname", mUserName));
		params.add(new BasicNameValuePair("mname", taskName));
		return sendMesToServer(params);
	}
	
	public static boolean getUpdateInfo() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("label", "getupdateinfo"));
		params.add(new BasicNameValuePair("uname", mUserName));
		String result = sendMesToServer(params);
		if (result.equals("success"))
			return true;
		return false;
	}
	
	//发送数据，使用的时http
	//List<NameValuePair> params 是指数据的（key，value）的list
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
	
	public static void writeBitmapFile(Bitmap bitmap) {
		//将缩小的文件写在sd卡上
        String newPath = "/mnt/sdcard/" + DataModel.mUserName + ".jpg";
        File file = new File(newPath);
        try {
            FileOutputStream out=new FileOutputStream(file);
           if(bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)){
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static void deleteBitmapFile() {
		String strFileName = "/mnt/sdcard/" + DataModel.mUserName + ".jpg";
		File file = new File(strFileName);
		if (file.exists()) {
			if(file.delete()){
				Log.w("deleteBitmapFile", "file delete success!");
			}else{
				Log.w("deleteBitmapFile", "file delete fail!");
		}
	}
	}
}
