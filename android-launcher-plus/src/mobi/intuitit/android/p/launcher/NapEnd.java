package mobi.intuitit.android.p.launcher;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;

public class NapEnd extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);

		NotificationManager NM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		NM.cancel(1);
		
		finish();
	}
}
