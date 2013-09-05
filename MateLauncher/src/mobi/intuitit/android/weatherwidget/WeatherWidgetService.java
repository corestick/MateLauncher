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

import mobi.intuitit.android.mate.launcher.Launcher;
import mobi.intuitit.android.mate.launcher.MGlobal;
import mobi.intuitit.android.mate.launcher.Workspace;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
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
					Thread.sleep(1800000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	Handler mHandler = new Handler() {
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
				
//				Log.e("RRR", "-->>" + weatherStr);

				if (Launcher.getWorkspace() != null) {
					if (weatherStr.equals("����")) {
						Launcher.mWeather = MGlobal.WEATHER_SUNNY;
					} else if (weatherStr.equals("�帲")
							|| weatherStr.equals("���� ����")
							|| weatherStr.equals("���� ����")
							|| weatherStr.equals("�Ȱ�")) {

						Launcher.mWeather = MGlobal.WEATHER_CLOUD;

					} else if (weatherStr.equals("��")) {
						Launcher.mWeather = MGlobal.WEATHER_RAIN;
					} else if (weatherStr.equals("��")) {
						Launcher.mWeather = MGlobal.WEATHER_SNOW;
					}

					Workspace mWorkspace = Launcher.getWorkspace();
					mWorkspace.setWidgetImg();

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
