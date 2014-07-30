package com.example.helpandgetfun.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;

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

import com.example.helpandgetfun.R;
import com.example.helpandgetfun.R.drawable;

import android.R.bool;
import android.R.integer;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter.ViewBinder;

public class DataUtils {
	public static List< Map<String, Object> > homePageList, otherList, taskAcceptedList, myTaskList, myFriendList, infoList;
	public static JSONArray homePageJsonArray, otherJsonArray, taskAcceptedJsonArray, myTaskJsonArray;
	private static String ServerURL = "http://1.helpandgetfun.sinaapp.com/phpsrc/";
	private static String ImgServerURL = "http://helpandgetfun-headimg.stor.sinaapp.com/";
	public static String mUserName, mRealName, mPhone, mPassword, mheadimg;
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
	public static String SENDMES_SUCCESS = "success";
	public static String FINISHTASK_SUCCESS = "success";
	public static boolean LOGIN_FLAG = false;
	public static boolean NEW_MES_FLAG = false;
	public static boolean NEW_MES_CALL_FLAG = false;
	public static Bitmap imgBm = null;
	public static boolean ADD_TASK_FLAG = false;
	public static boolean PERSON_INFO_UPDATE = false;
	public static boolean CACHE_HOMEPAGE_FLAG = false;
	public static boolean CACHE_TASKACCEPTED_FLAG = false;
	public static boolean CACHE_MYTASK_FLAG = false;
	public static boolean CACHE_OTHER_FLAG = false;
	
	public static List<Map<String, Object> > getHomePageData() throws JSONException, IOException{
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
		JSONArray jsonArray;
		if (CACHE_HOMEPAGE_FLAG == false) {
			jsonArray = sendMesToServerJSONArray(params);
			homePageJsonArray = new JSONArray();
			homePageJsonArray = jsonArray;
		}
		else  {
			jsonArray = homePageJsonArray;
			CACHE_HOMEPAGE_FLAG = false;
		}
		//String str = jsonArray.toString();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject json = (JSONObject)jsonArray.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			if (json.getString("headimg").length() > 0) {
				writeBitmapFile(DataUtils.getImg(json.getString("headimg")), json.getString("user"));
				map.put("headImg", DataUtils.getImg(json.getString("headimg")));
			}
			else
				map.put("headImg", R.drawable.homepage_headimg2);
			map.put("userName", json.getString("user"));
			map.put("date", json.getString("missiontime"));
			if (json.getString("missionstate").equals("0"))
				map.put("state", "等待中");
			else if (json.getString("missionstate").equals("1"))
				map.put("state", "进行中");
			else
				map.put("state", "已完成");
			map.put("taskContent", json.getString("missionname"));
			map.put("executeTime", json.getString("missiondeadline"));
			map.put("Location", json.getString("missionplace"));
			map.put("postscript", json.getString("missionps"));
			homePageListTmp.add(map);
		}
		return homePageListTmp;
	} 
	
	
	//获取陌生人发布的任务
	public static List<Map<String, Object> > getOtherData() throws JSONException, IOException{
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
		JSONArray jsonArray;
		if (CACHE_OTHER_FLAG == false) {
			jsonArray = sendMesToServerJSONArray(params);
			otherJsonArray = new JSONArray();
			otherJsonArray = jsonArray;
		}
		else {
			jsonArray = otherJsonArray;
			CACHE_OTHER_FLAG = false;
		}
		//String str = jsonArray.toString();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject json = (JSONObject)jsonArray.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			if (json.getString("headimg").length() > 0) {
				writeBitmapFile(DataUtils.getImg(json.getString("headimg")), json.getString("user"));
				map.put("headImg", DataUtils.getImg(json.getString("headimg")));
			}
			else
				map.put("headImg", R.drawable.homepage_headimg2);
			map.put("userName", json.getString("user"));
			map.put("date", json.getString("missiontime"));
			if (json.getString("missionstate").equals("0"))
				map.put("state", "等待中");
			else if (json.getString("missionstate").equals("1"))
				map.put("state", "进行中");
			else
				map.put("state", "已完成");
			map.put("taskContent", json.getString("missionname"));
			map.put("executeTime", json.getString("missiondeadline"));
			map.put("Location", json.getString("missionplace"));
			map.put("postscript", json.getString("missionps"));
			otherListTmp.add(map);
		}
		return otherListTmp;
	} 
	
	//获取已接任务列表
	public static List<Map<String, Object> > getTaskAcceptedData() throws JSONException, IOException {
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
		JSONArray jsonArray;
		if (CACHE_TASKACCEPTED_FLAG == false) {
			jsonArray = sendMesToServerJSONArray(params);
			taskAcceptedJsonArray = new JSONArray();
			taskAcceptedJsonArray = jsonArray;
		}
		else {
			jsonArray = taskAcceptedJsonArray;
			CACHE_TASKACCEPTED_FLAG = false;
		}
		//String str = jsonArray.toString();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject json = (JSONObject)jsonArray.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			if (json.getString("headimg").length() > 0) {
				writeBitmapFile(DataUtils.getImg(json.getString("headimg")), json.getString("user"));
				map.put("headImg", DataUtils.getImg(json.getString("headimg")));
			}
			else
				map.put("headImg", R.drawable.homepage_headimg2);
			map.put("userName", json.getString("user"));
			map.put("date", json.getString("missiontime"));
			if (json.getString("missionstate").equals("0"))
				map.put("state", "等待中");
			else if (json.getString("missionstate").equals("1"))
				map.put("state", "进行中");
			else
				map.put("state", "已完成");
			map.put("taskContent", json.getString("missionname"));
			map.put("executeTime", json.getString("missiondeadline"));
			map.put("Location", json.getString("missionplace"));
			map.put("postscript", json.getString("missionps"));
			taskAcceptedListTmp.add(map);
		}
		return taskAcceptedListTmp;
	} 
	
	//获取我的任务列表
	public static List<Map<String, Object> > getMyTaskData() throws JSONException, IOException {
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
		JSONArray jsonArray;
		if (CACHE_MYTASK_FLAG == false) {
			jsonArray = sendMesToServerJSONArray(params);
			myTaskJsonArray = new JSONArray();
			myTaskJsonArray = jsonArray;
		}
		else {
			jsonArray = myTaskJsonArray;
			CACHE_MYTASK_FLAG = false;
		}
		//String str = jsonArray.toString();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject json = (JSONObject)jsonArray.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			if (json.getString("headimg").length() > 0) {
				writeBitmapFile(DataUtils.getImg(json.getString("headimg")), mUserName);
				map.put("headImg", DataUtils.getImg(json.getString("headimg")));
			}
			else
				map.put("headImg", R.drawable.homepage_headimg2);
			map.put("userName", mUserName);
			map.put("date", json.getString("missiontime"));
			if (json.getString("missionstate").equals("0"))
				map.put("state", "等待中");
			else if (json.getString("missionstate").equals("1"))
				map.put("state", "进行中");
			else
				map.put("state", "已完成");
			map.put("taskContent", json.getString("missionname"));
			map.put("executeTime", json.getString("missiondeadline"));
			map.put("Location", json.getString("missionplace"));
			map.put("postscript", json.getString("missionps"));
			myTaskListTmp.add(map);
		}
		return myTaskListTmp;
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
		return myFriendListTmp;
	}
	
	//获取消息列表
	public static List<Map<String, Object> > getInfoList() throws JSONException{
		List< Map<String, Object> > InfoListTmp = new ArrayList<Map<String, Object>>();
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("label", "getinfolist"));
		params.add(new BasicNameValuePair("name", mUserName));
		JSONArray jsonArray = sendMesToServerJSONArray(params);
		//String str = jsonArray.toString();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject json = (JSONObject)jsonArray.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("senderName", json.getString("sender"));
			String mes = json.getString("mes");
			if (mes.equals("accept your task"))
				map.put("senderMes", "接受了你的任务");
			else
				map.put("senderMes", mes);
			InfoListTmp.add(map);
		}

		return InfoListTmp;
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
	public static String updateInfo(String name, String realname, String mobliePhone, String headimg) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("label", "changeinfo"));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("realname", realname));
		params.add(new BasicNameValuePair("phone", mobliePhone));
		params.add(new BasicNameValuePair("headimg", headimg));
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
	
	public static String finishMyTask(String taskName) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("label", "finishmission"));
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
	
	public static String sendMes(String senderName, String content) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("label", "sendmes"));
		params.add(new BasicNameValuePair("uname", mUserName));
		params.add(new BasicNameValuePair("sendername", senderName));
		params.add(new BasicNameValuePair("content", content));
		return sendMesToServer(params);
	}
	
	//发送数据，使用的是http
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
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[4096];
				int ch = -1;
				String str = "";
				while ((ch = inputStream.read(buffer)) != -1) {
					baos.write(buffer, 0, ch);
				}
				baos.flush();
				str = baos.toString("GB2312");
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
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[4096];
				int ch = -1;
				String str = "";
				while ((ch = inputStream.read(buffer)) != -1) {
					baos.write(buffer, 0, ch);
				}
				baos.flush();
				str = baos.toString("GB2312");
				if (str != null) {
					Log.d("jsonStr", str);
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
	
	public static void writeBitmapFile(Bitmap bitmap, String name) {
		//将缩小的文件写在data文件夹中
        String newPath = getSDPath() + "/" + name + ".jpg";
        File file = new File(newPath);
        try {
        	FileOutputStream out=new FileOutputStream(file);
        	if(bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)){
		        out.flush();
		        out.close();
        	}
        	Log.w("writeBitmapFile", "file create success!");
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static void deleteBitmapFile() {
		String strFileName = getSDPath() + "/" + DataUtils.mUserName + ".jpg";
		File file = new File(strFileName);
		if (file.exists()) {
			if(file.delete()){
				Log.w("deleteBitmapFile", "file delete success!");
			}else{
				Log.w("deleteBitmapFile", "file delete fail!");
			}
		}
	}
	
	// 参考于 http://blog.csdn.net/sxwyf248/article/details/7012496
	public static String uploadFile() {
		String filepath = getSDPath() +"/" + DataUtils.mUserName + ".jpg";
		File uploadFile = new File(filepath);
		if (uploadFile.exists()) {
			String urlServer = ServerURL + "receiveImg.php";
			String end = "\r\n";
		    String twoHyphens = "--";
		    String boundary = "******";
		    try
		    {
		    	URL url = new URL(urlServer);
		    	HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		    	// 设置每次传输的流大小，可以有效防止手机因为内存不足崩溃
		    	// 此方法用于在预先不知道内容长度时启用没有进行内部缓冲的 HTTP 请求正文的流。
		    	httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
		    	// 允许输入输出流
		    	httpURLConnection.setDoInput(true);
		    	httpURLConnection.setDoOutput(true);
		    	httpURLConnection.setUseCaches(false);
		    	// 使用POST方法
		    	httpURLConnection.setRequestMethod("POST");
		    	httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
		    	httpURLConnection.setRequestProperty("Charset", "UTF-8");
		    	httpURLConnection.setRequestProperty("Content-Type","multipart/form-data;boundary=" + boundary);

		    	DataOutputStream dos = new DataOutputStream(
		    			httpURLConnection.getOutputStream());
		    	dos.writeBytes(twoHyphens + boundary + end);
		    	dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\"; filename=\""
		    			+ filepath.substring(filepath.lastIndexOf("/") + 1)
		    			+ "\""
		    			+ end);
		    	dos.writeBytes(end);

		    	FileInputStream fis = new FileInputStream(filepath);
		    	byte[] buffer = new byte[8192]; // 8k
		    	int count = 0;
		    	// 读取文件
		    	while ((count = fis.read(buffer)) != -1)
		    	{
		    		dos.write(buffer, 0, count);
		    	}
		    	fis.close();

		    	dos.writeBytes(end);
		    	dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
		    	dos.flush();

		    	InputStream is = httpURLConnection.getInputStream();
		    	InputStreamReader isr = new InputStreamReader(is, "utf-8");
		    	BufferedReader br = new BufferedReader(isr);
		    	String result = br.readLine();

		    	dos.close();
		    	is.close();
		    	
		    	return result;

		    } catch (Exception e)
		    {
		    	e.printStackTrace();
		    }
		}

		return null;
	}
	
	public static String getSDPath(){ 
	       File sdDir = null; 
	       boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);   //判断sd卡是否存在 
	       if   (sdCardExist)   
	       {                               
	         sdDir = Environment.getExternalStorageDirectory();//获取跟目录 
	       }   
	       File file = new File(sdDir.toString() + "/helpandfun/cache");
	       if(!file.exists()) {
	    	   file.mkdirs();
	       }
	       return sdDir.toString() + "/helpandfun/cache"; 
	       
	}
	
	public static Bitmap getImg(String img) throws IOException {
		URL url;
		try {
			url = new URL(ImgServerURL + img);
			InputStream in = url.openStream();
			//通过BitmapFactory获得实例  
	        return BitmapFactory.decodeStream(in);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null; 
	}
	
	public static boolean isDateValid(int year, int month, int day) {
		if (year < 0 || month < 0 || day < 0)
			return false;
		int maxDay = -1;
		switch (month) {
		case 4: case 6: case 9: case 11:
			maxDay = 30;
			break;
		case 1: case 3: case 5: case 7: case 8: case 10: case 12:
			maxDay = 31;
			break;
		case 2:
			if((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
				maxDay = 29;
			else
				maxDay = 28;
			break;
		}
		if (maxDay == -1 || day > maxDay)
			return false;
		return true;
	}
	
	public static void writeCache() {
		String cacheStr = "[" + "{homepage:" + homePageJsonArray.toString() + "}" 
				+ ",{taskaccepted:" + taskAcceptedJsonArray.toString() + "}"
				+ ",{mytask:" + myTaskJsonArray.toString() + "}"
				+ ",{othertask:" + otherJsonArray.toString() + "}" + "]";
		Log.d("cacheStr", cacheStr);

		File file = new File(getSDPath() + '/' + mUserName + ".txt");
		FileWriter fw = null;
		try {
		    if(!file.exists()){
		    	file.createNewFile();
		    }
		    fw = new FileWriter(file);
		    BufferedWriter out = new BufferedWriter(fw);
		    out.write(cacheStr, 0, cacheStr.length());
		    out.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}

		Log.d("homePage", homePageJsonArray.toString());
		Log.d("taskAccept", taskAcceptedJsonArray.toString());
		Log.d("mytask", myTaskJsonArray.toString());
		Log.d("otherTask", otherJsonArray.toString());
	}
	
	public static void readCache() {
		if (mUserName != null) {
			File file = new File(getSDPath() + '/' + mUserName + ".txt");
			if (file.exists()) {
				try {
					BufferedReader reader  = new BufferedReader(new FileReader(file));
					String cacheStr = "";
					String tmp;
					while((tmp= reader.readLine()) != null)   
						cacheStr += tmp;
					reader.close();
					JSONArray jsonArray = new JSONArray(cacheStr);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonTask = (JSONObject)jsonArray.get(i);
						Log.d("jsontask", jsonTask.toString());
						if (!jsonTask.isNull("homepage")) {
							homePageJsonArray = (JSONArray) jsonTask.get("homepage");
						}
						else if (!jsonTask.isNull("taskaccepted") ) {
							taskAcceptedJsonArray = (JSONArray) jsonTask.get("taskaccepted");
						}else if (!jsonTask.isNull("mytask")) {
							myTaskJsonArray = (JSONArray) jsonTask.get("mytask");
						}else if (!jsonTask.isNull("othertask")) {
							otherJsonArray = (JSONArray) jsonTask.get("othertask");
						}					
					}
					CACHE_HOMEPAGE_FLAG = true;
					CACHE_TASKACCEPTED_FLAG = true;
					CACHE_OTHER_FLAG = true;
					CACHE_MYTASK_FLAG = true;
					Log.d("homePage", homePageJsonArray.toString());
					Log.d("taskAccept", taskAcceptedJsonArray.toString());
					Log.d("mytask", myTaskJsonArray.toString());
					Log.d("otherTask", otherJsonArray.toString());

				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
