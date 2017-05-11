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
 * MyContentProvider�̳�ContentProvider�࣬ʵ����insert,update,delete,getType,onCreate�ȷ���
 */

public class MycontentProvider extends ContentProvider{
	//����һ��SQLiteDatabase����
	private SQLiteDatabase sqlDB;
	// ����һ��DatabaseHelper����
	private DatabaseHelper dbHelper;//���ݿ�����࣬�½����������ݿ�
	//���ݿ���
	private static final String DATABASE_NAME="Users.db";
	//���ݿ�汾
	private static final int DATABASE_VERSION=1;
	//����
	private static final String TABLE_NAME="User";
	
	
	//���ݿ�����࣬ʵ�����ݿ���½�����������ɾ�Ĳ飩
	public static class DatabaseHelper extends SQLiteOpenHelper{
		//���췽��
		public DatabaseHelper(Context context ){
			//���츸�෽��
			super(context, DATABASE_NAME, null,DATABASE_VERSION );
		}
		@Override
		public void onCreate(SQLiteDatabase db){
			//�����ݿ�������һ�ű�
			db.execSQL("Create table "+TABLE_NAME+"( _id INTEGER PRIMARY KEY AUTOINCREMENT, USER_NAME TEXT);");
			//onCreate(db);
		}
		// ���������ݿ�汾��ʱ�򣬵��ø÷���������ɾ�����޸ı��һЩ��Ϣ
		@Override
		public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
			db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
			onCreate(db);
		}
	}
	 // ����һ���ص�������������������Ķ���ʱ��������������ã�����һ�����ݿ�
	@Override
	public boolean onCreate(){
		dbHelper=new DatabaseHelper(getContext());
		return (dbHelper == null)?false:true;
	}
	// ��ѯ
	@Override
	public Cursor query(Uri uri, String[] projection,String selection,String[] selectionArgs,String sortOrder){
		SQLiteQueryBuilder qb= new SQLiteQueryBuilder();
		SQLiteDatabase db=dbHelper.getReadableDatabase();
		qb.setTables(TABLE_NAME);
		Cursor c=qb.query(db, projection, selection, null, null, null, sortOrder);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}
	
	//ȡ������
	@Override
	public String getType(Uri uri){
		return null;
	}
	//��������
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
	//ɾ������
	@Override
	public int delete(Uri uri,String selection,String[] selectionArgs){
		return 0;
	}
	// ��������
	@Override
	public int update(Uri uri,ContentValues values,String selection,String[] selectionArgs){
		return 0;
	}
	
}
