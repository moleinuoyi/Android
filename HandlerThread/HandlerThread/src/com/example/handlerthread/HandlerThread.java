package com.example.handlerthread;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

public class HandlerThread extends Activity {
	
	ProgressBar bar = null;
	Button start = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		bar = (ProgressBar)findViewById(R.id.bar);
		start = (Button)findViewById(R.id.btn_start);
		start.setOnClickListener(new startButtonListener());
		
	}

	class startButtonListener implements OnClickListener{
		
		@Override
		public void onClick(View v) {
			// TODO �Զ����ɵķ������
			bar.setVisibility(View.VISIBLE);
			updateBarHandler.post(updateThread);
		}
	}
	//ʹ�������ڲ�������дHandler���е�handleMessage����
	Handler updateBarHandler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			bar.setProgress(msg.arg1);
			updateBarHandler.post(updateThread);
		}
	};
	//�߳��࣬����ʹ�������ڲ���ķ�ʽ��������
	Runnable updateThread = new Runnable(){
		int i = 0;
		@Override
		public void run(){
			System.out.println("Begin Thread");
			i = i+1;
			//�õ�һ����Ϣ����Message������Android����ϵͳ�ṩ
			Message msg = updateBarHandler.obtainMessage();
			msg.arg1 = i;
			try{
				Thread.sleep(100);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			updateBarHandler.sendMessage(msg);
			if(i == 100){
				updateBarHandler.removeCallbacks(updateThread);
				bar.setVisibility(View.GONE);
			}
		}
	};
}
