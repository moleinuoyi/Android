package com.example.mybroadcast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyBroadCastActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_broad_cast);
		
		Button btn=(Button) findViewById(R.id.button);
		final Intent intent=new Intent("android.app.action.ACTION_PASSWORD_CHANGED");
		btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				sendBroadcast(intent);
			}
			
		});
	}
}
