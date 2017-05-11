package com.example.mycontentprovider;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * MyContentProvider继承ContentProvider类，实现其insert,update,delete,getType,onCreate等方法
 */

public class MycontentProvider extends ContentProvider{
	//定义一个SQLiteDatabase变量
	private SQLiteDatabase sqlDB;
	// 定义一个DatabaseHelper变量
	private DatabaseHelper dbHelper;//数据库帮助类，新建和升级数据库
	//数据库名
	private static final String DATABASE_NAME="Users.db";
	//数据库版本
	private static final int DATABASE_VERSION=1;
	//表名
	private static final String TABLE_NAME="User";
	
	
	//数据库帮助类，实现数据库的新建和升级（增删改查）
	public static class DatabaseHelper extends SQLiteOpenHelper{
		//构造方法
		public DatabaseHelper(Context context ){
			//构造父类方法
			super(context, DATABASE_NAME, null,DATABASE_VERSION );
		}
		@Override
		public void onCreate(SQLiteDatabase db){
			//在数据库中生成一张表
			db.execSQL("Create table "+TABLE_NAME+"( _id INTEGER PRIMARY KEY AUTOINCREMENT, USER_NAME TEXT);");
			//onCreate(db);
		}
		// 当更新数据库版本的时候，调用该方法。可以删除，修改表的一些信息
		@Override
		public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
			db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
			onCreate(db);
		}
	}
	 // 这是一个回调函数，当生成所在类的对象时，这个方法被调用，创建一个数据库
	@Override
	public boolean onCreate(){
		dbHelper=new DatabaseHelper(getContext());
		return (dbHelper == null)?false:true;
	}
	// 查询
	@Override
	public Cursor query(Uri uri, String[] projection,String selection,String[] selectionArgs,String sortOrder){
		SQLiteQueryBuilder qb= new SQLiteQueryBuilder();
		SQLiteDatabase db=dbHelper.getReadableDatabase();
		qb.setTables(TABLE_NAME);
		Cursor c=qb.query(db, projection, selection, null, null, null, sortOrder);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}
	
	//取得类型
	@Override
	public String getType(Uri uri){
		return null;
	}
	//插入数据
	@Override
	public Uri insert(Uri uri,ContentValues contentvalues){
		sqlDB=dbHelper.getWritableDatabase();
		long rowId=sqlDB.insert(TABLE_NAME,"",contentvalues);
		if(rowId > 0){
			Uri rowUri=ContentUris.appendId(MyUser.User.CONTENT_URI.buildUpon(), rowId).build();
			getContext().getContentResolver().notifyChange(rowUri, null);
		return rowUri;
		}
		throw new SQLException("Failed to insert row into "+uri);
	}
	//删除数据
	@Override
	public int delete(Uri uri,String selection,String[] selectionArgs){
		return 0;
	}
	// 更新数据
	@Override
	public int update(Uri uri,ContentValues values,String selection,String[] selectionArgs){
		return 0;
	}
	
}
