package com.example.mycontentclient;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	public static final String AUTHORITY="com.example.MyContentProvider";
	private Button insertButton=null;
	Uri CONTENT_URI=Uri.parse("content://"+AUTHORITY);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView show=(TextView)findViewById(R.id.show);
		StringBuffer sb=new StringBuffer("");
		//得到ContentProvoider对表的所有数据，以游标格式保存
		Cursor c = managedQuery(CONTENT_URI,new String[] {"_id","USER_NAME"},null,null,null);
		//循环打印ContentProvider的数据
		if(c.moveToFirst()){
			String _id=null;
			String user_name=null;
			do{
				_id=c.getString(c.getColumnIndex("USER_NAME"));
				
				sb.append("_id "+_id+", user_name = "+user_name+"\n");
			}while(c.moveToNext());
			show.setText(sb);
			//根据ID得到控制对象
			insertButton = (Button)findViewById(R.id.insert);
			//给按钮绑定事件监听器
			insertButton.setOnClickListener(new View.OnClickListener(){
				
				@Override
				public void onClick(View v) {
					// TODO 自动生成的方法存根
					String username=((EditText)findViewById(R.id.userName)).getText().toString();
					//生成一个ContentResolver对象
					ContentResolver cr=getContentResolver();
					//生成一个ContentValues对象
					ContentValues values = new ContentValues();
					//将EditText输入的值保存到ContentValues对象中
					values.put("USER_NAME", username);
					//插入数据
					cr.insert(CONTENT_URI, values);
				}
			});
		}
	}
}
