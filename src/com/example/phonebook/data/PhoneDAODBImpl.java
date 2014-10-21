package com.example.phonebook.data;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PhoneDAODBImpl implements PhoneDAO {

	Context context;
	public PhoneDAODBImpl(Context context)
	{
		this.context = context;
	}
	
	@Override
	public void add(Phone p) {
		// TODO Auto-generated method stub
		PhoneDBHelper helper = new PhoneDBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("Insert into contacts (UserName, Tel, Addr) values ('" + p.Name +  "', '" + p.Tel + "', '" + p.Addr + "')");
        db.close();
	}

	@Override
	public Phone[] getAll() {
		// TODO Auto-generated method stub
		ArrayList<Phone> list = new ArrayList();
		PhoneDBHelper helper = new PhoneDBHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * From contacts", null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
        	list.add(new Phone(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
        	cursor.moveToNext();
        }
        db.close();
        
        Phone[] rtValue = new Phone[list.size()];
        list.toArray(rtValue);
        return rtValue;
	}

	@Override
	public Phone getPhone(int ID) {
		PhoneDBHelper helper = new PhoneDBHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * From contacts where ID=" + ID, null);
        cursor.moveToFirst();
        Phone p = new Phone(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        db.close();
		return p;
	}

	@Override
	public void removeAll() {
		// TODO Auto-generated method stub
		PhoneDBHelper helper = new PhoneDBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from contacts");
        db.close();
	}

	@Override
	public void delete(int ID) {
		// TODO Auto-generated method stub
		PhoneDBHelper helper = new PhoneDBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from contacts where ID=" + ID);
        db.close();
	}

	@Override
	public Phone[] search(String keyword) {
		ArrayList<Phone> list = new ArrayList();
		PhoneDBHelper helper = new PhoneDBHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * From contacts where UserName like '%" + keyword +"%'", null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
        	list.add(new Phone(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
        	cursor.moveToNext();
        }
        db.close();
        
        Phone[] rtValue = new Phone[list.size()];
        list.toArray(rtValue);
        return rtValue;
	}

	@Override
	public void edit(Phone p) {
		// TODO Auto-generated method stub
		PhoneDBHelper helper = new PhoneDBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("Update contacts Set UserName = '" + p.Name + "', Tel = '" + p.Tel + "', Addr = '" + p.Addr + "' Where ID = " + p.ID);
        db.close();
	}

}
