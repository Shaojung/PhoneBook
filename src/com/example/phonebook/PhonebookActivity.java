package com.example.phonebook;

import java.io.File;
import java.util.ArrayList;

import com.example.phonebook.data.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class PhonebookActivity extends Activity {

	Phone[] data;
	ArrayList<Boolean> listShow;
	Context context;
	 ListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonebook);
        context = this;
        listShow = new ArrayList();
        // MyTest();

    }
    
    @Override
    protected void onResume()
    {
    	super.onResume();
        PhoneDAO dao = new PhoneDAODBImpl(this);
        data = dao.getAll();
        for (int i=0;i<data.length;i++)
        {
        	listShow.add(false);
        }
        ListView lv = (ListView) findViewById(R.id.listView1);
        adapter = new ListAdapter(this, data);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new ListView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				int ID = data[position].ID;
				Intent it = new Intent(PhonebookActivity.this, DetailActivity.class);
				it.putExtra("ID", ID);
				startActivity(it);
				// Toast.makeText(context, "" + position, Toast.LENGTH_LONG).show();
			}});

    }

    void MyTest()
    {
        PhoneDAO dao = new PhoneDAODBImpl(this);
        dao.add(new Phone(0, "Bob", "123", "AABB"));
        dao.add(new Phone(0, "John", "222", "BBBB"));
        dao.add(new Phone(0, "Mary", "333", "CCCC"));
        data = dao.getAll();
        for (int i=0;i<data.length;i++)
        {
        	Log.d("DB", data[i].Name + "," + data[i].Tel);
        }
        // dao.removeAll();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.phonebook, menu);
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
        
        if (id == R.id.action_add)
        {
        	Intent it = new Intent(PhonebookActivity.this, AddDataActivity.class);
        	startActivity(it);
        }
        
        if (id == R.id.action_delete)
        {
        	AlertDialog.Builder alert = new AlertDialog.Builder(context);
        	alert.setTitle("請確認刪除?");

        	alert.setPositiveButton("確認", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					PhoneDAO dao = new PhoneDAODBImpl(context);
			        Log.d("PHONE", "Befor for loop");
					for (int i=0;i<listShow.size();i++)
					{
						if (listShow.get(i))
						{   
					        dao.delete(data[i].ID);
						}
					}
			        Log.d("PHONE", "Before data re-getAll");
			        data = dao.getAll();
			        Log.d("PHONE", "Before listShow Clear");
			        listShow.clear();
			        for (int i=0;i<data.length;i++)
			        {
			        	listShow.add(false);
			        }
			        Log.d("PHONE", "listShow Size:" + listShow.size() + ", data size:" + data.length);
			        adapter.data = data;
			        adapter.notifyDataSetChanged();
					
				}});
        	alert.setNegativeButton("取消", null);
        	alert.show();
        	
        }
        
        if (id == R.id.action_search)
        {
        	
        	AlertDialog.Builder alert = new AlertDialog.Builder(this);
        	alert.setTitle("請輸入搜尋關鍵字:");
            final EditText input = new EditText(this);
            alert.setView(input);        	
        	alert.setPositiveButton("確定", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
		            PhoneDAO dao = new PhoneDAODBImpl(context);
		            data = dao.search(input.getText().toString());
		            for (int i=0;i<data.length;i++)
		            {
		            	listShow.add(false);
		            }
		            adapter.data = data;		            
		        	adapter.notifyDataSetChanged();
				}});
        	alert.setNegativeButton("取消", null);
        	alert.show();
        	
	
        }
        return super.onOptionsItemSelected(item);
    }
    
    class ListAdapter extends BaseAdapter
    {
    	Phone[] data;
    	Activity act;
    	private LayoutInflater inflater = null;
    	public ListAdapter(Activity a, Phone[] data)
    	{
    		this.act = a;
    		this.data = data;
    		inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	}
    	
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.length;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			View v = inflater.inflate(R.layout.listitem, null);
			TextView tv = (TextView) v.findViewById(R.id.tvName);
			tv.setText(data[position].Name);
			TextView tv1 = (TextView) v.findViewById(R.id.tvTel);
			tv1.setText(data[position].Tel);

			ImageView iv = (ImageView) v.findViewById(R.id.imageView1);
	        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "p" + data[position].ID + ".jpg");
	        
	        if (file.exists())
	        {
	        	Bitmap bm = BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + "p" + data[position].ID + ".jpg");
	        	iv.setImageBitmap(bm);
	        }
	        else
	        {
				if (data[position].Name.equals("Mary"))
				{
					iv.setImageResource(R.drawable.girl);	
				}
				else
				{
					iv.setImageResource(R.drawable.boy);
				}
	        }
			
			CheckBox chk = (CheckBox) v.findViewById(R.id.checkBox1);
			chk.setChecked(listShow.get(position));
			chk.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					// TODO Auto-generated method stub
					listShow.set(position, arg1);
				}});
			
			return v;
		}
    	
    }
}
