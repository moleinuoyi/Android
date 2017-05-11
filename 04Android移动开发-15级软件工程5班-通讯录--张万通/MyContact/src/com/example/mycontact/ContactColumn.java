package com.example.mycontact;

import android.provider.BaseColumns;

public class ContactColumn implements BaseColumns{
	public ContactColumn(){}
	//����
	public static final String NAME="name";
	public static final String MOBILENUM = "mobileNumber";//�ƶ��绰
	public static final String HOMENUM = "homeNumber";	//����
	public static final String ADDRESS = "address";		//��ַ
	public static final String EMAIL = "email";			//����
	
	//�� ����ֵ
	public static final int _ID_COLUMN = 0;
	public static final int NAME_COLUMN = 1;//����
	public static final int MOBILENUM_COLUMN = 2;//�绰����
	public static final int HOMENUM_COLUMN = 3;//�����绰����
	public static final int ADDRESS_COLUMN = 4;//��ַ
	public static final int EAMIL_COLUMN = 5;//����
	
	//��ѯ���
	public static final String[] PROJECTION ={
			_ID,
			NAME,
			MOBILENUM,
			HOMENUM,
			ADDRESS,
			EMAIL,
	};
}
