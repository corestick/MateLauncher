package mobi.intuitit.android.mate.launcher;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.drawable.Drawable;

public class Mobject extends ItemInfo {

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
	 * 아이콘 사이즈
	 */
	// float scale;
	
	Mobject() {
		itemType = LauncherSettings.BaseLauncherColumns.ITEM_TYPE_SHORTCUT;
	}

	public Mobject(Mobject info) {
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
