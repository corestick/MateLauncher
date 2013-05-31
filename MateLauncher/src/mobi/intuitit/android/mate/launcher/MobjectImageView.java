package mobi.intuitit.android.mate.launcher;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class MobjectImageView extends ViewGroup {
	private boolean mBackgroundSizeChanged;
	private Drawable mBackground;
	
	private Launcher mLauncher;
	private ImageView mobjectImageView;
	private TextView txtSpeechBubble;
	private ScrollView scrollView;
	
	public MobjectImageView(Context context) {
		super(context);
	}

	public MobjectImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MobjectImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public void setLauncher(Launcher launcher) {
		this.mLauncher = launcher;
	}
	
	public void initMAvatarView() {
		
//		setFocusable(true);
//		mBackground = getBackground();
//		setBackgroundDrawable(null);
//		mBackground.setCallback(this);
		
		this.setBackgroundDrawable(getResources().getDrawable(R.drawable.screen_plate));
		
		Log.e("RRR", "initMAvatarView");
		
		mobjectImageView = new ImageView(mLauncher);
		scrollView = new ScrollView(mLauncher);
		txtSpeechBubble = new TextView(mLauncher);
		txtSpeechBubble.setBackgroundDrawable(getResources().getDrawable(R.drawable.speechbubble));
		
//		txtSpeechBubble.
		
		
		
		
		
		this.addView(scrollView);
		this.addView(mobjectImageView);
		
		Log.e("RRR", "initMAvatarView22");
		
	}

//	@Override
//	protected boolean setFrame(int left, int top, int right, int bottom) {
//		if (getLeft() != left || getRight() != right || getTop() != top
//				|| getBottom() != bottom) {
//			mBackgroundSizeChanged = true;
//		}
//		return super.setFrame(left, top, right, bottom);
//	}

	@Override
	protected boolean verifyDrawable(Drawable who) {
		return who == mBackground || super.verifyDrawable(who);
	}

	@Override
	protected void drawableStateChanged() {
		Drawable d = mBackground;
		if (d != null && d.isStateful()) {
			d.setState(getDrawableState());
		}
		super.drawableStateChanged();
	}

	@Override
	public void draw(Canvas canvas) {
		final Drawable background = mBackground;
		if (background != null) {
			final int scrollX = getScrollX();
			final int scrollY = getScrollY();

			if (mBackgroundSizeChanged) {
				background.setBounds(0, 0, getRight() - getLeft(), getBottom()
						- getTop());
				mBackgroundSizeChanged = false;
			}

			if ((scrollX | scrollY) == 0) {
				background.draw(canvas);
			} else {
				canvas.translate(scrollX, scrollY);
				background.draw(canvas);
				canvas.translate(-scrollX, -scrollY);
			}
		}

		super.draw(canvas);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		
		Log.e("RRR", "onLayout=" + changed);
	}
	
	public void setObjectImage(int resId) {
		mobjectImageView.setImageResource(resId);
	}
}
