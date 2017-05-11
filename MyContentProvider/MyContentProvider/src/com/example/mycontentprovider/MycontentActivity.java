package com.example.mycontentprovider;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class MycontentActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//����������¼
		insertRecord("����ͨ");
		insertRecord("�Ӿ���");
		//��ʾ��¼
		displayRecord();
		setContentView(R.layout.activity_mycontent);
	}
	
	//�����¼
	private void insertRecord(String userName){
		ContentValues values=new ContentValues();
		values.put(MyUser.User.USER_NAME,userName);
		getContentResolver().insert(MyUser.User.CONTENT_URI, values);
	}
	//��ʾ����
	private void displayRecord(){
		//����һ���ַ����������ڴ���û��ļ�¼
		String columns[]=new String[]{MyUser.User._ID,MyUser.User.USER_NAME};
		//�趨ContentProvider��Uri
		Uri myUri=MyUser.User.CONTENT_URI;
		@SuppressWarnings("deprecation")
		Cursor cur=managedQuery(myUri,columns,null,null,null);
		if(cur.moveToFirst()){
			String id=null;
			String userName=null;
			do{
				id=cur.getString(cur.getColumnIndex(MyUser.User._ID));
				userName = cur.getString(cur.getColumnIndex(MyUser.User.USER_NAME));
				//��ʾ���ݱ��е�����
				Toast.makeText(this, id+" "+userName, Toast.LENGTH_LONG).show();
			}while(cur.moveToNext());
		}
	}
}
