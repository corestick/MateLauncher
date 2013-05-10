package mobi.intuitit.android.mate.launcher;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
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
		}

		if (v.equals(mRight)) {
			mMDockbarMode = HIDE_MODE;
			launcher.mObjectView.setVisibility(View.GONE);
			hideMDockbar();
			Launcher.modifyMode = false;
			launcher.mDockbar.showDockbar();
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
