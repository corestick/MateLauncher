package mobi.intuitit.android.mate.launcher;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MDockbar extends LinearLayout implements View.OnClickListener {
	
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
			if (launcher.mObjectView.mObjectViewType == launcher.mObjectView.FURNITURE) {
				launcher.mObjectView.hideMobjectView();
			} else {
				launcher.mObjectView.setAdapter(mFurnitureAdapter);
				launcher.mObjectView.setVisibility(View.VISIBLE);
				launcher.mObjectView.mObjectViewType = launcher.mObjectView.FURNITURE;
			}
			return;
		}

		if (v.equals(mWallpaper)) {

			if (launcher.mObjectView.mObjectViewType == launcher.mObjectView.WALLPAPER) {
				launcher.mObjectView.hideMobjectView();
			} else {
				launcher.mObjectView.setAdapter(mWallpaperAdapter);
				launcher.mObjectView.setVisibility(View.VISIBLE);
				launcher.mObjectView.mObjectViewType = launcher.mObjectView.WALLPAPER;
			}
			return;
		}

		if (v.equals(mFlooring)) {
			if (launcher.mObjectView.mObjectViewType == launcher.mObjectView.FLOORING) {
				launcher.mObjectView.hideMobjectView();
			} else {
				launcher.mObjectView.setAdapter(mFlooringAdapter);
				launcher.mObjectView.setVisibility(View.VISIBLE);
				launcher.mObjectView.mObjectViewType = launcher.mObjectView.FLOORING;
			}
			return;
		}

		if (v.equals(mAvatar)) {
			if (launcher.mObjectView.mObjectViewType == launcher.mObjectView.AVATAR) {
				launcher.mObjectView.hideMobjectView();
			} else {
				launcher.mObjectView.setAdapter(mAvatarAdapter);
				launcher.mObjectView.setVisibility(View.VISIBLE);
				launcher.mObjectView.mObjectViewType = launcher.mObjectView.AVATAR;
			}
			return;
		}
		if (v.equals(mHomepage)) {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.setClassName("com.LBL.launcherhome",
					"com.LBL.launcherhome.Main");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			launcher.startActivity(intent);
			return;
		}

		if (v.equals(mRight)) {
			launcher.mObjectView.hideMobjectView();
			hideMDockbar();
			Launcher.modifyMode = false;
			launcher.mDockbar.showDockbar();
			return;
		}
	}
}
