package com.example.phonebook.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class PhoneDBHelper extends SQLiteOpenHelper {

	final static String DB_NAME = "phone";
	final static int DB_VERSION = 1;
	
	public PhoneDBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("Create table contacts (ID integer Primary Key AUTOINCREMENT, UserName varchar(50), Tel varchar(50), Addr varchar(50))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
