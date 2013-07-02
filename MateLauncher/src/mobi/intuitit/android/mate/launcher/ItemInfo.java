/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mobi.intuitit.android.mate.launcher;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Represents an item in the launcher.
 */
class ItemInfo {
    
    static final int NO_ID = -1;
    
    /**
     * The id in the settings database for this item
     */
    long id = NO_ID;
    
    /**
     * One of {@link LauncherSettings.Favorites#ITEM_TYPE_APPLICATION},
     * {@link LauncherSettings.Favorites#ITEM_TYPE_SHORTCUT},
     * {@link LauncherSettings.Favorites#ITEM_TYPE_USER_FOLDER}, or
     * {@link LauncherSettings.Favorites#ITEM_TYPE_APPWIDGET}.
     */
    int itemType;
    
    /**
     * The id of the container that holds this item. For the desktop, this will be 
     * {@link LauncherSettings.Favorites#CONTAINER_DESKTOP}. For the all applications folder it
     * will be {@link #NO_ID} (since it is not stored in the settings DB). For user folders
     * it will be the id of the folder.
     */
    long container = NO_ID;
    
    /**
     * Iindicates the screen in which the shortcut appears.
     */
    int screen = -1;
    
    /**
     * Indicates the X position of the associated cell.
     */
    int cellX = -1;

    /**
     * Indicates the Y position of the associated cell.
     */
    int cellY = -1;

    /**
     * Indicates the X cell span.
     */
    int spanX = 1;

    /**
     * Indicates the Y cell span.
     */
    int spanY = 1;

    /**
     * Indicates whether the item is a gesture.
     */
    boolean isGesture = false;
    
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
     * When set to true, indicates that the icon has been resized.
     */
    boolean filtered;
    
    int mobjectType = -1; // 0 = 가구, 1 = 아바타
    int mobjectIcon = -1;
    
    String contact_num;
    String contact_name;

    ItemInfo() {
    }

    ItemInfo(ItemInfo info) {
        id = info.id;
        cellX = info.cellX;
        cellY = info.cellY;
        spanX = info.spanX;
        spanY = info.spanY;
        screen = info.screen;
        itemType = info.itemType;
        container = info.container;
        mobjectType = info.mobjectType;
        mobjectIcon = info.mobjectIcon;
    }

    /**
     * Write the fields of this item to the DB
     * 
     * @param values
     */
    void onAddToDatabase(ContentValues values) { 
        values.put(LauncherSettings.BaseLauncherColumns.ITEM_TYPE, itemType);
        if (!isGesture) {
            values.put(LauncherSettings.Favorites.CONTAINER, container);
            values.put(LauncherSettings.Favorites.SCREEN, screen);
            values.put(LauncherSettings.Favorites.CELLX, cellX);
            values.put(LauncherSettings.Favorites.CELLY, cellY);
            values.put(LauncherSettings.Favorites.SPANX, spanX);
            values.put(LauncherSettings.Favorites.SPANY, spanY);
            values.put(LauncherSettings.Favorites.MOBJECT_TYPE, mobjectType);
            values.put(LauncherSettings.Favorites.MOBJECT_ICON, mobjectIcon);
            String titleStr = null;
    		values.put(LauncherSettings.BaseLauncherColumns.TITLE, titleStr);
    		String uri = null;
    		values.put(LauncherSettings.BaseLauncherColumns.INTENT, uri);
    		
    		values.put(LauncherSettings.BaseLauncherColumns.CONTACT_NUM, contact_num);
    		values.put(LauncherSettings.BaseLauncherColumns.CONTACT_NAME, contact_name);
        }
    }

    static void writeBitmap(ContentValues values, Bitmap bitmap) {
        if (bitmap != null) {
            // Try go guesstimate how much space the icon will take when serialized
            // to avoid unnecessary allocations/copies during the write.
            int size = bitmap.getWidth() * bitmap.getHeight() * 4;
            ByteArrayOutputStream out = new ByteArrayOutputStream(size);
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
                

                values.put(LauncherSettings.Favorites.ICON, out.toByteArray());
            } catch (IOException e) {
                Log.w("Favorite", "Could not write icon");
            }
        }
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
