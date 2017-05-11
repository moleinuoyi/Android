package com.example.mycontact;
//���ݿ�����࣬�������ݿ� 
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
	
	//���ݿ���
	public static final String DATABASE_NAME = "mycontacts.db";
	//�汾
	public static final int DATABASE_VERSION = 2;	
	//����
	public static final String CONTACTS_TABLE = "contacts";	 
	//������
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
		// TODO �Զ����ɵĹ��캯�����
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO �Զ����ɵķ������
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO �Զ����ɵķ������
		db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE);
		onCreate(db);
	}

}
