package com.example.mycontentprovider;

import android.net.Uri;
import android.provider.BaseColumns;

public class MyUser {
	public static final String AUTHORITY = "com.example.MyContentProvider";
	// BaseColumn�����Ѿ�������_id�ֶ�
	public static final class User implements BaseColumns{
		//����Uri
		public static final Uri CONTENT_URI=Uri.parse("content://"+AUTHORITY);
		//���������б�
		public static final String USER_NAME="USER_NAME";
		
	}
}
