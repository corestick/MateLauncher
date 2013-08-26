package mobi.intuitit.android.weatherwidget;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class LocationHelper {
	/**
	 * Context ��ü �Դϴ�.
	 */
	private Context mContext;
	/**
	 * �������� ������ ���� �Դϴ�.
	 */
	private double mLat;
	/**
	 * �浵���� ������ ���� �Դϴ�.
	 */
	private double mLng;
	/**
	 * GPS�� ��Ȯ�� �Դϴ�.
	 */
	private int mAccuracy;
	/**
	 * ���������� ������ ���� �Դϴ�.
	 */
	private boolean isLocationSetting = false;
	/**
	 * ACCURACY�� ���� (�ڼ���)
	 */
	public static final int ACCURACY_FINE = 1;
	/**
	 * ACCURACY�� ���� (����)
	 */
	public static final int ACCURACY_COARSE = 2;

	public LocationHelper(Context context) {
		this.mContext = context;
		this.mAccuracy = ACCURACY_COARSE;
	}

	/**
	 * ������ �����Ѵ�.
	 */
	public void run() {
		final LocationManager locationManager = (LocationManager) mContext
				.getSystemService(Context.LOCATION_SERVICE);
		final Criteria criteria = new Criteria();
		criteria.setAccuracy(mAccuracy);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);

		// Provider ����
		final String bestProvider = locationManager.getBestProvider(criteria,
				true);
		locationManager.requestLocationUpdates(bestProvider, 2000, 10,
				mLocationListener);

		Location location = locationManager.getLastKnownLocation(bestProvider);
		updateWithNewLocation(location);
	}

	private final LocationListener mLocationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			updateWithNewLocation(location);
		}

		public void onProviderDisabled(String provider) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};

	private void updateWithNewLocation(Location location) {
		if (location == null) {
			isLocationSetting = false;
			return;
		}

		mLat = location.getLatitude();
		mLng = location.getLongitude();
		isLocationSetting = true;

//		Toast.makeText(mContext, "Location �� ���� �Ǿ����ϴ�.", Toast.LENGTH_SHORT)
//				.show();
	}

	/**
	 * �������� �����մϴ�.
	 * 
	 * @return
	 */
	public double getLat() {
		return mLat;
	}

	/**
	 * �浵���� �����մϴ�.
	 * 
	 * @return
	 */
	public double getLng() {
		return mLng;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isLocationSetting() {
		return isLocationSetting;
	}

	/**
	 * Criteria ��ü�� Accuracy�� �����Ѵ�.
	 * 
	 * @param accuracy
	 */
	public void setAccuracy(int accuracy) {
		mAccuracy = accuracy;
	}

	/**
	 * ������ �浵���� �������� �ּҰ��� �����մϴ�.
	 * 
	 * @param context
	 *            Activity
	 * @param lat
	 *            ����
	 * @param lon
	 *            �浵
	 * @return
	 */
	public String getAddress() {
		return LocationHelper.getAddress(mContext, mLat, mLng);
	}

	/**
	 * ������ ������ �浵���� �������� �ּҸ� �����մϴ�. static Ű����� ����
	 * 
	 * @param lat
	 *            ����
	 * @param lng
	 *            �浵
	 * @return
	 */
	public static String getAddress(Context context, double lat, double lng) {
		String addressString = "No address found";
		Geocoder gc = new Geocoder(context, Locale.getDefault());
		try {
			List<Address> addresses = gc.getFromLocation(lat, lng, 1);
			if (addresses.size() > 0) {
				Address address = addresses.get(0);
				addressString = address.getAddressLine(0);
				addressString = addressString.substring(addressString
						.indexOf(" ") + 1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return addressString;
	}
}