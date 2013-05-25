package mobi.intuitit.android.mate.launcher;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MDockbar extends LinearLayout implements View.OnClickListener {

	public final int HIDE_MODE = 0;
	public final int FURNITURE_MODE = 1;
	public final int WALLPAPER_MODE = 2;
	public final int FLOORING_MODE = 3;
	public final int AVATAR_MODE = 4;

	public int mMDockbarMode = HIDE_MODE;

	private ImageButton mFurniture;
	private ImageButton mWallpaper;
	private ImageButton mFlooring;
	private ImageButton mAvatar;
	private ImageButton mHomepage;
	private ImageButton mRight;

	Launcher launcher;

	Context context;

	MobjectAdapter mFurnitureAdapter;
	MobjectAdapter mWallpaperAdapter;
	MobjectAdapter mFlooringAdapter;
	MobjectAdapter mAvatarAdapter;
	ArrayList<Mobject> mFurnitureList;
	ArrayList<Mobject> mWallpaperList;
	ArrayList<Mobject> mFlooringList;
	ArrayList<Mobject> mAvatarList;

//	Bitmap bm = null; // 스크린샷용 비트맵

	public MDockbar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.setPadding(2, 2, 2, 2);
		this.setBackgroundResource(R.drawable.dock);
	}

	public void initMDockbar(Launcher launcher) {
		this.launcher = launcher;

		mFurniture = new ImageButton(context);
		mFlooring = new ImageButton(context);
		mWallpaper = new ImageButton(context);
		mAvatar = new ImageButton(context);
		mHomepage = new ImageButton(context);
		mRight = new ImageButton(context);

		mFurniture.setBackgroundResource(R.drawable.icon_furniture);
		mFlooring.setBackgroundResource(R.drawable.icon_flooring);
		mWallpaper.setBackgroundResource(R.drawable.icon_wallpaper);
		mAvatar.setBackgroundResource(R.drawable.icon_avatar);
		mHomepage.setBackgroundResource(R.drawable.icon_homepage);
		mRight.setBackgroundResource(R.drawable.icon_right);

		addView(mFurniture);
		LayoutParams params = (LayoutParams) mFurniture.getLayoutParams();
		params.weight = 50;
		mFurniture.setLayoutParams(params);

		addView(mWallpaper);
		params = (LayoutParams) mFurniture.getLayoutParams();
		params.weight = 50;
		mWallpaper.setLayoutParams(params);

		addView(mFlooring);
		params = (LayoutParams) mFlooring.getLayoutParams();
		params.weight = 50;
		mFlooring.setLayoutParams(params);

		addView(mAvatar);
		params = (LayoutParams) mAvatar.getLayoutParams();
		params.weight = 50;
		mAvatar.setLayoutParams(params);

		addView(mHomepage);
		params = (LayoutParams) mHomepage.getLayoutParams();
		params.weight = 50;
		mHomepage.setLayoutParams(params);

		addView(mRight);
		params = (LayoutParams) mRight.getLayoutParams();
		params.weight = 10;
		mRight.setLayoutParams(params);

		mFurniture.setOnClickListener(this);
		mWallpaper.setOnClickListener(this);
		mFlooring.setOnClickListener(this);
		mAvatar.setOnClickListener(this);
		mHomepage.setOnClickListener(this);
		mRight.setOnClickListener(this);

		initAdapter();
	}

	public void initAdapter() {
		mFurnitureList = new ArrayList<Mobject>();
		mWallpaperList = new ArrayList<Mobject>();
		mFlooringList = new ArrayList<Mobject>();
		mAvatarList = new ArrayList<Mobject>();

		for (int i = 0; i < MImageList.getInstance().furnitureList.size(); i++) {
			Mobject mObject = new Mobject();
			mObject.icon = getResources().getDrawable(
					MImageList.getInstance().furnitureList.get(i));

			mFurnitureList.add(mObject);
		}
		mFurnitureAdapter = new MobjectAdapter(launcher, mFurnitureList);

		for (int i = 0; i < MImageList.getInstance().wallpaperList.size(); i++) {
			Mobject mObject = new Mobject();
			mObject.icon = getResources().getDrawable(
					MImageList.getInstance().wallpaperList.get(i));

			mWallpaperList.add(mObject);
		}
		mWallpaperAdapter = new MobjectAdapter(launcher, mWallpaperList);

		for (int i = 0; i < MImageList.getInstance().flooringList.size(); i++) {
			Mobject mObject = new Mobject();
			mObject.icon = getResources().getDrawable(
					MImageList.getInstance().flooringList.get(i));

			mFlooringList.add(mObject);
		}
		mFlooringAdapter = new MobjectAdapter(launcher, mFlooringList);

		for (int i = 0; i < MImageList.getInstance().avatarList.size(); i++) {
			Mobject mObject = new Mobject();
			mObject.icon = getResources().getDrawable(
					MImageList.getInstance().avatarList.get(i));

			mAvatarList.add(mObject);
		}
		mAvatarAdapter = new MobjectAdapter(launcher, mAvatarList);
	}

	public void showMDockbar() {
		this.setVisibility(VISIBLE);
	}

	public void hideMDockbar() {
		this.setVisibility(INVISIBLE);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stubs

		if (v.equals(mFurniture)) {
			if (mMDockbarMode == FURNITURE_MODE) {
				launcher.mObjectView.setVisibility(View.GONE);
				mMDockbarMode = HIDE_MODE;
			} else {
				launcher.mObjectView.setAdapter(mFurnitureAdapter);
				launcher.mObjectView.setVisibility(View.VISIBLE);
				mMDockbarMode = FURNITURE_MODE;
			}
			return;
		}

		if (v.equals(mWallpaper)) {

			if (mMDockbarMode == WALLPAPER_MODE) {
				launcher.mObjectView.setVisibility(View.GONE);
				mMDockbarMode = HIDE_MODE;
			} else {
				launcher.mObjectView.setAdapter(mWallpaperAdapter);
				launcher.mObjectView.setVisibility(View.VISIBLE);
				mMDockbarMode = WALLPAPER_MODE;
			}
			return;
		}

		if (v.equals(mFlooring)) {
			if (mMDockbarMode == FLOORING_MODE) {
				launcher.mObjectView.setVisibility(View.GONE);
				mMDockbarMode = HIDE_MODE;
			} else {
				launcher.mObjectView.setAdapter(mFlooringAdapter);
				launcher.mObjectView.setVisibility(View.VISIBLE);
				mMDockbarMode = FLOORING_MODE;
			}
			return;
		}

		if (v.equals(mAvatar)) {
			if (mMDockbarMode == AVATAR_MODE) {
				launcher.mObjectView.setVisibility(View.GONE);
				mMDockbarMode = HIDE_MODE;
			} else {
				launcher.mObjectView.setAdapter(mAvatarAdapter);
				launcher.mObjectView.setVisibility(View.VISIBLE);
				mMDockbarMode = AVATAR_MODE;
			}
			return;
		}
		if (v.equals(mHomepage)) {

			// 캡쳐
			// int ScreenNum[] = {R.id.cell1, R.id.cell2, R.id.cell3};
			String sdcard = Environment.getExternalStorageDirectory()
					.getAbsolutePath();
			File cfile = new File(sdcard
					+ "/Android/data/mobi.intuitit.android/");
			cfile.mkdirs();
//			View captureView = launcher.getWorkspace();
//			captureView.getRootView().buildDrawingCache();
//			bm = captureView.getRootView().getDrawingCache();

			for (int i = 0; i < 3; i++) {
				String path = sdcard + "/Android/data/mobi.intuitit.android/"
						+ "screen"+i+".jpg";
				try {
					FileOutputStream fos = new FileOutputStream(path);
					launcher.sScreens[i].compress(Bitmap.CompressFormat.JPEG, 100, fos);
					fos.flush();
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
//				Intent intentScreen = new Intent(
//						Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//				Uri uri = Uri.parse("file://" + path);
//				intentScreen.setData(uri);
//				launcher.sendBroadcast(intentScreen);
			}

			// 런처 홈으로 이동
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.setClassName("com.LBL.launcherhome",
					"com.LBL.launcherhome.Main");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			launcher.startActivity(intent);
			return;
		}

		if (v.equals(mRight)) {
			mMDockbarMode = HIDE_MODE;
			launcher.mObjectView.setVisibility(View.GONE);
			hideMDockbar();
			Launcher.modifyMode = false;
			launcher.mDockbar.showDockbar();
			return;
		}
	}

	public boolean isDraggable() {
		switch (mMDockbarMode) {
		case FURNITURE_MODE:
		case AVATAR_MODE:
			return true;
		case WALLPAPER_MODE:
		case FLOORING_MODE:
			return false;
		}
		return false;
	}
}
