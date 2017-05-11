package com.example.handlermessageproess;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Handlermessageprogess extends Activity {
	
	static int i = 0;

	Button start = null;
	Button end = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		start = (Button)findViewById(R.id.btn_start);
		start.setOnClickListener(new StartButtonListener());
		end = (Button)findViewById(R.id.btn_end);
		end.setOnClickListener(new EndButtonListener());

	}
	//创建一个Handler对象
	Handler handler = new Handler();
	
	class StartButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			handler.post(updateThread);
		}
		
	}
		
	class EndButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			handler.removeCallbacks(updateThread);
		}	
	}
	
	Runnable updateThread = new Runnable(){
		
		@Override
		public void run(){
			System.out.println("hello！");
			i+=1;
			handler.postDelayed(updateThread, 2000);
		}
	};

}
