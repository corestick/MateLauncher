package mobi.intuitit.android.homepage;

import java.io.File;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class GuestImageAdapter extends BaseAdapter {
	private Context mContext;
	private String sdcard = Environment.getExternalStorageDirectory()
			.getAbsolutePath();	// sdcard °æ·Î

//	private int[] mImageID = {
	private String[] mImagePath = {	
			sdcard+"/Test/screen0.jpg",
			sdcard+"/Test/screen1.jpg",
			sdcard+"/Test/screen2.jpg" 
//			R.drawable.sample_0,
//			R.drawable.sample_1,
//			R.drawable.sample_2
			};

	public GuestImageAdapter(Context c) {
		mContext = c;
	}

	@Override
	public int getCount() {
		return mImagePath.length;
//		return mImageID.length;
	}

	@Override
	public Object getItem(int position) {
		return mImagePath[position];
//		return mImageID[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(mContext);
		
		Uri uri = Uri.fromFile(new File(mImagePath[position])); 
		Log.e("na"+position, uri.toString());
		imageView.setImageURI(uri);		
		
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		imageView.setLayoutParams(new Gallery.LayoutParams(300, 400));
			
		return imageView;
	}	
}