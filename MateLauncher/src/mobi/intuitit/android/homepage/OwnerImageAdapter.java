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

public class OwnerImageAdapter extends BaseAdapter {
	private Context mContext;
	private String sdcard = Environment.getExternalStorageDirectory()
			.getAbsolutePath(); // sdcard °æ·Î

	private String[] mImagePath = {
			sdcard + "Android/data/mobi.intuitit.android/owner/screen0.jpg",
			sdcard + "Android/data/mobi.intuitit.android/owner/screen1.jpg", 
			sdcard + "Android/data/mobi.intuitit.android/owner/screen2.jpg"
	};

	public OwnerImageAdapter(Context c) {
		mContext = c;
	}

	@Override
	public int getCount() {
		return mImagePath.length;
		// return mImageID.length;
	}

	@Override
	public Object getItem(int position) {
		return mImagePath[position];
		// return mImageID[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(mContext);

		// Uri uri = Uri.parse(mImagePath[position]);
		Uri uri = Uri.fromFile(new File(mImagePath[position]));
		Log.e("na" + position, uri.toString());
		imageView.setImageURI(uri);

		// imageView.setImageResource(mImageID[position]);
		// Log.e("na"+position, mImageID[position]+"");

		imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		imageView.setLayoutParams(new Gallery.LayoutParams(150, 400));		

		return imageView;
	}

}