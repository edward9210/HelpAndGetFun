package com.example.helpandgetfun.viewcontroller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.example.helpandgetfun.R;
import com.example.helpandgetfun.R.id;
import com.example.helpandgetfun.R.layout;
import com.example.helpandgetfun.utils.DataUtils;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.ContactsContract.Contacts.Data;

public class PersonInfoActivity extends Activity {
	private final String IMAGE_TYPE = "image/*";
	private final int IMAGE_CODE = 0;
	
	private EditText realNameEditText ,mobilePhoneEditText;
	private TextView userNameTextView;
	private ImageButton cancelButton;
	private ImageView headimg;
	private Button saveButton;
	private Bitmap bitmap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_info);
		setAllWidget();
		setAllListener();
		init();
	}


	private void init() {
		// TODO Auto-generated method stub
		bitmap = null;
		userNameTextView.setText(DataUtils.mUserName);
		realNameEditText.setText(DataUtils.mRealName);
		mobilePhoneEditText.setText(DataUtils.mPhone);
	}


	private void setAllWidget() {
		// TODO Auto-generated method stub
		
		userNameTextView = (TextView) findViewById(R.id.person_info_username);
		realNameEditText = (EditText) findViewById(R.id.person_info_realname);
		mobilePhoneEditText = (EditText) findViewById(R.id.person_info_mobilephone);
		saveButton = (Button) findViewById(R.id.person_info_save);
		cancelButton = (ImageButton) findViewById(R.id.person_info_cancelbutton);
		headimg = (ImageView) findViewById(R.id.person_info_headimg);
	}

	private void setAllListener() {
		saveButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(PersonInfoActivity.this, "saveButton!!!" , Toast.LENGTH_SHORT).show();
				String name = userNameTextView.getText().toString();
				String realname = realNameEditText.getText().toString();
				String mobliePhone = mobilePhoneEditText.getText().toString();
				String result = DataUtils.updateInfo(name, realname, mobliePhone, DataUtils.mUserName + ".jpg");
				if (result.equals(DataUtils.UPDATE_SUCCESS)) {
					Toast.makeText(PersonInfoActivity.this, "保存成功" , Toast.LENGTH_SHORT).show();
					DataUtils.mRealName = realname;
					DataUtils.mPhone = mobliePhone;
					Intent intent = new Intent(); 
		        	intent.setClass(PersonInfoActivity.this, MainActivity.class); /* 调用一个新的Activity */
		        	startActivity(intent);
		        	/* 关闭原本的Activity */ 
		        	PersonInfoActivity.this.finish();
				}
				else {
					Toast.makeText(PersonInfoActivity.this, "保存失败" , Toast.LENGTH_SHORT).show();
				}
			}
		
		});
		cancelButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(PersonInfoActivity.this, "cancelButton!!!" , Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(); 
	        	intent.setClass(PersonInfoActivity.this, MainActivity.class); /* 调用一个新的Activity */
	        	startActivity(intent);
	        	/* 关闭原本的Activity */ 
	        	PersonInfoActivity.this.finish();
			}
		
		});
		
		headimg.setOnClickListener(new Button.OnClickListener()
    	{
    		public void onClick(View v)
    		{
    			//选择照片
    			Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
            	getAlbum.setType(IMAGE_TYPE);
            	startActivityForResult(getAlbum, IMAGE_CODE);
    		}
    	});
		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);  
		   
        if (requestCode == IMAGE_CODE && resultCode == RESULT_OK && data != null) {  
            Uri selectedImage = data.getData();  
            String[] filePathColumn = { MediaStore.Images.Media.DATA };  
   
            Cursor cursor = getContentResolver().query(selectedImage,  
                    filePathColumn, null, null, null);  
            cursor.moveToFirst();  
   
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);  
            String picturePath = cursor.getString(columnIndex);  
            cursor.close();  
            
        	BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inJustDecodeBounds = true;
	        // 获取这个图片的宽和高
	        bitmap =BitmapFactory.decodeFile(picturePath, options); 
	        options.inJustDecodeBounds =false;
	         //计算缩放比
	        int be = (int)(options.outHeight/ (float)250);
	        if (be <= 0)
	            be = 1;
	        options.inSampleSize = be;
	        bitmap = BitmapFactory.decodeFile(picturePath,options);
	        //裁剪成正方形
	        int w = bitmap.getWidth();
	        int h = bitmap.getHeight();
	        int wh = w > h ? h : w;
	        int retX = w > h ? (w - h) / 2 : 0;//基于原图，取正方形左上角x坐标
	        int retY = w > h ? 0 : (h - w) / 2;
	        bitmap = Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null, false);

            headimg.setImageBitmap(bitmap);  
   
        }  
    } 

}
