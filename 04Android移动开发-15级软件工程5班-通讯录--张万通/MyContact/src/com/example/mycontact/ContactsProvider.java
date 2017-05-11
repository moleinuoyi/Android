package com.example.mycontact;
//�ṩ���ݿ�����ϵ�˵���Ϣ
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
	
	//��ǩ
	private static final String TAG = "ContactsProvider";
	//���ݿ������
	private DBHelper dbHelper;
	//���ݿ�
	private SQLiteDatabase contactsDB;
	//���ݿ����uri��ַ
	public static final String AUTHORITY = "com.example.provider.ContactsProvider";//�൱�ڵ绰����
	public static final String CONTACTS_TABLE = "contacts";//��������������µ�һ����ҳ
	public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/"+CONTACTS_TABLE);//content://com.example.provider.ContactsProvider/contacts
	//��content://���������Ϊhttp��//������Ŀ������Ϊ��վ������������������һ����������ַ�������ַָ����ϵ�����ݿ�ĵ�ַ
	//�Զ��������
	public static final int CONTACTS = 1;
	public static final int CONTACT_ID = 2;
	private static  UriMatcher uriMatcher;//����ƥ����ϵ����Ϣ��UriMatcher�࣬����ɸѡ��Ϣ
	static{		//��̬����飬����������ʱ������Ҫ��������࣬�������ˣ��൱��Ԥ���أ�
		//û��ƥ�����Ϣ
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		//ȫ����ϵ����Ϣ
		uriMatcher.addURI(AUTHORITY,"contacts",CONTACTS);
		//����һ����ϵ����Ϣ
		uriMatcher.addURI(AUTHORITY,"contacts/#",CONTACT_ID);
	}
	//ȡ�����ݿ�
	@Override
	public boolean onCreate() {
		// TODO �Զ����ɵķ������d
		dbHelper = new DBHelper(getContext());//�½����ݿ��Լ�д��DBHelper��
		//ִ�д������ݿ�
		contactsDB = dbHelper.getWritableDatabase();//���������ݿ��ö���contactsDB���
		return (contactsDB == null) ? false : true;
	}
	// ��ѯ����
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		Log.e(TAG + ":query", " in Query");
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		//����Ҫ��ѯ�����ݱ�
		qb.setTables(CONTACTS_TABLE);

		switch (uriMatcher.match(uri))
		{
			//����where��䣬��λ��ָ��idֵ����
			case CONTACT_ID://ͨ��id������
				qb.appendWhere(ContactColumn._ID + "=" + uri.getPathSegments().get(1));
				break;
			default:
				break;
		}
		//��ѯ
		Cursor c = qb.query(contactsDB, projection, selection, selectionArgs, null, null, sortOrder);
		//����֪ͨ�ı��uri
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}
	
	// URI����ת��
	@Override
	public String getType(Uri uri) {
		// TODO �Զ����ɵķ������
		switch (uriMatcher.match(uri)){
			//������ϵ��
			case CONTACTS:
				return "vnd.android.cursor.dir/vnd.example.android.mycontacts";//��AndroidManifest.xml�ж��岻ͬ���������ͻ�������ͬ��activity
			//ָ����ϵ��
			case CONTACT_ID:
				return "vnd.android.cursor.item/vnd.example.android.mycontacts";
			default:
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	// ��������
	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		//�ж�uri��ַ�Ƿ�Ϸ�
		if (uriMatcher.match(uri) != CONTACTS){
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		ContentValues values;//��һ��ContentValues����Ϣ����ı���
		if (initialValues != null){
			values = new ContentValues(initialValues);
			Log.e(TAG + "insert", "initialValues is not null");
		}else{
			values = new ContentValues();
		}
		// �����Ӧ������û��ֵ��������Ĭ��ֵΪ����
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
		//��������
		long rowId = contactsDB.insert(CONTACTS_TABLE, null, values);
		if (rowId > 0){
			//��idֵ����uri��ַ��
			Uri noteUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
			//֪ͨ�ı�
			getContext().getContentResolver().notifyChange(noteUri, null);
			Log.e(TAG + "insert", noteUri.toString());
			return noteUri;
		}
		throw new SQLException("Failed to insert row into " + uri);
	}
	
	//ɾ��ָ��������
	@Override
	public int delete(Uri uri, String where, String[] selectionArgs) {
		// TODO �Զ����ɵķ������
		int count;
		switch(uriMatcher.match(uri)){
			//ɾ����������where����
		case CONTACTS://uriMatcherƥ�䵽CONTACTS�������
			count = contactsDB.delete(CONTACTS_TABLE, where, selectionArgs);
			break;
		case CONTACT_ID://����idɾ��
			//ȡ����ϵ�˵�id��Ϣ
			String contactID = uri.getPathSegments().get(1);
			//ɾ������where����������idֵΪcontactID�ļ�¼
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
		// TODO �Զ����ɵķ������
		int count;
		Log.e(TAG + "update", values.toString());
		Log.e(TAG + "update", uri.toString());
		Log.e(TAG + "update :match", "" + uriMatcher.match(uri));
		switch (uriMatcher.match(uri)){
			//����where�����������и���
			case CONTACTS:
				Log.e(TAG + "update", CONTACTS + "");
				count = contactsDB.update(CONTACTS_TABLE, values, where, selectionArgs);
				break;
			//����ָ����
			case CONTACT_ID:
				String contactID = uri.getPathSegments().get(1);
				Log.e(TAG + "update", contactID + "");
				count = contactsDB.update(CONTACTS_TABLE, values, ContactColumn._ID + "=" + contactID
						+ (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""), selectionArgs);
				break;
			default:
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		//֪ͨ����
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}
}
