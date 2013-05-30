package mobi.intuitit.android.homepage;


import mobi.intuitit.android.mate.launcher.R;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.TabHost;

public class HomeMain extends TabActivity {	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		TabHost tabHost = getTabHost();
		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			tabHost.getTabWidget().getChildAt(i).getLayoutParams().height = 50;
		}
				
		LayoutInflater.from(this).inflate(R.layout.homepagemain,
				tabHost.getTabContentView(), true);

		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("홈")
				.setContent(new Intent(this, OwnerHome.class)));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("친구")
				.setContent(new Intent(this, FriendsList.class)));
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("랭킹")
				.setContent(new Intent(this, RankList.class)));
		tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator("설정")
				.setContent(new Intent(this, Setting.class)				
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
	}
	
}