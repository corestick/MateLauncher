package mobi.intuitit.android.mate.launcher;

import java.util.HashMap;

import android.util.Log;

public class MImageList {

	private HashMap<String, Integer> flooringList;
	private HashMap<String, Integer> wallpaperList;
	private HashMap<String, Integer> avatarList;
	private HashMap<String, Integer> furnitureList;
	
	private static MImageList mImageList = new MImageList();

	private MImageList() {
		
		flooringList = new HashMap<String, Integer>();
		wallpaperList = new HashMap<String, Integer>();
		avatarList = new HashMap<String, Integer>();
		furnitureList = new HashMap<String, Integer>();
		
		initFlooring();
		initWallpapaer();
		initAvatar();
		initFurniture();
	}
	
	public static MImageList getInstance() {
		return mImageList;
	}

	private void initFlooring() {
		
		flooringList.put("1", R.drawable.m_flooring_01);

	}

	private void initWallpapaer() {

		wallpaperList.put("1", R.drawable.m_wall_01);

	}

	private void initAvatar() {

		avatarList.put("1", R.drawable.m_avatar_01);

	}

	private void initFurniture() {

		furnitureList.put("audio_01", R.drawable.m_audio_01);
		furnitureList.put("tv_01", R.drawable.m_tv_01);
		furnitureList.put("phone_01", R.drawable.m_phone_01);

	}

	public int getFlooring(String Idx) {

		return flooringList.get(Idx); 
	}
	
	public int getWallpaper(String Idx) {

		return wallpaperList.get(Idx); 
	}
	
	public int getAvatar(String Idx) {

		return avatarList.get(Idx); 
	}
	
	public int getFurniture(String Idx) {

		return furnitureList.get(Idx); 
	}
}
