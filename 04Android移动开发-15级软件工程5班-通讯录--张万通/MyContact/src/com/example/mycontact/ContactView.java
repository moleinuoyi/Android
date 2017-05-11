package com.example.mycontact;
//�鿴��ϵ��
import android.app.Activity;
import android.content.ClipData.Item;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TextView;

public class ContactView extends Activity{
	
	//����
	private TextView mTextViewName;
	private TextView mTextViewMobile;//�ֻ�
	private TextView mTextViewHome;//�����绰
	private TextView mTextViewAddress;//��ַ
	private TextView mTextViewEmail;//����
	
	private Cursor mCursor;
	private Uri mUri;
	
	//���ò˵������
	private static final int revert_id = Menu.FIRST;
	private static final int delete_id = Menu.FIRST+1;
	private static final int editor_id = Menu.FIRST+2;
	private static final int call_id = Menu.FIRST+3;
	private static final int sendsms_id = Menu.FIRST+4;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		mUri = getIntent().getData();
		this.setContentView(R.layout.viewuser);
	
		mTextViewName = (TextView) findViewById(R.id.TV_name);
		mTextViewMobile = (TextView) findViewById(R.id.TV_mobile);
		mTextViewHome = (TextView) findViewById(R.id.TV_home);
		mTextViewAddress = (TextView) findViewById(R.id.TV_address);
		mTextViewEmail = (TextView) findViewById(R.id.TextView_Email);		
		//��ò�����ԭʼ��ϵ����Ϣ
		mCursor = managedQuery(mUri, ContactColumn.PROJECTION, null, null, null);
		mCursor.moveToFirst();//����ƶ�����һ��
		if(mCursor != null){
			//��ȡ����ʾ��ϵ��
			mCursor.moveToFirst();
			
			mTextViewName.setText(mCursor.getString(ContactColumn.NAME_COLUMN));
			mTextViewMobile.setText(mCursor.getString(ContactColumn.MOBILENUM_COLUMN));
			mTextViewHome.setText(mCursor.getString(ContactColumn.HOMENUM_COLUMN));
			mTextViewAddress.setText(mCursor.getString(ContactColumn.ADDRESS_COLUMN));
			mTextViewEmail.setText(mCursor.getString(ContactColumn.EAMIL_COLUMN));
		}else{
			setTitle("������Ϣ");
		}	
	}
	//��Ӳ˵�
	 public boolean onCreateOptionsMenu(Menu menu){
			super.onCreateOptionsMenu(menu);
			//����
			menu.add(0, revert_id, 0, R.string.revert);//.setShortcut('0', 'r').setIcon(R.drawable.listuser);
			//ɾ����ϵ��
			menu.add(0, delete_id, 0, R.string.delete_user);//.setShortcut('0', 'd').setIcon(R.drawable.remove);
			//�༭��ϵ��
			menu.add(0, editor_id, 0, R.string.editor_user);//.setShortcut('0', 'd').setIcon(R.drawable.edituser);
			//�����û�
			menu.add(0, call_id, 0, R.string.call_user).setShortcut('0', 'd').setIcon(R.drawable.calluser)
					.setTitle(this.getResources().getString(R.string.call_user)+mTextViewName.getText());
			//���Ͷ���
			menu.add(0, sendsms_id, 0, R.string.sendsms_user).setShortcut('0', 'd').setIcon(R.drawable.sendsms)
			.setTitle(this.getResources().getString(R.string.sendsms_user)+mTextViewName.getText());
			return true;
		}
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		//ɾ��
		case delete_id:
			deleteContact();
			finish();
			break;
		//���ذ�ť
		case revert_id:
			setResult(RESULT_CANCELED);
			finish();
			break;
		//�༭��ϵ��
		case editor_id:
			startActivity(new Intent(Intent.ACTION_EDIT, mUri)); 
			finish();
			break;
		//������ϵ��
		case call_id:
			 Intent call = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+mTextViewMobile.getText()));
		     startActivity(call);
			 break;
		//�����Ÿ���ϵ��
		case sendsms_id:
			 Intent sms = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:"+mTextViewMobile.getText()));
		     startActivity(sms);
			 break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	// ɾ����ϵ����Ϣ
	private void deleteContact(){
		if (mCursor != null){
			mCursor.close();
			mCursor = null;
			getContentResolver().delete(mUri, null, null);
			setResult(RESULT_CANCELED);
		}
	}
}

