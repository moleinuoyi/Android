package com.example.mycontentprovider;

import android.net.Uri;
import android.provider.BaseColumns;

public class MyUser {
	public static final String AUTHORITY = "com.example.MyContentProvider";
	// BaseColumn类中已经包含了_id字段
	public static final class User implements BaseColumns{
		//定义Uri
		public static final Uri CONTENT_URI=Uri.parse("content://"+AUTHORITY);
		//定义数据列表
		public static final String USER_NAME="USER_NAME";
		
	}
}
