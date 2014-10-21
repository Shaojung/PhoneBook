package com.example.phonebook;

import com.example.phonebook.data.Phone;
import com.example.phonebook.data.PhoneDAO;
import com.example.phonebook.data.PhoneDAODBImpl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailActivity extends Activity {
	TextView tv1, tv2, tv3;
	int ID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		Intent it = getIntent();
		ID = it.getIntExtra("ID", 0);

	}

	@Override
	protected void onResume()
	{
		super.onResume();
        PhoneDAO dao = new PhoneDAODBImpl(this);
        Phone p = dao.getPhone(ID);
        
        tv1 = (TextView) findViewById(R.id.textView1);
        tv2 = (TextView) findViewById(R.id.textView2);
        tv3 = (TextView) findViewById(R.id.textView3);
        tv1.setText(p.Name);
        tv2.setText(p.Tel);
        tv3.setText(p.Addr);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail, menu);
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
		if (id == R.id.action_edit)
		{
			Intent i = new Intent(DetailActivity.this, EditActivity.class);
			i.putExtra("ID", ID);
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}
}
