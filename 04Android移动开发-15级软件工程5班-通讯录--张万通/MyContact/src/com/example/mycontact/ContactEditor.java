package com.example.mycontact;

//编辑界面
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactEditor extends Activity{
	//标志位常量
	private static final int state_edit = 0;
	private static final int state_insert = 1;
	
	private static final int revert_id = Menu.FIRST;
	private static final int discard_id = Menu.FIRST+1;
	private static final int delete_id = Menu.FIRST+2;
	
	
	private Cursor mCursor;
    private int mState;		//当前处于新建状态还是编辑状态的标志位变量
    private Uri mUri;
	//界面元素
    private EditText nameText;
    private EditText mobileText;
    private EditText homeText;
    private EditText addressText;
    private EditText emailText;
    //按键
    private Button okButton;
    private Button cancelButton;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		final Intent intent = getIntent();
		final String action = intent.getAction();//获得action，根据action设置编辑位state_edit = 0 编辑，state_insert = 1 插入
		//根据action的不同进行不同的操作
		//编辑联系人
		if(Intent.ACTION_EDIT.equals(action)){
			mState = state_edit;//mState=0；编辑
			mUri = intent.getData();
		}else if(Intent.ACTION_INSERT.equals(action)){
			//添加联系人
			mState = state_insert;//mState=1；插入
			mUri = getContentResolver().insert(intent.getData(),null);
			if(mUri == null){
				finish();
				return;
			}
			setResult(RESULT_OK,(new Intent()).setAction(mUri.toString()));	//设置界面
		}else{ 		//其他情况，退出
			finish();
			return;
		}
		setContentView(R.layout.editorcontacts);
		//初始化界面文本框
		nameText = (EditText)findViewById(R.id.ET_name);
		mobileText = (EditText) findViewById(R.id.ET_mobile);
		homeText = (EditText) findViewById(R.id.ET_home);
		addressText = (EditText) findViewById(R.id.ET_address);
		emailText = (EditText) findViewById(R.id.ET_email);
		//初始化按键
		okButton = (Button)findViewById(R.id.BTN_save);
		cancelButton = (Button)findViewById(R.id.BTN_cancel);
		//设置确定按键监听器
		okButton.setOnClickListener(new OnClickListener(){//实际是插入一条空信息，然后进行更新操作
			public void onClick(View v){
				String textname = nameText.getText().toString();
				String texrmphone = mobileText.getText().toString();
				String texthome = homeText.getText().toString();
				if(textname.length() == 0){
					//姓名文本框里为空
					setResult(RESULT_CANCELED);
					Toast.makeText(ContactEditor.this, "姓名不能为空", Toast.LENGTH_SHORT).show();
					//finish();
				}else if(texrmphone.length() == 0 && texthome.length() == 0){
					Toast.makeText(ContactEditor.this, "电话或手机不能为空", Toast.LENGTH_SHORT).show();
				}else{
					//更新数据
					updateContact();
				}
			}
		});
		//设置取消按钮监听器
		cancelButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				//不添加记录，也不保存记录
				if(mState == state_insert){
					setResult(RESULT_CANCELED);
					deleteContact();
					finish();
				}else{
					finish();
				}
			}
		});
		//获得联系人保存的原始信息，用于编辑状态的数据
		mCursor = managedQuery(mUri, ContactColumn.PROJECTION, null, null, null);
		mCursor.moveToFirst();
		if(mCursor != null){
			//读取并显示联系人信息
			mCursor.moveToFirst();
			if(mState == state_edit){
				setTitle(getText(R.string.editor_user));
			}else if(mState == state_insert){
				setTitle(getText(R.string.add_user));
			}
			String name = mCursor.getString(ContactColumn.NAME_COLUMN);
			String moblie = mCursor.getString(ContactColumn.MOBILENUM_COLUMN);
			String home = mCursor.getString(ContactColumn.HOMENUM_COLUMN);
			String address = mCursor.getString(ContactColumn.ADDRESS_COLUMN);
			String email = mCursor.getString(ContactColumn.EAMIL_COLUMN);
			//显示信息
			nameText.setText(name);
			mobileText.setText(moblie);
			homeText.setText(home);
			addressText.setText(address);
			emailText.setText(email);
		}else{
			setTitle("错误信息");
		}
	}
	//菜单选项
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		if(mState == state_edit){//处于编辑状态
			//返回按钮
			menu.add(0, revert_id, 0, R.string.revert).setShortcut('0', 'r').setIcon(R.drawable.listuser);
			//删除联系人
			menu.add(0, delete_id, 0, R.string.delete_user).setShortcut('0', 'f').setIcon(R.drawable.remove);			
		}else{//处于插入状态
			//返回按钮
			menu.add(0, discard_id, 0, R.string.revert).setShortcut('0', 'd').setIcon(R.drawable.listuser);
		}
		return true;
	}
	//菜单处理
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		//删除联系人
		case delete_id:
			deleteContact();
			finish();
			break;
		//删除刚创建的新联系人
		case discard_id:
			cancelContact();
			finish();
			break;
		case revert_id:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//删除联系人信息
	private void deleteContact(){
		if(mCursor != null){
			mCursor.close();
			mCursor = null;
			getContentResolver().delete(mUri, null, null);//根据mUri地址删除
			nameText.setText("");
		}
	}
	//丢弃信息
	private void cancelContact(){
		if(mCursor != null){
			deleteContact();
		}
		setResult(RESULT_CANCELED);
		finish();
	}
	//更新信息
	private void updateContact(){
		if(mCursor != null){
			mCursor.close();
			mCursor=null;
			ContentValues values = new ContentValues();
			values.put(ContactColumn.NAME, nameText.getText().toString());
			values.put(ContactColumn.MOBILENUM, mobileText.getText().toString());
			values.put(ContactColumn.HOMENUM, homeText.getText().toString());
			values.put(ContactColumn.ADDRESS, addressText.getText().toString());
			values.put(ContactColumn.EMAIL, emailText.getText().toString());
			//更新数据
			getContentResolver().update(mUri, values, null, null);//更新到数据库中
		}
		setResult(RESULT_CANCELED);
		finish();
	}
}
