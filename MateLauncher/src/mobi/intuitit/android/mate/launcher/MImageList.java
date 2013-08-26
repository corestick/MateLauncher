package mobi.intuitit.android.mate.launcher;

import java.util.Vector;

import android.R.integer;

public class MImageList {

	public Vector<Integer> backgroundList;
	public Vector<Integer> avatarList;
	public Vector<Integer> furnitureList;
	public Vector<Integer> widgetList;

	private static MImageList mImageList = new MImageList();

	private MImageList() {

		backgroundList = new Vector<Integer>();
		avatarList = new Vector<Integer>();
		furnitureList = new Vector<Integer>();
		widgetList = new Vector<Integer>();

		initBackground();
		initAvatar();
		initFurniture();
		initWidget();
	}

	public static MImageList getInstance() {
		return mImageList;
	}

	private void initBackground() {

		backgroundList.add(R.drawable.m_wall_01);
		backgroundList.add(R.drawable.m_wall_02);
		backgroundList.add(R.drawable.m_wall_03);
		backgroundList.add(R.drawable.m_wall_04);
		backgroundList.add(R.drawable.m_wall_05);
		backgroundList.add(R.drawable.m_wall_06);
		backgroundList.add(R.drawable.m_wall_07);
		backgroundList.add(R.drawable.m_wall_08);
		backgroundList.add(R.drawable.m_wall_09);
		backgroundList.add(R.drawable.m_wall_10);
		backgroundList.add(R.drawable.m_wall_11);
		backgroundList.add(R.drawable.m_wall_12);
		backgroundList.add(R.drawable.m_wall_13);
		backgroundList.add(R.drawable.m_wall_14);
		backgroundList.add(R.drawable.m_wall_15);	
	}

	private void initAvatar() {
		avatarList.add(R.drawable.m_avatar_01);
		avatarList.add(R.drawable.m_avatar_02);
		avatarList.add(R.drawable.m_avatar_03);
		avatarList.add(R.drawable.m_avatar_04);
		avatarList.add(R.drawable.m_avatar_05);		
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
	}

	private void initWidget() {
		widgetList.add(R.drawable.sunny);
		widgetList.add(R.drawable.big_sunny);
		widgetList.add(R.drawable.cloud);
		widgetList.add(R.drawable.big_cloud);
		widgetList.add(R.drawable.rain);
		widgetList.add(R.drawable.big_rain);
		widgetList.add(R.drawable.snow);
		widgetList.add(R.drawable.big_snow);
	}

	public int getIcon(int resType, int resIdx) {

		switch (resType) {
		case 0: // ����
			return furnitureList.get(resIdx);
		case 1: // �ƹ�Ÿ
			return avatarList.get(resIdx);
		case 2: // ����
			return widgetList.get(resIdx);
		default:
			return -1;
		}
	}
}
