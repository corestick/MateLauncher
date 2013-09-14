package mobi.intuitit.android.weatherwidget;

import java.io.IOException;
import java.util.List;

import mobi.intuitit.android.mate.launcher.R;
import mobi.intuitit.android.weatherwidget.CurrentWeatherXmlParser.CurrentWeatherXmlData;

import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

public class CurrentWeatherData {
	private static final String TAG = "WWW";
	private CurrentWeatherXmlParser mCurrentParser = new CurrentWeatherXmlParser();
	private List<CurrentWeatherXmlData> mCurrentParsedList = null;
	private String mUrl;
	private int mStnId;

	protected CurrentWeatherData() {
	}

	public void setOption(String url, int stnId) {
		mUrl = url;
		mStnId = stnId;
	}

	// private int determineResourceByIcon(int icon){
	// switch(icon) {
	// case 1: return R.drawable.sunny;
	// case 2: return R.drawable.sunny_cloudy;
	// case 3: return R.drawable.cloudy_sunny;
	// case 4: return R.drawable.cloudy;
	// case 5: return R.drawable.sunny;
	// case 6: return R.drawable.sunny;
	// case 7: return R.drawable.sunny;
	// case 8: return R.drawable.rain;
	// case 9: return R.drawable.rain;
	// case 10: return R.drawable.snow;
	// case 11: return R.drawable.snow;
	// case 15: return R.drawable.foggy;
	// case 17: return R.drawable.foggy;
	// case 18: return R.drawable.foggy;
	// default: return R.drawable.triceratops_color;
	// }
	// }

	protected void update() {
		Log.d(TAG, "URL: " + mUrl + " stnid:" + mStnId);

		try {
			mCurrentParsedList = mCurrentParser.parseFromUrl(mUrl);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
	}

	protected int getCurrentTemp() {
		int currentTemp = 0;

		for (int i = 0; i < mCurrentParsedList.size(); i++) {
			if (mCurrentParsedList.get(i).mStnId.equals("" + mStnId)) {
				currentTemp = Math.round(Float.parseFloat(mCurrentParsedList
						.get(i).mTemp));
				break;
			}
		}
		return currentTemp;
	}

	protected String getWeatherString() {
		int icon = getIcon();

		Log.e("RRR", "Icon=" + icon);

		switch (icon) {
		case 1:
			return "맑음";
		case 2:
			return "구름 조금";
		case 3:
			return "구름 많음";
		case 4:
			return "흐림";
		case 5:
			return "맑음";
		case 6:
			return "맑음";
		case 7:
			return "맑음";
		case 8:
			return "비";
		case 9:
			return "비";
		case 10:
			return "눈";
		case 11:
			return "눈";
		case 14:
			return "비";
		case 15:
			return "안개";
		case 17:
			return "박무";
		case 18:
			return "안개";
		default:
			return "";
		}
	}

	protected int getIcon() {
		int icon = 0;

		if (mCurrentParsedList == null) {
			// ���ͳ� �ȵǸ� ����
			return 1;
		} else {

			for (int i = 0; i < mCurrentParsedList.size(); i++) {
				if (mCurrentParsedList.get(i).mStnId.equals("" + mStnId)) {
					try {
						icon = Integer
								.parseInt(mCurrentParsedList.get(i).mIcon);
					} catch (NumberFormatException nfe) {
						icon = 0;
					}
					break;
				}
			}
			return icon;
		}
	}
}
