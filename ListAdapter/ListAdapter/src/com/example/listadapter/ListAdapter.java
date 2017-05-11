package com.example.listadapter;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListAdapter extends ListActivity {
//ListAdapter是ListView和数据的适配器
	
	String[] weekStrings = new String[]{"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
	ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,weekStrings);
		this.setListAdapter(adapter);
		this.getListView().setOnItemSelectedListener(new OnItemSelectedListener(){		
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg3, long arg4) {
				// TODO 自动生成的方法存根
				ListAdapter.this.setTitle(((TextView) arg1).getText());
				
			}
			
			public void onNothingSelected(AdapterView<?> arg0){
				ListAdapter.this.setTitle("没有选择!!!");
			}

			
		});
		
	}


}
