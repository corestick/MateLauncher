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

package mobi.intutit.android.weatherwidget;



import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * This is the service that provides the factory to be bound to the collection service.
 */
public class WeatherWidgetService extends Service {
	private WeatherDataManager mWeatherDataManager;
	private Context mContext;
	private int mAppWidgetId;
	
	private static HandlerThread sWorkerThread;
	private static Handler sWorkerQueue;
	private Handler mHandler;

	static {
		sWorkerThread = new HandlerThread("WeatherActivity-worker");
		sWorkerThread.start();
		sWorkerQueue = new Handler(sWorkerThread.getLooper());
	}
	
	@Override
	public void onCreate()
	{
		Log.e("w11", "w1111");
		super.onCreate();		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		Log.e("w22", "weee");
		mContext = this.getApplicationContext();
		mWeatherDataManager = WeatherDataManager.getInstance(mContext);
		mAppWidgetId = intent.getIntExtra("id", 0);
		
		sWorkerQueue.post(new Runnable() {
			public void run() {
				Location location = new Location(LocationManager.NETWORK_PROVIDER);
				location.setLatitude(37.566535);
				location.setLongitude(126.977969);			
				mWeatherDataManager.setLocation(location);
//				Message.obtain(mHandler, 0, 0).sendToTarget();
			}
		});
		return startId;		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}