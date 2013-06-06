package mobi.intuitit.android.homepage;

import java.io.File;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class GuestImageAdapter extends BaseAdapter {
	private Context mContext;
	int list_position;
	Uri uri;
	private String sdcard = Environment.getExternalStorageDirectory()
			.getAbsolutePath(); // sdcard °æ·Î

	// private int[] mImageID = {
	private String[] mImagePath1 = {
			sdcard + "/MateLauncher/Guest/screen0.jpg",
			sdcard + "/MateLauncher/Guest/screen1.jpg",
			sdcard + "/MateLauncher/Guest/screen2.jpg"
	};

	private String[] mImagePath2 = {
			sdcard + "/MateLauncher/Guest/screen4.jpg",
			sdcard + "/MateLauncher/Guest/screen5.jpg",
			sdcard + "/MateLauncher/Guest/screen6.jpg"
	};

	private String[] mImagePath3 = {
			sdcard + "/MateLauncher/Guest/screen7.jpg",
			sdcard + "/MateLauncher/Guest/screen8.jpg",
			sdcard + "/MateLauncher/Guest/screen9.jpg"
	};
	
	private String[] mImagePath4 = {
			sdcard + "/MateLauncher/Owner/screen0.jpg",
			sdcard + "/MateLauncher/Owner/screen1.jpg",
			sdcard + "/MateLauncher/Owner/screen2.jpg"
	};

	public GuestImageAdapter(Context c, int position) {
		mContext = c;
		list_position = position;
	}

	@Override
	public int getCount() {
		return mImagePath1.length;
		// return mImageID.length;
	}

	@Override
	public Object getItem(int position) {
		return mImagePath1[position];
		// return mImageID[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(mContext);
		if (list_position == 0) {
			uri = Uri.fromFile(new File(mImagePath1[position]));
		} else if (list_position == 1) {
			uri = Uri.fromFile(new File(mImagePath2[position]));
		} else if (list_position == 2) {
			uri = Uri.fromFile(new File(mImagePath3[position]));
		} else if (list_position == 3) {
			uri = Uri.fromFile(new File(mImagePath4[position]));
		}
		imageView.setImageURI(uri);

		imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		imageView.setLayoutParams(new Gallery.LayoutParams(150, 400));

		return imageView;
	}
}