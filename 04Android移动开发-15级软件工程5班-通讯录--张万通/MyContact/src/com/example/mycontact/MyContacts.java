package com.example.mycontact;

import android.app.ListActivity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MyContacts extends ListActivity {

	private static final int AddContact_ID = Menu.FIRST;//ID���õ���С��ֵ,��Ȼ��ʹ�������ĳ������Menu.FIRSTҲ����Ӱ��ʵ�ʵĽ������Ҫ�����ǳ����װ�ı�������������ռ�ڴ棬������Ŀ죬�����׳���
	private static final int DELEContact_ID = Menu.FIRST+2;
	private static final int EXITContact_ID = Menu.FIRST+3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setDefaultKeyMode(DEFAULT_KEYS_SHORTCUT);//��Ĭ�ϵİ���������Ϊ�˵���ݼ����д���Ҳ����˵���û�����Ҫ����menu�������Ϳ��Դ���˵���ݼ�
		//Ϊintent������
		Intent intent = getIntent();//������Ŀ�а�����ԭʼintent������������Intent intent=getIntent();�ǽ�����������intent��ֵ��һ��Intent���͵ı���intent�Ͼ��У���һ��intent�Ǳ�����е�һ��intent����������Ǹ�����������Ҫ��ֵ
		if(intent.getData() == null){
			intent.setData(ContactsProvider.CONTENT_URI);//�ŵ������ݿ�ĵ�ַ
		}
		//���ò˵����������
		getListView().setOnCreateContextMenuListener(this);
		//��ѯ�����������ϵ�˵�����
		Cursor cursor = managedQuery(getIntent().getData(), ContactColumn.PROJECTION, null, null, null);//ͨ�������ַ��ѯ���ݿ⣬�ҵ�ÿ�����ݣ���������α�
		
		//ע��ÿ���б��ʾ��ʽ ������ + �ƶ��绰
		//һ���򵥵��α�������
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, new String[] {ContactColumn.NAME, ContactColumn.MOBILENUM},new int[] {android.R.id.text1, android.R.id.text2});
		setListAdapter(adapter);//Ϊ����б��������
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		super.onCreateOptionsMenu(menu);
		//�����ϵ��
		menu.add(0,AddContact_ID,0,R.string.add_user);//.setShortcut('3','a').setIcon(R.drawable.add);
		//.setShortcut('3','a')���ÿ�ݼ�
		//.setIcon(R.drawable.add)����ͼ��
		//�˳�����
		menu.add(0, EXITContact_ID, 0, R.string.exit);//.setShortcut('4', 'd').setIcon(R.drawable.exit);
		//getMenuInflater().inflate(R.menu.my_contacts, menu);
		return true;
	}
//����˵�������������˵�ʱ���еĲ���
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch(id){
		case AddContact_ID:
			//Intent intent = new Intent();
			//intent.setClass(MyContacts.this, ContactEditor.class);
			//startActivity(intent);
			startActivity(new Intent(Intent.ACTION_INSERT, getIntent().getData()));//��������idɾ����ϵ��
			//MyContacts.this.finish();
            return true;
		case EXITContact_ID:
			this.finish();
            return true;
		}
		return super.onOptionsItemSelected(item);
	}
	//��̬�˵�����
    //�����Ĭ�ϲ���Ҳ���������ﴦ��
	protected void onListItemClick(ListView l,View v,int position,long id){
		Uri uri = ContentUris.withAppendedId(getIntent().getData(), id);
		//�鿴��ϵ��
    	startActivity(new Intent(Intent.ACTION_VIEW, uri));
	}
	
	//���������Ĳ˵�
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo){
		AdapterView.AdapterContextMenuInfo info;
		try{
			info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		}catch(ClassCastException e){
			e.printStackTrace();
			return;
		}
		//�õ�������������
		Cursor cursor = (Cursor) getListAdapter().getItem(info.position);
		if(cursor == null){
			return;
		}
		
		menu.setHeaderTitle(cursor.getString(1));
		//���ɾ���˵�
		menu.add(0, DELEContact_ID, 0, R.string.delete_user);
	}
	
	//�����б����ĺ���
	@Override
	public boolean onContextItemSelected(MenuItem item){
		AdapterView.AdapterContextMenuInfo info;
		try{
			//���ѡ�е���Ŀ��Ϣ
			info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		}catch (ClassCastException e){
			e.printStackTrace();
			return false;
		}
		switch (item.getItemId()){
			//ɾ������
			case DELEContact_ID:
				//ɾ��һ����¼
				Uri noteUri = ContentUris.withAppendedId(getIntent().getData(), info.id);
				getContentResolver().delete(noteUri, null, null);
				return true;
		}
		return false;
	}
}
