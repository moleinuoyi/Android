package com.example.datepickerdialog;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class DatePickerDialogExample extends Activity {
	private TextView showdate;
	private Button setdate;
	private int year,month,day;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		showdate = (TextView) findViewById(R.id.showdate);
		setdate = (Button) findViewById(R.id.setdate);
		
		Calendar c = Calendar.getInstance(Locale.CHINA);
		Date mydate = new Date();
		c.setTime(mydate);
		
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		
		showdate.setText("今年是："+year+"年"+(month+1)+"月"+day+"日");
		
		setdate.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				
				DatePickerDialog my_datePickerDialog = new DatePickerDialog(DatePickerDialogExample.this, Datelistener, year, month, day);
				my_datePickerDialog.setCancelable(true);
				my_datePickerDialog.setCanceledOnTouchOutside(true);
				
				my_datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "设置", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO 自动生成的方法存根
						//点击设置按钮事件
					}
				});
				my_datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO 自动生成的方法存根
						//点击取消按钮事件
					}
				});
				DatePicker datepicker = my_datePickerDialog.getDatePicker();
				Date date = new Date();
				long time = date.getTime();
				//datepicker.setMinDate(time);
				datepicker.setMaxDate(time);//设置最大时间为今天
				my_datePickerDialog.show();
			}
		});
	}
	
	private DatePickerDialog.OnDateSetListener Datelistener = new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int y, int m, int d) {
			// TODO 自动生成的方法存根
			year = y;
			month = m;
			day = d;
			updateDate();
		}
		private void updateDate(){
			showdate.setText("今年是："+year+"年"+(month+1)+"月"+day+"日");
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.date_picker_dialog_example, menu);
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
