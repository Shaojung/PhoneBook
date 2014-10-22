package com.example.phonebook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.phonebook.data.Phone;
import com.example.phonebook.data.PhoneDAO;
import com.example.phonebook.data.PhoneDAODBImpl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Files;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AddDataActivity extends Activity {
	Button btnAdd, btnCancel, btnPhoto;
	EditText ed1, ed2, ed3;
	Context context;
	ImageView img;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_data);
		context = this;
		btnAdd = (Button) findViewById(R.id.button2);
		btnCancel = (Button) findViewById(R.id.button1);
		btnPhoto = (Button) findViewById(R.id.button3);
		
		ed1 = (EditText) findViewById(R.id.editText1);
		ed2 = (EditText) findViewById(R.id.editText2);
		ed3 = (EditText) findViewById(R.id.editText3);
		
		img = (ImageView) findViewById(R.id.imageView1);
		
		btnAdd.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
		        PhoneDAO dao = new PhoneDAODBImpl(context);
		        int i = dao.add(new Phone(0, ed1.getText().toString(), ed2.getText().toString(), ed3.getText().toString()));
		        
		        Log.d("DB", "rowId:" + i);
		        InputStream is = null;
		        OutputStream os = null;
		        File dest = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "p" + i + ".jpg");
		        
		        Log.d("DB", dest.toString());

		        if (photoSourcePath != null)
		        {
			        try {
			            is = new FileInputStream(photoSourcePath);
			            os = new FileOutputStream(dest);
			            byte[] buffer = new byte[1024];
			            int length;
			            while ((length = is.read(buffer)) > 0) {
			                os.write(buffer, 0, length);
			            }
			        } catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
			            try {
							is.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			            try {
							os.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }
		
		        }

		        
				finish();
				
				
			}});
		btnCancel.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}});
		btnPhoto.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// dispatchTakePictureIntent();
				
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				startActivityForResult(intent, 567);
			}});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
	    	Uri uri = Uri.parse(mCurrentPhotoPath);
	    	img.setImageURI(uri);
	    }
	}
	
	static final int REQUEST_TAKE_PHOTO = 1;

	private void dispatchTakePictureIntent() {
	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    // Ensure that there's a camera activity to handle the intent
	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	        // Create the File where the photo should go
	        File photoFile = null;
	        try {
	            photoFile = createImageFile();
	        } catch (IOException ex) {
	            // Error occurred while creating the File

	        }
	        // Continue only if the File was successfully created
	        if (photoFile != null) {
	            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
	                    Uri.fromFile(photoFile));
	            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
	        }
	    }
	}
	
	String mCurrentPhotoPath, photoSourcePath;
	
	private File createImageFile() throws IOException {
	    // Create an image file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    String imageFileName = "JPEG_" + timeStamp + "_";
	    File storageDir = Environment.getExternalStoragePublicDirectory(
	            Environment.DIRECTORY_PICTURES);
	    File image = File.createTempFile(
	        imageFileName,  /* prefix */
	        ".jpg",         /* suffix */
	        storageDir      /* directory */
	    );

	    // Save a file: path for use with ACTION_VIEW intents
	    photoSourcePath = image.getAbsolutePath();
	    mCurrentPhotoPath = "file:" + image.getAbsolutePath();
	    return image;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_data, menu);
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
