package mobi.intuitit.android.mate.launcher;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.drawable.Drawable;

public class MObject extends ItemInfo {

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
    float scale;

    MObject() {
        itemType = LauncherSettings.BaseLauncherColumns.ITEM_TYPE_SHORTCUT;
    }
    
    public MObject(MObject info) {
        super(info);
        title = info.title.toString();
        intent = new Intent(info.intent);
        icon = info.icon;
        scale = info.scale;
    }

    /**
     * Creates the application intent based on a component name and various launch flags.
     * Sets {@link #itemType} to {@link LauncherSettings.BaseLauncherColumns#ITEM_TYPE_APPLICATION}.
     *
     * @param className the class name of the component representing the intent
     * @param launchFlags the launch flags
     */
    final void setActivity(ComponentName className, int launchFlags) {
        intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(className);
        intent.setFlags(launchFlags);
        itemType = LauncherSettings.BaseLauncherColumns.ITEM_TYPE_APPLICATION;
    }

    @Override
    void onAddToDatabase(ContentValues values) {
        super.onAddToDatabase(values);

        String titleStr = title != null ? title.toString() : null;
        values.put(LauncherSettings.BaseLauncherColumns.TITLE, titleStr);

        String uri = intent != null ? intent.toUri(0) : null;
        values.put(LauncherSettings.BaseLauncherColumns.INTENT, uri);

//        if (customIcon) {
//            values.put(LauncherSettings.BaseLauncherColumns.ICON_TYPE,
//                    LauncherSettings.BaseLauncherColumns.ICON_TYPE_BITMAP);
//            Bitmap bitmap = ((FastBitmapDrawable) icon).getBitmap();
//            writeBitmap(values, bitmap);
//        } else {
//            values.put(LauncherSettings.BaseLauncherColumns.ICON_TYPE,
//                    LauncherSettings.BaseLauncherColumns.ICON_TYPE_RESOURCE);
//            if (iconResource != null) {
//                values.put(LauncherSettings.BaseLauncherColumns.ICON_PACKAGE,
//                        iconResource.packageName);
//                values.put(LauncherSettings.BaseLauncherColumns.ICON_RESOURCE,
//                        iconResource.resourceName);
//            }
//        }
    }

    @Override
    public String toString() {
        return title.toString();
    }
}
