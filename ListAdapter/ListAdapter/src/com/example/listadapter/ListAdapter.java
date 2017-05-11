package com.example.listadapter;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListAdapter extends ListActivity {
//ListAdapter��ListView�����ݵ�������
	
	String[] weekStrings = new String[]{"������","����һ","���ڶ�","������","������","������","������"};
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
				// TODO �Զ����ɵķ������
				ListAdapter.this.setTitle(((TextView) arg1).getText());
				
			}
			
			public void onNothingSelected(AdapterView<?> arg0){
				ListAdapter.this.setTitle("û��ѡ��!!!");
			}

			
		});
		
	}


}
