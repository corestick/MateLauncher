package mobi.intuitit.android.mate.launcher;

import java.util.Vector;

public class MImageList {

	public Vector<Integer> flooringList;
	public Vector<Integer> wallpaperList;
	public Vector<Integer> avatarList;
	public Vector<Integer> furnitureList;

	private static MImageList mImageList = new MImageList();

	private MImageList() {

		flooringList = new Vector<Integer>();
		wallpaperList = new Vector<Integer>();
		avatarList = new Vector<Integer>();
		furnitureList = new Vector<Integer>();

		initFlooring();
		initWallpapaer();
		initAvatar();
		initFurniture();
	}

	public static MImageList getInstance() {
		return mImageList;
	}

	private void initFlooring() {

		flooringList.add(R.drawable.m_flooring_01);
		flooringList.add(R.drawable.m_flooring_02);
		flooringList.add(R.drawable.m_flooring_03);
		flooringList.add(R.drawable.m_flooring_04);
		flooringList.add(R.drawable.m_flooring_05);
		flooringList.add(R.drawable.m_flooring_06);
		flooringList.add(R.drawable.m_flooring_07);
		flooringList.add(R.drawable.m_flooring_08);
//		flooringList.add(R.drawable.m_flooring_09);
		

	}

	private void initWallpapaer() {

		wallpaperList.add(R.drawable.m_wall_01);
		wallpaperList.add(R.drawable.m_wall_02);
		wallpaperList.add(R.drawable.m_wall_03);
		wallpaperList.add(R.drawable.m_wall_04);
		wallpaperList.add(R.drawable.m_wall_05);
		wallpaperList.add(R.drawable.m_wall_06);
		wallpaperList.add(R.drawable.m_wall_07);
		wallpaperList.add(R.drawable.m_wall_08);
//		wallpaperList.add(R.drawable.m_wall_09);
//		wallpaperList.add(R.drawable.m_wall_010);

	}

	private void initAvatar() {

		avatarList.add(R.drawable.m_avatar_01);
		avatarList.add(R.drawable.m_avatar_02);
		avatarList.add(R.drawable.m_avatar_03);
		avatarList.add(R.drawable.m_avatar_04);
		avatarList.add(R.drawable.m_avatar_05);
		avatarList.add(R.drawable.m_avatar_06);

	}

	private void initFurniture() {

		furnitureList.add(R.drawable.m_furniture_01);
		furnitureList.add(R.drawable.m_furniture_02);
		furnitureList.add(R.drawable.m_furniture_03);
		furnitureList.add(R.drawable.m_furniture_04);
		furnitureList.add(R.drawable.m_furniture_05);
		furnitureList.add(R.drawable.m_furniture_06);
		furnitureList.add(R.drawable.m_furniture_07);
		
	}
}
