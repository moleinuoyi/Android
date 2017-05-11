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

	private static final int AddContact_ID = Menu.FIRST;//ID设置的最小数值,当然即使用其他的常量替代Menu.FIRST也不会影响实际的结果，主要是这是程序封装的变量，用起来不占内存，程序读的快，不容易出错
	private static final int DELEContact_ID = Menu.FIRST+2;
	private static final int EXITContact_ID = Menu.FIRST+3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setDefaultKeyMode(DEFAULT_KEYS_SHORTCUT);//将默认的按键输入作为菜单快捷键进行处理。也就是说，用户不需要按下menu按键，就可以处理菜单快捷键
		//为intent绑定数据
		Intent intent = getIntent();//将该项目中包含的原始intent检索出来，而Intent intent=getIntent();是将检索出来的intent赋值给一个Intent类型的变量intent上句中，第一个intent是本身就有的一个intent，而后面的是个变量名，需要赋值
		if(intent.getData() == null){
			intent.setData(ContactsProvider.CONTENT_URI);//放的是数据库的地址
		}
		//设置菜单项长按监听器
		getListView().setOnCreateContextMenuListener(this);
		//查询，获得所有联系人的数据
		Cursor cursor = managedQuery(getIntent().getData(), ContactColumn.PROJECTION, null, null, null);//通过这个地址查询数据库，找到每个数据，获得他的游标
		
		//注册每个列表表示形式 ：姓名 + 移动电话
		//一个简单的游标适配器
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, new String[] {ContactColumn.NAME, ContactColumn.MOBILENUM},new int[] {android.R.id.text1, android.R.id.text2});
		setListAdapter(adapter);//为这个列表绑定适配器
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		super.onCreateOptionsMenu(menu);
		//添加联系人
		menu.add(0,AddContact_ID,0,R.string.add_user);//.setShortcut('3','a').setIcon(R.drawable.add);
		//.setShortcut('3','a')设置快捷键
		//.setIcon(R.drawable.add)设置图标
		//退出程序
		menu.add(0, EXITContact_ID, 0, R.string.exit);//.setShortcut('4', 'd').setIcon(R.drawable.exit);
		//getMenuInflater().inflate(R.menu.my_contacts, menu);
		return true;
	}
//处理菜单操作，当点击菜单时进行的操作
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
			startActivity(new Intent(Intent.ACTION_INSERT, getIntent().getData()));//根据他的id删除联系人
			//MyContacts.this.finish();
            return true;
		case EXITContact_ID:
			this.finish();
            return true;
		}
		return super.onOptionsItemSelected(item);
	}
	//动态菜单处理
    //点击的默认操作也可以在这里处理
	protected void onListItemClick(ListView l,View v,int position,long id){
		Uri uri = ContentUris.withAppendedId(getIntent().getData(), id);
		//查看联系人
    	startActivity(new Intent(Intent.ACTION_VIEW, uri));
	}
	
	//长按触发的菜单
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo){
		AdapterView.AdapterContextMenuInfo info;
		try{
			info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		}catch(ClassCastException e){
			e.printStackTrace();
			return;
		}
		//得到长按的数据项
		Cursor cursor = (Cursor) getListAdapter().getItem(info.position);
		if(cursor == null){
			return;
		}
		
		menu.setHeaderTitle(cursor.getString(1));
		//添加删除菜单
		menu.add(0, DELEContact_ID, 0, R.string.delete_user);
	}
	
	//长按列表触发的函数
	@Override
	public boolean onContextItemSelected(MenuItem item){
		AdapterView.AdapterContextMenuInfo info;
		try{
			//获得选中的项目信息
			info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		}catch (ClassCastException e){
			e.printStackTrace();
			return false;
		}
		switch (item.getItemId()){
			//删除操作
			case DELEContact_ID:
				//删除一条记录
				Uri noteUri = ContentUris.withAppendedId(getIntent().getData(), info.id);
				getContentResolver().delete(noteUri, null, null);
				return true;
		}
		return false;
	}
}
