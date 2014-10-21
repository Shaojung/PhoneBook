package com.example.phonebook;

import com.example.phonebook.data.Phone;
import com.example.phonebook.data.PhoneDAO;
import com.example.phonebook.data.PhoneDAODBImpl;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends Activity {
	int ID;
	Context context;
	EditText ed1, ed2, ed3;
	Button btn1, btn2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		ID = getIntent().getIntExtra("ID", 0);
		context = this;
        PhoneDAO dao = new PhoneDAODBImpl(this);
        Phone p = dao.getPhone(ID);
        ed1 = (EditText) findViewById(R.id.editText1);
        ed2 = (EditText) findViewById(R.id.editText2);
        ed3 = (EditText) findViewById(R.id.editText3);
        
        ed1.setText(p.Name);
        ed2.setText(p.Tel);
        ed3.setText(p.Addr);
        
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        
        btn1.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}});
        btn2.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Phone p = new Phone(ID, ed1.getText().toString(), ed2.getText().toString(), ed3.getText().toString());
		        PhoneDAO dao = new PhoneDAODBImpl(context);
		        dao.edit(p);
		        finish();
			}});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
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
