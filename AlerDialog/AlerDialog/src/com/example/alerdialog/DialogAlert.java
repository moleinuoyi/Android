package com.example.alerdialog;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.Toast;

public class DialogAlert extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Builder dialog = new AlertDialog.Builder(DialogAlert.this);
		dialog.create().setIcon(android.R.drawable.ic_dialog_info);
		dialog.setTitle("欢迎！！！");
		dialog.setMessage("欢迎使用本程序！");
		dialog.setPositiveButton("确定", new OnClickListener(){
			public void onClick(DialogInterface a0,int a1){
				Toast.makeText(DialogAlert.this, "欢迎使用本程序", Toast.LENGTH_SHORT).show();
			}
		});
		dialog.setNegativeButton("退出", new OnClickListener(){
			public void onClick(DialogInterface a0,int a1){
				finish();
			}
		});
		dialog.create();
		dialog.show();
		try{
			//
			ViewConfiguration mconfig = ViewConfiguration.get(this);
			Field menuKeyField;
			menuKeyField= ViewConfiguration.class.getDeclaredField("sHasPermanentMenukey");
			if(menuKeyField != null){
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(mconfig, false);
			}
		}catch(NoSuchFieldException e){
			e.printStackTrace();
		}catch(IllegalAccessException e){
			e.printStackTrace();
		}catch(IllegalArgumentException e){
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater=getMenuInflater();
		inflater.inflate(R.menu.dialog_alert, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch(item.getItemId()){
		case R.id.action_calendar:
			SimpleDateFormat formatter = new SimpleDateFormat("yyy年MM月dd日HH:mm:ss");
			Date curDate = new Date(System.currentTimeMillis());
			String str = formatter.format(curDate);
			Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
