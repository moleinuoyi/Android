package com.example.progressdialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class DialogProgress extends Activity {
	
	private ProgressBar firstBar;
	private ProgressBar secondBar;
	private Button myButton = null;
	private int i=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mian);
		firstBar = (ProgressBar) findViewById(R.id.fristBar);
		secondBar = (ProgressBar) findViewById(R.id.secondBar);
		myButton = (Button) findViewById(R.id.myButton);
		myButton.setOnClickListener(new ButtonListener());
	}

	class ButtonListener implements OnClickListener, android.view.View.OnClickListener{
		
		public void onClick(View v){
			if(i==0){
				firstBar.setVisibility(View.VISIBLE);
				secondBar.setVisibility(View.VISIBLE);
				firstBar.setMax(150);
			}else if(i < firstBar.getMax()){
				firstBar.setProgress(i);
				firstBar.setSecondaryProgress(i+10);
			}else{
				firstBar.setVisibility(View.GONE);
				secondBar.setVisibility(View.GONE);
				Toast.makeText(DialogProgress.this, "加载完成", Toast.LENGTH_SHORT).show();
				i=0;
			}
			i+=10;
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO 自动生成的方法存根
			
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dialog_progress, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
