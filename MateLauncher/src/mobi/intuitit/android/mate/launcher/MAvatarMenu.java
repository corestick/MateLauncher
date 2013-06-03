package mobi.intuitit.android.mate.launcher;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MAvatarMenu extends LinearLayout implements OnClickListener {

	private int mVisibleState = INVISIBLE;

	ImageView imgCall;
	ImageView imgSMS;
	ImageView imgKakaoTalk;
	ImageView imgTwitter;
	ImageView imgFacebook;
	
	private Launcher mLauncher;
	String mContacts;

	public MAvatarMenu(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MAvatarMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public void initMAvatarMenu(Launcher launcher, String contacts) {
		setVisible(mVisibleState);
		
		this.mLauncher = launcher;
		this.mContacts = contacts;
		
		imgCall = (ImageView) findViewById(R.id.imgCall);
		imgSMS = (ImageView) findViewById(R.id.imgSMS);
		imgKakaoTalk = (ImageView) findViewById(R.id.imgKakaoTalk);
		imgTwitter = (ImageView) findViewById(R.id.imgTwitter);
		imgFacebook = (ImageView) findViewById(R.id.imgFacebook);

		imgCall.setOnClickListener(this);
		imgSMS.setOnClickListener(this);
		imgKakaoTalk.setOnClickListener(this);
		imgTwitter.setOnClickListener(this);
		imgFacebook.setOnClickListener(this);

	}

	public int getVisible() {
		return mVisibleState;
	}

	public void setVisible(int mVisible) {
		mVisibleState = mVisible;
		this.setVisibility(mVisibleState);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.equals(imgCall)) {
			Intent intent = new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel:" + mContacts));
			mLauncher.startActivity(intent);
		} else if (v.equals(imgSMS)) {
			
		} else if (v.equals(imgKakaoTalk)) {
			
		} else if (v.equals(imgTwitter)) {
			
		} else if (v.equals(imgFacebook)) {
			
		}
	}

}
