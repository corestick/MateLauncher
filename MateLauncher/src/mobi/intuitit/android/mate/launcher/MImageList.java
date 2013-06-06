package mobi.intuitit.android.mate.launcher;

import java.util.Vector;

import android.R.integer;

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
		avatarList.add(R.drawable.m_avatar_07);
		avatarList.add(R.drawable.m_avatar_08);

	}

	private void initFurniture() {
						
		furnitureList.add(R.drawable.m_furniture_01);
		furnitureList.add(R.drawable.m_furniture_02);
		furnitureList.add(R.drawable.m_furniture_03);
		furnitureList.add(R.drawable.m_furniture_04);
		furnitureList.add(R.drawable.m_furniture_05);
		furnitureList.add(R.drawable.m_furniture_06);
		furnitureList.add(R.drawable.m_furniture_07);
		furnitureList.add(R.drawable.m_furniture_08);
		furnitureList.add(R.drawable.m_furniture_09);
		furnitureList.add(R.drawable.m_furniture_10);
		furnitureList.add(R.drawable.m_furniture_11);
		furnitureList.add(R.drawable.m_furniture_12);
		furnitureList.add(R.drawable.m_furniture_13);
		furnitureList.add(R.drawable.m_furniture_14);
		furnitureList.add(R.drawable.m_furniture_15);
		furnitureList.add(R.drawable.m_furniture_16);
		furnitureList.add(R.drawable.m_furniture_17);
		furnitureList.add(R.drawable.m_furniture_18);
		furnitureList.add(R.drawable.m_furniture_19);
		furnitureList.add(R.drawable.m_furniture_20);
		furnitureList.add(R.drawable.m_furniture_21);
		furnitureList.add(R.drawable.m_furniture_22);
		furnitureList.add(R.drawable.m_furniture_23);
		furnitureList.add(R.drawable.m_furniture_24);
		furnitureList.add(R.drawable.m_furniture_25);
		furnitureList.add(R.drawable.m_furniture_26);
		furnitureList.add(R.drawable.m_furniture_27);
		furnitureList.add(R.drawable.m_furniture_28);
		furnitureList.add(R.drawable.m_furniture_29);
		furnitureList.add(R.drawable.m_furniture_30);
		furnitureList.add(R.drawable.m_furniture_31);
		furnitureList.add(R.drawable.m_furniture_32);
		furnitureList.add(R.drawable.m_furniture_33);
		furnitureList.add(R.drawable.m_furniture_34);
		furnitureList.add(R.drawable.m_furniture_35);
		furnitureList.add(R.drawable.m_furniture_36);
		furnitureList.add(R.drawable.m_furniture_37);
		furnitureList.add(R.drawable.m_furniture_38);
		furnitureList.add(R.drawable.m_furniture_39);
		furnitureList.add(R.drawable.m_furniture_40);
		
	}
	
	public int getIcon(int resType, int resIdx) {
		if(resType == 0) { //����
			return furnitureList.get(resIdx);
		}
		else
			return avatarList.get(resIdx);
	}
}
