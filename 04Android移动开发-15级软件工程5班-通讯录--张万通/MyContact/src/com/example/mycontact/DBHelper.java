package com.example.mycontact;
//数据库帮助类，创建数据库 
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
	
	//数据库名
	public static final String DATABASE_NAME = "mycontacts.db";
	//版本
	public static final int DATABASE_VERSION = 2;	
	//表名
	public static final String CONTACTS_TABLE = "contacts";	 
	//创建表
	private static final String DATABASE_CREATE=
			"CREATE TABLE " + CONTACTS_TABLE +" ("					
			+ ContactColumn._ID+" integer primary key autoincrement,"
			+ ContactColumn.NAME+" text,"
			+ ContactColumn.MOBILENUM+" text,"
			+ ContactColumn.HOMENUM+" text,"
			+ ContactColumn.ADDRESS+" text,"
			+ ContactColumn.EMAIL+" text )";;
	
	public DBHelper(Context context) {
		//super(context, name, factory, version);
		// TODO 自动生成的构造函数存根
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO 自动生成的方法存根
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO 自动生成的方法存根
		db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE);
		onCreate(db);
	}

}
