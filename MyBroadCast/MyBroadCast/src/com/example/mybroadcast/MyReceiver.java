package com.example.mybroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {//Context上下文
		// TODO 自动生成的方法存根
		Toast.makeText(context, "接收成功！", Toast.LENGTH_SHORT).show();
	}
	
}
