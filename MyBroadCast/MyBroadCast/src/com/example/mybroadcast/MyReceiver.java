package com.example.mybroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {//Context������
		// TODO �Զ����ɵķ������
		Toast.makeText(context, "���ճɹ���", Toast.LENGTH_SHORT).show();
	}
	
}
