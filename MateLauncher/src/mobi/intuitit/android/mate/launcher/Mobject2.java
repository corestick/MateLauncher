package mobi.intuitit.android.mate.launcher;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.drawable.Drawable;

public class Mobject2 extends ItemInfo {

	/**
	 * The application name.
	 */
	CharSequence title;

	/**
	 * The intent used to start the application.
	 */
	Intent intent;

	/**
	 * The application icon.
	 */
	Drawable icon;

	/**
	 * ������ ������
	 */
	// float scale;
	
	Mobject2() {
		itemType = LauncherSettings.BaseLauncherColumns.ITEM_TYPE_SHORTCUT;
	}

	public Mobject2(Mobject2 info) {
		super(info);
	}

	@Override
	void onAddToDatabase(ContentValues values) {
		super.onAddToDatabase(values);
	}

	@Override
	public String toString() {
		return title.toString();
	}
}
