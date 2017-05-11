package com.example.mycontact;
//提供数据库存放联系人的信息
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class ContactsProvider extends ContentProvider{
	
	//标签
	private static final String TAG = "ContactsProvider";
	//数据库帮助类
	private DBHelper dbHelper;
	//数据库
	private SQLiteDatabase contactsDB;
	//数据库操作uri地址
	public static final String AUTHORITY = "com.example.provider.ContactsProvider";//相当于电话号码
	public static final String CONTACTS_TABLE = "contacts";//可以理解是域名下的一个子页
	public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/"+CONTACTS_TABLE);//content://com.example.provider.ContactsProvider/contacts
	//“content://”可以理解为http：//，后面的可以理解为网站的域名，整个构成了一个完整的网址，这个网址指向联系人数据库的地址
	//自定义的类型
	public static final int CONTACTS = 1;
	public static final int CONTACT_ID = 2;
	private static  UriMatcher uriMatcher;//用于匹配联系人信息，UriMatcher类，用来筛选信息
	static{		//静态代码块，当程序运行时，不需要调用这个类，就运行了，相当于预加载，
		//没有匹配的信息
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		//全部联系人信息
		uriMatcher.addURI(AUTHORITY,"contacts",CONTACTS);
		//单独一个联系人信息
		uriMatcher.addURI(AUTHORITY,"contacts/#",CONTACT_ID);
	}
	//取得数据库
	@Override
	public boolean onCreate() {
		// TODO 自动生成的方法存根d
		dbHelper = new DBHelper(getContext());//新建数据库自己写的DBHelper类
		//执行创建数据库
		contactsDB = dbHelper.getWritableDatabase();//获得这个数据库用对象contactsDB存放
		return (contactsDB == null) ? false : true;
	}
	// 查询数据
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		Log.e(TAG + ":query", " in Query");
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		//设置要查询的数据表
		qb.setTables(CONTACTS_TABLE);

		switch (uriMatcher.match(uri))
		{
			//构建where语句，定位到指定id值的列
			case CONTACT_ID://通过id来查找
				qb.appendWhere(ContactColumn._ID + "=" + uri.getPathSegments().get(1));
				break;
			default:
				break;
		}
		//查询
		Cursor c = qb.query(contactsDB, projection, selection, selectionArgs, null, null, sortOrder);
		//设置通知改变的uri
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}
	
	// URI类型转换
	@Override
	public String getType(Uri uri) {
		// TODO 自动生成的方法存根
		switch (uriMatcher.match(uri)){
			//所有联系人
			case CONTACTS:
				return "vnd.android.cursor.dir/vnd.example.android.mycontacts";//在AndroidManifest.xml中定义不同的数据类型会启动不同的activity
			//指定联系人
			case CONTACT_ID:
				return "vnd.android.cursor.item/vnd.example.android.mycontacts";
			default:
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	// 插入数据
	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		//判断uri地址是否合法
		if (uriMatcher.match(uri) != CONTACTS){
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		ContentValues values;//用一个ContentValues将信息逐个的保存
		if (initialValues != null){
			values = new ContentValues(initialValues);
			Log.e(TAG + "insert", "initialValues is not null");
		}else{
			values = new ContentValues();
		}
		// 如果对应的名称没有值，则设置默认值为“”
		if (values.containsKey(ContactColumn.NAME) == false){
			values.put(ContactColumn.NAME, "");
		}
		if (values.containsKey(ContactColumn.MOBILENUM) == false){
			values.put(ContactColumn.MOBILENUM, "");
		}
		if (values.containsKey(ContactColumn.HOMENUM) == false){
			values.put(ContactColumn.HOMENUM, "");
		}
		if (values.containsKey(ContactColumn.ADDRESS) == false){
			values.put(ContactColumn.ADDRESS, "");
		}
		if (values.containsKey(ContactColumn.EMAIL) == false){
			values.put(ContactColumn.EMAIL, "");
		}
		Log.e(TAG + "insert", values.toString());
		//插入数据
		long rowId = contactsDB.insert(CONTACTS_TABLE, null, values);
		if (rowId > 0){
			//将id值加入uri地址中
			Uri noteUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
			//通知改变
			getContext().getContentResolver().notifyChange(noteUri, null);
			Log.e(TAG + "insert", noteUri.toString());
			return noteUri;
		}
		throw new SQLException("Failed to insert row into " + uri);
	}
	
	//删除指定数据列
	@Override
	public int delete(Uri uri, String where, String[] selectionArgs) {
		// TODO 自动生成的方法存根
		int count;
		switch(uriMatcher.match(uri)){
			//删除满足条件where的行
		case CONTACTS://uriMatcher匹配到CONTACTS这个数据
			count = contactsDB.delete(CONTACTS_TABLE, where, selectionArgs);
			break;
		case CONTACT_ID://根据id删除
			//取得联系人的id信息
			String contactID = uri.getPathSegments().get(1);
			//删除满足where条件，并且id值为contactID的记录
			count = contactsDB.delete(CONTACTS_TABLE, 
									  ContactColumn._ID 
									  + "=" + contactID 
									  + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""),
									  selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String where, String[] selectionArgs) {
		// TODO 自动生成的方法存根
		int count;
		Log.e(TAG + "update", values.toString());
		Log.e(TAG + "update", uri.toString());
		Log.e(TAG + "update :match", "" + uriMatcher.match(uri));
		switch (uriMatcher.match(uri)){
			//根据where条件批量进行更新
			case CONTACTS:
				Log.e(TAG + "update", CONTACTS + "");
				count = contactsDB.update(CONTACTS_TABLE, values, where, selectionArgs);
				break;
			//更新指定行
			case CONTACT_ID:
				String contactID = uri.getPathSegments().get(1);
				Log.e(TAG + "update", contactID + "");
				count = contactsDB.update(CONTACTS_TABLE, values, ContactColumn._ID + "=" + contactID
						+ (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""), selectionArgs);
				break;
			default:
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		//通知更改
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}
}
