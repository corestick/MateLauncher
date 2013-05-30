package mobi.intuitit.android.mate.launcher;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public class MAvatarView extends ViewGroup {

	private Launcher mLauncher;
	
	private MobjectImageView mobjectImageView;
	private TextView txtSpeechBubble;
	private ScrollView scrollView;
	
	private boolean mBackgroundSizeChanged;
	private Drawable mBackground;
	
	public MAvatarView(Context context) {
		super(context);
	}

	public MAvatarView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MAvatarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public void setLauncher(Launcher launcher) {
		this.mLauncher = launcher;
	}
	
	public void initMAvatarView() {
		
		Log.e("RRR", "initMAvatarView");
		mobjectImageView = new MobjectImageView(mLauncher);
		scrollView = new ScrollView(mLauncher);
		txtSpeechBubble = new TextView(mLauncher);
		
		
		
		this.addView(scrollView);
		this.addView(mobjectImageView);
		
//		setFocusable(true);
//		mBackground = getBackground();
//		setBackgroundDrawable(null);
//		mBackground.setCallback(this);
		Log.e("RRR", "initMAvatarView22");
		
	}
	
	public void setAvatarImage(int resId) {
//		mobjectImageView.setImageResource(resId);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		
		LayoutParams svLP = (LayoutParams) scrollView.getLayoutParams();
		LayoutParams mLP = (LayoutParams) mobjectImageView.getLayoutParams();
		
//		scrollView.layout(0, 0, 80, 60);
	}
	
	public void setAvatarMessage(String msg) {
		txtSpeechBubble.setText(msg);
	}
	
	

}
