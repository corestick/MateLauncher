/*
 * Copyright (C) 2011 The Android Open Source Project
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

package mobi.intuitit.android.weatherwidget;

import mobi.intuitit.android.mate.launcher.ItemInfo;
import mobi.intuitit.android.mate.launcher.Launcher;
import mobi.intuitit.android.mate.launcher.LayoutType;
import mobi.intuitit.android.mate.launcher.MGlobal;
import mobi.intuitit.android.mate.launcher.MLayout;
import mobi.intuitit.android.mate.launcher.MobjectImageView;
import mobi.intuitit.android.mate.launcher.R;
import mobi.intuitit.android.mate.launcher.Workspace;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

/**
 * This is the service that provides the factory to be bound to the collection
 * service.
 */
public class WeatherWidgetService extends Service {
	private WeatherDataManager mWeatherDataManager;
	private Context mContext;

	private LocationHelper lh;
	public String weatherStr;

	class NewThread extends Thread {
		WeatherWidgetService mParent;
		private Handler mHandler;

		public NewThread(WeatherWidgetService parent, Handler handler) {
			mHandler = handler;
			mParent = parent;
		}

		public void run() {
			while (true) {
				Message msg = new Message();
				msg.what = 0;
				mHandler.sendMessage(msg);
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	Handler mHandler = new Handler() {
		Drawable d;

		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				lh.run();
				Log.e("RRR", "Lat=" + lh.getLat());
				Log.e("RRR", "Lng=" + lh.getLng());

				Location location = new Location(
						LocationManager.NETWORK_PROVIDER);
				location.setLatitude(lh.getLat());
				location.setLongitude(lh.getLng());
				mWeatherDataManager.setLocation(location);
				weatherStr = mWeatherDataManager.getCurrentWeather();
				Log.e("CW", weatherStr);

				if (Launcher.getWorkspace() != null) {
					Workspace mWorkspace = Launcher.getWorkspace();
					for (int i = 0; i < mWorkspace.getChildCount(); i++) {						
						LayoutType layoutType = (LayoutType) mWorkspace
								.getChildAt(i);
						if (layoutType instanceof MLayout) {							
							for (int j = 0; j < layoutType.getChildCount(); j++) {								
								if (layoutType.getChildAt(j) instanceof MobjectImageView) {
									MobjectImageView mObjectImageView = (MobjectImageView) layoutType
											.getChildAt(j);
									ItemInfo itemInfo = (ItemInfo) mObjectImageView
											.getTag();									
									if (itemInfo.mobjectType == MGlobal.MOBJECTTYPE_WIDGET) {
										Log.e("RRR", "chang-weather");
										if (weatherStr.equals("맑음")) {
											// 0이면 작은 아이콘 1이면 큰아이콘
											if (itemInfo.mobjectIcon == 0) {
												d = getResources().getDrawable(
														R.drawable.sunny);
											} else {
												d = getResources().getDrawable(
														R.drawable.big_sunny);
											}
											mObjectImageView
													.setBackgroundDrawable(d);

										} else if (weatherStr.equals("흐림")
												|| weatherStr.equals("구름 조금")
												|| weatherStr.equals("구름 많음")
												|| weatherStr.equals("안개")) {

											if (itemInfo.mobjectIcon == 0) {
												d = getResources().getDrawable(
														R.drawable.cloud);
											} else {
												d = getResources().getDrawable(
														R.drawable.big_cloud);
											}
											mObjectImageView
													.setBackgroundDrawable(d);

										} else if (weatherStr.equals("비")) {
											if (itemInfo.mobjectIcon == 0) {
												d = getResources().getDrawable(
														R.drawable.rain);
											} else {
												d = getResources().getDrawable(
														R.drawable.big_rain);
											}
											mObjectImageView
													.setBackgroundDrawable(d);
										} else if (weatherStr.equals("눈")) {
											if (itemInfo.mobjectIcon == 0) {
												d = getResources().getDrawable(
														R.drawable.snow);
											} else {
												d = getResources().getDrawable(
														R.drawable.big_snow);
											}
											mObjectImageView
													.setBackgroundDrawable(d);
										}
									}

								}
							}
						}
					}
				}
			}
		}
	};

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mContext = this.getApplicationContext();
		mWeatherDataManager = WeatherDataManager.getInstance(mContext);
		lh = new LocationHelper(mContext);
		NewThread th = new NewThread(this, mHandler);
		th.start();
		return startId;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
