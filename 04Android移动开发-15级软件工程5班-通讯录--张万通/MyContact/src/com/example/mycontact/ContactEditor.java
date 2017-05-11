package com.example.mycontact;

//�༭����
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
	//��־λ����
	private static final int state_edit = 0;
	private static final int state_insert = 1;
	
	private static final int revert_id = Menu.FIRST;
	private static final int discard_id = Menu.FIRST+1;
	private static final int delete_id = Menu.FIRST+2;
	
	
	private Cursor mCursor;
    private int mState;		//��ǰ�����½�״̬���Ǳ༭״̬�ı�־λ����
    private Uri mUri;
	//����Ԫ��
    private EditText nameText;
    private EditText mobileText;
    private EditText homeText;
    private EditText addressText;
    private EditText emailText;
    //����
    private Button okButton;
    private Button cancelButton;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		final Intent intent = getIntent();
		final String action = intent.getAction();//���action������action���ñ༭λstate_edit = 0 �༭��state_insert = 1 ����
		//����action�Ĳ�ͬ���в�ͬ�Ĳ���
		//�༭��ϵ��
		if(Intent.ACTION_EDIT.equals(action)){
			mState = state_edit;//mState=0���༭
			mUri = intent.getData();
		}else if(Intent.ACTION_INSERT.equals(action)){
			//�����ϵ��
			mState = state_insert;//mState=1������
			mUri = getContentResolver().insert(intent.getData(),null);
			if(mUri == null){
				finish();
				return;
			}
			setResult(RESULT_OK,(new Intent()).setAction(mUri.toString()));	//���ý���
		}else{ 		//����������˳�
			finish();
			return;
		}
		setContentView(R.layout.editorcontacts);
		//��ʼ�������ı���
		nameText = (EditText)findViewById(R.id.ET_name);
		mobileText = (EditText) findViewById(R.id.ET_mobile);
		homeText = (EditText) findViewById(R.id.ET_home);
		addressText = (EditText) findViewById(R.id.ET_address);
		emailText = (EditText) findViewById(R.id.ET_email);
		//��ʼ������
		okButton = (Button)findViewById(R.id.BTN_save);
		cancelButton = (Button)findViewById(R.id.BTN_cancel);
		//����ȷ������������
		okButton.setOnClickListener(new OnClickListener(){//ʵ���ǲ���һ������Ϣ��Ȼ����и��²���
			public void onClick(View v){
				String textname = nameText.getText().toString();
				String texrmphone = mobileText.getText().toString();
				String texthome = homeText.getText().toString();
				if(textname.length() == 0){
					//�����ı�����Ϊ��
					setResult(RESULT_CANCELED);
					Toast.makeText(ContactEditor.this, "��������Ϊ��", Toast.LENGTH_SHORT).show();
					//finish();
				}else if(texrmphone.length() == 0 && texthome.length() == 0){
					Toast.makeText(ContactEditor.this, "�绰���ֻ�����Ϊ��", Toast.LENGTH_SHORT).show();
				}else{
					//��������
					updateContact();
				}
			}
		});
		//����ȡ����ť������
		cancelButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				//����Ӽ�¼��Ҳ�������¼
				if(mState == state_insert){
					setResult(RESULT_CANCELED);
					deleteContact();
					finish();
				}else{
					finish();
				}
			}
		});
		//�����ϵ�˱����ԭʼ��Ϣ�����ڱ༭״̬������
		mCursor = managedQuery(mUri, ContactColumn.PROJECTION, null, null, null);
		mCursor.moveToFirst();
		if(mCursor != null){
			//��ȡ����ʾ��ϵ����Ϣ
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
			//��ʾ��Ϣ
			nameText.setText(name);
			mobileText.setText(moblie);
			homeText.setText(home);
			addressText.setText(address);
			emailText.setText(email);
		}else{
			setTitle("������Ϣ");
		}
	}
	//�˵�ѡ��
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		if(mState == state_edit){//���ڱ༭״̬
			//���ذ�ť
			menu.add(0, revert_id, 0, R.string.revert).setShortcut('0', 'r').setIcon(R.drawable.listuser);
			//ɾ����ϵ��
			menu.add(0, delete_id, 0, R.string.delete_user).setShortcut('0', 'f').setIcon(R.drawable.remove);			
		}else{//���ڲ���״̬
			//���ذ�ť
			menu.add(0, discard_id, 0, R.string.revert).setShortcut('0', 'd').setIcon(R.drawable.listuser);
		}
		return true;
	}
	//�˵�����
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		//ɾ����ϵ��
		case delete_id:
			deleteContact();
			finish();
			break;
		//ɾ���մ���������ϵ��
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
	
	//ɾ����ϵ����Ϣ
	private void deleteContact(){
		if(mCursor != null){
			mCursor.close();
			mCursor = null;
			getContentResolver().delete(mUri, null, null);//����mUri��ַɾ��
			nameText.setText("");
		}
	}
	//������Ϣ
	private void cancelContact(){
		if(mCursor != null){
			deleteContact();
		}
		setResult(RESULT_CANCELED);
		finish();
	}
	//������Ϣ
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
			//��������
			getContentResolver().update(mUri, values, null, null);//���µ����ݿ���
		}
		setResult(RESULT_CANCELED);
		finish();
	}
}
