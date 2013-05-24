package mobi.intuitit.android.mate.launcher;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.Drawable;

class ItemType extends ItemInfo {

	CharSequence title;
	boolean filtered;
	Drawable icon;
	Intent intent;

	public ItemType(ItemType info) {
		super(info);
	}

	public ItemType() {
		super();
	}

	public void setActivity(ComponentName componentName, int launchFlags) {
		// TODO Auto-generated method stub
		intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		intent.setComponent(componentName);
		intent.setFlags(launchFlags);
		itemType = LauncherSettings.BaseLauncherColumns.ITEM_TYPE_APPLICATION;
	}
}
