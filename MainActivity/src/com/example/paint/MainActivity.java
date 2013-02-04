package com.example.paint;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public TextView value,redValue,greenValue,blueValue,square;
    public SeekBar seekbar, redSeekbar, greenSeekbar,blueSeekbar;
    public static int brush_size=30,red_value=255,green_value=0,blue_value=0;
    public static String brush_style="Circle";
    public static Bitmap image =null;
    private Intent intent;
    public static ArrayList <Snap> list = new ArrayList <Snap> ();
    public static int indexOfLast;
    private static int width,height;
    private static File photoPath ;
    private static String currentPhotoPath;
	private AlbumStorageDirFactory mAlbumStorageDirFactory = null;

    
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //getDimensions of the Screen
        Display display = getWindowManager().getDefaultDisplay();
      		Point size = new Point ();
      		display.getSize(size);
      		width = size.x;
      		height = size.y;
      		//done getting dimensions 
      		//Huawei Ascend P1, width: 540 height: 960
        value = (TextView) findViewById(R.id.textview);
        seekbar = (SeekBar) findViewById(R.id.seekbar);
        redSeekbar = (SeekBar) findViewById(R.id.redseek);
        greenSeekbar = (SeekBar) findViewById(R.id.greenseek);
        blueSeekbar = (SeekBar) findViewById(R.id.blueseek);
        
        square = (TextView) findViewById(R.id.colorSquare);
        redValue = (TextView) findViewById(R.id.redValue);
        blueValue = (TextView) findViewById(R.id.blueValue);
        greenValue = (TextView) findViewById(R.id.greenValue);

        seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
        {
        	public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser)
        	{
               // TODO Auto-generated method stub
               value.setText((progress+5)+"px");
               brush_size=progress+5;
            }
            public void onStartTrackingTouch(SeekBar seekBar)
            {
                                        
            }

            public void onStopTrackingTouch(SeekBar seekBar)
            {
            	
            }
        }
        
        );
        
        redSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
        {
        	public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser)
        	{
               // TODO Auto-generated method stub
               redValue.setText(progress+"");
               red_value=progress;
               square.setBackgroundColor(Color.rgb(red_value,green_value,blue_value));
               
            }
            public void onStartTrackingTouch(SeekBar seekBar)
            {
                                        
            }

            public void onStopTrackingTouch(SeekBar seekBar)
            {
            	
            }
        }
        
        );
        
        greenSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
        {
        	public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser)
        	{
               // TODO Auto-generated method stub
        		 greenValue.setText(progress+"");
                 green_value=progress;
                 square.setBackgroundColor(Color.rgb(red_value,green_value,blue_value));

            }
            public void onStartTrackingTouch(SeekBar seekBar)
            {
                                        
            }

            public void onStopTrackingTouch(SeekBar seekBar)
            {
            	
            }
        }
        
        );
        
        blueSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
        {
        	public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser)
        	{
               // TODO Auto-generated method stub
        		blueValue.setText(progress+"");
                blue_value=progress;
                square.setBackgroundColor(Color.rgb(red_value,green_value,blue_value));

            }
            public void onStartTrackingTouch(SeekBar seekBar)
            {
                                        
            }

            public void onStopTrackingTouch(SeekBar seekBar)
            {
            	
            }
        }
        
        );
        
  
      
    }
    
    public void onPause()
    {
    	super.onPause();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public boolean onTouchEvent(MotionEvent event)//called if no other things handle the touch
    {
    	return true;
    }
    
//    public void sendMessage(View view)
//    {
//    	Intent intent = new Intent(this, DisplayMessageActivity.class);
//        EditText editText = (EditText) findViewById(R.id.edit_message);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
//    }
    
    
    
    public void openGl(View view)//action taken on start of painting
    {
    	 	Button b = (Button)view;
    	    String buttonText = b.getText().toString();
    	    if (buttonText.equals("New"))
    	    {

    	    	image=null;
    		}
    	    	
    	    	
    	
    	  RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radiogroup);
          int checkedRadioButton = radioGroup.getCheckedRadioButtonId();
          
          
          switch (checkedRadioButton) {
            case R.id.radiobutton1 : 
                brush_style="Circle";
                break;
            case R.id.radiobutton2 : 
          		brush_style="Square";
          		break;
            case R.id.radiobutton3 :
            	brush_style="Spray";
            	break;
            case R.id.radioButton4 :
            	brush_style="Fill";
          }
//    	Intent intent = new Intent(this, OpenGL.class);
      	intent = new Intent(this, CanvasActivity.class);
        startActivity(intent);
    }
    
    public void saveImage (View view)//method called when save button is pressed
    {
    	  if (image!=null)  
          {
    		System.out.println("SAVING");
          	String root = Environment.getExternalStorageDirectory().toString();
          	File myDir = new File(root + "/saved_images");    
          	myDir.mkdirs();
          	Random generator = new Random();
          	int n = 10000;
          	n = generator.nextInt(n);
          	String fname = "Image-"+ n +".jpg";
          	File file = new File (myDir, fname);
          	if (file.exists ()) file.delete (); 
          	try {
          	       FileOutputStream out = new FileOutputStream(file);
          	       image.compress(Bitmap.CompressFormat.JPEG, 90, out);
          	       out.flush();
          	       out.close();

          	} catch (Exception e) {
          	       e.printStackTrace();
          	}
          	sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
          }
    	
    }
    
    //method called on button click
    public void Picture(View v)
    {
		dispatchTakePictureIntent(1);//take picture

    }
    
    //called when the camera button is pressed
    private void dispatchTakePictureIntent(int actionCode) {

		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		switch(actionCode) {
		case 1:
			File f = null;
			
			try {
				f = setUpPhotoFile();
				currentPhotoPath = f.getAbsolutePath();
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
			} catch (IOException e) {
				e.printStackTrace();
				f = null;
				currentPhotoPath = null;
			}
			break;

		default:
			break;			
		} // switch

		startActivityForResult(takePictureIntent, actionCode);
    }
    
private File setUpPhotoFile() throws IOException {
		
		File f = createImageFile();
		currentPhotoPath = f.getAbsolutePath();
		
		return f;
	}
    

private String getAlbumName() {
	return getString(R.string.album_name);
}
      
    private File getAlbumDir() {
    	File storageDir = null;

    	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
			mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
		} 
    	else {
			System.out.println("BASE");
		}
    	
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			
			storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

			if (storageDir != null) {
				if (! storageDir.mkdirs()) {
					if (! storageDir.exists()){
						Log.d("CameraSample", "failed to create directory");
						return null;
					}
				}
			}
			
		} else {
			Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
		}
		
		return storageDir;
	}
    
    private File createImageFile() throws IOException{
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File albumF = getAlbumDir();
		File imageF = File.createTempFile(imageFileName, ".jpg", albumF);
        return imageF;
    }
    
    private void galleryAddPic() {
	    Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
		File f = new File(currentPhotoPath);
	    Uri contentUri = Uri.fromFile(f);
	    mediaScanIntent.setData(contentUri);
	    this.sendBroadcast(mediaScanIntent);
}
    
    //when the picture gets taken
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
		if (currentPhotoPath != null) {
			setPic();
//			galleryAddPic();
			currentPhotoPath = null;
		}
	}

    
    
    private void setPic() {
//    	Bundle extras = intent.getExtras();
//		Bitmap temp = (Bitmap) extras.get("data");//get the bitmap image
//		
//		int [] pixels = new int[temp.getHeight()*temp.getWidth()];
//		System.out.println(temp.getHeight()+","+temp.getWidth());
//		temp.getPixels(pixels,0,temp.getWidth(),0,0,temp.getWidth(),temp.getHeight());
//		Bitmap b = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)
//				profileImage.setImageBitmap(Bitmap.createScaledBitmap(b, 120, 120, false));
//		image.setPixels(pixels,0,temp.getWidth(),0,0,width,height);
     	
    	    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
    	    bmOptions.inJustDecodeBounds = true;
    	    BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
    	    int photoW = bmOptions.outWidth;
    	    int photoH = bmOptions.outHeight;
    	    
    	    //scales the image from the camera to the display area.  (screen resolution)
    	    int scaleFactor = 1;
   		 	System.out.println("PWidth:"+photoW+","+photoH);
    		 System.out.println("Width:"+width+","+height);
    		 
    		 System.out.println("HERE");
    		if ((width > 0) || (height > 0)) {
    			scaleFactor = Math.min(photoW/width, photoH/height);	
    		}	
   		 System.out.println("Scale Factor:"+scaleFactor);

    		    		
    		 bmOptions.inJustDecodeBounds = false;
    		 bmOptions.inSampleSize = scaleFactor+1;
    		 bmOptions.inPurgeable = true;
    		  
    		 image = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
    		 image = Bitmap.createScaledBitmap(image,width,height,false);   
    		 System.out.println(image.getWidth()+","+image.getHeight());
    		 System.out.println("HERE");
    	     intent = new Intent(this, CanvasActivity.class);
    	     startActivity(intent);
    }
  
    
    
    public static int getWidth()
    {
    	return width;	
    }
    
    public static int getHeight()
    {
    	return height;
    }
    
    
    
    
    
}
