package mobi.intuitit.android.mate.launcher;

import android.content.Context;
import android.util.AttributeSet;

public class MFolder extends Folder {
	

	public MFolder(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mInfo = new FolderInfo();
		mInfo.itemType = LauncherSettings.Favorites.ITEM_TYPE_USER_FOLDER;
	}
	

}
