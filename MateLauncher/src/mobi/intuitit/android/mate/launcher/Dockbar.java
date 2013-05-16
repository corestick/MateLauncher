package mobi.intuitit.android.mate.launcher;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import mobi.intuitit.android.mate.launcher.R.color;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Dockbar extends LinearLayout implements View.OnClickListener {

	private Launcher mLauncher;
	private TextView[] mDockButton;
	private Workspace mWorkspace;
	private View mAllAppsGrid;
	private ImageView left;
	LinearLayout linearLayout=null;
    Button saveBtn=null;
    Bitmap bm=null;

	public Dockbar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setBackgroundResource(R.drawable.dock);
	}

	public void setLauncher(Launcher launcher) {
		mLauncher = launcher;
	}

	public void setWorkspace(Workspace workspace) {
		mWorkspace = workspace;
	}

	public void setAllgridView(View allgridview) {
		mAllAppsGrid = allgridview;
	}

	public void CreateDockbar() {
		

		left = new ImageView(mLauncher);
		addView(left);
		left.setOnClickListener(this);
		LayoutParams param = (LayoutParams) left.getLayoutParams();
		param.weight = 10;
		left.setLayoutParams(param);
		Drawable drawable4 = getResources().getDrawable(R.drawable.icon_left);
		left.setImageDrawable(drawable4);

		mDockButton = new TextView[4];
		for (int i = 0; i < 4; i++) {
			mDockButton[i] = new TextView(mLauncher);
			addView(mDockButton[i]);
			mDockButton[i].setOnClickListener(this);
			LayoutParams param1 = (LayoutParams) mDockButton[i]
					.getLayoutParams();
			param1.weight = 50;
			mDockButton[i].setLayoutParams(param1);
			mDockButton[i].setPadding(2, 2, 5, 2);
			mDockButton[i].setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
			mDockButton[i].setTextSize(12);
			mDockButton[i].setTextColor(color.bright_text_dark_focused);
		}
		Drawable drawable = getResources().getDrawable(R.drawable.phone);
		drawable = Utilities.createIconThumbnail(drawable, mLauncher);
		mDockButton[0].setCompoundDrawablesWithIntrinsicBounds(null, drawable,
				null, null);
		mDockButton[0].setText("전화");

		Drawable drawable1 = getResources().getDrawable(R.drawable.contacts);
		drawable1 = Utilities.createIconThumbnail(drawable1, mLauncher);
		mDockButton[1].setCompoundDrawablesWithIntrinsicBounds(null, drawable1,
				null, null);
		mDockButton[1].setText("연락처");

		Drawable drawable2 = getResources().getDrawable(R.drawable.message);
		drawable2 = Utilities.createIconThumbnail(drawable2, mLauncher);
		mDockButton[2].setCompoundDrawablesWithIntrinsicBounds(null, drawable2,
				null, null);
		mDockButton[2].setText("문자");

		Drawable drawable3 = getResources().getDrawable(R.drawable.allapps);
		drawable3 = Utilities.createIconThumbnail(drawable3, mLauncher);
		mDockButton[3].setCompoundDrawablesWithIntrinsicBounds(null, drawable3,
				null, null);
		mDockButton[3].setText("어플리케이션");
	}

	public void showDockbar() {
		this.setVisibility(VISIBLE);
	}

	public void hideDockbar() {
		this.setVisibility(INVISIBLE);
	}

	@Override
	public void onClick(View v) {
		if (v.equals(mDockButton[0])) {

		    linearLayout=(LinearLayout) findViewById(R.id.workspace);
			linearLayout.buildDrawingCache();
			bm=linearLayout.getDrawingCache();
			
			long time = System.currentTimeMillis(); 
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss"); 
	        Date dd = new Date(time);  
	        String strTime = sdf.format(dd);  
	                
	        String sdcard=Environment.getExternalStorageDirectory().getAbsolutePath();
	        File cfile=new File(sdcard + "/ScreenShotTest");  
	        cfile.mkdirs(); //폴더가 없을 경우 ScreenShotTest 폴더생성
	        
	        String path=sdcard + "/ScreenShotTest/"  + strTime + ".jpg";  //ScreenShotTest 폴더에 시간순으로 저장
	 		try{
	 			FileOutputStream fos=new FileOutputStream(path);
	 			bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
	 			fos.flush();
	 			fos.close();
	 		}catch(Exception e){
	 			e.printStackTrace();
	 		}	 		
	 		Intent intent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
	 		Uri uri=Uri.parse("file://" + path);
	 		intent.setData(uri);
	 		getContext().sendBroadcast(intent);
	 				
			Toast.makeText(mLauncher, "스크린샷", Toast.LENGTH_SHORT).show();
			return;
		} else if (v.equals(mDockButton[1])) {
			Toast.makeText(mLauncher, "b2", Toast.LENGTH_SHORT).show();
			return;
		} else if (v.equals(mDockButton[2])) {
			Toast.makeText(mLauncher, "b3", Toast.LENGTH_SHORT).show();
			return;
		} else if (v.equals(mDockButton[3])) {
			final Rect bounds = mWorkspace.mDrawerBounds;
			mLauncher.offsetBoundsToDragLayer(bounds, mAllAppsGrid);
			mAllAppsGrid.setFocusable(true);
			mAllAppsGrid.setVisibility(View.VISIBLE);
			// mSpeechBubbleview.setVisibility(View.INVISIBLE);
			return;
		} else if (v.equals(left)) {
			hideDockbar();
			Launcher.modifyMode = true;
			mLauncher.mMDockbar.showMDockbar();
		}

	}
}
