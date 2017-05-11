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
		//�õ�ContentProvoider�Ա���������ݣ����α��ʽ����
		Cursor c = managedQuery(CONTENT_URI,new String[] {"_id","USER_NAME"},null,null,null);
		//ѭ����ӡContentProvider������
		if(c.moveToFirst()){
			String _id=null;
			String user_name=null;
			do{
				_id=c.getString(c.getColumnIndex("USER_NAME"));
				
				sb.append("_id "+_id+", user_name = "+user_name+"\n");
			}while(c.moveToNext());
			show.setText(sb);
			//����ID�õ����ƶ���
			insertButton = (Button)findViewById(R.id.insert);
			//����ť���¼�������
			insertButton.setOnClickListener(new View.OnClickListener(){
				
				@Override
				public void onClick(View v) {
					// TODO �Զ����ɵķ������
					String username=((EditText)findViewById(R.id.userName)).getText().toString();
					//����һ��ContentResolver����
					ContentResolver cr=getContentResolver();
					//����һ��ContentValues����
					ContentValues values = new ContentValues();
					//��EditText�����ֵ���浽ContentValues������
					values.put("USER_NAME", username);
					//��������
					cr.insert(CONTENT_URI, values);
				}
			});
		}
	}
}
