package com.example.mycontact;

import android.provider.BaseColumns;

public class ContactColumn implements BaseColumns{
	public ContactColumn(){}
	//列名
	public static final String NAME="name";
	public static final String MOBILENUM = "mobileNumber";//移动电话
	public static final String HOMENUM = "homeNumber";	//座机
	public static final String ADDRESS = "address";		//地址
	public static final String EMAIL = "email";			//邮箱
	
	//列 索引值
	public static final int _ID_COLUMN = 0;
	public static final int NAME_COLUMN = 1;//姓名
	public static final int MOBILENUM_COLUMN = 2;//电话号码
	public static final int HOMENUM_COLUMN = 3;//座机电话号码
	public static final int ADDRESS_COLUMN = 4;//地址
	public static final int EAMIL_COLUMN = 5;//邮箱
	
	//查询结果
	public static final String[] PROJECTION ={
			_ID,
			NAME,
			MOBILENUM,
			HOMENUM,
			ADDRESS,
			EMAIL,
	};
}
